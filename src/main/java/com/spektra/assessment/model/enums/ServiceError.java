package com.spektra.assessment.model.enums;


public enum ServiceError {

    INVALID_TRANSACTION(2000 , "Invalid transaction"),
    FAILED_TO_CREATE_TRANSACTION(2001 , "Failed to create transaction");
    //TODO - Define error codes and messages
    // 1000 - 1999 - User related errors
    // 2000 - 2999 - Transaction related errors
    // 3000 - 3999 - Account related errors;



    private final int code;
    private final String message;
    ServiceError(int code , String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return this.code;
    }

    public String getMessage() {
        return message;
    }
}
