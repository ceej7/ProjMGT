package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("EmployeeRoleProject Entity: 项目成员角色数据")
public class EmployeeRoleProject {
    public EmployeeRoleProject(Integer erpid, String role, Integer employee_project_id) {
        this.erpid = erpid;
        this.role = role;
        this.employee_project_id = employee_project_id;
    }

    public Integer getErpid() {
        return erpid;
    }

    public void setErpid(Integer erpid) {
        this.erpid = erpid;
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

    Integer erpid;
    @ApiModelProperty("仅支持'pm','rd_leader','test_leader','rd','test','configurer','qa','epg'")
    String role;
    @ApiModelProperty("外键：项目成员id")
    Integer employee_project_id;
}
