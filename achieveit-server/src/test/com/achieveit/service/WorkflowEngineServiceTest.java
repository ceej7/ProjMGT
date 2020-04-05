package com.achieveit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkflowEngineServiceTest {
    WorkflowEngineService workflowEngineService;

    @BeforeEach
    void setup(){
        workflowEngineService.setDependency(1, 0,32);
        workflowEngineService.setDependency(2, 1,32);
        workflowEngineService.setDependency(3, 1,32);
        workflowEngineService.setDependency(4, 2,32);
        workflowEngineService.setDependency(4, 3,32);
        workflowEngineService.setDependency(5, 4,32);
        workflowEngineService.setDependency(6, 5,32);
        workflowEngineService=new WorkflowEngineService();
    }

    @Test
    void happy_path_isTodo() {
        assertEquals(true,
                workflowEngineService.isTodo(4,15));
        assertEquals(true,
                workflowEngineService.isTodo(5,31));
        assertEquals(true,
                workflowEngineService.isTodo(6,63));
    }

    @Test
    void error_path_isTodo_oob() {
        assertEquals(true,
                workflowEngineService.isTodo(4,15));
        assertEquals(true,
                workflowEngineService.isTodo(5,31));
        assertEquals(false,
                workflowEngineService.isTodo(33,63));
    }

    @Test
    void error_path_isTodo_have_finished() {
        assertEquals(false,
                workflowEngineService.isTodo(3,15));
    }


    @Test
    void alternate_path_isTodo() {
        assertEquals(false,
                workflowEngineService.isTodo(4,11));
        assertEquals(false,
                workflowEngineService.isTodo(5,15));
        assertEquals(false,
                workflowEngineService.isTodo(6,31));
    }

    @Test
    void happy_path_checkTodo() {
        assertEquals(31,
                workflowEngineService.checkTodo(4,15));
        assertEquals(63,
                workflowEngineService.checkTodo(5,31));
        assertEquals(127,
                workflowEngineService.checkTodo(6,63));
    }
    @Test
    void alter_path_checkTodo() {
        assertEquals(11,
                workflowEngineService.checkTodo(4,11));
        assertEquals(15,
                workflowEngineService.checkTodo(5,15));
        assertEquals(31,
                workflowEngineService.checkTodo(6,31));
    }

    @Test
    void happy_path_uncheckTodo() {
        assertEquals(0, workflowEngineService.uncheckTodo(0, 1));
    }

    @Test
    void error_path_uncheckTodo() {
        assertEquals(1
                , workflowEngineService.uncheckTodo(34, 1));
    }
}