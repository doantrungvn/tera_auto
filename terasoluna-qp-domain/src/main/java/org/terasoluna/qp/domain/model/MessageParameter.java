package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageParameter implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private Long messageParameterId;

	private Integer targetType;

	private Long targetId;

	private Integer parameterType;

	private String parameterCode;

	private String parameterCodeAutocomplete;

	private String parameterValue;

	private Integer parameterScope;

	private int itemSequenceNo;

	private Integer messageLevel;

	private Long businessLogicId;

	private List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();

	private Boolean existedMessageFlg = true;
	
	private String patternFormat;


	public Long getMessageParameterId() {
		return messageParameterId;
	}

	public void setMessageParameterId(Long messageParameterId) {
		this.messageParameterId = messageParameterId;
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public int getItemSequenceNo() {
		return itemSequenceNo;
	}

	public void setItemSequenceNo(int itemSequenceNo) {
		this.itemSequenceNo = itemSequenceNo;
	}

	public String getParameterCodeAutocomplete() {
		return parameterCodeAutocomplete;
	}

	public void setParameterCodeAutocomplete(String parameterCodeAutocomplete) {
		this.parameterCodeAutocomplete = parameterCodeAutocomplete;
	}

	public Integer getMessageLevel() {
		return messageLevel;
	}

	public void setMessageLevel(Integer messageLevel) {
		this.messageLevel = messageLevel;
	}

	public Integer getParameterType() {
		return parameterType;
	}

	public void setParameterType(Integer parameterType) {
		this.parameterType = parameterType;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public Long getBusinessLogicId() {
		return businessLogicId;
	}

	public void setBusinessLogicId(Long businessLogicId) {
		this.businessLogicId = businessLogicId;
	}

	public Integer getParameterScope() {
		return parameterScope;
	}

	public void setParameterScope(Integer parameterScope) {
		this.parameterScope = parameterScope;
	}

	public List<BDParameterIndex> getLstParameterIndex() {
		return lstParameterIndex;
	}

	public void setLstParameterIndex(List<BDParameterIndex> lstParameterIndex) {
		this.lstParameterIndex = lstParameterIndex;
	}

	public Boolean getExistedMessageFlg() {
		return existedMessageFlg;
	}

	public void setExistedMessageFlg(Boolean existedMessageFlg) {
		this.existedMessageFlg = existedMessageFlg;
	}

	public String getPatternFormat() {
		return patternFormat;
	}

	public void setPatternFormat(String value1) {
		this.patternFormat = value1;
	}

}
