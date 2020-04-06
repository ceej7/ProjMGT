package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@ApiModel("Project Entity: 项目信息")
public class Project {
    public Project(String pid, String name, Timestamp starttime, Timestamp endtime, String technique, String domain, String function, Integer client_id, Integer workflow_id) {
        this.pid = pid;
        this.name = name;
        this.starttime = starttime;
        this.endtime = endtime;
        this.technique = technique;
        this.domain = domain;
        setFunction(function);
        this.client_id = client_id;
        this.workflow_id = workflow_id;
    }

    public Project() {
        this.pid = "";
        this.name = "";
        this.starttime = new Timestamp(0);
        this.endtime = new Timestamp(0);
        this.technique = "";
        this.domain = "";
        setFunction("{\"000000\":\"\"}");
        this.client_id = 0;
        this.workflow_id = 0;
        client = new Client();
        workflow= new Workflow();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setFunction(Map param){
        if(param==null){
            this.function=null;
            return;
        }
        JSONObject jsonObject=new JSONObject();
        Set<String> keys = param.keySet();
        Iterator<String> iter=keys.iterator();
        while (iter.hasNext()){
            String str = iter.next() ;
            jsonObject.put(str, param.get(str).toString());
        }
        this.function=jsonObject.toString();
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public Integer getWorkflow_id() {
        return workflow_id;
    }

    public void setWorkflow_id(Integer workflow_id) {
        this.workflow_id = workflow_id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    String pid;
    String name;
    Timestamp starttime;
    Timestamp endtime;
    String technique;
    String domain;
    @ApiModelProperty("使用json存储，使用两个Integer解析")
    String function;
    @ApiModelProperty("外键：客户的id")
    Integer client_id;
    @ApiModelProperty("外键：工作流的id；一一对应关系")
    Integer workflow_id;

    Client client;
    Workflow workflow;
}
