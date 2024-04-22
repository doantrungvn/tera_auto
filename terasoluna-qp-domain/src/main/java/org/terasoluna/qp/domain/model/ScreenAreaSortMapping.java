package org.terasoluna.qp.domain.model;

public class ScreenAreaSortMapping {

	private static final long serialVersionUID = 1L;
	
	private Long screenAreaSortMappingId;
	
	private String screenAreaCode;
	
	private String screenItemCode;
	
	private String sqlColumnCode;
	
	private String screenAreaId;
	
	private Long screenId;

	public String getScreenAreaCode() {
		return screenAreaCode;
	}

	public void setScreenAreaCode(String screenAreaCode) {
		this.screenAreaCode = screenAreaCode;
	}

	public String getScreenItemCode() {
		return screenItemCode;
	}

	public void setScreenItemCode(String screenItemCode) {
		this.screenItemCode = screenItemCode;
	}

	public String getSqlColumnCode() {
		return sqlColumnCode;
	}

	public void setSqlColumnCode(String sqlColumnCode) {
		this.sqlColumnCode = sqlColumnCode;
	}

	public Long getScreenAreaSortMappingId() {
		return screenAreaSortMappingId;
	}

	public void setScreenAreaSortMappingId(Long screenAreaSortMappingId) {
		this.screenAreaSortMappingId = screenAreaSortMappingId;
	}

	public String getScreenAreaId() {
		return screenAreaId;
	}

	public void setScreenAreaId(String screenAreaId) {
		this.screenAreaId = screenAreaId;
	}

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}
	
}
