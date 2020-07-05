package com.yc.service;

import com.yc.bean.User;

/**
 * @author wys
 * @date 2020/6/21 - 11:35
 */

public interface UserService {
    //生成验证码，发送到手机
    public void genVerifyCode(String nationCode,String phoneNum) throws Exception;

    public boolean verify(User user);

    public boolean deposit(User user);

    public boolean identity(User user);
}
