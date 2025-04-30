package com.rusty.datasourcesingle.dbconfig;

public record DatabaseConnectionResult(
        boolean success,
        String message,
        DbType currentDb
) {}