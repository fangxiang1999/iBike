package com.yc.web.controller;

import com.yc.bean.User;
import com.yc.service.UserService;
import com.yc.web.model.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wys
 * @date 2020/6/21 - 12:18
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/identity")
    public @ResponseBody JsonModel identity(JsonModel js,User user){
        boolean flag=userService.identity(user);
        if(flag){
            js.setCode(1);
        }else{
            js.setCode(0);
        }
        return js;
    }

    @PostMapping("/deposit")
    public @ResponseBody JsonModel deposit(JsonModel js,User user){
        boolean flag=userService.deposit(user);
        if(flag){
            js.setCode(1);
        }else{
            js.setCode(0);
        }
        return js;
    }


    @PostMapping("/genCode")
    public @ResponseBody JsonModel genSMSCode(JsonModel js,String nationCode,String phoneNum){
        String msg="true";
        try {
            userService.genVerifyCode(nationCode,phoneNum);
            js.setCode(1);
        }catch (Exception e){
            e.printStackTrace();
            js.setCode(0);
            js.setMsg(e.getMessage());
        }
        return js;

    }

    @PostMapping("/verify")
    public @ResponseBody JsonModel verify(JsonModel js, User user){
        boolean flag=false;
        try {
            flag=userService.verify(user);
            if(flag){
                js.setCode(1);//成功
            }else {
                js.setCode(0);
                js.setMsg("验证码错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            js.setCode(0);
            js.setMsg("错误原因："+e.getMessage());
        }

        return js;
    }
}
