package com.achieveit.mapper;

import com.achieveit.entity.Workflow;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowMapper {

    @Select("select * from workflow where wid=#{wid}")
    Workflow getByWid(int wid);

    @Select("select * from workflow where wid=#{wid}")
    @Results({
            @Result(property = "pm",column = "pm_eid", one=@One(select = "com.achieveit.mapper.EmployeeMapper.getByEidCascade")),
            @Result(property = "sup",column = "sup_eid", one=@One(select = "com.achieveit.mapper.EmployeeMapper.getByEidCascade")),
            @Result(property = "configurer",column = "configurer_eid", one=@One(select = "com.achieveit.mapper.EmployeeMapper.getByEidCascade")),
            @Result(property = "epgleader",column = "epgleader_eid", one=@One(select = "com.achieveit.mapper.EmployeeMapper.getByEidCascade")),
            @Result(property = "qamanager",column = "qamanager_eid", one=@One(select = "com.achieveit.mapper.EmployeeMapper.getByEidCascade")),
    })
    Workflow getByWidCascade(int wid);

//    @Options(useGeneratedKeys = true,keyProperty = "wid")
//    @Insert("insert into " +
//            "workflow(flowbits, pm_eid,sup_eid, configurer_eid, epgleader_eid,qamanager_eid) " +
//            "values(#{flowbits}, #{pm_eid},#{sup_eid}, #{configurer_eid}, #{epgleader_eid},#{epgleader_eid})")
//    public int addWorkflow(Workflow workflow);
}
