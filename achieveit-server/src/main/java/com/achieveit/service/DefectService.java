package com.achieveit.service;

import com.achieveit.entity.Defect;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.mapper.DefectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefectService {
    public DefectService(DefectMapper defectMapper) {
        this.defectMapper = defectMapper;
    }

    Logger logger = LoggerFactory.getLogger(getClass());
    DefectMapper defectMapper;

    public ResponseMsg getPagedDefectByEid(int eid, int page, int length){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<Defect> pws = defectMapper.getByEidCascade(eid);
            List<Defect> pws_paged = new ArrayList<Defect>();
            for(int i=page*length;i<page*length+length;i++){
                if(i<pws.size()){
                    pws_paged.add(pws.get(i));
                }
            }
            int page_length = (pws.size()+length-1)/length;
            msg.setStatusAndMessage(200, "请求正常");
            msg.getResponseMap().put("Defect",pws_paged);
            msg.getResponseMap().put("page_length",page_length);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
    public ResponseMsg getFilteredPagedDefectByEid(int eid, int page, int length,String desc){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<Defect> pws = defectMapper.getDescedByEidCascade(eid,desc);
            List<Defect> pws_paged = new ArrayList<Defect>();
            for(int i=page*length;i<page*length+length;i++){
                if(i<pws.size()){
                    pws_paged.add(pws.get(i));
                }
            }
            int page_length = (pws.size()+length-1)/length;
            msg.setStatusAndMessage(200, "请求正常");
            msg.getResponseMap().put("Defect",pws_paged);
            msg.getResponseMap().put("page_length",page_length);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

}
