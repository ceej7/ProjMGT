package com.achieveit.controller;

import com.achieveit.config.DateUtil;
import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.DefectService;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import com.achieveit.service.PropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Map;

@RestController
@Api(tags = "资产和设备接口", value="以资产和设备为主体的请求")
public class PropertyController {
    Logger logger = LoggerFactory.getLogger(getClass());

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    PropertyService propertyService;

    @ResponseBody
    @GetMapping("/property/{pid}")
    @ApiOperation("获取一个具体的资产")
    public ResponseMsg getByPid(@PathVariable int pid){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        if(pid<0){
            msg.setStatusAndMessage(208, "参数错误");
            return msg;
        }
        msg=propertyService.getByPropertyId(pid);
        return msg;
    }

    @ResponseBody
    @GetMapping("/property")
    @ApiOperation("获取所有资产")
    public ResponseMsg getAll(){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        msg=propertyService.getAllProperty();
        return msg;
    }

    @ResponseBody
    @GetMapping("/property/occupy/employee/{eid}")
    @ApiOperation("获取员工借的所有资产")
    public ResponseMsg getByEid(@PathVariable int eid){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        if(eid<0){
            msg.setStatusAndMessage(208, "参数错误");
            return msg;
        }
        msg=propertyService.getByEmployeeId(eid);
        return msg;
    }

    @ResponseBody
    @GetMapping("/property/occupy/project/{pid}")
    @ApiOperation("获取资产下所有员工租借的资产")
    public ResponseMsg getByProjectId(@PathVariable String pid){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        if(pid.length()!=11){
            msg.setStatusAndMessage(208, "参数错误");
            return msg;
        }
        msg = propertyService.getByProjectId(pid);
        return msg;
    }

    @ResponseBody
    @PostMapping("/property/occupy/{employee_id}/{project_id}/{property_id}")
    @ApiOperation(value = "根据property_id租借设备",notes = "{\n" +
            "    \"expire_time\":\"2020-04-08T16:00:00.000Z\",\n" +
            "    \"is_intact\":true\n" +
            "}")
    public ResponseMsg rent(@PathVariable int employee_id, @PathVariable String project_id, @PathVariable int property_id, @RequestBody Map param){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        if(employee_id<0||project_id.length()!=11||property_id<0||!param.containsKey("expire_time")||!param.containsKey("is_intact")){
            msg.setStatusAndMessage(208, "参数错误");
            return msg;
        }
        Timestamp time;
        try{
            String[] timeStr = param.get("expire_time").toString().split("T");
            time = DateUtil.String2Timestamp(timeStr[0]+" "+timeStr[1].split("\\.")[0], "yyyy-MM-dd HH:mm:ss");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            msg.setStatusAndMessage(210, "时间参数无法解析");
            return msg;
        }
        boolean is_intact=(boolean)param.get("is_intact");
        msg = propertyService.rentIn(employee_id,project_id,property_id,time,is_intact);
        return msg;
    }

    @ResponseBody
    @PutMapping("/property/occupy/{poid}")
    @ApiOperation(value = "更新租借的到期时间expire_time和is_intact",notes = "{\n" +
            "    \"expire_time\":\"2020-04-08T16:00:00.000Z\",\n" +
            "    \"is_intact\":true\n" +
            "}")
    public ResponseMsg updatePropertyOccupyStatus(@PathVariable int poid, @RequestBody Map param){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        if(poid<0){
            msg.setStatusAndMessage(208, "参数错误");
            return msg;
        }
        msg = propertyService.updatePropertyOccupy(poid, param);
        return msg;
    }
}
