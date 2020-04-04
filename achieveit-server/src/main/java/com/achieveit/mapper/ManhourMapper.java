package com.achieveit.mapper;

import com.achieveit.entity.Activity;
import com.achieveit.entity.Manhour;
import com.achieveit.entity.Project;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ManhourMapper {

    @Select("select * from manhour where mid=#{mid}")
    Manhour getByMid(int mid);

    @Select("select * from manhour where mid=#{mid}")
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

    @Options(useGeneratedKeys = true,keyProperty = "mid")
    @Insert("insert into " +
            "manhour(fid, date, starttime, endtime, status,employee_project_id, activity_id) " +
            "values(#{fid}, #{date},#{starttime}, #{endtime}, #{status},#{employee_project_id}, #{activity_id})")
    int add(Manhour manhour);

    @Select("select * from activity where aid=#{aid}")
    Activity getActivityByAid(int aid);

    @Delete("delete from manhour where mid=#{mid}")
    int deleteManhour(int mid);

    @Update("update manhour set " +
            "fid = #{fid}," +
            "starttime = #{starttime}," +
            "endtime = #{endtime}," +
            "status = #{status}," +
            "activity_id = #{activity_id} "+
            "where mid=#{mid}")
    int update(Manhour manhour);

    @Select("select m.mid,m.fid,m.date,m.starttime,m.endtime,m.status, m.employee_project_id, m.activity_id " +
            "from manhour m " +
            "inner join employee_project ep1 on m.employee_project_id=ep1.epid " +
            "INNER join employee_project ep2 on ep1.superior_epid=ep2.epid " +
            "where ep2.employee_id=#{eid};")
    @Results({
            @Result(property = "employeeProject", column = "employee_project_id", one = @One(select = "com.achieveit.mapper.EmployeeProjectMapper.getByEpidCascade")),
            @Result(property = "activity", column = "activity_id", one = @One(select = "com.achieveit.mapper.ActivityMapper.getByAid")),
    })
    List<Manhour> getSubManhour(int eid);
}
