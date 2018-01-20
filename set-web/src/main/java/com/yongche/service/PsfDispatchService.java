package com.yongche.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yongche.pojo.Car;
import com.yongche.psf.PSFClient;
import com.yongche.util.HttpConstant;
import com.yongche.util.PSFManager;
import com.yongche.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

        request.data = "";
        request.service_uri = "State/createOrder?"+ RequestUtil.toQueryString(map);
        String response = null;
        try {
            response = PSFManager.getManager().call("oc", request);
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
            JSONObject result = resultJson.getJSONObject("result");
            return result;
        }
    }

    /**
     * 获取订单信息
     * @param orderId
     * @param fields
     * @return
     */
    public JSONObject getFromPsfOrderCenter(long orderId,String[] fields) {
        String fieldStr = null;
        if(fields != null && fields.length > 0){
            fieldStr = StringUtils.join(fields,",");
        }
        Map<String,Object> paraMap = Maps.newHashMap();
        paraMap.put("service_order_id",orderId);
        if(StringUtils.isNotBlank(fieldStr)) {
            paraMap.put("fields", fieldStr);
        }
        PSFClient.PSFRPCRequestData request = new PSFClient.PSFRPCRequestData();
        request.data = "";
        request.service_uri = "order/getFieldsByOrderId?"+RequestUtil.toQueryString(paraMap);
        String response = null;
        try {
            response = PSFManager.getManager().call("order",request);
        } catch (Throwable e) {
            logger.error("get order field faild from psf order center. order id:{},e:{}", orderId,e);
        }
        if(StringUtils.isBlank(response)){
            logger.error("get order field result is null from psf order center. order id:{}", orderId);
            return null;
        }
        JSONObject resultJson = JSONObject.parseObject(response);
        if(resultJson.getIntValue("ret_code") != HttpConstant.HTTP_SUCCESS_CODE){
            logger.error("get order field faild from order center. order id:{} result:{}",orderId,resultJson);
            return null;
        }else{
            logger.info("get order field success from order center.orderId:{} | response:{}",orderId,response);
            JSONObject result = resultJson.getJSONObject("result");
            return result;
        }
    }


    /**
     * 调取dispatch派单
     */

    public JSONObject startDispatch(Map<String,Object> map){
        PSFClient.PSFRPCRequestData request = new PSFClient.PSFRPCRequestData();

        request.data = map.toString();
        request.service_uri = "dispatch/startDispatch?service_order_id="+map.get("service_order_id");
        String response = null;
        try {
            response = PSFManager.getManager().call("dispatch", request);
        } catch (Throwable e) {
            logger.error("startDispatch faild . orderId:{},e:{}", map.get("service_order_id"), e);
        }
        if (StringUtils.isBlank(response)) {
            logger.error("startDispatch result is null . orderId:{}",map.get("service_order_id"));
            return null;
        }
        JSONObject resultJson = JSONObject.parseObject(response);
        if (resultJson.getIntValue("ret_code") != HttpConstant.HTTP_SUCCESS_CODE) {
            logger.error("startDispatch faild . orderId:{} result:{}", map.get("service_order_id"), resultJson);
            return null;
        } else {
            JSONObject result = resultJson.getJSONObject("result");
            return result;
        }
    }


    /**
     * 根据orderId取消订单
     */

    public JSONObject cancelOrder(Map<String,Object> map){
        PSFClient.PSFRPCRequestData request = new PSFClient.PSFRPCRequestData();

        request.data = "";
        request.service_uri = "State/cancel?"+ RequestUtil.toQueryString(map);
        String response = null;
        try {
            response = PSFManager.getManager().call("order", request);
        } catch (Throwable e) {
            logger.error("cancel order faild . orderId:{},e:{}", map.get("service_order_id"), e);
        }
        if (StringUtils.isBlank(response)) {
            logger.error("cancel order result is null . orderId:{}", map.get("service_order_id"));
            return null;
        }
        JSONObject resultJson = JSONObject.parseObject(response);
        if (resultJson.getIntValue("ret_code") != HttpConstant.HTTP_SUCCESS_CODE) {
            logger.error("cancel order faild . orderId:{} result:{}", map.get("service_order_id"), resultJson);
            return null;
        } else {
            JSONObject result = resultJson.getJSONObject("result");
            return result;
        }
    }

    /**
     * 司机接单
     */

    public JSONObject driverResponse(Map<String,Object> map){
        PSFClient.PSFRPCRequestData request = new PSFClient.PSFRPCRequestData();

        request.data = "";
        request.service_uri = "dispatch/driverResponse?"+ RequestUtil.toQueryString(map);
        String response = null;
        try {
            response = PSFManager.getManager().call("dispatch", request);
        } catch (Throwable e) {
            logger.error("driverResponse faild . orderId:{},e:{}", map.get("order_id"), e);
        }
        if (StringUtils.isBlank(response)) {
            logger.error("driverResponse result is null . orderId:{}", map.get("order_id"));
            return null;
        }
        JSONObject resultJson = JSONObject.parseObject(response);
        if (resultJson.getIntValue("ret_code") != HttpConstant.HTTP_SUCCESS_CODE) {
            logger.error("driverResponse faild . orderId:{} result:{}", map.get("service_order_id"), resultJson);
            return null;
        } else {
            JSONObject result = resultJson.getJSONObject("result");
            return result;
        }
    }


    //获取派单明细

    public JSONObject getDispatchDetail(Map<String,Object> map){
        PSFClient.PSFRPCRequestData request = new PSFClient.PSFRPCRequestData();

        request.data = "";
        request.service_uri = "dispatch/getDispatchDetail?"+ RequestUtil.toQueryString(map);
        String response = null;
        try {
            response = PSFManager.getManager().call("dispatch", request);
        } catch (Throwable e) {
            logger.error("getDispatchDetail faild . orderId:{},e:{}", map.get("order_id"), e);
        }
        if (StringUtils.isBlank(response)) {
            logger.error("getDispatchDetail result is null . orderId:{}", map.get("order_id"));
            return null;
        }

        JSONObject resultJson = JSONObject.parseObject(response);
        if (resultJson.getIntValue("ret_code") != HttpConstant.HTTP_SUCCESS_CODE) {
            logger.error("getDispatchDetail faild . orderId:{} result:{}", map.get("service_order_id"), resultJson);
            return null;
        } else {

           return resultJson;

        }

    }


}
