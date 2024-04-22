package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class FeedbackComponent extends BlogicComponent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long feedbackComponentId;

	private String messageCode = "";

	private String messageCodeAutocomplete = "";

	private String type = "";

	private List<MessageParameter> messageParameter;

	private Boolean existedMessageFlg = true;

	public Long getFeedbackComponentId() {
		return feedbackComponentId;
	}

	public void setFeedbackComponentId(Long feedbackComponentId) {
		this.feedbackComponentId = feedbackComponentId;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessageCodeAutocomplete() {
		return messageCodeAutocomplete;
	}

	public void setMessageCodeAutocomplete(String messageCodeAutocomplete) {
		this.messageCodeAutocomplete = messageCodeAutocomplete;
	}

	public List<MessageParameter> getMessageParameter() {
		return messageParameter;
	}

	public void setMessageParameter(List<MessageParameter> messageParameter) {
		this.messageParameter = messageParameter;
	}

	public Boolean getExistedMessageFlg() {
		return existedMessageFlg;
	}

	public void setExistedMessageFlg(Boolean existedMessageFlg) {
		this.existedMessageFlg = existedMessageFlg;
	}

}
