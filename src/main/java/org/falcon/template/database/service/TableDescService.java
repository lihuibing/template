package org.falcon.template.database.service;

import java.util.List;
import java.util.Map;

import com.zaxxer.hikari.HikariDataSource;
import org.falcon.template.database.dao.TableDescDao;
import org.falcon.template.generator.entity.JDBCForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class TableDescService {
	
	@Autowired
	TableDescDao tableDescDao;

	@Autowired
	DataSourceService dataSourceService;


	
	/**
	 * 获取表明细
	 * @param tableName 表名称
	 * @return
	 */
	public String getTableDescByTableName(String tableName,JDBCForm jdbcForm){
		JdbcTemplate jdbcTemplate = dataSourceService.getDataSource(jdbcForm);
		return tableDescDao.getTableDescByTableName(tableName,jdbcTemplate);
	}

}
