package org.terasoluna.qp.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.terasoluna.qp.app.common.constants.DbDomainConst;

/**
 * Define key of table
 * 
 * Table in DB : table_design_key
 * 
 * @author dungnn1
 *
 */

@XmlRootElement(namespace = "org.terasoluna.qp.domain.model.TableDesign")
public class TableDesignKey {

	private Long keyId;
	
	private String code = UUID.randomUUID().toString();
	
	private int type;
	
	private Long tableDesignId;
	
	private String strKeyItems;
	
	private List<String> keyItems = new ArrayList<String>();

	public TableDesignKey() {

	}

	public TableDesignKey(String code, int type, List<String> keyItems) {
		setCode(code);
		setType(type);
		setKeyItems(keyItems);
	}

	@XmlAttribute (name="name")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@XmlAttribute(name = "type")
	public String getTypeFromXml() {
		String result = "";
		if (type == DbDomainConst.TblDesignKeyType.PK)
		{
			result = "PRIMARY";
		}
		else if (type == DbDomainConst.TblDesignKeyType.INDEX)
		{
			result = "INDEX";
		}
		else if (type == DbDomainConst.TblDesignKeyType.UNIQUE)
		{
			result = "UNIQUE";
		}
		else if (type == DbDomainConst.TblDesignKeyType.FULLTEXT)
		{
			result = "FULLTEXT";
		}
		
		
		return result;
	}

	public void setTypeFromXml(String value) {
		if ("PRIMARY".equals(value) )
		{
			this.type = DbDomainConst.TblDesignKeyType.PK;
		}
		else if ("INDEX".equals(value))
		{
			this.type = DbDomainConst.TblDesignKeyType.INDEX;
		}
		else if ("UNIQUE".equals(value))
		{
			this.type = DbDomainConst.TblDesignKeyType.UNIQUE;
		}
		else if ("FULLTEXT".equals(value))
		{
			this.type = DbDomainConst.TblDesignKeyType.FULLTEXT;
		}

	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@XmlElement(name = "part")
	public List<String> getKeyItems() {
		return keyItems;
	}

	public void setKeyItems(List<String> keyItems) {
		this.keyItems = keyItems;
	}

	@XmlAttribute(name = "keyid")
	public Long getKeyId() {
		return keyId;
	}

	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}
	
	public Long getTableDesignId() {
		return tableDesignId;
	}

	public void setTableDesignId(Long tableDesignId) {
		this.tableDesignId = tableDesignId;
	}

	@Override
	public String toString() {

		StringBuffer str = new StringBuffer();

		str.append("name=");
		str.append(",Type=");
		str.append(getType());
		str.append("\n");
		str.append("part=");
		str.append(keyItems.toString());
		str.append("\n");

		return str.toString();

	}

	public String getStrKeyItems() {
		return strKeyItems;
	}

	public void setStrKeyItems(String strKeyItems) {
		this.strKeyItems = strKeyItems;
	}
}
