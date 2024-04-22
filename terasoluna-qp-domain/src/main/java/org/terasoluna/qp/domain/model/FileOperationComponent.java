package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileOperationComponent extends BlogicComponent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long fileOperationComponentId;

	private Integer type;

	private Boolean overwriteFlg = false;

	private Integer sourcePathType;

	private String sourcePathContent;

	private Long sourcePathFormulaId;

	private List<FormulaDetail> sourcePathFormulaDetails;

	private Integer destinationPathType;

	private String destinationPathContent;

	private Long destinationPathFormulaId;

	private List<FormulaDetail> destinationPathFormulaDetails;

	private Integer newFilenameType;

	private String newFilenameContent;

	private Long newFilenameFormulaId;

	private List<FormulaDetail> newFilenameFormulaDetails;

	private List<MergeFileDetail> lstMergeFileDetails = new ArrayList<MergeFileDetail>();

	public Long getFileOperationComponentId() {
		return fileOperationComponentId;
	}

	public void setFileOperationComponentId(Long fileoperationComponentId) {
		this.fileOperationComponentId = fileoperationComponentId;
	}

	public List<MergeFileDetail> getLstMergeFileDetails() {
		return lstMergeFileDetails;
	}

	public void setLstMergeFileDetails(List<MergeFileDetail> lstMergeFileDetails) {
		this.lstMergeFileDetails = lstMergeFileDetails;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean getOverwriteFlg() {
		return overwriteFlg;
	}

	public void setOverwriteFlg(Boolean overwriteFlg) {
		this.overwriteFlg = overwriteFlg;
	}

	public Integer getSourcePathType() {
		return sourcePathType;
	}

	public void setSourcePathType(Integer sourcePathType) {
		this.sourcePathType = sourcePathType;
	}

	public String getSourcePathContent() {
		return sourcePathContent;
	}

	public void setSourcePathContent(String sourcePathContent) {
		this.sourcePathContent = sourcePathContent;
	}

	public Long getSourcePathFormulaId() {
		return sourcePathFormulaId;
	}

	public void setSourcePathFormulaId(Long sourcePathFormulaId) {
		this.sourcePathFormulaId = sourcePathFormulaId;
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

	public Integer getNewFilenameType() {
		return newFilenameType;
	}

	public void setNewFilenameType(Integer newFilenameType) {
		this.newFilenameType = newFilenameType;
	}

	public String getNewFilenameContent() {
		return newFilenameContent;
	}

	public void setNewFilenameContent(String newFilenameContent) {
		this.newFilenameContent = newFilenameContent;
	}

	public Long getNewFilenameFormulaId() {
		return newFilenameFormulaId;
	}

	public void setNewFilenameFormulaId(Long newFilenameFormulaId) {
		this.newFilenameFormulaId = newFilenameFormulaId;
	}

	public List<FormulaDetail> getSourcePathFormulaDetails() {
		return sourcePathFormulaDetails;
	}

	public void setSourcePathFormulaDetails(List<FormulaDetail> sourcePathFormulaDetails) {
		this.sourcePathFormulaDetails = sourcePathFormulaDetails;
	}

	public List<FormulaDetail> getDestinationPathFormulaDetails() {
		return destinationPathFormulaDetails;
	}

	public void setDestinationPathFormulaDetails(List<FormulaDetail> destinationPathFormulaDetails) {
		this.destinationPathFormulaDetails = destinationPathFormulaDetails;
	}

	public List<FormulaDetail> getNewFilenameFormulaDetails() {
		return newFilenameFormulaDetails;
	}

	public void setNewFilenameFormulaDetails(List<FormulaDetail> newFilenameFormulaDetails) {
		this.newFilenameFormulaDetails = newFilenameFormulaDetails;
	}

}
