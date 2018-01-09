package com.yongche.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yongche.psf.PSFClient;
import com.yongche.util.HttpConstant;
import com.yongche.util.PSFManager;
import com.yongche.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * psf业务
 * by yongche.com
 *
 * @author mma
 * @since 2017-12-06 下午3:47
 */
@Service
public class PsfDispatchService {

    private final static String SERVICE_ID = "dispatch";

    private static final Logger logger = LoggerFactory.getLogger(PsfDispatchService.class);

    public JSONObject getAcceptNumByDriverId(long driverId) {
        PSFClient.PSFRPCRequestData request = new PSFClient.PSFRPCRequestData();
        Map<String, Object> param = Maps.newHashMap();
        param.put("driver_id", driverId);
        request.data = "";
        request.service_uri = "dispatch/getDriverAcceptNum?" + RequestUtil.toQueryString(param);
        String response = null;
        try {
            response = PSFManager.getManager().call(SERVICE_ID, request);
        } catch (Throwable e) {
            logger.error("get order field faild from psf order center. driverId:{},e:{}", driverId, e);
        }
        if (StringUtils.isBlank(response)) {
            logger.error("get order field result is null from psf order center. driverId:{}", driverId);
            return null;
        }
        JSONObject resultJson = JSONObject.parseObject(response);
        if (resultJson.getIntValue("ret_code") != HttpConstant.HTTP_SUCCESS_CODE) {
            logger.error("get order field faild from order center. driverId:{} result:{}", driverId, resultJson);
            return null;
        } else {
            JSONObject result = resultJson.getJSONObject("ret");
            return result;
        }
    }

    public JSONObject createOrder(Map<String,Object> map){
        PSFClient.PSFRPCRequestData request = new PSFClient.PSFRPCRequestData();
        Map<String, Object> param = Maps.newHashMap();
        request.data = "";
        request.service_uri = "State/createOrder?"+ RequestUtil.toQueryString(map);
        String response = null;
        try {
            response = PSFManager.getManager().call("order", request);
        } catch (Throwable e) {
            logger.error("get order field faild from psf order center. driverId:{},e:{}", 1, e);
        }
        if (StringUtils.isBlank(response)) {
            logger.error("get order field result is null from psf order center. driverId:{}", 1);
            return null;
        }
        JSONObject resultJson = JSONObject.parseObject(response);
        if (resultJson.getIntValue("ret_code") != HttpConstant.HTTP_SUCCESS_CODE) {
            logger.error("get order field faild from order center. driverId:{} result:{}", 1, resultJson);
            return null;
        } else {
            JSONObject result = resultJson.getJSONObject("ret");
            return result;
        }
    }

    public static void main(String[] args) {

    }

}
