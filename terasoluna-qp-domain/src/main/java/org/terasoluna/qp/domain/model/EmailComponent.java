package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmailComponent extends BlogicComponent implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long emailComponentId;

	private Integer priorityType;

	private Integer subjectType;

	private String subjectContent;

	private Long subjectFormulaId;

	private List<FormulaDetail> subjectFormulaDetails;

	private List<EmailRecipient> emailRecipients = new ArrayList<EmailRecipient>();
	
	private EmailContent emailContent;

	public Long getEmailComponentId() {
		return emailComponentId;
	}

	public void setEmailComponentId(Long fileoperationComponentId) {
		this.emailComponentId = fileoperationComponentId;
	}

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	public String getSubjectContent() {
		return subjectContent;
	}

	public void setSubjectContent(String subjectContent) {
		this.subjectContent = subjectContent;
	}

	public Long getSubjectFormulaId() {
		return subjectFormulaId;
	}

	public void setSubjectFormulaId(Long subjectFormulaId) {
		this.subjectFormulaId = subjectFormulaId;
	}

	public List<FormulaDetail> getSubjectFormulaDetails() {
		return subjectFormulaDetails;
	}

	public void setSubjectFormulaDetails(List<FormulaDetail> subjectFormulaDetails) {
		this.subjectFormulaDetails = subjectFormulaDetails;
	}

	public Integer getPriorityType() {
		return priorityType;
	}

	public void setPriorityType(Integer priorityType) {
		this.priorityType = priorityType;
	}

	public List<EmailRecipient> getEmailRecipients() {
		return emailRecipients;
	}

	public void setEmailRecipients(List<EmailRecipient> emailRecipients) {
		this.emailRecipients = emailRecipients;
	}

	public EmailContent getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(EmailContent emailContent) {
		this.emailContent = emailContent;
	}

}
