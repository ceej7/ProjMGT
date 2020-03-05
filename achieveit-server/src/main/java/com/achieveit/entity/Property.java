package com.achieveit.entity;

import io.swagger.annotations.ApiModel;

@ApiModel("Property Entity: 资产")
public class Property {
    public Property(Integer pid, String desc) {
        this.pid = pid;
        this.desc = desc;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    Integer pid;
    String desc;
}
