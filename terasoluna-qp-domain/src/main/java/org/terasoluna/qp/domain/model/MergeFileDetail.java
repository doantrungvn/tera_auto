package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class MergeFileDetail implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long mergeFileDetailId;

	private Long fileOperationComponentId;

	private Integer sourcePathType;

	private String sourcePathContent;

	private Long sourcePathFormulaId;

	private List<FormulaDetail> sourcePathFormulaDetails;

	/**
	 * @return the mergeFileDetailId
	 */
	public Long getMergeFileDetailId() {
		return mergeFileDetailId;
	}

	/**
	 * @param mergeFileDetailId
	 *            the mergeFileDetailId to set
	 */
	public void setMergeFileDetailId(Long mergeFileDetailId) {
		this.mergeFileDetailId = mergeFileDetailId;
	}

	/**
	 * @return the fileOperationComponentId
	 */
	public Long getFileOperationComponentId() {
		return fileOperationComponentId;
	}

	/**
	 * @param fileOperationComponentId
	 *            the fileOperationComponentId to set
	 */
	public void setFileOperationComponentId(Long fileOperationComponentId) {
		this.fileOperationComponentId = fileOperationComponentId;
	}

	/**
	 * @return the sourcePathContent
	 */
	public String getSourcePathContent() {
		return sourcePathContent;
	}

	/**
	 * @param sourcePathContent
	 *            the sourcePathContent to set
	 */
	public void setSourcePathContent(String sourcePathContent) {
		this.sourcePathContent = sourcePathContent;
	}

	public Integer getSourcePathType() {
		return sourcePathType;
	}

	public void setSourcePathType(Integer sourcePathType) {
		this.sourcePathType = sourcePathType;
	}

	public Long getSourcePathFormulaId() {
		return sourcePathFormulaId;
	}

	public void setSourcePathFormulaId(Long sourcePathFormulaId) {
		this.sourcePathFormulaId = sourcePathFormulaId;
	}

	public List<FormulaDetail> getSourcePathFormulaDetails() {
	    return sourcePathFormulaDetails;
    }

	public void setSourcePathFormulaDetails(List<FormulaDetail> sourcePathFormulaDetails) {
	    this.sourcePathFormulaDetails = sourcePathFormulaDetails;
    }

}
