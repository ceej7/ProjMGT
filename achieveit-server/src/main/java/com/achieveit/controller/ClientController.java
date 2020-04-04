package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "客户接口", value="Client相关API")
public class ClientController {
    Logger logger = LoggerFactory.getLogger(getClass());
    JwtToken jwtToken;
    private final ClientService clientService;

    public ClientController(ClientService clientService, JwtToken jwtToken) {
        this.clientService = clientService;
        this.jwtToken = jwtToken;
    }


    @ResponseBody
    @ApiOperation("获取Client的列表")
    @GetMapping("/client/getAll")
    ResponseMsg getAll(){
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setStatusAndMessage(404,"查询发生异常");
        responseMsg = clientService.getAll();
        return responseMsg;
    }
}
