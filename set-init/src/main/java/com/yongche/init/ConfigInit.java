package com.yongche.init;

import com.google.common.base.Strings;
import com.yongche.util.Constant;
import com.yongche.util.EnvUtil;
import com.yongche.util.Startup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 配置中心初始化
 * by yongche.com
 *
 * @author mma
 * @since 2017-11-27 下午1:15
 */
@Component
@Order(value=1)
public class ConfigInit implements CommandLineRunner{

    @Autowired
    private Environment environment;

    @Override
    public void run(String... strings) throws Exception {
        init(null);
    }

    public void init(String configFilePath) {
        String env = EnvUtil.getEnv(environment, Constant.TEST);
        if (Strings.isNullOrEmpty(configFilePath)) {
            try {
                URL url = ResourceUtils.getURL("classpath:"+env + "-config-node.xml");
                if(ResourceUtils.isJarFileURL(url)){
                    configFilePath = ResourceUtils.extractJarFileURL(url).getPath();
                }else{
                    configFilePath = url.getPath();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        Startup.getInstance().initConfigManager(configFilePath,env);

    }
}
