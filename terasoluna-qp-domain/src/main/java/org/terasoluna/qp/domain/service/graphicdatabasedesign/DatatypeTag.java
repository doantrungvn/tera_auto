package org.terasoluna.qp.domain.service.graphicdatabasedesign;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "org.terasoluna.qp.domain.service.graphicdatabasedesign.SqlDesignXml")
public class DatatypeTag {
	private String dbType;

	private List<GroupDataTypeDB> listOfGroupDBType = new ArrayList<GroupDataTypeDB>();

	public DatatypeTag() {

	}

	@XmlAttribute(name = "db")
	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	@XmlElement(name = "group")
	public List<GroupDataTypeDB> getListOfGroupDBType() {
		return listOfGroupDBType;
	}

	public void setListOfGroupDBType(List<GroupDataTypeDB> listOfGroup) {
		this.listOfGroupDBType = listOfGroup;
	}
}
