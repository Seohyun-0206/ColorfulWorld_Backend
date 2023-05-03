package com.example.Colorful_World.service;


import com.example.Colorful_World.dto.LoginDto;
import com.example.Colorful_World.dto.TokenDto;
import com.example.Colorful_World.dto.UserDto;
import com.example.Colorful_World.entity.UserEntity;
import com.example.Colorful_World.exception.BaseException;
import com.example.Colorful_World.exception.ErrorCode;
import com.example.Colorful_World.repository.UserRepository;
import com.example.Colorful_World.token.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    @Transactional
    public void register(UserDto userDto){

        //아이디 중복 확인
        if(userRepository.existsByEmail(userDto.getEmail())){
            //중복 시 error message 넘겨줘야함
            throw new BaseException(ErrorCode.DUPLICATE_EMAIL);
        }

        //dto를 entity로 만들어서 저장
        userRepository.save(userDto.toEntity());

    }

    @Transactional
    public void login(LoginDto loginDto, HttpServletResponse response){

        //회원이 존재하는지 확인
        UserEntity userEntity = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                ()-> new BaseException(ErrorCode.NO_USER)
        );

        if(!passwordEncoder.matches(loginDto.getPassword(), userEntity.getPassword())){
            throw new BaseException(ErrorCode.PASSWORD_MISMATCH);
        }

        TokenDto tokenDto = jwtTokenProvider.createAllToken(loginDto.getEmail());

        //redis에 RTK 저장
        redisTemplate.opsForValue()
                        .set("RTK: " + loginDto.getEmail(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpiration(), TimeUnit.MILLISECONDS);


        setHeader(response, tokenDto);
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto){
        response.addHeader("access_token", tokenDto.getAccessToken());
        response.addHeader("refresh_token", tokenDto.getRefreshToken());
    }

    @Transactional
    public void logout(String atk){

        //ATK 유효 검증
        if(!jwtTokenProvider.validateToken(atk)){
            throw new BaseException(ErrorCode.EXPIRATION_TOKEN);
        }

        //ATK에서 email 가져오기
        String email = jwtTokenProvider.getEmail(atk);

        //Redis에 RTK 있는지 확인
        if(redisTemplate.opsForValue().get("RTK: " + email) != null){
            //RTK 삭제
            redisTemplate.delete("RTK: " + email);
        }

        //ATK를 blacklist로 저장
        Long expiration = jwtTokenProvider.getExpiration(atk);
        redisTemplate.opsForValue().set(atk,"logout: " + email, expiration, TimeUnit.MILLISECONDS);
    }
}
