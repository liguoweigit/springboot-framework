package com.yongche.conf;

import com.yongche.init.beanfactory.ConfigManagerInitBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

/**
 * Mango初始化
 * by yongche.com
 *
 * @author mma
 * @since 2017-11-26 下午4:23
 */
@Configuration
public class InitConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(InitConfiguration.class);

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    /*@Bean
    @ConditionalOnBean(annotation = { MangoScan.class })
    public MangoDaoScannerCustom initScanner(){
        MangoDaoScannerCustom mangoDaoScannerCustom = new MangoDaoScannerCustom();
        try {
            Class<?> c = Class.forName(dbInitProperties.getImpl());
            mangoDaoScannerCustom.setFactoryBeanClass(c);
            mangoDaoScannerCustom.setPackages(dbInitProperties.getPackages())
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return mangoDaoScannerCustom;
        return new MangoDaoScannerCustom();
    }*/

    @Bean
    @DependsOn({"environment","springUtil"})
    public static ConfigManagerInitBeanFactory configManagerInitBeanFactory(){
        logger.info("init config configManagerInitBeanFactory...");
        return new ConfigManagerInitBeanFactory();
    }
}
