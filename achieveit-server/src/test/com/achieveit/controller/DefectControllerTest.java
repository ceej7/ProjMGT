package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.Defect;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DefectControllerTest {
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
        mockMvc = MockMvcBuilders.standaloneSetup(new DefectController(mailService,fileService, defectService,jwtToken)).build();
    }

    @Test
    void happy_path_getByPid_ret200() throws Exception {
        ResponseMsg msg=new ResponseMsg();
        msg.setStatusAndMessage(200, "");
        when(defectService.getByPid(anyString())).thenReturn(msg);
        mockMvc.perform(MockMvcRequestBuilders.get("/defect/getByPid/20200001S01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
        verify(defectService).getByPid(anyString());
    }

    @Test
    void alternate_path_getByPid_ret200() throws Exception {
        ResponseMsg msg=new ResponseMsg();
        msg.setStatusAndMessage(200, "");
        when(defectService.getByPid(anyString())).thenReturn(msg);
        mockMvc.perform(MockMvcRequestBuilders.get("/defect/getByPid/20200001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208));
        verify(defectService,times(0)).getByPid(anyString());
    }

    @Test
    void happy_path_when_getMyDefect_woDesc_ret200() throws Exception {
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
    void happy_path_when_getMyDefect_wDesc_ret200() throws Exception {
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

    @Test
    void wrong_param_path_when_getMyDefect_ret208() throws Exception {
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        mockMvc.perform(MockMvcRequestBuilders.get("/defect/myDefect")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "-1")
                .param("length","10")
                .param("desc", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208));
    }

    @Test
    void errorToken_path_when_getMyDefect_BearerError_ret202()throws Exception{
        String authHeader="Bearer";
        mockMvc.perform(MockMvcRequestBuilders.get("/defect/myDefect")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "0")
                .param("length","10")
                .param("desc", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(202));
    }

    @Test
    void ineffectiveToken_path_when_getMyDefect_BearerError_ret204()throws Exception{
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1))+"1";
        mockMvc.perform(MockMvcRequestBuilders.get("/defect/myDefect")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "0")
                .param("length","10")
                .param("desc", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(204));
    }

    @Test
    void expiredToken_path_when_getMyDefect_BearerError_ret206()throws Exception{
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
        mockMvc.perform(MockMvcRequestBuilders.get("/defect/myDefect")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .param("page", "0")
                .param("length","10")
                .param("desc", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(206));
    }

    @Test
    void happy_path_when_addDefect_ret200()throws Exception{
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得Defect");
        when(defectService.addDefect(anyInt(), any(), anyString(), any(), anyString(),any())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.post("/defect/20200202S01")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"authority_desc\":\"noneAuthority\",\n" +
                        "\"desc\":\"aaa\",\n" +
                        "\"git_repo\":\"aaa\",\n" +
                        "\"commit\":\"aaa\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void wrong_parameter_path_when_addDefect_ret208()throws Exception{
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        mockMvc.perform(MockMvcRequestBuilders.post("/defect/2020002S01")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"authority_desc\":\"noneAuthority\",\n" +
                        "\"desc\":\"aaa\",\n" +
                        "\"git_repo\":\"aaa\",\n" +
                        "\"commit\":\"aaa\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208));
    }

    @Test
    void errorToken_path_when_addDefect_BearerError_ret202()throws Exception{
        String authHeader="Bearer";
        mockMvc.perform(MockMvcRequestBuilders.post("/defect/1")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"authority_desc\":\"noneAuthority\",\n" +
                        "\"desc\":\"aaa\",\n" +
                        "\"git_repo\":\"aaa\",\n" +
                        "\"commit\":\"aaa\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(202));
    }

    @Test
    void ineffectiveToken_path_when_addDefect_BearerError_ret204()throws Exception{
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1))+"1";
        mockMvc.perform(MockMvcRequestBuilders.post("/defect/1")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"authority_desc\":\"noneAuthority\",\n" +
                        "\"desc\":\"aaa\",\n" +
                        "\"git_repo\":\"aaa\",\n" +
                        "\"commit\":\"aaa\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(204));
    }

    @Test
    void expiredToken_path_when_addDefect_BearerError_ret206()throws Exception{
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
        mockMvc.perform(MockMvcRequestBuilders.post("/defect/1")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"authority_desc\":\"noneAuthority\",\n" +
                        "\"desc\":\"aaa\",\n" +
                        "\"git_repo\":\"aaa\",\n" +
                        "\"commit\":\"aaa\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(206));
    }


    @Test
    void happy_path_when_updateDefect_ret200()throws Exception{
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得Defect");
        when(defectService.updateDefect(anyInt(), anyInt(), any())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.put("/defect/1")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"authority_desc\":\"noneAuthority\",\n" +
                        "\"desc\":\"aaa\",\n" +
                        "\"git_repo\":\"aaa\",\n" +
                        "\"commit\":\"aaa\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void wrong_parameter_path_when_updateDefect_ret208()throws Exception{
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得Defect");
        when(defectService.updateDefect(anyInt(), anyInt(), any())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.put("/defect/-1")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"authority_desc\":\"noneAuthority\",\n" +
                        "\"desc\":\"aaa\",\n" +
                        "\"git_repo\":\"aaa\",\n" +
                        "\"commit\":\"aaa\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208));
    }

    @Test
    void errorToken_path_when_updateDefect_BearerError_ret202()throws Exception{
        String authHeader="Bearer";
        mockMvc.perform(MockMvcRequestBuilders.put("/defect/1")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"authority_desc\":\"noneAuthority\",\n" +
                        "\"desc\":\"aaa\",\n" +
                        "\"git_repo\":\"aaa\",\n" +
                        "\"commit\":\"aaa\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(202));
    }

    @Test
    void ineffectiveToken_path_when_updateDefect_BearerError_ret204()throws Exception{
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1))+"1";
        mockMvc.perform(MockMvcRequestBuilders.put("/defect/1")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"authority_desc\":\"noneAuthority\",\n" +
                        "\"desc\":\"aaa\",\n" +
                        "\"git_repo\":\"aaa\",\n" +
                        "\"commit\":\"aaa\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(204));
    }

    @Test
    void expiredToken_path_when_updateDefect_BearerError_ret206()throws Exception{
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
        mockMvc.perform(MockMvcRequestBuilders.put("/defect/1")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"authority_desc\":\"noneAuthority\",\n" +
                        "\"desc\":\"aaa\",\n" +
                        "\"git_repo\":\"aaa\",\n" +
                        "\"commit\":\"aaa\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(206));
    }


    @Test
    void happy_path_when_deleteDefect_ret200()throws Exception{
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得Defect");
        when(defectService.deleteDefect(anyInt(), anyInt())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.delete("/defect/1")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void wrong_parameter_path_when_deleteDefect_ret208()throws Exception{
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1));
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "获得Defect");
        when(defectService.updateDefect(anyInt(), anyInt(), any())).thenReturn(responseMsg);
        mockMvc.perform(MockMvcRequestBuilders.delete("/defect/-1")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208));
    }

    @Test
    void errorToken_path_when_deleteDefect_BearerError_ret202()throws Exception{
        String authHeader="Bearer";
        mockMvc.perform(MockMvcRequestBuilders.delete("/defect/1")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(202));
    }

    @Test
    void ineffectiveToken_path_when_deleteDefect_BearerError_ret204()throws Exception{
        String authHeader="Bearer"+jwtToken.generateToken(Long.valueOf(1))+"1";
        mockMvc.perform(MockMvcRequestBuilders.delete("/defect/1")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(204));
    }

    @Test
    void expiredToken_path_when_deleteDefect_BearerError_ret206()throws Exception{
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
        mockMvc.perform(MockMvcRequestBuilders.delete("/defect/1")
                .header("accept", "*/*")
                .header("Authorization",authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"authority_desc\":\"noneAuthority\",\n" +
                        "\"desc\":\"aaa\",\n" +
                        "\"git_repo\":\"aaa\",\n" +
                        "\"commit\":\"aaa\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(206));
    }
}