package com.achieveit.service;

import com.achieveit.entity.ResponseMsg;
import com.achieveit.mapper.ClientMapper;
import com.achieveit.mapper.DefectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    Logger logger = LoggerFactory.getLogger(getClass());
    ClientMapper clientMapper;

    public ClientService(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    public ResponseMsg getAll(){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404, "获取异常");
        try{
            responseMsg.getResponseMap().put("clients",clientMapper.getAll());
            responseMsg.setStatusAndMessage(200, "获取所有Clients");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }
}
