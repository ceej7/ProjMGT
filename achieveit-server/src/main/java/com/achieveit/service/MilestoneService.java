package com.achieveit.service;

import com.achieveit.config.DateUtil;
import com.achieveit.entity.Milestone;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.mapper.MilestoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Map;

@Service
public class MilestoneService {
    Logger logger = LoggerFactory.getLogger(getClass());
    MilestoneMapper milestoneMapper;


    public MilestoneService(MilestoneMapper milestoneMapper) {
        this.milestoneMapper = milestoneMapper;
    }

    public ResponseMsg getByPid(String pid) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404, "获取异常");
        try{
            responseMsg.getResponseMap().put("milestones",milestoneMapper.getByPid(pid));
            responseMsg.setStatusAndMessage(200, "获取"+pid+"相关的milestone");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }

    public ResponseMsg getByMid(int mid) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404, "获取异常");
        try{
            Milestone milestone = milestoneMapper.getByMid(mid);
            if(milestone!=null){
                responseMsg.getResponseMap().put("milestone",milestone);
                responseMsg.setStatusAndMessage(200, "获取了一个milestone");
            }
            else{
                responseMsg.setStatusAndMessage(210, "没有"+mid+"相关的milestone");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }

    public ResponseMsg add(String pid, Timestamp time, String desc) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404, "获取异常");
        try{
            Milestone milestone=new Milestone(0, time, desc, pid);
            if(milestoneMapper.add(milestone)){
                responseMsg.getResponseMap().put("milestone", milestone);
                responseMsg.setStatusAndMessage(200, "添加成功");
            }else{

                responseMsg.setStatusAndMessage(212, "添加失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }

    public ResponseMsg deleteByMid(int mid) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404, "获取异常");
        try{
            if(milestoneMapper.delete(mid)){
                responseMsg.setStatusAndMessage(200, "删除成功");
            }else{

                responseMsg.setStatusAndMessage(212, "删除失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }

    public ResponseMsg update(int mid, Map param) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404, "获取异常");
        try{
            Milestone milestone=milestoneMapper.getByMid(mid);
            if(param.containsKey("desc")){
                milestone.setDesc(param.get("desc").toString());
            }
            if(param.containsKey("time")){
                Timestamp time = DateUtil.fromInputUTC2Timestamp(param.get("time").toString());
                milestone.setTime(time);
            }
            if(milestoneMapper.update(milestone)>0){
                responseMsg.setStatusAndMessage(200, "更新成功");
            }else{
                responseMsg.setStatusAndMessage(212, "更新失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }

//    public ResponseMsg getAll(){
//        ResponseMsg responseMsg = new ResponseMsg();
//        responseMsg.setStatusAndMessage(404, "获取异常");
//        try{
//        }catch (Exception e){
//            logger.error(e.getMessage(), e);
//        }
//        return responseMsg;
//    }
}
