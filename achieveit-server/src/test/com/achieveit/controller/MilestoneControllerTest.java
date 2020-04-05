package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MilestoneControllerTest {
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
        mockMvc = MockMvcBuilders.standaloneSetup(new MilestoneController(jwtToken, milestoneService)).build();
    }

    @Test
    void happy_path_getByMid_ret200() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "成功登陆");
        responseMsg.getResponseMap().put("employee",1);

        when(milestoneService.getByMid(anyInt())).thenReturn(responseMsg);
        mockMvc.perform(get("/milestone/getByMid/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty())
        ;
        verify(milestoneService).getByMid(anyInt());
    }

    @Test
    void error_path_getByMid_ret208() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "成功登陆");
        responseMsg.getResponseMap().put("employee",1);

        when(milestoneService.getByMid(anyInt())).thenReturn(responseMsg);
        mockMvc.perform(get("/milestone/getByMid/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty())
        ;
//        verify(milestoneService).getByMid(anyInt());
    }

    @Test
    void happy_path_getByPid_ret200() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "成功登陆");
        responseMsg.getResponseMap().put("employee",1);

        when(milestoneService.getByPid(any())).thenReturn(responseMsg);
        mockMvc.perform(get("/milestone/getByPid/20200202S02")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty())
        ;
        verify(milestoneService).getByPid(any());
    }

    @Test
    void error_path_getByPid_ret208() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "成功登陆");
        responseMsg.getResponseMap().put("employee",1);

        when(milestoneService.getByPid(any())).thenReturn(responseMsg);
        mockMvc.perform(get("/milestone/getByPid/20200202S021")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty())
        ;
//        verify(milestoneService).getByPid(any());
    }

    @Test
    void happy_path_deleteByMid_ret200() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "成功登陆");
        responseMsg.getResponseMap().put("employee",1);

        when(milestoneService.deleteByMid(anyInt())).thenReturn(responseMsg);
        mockMvc.perform(delete("/milestone/deleteByMid/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty())
        ;
        verify(milestoneService).deleteByMid(anyInt());
    }

    @Test
    void error_path_deleteByMid_ret208() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "成功登陆");
        responseMsg.getResponseMap().put("employee",1);

        when(milestoneService.deleteByMid(anyInt())).thenReturn(responseMsg);
        mockMvc.perform(delete("/milestone/deleteByMid/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty())
        ;
//        verify(milestoneService).getByMid(anyInt());
    }

    @Test
    void happy_path_updateProject_ret200() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "成功登陆");
        responseMsg.getResponseMap().put("employee",1);

        when(milestoneService.update(anyInt(),any())).thenReturn(responseMsg);
        mockMvc.perform(put("/milestone/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"time\":\"2020-04-09T16:00:00.000Z\",\n" +
                        "\"desc\":\"哈哈\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty())
        ;
        verify(milestoneService).update(anyInt(),any());
    }

    @Test
    void error_path_updateProject_ret208() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "成功登陆");
        responseMsg.getResponseMap().put("employee",1);

        when(milestoneService.deleteByMid(anyInt())).thenReturn(responseMsg);
        mockMvc.perform(put("/milestone/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"time\":\"2020-04-09T16:00:00.000Z\",\n" +
                        "\"desc\":\"哈哈\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty())
        ;
//        verify(milestoneService).getByMid(anyInt());
    }

    @Test
    void happy_path_addMilestone_ret200() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "成功登陆");
        responseMsg.getResponseMap().put("employee",1);

        when(milestoneService.add(anyString(),any(),any())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.post("/milestone/add/20200202S02")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"time\":\"2020-04-09T16:00:00.000Z\",\n" +
                        "\"desc\":\"哈哈\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty())
        ;
        verify(milestoneService).add(anyString(),any(),any());
    }

    @Test
    void error_path_addMilestone_ret208() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "成功登陆");
        responseMsg.getResponseMap().put("employee",1);

        when(milestoneService.deleteByMid(anyInt())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.post("/milestone/add/20200202S02")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"time1\":\"2020-04-09T16:00:00.000Z\",\n" +
                        "\"desc\":\"哈哈\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty())
        ;
//        verify(milestoneService).getByMid(anyInt());
    }

    @Test
    void error_path_addMilestone_ret210() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "成功登陆");
        responseMsg.getResponseMap().put("employee",1);

        when(milestoneService.deleteByMid(anyInt())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.post("/milestone/add/20200202S02")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"time\":\"20201-04-09--T-16:00:00.000Z\",\n" +
                        "\"desc\":\"哈哈\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(210))
//                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty())
        ;
//        verify(milestoneService).getByMid(anyInt());
    }
}