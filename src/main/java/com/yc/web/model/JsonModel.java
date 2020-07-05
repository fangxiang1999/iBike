package com.yc.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wys
 * @date 2020/6/13 - 21:56
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)  //忽略掉null，然后返回
@ApiModel(value = "结果信息实体",description = "所有的REST调用得到的结果")
public class JsonModel implements Serializable {
    private static final long serialVersionUID = 8339928920251792494L;
    @ApiModelProperty(value="操作响应码,一般1为成功，其他则失败")
    private Integer code;
    @ApiModelProperty(value="操作响应信息,如code为0，则为异常信息")
    private String msg;
    @ApiModelProperty(value="操作的结果,如code为1，则为结果值")
    private Object obj;
    @ApiModelProperty(value="本次操作执行后，要跳转的网址")
    private String url;

    @Override
    public String toString() {
        return "JsonModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                ", url='" + url + '\'' +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

