package com.rusty.replication.common.exception;

import org.springframework.http.HttpStatus;

public class NotEnoughQuantityToExportException extends BaseRuntimeException {

    public NotEnoughQuantityToExportException(String message) {
        super("Not enough quantity for " + message, HttpStatus.BAD_REQUEST);
    }
}