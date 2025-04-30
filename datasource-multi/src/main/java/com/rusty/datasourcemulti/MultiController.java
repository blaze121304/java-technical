package com.rusty.datasourcemulti;

import com.rusty.datasourcemulti.dbconfig.DbContextHolder;
import com.rusty.datasourcemulti.dbconfig.DbType;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MultiController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/switch/{dbType}")
    public ResponseEntity<?> switchDatabase(@PathVariable String dbType, HttpSession session) {
        try {
            DbType selectedDb = DbType.valueOf(dbType.toUpperCase());
            DbContextHolder.setCurrentDb(selectedDb);

            return ResponseEntity.ok(Map.of(
                    "message", "데이터베이스가 " + selectedDb + "로 전환되었습니다.",
                    "currentDb", selectedDb
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "유효하지 않은 데이터베이스 타입입니다. (사용 가능: PRIMARY, SECONDARY)"
            ));
        }
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentDatabase() {
        DbType currentDb = DbContextHolder.getCurrentDb();
        return ResponseEntity.ok(Map.of(


                "message", "현재 데이터베이스는 " + currentDb + "입니다." + DbContextHolder.hasCurrentDb()

//                "currentDb", currentDb,
//                "isSet", DbContextHolder.hasCurrentDb()
        ));
    }

    @GetMapping("/data")
    public ResponseEntity<?> getData() {
        try {
            // 현재 설정된 데이터베이스에서 데이터 조회
            var result = jdbcTemplate.queryForList("select DATABASE()");
            return ResponseEntity.ok(Map.of(
                    "currentDb", DbContextHolder.getCurrentDb(),
                    "data", result
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "데이터 조회 중 오류 발생: " + e.getMessage()
            ));
        }
    }

}

