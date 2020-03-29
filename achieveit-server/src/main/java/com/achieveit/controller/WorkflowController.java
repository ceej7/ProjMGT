package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.Employee;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Workflow;
import com.achieveit.service.EmployeeService;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import com.achieveit.service.WorkflowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "工作流接口", value = "工作流的关键API")
public class WorkflowController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    JwtToken jwtToken;
    private final MailService mailService;
    private final FileService fileService;
    private final WorkflowService workflowService;

    public WorkflowController(MailService mailService, FileService fileService, WorkflowService workflowService, JwtToken jwtToken) {
        this.mailService = mailService;
        this.fileService = fileService;
        this.workflowService = workflowService;
        this.jwtToken=jwtToken;
    }

    @ResponseBody
    @GetMapping("/workflow/{wid}")
    @ApiOperation("根据id获得一个workflow,以及对应的组织级成员")
    public ResponseMsg getWorkflowByIdWithMember(@PathVariable int wid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        if(wid<0){
            responseMsg.setStatusAndMessage(202, "参数无效");
        }
        else
            responseMsg = workflowService.getById(wid);
        return responseMsg;
    }
}
