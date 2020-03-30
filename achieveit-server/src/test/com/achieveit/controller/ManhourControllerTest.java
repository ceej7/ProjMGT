package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.EmployeeService;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import com.achieveit.service.ManhourService;
import org.hamcrest.core.AnyOf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.internal.matchers.Any;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ManhourControllerTest {
    MailService mailService;
    FileService fileService;
    ManhourService manhourService;
    MockMvc mockMvc;
    JwtToken jwtToken;

    @BeforeEach
    void setUp(){
        jwtToken = new JwtToken();
        mailService = mock(MailService.class);
        fileService = mock(FileService.class);
        manhourService = mock(ManhourService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ManhourController(mailService,fileService, manhourService,jwtToken)).build();
    }


    @Test
    void happy_path_when_getMyManhour_woDate() throws Exception {
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
    void happy_path_when_getMyManhour_wDate() throws Exception {
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
}