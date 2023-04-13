package com.example.Colorful_World.service;

import com.example.Colorful_World.dto.UserDto;
import com.example.Colorful_World.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring6.SpringTemplateEngine;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    private JavaMailSender javaMailSender;
    private SpringTemplateEngine springTemplateEngine;
    private MailProperties mailProperties;

    @BeforeEach
    void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유효하지 않은 이메일로 전송시에 Mail_Not_Sent")
    void sendEmailDuplicate() {
        //given
        UserDto userDto1 = new UserDto("test1@naver.com", "test1", 1);
        UserDto userDto2 = new UserDto("test2@naver.com", "test2", 1);
        
        //when

        //then
    }
}