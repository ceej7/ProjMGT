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

    public ResponseMsg getProjectToCheck(String authHeader){

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            Claims claims = jwtToken.getClaimByToken(authHeader);
            if (claims == null ) {
                msg.setStatusAndMessage(204, "Token无效");
            }
            else if (JwtToken.isTokenExpired(claims.getExpiration())){
                msg.setStatusAndMessage(206, "Token过期");
            }
            else{
                int userId = Integer.valueOf(claims.getSubject());
                List<ProjectWorkflow> pws = projectMapper.getBySupEid(userId);
                msg.getResponseMap().put("ProjectWorkflows",pws);
                msg.setStatusAndMessage(200, "查询正常,ProjectWorkflows数组一一对应");
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
