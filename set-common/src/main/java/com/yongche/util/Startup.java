package com.yongche.util;

import com.google.common.base.Strings;
import com.le.config.client.ConfigManager;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * java读取application.yml
 * by yongche.com
 *
 * @author mma
 * @since 2017-11-27 下午4:24
 */

public class Startup {

    //private final static Logger logger = LoggerFactory.getLogger(Startup.class);

    private static AtomicBoolean flag = new AtomicBoolean(false);

    private static ConcurrentHashMap<String,String> argMap = null;

    private Startup(){
        argMap = new ConcurrentHashMap();
    }

    public static class SingleHolder{
        private final static Startup instance = new Startup();
    }

    public static Startup getInstance(){
        return SingleHolder.instance;
    }

    public void initConfigManager(String filePath,String env){
        if(!flag.get()) {
            Objects.requireNonNull(filePath, "config path must be not null");
            Objects.requireNonNull(env, "env must be not null");
            ConfigManager.init(filePath);
            if(StringUtils.isBlank(argMap.get("env"))){
                argMap.put("env",env);
            }
            flag.compareAndSet(false,true);
        }
    }

    public String getCurrentEvn(String defaultValue){
        return argMap.get("env") == null ? defaultValue:argMap.get("env");
    }
}
