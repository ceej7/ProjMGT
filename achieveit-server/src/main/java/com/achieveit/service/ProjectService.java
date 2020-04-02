package com.achieveit.service;

import com.achieveit.entity.*;
import com.achieveit.mapper.EmployeeMapper;
import com.achieveit.mapper.EmployeeProjectMapper;
import com.achieveit.mapper.ProjectMapper;
import com.achieveit.mapper.WorkflowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ProjectService {
    Logger logger = LoggerFactory.getLogger(getClass());
    public ProjectService(ProjectMapper projectMapper, WorkflowMapper workflowMapper, EmployeeMapper employeeMapper, EmployeeProjectMapper employeeProjectMapper) {
        this.projectMapper = projectMapper;
        this.workflowMapper = workflowMapper;
        this.employeeMapper = employeeMapper;
        this.employeeProjectMapper = employeeProjectMapper;
    }

    private final ProjectMapper projectMapper;
    private final WorkflowMapper workflowMapper;
    private final EmployeeMapper employeeMapper;
    private final EmployeeProjectMapper employeeProjectMapper;

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

    public ResponseMsg newProject(String name, Timestamp startdate, Timestamp enddate, String technique, String domain, int client,
                                  int configurer_eid, int epgleader_eid, int qamanager_eid, int pm_eid) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            Employee pm = employeeMapper.getByEid(pm_eid);//检查pm
            if(!pm.getTitle().equals("pm")){
                msg.setStatusAndMessage(216, "你不是pm不可以新建项目哦");
                return msg;
            }
            // 生成PID
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int year = calendar.get(Calendar.YEAR);//获取当前年份
            int client_id = client;
            char types[] = {'D','O','M','S'};
            Random random=new Random(new Date().getTime());
            int nexti  =random.nextInt();
            if(nexti<0) nexti=-nexti;
            char type = types[nexti%4];
            int seq_num =-1;
            String header = String.format("%04d",year)+String.format("%04d",client_id)+type;
            List<Project> projects = projectMapper.getAllProjectIds();//判断顺序号
            ArrayList<Integer> existSeqNums = new ArrayList<Integer>();
            for (int i = 0; i < projects.size(); i++) {
                if(projects.get(i).getPid().contains(header)){
                    existSeqNums.add(Integer.valueOf(projects.get(i).getPid().split(header)[1]));
                }
            }
            existSeqNums.sort(new Comparator<Integer>() {//顺序号排序
                @Override
                public int compare(Integer o1, Integer o2) {
                    int i = o1 > o2 ? 1 : -1;
                    return i;
                }
            });
            for(int i=0;i<100;i++){//确定顺序号
                if(i<existSeqNums.size()&&i!=existSeqNums.get(i)){
                    seq_num=i;
                    break;
                }
                else if(i>=existSeqNums.size()){
                    seq_num=i;
                    break;
                }
            }
            if(seq_num<0||seq_num>100)//处理非两位的顺序号
            {
               msg.setStatusAndMessage(212, "异常的顺序号，大概是不能对这个客户新建项目了吧0 0？不然你再试试？");
               return msg;
            }
            String pid = header + String.format("%02d",seq_num);// 生成PID
            Project project = new Project(pid,name , startdate, enddate, technique, domain, null, client_id, 0);
            int ret = projectMapper.add(project);//生成项目
            if(ret<=0){
                msg.setStatusAndMessage(214, "新增项目失败啦0 0？不然你再试试？");
                return msg;
            }
            Workflow new_workflow = new Workflow(0, 1, pm_eid, pm.getSup_eid(),configurer_eid, epgleader_eid, qamanager_eid,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
            workflowMapper.addWorkflow(new_workflow);
            projectMapper.updateWorkflow(pid,new_workflow.getWid());//绑定工作流
            workflowMapper.addTimeline(new_workflow.getWid(),"applying",pm_eid);
            //将pm加入成员列表
            byte[] pm_authority={3};
            EmployeeProject pm_ep=new EmployeeProject(0, pm_authority, null, pid,pm_eid);
            employeeProjectMapper.addEmployeeProject(pm_ep);
            employeeProjectMapper.addEmployeeRoleProject(new EmployeeRoleProject("pm", pm_ep.getEpid()));

            msg.getResponseMap().put("project",projectMapper.getByPidCascade(pid));
            msg.setStatusAndMessage(200,"正常新增");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
