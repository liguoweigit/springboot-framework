package com.yongche.pojo;

import java.io.Serializable;

/**
 * 车型数据表(car_type)对应实体
 * Created by jiangchao3 on 2016/11/1 0001.
 */
public class CarType implements Serializable {
    private int carTypeId;                        //
    private String name;                          //
    private String enName;                        //english name
    private String description;                   //
    private String personNumber;                  //
    private int status;                           //1: 正常， 2. 冻结， -1: 删除
    private int orderId;                          //车型在显示中的排序
    private int flag;                             //按位保存的车型的属性
    private int priceRank;                        //The price rank number for each car type
    private int updateTime;                       //更新时间
    private int createTime;                       //创建时间
    private int operatorId;                       //最后修改人
    private String recommendCarTypes;             //推荐车型
    private String waitfordispatchcarPopupDesc;   //派单动画页面弹窗
    private int showRedDot;                       //车型显示new标签 1显示 0不显示
    private int onlySupportUserDecision;          //1:只支持用户决策

    public int getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(int carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getPriceRank() {
        return priceRank;
    }

    public void setPriceRank(int priceRank) {
        this.priceRank = priceRank;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public String getRecommendCarTypes() {
        return recommendCarTypes;
    }

    public void setRecommendCarTypes(String recommendCarTypes) {
        this.recommendCarTypes = recommendCarTypes;
    }

    public String getWaitfordispatchcarPopupDesc() {
        return waitfordispatchcarPopupDesc;
    }

    public void setWaitfordispatchcarPopupDesc(String waitfordispatchcarPopupDesc) {
        this.waitfordispatchcarPopupDesc = waitfordispatchcarPopupDesc;
    }

    public int getShowRedDot() {
        return showRedDot;
    }

    public void setShowRedDot(int showRedDot) {
        this.showRedDot = showRedDot;
    }

    public int getOnlySupportUserDecision() {
        return onlySupportUserDecision;
    }

    public void setOnlySupportUserDecision(int onlySupportUserDecision) {
        this.onlySupportUserDecision = onlySupportUserDecision;
    }
}
