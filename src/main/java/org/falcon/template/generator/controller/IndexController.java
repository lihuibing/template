package org.falcon.template.generator.controller;

import freemarker.template.TemplateException;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import org.apache.commons.lang3.StringUtils;
import org.falcon.template.database.service.DataSourceService;
import org.falcon.template.database.service.TableDescService;
import org.falcon.template.database.service.TableService;
import org.falcon.template.generator.entity.ClassInfo;
import org.falcon.template.generator.entity.JDBCForm;
import org.falcon.template.generator.entity.ReturnT;
import org.falcon.template.generator.util.CodeGeneratorTool;
import org.falcon.template.generator.util.FileUtil;
import org.falcon.template.generator.util.FreemarkerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * spring boot code generator
 * 
 * @author zhengk/moshow
 */
@RestController
public class IndexController {

	@Autowired
	private FreemarkerTool freemarkerTool;

	@Autowired
	private DataSourceService dataSourceService;

	@Autowired
	private TableService tableService;

	@Autowired
	private TableDescService tableDescService;

	@RequestMapping("/allTables")
	public List<Map<String,String>> findAllTables(@RequestBody JDBCForm jdbcForm){
		JdbcTemplate jdbcTemplate = dataSourceService.getDataSource(jdbcForm);
		List<Map<String,String>> datas = new ArrayList<>();
		if(jdbcTemplate != null){
			List<String> tables = tableService.findAllTables(jdbcForm);
			for(String table:tables){
				Map<String,String> data = new HashMap<>();
				String tableDesc = tableDescService.getTableDescByTableName(table,jdbcForm);
				data.put("tableName",table);
				data.put("tableDesc",tableDesc);
				datas.add(data);
			}
		}
		return datas;
	}

	@RequestMapping("/genCode")
	public void codeGenerate(String tableSql,
								 @RequestParam(required = false, defaultValue = "大狼狗") String authorName,
								 @RequestParam(required = false, defaultValue = "com.softdev.system") String packageName,
								 @RequestParam(required = false, defaultValue = "ApiReturnUtil") String returnUtil,
								 @RequestParam(required = false, defaultValue = "true") boolean isUnderLineToCamelCase,
								 HttpServletResponse response) {

		try {

//			if (StringUtils.isBlank(tableSql)) {
//				return new ReturnT<>(ReturnT.FAIL_CODE, "表结构信息不可为空");
//			}

			// parse table
            ClassInfo classInfo = CodeGeneratorTool.processTableIntoClassInfo(tableSql, isUnderLineToCamelCase);

            // code genarete
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("classInfo", classInfo);
            params.put("authorName", authorName);
            params.put("packageName", packageName);
            params.put("returnUtil", returnUtil);
            //controller
            writeFile("code-generator/mybatis/controller.ftl",params,packageName,"controller",classInfo,true);
			//service
			writeFile("code-generator/mybatis/service.ftl",params,packageName,"service",classInfo,true);
			//service_impl
			writeFile("code-generator/mybatis/service_impl.ftl",params,packageName,"serviceImpl",classInfo,true);
			//mapper
			writeFile("code-generator/mybatis/mapper.ftl",params,packageName,"mapper",classInfo,true);
			//model
			writeFile("code-generator/mybatis/model.ftl",params,packageName,"model",classInfo,true);
			//mybatis
			writeFile("code-generator/mybatis/mybatis.ftl",params,packageName,"mybatis",classInfo,false);
			File dir = new File(getRootDir(packageName));
			ZipFile zipFile = new ZipFile("code.zip");
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionLevel(CompressionLevel.NORMAL);
			parameters.setCompressionMethod(CompressionMethod.DEFLATE);
			File packageDir = new File(dir.getAbsolutePath());
			zipFile.addFolder(packageDir,parameters);
			File zip = new File("code.zip");

			OutputStream out = response.getOutputStream();
			FileInputStream in = new FileInputStream(zip);

			response.setHeader("Content-Type","application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename="+java.net.URLEncoder.encode("code.zip", "UTF-8"));
			int read = 0;
			byte[] buffer = new byte[1024];
			while((read = in.read(buffer)) != -1){
				out.write(buffer, 0, read);
			}

			FileUtil.delFile(zip);
			FileUtil.delFile(packageDir);

			System.out.println(freemarkerTool.processString("code-generator/front/ts/views.ftl", params));


//			// result
//			Map<String, String> result = new HashMap<String, String>();
//
//			// UI
//			result.put("swagger-ui", freemarkerTool.processString("code-generator/ui/swagger-ui.ftl", params));
//			result.put("element-ui", freemarkerTool.processString("code-generator/ui/element-ui.ftl", params));
//			result.put("bootstrap-ui", freemarkerTool.processString("code-generator/ui/bootstrap-ui.ftl", params));
//			// mybatis old
//			result.put("controller", freemarkerTool.processString("code-generator/mybatis/controller.ftl", params));
//			result.put("service", freemarkerTool.processString("code-generator/mybatis/service.ftl", params));
//			result.put("service_impl", freemarkerTool.processString("code-generator/mybatis/service_impl.ftl", params));
//			result.put("mapper", freemarkerTool.processString("code-generator/mybatis/mapper.ftl", params));
//			result.put("mybatis", freemarkerTool.processString("code-generator/mybatis/mybatis.ftl", params));
//			result.put("model", freemarkerTool.processString("code-generator/mybatis/model.ftl", params));
//			// jpa
//			result.put("entity", freemarkerTool.processString("code-generator/jpa/entity.ftl", params));
//			result.put("repository", freemarkerTool.processString("code-generator/jpa/repository.ftl", params));
//			result.put("jpacontroller", freemarkerTool.processString("code-generator/jpa/jpacontroller.ftl", params));
//			// jdbc template
//			result.put("jtdao", freemarkerTool.processString("code-generator/jdbc-template/jtdao.ftl", params));
//			result.put("jtdaoimpl", freemarkerTool.processString("code-generator/jdbc-template/jtdaoimpl.ftl", params));
//			// beetsql
//			result.put("beetlmd", freemarkerTool.processString("code-generator/beetlsql/beetlmd.ftl", params));
//			result.put("beetlentity", freemarkerTool.processString("code-generator/beetlsql/beetlentity.ftl", params));
//			result.put("beetlentitydto",
//					freemarkerTool.processString("code-generator/beetlsql/beetlentitydto.ftl", params));
//			result.put("beetlcontroller",
//					freemarkerTool.processString("code-generator/beetlsql/beetlcontroller.ftl", params));
//			// mybatis plus
//			result.put("pluscontroller",
//					freemarkerTool.processString("code-generator/mybatis-plus/pluscontroller.ftl", params));
//			result.put("plusmapper",
//					freemarkerTool.processString("code-generator/mybatis-plus/plusmapper.ftl", params));
//			// util
//			result.put("util", freemarkerTool.processString("code-generator/util/util.ftl", params));
//			// sql generate
//			result.put("select", freemarkerTool.processString("code-generator/sql/select.ftl", params));
//			result.put("insert", freemarkerTool.processString("code-generator/sql/insert.ftl", params));
//			result.put("update", freemarkerTool.processString("code-generator/sql/update.ftl", params));
//			result.put("delete", freemarkerTool.processString("code-generator/sql/delete.ftl", params));

			// 计算,生成代码行数
//			int lineNum = 0;
//			for (Map.Entry<String, String> item : result.entrySet()) {
//				if (item.getValue() != null) {
//					lineNum += StringUtils.countMatches(item.getValue(), "\n");
//				}
//			}
			// 测试环境可自行开启
			// log.info("生成代码数据：{}", result);
//			return new ReturnT<>(result);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
//			return new ReturnT<>(ReturnT.FAIL_CODE, "表结构解析失败" + e.getMessage());
		}

	}

	private String getRootDir(String packageName){
		String packageFiles = packageName.split("\\.")[0];
		return packageFiles;
	}

	private void writeFile(String templateName, Map<String, Object> params,String packageName,String type, ClassInfo classInfo,boolean isJava) throws IOException, TemplateException {
		String controllerStr = freemarkerTool.processString(templateName,params);
		String packageFiles = packageName.replaceAll("\\.","/");
		FileUtil.createDirs(packageFiles);
		String packageStr = packageFiles + "/" + type;
		FileUtil.createDirs(packageStr);
		String fileName = classInfo.getClassName();
		if(!StringUtils.equals(type,"model")){
			type = org.falcon.template.generator.util.StringUtils.upperCaseFirst(type);
			if(isJava){
				fileName = String.format("%s%s.java", classInfo.getClassName(), type);
			} else {
				fileName = String.format("%s.xml", classInfo.getClassName());
			}

		} else {
			fileName = fileName + ".java";
		}
		FileUtil.writeFile(packageStr,fileName,controllerStr);
	}

}
