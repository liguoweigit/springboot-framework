package com.yongche.cache.redisbinary;


import com.yongche.enumdata.CompressModelEnum;

import java.util.Map;

/**
 * Created by mma on 16/12/28.
 */
public interface RedisBinaryFace {
    /**
     * 获取实例
     * 2014-1-17
     * @return
     */
    Object getResource();

    /**
     * 释放整个资源
     * 2014-1-8
     */
    void releaseResource();

    /**
     * 关闭单个连接
     * 2014-1-8
     * @param resource
     */
    public void close(Object resource);

    int getShardSize();

    String set(String key, byte[] value) ;

    String setex(String key, int seconds, byte[] value);

    Long setnx(String key, byte[] value);

    byte[] get(String key);

    byte[] hget(String key, String field);

    String hmset(String key, Map<String, byte[]> hash);

    String setBinaryValue(String key, String value, CompressModelEnum compressModelEnum) throws Exception;

    String setexBinaryValue(String key, int seconds, String value, CompressModelEnum compressModelEnum)throws Exception;

    Long setnxBinaryValue(String key, String value, CompressModelEnum compressModelEnum) throws Exception;

    String getStringBinaryValue(String key, CompressModelEnum compressModelEnum) throws Exception;

    Long hsetBinaryValue(String key, String field, String value, CompressModelEnum compressModelEnum)throws Exception;

    String hgetStringValue(String key, String field, CompressModelEnum compressModelEnum)throws Exception;

    String hmsetBinaryValue(String key, Map<String, String> value, CompressModelEnum compressModelEnum)throws Exception;

    Map<String,String> hgetAllStringValue(String key, CompressModelEnum compressModelEnum)throws Exception;
}
