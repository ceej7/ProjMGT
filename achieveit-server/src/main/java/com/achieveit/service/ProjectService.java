package com.achieveit.service;

import com.achieveit.entity.Project;
import com.achieveit.entity.Workflow;
import com.achieveit.mapper.ProjectMapper;
import com.achieveit.mapper.WorkflowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    public ProjectService(ProjectMapper projectMapper, WorkflowMapper workflowMapper) {
        this.projectMapper = projectMapper;
        this.workflowMapper = workflowMapper;
    }

    private final ProjectMapper projectMapper;
    private final WorkflowMapper workflowMapper;

    public List<Project> getByWorkflow(List<Workflow> workflows){
        List<Project> projects = new ArrayList<Project>();
        for(Workflow w:workflows){
            projects.add(projectMapper.getById(w.getWid()));
        }
        return projects;
    }
}
