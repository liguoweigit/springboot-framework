package com.yongche.dao;


import com.yongche.mango.DispatchShardingStrategy;
import com.yongche.pojo.Dispatch;
import com.yongche.pojo.DispatcherInfo;
import org.jfaster.mango.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 派单信息表 根据serviceOrderId分库
 * Created by xieweibo on 2016/10/24.
 */
@DB(table = "`dispatch`")
@Sharding(shardingStrategy = DispatchShardingStrategy.class)
public interface DispatchDao {
    String COLUMNS = "service_order_id,dispatch_count,response_count,accept_count,flag,dispatch_time,decision_time,contribution,expect_decision_time,dispatch_template_id,template_snapshot,status,dispatch_type,decision_type,round,batch,create_time,update_time,estimate_time,can_dispatch_count,user_id,user_level,user_name,user_gender,add_price_redispatch,add_price_info,decision_driver_id,decision_car_type_id,bidding_id,bidding_rate";

    @SQL("insert into #table(" + COLUMNS + ") values(:serviceOrderId,dispatchCount,:responseCount,:acceptCount,:flag,:dispatchTime,:decisionTime,:contribution,:expectDecisionTime,:dispatchTemplateId,:templateSnapshot,:status,:dispatchType,:decisionType,:round,:batch,:createTime,:updateTime,:estimateTime,:canDispatchCount,:userId,:userLevel,:userName,:userGender,:addPriceRedispatch,:addPriceInfo,:decisionDriverId,:decisionCarTypeId,:biddingId,:biddingRate)")
    int insert(@ShardingBy("serviceOrderId") Dispatch dispath);

    /**
     * 根据主键查询一条记录
     * @return
     */
    @SQL("select " + COLUMNS + " from #table where service_order_id =:1")
    @UseMaster
    Dispatch selectOneById(@ShardingBy long serviceOrderId);



    /**
     * 根据主键查询一条记录
     * @return
     */
    @SQL("select " + COLUMNS + " from #table where service_order_id =:1")
    @UseMaster
    DispatcherInfo selectDispatcherInfoById(@ShardingBy long serviceOrderId);

    /**
     * 根据参数更新dispatch信息
     * @param dispatch
     * @return
     */
    @SQL("update #table set dispatch_count=:dispatchCount ,response_count=:responseCount ,"
            + "accept_count=:acceptCount ,flag=:flag ,dispatch_time=:dispatchTime ,decision_time=:decisionTime ,"
            + "contribution=:contribution ,expect_decision_time=:expectDecisionTime ,dispatch_template_id=:dispatchTemplateId ,"
            + "template_snapshot=:templateSnapshot ,status=:status ,dispatch_type=:dispatchType ,decision_type=:decisionType ,"
            + "round=:round ,batch=:batch ,create_time=:createTime ,update_time=:updateTime ,estimate_time=:estimateTime ,"
            + "can_dispatch_count=:canDispatchCount ,user_id=:userId ,user_level=:userLevel ,user_name=:userName ,user_gender=:userGender ,add_price_redispatch=:addPriceRedispatch ,"
            + "add_price_info=:addPriceInfo ,decision_driver_id=:decisionDriverId ,decision_car_type_id=:decisionCarTypeId ,bidding_id=:biddingId ,bidding_rate=:biddingRate"
        + " where service_order_id=:serviceOrderId")
    int updateById(@ShardingBy("serviceOrderId") Dispatch dispatch);


    @SQL("update #table set dispatch_count=:2 ,batch=:3 ,dispatch_time=:4,estimate_time=:5 #if(:6 != null) ,user_level=:6 #end #if(:7 != null) ,user_name=:7 #end #if(:8 != null) ,user_gender=:8 #end where service_order_id=:1")
    int updateDispatchWhenDispatch(@ShardingBy long orderId, int dispatchCount, int batch,
                                   long dispatchTime, int estimateTime, Integer levelId,
                                   String userName, String userGender);


    @SQL("update #table set add_price_info=:2,status=:3 " +
            "where service_order_id=:1")
    int updateWhenReDispatch(@ShardingBy long orderId, String addPriceInfo, int status);


    @SQL("update #table set flag=flag|:2 where service_order_id = :1 ")
    int updateWhenOrderCenterReturn(@ShardingBy long orderId, int bit);

    @SQL("update #table set status=:5,decision_time=:2,decision_type=:4,update_time=:3 #if(:6 != null) ,add_price_info=:6 #end where service_order_id = :1 and status in (:7)")
    int updateWhenDecisionFailure(@ShardingBy long orderId, long decisionTime,
                                  long updateTime, int decisionType,
                                  int status, String addPriceInfo, List<Integer> whereList);

    @SQL("update #table set status=:2,decision_driver_id=:3,decision_car_type_id=:4,decision_time=:5,decision_type=:6,update_time=:7,add_price_info=:8 #if(:9 != null) ,bidding_rate=:9 #end where service_order_id = :1 and status in (:10)")
    int updateWhenDecisionFinish(@ShardingBy long orderId, int status, long decisionDriverId,
                                 int decisionCarTypeId, int decisionTime,
                                 int decisionType, int updateTime,
                                 String addPriceInfo, BigDecimal biddingRate, List<Integer> whereList);

    @SQL("update #table set status=:2,update_time=:3 where service_order_id=:1")
    int updateDispatchStatus(@ShardingBy long orderId, int status, long updateTime);

    @SQL("delete from #table where service_order_id=:1")
    int deleteByOrderId(@ShardingBy long orderId);

    @SQL("update #table set response_count=response_count + 1 #if(:2 == 1) ,accept_count=accept_count + 1 #end where service_order_id=:1")
    int increasementResponseAndAccept(@ShardingBy long orderId, int accept);
}
