package com.sadegh.blogrestapi.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public BlogApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }


    public HttpStatus getStatus() {
        return status;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
