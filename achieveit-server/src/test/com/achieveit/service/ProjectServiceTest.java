package com.achieveit.service;

import com.achieveit.entity.EmployeeProject;
import com.achieveit.entity.Project;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.mapper.EmployeeMapper;
import com.achieveit.mapper.EmployeeProjectMapper;
import com.achieveit.mapper.ProjectMapper;
import com.achieveit.mapper.WorkflowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   ProjectServiceTest {
    ProjectMapper projectMapper;
    WorkflowMapper workflowMapper;
    EmployeeMapper employeeMapper;
    EmployeeProjectMapper employeeProjectMapper;
    ProjectService projectService;


    @BeforeEach
    void setup(){
        projectMapper = mock(ProjectMapper.class);
        workflowMapper = mock(WorkflowMapper.class);
        employeeMapper = mock(EmployeeMapper.class);
        employeeProjectMapper = mock(EmployeeProjectMapper.class);
        projectService = new ProjectService(projectMapper, workflowMapper, employeeMapper,employeeProjectMapper);
    }

    @Test
    void happy_path_getById() {
        Project w = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        when(projectMapper.getByPidCascade("20200001O01")).thenReturn(w);
        ResponseMsg msg = projectService.getById("20200001O01");
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Project"));
        assertEquals("20200001O01", ((Project)msg.getResponseMap().get("Project")).getPid());
        verify(projectMapper).getByPidCascade("20200001O01");
    }

    @Test
    void happy_path_getProjectToCheck() {

    }

    @Test
    void happy_path_getPagedProjectByEid() {
        Project w = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        List<Project> pws = new ArrayList<Project>();
        for (int i = 0; i < 100; i++) {
            pws.add(w);
        }
        when(projectMapper.getByEidCascade(1)).thenReturn(pws);
        ResponseMsg msg = projectService.getPagedProjectByEid(1,2,10);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Project"));
        assertEquals(10, ((List<Project>)msg.getResponseMap().get("Project")).size());
        assertEquals(10, (int)msg.getResponseMap().get("page_length"));
        verify(projectMapper).getByEidCascade(1);
    }

    @Test
    void happy_path_getFilteredPagedProjectByEid() {
        Project w = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        List<Project> pws = new ArrayList<Project>();
        for (int i = 0; i < 100; i++) {
            pws.add(w);
        }
        when(projectMapper.getNamedStatusByEidCascade(1,"",256,2047)).thenReturn(pws);
        ResponseMsg msg = projectService.getFilteredPagedProjectByEid(1,2,10,"","doing");
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Project"));
        assertEquals(10, ((List<Project>)msg.getResponseMap().get("Project")).size());
        assertEquals(10, (int)msg.getResponseMap().get("page_length"));
        verify(projectMapper).getNamedStatusByEidCascade(1,"",256,2047);
    }

    @Test
    void happy_path_getFilteredPagedProjectByEid_wo_status() {
        Project w = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        List<Project> pws = new ArrayList<Project>();
        for (int i = 0; i < 100; i++) {
            pws.add(w);
        }
        when(projectMapper.getNamedStatusByEidCascade(anyInt(),anyString(),anyInt(),anyInt())).thenReturn(pws);
        ResponseMsg msg = projectService.getFilteredPagedProjectByEid(1,2,10,"",null);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Project"));
        assertEquals(10, ((List<Project>)msg.getResponseMap().get("Project")).size());
        assertEquals(10, (int)msg.getResponseMap().get("page_length"));
        verify(projectMapper).getNamedStatusByEidCascade(anyInt(),anyString(),anyInt(),anyInt());
    }

    @Test
    void happy_path_getFilteredPagedProjectByEid_wo_name() {
        Project w = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        List<Project> pws = new ArrayList<Project>();
        for (int i = 0; i < 100; i++) {
            pws.add(w);
        }
        when(projectMapper.getNamedStatusByEidCascade(anyInt(),anyString(),anyInt(),anyInt())).thenReturn(pws);
        ResponseMsg msg = projectService.getFilteredPagedProjectByEid(1,2,10,null,"done");
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Project"));
        assertEquals(10, ((List<Project>)msg.getResponseMap().get("Project")).size());
        assertEquals(20, (int)msg.getResponseMap().get("page_length"));
        verify(projectMapper, times(2)).getNamedStatusByEidCascade(anyInt(),anyString(),anyInt(),anyInt());
    }
}