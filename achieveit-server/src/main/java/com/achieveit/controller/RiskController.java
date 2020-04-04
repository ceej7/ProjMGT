package com.achieveit.controller;

import com.achieveit.config.JwtToken;
import com.achieveit.service.ClientService;
import com.achieveit.service.RiskService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "风险接口", value="Risk相关API")
public class RiskController {
    Logger logger = LoggerFactory.getLogger(getClass());
    JwtToken jwtToken;
    private final RiskService riskService;


    public RiskController(RiskService riskService, JwtToken jwtToken) {
        this.riskService = riskService;
        this.jwtToken = jwtToken;
    }
}
