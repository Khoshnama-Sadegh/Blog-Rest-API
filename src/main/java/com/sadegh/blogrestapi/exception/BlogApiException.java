package com.sadegh.blogrestapi.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class BlogApiException extends RuntimeException{
    private HttpStatus status;
    private String message;



    public HttpStatus getStatus() {
        return status;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
