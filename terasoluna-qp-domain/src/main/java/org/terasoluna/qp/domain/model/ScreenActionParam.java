package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ScreenActionParam implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long screenActionParamId;
	private Long screenActionId;
	private Long domainTblMappingId;
	private Long domainTblMappingItemId;
	private String actionParamCode;
	private String actionParamName;
	private Integer dataType;
	private Integer arrayFlg;
	private Integer paramSeqNo;
	private Long parentScreenActionParamId;
	private Long createdBy;
	private Timestamp createdDate;
	private Long updatedBy;
	private Timestamp updatedDate;
	private String domainColumnName;
	private String domainTblMappingName;
	private String screenItemCode;
	private Long screenId;
	private Long moduleId;
	private Long outputBeanId;
	private String areaCodeOfItem;
	private String formCodeOfItem;
	private Integer areaTypeOfItem;
	private Long areaIdOfItem;
	private Boolean arrayFlgOfJava;
	private Long itemId;
	private Integer itemPhysicalDataType;
	private Long outputIdOfItem;
	private Integer outputPhysicalDataTypeOfItem;
	
	public Long getScreenActionId() {
		return screenActionId;
	}

	public void setScreenActionId(Long screenActionId) {
		this.screenActionId = screenActionId;
	}

	public String getActionParamCode() {
		return actionParamCode;
	}

	public void setActionParamCode(String actionParamCode) {
		this.actionParamCode = actionParamCode;
	}

	public Integer getParamSeqNo() {
		return paramSeqNo;
	}

	public void setParamSeqNo(Integer paramSeqNo) {
		this.paramSeqNo = paramSeqNo;
	}

	public Long getDomainTblMappingId() {
		return domainTblMappingId;
	}

	public void setDomainTblMappingId(Long domainTblMappingId) {
		this.domainTblMappingId = domainTblMappingId;
	}

	public Long getDomainTblMappingItemId() {
		return domainTblMappingItemId;
	}

	public void setDomainTblMappingItemId(Long domainTblMappingItemId) {
		this.domainTblMappingItemId = domainTblMappingItemId;
	}

	public Long getScreenActionParamId() {
		return screenActionParamId;
	}

	public void setScreenActionParamId(Long screenActionParamId) {
		this.screenActionParamId = screenActionParamId;
	}

	public String getActionParamName() {
		return actionParamName;
	}

	public void setActionParamName(String actionParamName) {
		this.actionParamName = actionParamName;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(Integer arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	public Long getParentScreenActionParamId() {
		return parentScreenActionParamId;
	}

	public void setParentScreenActionParamId(Long parentScreenActionParamId) {
		this.parentScreenActionParamId = parentScreenActionParamId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
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

	public String getDomainColumnName() {
		return domainColumnName;
	}

	public void setDomainColumnName(String domainColumnName) {
		this.domainColumnName = domainColumnName;
	}

	public String getDomainTblMappingName() {
		return domainTblMappingName;
	}

	public void setDomainTblMappingName(String domainTblMappingName) {
		this.domainTblMappingName = domainTblMappingName;
	}

	public String getScreenItemCode() {
		return screenItemCode;
	}

	public void setScreenItemCode(String screenItemCode) {
		this.screenItemCode = screenItemCode;
	}

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getOutputBeanId() {
		return outputBeanId;
	}

	public void setOutputBeanId(Long outputBeanId) {
		this.outputBeanId = outputBeanId;
	}

	public String getAreaCodeOfItem() {
		return areaCodeOfItem;
	}

	public void setAreaCodeOfItem(String areaCodeOfItem) {
		this.areaCodeOfItem = areaCodeOfItem;
	}

	public String getFormCodeOfItem() {
		return formCodeOfItem;
	}

	public void setFormCodeOfItem(String formCodeOfItem) {
		this.formCodeOfItem = formCodeOfItem;
	}

	public Integer getAreaTypeOfItem() {
		return areaTypeOfItem;
	}

	public void setAreaTypeOfItem(Integer areaTypeOfItem) {
		this.areaTypeOfItem = areaTypeOfItem;
	}

	public Long getAreaIdOfItem() {
		return areaIdOfItem;
	}

	public void setAreaIdOfItem(Long areaIdOfItem) {
		this.areaIdOfItem = areaIdOfItem;
	}

	public Integer getItemPhysicalDataType() {
		return itemPhysicalDataType;
	}

	public void setItemPhysicalDataType(Integer itemPhysicalDataType) {
		this.itemPhysicalDataType = itemPhysicalDataType;
	}

	public Boolean getArrayFlgOfJava() {
	    return arrayFlgOfJava;
    }

	public void setArrayFlgOfJava(Boolean arrayFlgOfJava) {
	    this.arrayFlgOfJava = arrayFlgOfJava;
    }

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getOutputIdOfItem() {
		return outputIdOfItem;
	}

	public void setOutputIdOfItem(Long outputIdOfItem) {
		this.outputIdOfItem = outputIdOfItem;
	}

	public Integer getOutputPhysicalDataTypeOfItem() {
		return outputPhysicalDataTypeOfItem;
	}

	public void setOutputPhysicalDataTypeOfItem(Integer outputPhysicalDataTypeOfItem) {
		this.outputPhysicalDataTypeOfItem = outputPhysicalDataTypeOfItem;
	}
	
	
}
