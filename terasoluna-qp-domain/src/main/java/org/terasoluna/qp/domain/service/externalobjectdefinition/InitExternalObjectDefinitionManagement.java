package org.terasoluna.qp.domain.service.externalobjectdefinition;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.terasoluna.qp.domain.model.ExternalObjectDefinition;

@XmlRootElement(name = "sql")
public class InitExternalObjectDefinitionManagement {

	private List<ExternalObjectDefinition> listOfExternalObjectDefinition;

	@XmlElement(name = "externalObjDef", type = ExternalObjectDefinition.class)
	public List<ExternalObjectDefinition> getListOfExternalObjectDefinition() {
		return listOfExternalObjectDefinition;
	}

	public void setListOfExternalObjectDefinition(
			List<ExternalObjectDefinition> listOfExternalObjectDefinition) {
		this.listOfExternalObjectDefinition = listOfExternalObjectDefinition;
	}
	
}
