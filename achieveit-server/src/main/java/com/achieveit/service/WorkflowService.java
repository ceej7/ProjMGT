package com.achieveit.service;

import com.achieveit.entity.Workflow;
import com.achieveit.mapper.WorkflowMapper;
import org.springframework.stereotype.Service;

@Service
public class WorkflowService {
    public WorkflowService(WorkflowMapper workflowMapper) {
        this.workflowMapper = workflowMapper;
    }

    private final WorkflowMapper workflowMapper;

    public Workflow getById(int wid){
        return workflowMapper.getById(wid);
    }

}
