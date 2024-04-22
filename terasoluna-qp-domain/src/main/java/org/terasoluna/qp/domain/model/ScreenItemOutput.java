package org.terasoluna.qp.domain.model;

public class ScreenItemOutput extends ScreenItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String formCode;
	
	private Integer formRowspan;
	
	private String areaName;
	
	private String areaType;
	
	private String messageCodeArea;
	
	private Integer areaRowspan;
	
	private String itemName;
	
	private Integer dataType;
	
	private Boolean outputSelect;
	
	private Long screenFormId;
	
	private Integer mappingType;
	
	private String messageString;
	
	private String logicalDataTypeMessageString;

	private Integer areaTypeAction;
	
	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public Integer getFormRowspan() {
		return formRowspan;
	}

	public void setFormRowspan(Integer formRowspan) {
		this.formRowspan = formRowspan;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public Integer getAreaRowspan() {
		return areaRowspan;
	}

	public void setAreaRowspan(Integer areaRowspan) {
		this.areaRowspan = areaRowspan;
	}

	public String getItemName() {
	    return itemName;
    }

	public void setItemName(String itemName) {
	    this.itemName = itemName;
    }

	public Integer getDataType() {
	    return dataType;
    }

	public void setDataType(Integer dataType) {
	    this.dataType = dataType;
    }

	public Long getScreenFormId() {
	    return screenFormId;
    }

	public void setScreenFormId(Long screenFormId) {
	    this.screenFormId = screenFormId;
    }

	public Boolean getOutputSelect() {
	    return outputSelect;
    }

	public void setOutputSelect(Boolean outputSelect) {
	    this.outputSelect = outputSelect;
    }

	public String getMessageCodeArea() {
	    return messageCodeArea;
    }

	public void setMessageCodeArea(String messageCodeArea) {
	    this.messageCodeArea = messageCodeArea;
    }

	public Integer getMappingType() {
		return mappingType;
	}

	public void setMappingType(Integer mappingType) {
		this.mappingType = mappingType;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	public String getLogicalDataTypeMessageString() {
	    return logicalDataTypeMessageString;
    }

	public void setLogicalDataTypeMessageString(String logicalDataTypeMessageString) {
	    this.logicalDataTypeMessageString = logicalDataTypeMessageString;
    }

	public Integer getAreaTypeAction() {
		return areaTypeAction;
	}

	public void setAreaTypeAction(Integer areaTypeAction) {
		this.areaTypeAction = areaTypeAction;
	}
}
