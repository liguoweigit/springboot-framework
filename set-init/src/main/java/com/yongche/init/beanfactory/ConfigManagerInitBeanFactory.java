package com.yongche.init.beanfactory;

import com.google.common.base.Strings;
import com.yongche.config.ConfigConsts;
import com.yongche.util.Constant;
import com.yongche.util.EnvUtil;
import com.yongche.util.SpringUtil;
import com.yongche.util.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * test
 * by yongche.com
 *
 * @author mma
 * @since 2017-11-28 上午10:17
 */

public class ConfigManagerInitBeanFactory implements BeanFactoryPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ConfigManagerInitBeanFactory.class);


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        initConfig();
    }

    public void initConfig() {
        Environment environment = (Environment) SpringUtil.getBean(Environment.class);
        String env = EnvUtil.getEnv(environment, Constant.TEST);
        String configFilePath = null;
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
        logger.info("BeanFactoryPostProcessor init config. filePath:{} | env:{}",configFilePath,env);
        Startup.getInstance().initConfigManager(configFilePath,env);

    }
}
