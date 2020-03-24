package com.achieveit.mapper;

import com.achieveit.entity.Project;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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
            @Result(property = "workflow",column = "workflow_id", one=@One(select = "com.achieveit.mapper.WorkflowMapper.getByWid"))
    })
    List<Project> getByEidCascade(int eid);

    @Select("SELECT pid,name,starttime,endtime,technique,domain,function,client_id,workflow_id FROM project p INNER JOIN workflow w ON p.workflow_id =w.wid WHERE sup_eid=#{eid}")
    @Results({
            @Result(property = "client",column = "client_id", one=@One(select = "com.achieveit.mapper.ClientMapper.getByCid")),
            @Result(property = "workflow",column = "workflow_id", one=@One(select = "com.achieveit.mapper.WorkflowMapper.getByWid"))
    })
    List<Project> getBySupEidCascade(int eid);
}
