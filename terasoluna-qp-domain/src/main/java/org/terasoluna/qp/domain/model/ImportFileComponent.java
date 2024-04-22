package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImportFileComponent extends BlogicComponent implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long importFileComponentId;

	private Integer sourcePathType;

	private String sourcePathContent;

	private Long sourcePathFormulaId;

	private Integer targetScope;

	private String targetId;

	private String targetIdAutocomplete;

	private FileFormat fileFormat;

	private List<ImportAssignValue> lstImportAssignValues = new ArrayList<ImportAssignValue>();

	private List<FormulaDetail> sourcePathFormulaDetails;

	private ObjectDefinition objectDefinition;

	public Long getImportFileComponentId() {
		return importFileComponentId;
	}

	public void setImportFileComponentId(Long importFileComponentId) {
		this.importFileComponentId = importFileComponentId;
	}

	public Integer getTargetScope() {
		return targetScope;
	}

	public void setTargetScope(Integer targetScope) {
		this.targetScope = targetScope;
	}

	/**
	 * @return the lstImportAssignValues
	 */
	public List<ImportAssignValue> getLstImportAssignValues() {
		return lstImportAssignValues;
	}

	/**
	 * @param lstImportAssignValues the lstImportAssignValues to set
	 */
	public void setLstImportAssignValues(List<ImportAssignValue> lstImportAssignValues) {
		this.lstImportAssignValues = lstImportAssignValues;
	}

	/**
	 * @return the sourcePathType
	 */
	public Integer getSourcePathType() {
		return sourcePathType;
	}

	/**
	 * @param sourcePathType the sourcePathType to set
	 */
	public void setSourcePathType(Integer sourcePathType) {
		this.sourcePathType = sourcePathType;
	}

	/**
	 * @return the sourcePathContent
	 */
	public String getSourcePathContent() {
		return sourcePathContent;
	}

	/**
	 * @param sourcePathContent the sourcePathContent to set
	 */
	public void setSourcePathContent(String sourcePathContent) {
		this.sourcePathContent = sourcePathContent;
	}

	/**
	 * @return the sourcePathFormulaId
	 */
	public Long getSourcePathFormulaId() {
		return sourcePathFormulaId;
	}

	/**
	 * @param sourcePathFormulaId the sourcePathFormulaId to set
	 */
	public void setSourcePathFormulaId(Long sourcePathFormulaId) {
		this.sourcePathFormulaId = sourcePathFormulaId;
	}

	/**
	 * @return the fileFormat
	 */
	public FileFormat getFileFormat() {
		return fileFormat;
	}

	/**
	 * @param fileFormat the fileFormat to set
	 */
	public void setFileFormat(FileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}

	/**
	 * @return the sourcePathFormulaDetails
	 */
	public List<FormulaDetail> getSourcePathFormulaDetails() {
		return sourcePathFormulaDetails;
	}

	/**
	 * @param sourcePathFormulaDetails the sourcePathFormulaDetails to set
	 */
	public void setSourcePathFormulaDetails(List<FormulaDetail> sourcePathFormulaDetails) {
		this.sourcePathFormulaDetails = sourcePathFormulaDetails;
	}

	/**
	 * @return the targetIdAutocomplete
	 */
	public String getTargetIdAutocomplete() {
		return targetIdAutocomplete;
	}

	/**
	 * @param targetIdAutocomplete the targetIdAutocomplete to set
	 */
	public void setTargetIdAutocomplete(String targetIdAutocomplete) {
		this.targetIdAutocomplete = targetIdAutocomplete;
	}

	/**
	 * @return the targetId
	 */
	public String getTargetId() {
		return targetId;
	}

	/**
	 * @param targetId the targetId to set
	 */
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public ObjectDefinition getObjectDefinition() {
		return objectDefinition;
	}

	public void setObjectDefinition(ObjectDefinition objectDefinition) {
		this.objectDefinition = objectDefinition;
	}


}
