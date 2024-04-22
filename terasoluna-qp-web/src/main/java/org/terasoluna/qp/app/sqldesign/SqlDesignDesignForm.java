package org.terasoluna.qp.app.sqldesign;

import java.io.Serializable;

import javax.validation.Valid;

public class SqlDesignDesignForm implements Serializable{

	private static final long serialVersionUID = 4081490716607523111L;

	@Valid
	private SqlDesignForm sqlDesignForm;
	
	@Valid
	private SelectForm[] selectForm;
	
	@Valid
	private FromForm[] fromForm;
	
	@Valid
	private WhereForm[] whereForm;
	
	@Valid
	private GroupByForm[] groupByForm;
	
	@Valid
	private HavingForm[] havingForm;
	
	@Valid
	private OrderByForm[] orderByForm;
	
	@Valid
	private ValueForm[] valueForm;
	
	@Valid
	private InputForm[] inputForm;
	
	@Valid
	private OutputForm[] outputForm;
	
	@Valid
	private IntoForm intoForm;
	
	private String activeTab;
	
	private String mode;
	
	private Integer designStatus;
	
	private Boolean actionDelete;
	
	private String openOwner;
	
	private Boolean showImpactFlag;
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public SqlDesignDesignForm(){
		this.sqlDesignForm = new SqlDesignForm();
	}
	
	public SelectForm[] getSelectForm() {
		return selectForm;
	}

	public void setSelectForm(SelectForm[] selectedColumns) {
		this.selectForm = selectedColumns;
	}

	public FromForm[] getFromForm() {
		return fromForm;
	}

	public void setFromForm(FromForm[] fromForm) {
		this.fromForm = fromForm;
	}

	public WhereForm[] getWhereForm() {
		return whereForm;
	}

	public void setWhereForm(WhereForm[] whereForm) {
		this.whereForm = whereForm;
	}

	public GroupByForm[] getGroupByForm() {
		return groupByForm;
	}

	public void setGroupByForm(GroupByForm[] groupByForm) {
		this.groupByForm = groupByForm;
	}

	public HavingForm[] getHavingForm() {
		return havingForm;
	}

	public void setHavingForm(HavingForm[] havingForm) {
		this.havingForm = havingForm;
	}

	public OrderByForm[] getOrderByForm() {
		return orderByForm;
	}

	public void setOrderByForm(OrderByForm[] orderByForm) {
		this.orderByForm = orderByForm;
	}

	public SqlDesignForm getSqlDesignForm() {
		return sqlDesignForm;
	}

	public void setSqlDesignForm(SqlDesignForm sqlDesignForm) {
		this.sqlDesignForm = sqlDesignForm;
	}

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	public InputForm[] getInputForm() {
		return inputForm;
	}

	public void setInputForm(InputForm[] inputForm) {
		this.inputForm = inputForm;
	}

	public OutputForm[] getOutputForm() {
		return outputForm;
	}

	public void setOutputForm(OutputForm[] outputForm) {
		this.outputForm = outputForm;
	}

	public ValueForm[] getValueForm() {
		return valueForm;
	}

	public void setValueForm(ValueForm[] valueForm) {
		this.valueForm = valueForm;
	}

	public IntoForm getIntoForm() {
		return intoForm;
	}

	public void setIntoForm(IntoForm intoForm) {
		this.intoForm = intoForm;
	}

	public Boolean getActionDelete() {
		return actionDelete;
	}

	public void setActionDelete(Boolean actionDelete) {
		this.actionDelete = actionDelete;
	}

	public String getOpenOwner() {
		return openOwner;
	}

	public void setOpenOwner(String openOwner) {
		this.openOwner = openOwner;
	}

	public Integer getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(Integer designStatus) {
		this.designStatus = designStatus;
	}

	public Boolean getShowImpactFlag() {
		return showImpactFlag;
	}

	public void setShowImpactFlag(Boolean showImpactFlag) {
		this.showImpactFlag = showImpactFlag;
	}
	
}
