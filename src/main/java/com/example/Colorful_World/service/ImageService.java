package com.example.Colorful_World.service;

import com.example.Colorful_World.entity.ImageEntity;
import com.example.Colorful_World.exception.BaseException;
import com.example.Colorful_World.exception.ErrorCode;
import com.example.Colorful_World.repository.ImageRepository;
import com.example.Colorful_World.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final JwtTokenProvider jwtTokenProvider;

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
            //임시 파일 경로에 file 생성
            OutputStream outputStream = new FileOutputStream(temporaryUrl);

            //write() : byte 배열을 파일에 쓰기
            //image.getBytes(1, (int) blob.length()) : blob 데이터를 byte로 가져오기(첫번째 바이트부터 전체길이까지의 바이트 배열)
            outputStream.write(imageEntity.getImage().getBytes(1, (int) imageEntity.getImage().length()));
        }catch(Exception e){

        }

        System.out.println(temporaryUrl);
        return temporaryUrl;
    }

    public String temporarySave(MultipartFile img, String atk){

        String email = jwtTokenProvider.getEmail(atk);

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String expired = formatter.format(now);

        String filePath = System.getProperty("user.dir") + "/src/main/resources/files";

        String fileName = email + "_" + expired + ".png";

        try {
            File saveFile = new File(filePath, fileName);

            img.transferTo(saveFile);
        }catch(Exception e){
            throw new BaseException(ErrorCode.IMAGE_SAVE_FAILED);
        }

        return fileName;
    }

    public Resource getImage(String fileName){

        String filePath = System.getProperty("user.dir") + "/src/main/resources/files";

        try {
            Path imagePath = Paths.get(filePath, fileName);
            Resource imageResource = new UrlResource(imagePath.toUri());

            return imageResource;

        }catch(Exception e){
            throw new BaseException(ErrorCode.IMAGE_LOAD_FAILED);
        }
    }
}
