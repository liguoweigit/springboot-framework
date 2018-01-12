package com.yongche.pojo;

/**
 * 派单bean
 * by yongche.com
 *
 * @author mma
 * @since 2017-04-07 下午4:04
 */

public class DispatcherInfo extends Dispatch{

    private String city;

    private float estimate_price;

    private float dsb;              //driver_system_bidding

    public DispatcherInfo() {
        super();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getEstimate_price() {
        return estimate_price;
    }

    public void setEstimate_price(float estimate_price) {
        this.estimate_price = estimate_price;
    }

    public float getDsb() {
        return dsb;
    }

    public void setDsb(float dsb) {
        this.dsb = dsb;
    }
}
