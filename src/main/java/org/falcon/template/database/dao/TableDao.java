package org.falcon.template.database.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TableDao {
    
    public List<String> findAllTables(JdbcTemplate jdbcTemplate){
    	String sql = "show tables";
		return jdbcTemplate.queryForList(sql,String.class);
    }
}