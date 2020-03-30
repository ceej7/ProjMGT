//package com.achieveit.controller;
//
//import com.achieveit.config.JwtToken;
//import com.achieveit.entity.ResponseMsg;
//import com.achieveit.entity.Workflow;
//import com.achieveit.service.FileService;
//import com.achieveit.service.MailService;
//import com.achieveit.service.ProjectService;
//import com.achieveit.service.WorkflowService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//class ProjectControllerTest {
//
//    ProjectService projectService;
//    MockMvc mockMvc;
//    JwtToken jwtToken;
//
//    @BeforeEach
//    void setUp(){
//        projectService = mock(ProjectService.class);
//        mockMvc = MockMvcBuilders.standaloneSetup(new ProjectController(projectService)).build();
//        jwtToken = mock(JwtToken.class);
//    }
//
//    @Test
//    void happy_path_when_get_to_check_of_sup() throws Exception{
//        ResponseMsg responseMsg=new ResponseMsg();
//        responseMsg.setStatusAndMessage(200, "请求正常");
//        responseMsg.getResponseMap().put("Project",1);
//        when(projectService.getProjectToCheck(1)).thenReturn(responseMsg);
//
//        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
//        mockMvc.perform(MockMvcRequestBuilders.get("/employee/get")
//                .header("accept", "*/*")
//                .header("Authorization",authHeader))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty());
//        verify(projectService).getProjectToCheck(1);
//    }
//}