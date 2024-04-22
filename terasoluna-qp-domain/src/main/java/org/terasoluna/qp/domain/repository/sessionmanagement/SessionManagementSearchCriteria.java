package org.terasoluna.qp.domain.repository.sessionmanagement;

import java.io.Serializable;

public class SessionManagementSearchCriteria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sessionManagementName;
    
    private String sessionManagementCode;
    
    private Long projectId;
    
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

}
