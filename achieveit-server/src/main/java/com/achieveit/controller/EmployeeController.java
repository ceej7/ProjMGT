package com.achieveit.controller;

import com.achieveit.entity.Employee;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "员工接口", value="员工相关API")
public class EmployeeController {
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private final EmployeeService employeeService;
    Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @ApiOperation("根据title获取所有相关员工的信息, 仅支持'pm_manager','configurer','pm','epg_leader','qa_manager','member'")
    @GetMapping("/employee/getByTitle/{title}")
    ResponseMsg getByTitle(@PathVariable String title){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        try{
            List<Employee> employees=employeeService.getByTitle(title);
            msg.getResponseMap().put("employees", employees);
            msg.setStatusAndMessage(200, "正常查找");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
