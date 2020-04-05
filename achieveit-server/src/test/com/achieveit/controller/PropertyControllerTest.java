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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PropertyControllerTest {

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
        mockMvc = MockMvcBuilders.standaloneSetup(new PropertyController(propertyService,jwtToken)).build();
    }

    @Test
    void happy_path_getByPid_ret200() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("property",1);
        when(propertyService.getByPropertyId(anyInt())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.get("/property/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.property").isNotEmpty())
        ;
        verify(propertyService).getByPropertyId(anyInt());
    }

    @Test
    void error_path_getByPid_ret208() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("property",1);
        when(propertyService.getByPropertyId(anyInt())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.get("/property/-11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.property").isNotEmpty())
        ;
//        verify(propertyService).getByPropertyId(anyInt());
    }

    @Test
    void happy_path_getAll_ret200() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("property",1);
        when(propertyService.getAllProperty()).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.get("/property"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.property").isNotEmpty())
        ;
        verify(propertyService).getAllProperty();
    }

    @Test
    void happy_path_getByEid_ret200() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("property",1);
        when(propertyService.getByEmployeeId(anyInt())).thenReturn(responseMsg)
        ;

        mockMvc.perform(MockMvcRequestBuilders.get("/property/occupy/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.property").isNotEmpty());
        verify(propertyService).getByEmployeeId(anyInt());
    }

    @Test
    void error_path_getByEid_ret208() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("property",1);
        when(propertyService.getByEmployeeId(anyInt())).thenReturn(responseMsg)
        ;

        mockMvc.perform(MockMvcRequestBuilders.get("/property/occupy/employee/-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.property").isNotEmpty())
        ;
//        verify(propertyService).getByEmployeeId(anyInt());
    }

    @Test
    void happy_path_getByProjectId_ret200() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("property",1);
        when(propertyService.getByProjectId(anyString())).thenReturn(responseMsg)
        ;

        mockMvc.perform(MockMvcRequestBuilders.get("/property/occupy/project/20200002S02"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.property").isNotEmpty());
        verify(propertyService).getByProjectId(anyString());
    }

    @Test
    void error_path_getByProjectId_ret208() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("property",1);
        when(propertyService.getByProjectId(anyString())).thenReturn(responseMsg)
        ;

        mockMvc.perform(MockMvcRequestBuilders.get("/property/occupy/project/2020002S02"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.property").isNotEmpty())
        ;
//        verify(propertyService).getByProjectId(anyString());
    }

    @Test
    void happy_path_rent_ret200() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("property",1);
        when(propertyService.rentIn(anyInt(),any(),anyInt(),any(),anyBoolean())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.post("/property/occupy/1/20200002S02/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"expire_time\":\"2020-04-08T16:00:00.000Z\",\n" +
                        "\"is_intact\":true\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.property").isNotEmpty())
        ;
        verify(propertyService).rentIn(anyInt(),any(),anyInt(),any(),anyBoolean());
    }

    @Test
    void error_path_rent_ret208() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("property",1);
        when(propertyService.rentIn(anyInt(),any(),anyInt(),any(),anyBoolean())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.post("/property/occupy/-1/20200002S02/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"expire_time\":\"2020-04-08T16:00:00.000Z\",\n" +
                        "\"is_intact\":true\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.property").isNotEmpty())
        ;
//        verify(propertyService).rentIn(anyInt(),any(),anyInt(),any(),anyBoolean());
    }

    @Test
    void error_path_rent_ret210() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("property",1);
        when(propertyService.rentIn(anyInt(),any(),anyInt(),any(),anyBoolean())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.post("/property/occupy/1/20200002S02/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"expire_time\":\"2020-0-104-08T16:00:00.000Z\",\n" +
                        "\"is_intact\":true\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(210))
//                .andExpect(jsonPath("$.responseMap.property").isNotEmpty())
        ;
//        verify(propertyService).rentIn(anyInt(),any(),anyInt(),any(),anyBoolean());
    }

    @Test
    void happy_path_updatePropertyOccupyStatus_ret200() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("property",1);
        when(propertyService.updatePropertyOccupy(anyInt(),any())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.put("/property/occupy/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"expire_time\":\"2020-04-08T16:00:00.000Z\",\n" +
                        "\"is_intact\":true\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.property").isNotEmpty())
        ;
        verify(propertyService).updatePropertyOccupy(anyInt(),any());
    }

    @Test
    void error_path_updatePropertyOccupyStatus_ret208() throws Exception {
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("property",1);
        when(propertyService.updatePropertyOccupy(anyInt(),any())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.put("/property/occupy/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"expire_time\":\"2020-04-08T16:00:00.000Z\",\n" +
                        "\"is_intact\":true\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.property").isNotEmpty())
        ;
//        verify(propertyService).updatePropertyOccupy(anyInt(),any());
    }
}