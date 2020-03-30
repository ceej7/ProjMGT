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

    public ResponseMsg getById(String pid){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            Project pws = projectMapper.getByPidCascade(pid);
            msg.setStatusAndMessage(200, "请求正常");
            msg.getResponseMap().put("Project",pws);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

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
    public ResponseMsg getFilteredPagedProjectByEid(int eid, int page, int length,String name,String status){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            int bit_l;
            int bit_u;
            List<Project> pws=new ArrayList<Project>();
            List<Project> pws_paged = new ArrayList<Project>();
            if(name==null){
                name="";
            }
            if(status==null||!status.equals("done")){
                if(status==null){
                    bit_l=0;
                    bit_u=0x3fffffff;
                }else if(status.equals("doing")){
                    bit_l=256;
                    bit_u=2047;
                }
                else{ //if(status.equals("applying"))
                    bit_l=1;
                    bit_u=3;
                }
                pws = projectMapper.getNamedStatusByEidCascade(eid,name,bit_l,bit_u);
            }
            else{//if(status.equals("done"))
                bit_l=0;
                bit_u=0x1fffffff;
                List<Project> pws1 = projectMapper.getNamedStatusByEidCascade(eid,name,bit_l,bit_l);
                List<Project> pws2 = projectMapper.getNamedStatusByEidCascade(eid,name,bit_u,bit_u);
                pws.addAll(pws1);
                pws.addAll(pws2);
            }
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
