package org.falcon.template.database.service;

import com.zaxxer.hikari.HikariDataSource;
import org.falcon.template.generator.entity.JDBCForm;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataSourceService {

    public JdbcTemplate getDataSource(JDBCForm jdbcForm){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcForm.getJdbcUrl());
        dataSource.setUsername(jdbcForm.getUsername());
        dataSource.setPassword(jdbcForm.getPassword());
        dataSource.setDriverClassName(jdbcForm.getDriverClassName());
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }
}
