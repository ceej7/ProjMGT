package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShowDaoPurposeControllerTest {


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
        mockMvc = MockMvcBuilders.standaloneSetup(new ShowDaoPurposeController()).build();
    }

    @Test
    void happy_path_get_ok() throws Exception {
        mockMvc.perform(get("/showDao/getActivity"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/showDao/getClient"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/showDao/getDefect"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/showDao/getEmployee"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/showDao/getEmployeeProject"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/showDao/getEmployeeRoleProject"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/showDao/getManhour"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/showDao/getMilestone"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/showDao/getProject"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/showDao/getProperty"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/showDao/getPropertyOccupy"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/showDao/getRisk"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/showDao/getRiskRelation"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/showDao/getWorkflow"))
                .andExpect(status().isOk());
    }

}