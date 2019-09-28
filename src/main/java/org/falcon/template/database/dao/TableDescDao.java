package org.falcon.template.database.dao;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TableDescDao {
	
	public String getTableDescByTableName(String tableName,JdbcTemplate jdbcTemplate){
		String sql = "show create table " + tableName;
		Map<String,Object> map = jdbcTemplate.queryForMap(sql);
		String tableDesc = (String) map.get("Create Table");
		return tableDesc;
	}
}
