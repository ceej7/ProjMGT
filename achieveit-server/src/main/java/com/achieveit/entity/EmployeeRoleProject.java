package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("EmployeeRoleProject Entity: 项目成员角色数据")
public class EmployeeRoleProject {
    public EmployeeRoleProject(String role, Integer employee_project_id) {
        this.role = role;
        this.employee_project_id = employee_project_id;
    }

    public EmployeeRoleProject(){
        role = "";
        employee_project_id=0;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getEmployee_project_id() {
        return employee_project_id;
    }

    public void setEmployee_project_id(Integer employee_project_id) {
        this.employee_project_id = employee_project_id;
    }

    @ApiModelProperty("主键Ⅰ，仅支持'pm','rd_leader','qa_leader','rd','qa','epg'")
    String role;
    @ApiModelProperty("主键Ⅱ+外键：项目成员id")
    Integer employee_project_id;
}
