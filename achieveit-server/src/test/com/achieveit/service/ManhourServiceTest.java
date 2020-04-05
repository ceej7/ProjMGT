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
    public void happy_path_with_get_paged_manhour_by_eid_ret200() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        Project project = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        project.setFunction("{0:\"能用就行\"}");
        employeeProject.setProject(project);
        manhour.setFid(1);
        manhour.setEmployeeProject(employeeProject);
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
    public void exception_when_get_paged_manhour_by_eid_ret404() throws Exception {
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
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        Project project = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        project.setFunction("{0:\"能用就行\"}");
        employeeProject.setProject(project);
        manhour.setFid(1);
        manhour.setEmployeeProject(employeeProject);
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

    //////////////getActivity()//////////////
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

    //////////////newManhour()//////////////
    @Test
    public void happy_path_with_newManhour_ret200() throws Exception {
        Activity activity = new Activity(1, null, null);
        List<Activity> activitys = new ArrayList<>();
        activitys.add(activity);
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(employeeProject);
        Project project = new Project("1", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        project.setFunction("{0:\"做梦去吧\"}");

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);

        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(projectMapper.getByPid(anyString())).thenReturn(project);
        when(manhourMapper.getActivityByAid(anyInt())).thenReturn(activity);
        when(manhourMapper.add(any())).thenReturn(1);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Timestamp startdate=new Timestamp((long)1);
        Timestamp enddate=new Timestamp((long)2);
        msg = manhourService.newManhour(1,"1",1,1,startdate,enddate);

        assertEquals(200, msg.getStatus());
    }

    @Test
    public void alternate_path_with_newManhour_ret212() throws Exception {
        Activity activity = new Activity(1, null, null);
        List<Activity> activitys = new ArrayList<>();
        activitys.add(activity);
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(employeeProject);
        Project project = new Project("1", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        project.setFunction("{0:\"做梦去吧\"}");

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);

        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(null);
        when(projectMapper.getByPid(anyString())).thenReturn(project);
        when(manhourMapper.getActivityByAid(anyInt())).thenReturn(activity);
        when(manhourMapper.add(any())).thenReturn(1);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Timestamp startdate=new Timestamp((long)1);
        Timestamp enddate=new Timestamp((long)2);
        msg = manhourService.newManhour(1,"1",1,1,startdate,enddate);

        assertEquals(212, msg.getStatus());
    }

    @Test
    public void alternate_path_with_newManhour_ret216() throws Exception {
        Activity activity = new Activity(1, null, null);
        List<Activity> activitys = new ArrayList<>();
        activitys.add(activity);
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(employeeProject);
        Project project = new Project("1", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        project.setFunction("{0:\"做梦去吧\"}");

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(sup);

        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(projectMapper.getByPid(anyString())).thenReturn(project);
        when(manhourMapper.getActivityByAid(anyInt())).thenReturn(activity);
        when(manhourMapper.add(any())).thenReturn(1);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Timestamp startdate=new Timestamp((long)1);
        Timestamp enddate=new Timestamp((long)2);
        msg = manhourService.newManhour(1,"1",1,1,startdate,enddate);

        assertEquals(216, msg.getStatus());
    }

    @Test
    public void alternate_path_with_newManhour_ret214() throws Exception {
        Activity activity = new Activity(1, null, null);
        List<Activity> activitys = new ArrayList<>();
        activitys.add(activity);
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(employeeProject);
        Project project = new Project("1", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        project.setFunction("{0:\"做梦去吧\"}");

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);

        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(projectMapper.getByPid(anyString())).thenReturn(project);
        when(manhourMapper.getActivityByAid(anyInt())).thenReturn(null);
        when(manhourMapper.add(any())).thenReturn(1);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Timestamp startdate=new Timestamp((long)1);
        Timestamp enddate=new Timestamp((long)2);
        msg = manhourService.newManhour(1,"1",1,1,startdate,enddate);

        assertEquals(214, msg.getStatus());
    }

    @Test
    public void alternate_path_with_newManhour_ret212_02() throws Exception {
        Activity activity = new Activity(1, null, null);
        List<Activity> activitys = new ArrayList<>();
        activitys.add(activity);
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(employeeProject);
        Project project = new Project("1", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        project.setFunction("{0:\"做梦去吧\"}");

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);

        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(projectMapper.getByPid(anyString())).thenReturn(project);
        when(manhourMapper.getActivityByAid(anyInt())).thenReturn(activity);
        when(manhourMapper.add(any())).thenReturn(0);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Timestamp startdate=new Timestamp((long)1);
        Timestamp enddate=new Timestamp((long)2);
        msg = manhourService.newManhour(1,"1",1,1,startdate,enddate);

        assertEquals(212, msg.getStatus());
    }

    @Test
    public void exception_when_newManhour_ret404() throws Exception {
        Activity activity = new Activity(1, null, null);
        List<Activity> activitys = new ArrayList<>();
        activitys.add(activity);
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(employeeProject);
        Project project = new Project("1", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        project.setFunction("{0:\"做梦去吧\"}");

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);

        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenThrow(new RuntimeException());
        when(projectMapper.getByPid(anyString())).thenReturn(project);
        when(manhourMapper.getActivityByAid(anyInt())).thenReturn(activity);
        when(manhourMapper.add(any())).thenReturn(1);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Timestamp startdate=new Timestamp((long)1);
        Timestamp enddate=new Timestamp((long)2);
        msg = manhourService.newManhour(1,"1",1,1,startdate,enddate);

        assertEquals(404, msg.getStatus());
    }

    //////////////deleteManhour()//////////////
    @Test
    public void happy_path_with_deleteManhour_ret200() throws Exception {
        Activity activity = new Activity(1, null, null);
        List<Activity> activitys = new ArrayList<>();
        activitys.add(activity);
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(employeeProject);
        Project project = new Project("1", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        project.setFunction("{0:\"做梦去吧\"}");

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);

        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(projectMapper.getByPid(anyString())).thenReturn(project);
        when(manhourMapper.getActivityByAid(anyInt())).thenReturn(activity);
        when(manhourMapper.getByMidCascade(anyInt())).thenReturn(manhour);
        when(manhourMapper.deleteManhour(anyInt())).thenReturn(1);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.deleteManhour(1,1);

        assertEquals(200, msg.getStatus());
    }

    @Test
    public void alternate_path_with_deleteManhour_ret210() throws Exception {
        Activity activity = new Activity(1, null, null);
        List<Activity> activitys = new ArrayList<>();
        activitys.add(activity);
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(sup);
        Project project = new Project("1", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        project.setFunction("{0:\"做梦去吧\"}");

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);

        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(projectMapper.getByPid(anyString())).thenReturn(project);
        when(manhourMapper.getActivityByAid(anyInt())).thenReturn(activity);
        when(manhourMapper.getByMidCascade(anyInt())).thenReturn(manhour);
        when(manhourMapper.deleteManhour(anyInt())).thenReturn(1);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.deleteManhour(1,1);

        assertEquals(210, msg.getStatus());
    }

    @Test
    public void alternate_path_with_deleteManhour_ret212() throws Exception {
        Activity activity = new Activity(1, null, null);
        List<Activity> activitys = new ArrayList<>();
        activitys.add(activity);
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(employeeProject);
        Project project = new Project("1", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        project.setFunction("{0:\"做梦去吧\"}");

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);

        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(projectMapper.getByPid(anyString())).thenReturn(project);
        when(manhourMapper.getActivityByAid(anyInt())).thenReturn(activity);
        when(manhourMapper.getByMidCascade(anyInt())).thenReturn(manhour);
        when(manhourMapper.deleteManhour(anyInt())).thenReturn(0);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.deleteManhour(1,1);

        assertEquals(212, msg.getStatus());
    }

    @Test
    public void error_path_with_deleteManhour_ret404() throws Exception {
        Activity activity = new Activity(1, null, null);
        List<Activity> activitys = new ArrayList<>();
        activitys.add(activity);
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(employeeProject);
        Project project = new Project("1", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        project.setFunction("{0:\"做梦去吧\"}");

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);

        when(manhourMapper.getByMidCascade(anyInt())).thenThrow(new RuntimeException());
        when(manhourMapper.deleteManhour(anyInt())).thenReturn(1);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.deleteManhour(1,1);

        assertEquals(404, msg.getStatus());
    }

    //////////////updateManhour()//////////////
    @Test
    public void happy_path_with_updateManhour_ret200() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(employeeProject);

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(sup);

        when(manhourMapper.getByMidCascade(anyInt())).thenReturn(manhour);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(manhourMapper.update(manhour)).thenReturn(1);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Map<String,String> param=new HashMap<String, String>();
        param.put("status","unfilled");
        param.put("fid","0");
        param.put("starttime","2020-04-09T16:00:00.000Z");
        param.put("endtime","2020-05-09T16:00:00.000Z");
        param.put("activity_id","1");

        msg = manhourService.updateManhour(1,1,param);


        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("manhour"));
    }

    @Test
    public void happy_path_with_updateManhour_wo_status_ret200() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(employeeProject);

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);

        when(manhourMapper.getByMidCascade(anyInt())).thenReturn(manhour);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(manhourMapper.update(manhour)).thenReturn(1);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Map<String,String> param=new HashMap<String, String>();
        param.put("fid","0");
        param.put("starttime","2020-04-09T16:00:00.000Z");
        param.put("endtime","2020-05-09T16:00:00.000Z");
        param.put("activity_id","1");

        msg = manhourService.updateManhour(1,1,param);

        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("manhour"));
    }

    @Test
    public void alternate_updateManhour_ret210_01() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 0, "1", 1);
        manhour.setEmployeeProject(employeeProject);
        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();

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
    }

    @Test
    public void alternate_updateManhour_ret210_02() throws Exception {
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
    }

    @Test
    public void alternate_updateManhour_ret210_03() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 0, "1", 1);
        manhour.setEmployeeProject(employeeProject);
        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();

        when(manhourMapper.getByMidCascade(anyInt())).thenReturn(manhour);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Map<String,String> param=new HashMap<String, String>();
        param.put("fid","0");
        param.put("starttime","2020-04-09T16:00:00.000Z");
        param.put("endtime","2020-05-09T16:00:00.000Z");
        param.put("activity_id","1");

        msg = manhourService.updateManhour(1,1,param);

        when(manhourMapper.update(manhour)).thenReturn(1);
        assertEquals(210, msg.getStatus());
    }

    @Test
    public void alternate_updateManhour_ret210_04() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        sup.setEpid(3);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 0, "1", 1);
        manhour.setEmployeeProject(employeeProject);

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(sup);

        when(manhourMapper.getByMidCascade(anyInt())).thenReturn(manhour);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Map<String,String> param=new HashMap<String, String>();
        param.put("fid","0");
        param.put("starttime","2020-04-09T16:00:00.000Z");
        param.put("endtime","2020-05-09T16:00:00.000Z");
        param.put("activity_id","1");

        msg = manhourService.updateManhour(1,1,param);

        when(manhourMapper.update(manhour)).thenReturn(1);
        assertEquals(210, msg.getStatus());
    }

    @Test
    public void alternate_updateManhour_ret216() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(employeeProject);
        manhour.setStatus("fiiled");
        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(sup);

        when(manhourMapper.getByMidCascade(anyInt())).thenReturn(manhour);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(manhourMapper.update(manhour)).thenReturn(1);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Map<String,String> param=new HashMap<String, String>();

        param.put("fid","0");
        param.put("starttime","2020-04-09T16:00:00.000Z");
        param.put("endtime","2020-05-09T16:00:00.000Z");
        param.put("activity_id","1");

        msg = manhourService.updateManhour(1,1,param);

        assertEquals(216, msg.getStatus());
    }

    @Test
    public void alternate_updateManhour_ret212() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(employeeProject);

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(sup);

        when(manhourMapper.getByMidCascade(anyInt())).thenReturn(manhour);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(manhourMapper.update(manhour)).thenReturn(1);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Map<String,String> param=new HashMap<String, String>();
        param.put("status","no");
        param.put("fid","0");
        param.put("starttime","2020-04-09T16:00:00.000Z");
        param.put("endtime","2020-05-09T16:00:00.000Z");
        param.put("activity_id","1");

        msg = manhourService.updateManhour(1,1,param);

        assertEquals(212, msg.getStatus());
    }

    @Test
    public void alternate_updateManhour_ret214() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, "unfilled", 1, 1);
        EmployeeProject employeeProject = new EmployeeProject(1,null, 1, "1", 1);
        EmployeeProject sup = new EmployeeProject(2,null, 2, "2", 2);
        employeeProject.setSup(sup);
        manhour.setEmployeeProject(employeeProject);

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(sup);

        when(manhourMapper.getByMidCascade(anyInt())).thenReturn(manhour);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(manhourMapper.update(manhour)).thenReturn(0);

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        Map<String,String> param=new HashMap<String, String>();
        param.put("status","unfilled");
        param.put("fid","0");
        param.put("starttime","2020-04-09T16:00:00.000Z");
        param.put("endtime","2020-05-09T16:00:00.000Z");
        param.put("activity_id","1");

        msg = manhourService.updateManhour(1,1,param);

        assertEquals(214, msg.getStatus());
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
    public void happy_path_with_getSubManhour() throws Exception {
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
        when(manhourMapper.getSubManhour(anyInt())).thenThrow(new RuntimeException());

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.getSubManhour(1);

        assertEquals(404, msg.getStatus());
    }
}
