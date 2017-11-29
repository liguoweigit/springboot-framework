/*
 *  Copyright 2013 SOHU-INC
 */

package com.yongche.config;

/**
 * <p>
 * 缓存时效
 * </p>
 *  @author mma
 *  @since 2017-04-10
 *
 */
public interface CacheConstants {
    
    /** 缓存过期时间 - 单位为秒*/
    int TIME_20_SEC = 20;
    int TIME_5_SEC = 5;
    int TIME_1_MIN = 60;
    int TIME_2_MINS = 2 * TIME_1_MIN;
    int TIME_3_MINS = 3 * TIME_1_MIN;
    int TIME_5_MINS = 5 * TIME_1_MIN;
    int TIME_10_MINS = 10 * TIME_1_MIN;
    int TIME_20_MINS = 20 * TIME_1_MIN;
    int TIME_15_MINS = 15 * TIME_1_MIN;
    int TIME_30_MINS = 30 * TIME_1_MIN;
    int TIME_1_HOUR = 60 * TIME_1_MIN;
    int TIME_2_HOUR = 2 * TIME_1_HOUR;
    int TIME_4_HOUR = 4 * TIME_1_HOUR;
    int TIME_1_DAY = 24 * TIME_1_HOUR;
    int TIME_2_DAY = 2 * TIME_1_DAY;
    int TIME_5_DAY = 5 * TIME_1_DAY;
    int TIME_7_DAY = 7 * TIME_1_DAY;
    int TIME_1_MONTH = 30 * TIME_1_DAY;
    int TIME_2_MONTH = 60 * TIME_1_DAY;
    int TIME_3_MONTH = 90 * TIME_1_DAY;
    int TIME_10_YEAR = 365 * TIME_1_DAY;
    
}
