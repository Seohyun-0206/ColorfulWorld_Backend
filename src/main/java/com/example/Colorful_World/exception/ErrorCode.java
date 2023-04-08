package com.example.Colorful_World.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*409:CONFLICT*/
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "REGISTER-001", "중복된 이메일이 존재합니다.")

    ;



    private HttpStatus httpStatus;
    private String code;
    private String detail;
}
