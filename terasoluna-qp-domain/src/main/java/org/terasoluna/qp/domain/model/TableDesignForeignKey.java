package org.terasoluna.qp.domain.model;

import java.text.MessageFormat;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;

/**
 * Define foreign key of table Table in DB: table_design_foreign_key
 * 
 * @author dungnn1
 *
 */
@XmlRootElement(namespace = "org.terasoluna.qp.domain.model.TableDesignDetails")
public class TableDesignForeignKey {

	private Long foreignKeyId;
	private Integer foreignKeyType;
	private Long fromColumnId;
	private String fromColumnCode;
	private String fromColumnName;
	private Long fromTableId;
	private String fromTableCode;
	private String fromTableName;
	private Long toTableId;
	private String toTableCode;
	private String toTableName;
	private Long toColumnId;
	private String toColumnCode;
	private String toColumnName;
	private String foreignKeyCode;
	private String toForeignKeyType;
	private int indexRow;

	private static final String FK_CODE_TEMPLATE_AUTO = "fk_{0}";

	public static final String FK_CODE_TEMPLATE = "fk_{0}_{1}_{2}_{3}";
	
	public TableDesignForeignKey() {

	}

	public String getFromColumnName() {
		return fromColumnName;
	}

	public void setFromColumnName(String fromColumnName) {
		this.fromColumnName = fromColumnName;
	}

	public String getFromTableName() {
		return fromTableName;
	}

	public void setFromTableName(String fromTableName) {
		this.fromTableName = fromTableName;
	}

	public String getFromTableCode() {
		return fromTableCode;
	}

	public void setFromTableCode(String fromTableCode) {
		this.fromTableCode = fromTableCode;
	}

	public String getToTableName() {
		return toTableName;
	}

	public void setToTableName(String toTableName) {
		this.toTableName = toTableName;
	}

	public String getToColumnName() {
		return toColumnName;
	}

	public void setToColumnName(String toColumnName) {
		this.toColumnName = toColumnName;
	}

	public Long getToColumnId() {
		return toColumnId;
	}

	public void setToColumnId(Long toColumnId) {
		this.toColumnId = toColumnId;
	}

	public void setFromColumnId(Long fromColumnId) {
		this.fromColumnId = fromColumnId;
	}

	public String getFromColumnCode() {
		return fromColumnCode;
	}

	public void setFromColumnCode(String fromColumnCode) {
		this.fromColumnCode = fromColumnCode;
	}

	@XmlAttribute(name = "relationid")
	public Long getForeignKeyId() {
		return foreignKeyId;
	}

	public void setForeignKeyId(Long foreignKeyId) {
		this.foreignKeyId = foreignKeyId;
	}

	@XmlAttribute(name = "table")
	public String getToTableCode() {
		return toTableCode;
	}

	public void setToTableCode(String toTableCode) {
		this.toTableCode = toTableCode;
	}

	@XmlAttribute(name = "row")
	public String getToColumnCode() {
		return toColumnCode;
	}

	public void setToColumnCode(String toColumnCode) {
		this.toColumnCode = toColumnCode;
	}

	public Long getFromTableId() {
		return fromTableId;
	}

	public void setFromTableId(Long fromTableId) {
		this.fromTableId = fromTableId;
	}

	public Long getFromColumnId() {
		return fromColumnId;
	}

	public Long getToTableId() {
		return toTableId;
	}

	public void setToTableId(Long toTableId) {
		this.toTableId = toTableId;
	}

	public String getForeignKeyCode() {
		if (StringUtils.isBlank(foreignKeyCode)) {
			foreignKeyCode = MessageFormat.format(FK_CODE_TEMPLATE_AUTO, GenerateUniqueKey.generateRandomInteger());
		}
		return foreignKeyCode;
	}

	public void setForeignKeyCode(String foreignKeyCode) {
		this.foreignKeyCode = foreignKeyCode;
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();

		str.append("Table Relation Infor:name=");
		str.append(getToTableCode());
		str.append(",column=");
		str.append(getToColumnCode());

		return str.toString();
	}

	public Integer getForeignKeyType() {
		return foreignKeyType;
	}

	public void setForeignKeyType(Integer foreignKeyType) {
		this.foreignKeyType = foreignKeyType;
	}

	public int getIndexRow() {
		return indexRow;
	}

	public void setIndexRow(int indexRow) {
		this.indexRow = indexRow;
	}

	public String getToForeignKeyType() {
		return toForeignKeyType;
	}

	public void setToForeignKeyType(String toForeignKeyType) {
		this.toForeignKeyType = toForeignKeyType;
	}
}
