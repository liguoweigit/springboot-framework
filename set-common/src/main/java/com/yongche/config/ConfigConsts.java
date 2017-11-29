package com.yongche.config;

import com.google.common.base.Strings;
import com.le.config.client.ConfigManager;
import com.yongche.config.bean.SourcePropertiesConfig;
import com.yongche.config.dict.DictConfig;
import com.yongche.util.SpringNumberUtil;
import com.yongche.xmlbean.db.DatabaseConfig;
import org.apache.commons.lang3.math.NumberUtils;


/**
 * Created by xieweibo on 2016/10/22.
 */
public class ConfigConsts {
    // 系统级别
    public static final String SYSTEM_SERVICE_LEVEL = "system.service_level";

    public static final String DISPATCH_MQ_NAMES="dispatch.mq.names";

    /*司机运营工具队列*/
    public static final String CHELV_APPOINT_MQ_NAMES="chelv_appoint.mq.names";

    public static final String DISPATCH_GOOD_COMMENT_RATE = "dispatch.good_comment_rate";
    public static final String DISPATCH_BLACK_LIST = "dispatch.black_list";
    // 混派开关，true 开
    public static final String DISPATCH_MIX_DISPATCH = "dispatch.mix_dispatch";
    public static final String CAR_FILTERS = "car.filters";

    /*司机诱导乘客取消订单罪行缓存时间*/
    public static final String DISPATCH_LIMIT_INDUCED_CANCEL_EXPIRE = "dispatch.limit.induced.cancel.timeout";
    public static final String DESPATCH_LIMIT_SERVICE_EXPIRE = "dispatch.limit.timeout";
    public static final String DESPATCH_LIMIT_SERVICE_MERCY_CHANGE = "dispatch.limit.changedriver";
    public static final String DESPATCH_LIMIT_SERVICE_MERCY_CHANGE_SWITCH = "dispatch.limit.changedriver.switch";
    public static final String DESPATCH_LIMIT_SERVICE_MERCY_CANCEL = "dispatch.limit.cancel";
    public static final String DESPATCH_LIMIT_SERVICE_MERCY_CANCEL_SWITCH = "dispatch.limit.cancel.switch";

    public static final String DISPATCH_DRIVER_FIXED_VERSION = "dispatch.driver_fixed_version"; //司机一口价版本
    public static final String DISPATCH_DRIVER_SURPPORT_VERSION = "dispatch.driver_surpport_version"; //司机默认版本号
    public static final String DISPATCH_COUNSUMER_COUNT="dispatch.counsumer.count";
    public static final String DISPATCH_LBSNAVTIME_CONTROL_SWITCH="dispatch.lbsnavtime_control_switch"; //按照导航时间排序开关
    public static final String CACHE_CLOSED="cache.closed";
    public static final String CAR_TYPE_YOUNG="car.type.young";      //young车型ID
    public static final String CAR_TYPE_COROLLA="car.type.corolla";  //卡罗拉车型ID
    public static final String CACHE_DATE_COMPRESS="cache.data.compress";  //缓存数据是否压缩
    public static final String DISPATCH_RISK_CONTROL_SWITCH="dispatch.risk_control_switch";  //风控开关
    /*线程池关闭的最大等待时间,单位毫秒*/
    public static final String THREADPOOL_SHUTDOWN_TIMEOUT="threadpool.shutdown.timeout";
    /*改派按照原始订单车型,开关*/
    public static final String DISPATCH_ORDER_CARTYPEID_SWITCH="dispatch.order.cartypeid.switch";
    /*HttpClient3.x客户端关闭连接*/
    public static final String HTTP_CLIENT_CLOSE_CONN="http.client.close.conn";

    /*降级服务线程池等待队列阈值*/
    public static final String HYSTRIX_THREADPOOL_QUEUE_REJECTION_THRESHOLD="hystrix.threadpool.queue.rejection.threshold";

    /*是否强制关闭熔断器,默认false不关闭*/
    public static final String HYSTRIX_CIRCUITBREAKER_FORCE_CLOSE="hystrix.circuitbreaker.force.close";

    /*调用商户平台,插入参与分,熔断器是否强制关闭,默认false不关闭*/
    public static final String DCO_HYSTRIX_CIRCUITBREAKER_FORCE_CLOSE="dco.hystrix.circuitbreaker.force.close";

    /*调用商户平台验证诱导取消开关*/
    public static final String MERCHANT_INDUCED_ORDER_SWITCH="merchant.induced.order.switch";

    /*延时多少秒调用*/
    public static final String MERCHANT_INDUCED_DELAY_INVOKE="merchant.induced.cancel.delay.invoke";

    /*调用黑名单读取数据超时时间*/
    public static final String BLACKLIST_HTTP_READ_TIMEOUT="blacklist.http.read.timeout";

    /*调用商户平台读取数据超时时间*/
    public static final String MERCHANT_HTTP_READ_TIMEOUT="merchant.http.read.timeout";

    /*预约订单，派单组添加的司机导航时间额外的一个配置时间长度*/
    public static final String DISPATCH_ROUTE_TIME_EXTRA_CONFIG_TIME="dispatch.route.time.extra.config.time";

    /*预约订单，派单组添加的司机导航时间额外的一个配置时间长度，没有导航时间的配置阀值*/
    public static final String DISPATCH_NO_ROUTE_TIME_EXTRA_CONFIG_TIME="dispatch.no.route.time.extra.config.time";

    /*预约订单，派单组添加的司机导航时间额外的一个配置时间长度，上线功能开关*/
    public static final String DISPATCH_ROUTE_TIME_EXTRA_CONFIG_TIME_SWITCH="dispatch.route.time.extra.config.time.switcher";

    //贵阳地区尾号是字母的车量不派单
    public static final String DISPATCH_GUIYANG_CAR_LAST_NO_IS_CHARACTER_SWITCH="dispatch.guiyang.car.last.no.is.character.switcher";

    /*上海地区过滤非沪牌照车辆开关*/
    public static final String DISPATCH_FILTER_NOT_HU_CONTROL_SWITCH="dispatch.filter.not.hu.control.switcher";

    /*上海地区替换非沪牌照为沪牌照开关*/
    public static final String DISPATCH_REPLACE_NOT_HU_CONTROL_SWITCH="dispatch.replace.not.hu.control.switcher";

    /*杭州地区过滤非沪牌照车辆开关*/
    public static final String DISPATCH_FILTER_NOT_ZHE_A_CONTROL_SWITCH="dispatch.filter.not.zhe.a.control.switcher";

    /*深圳地区过滤非沪牌照车辆开关*/
    public static final String DISPATCH_FILTER_NOT_YUE_B_OR_Z_CONTROL_SWITCH="dispatch.filter.not.yue.b.or.z.control.switcher";

    /*新的改派flag mark 开关*/
    public static final String DISPATCH_NEW_CHANGE_DRIVER_FLAG_SWITCH = "dispatch.new.change.driver.flag.switch";
    /*一车多牌开关*/
    public static final String DISPATCH_FILTER_MULTI_VEHICLE_NUMBER_SWITCH="dispatch.filter.multi.vehicle.number.switcher";
    /*电子围栏开关*/
    public static final String DISPATCH_FILTER_GEO_FENCE_SWITCH="dispatch.filter.geo.fence.switcher";
    /*workerId生成策略*/
    public static final String WORKER_ID_GEN_IMPL_CLASS="worker.id.gen.impl.class";

    /*派单灰度开关*/
    public static final String DISPATCH_GRAY_SWITCH = "dispatch.gray.switch";

    /*派单灰度-白名单开关*/
    public static final String DISPATCH_GRAY_WHITELIST_SWITCH = "dispatch.gray.whitelist.switch";

    /*派单灰度-百分比开关*/
    public static final String DISPATCH_GRAY_PERCENT_SWITCH = "dispatch.gray.percent.switch";

    public static final String DISPATCH_GRAY_WHITELIST = "dispatch.gray.whitelist";

    public static final String DISPATCH_GRAY_PERCENT = "dispatch.gray.percent";

    /*id生成序列是否在redis中存取*/
    public static final String ID_SEQUENCE_STORAGE_SWITCH = "id.sequence.storage.switch";

    /*司机运营工具消息队列发送开关,默认关闭状态*/
    public static final String MQ_CHELV_APPOINT_SWITCH = "mq.chelv.appoint.switch";

    public static final String DISPATCH_DETAIL_DB_SERVER_COUNT = "dispatch.detail.db.server.count";

    public static final String DISPATCH_LOG_HEADER_LIST="dispatch.log.header.list";

    //改派失败，用户补偿优惠券开关
    public static final String DISPATCH_REDISPATCH_FAILED_USER_COUPONS_SWITCH = "dispatch.redispatch.failed.user.coupons.switch";

    /**
     * 系统级别
     *
     * @return 大于3
     */
    public static boolean isHighSystemLevel() {
        return NumberUtils.toInt(ConfigConsts.getDictConfig(ConfigConsts.SYSTEM_SERVICE_LEVEL)) > 3;
    }

    /**
     * 获取配置的boolean值
     *
     * @param key
     * @return
     */
    public static final boolean isDictConfig(String key) {
        return Boolean.parseBoolean(getDictConfig(key));
    }

    public static final <T extends Number> T getDictConfig(String key, Class<T> clazz) {
        return getDictConfig(key, clazz, "0");
    }

    public static final <T extends Number> T getDictConfig(String key, Class<T> clazz, String defaultVal) {
        String val = getDictConfig(key);
        if (Strings.isNullOrEmpty(val))
            val = defaultVal;
        return SpringNumberUtil.parseNumber(val, clazz);
    }

    // 字典配置
    public static final String getDictConfig(String key) {
        return ConfigManager.get(DictConfig.class).get(key);
    }

    // 资源配置
    public static final SourcePropertiesConfig getSouceProperties() {
        return ConfigManager.get(SourcePropertiesConfig.class);
    }

    public static final DatabaseConfig getDatabaseConfig() {
        return ConfigManager.get(DatabaseConfig.class);
    }
}
