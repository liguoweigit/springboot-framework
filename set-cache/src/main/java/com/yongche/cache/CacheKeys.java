package com.yongche.cache;


import com.yongche.util.CacheKeyUtil;

/**
 * Created by xieweibo on 2016/10/25.
 */
public abstract class CacheKeys {

    public static final String DISPATCH_TEMPLATE="c_d_template";
    public static final String DISPATCH_TEMPLATE_ID="c_d_template_id"; //根据模板ID查询的缓存key

    public static final String DISPATCH_CITY_OPTION="c_d_city_option";
    
    public static final String BLACK_LIST_PERFIX = "black_list2";

    public static final String BRAND_DRIVER_PERFIX = "brand_driver_ids";//品牌车型缓存前缀
   

    public static final String RECOMMEND_KEY_PREFIX = "'recommend'"; //推荐车型缓存前缀

   // public static final String VALID_CAR_TYPE = "ValidCarType"; //城市和产品对应有效车类型ID.

    /**
     * 带 PUB_ 前缀的key是和php派单系统公用的缓存
     */
    public static final String PUB_ORDER_INFO_PREFIX = "order_info";//订单信息缓存前缀

    public static final String PUB_SELECT_DRIVER_PREFIX = "select_driver:"; //选出来的司机列表缓存前缀
    
    public static final String PUB_CHANGE_DRIVER_PERFIX = "limit_change:";//改派司机前缀

    public static final String PUB_CANCEL_ORDER_PERFIX = "limit_cancel:"; //诱导关闭司机前缀

    public static final String RESPONSE_DRIVER_NUM_KEY = "response_driver_num";// 接单司机数

    private static final String CAR_FILTER_LOGGER_KEY = "car_filter_logger:{0}:{1}";

    public static final String ASSIGNED_DRIVER = "assigned_driver:";

    public static String getCarFilterLoggerKey(long orderId,int round) {
        return CacheKeyUtil.dynamicFormat(CAR_FILTER_LOGGER_KEY, orderId,round);
    }
    
}
