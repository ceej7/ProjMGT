package com.achieveit.service;

import com.achieveit.entity.Manhour;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.mapper.ManhourMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManhourService {

    public ManhourService(ManhourMapper manhourMapper) {
        this.manhourMapper = manhourMapper;
    }

    Logger logger = LoggerFactory.getLogger(getClass());
    ManhourMapper manhourMapper;

    public ResponseMsg getPagedManhourByEid(int eid, int page, int length){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<Manhour> pws = manhourMapper.getByEidCascade(eid);
            List<Manhour> pws_paged = new ArrayList<Manhour>();
            for(int i=page*length;i<page*length+length;i++){
                if(i<pws.size()){
                    pws_paged.add(pws.get(i));
                }
            }
            int page_length = (pws.size()+length-1)/length;
            msg.setStatusAndMessage(200, "请求正常");
            msg.getResponseMap().put("Manhour",pws_paged);
            msg.getResponseMap().put("page_length",page_length);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
    public ResponseMsg getFilteredPagedManhourByEid(int eid, int page, int length, Date date){
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求异常");
        try{
            List<Manhour> pws = manhourMapper.getDatedByEidCascade(eid,date);
            List<Manhour> pws_paged = new ArrayList<Manhour>();
            for(int i=page*length;i<page*length+length;i++){
                if(i<pws.size()){
                    pws_paged.add(pws.get(i));
                }
            }
            int page_length = (pws.size()+length-1)/length;
            msg.setStatusAndMessage(200, "请求正常");
            msg.getResponseMap().put("Manhour",pws_paged);
            msg.getResponseMap().put("page_length",page_length);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}
