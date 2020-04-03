package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.DefectService;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Api(tags = "缺陷接口", value="缺陷相关API")
public class DefectController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    JwtToken jwtToken;
    private final DefectService defectService;
    private final MailService mailService;
    private final FileService fileService;

    public DefectController(MailService mailService, FileService fileService, DefectService defectService, JwtToken jwtToken) {
        this.mailService = mailService;
        this.fileService = fileService;
        this.defectService = defectService;
        this.jwtToken=jwtToken;
    }

    @ResponseBody
    @GetMapping("/defect/getByPid/{pid}")
    @ApiOperation("获取某个项目的Defect的具体列表")
    public ResponseMsg getByPid(@PathVariable String pid){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        if(pid.length()!=11){
            msg.setStatusAndMessage(208, "参数错误");
        }
        else{
            msg=defectService.getByPid(pid);
        }
        return msg;
    }


    @ResponseBody
    @GetMapping("/defect/myDefect")
    @ApiOperation("获取和自己发起的Defect的具体列表，需要提供[Authorization, Bearer [token]] 键值对验证用户的token\n" +
            "需要提供[page:数字]和[length:数字]来表示分页位置和每页长度(Page从0开始计数)\n" +
            "可选提供[desc:string]来filter缺陷名字\n" +
            "可选提供[status:'bug','reopen','fixed','wontfix','feature','closed']来filter缺陷名字\n" +
            "返回附带page_length来表示最大页数")
    public ResponseMsg getMyDefect(@RequestHeader("Authorization") String authHeader,
                                    @RequestParam("page") int page,
                                    @RequestParam("length") int length,
                                    @RequestParam(value = "desc",required =false) String desc,
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
            else if(page<0||length<0){
                msg.setStatusAndMessage(208, "参数错误");
            }
            else if(desc==null&&status==null){
                int userId = Integer.valueOf(claims.getSubject());
                msg=defectService.getPagedDefectByEid(userId,page,length);
            }
            else{
                int userId = Integer.valueOf(claims.getSubject());
                msg=defectService.getFilteredPagedDefectByEid(userId,page,length,desc,status);
            }
        }
        return msg;
    }

    @ResponseBody
    @PostMapping("/defect/{pid}")
    @ApiOperation(value = "新增一个defect，需要提供[Authorization, Bearer [token]] 键值对验证用户的token\n",
            notes="{\n" +
                    "    \"authority_desc\":\"noneAuthority/rdLeaderAuthority/qaLeaderAuthority/pmAuthority\",\n" +
                    "    \"desc\":\"aaa\",\n" +
                    "    \"git_repo\":\"aaa\",\n" +
                    "    \"commit\":\"aaa\"\n" +
                    "}")
    public ResponseMsg addDefect(@RequestHeader("Authorization") String authHeader, @PathVariable String pid,@RequestBody Map param){
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
            else if(pid.length()!=11||!param.containsKey("authority_desc")||!param.containsKey("desc")||!param.containsKey("git_repo")||!param.containsKey("commit")){
                msg.setStatusAndMessage(208, "参数错误");
            }
            else{
                int userId = Integer.valueOf(claims.getSubject());
                msg=defectService.addDefect(userId, pid,
                        param.get("authority_desc").toString(), param.get("desc").toString(), param.get("git_repo").toString(), param.get("commit").toString());
            }
        }
        return msg;
    }

    @ResponseBody
    @PutMapping("/defect/{did}")
    @ApiOperation(value = "更新一个defect，需要提供[Authorization, Bearer [token]] 键值对验证用户的token\n",
            notes="{\n" +
                    "    \"authority_desc\":\"noneAuthority/rdLeaderAuthority/qaLeaderAuthority/pmAuthority\",\n" +
                    "    \"desc\":\"aaa\",\n" +
                    "    \"git_repo\":\"aaa\",\n" +
                    "    \"commit\":\"aaa\",\n" +
                    "    \"status\":\"feature\"" +
                    "}")
    public ResponseMsg updateDefect(@RequestHeader("Authorization") String authHeader, @RequestBody Map param, @PathVariable int did){
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
            else if(did<0){
                msg.setStatusAndMessage(208, "参数错误");
            }
            else{
                int userId = Integer.valueOf(claims.getSubject());
                msg=defectService.updateDefect(userId, did, param);
            }
        }
        return msg;
    }

    @ResponseBody
    @DeleteMapping("/defect/{did}")
    @ApiOperation(value = "删除某个defect，需要提供[Authorization, Bearer [token]] 键值对验证用户的token\n",notes=
            "成员的authority：[noneAuthority/rdLeaderAuthority/qaLeaderAuthority/pmAuthority]\n" +
            "拥有[noneAuthority]的成员只能管理自己发起的缺陷\n" +
            "拥有[rdLeaderAuthority]的成员只能管理自己发起的缺陷，noneAuthority/rdLeaderAuthority的缺陷\n" +
            "拥有[qaLeaderAuthority]的成员只能管理自己发起的缺陷，noneAuthority/qaLeaderAuthority的缺陷\n" +
            "拥有[pmAuthority]的成员能管理所有缺陷")
    public ResponseMsg deleteDefect(@RequestHeader("Authorization") String authHeader,@PathVariable int did){
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
            else if(did<0){
                msg.setStatusAndMessage(208, "参数错误");
            }
            else{
                int userId = Integer.valueOf(claims.getSubject());
                msg=defectService.deleteDefect(userId,did);
            }
        }
        return msg;
    }
}
