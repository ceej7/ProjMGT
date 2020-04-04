package com.achieveit.service;

import com.achieveit.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RiskService {
    Logger logger = LoggerFactory.getLogger(getClass());
    private final ProjectMapper projectMapper;
    private final WorkflowMapper workflowMapper;
    private final EmployeeMapper employeeMapper;
    private final RiskMapper riskMapper;
    private final EmployeeProjectMapper employeeProjectMapper;


    public RiskService(ProjectMapper projectMapper, WorkflowMapper workflowMapper, EmployeeMapper employeeMapper, RiskMapper riskMapper, EmployeeProjectMapper employeeProjectMapper) {
        this.projectMapper = projectMapper;
        this.workflowMapper = workflowMapper;
        this.employeeMapper = employeeMapper;
        this.riskMapper = riskMapper;
        this.employeeProjectMapper = employeeProjectMapper;
    }


}
