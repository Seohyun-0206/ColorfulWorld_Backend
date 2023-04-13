package com.example.Colorful_World.service;

import com.example.Colorful_World.dto.UserDto;
import com.example.Colorful_World.entity.UserEntity;
import com.example.Colorful_World.exception.BaseException;
import com.example.Colorful_World.exception.ErrorCode;
import com.example.Colorful_World.repository.UserRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;
    private JavaMailSender javaMailSender;
    private SpringTemplateEngine springTemplateEngine;
    private MailProperties mailProperties;

    @BeforeEach
    void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유효한 이메일로 전송시에 인증코드 출력")
    void sendValidEmail() {
        //given
        String email = "zenia02250@naver.com";

        //when

        //then
        try {
            System.out.println("code = " + mailService.sendMail(email));
        }catch(BaseException e){
            Assertions.assertEquals(e.getErrorCode(), ErrorCode.MAIL_NOT_SENT);
        }
    }

    @Test
    @DisplayName("유효하지 않은 이메일로 전송시에 MAIL_NOT_SENT")
    void sendInvalidEmail() {
        //given
        String email = "test1@d.com";

        //when

        //then
        try {
            System.out.println("code = " + mailService.sendMail(email));
        }catch(BaseException e){
            Assertions.assertEquals(e.getErrorCode(), ErrorCode.MAIL_NOT_SENT);
        }
    }
}