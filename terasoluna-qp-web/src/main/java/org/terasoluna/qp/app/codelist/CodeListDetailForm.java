package org.terasoluna.qp.app.codelist;

import java.io.Serializable;

public class CodeListDetailForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long clDeatailId;
	private long codeListId;

	/*@NotEmpty (message="sc.codelist.0007")*/
	private String name;

	private String value;
	private String value1;
	private String value2;
	private String value3;
	private String value4;
	private String value5;
	private int isDefault;

	public long getclDeatailId() {
		return clDeatailId;
	}

	public void setclDeatailId(long clDeatailId) {
		this.clDeatailId = clDeatailId;
	}

	public long getCodeListId() {
		return codeListId;
	}

	public void setCodeListId(long codeListId) {
		this.codeListId = codeListId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}  

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}

	public String getValue5() {
		return value5;
	}

	public void setValue5(String value5) {
		this.value5 = value5;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

}
