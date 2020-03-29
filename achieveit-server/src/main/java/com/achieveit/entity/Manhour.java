package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;
import java.sql.Timestamp;
@ApiModel("Manhour Entity: 工作时间")
public class Manhour {

    public Manhour(Integer mid, Integer fid, Date date, Timestamp starttime, Timestamp endtime, String status, Integer employee_project_id, Integer activity_id) {
        this.mid = mid;
        this.fid = fid;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.status = status;
        this.employee_project_id = employee_project_id;
        this.activity_id = activity_id;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEmployee_project_id() {
        return employee_project_id;
    }

    public void setEmployee_project_id(Integer employee_project_id) {
        this.employee_project_id = employee_project_id;
    }

    public Integer getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Integer activity_id) {
        this.activity_id = activity_id;
    }

    Integer mid;
    @ApiModelProperty("从Project的function字段的json中解析出的项目功能需求id")
    Integer fid;
    Date date;
    Timestamp starttime;
    Timestamp endtime;
    @ApiModelProperty("仅支持'unfilled','unchecked','checked','expired'")
    String status;
    @ApiModelProperty("外键：项目成员id")
    Integer employee_project_id;
    @ApiModelProperty("外键：工时活动id")
    Integer activity_id;

    public EmployeeProject getEmployeeProject() {
        return employeeProject;
    }

    public void setEmployeeProject(EmployeeProject employeeProject) {
        this.employeeProject = employeeProject;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    EmployeeProject employeeProject;
    Activity activity;
}
