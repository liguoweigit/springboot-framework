package com.yongche.cache.redisbinary;

import jmind.core.lang.shard.LoadBalance;
import jmind.core.util.AddrUtil;
import jmind.redis.*;
import jmind.redis.protocol.Command;
import jmind.redis.protocol.CommandHandler;
import jmind.redis.protocol.RedisWatchdog;
import jmind.redis.pubsub.PubSubCommandHandler;
import jmind.redis.pubsub.RedisPubSub;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.ClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by mma on 16/12/28.
 */
public class RedisBootstrapBinary {

    private final ClientBootstrap bootstrap;
    private final Timer timer;
    private final ChannelGroup channels;

    private final List<InetSocketAddress> addrs;
    private final int timeout;

    public RedisBootstrapBinary(String hosts) {
        this(hosts, 30);

    }

    public RedisBootstrapBinary(String hosts, int timeout) {
        this.timeout = timeout;
        addrs = AddrUtil.getAddress(hosts);
        ExecutorService connectors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        ExecutorService workers = Executors.newCachedThreadPool();
        ClientSocketChannelFactory factory = new NioClientSocketChannelFactory(connectors, workers);
        bootstrap = new ClientBootstrap(factory);
        bootstrap.setOption("connectTimeoutMillis", TimeUnit.SECONDS.toMillis(timeout));

        channels = new DefaultChannelGroup();
        timer = new HashedWheelTimer();

    }

    public RedisCmd<String, byte[]> connectAsync() {
        return connectAsync(LoadBalance.Balance.Hash);

    }

    public RedisCmd<String, byte[]> connectAsync(LoadBalance.Balance balance) {
        List<RedisHandler<String, byte[]>> redis = new CopyOnWriteArrayList<RedisHandler<String, byte[]>>();
        for (InetSocketAddress address : addrs) {
            RedisHandler<String, byte[]> handler = connectAsync(address);
            if (handler != null)
                redis.add(handler);
        }
        if (redis.size() == 1) {
            return new SingleRedisCmd<String, byte[]>(redis);

        }else if(balance== LoadBalance.Balance.Time33){
            return new Time33RedisCmd<String,byte[]>(redis);
        }
        return new RedisCmd<String, byte[]>(redis);

    }

    /**
     * Open a new pub/sub connection to the redis server that treats
     * keys and values as UTF-8 strings.
     *
     * @return A new connection.
     */
    public RedisPubSub<String, byte[]> connectPubSub() {
        Utf8CodecBinary codec = new Utf8CodecBinary();
        BlockingQueue<Command<String, byte[], ?>> queue = new LinkedBlockingQueue<Command<String, byte[], ?>>();
        PubSubCommandHandler<String, byte[]> handler = new PubSubCommandHandler<String, byte[]>(queue, codec);
        RedisPubSub<String, byte[]> connection = new RedisPubSub<String, byte[]>(queue, codec, timeout);

        try {
            RedisWatchdog watchdog = new RedisWatchdog(bootstrap, channels, timer, addrs.get(0), RedisHandler.class);
            ChannelPipeline pipeline = Channels.pipeline(watchdog, handler, connection);
            Channel channel = bootstrap.getFactory().newChannel(pipeline);

            ChannelFuture future = channel.connect(addrs.get(0));
            //            bootstrap.setPipeline(pipeline);
            //            ChannelFuture future = bootstrap.connect((SocketAddress) bootstrap.getOption("remoteAddress"));
            future.await();

            if (!future.isSuccess()) {
                throw future.getCause();
            }

            watchdog.setReconnect(true);
            this.channels.add(future.getChannel());
            return connection;
        } catch (Throwable e) {
            throw new RedisException("Unable to connect", e);
        }
    }

    private RedisHandler<String, byte[]> connectAsync(InetSocketAddress address) {
        Utf8CodecBinary codec = new Utf8CodecBinary();
        BlockingQueue<Command<String, byte[], ?>> queue = new LinkedBlockingQueue<Command<String, byte[], ?>>();

        CommandHandler<String, byte[]> handler = new CommandHandler<String, byte[]>(queue);
        RedisHandler<String, byte[]> redisHandler = new RedisHandler<String, byte[]>(queue, codec, timeout);

        try {
            RedisWatchdog watchdog = new RedisWatchdog(bootstrap, channels, timer, address, RedisHandler.class);
            ChannelPipeline pipeline = Channels.pipeline(watchdog, handler, redisHandler);

            bootstrap.setPipeline(pipeline);
            ChannelFuture future = bootstrap.connect(address);
            future.await();
            if (!future.isSuccess()) {
                future.getCause().printStackTrace();
                return null;
            }

            watchdog.setReconnect(true);
            this.channels.add(future.getChannel());
            return redisHandler;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Shutdown this client and close all open connections. The client should be
     * discarded after calling shutdown.
     */
    public void shutdown() {
        for (Channel c : channels) {
            ChannelPipeline pipeline = c.getPipeline();
            RedisHandler<?, ?> connection = pipeline.get(RedisHandler.class);
            connection.close();
        }
        ChannelGroupFuture future = channels.close();
        future.awaitUninterruptibly();
        bootstrap.releaseExternalResources();
    }
}
