package com.achieveit.entity;

import java.util.HashMap;

/**
 * status: 提示代码
 * response: 数据实体
 */
public class ResponseMsg {
    private int status;
    private HashMap<String, Object> responseMap = new HashMap<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public HashMap<String, Object> getResponseMap() {
        return responseMap;
    }
}