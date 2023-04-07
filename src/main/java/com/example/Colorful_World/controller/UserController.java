package com.example.Colorful_World.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    @PostMapping("/register")
    public ResponseEntity<String> register(){


        return ResponseEntity.ok("회원가입에 성공하였습니다.");
    }
}
