package com.yongche.enumdata;


import com.yongche.config.CacheConstants;

import java.text.MessageFormat;

/**
 * @author mma
 * @since 2017-04-10-上午10:29
 */
public enum CacheKeyEnum {
    ORDER_INFO("order_info:{0}:{1}", CacheConstants.TIME_1_HOUR),
    LAST_ACCESS_TIME("last_access_time:{0}", CacheConstants.TIME_3_MINS),   //订单超时设置
    ESTIMATE_SNAP("estimate_snap:{0}",CacheConstants.TIME_30_MINS),
    SELECT_DRIVER("select_driver:{0}_{1}",CacheConstants.TIME_1_HOUR),
    FULL_DISPATCH_TIMES("full_dispatch_times:{0}",CacheConstants.TIME_3_MINS),
    DISPATCH_NUM_KEY("dispatch_num:{0}",86400),
    BIDDING_INFO_KEY("bidding_info:{0}",CacheConstants.TIME_3_MINS),

    ACCEPT_DRIVER_LIST("accept_driver_list:{0}",CacheConstants.TIME_1_HOUR),
    STRATEGY("add_price_strategy:{0}:{1}:{2}:{3}:{4}",CacheConstants.TIME_10_MINS),
    REDIS_DISTANCE_RATE_DB("estimate_distance_rate:{0}:{1}",CacheConstants.TIME_30_MINS),
    REDIS_USER_RATE_DB("user_level_rate:{1}",CacheConstants.TIME_30_MINS),
    //DETAIL_KEY("dispatch_detail:{0}:{1}",3050),
    PRODUCT_TYPE("producttype_{0}",CacheConstants.TIME_10_MINS),  //没有设置expire

    DISPATCH_DETAIL("dispatch_detail:{0}:{1}",3050),
    REDISPATCH_NO_DRIVER("redispatch_no_driver:{0}",CacheConstants.TIME_10_MINS),
    CAR_TYPE("cartype_:{0}",CacheConstants.TIME_10_MINS),
    ASSIGN_LOCK_KEY("assign_lock_{0}",CacheConstants.TIME_20_SEC),
    REDISPATCH_INFO("redispatch_info:{0}",CacheConstants.TIME_1_DAY),
    ASSIGN_RATE_LOCK_KEY("daemon_lock:setAssignAcceptRate:{0}-{1}",CacheConstants.TIME_5_SEC),
    ASSIGN_RATE("assign_rate:{0}",CacheConstants.TIME_2_DAY),
    DRIVER_DISPATCH_TIME("driver_dispatch_time_{0}",CacheConstants.TIME_2_DAY),
    ASSIGN_RATE_ORDER_CANCEL("assign_rate:order_cancel:{0}",CacheConstants.TIME_30_MINS),
    ASSIGN_RATE_ORDER_ACCEPT("assign_rate:order_accept:{0}",CacheConstants.TIME_30_MINS),
    DRIVER_INFO("drivers_info:{0}",CacheConstants.TIME_10_YEAR),

    TEMPLATE_ID("id_tempalte_{0}",CacheConstants.TIME_10_YEAR),
    ID_SEQUENCE("id_sequence:{0}",CacheConstants.TIME_10_YEAR),

    ACCEPT_ORDER_NUM("response_driver_num:{0}",CacheConstants.TIME_1_DAY),


    CHARGE_RPC_ORDER_INFO("ChargeRpc_order_info:{0}",CacheConstants.TIME_30_MINS);


    private String key;
    private int expire;

    CacheKeyEnum(String key, int expire) {
        this.key = key;
        this.expire = expire;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String toKey(Object... dynamicObject){
        if (dynamicObject == null || dynamicObject.length == 0) {
            return key;
        }
        for (int i = 0; i < dynamicObject.length; i++) {
            dynamicObject[i] = String.valueOf(dynamicObject[i]);
        }
        return MessageFormat.format(key, dynamicObject);
    }

}
