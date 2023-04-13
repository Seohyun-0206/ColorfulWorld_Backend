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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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


        setHeader(response, tokenDto);
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto){
        response.setHeader("access_token", tokenDto.getAccessToken());
        response.setHeader("refresh_token", tokenDto.getRefreshToken());
    }
}
