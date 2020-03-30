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
import java.sql.Date;
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
public class ManhourServiceTest {
    private static ManhourService manhourService;
    @Mock
    private ManhourMapper manhourMapper;


    @BeforeEach
    public void init() {
        manhourMapper = mock(ManhourMapper.class);
        manhourService = new ManhourService(manhourMapper);
    }

    @Test
    public void happy_path_with_get_paged_manhour_by_eid() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, null, null, null);
        List<Manhour> manhours = new ArrayList<>();
        manhours.add(manhour);

        when(manhourMapper.getByEidCascade(1)).thenReturn(manhours);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.getPagedManhourByEid(1, 0, 1);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        assertNotNull(msg.getResponseMap().get("Manhour"));
        assertNotNull(msg.getResponseMap().get("page_length"));
        verify(manhourMapper).getByEidCascade(1);
    }

    @Test
    public void happy_path_with_get_filtered_paged_manhour_by_eid() throws Exception {
        Manhour manhour = new Manhour(1, null, null, null, null, null, null, null);
        List<Manhour> manhours = new ArrayList<>();
        manhours.add(manhour);

        Date date=new Date(2020-3-29);
        when(manhourMapper.getDatedByEidCascade(1,date)).thenReturn(manhours);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = manhourService.getFilteredPagedManhourByEid(1, 0, 1,date);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        assertNotNull(msg.getResponseMap().get("Manhour"));
        assertNotNull(msg.getResponseMap().get("page_length"));
        verify(manhourMapper).getDatedByEidCascade(1,date);
    }

}
