package org.terasoluna.qp.domain.model;

import java.util.List;

public class UserDefineCodelist {
	
	private Long codelistId;
	private int supportOptionFlg;
	private String codelistName;
	private List<UserDefineCodelistDetails> userDefineCodelistDetails;
	
	
	public Long getCodelistId() {
		return codelistId;
	}
	public void setCodelistId(Long codelistId) {
		this.codelistId = codelistId;
	}
	
	public int getSupportOptionFlg() {
		return supportOptionFlg;
	}
	public void setSupportOptionFlg(int supportOptionFlg) {
		this.supportOptionFlg = supportOptionFlg;
	}
	public List<UserDefineCodelistDetails> getUserDefineCodelistDetails() {
		return userDefineCodelistDetails;
	}
	public void setUserDefineCodelistDetails(
			List<UserDefineCodelistDetails> userDefineCodelistDetails) {
		this.userDefineCodelistDetails = userDefineCodelistDetails;
	}
	public String getCodelistName() {
		return codelistName;
	}
	public void setCodelistName(String codelistName) {
		this.codelistName = codelistName;
	}	
}
