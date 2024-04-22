package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class FileFormat implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer fileType;

	private Long fileId;

	private Integer fileEncoding;

	private Integer lineFeedCharType;

	private String lineFeedChar;

	private String delimiter;

	private Integer encloseCharType;

	private String encloseChar;

	private String headLineCount;

	private String trailerLineCount;

	private Boolean overwriteFlg = false;

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Integer getFileEncoding() {
		return fileEncoding;
	}

	public void setFileEncoding(Integer fileEncoding) {
		this.fileEncoding = fileEncoding;
	}

	public String getLineFeedChar() {
		return lineFeedChar;
	}

	public void setLineFeedChar(String lineFeedChar) {
		this.lineFeedChar = lineFeedChar;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getEncloseChar() {
		return encloseChar;
	}

	public void setEncloseChar(String encloseChar) {
		this.encloseChar = encloseChar;
	}

	public String getHeadLineCount() {
		return headLineCount;
	}

	public void setHeadLineCount(String headLineCount) {
		this.headLineCount = headLineCount;
	}

	public String getTrailerLineCount() {
		return trailerLineCount;
	}

	public void setTrailerLineCount(String trailerLineCount) {
		this.trailerLineCount = trailerLineCount;
	}

	public Boolean getOverwriteFlg() {
		return overwriteFlg;
	}

	public void setOverwriteFlg(Boolean overwriteFlg) {
		this.overwriteFlg = overwriteFlg;
	}

	/**
	 * @return the lineFeedCharType
	 */
	public Integer getLineFeedCharType() {
		return lineFeedCharType;
	}

	/**
	 * @param lineFeedCharType the lineFeedCharType to set
	 */
	public void setLineFeedCharType(Integer lineFeedCharType) {
		this.lineFeedCharType = lineFeedCharType;
	}

	/**
	 * @return the encloseCharType
	 */
	public Integer getEncloseCharType() {
		return encloseCharType;
	}

	/**
	 * @param encloseCharType the encloseCharType to set
	 */
	public void setEncloseCharType(Integer encloseCharType) {
		this.encloseCharType = encloseCharType;
	}

}
