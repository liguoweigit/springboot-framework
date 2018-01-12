package com.yongche.mango;


import org.jfaster.mango.sharding.ShardingStrategy;
import org.jfaster.mango.sharding.TableShardingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xieweibo on 2016/10/24.
 */
public class DispatchShardingStrategy implements ShardingStrategy<Long,Long> {

    private final static Logger logger = LoggerFactory.getLogger(DispatchShardingStrategy.class);

    // 由于目前分库没有分在不同机器上，先不分库配置

    public String getDatabase(Long aLong) {
        return "dispatch";
    }


    public String getTargetTable(String table, Long serviceOrderId) {
        String targetTable = "dispatch" + (serviceOrderId % 256 )+ "." + table;
        logger.info("targetTable orderId:{} | name :{}",serviceOrderId,targetTable);
        return targetTable;
    }

    public static void main(String[] args) {
        System.out.println(new DispatchShardingStrategy().getTargetTable("dispatch",6510101745209649592l));
    }


    public String getDataSourceFactoryName(Long shardingParameter) {
        return "dispatch";
    }
}
