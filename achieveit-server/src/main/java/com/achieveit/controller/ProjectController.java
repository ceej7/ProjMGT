package com.achieveit.controller;

import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.ProjectService;
import com.achieveit.service.WorkflowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/project/toCheck")
    @ApiOperation("获取上级自己需要审核的项目, [Authorization, Bearer [token]] 键值对验证用户的token")
    public ResponseMsg getToCheckOfSup(@RequestHeader("Authorization") String authHeader){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        if(authHeader.split("Bearer").length!=2||!authHeader.split(" ")[0].equals("Bearer")){
            msg.setStatusAndMessage(202, "非法的token");
        }
        else{
            msg=projectService.getProjectToCheck(authHeader);
        }
        return msg;
    }
}
