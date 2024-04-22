package org.terasoluna.qp.domain.service.businessdesign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ScreenItem;

public class ImpactOfBusinessDesign {
	
	private List<InputBean> lstNewInputBeans = new ArrayList<InputBean>();
	
	private Map<InputBean,InputBean> mapModifedInputBeans = new HashMap<InputBean, InputBean>();
	
	private List<InputBean> lstDeletedInputBeans = new ArrayList<InputBean>();
	
	private List<OutputBean> lstNewOutputBeans = new ArrayList<OutputBean>();
	
	private Map<OutputBean,OutputBean> mapModifedOutputBeans = new HashMap<OutputBean, OutputBean>();
	
	private List<OutputBean> lstDeletedOutputBeans = new ArrayList<OutputBean>();
	
	private List<BusinessDesign> lstUsedCommonBlogics = new ArrayList<BusinessDesign>();
	
	private BusinessDesign currentBusinessDesign = new BusinessDesign();
	
	private BusinessDesign oldBusinessDesign = new BusinessDesign();
	
	private List<BusinessDesign> lstUsedNavigatorBlogics = new ArrayList<BusinessDesign>();
	
	private List<ScreenItem> lstUsedNavigatorScreenItems = new ArrayList<ScreenItem>();

	public List<InputBean> getLstNewInputBeans() {
		return lstNewInputBeans;
	}

	public void setLstNewInputBeans(List<InputBean> lstNewInputBeans) {
		this.lstNewInputBeans = lstNewInputBeans;
	}

	public Map<InputBean, InputBean> getMapModifedInputBeans() {
		return mapModifedInputBeans;
	}

	public void setMapModifedInputBeans(Map<InputBean, InputBean> mapModifedInputBeans) {
		this.mapModifedInputBeans = mapModifedInputBeans;
	}

	public List<InputBean> getLstDeletedInputBeans() {
		return lstDeletedInputBeans;
	}

	public void setLstDeletedInputBeans(List<InputBean> lstDeletedInputBeans) {
		this.lstDeletedInputBeans = lstDeletedInputBeans;
	}

	public List<OutputBean> getLstNewOutputBeans() {
		return lstNewOutputBeans;
	}

	public void setLstNewOutputBeans(List<OutputBean> lstNewOutputBeans) {
		this.lstNewOutputBeans = lstNewOutputBeans;
	}

	public Map<OutputBean, OutputBean> getMapModifedOutputBeans() {
		return mapModifedOutputBeans;
	}

	public void setMapModifedOutputBeans(Map<OutputBean, OutputBean> mapModifedOutputBeans) {
		this.mapModifedOutputBeans = mapModifedOutputBeans;
	}

	public List<OutputBean> getLstDeletedOutputBeans() {
		return lstDeletedOutputBeans;
	}

	public void setLstDeletedOutputBeans(List<OutputBean> lstDeletedOutputBeans) {
		this.lstDeletedOutputBeans = lstDeletedOutputBeans;
	}

	public List<BusinessDesign> getLstUsedCommonBlogics() {
	    return lstUsedCommonBlogics;
    }

	public void setLstUsedCommonBlogics(List<BusinessDesign> lstUsedCommonBlogics) {
	    this.lstUsedCommonBlogics = lstUsedCommonBlogics;
    }

	public BusinessDesign getCurrentBusinessDesign() {
		return currentBusinessDesign;
	}

	public void setCurrentBusinessDesign(BusinessDesign currentBusinessDesign) {
		this.currentBusinessDesign = currentBusinessDesign;
	}

	public BusinessDesign getOldBusinessDesign() {
		return oldBusinessDesign;
	}

	public void setOldBusinessDesign(BusinessDesign oldBusinessDesign) {
		this.oldBusinessDesign = oldBusinessDesign;
	}

	public List<BusinessDesign> getLstUsedNavigatorBlogics() {
	    return lstUsedNavigatorBlogics;
    }

	public void setLstUsedNavigatorBlogics(List<BusinessDesign> lstUsedNavigatorBlogics) {
	    this.lstUsedNavigatorBlogics = lstUsedNavigatorBlogics;
    }

	public List<ScreenItem> getLstUsedNavigatorScreenItems() {
	    return lstUsedNavigatorScreenItems;
    }

	public void setLstUsedNavigatorScreenItems(List<ScreenItem> lstUsedNavigatorScreenItems) {
	    this.lstUsedNavigatorScreenItems = lstUsedNavigatorScreenItems;
    }
	
}
