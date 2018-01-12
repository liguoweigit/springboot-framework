package com.yongche.service;

import com.yongche.dao.DispatchDao;
import com.yongche.pojo.Car;
import com.yongche.pojo.Dispatch;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 测试
 * by yongche.com
 *
 * @author mma
 * @since 2017-11-27 下午1:11
 */

@Service
public class TestService {

    public void test(){
        System.out.println("11111111111111");
    }

    @Autowired
    private DispatchDao dispatchDao;

    @Autowired
    private OrderCacheService orderCacheService;


    public List<Car> getSelectedCars(long orderId,boolean old,Class c){
        //通过orderId，在派单表拿到相关信息
        Dispatch dispatch = dispatchDao.selectOneById(orderId);
        if(null == dispatch){
            return Collections.emptyList();
        }
        int round = dispatch.getRound();
        //根据派单表中的轮次，拿到redis中的选车列表
        List<Car> carList = orderCacheService.getCarListFromRedis(orderId,round,old,c);
        if(CollectionUtils.isEmpty(carList)){
            return Collections.emptyList();
        }else{
            return carList;
        }
    }
}
