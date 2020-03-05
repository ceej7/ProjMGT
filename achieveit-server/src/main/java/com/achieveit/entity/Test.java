package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Test Entity: 仅用于测试框架")
public class Test {
    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Test() {
    }

    public Test(Integer tid, String description) {
        this.tid = tid;
        this.description = description;
    }

    @ApiModelProperty("测试数据tid，唯一且非null")
    private Integer tid;
    @ApiModelProperty("测试数据的描述")
    private String description;
}
