package com.yongche.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yongche.cache.CacheKeys;
import com.yongche.cache.CacheManager;
import com.yongche.config.ConfigConsts;
import com.yongche.enumdata.CompressModelEnum;
import jmind.core.util.DataUtil;
import jmind.core.util.GlobalConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单， 车列表缓存的一些操作
 * Created by xieweibo on 2016/11/9.
 */
@Service
public class OrderCacheService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 从redis获得车辆信息,判断压缩开关状态
     * @param serviceOrderId
     * @param round
     * @return
     */
    public <T> List<T> getCarListFromRedis(long serviceOrderId, int round,boolean old,Class<T> t){
        String key = CacheKeys.PUB_SELECT_DRIVER_PREFIX + serviceOrderId + GlobalConstants.DASH + round;
        if(old){
            key = key + "old";
        }
        boolean isCompress = ConfigConsts.isDictConfig(ConfigConsts.CACHE_DATE_COMPRESS);
        String orderStr = null;
        if(isCompress){
            try {
                orderStr = CacheManager.getInstance().getRedisBinary().getStringBinaryValue(key, CompressModelEnum.UNCOMPRESS_COMPATIBLE_MODEL);
            } catch (Exception e) {
                logger.error("getOrderFromRedisWithSwitch error orderId:{} | round:{} | e:{}",
                        serviceOrderId,round,e);
                e.printStackTrace();
                return null;
            }
        }else{
            orderStr = CacheManager.getInstance().getRedis().get(key);
        }
        return JSON.parseObject(orderStr, new TypeReference<List<T>>(t) {});

    }

    /**
     * 获取接单司机数
     * @param serviceOrderId 订单数
     * @return
     */
    public Integer getResponseDriverNum(Long serviceOrderId){
        Integer responseDriverNum = 0;
        try {
            String key = DataUtil.join(GlobalConstants.COLON, CacheKeys.RESPONSE_DRIVER_NUM_KEY, serviceOrderId);
            String responseDriverStr = CacheManager.getInstance().getRedis().get(key);
            if(StringUtils.isNotBlank(responseDriverStr)){
                responseDriverNum = JSONObject.parseObject(responseDriverStr).getInteger("order_accept_num");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.warn("getResponseDriverNum error,serviceOrderId:{},errorMessage:{}",serviceOrderId,e.getMessage());
        }
        logger.info("getResponseDriverNum success,serviceOrderId:{},responseDriverNum:{}",serviceOrderId,responseDriverNum);
        return responseDriverNum;
    }
}
