package com.achieveit.service;

import com.achieveit.entity.Workflow;
import com.achieveit.mapper.WorkflowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowService {
    public WorkflowService(WorkflowMapper workflowMapper) {
        this.workflowMapper = workflowMapper;
    }

    private final WorkflowMapper workflowMapper;

    public Workflow getById(int wid){
        return workflowMapper.getById(wid);
    }

    public List<Workflow> getSupToCheck(int sup_eid){
        return workflowMapper.getSupToCheck(sup_eid);
    }

    public int addWorkflow(Workflow workflow){
        workflow.setWid(0);
        workflow.setFlowbits(1);
        //应提供好pm_id/sup_eid/conf_eid/epgleader_eid/qamanager_eid
        return workflowMapper.addWorkflow(workflow);
    }

}
