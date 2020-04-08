package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("Employee Entity: 员工数据")
public class Employee {
    public Employee(Integer eid, String name, String email, String address, String department, String phone, String password, String portrait, String title, Integer sup_eid) {
        this.eid = eid;
        this.name = name;
        this.email = email;
        this.address = address;
        this.department = department;
        this.phone = phone;
        this.password = password;
        this.portrait = portrait;
        this.title = title;
        this.sup_eid = sup_eid;
    }

//    public Employee(){
//        this.eid = 0;
//        this.name = "";
//        this.email = "";
//        this.address = "";
//        this.department = "";
//        this.phone = "";
//        this.password = "";
//        this.portrait = "";
//        this.title = "";
//        this.sup_eid = 0;
//    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSup_eid() {
        return sup_eid;
    }

    public void setSup_eid(Integer sup_eid) {
        this.sup_eid = sup_eid;
    }

    public Employee getSup() {
        return sup;
    }

    public void setSup(Employee sup) {
        this.sup = sup;
    }

    Integer eid;
    String name;
    String email;
    String address;
    String department;
    String phone;
    String password;
    String portrait;
    @ApiModelProperty("仅支持'pm_manager','configurer','pm','epg_leader','qa_manager','member'")
    String title;
    @ApiModelProperty("外键: 上级的id")
    Integer sup_eid;
    Employee sup;
}
