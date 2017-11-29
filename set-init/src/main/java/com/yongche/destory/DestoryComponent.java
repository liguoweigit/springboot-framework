package com.yongche.destory;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 停止服务销毁
 * by yongche.com
 *
 * @author mma
 * @since 2017-11-27 下午1:34
 */
@Component
public class DestoryComponent {

    @PreDestroy
    public void destory() {

    }
}