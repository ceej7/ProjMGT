package com.achieveit.service;

import com.achieveit.entity.Employee;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Workflow;
import com.achieveit.mapper.EmployeeMapper;
import com.achieveit.mapper.EmployeeProjectMapper;
import com.achieveit.mapper.ProjectMapper;
import com.achieveit.mapper.WorkflowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class WorkflowServiceTest {

    ProjectMapper projectMapper;
    EmployeeProjectMapper employeeProjectMapper;
    WorkflowMapper workflowMapper;
    EmployeeMapper employeeMapper;
    WorkflowService workflowService;

    @BeforeEach
    void setup(){
        employeeProjectMapper = mock(EmployeeProjectMapper.class);
        projectMapper = mock(ProjectMapper.class);
        workflowMapper=mock(WorkflowMapper.class);
        employeeMapper = mock(EmployeeMapper.class);
        workflowService = new WorkflowService(workflowMapper, employeeMapper,projectMapper,employeeProjectMapper);
    }

    @Test
    void happy_path_with_getById() {
        Workflow w = new Workflow(1, 1, 1, 1, 1, 1, 1, "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
        when(workflowMapper.getByWidCascade(1)).thenReturn(w);
        ResponseMsg msg = workflowService.getById(1);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("workflow"));
        assertEquals(1, ((Workflow)msg.getResponseMap().get("workflow")).getWid());
        verify(workflowMapper).getByWidCascade(1);
    }

}