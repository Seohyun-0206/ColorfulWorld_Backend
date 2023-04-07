package com.example.Colorful_World.service;


import com.example.Colorful_World.Dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Transactional
    public void register(UserDto userDto){



    }

}
