package com.achieveit.controller;

import com.achieveit.config.MyServerConfig;
import com.achieveit.entity.ResponseMsg;
import com.achieveit.entity.Test;
import com.achieveit.exception.FileException;
import com.achieveit.service.FileService;
import com.achieveit.service.MailService;
import com.achieveit.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@Api(tags = "测试接口", value = "测试相关Rest API")
public class TestController {
    private final MailService mailService;
    private final FileService fileService;
    private final TestService testService;

    Logger logger = LoggerFactory.getLogger(getClass());

    public TestController(FileService fileService, MailService mailService, TestService testService) {
        this.fileService = fileService;
        this.mailService = mailService;
        this.testService = testService;
    }

    @ResponseBody
    @GetMapping("/test/hello")
    @ApiOperation("向服务器请求一个hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/test/sendMail")
    @ApiOperation("向address发送一个邮件")
    public void send(@RequestParam("address") String address){
        mailService.sendmail(address, "Father");
    }

    @ResponseBody
    @PostMapping("/test/uploadImage")
    @ApiOperation("向服务器上传一张图片")
    public ResponseMsg updateProfilePic(@RequestParam("image") MultipartFile file) {
        ResponseMsg msg = new ResponseMsg();
        msg.setStatusAndMessage(404, "");
        String fileStoragePath = null;
        String fileUrl = null;
        try {
            fileStoragePath = fileService.storeFile(file);
            String[] fileStoragePathSplit = fileStoragePath.split("/");
            fileUrl = MyServerConfig.server + ":" + MyServerConfig.port + "/images/";
            fileUrl = fileUrl + fileStoragePathSplit[fileStoragePathSplit.length - 1];
            msg.setStatusAndMessage(200, "上传图片成功");
            msg.getResponseMap().put("result", fileUrl);
        } catch (FileException e) {
            msg.setStatusAndMessage(404, "上传图片不成功");
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    @ResponseBody
    @GetMapping("/test/{tid}")
    @ApiOperation("通过tid来获取Test")
    public Test getTestById(@PathVariable("tid") int tid){
        Test test=null;
        try{
            test = testService.getTestById(tid);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return test;
    }

    @ResponseBody
    @GetMapping("/test/getAll")
    @ApiOperation("获取所有Test")
    public List<Test> getAllTests(){
        List<Test> tests=null;
        try{
            tests = testService.getAllTests();
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return tests;
    }

    @ResponseBody
    @PostMapping("/test")
    @ApiOperation("新增一个Test")
    public int addTest(@RequestBody Test test){
        return testService.addTest(test);
    }

    @ResponseBody
    @DeleteMapping("/test/{tid}")
    @ApiOperation("通过tid来删除Test")
    public int deleteTestById(@PathVariable("tid") int tid){
        return testService.deleteTest(tid);
    }

    @ResponseBody
    @PutMapping("/test")
    @ApiOperation("修改一个test")
    public int updateTest(@RequestBody Test test){
        return testService.updateTest(test);
    }
}
