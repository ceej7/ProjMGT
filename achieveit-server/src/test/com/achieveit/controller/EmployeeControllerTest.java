package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.Employee;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Workflow;
import com.achieveit.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmployeeControllerTest {
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
        mockMvc = MockMvcBuilders.standaloneSetup(new EmployeeController(mailService,fileService, employeeService,jwtToken)).build();
    }

    @Test
    public void happy_path_when_login() throws Exception{

        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "成功登陆");
        responseMsg.getResponseMap().put("employee",1);

        when(employeeService.login("Alias", "123456")).thenReturn(responseMsg);

        Map<String, String> requestParam = new HashMap<>();
        requestParam.put("name", "Alias");
        requestParam.put("password", "123456");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ((ObjectWriter) objectWriter).writeValueAsString(requestParam);

        mockMvc.perform(post("/employee/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty());
        verify(employeeService).login("Alias", "123456");
    }

    @Test
    public void error_path_when_login() throws Exception{

        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "成功登陆");
        responseMsg.getResponseMap().put("employee",1);

        when(employeeService.login("Alias", "123456")).thenReturn(responseMsg);

        Map<String, String> requestParam = new HashMap<>();
        requestParam.put("password", "123456");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ((ObjectWriter) objectWriter).writeValueAsString(requestParam);

        mockMvc.perform(post("/employee/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(202));
//        verify(employeeService).login("Alias", "123456");
    }

    @Test
    void happy_path_when_get_by_title() throws Exception{
        String[] titles={"pm_manager","configurer","pm","epg_leader","qa_manager","member"};

        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "正常返回");
        responseMsg.getResponseMap().put("employees",1);
        int i=0;
        while(i<6) {
            String title = titles[i++];
            when(employeeService.getByTitle(title)).thenReturn(responseMsg);
            mockMvc.perform(get("/employee/getByTitle/"+title))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(200))
                    .andExpect(jsonPath("$.responseMap.employees").isNotEmpty());
            verify(employeeService).getByTitle(title);
        }
    }

    @Test
    void error_path_when_get_by_title() throws Exception{
        String[] titles={"pm_manager","configurer","pm","epg_leader","qa_manager","member"};

        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "正常返回");
        responseMsg.getResponseMap().put("employees",1);
        int i=0;
        while(i<6) {
            String title = titles[i++];
            when(employeeService.getByTitle(title)).thenReturn(responseMsg);
            mockMvc.perform(get("/employee/getByTitle/"+"1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(208));
        }
    }

    @Test
    void happy_path_when_get_by_token() throws Exception{
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得用户1");
        responseMsg.getResponseMap().put("employee",1);
        when(employeeService.getByIdConfidential(1)).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/get")
                .header("accept", "*/*")
                .header("Authorization",authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty());
        verify(employeeService).getByIdConfidential(1);
    }

    @Test
    void errorToken_path_when_getByToken_BearerError()throws Exception{
        String authHeader="Bearer";
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得用户1");
        responseMsg.getResponseMap().put("employee",1);
        when(employeeService.getByIdConfidential(1)).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/get")
                .header("accept", "*/*")
                .header("Authorization",authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(202));
    }

    @Test
    void ineffectiveToken_path_when_getByToken_BearerError()throws Exception{
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1))+"1";
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得用户1");
        responseMsg.getResponseMap().put("employee",1);
        when(employeeService.getByIdConfidential(1)).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/get")
                .header("accept", "*/*")
                .header("Authorization",authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(204));
    }

    @Test
    void expiredToken_path_when_getByToken_BearerError()throws Exception{
        Date nowDate = new Date(0);
        Date expireDate = new Date(nowDate.getTime() + jwtToken.expire * 1000);
        String token= Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(1 + "")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtToken.secret)
                .compact();
        String authHeader="Bearer"+token;
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得用户1");
        responseMsg.getResponseMap().put("employee",1);
        when(employeeService.getByIdConfidential(1)).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/get")
                .header("accept", "*/*")
                .header("Authorization",authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(206));
    }

    @Test
    void happy_path_when_get_by_id() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "正常返回");
        responseMsg.getResponseMap().put("employee",1);

        when(employeeService.getByIdNonConfidential(1)).thenReturn(responseMsg);
        mockMvc.perform(get("/employee/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty());
        verify(employeeService).getByIdNonConfidential(1);
    }

    @Test
    void error_path_when_get_by_id() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "正常返回");
        responseMsg.getResponseMap().put("employee",1);

        when(employeeService.getByIdNonConfidential(1)).thenReturn(responseMsg);
        mockMvc.perform(get("/employee/get/-11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208));
    }

    @Test
    void happy_path_when_get_dashboard_by_id_confidential() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "正常返回");
        responseMsg.getResponseMap().put("employee",1);
        when(employeeService.getDashBoardByIdConfidential(1)).thenReturn(responseMsg);

        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/myDashBoard")
                .header("accept", "*/*")
                .header("Authorization",authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.employee").isNotEmpty());
        verify(employeeService).getDashBoardByIdConfidential(1);

    }

    @Test
    void errorToken_path_when_get_dashboard_by_id_BearerError()throws Exception{
        String authHeader="Bearer";
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/myDashBoard")
                .header("accept", "*/*")
                .header("Authorization",authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(202));
    }

    @Test
    void ineffectiveToken_path_when_get_dashboard_by_id_BearerError()throws Exception{
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1))+"1";
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/myDashBoard")
                .header("accept", "*/*")
                .header("Authorization",authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(204));
    }

    @Test
    void expiredToken_path_when_get_dashboard_by_id_BearerError()throws Exception{
        Date nowDate = new Date(0);
        Date expireDate = new Date(nowDate.getTime() + jwtToken.expire * 1000);
        String token= Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(1 + "")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtToken.secret)
                .compact();
        String authHeader="Bearer"+token;
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/myDashBoard")
                .header("accept", "*/*")
                .header("Authorization",authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(206));
    }
}
