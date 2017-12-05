package com.yongche.mq;

/**
 * 生产者
 * by yongche.com
 *
 * @author mma
 * @since 2017-12-04 下午3:02
 */

public interface Producer<T,R> {

    public T produce(R r);
}
