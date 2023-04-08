package com.example.Colorful_World.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException{

    private ErrorCode errorCode;
}
