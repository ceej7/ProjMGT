package com.achieveit.config;

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

//    @Autowired
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
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        port = String.valueOf(webServerInitializedEvent.getWebServer().getPort());
        logger.info(String.format("--------------------------------------server port: %s", port));
    }
}
