package com.rusty.datasourcesingle.dbconfig;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class DatabaseConnectionManager {

    @Getter
    private volatile HikariDataSource currentDataSource;

    private final Object lock = new Object();
    private DbType currentDbType;

    @Value("${spring.datasource.primary.jdbc-url}")
    private String primaryJdbcUrl;

    @Value("${spring.datasource.primary.username}")
    private String primaryUsername;

    @Value("${spring.datasource.primary.password}")
    private String primaryPassword;

    @Value("${spring.datasource.primary.driver-class-name}")
    private String primaryDriverClassName;

    @Value("${spring.datasource.secondary.jdbc-url}")
    private String secondaryJdbcUrl;

    @Value("${spring.datasource.secondary.username}")
    private String secondaryUsername;

    @Value("${spring.datasource.secondary.password}")
    private String secondaryPassword;

    @Value("${spring.datasource.primary.driver-class-name}")
    private String secondaryDriverClassName;

    @Value("default.db")
    private String defaultDb;

    @PostConstruct
    public void init() {
        //switchDatabase(DbType.valueOf(defaultDb));
        switchDatabase(DbType.PRIMARY);
    }

    public boolean testConnection(HikariDataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            return conn.isValid(3);
        } catch (SQLException e) {
//            log.error("데이터베이스 연결 테스트 실패: {}", e.getMessage());
            return false;
        }
    }

    public DatabaseConnectionResult switchDatabase(DbType targetDb) {
        synchronized (lock) {
            HikariDataSource newDataSource = null;

            try {
                DbContextHolderThread.clear();
                newDataSource = createDataSource(targetDb);

                if (!testConnection(newDataSource)) {
                    if (newDataSource != null) {
                        newDataSource.close();
                    }
                    return new DatabaseConnectionResult(false,
                            targetDb + " 데이터베이스에 연결할 수 없습니다.",
                            currentDbType);
                }

                if (currentDataSource != null) {
                    currentDataSource.close();
                }

                currentDataSource = newDataSource;
                currentDbType = targetDb;
                DbContextHolderThread.setCurrentDb(targetDb); //여기서?

                return new DatabaseConnectionResult(true,
                        targetDb + " 데이터베이스로 성공적으로 전환되었습니다.",
                        targetDb);

            } catch (Exception e) {
//                log.error("데이터베이스 전환 중 오류 발생: {}", e.getMessage());
                if (newDataSource != null) {
                    newDataSource.close();
                }

                if (currentDataSource != null && !testConnection(currentDataSource)) {
                    currentDataSource.close();
                    currentDataSource = null;
                    currentDbType = null;
                }

                return new DatabaseConnectionResult(false,
                        "데이터베이스 전환 실패: " + e.getMessage(),
                        currentDbType);
            }
        }
    }

    public HikariDataSource createDataSource(DbType dbType) {
        HikariDataSource newDataSource = new HikariDataSource();

        switch (dbType) {
            case PRIMARY -> {
                newDataSource.setDriverClassName(primaryDriverClassName);
                newDataSource.setJdbcUrl(primaryJdbcUrl);
                newDataSource.setUsername(primaryUsername);
                newDataSource.setPassword(primaryPassword);
            }
            case SECONDARY -> {
                newDataSource.setDriverClassName(secondaryDriverClassName);
                newDataSource.setJdbcUrl(secondaryJdbcUrl);
                newDataSource.setUsername(secondaryUsername);
                newDataSource.setPassword(secondaryPassword);
            }
        }

        newDataSource.setMaximumPoolSize(10);
        newDataSource.setMinimumIdle(5);
        newDataSource.setIdleTimeout(300000);
        newDataSource.setConnectionTimeout(5000);
        newDataSource.setInitializationFailTimeout(1000);

        return newDataSource;
    }

    public DatabaseStatus getCurrentStatus() {
        if (currentDataSource == null) {
            return new DatabaseStatus(null, false, "연결된 데이터베이스 없음", "null");
        }

        boolean isConnected = testConnection(currentDataSource);
        String message = isConnected ?
                "데이터베이스 연결 정상" :
                "데이터베이스 연결 끊김";
        String type = DbContextHolderThread.getCurrentDb().name();

        return new DatabaseStatus(currentDbType, isConnected, message, type);
    }

    @PreDestroy
    public void cleanup() {
        DbContextHolderThread.clear();
        if (currentDataSource != null) {
            currentDataSource.close();
        }
    }

}
