package com.achieveit.service;

import com.achieveit.entity.Defect;
import com.achieveit.entity.EmployeeProject;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.mapper.DefectMapper;
import com.achieveit.mapper.EmployeeProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DefectService {
    Logger logger = LoggerFactory.getLogger(getClass());
    DefectMapper defectMapper;
    EmployeeProjectMapper employeeProjectMapper;

    public DefectService(DefectMapper defectMapper, EmployeeProjectMapper employeeProjectMapper) {
        this.defectMapper = defectMapper;
        this.employeeProjectMapper=employeeProjectMapper;
    }

    public ResponseMsg getPagedDefectByEid(int eid, int page, int length){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<Defect> pws = defectMapper.getByEidCascade(eid);
            List<Defect> pws_paged = new ArrayList<Defect>();
            for(int i=page*length;i<page*length+length;i++){
                if(i<pws.size()){
                    pws_paged.add(pws.get(i));
                }
            }
            int page_length = (pws.size()+length-1)/length;
            msg.setStatusAndMessage(200, "请求正常");
            msg.getResponseMap().put("Defect",pws_paged);
            msg.getResponseMap().put("page_length",page_length);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
    public ResponseMsg getFilteredPagedDefectByEid(int eid, int page, int length,String desc,String status){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<Defect> pws_raw=null;
            if(desc!=null)
                pws_raw=defectMapper.getDescedByEidCascade(eid,desc);
            else
                pws_raw=defectMapper.getByEidCascade(eid);

            List<Defect> pws =new ArrayList<>();
            if(status!=null){//如果有状态则对其filter
                for (int i = 0; i < pws_raw.size(); i++) {
                    if(pws_raw.get(i).getStatus().equals(status)){
                        pws.add(pws_raw.get(i));
                    }
                }
            }
            else{//否则不filter
                pws=pws_raw;
            }

            List<Defect> pws_paged = new ArrayList<Defect>();
            for(int i=page*length;i<page*length+length;i++){
                if(i<pws.size()){
                    pws_paged.add(pws.get(i));
                }
            }
            int page_length = (pws.size()+length-1)/length;
            msg.setStatusAndMessage(200, "请求正常");
            msg.getResponseMap().put("Defect",pws_paged);
            msg.getResponseMap().put("page_length",page_length);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg getByPid(String pid) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<Defect> pws = defectMapper.getByPidCascade(pid);
            msg.getResponseMap().put("Defect", pws);
            msg.setStatusAndMessage(200, "正常获取了项目"+pid+"名下的Defect");
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg deleteDefect(int eid, int did) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            Defect defect = defectMapper.getByDid(did);
            if(defect==null){
                msg.setStatusAndMessage(216, "不存在此defect");
                return msg;
            }
            String pid = defect.getProject_id();
            List<EmployeeProject> employeeProjects=employeeProjectMapper.getEmployeeProject(pid,eid);
            if(employeeProjects==null||employeeProjects.size()<=0){
                msg.setStatusAndMessage(212, "没有这员工");
                return msg;
            }
            EmployeeProject employeeProject=employeeProjects.get(0);
            boolean toConfig= false;
            if(employeeProject.getDefect_authority()[0]==3){//pm
                //都可以配置
                toConfig=true;
            }else if(employeeProject.getDefect_authority()[0]==2){//qa_leader
                if(defect.getAuthority()[0]==0||defect.getAuthority()[0]==2|| defect.getEmployee_project_id().equals(employeeProject.getEpid())){
                    toConfig=true;
                }
            }else if(employeeProject.getDefect_authority()[0]==1){//rd_leader
                if(defect.getAuthority()[0]==0||defect.getAuthority()[0]==1|| defect.getEmployee_project_id().equals(employeeProject.getEpid())){
                    toConfig=true;
                }
            }else{//none
                if(defect.getEmployee_project_id().equals(employeeProject.getEpid())){
                    toConfig=true;
                }
            }
            if(toConfig){
                defectMapper.deleteByDid(did);
                msg.setStatusAndMessage(200, "正常删除");
            }
            else{
                msg.setStatusAndMessage(214,"你没有权限删除");
            }


        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg addDefect(int eid, String pid, String authority_desc, String desc, String git_repo, String commit) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<EmployeeProject> employeeProjects=employeeProjectMapper.getEmployeeProject(pid,eid);
            if(employeeProjects==null||employeeProjects.size()<=0){
                msg.setStatusAndMessage(212, "没有这员工");
                return msg;
            }
            EmployeeProject employeeProject=employeeProjects.get(0);
            int epid = employeeProject.getEpid();
            byte[] authority = {0};
            if(authority_desc.equals("pmAuthority")) authority[0]=3;
            else if(authority_desc.equals("qaLeaderAuthority")) authority[0]=2;
            else if(authority_desc.equals("rdLeaderAuthority")) authority[0]=1;
            else authority[0]=0;
            Defect defect=new Defect(0, authority, desc, git_repo, commit, "bug", pid, epid);
            int ret=defectMapper.add(defect);
            if(ret>0){
                msg.getResponseMap().put("defect", defect);
                msg.setStatusAndMessage(200, "成功新建");
            }

        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg updateDefect(int eid, int did, Map param) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            Defect defect = defectMapper.getByDid(did);
            if(defect==null){
                msg.setStatusAndMessage(216, "不存在此defect");
                return msg;
            }
            String pid = defect.getProject_id();
            List<EmployeeProject> employeeProjects=employeeProjectMapper.getEmployeeProject(pid,eid);
            if(employeeProjects==null||employeeProjects.size()<=0){
                msg.setStatusAndMessage(212, "没有这员工");
                return msg;
            }
            EmployeeProject employeeProject=employeeProjects.get(0);
            boolean toConfig= false;
            if(employeeProject.getDefect_authority()[0]==3){//pm
                //都可以配置
                toConfig=true;
            }else if(employeeProject.getDefect_authority()[0]==2){//qa_leader
                if(defect.getAuthority()[0]==0||defect.getAuthority()[0]==2|| defect.getEmployee_project_id().equals(employeeProject.getEpid())){
                    toConfig=true;
                }
            }else if(employeeProject.getDefect_authority()[0]==1){//rd_leader
                if(defect.getAuthority()[0]==0||defect.getAuthority()[0]==1|| defect.getEmployee_project_id().equals(employeeProject.getEpid())){
                    toConfig=true;
                }
            }else{//none
                if(defect.getEmployee_project_id().equals(employeeProject.getEpid())){
                    toConfig=true;
                }
            }
            if(toConfig){
                if(param.containsKey("authority_desc")){
                    String authority_desc=param.get("authority_desc").toString();
                    byte[] authority = {0};
                    if(authority_desc.equals("pmAuthority")) authority[0]=3;
                    else if(authority_desc.equals("qaLeaderAuthority")) authority[0]=2;
                    else if(authority_desc.equals("rdLeaderAuthority")) authority[0]=1;
                    else authority[0]=0;
                    defect.setAuthority(authority);
                } if(param.containsKey("desc")){
                    defect.setDesc(param.get("desc").toString());
                } if(param.containsKey("git_repo")){
                    defect.setGit_repo(param.get("git_repo").toString());
                } if(param.containsKey("commit")){
                    defect.setCommit(param.get("commit").toString());
                } if(param.containsKey("status")){
                    String status=param.get("status").toString();
                    if(!status.equals("bug")
                            &&!status.equals("reopen")
                            &&!status.equals("fixed")
                            &&!status.equals("wontfix")
                            &&!status.equals("feature")
                            &&!status.equals("closed")){
                        msg.setStatusAndMessage(218, "无效的status");
                        return msg;
                    }
                    defect.setStatus(status);
                }
                int ret = defectMapper.update(defect);
                if(ret>0){
                    msg.setStatusAndMessage(200, "成功更新");
                }
                else{
                    msg.setStatusAndMessage(214, "更新失败");
                }
            }
            else{
                msg.setStatusAndMessage(214,"你没有权限更新");
            }

        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
