package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Activity Entity： 用于项目工时中的活动定义")
public class Activity {
    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getDef1() {
        return def1;
    }

    public void setDef1(String def1) {
        this.def1 = def1;
    }

    public String getDef2() {
        return def2;
    }

    public void setDef2(String def2) {
        this.def2 = def2;
    }

    public Activity(Integer aid, String def1, String def2) {
        this.aid = aid;
        this.def1 = def1;
        this.def2 = def2;
    }

//    public Activity(){
//        this.aid = 0;
//        this.def1 = "";
//        this.def2 = "";
//    }

    Integer aid;
    @ApiModelProperty("第一层定义")
    String def1;
    @ApiModelProperty("第二层定义")
    String def2;
}
