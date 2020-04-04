package com.achieveit.mapper;

import com.achieveit.entity.Risk;
import com.achieveit.entity.RiskRelation;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskMapper {
    @Select("select * from risk_relation where risk_id=#{rid}")
    @Results({
            @Result(property = "employeeProject",column = "employee_project_id", one=@One(select = "com.achieveit.mapper.EmployeeProjectMapper.getByEpidCascade_EmployeeInfo"))
    })
    List<RiskRelation> getRiskRelationsByRidCascade(int rid);

    @Select("SELECT * FROM risk WHERE employee_id=#{eid}")
    @Results({
            @Result(property = "employee",column = "employee_id", one=@One(select = "com.achieveit.mapper.EmployeeMapper.getByEid")),
            @Result(property = "project",column = "project_id", one=@One(select = "com.achieveit.mapper.ProjectMapper.getByPid")),
            @Result(property = "relations",column = "rid", many=@Many(select = "com.achieveit.mapper.RiskMapper.getRiskRelationsByRidCascade"))
    })
    List<Risk> getByEidCascade(int eid);

    @Select("SELECT * FROM risk WHERE project_id=#{pid}")
    @Results({
            @Result(property = "employee",column = "employee_id", one=@One(select = "com.achieveit.mapper.EmployeeMapper.getByEid")),
            @Result(property = "relations",column = "rid", many=@Many(select = "com.achieveit.mapper.RiskMapper.getRiskRelationsByRidCascade"))
    })
    List<Risk> getByPidCascade(String pid);

    @Select("SELECT * FROM risk WHERE template=true")
    @Results({
            @Result(property = "employee",column = "employee_id", one=@One(select = "com.achieveit.mapper.EmployeeMapper.getByEid")),
            @Result(property = "relations",column = "rid", many=@Many(select = "com.achieveit.mapper.RiskMapper.getRiskRelationsByRidCascade"))
    })
    List<Risk> getByTemplate();

    @Select("select * from risk where rid=#{rid}")
    @Results({
            @Result(property = "employee",column = "employee_id", one=@One(select = "com.achieveit.mapper.EmployeeMapper.getByEid")),
            @Result(property = "relations",column = "rid", many=@Many(select = "com.achieveit.mapper.RiskMapper.getRiskRelationsByRidCascade"))
    })
    Risk getByRid(int rid);

    @Delete("delete from risk where rid=#{rid}")
    int delete(int rid);

    @Options(useGeneratedKeys = true,keyProperty = "rid")
    @Insert("insert into " +
            "risk(risk.type, risk.desc, risk.grade, risk.influence, risk.strategy, risk.frequency, risk.template, risk.employee_id, risk.project_id) " +
            "values(#{type}, #{desc},#{grade}, #{influence}, #{strategy}, #{frequency}, #{template}, #{employee_id}, #{project_id})")
    int add(Risk risk);

    @Insert("insert into " +
            "risk_relation(employee_project_id, risk_id) " +
            "values(#{epid}, #{rid})")
    int addRelation(Integer rid, Integer epid);

    @Update("update risk set " +
            "risk.type = #{type}," +
            "risk.desc = #{desc}," +
            "risk.grade = #{grade}," +
            "risk.influence = #{influence}," +
            "risk.strategy = #{strategy}," +
            "risk.frequency = #{frequency}," +
            "risk.template = #{template}," +
            "risk.employee_id = #{employee_id}," +
            "risk.project_id = #{project_id} "+
            "where risk.rid=#{rid}")
    int update(Risk risk);

    @Delete("delete from risk_relation where risk_id=#{rid}")
    int deleteRelationsByRid(int rid);
}
