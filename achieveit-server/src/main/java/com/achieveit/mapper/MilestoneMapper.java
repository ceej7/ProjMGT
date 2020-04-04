package com.achieveit.mapper;

import com.achieveit.entity.Milestone;
import com.achieveit.entity.Workflow;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilestoneMapper {
    @Select("select * from milestone where project_id=#{pid}")
    List<Milestone> getByPid(String pid);

    @Select("select * from milestone where mid=#{mid}")
    Milestone getByMid(int mid);

    @Options(useGeneratedKeys = true,keyProperty = "mid")
    @Insert("insert into " +
            "milestone(time, milestone.desc, project_id) " +
            "values(#{time}, #{desc}, #{project_id})")
    boolean add(Milestone milestone);

    @Delete("delete from milestone where mid=#{mid}")
    boolean delete(int mid);

    @Update("update milestone set milestone.desc=#{desc}, time=#{time} where milestone.mid=#{mid}")
    int update(Milestone milestone);


}
