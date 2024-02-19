package com.encore.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
//create나 update 될때 메세지 or status
public class CommonResponse {
    private HttpStatus status;
    private String message;
    private Object result;
}