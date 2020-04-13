package com.achieveit.service;

import com.achieveit.config.DateUtil;
import com.achieveit.entity.*;
import com.achieveit.mapper.EmployeeMapper;
import com.achieveit.mapper.EmployeeProjectMapper;
import com.achieveit.mapper.ProjectMapper;
import com.achieveit.mapper.WorkflowMapper;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ProjectService {
    Logger logger = LoggerFactory.getLogger(getClass());
    private final ProjectMapper projectMapper;
    private final WorkflowMapper workflowMapper;
    private final EmployeeMapper employeeMapper;
    private final EmployeeProjectMapper employeeProjectMapper;

    public ProjectService(ProjectMapper projectMapper, WorkflowMapper workflowMapper, EmployeeMapper employeeMapper, EmployeeProjectMapper employeeProjectMapper) {
        this.projectMapper = projectMapper;
        this.workflowMapper = workflowMapper;
        this.employeeMapper = employeeMapper;
        this.employeeProjectMapper = employeeProjectMapper;
    }

    public ResponseMsg getById(String pid){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            Project pws = projectMapper.getByPidCascade(pid);
            List<EmployeeProject> employeeProjects=employeeProjectMapper.getByPidCascade(pid);
            msg.setStatusAndMessage(200, "请求正常");
            msg.getResponseMap().put("Project",pws);
            msg.getResponseMap().put("EmployeeProjects",employeeProjects);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg getProjectToManage(int eid){

        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            Employee employee = employeeMapper.getByEid(eid);
            String title = employee.getTitle();
            if(title.equals("pm_manager")){
                List<Project> pws = projectMapper.getBySupEidCascade(eid);
                msg.setStatusAndMessage(200, "请求正常");
                msg.getResponseMap().put("Project",pws);
            }else if(title.equals("pm")){
                List<Project> pws = projectMapper.getByPmEidCascade(eid);
                msg.setStatusAndMessage(200, "请求正常");
                msg.getResponseMap().put("Project",pws);
            }else if(title.equals("qa_manager")){
                List<Project> pws = projectMapper.getByQaManagerEidCascade(eid);
                msg.setStatusAndMessage(200, "请求正常");
                msg.getResponseMap().put("Project",pws);
            }else if(title.equals("epg_leader")){
                List<Project> pws = projectMapper.getByEpgLeaderEidCascade(eid);
                msg.setStatusAndMessage(200, "请求正常");
                msg.getResponseMap().put("Project",pws);
            }else if(title.equals("configurer")){
                List<Project> pws = projectMapper.getByConfigurerEidCascade(eid);
                msg.setStatusAndMessage(200, "请求正常");
                msg.getResponseMap().put("Project",pws);
            }else{
                msg.setStatusAndMessage(208, "你不是项目管理级别人员");
            }
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
                    bit_l=4;
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
                List<Project> pws1 = projectMapper.getNamedStatusByEidCascade(eid,name,0,0);
                List<Project> pws2 = projectMapper.getNamedStatusByEidCascade(eid,name,2048,0x1fffffff);
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

    public ResponseMsg removeEmployeeProject(int epid) {//'pm','rd_leader','qa_leader','rd','qa','epg'
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            EmployeeProject employeeProject = employeeProjectMapper.getByEpid(epid);
            if(employeeProject.getRoles()!=null){//检查这个人的角色是不是pm/rd_leader/qa_leader
                for (int i = 0; i < employeeProject.getRoles().size(); i++) {
                    String role = employeeProject.getRoles().get(i).getRole();
                    if(role.equals("pm")||role.equals("rd_leader")||role.equals("qa_leader")){
                        msg.setStatusAndMessage(210, "不能删除pm/rd_leader/qa_leader");
                        return msg;
                    }
                }
            }
            int i = employeeProjectMapper.delete(epid);
            if(i>0){
                msg.setStatusAndMessage(200, "正常删除");
            }
            else{
                msg.setStatusAndMessage(212, "不存在这个成员");
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg updateEmployeeProjectAndRole(ArrayList<String> roles, int eid, String pid) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            //移除多余的leader级角色
            roles.removeIf(role -> role.equals("pm"));
            roles.removeIf(role -> role.equals("rd_leader"));
            roles.removeIf(role -> role.equals("qa_leader"));

            //分别取出项目中的pm, rdLeader, qaLeader
            List<EmployeeProject> employeeProjects= employeeProjectMapper.getEmployeeProject(pid, eid);
            EmployeeProject pmEp= employeeProjectMapper.getEmployeeProjectByRole(pid, "pm").get(0);
            EmployeeProject rdEp= employeeProjectMapper.getEmployeeProjectByRole(pid, "rd_leader").get(0);
            EmployeeProject qaEp= employeeProjectMapper.getEmployeeProjectByRole(pid, "qa_leader").get(0);

            if(employeeProjects==null||employeeProjects.size()==0){//需要新增这个member
                //先新增ep
                Integer sup_epid=null;
                if(roles.contains("epg")){
                    sup_epid=pmEp.getEpid();
                }else if(roles.contains("rd")){
                    sup_epid=rdEp.getEpid();
                }else if(roles.contains("qa")){
                    sup_epid=qaEp.getEpid();
                }
                if(sup_epid==null){
                    msg.setStatusAndMessage(212, "无法配置上级");
                    return msg;
                }
                byte authority[]={0};
                EmployeeProject employeeProject=new EmployeeProject(0, authority, sup_epid,pid,eid);
                employeeProjectMapper.addEmployeeProject(employeeProject);
                //再插入role
                for (int i = 0; i < roles.size(); i++) {
                    EmployeeRoleProject employeeRoleProject = new EmployeeRoleProject(roles.get(i), employeeProject.getEpid());
                    employeeProjectMapper.addEmployeeRoleProject(employeeRoleProject);
                }
            }else{//不需要新增
                EmployeeProject employeeProject=employeeProjects.get(0);
                int epid=employeeProject.getEpid();
                // 特殊处理自己本身就是leader的情况
                if(epid == pmEp.getEpid()){
                    //我自己本身就是leader级别的
                    roles.add("pm");
                }
                if(epid == qaEp.getEpid()){
                    roles.add("qa_leader");
                }
                if(epid == rdEp.getEpid()){
                    roles.add("rd_leader");
                }
                //先删除所有的role
                employeeProjectMapper.deleteEmployeeRoleProject(epid);
                //然后新增role
                for (int i = 0; i < roles.size(); i++) {
                    EmployeeRoleProject employeeRoleProject = new EmployeeRoleProject(roles.get(i), epid);
                    employeeProjectMapper.addEmployeeRoleProject(employeeRoleProject);
                }
            }
            employeeProjects= employeeProjectMapper.getEmployeeProject(pid, eid);
            msg.getResponseMap().put("employeeProject",employeeProjects.get(0));
            msg.setStatusAndMessage(200, "正常设定了角色");
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg updateProjectInfo(String pid, Map param) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            Project project = projectMapper.getByPid(pid);
            //name，starttime，endtime，technique，domain，function
            if(param.containsKey("name")){
                project.setName(param.get("name").toString());
            }
            if(param.containsKey("starttime")){
                Timestamp startdate = DateUtil.fromInputUTC2Timestamp(param.get("starttime").toString());
                project.setStarttime(startdate);
            }
            if(param.containsKey("endtime")){
                Timestamp enddate = DateUtil.fromInputUTC2Timestamp(param.get("endtime").toString());
                project.setEndtime(enddate);
            }
            if(param.containsKey("technique")){
                project.setTechnique(param.get("technique").toString());
            }
            if(param.containsKey("domain")){
                project.setDomain(param.get("domain").toString());
            }
            if(param.containsKey("function")){
                project.setFunction((Map)param.get("function"));
            }
            projectMapper.updateProject(project);
            msg.setStatusAndMessage(200, "正常更新");
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg getNotInProject(String pid) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try {
            List<Employee> employees = employeeMapper.getAll();
            List<EmployeeProject> employeeProjects = employeeProjectMapper.getByPidCascade(pid);
            ArrayList<Integer> includes = new ArrayList<>();
            for (int i = 0; i < employeeProjects.size(); i++) {
                includes.add(employeeProjects.get(i).getEmployee_id());
            }
            List<Employee> employees2Output = new ArrayList<>();
            for (int i = 0; i < employees.size(); i++) {
                if (!includes.contains(employees.get(i).getEid())) {
                    employees2Output.add(employees.get(i));
                }
            }
            msg.getResponseMap().put("employees",employees2Output);
            msg.setStatusAndMessage(200, "正常查询");
            return msg;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
