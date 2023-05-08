package com.example.Colorful_World.service;

import com.example.Colorful_World.entity.ImageEntity;
import com.example.Colorful_World.exception.BaseException;
import com.example.Colorful_World.exception.ErrorCode;
import com.example.Colorful_World.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

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
            throw new BaseException(ErrorCode.IMAGE_SAVE_FAILED);
        }

    }

    public String loadImage(int id){

        ImageEntity imageEntity = imageRepository.findById(id);

        String temporaryUrl = "src/main/resources/file.png";
        try{
            OutputStream outputStream = new FileOutputStream(temporaryUrl);
            outputStream.write(imageEntity.getImage().getBytes(1, (int) imageEntity.getImage().length()));
        }catch(Exception e){

        }

        System.out.println(temporaryUrl);
        return temporaryUrl;
    }
}
