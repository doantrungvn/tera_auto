package org.terasoluna.qp.app.menudesign;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

public class MenuDesignForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long menuId;

	private int menuType;

	private Long projectId;

	private String headerMenuName;

	private Long languageId;

	private String actionAutocomplete;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private Timestamp systemTime;

	private int updateType;

	private Long screenId;

	private String screenIdAutocomplete;

	private Long headerMenuAction;
	
	private String headerMenuActionAutocomplete;

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	@Valid
	private List<MenuDesignItemForm> listMenuDesignItem;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public int getMenuType() {
		return menuType;
	}

	public void setMenuType(int menuType) {
		this.menuType = menuType;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Timestamp systemTime) {
		this.systemTime = systemTime;
	}

	public List<MenuDesignItemForm> getListMenuDesignItem() {
		return listMenuDesignItem;
	}

	public void setListMenuDesignItem(
			List<MenuDesignItemForm> listMenuDesignItem) {
		this.listMenuDesignItem = listMenuDesignItem;
	}

	public String getActionAutocomplete() {
		return actionAutocomplete;
	}

	public void setActionAutocomplete(String actionAutocomplete) {
		this.actionAutocomplete = actionAutocomplete;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getUpdateType() {
		return updateType;
	}

	public void setUpdateType(int updateType) {
		this.updateType = updateType;
	}

	public String getScreenIdAutocomplete() {
		return screenIdAutocomplete;
	}

	public void setScreenIdAutocomplete(String screenIdAutocomplete) {
		this.screenIdAutocomplete = screenIdAutocomplete;
	}

	public String getHeaderMenuName() {
		return headerMenuName;
	}

	public void setHeaderMenuName(String headerMenuName) {
		this.headerMenuName = headerMenuName;
	}

	public Long getHeaderMenuAction() {
		return headerMenuAction;
	}

	public void setHeaderMenuAction(Long headerMenuAction) {
		this.headerMenuAction = headerMenuAction;
	}

	public String getHeaderMenuActionAutocomplete() {
		return headerMenuActionAutocomplete;
	}

	public void setHeaderMenuActionAutocomplete(
			String headerMenuActionAutocomplete) {
		this.headerMenuActionAutocomplete = headerMenuActionAutocomplete;
	}
}
