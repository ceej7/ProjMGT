package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Client Entity: 客户数据")
public class Client {

//    public setDefault() {
//        this.cid = 0;
//        this.name = "";
//        this.company = "";
//        this.grade = "";
//        this.email = "";
//        this.phone = "";
//        this.address = "";
//    }

    public Client(Integer cid, String name, String company, String grade, String email, String phone, String address) {
        this.cid = cid;
        this.name = name;
        this.company = company;
        this.grade = grade;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    Integer cid;
    String name;
    String company;
    @ApiModelProperty("允许的等级为'p0','p1','p2','p3','p4','p5','p6','p7','p8','p9'")
    String grade;
    String email;
    String phone;
    String address;
}
