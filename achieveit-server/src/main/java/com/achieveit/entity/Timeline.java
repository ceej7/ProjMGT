package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import java.sql.Timestamp;

@ApiModel("某个状态变化的具体时间点")
public class Timeline {


    public Timeline(String operation_type, Timestamp add_time, Integer workflow_id, Integer employee_id) {
        this.operation_type = operation_type;
        this.add_time = add_time;
        this.workflow_id = workflow_id;
        this.employee_id = employee_id;
    }
    public Timeline(){
        this.operation_type = "";
        this.add_time = new Timestamp(0);
        this.workflow_id = 0;
        this.employee_id = 0;
        employee=new Employee();
    }

    public String getOperation() {
        return operation_type;
    }

    public void setOperation(String operation_type) {
        this.operation_type = operation_type;
    }

    public Timestamp getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Timestamp add_time) {
        this.add_time = add_time;
    }

    public Integer getWorkflow_id() {
        return workflow_id;
    }

    public void setWorkflow_id(Integer workflow_id) {
        this.workflow_id = workflow_id;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @ApiModelProperty("'applying','rejected','approved','started','delivering','submitted','achieved'")
    String operation_type;
    @ApiModelProperty("时间节点")
    Timestamp add_time;
    @ApiModelProperty("workflow_id")
    Integer workflow_id;
    @ApiModelProperty("操作者employee_id")
    Integer employee_id;

    @ApiModelProperty("操作者")
    Employee employee;
}
