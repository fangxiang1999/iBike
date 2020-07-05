package com.yc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author wys
 * @date 2020/6/13 - 14:21
 */
@ApiModel(value="单车",description="单车实体信息类")
public class Bike implements Serializable {

    private static final long serialVersionUID = -6069062486956102486L;
    @ApiModelProperty(value="单车编号")
    private Long bid;
    //状态
    @ApiModelProperty(value = "状态")
    private int status;
    //二维码
    @ApiModelProperty(value = "二维码")
    private String qrcode;

    @Override
    public String toString() {
        return "Bike{" +
                "bid=" + bid +
                ", status=" + status +
                ", qrcode='" + qrcode + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", id=" + id +
                ", loc=" + Arrays.toString(loc) +
                '}';
    }

    @ApiModelProperty(value = "经度")
    private double longitude;
    @ApiModelProperty(value = "纬度")
    private double latitude;
    private  Long id;
    private Double[]loc=new Double[2];

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double[] getLoc() {
        return loc;
    }

    public void setLoc(Double[] loc) {
        this.loc = loc;
    }

    /**
     * 未启用
     */
    public static final int UNACTIVE=0;
    /**
     * 未解锁
     */
    public static final int LOCK=1;
    /**
     * 正在使用
     */
    public static final int USING=2;
    /**
     * 维修中
     */
    public static final int INTROUBLE=3;

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
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

}
