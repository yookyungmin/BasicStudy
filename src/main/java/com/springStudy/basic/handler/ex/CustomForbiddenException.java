package com.springStudy.basic.handler.ex;

//추후에 사용 예정
public class CustomForbiddenException extends RuntimeException{

    public CustomForbiddenException(String message) {
        super(message);
    }
}
