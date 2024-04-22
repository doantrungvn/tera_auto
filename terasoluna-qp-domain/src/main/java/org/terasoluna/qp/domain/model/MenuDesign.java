package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class MenuDesign implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long menuId;

	private int menuType;

	private Long projectId;

	private Long languageId;

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

	private String headerMenuName;
	
	private String messageCode;
	
	private String urlMainAction;
	private String urlRoot;
	
	private String moduleCode;
	private List<MenuDesignItem> listMenuDesignItem;
	
	private Boolean genOneTime;
	
	private String urlHomePage;

	public MenuDesign() {
		
	}
	
	public MenuDesign(Long projectId, Long languageId) {
		setProjectId(projectId);
		setLanguageId(languageId);
	}
	
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

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
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

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Timestamp getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Timestamp systemTime) {
		this.systemTime = systemTime;
	}

	public List<MenuDesignItem> getListMenuDesignItem() {
		return listMenuDesignItem;
	}

	public void setListMenuDesignItem(List<MenuDesignItem> listMenuDesignItem) {
		this.listMenuDesignItem = listMenuDesignItem;
	}

	public int getUpdateType() {
		return updateType;
	}

	public void setUpdateType(int updateType) {
		this.updateType = updateType;
	}

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
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

	public String getUrlRoot() {
		return urlRoot;
	}

	public void setUrlRoot(String urlRoot) {
		this.urlRoot = urlRoot;
	}

	public String getUrlMainAction() {
		return urlMainAction;
	}

	public void setUrlMainAction(String urlMainAction) {
		this.urlMainAction = urlMainAction;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public Boolean getGenOneTime() {
		return genOneTime;
	}

	public void setGenOneTime(Boolean genOneTime) {
		this.genOneTime = genOneTime;
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

	public String getUrlHomePage() {
		return urlHomePage;
	}

	public void setUrlHomePage(String urlHomePage) {
		this.urlHomePage = urlHomePage;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
}
