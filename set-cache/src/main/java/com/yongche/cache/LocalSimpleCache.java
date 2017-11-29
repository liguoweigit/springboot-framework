package com.yongche.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mma on 17/1/18.
 */
public class LocalSimpleCache {

    private static class SingletonHolder{
        private final static LocalSimpleCache instance = new LocalSimpleCache();
    }

    private LocalSimpleCache(){}

    public static LocalSimpleCache getInstance(){
        return SingletonHolder.instance;
    }

    private Map<String,Object> cache = new ConcurrentHashMap<String,Object>();

    public Map<String,Object> getCache(){
        return cache;
    }


}
