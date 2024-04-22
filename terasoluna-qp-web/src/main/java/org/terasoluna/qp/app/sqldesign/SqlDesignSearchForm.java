package org.terasoluna.qp.app.sqldesign;

import java.io.Serializable;

public class SqlDesignSearchForm implements Serializable{

	private static final long serialVersionUID = -122375287212682271L;
	private String sqlDesignName;
	private String sqlDesignCode;
	private String moduleId;
	private String moduleIdAutocomplete;
	private String[] sqlDesignTypes;
	private String tableId;
	private String tableIdAutocomplete;
	private String[] designStatus;

	public String[] getDesignStatus() {
		return designStatus;
	}
	public void setDesignStatus(String[] designStatus) {
		this.designStatus = designStatus;
	}
	public String getSqlDesignName() {
		return sqlDesignName;
	}
	public void setSqlDesignName(String sqlDesignName) {
		this.sqlDesignName = sqlDesignName;
	}
	public String getSqlDesignCode() {
		return sqlDesignCode;
	}
	public void setSqlDesignCode(String sqlDesignCode) {
		this.sqlDesignCode = sqlDesignCode;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}
	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}
	public String[] getSqlDesignTypes() {
		return sqlDesignTypes;
	}
	public void setSqlDesignTypes(String[] sqlDesignTypes) {
		this.sqlDesignTypes = sqlDesignTypes;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getTableIdAutocomplete() {
		return tableIdAutocomplete;
	}
	public void setTableIdAutocomplete(String tableIdAutocomplete) {
		this.tableIdAutocomplete = tableIdAutocomplete;
	}

}
