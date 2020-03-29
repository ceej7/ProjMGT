package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.ProjectService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "项目接口", value="以项目为主体的请求")
public class ProjectController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    JwtToken jwtToken;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    private final ProjectService projectService;

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
}
