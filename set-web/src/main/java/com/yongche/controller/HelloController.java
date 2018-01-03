package com.yongche.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yongche.cache.CacheManager;
import com.yongche.dao.CarTypeDao;
import com.yongche.enumdata.CacheKeyEnum;
import com.yongche.factory.MangoFactoryBean;
import com.yongche.service.PsfDispatchService;
import com.yongche.service.TestService;
import com.yongche.util.SpringUtil;
import jmind.core.redis.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private Environment environment;

    @Autowired
    private CarTypeDao carTypeDao;

    @Autowired
    private PsfDispatchService psfDispatchService;


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
}
