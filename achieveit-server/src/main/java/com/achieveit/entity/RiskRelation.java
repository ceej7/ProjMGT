package com.achieveit.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("RiskRelation Entity: 风险相关人信息")
public class RiskRelation {

    public RiskRelation(Integer employee_project_id, Integer risk_id) {
        this.employee_project_id = employee_project_id;
        this.risk_id = risk_id;
    }
//    public RiskRelation(){
//        this.employee_project_id = 0;
//        this.risk_id = 0;
//        employeeProject=new EmployeeProject();
//    }

    public Integer getEmployee_project_id() {
        return employee_project_id;
    }

    public void setEmployee_project_id(Integer employee_project_id) {
        this.employee_project_id = employee_project_id;
    }

    public Integer getRisk_id() {
        return risk_id;
    }

    public void setRisk_id(Integer risk_id) {
        this.risk_id = risk_id;
    }

    public EmployeeProject getEmployeeProject() {
        return employeeProject;
    }

    public void setEmployeeProject(EmployeeProject employeeProject) {
        this.employeeProject = employeeProject;
    }

    @ApiModelProperty("主键Ⅰ：外键：成员的id")
    Integer employee_project_id;
    @ApiModelProperty("主键Ⅱ：外键：风险的id")
    Integer risk_id;

    EmployeeProject employeeProject;
}
