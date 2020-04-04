package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.Employee;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Workflow;
import com.achieveit.service.EmployeeService;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import com.achieveit.service.WorkflowService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.Map;

@RestController
@Api(tags = "工作流接口", value = "工作流的关键API")
public class WorkflowController {
    Logger logger = LoggerFactory.getLogger(getClass());
    JwtToken jwtToken;
    private final MailService mailService;
    private final FileService fileService;
    private final WorkflowService workflowService;

    public WorkflowController(MailService mailService, FileService fileService, WorkflowService workflowService, JwtToken jwtToken) {
        this.mailService = mailService;
        this.fileService = fileService;
        this.workflowService = workflowService;
        this.jwtToken=jwtToken;
    }

    @ResponseBody
    @GetMapping("/workflow/{wid}")
    @ApiOperation("根据id获得一个workflow,以及对应的组织级成员")
    public ResponseMsg getWorkflowByIdWithMember(@PathVariable int wid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        if(wid<0){
            responseMsg.setStatusAndMessage(208, "参数无效");
        }
        else
            responseMsg = workflowService.getById(wid);
        return responseMsg;
    }

    @ResponseBody
    @GetMapping("/workflow/timeline/{wid}")
    @ApiOperation("根据wid获得一个workflow对应的操作时间")
    public ResponseMsg getWorkflowTimelineByIdWithMember(@PathVariable int wid) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404, "查询发生异常");
        if (wid < 0) {
            responseMsg.setStatusAndMessage(208, "参数无效");
        } else
            responseMsg = workflowService.getTimelineByWid(wid);
        return responseMsg;
    }

    @ResponseBody
    @PutMapping("/workflow/{wid}/{eid}")
    @ApiOperation(value = "根据给定的wid,eid,todo来解析并推进对应需要做的工作流",notes = "\n" +
            "{\"todo\":\"agree\"}代表sup同意启动项目\n"+"****************************************\n"+
            "{\"todo\":\"disagree\"}代表sup不同意启动项目\n"+"****************************************\n"+
            "{ \"todo\": \"config\",\"git_repo\":\"git.com\", \"server_root\":\"./home\",\"mail_list\": \"hh\"}代表Configurer配置项目\n" +"****************************************\n"+
            "{ \"todo\": \"epg\",\"epgs\":[10,11,12]}代表epgleader拉epg，epgleader本人不进入项目，epg的sup是pm\n" +"****************************************\n"+
            "{\"todo\": \"qa\", \"qas\": [13,14,15]}代表qamanager拉qa，qamanager本人进入项目做qaleader，qa的sup是qaleader\n"+"****************************************\n"+
            "{\n" +
            "    \"todo\": \"pm_member\",\n" +
            "    \"members\": {\n" +
            "        \"rd_leader\":15,\n" +
            "        \"rd\":[16,17,18,19,20],\n" +
            "        \"qa\":[21,22,23]\n" +
            "    }\n" +
            "}\n这时候只支持拉一个rd_leader，多个rd和qa进来了，qpg,pm,qaleader都已经配置好了\n"+"****************************************\n"+
            "{\"todo\": \"pm_authority\"}\n一个纯粹的接口，配置member的文件/git/邮件权限，推进工作流\n"+"****************************************\n"+
            "{\n" +
            "    \"todo\": \"pm_function\",\n" +
            "\"function\":{\"000000\":\"0-1\"}\n"+
            "}\n配置功能列表\n"+"****************************************\n"+
            "{\"todo\": \"pm_start\"}开始项目\n"+"****************************************\n"+
            "{\"todo\": \"pm_submitting\"}开始交付\n"+"****************************************\n"+
            "{\"todo\": \"pm_submitted\"}交付结束，准备归档\n"+"****************************************\n"+
            "{\"todo\": \"pm_archive\",\"archive_id\":0,\"content\":\"你就随便填吧反正没人看\"}\n接下来要做17个文件的归档，archive_id in [0,16]\n"+"****************************************\n"+
            "{\"todo\": \"configurer_archive_done\"}最后由configurer确认归档完成\n")
    public ResponseMsg proceedWorkflow(@PathVariable int wid, @PathVariable int eid, @RequestBody Map param){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        if(!param.containsKey("todo")){
            msg.setStatusAndMessage(208, "参数不足");
            return msg;
        }
        if(param.get("todo").equals("agree")){
            msg = workflowService.sup_agree(eid,wid);
        }else if(param.get("todo").equals("disagree")){
            msg = workflowService.sup_disagree(eid,wid);
        }else if(param.get("todo").equals("config")){
            if(!param.containsKey("git_repo")||!param.containsKey("server_root")||!param.containsKey("mail_list")){
                msg.setStatusAndMessage(208, "参数不足");
                return msg;
            }
            msg = workflowService.configurer_config(eid,wid,param.get("git_repo").toString(), param.get("server_root").toString(), param.get("mail_list").toString());
        }else if(param.get("todo").equals("epg")){
            if(!param.containsKey("epgs")){
                msg.setStatusAndMessage(208, "参数不足");
                return msg;
            }
            msg=workflowService.epg_config(eid, wid, (ArrayList<Integer>) param.get("epgs"));
        }else if(param.get("todo").equals("qa")){
            if(!param.containsKey("qas")){
                msg.setStatusAndMessage(208, "参数不足");
                return msg;
            }
            msg=workflowService.qa_config(eid, wid, (ArrayList<Integer>) param.get("qas"));
        }else if(param.get("todo").equals("pm_member")){
            if(!param.containsKey("members")){
                msg.setStatusAndMessage(208, "参数不足");
                return msg;
            }
            Map members = (Map)param.get("members");
            if(!members.containsKey("rd_leader")||!members.containsKey("rd")){
                msg.setStatusAndMessage(208, "参数不足");
                return msg;
            }
            Integer rd_leader_id = Integer.valueOf(members.get("rd_leader").toString());
            ArrayList<Integer> rd_ids = (ArrayList<Integer>) members.get("rd");
            ArrayList<Integer> qa_ids = null;
            if(!members.containsKey("rd")){
                qa_ids = new ArrayList<>();
            }
            else{
                qa_ids = (ArrayList<Integer>) members.get("qa");
            }
            msg = workflowService.member_config(eid,wid,rd_leader_id,rd_ids,qa_ids);
        }else if(param.get("todo").equals("pm_authority")){
            msg=workflowService.pm_authority(eid, wid);
        }else if(param.get("todo").equals("pm_function")){
            if(!param.containsKey("function")){
                msg.setStatusAndMessage(208, "参数不足");
                return msg;
            }
            msg=workflowService.pm_function(eid, wid,(Map)param.get("function"));
        }else if(param.get("todo").equals("pm_start")){
            msg=workflowService.pm_common_doing_flow(eid, wid,8);
        }else if(param.get("todo").equals("pm_submitting")){
            msg=workflowService.pm_common_doing_flow(eid, wid,9);
        }else if(param.get("todo").equals("pm_submitted")){
            msg=workflowService.pm_common_doing_flow(eid, wid,10);
        }else if(param.get("todo").equals("pm_archive")){
            if(!param.containsKey("archive_id")||!param.containsKey("content")){
                msg.setStatusAndMessage(208, "参数不足");
                return msg;
            }
            if(Integer.valueOf(param.get("archive_id").toString())<0||Integer.valueOf(param.get("archive_id").toString())>=17){
                msg.setStatusAndMessage(208, "archive_id参数错误");
                return msg;
            }
            msg=workflowService.pm_common_archive(eid, wid,Integer.valueOf(param.get("archive_id").toString()),param.get("content").toString());
        }else if(param.get("todo").equals("configurer_archive_done")){
            msg=workflowService.configurer_after_archive(eid, wid);
        }
        return msg;
    }
}
