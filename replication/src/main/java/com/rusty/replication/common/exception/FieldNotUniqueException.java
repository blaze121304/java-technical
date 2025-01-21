package com.rusty.replication.common.exception;

import org.springframework.http.HttpStatus;

public class FieldNotUniqueException extends BaseRuntimeException {

    public FieldNotUniqueException(String message) {
        super("Field " + message + " is not unique", HttpStatus.BAD_REQUEST);
    }
}
