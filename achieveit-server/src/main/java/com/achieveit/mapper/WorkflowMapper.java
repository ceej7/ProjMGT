package com.achieveit.mapper;

import com.achieveit.entity.Workflow;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowMapper {

    @Select("select * from workflow where wid=#{wid}")
    Workflow getById(int wid);

//    @Options(useGeneratedKeys = true,keyProperty = "wid")
//    @Insert("insert into " +
//            "workflow(flowbits, pm_eid,sup_eid, configurer_eid, epgleader_eid,qamanager_eid) " +
//            "values(#{flowbits}, #{pm_eid},#{sup_eid}, #{configurer_eid}, #{epgleader_eid},#{epgleader_eid})")
//    public int addWorkflow(Workflow workflow);
}
