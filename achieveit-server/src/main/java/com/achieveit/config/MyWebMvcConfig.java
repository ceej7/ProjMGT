package com.achieveit.config;

import com.achieveit.service.FileService;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    public static String imageToStorage = new ApplicationHome(FileService.class).getSource().getParentFile().getPath() + "/images/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
        // dynamic directory
        .addResourceLocations("file:"+imageToStorage);
    }
}