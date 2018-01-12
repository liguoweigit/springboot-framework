package com.yongche;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yongche.service.PsfDispatchService;
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

    @Test
    public void test(){
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", 8060);
        map.put("corporate_id", 0);
        map.put("passenger_phone", 123456789);
        map.put("passneger_name", "张三");
        map.put("city", "bj");
        map.put("product_type_id", 8);
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
        map.put("ip","10.1.7 .202");
        map.put("order_port", 60428);
        map.put("dispatch_type", 2);
        map.put("time_length", 1800);
        //String arg = JSON.toJSONString(map);
        //System.out.println(arg);
        Object obj = psfDispatchService.createOrder(map);
        System.out.println(JSON.toJSONString(obj));


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
        };


        JSONObject orderInfoJSON = psfDispatchService.getFromPsfOrderCenter(orderId,fileds);
        System.out.println(JSONObject.toJSONString(orderInfoJSON));

    }
}
