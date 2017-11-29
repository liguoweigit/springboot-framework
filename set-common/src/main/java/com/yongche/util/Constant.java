package com.yongche.util;

/**
 * 常量
 * by yongche.com
 *
 * @author mma
 * @since 2017-11-28 上午11:07
 */

public class Constant {
    /**
     * (1) 命令行参数
     * (2) java:comp/env 里的JNDI属性
     * (3) JVM系统属性
     * (4) 操作系统环境变量
     * (5) 随机生成的带 random.* 前缀的属性 （在设置其他属性时， 可以引用它们， 比如 ${random.long} ）
     * (6) 应用程序以外的application.properties或者appliaction.yml文件
     * (7) 打包在应用程序内的application.properties或者appliaction.yml文件
     * (8) 通过 @PropertySource 标注的属性源
     * (9) 默认属性这个列表按照优先级排序，也就是说，任何在高优先级属性源里设置的属性都会覆盖低优先级的相同属性。例如，命令行参数会覆盖其他属性源里的属性。
     */
    /*外部环境配置*/
    public static final String OUTER_ENV = "APP_ENV";
    /*application.yml中的配置*/
    public static final String INNER_ENV = "spring.profiles.active";

    public static final String DEV = "dev";
    public static final String TEST = "test";
    public static final String QA = "qa";
    public static final String PRODUCTION = "prod";
}
