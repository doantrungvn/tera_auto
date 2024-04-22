package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class ConsistencyValidationModel implements Serializable {

	private static final long serialVersionUID = -3395851571319068166L;
	
	private String groupName;
	private String itemName;
	private int groupType;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getGroupType() {
		return groupType;
	}
	public void setGroupType(int groupType) {
		this.groupType = groupType;
	}
}
