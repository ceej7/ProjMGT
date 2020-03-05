package com.achieveit.mapper;

import com.achieveit.entity.Workflow;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import com.achieveit.entity.Test;

import java.util.List;

@Repository
public interface WorkflowMapper {

    @Select("select * from workflow where wid=#{wid}")
    public Workflow getById(int wid);
}
