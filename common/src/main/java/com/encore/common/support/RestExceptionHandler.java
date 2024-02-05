package com.encore.common.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({SomException.class})
    public ResponseEntity handlerSomException(SomException ex) {
        return new ResponseEntity(DefaultResponse.ErrorResponse.builder().error(ex.getData()).build(), HttpStatus.CONFLICT);
    }
}
