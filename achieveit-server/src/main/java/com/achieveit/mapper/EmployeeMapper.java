package com.achieveit.mapper;

import com.achieveit.entity.Employee;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper {
    @Select("select * from employee where eid=#{eid}")
    Employee getByEid(int eid);

    @Select("select * from employee where eid=#{eid}")
    @Results({
            @Result(property = "sup", column = "sup_eid",
                    one = @One(select = "com.achieveit.mapper.EmployeeMapper.getByEid"))
    })
    Employee getByEidCascade(int eid);

    @Select("select * from employee where title=#{title}")
    List<Employee> getByTitle(String title);

    @Select("select * from employee where name=#{name}")
    List<Employee> getByName(String name);

    @Select("select * from employee")
    List<Employee> getAll();
}
