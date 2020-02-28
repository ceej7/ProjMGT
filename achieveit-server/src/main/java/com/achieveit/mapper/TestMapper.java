package com.achieveit.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import com.achieveit.entity.Test;

import java.util.List;

@Repository
public interface TestMapper {
    @Select("select * from test where tid=#{tid}")
    Test getTestById(int tid);

    @Select("select * from test")
    List<Test> getAllTests();

    @Options(useGeneratedKeys = true,keyProperty = "tid")
    @Insert("insert into test(description) values(#{description})")
    int addTest(Test test);

    @Delete("delete from test where tid=#{tid}")
    int deleteTest(int tid);

    @Update("update test set description=#{description} where tid=#{tid}")
    int updateTest(Test test);
}
