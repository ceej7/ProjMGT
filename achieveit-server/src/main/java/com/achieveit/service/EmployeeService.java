package com.achieveit.service;

import com.achieveit.entity.Employee;
import com.achieveit.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    public EmployeeService(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    EmployeeMapper employeeMapper;

    public Employee getById(int eid){
        return employeeMapper.getById(eid);
    }

    public List<Employee> getByTitle(String title){
        return employeeMapper.getByTitle(title);
    }
}
