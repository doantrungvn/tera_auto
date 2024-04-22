package org.terasoluna.qp.domain.model;

public class ImportManagement {

	private static final long serialVersionUID = 1L;
	private String documentTypeAutocomplete;
	private int documentType;
	private boolean rollback;
	private boolean delete;
	private String languageAutocomplete;
	private int language;
	private String languageCode;
	private String filePath;
	private AccountProfile accountProfile;
	
	public String getDocumentTypeAutocomplete() {
		return documentTypeAutocomplete;
	}
	public void setDocumentTypeAutocomplete(String documentTypeAutocomplete) {
		this.documentTypeAutocomplete = documentTypeAutocomplete;
	}
	public int getDocumentType() {
		return documentType;
	}
	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}
	public boolean isRollback() {
		return rollback;
	}
	public void setRollback(boolean rollback) {
		this.rollback = rollback;
	}
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	public String getLanguageAutocomplete() {
		return languageAutocomplete;
	}
	public void setLanguageAutocomplete(String languageAutocomplete) {
		this.languageAutocomplete = languageAutocomplete;
	}
	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	public AccountProfile getAccountProfile() {
		return accountProfile;
	}
	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}
	
	
}
