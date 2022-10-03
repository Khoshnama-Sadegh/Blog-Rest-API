package com.sadegh.blogrestapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorDetails {

    private Date timeStamp;
    private String message;
    private String details;
    private HttpStatus status;


}
