package com.yongche.util;

import com.google.common.collect.Maps;
import com.yongche.config.ConfigConsts;
import com.yongche.psf.PSFClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;


/**
 * psf 服务
 * Created by xieweibo on 2016/11/4.
 */
public class PSFManager {
    final Logger logger = LoggerFactory.getLogger(getClass());

    static class Nested {
        private static final PSFManager instance = new PSFManager();
    }

    public static PSFManager getManager() {
        return Nested.instance;
    }

    private PSFClient psf ;

    private PSFManager(){
        try {
             //psf = new PSFClient(ConfigConsts.getSouceProperties().getProperty("psf.services").split(","),2000, 30000, 524288, 3);
            psf = new PSFClient(ConfigConsts.getSouceProperties().getProperty("psf.services").split(","),2000, 30000, 524288, 3,30,2000);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("PSFClient init failure! errorMessage={}",e);
        }
    }


    public String call(String serviceType, PSFClient.PSFRPCRequestData request){
        try {
           return psf.call(serviceType,request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("PSFClient call is error!");
        }
        return null ;
    }

    /**
     * 选车完成记录日志
     * @param orderId
     * @param actionName
     * @return
     */
    public String orderTrack(String orderId,String actionName){
        PSFClient.PSFRPCRequestData request=new PSFClient.PSFRPCRequestData();
        Map<String,String> jo= Maps.newHashMap();
        jo.put("order_id",orderId);
        jo.put("username","system");
        jo.put("action_name",actionName);
        jo.put("operator","operator");
        jo.put("dateline", Instant.now().getEpochSecond()+"");
        request.service_uri="orderTrack/appendTrack?"+ RequestUtil.toQueryString(jo);
        request.data= "";

        return call("order",request);
    }
}
