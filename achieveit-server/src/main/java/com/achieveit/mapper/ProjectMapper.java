package com.achieveit.mapper;

import com.achieveit.entity.Project;
import com.achieveit.entity.ProjectWorkflow;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMapper {
    @Select("SELECT * FROM project p INNER JOIN workflow w ON p.workflow_id =w.wid WHERE sup_eid=#{eid}")
    List<ProjectWorkflow> getBySupEid(int eid);
}
