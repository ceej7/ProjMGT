package com.achieveit.service;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.Employee;
import com.achieveit.entity.EmployeeProject;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.mapper.*;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
public class EmployeeService {
    Logger logger = LoggerFactory.getLogger(getClass());


    public EmployeeService(EmployeeMapper employeeMapper, DefectMapper defectMapper, ManhourMapper manhourMapper, ProjectMapper projectMapper, PropertyMapper propertyMapper, RiskMapper riskMapper, EmployeeProjectMapper employeeProjectMapper) {
        this.employeeMapper = employeeMapper;
        this.defectMapper = defectMapper;
        this.manhourMapper = manhourMapper;
        this.projectMapper = projectMapper;
        this.propertyMapper = propertyMapper;
        this.riskMapper = riskMapper;
        this.employeeProjectMapper = employeeProjectMapper;
    }

    EmployeeMapper employeeMapper;
    DefectMapper defectMapper;
    ManhourMapper manhourMapper;
    ProjectMapper projectMapper;
    PropertyMapper propertyMapper;
    RiskMapper riskMapper;
    EmployeeProjectMapper employeeProjectMapper;

    @Autowired
    private JwtToken jwtToken;


    public ResponseMsg getByTitle(String title){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        try{
            List<Employee> employees=employeeMapper.getByTitle(title);
            for (int i = 0; i < employees.size(); i++) {
                employees.get(i).setPassword("");
            }
            msg.getResponseMap().put("employees", employees);
            msg.setStatusAndMessage(200, "正常查找");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg login(String name, String password){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        try{
            List<Employee> employees = employeeMapper.getByName(name);
            String pwd = password;
            if(employees.size()==0){
                msg.setStatusAndMessage(204, "没有对应员工");
            }
            else if(employees.size()==1){
                if(pwd.equals(employees.get(0).getPassword())){
                    msg.setStatusAndMessage(200, "成功登陆");
                    // 返回DAO和token
                    msg.getResponseMap().put("employee", employees.get(0));
                    String token = jwtToken.generateToken(Long.valueOf(employees.get(0).getEid()));
                    msg.getResponseMap().put("token", token);
                }
                else{
                    msg.setStatusAndMessage(206, "密码错误");
                }
            }
            else{
                msg.setStatusAndMessage(208, "数据库出现异常");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg getByIdConfidential(int eid){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        try{
            Employee e = employeeMapper.getByEidCascade(eid);
            if(e==null)
                msg.setStatusAndMessage(208, "不存在用户"+eid);
            else{
                msg.setStatusAndMessage(200, "获得用户"+eid);
                msg.getResponseMap().put("employee", e);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg getByIdNonConfidential(int eid){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        try{
            Employee e =employeeMapper.getByEidCascade(eid);
            if(e==null)
                msg.setStatusAndMessage(204, "未获得用户"+eid);
            else{
                e.setPassword("");
                msg.setStatusAndMessage(200, "获得用户"+eid);
                msg.getResponseMap().put("employee", e);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg getDashBoardByIdConfidential(int eid) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        try{
            Employee e =employeeMapper.getByEidCascade(eid);
            if(e==null)
                msg.setStatusAndMessage(208, "未知用户"+eid);
            else{
                msg.setStatusAndMessage(200, "获得用户Dashboard"+eid);
                msg.getResponseMap().put("employee", e);
                msg.getResponseMap().put("properties", propertyMapper.getByEid(eid));
                msg.getResponseMap().put("projects", employeeProjectMapper.getByEidCascade(eid));
                msg.getResponseMap().put("manhours", manhourMapper.getByEidCascade(eid));
                msg.getResponseMap().put("defects", defectMapper.getByEidCascade(eid));
                msg.getResponseMap().put("risks", riskMapper.getByEidCascade(eid));
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
