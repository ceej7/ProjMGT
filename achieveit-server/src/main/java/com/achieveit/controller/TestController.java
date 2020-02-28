package com.achieveit.controller;

import com.achieveit.config.MyServerConfig;
import com.achieveit.exception.FileException;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/test")
@Api(tags = "测试接口", value = "测试相关Rest API")
public class TestController {
    private final MailService mailService;
    private final FileService fileService;

    Logger logger = LoggerFactory.getLogger(getClass());

    public TestController(FileService fileService, MailService mailService) {
        this.fileService = fileService;
        this.mailService = mailService;
    }

    @ResponseBody
    @GetMapping("/hello")
    @ApiOperation("向服务器请求一个hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/sendMail")
    @ApiOperation("向服务器请求发送一个邮件")
    public void send(){
        mailService.sendmail("1749597640@qq.com", "Father");
    }

    @ResponseBody
    @PostMapping("/uploadImage")
    @ApiOperation("向服务器上传一张图片")
    public String updateProfilePic(@RequestParam("image") MultipartFile file) {
        String fileStoragePath = null;
        String fileUrl = null;
        try {
            fileStoragePath = fileService.storeFile(file);
            String[] fileStoragePathSplit = fileStoragePath.split("/");
            fileUrl = MyServerConfig.server + ":" + MyServerConfig.port + "/images/";
            fileUrl = fileUrl + fileStoragePathSplit[fileStoragePathSplit.length - 1];
        } catch (FileException e) {
            logger.error(e.getMessage(), e);
        }
        return fileUrl;
    }


}
