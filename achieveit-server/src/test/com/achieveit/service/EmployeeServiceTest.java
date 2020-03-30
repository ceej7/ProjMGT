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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;


import java.io.File;
import java.util.*;

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
        employeeService = new EmployeeService(employeeMapper, defectMapper, manhourMapper, projectMapper, propertyMapper, riskMapper, employeeProjectMapper);
    }

    @Test
    public void happy_path_with_get_by_title() throws Exception {
        String[] titles = {"pm_manager", "configurer", "pm", "epg_leader", "qa_manager", "member"};
        int i = 0;
        Employee employee = new Employee(1, "Alias", null, null, null, null, null, null, null, null);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        while (i < 6) {
            String title = titles[i++];
            when(employeeMapper.getByTitle(title)).thenReturn(employees);
            ResponseMsg msg = new ResponseMsg();
            msg.setStatusAndMessage(404, "请求出现异常");
            msg = employeeService.getByTitle(title);
            assertEquals(200, msg.getStatus());
            assertNotNull(msg.getResponseMap());
            verify(employeeMapper).getByTitle(title);
        }
    }

    @Test
    public void happy_path_with_login() throws Exception {
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        when(employeeMapper.getByName("Alias")).thenReturn(employees);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = employeeService.login("Alias", "123456");
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        Employee e = (Employee) msg.getResponseMap().get("employee");
        assertEquals("Alias", e.getName());
        assertEquals("123456", e.getPassword());
        verify(employeeMapper).getByName("Alias");
    }

    @Test
    public void happy_path_with_get_by_id_confidential() throws Exception {
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);
        when(employeeMapper.getByEidCascade(1)).thenReturn(employee);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = employeeService.getByIdConfidential(1);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        Employee e = (Employee) msg.getResponseMap().get("employee");
        assertEquals("Alias", e.getName());
        verify(employeeMapper).getByEidCascade(1);
    }

    @Test
    public void happy_path_with_get_by_id_non_confidential() throws Exception {
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);
        when(employeeMapper.getByEidCascade(1)).thenReturn(employee);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = employeeService.getByIdConfidential(1);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        Employee e = (Employee) msg.getResponseMap().get("employee");
        assertEquals("Alias", e.getName());
        verify(employeeMapper).getByEidCascade(1);
    }

    @Test
    public void happy_path_with_get_dash_board_by_id_confidential() throws Exception {
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);

        PropertyOccupy propertyOccupy = new PropertyOccupy(1,null, true, null, null, null);
        List<PropertyOccupy> propertyOccupys = new ArrayList<>();
        propertyOccupys.add(propertyOccupy);

        EmployeeProject employeeProject = new EmployeeProject(1,null, null, null, null);
        List<EmployeeProject> employeeProjects = new ArrayList<>();
        employeeProjects.add(employeeProject);

        Manhour manhour = new Manhour(1,null, null,null,null, null, null, null);
        List<Manhour> manhours = new ArrayList<>();
        manhours.add(manhour);

        Defect defect = new Defect(1,null, null,null,null, null, null, null);
        List<Defect> defects = new ArrayList<>();
        defects.add(defect);

        Risk risk = new Risk(1,null,null,null,null, null,null,true, null, null);
        List<Risk> risks = new ArrayList<>();
        risks.add(risk);

        when(employeeMapper.getByEidCascade(1)).thenReturn(employee);
        when(propertyMapper.getByEid(1)).thenReturn(propertyOccupys);
        when(employeeProjectMapper.getByEidCascade(1)).thenReturn(employeeProjects);
        when(manhourMapper.getByEidCascade(1)).thenReturn(manhours);
        when(defectMapper.getByEidCascade(1)).thenReturn(defects);
        when(riskMapper.getByEidCascade(1)).thenReturn(risks);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = employeeService.getDashBoardByIdConfidential(1);

        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("employee"));
        assertNotNull(msg.getResponseMap().get("properties"));
        assertNotNull(msg.getResponseMap().get("projects"));
        assertNotNull(msg.getResponseMap().get("manhours"));
        assertNotNull(msg.getResponseMap().get("defects"));
        assertNotNull(msg.getResponseMap().get("risks"));

        verify(employeeMapper).getByEidCascade(1);
        verify(propertyMapper).getByEid(1);
        verify(employeeProjectMapper).getByEidCascade(1);
        verify(manhourMapper).getByEidCascade(1);
        verify(defectMapper).getByEidCascade(1);
        verify(riskMapper).getByEidCascade(1);
    }
}
