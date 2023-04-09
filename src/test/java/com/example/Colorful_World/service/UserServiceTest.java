package com.example.Colorful_World.service;

import com.example.Colorful_World.dto.UserDto;
import com.example.Colorful_World.entity.UserEntity;
import com.example.Colorful_World.exception.BaseException;
import com.example.Colorful_World.exception.ErrorCode;
import com.example.Colorful_World.repository.UserRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    void 회원가입() {
        //given
        UserDto userDto = new UserDto("test2@naver.com", "test2", 2);

        //when
        userService.register(userDto);
        List<UserEntity> userList = userRepository.findAll();
        UserEntity user = userList.get(0);

        //then
        assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(passwordEncoder.matches(user.getPassword(), userDto.getPassword()));
        assertThat(user.getIntensity()).isEqualTo(userDto.getIntensity());
    }

    @Test
    void 중복이메일테스트() {
        //given
        UserDto userDto1 = new UserDto("test1@naver.com", "test1", 1);
        UserDto userDto2 = new UserDto("test1@naver.com", "test2", 2);

        //when
        userService.register(userDto1);

        //then
        try {
            userService.register(userDto2);
        }catch (BaseException e){
            //에러코드 같은지 확인
            Assertions.assertEquals(e.getErrorCode(), ErrorCode.DUPLICATE_EMAIL);
        }
    }
}