package com.achieveit.service;

import com.achieveit.entity.EmployeeProject;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Risk;
import com.achieveit.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RiskService {
    Logger logger = LoggerFactory.getLogger(getClass());
    private final ProjectMapper projectMapper;
    private final WorkflowMapper workflowMapper;
    private final EmployeeMapper employeeMapper;
    private final RiskMapper riskMapper;
    private final EmployeeProjectMapper employeeProjectMapper;


    public RiskService(ProjectMapper projectMapper, WorkflowMapper workflowMapper, EmployeeMapper employeeMapper, RiskMapper riskMapper, EmployeeProjectMapper employeeProjectMapper) {
        this.projectMapper = projectMapper;
        this.workflowMapper = workflowMapper;
        this.employeeMapper = employeeMapper;
        this.riskMapper = riskMapper;
        this.employeeProjectMapper = employeeProjectMapper;
    }

    public ResponseMsg getByProjectId(String pid) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            msg.getResponseMap().put("risks", riskMapper.getByPidCascade(pid));
            msg.setStatusAndMessage(200, "正常获取");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg getByTemplate() {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            msg.getResponseMap().put("risks", riskMapper.getByTemplate());
            msg.setStatusAndMessage(200, "正常获取");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg delete(int eid, int rid) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            Risk risk = riskMapper.getByRid(rid);
            if(risk==null){
                msg.setStatusAndMessage(210, "不存在的risk");
                return msg;
            }
            if(risk.getEmployee_id()!=eid){
                msg.setStatusAndMessage(212, "你不是risk owner不能删除");
                return msg;
            }
            int ret = riskMapper.delete(rid);
            if(ret>0){
                msg.setStatusAndMessage(200, "删除成功");
                return msg;
            }
            else{
                msg.setStatusAndMessage(214, "删除失败");
                return msg;
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg add(int eid, String pid, String type, String desc, String grade, String influence, String strategy, int frequency ,ArrayList<Integer> ep_ids) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<EmployeeProject> employeeProjects= employeeProjectMapper.getEmployeeProject(pid, eid);
            if(employeeProjects==null||employeeProjects.size()==0){
                msg.setStatusAndMessage(210, "员工"+eid+"不在"+pid+"项目中");
                return msg;
            }
            EmployeeProject creator = employeeProjects.get(0);
            Risk risk=new Risk(0, type, desc, grade, strategy, influence, frequency, false,eid, pid);
            int ret = riskMapper.add(risk);
            if(ret>0){
                for (int i = 0; i < ep_ids.size(); i++) {
                    EmployeeProject attender=employeeProjectMapper.getByEpid(ep_ids.get(i));
                    if(attender!=null&&attender.getProject_id().equals(pid)){
                        riskMapper.addRelation(risk.getRid(),attender.getEpid());
                    }
                }
                msg.getResponseMap().put("risk", risk);
                msg.setStatusAndMessage(200, "新建成功");
                return msg;
            }
            else{
                msg.setStatusAndMessage(212, "新建失败");
                return msg;
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg update(int eid, int rid, Map param) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            Risk risk = riskMapper.getByRid(rid);
            if(risk==null){
                msg.setStatusAndMessage(210, "没有这个risk");
                return msg;
            }
            if(!risk.getEmployee_id().equals(eid)){
                msg.setStatusAndMessage(212, "你不是拥有者");
                return msg;
            }
            if(param.containsKey("type")){
                String type = param.get("type").toString();
                risk.setType(type);
            }
            if(param.containsKey("desc")){
                String desc= param.get("desc").toString();
                risk.setDesc(desc);
            }
            if(param.containsKey("grade")){
                String grade= param.get("grade").toString();
                ArrayList<String> pList = new ArrayList<String>();
                for(int i=0;i<10;i++){
                    pList.add("p"+i);
                }
                if(!pList.contains(grade)){
                    msg.setStatusAndMessage(208, "Grade参数解析错误");
                    return msg;
                }
                risk.setGrade(grade);
            }
            if(param.containsKey("influence")){
                String influence= param.get("influence").toString();
                risk.setInfluence(influence);
            }
            if(param.containsKey("strategy")){
                String strategy= param.get("strategy").toString();
                risk.setStrategy(strategy);
            }
            if(param.containsKey("frequency")){
                int frequency = Integer.valueOf(param.get("frequency").toString());
                risk.setFrequency(frequency);
            }
            int ret = riskMapper.update(risk);

            if(param.containsKey("ep_ids")){
                riskMapper.deleteRelationsByRid(rid);
                ArrayList<Integer> ep_ids= (ArrayList<Integer> )param.get("ep_ids");
                for (int i = 0; i < ep_ids.size(); i++) {
                    EmployeeProject attender=employeeProjectMapper.getByEpid(ep_ids.get(i));
                    if(attender!=null&&attender.getProject_id().equals(risk.getProject_id())){
                        riskMapper.addRelation(risk.getRid(),attender.getEpid());
                    }
                }
            }
            risk = riskMapper.getByRid(rid);
            msg.getResponseMap().put("risk",risk);
            msg.setStatusAndMessage(200, "正常更新了一个Risk");
            return msg;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
