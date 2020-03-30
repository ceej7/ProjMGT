package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
@ApiModel("PropertyOccupy Entity: 资产租借")
public class PropertyOccupy {
    public PropertyOccupy(Integer poid, Timestamp expire_time, boolean is_intact, Integer property_id, String project_id, Integer employee_id) {
        this.poid = poid;
        this.expire_time = expire_time;
        this.is_intact = is_intact;
        this.property_id = property_id;
        this.project_id = project_id;
        this.employee_id = employee_id;
    }

    public Integer getPoid() {
        return poid;
    }

    public void setPoid(Integer poid) {
        this.poid = poid;
    }

    public Timestamp getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(Timestamp expire_time) {
        this.expire_time = expire_time;
    }

    public boolean isIs_intact() {
        return is_intact;
    }

    public void setIs_intact(boolean is_intact) {
        this.is_intact = is_intact;
    }

    public Integer getProperty_id() {
        return property_id;
    }

    public void setProperty_id(Integer property_id) {
        this.property_id = property_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
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

    Integer poid;
    Timestamp expire_time;
    boolean is_intact;
    @ApiModelProperty("外键：资产的id")
    Integer property_id;
    @ApiModelProperty("外键：项目的id")
    String project_id;
    @ApiModelProperty("外键：员工的id")
    Integer employee_id;

    Property property;
    Employee employee;
    Project project;
}
