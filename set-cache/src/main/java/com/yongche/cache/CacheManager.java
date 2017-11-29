package com.yongche.cache;


import com.yongche.cache.redisbinary.NioRedisBinary;
import com.yongche.cache.redisbinary.RedisBinaryFace;
import com.yongche.config.ConfigConsts;
import jmind.core.cache.ConcurrentLRUCache;
import jmind.core.cache.MemCache;
import jmind.core.cache.support.GuavaCache;
import jmind.core.lang.shard.LoadBalance;
import jmind.core.redis.NioRedis;
import jmind.core.redis.Redis;

import java.util.concurrent.ConcurrentHashMap;

public class CacheManager {

    private CacheManager() {

    }

    public static CacheManager getInstance() {
        return Nested.instance;
    }

    static class Nested {
        private static final CacheManager instance = new CacheManager();
    }

    private final MemCache<String, Object> guava = new GuavaCache<String, Object>(6000, 1, false);

    private final ConcurrentLRUCache<String, Object> PersistentCache = new ConcurrentLRUCache<String, Object>(
            new ConcurrentHashMap<String, Object>());

    private final Redis redis = new NioRedis(ConfigConsts.getSouceProperties().getProperty("redis.choose.host"), 10, LoadBalance.Balance.Time33);
    // private final Redis redis = new LoadBalanceRedis(ConfigConsts.getSouceProperties().getProperty("redis.choose.host"), 10);

    /*在jmind基础上,增加了value为的二进制的接口,key不变,还是string类型,测试redis,php喜欢用10.0.11.207:6381,10.0.11.202:6382*/
    private final RedisBinaryFace redisBinary = new NioRedisBinary(ConfigConsts.getSouceProperties().getProperty("redis.choose.host"), 10, LoadBalance.Balance.Time33);

    private final Redis redisService = new NioRedis(ConfigConsts.getSouceProperties().getProperty("redis.dispatch-service.host"), 10, LoadBalance.Balance.Time33);



    public MemCache<String, Object> getCache(MemCache.Type type) {
        switch (type) {
            case GUAVA:
                return guava;
            case PERSISTENT:
                return PersistentCache;
            default:
                return guava;
        }
    }

    public final Redis getRedis() {
        return redis;
    }

    public final RedisBinaryFace getRedisBinary(){
        return redisBinary;
    }

    public final Redis getRedisService() {
        return redisService;
    }

    /**
     * 基于redis实现的分布式锁
     *
     * @return
     */
//    RedissonManager.create("choose",ConfigConsts.getSouceProperties());
//    public final RedissonClient getRedisson() {
//        return redissonClient;
//    }


}
