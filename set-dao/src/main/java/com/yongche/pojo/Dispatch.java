package com.yongche.pojo;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by xieweibo on 2016/10/24.
 */
public class Dispatch implements Serializable {
    private long id;
    private long serviceOrderId;         //订单ID
    private int dispatchCount;           //派发数量
    private int responseCount;           //已响应的司机数量
    private int acceptCount;             //接受订单司机数量
    private int flag;                    //标志位。详见OrderFlagEnum
    private int dispatchTime;            //派单时间
    private int decisionTime;            //决策时间
    private int contribution;            //参与分(又名贡献分)
    private int expectDecisionTime;      //预计决策时间
    private int dispatchTemplateId;      //模板编码
    private String templateSnapshot;     //模板快照
    private int status;                  //派单状态
    private int dispatchType;            //1：自动派单；2：手动派单
    private int decisionType;            //1：自动决策；2：手动决策
    private int round;                   //轮次
    private int batch;                   //批次(轮次内的批次)
    private int createTime;              //创建时间
    private int updateTime;              //更新时间
    private int estimateTime;            //预估时间
    private int canDispatchCount;        //可派司机数量
    private long userId;                 //用户ID
    private int userLevel;               //用户等级
    private String userName;             //用户名称
    private String userGender;           //用户性别
    private double addPriceRedispatch;   //改派加价金额(单位：分）
    private String addPriceInfo;         //加价信息
    private long decisionDriverId;       //决策成功司机ID
    private int decisionCarTypeId;       //决策成功司机车型ID
    private long biddingId;              //加价ID
    private BigDecimal biddingRate;      //加价倍率

    public Dispatch() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(long serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public int getDispatchCount() {
        return dispatchCount;
    }

    public void setDispatchCount(int dispatchCount) {
        this.dispatchCount = dispatchCount;
    }

    public int getResponseCount() {
        return responseCount;
    }

    public void setResponseCount(int responseCount) {
        this.responseCount = responseCount;
    }

    public int getAcceptCount() {
        return acceptCount;
    }

    public void setAcceptCount(int acceptCount) {
        this.acceptCount = acceptCount;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(int dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public int getDecisionTime() {
        return decisionTime;
    }

    public void setDecisionTime(int decisionTime) {
        this.decisionTime = decisionTime;
    }

    public int getContribution() {
        return contribution;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
    }

    public int getExpectDecisionTime() {
        return expectDecisionTime;
    }

    public void setExpectDecisionTime(int expectDecisionTime) {
        this.expectDecisionTime = expectDecisionTime;
    }

    public int getDispatchTemplateId() {
        return dispatchTemplateId;
    }

    public void setDispatchTemplateId(int dispatchTemplateId) {
        this.dispatchTemplateId = dispatchTemplateId;
    }

    public String getTemplateSnapshot() {
        return templateSnapshot;
    }

    public void setTemplateSnapshot(String templateSnapshot) {
        this.templateSnapshot = templateSnapshot;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(int dispatchType) {
        this.dispatchType = dispatchType;
    }

    public int getDecisionType() {
        return decisionType;
    }

    public void setDecisionType(int decisionType) {
        this.decisionType = decisionType;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public int getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(int estimateTime) {
        this.estimateTime = estimateTime;
    }

    public int getCanDispatchCount() {
        return canDispatchCount;
    }

    public void setCanDispatchCount(int canDispatchCount) {
        this.canDispatchCount = canDispatchCount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public double getAddPriceRedispatch() {
        return addPriceRedispatch;
    }

    public void setAddPriceRedispatch(double addPriceRedispatch) {
        this.addPriceRedispatch = addPriceRedispatch;
    }

    public String getAddPriceInfo() {
        return addPriceInfo;
    }

    public void setAddPriceInfo(String addPriceInfo) {
        this.addPriceInfo = addPriceInfo;
    }

    public long getDecisionDriverId() {
        return decisionDriverId;
    }

    public void setDecisionDriverId(long decisionDriverId) {
        this.decisionDriverId = decisionDriverId;
    }

    public int getDecisionCarTypeId() {
        return decisionCarTypeId;
    }

    public void setDecisionCarTypeId(int decisionCarTypeId) {
        this.decisionCarTypeId = decisionCarTypeId;
    }

    public long getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(long biddingId) {
        this.biddingId = biddingId;
    }

    public BigDecimal getBiddingRate() {
        return biddingRate;
    }

    public void setBiddingRate(BigDecimal biddingRate) {
        this.biddingRate = biddingRate;
    }


}
