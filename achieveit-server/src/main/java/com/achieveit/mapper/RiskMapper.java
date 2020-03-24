package com.achieveit.mapper;

import com.achieveit.entity.Risk;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskMapper {
    @Select("SELECT * FROM risk WHERE employee_id=#{eid}")
    @Results({
            @Result(property = "employee",column = "employee_id", one=@One(select = "com.achieveit.mapper.EmployeeMapper.getByEid")),
            @Result(property = "project",column = "project_id", one=@One(select = "com.achieveit.mapper.ProjectMapper.getByPid"))
    })
    List<Risk> getByEidCascade(int eid);
}
