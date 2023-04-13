package com.example.Colorful_World.controller;


import com.example.Colorful_World.dto.UserDto;
import com.example.Colorful_World.service.MailService;
import com.example.Colorful_World.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody Map<String, String> param){

        UserDto userDto = new UserDto(param.get("email"),
                passwordEncoder.encode(param.get("password")),
                Integer.parseInt(param.get("intensity")));


        userService.register(userDto);

        return ResponseEntity.ok("회원가입에 성공하였습니다.");
    }


    @GetMapping("/checkEmail")
    @ResponseBody
    public ResponseEntity<Object> checkEmail(@RequestParam String email){

        //String email = param.get("email");
        System.out.println(email);

        String code = mailService.sendMail(email);
        System.out.println("인증코드: " + code);

        return new ResponseEntity<>(code, HttpStatus.OK);
    }
}
