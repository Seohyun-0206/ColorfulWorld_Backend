package com.example.Colorful_World.controller;


import com.example.Colorful_World.Dto.UserDto;
import com.example.Colorful_World.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> param){

        UserDto userDto = new UserDto(param.get("email"),
                param.get("password"),
                Integer.parseInt(param.get("index")));


        userService.register(userDto);

        return ResponseEntity.ok("회원가입에 성공하였습니다.");
    }
}
