package com.achieveit.mapper;

import com.achieveit.entity.EmployeeProject;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProjectMapper {
    @Select("select * from employee_project where epid=#{epid}")
    EmployeeProject getByEpid(int epid);

    @Select("select * from employee_project where epid=#{epid}")
    @Results({
            @Result(property = "sup", column = "superior_epid", one = @One(select = "com.achieveit.mapper.EmployeeProjectMapper.getByEpid")),
            @Result(property = "project", column = "project_id", one = @One(select = "com.achieveit.mapper.ProjectMapper.getByPid")),
            @Result(property = "employee", column = "employee_id", one = @One(select = "com.achieveit.mapper.EmployeeMapper.getByEid"))
    })
    EmployeeProject getByEpidCascade(int epid);

    @Select("select epid,superior_epid,defect_authority,project_id,employee_id from employee_project where employee_id=#{eid}")
    @Results({
            @Result(property = "sup", column = "superior_epid", one = @One(select = "com.achieveit.mapper.EmployeeProjectMapper.getByEpid")),
            @Result(property = "project", column = "project_id", one = @One(select = "com.achieveit.mapper.ProjectMapper.getByPidCascade")),
            @Result(property = "employee", column = "employee_id", one = @One(select = "com.achieveit.mapper.EmployeeMapper.getByEid"))
    })
    List<EmployeeProject> getByEidCascade(int eid);


}
