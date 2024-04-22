package org.terasoluna.qp.domain.model;

public class ScreenItemAutocompleteInput {
	private Long screenAutocompleteInputId;
	private Long screenItemId;
	private Long inputId;
	private String screenItemCode;
	private Integer logicalDataTypeDepend; 
	private String screenItemCodeDepend;
	private Long screenItemIdDepend;
	private String screenItemCodeBeDepended;
	
	public Long getScreenAutocompleteInputId() {
		return screenAutocompleteInputId;
	}
	public void setScreenAutocompleteInputId(Long screenAutocompleteInputId) {
		this.screenAutocompleteInputId = screenAutocompleteInputId;
	}
	public Long getScreenItemId() {
		return screenItemId;
	}
	public void setScreenItemId(Long screenItemId) {
		this.screenItemId = screenItemId;
	}
	public Long getInputId() {
		return inputId;
	}
	public void setInputId(Long inputId) {
		this.inputId = inputId;
	}
	public String getScreenItemCode() {
		return screenItemCode;
	}
	public void setScreenItemCode(String screenItemCode) {
		this.screenItemCode = screenItemCode;
	}
	public Integer getLogicalDataTypeDepend() {
		return logicalDataTypeDepend;
	}
	public void setLogicalDataTypeDepend(Integer logicalDataTypeDepend) {
		this.logicalDataTypeDepend = logicalDataTypeDepend;
	}
	public String getScreenItemCodeDepend() {
		return screenItemCodeDepend;
	}
	public void setScreenItemCodeDepend(String screenItemCodeDepend) {
		this.screenItemCodeDepend = screenItemCodeDepend;
	}
	public Long getScreenItemIdDepend() {
		return screenItemIdDepend;
	}
	public void setScreenItemIdDepend(Long screenItemIdDepend) {
		this.screenItemIdDepend = screenItemIdDepend;
	}
	public String getScreenItemCodeBeDepended() {
		return screenItemCodeBeDepended;
	}
	public void setScreenItemCodeBeDepended(String screenItemCodeBeDepended) {
		this.screenItemCodeBeDepended = screenItemCodeBeDepended;
	}
	
}
