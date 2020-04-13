package com.achieveit.mapper;

import com.achieveit.entity.Timeline;
import com.achieveit.entity.Workflow;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Options(useGeneratedKeys = true,keyProperty = "wid")
    @Insert("insert into " +
            "workflow(flowbits, pm_eid,sup_eid, configurer_eid, epgleader_eid,qamanager_eid) " +
            "values(#{flowbits}, #{pm_eid},#{sup_eid}, #{configurer_eid}, #{epgleader_eid},#{qamanager_eid})")
    int addWorkflow(Workflow workflow);

    @Update("update workflow set flowbits=#{flowbits} where wid=#{wid}")
    int updateFlowBits(int wid, int flowbits);

    @Update("update workflow set git_repo=#{git_repo},server_root=#{server_root},mail_list=#{mail_list} where wid=#{wid}")
    int updateConfig(int wid, String git_repo, String server_root, String mail_list);


    @Update("update workflow set " +
            "flowbits = #{flowbits}," +
            "pm_eid = #{pm_eid}," +
            "sup_eid = #{sup_eid}," +
            "configurer_eid = #{configurer_eid}," +
            "epgleader_eid = #{epgleader_eid}," +
            "qamanager_eid = #{qamanager_eid}," +
            "git_repo = #{git_repo}," +
            "server_root = #{server_root}," +
            "mail_list = #{mail_list}," +
            "archive00 = #{archive00}," +
            "archive01 = #{archive01}," +
            "archive02 = #{archive02}," +
            "archive03 = #{archive03}," +
            "archive04 = #{archive04}," +
            "archive05 = #{archive05}," +
            "archive06 = #{archive06}," +
            "archive07 = #{archive07}," +
            "archive08 = #{archive08}," +
            "archive09 = #{archive09}," +
            "archive10 = #{archive10}," +
            "archive11 = #{archive11}," +
            "archive12 = #{archive12}," +
            "archive13 = #{archive13}," +
            "archive14 = #{archive14}," +
            "archive15 = #{archive15}," +
            "archive16 = #{archive16} " +
            "where wid=#{wid}")
    int update(Workflow workflow);

    @Select("select operation_type, add_time, workflow_id, employee_id from timeline where workflow_id=#{workflow_id} order by add_time desc")
    @Results({
            @Result(property = "employee",column = "employee_id", one=@One(select = "com.achieveit.mapper.EmployeeMapper.getByEid"))
    })
    List<Timeline> getTimelineByWid(int workflow_id);

    @Insert("insert into " +
            "timeline(workflow_id, operation_type,employee_id) " +
            "values(#{workflow_id}, #{operation_type},#{employee_id})")
    int addTimeline(int workflow_id, String operation_type, int employee_id);
}
