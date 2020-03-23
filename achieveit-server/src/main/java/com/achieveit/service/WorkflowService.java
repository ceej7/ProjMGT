package com.achieveit.service;

import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Workflow;
import com.achieveit.mapper.EmployeeMapper;
import com.achieveit.mapper.WorkflowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowService {
    Logger logger = LoggerFactory.getLogger(getClass());

    public WorkflowService(WorkflowMapper workflowMapper, EmployeeMapper employeeMapper) {
        this.workflowMapper = workflowMapper;
        this.employeeMapper = employeeMapper;
    }

    private final WorkflowMapper workflowMapper;
    private final EmployeeMapper employeeMapper;

    public ResponseMsg getById(int wid){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        try{
            Workflow w =workflowMapper.getById(wid);
            if(w!=null){
                responseMsg.getResponseMap().put("workflow",w);
                if(w.getPm_eid()!=null) responseMsg.getResponseMap().put("pm",employeeMapper.getById(w.getPm_eid()));
                if(w.getSup_eid()!=null) responseMsg.getResponseMap().put("sup",employeeMapper.getById(w.getSup_eid()));
                if(w.getConfigurer_eid()!=null) responseMsg.getResponseMap().put("configurer",employeeMapper.getById(w.getConfigurer_eid()));
                if(w.getEpgleader_eid()!=null) responseMsg.getResponseMap().put("epgleader",employeeMapper.getById(w.getEpgleader_eid()));
                if(w.getQamanager_eid()!=null) responseMsg.getResponseMap().put("qamanager",employeeMapper.getById(w.getQamanager_eid()));
                responseMsg.setStatusAndMessage(200,"查询成功，附带组织级成员的具体信息");
            }
            else{
                responseMsg.setStatusAndMessage(204,"查询无此Workflow");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return responseMsg;
    }

}
