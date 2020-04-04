package com.achieveit.service;

import com.achieveit.entity.*;
import com.achieveit.mapper.EmployeeMapper;
import com.achieveit.mapper.EmployeeProjectMapper;
import com.achieveit.mapper.ProjectMapper;
import com.achieveit.mapper.WorkflowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkflowService {
    Logger logger = LoggerFactory.getLogger(getClass());
    private final WorkflowMapper workflowMapper;
    private final EmployeeMapper employeeMapper;
    private final ProjectMapper projectMapper;
    private final EmployeeProjectMapper employeeProjectMapper;


    public WorkflowService(WorkflowMapper workflowMapper, EmployeeMapper employeeMapper, ProjectMapper projectMapper,EmployeeProjectMapper employeeProjectMapper) {
        this.workflowMapper = workflowMapper;
        this.employeeMapper = employeeMapper;
        this.projectMapper = projectMapper;
        this.employeeProjectMapper = employeeProjectMapper;
    }

    //sup, configurer, epgleader, qamanager, pm
    boolean commonCheckImpl(int wid, ResponseMsg responseMsg, int eid, String role, int bit_place)  throws Exception{
        Workflow workflow = workflowMapper.getByWidCascade(wid);
        if(workflow==null){
            responseMsg.setStatusAndMessage(210,"没有查到这个workflow");
            return false;
        }
        int match_id=0;
        if(role.equals("sup"))
        {
            match_id=workflow.getSup_eid();
        }else if(role.equals("configurer"))
        {
            match_id=workflow.getConfigurer_eid();
        }else if(role.equals("epgleader"))
        {
            match_id=workflow.getEpgleader_eid();
        }else if(role.equals("qamanager"))
        {
            match_id=workflow.getQamanager_eid();
        }
        else{
            match_id=workflow.getPm_eid();
        }
        if(eid!=match_id){
            responseMsg.setStatusAndMessage(212,"你不是"+role+"没资格配置项目");
            return false;
        }
        WorkflowEngineService workflowEngineService=new WorkflowEngineService();
        int altered_flowbits=workflowEngineService.checkTodo(bit_place, workflow.getFlowbits());
        if(altered_flowbits==workflow.getFlowbits()){
            responseMsg.setStatusAndMessage(214,"项目状态没变，可能是没有到你的流程或者你的流程已经结束啦");
            return false;
        }
        workflowMapper.updateFlowBits(wid, altered_flowbits);//更新workflow的比特位
        return true;
    }
    int memberAttend_and_RoleAssign_commonImpl(String role, int sup_Epid, String pid, int eid) throws Exception{
        byte[] authority = {0};
        if(role.equals("pm")){
            authority[0]=3;
        }else
        if(role.equals("rd_leader")){
            authority[0]=1;
        }else
        if(role.equals("qa_leader")){
            authority[0]=2;
        }else
        {
            authority[0]=0;
        }
        EmployeeProject employeeProjectQa = new EmployeeProject(0, authority, sup_Epid, pid, eid);
        List<EmployeeProject> existenceTest = employeeProjectMapper.getEmployeeProject(pid,eid);//测试存在性
        if(existenceTest.size()>0){
            employeeProjectQa=existenceTest.get(0);//如果这个人存在的话，添加的角色需要收敛权限，比如它本来是rd_leader，现在又要当qa_leader了，那么就给他11的权限
            byte existAuthority = employeeProjectQa.getDefect_authority()[0];
            int combintedAuthority = (int)authority[0]|(int)existAuthority;
            employeeProjectQa.getDefect_authority()[0]=(byte)combintedAuthority;
        }
        else
            employeeProjectMapper.addEmployeeProject(employeeProjectQa);//不存在则新建
        employeeProjectMapper.addEmployeeRoleProject(new EmployeeRoleProject(role, employeeProjectQa.getEpid()));
        return employeeProjectQa.getEpid();
    }

    public ResponseMsg getById(int wid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            Workflow w =workflowMapper.getByWidCascade(wid);
            if(w!=null){
                responseMsg.getResponseMap().put("workflow",w);
                responseMsg.setStatusAndMessage(200,"查询成功，附带组织级成员的具体信息");
            }
            else{
                responseMsg.setStatusAndMessage(204,"查询无此Workflow");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
    public ResponseMsg getTimelineByWid(int wid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            List<Timeline> w =workflowMapper.getTimelineByWid(wid);
            if(w!=null){
                responseMsg.getResponseMap().put("timelines",w);
                responseMsg.setStatusAndMessage(200,"查询成功，具体的操作时间信息");
            }
            else{
                responseMsg.setStatusAndMessage(204,"查询无此Workflow");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
    public ResponseMsg sup_agree(int sup_eid, int wid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            if(!commonCheckImpl(wid, responseMsg, sup_eid,"sup",1)){
                return responseMsg;
            }
            responseMsg.getResponseMap().put("workflow",workflowMapper.getByWidCascade(wid));
            responseMsg.setStatusAndMessage(200,"正常设定了workflow的状态");//TODO:发邮件通知管理级人员前往配置
            workflowMapper.addTimeline(wid,"approved",sup_eid);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
    public ResponseMsg sup_disagree(int sup_eid, int wid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            Workflow workflow = workflowMapper.getByWidCascade(wid);
            if(workflow==null){
                responseMsg.setStatusAndMessage(210,"没有查到这个workflow");
                return responseMsg;
            }
            if(sup_eid!=workflow.getSup_eid()){
                responseMsg.setStatusAndMessage(212,"你不是sup没资格不同意项目");
                return responseMsg;
            }
            WorkflowEngineService workflowEngineService=new WorkflowEngineService();
            int altered_flowbits=workflowEngineService.uncheckTodo(0, workflow.getFlowbits());
            if(altered_flowbits==workflow.getFlowbits()){
                responseMsg.setStatusAndMessage(214,"项目状态没变，可能是没有到你的流程或者你的流程已经结束啦");
                return responseMsg;
            }
            workflowMapper.updateFlowBits(wid, altered_flowbits);//更新workflow的比特位
            responseMsg.getResponseMap().put("workflow",workflowMapper.getByWidCascade(wid));
            responseMsg.setStatusAndMessage(200,"正常设定了workflow的状态");
            workflowMapper.addTimeline(wid,"rejected",sup_eid);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
    public ResponseMsg configurer_config(int configurer_eid, int wid,String git_repo, String server_root, String mail_list){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            if(!commonCheckImpl(wid, responseMsg, configurer_eid,"configurer",2)){
                return responseMsg;
            }
            workflowMapper.updateConfig(wid, git_repo, server_root, mail_list);
            responseMsg.getResponseMap().put("workflow",workflowMapper.getByWidCascade(wid));
            responseMsg.setStatusAndMessage(200,"正常设定了workflow的状态");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
    public ResponseMsg epg_config(int epgleader_eid, int wid, List<Integer> epgs){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            if(epgs==null){
                responseMsg.setStatusAndMessage(404,"epg数组出现null异常了");
                return responseMsg;
            }
            if(!commonCheckImpl(wid, responseMsg, epgleader_eid,"epgleader",3)){
                return responseMsg;
            }
            Project project = projectMapper.getByWid(wid).get(0);
            //设定epgLeader在项目里的角色
            byte authority[]={0};// epg leader不加入项目成员
            //设定拉入的epg的角色
            EmployeeProject supEP = employeeProjectMapper.getEmployeeProjectByRole(project.getPid(),"pm").get(0);//认为上级是pm，拉pm的epid
            for (int i = 0; i < epgs.size(); i++) {
                EmployeeProject employeeProjectEpg = new EmployeeProject(0, authority, supEP.getEpid(), project.getPid(), epgs.get(i));
                List<EmployeeProject> existenceTest = employeeProjectMapper.getEmployeeProject(project.getPid(),epgs.get(i));//测试存在性
                if(existenceTest.size()>0){
                    employeeProjectEpg=existenceTest.get(0);
                }
                else
                    employeeProjectMapper.addEmployeeProject(employeeProjectEpg);
                employeeProjectMapper.addEmployeeRoleProject(new EmployeeRoleProject("epg", employeeProjectEpg.getEpid()));
            }
            // 返回workflow
            responseMsg.getResponseMap().put("workflow",workflowMapper.getByWidCascade(wid));
            responseMsg.setStatusAndMessage(200,"正常设定了workflow的状态");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
    public ResponseMsg qa_config(int qamanager_eid, int wid, ArrayList<Integer> qas) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            if(qas==null){
                responseMsg.setStatusAndMessage(404,"qa数组出现null异常了");
                return responseMsg;
            }
            if(!commonCheckImpl(wid, responseMsg, qamanager_eid,"qamanager",4)){
                return responseMsg;
            }
            Project project = projectMapper.getByWid(wid).get(0);
            //设定qaManager在项目里的角色
            byte authority[]={2};// qamanager 加入项目成员
            EmployeeProject pmEP = employeeProjectMapper.getEmployeeProjectByRole(project.getPid(),"pm").get(0);//认为上级是pm，拉pm的epid
            EmployeeProject employeeProjectQamanager = new EmployeeProject(0, authority, pmEP.getEpid(), project.getPid(), qamanager_eid);
            List<EmployeeProject> existenceTest = employeeProjectMapper.getEmployeeProject(project.getPid(),qamanager_eid);//测试存在性
            if(existenceTest.size()>0){
                employeeProjectQamanager=existenceTest.get(0);
            }
            else
                employeeProjectMapper.addEmployeeProject(employeeProjectQamanager);
            employeeProjectMapper.addEmployeeRoleProject(new EmployeeRoleProject("qa_leader", employeeProjectQamanager.getEpid()));
            //设定拉入的qa的角色
            for (int i = 0; i < qas.size(); i++) {
                authority[0]=0;
                EmployeeProject employeeProjectQa = new EmployeeProject(0, authority, employeeProjectQamanager.getEpid(), project.getPid(), qas.get(i));
                existenceTest = employeeProjectMapper.getEmployeeProject(project.getPid(),qas.get(i));//测试存在性
                if(existenceTest.size()>0){
                    employeeProjectQa=existenceTest.get(0);
                }
                else
                    employeeProjectMapper.addEmployeeProject(employeeProjectQa);
                employeeProjectMapper.addEmployeeRoleProject(new EmployeeRoleProject("qa", employeeProjectQa.getEpid()));
            }
            // 返回workflow
            responseMsg.getResponseMap().put("workflow",workflowMapper.getByWidCascade(wid));
            responseMsg.setStatusAndMessage(200,"正常设定了workflow的状态");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
    public ResponseMsg member_config(int pm_eid, int wid, Integer rd_leader_id, ArrayList<Integer> rd_ids, ArrayList<Integer> qa_ids) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            if(rd_ids==null||qa_ids==null){
                responseMsg.setStatusAndMessage(404,"qa数组出现null异常了");
                return responseMsg;
            }
            if(!commonCheckImpl(wid, responseMsg, pm_eid,"pm",5)){
                return responseMsg;
            }
            Project project = projectMapper.getByWid(wid).get(0);
            EmployeeProject pmEP = employeeProjectMapper.getEmployeeProjectByRole(project.getPid(),"pm").get(0);//认为上级是pm，拉pm的epid
            EmployeeProject qaleaderEP = employeeProjectMapper.getEmployeeProjectByRole(project.getPid(),"qa_leader").get(0);//认为上级是pm，拉pm的epid
            //设定rd_leader在项目里的角色
            int rdleader_EPid = memberAttend_and_RoleAssign_commonImpl("rd_leader",pmEP.getEpid(),project.getPid(),rd_leader_id);
            //设定拉入的rd的角色
            for (int i = 0; i < rd_ids.size(); i++) {
                memberAttend_and_RoleAssign_commonImpl("rd",rdleader_EPid,project.getPid(),rd_ids.get(i));
            }
            //设定拉入的qa的角色
            for (int i = 0; i < qa_ids.size(); i++) {
                memberAttend_and_RoleAssign_commonImpl("qa",qaleaderEP.getEpid(),project.getPid(),qa_ids.get(i));
            }
            // 返回workflow
            responseMsg.getResponseMap().put("workflow",workflowMapper.getByWidCascade(wid));
            responseMsg.setStatusAndMessage(200,"正常设定了workflow的状态");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
    public ResponseMsg pm_authority(int pm_eid, int wid) {
        //这里应该去掉外部接口 给成员配置git/文件/邮件的权限的
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            if(!commonCheckImpl(wid, responseMsg, pm_eid,"pm",6)){
                return responseMsg;
            }
            responseMsg.getResponseMap().put("workflow",workflowMapper.getByWidCascade(wid));
            responseMsg.setStatusAndMessage(200,"正常设定了workflow的状态");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
    public ResponseMsg pm_function(int pm_eid, int wid,String function) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            if(!commonCheckImpl(wid, responseMsg, pm_eid,"pm",7)){
                return responseMsg;
            }
            Project project=projectMapper.getByWid(wid).get(0);
            project.setFunction(function);//TODO: 检查function的正确性
            projectMapper.updateProject(project);
            responseMsg.getResponseMap().put("workflow",workflowMapper.getByWidCascade(wid));
            responseMsg.setStatusAndMessage(200,"正常设定了workflow的状态");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
    public ResponseMsg pm_common_doing_flow(int pm_eid, int wid, int bits) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            if(!commonCheckImpl(wid, responseMsg, pm_eid,"pm",bits)){
                return responseMsg;
            }
            responseMsg.getResponseMap().put("workflow",workflowMapper.getByWidCascade(wid));
            responseMsg.setStatusAndMessage(200,"正常设定了workflow的状态");
            String[] operations={"started","delivering","submitted"};
            workflowMapper.addTimeline(wid, operations[(bits-8)%3], pm_eid);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
    public ResponseMsg pm_common_archive(int eid, int wid, Integer archive_id, String content) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            if(!commonCheckImpl(wid, responseMsg, eid,"pm",archive_id+11)){
                return responseMsg;
            }
            Workflow workflow =workflowMapper.getByWid(wid);
            switch (archive_id){
                case 0:
                    workflow.setArchive00(content);
                    break;
                case 1:
                    workflow.setArchive01(content);
                    break;
                case 2:
                    workflow.setArchive02(content);
                    break;
                case 3:
                    workflow.setArchive03(content);
                    break;
                case 4:
                    workflow.setArchive04(content);
                    break;
                case 5:
                    workflow.setArchive05(content);
                    break;
                case 6:
                    workflow.setArchive06(content);
                    break;
                case 7:
                    workflow.setArchive07(content);
                    break;
                case 8:
                    workflow.setArchive08(content);
                    break;
                case 9:
                    workflow.setArchive09(content);
                    break;
                case 10:
                    workflow.setArchive10(content);
                    break;
                case 11:
                    workflow.setArchive11(content);
                    break;
                case 12:
                    workflow.setArchive12(content);
                    break;
                case 13:
                    workflow.setArchive13(content);
                    break;
                case 14:
                    workflow.setArchive14(content);
                    break;
                case 15:
                    workflow.setArchive15(content);
                    break;
                case 16:
                    workflow.setArchive16(content);
                    break;
                default:
                    break;
            }
            workflowMapper.update(workflow);
            responseMsg.getResponseMap().put("workflow",workflowMapper.getByWidCascade(wid));
            responseMsg.setStatusAndMessage(200,"正常设定了workflow的状态");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
    public ResponseMsg configurer_after_archive(int eid, int wid) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            if(!commonCheckImpl(wid, responseMsg, eid,"configurer",28)){
                return responseMsg;
            }
            responseMsg.getResponseMap().put("workflow",workflowMapper.getByWidCascade(wid));
            responseMsg.setStatusAndMessage(200,"正常设定了workflow的状态");
            workflowMapper.addTimeline(wid,"achieved",eid);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
}
