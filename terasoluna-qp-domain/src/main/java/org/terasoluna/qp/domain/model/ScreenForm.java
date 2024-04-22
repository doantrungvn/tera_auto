package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class ScreenForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long screenFormId;
	
	private String formCode;
	
	private Integer enctypeType;
	
	private Integer methodType;
	
	private Integer formSeqNo;
	
	private Integer formType;
	
	private Long screenId;
	
	private Integer formCoverageFlg;
	
	List<ScreenArea> listScreenAreas;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate; 
	
	private List<ScreenArea> areas;
	
	private String formActionName;
	
	private Long screenFormIdStore;
	
	private List<ScreenItem> screenItems;
	
	private List<InputBean> inputBeans; 
	
	private String tabValue;
	
	private ScreenFormTabs[] screenFormTabs;

	private List<ScreenFormTabGroup> screenFormTabGroups;
	
	private BusinessDesign businessDesign;
	
	private List<FormulaDefinition> formulaDefinitions;
	
	public List<ScreenArea> getListScreenAreas() {
		return listScreenAreas;
	}

	public void setListScreenAreas(List<ScreenArea> listScreenAreas) {
		this.listScreenAreas = listScreenAreas;
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

	public Long getScreenFormId() {
		return screenFormId;
	}

	public void setScreenFormId(Long screenFormId) {
		this.screenFormId = screenFormId;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public Integer getEnctypeType() {
		return enctypeType;
	}

	public void setEnctypeType(Integer enctypeType) {
		this.enctypeType = enctypeType;
	}

	public Integer getMethodType() {
		return methodType;
	}

	public void setMethodType(Integer methodType) {
		this.methodType = methodType;
	}

	public Integer getFormSeqNo() {
		return formSeqNo;
	}

	public void setFormSeqNo(Integer formSeqNo) {
		this.formSeqNo = formSeqNo;
	}

	public Integer getFormType() {
		return formType;
	}

	public void setFormType(Integer formType) {
		this.formType = formType;
	}

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public Integer getFormCoverageFlg() {
		return formCoverageFlg;
	}

	public void setFormCoverageFlg(Integer formCoverageFlg) {
		this.formCoverageFlg = formCoverageFlg;
	}

	public List<ScreenArea> getAreas() {
		return areas;
	}

	public void setAreas(List<ScreenArea> areas) {
		this.areas = areas;
	}

	public String getFormActionName() {
		return formActionName;
	}

	public void setFormActionName(String formActionName) {
		this.formActionName = formActionName;
	}

	public Long getScreenFormIdStore() {
		return screenFormIdStore;
	}

	public void setScreenFormIdStore(Long screenFormIdStore) {
		this.screenFormIdStore = screenFormIdStore;
	}

	public List<ScreenItem> getScreenItems() {
		return screenItems;
	}

	public void setScreenItems(List<ScreenItem> screenItems) {
		this.screenItems = screenItems;
	}

	public List<InputBean> getInputBeans() {
		return inputBeans;
	}

	public void setInputBeans(List<InputBean> inputBeans) {
		this.inputBeans = inputBeans;
	}

	public String getTabValue() {
		return tabValue;
	}

	public void setTabValue(String tabValue) {
		this.tabValue = tabValue;
	}

	public ScreenFormTabs[] getScreenFormTabs() {
		return screenFormTabs;
	}

	public void setScreenFormTabs(ScreenFormTabs[] screenFormTabs) {
		this.screenFormTabs = screenFormTabs;
	}

	public List<ScreenFormTabGroup> getScreenFormTabGroups() {
		return screenFormTabGroups;
	}

	public void setScreenFormTabGroups(List<ScreenFormTabGroup> screenFormTabGroups) {
		this.screenFormTabGroups = screenFormTabGroups;
	}

	public BusinessDesign getBusinessDesign() {
		return businessDesign;
	}

	public void setBusinessDesign(BusinessDesign businessDesign) {
		this.businessDesign = businessDesign;
	}

	public List<FormulaDefinition> getFormulaDefinition() {
		return formulaDefinitions;
	}

	public void setFormulaDefinition(List<FormulaDefinition> formulaDefinitions) {
		this.formulaDefinitions = formulaDefinitions;
	}
}
