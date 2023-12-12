package com.weekytripstravelcrm.exception;

public class CustomerRecordNotFoundException extends RuntimeException {
    public CustomerRecordNotFoundException(String message, String exMessage) {
        super(message);
    }

    public CustomerRecordNotFoundException() {

    }
}
