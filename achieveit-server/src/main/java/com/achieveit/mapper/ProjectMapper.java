package com.achieveit.mapper;

import com.achieveit.entity.Project;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMapper {
    @Select("SELECT * FROM project where pid=#{pid}")
    Project getByPid(String pid);

    @Select("SELECT * FROM project where pid=#{pid}")
    @Results({
            @Result(property = "client",column = "client_id", one=@One(select = "com.achieveit.mapper.ClientMapper.getByCid")),
            @Result(property = "workflow",column = "workflow_id", one=@One(select = "com.achieveit.mapper.WorkflowMapper.getByWidCascade"))
    })
    Project getByPidCascade(String pid);

    @Select("SELECT pid, p.name,starttime,endtime,technique,domain,function,client_id,workflow_id FROM employee e INNER JOIN employee_project ep on e.eid = ep.employee_id INNER JOIN project p on ep.project_id=p.pid where eid =#{eid}")
    @Results({
            @Result(property = "client",column = "client_id", one=@One(select = "com.achieveit.mapper.ClientMapper.getByCid")),
            @Result(property = "workflow",column = "workflow_id", one=@One(select = "com.achieveit.mapper.WorkflowMapper.getByWidCascade"))
    })
    List<Project> getByEidCascade(int eid);

    @Select("SELECT pid, p.name,starttime,endtime,technique,domain,function,client_id,workflow_id " +
            "FROM employee e INNER JOIN employee_project ep on e.eid = ep.employee_id " +
            "INNER JOIN project p on ep.project_id=p.pid " +
            "INNER JOIN workflow w on w.wid=workflow_id " +
            "where eid =#{eid} and " +
            "p.name like CONCAT('%',#{name},'%') and " +
            "w.flowbits>=#{l} and w.flowbits<=#{u}")
    @Results({
            @Result(property = "client",column = "client_id", one=@One(select = "com.achieveit.mapper.ClientMapper.getByCid")),
            @Result(property = "workflow",column = "workflow_id", one=@One(select = "com.achieveit.mapper.WorkflowMapper.getByWidCascade"))
    })
    List<Project> getNamedStatusByEidCascade(int eid,String name, int l, int u);

    @Select("SELECT pid,name,starttime,endtime,technique,domain,function,client_id,workflow_id FROM project p " +
            "INNER JOIN workflow w ON p.workflow_id =w.wid WHERE sup_eid=#{eid}")
    @Results({
            @Result(property = "client",column = "client_id", one=@One(select = "com.achieveit.mapper.ClientMapper.getByCid")),
            @Result(property = "workflow",column = "workflow_id", one=@One(select = "com.achieveit.mapper.WorkflowMapper.getByWidCascade"))
    })
    List<Project> getBySupEidCascade(int eid);

    @Select("SELECT pid,name,starttime,endtime,technique,domain,function,client_id,workflow_id FROM project p " +
            "INNER JOIN workflow w ON p.workflow_id =w.wid WHERE pm_eid=#{eid}")
    @Results({
            @Result(property = "client",column = "client_id", one=@One(select = "com.achieveit.mapper.ClientMapper.getByCid")),
            @Result(property = "workflow",column = "workflow_id", one=@One(select = "com.achieveit.mapper.WorkflowMapper.getByWidCascade"))
    })
    List<Project> getByPmEidCascade(int eid);

    @Select("SELECT pid,name,starttime,endtime,technique,domain,function,client_id,workflow_id FROM project p " +
            "INNER JOIN workflow w ON p.workflow_id =w.wid WHERE qamanager_eid=#{eid}")
    @Results({
            @Result(property = "client",column = "client_id", one=@One(select = "com.achieveit.mapper.ClientMapper.getByCid")),
            @Result(property = "workflow",column = "workflow_id", one=@One(select = "com.achieveit.mapper.WorkflowMapper.getByWidCascade"))
    })
    List<Project> getByQaManagerEidCascade(int eid);

    @Select("SELECT pid,name,starttime,endtime,technique,domain,function,client_id,workflow_id FROM project p " +
            "INNER JOIN workflow w ON p.workflow_id =w.wid WHERE epgleader_eid=#{eid}")
    @Results({
            @Result(property = "client",column = "client_id", one=@One(select = "com.achieveit.mapper.ClientMapper.getByCid")),
            @Result(property = "workflow",column = "workflow_id", one=@One(select = "com.achieveit.mapper.WorkflowMapper.getByWidCascade"))
    })
    List<Project> getByEpgLeaderEidCascade(int eid);

    @Select("SELECT pid,name,starttime,endtime,technique,domain,function,client_id,workflow_id FROM project p " +
            "INNER JOIN workflow w ON p.workflow_id =w.wid WHERE configurer_eid=#{eid}")
    @Results({
            @Result(property = "client",column = "client_id", one=@One(select = "com.achieveit.mapper.ClientMapper.getByCid")),
            @Result(property = "workflow",column = "workflow_id", one=@One(select = "com.achieveit.mapper.WorkflowMapper.getByWidCascade"))
    })
    List<Project> getByConfigurerEidCascade(int eid);

    @Select("SELECT * FROM project")
    List<Project> getAllProjectIds();

    @Insert("insert into project(pid,name,starttime,endtime,technique,domain,client_id) " +
            "values(#{pid},#{name},#{starttime},#{endtime},#{technique},#{domain},#{client_id})")
    int add(Project ground);

    @Update("update project set workflow_id=#{workflow_id} where pid=#{pid}")
    int updateWorkflow(String pid, int workflow_id);

    @Select("select * from project where workflow_id=#{workflow_id}")
    List<Project> getByWid(int workflow_id);

    @Update("update project set " +
            "name = #{name}," +
            "starttime = #{starttime}," +
            "endtime = #{endtime}," +
            "technique = #{technique}," +
            "domain = #{domain}," +
            "function = #{function}," +
            "client_id = #{client_id}," +
            "workflow_id = #{workflow_id} " +
            "where pid=#{pid}")
    int updateProject(Project project);
}
