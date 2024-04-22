package org.terasoluna.qp.app.viewdesign;

import java.io.Serializable;

import javax.validation.Valid;

import org.terasoluna.qp.app.sqldesign.FromForm;
import org.terasoluna.qp.app.sqldesign.GroupByForm;
import org.terasoluna.qp.app.sqldesign.HavingForm;
import org.terasoluna.qp.app.sqldesign.InputForm;
import org.terasoluna.qp.app.sqldesign.IntoForm;
import org.terasoluna.qp.app.sqldesign.OrderByForm;
import org.terasoluna.qp.app.sqldesign.OutputForm;
import org.terasoluna.qp.app.sqldesign.SelectForm;
import org.terasoluna.qp.app.sqldesign.ValueForm;
import org.terasoluna.qp.app.sqldesign.WhereForm;

public class ViewDesignDesignForm implements Serializable{

	private static final long serialVersionUID = 4081490716607523111L;

	@Valid
	private ViewDesignForm sqlDesignForm;
	
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
	
	private Boolean actionDelete;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public ViewDesignDesignForm(){
		this.sqlDesignForm = new ViewDesignForm();
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

	public ViewDesignForm getSqlDesignForm() {
		return sqlDesignForm;
	}

	public void setSqlDesignForm(ViewDesignForm sqlDesignForm) {
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
	
}
