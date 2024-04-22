package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommonOutputValue implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long commonOutputValueId;

	private Long commonComponentId;

	private Long outputBeanId;

	private String outputBeanCode;

	private String outputBeanName;

	private Integer dataType;

	private Boolean arrayFlg = false;

	private Integer targetScope;

	private String targetId;

	private String targetIdAutocomplete;

	private String impactStatus;

	private Long outputBeanIdRefer;

	private String outputBeanCodeRefer;

	private String outputBeanNameRefer;

	private Integer dataTypeRefer;

	private Boolean arrayFlgRefer = false;

	private Long businessLogicIdRefer;

	private Long businessLogicId;

	private Long moduleId;

	private List<CommonOutputValue> lstNotObjScopeOutput;

	private List<CommonOutputValue> lstObjScopeOutputTypeTree;

	private List<BDParameterIndex> lstTargetIndex = new ArrayList<BDParameterIndex>();

	public Long getCommonOutputValueId() {
		return commonOutputValueId;
	}

	public void setCommonOutputValueId(Long commonOutputValueId) {
		this.commonOutputValueId = commonOutputValueId;
	}

	public Long getCommonComponentId() {
		return commonComponentId;
	}

	public void setCommonComponentId(Long commonComponentId) {
		this.commonComponentId = commonComponentId;
	}

	public Long getOutputBeanId() {
		return outputBeanId;
	}

	public void setOutputBeanId(Long outputBeanId) {
		this.outputBeanId = outputBeanId;
	}

	public String getOutputBeanCode() {
		return outputBeanCode;
	}

	public void setOutputBeanCode(String outputBeanCode) {
		this.outputBeanCode = outputBeanCode;
	}

	public String getOutputBeanName() {
		return outputBeanName;
	}

	public void setOutputBeanName(String outputBeanName) {
		this.outputBeanName = outputBeanName;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getTargetScope() {
		return targetScope;
	}

	public void setTargetScope(Integer targetScope) {
		this.targetScope = targetScope;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTargetIdAutocomplete() {
		return targetIdAutocomplete;
	}

	public void setTargetIdAutocomplete(String targetIdAutocomplete) {
		this.targetIdAutocomplete = targetIdAutocomplete;
	}

	public String getImpactStatus() {
		return impactStatus;
	}

	public void setImpactStatus(String impactStatus) {
		this.impactStatus = impactStatus;
	}

	public Long getOutputBeanIdRefer() {
		return outputBeanIdRefer;
	}

	public void setOutputBeanIdRefer(Long outputBeanIdRefer) {
		this.outputBeanIdRefer = outputBeanIdRefer;
	}

	public String getOutputBeanCodeRefer() {
		return outputBeanCodeRefer;
	}

	public void setOutputBeanCodeRefer(String outputBeanCodeRefer) {
		this.outputBeanCodeRefer = outputBeanCodeRefer;
	}

	public String getOutputBeanNameRefer() {
		return outputBeanNameRefer;
	}

	public void setOutputBeanNameRefer(String outputBeanNameRefer) {
		this.outputBeanNameRefer = outputBeanNameRefer;
	}

	public Integer getDataTypeRefer() {
		return dataTypeRefer;
	}

	public void setDataTypeRefer(Integer dataTypeRefer) {
		this.dataTypeRefer = dataTypeRefer;
	}


	public Long getBusinessLogicIdRefer() {
		return businessLogicIdRefer;
	}

	public void setBusinessLogicIdRefer(Long businessLogicIdRefer) {
		this.businessLogicIdRefer = businessLogicIdRefer;
	}

	public Boolean getArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(Boolean arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	public Boolean getArrayFlgRefer() {
		return arrayFlgRefer;
	}

	public void setArrayFlgRefer(Boolean arrayFlgRefer) {
		this.arrayFlgRefer = arrayFlgRefer;
	}

	public Long getBusinessLogicId() {
		return businessLogicId;
	}

	public void setBusinessLogicId(Long businessLogicId) {
		this.businessLogicId = businessLogicId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * @return the lstObjScopeOutputTypeTree
	 */
	public List<CommonOutputValue> getLstObjScopeOutputTypeTree() {
		return lstObjScopeOutputTypeTree;
	}

	/**
	 * @param lstObjScopeOutputTypeTree the lstObjScopeOutputTypeTree to set
	 */
	public void setLstObjScopeOutputTypeTree(
			List<CommonOutputValue> lstObjScopeOutputTypeTree) {
		this.lstObjScopeOutputTypeTree = lstObjScopeOutputTypeTree;
	}

	/**
	 * @return the lstNotObjScopeOutput
	 */
	public List<CommonOutputValue> getLstNotObjScopeOutput() {
		return lstNotObjScopeOutput;
	}

	/**
	 * @param lstNotObjScopeOutput the lstNotObjScopeOutput to set
	 */
	public void setLstNotObjScopeOutput(List<CommonOutputValue> lstNotObjScopeOutput) {
		this.lstNotObjScopeOutput = lstNotObjScopeOutput;
	}

	public List<BDParameterIndex> getLstTargetIndex() {
		return lstTargetIndex;
	}

	public void setLstTargetIndex(List<BDParameterIndex> lstTargetIndex) {
		this.lstTargetIndex = lstTargetIndex;
	}

}
