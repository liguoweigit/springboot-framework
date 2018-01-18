package com.yongche;

import com.yongche.anno.MangoScan;
import com.yongche.factory.MangoFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true,exposeProxy = true)
@MangoScan(packages = {"com.yongche.dao"},factoryClass = MangoFactoryBean.class)
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);

    }

}
