package com.yongche.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yongche.cache.CacheManager;
import com.yongche.dao.CarTypeDao;
import com.yongche.dao.DispatchDao;
import com.yongche.enumdata.CacheKeyEnum;
import com.yongche.factory.MangoFactoryBean;
import com.yongche.pojo.Car;
import com.yongche.service.PsfDispatchService;
import com.yongche.service.TestService;
import com.yongche.util.SpringUtil;
import jmind.core.redis.Redis;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * hello
 * by yongche.com
 *
 * @author mma
 * @since 2017-11-25 下午10:13
 */
@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private Environment environment;

    @Autowired
    private CarTypeDao carTypeDao;

    @Autowired
    private PsfDispatchService psfDispatchService;

    @Autowired
    private TestService testService;


    @RequestMapping(value = "/hello/{arg}")
    public Object hello(@PathVariable String arg){
        Objects.requireNonNull(arg,"arguments must be not null");
        Map<String,Object> map = new HashMap<String,Object>();
        System.out.println(environment.getProperty("db.impl"));
        TestService testService = SpringUtil.getBean(TestService.class);
        testService.test();
        //carTypeDao.getAllCarTypeIds(2);
        map.put("hello",arg);
        Redis redis = CacheManager.getInstance().getRedis();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String cacheVal = redis.get("aaa");
        if(!Strings.isNullOrEmpty(cacheVal)){
            redis.del("aaa");
        }
        map.put("cache",cacheVal);
        return map;
    }

    @RequestMapping
    public String heartBeat(HttpServletRequest request, HttpServletResponse response){
        response.addHeader("Coyote","Coyote");
        return "ok";
    }

    /**
     * 调用PSF接口示例
     * @param driverId
     * @return
     */
    @RequestMapping(value = "accept/{driverId}")
    public Object getAccpetOrderNum(@PathVariable long driverId){
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject jsonObject = psfDispatchService.getAcceptNumByDriverId(driverId);
        if(null != jsonObject) {
            map.put(String.valueOf(driverId),jsonObject);
        }
        return map;
    }

    @RequestMapping(value = "schedule/get/{driverId}")
    public Object getScheduleByDriverId(@PathVariable long driverId){
        Map<String,Object> map = new HashMap<String,Object>();
        Redis redis = CacheManager.getInstance().getRedis();
        List<String> scheduleList = redis.zrange(String.valueOf(driverId),0,-1);
        map.put(String.valueOf(driverId),scheduleList);
        return map;
    }

    @RequestMapping(value = "schedule/del/{driverId}")
    public Object delScheduleByDriverId(@PathVariable long driverId){
        Map<String,Object> map = new HashMap<String,Object>();
        Redis redis = CacheManager.getInstance().getRedis();
        String driverIdStr = String.valueOf(driverId);
        boolean exists = redis.exists(driverIdStr);
        if(exists){
            long l = redis.del(driverIdStr);
            map.put(driverIdStr,l);
        }else{
            map.put(driverIdStr,"this driver no schedules");
        }
        return map;
    }

    @RequestMapping(value = "bidding/{orderId}")
    public Object biddingByOrder(@PathVariable long orderId){
        Map<String,Object> map = new HashMap<String,Object>();
        Redis redis = CacheManager.getInstance().getRedis();
        String biddingKey = CacheKeyEnum.BIDDING_INFO_KEY.toKey(orderId);
        String biddingInfoValue = CacheManager.getInstance().getRedis().get(biddingKey);
        map.put(String.valueOf(orderId),biddingInfoValue);
        return map;
    }

    @RequestMapping(value = "createOrder")
    public Object createOrder(){
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", 3043745);
        map.put("corporate_id", 0);
        map.put("passenger_phone", 123456789);
        map.put("passneger_name", "张三");
        map.put("city", "bj");
        map.put("product_type_id", 1);
        map.put("fixed_product_id", 35);
        map.put("car_type_id", 37);
        map.put("car_type_ids", "37,2");
        map.put("source", 11000001);
        map.put("expect_start_time", Instant.now().getEpochSecond());
        map.put("in_coord_type", "baidu");
        map.put("expect_start_latitude", 39.989910038478);
        map.put("expect_start_longitude", 116.31401996887);
        map.put("expect_end_latitude", 40.007924675665);
        map.put("expect_end_longitude", 116.38557987398);
        map.put("start_position", "中国技术交易大厦");
        map.put("start_address", "中国技术交易大厦");
        map.put("end_position", "南沟泥河");
        map.put("end_address", "南沟泥河");
        map.put("flight_number", 0);
        map.put("app_version", "iWeidao/6.2.5 D/877035");
        map.put("sms", "passenger");
        map.put("time_span", 0);
        map.put("has_custom_decision", 1);
        map.put("is_need_manual_dispatch", 0);
        map.put("is_auto_dispatch", 1);
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
        JSONObject jsonObject = psfDispatchService.createOrder(map);
        if(null != jsonObject){
            String orderId = jsonObject.getString("service_order_id");
            if(StringUtils.isNotBlank(orderId)){
                logger.info("create order succ. orderId:{}",orderId);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Car> carList = testService.getSelectedCars(Long.parseLong(orderId),true,Car.class);
                resultMap.put("orderId",orderId);
                resultMap.put("carList",carList);
            }
        }else{
            resultMap.put("error","创建订单失败");
        }
       // resultMap.put("result",obj);
        return resultMap;

    }


    @RequestMapping(value = "selected_cars/{orderId}")
    public Object getSelectedCars(@PathVariable long orderId){
        List<Car> carList = testService.getSelectedCars(orderId,true,Car.class);
        return carList;
    }


    @RequestMapping(value = "Dispatch/driverResponse/{orderId}")
    public int getResponsedrivernum(@PathVariable long orderId){

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Integer count = testService.getResponsedrivernum(orderId);
        return count;

    }


}
