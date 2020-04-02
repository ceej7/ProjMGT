package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectControllerTest {
    MailService mailService;
    FileService fileService;
    ProjectService projectService;
    MockMvc mockMvc;
    JwtToken jwtToken;

    @BeforeEach
    void setUp(){
        jwtToken = new JwtToken();
        mailService = mock(MailService.class);
        fileService = mock(FileService.class);
        projectService = mock(ProjectService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ProjectController(mailService,fileService,projectService,jwtToken)).build();
    }

    @Test
    void happy_path_when_getByPid() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.getById(anyString())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.get("/project/getByPid/20200001D01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty());
        verify(projectService).getById(anyString());
    }

    @Test
    void happy_path_when_get_to_check_of_sup() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.getProjectToManage(1)).thenReturn(responseMsg);

        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        mockMvc.perform(MockMvcRequestBuilders.get("/project/toCheck")
                .header("accept", "*/*")
                .header("Authorization",authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty());
        verify(projectService).getProjectToManage(1);
    }

    @Test
    void happy_path_when_getMyProject_woStatus() throws Exception {
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得project");
        responseMsg.getResponseMap().put("project",1);
        when(projectService.getPagedProjectByEid(1,0,10)).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/project/myProject")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "0")
                .param("length","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.project").isNotEmpty());
        verify(projectService).getPagedProjectByEid(1,0,10);
    }

    @Test
    void happy_path_when_getMyProject_wStatus() throws Exception {
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得project");
        responseMsg.getResponseMap().put("project",1);
        when(projectService.getFilteredPagedProjectByEid(anyInt(),anyInt(),anyInt(), any(String.class), any(String.class))).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/project/myProject")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "0")
                .param("length","10")
                .param("name", "a")
                .param("status", "done"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.project").isNotEmpty());
        verify(projectService).getFilteredPagedProjectByEid(anyInt(),anyInt(),anyInt(), any(String.class), any(String.class));
    }
}