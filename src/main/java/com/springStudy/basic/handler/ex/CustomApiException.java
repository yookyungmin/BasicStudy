package com.springStudy.basic.handler.ex;

public class CustomApiException extends RuntimeException{

    public CustomApiException(String message) {
        super(message);
    }
}
