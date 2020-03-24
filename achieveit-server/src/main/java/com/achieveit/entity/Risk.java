package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Risk Entity: 风险信息")
public class Risk {
    public Risk(Integer rid, String type, String desc, String grade, String strategy, String influence, Integer frequency, boolean template, Integer employee_id, String project_id) {
        this.rid = rid;
        this.type = type;
        this.desc = desc;
        this.grade = grade;
        this.strategy = strategy;
        this.influence = influence;
        this.frequency = frequency;
        this.template = template;
        this.employee_id = employee_id;
        this.project_id = project_id;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getInfluence() {
        return influence;
    }

    public void setInfluence(String influence) {
        this.influence = influence;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    Integer rid;
    String type;
    String desc;
    @ApiModelProperty("'p0','p1','p2','p3','p4','p5','p6','p7','p8','p9'")
    String grade;
    @ApiModelProperty("代表以n天的频率提醒一次")
    String strategy;
    String influence;
    Integer frequency;
    boolean template;

    @ApiModelProperty("外键：员工的id")
    Integer employee_id;
    @ApiModelProperty("外键：项目的id")
    String project_id;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    Employee employee;
    Project project;

}
