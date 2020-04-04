package com.achieveit.mapper;

import com.achieveit.entity.Property;
import com.achieveit.entity.PropertyOccupy;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyMapper {
    @Select("SELECT * from property where pid=#{pid}")
    Property getByPid(int pid);

    @Select("SELECT * from property")
    List<Property> getAll();

    @Select("SELECT poid,expire_time,is_intact,property_id,project_id,employee_id FROM property_occupy po INNER JOIN property p on po.property_id = p.pid WHERE po.employee_id = #{eid}")
    @Results({
            @Result(property = "property",column = "property_id", one=@One(select = "com.achieveit.mapper.PropertyMapper.getByPid")),
            @Result(property = "project",column = "project_id", one=@One(select = "com.achieveit.mapper.ProjectMapper.getByPid"))
    })
    List<PropertyOccupy> getPropertyOccupyByEid(int eid);

    @Select("SELECT poid,expire_time,is_intact,property_id,project_id,employee_id FROM property_occupy po INNER JOIN property p on po.property_id = p.pid WHERE po.project_id = #{pid}")
    @Results({
            @Result(property = "property",column = "property_id", one=@One(select = "com.achieveit.mapper.PropertyMapper.getByPid")),
            @Result(property = "project",column = "project_id", one=@One(select = "com.achieveit.mapper.ProjectMapper.getByPid"))
    })
    List<PropertyOccupy> getPropertyOccupyByProjectId(String pid);


    @Select("SELECT poid,expire_time,is_intact,property_id,project_id,employee_id FROM property_occupy po INNER JOIN property p on po.property_id = p.pid WHERE po.property_id = #{property_id}")
    List<PropertyOccupy> getPropertyOccupyByPropertyId(int property_id);

    @Options(useGeneratedKeys = true,keyProperty = "poid")
    @Insert("insert into " +
            "property_occupy(expire_time, is_intact,property_id, project_id, employee_id) " +
            "values(#{expire_time}, #{is_intact},#{property_id}, #{project_id}, #{employee_id})")
    int addPropertyOccupy(PropertyOccupy propertyOccupy);

    @Select("select * from property_occupy where poid=#{poid}")
    PropertyOccupy getByPoid(int poid);

    @Update("update property_occupy set " +
            "expire_time = #{expire_time}," +
            "is_intact = #{is_intact}," +
            "property_id = #{property_id}," +
            "project_id = #{project_id}," +
            "employee_id = #{employee_id} "+
            "where poid=#{poid}")
    int updatePropertyOccupy(PropertyOccupy propertyOccupy);
}
