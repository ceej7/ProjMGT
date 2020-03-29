package com.achieveit.controller;

import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Workflow;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import com.achieveit.service.WorkflowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WorkflowControllerTest {

    MailService mailService;
    FileService fileService;
    WorkflowService workflowService;
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mailService = mock(MailService.class);
        fileService = mock(FileService.class);
        workflowService = mock(WorkflowService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new WorkflowController(mailService,fileService, workflowService)).build();
    }

    @Test
    void get_workflow_by_correct_id_with_member() throws Exception{
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
}