package org.terasoluna.qp.domain.service.graphicdatabasedesign;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.terasoluna.qp.domain.model.TableDesign;

@XmlRootElement(name = "sql")
public class SqlDesignXml {

	private List<TableDesign> listOfTables;

	private List<DatatypeTag> listOfDatatypes;

	public SqlDesignXml() {

	}

	@XmlElement(name = "datatypes")
	public List<DatatypeTag> getListOfDatatypes() {
		return listOfDatatypes;
	}

	public void setListOfDatatypes(List<DatatypeTag> listOfDatatypes) {
		this.listOfDatatypes = listOfDatatypes;
	}
	
	@XmlElement(name = "table")
	public List<TableDesign> getListOfTables() {
		return listOfTables;
	}

	public void setListOfTables(List<TableDesign> listOfTables) {
		this.listOfTables = listOfTables;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();

		for (TableDesign tbTag : listOfTables) {
			strBuilder.append(tbTag.toString());
		}
		return strBuilder.toString();
	}
}
