package com.achieveit.mapper;

import com.achieveit.entity.Workflow;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import com.achieveit.entity.Test;

import java.util.List;

@Repository
public interface WorkflowMapper {

    @Select("select * from workflow where wid=#{wid}")
    public Workflow getById(int wid);

    @Select("select * from workflow where sup_eid=#{sup_eid} and flowbits=1")
    public List<Workflow> getSupToCheck(int sup_eid);

    @Options(useGeneratedKeys = true,keyProperty = "wid")
    @Insert("insert into " +
            "workflow(flowbits, pm_eid,sup_eid, configurer_eid, epgleader_eid,qamanager_eid) " +
            "values(#{flowbits}, #{pm_eid},#{sup_eid}, #{configurer_eid}, #{epgleader_eid},#{epgleader_eid})")
    public int addWorkflow(Workflow workflow);
}
