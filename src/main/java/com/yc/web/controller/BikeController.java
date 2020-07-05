package com.yc.web.controller;

import com.yc.bean.Bike;
import com.yc.service.BikeService;
import com.yc.web.model.JsonModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author wys
 * @date 2020/6/12 - 21:06
 */
    //@RestController   相当于 controller+ResponseBody 所以的返回都是json
@Controller
@Api(value="单车操作接口",tags={"单车操作接口","控制层"})
public class BikeController {

    private Logger log= LogManager.getLogger(BikeController.class);
    @Autowired
    private BikeService bikeService;

    @RequestMapping(value = "/index",method = {RequestMethod.POST,RequestMethod.GET})
    public String hello(){
        return "index.html";
    }



    @ApiOperation(value="开锁操作",notes="给指定的单车开锁，参数以json传过来")
    @RequestMapping(value = "/open",method = {RequestMethod.POST})    //这里不需要/yc74ibike前置路径
                //返回json数据                  忽略
    public @ResponseBody JsonModel openLock(@ApiIgnore JsonModel js, @RequestBody Bike bike){
        log.info("请求参数"+bike);
        try {
            bikeService.open(bike);
            js.setCode(1);
            js.setMsg("开锁成功");
        }catch (Exception e){
            //e.printStackTrace();
            js.setCode(0);
            js.setMsg(e.getMessage());
        }
        return js;
    }

    @ApiOperation(value="寻找最近的单车",notes="查找最近的40部单车")
    @RequestMapping(value = "/findNearAll",method = RequestMethod.POST)
    public @ResponseBody JsonModel findNearAll(@ApiIgnore JsonModel js,@RequestBody Bike bike){
        List<Bike> list=bikeService.findNearAll(bike);
        js.setCode(1);
        js.setObj(list);
        return js;
    }

}
