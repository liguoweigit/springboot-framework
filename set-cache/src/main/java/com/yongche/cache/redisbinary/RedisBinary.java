package com.yongche.cache.redisbinary;

import com.google.common.base.Charsets;
import com.yongche.enumdata.CompressModelEnum;
import jmind.redis.RedisCmd;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by mma on 16/12/28.
 */
public abstract class RedisBinary implements RedisBinaryFace{

    static final Logger logger = LoggerFactory.getLogger(RedisBinary.class);

    protected RedisCmd<String, byte[]> c = null;
    protected RedisBootstrapBinary bootstarp = null;

    @Override
    public Object getResource() {
        return c;
    }

    @Override
    public void releaseResource() {
        if(null != bootstarp){
            bootstarp.shutdown();
        }
    }

    @Override
    public void close(Object resource) {

    }

    public String setBinaryValue(String key, String value,CompressModelEnum compressModelEnum) throws Exception{
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("set key must be not null");
        }
        byte[] compressedData = convertStringToByte(value,compressModelEnum);
        if(logger.isDebugEnabled()){
            logger.debug("setBinaryValue key:{} and compressedData size:{}",key,compressedData.length);
        }
        return c.await(c.set(key, compressedData));
    }

    public String setexBinaryValue(String key, int seconds, String value,CompressModelEnum compressModelEnum)throws Exception {
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("setex key must be not null");
        }
        byte[] compressedData = convertStringToByte(value,compressModelEnum);
        if(logger.isDebugEnabled()){
            logger.debug("setexBinaryValue key:{} and compressedData size:{}",key,compressedData.length);
        }
        return c.await(c.setex(key, seconds,compressedData));
    }

    public Long hsetBinaryValue(String key,String field,String value,CompressModelEnum compressModelEnum)throws Exception {
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("hset key or fidls must be not null! key:" + key + " field:"+field);
        }
        byte[] compressedData = convertStringToByte(value,compressModelEnum);
        if(logger.isDebugEnabled()){
            logger.debug("hsetBinaryValue key:{} field:{} and compressedData size:{}",key,field,compressedData.length);
        }
        return c.await(c.hset(key, field,compressedData));
    }

    public String hgetStringValue(String key,String field,CompressModelEnum compressModelEnum)throws Exception {
        if(StringUtils.isBlank(key) || StringUtils.isBlank(field)){
            throw new NullPointerException("hget key or fidls must be not null! key:" + key + " field:"+field);
        }
        byte[] compressData = c.await(c.hget(key,field));
        if(null == compressData || compressData.length == 0){
            return null;
        }
        String data = convertByte2String(compressData,compressModelEnum);
        if(logger.isDebugEnabled()){
            logger.debug("hgetStringValue key:{} and field:{} result:{}",key,field,data);
        }
        return data;
    }

    public String hmsetBinaryValue(String key,Map<String,String> value,CompressModelEnum compressModelEnum)throws Exception {
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("hmset key must be not null");
        }
        Map<String,byte[]> compressMap = new HashMap<>(value.size() * 2);
        Iterator<Map.Entry<String,String>> iterator = value.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> entry = iterator.next();
            byte[] compressedData = convertStringToByte(entry.getValue(),compressModelEnum);
            compressMap.put(entry.getKey(),compressedData);
        }
        if(logger.isDebugEnabled()){
            logger.debug("hmsetBinaryValue key:{} and compressMap size:{}",key,compressMap.size());
        }
        return c.await(c.hmset(key, compressMap));
    }

    public Map<String,String> hgetAllStringValue(String key,CompressModelEnum compressModelEnum)throws Exception {
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("hgetall key must be not null");
        }
        Map<String,byte[]> compressMap = c.await(c.hgetall(key));
        if(MapUtils.isEmpty(compressMap)){
            return null;
        }
        Map<String,String> resultMap = new HashMap<>(compressMap.size() * 2);
        Iterator<Map.Entry<String,byte[]>> iterator = compressMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,byte[]> entry = iterator.next();
            String decData = convertByte2String(entry.getValue(),compressModelEnum);
            resultMap.put(entry.getKey(),decData);
        }
        if(logger.isDebugEnabled()){
            logger.debug("hgetAllStringValue key:{} and resultMap size:{}",key,resultMap.size());
        }
        return resultMap;
    }

    public Long setnxBinaryValue(String key, String value,CompressModelEnum compressModelEnum) throws Exception{
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("setnx key must be not null");
        }
        byte[] compressedData = convertStringToByte(value,compressModelEnum);
        if(logger.isDebugEnabled()){
            logger.debug("setnxBinaryValue key:{} and compressedData size:{}",key,compressedData.length);
        }
        return c.await(c.setnx(key,compressedData));
    }

    public String getStringBinaryValue(String key,CompressModelEnum compressModelEnum) throws Exception{
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("get key must be not null");
        }
        byte[] input = c.await(c.get(key));
        if(null != input){
            if(logger.isDebugEnabled()){
                logger.debug("getStringBinaryValue key:{} and input data size:{}",key,input.length);
            }
            return convertByte2String(input,compressModelEnum);
        }
        return null;
    }

    //压缩数据
    private byte[] convertStringToByte(String value,CompressModelEnum compressModelEnum)throws Exception{
        if(StringUtils.isBlank(value)){
            throw new NullPointerException("set value must be not null");
        }
        if(null == compressModelEnum){
            compressModelEnum = CompressModelEnum.COMPRESS_COMMON_LEVEL_COMPATIBLE_MODEL;
        }
        byte[] input = value.getBytes(Charsets.UTF_8);
        return compress(input,compressModelEnum);
    }

    //解压数据
    private String convertByte2String(byte[] value,CompressModelEnum compressModelEnum)throws Exception{
        if(null == value || value.length == 0){
            throw new NullPointerException("convertByte2String value must be not null");
        }
        if(null == compressModelEnum){
            compressModelEnum = CompressModelEnum.UNCOMPRESS_COMPATIBLE_MODEL;
        }
        return decompress(value,compressModelEnum);
    }


    abstract byte[] compress(byte[] input, CompressModelEnum model)throws Exception;

    abstract String decompress(byte[] input, CompressModelEnum model)throws Exception;
}
