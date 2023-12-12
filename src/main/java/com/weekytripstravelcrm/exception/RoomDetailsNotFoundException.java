package com.weekytripstravelcrm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoomDetailsNotFoundException extends RuntimeException {
    public RoomDetailsNotFoundException(String message) {
        super(message);
    }
}