package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.ManhourService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@Api(tags = "工时接口", value="工时相关API")
public class ManhourController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    JwtToken jwtToken;

    public ManhourController(ManhourService manhourService) {
        this.manhourService = manhourService;
    }

    private final ManhourService manhourService;

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

}
