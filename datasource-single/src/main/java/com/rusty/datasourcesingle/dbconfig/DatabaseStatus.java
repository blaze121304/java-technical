package com.rusty.datasourcesingle.dbconfig;

public record DatabaseStatus(
        DbType currentDb,
        boolean connected,
        String message,
        String type
) {}