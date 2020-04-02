package com.achieveit.config;

import com.achieveit.service.WorkflowEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class MyServerConfig implements ApplicationListener<WebServerInitializedEvent>{
    public static String server;
    public static String port;
    public static String remoteServer = "119.29.87.183";

    Logger logger = LoggerFactory.getLogger(getClass());

    public MyServerConfig() {
        try {
            server = InetAddress.getLocalHost().getHostAddress();
            if (server.startsWith("192.168"))
                server = "localhost";
            else if (server.startsWith("127.0"))
                server = remoteServer;
            else{
                logger.error(String.format("--------------------------------------unrecognized ip address: %s", server));
            }
            logger.info(String.format("--------------------------------------server ip address: %s", server));
        } catch (UnknownHostException e) {
            logger.error(e.getMessage(),e);
            server = "localhost";
        }
        // 配置重要的工作流部分
        int workflow_bit_num=32;
        WorkflowEngineService.setDependency(1,0,workflow_bit_num);
        WorkflowEngineService.setDependency(2,1,workflow_bit_num);
        WorkflowEngineService.setDependency(3,1,workflow_bit_num);
        WorkflowEngineService.setDependency(4,1,workflow_bit_num);
        WorkflowEngineService.setDependency(5,2,workflow_bit_num);
        WorkflowEngineService.setDependency(5,3,workflow_bit_num);
        WorkflowEngineService.setDependency(5,4,workflow_bit_num);
        WorkflowEngineService.setDependency(6,5,workflow_bit_num);
        WorkflowEngineService.setDependency(7,6,workflow_bit_num);
        WorkflowEngineService.setDependency(8,5,workflow_bit_num);
        WorkflowEngineService.setDependency(8,6,workflow_bit_num);
        WorkflowEngineService.setDependency(8,7,workflow_bit_num);
        WorkflowEngineService.setDependency(9,8,workflow_bit_num);
        WorkflowEngineService.setDependency(10,9,workflow_bit_num);
        for (int i = 11; i <=27; i++) {
            WorkflowEngineService.setDependency(i,10,workflow_bit_num);
            WorkflowEngineService.setDependency(28,i,workflow_bit_num);
        }
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        port = String.valueOf(webServerInitializedEvent.getWebServer().getPort());
        logger.info(String.format("--------------------------------------server port: %s", port));
    }
}
