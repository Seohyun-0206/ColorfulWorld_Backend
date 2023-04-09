package com.example.Colorful_World.service;


import com.example.Colorful_World.dto.UserDto;
import com.example.Colorful_World.exception.BaseException;
import com.example.Colorful_World.exception.ErrorCode;
import com.example.Colorful_World.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

}
