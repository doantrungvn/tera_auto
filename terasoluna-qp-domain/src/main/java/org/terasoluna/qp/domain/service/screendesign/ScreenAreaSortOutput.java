package org.terasoluna.qp.domain.service.screendesign;

import java.util.List;

import org.terasoluna.qp.domain.model.ScreenAreaSortMapping;

public class ScreenAreaSortOutput {
	
	private Boolean enableSort;
	
	private String sqlId;
	
	private String sqlCode;
	
	private List<ScreenAreaSortMapping> areaSorts;

	public String getSqlId() {
		if(this.sqlId == null || this.sqlId.equals("null") || this.sqlId.equals("undefined")) {
			return "";
		}
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	public Boolean getEnableSort() {
		return enableSort;
	}

	public void setEnableSort(Boolean enableSort) {
		this.enableSort = enableSort;
	}

	public List<ScreenAreaSortMapping> getAreaSorts() {
		return areaSorts;
	}

	public void setAreaSorts(List<ScreenAreaSortMapping> areaSorts) {
		this.areaSorts = areaSorts;
	}

	public String getSqlCode() {
		return sqlCode;
	}

	public void setSqlCode(String sqlCode) {
		this.sqlCode = sqlCode;
	}

}
