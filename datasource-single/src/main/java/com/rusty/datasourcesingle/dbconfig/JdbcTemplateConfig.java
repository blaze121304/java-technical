package com.rusty.datasourcesingle.dbconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {

    @Autowired
    private final DatabaseConnectionManager databaseConnectionManager;

    public JdbcTemplateConfig(DatabaseConnectionManager databaseConnectionManager) {
        this.databaseConnectionManager = databaseConnectionManager;
    }


    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new DynamicJdbcTemplate(databaseConnectionManager);
    }

    private static class DynamicJdbcTemplate extends JdbcTemplate {
        private final DatabaseConnectionManager connectionManager;

        public DynamicJdbcTemplate(DatabaseConnectionManager connectionManager) {
//            super(connectionManager.getCurrentDataSource());
            this.connectionManager = connectionManager;
            setDataSource(connectionManager.getCurrentDataSource());
        }

        @Override
        public DataSource getDataSource() {
            return connectionManager.getCurrentDataSource();
        }
    }

}
