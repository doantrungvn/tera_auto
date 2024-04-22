package org.terasoluna.qp.domain.service.screendesign;

public class Tab {
	private String title;
	private String areas;
	private String tabCode;
	private String tabDirection;
	
	public String getTitle() {
		if (this.title == null || this.title.equals("null") || this.title.equals("undefined")) {
			return "";
		}
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAreas() {
		if (this.areas == null || this.areas.equals("null") || this.areas.equals("undefined")) {
			return "";
		}
		return areas;
	}
	public void setAreas(String areas) {
		this.areas = areas;
	}
	public String getTabCode() {
		if (this.tabCode == null || this.tabCode.equals("null") || this.tabCode.equals("undefined")) {
			return "";
		}
		return tabCode;
	}
	public void setTabCode(String tabCode) {
		this.tabCode = tabCode;
	}
	public String getTabDirection() {
		if (this.tabDirection == null || this.tabDirection.equals("null") || this.tabDirection.equals("undefined")) {
			return "";
		}
		return tabDirection;
	}
	public void setTabDirection(String tabDirection) {
		this.tabDirection = tabDirection;
	}


}
