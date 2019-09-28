package org.falcon.template.database.model;

import java.io.Serializable;

public class TableDesc implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4603229471668729122L;

	private String field;
	
	private String type;
	
	private boolean isNull;
	
	private String key;
	
	private String extra;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNull() {
		return isNull;
	}

	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	
	

}
