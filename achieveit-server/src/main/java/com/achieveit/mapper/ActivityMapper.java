package com.achieveit.mapper;

import com.achieveit.entity.Activity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityMapper {
    @Select("Select * from activity where aid=#{aid}")
    Activity getByAid(int aid);
}
