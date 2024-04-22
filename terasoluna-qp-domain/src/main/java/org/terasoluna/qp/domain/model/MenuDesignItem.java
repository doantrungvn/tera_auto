/**
 * 
 */
package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author datld
 *
 */
public class MenuDesignItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String menuItemId;
	
	private Long menuId;
	
	private String menuName;
	
	private int menuItemType;
	
	private String actionUrlCode;
	
	private String screenIdAutocomplete;
	
	private int itemSeqNo;
	
	private Long parentId;
	
	private String groupId;
	
	private Long screenId;
	
	private Long headerMenuAction;
	
	private String headerMenuActionAutocomplete;
	
	private String parentMenuItemId;
	
	private String jsonMenuDesignItem;
	
	private List<MenuDesignItem> listChild;
	
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

	private String tableIndex;

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

	public List<MenuDesignItem> getListChild() {
		return listChild;
	}

	public void setListChild(List<MenuDesignItem> listChild) {
		this.listChild = listChild;
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
