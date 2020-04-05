package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectControllerTest {
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
        mockMvc = MockMvcBuilders.standaloneSetup(new ProjectController(mailService,fileService,projectService,jwtToken)).build();
    }

    @Test
    void happy_path_when_getByPid_ret200() throws Exception{
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
    void error_path_when_getByPid_ret208() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/project/getByPid/2020000101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty())
        ;
//        verify(projectService).getById(anyString());
    }

    @Test
    void happy_path_when_get_toManage_ret200() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.getProjectToManage(1)).thenReturn(responseMsg);

        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        mockMvc.perform(MockMvcRequestBuilders.get("/project/toManage")
                .header("accept", "*/*")
                .header("Authorization",authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty());
        verify(projectService).getProjectToManage(1);
    }

    @Test
    void auth_error_path_when_get_toManage_ret202() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.getProjectToManage(1)).thenReturn(responseMsg);

        String authHeader="Bearer";
        mockMvc.perform(MockMvcRequestBuilders.get("/project/toManage")
                .header("accept", "*/*")
                .header("Authorization",authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(202))
//                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty())
        ;
//        verify(projectService).getProjectToManage(1);
    }

    @Test
    void auth_incorrect_path_when_get_toManage_ret204() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.getProjectToManage(1)).thenReturn(responseMsg);

        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1))+"1";
        mockMvc.perform(MockMvcRequestBuilders.get("/project/toManage")
                .header("accept", "*/*")
                .header("Authorization",authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(204))
//                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty())
        ;
//        verify(projectService).getProjectToManage(1);
    }

    @Test
    void auth_expired_path_when_get_toManage_ret206() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.getProjectToManage(1)).thenReturn(responseMsg);
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
        mockMvc.perform(MockMvcRequestBuilders.get("/project/toManage")
                .header("accept", "*/*")
                .header("Authorization",authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(206))
//                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty())
        ;
//        verify(projectService).getProjectToManage(1);
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
    void happy_path_when_getMyProject_wStatus_ret200() throws Exception {
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

    @Test
    void happy_path_when_getMyProject_wStatus_and_name_ret200() throws Exception {
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得project");
        responseMsg.getResponseMap().put("project",1);
        when(projectService.getPagedProjectByEid(anyInt(), anyInt(), anyInt())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/project/myProject")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "0")
                .param("length","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.project").isNotEmpty());
        verify(projectService).getPagedProjectByEid(anyInt(), anyInt(), anyInt());
    }

    @Test
    void error_path_when_getMyProject_wStatus_ret208() throws Exception {
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得project");
        responseMsg.getResponseMap().put("project",1);
        when(projectService.getFilteredPagedProjectByEid(anyInt(),anyInt(),anyInt(), any(String.class), any(String.class))).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.get("/project/myProject")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "-1")
                .param("length","10")
                .param("name", "a")
                .param("status", "done"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.project").isNotEmpty())
        ;
//        verify(projectService).getFilteredPagedProjectByEid(anyInt(),anyInt(),anyInt(), any(String.class), any(String.class));
    }

    @Test
    void auth_error_path_when_getMyProject_ret202() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.getProjectToManage(1)).thenReturn(responseMsg);

        String authHeader="Bearer";
        mockMvc.perform(MockMvcRequestBuilders.get("/project/myProject")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "-1")
                .param("length","10")
                .param("name", "a")
                .param("status", "done"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(202))
//                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty())
        ;
//        verify(projectService).getProjectToManage(1);
    }

    @Test
    void auth_incorrect_path_when_getMyProject_ret204() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.getProjectToManage(1)).thenReturn(responseMsg);

        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1))+"1";
        mockMvc.perform(MockMvcRequestBuilders.get("/project/myProject")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "-1")
                .param("length","10")
                .param("name", "a")
                .param("status", "done"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(204))
//                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty())
        ;
//        verify(projectService).getProjectToManage(1);
    }

    @Test
    void auth_expired_path_when_getMyProject_ret206() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.getProjectToManage(1)).thenReturn(responseMsg);
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
        mockMvc.perform(MockMvcRequestBuilders.get("/project/myProject")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "-1")
                .param("length","10")
                .param("name", "a")
                .param("status", "done"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(206))
//                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty())
        ;
//        verify(projectService).getProjectToManage(1);
    }

    @Test
    void happy_path_when_newProject_ret200()throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.newProject(any(),any(),any(),any(), any(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.post("/project/new/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"name\": \"new project\",\n" +
                        "\"startdate\":\"2020-04-08T16:00:00.000Z\",\n" +
                        "\"enddate\":\"2020-05-21T16:00:00.000Z\",\n" +
                        "\"technique\": \"tech\",\n" +
                        "\"domain\": \"domain\",\n" +
                        "\"client\": 1,\n" +
                        "\"configurer_eid\": 7,\n" +
                        "\"epgleader_eid\": 5,\n" +
                        "\"qamanager_eid\": 4\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
        verify(projectService).newProject(any(),any(),any(),any(), any(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void error_path_when_newProject_ret210()throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.newProject(any(),any(),any(),any(), any(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.post("/project/new/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"name\": \"new project\",\n" +
                        "\"startdate\":\"2020-04-0901-08T16:00:00.000Z\",\n" +
                        "\"enddate\":\"2020-05-21T16:00:00.000Z\",\n" +
                        "\"technique\": \"tech\",\n" +
                        "\"domain\": \"domain\",\n" +
                        "\"client\": 1,\n" +
                        "\"configurer_eid\": 7,\n" +
                        "\"epgleader_eid\": 5,\n" +
                        "\"qamanager_eid\": 4\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(210));
//        verify(projectService).newProject(any(),any(),any(),any(), any(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void error_path_when_newProject_ret208()throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.newProject(any(),any(),any(),any(), any(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.post("/project/new/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"name\": \"new project\",\n" +
                        "\"startdate\":\"2020-04-08T16:00:00.000Z\",\n" +
                        "\"enddate\":\"2020-05-21T16:00:00.000Z\",\n" +
                        "\"technique\": \"tech\",\n" +
                        "\"domain\": \"domain\",\n" +
                        "\"configurer_eid\": 7,\n" +
                        "\"epgleader_eid\": 5,\n" +
                        "\"qamanager_eid\": 4\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208));
//        verify(projectService).newProject(any(),any(),any(),any(), any(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void happy_path_when_removeEmployeeProject_ret200() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.removeEmployeeProject(anyInt())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.delete("/project/member/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty());
        verify(projectService).removeEmployeeProject(anyInt());
    }

    @Test
    void error_path_when_removeEmployeeProject_ret208() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.removeEmployeeProject(anyInt())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.delete("/project/member/-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty())
        ;
//        verify(projectService).removeEmployeeProject(anyInt());
    }

    @Test
    void happy_path_when_updateProject_ret200() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.updateProjectInfo(any(),any())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.put("/project/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"name\":\"doge\",\n" +
                        "\"starttime\":\"2020-04-08T16:00:00.000Z\",\n" +
                        "\"endtime\":\"2020-04-09T16:00:00.000Z\",\n" +
                        "\"technique\":\"no tech\",\n" +
                        "\"domain\":\"not again\",\n" +
                        "\"function\":{\"000000\":\"0-1\"}\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty());
        verify(projectService).updateProjectInfo(any(),any());
    }

    @Test
    void happy_path_when_updateEmployeeProjectAndRole_ret200() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.updateEmployeeProjectAndRole(any(),anyInt(),any())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.post("/project/member/1/20200202S01")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"roles\":[\"qa\",\"epg\",\"rd\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty());
        verify(projectService).updateEmployeeProjectAndRole(any(),anyInt(),any());
    }

    @Test
    void error_path_when_updateEmployeeProjectAndRole_ret208() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.updateEmployeeProjectAndRole(any(),anyInt(),any())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.post("/project/member/1/201200202S01")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"roles\":[\"qa\",\"epg\",\"rd\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty())
        ;
//        verify(projectService).updateEmployeeProjectAndRole(any(),anyInt(),any());
    }

    @Test
    void error_path_when_updateEmployeeProjectAndRole_ret210() throws Exception{
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "请求正常");
        responseMsg.getResponseMap().put("Project",1);
        when(projectService.updateEmployeeProjectAndRole(any(),anyInt(),any())).thenReturn(responseMsg);

        mockMvc.perform(MockMvcRequestBuilders.post("/project/member/1/20120202S01")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"roles1\":[\"qa\",\"epg\",\"rd\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(210))
//                .andExpect(jsonPath("$.responseMap.Project").isNotEmpty())
        ;
//        verify(projectService).updateEmployeeProjectAndRole(any(),anyInt(),any());
    }
}