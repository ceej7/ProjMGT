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
public class ClientServiceTest {
    private static ClientService clientService;
    @Mock
    private ClientMapper clientMapper;

    @BeforeEach
    public void init() {
        clientMapper = mock(ClientMapper.class);
        clientService = new ClientService(clientMapper);
    }

//    //////////////getAll()//////////////
//    @Test
//    public void happy_path_with_get_all() throws Exception {
//        Client client = new Client(1, null, null, null, null, null, null);
//        List<Client> clients = new ArrayList<>();
//        clients.add(client);
//
//        when(clientMapper.getAll()).thenReturn(clients);
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = clientService.getAll();
//        assertEquals(200, msg.getStatus());
//        assertNotNull(msg.getResponseMap());
//        assertNotNull(msg.getResponseMap().get("clients"));
//        verify(clientMapper).getAll();
//    }
//
//    @Test
//    public void exception_when_get_all() throws Exception {
//        when(clientMapper.getAll()).thenThrow(new RuntimeException());
//        ResponseMsg msg = new ResponseMsg();
//        msg.setStatusAndMessage(404, "请求出现异常");
//        msg = clientService.getAll();
//        assertEquals(404, msg.getStatus());
//        verify(clientMapper).getAll();
//    }


}
