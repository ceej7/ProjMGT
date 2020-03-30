package com.achieveit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkflowEngineServiceTest {
    WorkflowEngineService workflowEngineService;

    @BeforeEach
    void setup(){
        workflowEngineService=new WorkflowEngineService(32);
        workflowEngineService.setDependency(1, 0);
        workflowEngineService.setDependency(2, 1);
        workflowEngineService.setDependency(3, 1);
        workflowEngineService.setDependency(4, 2);
        workflowEngineService.setDependency(4, 3);
        workflowEngineService.setDependency(5, 4);
        workflowEngineService.setDependency(6, 5);
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
}