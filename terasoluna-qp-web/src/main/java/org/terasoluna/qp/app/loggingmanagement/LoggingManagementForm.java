package org.terasoluna.qp.app.loggingmanagement;

import java.io.Serializable;
import java.util.List;

import org.terasoluna.qp.domain.model.ConversionPattern;
import org.terasoluna.qp.domain.model.Log;
import org.terasoluna.qp.domain.model.Project;

public class LoggingManagementForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private Project currentProject;
	private Log consoleLog;
	private boolean consoleLogStatus;
	private Log fileLog;
	private boolean fileLogStatus;
	private Log databaseLog;
	private boolean databaseLogStatus;
	/**
	 * List of all conversion patterns exist in DB 
	 */
	private List<ConversionPattern> lstConversionPattern;
	public Log getConsoleLog() {
		return consoleLog;
	}
	public void setConsoleLog(Log consoleLog) {
		this.consoleLog = consoleLog;
	}
	public boolean getConsoleLogStatus() {
		return consoleLogStatus;
	}
	public void setConsoleLogStatus(boolean consoleLogStatus) {
		this.consoleLogStatus = consoleLogStatus;
	}
	public Log getFileLog() {
		return fileLog;
	}
	public void setFileLog(Log fileLog) {
		this.fileLog = fileLog;
	}
	public boolean getFileLogStatus() {
		return fileLogStatus;
	}
	public void setFileLogStatus(boolean fileLogStatus) {
		this.fileLogStatus = fileLogStatus;
	}
	public Log getDatabaseLog() {
		return databaseLog;
	}
	public void setDatabaseLog(Log databaseLog) {
		this.databaseLog = databaseLog;
	}
	public boolean getDatabaseLogStatus() {
		return databaseLogStatus;
	}
	public void setDatabaseLogStatus(boolean databaseLogStatus) {
		this.databaseLogStatus = databaseLogStatus;
	}
	
	public Project getCurrentProject() {
		return currentProject;
	}
	public void setCurrentProject(Project currentProject) {
		this.currentProject = currentProject;
	}
	public List<ConversionPattern> getLstConversionPattern() {
		return lstConversionPattern;
	}
	public void setLstConversionPattern(List<ConversionPattern> lstConversionPattern) {
		this.lstConversionPattern = lstConversionPattern;
	}
	
}
