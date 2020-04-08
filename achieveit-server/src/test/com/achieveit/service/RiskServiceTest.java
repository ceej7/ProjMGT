package com.achieveit.service;

import com.achieveit.entity.EmployeeProject;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Risk;
import com.achieveit.mapper.*;
import com.mysql.fabric.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RiskServiceTest {
    ProjectMapper projectMapper;
    WorkflowMapper workflowMapper;
    EmployeeMapper employeeMapper;
    RiskMapper riskMapper;
    EmployeeProjectMapper employeeProjectMapper;
    RiskService riskService;

    @BeforeEach
    void setup(){
        this.projectMapper = mock(ProjectMapper.class);
        this.workflowMapper = mock(WorkflowMapper.class);
        this.employeeMapper = mock(EmployeeMapper.class);
        this.riskMapper = mock(RiskMapper.class);
        this.employeeProjectMapper = mock(EmployeeProjectMapper.class);
        riskService = new RiskService(projectMapper, workflowMapper,employeeMapper,riskMapper,employeeProjectMapper);
    }

//    @Test
//    void happy_path_getByProjectId_ret200() {
//        when(riskMapper.getByPidCascade(any())).thenReturn(new ArrayList<Risk>());
//        ResponseMsg msg= riskService.getByProjectId("");
//        assertEquals(200, msg.getStatus());
//    }
//
//    @Test
//    void error_path_getByProjectId_ret404() {
//        when(riskMapper.getByPidCascade(any())).thenThrow(new RuntimeException());
//        ResponseMsg msg= riskService.getByProjectId("");
//        assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void happy_path_getByTemplate_ret200() {
//        when(riskMapper.getByTemplate()).thenReturn(new ArrayList<Risk>());
//        ResponseMsg msg= riskService.getByTemplate();
//        assertEquals(200, msg.getStatus());
//    }
//
//    @Test
//    void error_path_getByTemplate_ret404() {
//        when(riskMapper.getByTemplate()).thenThrow(new RuntimeException());
//        ResponseMsg msg= riskService.getByTemplate();
//        assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void happy_path_delete_ret200() {
//        when(riskMapper.getByRid(anyInt())).thenReturn(new Risk());
//        when(riskMapper.delete(anyInt())).thenReturn(1);
//        ResponseMsg msg = riskService.delete(0,1);assertEquals(200, msg.getStatus());
//
//        when(riskMapper.getByRid(anyInt())).thenReturn(null);
//        when(riskMapper.delete(anyInt())).thenReturn(1);
//        msg = riskService.delete(0,0);assertEquals(210, msg.getStatus());
//
//        when(riskMapper.getByRid(anyInt())).thenReturn(new Risk());
//        when(riskMapper.delete(anyInt())).thenReturn(1);
//        msg = riskService.delete(1,1);assertEquals(212, msg.getStatus());
//
//        when(riskMapper.getByRid(anyInt())).thenReturn(new Risk());
//        when(riskMapper.delete(anyInt())).thenReturn(0);
//        msg = riskService.delete(0,0);assertEquals(214, msg.getStatus());
//    }
//    @Test
//    void error_path_delete_ret404() {
//        when(riskMapper.getByRid(anyInt())).thenThrow(new RuntimeException());
//        when(riskMapper.delete(anyInt())).thenReturn(1);
//        ResponseMsg msg = riskService.delete(0,1);assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void happy_path_add_ret200() {
//        List<EmployeeProject> employeeProjects = new ArrayList<>();
//        employeeProjects.add(new EmployeeProject());
//        when(employeeProjectMapper.getEmployeeProject(any(), anyInt())).thenReturn(employeeProjects);
//        ArrayList<Integer> ids = new ArrayList<>();
//        ids.add(0);
//        when(riskMapper.add(any())).thenReturn(1);
//        when(employeeProjectMapper.getByEpid(anyInt())).thenReturn(new EmployeeProject());
//        ResponseMsg msg =riskService.add(0,"", "", "", "", "", "", 0, ids);
//        assertEquals(200, msg.getStatus());
//
//        when(riskMapper.add(any())).thenReturn(0);
//        msg =riskService.add(0,"", "", "", "", "", "", 0, ids);
//        assertEquals(212, msg.getStatus());
//
//        when(employeeProjectMapper.getEmployeeProject(any(), anyInt())).thenReturn(null);
//        msg =riskService.add(0,"", "", "", "", "", "", 0, ids);
//        assertEquals(210, msg.getStatus());
//    }
//
//    @Test
//    void error_path_add_ret404() {
//        List<EmployeeProject> employeeProjects = new ArrayList<>();
//        employeeProjects.add(new EmployeeProject());
//        when(employeeProjectMapper.getEmployeeProject(any(), anyInt())).thenThrow(new RuntimeException());
//        ArrayList<Integer> ids = new ArrayList<>();
//        ids.add(0);
//        when(riskMapper.add(any())).thenReturn(1);
//        when(employeeProjectMapper.getByEpid(anyInt())).thenReturn(new EmployeeProject());
//        ResponseMsg msg =riskService.add(0,"", "", "", "", "", "", 0, ids);
//        assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void happy_path_update_ret200() {
//        Map<String,Object> param = new HashMap<>();
//        ArrayList<Integer>ids=new ArrayList<Integer>();
//        ids.add(0);
//        param.put("type", "");
//        param.put("desc", "");
//        param.put("grade", "p1");
//        param.put("influence", "");
//        param.put("strategy", "");
//        param.put("frequency", 1);
//        param.put("ep_ids", ids);
//        when(riskMapper.getByRid(anyInt())).thenReturn(new Risk());
//        when(employeeProjectMapper.getByEpid(anyInt())).thenReturn(new EmployeeProject());
//        ResponseMsg msg =riskService.update(0, 0, param);
//        assertEquals(200, msg.getStatus());
//
//        when(riskMapper.getByRid(anyInt())).thenReturn(null);
//        msg =riskService.update(0, 0, param);
//        assertEquals(210, msg.getStatus());
//
//        when(riskMapper.getByRid(anyInt())).thenReturn(new Risk());
//        when(employeeProjectMapper.getByEpid(anyInt())).thenReturn(new EmployeeProject());
//        msg =riskService.update(1, 0, param);
//        assertEquals(212, msg.getStatus());
//
//        param.put("grade", "p11");
//        when(riskMapper.getByRid(anyInt())).thenReturn(new Risk());
//        when(employeeProjectMapper.getByEpid(anyInt())).thenReturn(new EmployeeProject());
//        msg =riskService.update(0, 0, param);
//        assertEquals(208, msg.getStatus());
//        param.put("grade", "p1");
//    }
//
//    @Test
//    void error_path_update_ret404() {
//        Map<String,Object> param = new HashMap<>();
//        ArrayList<Integer>ids=new ArrayList<Integer>();
//        ids.add(0);
//        param.put("type", "");
//        param.put("desc", "");
//        param.put("grade", "p1");
//        param.put("influence", "");
//        param.put("strategy", "");
//        param.put("frequency", 1);
//        param.put("ep_ids", ids);
//        when(riskMapper.getByRid(anyInt())).thenThrow(new RuntimeException());
//        when(employeeProjectMapper.getByEpid(anyInt())).thenReturn(new EmployeeProject());
//        ResponseMsg msg =riskService.update(0, 0, param);
//        assertEquals(404, msg.getStatus());
//    }
}