package com.achieveit.service;

import com.achieveit.entity.Test;
import com.achieveit.mapper.TestMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    private final TestMapper testMapper;

    public TestService(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    public Test getTestById(int tid){
        return testMapper.getTestById(tid);
    }

    public List<Test> getAllTests(){
        return testMapper.getAllTests();
    }

    public int addTest(Test test){
        return testMapper.addTest(test);
    }

    public int deleteTest(int tid){
        return testMapper.deleteTest(tid);
    }

    public int updateTest(Test test){
        return testMapper.updateTest(test);
    }
}
