package com.yongche.anno;

import com.yongche.init.DbBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * DB
 * by yongche.com
 *
 * @author mma
 * @since 2017-11-26 下午10:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DbBeanDefinitionRegistrar.class)
public @interface MangoScan {

    String[] packages() default {};

    Class<?> factoryClass();
}
