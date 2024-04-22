/**
 * 
 */
package org.terasoluna.qp.app.tabledesign;

/**
 * @author datld
 *
 */
public class TableDesignSearchForm {

	private String tableName;

	private String tableCode;

	private Long projectId;

	private String projectIdAutocomplete;

	private Integer subjectAreaId;

	private String subjectAreaIdAutocomplete;
	
	private Integer status;
	
	private int[] designStatus = new int[0];
	
	private Integer typeTable;
	
	private int[] types = new int[0];
	
	private boolean genDrop;
	
	private boolean unMatched = false;
	
	public boolean isUnMatched() {
		return unMatched;
	}

	public void setUnMatched(boolean unMatched) {
		this.unMatched = unMatched;
		
		if(unMatched){
			this.setStatus(0);
		}else{
			this.setStatus(null);
		}
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}

	public Integer getSubjectAreaId() {
		return subjectAreaId;
	}

	public void setSubjectAreaId(Integer subjectAreaId) {
		this.subjectAreaId = subjectAreaId;
	}

	public String getSubjectAreaIdAutocomplete() {
		return subjectAreaIdAutocomplete;
	}

	public void setSubjectAreaIdAutocomplete(String subjectAreaIdAutocomplete) {
		this.subjectAreaIdAutocomplete = subjectAreaIdAutocomplete;
	}

	public int[] getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(int[] designStatus) {
		this.designStatus = designStatus;
	}

	public int[] getTypes() {
		return types;
	}

	public void setTypes(int[] types) {
		this.types = types;
	}

	public Integer getTypeTable() {
		return typeTable;
	}

	public void setTypeTable(Integer typeTable) {
		this.typeTable = typeTable;
	}

	public boolean getGenDrop() {
		return genDrop;
	}

	public void setGenDrop(boolean genDrop) {
		this.genDrop = genDrop;
	}

}
