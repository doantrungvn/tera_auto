package org.terasoluna.qp.app.domaindatatype;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.DomainDatatypeMessage;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;

public class DomainDatatypeForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long domainDatatypeId;
	
	@NotEmpty (message=DomainDatatypeMessage.SC_DOMAINDATATYPE_0002)
	//@Size(min = 1, max = 200, message = DomainDatatypeMessage.SC_DOMAINDATATYPE_0002 + ";" + DomainDatatypeConst.REQUIRED_MIN_INPUT_VAL +";" + DbDomainConst.MAX_LENGTH_OF_NAME)
	@Pattern(regexp=DomainDatatypeConst.REGULAR_EXP_NAME, message = DomainDatatypeMessage.SC_DOMAINDATATYPE_0002 + ";" + CommonMessageConst.NAME_INPUTMASK)
	private String domainDatatypeName;

	@NotEmpty (message=DomainDatatypeMessage.SC_DOMAINDATATYPE_0003)
	//@Size(min = 1, max = 50, message = DomainDatatypeMessage.SC_DOMAINDATATYPE_0003 + ";" + DomainDatatypeConst.REQUIRED_MIN_INPUT_VAL + ";" + DbDomainConst.MAX_LENGTH_OF_CODE)
	@Pattern(regexp = DomainDatatypeConst.REGULAR_EXP_CODE, message= DomainDatatypeMessage.SC_DOMAINDATATYPE_0003 + ";" + CommonMessageConst.CODE_INPUTMASK)
	private String domainDatatypeCode;

	private int status;
	
	private String remark;

	private String tableDesignName;

	private long tableDesignId;

	private Boolean isGenerate;

	private int changeDesignFlg;

	private Long projectId;

	private String projectName;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Valid
	private List<DomainDatatypeItemForm> domainDatatypeItems;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsGenerate() {
		return isGenerate;
	}

	public void setIsGenerate(Boolean isGenerate) {
		this.isGenerate = isGenerate;
	}

	public long getDomainDatatypeId() {
		return domainDatatypeId;
	}

	public void setDomainDatatypeId(long domainDatatypeId) {
		this.domainDatatypeId = domainDatatypeId;
	}

	public String getDomainDatatypeName() {
		return domainDatatypeName;
	}

	public void setDomainDatatypeName(String domainDatatypeName) {
		this.domainDatatypeName = domainDatatypeName;
	}

	public String getDomainDatatypeCode() {
		return domainDatatypeCode;
	}

	public void setDomainDatatypeCode(String domainDatatypeCode) {
		this.domainDatatypeCode = domainDatatypeCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getTableDesignId() {
		return tableDesignId;
	}

	public void setTableDesignId(long tableDesignId) {
		this.tableDesignId = tableDesignId;
	}

	public String getTableDesignName() {
		return tableDesignName;
	}

	public void setTableDesignName(String tableDesignName) {
		this.tableDesignName = tableDesignName;
	}

	public List<DomainDatatypeItemForm> getDomainDatatypeItems() {
		return domainDatatypeItems;
	}

	public void setDomainDatatypeItems(List<DomainDatatypeItemForm> domainDatatypeItems) {
		this.domainDatatypeItems = domainDatatypeItems;
	}

	public int getChangeDesignFlg() {
		return changeDesignFlg;
	}

	public void setChangeDesignFlg(int changeDesignFlg) {
		this.changeDesignFlg = changeDesignFlg;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
