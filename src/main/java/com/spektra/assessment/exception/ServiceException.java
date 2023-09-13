package com.spektra.assessment.exception;

import com.spektra.assessment.model.enums.ServiceError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ServiceException extends RuntimeException {

    private int code;
    private String message;

    public ServiceException(int code, String message){
        this.code = code;
        this.message = message;
    }

    public ServiceException(ServiceError error){
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

