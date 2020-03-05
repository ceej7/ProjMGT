package com.achieveit.controller;

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
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "工作流接口", value = "工作流的关键API")
public class WorkflowController {
    Logger logger = LoggerFactory.getLogger(getClass());


    public WorkflowController(MailService mailService, FileService fileService, WorkflowService workflowService, EmployeeService employeeService) {
        this.mailService = mailService;
        this.fileService = fileService;
        this.workflowService = workflowService;
        this.employeeService = employeeService;
    }

    private final MailService mailService;
    private final FileService fileService;
    private final WorkflowService workflowService;
    private final EmployeeService employeeService;


    @ResponseBody
    @GetMapping("/workflow/{wid}")
    @ApiOperation("根据id获得一个workflow,以及对应的组织级成员")
    public ResponseMsg getWorkflowById(@PathVariable int wid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            Workflow w =workflowService.getById(wid);
            if(w!=null){
                responseMsg.getResponseMap().put("workflow",w);
                if(w.getPm_eid()!=null) responseMsg.getResponseMap().put("pm",employeeService.getById(w.getPm_eid()));
                if(w.getSup_eid()!=null) responseMsg.getResponseMap().put("sup",employeeService.getById(w.getSup_eid()));
                if(w.getConfigurer_eid()!=null) responseMsg.getResponseMap().put("configurer",employeeService.getById(w.getConfigurer_eid()));
                if(w.getEpgleader_eid()!=null) responseMsg.getResponseMap().put("epgleader",employeeService.getById(w.getEpgleader_eid()));
                if(w.getQamanager_eid()!=null) responseMsg.getResponseMap().put("qamanager",employeeService.getById(w.getQamanager_eid()));
                responseMsg.setStatusAndMessage(200,"查询成功，附带组织级成员的具体信息");
            }
            else{
                responseMsg.setStatusAndMessage(202,"查询不着");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
}
