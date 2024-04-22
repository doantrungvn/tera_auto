package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class ExportAssignValue implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long exportAssignValueId;
	
	private Long exportFileComponentId;

	private Integer parameterScope;
	
	private String parameterId;

	private String parameterIdAutocomplete;

	private String columnNo;
	
	private String dataGroup;
	
	private Integer dataType;
	
	private ColumnFileFormat columnFileFormat;

	public String getColumnNo() {
		return columnNo;
	}

	public void setColumnNo(String columnNo) {
		this.columnNo = columnNo;
	}

	public Long getExportAssignValueId() {
		return exportAssignValueId;
	}

	public void setExportAssignValueId(Long exportAssignValueId) {
		this.exportAssignValueId = exportAssignValueId;
	}

	public Long getExportFileComponentId() {
		return exportFileComponentId;
	}

	public void setExportFileComponentId(Long exportFileComponentId) {
		this.exportFileComponentId = exportFileComponentId;
	}

	public Integer getParameterScope() {
		return parameterScope;
	}

	public void setParameterScope(Integer parameterScope) {
		this.parameterScope = parameterScope;
	}

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public String getParameterIdAutocomplete() {
		return parameterIdAutocomplete;
	}

	public void setParameterIdAutocomplete(String parameterIdAutocomplete) {
		this.parameterIdAutocomplete = parameterIdAutocomplete;
	}

	/**
	 * @return the dataGroup
	 */
	public String getDataGroup() {
		return dataGroup;
	}

	/**
	 * @param dataGroup the dataGroup to set
	 */
	public void setDataGroup(String dataGroup) {
		this.dataGroup = dataGroup;
	}

	/**
	 * @return the dataType
	 */
	public Integer getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the columnFileFormat
	 */
	public ColumnFileFormat getColumnFileFormat() {
		return columnFileFormat;
	}

	/**
	 * @param columnFileFormat the columnFileFormat to set
	 */
	public void setColumnFileFormat(ColumnFileFormat columnFileFormat) {
		this.columnFileFormat = columnFileFormat;
	}
}
