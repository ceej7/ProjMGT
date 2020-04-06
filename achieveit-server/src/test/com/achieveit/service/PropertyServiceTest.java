package com.achieveit.service;

import com.achieveit.entity.Property;
import com.achieveit.entity.PropertyOccupy;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.mapper.PropertyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PropertyServiceTest {

    PropertyMapper propertyMapper;
    PropertyService propertyService;

    @BeforeEach
    void setup(){
        propertyMapper = mock(PropertyMapper.class);
        propertyService=new PropertyService(propertyMapper);
    }

    @Test
    void happy_path_getByPropertyId_ret200() {
        when(propertyMapper.getByPid(anyInt())).thenReturn(new Property());
        ResponseMsg msg =propertyService.getByPropertyId(0);
        assertEquals(200, msg.getStatus());
        when(propertyMapper.getByPid(anyInt())).thenReturn(null);
        msg =propertyService.getByPropertyId(0);
        assertEquals(212, msg.getStatus());

    }

    @Test
    void error_path_getByPropertyId_ret404() {
        when(propertyMapper.getByPid(anyInt())).thenThrow(new RuntimeException());
        ResponseMsg msg =propertyService.getByPropertyId(0);
        assertEquals(404, msg.getStatus());

    }

    @Test
    void happy_path_getAllProperty_ret200() {
        when(propertyMapper.getAll()).thenReturn(null);
        ResponseMsg msg =propertyService.getAllProperty();
        assertEquals(200, msg.getStatus());
    }

    @Test
    void error_path_getAllProperty_ret200() {
        when(propertyMapper.getAll()).thenThrow(new RuntimeException());
        ResponseMsg msg =propertyService.getAllProperty();
        assertEquals(404, msg.getStatus());
    }

    @Test
    void happy_path_getByEmployeeId_ret200() {
        List<PropertyOccupy> propertyOccupies = new ArrayList<>();
        propertyOccupies.add(new PropertyOccupy());
        when(propertyMapper.getPropertyOccupyByEid(anyInt())).thenReturn(propertyOccupies);
        ResponseMsg msg =propertyService.getByEmployeeId(0);
        assertEquals(200, msg.getStatus());
        when(propertyMapper.getPropertyOccupyByEid(anyInt())).thenReturn(null);
        msg =propertyService.getByEmployeeId(0);
        assertEquals(212, msg.getStatus());
    }

    @Test
    void error_path_getByEmployeeId_ret404() {
        List<PropertyOccupy> propertyOccupies = new ArrayList<>();
        propertyOccupies.add(new PropertyOccupy());
        when(propertyMapper.getPropertyOccupyByEid(anyInt())).thenThrow(new RuntimeException());
        ResponseMsg msg =propertyService.getByEmployeeId(0);
        assertEquals(404, msg.getStatus());
    }

    @Test
    void happy_path_getByProjectId_ret200() {
        List<PropertyOccupy> propertyOccupies = new ArrayList<>();
        propertyOccupies.add(new PropertyOccupy());
        when(propertyMapper.getPropertyOccupyByProjectId(anyString())).thenReturn(propertyOccupies);
        ResponseMsg msg =propertyService.getByProjectId("");
        assertEquals(200, msg.getStatus());
        when(propertyMapper.getPropertyOccupyByProjectId(anyString())).thenReturn(null);
        msg =propertyService.getByProjectId("0");
        assertEquals(212, msg.getStatus());
    }

    @Test
    void error_path_getByProjectId_ret404() {
        List<PropertyOccupy> propertyOccupies = new ArrayList<>();
        propertyOccupies.add(new PropertyOccupy());
        when(propertyMapper.getPropertyOccupyByProjectId(anyString())).thenThrow(new RuntimeException());
        ResponseMsg msg =propertyService.getByProjectId("");
        assertEquals(404, msg.getStatus());
    }

    @Test
    void happy_path_rentIn_ret200() {
        List<PropertyOccupy> propertyOccupies = new ArrayList<>();
        PropertyOccupy propertyOccupy = new PropertyOccupy();
        propertyOccupies.add(propertyOccupy);
        when(propertyMapper.getPropertyOccupyByPropertyId(0)).thenReturn(propertyOccupies);
        when(propertyMapper.addPropertyOccupy(any())).thenReturn(1);
        ResponseMsg msg = propertyService.rentIn(0, "", 0, new Timestamp(0), true);
        assertEquals(200, msg.getStatus());

        when(propertyMapper.addPropertyOccupy(any())).thenReturn(0);
        msg = propertyService.rentIn(0, "", 0, new Timestamp(0), true);
        assertEquals(214, msg.getStatus());

        propertyOccupy.setExpire_time(new Timestamp(Calendar.getInstance().getTimeInMillis()+100000));
        msg = propertyService.rentIn(0, "", 0, new Timestamp(0), true);
        assertEquals(212, msg.getStatus());
    }

    @Test
    void error_path_rentIn_ret214() {
        List<PropertyOccupy> propertyOccupies = new ArrayList<>();
        PropertyOccupy propertyOccupy = new PropertyOccupy();
        propertyOccupies.add(propertyOccupy);
        when(propertyMapper.getPropertyOccupyByPropertyId(0)).thenThrow(new RuntimeException());
        when(propertyMapper.addPropertyOccupy(any())).thenReturn(1);
        ResponseMsg msg = propertyService.rentIn(0, "", 0, new Timestamp(0), true);
        assertEquals(404, msg.getStatus());
    }

    @Test
    void happy_path_updatePropertyOccupy_ret200() {
        Map<String,Object> param = new HashMap<>();
        param.put("is_intact",true);
        param.put("expire_time","2020-04-08T16:00:00.000Z");
        when(propertyMapper.updatePropertyOccupy(any())).thenReturn(1);
        when(propertyMapper.getByPoid(0)).thenReturn(new PropertyOccupy());
        ResponseMsg msg =propertyService.updatePropertyOccupy(0, param);
        assertEquals(200, msg.getStatus());

        when(propertyMapper.updatePropertyOccupy(any())).thenReturn(0);
        when(propertyMapper.getByPoid(0)).thenReturn(new PropertyOccupy());
        msg =propertyService.updatePropertyOccupy(0, param);
        assertEquals(210, msg.getStatus());
    }

    @Test
    void error_path_updatePropertyOccupy_ret404() {
        Map<String,Object> param = new HashMap<>();
        param.put("is_intact",true);
        param.put("expire_time","2020-04-08T16:00:00.000Z");
        when(propertyMapper.updatePropertyOccupy(any())).thenReturn(1);
        when(propertyMapper.getByPoid(0)).thenThrow(new RuntimeException());
        ResponseMsg msg =propertyService.updatePropertyOccupy(0, param);
        assertEquals(404, msg.getStatus());
    }
}