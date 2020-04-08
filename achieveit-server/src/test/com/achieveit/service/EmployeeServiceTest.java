package com.achieveit.service;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.*;
import com.achieveit.mapper.*;
import com.achieveit.service.EmployeeService;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import com.achieveit.service.WorkflowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Assert;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;


import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {
    private static EmployeeService employeeService;
    @Mock
    private EmployeeMapper employeeMapper;
    private DefectMapper defectMapper;
    private ManhourMapper manhourMapper;
    private ProjectMapper projectMapper;
    private PropertyMapper propertyMapper;
    private RiskMapper riskMapper;
    JwtToken jwtToken;
    private EmployeeProjectMapper employeeProjectMapper;

    @BeforeEach
    public void init() {
        employeeMapper = mock(EmployeeMapper.class);
        defectMapper = mock(DefectMapper.class);
        manhourMapper = mock(ManhourMapper.class);
        projectMapper = mock(ProjectMapper.class);
        propertyMapper = mock(PropertyMapper.class);
        riskMapper = mock(RiskMapper.class);
        employeeProjectMapper = mock(EmployeeProjectMapper.class);
        jwtToken = new JwtToken();
        employeeService = new EmployeeService(employeeMapper, defectMapper, manhourMapper, projectMapper, propertyMapper, riskMapper, employeeProjectMapper,jwtToken);
    }

//    //////////////getByTitle()//////////////
//    @Test
//    public void happy_path_with_get_by_title() throws Exception {
//        String[] titles = {"pm_manager", "configurer", "pm", "epg_leader", "qa_manager", "member"};
//        int i = 0;
//        Employee employee = new Employee(1, "Alias", null, null, null, null, null, null, null, null);
//        List<Employee> employees = new ArrayList<>();
//        employees.add(employee);
//        while (i < 6) {
//            String title = titles[i++];
//            when(employeeMapper.getByTitle(title)).thenReturn(employees);
//            ResponseMsg msg = new ResponseMsg();
//            msg.setStatusAndMessage(404, "请求出现异常");
//            msg = employeeService.getByTitle(title);
//            assertEquals(200, msg.getStatus());
//            assertNotNull(msg.getResponseMap());
//            verify(employeeMapper).getByTitle(title);
//        }
//    }
//
//    @Test
//    public void exception_when_get_by_title()
//    {
//        String[] titles = {"pm_manager", "configurer", "pm", "epg_leader", "qa_manager", "member"};
//        int i = 0;
//        while (i < 6) {
//            String title = titles[i++];
//            when(employeeMapper.getByTitle(title)).thenThrow(new RuntimeException());
//            ResponseMsg msg = new ResponseMsg();
//            msg.setStatusAndMessage(404, "请求出现异常");
//            msg = employeeService.getByTitle(title);
//            assertEquals(404, msg.getStatus());
//            verify(employeeMapper).getByTitle(title);
//        }
//    }
//
//    //////////////login()//////////////
//    @Test
//    public void happy_path_with_login() throws Exception {
//        Employee employee = new Employee(1, "Alias", "", "", "", "", "123456", "", "", 0);
//        List<Employee> employees = new ArrayList<>();
//        employees.add(employee);
//        when(employeeMapper.getByName("Alias")).thenReturn(employees);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = employeeService.login("Alias", "123456");
//        assertEquals(200, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        Employee e = (Employee) msg.getResponseMap().get("employee");
//        assertEquals("Alias", e.getName());
//        assertEquals("123456", e.getPassword());
//        verify(employeeMapper).getByName("Alias");
//    }
//
//    @Test
//    public void exception_when_login() throws Exception {
//        when(employeeMapper.getByName("Alias")).thenThrow(new RuntimeException());
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = employeeService.login("Alias", "123456");
//        assertEquals(404, msg.getStatus());
//        verify(employeeMapper).getByName("Alias");
//    }
//
//    @Test
//    public void no_such_employee_when_login() throws Exception {
//        List<Employee> employees = new ArrayList<>();
//        when(employeeMapper.getByName("Alias")).thenReturn(employees);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = employeeService.login("Alias", "123456");
//        assertEquals(204, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        verify(employeeMapper).getByName("Alias");
//    }
//
//    @Test
//    public void wrong_password_when_login() throws Exception {
//        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);
//        List<Employee> employees = new ArrayList<>();
//        employees.add(employee);
//        when(employeeMapper.getByName("Alias")).thenReturn(employees);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = employeeService.login("Alias", "654321");
//        assertEquals(206, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        Employee e = (Employee) msg.getResponseMap().get("employee");
//        assertNull(e);
//        verify(employeeMapper).getByName("Alias");
//    }
//
//    @Test
//    public void multiple_employees_when_login() throws Exception {
//        Employee employee1 = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);
//        Employee employee2 = new Employee(2, "Alias", null, null, null, null, "654321", null, null, null);
//        List<Employee> employees = new ArrayList<>();
//        employees.add(employee1);
//        employees.add(employee2);
//        when(employeeMapper.getByName("Alias")).thenReturn(employees);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = employeeService.login("Alias", "123456");
//        assertEquals(208, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        Employee e = (Employee) msg.getResponseMap().get("employee");
//        assertNull(e);
//        verify(employeeMapper).getByName("Alias");
//    }
//
//    //////////////getByIdConfidential()//////////////
//    @Test
//    public void happy_path_with_get_by_id_confidential() throws Exception {
//        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);
//        when(employeeMapper.getByEidCascade(1)).thenReturn(employee);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = employeeService.getByIdConfidential(1);
//        assertEquals(200, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        Employee e = (Employee) msg.getResponseMap().get("employee");
//        assertEquals("Alias", e.getName());
//        verify(employeeMapper).getByEidCascade(1);
//    }
//
//    @Test
//    public void exception_when_get_by_id_confidential() throws Exception {
//        when(employeeMapper.getByEidCascade(1)).thenThrow(new RuntimeException());
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = employeeService.getByIdConfidential(1);
//        assertEquals(404, msg.getStatus());
//        verify(employeeMapper).getByEidCascade(1);
//    }
//
//    @Test
//    public void no_such_employee_when_get_by_id_confidential() throws Exception {
//        when(employeeMapper.getByEidCascade(1)).thenReturn(null);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = employeeService.getByIdConfidential(1);
//        assertEquals(208, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        Employee e = (Employee) msg.getResponseMap().get("employee");
//        assertNull(e);
//        verify(employeeMapper).getByEidCascade(1);
//    }
//
//    //////////////getByIdNonConfidential()//////////////
//    @Test
//    public void happy_path_with_get_by_id_non_confidential() throws Exception {
//        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);
//        when(employeeMapper.getByEidCascade(1)).thenReturn(employee);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = employeeService.getByIdNonConfidential(1);
//        assertEquals(200, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        Employee e = (Employee) msg.getResponseMap().get("employee");
//        assertEquals("Alias", e.getName());
//        verify(employeeMapper).getByEidCascade(1);
//    }
//
//    @Test
//    public void exception_when_get_by_id_non_confidential() throws Exception {
//        when(employeeMapper.getByEidCascade(1)).thenThrow(new RuntimeException());
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = employeeService.getByIdNonConfidential(1);
//        assertEquals(404, msg.getStatus());
//        verify(employeeMapper).getByEidCascade(1);
//    }
//
//    @Test
//    public void no_such_employee_when_get_by_id_non_confidential() throws Exception {
//        when(employeeMapper.getByEidCascade(1)).thenReturn(null);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = employeeService.getByIdNonConfidential(1);
//        assertEquals(210, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        Employee e = (Employee) msg.getResponseMap().get("employee");
//        assertNull(e);
//        verify(employeeMapper).getByEidCascade(1);
//    }
//
//    //////////////getDashBoardByIdConfidential()//////////////
//    @Test
//    public void happy_path_with_get_dash_board_by_id_confidential() throws Exception {
//        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);
//
//        PropertyOccupy propertyOccupy = new PropertyOccupy(1, new Timestamp((long)1), true, null, null, null);
//        List<PropertyOccupy> propertyOccupys = new ArrayList<>();
//        propertyOccupys.add(propertyOccupy);
//
//        EmployeeProject employeeProject = new EmployeeProject(1,null, null, null, null);
//        List<EmployeeProject> employeeProjects = new ArrayList<>();
//        employeeProjects.add(employeeProject);
//
//        Manhour manhour = new Manhour(1,null, null,null,null, null, null, null);
//        List<Manhour> manhours = new ArrayList<>();
//        manhours.add(manhour);
//
//        Defect defect = new Defect(1,null, null,null,null, null, null, null);
//        List<Defect> defects = new ArrayList<>();
//        defects.add(defect);
//
//        Risk risk = new Risk(1,null,null,null,null, null,null,true, null, null);
//        List<Risk> risks = new ArrayList<>();
//        risks.add(risk);
//
//        when(employeeMapper.getByEidCascade(1)).thenReturn(employee);
//        when(propertyMapper.getPropertyOccupyByEid(1)).thenReturn(propertyOccupys);
//        when(employeeProjectMapper.getByEidCascade(1)).thenReturn(employeeProjects);
//        when(manhourMapper.getByEidCascade(1)).thenReturn(manhours);
//        when(defectMapper.getByEidCascade(1)).thenReturn(defects);
//        when(riskMapper.getByEidCascade(1)).thenReturn(risks);
//
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = employeeService.getDashBoardByIdConfidential(1);
//
//        assertEquals(200, msg.getStatus());
//        assertNotNull(msg.getResponseMap().get("employee"));
//        assertNotNull(msg.getResponseMap().get("properties"));
//        assertNotNull(msg.getResponseMap().get("projects"));
//        assertNotNull(msg.getResponseMap().get("manhours"));
//        assertNotNull(msg.getResponseMap().get("defects"));
//        assertNotNull(msg.getResponseMap().get("risks"));
//
//        verify(employeeMapper).getByEidCascade(1);
//        verify(propertyMapper).getPropertyOccupyByEid(1);
//        verify(employeeProjectMapper).getByEidCascade(1);
//        verify(manhourMapper).getByEidCascade(1);
//        verify(defectMapper).getByEidCascade(1);
//        verify(riskMapper).getByEidCascade(1);
//    }
//
//    @Test
//    public void exception_when_get_dash_board_by_id_confidential() throws Exception {
//        when(employeeMapper.getByEidCascade(1)).thenThrow(new RuntimeException());
//                ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = employeeService.getDashBoardByIdConfidential(1);
//        assertEquals(404, msg.getStatus());
//        verify(employeeMapper).getByEidCascade(1);
//    }
//
//    @Test
//    public void no_such_employee_when_get_dash_board_by_id_confidential() throws Exception {
//        when(employeeMapper.getByEidCascade(1)).thenReturn(null);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = employeeService.getDashBoardByIdConfidential(1);
//
//        assertEquals(208, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        verify(employeeMapper).getByEidCascade(1);
//    }

}


