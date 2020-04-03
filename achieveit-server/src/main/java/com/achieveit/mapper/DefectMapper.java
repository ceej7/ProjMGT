package com.achieveit.mapper;

import com.achieveit.entity.Defect;
import org.apache.ibatis.annotations.*;
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

    @Select("SELECT d.did,d.authority,d.desc,d.git_repo,d.commit,d.status,d.project_id,d.employee_project_id FROM employee e INNER JOIN employee_project ep on e.eid = ep.employee_id INNER JOIN defect d on ep.epid=d.employee_project_id WHERE eid =#{eid} and d.desc like CONCAT('%',#{desc},'%')")
    @Results({
            @Result(property = "project", column = "project_id", one = @One(select = "com.achieveit.mapper.ProjectMapper.getByPid")),
            @Result(property = "employeeProject", column = "employee_project_id", one = @One(select = "com.achieveit.mapper.EmployeeProjectMapper.getByEpid"))
    })
    List<Defect> getDescedByEidCascade(int eid, String desc);


    @Select("select * from defect where project_id=#{pid}")
    @Results({
            @Result(property = "employeeProject", column = "employee_project_id", one = @One(select = "com.achieveit.mapper.EmployeeProjectMapper.getByEpidCascade"))
    })
    List<Defect> getByPidCascade(String pid);

    @Delete("delete from defect where did=#{did}")
    int deleteByDid(int did);

    @Options(useGeneratedKeys = true,keyProperty = "did")
    @Insert("insert into defect(authority,defect.desc,git_repo,commit,status,project_id,employee_project_id) " +
            "values(#{authority},#{desc},#{git_repo},#{commit},#{status},#{project_id},#{employee_project_id})")
    int add(Defect defect);

    @Update("update defect set " +
            "authority=#{authority}, "+
            "defect.desc=#{desc}, "+
            "git_repo=#{git_repo}, "+
            "commit=#{commit}, "+
            "status=#{status}, "+
            "project_id=#{project_id}, "+
            "employee_project_id=#{employee_project_id} "+
            "where did=#{did} ")
    int update(Defect defect);
}
