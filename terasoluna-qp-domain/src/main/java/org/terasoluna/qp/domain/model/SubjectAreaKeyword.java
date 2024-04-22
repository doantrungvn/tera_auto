package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class SubjectAreaKeyword implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long keywordId;
	
	private String keyword;
	
	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the keywordId
	 */
	public Long getKeywordId() {
		return keywordId;
	}

	/**
	 * @param keywordId the keywordId to set
	 */
	public void setKeywordId(Long keywordId) {
		this.keywordId = keywordId;
	}
}
