package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class EmailContent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long emailContentId;

	private Long emailComponentId;

	private String content;

	public Long getEmailContentId() {
		return emailContentId;
	}

	public void setEmailContentId(Long emailContentId) {
		this.emailContentId = emailContentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getEmailComponentId() {
		return emailComponentId;
	}

	public void setEmailComponentId(Long emailComponentId) {
		this.emailComponentId = emailComponentId;
	}

}
