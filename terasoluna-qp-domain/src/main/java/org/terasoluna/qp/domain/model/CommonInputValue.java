package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommonInputValue implements Serializable,Cloneable  {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long commonInputValueId;

	private Long commonComponentId;

	private Long inputBeanId;

	private String inputBeanCode;

	private String inputBeanName;

	private String messageStringAutocomplete;

	private Integer dataType;

	private Boolean arrayFlg = false;

	private Integer parameterScope;

	private String parameterId;

	private String parameterIdAutocomplete;

	private String impactStatus;

	private Long inputBeanIdRefer;

	private String inputBeanCodeRefer;

	private String inputBeanNameRefer;

	private Integer dataTypeRefer;

	private Boolean arrayFlgRefer = false;

	private Long businessLogicIdRefer;

	private Long businessLogicId;

	private Long moduleId;

	private List<CommonInputValue> lstNotObjScopeObjDef;

	private List<CommonInputValue> lstObjScopeObjDefTypeTree;

	private List<CommonInputValue> lstNotObjScopeInput;

	private List<CommonInputValue> lstObjScopeInputTypeTree;

	private List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();

	public Long getCommonInputValueId() {
		return commonInputValueId;
	}

	public void setCommonInputValueId(Long commonInputValueId) {
		this.commonInputValueId = commonInputValueId;
	}

	public Long getCommonComponentId() {
		return commonComponentId;
	}

	public void setCommonComponentId(Long commonComponentId) {
		this.commonComponentId = commonComponentId;
	}

	public Long getInputBeanId() {
		return inputBeanId;
	}

	public void setInputBeanId(Long inputBeanId) {
		this.inputBeanId = inputBeanId;
	}

	public String getInputBeanCode() {
		return inputBeanCode;
	}

	public void setInputBeanCode(String inputBeanCode) {
		this.inputBeanCode = inputBeanCode;
	}

	public String getInputBeanName() {
		return inputBeanName;
	}

	public void setInputBeanName(String inputBeanName) {
		this.inputBeanName = inputBeanName;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getParameterScope() {
		return parameterScope;
	}

	public void setParameterScope(Integer parameterScope) {
		this.parameterScope = parameterScope;
	}

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public String getParameterIdAutocomplete() {
		return parameterIdAutocomplete;
	}

	public void setParameterIdAutocomplete(String parameterIdAutocomplete) {
		this.parameterIdAutocomplete = parameterIdAutocomplete;
	}

	public String getImpactStatus() {
		return impactStatus;
	}

	public void setImpactStatus(String impactStatus) {
		this.impactStatus = impactStatus;
	}

	public Long getInputBeanIdRefer() {
		return inputBeanIdRefer;
	}

	public void setInputBeanIdRefer(Long inputBeanIdRefer) {
		this.inputBeanIdRefer = inputBeanIdRefer;
	}

	public String getInputBeanCodeRefer() {
		return inputBeanCodeRefer;
	}

	public void setInputBeanCodeRefer(String inputBeanCodeRefer) {
		this.inputBeanCodeRefer = inputBeanCodeRefer;
	}

	public String getInputBeanNameRefer() {
		return inputBeanNameRefer;
	}

	public void setInputBeanNameRefer(String inputBeanNameRefer) {
		this.inputBeanNameRefer = inputBeanNameRefer;
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

	public String getMessageStringAutocomplete() {
		return messageStringAutocomplete;
	}

	public void setMessageStringAutocomplete(String messageStringAutocomplete) {
		this.messageStringAutocomplete = messageStringAutocomplete;
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
	 * @return the lstNotObjScopeObjDef
	 */
	public List<CommonInputValue> getLstNotObjScopeObjDef() {
		return lstNotObjScopeObjDef;
	}

	/**
	 * @param lstNotObjScopeObjDef the lstNotObjScopeObjDef to set
	 */
	public void setLstNotObjScopeObjDef(List<CommonInputValue> lstNotObjScopeObjDef) {
		this.lstNotObjScopeObjDef = lstNotObjScopeObjDef;
	}

	/**
	 * @return the lstObjScopeObjDefTypeTree
	 */
	public List<CommonInputValue> getLstObjScopeObjDefTypeTree() {
		return lstObjScopeObjDefTypeTree;
	}

	/**
	 * @param lstObjScopeObjDefTypeTree the lstObjScopeObjDefTypeTree to set
	 */
	public void setLstObjScopeObjDefTypeTree(
			List<CommonInputValue> lstObjScopeObjDefTypeTree) {
		this.lstObjScopeObjDefTypeTree = lstObjScopeObjDefTypeTree;
	}

	/**
	 * @return the lstNotObjScopeInput
	 */
	public List<CommonInputValue> getLstNotObjScopeInput() {
		return lstNotObjScopeInput;
	}

	/**
	 * @param lstNotObjScopeInput the lstNotObjScopeInput to set
	 */
	public void setLstNotObjScopeInput(List<CommonInputValue> lstNotObjScopeInput) {
		this.lstNotObjScopeInput = lstNotObjScopeInput;
	}

	/**
	 * @return the lstObjScopeInputTypeTree
	 */
	public List<CommonInputValue> getLstObjScopeInputTypeTree() {
		return lstObjScopeInputTypeTree;
	}

	/**
	 * @param lstObjScopeInputTypeTree the lstObjScopeInputTypeTree to set
	 */
	public void setLstObjScopeInputTypeTree(List<CommonInputValue> lstObjScopeInputTypeTree) {
		this.lstObjScopeInputTypeTree = lstObjScopeInputTypeTree;
	}

	public List<BDParameterIndex> getLstParameterIndex() {
	    return lstParameterIndex;
    }

	public void setLstParameterIndex(List<BDParameterIndex> lstParameterIndex) {
	    this.lstParameterIndex = lstParameterIndex;
    }
}
