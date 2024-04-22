package org.terasoluna.qp.domain.model;

import java.util.List;

public class ScreenFormTabGroup {
	private String tabCode;
	
	private List<ScreenFormTabs> screenFormTabs;
	
	private Long screenFormId;
	
	private Integer tabDirection;
	
	private String startHtml;
	
	private String endHtml;
	
	public String getStartHtml() {
		return startHtml;
	}
	public void setStartHtml(String startHtml) {
		this.startHtml = startHtml;
	}
	public String getEndHtml() {
		return endHtml;
	}
	public void setEndHtml(String endHtml) {
		this.endHtml = endHtml;
	}
	public String getTabCode() {
		return tabCode;
	}
	public void setTabCode(String tabCode) {
		this.tabCode = tabCode;
	}

	public Long getScreenFormId() {
		return screenFormId;
	}
	public void setScreenFormId(Long screenFormId) {
		this.screenFormId = screenFormId;
	}
	public List<ScreenFormTabs> getScreenFormTabs() {
		return screenFormTabs;
	}
	public void setScreenFormTabs(List<ScreenFormTabs> screenFormTabs) {
		this.screenFormTabs = screenFormTabs;
	}
	public Integer getTabDirection() {
		return tabDirection;
	}
	public void setTabDirection(Integer tabDirection) {
		this.tabDirection = tabDirection;
	}
	
}
