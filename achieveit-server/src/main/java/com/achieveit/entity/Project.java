package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
@ApiModel("Project Entity: 项目信息")
public class Project {
    public Project(String pid, String name, Timestamp starttime, Timestamp endtime, String technique, String domain, String function, Integer client_id, Integer workflow_id) {
        this.pid = pid;
        this.name = name;
        this.starttime = starttime;
        this.endtime = endtime;
        this.technique = technique;
        this.domain = domain;
        this.function = function;
        this.client_id = client_id;
        this.workflow_id = workflow_id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public Integer getWorkflow_id() {
        return workflow_id;
    }

    public void setWorkflow_id(Integer workflow_id) {
        this.workflow_id = workflow_id;
    }

    String pid;
    String name;
    Timestamp starttime;
    Timestamp endtime;
    String technique;
    String domain;
    @ApiModelProperty("使用json存储，使用两个Integer解析")
    String function;
    @ApiModelProperty("外键：客户的id")
    Integer client_id;
    @ApiModelProperty("外键：工作流的id；一一对应关系")
    Integer workflow_id;

}
