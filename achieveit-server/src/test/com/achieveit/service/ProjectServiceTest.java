package com.achieveit.service;

import com.achieveit.entity.Employee;
import com.achieveit.entity.EmployeeProject;
import com.achieveit.entity.Project;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.mapper.EmployeeMapper;
import com.achieveit.mapper.EmployeeProjectMapper;
import com.achieveit.mapper.ProjectMapper;
import com.achieveit.mapper.WorkflowMapper;
import net.bytebuddy.implementation.bytecode.assign.primitive.PrimitiveUnboxingDelegate;
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

    //////////////getById()//////////////
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
    void exception_when_getById() {
        when(projectMapper.getByPidCascade(any())).thenThrow(new RuntimeException());
        ResponseMsg msg = projectService.getById("20200001O01");
        assertEquals(404, msg.getStatus());
        verify(projectMapper).getByPidCascade(any());
    }

    //////////////getProjectToManage()//////////////
    @Test
    void happy_path_getProjectToManage() {
        Project project = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        List<Project> projects = new ArrayList<Project>();
        projects.add(project);
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);

        when(employeeMapper.getByEid(1)).thenReturn(employee);
        when(projectMapper.getBySupEidCascade(1)).thenReturn(projects);
        when(projectMapper.getByPmEidCascade(1)).thenReturn(projects);
        when(projectMapper.getByQaManagerEidCascade(1)).thenReturn(projects);
        when(projectMapper.getByEpgLeaderEidCascade(1)).thenReturn(projects);
        when(projectMapper.getByConfigurerEidCascade(1)).thenReturn(projects);

        String[] titles = {"pm_manager", "configurer", "pm", "epg_leader", "qa_manager"};
        int i = 0;
        while (i < 5) {
            String title = titles[i++];
            employee.setTitle(title);
            ResponseMsg msg = projectService.getProjectToManage(1);
            assertEquals(200, msg.getStatus());
            assertNotNull(msg.getResponseMap().get("Project"));
        }
    }

    @Test
    void exception_when_getProjectToManage(){
        when(employeeMapper.getByEid(1)).thenThrow(new RuntimeException());
        ResponseMsg msg = projectService.getProjectToManage(1);
        assertEquals(404, msg.getStatus());
        assertNull(msg.getResponseMap().get("Project"));
        verify(employeeMapper).getByEid(1);
    }

    @Test
    void wrong_title_when_getProjectToManage(){
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, "member", null);
        when(employeeMapper.getByEid(1)).thenReturn(employee);
        ResponseMsg msg = projectService.getProjectToManage(1);
        assertEquals(208, msg.getStatus());
        assertNull(msg.getResponseMap().get("Project"));
        verify(employeeMapper).getByEid(1);
    }

    //////////////getPagedProjectByEid()//////////////
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
    void exception_when_getPagedProjectByEid() {
        when(projectMapper.getByEidCascade(1)).thenThrow(new RuntimeException());
        ResponseMsg msg = projectService.getPagedProjectByEid(1,2,10);
        assertEquals(404, msg.getStatus());
        assertNull(msg.getResponseMap().get("Project"));
        verify(projectMapper).getByEidCascade(1);
    }

    //////////////getFilteredPagedProjectByEid()//////////////
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
    void status_applying_getFilteredPagedProjectByEid() {
        Project w = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        List<Project> pws = new ArrayList<Project>();
        for (int i = 0; i < 100; i++) {
            pws.add(w);
        }
        when(projectMapper.getNamedStatusByEidCascade(1,"",256,2047)).thenReturn(pws);
        ResponseMsg msg = projectService.getFilteredPagedProjectByEid(1,2,10,"","applying");
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Project"));
        assertEquals(10, ((List<Project>)msg.getResponseMap().get("Project")).size());
        assertEquals(10, (int)msg.getResponseMap().get("page_length"));
        verify(projectMapper).getNamedStatusByEidCascade(1,"",256,2047);
    }

    @Test
    void exception_when_getFilteredPagedProjectByEid() {
        when(projectMapper.getNamedStatusByEidCascade(1,"",256,2047)).thenThrow(new RuntimeException());
        ResponseMsg msg = projectService.getFilteredPagedProjectByEid(1,2,10,"","doing");
        assertEquals(404, msg.getStatus());
        assertNull(msg.getResponseMap().get("Project"));
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

    //////////////newProject()//////////////


    //////////////removeEmployeeProject()//////////////
    //////////////updateEmployeeProjectAndRole()//////////////
    //////////////updateProjectInfo()//////////////

}