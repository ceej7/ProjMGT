package com.achieveit.controller;

import com.achieveit.config.DateUtil;
import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import com.achieveit.service.ManhourService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

@RestController
@Api(tags = "工时接口", value="工时相关API")
public class ManhourController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    JwtToken jwtToken;
    private final ManhourService manhourService;
    private final MailService mailService;
    private final FileService fileService;

    public ManhourController(MailService mailService, FileService fileService,ManhourService manhourService, JwtToken jwtToken) {
        this.mailService = mailService;
        this.fileService = fileService;
        this.manhourService = manhourService;
        this.jwtToken=jwtToken;
    }

    @ResponseBody
    @GetMapping("/manhour/activity")
    @ApiOperation("获取所有活动列别")
    public ResponseMsg getMyManhour(){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        msg = manhourService.getActivity();
        return msg;
    }

    @ResponseBody
    @GetMapping("/manhour/subManhour/{eid}")
    @ApiOperation("获取所有项目中下级提出的manhour/打回方或者审批")
    public ResponseMsg getSubManhour(@PathVariable int eid){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        if(eid<0){
            msg.setStatusAndMessage(208, "参数错误");
        }
        msg = manhourService.getSubManhour(eid);
        return msg;
    }

    @ResponseBody
    @GetMapping("/manhour/myManhour")
    @ApiOperation("获取自己发起的Manhour的具体列表，需要提供[Authorization, Bearer [token]] 键值对验证用户的token\n" +
            "需要提供[page:数字]和[length:数字]来表示分页位置和每页长度(Page从0开始计数)\n" +
            "可选提供[date:'2020-03-24']来filter工单的日期\n" +
            "返回附带page_length来表示最大页数")
    public ResponseMsg getMyManhour(@RequestHeader("Authorization") String authHeader,
                                   @RequestParam("page") int page,
                                   @RequestParam("length") int length,
                                   @RequestParam(value = "date",required =false) Date desc){
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
            else if(page<0||length<0){
                msg.setStatusAndMessage(208, "参数错误");
            }
            else if(desc==null){
                int userId = Integer.valueOf(claims.getSubject());
                msg=manhourService.getPagedManhourByEid(userId,page,length);
            }
            else{
                int userId = Integer.valueOf(claims.getSubject());
                msg=manhourService.getFilteredPagedManhourByEid(userId,page,length,desc);
            }
        }
        return msg;
    }

    @ResponseBody
    @PostMapping("/manhour/{eid}/{pid}/{aid}/{fid}")
    @ApiOperation(value = "新建工时单，员工id，项目id，活动id和功能id，外带Requestbody中传起止时间，数据库中的date自动生成",notes = "" +
            "{\n" +
            "    \"starttime\":\"2020-04-08T16:00:00.000Z\",\n" +
            "    \"endtime\":\"2020-04-09T16:00:00.000Z\"\n" +
            "}")
    public ResponseMsg newManhour(@PathVariable int eid, @PathVariable String pid, @PathVariable int aid, @PathVariable int fid,@RequestBody Map param){
        ResponseMsg msg=new ResponseMsg();
        msg.setStatusAndMessage(404, "异常请求");
        if(eid<0||pid.length()!=11||aid<0||fid<0||!param.containsKey("starttime")||!param.containsKey("endtime")){
            msg.setStatusAndMessage(208, "参数错误");
            return msg;
        }
        Timestamp startdate=null;
        Timestamp enddate = null;
        try{
            String[] startString = param.get("starttime").toString().split("T");
            String[] endString = param.get("endtime").toString().split("T");
            startdate = DateUtil.String2Timestamp(startString[0]+" "+startString[1].split("\\.")[0], "yyyy-MM-dd HH:mm:ss");
            enddate = DateUtil.String2Timestamp(endString[0]+" "+endString[1].split("\\.")[0], "yyyy-MM-dd HH:mm:ss");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            msg.setStatusAndMessage(210, "时间参数解析错误");
            return msg;
        }
        msg=manhourService.newManhour(eid,pid,aid,fid,startdate,enddate);
        return msg;
    }

    @ResponseBody
    @DeleteMapping("/manhour/{eid}/{mid}")
    @ApiOperation("删除工时单")
    public ResponseMsg deleteManhour(@PathVariable int eid, @PathVariable int mid){
        ResponseMsg msg=new ResponseMsg();
        msg.setStatusAndMessage(404, "异常请求");
        if(eid<0||mid<0){
              msg.setStatusAndMessage(208, "参数错误");
        }
        msg=manhourService.deleteManhour(eid,mid);
        return msg;
    }

    @ResponseBody
    @PutMapping("/manhour/{eid}/{mid}")
    @ApiOperation(value = "更新工时单，可以用来更新工时信息、审核工时单、撤回工时单",notes = "" +
            "{\n" +
            "    \"fid\":0,\n" +
            "    \"starttime\":\"2020-04-08T16:00:00.000Z\",\n" +
            "    \"endtime\":\"2020-04-09T16:00:00.000Z\",\n" +
            "    \"status\":\"unfilled/unchecked/checked\",\n" +
            "    \"activity_id\":1\n" +
            "}\n" +
            "如果提供了status则只检查status，则需要检查eid的身份是否是manhour发起者在项目中的上级\n " +
            "status具有语义-unfilled[发起者还可修改]-unchecked[不通过]-checked[通过]\n" +
            "上级打回方是将status置为unfilled，如果变为unfilled必须要在现在的状态是checked/unchecked而且现在距离manhour的date在三天内")
    public ResponseMsg update(@PathVariable int eid, @PathVariable int mid,@RequestBody Map map){
        ResponseMsg msg=new ResponseMsg();
        msg.setStatusAndMessage(404, "异常请求");
        if(eid<0||mid<0){
            msg.setStatusAndMessage(208, "参数错误");
        }
        msg=manhourService.updateManhour(eid, mid, map);
        return msg;
    }







//    ResponseMsg msg=new ResponseMsg();
//        msg.setStatusAndMessage(404, "异常请求");
//        if(eid<0||pid.length()!=11){
//              msg.setStatusAndMessage(208, "参数错误");
//    }
//    return msg;
}
