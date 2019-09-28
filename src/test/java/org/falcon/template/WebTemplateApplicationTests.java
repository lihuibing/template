package org.falcon.template;

import java.util.List;
import java.util.Map;

import org.falcon.template.database.service.TableDescService;
import org.falcon.template.generator.entity.JDBCForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebTemplateApplicationTests {
	
	@Autowired
	TableDescService service;

	@Test
	public void contextLoads() {
		JDBCForm jdbcForm = new JDBCForm();
		jdbcForm.setDriverClassName("com.mysql.cj.jdbc.Driver");
		jdbcForm.setJdbcUrl("jdbc:mysql://localhost:3306/template?useUnicode=true&characterEncoding=utf-8");
		jdbcForm.setUsername("root");
		jdbcForm.setPassword("123456");
		String tableDesc = service.getTableDescByTableName("t_sys_user",jdbcForm);
		System.out.println(tableDesc);
	}

}
