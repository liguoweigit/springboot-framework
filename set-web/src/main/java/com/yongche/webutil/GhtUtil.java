package com.yongche.webutil;

import com.yongche.pojo.Car;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GhtUtil {

    public static Map<String,Object> wrappedDriverResponse(Car car,long orderId,int round,int batch,long accountId){
        Map<String, Object> map = new HashMap<>();
        map.put("car_id", car.getCar_id());
        map.put("order_id", orderId);
        map.put("account_id", accountId);
        map.put("latitude", car.getLatitude());
        map.put("longitude", car.getLongitude());
        map.put("in_coord_type", "baidu");
        map.put("distance", car.getDistance());
        map.put("drive_time", 600);
        map.put("driver_add_price", 0);
        map.put("batch", batch);
        map.put("round", round);
        map.put("is_auto", 0);
        map.put("driver_id", car.getDriver_id());
        map.put("bargain_amount", 0);
        map.put("response_driver_ip", "10.1.16.60");
        System.out.println("driverResponse req param--->"+map.toString());
        return map;
    }

    public static Map<String,Object> wrappedUserDecision(UserDecisionVo userDecisionVo){
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", userDecisionVo.getOrder_id());
        map.put("service_order_id", userDecisionVo.getOrder_id());
        map.put("driver_id", userDecisionVo.getDriver_id());
        map.put("corporate_id", userDecisionVo.getCorporate_id());
        map.put("coupon_member_id", userDecisionVo.getCoupon_member_id());
        map.put("sms", "passenger");
        map.put("passenger_name", userDecisionVo.getPassenger_name());
        map.put("passenger_phone", userDecisionVo.getPassenger_phone());
        System.out.println("userDecision req param--->"+map.toString());
        return map;
    }
}