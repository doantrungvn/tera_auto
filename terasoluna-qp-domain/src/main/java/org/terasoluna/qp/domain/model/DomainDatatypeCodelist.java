package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class DomainDatatypeCodelist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Item option identify. */
	private int codelistId;

	/** CodeList name. */
	private String codelistName;

	/** Codelist value. */
	private String codelistValue;

	/** Support option flag. */
	private int supportOptionFlag;

	private long domainDatatypeItemId;
	
	public int getCodelistId() {
		return codelistId;
	}

	public void setCodelistId(int codelistId) {
		this.codelistId = codelistId;
	}

	public String getCodelistName() {
		return codelistName;
	}

	public void setCodelistName(String codelistName) {
		this.codelistName = codelistName;
	}

	public String getCodelistValue() {
		return codelistValue;
	}

	public void setCodelistValue(String codelistValue) {
		this.codelistValue = codelistValue;
	}

	public int getSupportOptionFlag() {
		return supportOptionFlag;
	}

	public void setSupportOptionFlag(int supportOptionFlag) {
		this.supportOptionFlag = supportOptionFlag;
	}

	public long getDomainDatatypeItemId() {
		return domainDatatypeItemId;
	}

	public void setDomainDatatypeItemId(long domainDatatypeItemId) {
		this.domainDatatypeItemId = domainDatatypeItemId;
	}

	

}
