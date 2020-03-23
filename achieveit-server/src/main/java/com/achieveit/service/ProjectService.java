package com.achieveit.service;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.Project;
import com.achieveit.entity.ProjectWorkflow;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Workflow;
import com.achieveit.mapper.ProjectMapper;
import com.achieveit.mapper.WorkflowMapper;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    JwtToken jwtToken;
    public ProjectService(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    private final ProjectMapper projectMapper;

    public ResponseMsg getProjectToCheck(int eid){

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<ProjectWorkflow> pws = projectMapper.getBySupEid(eid);
            msg.setStatusAndMessage(200, "请求正常");
            msg.getResponseMap().put("ProjectWorkflows",pws);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
