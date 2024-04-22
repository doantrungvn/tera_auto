package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.Date;

public class SampleChild implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer columnId;
	private String columnText;
	private SampleType columnAutocomplete;
	private Date columnDatetime;	
	private Sample sample;
	private Integer columnInteger;
	public Integer getColumnId() {
		return columnId;
	}
	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}
	public String getColumnText() {
		return columnText;
	}
	public void setColumnText(String columnText) {
		this.columnText = columnText;
	}
	public SampleType getColumnAutocomplete() {
		return columnAutocomplete;
	}
	public void setColumnAutocomplete(SampleType columnAutocomplete) {
		this.columnAutocomplete = columnAutocomplete;
	}
	public Date getColumnDatetime() {
		return columnDatetime;
	}
	public void setColumnDatetime(Date columnDatetime) {
		this.columnDatetime = columnDatetime;
	}
	public Sample getSample() {
		return sample;
	}
	public void setSample(Sample sample) {
		this.sample = sample;
	}
	public Integer getColumnInteger() {
		return columnInteger;
	}
	public void setColumnInteger(Integer columnInteger) {
		this.columnInteger = columnInteger;
	}
	
}
