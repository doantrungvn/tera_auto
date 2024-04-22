package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class ScreenAreaEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long screenAreaEventId;
	private String ifRequire;
	private String thenMustRequire;
	private Long screenAreaId;
	public Long getScreenAreaEventId() {
		return screenAreaEventId;
	}
	public void setScreenAreaEventId(Long screenAreaEventId) {
		this.screenAreaEventId = screenAreaEventId;
	}
	public String getIfRequire() {
		return ifRequire;
	}
	public void setIfRequire(String ifRequire) {
		this.ifRequire = ifRequire;
	}
	public String getThenMustRequire() {
		return thenMustRequire;
	}
	public void setThenMustRequire(String thenMustRequire) {
		this.thenMustRequire = thenMustRequire;
	}
	public Long getScreenAreaId() {
		return screenAreaId;
	}
	public void setScreenAreaId(Long screenAreaId) {
		this.screenAreaId = screenAreaId;
	}
	
	
}
