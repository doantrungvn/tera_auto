package org.terasoluna.qp.app.screendesign;

import java.io.Serializable;
import java.util.List;

import org.terasoluna.qp.domain.model.ScreenItemStatus;

public class ScreenItemStatusForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long itemId;
	
	private String itemName;
	
	private String itemCode;
	
	private Integer itemType;
	
	private Long screenFormId;
	
	private Long screenAreaId;
	
	private String jsonOutputBean;
	
	private String jsonScreenItem;
	
	private List<ScreenItemStatusForm> screenItemStatusForms;
	
	private List<ScreenItemStatus> screenItemStatuses;
	
	private List<FormularForm> formulars;

	public List<ScreenItemStatus> getScreenItemStatuses() {
		return screenItemStatuses;
	}

	public void setScreenItemStatuses(List<ScreenItemStatus> screenItemStatuses) {
		this.screenItemStatuses = screenItemStatuses;
	}

	public List<FormularForm> getFormulars() {
		return formulars;
	}

	public void setFormulars(List<FormularForm> formulars) {
		this.formulars = formulars;
	}

	public Long getScreenFormId() {
		return screenFormId;
	}

	public void setScreenFormId(Long screenFormId) {
		this.screenFormId = screenFormId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public String getJsonOutputBean() {
		return jsonOutputBean;
	}

	public void setJsonOutputBean(String jsonOutputBean) {
		this.jsonOutputBean = jsonOutputBean;
	}

	public String getJsonScreenItem() {
		return jsonScreenItem;
	}

	public void setJsonScreenItem(String jsonScreenItem) {
		this.jsonScreenItem = jsonScreenItem;
	}

	public List<ScreenItemStatusForm> getScreenItemStatusForms() {
		return screenItemStatusForms;
	}

	public void setScreenItemStatusForms(List<ScreenItemStatusForm> screenItemStatusForms) {
		this.screenItemStatusForms = screenItemStatusForms;
	}

	public Long getScreenAreaId() {
		return screenAreaId;
	}

	public void setScreenAreaId(Long screenAreaId) {
		this.screenAreaId = screenAreaId;
	}
	
}
