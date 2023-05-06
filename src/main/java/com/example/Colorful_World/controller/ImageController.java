package com.example.Colorful_World.controller;

import com.example.Colorful_World.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/image")
    @ResponseBody
    public ResponseEntity<String> imageSave(@RequestPart("image")MultipartFile img){

        imageService.saveImage(img);

        return ResponseEntity.ok("이미지 저장에 성공하였습니다.");
    }
}
