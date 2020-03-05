package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("EmployeeProject Entity: 项目成员数据")
public class EmployeeProject {
    public EmployeeProject(Integer epid, byte defect_authority, Integer superior_epid, String project_id, Integer employee_id) {
        this.epid = epid;
        this.defect_authority = defect_authority;
        this.superior_epid = superior_epid;
        this.project_id = project_id;
        this.employee_id = employee_id;
    }

    public Integer getEpid() {
        return epid;
    }

    public void setEpid(Integer epid) {
        this.epid = epid;
    }

    public byte getDefect_authority() {
        return defect_authority;
    }

    public void setDefect_authority(byte defect_authority) {
        this.defect_authority = defect_authority;
    }

    public Integer getSuperior_epid() {
        return superior_epid;
    }

    public void setSuperior_epid(Integer superior_epid) {
        this.superior_epid = superior_epid;
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

    Integer epid;
    @ApiModelProperty("两位bit：00-无权限\n" +
            "01-开发Leader权限\n" +
            "10-测试Leader权限\n" +
            "11-项目经理权限")
    byte defect_authority;
    @ApiModelProperty("外键：项目成员中的上级")
    Integer superior_epid;
    @ApiModelProperty("外键：项目id")
    String project_id;
    @ApiModelProperty("外键：人事信息中id")
    Integer employee_id;
}
