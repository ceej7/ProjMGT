package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RiskControllerTest {
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
        mockMvc = MockMvcBuilders.standaloneSetup(new RiskController(riskService,jwtToken)).build();
    }
    @Test
    void happy_path_getByProject_ret200() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("risk",1);
        when(riskService.getByProjectId(any())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.get("/risk/getByPid/20200001S01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.risk").isNotEmpty())
        ;
        verify(riskService).getByProjectId(any());
    }

    @Test
    void error_path_getByProject_ret208() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("risk",1);
        when(riskService.getByProjectId(any())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.get("/risk/getByPid/201200001S01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.risk").isNotEmpty())
        ;
//        verify(riskService).getByProjectId(any());
    }

    @Test
    void happy_path_getByTemplate_ret200() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("risk",1);
        when(riskService.getByTemplate()).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.get("/risk/getByTemplate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.risk").isNotEmpty())
        ;
        verify(riskService).getByTemplate();
    }

    @Test
    void happy_path_add_ret200() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("risk",1);
        when(riskService.add(anyInt(),anyString(),anyString(),any(),any(),any(),any(),anyInt(), any())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.post("/risk/1/20200101S01")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"type\":\"some risk\",\n" +
                        "\"desc\":\"description for the concrete risk\",\n" +
                        "\"grade\":\"p1\",\n" +
                        "\"influence\":\"filling as ur will\",\n" +
                        "\"strategy\":\"filling it plz\",\n" +
                        "\"frequency\":1, \"ep_ids\":[73,72,71,70,68,67,66]\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.risk").isNotEmpty())
        ;
        verify(riskService).add(anyInt(),anyString(),anyString(),any(),any(),any(),any(),anyInt(), any());
    }


    @Test
    void error_path_add_ret208_eid_error() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("risk",1);
        when(riskService.add(anyInt(),anyString(),anyString(),any(),any(),any(),any(),anyInt(), any())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.post("/risk/-1/20200101S01")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"type\":\"some risk\",\n" +
                        "\"desc\":\"description for the concrete risk\",\n" +
                        "\"grade\":\"p1\",\n" +
                        "\"influence\":\"filling as ur will\",\n" +
                        "\"strategy\":\"filling it plz\",\n" +
                        "\"frequency\":1, \"ep_ids\":[73,72,71,70,68,67,66]\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.risk").isNotEmpty())
        ;
//        verify(riskService).add(anyInt(),anyString(),anyString(),any(),any(),any(),any(),anyInt(), any());
    }

    @Test
    void error_path_add_ret208_grade_error() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("risk",1);
        when(riskService.add(anyInt(),anyString(),anyString(),any(),any(),any(),any(),anyInt(), any())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.post("/risk/1/20200101S01")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"type\":\"some risk\",\n" +
                        "\"desc\":\"description for the concrete risk\",\n" +
                        "\"grade\":\"p11\",\n" +
                        "\"influence\":\"filling as ur will\",\n" +
                        "\"strategy\":\"filling it plz\",\n" +
                        "\"frequency\":1, \"ep_ids\":[73,72,71,70,68,67,66]\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.risk").isNotEmpty())
        ;
//        verify(riskService).add(anyInt(),anyString(),anyString(),any(),any(),any(),any(),anyInt(), any());
    }

    @Test
    void happy_path_update_ret200()  throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("risk",1);
        when(riskService.update(anyInt(),anyInt(), any())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.put("/risk/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"type\":\"some risk\",\n" +
                        "\"desc\":\"description for the concrete risk\",\n" +
                        "\"grade\":\"p1\",\n" +
                        "\"influence\":\"filling as ur will\",\n" +
                        "\"strategy\":\"filling it plz\",\n" +
                        "\"frequency\":1, \"ep_ids\":[73,72,71,70,68,67,66]\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.risk").isNotEmpty())
        ;
        verify(riskService).update(anyInt(),anyInt(), any());
    }

    @Test
    void error_path_update_ret208()  throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("risk",1);
        when(riskService.update(anyInt(),anyInt(), any())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.put("/risk/-1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"type\":\"some risk\",\n" +
                        "\"desc\":\"description for the concrete risk\",\n" +
                        "\"grade\":\"p1\",\n" +
                        "\"influence\":\"filling as ur will\",\n" +
                        "\"strategy\":\"filling it plz\",\n" +
                        "\"frequency\":1, \"ep_ids\":[73,72,71,70,68,67,66]\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.risk").isNotEmpty())
        ;
//        verify(riskService).update(anyInt(),anyInt(), any());
    }

    @Test
    void happy_path_delete_ret200() throws Exception{

        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("risk",1);
        when(riskService.delete(anyInt(),anyInt())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.delete("/risk/1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.risk").isNotEmpty())
        ;
        verify(riskService).delete(anyInt(),anyInt());
    }

    @Test
    void error_path_delete_ret208() throws Exception{

        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("risk",1);
        when(riskService.delete(anyInt(),anyInt())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.delete("/risk/-1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.risk").isNotEmpty())
        ;
//        verify(riskService).delete(anyInt(),anyInt());
    }
}