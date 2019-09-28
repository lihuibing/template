package org.falcon.template.generator.util;



import java.io.IOException;

import org.falcon.template.generator.entity.ClassInfo;

/**
 * code generate tool
 *
 * @author xuxueli 2018-04-25 16:29:58
 */
public class CodeGeneratorTool {

	/**
	 * process Table Into ClassInfo
	 *
	 * @param tableSql
	 * @return
	 */
	public static ClassInfo processTableIntoClassInfo(String tableSql, boolean isUnderLineToCamelCase) throws IOException {
		return TableParseUtil.processTableIntoClassInfo(tableSql, isUnderLineToCamelCase);
	}

}