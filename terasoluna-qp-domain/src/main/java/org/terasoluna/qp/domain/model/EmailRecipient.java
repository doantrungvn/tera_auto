package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class EmailRecipient implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long emailRecipientId;

	private Long emailComponentId;

	private Integer type;

	private Integer recipientType;

	private String recipientContent;

	private Long recipientFormulaId;

	private List<FormulaDetail> recipientFormulaDetails;

	public Long getEmailRecipientId() {
		return emailRecipientId;
	}

	public void setEmailRecipientId(Long emailRecipientId) {
		this.emailRecipientId = emailRecipientId;
	}

	public Long getEmailComponentId() {
		return emailComponentId;
	}

	public void setEmailComponentId(Long emailComponentId) {
		this.emailComponentId = emailComponentId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRecipientContent() {
		return recipientContent;
	}

	public void setRecipientContent(String recipientContent) {
		this.recipientContent = recipientContent;
	}

	public Long getRecipientFormulaId() {
		return recipientFormulaId;
	}

	public void setRecipientFormulaId(Long recipientFormulaId) {
		this.recipientFormulaId = recipientFormulaId;
	}

	public List<FormulaDetail> getRecipientFormulaDetails() {
		return recipientFormulaDetails;
	}

	public void setRecipientFormulaDetails(
			List<FormulaDetail> recipientFormulaDetails) {
		this.recipientFormulaDetails = recipientFormulaDetails;
	}

	public Integer getRecipientType() {
		return recipientType;
	}

	public void setRecipientType(Integer recipientType) {
		this.recipientType = recipientType;
	}
	
	
}
