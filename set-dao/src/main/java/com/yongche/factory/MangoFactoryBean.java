package com.yongche.factory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.yongche.config.ConfigConsts;
import com.yongche.util.Startup;
import com.yongche.xmlbean.db.DataSourceInstance;
import com.yongche.xmlbean.db.Database;
import com.yongche.xmlbean.db.DatabaseConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.collections.CollectionUtils;
import org.jfaster.mango.datasource.DataSourceFactory;
import org.jfaster.mango.datasource.MasterSlaveDataSourceFactory;
import org.jfaster.mango.datasource.SimpleDataSourceFactory;
import org.jfaster.mango.operator.Mango;
import org.jfaster.mango.plugin.spring.AbstractMangoFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MangoFactoryBean extends AbstractMangoFactoryBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static Queue<HikariDataSource> dataSourceQueue = new LinkedList<HikariDataSource>();

    private static Lock lock = new ReentrantLock(true);

    @Override
    public Mango createMango() {
        try{
            lock.lock();
            if(null == dataSourceQueue.peek() && Mango.getInstances().isEmpty()){
                DatabaseConfig databaseConfig = ConfigConsts.getDatabaseConfig();
                if (databaseConfig == null) {
                    throw new IllegalStateException("databaseConfig is null, please check database.xml in config");
                }
                logger.debug(databaseConfig.toString());
                List<DataSourceFactory> list = Lists.newArrayList();
                //Map<String, DataSourceFactory> factories = Maps.newHashMap();
                for (Database database : databaseConfig.getDatabases()) {
                    //factories.put(database.getName(), buildDataSourceFactory(database));
                    list.add(buildDataSourceFactory(database));
                }
                return Mango.newInstance(list);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return getMango();
    }




    protected static DataSourceFactory buildDataSourceFactory(Database database) {
        DataSourceInstance master = database.getMaster();
        List<DataSourceInstance> slaves = database.getSlaves();
        DataSource masterDataSource = buildDataSource(master);
        if (CollectionUtils.isEmpty(slaves)) {
            return new SimpleDataSourceFactory(database.getName(),masterDataSource);
        } else {
            List<DataSource> slaveDataSources = Lists.newArrayList();
            for (DataSourceInstance slave : slaves) {
                slaveDataSources.add(buildDataSource(slave));
            }
            return new MasterSlaveDataSourceFactory(masterDataSource, slaveDataSources);
        }
    }

    protected static DataSource buildDataSource(DataSourceInstance dsi) {
        int port = dsi.getPort();
        if (port == 0) {
            port = 3306;
        }
        String url = String.format("jdbc:mysql://%s:%s/%s", dsi.getHost(), port, dsi.getDb()+"?useUnicode=true&characterEncoding=utf8&autoReconnect=true");
        int maximumPoolSize = dsi.getMaximumPoolSize();
        if (maximumPoolSize < 10) {
            maximumPoolSize = 10;
        }
        if (maximumPoolSize > 1000) {
            maximumPoolSize = 1000;
        }
        int connectionTimeout = Math.max(dsi.getConnectionTimeout(), 500);


        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(url);
        config.setUsername(dsi.getUsername());
        config.setPassword(dsi.getPassword());
        config.setMaximumPoolSize(maximumPoolSize);
        config.setConnectionTimeout(connectionTimeout);
        config.setAutoCommit(true);
        HikariDataSource ds = new HikariDataSource(config);
        dataSourceQueue.offer(ds);
        return ds;
    }

    /**
     * 关闭DB线程池
     */
    @PreDestroy
    public void shutdownConn(){
        System.out.println("-----------------------shutdownConn");
        Iterator<HikariDataSource> iterator = dataSourceQueue.iterator();
        while (iterator.hasNext()){
            HikariDataSource dataSource = iterator.next();
            if(null != dataSource){
                if(!dataSource.isClosed()){
                    logger.info("shutdown HikariCP jdbc connection pool,jdbcurl:{}",dataSource.getJdbcUrl());
                    dataSource.close();
                }
            }
        }
    }

}

