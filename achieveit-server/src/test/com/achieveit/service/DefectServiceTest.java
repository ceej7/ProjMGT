package com.achieveit.service;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.*;
import com.achieveit.mapper.*;
import com.achieveit.service.EmployeeService;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import com.achieveit.service.WorkflowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Assert;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;


import java.io.File;
import java.util.*;

import static org.mockito.Mockito.*;

public class DefectServiceTest {
    private static DefectService defectService;
    @Mock
    private DefectMapper defectMapper;
    @Mock
    private EmployeeProjectMapper employeeProjectMapper;


    @BeforeEach
    public void init() {
        defectMapper = mock(DefectMapper.class);
        employeeProjectMapper = mock(EmployeeProjectMapper.class);
        defectService = new DefectService(defectMapper,employeeProjectMapper);
    }

    //////////////getPagedDefectByEid()//////////////
    @Test
    public void happy_path_with_get_paged_defect_by_eid() throws Exception {
        Defect defect = new Defect(1, null, null, null, null, null, null, null);
        List<Defect> defects = new ArrayList<>();
        defects.add(defect);

        when(defectMapper.getByEidCascade(1)).thenReturn(defects);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = defectService.getPagedDefectByEid(1, 0, 1);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        assertNotNull(msg.getResponseMap().get("Defect"));
        assertNotNull(msg.getResponseMap().get("page_length"));
        verify(defectMapper).getByEidCascade(1);
    }

    @Test
    public void exception_when_get_paged_defect_by_eid() throws Exception {
        when(defectMapper.getByEidCascade(1)).thenThrow(new RuntimeException());
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = defectService.getPagedDefectByEid(1, 0, 1);
        assertEquals(404, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        verify(defectMapper).getByEidCascade(1);
    }


    //////////////getFilteredPagedDefectByEid()//////////////
    @Test
    public void happy_path_with_get_filtered_paged_defect_by_eid() throws Exception {
        Defect defect = new Defect(1, null, null, null, null, null, null, null);
        List<Defect> defects = new ArrayList<>();
        defects.add(defect);

        when(defectMapper.getDescedByEidCascade(1,"filter")).thenReturn(defects);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = defectService.getFilteredPagedDefectByEid(1, 0, 1,"filter",null);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        assertNotNull(msg.getResponseMap().get("Defect"));
        assertNotNull(msg.getResponseMap().get("page_length"));
        verify(defectMapper).getDescedByEidCascade(1,"filter");
    }

    @Test
    public void exception_when_get_filtered_paged_defect_by_eid() throws Exception {
        when(defectMapper.getDescedByEidCascade(1,"filter")).thenThrow(new RuntimeException());
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = defectService.getFilteredPagedDefectByEid(1, 0, 1,"filter");
        assertEquals(404, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        verify(defectMapper).getDescedByEidCascade(1,"filter");
    }


}
