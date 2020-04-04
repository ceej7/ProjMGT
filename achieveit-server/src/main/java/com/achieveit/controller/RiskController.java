package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.ClientService;
import com.achieveit.service.RiskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@Api(tags = "风险接口", value="Risk相关API")
public class RiskController {
    Logger logger = LoggerFactory.getLogger(getClass());
    JwtToken jwtToken;
    private final RiskService riskService;


    public RiskController(RiskService riskService, JwtToken jwtToken) {
        this.riskService = riskService;
        this.jwtToken = jwtToken;
    }


//    @ResponseBody
//    @GetMapping("/risk/getByPid/{pid}")
//    @ApiOperation("获取Project下Risk的列表")
//    ResponseMsg getByProject(@PathVariable String pid){
//        ResponseMsg responseMsg = new ResponseMsg();
//        responseMsg.setStatusAndMessage(404,"查询发生异常");
//        if(pid.length()!=11){
//            responseMsg.setStatusAndMessage(208, "参数解析错误");
//            return responseMsg;
//        }
//        return responseMsg;
//    }

    @ResponseBody
    @GetMapping("/risk/getByPid/{pid}")
    @ApiOperation("获取Project下Risk的列表")
    ResponseMsg getByProject(@PathVariable String pid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        if(pid.length()!=11){
            responseMsg.setStatusAndMessage(208, "参数解析错误");
            return responseMsg;
        }
        responseMsg=riskService.getByProjectId(pid);
        return responseMsg;
    }

    @ResponseBody
    @GetMapping("/risk/getByTemplate")
    @ApiOperation("获取是Template的下Risk的列表")
    ResponseMsg getByTemplate(){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        responseMsg=riskService.getByTemplate();
        return responseMsg;
    }

    @ResponseBody
    @PostMapping("/risk/{eid}/{pid}")
    @ApiOperation(value = "新建风险，提供员工id和项目id , requestBody中还要提供相关成员的ep_ids",notes = "eg[eid=2, pid=20200001D00]\n" +
            "{\n" +
            "    \"type\":\"some risk\",\n" +
            "    \"desc\":\"description for the concrete risk\",\n" +
            "    \"grade\":\"p1/p2/...../p9\",\n" +
            "    \"influence\":\"filling as ur will\",\n" +
            "    \"strategy\":\"filling it plz\",\n" +
            "    \"frequency\":1," +
            "    \"ep_ids\":[73,72,71,70,68,67,66]\n" +
            "}\n")
    ResponseMsg add(@PathVariable int eid, @PathVariable String pid,@RequestBody Map param){
        ArrayList<String> pList = new ArrayList<String>();
        for(int i=0;i<10;i++){
            pList.add("p"+i);
        }
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        if(eid<0||pid.length()!=11
                ||!param.containsKey("type")
                ||!param.containsKey("desc")
                ||!param.containsKey("grade")
                ||!param.containsKey("influence")
                ||!param.containsKey("strategy")
                ||!param.containsKey("frequency")
                ||!param.containsKey("ep_ids")){
            responseMsg.setStatusAndMessage(208, "参数解析错误");
            return responseMsg;
        }else if(!pList.contains(param.get("grade").toString())){
            responseMsg.setStatusAndMessage(208, "参数解析错误");
            return responseMsg;
        }
        String type = param.get("type").toString();
        String desc= param.get("desc").toString();
        String grade= param.get("grade").toString();
        String influence= param.get("influence").toString();
        String strategy= param.get("strategy").toString();
        int frequency = Integer.valueOf(param.get("frequency").toString());
        ArrayList<Integer> ep_ids= (ArrayList<Integer> )param.get("ep_ids");
        responseMsg=riskService.add(eid,pid,type, desc, grade, influence, strategy, frequency,ep_ids);
        return responseMsg;
    }


    @ResponseBody
    @PutMapping("/risk/{eid}/{rid}")
    @ApiOperation(value = "更新风险，提供员工eid和风险rid , requestBody中还可提供相关成员的ep_ids来更新整个相关成员列表",notes = "eg[eid=2, rid=37]\n" +
            "{\n" +
            "    \"type\":\"some risk yo\",\n" +
            "    \"desc\":\"description for the concrete risk yo\",\n" +
            "    \"grade\":\"p1/p2/...../p9\",\n" +
            "    \"influence\":\"filling as ur will yo\",\n" +
            "    \"strategy\":\"filling it plz yo\",\n" +
            "    \"frequency\":2," +
            "    \"ep_ids\":[73]\n" +
            "}\n")
    ResponseMsg update(@PathVariable int eid, @PathVariable int rid,@RequestBody Map param){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        if(eid<0||rid<0){
            responseMsg.setStatusAndMessage(208, "参数解析错误");
            return responseMsg;
        }
        responseMsg=riskService.update(eid,rid,param);
        return responseMsg;
    }

    @ResponseBody
    @DeleteMapping("/risk/{eid}/{rid}")
    @ApiOperation("删除风险信息，这里只能由拥有者删除")
    ResponseMsg delete(@PathVariable int eid, @PathVariable int rid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        if(eid<0||rid<0){
            responseMsg.setStatusAndMessage(208, "参数解析错误");
            return responseMsg;
        }
        responseMsg=riskService.delete(eid,rid);
        return responseMsg;
    }

}
