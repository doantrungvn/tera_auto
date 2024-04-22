package org.terasoluna.qp.domain.repository.languagedesign;

import java.io.Serializable;

public class LanguageDesignCriteria implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
	private String languageName;
    private String languageCode;
    private Long projectId;
    /**
     * @return the languageName
     */
    public String getLanguageName() {
        return languageName;
    }
    /**
     * @param languageName the languageName to set
     */
    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
    /**
     * @return the languageCode
     */
    public String getLanguageCode() {
        return languageCode;
    }
    /**
     * @param languageCode the languageCode to set
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
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
