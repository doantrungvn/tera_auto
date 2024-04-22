package org.terasoluna.qp.app.domaindatatype;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DomainDatatypeCodelistForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Item option identify. */
	private long codelistId;

	/** CodeList name. */
	@NotNull
	private String codelistName;

	/** Codelist value. */
	@Pattern(regexp = "[0-9a-zA-Z_]+")
	@Size(max = 50)
	private String codelistValue;

	/** Support option flag. */
	private int supportOptionFlag;

	private long domainDatatypeItemId;

	public long getCodelistId() {
		return codelistId;
	}

	public void setCodelistId(long codelistId) {
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
