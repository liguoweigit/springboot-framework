package com.yongche.controller;

import com.google.common.base.Strings;
import com.yongche.cache.CacheManager;
import com.yongche.dao.CarTypeDao;
import com.yongche.factory.MangoFactoryBean;
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
        redis.set("aaa","bbb");
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
}
