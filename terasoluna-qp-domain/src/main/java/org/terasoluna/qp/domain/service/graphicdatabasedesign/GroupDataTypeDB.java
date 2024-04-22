package org.terasoluna.qp.domain.service.graphicdatabasedesign;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "org.terasoluna.qp.domain.service.graphicdatabasedesign.DatatypeTag")
public class GroupDataTypeDB implements Comparable<GroupDataTypeDB>{

	private String label;
	private String color;
	private Integer datatypeFlg;
	private int itemSeqNo;
	
	private List<DataType> listOfDataType = new ArrayList<DataType>();
	
	public GroupDataTypeDB() {

	}

	@XmlAttribute(name = "label")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@XmlAttribute(name = "color")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<DataType> getListOfDataType() {
		return listOfDataType;
	}

	@XmlElement(name = "type")
	public void setListOfDataType(List<DataType> listOfDataType) {
		this.listOfDataType = listOfDataType;
	}

	
	
	public int getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(int itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	@Override
	public int compareTo(GroupDataTypeDB o) {
		// TODO Auto-generated method stub
		if (this.getItemSeqNo() < o.getItemSeqNo()) {
			return -1;
		} else {
			return 1;
		}
	}

	@XmlAttribute(name = "groupId")
	public Integer getDatatypeFlg() {
		return datatypeFlg;
	}

	public void setDatatypeFlg(Integer datatypeFlg) {
		this.datatypeFlg = datatypeFlg;
	}

}
