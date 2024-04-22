package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Sample implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer columnId;
	private String columnText;
	private Integer columnInteger;
	private Date columnDate;
	private Date columnDatetime;
	private Date columnTime;
	private SampleType columnRadio;
	private SampleType columnCheckbox;
	private BigDecimal columnCurrency;
	private byte[] columnImage = new byte[0];
	private Float columnFloat;
	private Integer columnSelect;
	private Integer columnAutocomplete;
	private Double columnPercentage;
	private Double columnPercentageDecimal;
	
	private SampleChild[] sampleChild;	
	private String base64Image;
	public String getBase64Image() {
		String imageBase64 = new sun.misc.BASE64Encoder().encode(columnImage);
		return imageBase64;
	}
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
	public Integer getColumnInteger() {
		return columnInteger;
	}
	public void setColumnInteger(Integer columnInteger) {
		this.columnInteger = columnInteger;
	}
	public Date getColumnDate() {
		return columnDate;
	}
	public void setColumnDate(Date columnDate) {
		this.columnDate = columnDate;
	}
	public Date getColumnDatetime() {
		return columnDatetime;
	}
	public void setColumnDatetime(Date columnDatetime) {
		this.columnDatetime = columnDatetime;
	}
	public Date getColumnTime() {
		return columnTime;
	}
	public void setColumnTime(Date columnTime) {
		this.columnTime = columnTime;
	}
	public SampleType getColumnRadio() {
		return columnRadio;
	}
	public void setColumnRadio(SampleType columnRadio) {
		this.columnRadio = columnRadio;
	}
	public SampleType getColumnCheckbox() {
		return columnCheckbox;
	}
	public void setColumnCheckbox(SampleType columnCheckbox) {
		this.columnCheckbox = columnCheckbox;
	}
	public BigDecimal getColumnCurrency() {
		return columnCurrency;
	}
	public void setColumnCurrency(BigDecimal columnCurrency) {
		this.columnCurrency = columnCurrency;
	}
	public byte[] getColumnImage() {
		return columnImage;
	}
	public void setColumnImage(byte[] columnImage) {
		this.columnImage = columnImage;
	}
	public Float getColumnFloat() {
		return columnFloat;
	}
	public void setColumnFloat(Float columnFloat) {
		this.columnFloat = columnFloat;
	}
	public Integer getColumnSelect() {
		return columnSelect;
	}
	public void setColumnSelect(Integer columnSelect) {
		this.columnSelect = columnSelect;
	}
	public Integer getColumnAutocomplete() {
		return columnAutocomplete;
	}
	public void setColumnAutocomplete(Integer columnAutocomplete) {
		this.columnAutocomplete = columnAutocomplete;
	}
	public Double getColumnPercentage() {
		return columnPercentage;
	}
	public void setColumnPercentage(Double columnPercentage) {
		this.columnPercentage = columnPercentage;
	}
	public Double getColumnPercentageDecimal() {
		return columnPercentageDecimal;
	}
	public void setColumnPercentageDecimal(Double columnPercentageDecimal) {
		this.columnPercentageDecimal = columnPercentageDecimal;
	}
	public SampleChild[] getSampleChild() {
		return sampleChild;
	}
	public void setSampleChild(SampleChild[] sampleChild) {
		this.sampleChild = sampleChild;
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	
}
