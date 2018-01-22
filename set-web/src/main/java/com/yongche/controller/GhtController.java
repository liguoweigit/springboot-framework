package com.yongche.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yongche.cache.CacheManager;
import com.yongche.dao.CarTypeDao;
import com.yongche.enumdata.CacheKeyEnum;
import com.yongche.pojo.Car;
import com.yongche.service.GhtPsfService;
import com.yongche.service.TestService;
import com.yongche.util.SpringUtil;
import com.yongche.webutil.GhtUtil;
import com.yongche.webutil.UserDecisionVo;
import jmind.core.redis.Redis;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.*;

/**
 * hello
 * by yongche.com
 *
 * @author mma
 * @since 2017-11-25 下午10:13
 */
@RestController
public class GhtController {

    private static final Logger logger = LoggerFactory.getLogger(GhtController.class);

    private static final String[] fileds = new String[]{
            "service_order_id",
            "estimate_snap",
            "estimate_price",
            "create_time",
            "flag",
            "source",
            "city",
            "product_type_id",
            "fixed_product_id",
            "expect_start_time",
            "time_length",
            "car_type_ids",
            "expect_end_latitude",
            "expect_end_longitude",
            "expect_start_latitude",
            "expect_start_longitude",
            "user_id",
            "passenger_name",
            "passenger_phone",
            "corporate_id",
            "start_position",
            "start_address",
            "end_position",
            "end_address",
            "app_version",
            "app_msg",
            "flight_number",
            "corporate_dept_id",
            "car_type",
            "dispatch_driver_ids",
            "pa_bargain_amount",
            "bidding_id",
            "add_price_amount",
            "distance",
            "extension",
    };

    @Autowired
    private Environment environment;

    @Autowired
    private GhtPsfService ghtPsfService;

    @Autowired
    private TestService testService;


    @RequestMapping(value = "ght/hello/{arg}")
    public Object hello(@PathVariable String arg) {
        Objects.requireNonNull(arg, "arguments must be not null");
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println(environment.getProperty("db.impl"));
        TestService testService = SpringUtil.getBean(TestService.class);
        testService.test();
        //carTypeDao.getAllCarTypeIds(2);
        map.put("hello", arg);
        Redis redis = CacheManager.getInstance().getRedis();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String cacheVal = redis.get("aaa");
        if (!Strings.isNullOrEmpty(cacheVal)) {
            redis.del("aaa");
        }
        map.put("cache", cacheVal);
        return map;
    }

    /*@RequestMapping
    public String heartBeat(HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("Coyote", "Coyote");
        return "ok";
    }*/

    /**
     * 调用PSF接口示例
     *
     * @param driverId
     * @return
     */
    @RequestMapping(value = "ght/accept/{driverId}")
    public Object getAccpetOrderNum(@PathVariable long driverId) {
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject jsonObject = ghtPsfService.getAcceptNumByDriverId(driverId);
        if (null != jsonObject) {
            map.put(String.valueOf(driverId), jsonObject);
        }
        return map;
    }

    @RequestMapping(value = "ght/schedule/get/{driverId}")
    public Object getScheduleByDriverId(@PathVariable long driverId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Redis redis = CacheManager.getInstance().getRedis();
        List<String> scheduleList = redis.zrange(String.valueOf(driverId), 0, -1);
        map.put(String.valueOf(driverId), scheduleList);
        return map;
    }

    @RequestMapping(value = "ght/schedule/del/{driverId}")
    public Object delScheduleByDriverId(@PathVariable long driverId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Redis redis = CacheManager.getInstance().getRedis();
        String driverIdStr = String.valueOf(driverId);
        boolean exists = redis.exists(driverIdStr);
        if (exists) {
            long l = redis.del(driverIdStr);
            map.put(driverIdStr, l);
        } else {
            map.put(driverIdStr, "this driver no schedules");
        }
        return map;
    }

    @RequestMapping(value = "ght/bidding/{orderId}")
    public Object biddingByOrder(@PathVariable long orderId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Redis redis = CacheManager.getInstance().getRedis();
        String biddingKey = CacheKeyEnum.BIDDING_INFO_KEY.toKey(orderId);
        String biddingInfoValue = CacheManager.getInstance().getRedis().get(biddingKey);
        map.put(String.valueOf(orderId), biddingInfoValue);
        return map;
    }

    @RequestMapping(value = "ght/createOrder/{autoDispatch}/{productTypeId}")
    public Object createOrder(@PathVariable int autoDispatch, @PathVariable int productTypeId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", 3046655);
        map.put("corporate_id", 0);
        map.put("passenger_phone", 123456789);
        map.put("passenger_name", "小小石头");
        map.put("city", "bj");
        map.put("product_type_id", productTypeId);
        map.put("fixed_product_id", 35);
        map.put("car_type_id", 37);
        map.put("car_type_ids", "37,2");
        map.put("source", 11000001);
        map.put("expect_start_time", Instant.now().getEpochSecond());
        map.put("in_coord_type", "baidu");
        map.put("expect_start_latitude", 39.9049593);
        map.put("expect_start_longitude", 116.4316638);
        map.put("expect_end_latitude", 40.007924675665);
        map.put("expect_end_longitude", 116.38557987398);
        map.put("start_position", "万豪中心A座");
        map.put("start_address", "万豪中心A座");
        map.put("end_position", "南沟泥河");
        map.put("end_address", "南沟泥河");
        map.put("flight_number", 0);
        map.put("app_version", "iWeidao/6.2.5 D/877035");
        map.put("sms", "passenger");
        map.put("time_span", 0);
        map.put("has_custom_decision", 1);
        map.put("is_need_manual_dispatch", 0);
        map.put("is_auto_dispatch", autoDispatch);
        map.put("estimate_price", 40);
        map.put("device_id", 0);
        map.put("corporate_dept_id", 0);
        map.put("estimate_info", "D123, T3700");
        map.put("flag", 281586647957506L);
        map.put("create_order_longitude", 116.314045);
        map.put("create_order_latitude", 39.990013);
        map.put("ip", "10.1.7 .202");
        map.put("order_port", 60428);
        map.put("dispatch_type", 2);
        map.put("time_length", 1800);
        map.put("app_msg", "");

        //1.创建订单
        JSONObject jsonObject = ghtPsfService.createOrder(map);
        long orderId = 0L;
        if (null != jsonObject) {
            String orderIdStr = jsonObject.getString("service_order_id");

            if (StringUtils.isNotBlank(orderIdStr)) {
                orderId = Long.parseLong(orderIdStr);
            } else {
                resultMap.put("error", "createOrder not found orderId");
                return resultMap;
            }
            JSONObject orderFields = ghtPsfService.getFromPsfOrderCenter(orderId, fileds);
            if (null == orderFields) {
                resultMap.put("error", "getFromPsfOrderCenter failed. orderId:" + orderId);
                return resultMap;
            }
            List<Car> carList = null;
            //如果是自动派单，则直接去查看选车接口
            if (1 == autoDispatch) {
                carList = getCarList(orderId, false);
            } else {
                JSONObject dispatchResult = ghtPsfService.startDispatch(orderFields);
                if (null != dispatchResult) {
                    carList = getCarList(orderId, false);
                }
                resultMap.put("startDispatch", dispatchResult);
            }
            resultMap.put("createOrder", String.valueOf(orderId));
            if (CollectionUtils.isNotEmpty(carList)) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Optional<Car> responseCar = carList.stream().findFirst();
                if(responseCar.isPresent()){
                    Map<String,Object> driverResponseReqMap = GhtUtil.wrappedDriverResponse(
                            responseCar.get(),orderId,1,1,orderFields.getLongValue("user_id"));
                    JSONObject responseRet = ghtPsfService.driverResponse(driverResponseReqMap);
                    resultMap.put("driverResponseResult", responseRet);
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Map<String, Object> acceptCarsReq = new HashMap<>();
                acceptCarsReq.put("order_id", orderId);
                acceptCarsReq.put("out_coord_type", "mars");
                acceptCarsReq.put("filter_driver_ids","");
                JSONObject acceptCars = ghtPsfService.getAcceptCars(acceptCarsReq);
                resultMap.put("acceptCars", acceptCars);
                if(null != acceptCars) {
                    JSONArray acceptCarsArray = acceptCars.getJSONArray("car_list");
                    if(CollectionUtils.isNotEmpty(acceptCarsArray)){
                        UserDecisionVo userDecisionVo = new UserDecisionVo();
                        userDecisionVo.setCorporate_id(orderFields.getLongValue("corporate_id"));
                        userDecisionVo.setCoupon_member_id(0);
                        userDecisionVo.setDriver_id(acceptCarsArray.getJSONObject(0).getLongValue("driver_id"));
                        userDecisionVo.setOrder_id(orderId);
                        userDecisionVo.setSms("passenger");
                        userDecisionVo.setPassenger_name(orderFields.getString("passenger_name"));
                        userDecisionVo.setPassenger_phone(orderFields.getString("passenger_phone"));
                        Map<String,Object> userDecisionReqMap = GhtUtil.wrappedUserDecision(userDecisionVo);
                        JSONObject decisionJson = ghtPsfService.userDecision(userDecisionReqMap);
                        resultMap.put("decisionResult", decisionJson);
                    }
                }
            }
            resultMap.put("carList", carList);
            resultMap.put("carListSize", carList.size());
        } else {
            resultMap.put("error", "创建订单失败");
        }



        //2.司机决策订单
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Map<String, Object> mapRe = new HashMap<>();
//        mapRe.put("car_id", 50072207);
//        mapRe.put("order_id", orderId);
//        mapRe.put("account_id", 100001140);
//        mapRe.put("latitude", 39.910607);
//        mapRe.put("longitude", 116.440167);
//        mapRe.put("in_coord_type", "baidu");
//        mapRe.put("distance", 3.5);
//        mapRe.put("drive_time", 600);
//        mapRe.put("driver_add_price", 0);
//        mapRe.put("batch", 1);
//        mapRe.put("round", 1);
//        mapRe.put("is_auto", 0);
//        mapRe.put("driver_id", 1140);
//        mapRe.put("bargain_amount", 0);
//        mapRe.put("response_driver_ip", "10.1.16.22");
//
//
//        Object obj = ghtPsfService.driverResponse(mapRe);

        return resultMap;

    }

    private List<Car> getCarList(long orderId, boolean old) {
        logger.info("create order succ. orderId:{}", orderId);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Car> carList = testService.getSelectedCars(orderId, old, Car.class);
        if (CollectionUtils.isEmpty(carList)) {
            return new ArrayList<Car>();
        }
        return carList;
    }


    @RequestMapping(value = "ght/selected_cars/{orderId}")
    public Object getSelectedCars(@PathVariable long orderId) {
        List<Car> carList = testService.getSelectedCars(orderId, false, Car.class);
        return carList;
    }


    @RequestMapping(value = "ght/dispatch/driverResponse/{orderId}")
    public int getResponsedrivernum(@PathVariable long orderId){

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Integer count = testService.getResponsedrivernum(orderId);
        return count;

    }

    /**取消订单
     *
     * @param orderId
     * @return
     */
//    @RequestMapping(value = "orderCancel/{orderId}/")
//    public Object orderCancel(@PathVariable long orderId) {
//        Map<String, Object> resultMap = new HashMap<>();
//        Map<String, Object> map = new HashMap<>();
//        map.put("order_id", orderId);
//        map.put("return_min", 1);
//        map.put("reason_id", 1);
//        map.put("extension", "{\"other_reason\":\"\"}");
//        map.put("user_confirmed", "0");
//
//        JSONObject jsonObject = ghtPsfService.cancelOrder(map);
//        System.out.println(JSON.toJSONString(jsonObject));
//    }

    /**司机接单
     *
      * @param orderId
     * @return
     */
    @RequestMapping(value = "ght/driverResponses/{orderId}")
        public Object driverResponses(@PathVariable long orderId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
            map.put("car_id", 50072207);
            map.put("order_id", orderId);
            map.put("account_id", 100001140);
            map.put("latitude", 39.910607);
            map.put("longitude", 116.440122);
            map.put("in_coord_type", "baidu");
            map.put("distance", 3.5);
            map.put("drive_time", 600);
            map.put("driver_add_price", 0);
            map.put("batch", 1);
            map.put("round", 1);
            map.put("is_auto", 0);
            map.put("driver_id", 1140);
            map.put("bargain_amount", 0);
            map.put("response_driver_ip", "10.1.16.60");

        JSONObject jsonObject = ghtPsfService.driverResponse(map);
        System.out.println(JSON.toJSONString(jsonObject));
        resultMap.put("response",JSON.toJSONString(jsonObject));
        return resultMap;
    }




    /**获取派单明细
     *
     * @param order_id，round
     * @return
     */
    // http://localhost:8080/createOrder/1/2
    // dispatch/getDispatchDetail/{order_id}/{round}

    @RequestMapping(value = "ght/dispatch/getDispatchDetail/{order_id}/{round}")
    public Object getDispatchDetail(@PathVariable long order_id,@PathVariable int round) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", order_id);
        map.put("round", round);

        JSONObject jsonObject = ghtPsfService.getDispatchDetail(map);
        System.out.println(JSON.toJSONString(jsonObject));
        resultMap.put("response",JSON.toJSONString(jsonObject));
        return resultMap;
    }

    @RequestMapping(value = "ght/acceptCars/{order_id}/{round}")
    public Object acceptCars(@PathVariable long order_id) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", order_id);
        map.put("out_coord_type", "mars");
        map.put("filter_driver_ids","");
        JSONObject jsonObject = ghtPsfService.getAcceptCars(map);
        System.out.println(jsonObject.toJSONString());
        resultMap.put("acceptCars",jsonObject);
        return resultMap;
    }





    //获取司机日程


    // http://localhost:8080/createOrder/1/2
    // http://localhost:8080/dispatch/getDriverCalendar/{driver_id}

    @RequestMapping(value = "ght/dispatch/getDriverCalendar/{driver_id}")
    public Object getDriverCalendar(@PathVariable int driver_id) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("driver_id", driver_id);


        JSONObject jsonObject = ghtPsfService.getDriverCalendar(map);
        System.out.println(JSON.toJSONString(jsonObject));
        resultMap.put("response",JSON.toJSONString(jsonObject));
        return resultMap;
    }

}

