package com.yongche.mq;

/**
 * 消费者
 * @author mma
 * @since 2017-12-04-下午3:02
 */
public interface Consumer<T,R> {

    public T consum(R r);
}
