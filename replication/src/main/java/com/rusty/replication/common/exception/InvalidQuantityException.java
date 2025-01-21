package com.rusty.replication.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidQuantityException extends BaseRuntimeException {

    public InvalidQuantityException(String message) {
        super("Not a positive quantity for " + message, HttpStatus.BAD_REQUEST);
    }
}
