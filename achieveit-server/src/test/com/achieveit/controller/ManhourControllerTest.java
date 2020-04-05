package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hamcrest.core.AnyOf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.internal.matchers.Any;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ManhourControllerTest {
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
        mockMvc = MockMvcBuilders.standaloneSetup(new ManhourController(mailService,fileService, manhourService,jwtToken)).build();
    }

    @Test
    void happy_path_when_getActivity_ret200() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得manhour");

        when(manhourService.getActivity()).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/manhour/activity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
        verify(manhourService).getActivity();
    }

    @Test
    void happy_path_when_getSubManhour_ret200() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得manhour");

        when(manhourService.getSubManhour(1)).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/manhour/subManhour/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
        verify(manhourService).getSubManhour(1);
    }

    @Test
    void error_path_when_getSubManhour_ret208() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得manhour");

        when(manhourService.getSubManhour(1)).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/manhour/subManhour/-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208));
    }

    @Test
    void happy_path_when_getMyManhour_woDate_ret200() throws Exception {
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得manhour");
        responseMsg.getResponseMap().put("manhour",1);
        when(manhourService.getPagedManhourByEid(1,0,10)).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/manhour/myManhour")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "0")
                .param("length","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.manhour").isNotEmpty());
        verify(manhourService).getPagedManhourByEid(1,0,10);
    }

    @Test
    void happy_path_when_getMyManhour_wDate_ret200() throws Exception {
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得manhour");
        responseMsg.getResponseMap().put("manhour",1);
        Date date= new Date(11);
        when(manhourService.getFilteredPagedManhourByEid(anyInt(),anyInt(),anyInt(), any(Date.class))).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/manhour/myManhour")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "0")
                .param("length","10")
                .param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.manhour").isNotEmpty());
        verify(manhourService).getFilteredPagedManhourByEid(anyInt(),anyInt(),anyInt(),any(Date.class));
    }

    @Test
    void error_path_when_getMyManhour_wDate_ret208() throws Exception {
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得manhour");
        responseMsg.getResponseMap().put("manhour",1);
        Date date= new Date(11);
        when(manhourService.getFilteredPagedManhourByEid(anyInt(),anyInt(),anyInt(), any(Date.class))).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/manhour/myManhour")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "-1")
                .param("length","10")
                .param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208));
    }

    @Test
    void errorToken_path_when_deleteDefect_BearerError_ret202()throws Exception{
        String authHeader="Bearer";
        Date date= new Date(11);
        mockMvc.perform(MockMvcRequestBuilders.get("/manhour/myManhour")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "0")
                .param("length","10")
                .param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(202));
    }

    @Test
    void ineffectiveToken_path_when_deleteDefect_BearerError_ret204()throws Exception{
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1))+"1";
        Date date= new Date(11);
        mockMvc.perform(MockMvcRequestBuilders.get("/manhour/myManhour")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "0")
                .param("length","10")
                .param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(204));
    }

    @Test
    void expiredToken_path_when_deleteDefect_BearerError_ret206()throws Exception{
        java.util.Date nowDate = new java.util.Date(0);
        Date date= new Date(11);
        java.util.Date expireDate = new java.util.Date(nowDate.getTime() + jwtToken.expire * 1000);
        String token= Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(1 + "")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtToken.secret)
                .compact();
        String authHeader="Bearer"+token;
        mockMvc.perform(MockMvcRequestBuilders.get("/manhour/myManhour")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "0")
                .param("length","10")
                .param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(206));
    }

    @Test
    void happy_path_when_newManhour_ret200() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得manhour");
        responseMsg.getResponseMap().put("manhour",1);
        when(manhourService.newManhour(anyInt(),anyString(),anyInt(),anyInt(),any(),any())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.post("/manhour/1/20200001S01/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"starttime\":\"2020-04-08T16:00:00.000Z\",\n" +
                        "\"endtime\":\"2020-04-09T16:00:00.000Z\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.manhour").isNotEmpty());
        verify(manhourService).newManhour(anyInt(),anyString(),anyInt(),anyInt(),any(),any());
    }

    @Test
    void error_path_when_newManhour_ret208() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得manhour");
        responseMsg.getResponseMap().put("manhour",1);
        when(manhourService.newManhour(anyInt(),anyString(),anyInt(),anyInt(),any(),any())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.post("/manhour/-11/20200001S01/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"starttime\":\"2020-04-08T16:00:00.000Z\",\n" +
                        "\"endtime\":\"2020-04-09T16:00:00.000Z\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208));
    }

    @Test
    void exception_path_when_newManhour_ret210() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得manhour");
        responseMsg.getResponseMap().put("manhour",1);
        when(manhourService.newManhour(anyInt(),anyString(),anyInt(),anyInt(),any(),any())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.post("/manhour/1/20200001S01/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"starttime\":\"2020-041-08T16-1100:00.000Z\",\n" +
                        "\"endtime\":\"2020-04-09T16:00:00.000Z\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(210));
    }

    @Test
    void happy_path_when_deleteManhour_ret200() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得manhour");
        responseMsg.getResponseMap().put("manhour",1);
        when(manhourService.deleteManhour(anyInt(),anyInt())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.delete("/manhour/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.manhour").isNotEmpty());
        verify(manhourService).deleteManhour(anyInt(),anyInt());
    }

    @Test
    void error_path_when_deleteManhour_ret208() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得manhour");
        responseMsg.getResponseMap().put("manhour",1);
        when(manhourService.deleteManhour(anyInt(),anyInt())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.delete("/manhour/1/-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208));
//        verify(manhourService).deleteManhour(anyInt(),anyInt());
    }

    @Test
    void happy_path_when_update_ret200() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得manhour");
        responseMsg.getResponseMap().put("manhour",1);
        when(manhourService.updateManhour(anyInt(),anyInt(),any())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.put("/manhour/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"fid\":0,\n" +
                        "\"starttime\":\"2020-04-08T16:00:00.000Z\",\n" +
                        "\"endtime\":\"2020-04-09T16:00:00.000Z\",\n" +
                        "\"status\":\"unfilled/unchecked/checked\",\n" +
                        "\"activity_id\":1\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.manhour").isNotEmpty());
        verify(manhourService).updateManhour(anyInt(),anyInt(),any());
    }

    @Test
    void error_path_when_update_ret208() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得manhour");
        responseMsg.getResponseMap().put("manhour",1);
        when(manhourService.deleteManhour(anyInt(),anyInt())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.put("/manhour/1/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"fid\":0,\n" +
                        "\"starttime\":\"2020-04-08T16:00:00.000Z\",\n" +
                        "\"endtime\":\"2020-04-09T16:00:00.000Z\",\n" +
                        "\"status\":\"unfilled/unchecked/checked\",\n" +
                        "\"activity_id\":1\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208));
//        verify(manhourService).deleteManhour(anyInt(),anyInt());
    }
}