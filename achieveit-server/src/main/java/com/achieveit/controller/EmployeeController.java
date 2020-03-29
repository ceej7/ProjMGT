package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.EmployeeService;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Api(tags = "员工接口", value="员工相关API")
public class EmployeeController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private JwtToken jwtToken;
    private final EmployeeService employeeService;
    private final MailService mailService;
    private final FileService fileService;

    public EmployeeController(MailService mailService, FileService fileService,EmployeeService employeeService, JwtToken jwtToken) {
        this.mailService = mailService;
        this.fileService = fileService;
        this.employeeService = employeeService;
        this.jwtToken=jwtToken;
    }

    @ResponseBody
    @ApiOperation(value = "用户登陆，提供name,password, 返回Employee和token", notes="{\"name\":\"Alias\",\"password\":\"123456\"}")
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
        String s[]=authHeader.split("Bearer");
        if(authHeader.split("Bearer").length!=2||!authHeader.split("Bearer")[0].equals("")){
            msg.setStatusAndMessage(JwtToken.Illegal, "非法的token");
        }
        else{
            Claims claims = jwtToken.getClaimByToken(authHeader);
            if (claims == null ) {
                msg.setStatusAndMessage(JwtToken.Invalid, "Token无效");
            }
            else if (JwtToken.isTokenExpired(claims.getExpiration())){
                msg.setStatusAndMessage(JwtToken.Expired, "Token过期");
            }
            else{
                int userId = Integer.valueOf(claims.getSubject());
                msg=employeeService.getByIdConfidential(userId);
            }
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

    @ResponseBody
    @ApiOperation("获得用户通用DashBoard信息（我的Project、Defect、Manhour、PropertyOccupy、Risk）\n根据访问头中的 [Authorization , Bearer [token]] 键值对验证用户的token")
    @GetMapping("/employee/myDashBoard")
    ResponseMsg getDashBoardByToken(@RequestHeader("Authorization") String authHeader){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        String s[]=authHeader.split("Bearer");
        if(authHeader.split("Bearer").length!=2||!authHeader.split("Bearer")[0].equals("")){
            msg.setStatusAndMessage(JwtToken.Illegal, "非法的token");
        }
        else{
            Claims claims = jwtToken.getClaimByToken(authHeader);
            if (claims == null ) {
                msg.setStatusAndMessage(JwtToken.Invalid, "Token无效");
            }
            else if (JwtToken.isTokenExpired(claims.getExpiration())){
                msg.setStatusAndMessage(JwtToken.Expired, "Token过期");
            }
            else{
                int userId = Integer.valueOf(claims.getSubject());
                msg=employeeService.getDashBoardByIdConfidential(userId);
            }
        }
        return msg;
    }
}
