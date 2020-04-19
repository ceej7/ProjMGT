package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;

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
        function_desc="";
    }

//    public Manhour(){
//        this.mid = 0;
//        this.fid = 0;
//        this.date = new Date(0);
//        this.starttime = new Timestamp(0);
//        this.endtime = new Timestamp(0);
//        this.status = "";
//        this.employee_project_id = 0;
//        this.activity_id = 0;
//        employeeProject=new EmployeeProject();
//        activity=new Activity();
//        String function_desc="";
//    }

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
    public String getFunction_desc() {
        return function_desc;
    }

    public void setFunction_desc(String function_desc) {
        this.function_desc = function_desc;
    }

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

    public void setFunction_desc_by_FunctionObject(int fid, JSONObject functionObject) {
        if(functionObject==null){
            function_desc="unresolved function";
        }
        int fid1=fid/1000;
        int fid2=fid-fid1*1000;
        String fid_str=String.format("%03d%03d", fid1,fid2);
        if(functionObject.containsKey(fid_str)){
            function_desc=functionObject.get(fid_str).toString();
            return;
        }
        else{
            function_desc="unresolved function";
        }

    }

    Integer mid;
    @ApiModelProperty("从Project的function字段的json中解析出的项目功能需求id")
    Integer fid;
    Date date;
    Timestamp starttime;
    Timestamp endtime;
    @ApiModelProperty("仅支持'unfilled','unchecked','checked'")
    String status;
    @ApiModelProperty("外键：项目成员id")
    Integer employee_project_id;
    @ApiModelProperty("外键：工时活动id")
    Integer activity_id;

    EmployeeProject employeeProject;
    Activity activity;

    String function_desc;
}
