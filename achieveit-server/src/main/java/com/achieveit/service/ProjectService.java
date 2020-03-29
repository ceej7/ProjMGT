package com.achieveit.service;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.Project;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.mapper.ProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            List<Project> pws = projectMapper.getBySupEidCascade(eid);
            msg.setStatusAndMessage(200, "请求正常");
            msg.getResponseMap().put("Project",pws);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg getPagedProjectByEid(int eid, int page, int length){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<Project> pws = projectMapper.getByEidCascade(eid);
            List<Project> pws_paged = new ArrayList<Project>();
            for(int i=page*length;i<page*length+length;i++){
                if(i<pws.size()){
                    pws_paged.add(pws.get(i));
                }
            }
            int page_length = (pws.size()+length-1)/length;
            msg.setStatusAndMessage(200, "请求正常");
            msg.getResponseMap().put("Project",pws_paged);
            msg.getResponseMap().put("page_length",page_length);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
    public ResponseMsg getFilteredPagedProjectByEid(int eid, int page, int length,String name){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<Project> pws = projectMapper.getNamedByEidCascade(eid,name);
            List<Project> pws_paged = new ArrayList<Project>();
            for(int i=page*length;i<page*length+length;i++){
                if(i<pws.size()){
                    pws_paged.add(pws.get(i));
                }
            }
            int page_length = (pws.size()+length-1)/length;
            msg.setStatusAndMessage(200, "请求正常");
            msg.getResponseMap().put("Project",pws_paged);
            msg.getResponseMap().put("page_length",page_length);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
