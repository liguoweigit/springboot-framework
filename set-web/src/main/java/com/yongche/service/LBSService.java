package com.yongche.service;

import com.yongche.pojo.Car;
import com.yongche.webutil.LBSUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * LBS服务
 * by yongche.com
 *
 * @author mma
 * @since 2018-01-22 下午2:56
 */

@Service
public class LBSService {

    public static final Logger logger = LoggerFactory.getLogger(LBSService.class);

    public boolean changeWorkStatus(long driverId,int workStatus){
        List<Car> carList = LBSUtil.getCarsByDriverIds(String.valueOf(driverId));
        boolean success = false;
        if(CollectionUtils.isNotEmpty(carList)){
            Car car = carList.get(0);
            logger.info("current driver work_status.driverId:{} | workStatus:{}",driverId,
                    car.getWork_status());
            if(car.getWork_status() == workStatus){
                return true;
            }
            car.setWork_status(workStatus);
            success = LBSUtil.changeDriverStatus(car);
            logger.info("changeWorkStatus driverId:{} | workStatus:{} | result:{}",
                    driverId,workStatus,success);
            return success;
        }
        return success;
    }
}
