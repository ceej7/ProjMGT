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

public class DefectServiceTest {
    private static DefectService defectService;
    @Mock
    private DefectMapper defectMapper;
    @Mock
    private EmployeeProjectMapper employeeProjectMapper;


//    @BeforeEach
//    public void init() {
//        defectMapper = mock(DefectMapper.class);
//        employeeProjectMapper = mock(EmployeeProjectMapper.class);
//        defectService = new DefectService(defectMapper,employeeProjectMapper);
//    }
//
//    //////////////getPagedDefectByEid()//////////////
//    @Test
//    public void happy_path_with_get_paged_defect_by_eid() throws Exception {
//        Defect defect = new Defect(1, null, null, null, null, null, null, null);
//        List<Defect> defects = new ArrayList<>();
//        defects.add(defect);
//
//        when(defectMapper.getByEidCascade(1)).thenReturn(defects);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = defectService.getPagedDefectByEid(1, 0, 1);
//        assertEquals(200, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        assertNotNull(msg.getResponseMap().get("Defect"));
//        assertNotNull(msg.getResponseMap().get("page_length"));
//        verify(defectMapper).getByEidCascade(1);
//    }
//
//    @Test
//    public void exception_when_get_paged_defect_by_eid() throws Exception {
//        when(defectMapper.getByEidCascade(1)).thenThrow(new RuntimeException());
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = defectService.getPagedDefectByEid(1, 0, 1);
//        assertEquals(404, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        verify(defectMapper).getByEidCascade(1);
//    }
//
//
//    //////////////getFilteredPagedDefectByEid()//////////////
//    @Test
//    public void happy_path_with_get_filtered_paged_defect_by_eid() throws Exception {
//        Defect defect = new Defect(1, null, null, null, null, "bug", null, null);
//        List<Defect> defects = new ArrayList<>();
//        defects.add(defect);
//
//        when(defectMapper.getByEidCascade(anyInt())).thenReturn(defects);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = defectService.getFilteredPagedDefectByEid(1, 0, 1,null,"bug");
//        assertEquals(200, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        assertNotNull(msg.getResponseMap().get("Defect"));
//        assertNotNull(msg.getResponseMap().get("page_length"));
//        verify(defectMapper).getByEidCascade(anyInt());
//    }
//
//    @Test
//    public void alternate_path_with_get_filtered_paged_defect_by_eid() throws Exception {
//        Defect defect = new Defect(1, null, null, null, null, "bug", null, null);
//        List<Defect> defects = new ArrayList<>();
//        defects.add(defect);
//
//        when(defectMapper.getByEidCascade(anyInt())).thenReturn(defects);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = defectService.getFilteredPagedDefectByEid(1, 0, 1,null,null);
//        assertEquals(200, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        assertNotNull(msg.getResponseMap().get("Defect"));
//        assertNotNull(msg.getResponseMap().get("page_length"));
//        verify(defectMapper).getByEidCascade(anyInt());
//    }
//
//    @Test
//    public void exception_when_get_filtered_paged_defect_by_eid() throws Exception {
//        when(defectMapper.getDescedByEidCascade(1,"filter")).thenThrow(new RuntimeException());
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = defectService.getFilteredPagedDefectByEid(1, 0, 1,"filter","bug");
//        assertEquals(404, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        verify(defectMapper).getDescedByEidCascade(1,"filter");
//    }
//
//    //////////////getByPid()//////////////
//    @Test
//    public void happy_path_with_get_by_pid() throws Exception {
//        Defect defect = new Defect(1, null, null, null, null, null, null, null);
//        List<Defect> defects = new ArrayList<>();
//        defects.add(defect);
//        when(defectMapper.getByPidCascade(anyString())).thenReturn(defects);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = defectService.getByPid("1");
//        assertEquals(200, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        assertNotNull(msg.getResponseMap().get("Defect"));
//        verify(defectMapper).getByPidCascade(anyString());
//    }
//
//    @Test
//    void exception_when_get_by_pid() throws Exception {
//        when(defectMapper.getByPidCascade(anyString())).thenThrow(new RuntimeException());
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = defectService.getByPid("1");
//        assertEquals(404, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        verify(defectMapper).getByPidCascade(anyString());
//    }
//
//    //////////////deleteDefect()//////////////
//    @Test
//    void happy_path_with_deleteDefect_ret200()throws Exception{
//        Defect defect = new Defect();
//        EmployeeProject employeeProject = new EmployeeProject();
//        ArrayList<EmployeeProject>  employeeProjects = new ArrayList<EmployeeProject>();
//        employeeProjects.add(employeeProject);
//        when(defectMapper.getByDid(anyInt())).thenReturn(defect);
//        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
//        ResponseMsg msg=defectService.deleteDefect(1,1);
//        assertEquals(200, msg.getStatus());
//
//        byte[] a={3};
//        employeeProject.setDefect_authority(a);
//        msg=defectService.deleteDefect(1,1);
//        assertEquals(200, msg.getStatus());
//
//        byte[] b={2};
//        employeeProject.setDefect_authority(b);
//        msg=defectService.deleteDefect(1,1);
//        assertEquals(200, msg.getStatus());
//
//        byte[] c={1};
//        employeeProject.setDefect_authority(c);
//        msg=defectService.deleteDefect(1,1);
//        assertEquals(200, msg.getStatus());
//
//    }
//
//    @Test
//    void alternate_path_with_deleteDefect_ret214()throws Exception{
//        Defect defect = new Defect();
//        EmployeeProject employeeProject = new EmployeeProject();
//        ArrayList<EmployeeProject>  employeeProjects = new ArrayList<EmployeeProject>();
//        employeeProjects.add(employeeProject);
//        when(defectMapper.getByDid(anyInt())).thenReturn(defect);
//        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
//        ResponseMsg msg=defectService.deleteDefect(1,1);
//        assertEquals(200, msg.getStatus());
//
//        defect.setEmployee_project_id(1);
//        employeeProject.setEpid(2);
//        msg=defectService.deleteDefect(1,1);
//        assertEquals(214, msg.getStatus());
//
//    }
//
//    @Test
//    void alternate_path_with_deleteDefect_ret212()throws Exception{
//        Defect defect = new Defect();
//        EmployeeProject employeeProject = new EmployeeProject();
//        ArrayList<EmployeeProject>  employeeProjects = new ArrayList<EmployeeProject>();
//        when(defectMapper.getByDid(anyInt())).thenReturn(defect);
//        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
//        ResponseMsg msg=defectService.deleteDefect(1,1);
//        assertEquals(212, msg.getStatus());
//    }
//
//    @Test
//    void altenate_path_with_deleteDefect_ret216()throws Exception{
//        Defect defect = new Defect();
//        when(defectMapper.getByDid(anyInt())).thenReturn(null);
//        ResponseMsg msg=defectService.deleteDefect(1,1);
//        assertEquals(216, msg.getStatus());
//    }
//
//    @Test
//    void error_path_with_deleteDefect_ret404()throws Exception{
//        Defect defect = new Defect();
//        when(defectMapper.getByDid(anyInt())).thenThrow(new RuntimeException());
//        ResponseMsg msg=defectService.deleteDefect(1,1);
//        assertEquals(404, msg.getStatus());
//    }
//
//    //////////////addDefect()//////////////
//    @Test
//    void happy_path_with_addDefect_ret200()throws Exception{
//        Defect defect = new Defect();
//        EmployeeProject employeeProject = new EmployeeProject();
//        ArrayList<EmployeeProject>  employeeProjects = new ArrayList<EmployeeProject>();
//        employeeProjects.add(employeeProject);
//        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
//        when(defectMapper.add(any())).thenReturn(1);
//        int i=0;
//        while(i<4) {
//            String authoritys[]={"pmAuthority","qaLeaderAuthority","rdLeaderAuthority","else"};
//            String authority=authoritys[i++];
//            ResponseMsg msg = defectService.addDefect(1, "1", authority, "hah", null, null);
//            assertEquals(200, msg.getStatus());
//        }
//    }
//
//    @Test
//    void alternate_path_with_addDefect_ret212()throws Exception{
//        Defect defect = new Defect();
//        EmployeeProject employeeProject = new EmployeeProject();
//        ArrayList<EmployeeProject>  employeeProjects = new ArrayList<EmployeeProject>();
//        employeeProjects.add(employeeProject);
//        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(null);
//        when(defectMapper.add(any())).thenReturn(1);
//        ResponseMsg msg=defectService.addDefect(1,"1","pmAuthority","hah",null,null);
//        assertEquals(212, msg.getStatus());
//    }
//
//    @Test
//    void error_path_with_addDefect_ret404()throws Exception{
//        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenThrow(new RuntimeException());
//        ResponseMsg msg=defectService.addDefect(1,"1","pmAuthority","hah",null,null);
//        assertEquals(404, msg.getStatus());
//    }
//
//    //////////////updateDefect()//////////////
//    @Test
//    void happy_path_with_updateDefect_ret200()throws Exception{
//        Defect defect = new Defect();
//        EmployeeProject employeeProject = new EmployeeProject();
//        ArrayList<EmployeeProject>  employeeProjects = new ArrayList<EmployeeProject>();
//        employeeProjects.add(employeeProject);
//        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
//        when(defectMapper.getByDid(anyInt())).thenReturn(defect);
//        when(defectMapper.update(any())).thenReturn(1);
//
//        Map<String,String> param=new HashMap<String, String>();
//        param.put("authority_desc","pmAuthority");
//        param.put("desc","haha");
//        param.put("git_repo","git");
//        param.put("commit","commit");
//        param.put("status","bug");
//        ResponseMsg msg=defectService.updateDefect(1,1,param);
//        assertEquals(200, msg.getStatus());
//
//        Map<String,String> param2=new HashMap<String, String>();
//        param2.put("authority_desc","qaLeaderAuthority");
//        param2.put("desc","haha");
//        param2.put("git_repo","git");
//        param2.put("commit","commit");
//        param2.put("status","bug");
//        byte[] a={3};
//        employeeProject.setDefect_authority(a);
//        msg=defectService.updateDefect(1,1,param2);
//        assertEquals(200, msg.getStatus());
//
//        Map<String,String> param3=new HashMap<String, String>();
//        param3.put("authority_desc","rdLeaderAuthority");
//        param3.put("desc","haha");
//        param3.put("git_repo","git");
//        param3.put("commit","commit");
//        param3.put("status","bug");
//        byte[] b={2};
//        employeeProject.setDefect_authority(b);
//        msg=defectService.updateDefect(1,1,param3);
//        assertEquals(200, msg.getStatus());
//
//        Map<String,String> param4=new HashMap<String, String>();
//        param4.put("authority_desc","null");
//        param4.put("desc","haha");
//        param4.put("git_repo","git");
//        param4.put("commit","commit");
//        param4.put("status","bug");
//        byte[] c={1};
//        employeeProject.setDefect_authority(c);
//        msg=defectService.updateDefect(1,1,param4);
//        assertEquals(200, msg.getStatus());
//    }
//
//    @Test
//    void alternate_path_with_updateDefect_ret216()throws Exception{
//        Defect defect = new Defect();
//        EmployeeProject employeeProject = new EmployeeProject();
//        ArrayList<EmployeeProject>  employeeProjects = new ArrayList<EmployeeProject>();
//        employeeProjects.add(employeeProject);
//        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(null);
//        when(defectMapper.getByDid(anyInt())).thenReturn(null);
//        when(defectMapper.update(any())).thenReturn(1);
//
//        Map<String,String> param=new HashMap<String, String>();
//        param.put("authority_desc","pmAuthority");
//        param.put("desc","haha");
//        param.put("git_repo","git");
//        param.put("commit","commit");
//        param.put("status","bug");
//
//        ResponseMsg msg=defectService.updateDefect(1,1,param);
//        assertEquals(216, msg.getStatus());
//    }
//
//    @Test
//    void alternate_path_with_updateDefect_ret212()throws Exception{
//        Defect defect = new Defect();
//        EmployeeProject employeeProject = new EmployeeProject();
//        ArrayList<EmployeeProject>  employeeProjects = new ArrayList<EmployeeProject>();
//        employeeProjects.add(employeeProject);
//        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(null);
//        when(defectMapper.getByDid(anyInt())).thenReturn(defect);
//        when(defectMapper.update(any())).thenReturn(1);
//
//        Map<String,String> param=new HashMap<String, String>();
//        param.put("authority_desc","pmAuthority");
//        param.put("desc","haha");
//        param.put("git_repo","git");
//        param.put("commit","commit");
//        param.put("status","bug");
//
//        ResponseMsg msg=defectService.updateDefect(1,1,param);
//        assertEquals(212, msg.getStatus());
//    }
//
//    @Test
//    void alternate_path_with_updateDefect_ret218()throws Exception{
//        Defect defect = new Defect();
//        EmployeeProject employeeProject = new EmployeeProject();
//        ArrayList<EmployeeProject>  employeeProjects = new ArrayList<EmployeeProject>();
//        employeeProjects.add(employeeProject);
//        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
//        when(defectMapper.getByDid(anyInt())).thenReturn(defect);
//        when(defectMapper.update(any())).thenReturn(1);
//
//        Map<String,String> param=new HashMap<String, String>();
//        param.put("authority_desc","pmAuthority");
//        param.put("desc","haha");
//        param.put("git_repo","git");
//        param.put("commit","commit");
//        param.put("status","no");
//
//        ResponseMsg msg=defectService.updateDefect(1,1,param);
//        assertEquals(218, msg.getStatus());
//}
//
//    @Test
//    void alternate_path_with_updateDefect_ret214_01()throws Exception{
//        Defect defect = new Defect();
//        EmployeeProject employeeProject = new EmployeeProject();
//        ArrayList<EmployeeProject>  employeeProjects = new ArrayList<EmployeeProject>();
//        employeeProjects.add(employeeProject);
//        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
//        when(defectMapper.getByDid(anyInt())).thenReturn(defect);
//        when(defectMapper.update(any())).thenReturn(0);
//
//        Map<String,String> param=new HashMap<String, String>();
//        param.put("authority_desc","pmAuthority");
//        param.put("desc","haha");
//        param.put("git_repo","git");
//        param.put("commit","commit");
//        param.put("status","bug");
//
//        ResponseMsg msg=defectService.updateDefect(1,1,param);
//        assertEquals(214, msg.getStatus());
//    }
//
//    @Test
//    void alternate_path_with_updateDefect_ret214_02()throws Exception{
//        Defect defect = new Defect();
//        byte[] a={1};
//        byte[] b={2};
//        defect.setAuthority(a);
//        EmployeeProject employeeProject = new EmployeeProject();
//        ArrayList<EmployeeProject>  employeeProjects = new ArrayList<EmployeeProject>();
//        employeeProject.setDefect_authority(b);
//        employeeProjects.add(employeeProject);
//        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
//        when(defectMapper.getByDid(anyInt())).thenReturn(defect);
//        when(defectMapper.update(any())).thenReturn(0);
//
//        Map<String,String> param=new HashMap<String, String>();
//        param.put("authority_desc","pmAuthority");
//        param.put("desc","haha");
//        param.put("git_repo","git");
//        param.put("commit","commit");
//        param.put("status","bug");
//
//        defect.setEmployee_project_id(1);
//        employeeProject.setEpid(2);
//
//        ResponseMsg msg=defectService.updateDefect(1,1,param);
//        assertEquals(214, msg.getStatus());
//    }
//
//    @Test
//    void error_path_with_updateDefect_ret404()throws Exception{
//        Defect defect = new Defect();
//        byte[] a={1};
//        byte[] b={2};
//        defect.setAuthority(a);
//        EmployeeProject employeeProject = new EmployeeProject();
//        ArrayList<EmployeeProject>  employeeProjects = new ArrayList<EmployeeProject>();
//        employeeProject.setDefect_authority(b);
//        employeeProjects.add(employeeProject);
//        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenThrow(new RuntimeException());
//        when(defectMapper.getByDid(anyInt())).thenReturn(defect);
//        when(defectMapper.update(any())).thenReturn(0);
//
//        Map<String,String> param=new HashMap<String, String>();
//        param.put("authority_desc","pmAuthority");
//        param.put("desc","haha");
//        param.put("git_repo","git");
//        param.put("commit","commit");
//        param.put("status","bug");
//
//        ResponseMsg msg=defectService.updateDefect(1,1,param);
//        assertEquals(404, msg.getStatus());
//    }

}
