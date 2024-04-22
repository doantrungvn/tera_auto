package org.terasoluna.qp.app.message;

import java.io.Serializable;

import org.terasoluna.qp.domain.model.Language;

public class MessageSearchForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String messageCode;
	
	private String messageString;
	
	private String languageCode;
	
	private Language language;
	
	private String moduleResource;
	
	private String[] messageTypes = new String[0];
	
	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String[] getMessageTypes() {
		return messageTypes;
	}

	public void setMessageTypes(String[] messageTypes) {
		this.messageTypes = messageTypes;
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
	
}