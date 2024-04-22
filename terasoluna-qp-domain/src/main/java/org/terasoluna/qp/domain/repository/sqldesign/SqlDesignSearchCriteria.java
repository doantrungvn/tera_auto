package org.terasoluna.qp.domain.repository.sqldesign;

import java.io.Serializable;

public class SqlDesignSearchCriteria implements Serializable {

	private static final long serialVersionUID = -916259283217717465L;
	private String sqlDesignName;
	private String sqlDesignCode;
	private Long moduleId;
	private Integer[] sqlDesignTypes;
	private Long tableId;
	private String tableIdAutocomplete;
	private Long projectId;
	private Integer[] designStatus;

	public Integer[] getDesignStatus() {
		return designStatus;
	}
	public void setDesignStatus(Integer[] designStatus) {
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
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public Integer[] getSqlDesignTypes() {
		return sqlDesignTypes;
	}
	public void setSqlDesignTypes(Integer[] sqlDesignTypes) {
		this.sqlDesignTypes = sqlDesignTypes;
	}
	public Long getTableId() {
		return tableId;
	}
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	public String getTableIdAutocomplete() {
		return tableIdAutocomplete;
	}
	public void setTableIdAutocomplete(String tableIdAutocomplete) {
		this.tableIdAutocomplete = tableIdAutocomplete;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
}
