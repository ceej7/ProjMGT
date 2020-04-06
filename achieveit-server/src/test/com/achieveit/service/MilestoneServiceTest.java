package com.achieveit.service;

import com.achieveit.config.DateUtil;
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

import java.sql.Timestamp;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;


import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MilestoneServiceTest {
    private static MilestoneService milestoneService;
    @Mock
    private MilestoneMapper milestoneMapper;

    @BeforeEach
    public void init() {
        milestoneMapper = mock(MilestoneMapper.class);
        milestoneService = new MilestoneService(milestoneMapper);
    }

    //////////////getByPid()//////////////
    @Test
    public void happy_path_with_get_by_pid() throws Exception {
        Milestone milestone = new Milestone(1, null, null, null);
        List<Milestone> milestones = new ArrayList<>();
        milestones.add(milestone);

        when(milestoneMapper.getByPid("1")).thenReturn(milestones);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = milestoneService.getByPid("1");
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        assertNotNull(msg.getResponseMap().get("milestones"));
        verify(milestoneMapper).getByPid("1");
    }

    @Test
    public void exception_when_get_by_pid() throws Exception {
        when(milestoneMapper.getByPid("1")).thenThrow(new RuntimeException());
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = milestoneService.getByPid("1");
        assertEquals(404, msg.getStatus());
        verify(milestoneMapper).getByPid("1");
    }

    //////////////getByMid()//////////////
    @Test
    public void happy_path_with_get_by_mid() throws Exception {
        Milestone milestone = new Milestone(1, null, null, null);

        when(milestoneMapper.getByMid(1)).thenReturn(milestone);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = milestoneService.getByMid(1);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        assertNotNull(msg.getResponseMap().get("milestone"));
        verify(milestoneMapper).getByMid(1);
    }

    @Test
    public void exception_when_get_by_mid() throws Exception {
        when(milestoneMapper.getByMid(1)).thenThrow(new RuntimeException());
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = milestoneService.getByMid(1);
        assertEquals(404, msg.getStatus());
        verify(milestoneMapper).getByMid(1);
    }

    @Test
    public void no_such_milestone_when_get_by_mid() throws Exception {
        when(milestoneMapper.getByMid(1)).thenReturn(null);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = milestoneService.getByMid(1);
        assertEquals(210, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        assertNull(msg.getResponseMap().get("milestone"));
        verify(milestoneMapper).getByMid(1);
    }

    //////////////add()//////////////

    @Test
    public void happy_path_with_add() throws Exception {
        Timestamp timestamp = DateUtil.String2Timestamp("2020-04-09 16:00:00", "yyyy-MM-dd HH:mm:ss");
        String desc = "haha" ;
        String pid = "1";
        Milestone milestone = new Milestone(0, timestamp, desc, pid);
        when(milestoneMapper.add(any())).thenReturn(true);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = milestoneService.add(pid,timestamp,desc);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        verify(milestoneMapper).add(any());
    }

    @Test
    public void exception_when_add() throws Exception {
        Timestamp timestamp = DateUtil.String2Timestamp("2020-04-09 16:00:00", "yyyy-MM-dd HH:mm:ss");
        String desc = "haha" ;
        String pid = "1";
        when(milestoneMapper.add(any())).thenThrow(new RuntimeException());
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = milestoneService.add(pid,timestamp,desc);
        assertEquals(404, msg.getStatus());
        verify(milestoneMapper).add(any());
    }

    @Test
    public void failed_to_add() throws Exception {
        Timestamp timestamp = DateUtil.String2Timestamp("2020-04-09 16:00:00", "yyyy-MM-dd HH:mm:ss");
        String desc = "haha" ;
        String pid = "1";
        Milestone milestone = new Milestone(0, timestamp, desc, pid);
        when(milestoneMapper.add(any())).thenReturn(false);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = milestoneService.add(pid,timestamp,desc);
        assertEquals(212, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        verify(milestoneMapper).add(any());
    }

    //////////////deleteByMid()//////////////
    @Test
    public void happy_path_with_delete_by_mid() throws Exception {
        when(milestoneMapper.delete(1)).thenReturn(true);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = milestoneService.deleteByMid(1);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        verify(milestoneMapper).delete(1);
    }

    @Test
    public void exception_when_delete_by_mid() throws Exception {
        when(milestoneMapper.delete(1)).thenThrow(new RuntimeException());
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = milestoneService.deleteByMid(1);
        assertEquals(404, msg.getStatus());
        verify(milestoneMapper).delete(1);
    }

    @Test
    public void failed_to_delete_by_mid() throws Exception {
        when(milestoneMapper.delete(1)).thenReturn(false);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = milestoneService.deleteByMid(1);
        assertEquals(212, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        verify(milestoneMapper).delete(1);
    }

    //////////////update()//////////////
    @Test
    public void happy_path_with_update() throws Exception {
        Timestamp timestamp = DateUtil.String2Timestamp("2020-04-09 16:00:00", "yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp_new = DateUtil.String2Timestamp("2020-05-09 16:00:00", "yyyy-MM-dd HH:mm:ss");
        String desc = "haha" ,desc_new="xixi";
        String pid = "1";
        Milestone milestone = new Milestone(1, timestamp, desc, pid);

        Map<String,String> param=new HashMap<String, String>();
        param.put("desc",desc_new);
        param.put("time","2020-05-09T16:00:00.000Z");

        when(milestoneMapper.getByMid(1)).thenReturn(milestone);
        when(milestoneMapper.update(milestone)).thenReturn(1);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = milestoneService.update(1,param);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap());
        assertEquals(desc_new,milestone.getDesc());
        assertEquals(timestamp_new,milestone.getTime());
        verify(milestoneMapper).update(any());
    }

    @Test
    public void alternate_path_with_update() throws Exception {
        Timestamp timestamp = DateUtil.String2Timestamp("2020-04-09 16:00:00", "yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp_new = DateUtil.String2Timestamp("2020-05-09 16:00:00", "yyyy-MM-dd HH:mm:ss");
        String desc = "haha" ,desc_new="xixi";
        String pid = "1";
        Milestone milestone = new Milestone(1, timestamp, desc, pid);

        Map<String,String> param=new HashMap<String, String>();
        param.put("desc",desc_new);
        param.put("time","2020-05-09T16:00:00.000Z");

        when(milestoneMapper.getByMid(1)).thenReturn(milestone);
        when(milestoneMapper.update(milestone)).thenReturn(0);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = milestoneService.update(1,param);
        assertEquals(212, msg.getStatus());
    }

    @Test
    public void error_path_with_update() throws Exception {
        Timestamp timestamp = DateUtil.String2Timestamp("2020-04-09 16:00:00", "yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp_new = DateUtil.String2Timestamp("2020-05-09 16:00:00", "yyyy-MM-dd HH:mm:ss");
        String desc = "haha" ,desc_new="xixi";
        String pid = "1";
        Milestone milestone = new Milestone(1, timestamp, desc, pid);

        Map<String,String> param=new HashMap<String, String>();
        param.put("desc",desc_new);
        param.put("time","2020-05-09T16:00:00.000Z");

        when(milestoneMapper.getByMid(1)).thenThrow(new RuntimeException());
        when(milestoneMapper.update(milestone)).thenReturn(1);
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "请求出现异常");
        msg = milestoneService.update(1,param);
        assertEquals(404, msg.getStatus());
    }

}

