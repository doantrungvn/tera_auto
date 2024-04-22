package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class DownloadFileComponent extends BlogicComponent implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long downloadFileComponentId;

	private Integer parameterScope;

	private String parameterId = "";

	private String parameterIdAutocomplete = "";

	private Integer fileNameType;

	private String fileNameContent;

	private Long fileNameFormulaId;

	private List<FormulaDetail> fileNameFormulaDetails;

	private OutputBean outputBean;

	public Long getDownloadFileComponentId() {
		return downloadFileComponentId;
	}

	public void setDownloadFileComponentId(Long downloadFileComponentId) {
		this.downloadFileComponentId = downloadFileComponentId;
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

	public Integer getFileNameType() {
		return fileNameType;
	}

	public void setFileNameType(Integer fileNameType) {
		this.fileNameType = fileNameType;
	}

	public String getFileNameContent() {
		return fileNameContent;
	}

	public void setFileNameContent(String fileNameContent) {
		this.fileNameContent = fileNameContent;
	}

	public Long getFileNameFormulaId() {
		return fileNameFormulaId;
	}

	public void setFileNameFormulaId(Long fileNameFormulaId) {
		this.fileNameFormulaId = fileNameFormulaId;
	}

	public List<FormulaDetail> getFileNameFormulaDetails() {
		return fileNameFormulaDetails;
	}

	public void setFileNameFormulaDetails(List<FormulaDetail> fileNameFormulaDetails) {
		this.fileNameFormulaDetails = fileNameFormulaDetails;
	}

	public OutputBean getOutputBean() {
		return outputBean;
	}

	public void setOutputBean(OutputBean outputBean) {
		this.outputBean = outputBean;
	}

}
