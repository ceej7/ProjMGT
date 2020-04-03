package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.DefectService;
import com.achieveit.service.EmployeeService;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DefectControllerTest {
    MailService mailService;
    FileService fileService;
    DefectService defectService;
    MockMvc mockMvc;
    JwtToken jwtToken;

    @BeforeEach
    void setUp(){
        jwtToken = new JwtToken();
        mailService = mock(MailService.class);
        fileService = mock(FileService.class);
        defectService = mock(DefectService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new DefectController(mailService,fileService, defectService,jwtToken)).build();
    }

    @Test
    void happy_path_when_getMyDefect_woDesc() throws Exception {
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得Defect");
        responseMsg.getResponseMap().put("defect",1);
        when(defectService.getPagedDefectByEid(1,0,10)).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/defect/myDefect")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "0")
                .param("length","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.defect").isNotEmpty());
        verify(defectService).getPagedDefectByEid(1,0,10);
    }

    @Test
    void happy_path_when_getMyDefect_wDesc() throws Exception {
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得Defect");
        responseMsg.getResponseMap().put("defect",1);
        when(defectService.getFilteredPagedDefectByEid(1,0,10,"desc",null)).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/defect/myDefect")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "0")
                .param("length","10")
                .param("desc", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.defect").isNotEmpty());
        verify(defectService).getFilteredPagedDefectByEid(1,0,10,"desc",null);
    }
}