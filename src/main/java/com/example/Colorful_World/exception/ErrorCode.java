package com.example.Colorful_World.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*400:BAD REQUEST*/
    NO_USER(HttpStatus.BAD_REQUEST, "LOGIN-001", "해당 유저가 없습니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "LOGIN-002", "비밀번호가 일치하지 않습니다."),
    EXPIRATION_TOKEN(HttpStatus.BAD_REQUEST, "LOGOUT-001", "토큰이 유효하지 않습니다."),
    IMAGE_LOAD_FAILED(HttpStatus.BAD_REQUEST, "IMG-002", "이미지를 불러오는데 실패하였습니다."),

    /*409:CONFLICT*/
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "REGISTER-001", "중복된 이메일이 존재합니다."),

    /*401:UNAUTHORIZED*/
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH-001", "토큰이 유효하지 않습니다."),

    /*500:INTERNAL SERVER ERROR*/
    MAIL_NOT_SENT(HttpStatus.INTERNAL_SERVER_ERROR, "MAIL-001", "메일이 정상적으로 발송되지 않았습니다."),
    IMAGE_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "IMG-001", "이미지 저장에 실패했습니다.")
    ;



    private HttpStatus httpStatus;
    private String code;
    private String detail;
}
