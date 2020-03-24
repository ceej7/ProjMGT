package com.achieveit.mapper;

import com.achieveit.entity.Defect;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefectMapper {
    @Select("select * from defect where did=#{did}")
    Defect getByDid(int did);

    @Select("select * from defect where did=#{did}")
    @Results({
            @Result(property = "project", column = "project_id", one = @One(select = "com.achieveit.mapper.ProjectMapper.getByPid")),
            @Result(property = "employeeProject", column = "employee_project_id", one = @One(select = "com.achieveit.mapper.EmployeeProjectMapper.getByEpid"))
    })
    Defect getByDidCascade(int did);

    @Select("SELECT d.did,d.authority,d.desc,d.git_repo,d.commit,d.status,d.project_id,d.employee_project_id FROM employee e INNER JOIN employee_project ep on e.eid = ep.employee_id INNER JOIN defect d on ep.epid=d.employee_project_id WHERE eid =#{eid}")
    @Results({
            @Result(property = "project", column = "project_id", one = @One(select = "com.achieveit.mapper.ProjectMapper.getByPid")),
            @Result(property = "employeeProject", column = "employee_project_id", one = @One(select = "com.achieveit.mapper.EmployeeProjectMapper.getByEpid"))
    })
    List<Defect> getByEidCascade(int eid);
}
