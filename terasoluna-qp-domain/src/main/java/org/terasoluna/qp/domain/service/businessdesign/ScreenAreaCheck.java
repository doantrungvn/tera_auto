package org.terasoluna.qp.domain.service.businessdesign;

import java.util.ArrayList;
import java.util.List;
import org.terasoluna.qp.domain.model.ItemValidation;

public class ScreenAreaCheck {
	private List<ItemValidation> lstItems = new ArrayList<ItemValidation>();

	private Long screenAreaId ;
	private String screenAreaName;
	public List<ItemValidation> getLstItems() {
		return lstItems;
	}

	public void setLstItems(List<ItemValidation> lstItems) {
		this.lstItems = lstItems;
	}

	public String getScreenAreaName() {
		return screenAreaName;
	}

	public void setScreenAreaName(String screenAreaName) {
		this.screenAreaName = screenAreaName;
	}

	public Long getScreenAreaId() {
		return screenAreaId;
	}

	public void setScreenAreaId(Long screenAreaId) {
		this.screenAreaId = screenAreaId;
	}
}
