package com.achieveit.mapper;

import com.achieveit.entity.EmployeeProject;
import com.achieveit.entity.EmployeeRoleProject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProjectMapper {
    @Select("select * from employee_project where epid=#{epid}")
    @Results({
            @Result(property = "roles", column = "epid", many = @Many(select = "com.achieveit.mapper.EmployeeProjectMapper.getEmployeeRoleProjectByEpid"))
    })
    EmployeeProject getByEpid(int epid);

    @Select("select * from employee_project where epid=#{epid}")
    @Results({
            @Result(property = "sup", column = "superior_epid", one = @One(select = "com.achieveit.mapper.EmployeeProjectMapper.getByEpid")),
            @Result(property = "project", column = "project_id", one = @One(select = "com.achieveit.mapper.ProjectMapper.getByPid")),
            @Result(property = "employee", column = "employee_id", one = @One(select = "com.achieveit.mapper.EmployeeMapper.getByEid")),
            @Result(property = "roles", column = "epid", many = @Many(select = "com.achieveit.mapper.EmployeeProjectMapper.getEmployeeRoleProjectByEpid"))
    })
    EmployeeProject getByEpidCascade(int epid);

    @Select("select role, employee_project_id from employee_role_project where employee_project_id=#{epid}")
    List<EmployeeRoleProject> getEmployeeRoleProjectByEpid(int epid);

    @Select("select epid,superior_epid,defect_authority,project_id,employee_id from employee_project where employee_id=#{eid}")
    @Results({
            @Result(property = "sup", column = "superior_epid", one = @One(select = "com.achieveit.mapper.EmployeeProjectMapper.getByEpid")),
            @Result(property = "project", column = "project_id", one = @One(select = "com.achieveit.mapper.ProjectMapper.getByPidCascade")),
            @Result(property = "employee", column = "employee_id", one = @One(select = "com.achieveit.mapper.EmployeeMapper.getByEid")),
            @Result(property = "roles", column = "epid", many = @Many(select = "com.achieveit.mapper.EmployeeProjectMapper.getEmployeeRoleProjectByEpid"))
    })
    List<EmployeeProject> getByEidCascade(int eid);

    @Options(useGeneratedKeys = true,keyProperty = "epid")
    @Insert("insert into " +
            "employee_project(superior_epid, defect_authority,project_id, employee_id) " +
            "values(#{superior_epid}, #{defect_authority},#{project_id}, #{employee_id})")
    int addEmployeeProject(EmployeeProject employeeProject);

    @Insert("insert into " +
            "employee_role_project(employee_project_id, role) " +
            "values(#{employee_project_id}, #{role})")
    int addEmployeeRoleProject(EmployeeRoleProject employeeRoleProject);

    @Select("select epid,superior_epid,defect_authority,project_id,employee_id from employee_project ep " +
            "INNER JOIN employee_role_project erp ON ep.epid=erp.employee_project_id where erp.role=#{role} and ep.project_id=#{project_id}")
    @Results({
            @Result(property = "roles", column = "epid", many = @Many(select = "com.achieveit.mapper.EmployeeProjectMapper.getEmployeeRoleProjectByEpid"))
    })
    List<EmployeeProject> getEmployeeProjectByRole(String project_id, String role);

    @Select("select * from employee_project where project_id=#{project_id} and employee_id=#{employee_id}")
    @Results({
            @Result(property = "roles", column = "epid", many = @Many(select = "com.achieveit.mapper.EmployeeProjectMapper.getEmployeeRoleProjectByEpid"))
    })
    List<EmployeeProject> getEmployeeProject(String project_id, int employee_id);

    @Delete("delete from employee_project where epid=#{epid}")
    int delete(int epid);

    @Delete("delete from employee_role_project where employee_project_id=#{epid}")
    int deleteEmployeeRoleProject(int epid);

    @Select("select epid,superior_epid,defect_authority,project_id,employee_id from employee_project where project_id=#{pid}")
    @Results({
            @Result(property = "sup", column = "superior_epid", one = @One(select = "com.achieveit.mapper.EmployeeProjectMapper.getByEpid")),
            @Result(property = "employee", column = "employee_id", one = @One(select = "com.achieveit.mapper.EmployeeMapper.getByEid")),
            @Result(property = "roles", column = "epid", many = @Many(select = "com.achieveit.mapper.EmployeeProjectMapper.getEmployeeRoleProjectByEpid"))
    })
    List<EmployeeProject> getByPidCascade(String pid);
}
