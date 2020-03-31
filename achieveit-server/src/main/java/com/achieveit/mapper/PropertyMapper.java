package com.achieveit.mapper;

import com.achieveit.entity.Property;
import com.achieveit.entity.PropertyOccupy;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyMapper {
    @Select("SELECT * from property where pid=#{pid}")
    Property getByPid(int pid);

    @Select("SELECT poid,expire_time,is_intact,property_id,project_id,employee_id FROM property_occupy po INNER JOIN property p on po.property_id = p.pid WHERE po.employee_id = #{eid}")
    @Results({
            @Result(property = "property",column = "property_id", one=@One(select = "com.achieveit.mapper.PropertyMapper.getByPid")),
            @Result(property = "project",column = "project_id", one=@One(select = "com.achieveit.mapper.ProjectMapper.getByPid"))
    })
    List<PropertyOccupy> getByEid(int eid);
}
