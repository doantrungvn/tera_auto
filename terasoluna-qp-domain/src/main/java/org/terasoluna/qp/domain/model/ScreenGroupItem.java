package org.terasoluna.qp.domain.model;

import java.sql.Timestamp;
import java.util.List;

public class ScreenGroupItem {
	private Long groupItemId;
	
	private Long screenAreaId;
	
	private Integer itemSeqNo;
	
	private Integer groupSeqNo;
	
	private String groupName;
	
	private Integer groupType;
	
	private Boolean enableGroup;
	
	private List<ScreenItem> listItemsInGroup;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate; 
	
	private Integer elementStart;
	
	private Integer elementEnd;
	
	private List<ScreenItem> items;
	
	private Integer itemGroupType;
	
	private String styleColRowSpan;
	
	private ScreenArea screenArea;
	
	public Integer getElementStart() {
		return elementStart;
	}

	public void setElementStart(Integer elementStart) {
		this.elementStart = elementStart;
	}

	public Integer getElementEnd() {
		return elementEnd;
	}

	public void setElementEnd(Integer elementEnd) {
		this.elementEnd = elementEnd;
	}

	public List<ScreenItem> getListItemsInGroup() {
		return listItemsInGroup;
	}

	public void setListItemsInGroup(List<ScreenItem> listItemsInGroup) {
		this.listItemsInGroup = listItemsInGroup;
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

	private int totalElement = 0;

	public Long getGroupItemId() {
		return groupItemId;
	}

	public void setGroupItemId(Long groupItemId) {
		this.groupItemId = groupItemId;
	}

	public Long getScreenAreaId() {
		return screenAreaId;
	}

	public void setScreenAreaId(Long screenAreaId) {
		this.screenAreaId = screenAreaId;
	}

	public Integer getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	public Integer getGroupSeqNo() {
		return groupSeqNo;
	}

	public void setGroupSeqNo(Integer groupSeqNo) {
		this.groupSeqNo = groupSeqNo;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public Boolean getEnableGroup() {
		return enableGroup;
	}

	public void setEnableGroup(Boolean enableGroup) {
		this.enableGroup = enableGroup;
	}

	public int getTotalElement() {
		return totalElement;
	}

	public void setTotalElement(int totalElement) {
		this.totalElement = totalElement;
	}

	public List<ScreenItem> getItems() {
		return items;
	}

	public void setItems(List<ScreenItem> items) {
		this.items = items;
	}

	public Integer getItemGroupType() {
		return itemGroupType;
	}

	public void setItemGroupType(Integer itemGroupType) {
		this.itemGroupType = itemGroupType;
	}

	public String getStyleColRowSpan() {
		return styleColRowSpan;
	}

	public void setStyleColRowSpan(String styleColRowSpan) {
		this.styleColRowSpan = styleColRowSpan;
	}

	public ScreenArea getScreenArea() {
		return screenArea;
	}

	public void setScreenArea(ScreenArea screenArea) {
		this.screenArea = screenArea;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if (arg0 instanceof ScreenGroupItem) {
			ScreenGroupItem groupItem = (ScreenGroupItem)arg0;
			return groupItem.getGroupItemId().equals(this.getGroupItemId());
		}
		return false;
	}
}
