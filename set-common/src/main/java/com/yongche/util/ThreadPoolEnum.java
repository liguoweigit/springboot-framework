package com.yongche.util;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 单例
 * Created by mma on 16/12/20.
 */
public enum ThreadPoolEnum {

    DEFAULT_FIXED_THREAD_POOL(Runtime.getRuntime().availableProcessors() * 2);

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolEnum.class);

    ThreadPoolEnum(int coreSize){
        service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(coreSize));
    }

    private ListeningExecutorService service;

    public ListeningExecutorService getService(){
        if(logger.isDebugEnabled()){
            logger.debug("get service,service hashcode:{}",service.hashCode());
        }
        return service;
    }

    public void shutdownPool(){
        if(logger.isDebugEnabled()){
            logger.debug("shutdownPool,class:{},service hashcode:{}",ThreadPoolEnum.class.getSimpleName(),service.hashCode());
        }
        if(null != service){
            synchronized (service){
                if(null != service){
                    try {
                        //发出关闭信号
                        service.shutdown();
                        // (所有的任务都结束的时候，返回TRUE)
                        long timeout = 3000;
                        if(!service.awaitTermination(timeout, TimeUnit.MILLISECONDS)){
                            // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                            service.shutdownNow();
                        }
                    } catch (InterruptedException e) {
                        // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
                        service.shutdownNow();
                    }
                }
            }
        }
    }
}
