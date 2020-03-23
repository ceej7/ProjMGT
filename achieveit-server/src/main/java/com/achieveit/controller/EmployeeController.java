package com.achieveit.controller;

import com.achieveit.entity.Employee;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "员工接口", value="员工相关API")
public class EmployeeController {
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private final EmployeeService employeeService;
    Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @ApiOperation("用户登陆，提供name,password, 返回Employee和token")
    @PostMapping("/employee/login")
    ResponseMsg login(@RequestBody Map params){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        if(!params.containsKey("name") ||!params.containsKey("password")){
            msg.setStatusAndMessage(202, "登陆信息不完全");
        }
        else{
            msg = employeeService.login(params.get("name").toString(),params.get("password").toString());
        }
        return msg;
    }

    @ResponseBody
    @ApiOperation("根据title获取所有相关员工的信息, 仅支持'pm_manager','configurer','pm','epg_leader','qa_manager','member'")
    @GetMapping("/employee/getByTitle/{title}")
    ResponseMsg getByTitle(@PathVariable String title){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        if(!title.equals("pm_manager")
                && !title.equals("configurer")
                && !title.equals("pm")
                && !title.equals("epg_leader")
                && !title.equals("qa_manager")
                && !title.equals("member")){
            msg.setStatusAndMessage(202, "未收录的职位");
        }
        else{
            msg=employeeService.getByTitle(title);
        }
        return msg;
    }

    @ResponseBody
    @ApiOperation("根据访问头中的 [Authorization , Bearer [token]] 键值对验证用户的token")
    @GetMapping("/employee/get")
    ResponseMsg getByToken(@RequestHeader("Authorization") String authHeader){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        if(authHeader.split("Bearer").length!=2||!authHeader.split(" ")[0].equals("Bearer")){
            msg.setStatusAndMessage(202, "非法的token");
        }
        else{
            msg=employeeService.getByToken(authHeader);
        }
        return msg;
    }

    @ResponseBody
    @ApiOperation("根据用户id获得非隐私信息")
    @GetMapping("/employee/get/{eid}")
    ResponseMsg getById(@PathVariable int eid){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        if(eid<0){
            msg.setStatusAndMessage(202, "非法的id");
        }
        else{
            msg=employeeService.getByIdNonConfidential(eid);
        }
        return msg;
    }
}
