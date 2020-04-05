package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel("EmployeeProject Entity: 项目成员数据")
public class EmployeeProject {
    public EmployeeProject(Integer epid, byte[] defect_authority, Integer superior_epid, String project_id, Integer employee_id) {
        this.epid = epid;
        this.defect_authority = defect_authority;
        this.superior_epid = superior_epid;
        this.project_id = project_id;
        this.employee_id = employee_id;
        authority2Desc();
    }

    public EmployeeProject() {
        this.epid = 0;
        byte[] b={0};
        this.defect_authority = b;
        this.superior_epid = 0;
        this.project_id = "";
        this.employee_id = 0;
        authority2Desc();
        project=new Project();
        employee=new Employee();
        List<EmployeeRoleProject> roles=new ArrayList<>();
        roles.add(new EmployeeRoleProject());
    }

    public Integer getEpid() {
        return epid;
    }

    public void setEpid(Integer epid) {
        this.epid = epid;
    }

    public byte[] getDefect_authority() {
        return defect_authority;
    }

    public void setDefect_authority(byte[] defect_authority) {
        this.defect_authority = defect_authority;
        authority2Desc();
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

    public EmployeeProject getSup() {
        return sup;
    }

    public void setSup(EmployeeProject sup) {
        this.sup = sup;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getAuthority_desc() {
        return authority_desc;
    }

    void authority2Desc(){
        //        00-无权限
        //        01-开发Leader权限
        //        10-测试Leader权限
        //        11-项目经理权限
        if(defect_authority==null||defect_authority.length<1) {
            authority_desc = "noneAuthority";
        }else if(defect_authority[0]==0) {
            authority_desc = "noneAuthority";
        }else if(defect_authority[0]==1) {
            authority_desc = "rdLeaderAuthority";
        }else if(defect_authority[0]==2) {
            authority_desc = "qaLeaderAuthority";
        }else{
            authority_desc = "pmAuthority";
        }
    }

    public List<EmployeeRoleProject> getRoles() {
        return roles;
    }

    public void setRoles(List<EmployeeRoleProject> roles) {
        this.roles = roles;
    }

    Integer epid;
    @ApiModelProperty("两位bit：00-无权限\n" +
            "01-开发Leader权限\n" +
            "10-测试Leader权限\n" +
            "11-项目经理权限")
    byte[] defect_authority;
    @ApiModelProperty("外键：项目成员中的上级")
    Integer superior_epid;
    @ApiModelProperty("外键：项目id")
    String project_id;
    @ApiModelProperty("外键：人事信息中id")
    Integer employee_id;

    @ApiModelProperty("对于defect_authority的描述，四种情况" +
            "{\n" +
            "noneAuthority:只能管理自己的Defect,\n" +
            "rdLeaderAuthority:可以管理所有rd类别的Defect,\n" +
            "qaLeaderAuthority:可以管理所有qa类别的Defect,\n" +
            "pmAuthority:可以管理所有Defect,\n" +
            "}")
    String authority_desc;

    EmployeeProject sup;
    Project project;
    Employee employee;
    List<EmployeeRoleProject> roles;
}
