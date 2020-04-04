package com.achieveit.service;

import com.achieveit.config.DateUtil;
import com.achieveit.entity.*;
import com.achieveit.mapper.EmployeeProjectMapper;
import com.achieveit.mapper.ManhourMapper;
import com.achieveit.mapper.ProjectMapper;
import com.fasterxml.jackson.core.JsonParser;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class ManhourService {
    Logger logger = LoggerFactory.getLogger(getClass());
    ManhourMapper manhourMapper;
    EmployeeProjectMapper employeeProjectMapper;
    ProjectMapper projectMapper;

    public ManhourService(ManhourMapper manhourMapper, EmployeeProjectMapper employeeProjectMapper, ProjectMapper projectMapper) {
        this.manhourMapper = manhourMapper;
        this.employeeProjectMapper = employeeProjectMapper;
        this.projectMapper = projectMapper;
    }

    public ResponseMsg getPagedManhourByEid(int eid, int page, int length){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<Manhour> pws = manhourMapper.getByEidCascade(eid);
            List<Manhour> pws_paged = new ArrayList<Manhour>();
            for(int i=page*length;i<page*length+length;i++){
                if(i<pws.size()){
                    pws.get(i).setFunction_desc_by_FunctionObject(pws.get(i).getFid(), JSONObject.fromObject(pws.get(i).getEmployeeProject().getProject().getFunction()));
                    pws_paged.add(pws.get(i));
                }
            }
            int page_length = (pws.size()+length-1)/length;
            msg.setStatusAndMessage(200, "请求正常");
            msg.getResponseMap().put("Manhour",pws_paged);
            msg.getResponseMap().put("page_length",page_length);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
    public ResponseMsg getFilteredPagedManhourByEid(int eid, int page, int length, Date date){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<Manhour> pws = manhourMapper.getDatedByEidCascade(eid,date);
            List<Manhour> pws_paged = new ArrayList<Manhour>();
            for(int i=page*length;i<page*length+length;i++){
                if(i<pws.size()){
                    pws.get(i).setFunction_desc_by_FunctionObject(pws.get(i).getFid(), JSONObject.fromObject(pws.get(i).getEmployeeProject().getProject().getFunction()));
                    pws_paged.add(pws.get(i));
                }
            }
            int page_length = (pws.size()+length-1)/length;
            msg.setStatusAndMessage(200, "请求正常");
            msg.getResponseMap().put("Manhour",pws_paged);
            msg.getResponseMap().put("page_length",page_length);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg getActivity() {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            msg.getResponseMap().put("activities", manhourMapper.getActivity());
            msg.setStatusAndMessage(200, "正常取出");
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }


    public ResponseMsg newManhour(int eid, String pid, int aid, int fid, Timestamp startdate, Timestamp enddate) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<EmployeeProject> employeeProjects=employeeProjectMapper.getEmployeeProject(pid, eid);
            if(employeeProjects==null||employeeProjects.size()==0){
                msg.setStatusAndMessage(212, "eid/pid组合出错");
                return msg;
            }
            EmployeeProject employeeProject=employeeProjects.get(0);
            if(employeeProject.getSup()==null){
                msg.setStatusAndMessage(216, "没有sup");
                return msg;
            }
            int epid = employeeProject.getEpid();

            Project project= projectMapper.getByPid(pid);
            JSONObject functionObject=JSONObject.fromObject(project.getFunction());

            Activity activity = manhourMapper.getActivityByAid(aid);
            if(activity==null){
                msg.setStatusAndMessage(214, "aid出错");
                return msg;
            }

            Date date=new Date(Calendar.getInstance().getTimeInMillis());
            Manhour manhour=new Manhour(0, fid, date,startdate,enddate,"unfilled", epid,aid);
            manhour.setFunction_desc_by_FunctionObject(fid, functionObject);

            int ret = manhourMapper.add(manhour);
            if(ret>0){
                msg.getResponseMap().put("manhour",manhour);
                msg.setStatusAndMessage(200, "正常新建manhour");
            }
            else{
                msg.setStatusAndMessage(212, "新建manhour失败啦");
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg deleteManhour(int eid, int mid) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            Manhour manhour=manhourMapper.getByMidCascade(mid);
            if(manhour.getEmployeeProject().getEmployee_id()!=eid){
                msg.setStatusAndMessage(210, "你不是Manhour发起者不能删除");
                return msg;
            }
            int ret=manhourMapper.deleteManhour(mid);
            if(ret>0){
                msg.setStatusAndMessage(200, "删除成功");
            }else{
                msg.setStatusAndMessage(212, "删除失败");
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

//    "{\n" +
//            "    \"fid\":0,\n" +
//            "    \"starttime\":\"2020-04-08T16:00:00.000Z\",\n" +
//            "    \"endtime\":\"2020-04-09T16:00:00.000Z\",\n" +
//            "    \"status\":\"unfilled/unchecked/checked\",\n" +
//            "    \"activity_id\":1\n" +
//            "}\n" +
//            "如果提供了status则只检查status，则需要检查eid的身份是否是manhour发起者在项目中的上级\n " +
//            "status具有语义-unfilled[发起者还可修改]-unchecked[不通过]-checked[通过]\n" +
//            "上级打回方是将status置为unfilled，如果变为unfilled必须要在现在的状态是checked/unchecked而且现在距离manhour的date在三天内"
    public ResponseMsg updateManhour(int eid, int mid, Map param) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            Manhour manhour=manhourMapper.getByMidCascade(mid);
            if(param.containsKey("status")){//Manhour的发起者的上级进行状态变更
                List<EmployeeProject> employeeProjects = employeeProjectMapper.getEmployeeProject(manhour.getEmployeeProject().getProject_id(), eid);
                if(employeeProjects.size()==0){
                    msg.setStatusAndMessage(210, "你不是可操作者");
                    return msg;
                }
                EmployeeProject sup_ep = employeeProjects.get(0);
                if(manhour.getEmployeeProject().getSup()==null||sup_ep.getEpid()!=manhour.getEmployeeProject().getSup().getEpid()){
                    msg.setStatusAndMessage(210, "你不是可操作者");
                    return msg;
                }
                String status = param.get("status").toString();
                if(!status.equals("unfilled")&&!status.equals("unchecked")&&!status.equals("checked")){
                    msg.setStatusAndMessage(212, "不存在的状态，状态参数错误");
                    return msg;
                }
                manhour.setStatus(status);
            }
            else{//Manhour的发起者自己更新除了status的内容
                if(!manhour.getStatus().equals("unfilled")){
                    msg.setStatusAndMessage(216, "非unfilled状态下无法更新");
                    return msg;
                }
                List<EmployeeProject> employeeProjects = employeeProjectMapper.getEmployeeProject(manhour.getEmployeeProject().getProject_id(), eid);
                if(employeeProjects.size()==0){
                    msg.setStatusAndMessage(210, "你不是可操作者");
                    return msg;
                }
                EmployeeProject me_ep = employeeProjects.get(0);
                if(me_ep.getEpid()!=manhour.getEmployee_project_id()){
                    msg.setStatusAndMessage(210, "你不是可操作者");
                    return msg;
                }
                if(param.containsKey("fid")){
                    manhour.setFid(Integer.valueOf(param.get("fid").toString()));
                }
                if(param.containsKey("starttime")){
                    Timestamp startdate=null;
                    String[] startString = param.get("starttime").toString().split("T");
                    startdate = DateUtil.String2Timestamp(startString[0]+" "+startString[1].split("\\.")[0], "yyyy-MM-dd HH:mm:ss");
                    manhour.setStarttime(startdate);
                }
                if(param.containsKey("endtime")){
                    Timestamp enddate = null;
                    String[] endString = param.get("endtime").toString().split("T");
                    enddate = DateUtil.String2Timestamp(endString[0]+" "+endString[1].split("\\.")[0], "yyyy-MM-dd HH:mm:ss");
                    manhour.setEndtime(enddate);
                }
                if(param.containsKey("activity_id")){
                    manhour.setActivity_id(Integer.valueOf(param.get("activity_id").toString()));
                }
            }
            int ret = manhourMapper.update(manhour);
            if(ret>0){
                msg.getResponseMap().put("manhour", manhour);
                msg.setStatusAndMessage(200, "更新成功");
            }
            else{

                msg.setStatusAndMessage(214, "更新失败");
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg getSubManhour(int eid) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            msg.getResponseMap().put("activities", manhourMapper.getSubManhour(eid));
            msg.setStatusAndMessage(200, "正常取出");
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
