package org.terasoluna.qp.app.autocomplete;

import java.io.Serializable;

import javax.validation.Valid;
import org.terasoluna.qp.app.sqldesign.FromForm;
import org.terasoluna.qp.app.sqldesign.GroupByForm;
import org.terasoluna.qp.app.sqldesign.HavingForm;
import org.terasoluna.qp.app.sqldesign.InputForm;
import org.terasoluna.qp.app.sqldesign.OrderByForm;
import org.terasoluna.qp.app.sqldesign.SelectForm;
import org.terasoluna.qp.app.sqldesign.SqlDesignForm;
import org.terasoluna.qp.app.sqldesign.WhereForm;

public class AutocompleteDesignForm implements Serializable{

	private static final long serialVersionUID = 4747509357559454364L;
	
	@Valid
	private AutocompleteForm autocompleteForm;
	
	private SqlDesignForm sqlDesignForm;
	
	@Valid
	private SelectForm[] selectedColumns;
	
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
	
	private InputForm[] inputForm;
	
	private OutputForm outputForm;
	
	private String mode;

	private String activeTab;
	
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

	public AutocompleteDesignForm(){
		this.autocompleteForm = new AutocompleteForm();
	}
	
	public AutocompleteForm getAutocompleteForm() {
		return autocompleteForm;
	}

	public void setAutocompleteForm(AutocompleteForm autocompleteForm) {
		this.autocompleteForm = autocompleteForm;
	}

	public SelectForm[] getSelectedColumns() {
		return selectedColumns;
	}

	public void setSelectedColumns(SelectForm[] selectedColumns) {
		this.selectedColumns = selectedColumns;
	}

	public SelectForm[] getSelectForm() {
		return selectForm;
	}

	public void setSelectForm(SelectForm[] selectForm) {
		this.selectForm = selectForm;
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

	public InputForm[] getInputForm() {
		return inputForm;
	}

	public void setInputForm(InputForm[] inputForm) {
		this.inputForm = inputForm;
	}

	public OutputForm getOutputForm() {
		return outputForm;
	}

	public void setOutputForm(OutputForm outputForm) {
		this.outputForm = outputForm;
	}

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	public SqlDesignForm getSqlDesignForm() {
		return sqlDesignForm;
	}

	public void setSqlDesignForm(SqlDesignForm sqlDesignForm) {
		this.sqlDesignForm = sqlDesignForm;
	}

	public Boolean getActionDelete() {
		return actionDelete;
	}

	public void setActionDelete(Boolean actionDelete) {
		this.actionDelete = actionDelete;
	}

	public Integer getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(Integer designStatus) {
		this.designStatus = designStatus;
	}

	public String getOpenOwner() {
		return openOwner;
	}

	public void setOpenOwner(String openOwner) {
		this.openOwner = openOwner;
	}

	public Boolean getShowImpactFlag() {
		return showImpactFlag;
	}

	public void setShowImpactFlag(Boolean showImpactFlag) {
		this.showImpactFlag = showImpactFlag;
	}
}
