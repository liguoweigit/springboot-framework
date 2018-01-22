package com.yongche.webutil;

import java.io.Serializable;

public class UserDecisionVo implements Serializable {
    private long order_id;
    private long driver_id;
    private long corporate_id;
    private long coupon_member_id;
    private String sms;
    private String passenger_name;
    private String passenger_phone;

    public UserDecisionVo() {
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public long getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(long driver_id) {
        this.driver_id = driver_id;
    }

    public long getCorporate_id() {
        return corporate_id;
    }

    public void setCorporate_id(long corporate_id) {
        this.corporate_id = corporate_id;
    }

    public long getCoupon_member_id() {
        return coupon_member_id;
    }

    public void setCoupon_member_id(long coupon_member_id) {
        this.coupon_member_id = coupon_member_id;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getPassenger_name() {
        return passenger_name;
    }

    public void setPassenger_name(String passenger_name) {
        this.passenger_name = passenger_name;
    }

    public String getPassenger_phone() {
        return passenger_phone;
    }

    public void setPassenger_phone(String passenger_phone) {
        this.passenger_phone = passenger_phone;
    }
}
