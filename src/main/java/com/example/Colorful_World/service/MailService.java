package com.example.Colorful_World.service;

import com.example.Colorful_World.exception.BaseException;
import com.example.Colorful_World.exception.ErrorCode;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public String sendMail(String email){

        String code = createCode();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Colorful World 회원가입 이메일 인증");

            javaMailSender.send(mimeMessage);

            return code;

        }catch(Exception e){
            throw new BaseException(ErrorCode.MAIL_NOT_SENT);
        }
    }


    public String createCode(){
        Random random = new Random();
        StringBuffer code = new StringBuffer();

        for(int i = 0; i < 6; i++){ //6자리로 생성(대문자와 숫자 조합)
            int index = random.nextInt(2);

            switch (index) {
                case 0:
                    code.append((char) ((int) (random.nextInt(26)) + 65)); //A~Z
                    break;
                case 1:
                    code.append((random.nextInt(10))); //0~9
                    break;
            }
        }
        return code.toString();
    }
}
