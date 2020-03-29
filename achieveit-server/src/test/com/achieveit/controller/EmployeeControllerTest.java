package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.Employee;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Workflow;
import com.achieveit.service.EmployeeService;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import com.achieveit.service.WorkflowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.jsonwebtoken.Claims;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmployeeControllerTest {
    EmployeeService employeeService;
    FileService fileService;
    MailService mailService;
    MockMvc mockMvc;
    JwtToken jwtToken;

    @BeforeEach
    void setUp(){
        jwtToken = new JwtToken();
        employeeService = mock(EmployeeService.class);
        fileService = mock(FileService.class);
        mailService = mock(MailService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new EmployeeController(mailService,fileService,employeeService,jwtToken)).build();
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
    void happy_path_when_get_dashboard_by_id_confidential() throws Exception{
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
}