package com.weekytripstravelcrm.exception;

public class AgencyRecordNotFoundException extends RuntimeException{
    public AgencyRecordNotFoundException(String message, String exMessage) {
        super(message);
    }
}
