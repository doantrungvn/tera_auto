package org.terasoluna.qp.app.importmanagement;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class ImportManagementForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String documentTypeAutocomplete;
	private String documentType;
	private MultipartFile file;
	private boolean rollback = true;
	private boolean delete = false;
	private String languageAutocomplete;
	private int language;
	private String resultFileName;
	private int maxSize;
	private String fileName;
	
	public String getDocumentTypeAutocomplete() {
		return documentTypeAutocomplete;
	}
	public void setDocumentTypeAutocomplete(String documentTypeAutocomplete) {
		this.documentTypeAutocomplete = documentTypeAutocomplete;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
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
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getResultFileName() {
		return resultFileName;
	}
	public void setResultFileName(String resultFileName) {
		this.resultFileName = resultFileName;
	}
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
