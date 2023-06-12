package com.example.Colorful_World.controller;

import com.example.Colorful_World.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/api/image")
    @ResponseBody
    public ResponseEntity<String> imageSave(@RequestPart("image")MultipartFile img){

        imageService.saveImage(img);

        return ResponseEntity.ok("이미지 저장에 성공하였습니다.");
    }

    //저장된 이미지 확인
    @GetMapping("/api/load")
    public String loadImage(@RequestParam("id") int id, Model model){

        model.addAttribute("image", imageService.loadImage(id));

        return "image";
    }

    @PostMapping("/api/saveImage")
    @ResponseBody
    public ResponseEntity<Object> saveImage(@RequestPart("image") MultipartFile img){

        String fileName = imageService.temporarySave(img);

        return new ResponseEntity<>(fileName, HttpStatus.OK);
    }

    @GetMapping("/api/getImage")
    @ResponseBody
    public ResponseEntity<Object> getImage(@RequestParam("name") String fileName){

        Resource img = imageService.getImage(fileName);

        return new ResponseEntity<>(img, HttpStatus.OK);
    }
}
