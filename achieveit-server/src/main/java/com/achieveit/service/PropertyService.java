package com.achieveit.service;

import com.achieveit.config.DateUtil;
import com.achieveit.entity.Property;
import com.achieveit.entity.PropertyOccupy;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.mapper.PropertyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class PropertyService {
    Logger logger = LoggerFactory.getLogger(getClass());
    PropertyMapper propertyMapper;


    public PropertyService(PropertyMapper propertyMapper) {
        this.propertyMapper = propertyMapper;
    }

    public ResponseMsg getByPropertyId(int pid) {
        ResponseMsg msg=new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            Property property=propertyMapper.getByPid(pid);
            if(property!=null){
                msg.getResponseMap().put("property", property);
                msg.setStatusAndMessage(200, "正常获取");
            }else {
                msg.setStatusAndMessage(212, "没有这个property");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg getAllProperty() {
        ResponseMsg msg=new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            msg.getResponseMap().put("properties",propertyMapper.getAll());
            msg.setStatusAndMessage(200, "正常获取");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
            return msg;
    }

    public ResponseMsg getByEmployeeId(int eid) {
        ResponseMsg msg=new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<PropertyOccupy> properties=propertyMapper.getPropertyOccupyByEid(eid);
            if(properties!=null){
                msg.getResponseMap().put("properties", properties);
                msg.setStatusAndMessage(200, "正常获取");
            }else {
                msg.setStatusAndMessage(212, "propertyOccupies获得的是null");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg getByProjectId(String pid) {
        ResponseMsg msg=new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<PropertyOccupy> properties=propertyMapper.getPropertyOccupyByProjectId(pid);
            if(properties!=null){
                msg.getResponseMap().put("properties", properties);
                msg.setStatusAndMessage(200, "正常获取");
            }else {
                msg.setStatusAndMessage(212, "propertyOccupies获得的是null");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg rentIn(int employee_id, String project_id, int property_id, Timestamp time, boolean is_intact) {
        ResponseMsg msg=new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<PropertyOccupy> propertyOccupies=propertyMapper.getPropertyOccupyByPropertyId(property_id);
            long nowTime = Calendar.getInstance().getTimeInMillis();
            for (int i = 0; i < propertyOccupies.size(); i++) {
                if(!propertyOccupies.get(i).isExpired()){
                    msg.setStatusAndMessage(212, "设备仍出借中");
                    return msg;
                }
            }
            PropertyOccupy propertyOccupy=new PropertyOccupy(0, time, is_intact, property_id, project_id, employee_id);
            int ret = propertyMapper.addPropertyOccupy(propertyOccupy);
            if(ret>0){
                msg.getResponseMap().put("propertyOccupy",propertyOccupy);
                msg.setStatusAndMessage(200, "出借成功");
                return msg;
            }
            else{
                msg.setStatusAndMessage(214, "出借失败");
                return msg;
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg updatePropertyOccupy(int poid, Map param) {
        ResponseMsg msg=new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            PropertyOccupy propertyOccupy = propertyMapper.getByPoid(poid);
            if(param.containsKey("expire_time")){
                Timestamp time;
                String[] timeStr = param.get("expire_time").toString().split("T");
                time = DateUtil.String2Timestamp(timeStr[0]+" "+timeStr[1].split("\\.")[0], "yyyy-MM-dd HH:mm:ss");
                propertyOccupy.setExpire_time(time);
            }
            if(param.containsKey("is_intact")){
                propertyOccupy.setIs_intact((boolean)param.get("is_intact"));
            }
            int ret = propertyMapper.updatePropertyOccupy(propertyOccupy);
            if(ret>0){
                msg.getResponseMap().put("propertyOccupy",propertyOccupy);
                msg.setStatusAndMessage(200, "正常更新");
            }else{
                msg.setStatusAndMessage(210, "更新失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }


}
