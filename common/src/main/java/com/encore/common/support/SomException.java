package com.encore.common.support;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class SomException extends RuntimeException{

    Object data;

    public SomException() {
        super();
    }

    public SomException(String message, Throwable cause) {
        super(message, cause);
    }

    public SomException(String message, ResponseCode responseCode) {
        super(message);
        this.data = responseCode;
    }

    public SomException(ResponseCode responseCode) {
        super(responseCode.getLabel());
        this.data = responseCode;
    }

    public SomException(ResponseCode responseCode, Object data) {
        super(responseCode.getLabel());
        this.data = data;
    }
}
