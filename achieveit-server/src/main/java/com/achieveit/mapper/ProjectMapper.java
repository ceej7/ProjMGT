package com.achieveit.mapper;

import com.achieveit.entity.Project;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMapper {
    @Select("select * from project where pid=#{pid}")
    public Project getById(int pid);
}
