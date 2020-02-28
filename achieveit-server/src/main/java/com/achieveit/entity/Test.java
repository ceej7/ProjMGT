package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@ApiModel("尝试数据Model")
public class Test {
    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
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

    public Test(int tid, String description) {
        this.tid = tid;
        this.description = description;
    }

    @ApiModelProperty("尝试数据tid，唯一且非null")
    private int tid;
    @ApiModelProperty("尝试数据的描述")
    private String description;
}
