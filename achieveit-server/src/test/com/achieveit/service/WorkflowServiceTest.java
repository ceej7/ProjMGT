package com.achieveit.service;

import com.achieveit.entity.Employee;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Timeline;
import com.achieveit.entity.Workflow;
import com.achieveit.mapper.EmployeeMapper;
import com.achieveit.mapper.EmployeeProjectMapper;
import com.achieveit.mapper.ProjectMapper;
import com.achieveit.mapper.WorkflowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

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
    void happy_path_with_getById_ret200() {
        Workflow w = new Workflow(1, 1, 1, 1, 1, 1, 1, "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
        when(workflowMapper.getByWidCascade(1)).thenReturn(w);
        ResponseMsg msg = workflowService.getById(1);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("workflow"));
        assertEquals(1, ((Workflow)msg.getResponseMap().get("workflow")).getWid());
        verify(workflowMapper).getByWidCascade(1);
    }
    @Test
    void error_path_with_getById_ret210() {
        Workflow w = new Workflow(1, 1, 1, 1, 1, 1, 1, "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
        when(workflowMapper.getByWidCascade(1)).thenReturn(null);
        ResponseMsg msg = workflowService.getById(1);
        assertEquals(210, msg.getStatus());
    }
    @Test
    void exception_path_with_getById_ret404() {
        Workflow w = new Workflow(1, 1, 1, 1, 1, 1, 1, "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
        when(workflowMapper.getByWidCascade(1)).thenThrow(new RuntimeException());
        ResponseMsg msg = workflowService.getById(1);
        assertEquals(404, msg.getStatus());
    }

    @Test
    void happy_path_getTimelineByWid_ret200(){
        Timeline timeline=new Timeline("", new Timestamp(0), 1, 1);
        ArrayList<Timeline> timelines = new ArrayList<>();
        timelines.add(timeline);
        when(workflowMapper.getTimelineByWid(anyInt())).thenReturn(timelines);
        ResponseMsg  msg = workflowService.getTimelineByWid(1);
        assertEquals(200, msg.getStatus());
    }

    @Test
    void error_path_getTimelineByWid_ret210(){
        Timeline timeline=new Timeline("", new Timestamp(0), 1, 1);
        ArrayList<Timeline> timelines = new ArrayList<>();
        timelines.add(timeline);
        when(workflowMapper.getTimelineByWid(anyInt())).thenReturn(null);
        ResponseMsg  msg = workflowService.getTimelineByWid(1);
        assertEquals(210, msg.getStatus());
    }

    @Test
    void exception_path_getTimelineByWid_ret404(){
        Timeline timeline=new Timeline("", new Timestamp(0), 1, 1);
        ArrayList<Timeline> timelines = new ArrayList<>();
        timelines.add(timeline);
        when(workflowMapper.getTimelineByWid(anyInt())).thenThrow(new RuntimeException());
        ResponseMsg  msg = workflowService.getTimelineByWid(1);
        assertEquals(404, msg.getStatus());
    }
}