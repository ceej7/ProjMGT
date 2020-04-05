package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.Employee;
import com.achieveit.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.internal.matchers.Any;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClientControllerTest {
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
        mockMvc = MockMvcBuilders.standaloneSetup(new ClientController(clientService,jwtToken)).build();
    }


    @Test
    void happy_path_getAll_ret200() throws Exception {
        when(clientService.getAll()).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/client/getAll"))
                .andExpect(status().isOk());
        verify(clientService).getAll();
    }
}