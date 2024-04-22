package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "session")
public class SessionManagement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long sessionManagementId;
	
	private String sessionManagementName;
	
	private String sessionManagementCode;
	
	private Integer sessionManagementType;
	
	private Integer dataType;
	
	private Long objectId;
	
	private String objectIdAutocomplete;
	
	/** module code of object */
	private String module_code;
	
	private String packageNameExternalObject;
	
	private Long createdBy;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
    
    private Timestamp systemTime;
    
    private Long projectId;
    
    private List<BusinessDesign> listBusinessDesign;
    
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

	@XmlElement(name = "name")
	public String getSessionManagementName() {
		return sessionManagementName;
	}

	public void setSessionManagementName(String sessionManagementName) {
		this.sessionManagementName = sessionManagementName;
	}

	@XmlElement(name = "code")
	public String getSessionManagementCode() {
		return sessionManagementCode;
	}

	public void setSessionManagementCode(String sessionManagementCode) {
		this.sessionManagementCode = sessionManagementCode;
	}

	@XmlElement(name = "datatype")
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
     * @return the systemTime
     */
    public Timestamp getSystemTime() {
        return systemTime;
    }

    /**
     * @param systemTime the systemTime to set
     */
    public void setSystemTime(Timestamp systemTime) {
        this.systemTime = systemTime;
    }

    /**
     * @return the projectId
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * @param projectId the projectId to set
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

	public Integer getSessionManagementType() {
		return sessionManagementType;
	}

	public void setSessionManagementType(Integer sessionManagementType) {
		this.sessionManagementType = sessionManagementType;
	}

	public String getPackageNameExternalObject() {
		return packageNameExternalObject;
	}

	public void setPackageNameExternalObject(String packageNameExternalObject) {
		this.packageNameExternalObject = packageNameExternalObject;
	}

    public String getModule_code() {
        return module_code;
    }

    public void setModule_code(String module_code) {
        this.module_code = module_code;
    }
	
	
}
