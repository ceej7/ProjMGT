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
import java.sql.Date;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;

import java.sql.Timestamp;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;


import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class ManhourServiceTest {
    private static ManhourService manhourService;
    @Mock
    private ManhourMapper manhourMapper;
    private EmployeeProjectMapper employeeProjectMapper;
    private ProjectMapper projectMapper;

    @BeforeEach
    public void init() {
        manhourMapper = mock(ManhourMapper.class);
        employeeProjectMapper=mock(EmployeeProjectMapper.class);
        projectMapper=mock(ProjectMapper.class);
        manhourService = new ManhourService(manhourMapper,employeeProjectMapper,projectMapper);
    }

    //////////////getPagedManhourByEid()//////////////
    @Test
    public void happy_path_with_get_paged_manhour_by_eid() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, null, null, null);
        List<Manhour> manhours = new ArrayList<>();
        manhours.add(manhour);

        when(manhourMapper.getByEidCascade(1)).thenReturn(manhours);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.getPagedManhourByEid(1, 0, 1);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        assertNotNull(msg.getResponseMap().get("Manhour"));
        assertNotNull(msg.getResponseMap().get("page_length"));
        verify(manhourMapper).getByEidCascade(1);
    }

    @Test
    public void exception_when_get_paged_manhour_by_eid() throws Exception {
        when(manhourMapper.getByEidCascade(1)).thenThrow(new RuntimeException());
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.getPagedManhourByEid(1, 0, 1);
        assertEquals(404, msg.getStatus());
        verify(manhourMapper).getByEidCascade(1);
    }

    //////////////getFilteredPagedManhourByEid()//////////////
    @Test
    public void happy_path_with_get_filtered_paged_manhour_by_eid() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, null, null, null);
        List<Manhour> manhours = new ArrayList<>();
        manhours.add(manhour);

        Date date=new Date(2020-3-29);
        when(manhourMapper.getDatedByEidCascade(1,date)).thenReturn(manhours);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.getFilteredPagedManhourByEid(1, 0, 1,date);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        assertNotNull(msg.getResponseMap().get("Manhour"));
        assertNotNull(msg.getResponseMap().get("page_length"));
        verify(manhourMapper).getDatedByEidCascade(1,date);
    }

    @Test
    public void exception_when_get_filtered_paged_manhour_by_eid() throws Exception {
        Date date=new Date(2020-3-29);
        when(manhourMapper.getDatedByEidCascade(1,date)).thenThrow(new RuntimeException());
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.getFilteredPagedManhourByEid(1, 0, 1,date);
        assertEquals(404, msg.getStatus());
        verify(manhourMapper).getDatedByEidCascade(1,date);
    }

    //////////////getFilteredPagedManhourByEid()//////////////
    @Test
    public void happy_path_with_get_activity() throws Exception {
        Activity activity = new Activity(1, null, null);
        List<Activity> activitys = new ArrayList<>();
        activitys.add(activity);

        when(manhourMapper.getActivity()).thenReturn(activitys);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.getActivity();
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        assertNotNull(msg.getResponseMap().get("activities"));
        verify(manhourMapper).getActivity();
    }

    @Test
    public void exception_when_get_activity() throws Exception {
        when(manhourMapper.getActivity()).thenThrow(new RuntimeException());
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.getActivity();
        assertEquals(404, msg.getStatus());
        verify(manhourMapper).getActivity();
    }

    //////////////updateManhour()//////////////
    @Test
    public void happy_path_with_updateManhour_ret200() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 0, "1", 1);
        manhour.setEmployeeProject(employeeProject);

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);

        when(manhourMapper.getByMidCascade(anyInt())).thenReturn(manhour);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Map<String,String> param=new HashMap<String, String>();
        param.put("status","unfilled");
        param.put("fid","0");
        param.put("starttime","2020-04-09T16:00:00.000Z");
        param.put("endtime","2020-05-09T16:00:00.000Z");
        param.put("activity_id","1");

        msg = manhourService.updateManhour(1,1,param);

        when(manhourMapper.update(manhour)).thenReturn(1);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Manhour"));
    }

    @Test
    public void happy_path_with_updateManhour_wo_status() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", null, null);
        EmployeeProject employeeProject = new EmployeeProject(1,null, null, null, null);
        manhour.setEmployeeProject(employeeProject);
        List<Manhour> manhours = new ArrayList<>();
        manhours.add(manhour);
        when(manhourMapper.getByMidCascade(anyInt())).thenReturn(manhour);
        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Map<String,String> param=new HashMap<String, String>();
        param.put("status","unfilled");
        param.put("fid","0");
        param.put("starttime","2020-04-09T16:00:00.000Z");
        param.put("endtime","2020-05-09T16:00:00.000Z");
        param.put("activity_id","1");

        msg = manhourService.updateManhour(1,1,param);

        when(manhourMapper.update(manhour)).thenReturn(1);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Manhour"));
    }

    @Test
    public void no_authorities_when_updateManhour_wo_status_ret210() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", null, null);
        EmployeeProject employeeProject = new EmployeeProject(1,null, null, null, null);
        manhour.setEmployeeProject(employeeProject);
        List<Manhour> manhours = new ArrayList<>();
        manhours.add(manhour);
        when(manhourMapper.getByMidCascade(anyInt())).thenReturn(manhour);
        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.updateManhour(anyInt(), anyInt(), anyMap());

        assertEquals(210, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Manhour"));
    }

    @Test
    public void alternate_updateManhour_ret210() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 0, "1", 1);
        manhour.setEmployeeProject(employeeProject);

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);

        when(manhourMapper.getByMidCascade(anyInt())).thenReturn(manhour);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Map<String,String> param=new HashMap<String, String>();
        param.put("status","unfilled");
        param.put("fid","0");
        param.put("starttime","2020-04-09T16:00:00.000Z");
        param.put("endtime","2020-05-09T16:00:00.000Z");
        param.put("activity_id","1");

        msg = manhourService.updateManhour(1,1,param);

        when(manhourMapper.update(manhour)).thenReturn(1);
        assertEquals(210, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Manhour"));
    }

    @Test
    public void exception_when_updateManhour() throws Exception {
        when(manhourMapper.getByMidCascade(anyInt())).thenThrow(new RuntimeException());

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.updateManhour(anyInt(), anyInt(), anyMap());
        assertEquals(404, msg.getStatus());
        assertNull(msg.getResponseMap().get("Manhour"));
    }

    //////////////updateManhour()//////////////
    @Test
    public void no_authorities_with_getSubManhour() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", null, null);
        List<Manhour> manhours = new ArrayList<>();
        manhours.add(manhour);
        when(manhourMapper.getSubManhour(anyInt())).thenReturn(manhours);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.getSubManhour(1);

        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("activities"));
    }

    @Test
    public void exception_when_getSubManhour() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", null, null);
        List<Manhour> manhours = new ArrayList<>();
        manhours.add(manhour);
        when(manhourMapper.getSubManhour(anyInt())).thenReturn(manhours);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.getSubManhour(1);

        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("activities"));
    }
}
