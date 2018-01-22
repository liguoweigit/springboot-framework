package com.yongche.webutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yongche.config.ConfigConsts;
import com.yongche.pojo.Car;
import com.yongche.util.HttpClient4Util;
import com.yongche.util.HttpConstant;
import jmind.core.util.DataUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/**
 * LBS请求类
 * by yongche.com
 *
 * @author mma
 * @since 2018-01-22 下午2:42
 */

public class LBSUtil {

    private  static final Logger logger = LoggerFactory.getLogger(LBSUtil.class);

    public static List<Car> getCarsByDriverIds(String ids){
        Objects.requireNonNull(ids,"ids must not null");
        String lbsDomain = ConfigConsts.getDictConfig("lbs_domain");
        String url = lbsDomain+"/api/v1/drivers/search_by_ids?from_service=auto_test&ids="+ids;
        String s="";
        try{
            s = HttpClient4Util.getIntance().postBody(url,null);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("LBS invoke failure! errorMessage= {}",e);
        }

        if(StringUtils.isNotBlank(s)){
            JSONObject jsonObject = JSON.parseObject(s);
            if(jsonObject.getIntValue("ret_code")== HttpConstant.HTTP_SUCCESS_CODE){
                logger.info("LBS response data is:{}",s);
                Object results = jsonObject.get("results");
                if(results!=null && results instanceof JSONObject){
                    JSONObject resObj = (JSONObject)results;
                    int driverNum = resObj.getIntValue("driver_num");
                    if(driverNum > 0){
                        if(resObj.containsKey("driver_info")){
                            List<Car> cars= JSON.parseArray(resObj.getString("driver_info"),Car.class);
                            if(!DataUtil.isEmpty(cars)){
                                int i=0;
                                for(Car car:cars){ // 设置一下index，未以后排序做准备。这样过滤可以用并发，不考虑排序了。
                                    car.setIndex(i++);
                                }
                                return cars;
                            }
                        }
                    }
                }
            }else{
                logger.info("result is:{} | url:{}",s,url);
            }
        }
        return Collections.emptyList();
    }

    public static boolean changeDriverStatus(Car car){
        Objects.requireNonNull(car,"car must not null");
        String lbsDomain = ConfigConsts.getDictConfig("lbs_domain");
        String[] domainArr = lbsDomain.split(":");
        String port = domainArr[2].replaceAll("8888","8887");
        lbsDomain = domainArr[0] +":"+ domainArr[1] +":"+ port;
        String url = lbsDomain+"/api/v1/drivers/update";
        String s = "";
        String body = JSON.toJSONString(car);
        try{
            s = HttpClient4Util.getIntance().postBody(url,body);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("LBS invoke changeDriverStatus failure! errorMessage= {} | body",e,body);
        }
        if(StringUtils.isNotBlank(s)){
            JSONObject jsonObject = JSON.parseObject(s);
            if(jsonObject.getIntValue("ret_code")== HttpConstant.HTTP_SUCCESS_CODE){
                logger.info("LBS changeDriverStatus response data is:{}",s);
                return true;
            }else{
                logger.info("result is:{} | url:{} | body:{}",s,url,body);
            }
        }
        return false;
    }

}
