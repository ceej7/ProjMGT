package com.achieveit.controller;

import com.achieveit.config.DateUtil;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.MilestoneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Map;

@RestController
@Api(tags = "里程碑接口", value="里程碑相关API")
public class MilestoneController {
    Logger logger = LoggerFactory.getLogger(getClass());

    public MilestoneController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    MilestoneService milestoneService;

    @ResponseBody
    @ApiOperation("获取某个Milestone")
    @GetMapping("/milestone/getByMid/{mid}")
    ResponseMsg getByMid(@PathVariable int mid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        if(mid<0){
            responseMsg.setStatusAndMessage(208, "mid参数错误");
        }else{
            responseMsg=milestoneService.getByMid(mid);
        }

        return responseMsg;
    }

    @ResponseBody
    @ApiOperation("获取某个项目Milestone的列表")
    @GetMapping("/milestone/getByPid/{pid}")
    ResponseMsg getByPid(@PathVariable String pid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        if(pid.length()!=11){
            responseMsg.setStatusAndMessage(208, "pid参数错误");
        }else{
            responseMsg=milestoneService.getByPid(pid);
        }

        return responseMsg;
    }

    @ResponseBody
    @ApiOperation(value = "添加某个Milestone",notes = "{\n" +
            "    \"time\":\"2020-04-09T16:00:00.000Z\",\n" +
            "    \"desc\":\"哈哈\"\n" +
            "}")
    @PostMapping("/milestone/add/{pid}")
    ResponseMsg getByMid(@RequestBody Map param,@PathVariable String pid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        if(!param.containsKey("time")||!param.containsKey("desc")||pid.length()!=11){
            responseMsg.setStatusAndMessage(208, "参数错误");
        }else{
            Timestamp time=null;
            try{
                String[] startString = param.get("time").toString().split("T");
                time = DateUtil.String2Timestamp(startString[0]+" "+startString[1].split("\\.")[0], "yyyy-MM-dd HH:mm:ss");
            }catch (Exception e){
                logger.error(e.getMessage(), e);
                responseMsg.setStatusAndMessage(210, "time无法解析");
                return responseMsg;
            }
            String desc = param.get("desc").toString();
            responseMsg=milestoneService.add(pid,time,desc);
        }

        return responseMsg;
    }

    @ResponseBody
    @ApiOperation("删除某个Milestone")
    @DeleteMapping("/milestone/deleteByMid/{mid}")
    ResponseMsg deleteByMid(@PathVariable int mid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        if(mid<0){
            responseMsg.setStatusAndMessage(208, "mid参数错误");
        }else{
            responseMsg=milestoneService.deleteByMid(mid);
        }

        return responseMsg;
    }

    @ResponseBody
    @PutMapping("/milestone/{mid}")
    @ApiOperation(value="更新milestone信息，time，desc，以json键值对形式提供，可以同时提供",
            notes = "时间的输入格式2020-04-08T16:00:00.000Z\n"+"{\n" +
                    "    \"time\":\"2020-04-09T16:00:00.000Z\",\n" +
                    "    \"desc\":\"哈哈\"\n" +
                    "}"
            )
    ResponseMsg updateProject(@RequestBody Map param, @PathVariable int mid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        if(mid<0){
            responseMsg.setStatusAndMessage(208, "mid参数异常");
            return responseMsg;
        }
        responseMsg = milestoneService.update(mid, param);
        return responseMsg;
    }

//    @ResponseBody
//    @ApiOperation("获取Client的列表")
//    @GetMapping("/client/getAll")
//    ResponseMsg getAll(){
//        ResponseMsg responseMsg = new ResponseMsg();
//        responseMsg.setStatusAndMessage(404,"查询发生异常");
//
//        return responseMsg;
//    }
}
