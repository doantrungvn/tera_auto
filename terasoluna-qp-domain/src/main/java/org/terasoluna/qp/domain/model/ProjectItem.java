package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class ProjectItem implements Serializable {
	/**
	 * table project_items
	 */
	private static final long serialVersionUID = 1L;
	private long projectItemId;
	private long projectId;
	private String messageCode;
	private String messageString;
	private String style;
	private String hoverStyle;
	private int itemPosition;
	private int itemType;
	private int componentType;
	private String moduleName;
	private long moduleId;
	private String screenName;
	private long screenId;
	
	public long getProjectItemId() {
		return projectItemId;
	}
	public void setProjectItemId(long projectItemId) {
		this.projectItemId = projectItemId;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	public String getMessageString() {
		return messageString;
	}
	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getHoverStyle() {
		return hoverStyle;
	}
	public void setHoverStyle(String hoverStyle) {
		this.hoverStyle = hoverStyle;
	}
	public int getItemPosition() {
		return itemPosition;
	}
	public void setItemPosition(int itemPosition) {
		this.itemPosition = itemPosition;
	}
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	public int getComponentType() {
		return componentType;
	}
	public void setComponentType(int componentType) {
		this.componentType = componentType;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public long getModuleId() {
		return moduleId;
	}
	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public long getScreenId() {
		return screenId;
	}
	public void setScreenId(long screenId) {
		this.screenId = screenId;
	}
}
