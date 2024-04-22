package org.terasoluna.qp.domain.service.businessdesign;

import java.util.ArrayList;
import java.util.List;

import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.service.impactchange.ImpactChangeDesign;

public class ImpactChangeOfStandardBlogic extends ImpactChangeDesign {

	private List<BusinessDesign> lstUsedBlogics = new ArrayList<BusinessDesign>();

	private List<ScreenItem> lstUsedNavigatorScreenItems = new ArrayList<ScreenItem>();

	public List<BusinessDesign> getLstUsedBlogics() {
		return lstUsedBlogics;
	}

	public void setLstUsedBlogics(List<BusinessDesign> lstUsedBlogics) {
		this.lstUsedBlogics = lstUsedBlogics;
	}

	public List<ScreenItem> getLstUsedNavigatorScreenItems() {
		return lstUsedNavigatorScreenItems;
	}

	public void setLstUsedNavigatorScreenItems(List<ScreenItem> lstUsedNavigatorScreenItems) {
		this.lstUsedNavigatorScreenItems = lstUsedNavigatorScreenItems;
	}
	
}
