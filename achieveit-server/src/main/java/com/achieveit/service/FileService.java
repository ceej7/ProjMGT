package com.achieveit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.achieveit.config.MyWebMvcConfig;
import com.achieveit.exception.FileException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class FileService {
    private final String uploadPath;

    Logger logger = LoggerFactory.getLogger(getClass());

    public FileService() {
        uploadPath = MyWebMvcConfig.imageToStorage;
    }

    public String storeFile(MultipartFile file) throws FileException {
        String fileName = file.getOriginalFilename();

        try {
            if (fileName == null) {
                throw new FileException("file not uploaded successfully");
            }
            if (fileName.contains("..")) {
                throw new FileException("file has invalid filename");
            }
            File dir = new File(uploadPath);
            if (!dir.exists()){
                if (dir.mkdir()) {
                    logger.info(String.format("创建文件上传目录成功-%s", uploadPath));
                }else {
                    logger.warn(String.format("创建文件上传目录失败-%s", uploadPath));
                }
            }
            SimpleDateFormat simpleDateFormat;
            simpleDateFormat = new SimpleDateFormat("ddHHssSSS");

            // Requiring distinctive name -> random generator
            Date date = new Date();
            String str = simpleDateFormat.format(date);
            Random random = new Random();
            int imgRan = random.nextInt() * (99999 - 10000 + 1) + 10000;// 获取5位随机数

            String intervalName = imgRan + "" + str;
            String displayName = intervalName+fileName;
            fileName = uploadPath + intervalName +fileName;
            logger.info(String.format("上传文件-%s; 目录-%s",displayName , uploadPath));

            File dest = new File(fileName);
            file.transferTo(dest);
            return fileName;
        } catch (Exception e) {
            throw new FileException(String.format("[%s] file upload failed", fileName));
        }
    }
}