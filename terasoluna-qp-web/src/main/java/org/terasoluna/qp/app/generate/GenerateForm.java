package org.terasoluna.qp.app.generate;

import java.io.Serializable;
import java.util.List;

public class GenerateForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long projectId;

	private String sqlScripts;
	
	private Integer generateMode;
	
	private Integer generateFrom;
	
	private List<String> listTableId;
	
	private boolean databaseLog;
	
	private boolean genDrop;
	
	public String getSqlScripts() {
		return sqlScripts;
	}

	public void setSqlScripts(String sqlScripts) {
		this.sqlScripts = sqlScripts;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Integer getGenerateMode() {
		return generateMode;
	}

	public void setGenerateMode(Integer generateMode) {
		this.generateMode = generateMode;
	}

	public List<String> getListTableId() {
		return listTableId;
	}

	public void setListTableId(List<String> listTableId) {
		this.listTableId = listTableId;
	}

	public Integer getGenerateFrom() {
		return generateFrom;
	}

	public void setGenerateFrom(Integer generateFrom) {
		this.generateFrom = generateFrom;
	}

	public boolean isDatabaseLog() {
		return databaseLog;
	}

	public void setDatabaseLog(boolean databaseLog) {
		this.databaseLog = databaseLog;
	}

	public boolean getGenDrop() {
		return genDrop;
	}

	public void setGenDrop(boolean genDrop) {
		this.genDrop = genDrop;
	}
	
}
