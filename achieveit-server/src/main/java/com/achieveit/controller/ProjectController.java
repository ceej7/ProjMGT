package com.achieveit.controller;

import com.achieveit.config.DateUtil;
import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import com.achieveit.service.ProjectService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "项目接口", value="以项目为主体的请求")
public class ProjectController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    JwtToken jwtToken;
    private final ProjectService projectService;
    private final MailService mailService;
    private final FileService fileService;

    public ProjectController(MailService mailService, FileService fileService,ProjectService projectService, JwtToken jwtToken) {
        this.mailService = mailService;
        this.fileService = fileService;
        this.projectService = projectService;
        this.jwtToken=jwtToken;
    }

    @ResponseBody
    @GetMapping("/project/getByPid/{pid}")
    @ApiOperation("根据pid获取项目信息，pid:String(四位年份+四位客户代码+1位研发类型(D/M/S/O)+2位顺序号)")
    public ResponseMsg getByPid(@PathVariable() String pid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        if(pid==null||pid.length()!=11){//四位年份+四位客户代码+1位研发类型(D/M/S/O)+2位顺序号
            responseMsg.setStatusAndMessage(202, "参数无效");
        }
        else
            responseMsg = projectService.getById(pid);
        return responseMsg;
    }


    @ResponseBody
    @GetMapping("/project/toCheck")
    @ApiOperation("获取上级自己需要审核的项目, [Authorization, Bearer [token]] 键值对验证用户的token")
    public ResponseMsg getToCheckOfSup(@RequestHeader("Authorization") String authHeader){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        if(authHeader.split("Bearer").length!=2||!authHeader.split("Bearer")[0].equals("")){
            msg.setStatusAndMessage(JwtToken.Illegal, "非法的token");
        }
        else{
            Claims claims = jwtToken.getClaimByToken(authHeader);
            if (claims == null ) {
                msg.setStatusAndMessage(JwtToken.Invalid, "Token无效");
            }
            else if (JwtToken.isTokenExpired(claims.getExpiration())){
                msg.setStatusAndMessage(JwtToken.Expired, "Token过期");
            }
            else{
                int userId = Integer.valueOf(claims.getSubject());
                msg=projectService.getProjectToCheck(userId);
            }
        }
        return msg;
    }

    @ResponseBody
    @GetMapping("/project/myProject")
    @ApiOperation("获取和自己相关的Project的具体列表，需要提供[Authorization, Bearer [token]] 键值对验证用户的token\n" +
            "需要提供[page:数字]和[length:数字]来表示分页位置和每页长度(Page从0开始计数)\n" +
            "可选提供[name:string]来filter项目名字\n" +
            "可选提供[status:string]来filter项目的状态，选项为[done,applying,doing]，不带此参数则代表不做status的filter\n" +
            "返回附带page_length来表示最大页数")
    public ResponseMsg getMyProject(@RequestHeader("Authorization") String authHeader,
                                     @RequestParam("page") int page,
                                     @RequestParam("length") int length,
                                     @RequestParam(value = "name",required =false) String name,
                                     @RequestParam(value = "status",required =false) String status){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        if(authHeader.split("Bearer").length!=2||!authHeader.split("Bearer")[0].equals("")){
            msg.setStatusAndMessage(JwtToken.Illegal, "非法的token");
        }
        else{
            Claims claims = jwtToken.getClaimByToken(authHeader);
            if (claims == null ) {
                msg.setStatusAndMessage(JwtToken.Invalid, "Token无效");
            }
            else if (JwtToken.isTokenExpired(claims.getExpiration())){
                msg.setStatusAndMessage(JwtToken.Expired, "Token过期");
            }
            else if(page<0||length<0||(status!=null&&!status.equals("doing")&&!status.equals("done")&&!status.equals("applying"))){
                msg.setStatusAndMessage(208, "参数错误");
            }
            else if(name==null&&status==null){
                int userId = Integer.valueOf(claims.getSubject());
                msg=projectService.getPagedProjectByEid(userId,page,length);
            }
            else{
                int userId = Integer.valueOf(claims.getSubject());
                msg=projectService.getFilteredPagedProjectByEid(userId,page,length,name,status);
            }
        }
        return msg;
    }

    @ResponseBody
    @PostMapping("/project/new/{pm_eid}")
    @ApiOperation(value = "项目经理执行新建项目，同时创建工作流。", notes = "{\n" +
            "    \"name\": \"刚创建的项目\",\n" +
            "    \"startdate\":\"2020-04-08T16:00:00.000Z\",\n" +
            "    \"enddate\":\"2020-05-21T16:00:00.000Z\",\n" +
            "    \"technique\": \"大猪头技术\",\n" +
            "    \"domain\": \"大猪蹄领域\",\n" +
            "    \"client\": 1,\n" +
            "    \"configurer_eid\": 7,\n" +
            "    \"epgleader_eid\": 5,\n" +
            "    \"qamanager_eid\": 4\n" +
            "}\n" +
            "\n")
    ResponseMsg newProject(@PathVariable int pm_eid,@RequestBody Map param){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        if(!param.containsKey("name")
                ||!param.containsKey("startdate")
                ||!param.containsKey("enddate")
                ||!param.containsKey("technique")
                ||!param.containsKey("domain")
                ||!param.containsKey("client")
                ||!param.containsKey("configurer_eid")
                ||!param.containsKey("epgleader_eid")
                ||!param.containsKey("qamanager_eid")){
            msg.setStatusAndMessage(208, "参数不足");
            return msg;
        }
        String name = param.get("name").toString();
        Timestamp startdate=null;
        Timestamp enddate = null;
        try{
            String[] startString = param.get("startdate").toString().split("T");
            String[] endString = param.get("enddate").toString().split("T");
            startdate = DateUtil.String2Timestamp(startString[0]+" "+startString[1].split("\\.")[0], "yyyy-MM-dd HH:mm:ss");
            enddate = DateUtil.String2Timestamp(endString[0]+" "+endString[1].split("\\.")[0], "yyyy-MM-dd HH:mm:ss");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            msg.setStatusAndMessage(210, "时间参数解析错误");
            return msg;
        }
        String technique = param.get("technique").toString();
        String domain = param.get("domain").toString();
        int client = Integer.valueOf(param.get("client").toString());
        int configurer_eid = Integer.valueOf(param.get("configurer_eid").toString());
        int epgleader_eid = Integer.valueOf(param.get("epgleader_eid").toString());
        int qamanager_eid = Integer.valueOf(param.get("qamanager_eid").toString());
        msg = projectService.newProject(name,startdate,enddate,technique,domain,client,configurer_eid,epgleader_eid,qamanager_eid, pm_eid);
        return msg;
    }
}
