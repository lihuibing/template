package org.falcon.template.database.service;

import java.util.List;

import org.falcon.template.database.dao.TableDao;
import org.falcon.template.generator.entity.JDBCForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TableService {
	@Autowired
	TableDao tableDao;

	@Autowired
	DataSourceService dataSourceService;
	
	/**
	 * 查询数据库下所有表名
	 * @return 表名集合
	 */
	public List<String> findAllTables(JDBCForm jdbcForm){
		JdbcTemplate jdbcTemplate = dataSourceService.getDataSource(jdbcForm);
		return tableDao.findAllTables(jdbcTemplate);
	}

}
