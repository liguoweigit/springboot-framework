package com.yongche.dao;

import com.yongche.pojo.CarType;
import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.SQL;

import java.util.List;
import java.util.Set;

/**
 * 车型数据表
 *
 */
@DB(name = "xxx",table = "car_type")
public interface CarTypeDao {

    @SQL("select * from #table where car_type_id in (:1)")
    List<CarType> getUsersInList(List<Integer> ids);

    /**
     * 根据标记位查询所有carTypeId
     * @param flag
     * @return
     */
    @SQL("select car_type_id from #table where flag & :1 = :1")
    Set<Integer> getAllCarTypeIds(int flag);

    @SQL("select * from #table where car_type_id = :1")
    CarType getByCarTypeId(int carTypeId);
}
