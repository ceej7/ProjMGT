package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("Defect Entity: 缺陷数据")
public class Defect {
    public Defect(Integer did, byte[] authority, String desc, String git_repo, String commit, String status, String project_id, Integer employee_project_id) {
        this.did = did;
        this.authority = authority;
        this.desc = desc;
        this.git_repo = git_repo;
        this.commit = commit;
        this.status = status;
        this.project_id = project_id;
        this.employee_project_id = employee_project_id;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public byte[] getAuthority() {
        return authority;
    }

    public void setAuthority(byte[] authority) {
        this.authority = authority;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGit_repo() {
        return git_repo;
    }

    public void setGit_repo(String git_repo) {
        this.git_repo = git_repo;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public Integer getEmployee_project_id() {
        return employee_project_id;
    }

    public void setEmployee_project_id(Integer employee_project_id) {
        this.employee_project_id = employee_project_id;
    }

    Integer did ;
    @ApiModelProperty("两个比特位00-无权限\n" +
            "01-开发Leader权限\n" +
            "10-测试Leader权限\n" +
            "11-项目经理权限")
    byte[] authority;
    String desc;
    String git_repo;
    String commit;
    @ApiModelProperty("仅支持'bug','reopen','fixed','wontfix','feature','closed'")
    String status;
    @ApiModelProperty("外键: Project_id")
    String project_id;
    @ApiModelProperty("外键: employee_project_id")
    Integer employee_project_id;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public EmployeeProject getEmployeeProject() {
        return employeeProject;
    }

    public void setEmployeeProject(EmployeeProject employeeProject) {
        this.employeeProject = employeeProject;
    }

    Project project;
    EmployeeProject employeeProject;
}
