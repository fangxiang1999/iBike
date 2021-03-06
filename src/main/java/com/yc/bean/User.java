package com.yc.bean;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 对应mongo的一个文档，多个文档构成一个collection
 * @author wys
 * @date 2020/6/21 - 19:24
 */
@Document(collection = "users") //会自动在mongo中创建collection和相应字段
public class User implements Serializable {

    private static final long serialVersionUID = -4750369623346428567L;

    /**
     * 用户状态: 0：刚注册，通过验证码
     *          1：押金缴纳
     *          2：实名认证
     */
    private int status;
    //这个字段创建索引
    @Indexed(unique = true)
    private String phoneNum;    //用手机号在mongo中创建唯一索引

    private String name;
    private String idNum; //身份证
    private double deposit; //押金
    private double balance; //余额

    @Transient //瞬态化 在数据库中不存储这个数据
    private String verifyCode;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "status=" + status +
                ", phoneNum='" + phoneNum + '\'' +
                ", name='" + name + '\'' +
                ", idNum='" + idNum + '\'' +
                ", deposit=" + deposit +
                ", balance=" + balance +
                ", verifyCode='" + verifyCode + '\'' +
                '}';
    }
}
