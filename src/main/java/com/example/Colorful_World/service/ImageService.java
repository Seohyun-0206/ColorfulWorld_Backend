package com.example.Colorful_World.service;

import com.example.Colorful_World.entity.ImageEntity;
import com.example.Colorful_World.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public void saveImage(MultipartFile img){

        byte[] imgBytes;

        try {
            imgBytes = img.getBytes();

            Blob blobImg = new SerialBlob(imgBytes);

            ImageEntity imageEntity = new ImageEntity(blobImg);

            imageRepository.save(imageEntity);

        }catch(Exception e){

        }


    }
}
