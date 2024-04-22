package org.terasoluna.qp.app.styledesign;

import java.io.Serializable;
import java.util.Map;

public class StyleDesignForm implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private Long projectId;
	private Map<String, String> mapTheme;
	private Long accountId;
	private int projectStatus;
	private String logo;
	private String backGroundColor;
	private String sizeOption;
	
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getBackGroundColor() {
		return backGroundColor;
	}
	public void setBackGroundColor(String backGroundColor) {
		this.backGroundColor = backGroundColor;
	}
	public Map<String, String> getMapTheme() {
		return mapTheme;
	}
	public void setMapTheme(Map<String, String> mapTheme) {
		this.mapTheme = mapTheme;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public int getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}
	public String getSizeOption() {
		return sizeOption;
	}
	public void setSizeOption(String sizeOption) {
		this.sizeOption = sizeOption;
	}
	
}
