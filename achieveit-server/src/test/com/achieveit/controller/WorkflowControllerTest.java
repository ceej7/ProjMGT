package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Workflow;
import com.achieveit.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WorkflowControllerTest {
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
        mockMvc = MockMvcBuilders.standaloneSetup(new WorkflowController(mailService,fileService, workflowService,jwtToken)).build();
    }

    @Test
    void happy_path_get_workflow_by_correct_id_with_member_ret200() throws Exception{
        Workflow workflow = new Workflow(1, 0, 1, 1, 1, 1, 1, "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "正常返回");
        responseMsg.getResponseMap().put("workflow",workflow);
        when(workflowService.getById(1)).thenReturn(responseMsg);
        mockMvc.perform(get("/workflow/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.workflow").isNotEmpty());
        verify(workflowService).getById(1);
    }

    @Test
    void error_workflow_by_correct_id_with_member_ret208() throws Exception{
        Workflow workflow = new Workflow(1, 0, 1, 1, 1, 1, 1, "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "正常返回");
        responseMsg.getResponseMap().put("workflow",workflow);
        when(workflowService.getById(1)).thenReturn(responseMsg);
        mockMvc.perform(get("/workflow/-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.workflow").isNotEmpty())
                ;
//        verify(workflowService).getById(1);
    }

    @Test
    void happy_path_getWorkflowTimelineByIdWithMember_ret200() throws Exception{
        Workflow workflow = new Workflow(1, 0, 1, 1, 1, 1, 1, "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "正常返回");
        responseMsg.getResponseMap().put("workflow",workflow);
        when(workflowService.getTimelineByWid(1)).thenReturn(responseMsg);
        mockMvc.perform(get("/workflow/timeline/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.responseMap.workflow").isNotEmpty());
        verify(workflowService).getTimelineByWid(1);
    }

    @Test
    void error_path_getWorkflowTimelineByIdWithMember_ret208() throws Exception{
        Workflow workflow = new Workflow(1, 0, 1, 1, 1, 1, 1, "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "正常返回");
        responseMsg.getResponseMap().put("workflow",workflow);
        when(workflowService.getById(1)).thenReturn(responseMsg);
        mockMvc.perform(get("/workflow/timeline/-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(208))
//                .andExpect(jsonPath("$.responseMap.workflow").isNotEmpty())
        ;
//        verify(workflowService).getById(1);
    }

    @Test
    void happy_path_proceedWorkflow_ret200() throws Exception{
        Workflow workflow = new Workflow(1, 0, 1, 1, 1, 1, 1, "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "正常返回");
        responseMsg.getResponseMap().put("workflow",workflow);
        String jsons[]={
                "{\"todo\":\"agree\"}",
                "{\"todo\":\"disagree\"}",
                "{ \"todo\": \"config\",\"git_repo\":\"git.com\", \"server_root\":\"./home\",\"mail_list\": \"hh\"}"
                ,"{ \"todo\": \"epg\",\"epgs\":[10,11,12]}"
                ,"{\"todo\": \"qa\", \"qas\": [13,14,15]}"
                ,"{\n" +
                "\"todo\": \"pm_member\",\n" +
                "\"members\": {\n" +
                "\"rd_leader\":15,\n" +
                "\"rd\":[16,17,18,19,20],\n" +
                "\"qa\":[21,22,23]\n" +
                "}\n" +
                "}"
                ,"{\n" +
                "\"todo\": \"pm_member\",\n" +
                "\"members\": {\n" +
                "\"rd_leader\":15,\n" +
                "\"rd\":[16,17,18,19,20],\n" +
                "\"qa1\":[21,22,23]\n" +
                "}\n" +
                "}"
                ,"{\"todo\": \"pm_authority\"}"
                ,"{\n" +
                "\"todo\": \"pm_function\",\n" +
                "\"function\":{\"000000\":\"0-1\"}\n" +
                "}"
                ,"{\"todo\": \"pm_start\"}"
                ,"{\"todo\": \"pm_submitting\"}"
                ,"{\"todo\": \"pm_submitted\"}"
                ,"{\"todo\": \"pm_archive\",\"archive_id\":0,\"content\":\"你就随便填吧反正没人看\"}"
                ,"{\"todo\": \"configurer_archive_done\"}"
        };
        when(workflowService.sup_agree(anyInt(),anyInt())).thenReturn(responseMsg);
        when(workflowService.sup_disagree(anyInt(),anyInt())).thenReturn(responseMsg);
        when(workflowService.configurer_config(anyInt(),anyInt(),any() ,any(),any())).thenReturn(responseMsg);
        when(workflowService.epg_config(anyInt(),anyInt(),any())).thenReturn(responseMsg);
        when(workflowService.qa_config(anyInt(),anyInt(),any())).thenReturn(responseMsg);
        when(workflowService.member_config(anyInt(),anyInt(),anyInt(),any(),any())).thenReturn(responseMsg);
        when(workflowService.pm_authority(anyInt(),anyInt())).thenReturn(responseMsg);
        when(workflowService.pm_function(anyInt(),anyInt(),any())).thenReturn(responseMsg);
        when(workflowService.pm_common_doing_flow(anyInt(),anyInt(),anyInt())).thenReturn(responseMsg);
        when(workflowService.pm_common_doing_flow(anyInt(),anyInt(),anyInt())).thenReturn(responseMsg);
        when(workflowService.pm_common_doing_flow(anyInt(),anyInt(),anyInt())).thenReturn(responseMsg);
        when(workflowService.pm_common_archive(anyInt(),anyInt(),anyInt(),any())).thenReturn(responseMsg);
        when(workflowService.configurer_after_archive(anyInt(),anyInt())).thenReturn(responseMsg);
        for (int i = 0; i < jsons.length; i++) {
            mockMvc.perform(put("/workflow/1/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsons[i]))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(200))
                    .andExpect(jsonPath("$.responseMap.workflow").isNotEmpty());
        }

    }

    @Test
    void error_path_proceedWorkflow_ret208() throws Exception{
        Workflow workflow = new Workflow(1, 0, 1, 1, 1, 1, 1, "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setStatusAndMessage(200, "正常返回");
        responseMsg.getResponseMap().put("workflow",workflow);
        String jsons[]={
                "{ \"todo\": \"config\",\"git_repo1\":\"git.com\", \"server_root\":\"./home\",\"mail_list\": \"hh\"}"
                ,"{ \"todo\": \"epg\",\"epgs1\":[10,11,12]}"
                ,"{ \"todo1\": \"epg\",\"epgs1\":[10,11,12]}"
                ,"{\"todo\": \"qa\", \"qas1\": [13,14,15]}"
                ,"{\n" +
                "\"todo\": \"pm_member\",\n" +
                "\"members1\": {\n" +
                "\"rd_leader\":15,\n" +
                "\"rd\":[16,17,18,19,20],\n" +
                "\"qa\":[21,22,23]\n" +
                "}\n" +
                "}","{\n" +
                "\"todo\": \"pm_member\",\n" +
                "\"members\": {\n" +
                "\"rd_leader1\":15,\n" +
                "\"rd\":[16,17,18,19,20],\n" +
                "\"qa\":[21,22,23]\n" +
                "}\n" +
                "}"
                ,"{\n" +
                "\"todo\": \"pm_function\",\n" +
                "\"function1\":{\"000000\":\"0-1\"}\n" +
                "}"
                ,"{\"todo\": \"pm_archive\",\"archive_id1\":0,\"content\":\"你就随便填吧反正没人看\"}"
                ,"{\"todo\": \"pm_archive\",\"archive_id\":-1,\"content\":\"你就随便填吧反正没人看\"}"
        };
        when(workflowService.sup_agree(anyInt(),anyInt())).thenReturn(responseMsg);
        when(workflowService.sup_disagree(anyInt(),anyInt())).thenReturn(responseMsg);
        when(workflowService.configurer_config(anyInt(),anyInt(),any() ,any(),any())).thenReturn(responseMsg);
        when(workflowService.epg_config(anyInt(),anyInt(),any())).thenReturn(responseMsg);
        when(workflowService.qa_config(anyInt(),anyInt(),any())).thenReturn(responseMsg);
        when(workflowService.member_config(anyInt(),anyInt(),anyInt(),any(),any())).thenReturn(responseMsg);
        when(workflowService.pm_authority(anyInt(),anyInt())).thenReturn(responseMsg);
        when(workflowService.pm_function(anyInt(),anyInt(),any())).thenReturn(responseMsg);
        when(workflowService.pm_common_doing_flow(anyInt(),anyInt(),anyInt())).thenReturn(responseMsg);
        when(workflowService.pm_common_doing_flow(anyInt(),anyInt(),anyInt())).thenReturn(responseMsg);
        when(workflowService.pm_common_doing_flow(anyInt(),anyInt(),anyInt())).thenReturn(responseMsg);
        when(workflowService.pm_common_archive(anyInt(),anyInt(),anyInt(),any())).thenReturn(responseMsg);
        when(workflowService.configurer_after_archive(anyInt(),anyInt())).thenReturn(responseMsg);
        for (int i = 0; i < jsons.length; i++) {
            mockMvc.perform(put("/workflow/1/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsons[i]))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(208))
//                    .andExpect(jsonPath("$.responseMap.workflow").isNotEmpty())
            ;
        }

    }
}