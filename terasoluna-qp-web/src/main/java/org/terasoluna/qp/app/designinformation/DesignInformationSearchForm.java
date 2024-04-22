package org.terasoluna.qp.app.designinformation;

import java.io.Serializable;
import java.sql.Timestamp;

public class DesignInformationSearchForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String moduleCode;
	
	private String moduleName;
	
	private String remark;
	
	private String title;
	
	private Long updatedBy;
	
	private String updatedByAutocomplete;
	
	private Timestamp updatedDate;
	
	private String dateFrom;
	
	private String dateTo;

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedByAutocomplete() {
		return updatedByAutocomplete;
	}

	public void setUpdatedByAutocomplete(String updatedByAutocomplete) {
		this.updatedByAutocomplete = updatedByAutocomplete;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	
}
