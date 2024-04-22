package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExceptionDetail implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long exceptionDetailId;

	private Long exceptionComponentId;

	private Long inputBeanId;

	private String inputBeanName  = "";

	private String messageStringAutocomplete;

	private String inputBeanCode  = "";

	private Integer dataType;

	private Boolean arrayFlg;

	private Integer parameterScope;

	private String parameterId  = "";

	private String parameterIdAutocomplete = "";

	private String impactStatus;

	private Long inputBeanIdRefer;

	private String inputBeanCodeRefer;

	private String inputBeanNameRefer;

	private Integer dataTypeRefer;

	private Boolean arrayFlgRefer = false;

	private Long exceptionToIdRefer;

	private Long businessLogicId;

	private Long moduleId;

	private List<ExceptionDetail> lstDefaultObject;

	private List<ExceptionDetail> lstCreateNewObject;

	private List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();

	public Long getExceptionDetailId() {
		return exceptionDetailId;
	}

	public void setExceptionDetailId(Long exceptionDetailId) {
		this.exceptionDetailId = exceptionDetailId;
	}

	public Long getExceptionComponentId() {
		return exceptionComponentId;
	}

	public void setExceptionComponentId(Long exceptionComponentId) {
		this.exceptionComponentId = exceptionComponentId;
	}

	public String getInputBeanCode() {
		return inputBeanCode;
	}

	public void setInputBeanCode(String inputBeanCode) {
		this.inputBeanCode = inputBeanCode;
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

	public Long getInputBeanId() {
		return inputBeanId;
	}

	public void setInputBeanId(Long inputBeanId) {
		this.inputBeanId = inputBeanId;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getInputBeanName() {
		return inputBeanName;
	}

	public void setInputBeanName(String inputBeanName) {
		this.inputBeanName = inputBeanName;
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

	public Long getExceptionToIdRefer() {
		return exceptionToIdRefer;
	}

	public void setExceptionToIdRefer(Long exceptionToIdRefer) {
		this.exceptionToIdRefer = exceptionToIdRefer;
	}

	public String getMessageStringAutocomplete() {
		return messageStringAutocomplete;
	}

	public void setMessageStringAutocomplete(String messageStringAutocomplete) {
		this.messageStringAutocomplete = messageStringAutocomplete;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getBusinessLogicId() {
		return businessLogicId;
	}

	public void setBusinessLogicId(Long businessLogicId) {
		this.businessLogicId = businessLogicId;
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

	/**
	 * @return the lstDefaultObject
	 */
	public List<ExceptionDetail> getLstDefaultObject() {
		return lstDefaultObject;
	}

	/**
	 * @param lstDefaultObject the lstDefaultObject to set
	 */
	public void setLstDefaultObject(List<ExceptionDetail> lstDefaultObject) {
		this.lstDefaultObject = lstDefaultObject;
	}

	/**
	 * @return the lstCreateNewObject
	 */
	public List<ExceptionDetail> getLstCreateNewObject() {
		return lstCreateNewObject;
	}

	/**
	 * @param lstCreateNewObject the lstCreateNewObject to set
	 */
	public void setLstCreateNewObject(List<ExceptionDetail> lstCreateNewObject) {
		this.lstCreateNewObject = lstCreateNewObject;
	}

	public List<BDParameterIndex> getLstParameterIndex() {
		return lstParameterIndex;
	}

	public void setLstParameterIndex(List<BDParameterIndex> lstParameterIndex) {
		this.lstParameterIndex = lstParameterIndex;
	}
}
