package com.yongche;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yongche.controller.HelloController;
import com.yongche.pojo.Car;
import com.yongche.service.LBSService;
import com.yongche.service.PsfDispatchService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.*;

/**
 * test
 * by yongche.com
 *
 * @author mma
 * @since 2017-05-10 上午10:14
 */

public class JunitTestControllerController extends JunitBaseTestController {

    static final Logger logger = LoggerFactory.getLogger(JunitTestControllerController.class);


    @Autowired
    private PsfDispatchService psfDispatchService;

    @Autowired
    private LBSService lbsService;

    @Test
    public void test() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", 3046655);
        map.put("corporate_id", 0);
        map.put("passenger_phone", 123456789);
        map.put("passneger_name", "张三");
        map.put("city", "bj");
        map.put("product_type_id", 17);
        map.put("fixed_product_id", 467);
        map.put("car_type_id", 37);
        map.put("car_type_ids", "37,2");
        map.put("source", 11000001);
        map.put("expect_start_time", Instant.now().getEpochSecond());
        map.put("in_coord_type", "baidu");
        map.put("expect_start_latitude", 39.989910038478);
        map.put("expect_start_longitude", 116.31401996887);
        map.put("expect_end_latitude", 40.007924675665);
        map.put("expect_end_longitude", 116.38557987398);
        map.put("start_position", "");
        map.put("start_address", "中国技术交易大厦");
        map.put("end_position", "南沟泥河");
        map.put("end_address", "南沟泥河");
        map.put("flight_number", 0);
        map.put("app_version", "iWeidao/6.2.5 D/877035");
        map.put("sms", "passenger");
        map.put("time_span", 0);
        map.put("has_custom_decision", 1);
        map.put("is_need_manual_dispatch", 0);
        map.put("is_auto_dispatch", 0);
        map.put("estimate_price", 40);
        map.put("device_id", 0);
        map.put("corporate_dept_id", 0);
        map.put("estimate_info", "D123, T3700");
        map.put("flag", 2);
        map.put("create_order_longitude", 116.314045);
        map.put("create_order_latitude", 39.990013);
        map.put("ip", "10.1.7 .202");
        map.put("order_port", 60428);
        map.put("dispatch_type", 2);
        map.put("time_length", 1800);
        map.put("app_msg", "");
        //String arg = JSON.toJSONString(map);
        //System.out.println(arg);
        Object obj = psfDispatchService.createOrder(map);
        System.out.println(JSON.toJSONString(obj));

//       Ordercenter获取orderInfo
        long orderId = JSONObject.parseObject(JSON.toJSONString(obj)).getLong("service_order_id");
        String[] fileds = new String[]{
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


        JSONObject orderInfoJSON = psfDispatchService.getFromPsfOrderCenter(orderId, fileds);
        System.out.println(JSONObject.toJSONString(orderInfoJSON));


        JSONObject startDispatchStat = psfDispatchService.startDispatch(orderInfoJSON);
        System.out.println("startDispatch--->" + startDispatchStat.toJSONString());

    }

    @Test
    public void test2() {
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", 6511856917944463356L);
        map.put("return_min", 1);
        map.put("reason_id", 1);
        map.put("extension", "{\"other_reason\":\"\"}");
        map.put("user_confirmed", "0");


        Object obj = psfDispatchService.cancelOrder(map);
        System.out.println(JSON.toJSONString(obj));

    }

    @Test
    public void test3() {
        Map<String, Object> map = new HashMap<>();
        map.put("car_id", 50072207);
        map.put("order_id", 6512254928268816792L);
        map.put("account_id", 100001140);
        map.put("latitude", 39.910607);
        map.put("longitude", 116.440167);
        map.put("in_coord_type", "baidu");
        map.put("distance", 3.5);
        map.put("drive_time", 600);
        map.put("driver_add_price", 0);
        map.put("batch", 1);
        map.put("round", 1);
        map.put("is_auto", 0);
        map.put("driver_id", 1140);
        map.put("bargain_amount", 0);
        map.put("response_driver_ip", "10.1.16.22");
        Object obj = psfDispatchService.driverResponse(map);
        System.out.println(JSON.toJSONString(obj));
    }

    @Test
    public void testLBS(){
        lbsService.changeWorkStatus(1140,1);
    }
}