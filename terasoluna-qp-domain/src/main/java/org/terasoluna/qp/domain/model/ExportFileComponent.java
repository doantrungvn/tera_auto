package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExportFileComponent extends BlogicComponent implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long exportFileComponentId;

	private Integer destinationPathType;

	private String destinationPathContent;

	private Long destinationPathFormulaId;

	private Integer parameterScope;

	private String parameterId;

	private String parameterIdAutocomplete;

	private FileFormat fileFormat;

	private List<ExportAssignValue> lstExportAssignValues = new ArrayList<ExportAssignValue>();

	private List<FormulaDetail> destinationPathFormulaDetails;

	private ObjectDefinition objectDefinition;

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

	public Integer getDestinationPathType() {
		return destinationPathType;
	}

	public void setDestinationPathType(Integer destinationPathType) {
		this.destinationPathType = destinationPathType;
	}

	public String getDestinationPathContent() {
		return destinationPathContent;
	}

	public void setDestinationPathContent(String destinationPathContent) {
		this.destinationPathContent = destinationPathContent;
	}

	public Long getDestinationPathFormulaId() {
		return destinationPathFormulaId;
	}

	public void setDestinationPathFormulaId(Long destinationPathFormulaId) {
		this.destinationPathFormulaId = destinationPathFormulaId;
	}

	public Integer getParameterScope() {
		return parameterScope;
	}

	public void setParameterScope(Integer parameterScope) {
		this.parameterScope = parameterScope;
	}

	public String getParameterIdAutocomplete() {
		return parameterIdAutocomplete;
	}

	public void setParameterIdAutocomplete(String parameterIdAutocomplete) {
		this.parameterIdAutocomplete = parameterIdAutocomplete;
	}

	public List<ExportAssignValue> getLstExportAssignValues() {
		return lstExportAssignValues;
	}

	public void setLstExportAssignValues(
			List<ExportAssignValue> lstExportAssignValues) {
		this.lstExportAssignValues = lstExportAssignValues;
	}

	public List<FormulaDetail> getDestinationPathFormulaDetails() {
		return destinationPathFormulaDetails;
	}

	public void setDestinationPathFormulaDetails(
			List<FormulaDetail> destinationPathFormulaDetails) {
		this.destinationPathFormulaDetails = destinationPathFormulaDetails;
	}

	/**
	 * @return the exportFileComponentId
	 */
	public Long getExportFileComponentId() {
		return exportFileComponentId;
	}

	/**
	 * @param exportFileComponentId the exportFileComponentId to set
	 */
	public void setExportFileComponentId(Long exportFileComponentId) {
		this.exportFileComponentId = exportFileComponentId;
	}

	/**
	 * @return the parameterId
	 */
	public String getParameterId() {
		return parameterId;
	}

	/**
	 * @param parameterId the parameterId to set
	 */
	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public ObjectDefinition getObjectDefinition() {
		return objectDefinition;
	}

	public void setObjectDefinition(ObjectDefinition objectDefinition) {
		this.objectDefinition = objectDefinition;
	}
}
