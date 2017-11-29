package com.yongche.util;

import org.springframework.core.env.Environment;

import java.util.Objects;
import java.util.Optional;

/**
 * 环境
 * by yongche.com
 *
 * @author mma
 * @since 2017-11-28 上午10:47
 */

public class EnvUtil {

    /**
     * 获得系统环境
     * @param environment
     * @param defValue
     * @return
     */
    public static String getEnv(Environment environment,String defValue){
        if(null == environment){
            return defValue;
        }
        String env = environment.getProperty(Constant.OUTER_ENV);  //先获得系统变量
        Optional<String> optional = Optional.ofNullable(env);
        return optional.orElseGet(() -> {
            return environment.getProperty(Constant.INNER_ENV,defValue);
        });
    }
}
