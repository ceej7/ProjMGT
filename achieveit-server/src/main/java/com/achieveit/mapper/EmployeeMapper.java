package com.achieveit.mapper;

import com.achieveit.entity.Employee;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper {
    @Select("select * from employee where eid=#{eid}")
    public Employee getById(int eid);

    @Select("select * from employee where title=#{title}")
    public List<Employee> getByTitle(String title);

    @Select("select * from employee where name=#{name}")
    public List<Employee> getByName(String name);
}
