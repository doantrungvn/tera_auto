package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class LogComponent extends BlogicComponent implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long logComponentId;

	private Integer level;

	private Integer messageType;

	private String messageContent;

	private Long messageFormulaId;

	private List<FormulaDetail> messageFormulaDetails;

	public Long getLogComponentId() {
		return logComponentId;
	}

	public void setLogComponentId(Long logComponentId) {
		this.logComponentId = logComponentId;
	}

	/**
	 * @return the messageType
	 */
	public Integer getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return the messageContent
	 */
	public String getMessageContent() {
		return messageContent;
	}

	/**
	 * @param messageContent the messageContent to set
	 */
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	/**
	 * @return the messageFormulaId
	 */
	public Long getMessageFormulaId() {
		return messageFormulaId;
	}

	/**
	 * @param messageFormulaId the messageFormulaId to set
	 */
	public void setMessageFormulaId(Long messageFormulaId) {
		this.messageFormulaId = messageFormulaId;
	}

	/**
	 * @return the messageFormulaDetails
	 */
	public List<FormulaDetail> getMessageFormulaDetails() {
		return messageFormulaDetails;
	}

	/**
	 * @param messageFormulaDetails the messageFormulaDetails to set
	 */
	public void setMessageFormulaDetails(List<FormulaDetail> messageFormulaDetails) {
		this.messageFormulaDetails = messageFormulaDetails;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}
