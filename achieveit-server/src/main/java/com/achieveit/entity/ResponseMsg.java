package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;

/**
 * status: 提示代码
 * message: 提示信息
 * response: 数据实体
 */
@ApiModel("ResponseMsg Entity：请求返回实体")
public class ResponseMsg {
    @ApiModelProperty("状态代码")
    private Integer status;
    @ApiModelProperty("对状态代码的说明")
    private String message;
    @ApiModelProperty("数据键值对")
    private HashMap<String, Object> responseMap = new HashMap<>();

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setStatusAndMessage(Integer status, String message){
        this.status = status;
        this.message = message;
    }

    public HashMap<String, Object> getResponseMap() {
        return responseMap;
    }
}