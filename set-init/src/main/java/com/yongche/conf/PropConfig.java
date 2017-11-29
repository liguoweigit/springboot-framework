package com.yongche.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 占位
 * by yongche.com
 *
 * @author mma
 * @since 2017-11-27 上午11:36
 */
@Configuration
@PropertySource(value = {"classpath:/db-init.properties"},
        ignoreResourceNotFound = true,encoding = "utf-8")
public class PropConfig {

    // PropertySourcesPlaceholderConfigurer这个bean，
    // 这个bean主要用于解决@value中使用的${…}占位符。
    // 假如你不使用${…}占位符的话，可以不使用这个bean。
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
