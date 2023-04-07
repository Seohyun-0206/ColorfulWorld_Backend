package com.example.Colorful_World.exception;

import com.example.Colorful_World.Dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//예외 처리 및 응답으로 객체 리턴하기 위해 RestControllerAdvice 사용
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value={BaseException.class})
    public ResponseEntity<Object> handleBaseExcption(BaseException e){
        ResponseDto responseDto  = new ResponseDto(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getCode(), e.getErrorCode().getDetail());
        return new ResponseEntity<>(responseDto, e.getErrorCode().getHttpStatus());
    }

}
