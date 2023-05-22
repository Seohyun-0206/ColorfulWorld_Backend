package com.example.Colorful_World.service;



import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ImageScheduler {

    @Scheduled(fixedRate = 60 * 60 * 1000) //테스크 시작 1시간 마다 실행
    public void cleanUpImage(){

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

        String filePath = System.getProperty("user.dir") + "/src/main/resources/files";
        File file = new File(filePath);

        if(file.exists()){
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for(File f : files){
                    try {
                        int length = f.getName().length();
                        String expired = f.getName().substring(length - 23, length - 4);

                        //파일명의 유효시간을 Date type으로 바꾸기
                        Date expiredDate = formatter.parse(expired);

                        if(expiredDate.before(now)){
                            System.out.println(f.getName());
                            f.delete();
                        }
                    }catch(Exception e){
                        System.out.println("Exception : " + e);
                    }

                }
            }
        }
    }
}
