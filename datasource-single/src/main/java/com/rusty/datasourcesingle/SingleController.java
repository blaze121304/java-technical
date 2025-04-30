package com.rusty.datasourcesingle;

import com.rusty.datasourcesingle.dbconfig.DatabaseConnectionManager;
import com.rusty.datasourcesingle.dbconfig.DatabaseSwitchResponse;
import com.rusty.datasourcesingle.dbconfig.DbContextHolderThread;
import com.rusty.datasourcesingle.dbconfig.DbType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/database")
@RequiredArgsConstructor
public class SingleController {

    private final DatabaseConnectionManager connectionManager;
    private final JdbcTemplate jdbcTemplate;

    @PutMapping("/switch/{type}")
    public ResponseEntity<DatabaseSwitchResponse> switchDatabase(@PathVariable String type) {
        try {
            DbType selectedDb = DbType.valueOf(type.toUpperCase());
            connectionManager.switchDatabase(selectedDb);

            return ResponseEntity.ok(DatabaseSwitchResponse.builder()
                    .status("SUCCESS")
                    .message("데이터베이스가 성공적으로 전환되었습니다")
                    .currentDatabase(type.toUpperCase())
                    .timestamp(LocalDateTime.now().toString())
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(DatabaseSwitchResponse.builder()
                    .status("ERROR")
                    .message("잘못된 데이터베이스 유형: " + e.getMessage())
                    .currentDatabase(connectionManager.getCurrentStatus().message())
                    .timestamp(LocalDateTime.now().toString())
                    .build());
        }
    }

    @GetMapping("/current")
    public ResponseEntity<DatabaseSwitchResponse> getCurrentDatabase() {
        return ResponseEntity.ok(DatabaseSwitchResponse.builder()
                .status("SUCCESS")
                .message("현재 데이터베이스 정보 조회 성공")
                .currentDatabase(connectionManager.getCurrentStatus().message() +" : "+ connectionManager.getCurrentStatus().currentDb())
                .timestamp(LocalDateTime.now().toString())
                .build());
    }

    @GetMapping("/data")
    public ResponseEntity<?> getData() {
        try {
            // 현재 설정된 데이터베이스에서 데이터 조회
            var result = jdbcTemplate.queryForList("select DATABASE()");
            return ResponseEntity.ok(Map.of(
                    "currentDb", DbContextHolderThread.getCurrentDb(),
                    "data", result
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "데이터 조회 중 오류 발생: " + e.getMessage()
            ));
        }
    }
}