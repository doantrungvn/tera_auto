package org.terasoluna.qp.domain.repository.message;

import java.io.Serializable;

public class MessageSearchCriteria implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String messageString;
	
	private String messageCode;
	
	private String moduleResource;
	
	private String languageCode;
	
	private String[] messageTypes = new String[0];

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getModuleResource() {
		return moduleResource;
	}

	public void setModuleResource(String moduleResource) {
		this.moduleResource = moduleResource;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String[] getMessageTypes() {
		return messageTypes;
	}

	public void setMessageTypes(String[] messageTypes) {
		this.messageTypes = messageTypes;
	}

}
