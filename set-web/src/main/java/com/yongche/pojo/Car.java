package com.yongche.pojo;

import com.alibaba.fastjson.JSONObject;
import jmind.core.json.Pojo;

import java.util.Map;

/**
 * lbs接口返回车辆信息对应实体
 */
public class Car extends Pojo implements Comparable<Car> {
    private String name;
    private String city;
    private String cellphone;
    private String vehicle_number;
    private double longitude;
    private double latitude;
    private int car_brand_id;           //车型ID
    private String brand;               //车辆型号 eg:宝马6系(进口)
    private long flag;
    private long car_id;                //汽车信息id
    private long driver_id;             //司机编号
    private int driver_level;           //司机等级
    private int car_type_id;            //车辆类型编号
    private long total_rate;
    private int distance_time_length;   //直线距离时间
    private long distance;              //直线距离
    private long distance_rate;         //预估距离倍率
    private long route_distance;        //导航距离
    private long route_time_length;     //导航距离时间
    private long contribution;          //司机贡献值
    private long contribution_rate;
    private long evaluation;            //评价分
    private long evaluation_rate;
    private long base_score;            //月度基础分
    private long base_score_rate;
    private long good_comment_rate;     //好评率
    private String version;             //司机客户端版本
    private String imei;                //手机 IMEI
    private long device_id;             //绑定终端id
    private int is_assigned;
    private int has_qualified;          //是否具有运营资质

    private int car_model_id;
    private int device_type;
    private int audit_status;
    private int has_logistics;
    private long discount_rate;
    private long last_position_time;
    private int surpport_face_pay;
    private int is_self_employed;
    private String country;
    private int seat_num;
    private int driver_type;
    private int color;
    private long silence_end_at;
    private int work_status;
    private float estimate_price;

    //派单用参数
    private JSONObject add_price_set;
    private int is_arrange;

	private int index ;


	//顺风车过滤用参数,开启顺丰车的司机才传此三个参数
    private boolean freeRide;    //顺路订单标识
    private long directDistance; //直接到司机目的地距离
    private long detourDistance; //绕路接乘客距离
    private Map<String,Object> driver_destination; //顺路司机目的地信息 "dest_lat":39.925555695,"dest_lng":116.5506538501,"coord_type":"mars"
    private boolean merchant_free_ride_order;
    private String listen_car_types;

    private int is_live_car;  //1:true;0:false,是否直播车


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getFlag() {
        return flag;
    }

    public void setFlag(long flag) {
        this.flag = flag;
    }

    public int getCar_brand_id() {
        return car_brand_id;
    }

    public void setCar_brand_id(int car_brand_id) {
        this.car_brand_id = car_brand_id;
    }

    public long getCar_id() {
        return car_id;
    }

    public void setCar_id(long car_id) {
        this.car_id = car_id;
    }

    public long getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(long driver_id) {
        this.driver_id = driver_id;
    }

    public long getDevice_id() {
        return device_id;
    }

    public void setDevice_id(long device_id) {
        this.device_id = device_id;
    }

    public int getDriver_level() {
        return driver_level;
    }

    public void setDriver_level(int driver_level) {
        this.driver_level = driver_level;
    }

    public int getCar_type_id() {
        return car_type_id;
    }

    public void setCar_type_id(int car_type_id) {
        this.car_type_id = car_type_id;
    }

    public long getTotal_rate() {
        return total_rate;
    }

    public void setTotal_rate(long total_rate) {
        this.total_rate = total_rate;
    }

    public int getDistance_time_length() {
        return distance_time_length;
    }

    public void setDistance_time_length(int distance_time_length) {
        this.distance_time_length = distance_time_length;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public long getDistance_rate() {
        return distance_rate;
    }

    public void setDistance_rate(long distance_rate) {
        this.distance_rate = distance_rate;
    }

    public long getRoute_distance() {
        return route_distance;
    }

    public void setRoute_distance(long route_distance) {
        this.route_distance = route_distance;
    }

    public long getRoute_time_length() {
        return route_time_length;
    }

    public void setRoute_time_length(long route_time_length) {
        this.route_time_length = route_time_length;
    }

    public long getContribution() {
        return contribution;
    }

    public void setContribution(long contribution) {
        this.contribution = contribution;
    }

    public long getContribution_rate() {
        return contribution_rate;
    }

    public void setContribution_rate(long contribution_rate) {
        this.contribution_rate = contribution_rate;
    }

    public long getBase_score() {
        return base_score;
    }

    public void setBase_score(long base_score) {
        this.base_score = base_score;
    }

    public long getBase_score_rate() {
        return base_score_rate;
    }

    public void setBase_score_rate(long base_score_rate) {
        this.base_score_rate = base_score_rate;
    }

    public long getGood_comment_rate() {
        return good_comment_rate;
    }

    public void setGood_comment_rate(long good_comment_rate) {
        this.good_comment_rate = good_comment_rate;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getIs_assigned() {
        return is_assigned;
    }

    public void setIs_assigned(int is_assigned) {
        this.is_assigned = is_assigned;
    }

    public int getHas_qualified() {
        return has_qualified;
    }

    public void setHas_qualified(int has_qualified) {
        this.has_qualified = has_qualified;
    }

    public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

    public long getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(long evaluation) {
        this.evaluation = evaluation;
    }

    public long getEvaluation_rate() {
        return evaluation_rate;
    }

    public void setEvaluation_rate(long evaluation_rate) {
        this.evaluation_rate = evaluation_rate;
    }

    public int getCar_model_id() {
        return car_model_id;
    }

    public void setCar_model_id(int car_model_id) {
        this.car_model_id = car_model_id;
    }

    public int getDevice_type() {
        return device_type;
    }

    public void setDevice_type(int device_type) {
        this.device_type = device_type;
    }

    public int getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(int audit_status) {
        this.audit_status = audit_status;
    }

    public int getHas_logistics() {
        return has_logistics;
    }

    public void setHas_logistics(int has_logistics) {
        this.has_logistics = has_logistics;
    }

    public long getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(long discount_rate) {
        this.discount_rate = discount_rate;
    }

    public long getLast_position_time() {
        return last_position_time;
    }

    public void setLast_position_time(long last_position_time) {
        this.last_position_time = last_position_time;
    }

    public int getSurpport_face_pay() {
        return surpport_face_pay;
    }

    public void setSurpport_face_pay(int surpport_face_pay) {
        this.surpport_face_pay = surpport_face_pay;
    }

    public int getIs_self_employed() {
        return is_self_employed;
    }

    public void setIs_self_employed(int is_self_employed) {
        this.is_self_employed = is_self_employed;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSeat_num() {
        return seat_num;
    }

    public void setSeat_num(int seat_num) {
        this.seat_num = seat_num;
    }

    public int getDriver_type() {
        return driver_type;
    }

    public void setDriver_type(int driver_type) {
        this.driver_type = driver_type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getSilence_end_at() {
        return silence_end_at;
    }

    public void setSilence_end_at(long silence_end_at) {
        this.silence_end_at = silence_end_at;
    }

    public int getWork_status() {
        return work_status;
    }

    public void setWork_status(int work_status) {
        this.work_status = work_status;
    }

    public float getEstimate_price() {
        return estimate_price;
    }

    public void setEstimate_price(float estimate_price) {
        this.estimate_price = estimate_price;
    }

    @Override
	public int compareTo(Car o) {
		return index-o.getIndex();
	}


    @Override
    public String toString() {
        return driver_id + "";
    }

    public JSONObject getAdd_price_set() {
        return add_price_set;
    }

    public void setAdd_price_set(JSONObject add_price_set) {
        this.add_price_set = add_price_set;
    }

    public int getIs_arrange() {return is_arrange;}
    public void setIs_arrange(int is_arrange) {this.is_arrange = is_arrange;}

    public boolean isFreeRide() {
        return freeRide;
    }

    public void setFreeRide(boolean freeRide) {
        this.freeRide = freeRide;
    }

    public long getDirectDistance() {
        return directDistance;
    }

    public void setDirectDistance(long directDistance) {
        this.directDistance = directDistance;
    }

    public long getDetourDistance() {
        return detourDistance;
    }

    public void setDetourDistance(long detourDistance) {
        this.detourDistance = detourDistance;
    }

    public Map<String, Object> getDriver_destination() {
        return driver_destination;
    }

    public void setDriver_destination(Map<String, Object> driver_destination) {
        this.driver_destination = driver_destination;
    }

    public boolean isMerchant_free_ride_order() {
        return merchant_free_ride_order;
    }

    public void setMerchant_free_ride_order(boolean merchant_free_ride_order) {
        this.merchant_free_ride_order = merchant_free_ride_order;
    }

    public int getIs_live_car() {
        return is_live_car;
    }

    public void setIs_live_car(int is_live_car) {
        this.is_live_car = is_live_car;
    }

    public String getListen_car_types() {
        return listen_car_types;
    }

    public void setListen_car_types(String listen_car_types) {
        this.listen_car_types = listen_car_types;
    }
}
