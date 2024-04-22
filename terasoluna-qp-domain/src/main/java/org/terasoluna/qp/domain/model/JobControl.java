package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
	
public class JobControl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String jobSeqId;
	
	private String jobAppCd;
	
	private String jobArgNm1;
	
	private String jobArgNm2;
	
	private Timestamp updatedDate;
	
	private Timestamp systemTime;

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

	public String getJobArgNm1() {
		return jobArgNm1;
	}

	public void setJobArgNm1(String jobArgNm1) {
		this.jobArgNm1 = jobArgNm1;
	}

	public String getJobArgNm2() {
		return jobArgNm2;
	}

	public void setJobArgNm2(String jobArgNm2) {
		this.jobArgNm2 = jobArgNm2;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Timestamp getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Timestamp systemTime) {
		this.systemTime = systemTime;
	}
	
	

}
