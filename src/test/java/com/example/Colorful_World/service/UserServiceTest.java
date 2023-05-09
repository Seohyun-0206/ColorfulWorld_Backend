package com.example.Colorful_World.service;

import com.example.Colorful_World.dto.LoginDto;
import com.example.Colorful_World.dto.UserDto;
import com.example.Colorful_World.entity.UserEntity;
import com.example.Colorful_World.exception.BaseException;
import com.example.Colorful_World.exception.ErrorCode;
import com.example.Colorful_World.repository.UserRepository;
import com.example.Colorful_World.token.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private HttpServletResponse response;

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

    @Test
    @DisplayName("로그인 테스트")
    void login(){
        //given
        String email = "logintest@naver.com";
        String rawpassword = "logintest";
        UserDto userDto = new UserDto(email, passwordEncoder.encode(rawpassword), 1);
        userService.register(userDto);

        LoginDto loginDto = new LoginDto(email, rawpassword);
        //response.setStatus(HttpServletResponse.SC_OK);

        //when
        userService.login(loginDto, response);
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        String access_token = values.get("RTK: "+email);

        //then
        assertThat(access_token).isEqualTo(values.get("RTK: " + userDto.getEmail()));
    }

    @Test
    @DisplayName("로그인시 없는 이메일인 경우")
    void login_error1(){
        //given
        String email = "logintest@naver.com";
        String rawPassword = "logintest";
        UserDto userDto = new UserDto(email, passwordEncoder.encode(rawPassword), 1);
        userService.register(userDto);

        //when
        String email2 = "logintesttt@naver.com";  //없는 이메일
        String rawPassword2 = "logintest";
        LoginDto loginDto = new LoginDto(email2, rawPassword2);

        //then
        try{
            userService.login(loginDto, response);
        }catch (BaseException e){
            Assertions.assertEquals(e.getErrorCode(), ErrorCode.NO_USER);
        }
    }

    @Test
    @DisplayName("올바르지 않은 암호인 경우")
    void login_error2(){
        //given
        String email = "logintest@naver.com";
        String rawPassword = "logintest";
        UserDto userDto = new UserDto(email, passwordEncoder.encode(rawPassword), 1);
        userService.register(userDto);

        //when
        String email2 = "logintest@naver.com";  //없는 이메일
        String rawPassword2 = "logintesttt";
        LoginDto loginDto = new LoginDto(email2, rawPassword2);

        //then
        try{
            userService.login(loginDto, response);
        }catch (BaseException e){
            Assertions.assertEquals(e.getErrorCode(), ErrorCode.PASSWORD_MISMATCH);
        }
    }
}