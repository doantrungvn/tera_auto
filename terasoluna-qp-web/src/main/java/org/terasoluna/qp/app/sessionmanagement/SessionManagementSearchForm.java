package org.terasoluna.qp.app.sessionmanagement;

import java.io.Serializable;

public class SessionManagementSearchForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long projectId;
	
	private String sessionManagementName;
    
    private String sessionManagementCode;
    
    private Integer sessionManagementType;

    /**
     * @return the sessionManagementName
     */
    public String getSessionManagementName() {
        return sessionManagementName;
    }

    /**
     * @param sessionManagementName the sessionManagementName to set
     */
    public void setSessionManagementName(String sessionManagementName) {
        this.sessionManagementName = sessionManagementName;
    }

    /**
     * @return the sessionManagementCode
     */
    public String getSessionManagementCode() {
        return sessionManagementCode;
    }

    /**
     * @param sessionManagementCode the sessionManagementCode to set
     */
    public void setSessionManagementCode(String sessionManagementCode) {
        this.sessionManagementCode = sessionManagementCode;
    }

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Integer getSessionManagementType() {
		return sessionManagementType;
	}

	public void setSessionManagementType(Integer sessionManagementType) {
		this.sessionManagementType = sessionManagementType;
	}

}
