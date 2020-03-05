package com.achieveit.controller;

import com.achieveit.entity.Project;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Workflow;
import com.achieveit.service.ProjectService;
import com.achieveit.service.WorkflowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "项目接口", value="以项目为主体的请求")
public class ProjectController {
    Logger logger = LoggerFactory.getLogger(getClass());

    public ProjectController(ProjectService projectService, WorkflowService workflowService) {
        this.projectService = projectService;
        this.workflowService = workflowService;
    }

    private final ProjectService projectService;
    private final WorkflowService workflowService;

    @ResponseBody
    @GetMapping("/project/toCheck/{sup_id}")
    @ApiOperation("获取上级自己需要审核的项目")
    public ResponseMsg getToCheck(@PathVariable int sup_id){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<Workflow> workflows = workflowService.getSupToCheck(sup_id);
            List<Project> projects = projectService.getByWorkflow(workflows);
            msg.getResponseMap().put("workflows",workflows);
            msg.getResponseMap().put("projects",projects);
            msg.setStatusAndMessage(200, "查询正常projects和workflows数组一一对应");
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

}
