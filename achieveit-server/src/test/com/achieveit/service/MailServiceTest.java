package com.achieveit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MailServiceTest {
    JavaMailSender mailSender;
    MailService mailService;
    String toMail = "49597640@qq.com";
    private MimeMessage mimeMessage;
    @BeforeEach
    void setup(){
        mimeMessage = new MimeMessage((Session)null);
        mailSender = mock(JavaMailSender.class);
        mailService = new MailService(mailSender);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void happy_path_sendSimpleMailMessage() {
        assertDoesNotThrow(()->{
            mailService.sendSimpleMailMessage(toMail,"","");
        });
    }

    @Test
    void happy_path_sendMimeMessage() {
        assertDoesNotThrow(()->{
            mailService.sendMimeMessage(toMail,"1","1");
        });
    }

    @Test
    void happy_path_testSendMimeMessage() {
        assertDoesNotThrow(()->{
            mailService.sendMimeMessage(toMail,"1","1","./a");
        });
    }

    @Test
    void happy_path_testSendMimeMessage1() {
        HashMap<String, String> m = new HashMap<String, String>();
        assertDoesNotThrow(()->{
            mailService.sendMimeMessage(toMail,"1","1",m);
        });
    }

    @Test
    void happy_path_sendmail() {
        assertDoesNotThrow(()->{
            mailService.sendmail(toMail,"");
        });
    }
}