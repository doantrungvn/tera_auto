package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class ColumnFileFormat implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private Long exportAssignValueId;
	
	private Integer paddingType;

	private Integer paddingCharType;

	private String paddingChar;

	private String specifyByte;

	private Integer columnFormat;

	private Integer trimType;

	private String trimChar;

	private Integer converter;

	public Long getExportAssignValueId() {
		return exportAssignValueId;
	}

	public void setExportAssignValueId(Long exportAssignValueId) {
		this.exportAssignValueId = exportAssignValueId;
	}

	public Integer getPaddingType() {
		return paddingType;
	}

	public void setPaddingType(Integer paddingType) {
		this.paddingType = paddingType;
	}

	public String getPaddingChar() {
		return paddingChar;
	}

	public void setPaddingChar(String paddingChar) {
		this.paddingChar = paddingChar;
	}

	public String getSpecifyByte() {
		return specifyByte;
	}

	public void setSpecifyByte(String specifyByte) {
		this.specifyByte = specifyByte;
	}

	public Integer getColumnFormat() {
		return columnFormat;
	}

	public void setColumnFormat(Integer columnFormat) {
		this.columnFormat = columnFormat;
	}

	public Integer getTrimType() {
		return trimType;
	}

	public void setTrimType(Integer trimType) {
		this.trimType = trimType;
	}

	public String getTrimChar() {
		return trimChar;
	}

	public void setTrimChar(String trimChar) {
		this.trimChar = trimChar;
	}

	/**
	 * @return the paddingCharType
	 */
	public Integer getPaddingCharType() {
		return paddingCharType;
	}

	/**
	 * @param paddingCharType the paddingCharType to set
	 */
	public void setPaddingCharType(Integer paddingCharType) {
		this.paddingCharType = paddingCharType;
	}

	/**
	 * @return the converter
	 */
	public Integer getConverter() {
		return converter;
	}

	/**
	 * @param converter the converter to set
	 */
	public void setConverter(Integer converter) {
		this.converter = converter;
	}

}
