package com.rusty.datasourcesingle.dbconfig;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DatabaseSwitchResponse {
    private String status;
    private String message;
    private String currentDatabase;
    private String timestamp;
}
