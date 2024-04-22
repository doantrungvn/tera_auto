package org.terasoluna.qp.domain.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model for conversion pattern.
 * @author quynd1
 *
 */
@XmlRootElement(name = "conversionPatternObj")
public class ConversionPattern implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long logDetailId;
	private Long patternId;
	private String patternCode;
	private String patternName;
	private String remark;
	private int itemSequence;
	
	public ConversionPattern(){
		this.setItemSequence(0);
	}
	
	public Long getLogDetailId() {
		return logDetailId;
	}

	public void setLogDetailId(Long logDetailId) {
		this.logDetailId = logDetailId;
	}

	@XmlElement(name = "patternId")
	public Long getPatternId() {
		return patternId;
	}
	public void setPatternId(Long patternId) {
		this.patternId = patternId;
	}
	public String getPatternCode() {
		return patternCode;
	}
	public void setPatternCode(String patternCode) {
		this.patternCode = patternCode;
	}
	public String getPatternName() {
		return patternName;
	}
	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@XmlElement(name = "itemSequence")
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}

}
