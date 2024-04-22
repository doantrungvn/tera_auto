package org.terasoluna.qp.app.sample;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.terasoluna.qp.domain.model.Sample;
import org.terasoluna.qp.domain.model.SampleType;

public class SampleChildForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer columnId;
	@NotNull
	private String columnText;
	private SampleType columnAutocomplete;
	private String columnDatetime;
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
	public String getColumnDatetime() {
		return columnDatetime;
	}
	public void setColumnDatetime(String columnDatetime) {
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
