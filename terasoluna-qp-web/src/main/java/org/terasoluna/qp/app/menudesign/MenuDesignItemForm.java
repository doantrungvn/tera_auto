package org.terasoluna.qp.app.menudesign;

import java.io.Serializable;

public class MenuDesignItemForm implements Serializable, Comparable<MenuDesignItemForm>{

	private static final long serialVersionUID = 1L;
	
	private String menuItemId;
	
	private Long menuId;
	
	private String menuName;
	
	private int menuItemType;
	
	private String actionUrlCode;
	
	private String screenIdAutocomplete;
	
	private int itemSeqNo;
	
	private Long parentId;
	
	private Long screenId;
	
	private String groupId;
	
	private String tableIndex;
	
	private String parentMenuItemId;
	
	private String jsonMenuDesignItem;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public int getMenuItemType() {
		return menuItemType;
	}

	public void setMenuItemType(int menuItemType) {
		this.menuItemType = menuItemType;
	}

	public String getActionUrlCode() {
		return actionUrlCode;
	}

	public void setActionUrlCode(String actionUrlCode) {
		this.actionUrlCode = actionUrlCode;
	}

	public int getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(int itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTableIndex() {
		return tableIndex;
	}

	public void setTableIndex(String tableIndex) {
		this.tableIndex = tableIndex;
	}

	public String getJsonMenuDesignItem() {
		return jsonMenuDesignItem;
	}

	public void setJsonMenuDesignItem(String jsonMenuDesignItem) {
		this.jsonMenuDesignItem = jsonMenuDesignItem;
	}

	public String getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(String menuItemId) {
		this.menuItemId = menuItemId;
	}

	public String getParentMenuItemId() {
		return parentMenuItemId;
	}

	public void setParentMenuItemId(String parentMenuItemId) {
		this.parentMenuItemId = parentMenuItemId;
	}

	@Override
	public int compareTo(MenuDesignItemForm menuDesignItemForm) {
		int compareage=((MenuDesignItemForm)menuDesignItemForm).getItemSeqNo();
        /* For Ascending order*/
        return this.itemSeqNo-compareage;
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
}
