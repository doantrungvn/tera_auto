package org.terasoluna.qp.app.message;

import java.io.Serializable;
import java.util.List;

import org.terasoluna.qp.domain.model.Language;
import org.terasoluna.qp.domain.model.Message;

public class MessageForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String messageCode;
	
	private String messageString;
	
	private String languageCode;
	
	private Language language;
	
	private String moduleResource;
	
	private Integer pageNumber;
	
	private Integer pageSize;
	
	private String[] messageTypes = new String[0];
	
	private String countryCode;
	
	private List<Message> listMessage;

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

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getModuleResource() {
		return moduleResource;
	}

	public void setModuleResource(String moduleResource) {
		this.moduleResource = moduleResource;
	}

	public String[] getMessageTypes() {
		return messageTypes;
	}

	public void setMessageTypes(String[] messageTypes) {
		this.messageTypes = messageTypes;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<Message> getListMessage() {
		return listMessage;
	}

	public void setListMessage(List<Message> listMessage) {
		this.listMessage = listMessage;
	}
}

