package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class BusinessLogic implements Serializable, Comparable<BusinessLogic>  {

	private static final long serialVersionUID = 1L;
	
	private Long businessLogicId;
	
	private String businessLogicName;
	
	private String businessLogicCode;
	
	private int designStatus;
	
	private String moduleName;
	
	private Long moduleId;
	
	private Long tableDesignId;
	
	private Long columnId;
	
	private int autoFix;
	
	private String columnName;
	
	private int columnStatus;
	
	private String dataType;
	
	private String dataTypeName;
	
	private String author;
	
	private String remark;

	public Long getBusinessLogicId() {
		return businessLogicId;
	}

	public void setBusinessLogicId(Long businessLogicId) {
		this.businessLogicId = businessLogicId;
	}

	public String getBusinessLogicName() {
		return businessLogicName;
	}

	public void setBusinessLogicName(String businessLogicName) {
		this.businessLogicName = businessLogicName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getBusinessLogicCode() {
		return businessLogicCode;
	}

	public void setBusinessLogicCode(String businessLogicCode) {
		this.businessLogicCode = businessLogicCode;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getTableDesignId() {
		return tableDesignId;
	}

	public void setTableDesignId(Long tableDesignId) {
		this.tableDesignId = tableDesignId;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public int getAutoFix() {
		return autoFix;
	}

	public void setAutoFix(int autoFix) {
		this.autoFix = autoFix;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getColumnStatus() {
		return columnStatus;
	}

	public void setColumnStatus(int columnStatus) {
		this.columnStatus = columnStatus;
	}

	@Override
	public int compareTo(BusinessLogic businessLogic) {
		Long compareage =((BusinessLogic)businessLogic).getBusinessLogicId();
		return (int) (this.businessLogicId - compareage);
	}

	public String getDataTypeName() {
		return dataTypeName;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	public int getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(int designStatus) {
		this.designStatus = designStatus;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
