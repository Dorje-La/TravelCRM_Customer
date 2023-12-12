package com.weekytripstravelcrm.exception;

public class AdminRecordNotFoundException extends RuntimeException {
    public AdminRecordNotFoundException(String message, String exMessage) {
        super(message);
    }
}
