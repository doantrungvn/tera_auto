package org.terasoluna.qp.domain.service.sessionmanagement;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.terasoluna.qp.domain.model.SessionManagement;
@XmlRootElement(name = "sql")
public class InitSessionManagement {

	private List<SessionManagement> listOfSessionManagements;

	@XmlElement(name = "session", type = SessionManagement.class)
	public List<SessionManagement> getListOfSessionManagements() {
		return listOfSessionManagements;
	}

	public void setListOfSessionManagements(List<SessionManagement> listOfSessionManagements) {
		this.listOfSessionManagements = listOfSessionManagements;
	}
	
}
