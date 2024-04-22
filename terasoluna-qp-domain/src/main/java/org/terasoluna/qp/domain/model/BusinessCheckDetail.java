package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BusinessCheckDetail implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long businessCheckDetailId;

	private Integer businessCheckType;

	private Long formulaDefinitionId;

	private String formulaDefinitionContent;

	private List<FormulaDetail> formulaDefinitionDetails = new ArrayList<FormulaDetail>();

	private String messageCode;

	private String messageCodeAutocomplete;

	private Boolean existedMessageFlg = true;

	private boolean abortFlg;

	private Long businessCheckComponentId;

	private Integer itemSequenceNo;

	private List<MessageParameter> parameters;

	private List<BusinessDetailContent> contents;

	public Long getBusinessCheckDetailId() {
		return businessCheckDetailId;
	}

	public void setBusinessCheckDetailId(Long businessCheckDetailId) {
		this.businessCheckDetailId = businessCheckDetailId;
	}

	public Integer getBusinessCheckType() {
		return businessCheckType;
	}

	public void setBusinessCheckType(Integer businessCheckType) {
		this.businessCheckType = businessCheckType;
	}

	public String getFormulaDefinitionContent() {
		return formulaDefinitionContent;
	}

	public void setFormulaDefinitionContent(String formulaDefinitionContent) {
		this.formulaDefinitionContent = formulaDefinitionContent;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public boolean isAbortFlg() {
		return abortFlg;
	}

	public void setAbortFlg(boolean abortFlg) {
		this.abortFlg = abortFlg;
	}

	public Long getBusinessCheckComponentId() {
		return businessCheckComponentId;
	}

	public void setBusinessCheckComponentId(Long businessCheckComponentId) {
		this.businessCheckComponentId = businessCheckComponentId;
	}

	public Integer getItemSequenceNo() {
		return itemSequenceNo;
	}

	public void setItemSequenceNo(Integer itemSequenceNo) {
		this.itemSequenceNo = itemSequenceNo;
	}

	public String getMessageCodeAutocomplete() {
		return messageCodeAutocomplete;
	}

	public void setMessageCodeAutocomplete(String messageCodeAutocomplete) {
		this.messageCodeAutocomplete = messageCodeAutocomplete;
	}

	public List<FormulaDetail> getFormulaDefinitionDetails() {
		return formulaDefinitionDetails;
	}

	public void setFormulaDefinitionDetails(List<FormulaDetail> formulaDefinitionDetails) {
		this.formulaDefinitionDetails = formulaDefinitionDetails;
	}

	public Long getFormulaDefinitionId() {
		return formulaDefinitionId;
	}

	public void setFormulaDefinitionId(Long formulaDefinitionId) {
		this.formulaDefinitionId = formulaDefinitionId;
	}

	public Boolean getExistedMessageFlg() {
		return existedMessageFlg;
	}

	public void setExistedMessageFlg(Boolean existedMessageFlg) {
		this.existedMessageFlg = existedMessageFlg;
	}

	public List<MessageParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<MessageParameter> parameters) {
		this.parameters = parameters;
	}

	public List<BusinessDetailContent> getContents() {
		return contents;
	}

	public void setContents(List<BusinessDetailContent> contents) {
		this.contents = contents;
	}


}
