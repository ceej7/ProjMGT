package com.achieveit.mapper;

import com.achieveit.entity.Activity;
import com.achieveit.entity.Manhour;
import com.achieveit.entity.Project;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ManhourMapper {

    @Select("select * from Manhour where mid=#{mid}")
    Manhour getByMid(int mid);

    @Select("select * from Manhour where mid=#{mid}")
    @Results({
            @Result(property = "employeeProject", column = "employee_project_id", one = @One(select = "com.achieveit.mapper.EmployeeProjectMapper.getByEpidCascade")),
            @Result(property = "activity", column = "activity_id", one = @One(select = "com.achieveit.mapper.ActivityMapper.getByAid")),
    })
    Manhour getByMidCascade(int mid);

    @Select("SELECT mid,fid,date,starttime,endtime,status,employee_project_id,activity_id FROM employee e INNER JOIN employee_project ep on e.eid = ep.employee_id INNER JOIN manhour m on ep.epid=m.employee_project_id where eid =#{eid}")
    @Results({
            @Result(property = "employeeProject", column = "employee_project_id", one = @One(select = "com.achieveit.mapper.EmployeeProjectMapper.getByEpidCascade")),
            @Result(property = "activity", column = "activity_id", one = @One(select = "com.achieveit.mapper.ActivityMapper.getByAid")),
    })
    List<Manhour> getByEidCascade(int eid);

    @Select("SELECT mid,fid,date,starttime,endtime,status,employee_project_id,activity_id FROM employee e INNER JOIN employee_project ep on e.eid = ep.employee_id INNER JOIN manhour m on ep.epid=m.employee_project_id where eid =#{eid} and date=#{date}")
    @Results({
            @Result(property = "employeeProject", column = "employee_project_id", one = @One(select = "com.achieveit.mapper.EmployeeProjectMapper.getByEpidCascade")),
            @Result(property = "activity", column = "activity_id", one = @One(select = "com.achieveit.mapper.ActivityMapper.getByAid")),
    })
    List<Manhour> getDatedByEidCascade(int eid, Date date);

    @Select("select * from activity")
    List<Activity> getActivity();
}
