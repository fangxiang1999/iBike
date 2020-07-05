package com.yc.web.controller;

import com.yc.service.LogService;
import com.yc.web.model.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wys
 * @date 2020/6/16 - 21:10
 */
@Controller
public class LogContoller {
  @Autowired
  private LogService logService;

  @PostMapping("log/savelog")
  @ResponseBody   //回应的数据为json  //@RequestBody 主要用来接收前端传递给后端的json字符串中的数据的
   public JsonModel ready(JsonModel js, @RequestBody String log){
      logService.save(log);
      js.setCode(1);
      return js;
  }

}
