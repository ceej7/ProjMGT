package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Workflow;
import com.achieveit.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WorkflowControllerTest {
    ClientService clientService;
    DefectService defectService;
    EmployeeService employeeService;
    FileService fileService;
    MailService mailService;
    ManhourService manhourService;
    MilestoneService milestoneService;
    ProjectService projectService;
    PropertyService propertyService;
    RiskService riskService;
    WorkflowService workflowService;
    JwtToken jwtToken;
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        clientService=mock(ClientService.class);
        defectService = mock(DefectService.class);
        employeeService=mock(EmployeeService.class);
        fileService = mock(FileService.class);
        mailService = mock(MailService.class);
        manhourService=mock(ManhourService.class);
        milestoneService=mock(MilestoneService.class);
        projectService=mock(ProjectService.class);
        propertyService=mock(PropertyService.class);
        riskService=mock(RiskService.class);
        workflowService=mock(WorkflowService.class);
        jwtToken = new JwtToken();
        mockMvc = MockMvcBuilders.standaloneSetup(new WorkflowController(mailService,fileService, workflowService,jwtToken)).build();
    }

    @Test
    void get_workflow_by_correct_id_with_member() throws Exception{
        Workflow workflow = new Workflow(1, 0, 1, 1, 1, 1, 1, "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "正常返回");
        responseMsg.getResponseMap().put("workflow",workflow);
        when(workflowService.getById(1)).thenReturn(responseMsg);
        mockMvc.perform(get("/workflow/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.workflow").isNotEmpty());
        verify(workflowService).getById(1);
    }
}