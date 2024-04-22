package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ImpactChangeJobControl implements Serializable{

	/**
	 * @author quangvd
	 */
    private static final long serialVersionUID = 1L;

    private String jobSeqId;
    
    private String jobAppCd;
    
    private String projectId;
    
    private String moduleId;
    
    private String createdBy;
    
    private String impactType;
    
    private String impactId;
    
    private String blogicAppStatus;
    
    private String curAppStatus;
    
    private String jobArgNm6;
    
    private String jobArgNm7;
    
    private String jobArgNm8;
    
    private String jobArgNm9;
    
    private String jobArgNm10;
    
    private Timestamp addDateTime;
    
    private Timestamp updDateTime;

	public String getJobSeqId() {
		return jobSeqId;
	}

	public void setJobSeqId(String jobSeqId) {
		this.jobSeqId = jobSeqId;
	}

	public String getJobAppCd() {
		return jobAppCd;
	}

	public void setJobAppCd(String jobAppCd) {
		this.jobAppCd = jobAppCd;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getImpactType() {
		return impactType;
	}

	public void setImpactType(String impactType) {
		this.impactType = impactType;
	}

	public String getImpactId() {
		return impactId;
	}

	public void setImpactId(String impactId) {
		this.impactId = impactId;
	}

	public String getBlogicAppStatus() {
		return blogicAppStatus;
	}

	public void setBlogicAppStatus(String blogicAppStatus) {
		this.blogicAppStatus = blogicAppStatus;
	}

	public String getCurAppStatus() {
		return curAppStatus;
	}

	public void setCurAppStatus(String curAppStatus) {
		this.curAppStatus = curAppStatus;
	}

	public Timestamp getAddDateTime() {
		return addDateTime;
	}

	public void setAddDateTime(Timestamp addDateTime) {
		this.addDateTime = addDateTime;
	}

	public Timestamp getUpdDateTime() {
		return updDateTime;
	}

	public void setUpdDateTime(Timestamp updDateTime) {
		this.updDateTime = updDateTime;
	}

	public String getJobArgNm6() {
		return jobArgNm6;
	}

	public void setJobArgNm6(String jobArgNm6) {
		this.jobArgNm6 = jobArgNm6;
	}

	public String getJobArgNm7() {
		return jobArgNm7;
	}

	public void setJobArgNm7(String jobArgNm7) {
		this.jobArgNm7 = jobArgNm7;
	}

	public String getJobArgNm8() {
	    return jobArgNm8;
    }

	public void setJobArgNm8(String jobArgNm8) {
	    this.jobArgNm8 = jobArgNm8;
    }

	public String getJobArgNm9() {
	    return jobArgNm9;
    }

	public void setJobArgNm9(String jobArgNm9) {
	    this.jobArgNm9 = jobArgNm9;
    }

	public String getJobArgNm10() {
	    return jobArgNm10;
    }

	public void setJobArgNm10(String jobArgNm10) {
	    this.jobArgNm10 = jobArgNm10;
    }
	
	
}
