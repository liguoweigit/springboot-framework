package com.yongche.cache.redisbinary;

import com.google.common.base.Charsets;
import com.yongche.enumdata.CompressModelEnum;
import com.yongche.util.CompressUtil;
import jmind.core.lang.shard.LoadBalance;

import java.util.Map;

/**
 * Created by mma on 16/12/28.
 */
public class NioRedisBinary extends RedisBinary{

    private int shardSize = 0;


    public NioRedisBinary(String hosts, int timeout) {
        this.shardSize = hosts.split(",").length;
        bootstarp = new RedisBootstrapBinary(hosts, timeout);
        c = bootstarp.connectAsync();

    }
    // hosts  ip:port,ip:port,ip:port
    public NioRedisBinary(String hosts, int timeout, LoadBalance.Balance balance) {
        this.shardSize = hosts.split(",").length;
        bootstarp = new RedisBootstrapBinary(hosts, timeout);
        c = bootstarp.connectAsync(balance);

    }

    @Override
    public int getShardSize() {
        return shardSize;
    }

    @Override
    public String set(String key, byte[] value) {
        return c.await(c.set(key, value));
    }

    @Override
    public byte[] get(String key) {
        return c.await(c.get(key));
    }

    @Override
    public byte[] hget(String key, String field) {
        return c.await(c.hget(key,field));
    }

    @Override
    public String hmset(String key, Map<String, byte[]> hash) {
        return c.await(c.hmset(key, hash));
    }

    @Override
    public String setex(String key, int seconds, byte[] value) {
        return c.await(c.setex(key,seconds,value));
    }

    @Override
    public Long setnx(String key, byte[] value) {
        return c.await(c.setnx(key,value));
    }

    @Override
    byte[] compress(byte[] input, CompressModelEnum model) throws Exception {
        return CompressUtil.compressData(input,model);
    }

    @Override
    String decompress(byte[] input, CompressModelEnum model) throws Exception {
        byte[] decompressData = CompressUtil.decompressData(input,model);
        return new String(decompressData, Charsets.UTF_8);
    }


}
