package org.terasoluna.qp.domain.repository.sample;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SampleCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer columnId;
	private String columnText;	
	private Integer columnInteger;
	private Date columnDateFrom;
	private Date columnDateTo;
	
	private Date columnDatetimeFrom;
	private Date columnDatetimeTo;
	
	private Date columnTimeFrom;
	private Date columnTimeTo;
	
	private Integer columnRadio;
	private Integer columnCheckbox;
	private BigDecimal columnCurrency;	
	private Float columnFloat;
	private Integer columnSelect;
	private Integer columnAutocomplete;
	
	private Double columnPercentageFrom;
	private Double columnPercentageTo;
	
	private Double columnPercentageDecimalFrom;
	private Double columnPercentageDecimalTo;
	
	private Integer columnIntegerFrom;
	private Integer columnIntegerTo;
	
	private Float columnFloatFrom;
	private Float columnFloatTo;
	
	private BigDecimal columnCurrencyFrom;
	private BigDecimal columnCurrencyTo;
	
	private int[] columnCheckboxs = new int[0];

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

	public Date getColumnDateFrom() {
		return columnDateFrom;
	}

	public void setColumnDateFrom(Date columnDateFrom) {
		this.columnDateFrom = columnDateFrom;
	}

	public Date getColumnDateTo() {
		return columnDateTo;
	}

	public void setColumnDateTo(Date columnDateTo) {
		this.columnDateTo = columnDateTo;
	}

	public Date getColumnDatetimeFrom() {
		return columnDatetimeFrom;
	}

	public void setColumnDatetimeFrom(Date columnDatetimeFrom) {
		this.columnDatetimeFrom = columnDatetimeFrom;
	}

	public Date getColumnDatetimeTo() {
		return columnDatetimeTo;
	}

	public void setColumnDatetimeTo(Date columnDatetimeTo) {
		this.columnDatetimeTo = columnDatetimeTo;
	}

	public Date getColumnTimeFrom() {
		return columnTimeFrom;
	}

	public void setColumnTimeFrom(Date columnTimeFrom) {
		this.columnTimeFrom = columnTimeFrom;
	}

	public Date getColumnTimeTo() {
		return columnTimeTo;
	}

	public void setColumnTimeTo(Date columnTimeTo) {
		this.columnTimeTo = columnTimeTo;
	}

	public Integer getColumnRadio() {
		return columnRadio;
	}

	public void setColumnRadio(Integer columnRadio) {
		this.columnRadio = columnRadio;
	}

	public Integer getColumnCheckbox() {
		return columnCheckbox;
	}

	public void setColumnCheckbox(Integer columnCheckbox) {
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

	public Double getColumnPercentageFrom() {
		return columnPercentageFrom;
	}

	public void setColumnPercentageFrom(Double columnPercentageFrom) {
		this.columnPercentageFrom = columnPercentageFrom;
	}

	public Double getColumnPercentageTo() {
		return columnPercentageTo;
	}

	public void setColumnPercentageTo(Double columnPercentageTo) {
		this.columnPercentageTo = columnPercentageTo;
	}

	public Double getColumnPercentageDecimalFrom() {
		return columnPercentageDecimalFrom;
	}

	public void setColumnPercentageDecimalFrom(Double columnPercentageDecimalFrom) {
		this.columnPercentageDecimalFrom = columnPercentageDecimalFrom;
	}

	public Double getColumnPercentageDecimalTo() {
		return columnPercentageDecimalTo;
	}

	public void setColumnPercentageDecimalTo(Double columnPercentageDecimalTo) {
		this.columnPercentageDecimalTo = columnPercentageDecimalTo;
	}

	public Integer getColumnIntegerFrom() {
		return columnIntegerFrom;
	}

	public void setColumnIntegerFrom(Integer columnIntegerFrom) {
		this.columnIntegerFrom = columnIntegerFrom;
	}

	public Integer getColumnIntegerTo() {
		return columnIntegerTo;
	}

	public void setColumnIntegerTo(Integer columnIntegerTo) {
		this.columnIntegerTo = columnIntegerTo;
	}

	public Float getColumnFloatFrom() {
		return columnFloatFrom;
	}

	public void setColumnFloatFrom(Float columnFloatFrom) {
		this.columnFloatFrom = columnFloatFrom;
	}

	public Float getColumnFloatTo() {
		return columnFloatTo;
	}

	public void setColumnFloatTo(Float columnFloatTo) {
		this.columnFloatTo = columnFloatTo;
	}

	public BigDecimal getColumnCurrencyFrom() {
		return columnCurrencyFrom;
	}

	public void setColumnCurrencyFrom(BigDecimal columnCurrencyFrom) {
		this.columnCurrencyFrom = columnCurrencyFrom;
	}

	public BigDecimal getColumnCurrencyTo() {
		return columnCurrencyTo;
	}

	public void setColumnCurrencyTo(BigDecimal columnCurrencyTo) {
		this.columnCurrencyTo = columnCurrencyTo;
	}

	public int[] getColumnCheckboxs() {
		return columnCheckboxs;
	}

	public void setColumnCheckboxs(int[] columnCheckboxs) {
		this.columnCheckboxs = columnCheckboxs;
	}

	
}
