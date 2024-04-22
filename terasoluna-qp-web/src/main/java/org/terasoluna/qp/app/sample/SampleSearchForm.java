package org.terasoluna.qp.app.sample;

import java.io.Serializable;
import java.math.BigDecimal;

public class SampleSearchForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer columnId;
	private String columnText;	
	private Integer columnInteger;
	
	private String columnDateFrom;
	private String columnDateTo;
	
	private String columnDatetimeFrom;
	private String columnDatetimeTo;
	
	private String columnTimeFrom;
	private String columnTimeTo;
	
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

	public String getColumnDateFrom() {
		return columnDateFrom;
	}

	public void setColumnDateFrom(String columnDateFrom) {
		this.columnDateFrom = columnDateFrom;
	}

	public String getColumnDateTo() {
		return columnDateTo;
	}

	public void setColumnDateTo(String columnDateTo) {
		this.columnDateTo = columnDateTo;
	}

	public String getColumnDatetimeFrom() {
		return columnDatetimeFrom;
	}

	public void setColumnDatetimeFrom(String columnDatetimeFrom) {
		this.columnDatetimeFrom = columnDatetimeFrom;
	}

	public String getColumnDatetimeTo() {
		return columnDatetimeTo;
	}

	public void setColumnDatetimeTo(String columnDatetimeTo) {
		this.columnDatetimeTo = columnDatetimeTo;
	}

	public String getColumnTimeFrom() {
		return columnTimeFrom;
	}

	public void setColumnTimeFrom(String columnTimeFrom) {
		this.columnTimeFrom = columnTimeFrom;
	}

	public String getColumnTimeTo() {
		return columnTimeTo;
	}

	public void setColumnTimeTo(String columnTimeTo) {
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
