package org.terasoluna.qp.app.sessionmanagement;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.message.SessionManagementMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;

public class SessionManagementForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long sessionManagementId;
	
	@NotEmpty(message = SessionManagementMessageConst.SC_SESSIONMANAGEMENT_0002)
    @QpNameSize(message = SessionManagementMessageConst.SC_SESSIONMANAGEMENT_0002)
    @QpNamePattern(message= SessionManagementMessageConst.SC_SESSIONMANAGEMENT_0002)
	private String sessionManagementName;
	
    @NotEmpty(message = SessionManagementMessageConst.SC_SESSIONMANAGEMENT_0003)
    @QpCodeSize(message = SessionManagementMessageConst.SC_SESSIONMANAGEMENT_0003)
    @QpCodePattern(message= SessionManagementMessageConst.SC_SESSIONMANAGEMENT_0003)
	private String sessionManagementCode;
	
	private Integer dataType;
	
	private Long objectId;
    
    private String objectIdAutocomplete;
	
	private Long createdBy;
	
	private Long projectId;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private String mode;

    private boolean hasErrors = false;
    
    private List<BusinessDesign> listBusinessDesign;
    
    private String sessionManagementType;
    
	private Boolean arrayFlg = false;
	
	public Boolean getArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(Boolean arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	public Long getSessionManagementId() {
		return sessionManagementId;
	}

	public void setSessionManagementId(Long sessionManagementId) {
		this.sessionManagementId = sessionManagementId;
	}

	public String getSessionManagementName() {
		return sessionManagementName;
	}

	public void setSessionManagementName(String sessionManagementName) {
		this.sessionManagementName = sessionManagementName;
	}

	public String getSessionManagementCode() {
		return sessionManagementCode;
	}

	public void setSessionManagementCode(String sessionManagementCode) {
		this.sessionManagementCode = sessionManagementCode;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

    /**
     * @return the objectIdAutocomplete
     */
    public String getObjectIdAutocomplete() {
        return objectIdAutocomplete;
    }

    /**
     * @param objectIdAutocomplete the objectIdAutocomplete to set
     */
    public void setObjectIdAutocomplete(String objectIdAutocomplete) {
        this.objectIdAutocomplete = objectIdAutocomplete;
    }

    /**
     * @return the hasErrors
     */
    public boolean isHasErrors() {
        return hasErrors;
    }

    /**
     * @param hasErrors the hasErrors to set
     */
    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    /**
     * @return the createdDate
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the updatedDate
     */
    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the listBusinessDesign
     */
    public List<BusinessDesign> getListBusinessDesign() {
        return listBusinessDesign;
    }

    /**
     * @param listBusinessDesign the listBusinessDesign to set
     */
    public void setListBusinessDesign(List<BusinessDesign> listBusinessDesign) {
        this.listBusinessDesign = listBusinessDesign;
    }

	public String getSessionManagementType() {
		return sessionManagementType;
	}

	public void setSessionManagementType(String sessionManagementType) {
		this.sessionManagementType = sessionManagementType;
	}
    
    
}
