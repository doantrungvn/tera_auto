package org.terasoluna.qp.app.sample;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.qp.domain.model.SampleType;

public class SampleForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer columnId;
	
	@NotNull
	private String columnText;
	
	@NumberFormat(style = Style.NUMBER)
	@NotNull
	private Integer columnInteger;
	
	private String columnDate;
	
	private String columnDatetime;
	
	private String columnTime;
	
	private SampleType columnRadio;
	
	private SampleType columnCheckbox;
	
	@Digits(integer = 6, fraction = 2)
	private BigDecimal columnCurrency;
	
	@Digits(integer = 6, fraction = 2)
	private Float columnFloat;
	
	@NotNull
	private Integer columnSelect;
	
	@NotNull
	private Integer columnAutocomplete;
	
	@Digits(integer = 3, fraction = 0)
	private Double columnPercentage;
	
	@Digits(integer = 3, fraction = 2)
	private Double columnPercentageDecimal;

	@Valid
	private SampleChildForm[] sampleChild;

	private MultipartFile columnImage;

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

	public String getColumnDate() {
		return columnDate;
	}

	public void setColumnDate(String columnDate) {
		this.columnDate = columnDate;
	}

	public String getColumnDatetime() {
		return columnDatetime;
	}

	public void setColumnDatetime(String columnDatetime) {
		this.columnDatetime = columnDatetime;
	}

	public String getColumnTime() {
		return columnTime;
	}

	public void setColumnTime(String columnTime) {
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

	public SampleChildForm[] getSampleChild() {
		return sampleChild;
	}

	public void setSampleChild(SampleChildForm[] sampleChild) {
		this.sampleChild = sampleChild;
	}

	public MultipartFile getColumnImage() {
		return columnImage;
	}

	public void setColumnImage(MultipartFile columnImage) {
		this.columnImage = columnImage;
	}

}
