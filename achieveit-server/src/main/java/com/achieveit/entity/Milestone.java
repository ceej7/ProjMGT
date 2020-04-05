package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
@ApiModel("Milestone Entity：项目里程碑")
public class Milestone {

    public Milestone(Integer mid, Timestamp time, String desc, String project_id) {
        this.mid = mid;
        this.time = time;
        this.desc = desc;
        this.project_id = project_id;
    }

    public Milestone(){
        this.mid = 0;
        this.time = new Timestamp(0);
        this.desc = "";
        this.project_id = "";
        project=new Project();
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    Integer mid;
    Timestamp time;
    String desc;
    @ApiModelProperty("外键：项目id")
    String project_id;

    Project project;
}
