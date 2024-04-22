/**
 * 
 */
package org.terasoluna.qp.domain.repository.tabledesign;

/**
 * @author datld
 *
 */
public class TableDesignCriteria {

	private String tableName;

	private String tableCode;

	private Long projectId;

	private Long tableId;

	private Long subjectAreaId;
	
	private int[] designStatus = new int[0];
	
	private int[] types = new int[0];

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

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getSubjectAreaId() {
		return subjectAreaId;
	}

	public void setSubjectAreaId(Long subjectAreaId) {
		this.subjectAreaId = subjectAreaId;
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
}
