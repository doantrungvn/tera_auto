package org.terasoluna.qp.domain.model;

public class ScreenItemEventMapping {
	private Long screenItemEventMappingId;
	
	private Long screenItemEventId;
	
	private Long beanId;
	
	private String inputBeanCode;
	
	private String outBeanCode;
	
	private String itemCode;
	
	private Integer beanType;
	
	private String parentCodeOutput;
	
	private String parentCodeInput;
	
	public String getParentCodeOutput() {
		return parentCodeOutput;
	}

	public void setParentCodeOutput(String parentCodeOutput) {
		this.parentCodeOutput = parentCodeOutput;
	}

	public String getParentCodeInput() {
		return parentCodeInput;
	}

	public void setParentCodeInput(String parentCodeInput) {
		this.parentCodeInput = parentCodeInput;
	}

	public ScreenItemEventMapping(Long beanId, String itemCode, Integer beanType) {
		this.screenItemEventId = screenItemEventId;
		this.beanId = beanId;
		this.itemCode = itemCode;
		this.beanType = beanType;
	}
	
	public ScreenItemEventMapping() {
		
	}

	public Long getScreenItemEventId() {
		return screenItemEventId;
	}

	public void setScreenItemEventId(Long screenItemEventId) {
		this.screenItemEventId = screenItemEventId;
	}

	public Long getBeanId() {
		return beanId;
	}

	public void setBeanId(Long beanId) {
		this.beanId = beanId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getBeanType() {
		return beanType;
	}

	public void setBeanType(Integer beanType) {
		this.beanType = beanType;
	}

	public Long getScreenItemEventMappingId() {
		return screenItemEventMappingId;
	}

	public void setScreenItemEventMappingId(Long screenItemEventMappingId) {
		this.screenItemEventMappingId = screenItemEventMappingId;
	}

	public String getInputBeanCode() {
		return inputBeanCode;
	}

	public void setInputBeanCode(String inputBeanCode) {
		this.inputBeanCode = inputBeanCode;
	}

	public String getOutBeanCode() {
		return outBeanCode;
	}

	public void setOutBeanCode(String outBeanCode) {
		this.outBeanCode = outBeanCode;
	}

	
}
