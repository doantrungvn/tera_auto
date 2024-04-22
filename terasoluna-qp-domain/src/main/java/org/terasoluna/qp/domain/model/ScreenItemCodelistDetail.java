package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class ScreenItemCodelistDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long screenItemId;
	private String codelistName;
	private String codelistVal;
	private String otherName;
	public String getCodelistName() {
		return codelistName;
	}
	public void setCodelistName(String codelistName) {
		this.codelistName = codelistName;
	}
	public String getCodelistVal() {
		return codelistVal;
	}
	public void setCodelistVal(String codelistVal) {
		this.codelistVal = codelistVal;
	}
	public Long getScreenItemId() {
		return screenItemId;
	}
	public void setScreenItemId(Long screenItemId) {
		this.screenItemId = screenItemId;
	}
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
}
