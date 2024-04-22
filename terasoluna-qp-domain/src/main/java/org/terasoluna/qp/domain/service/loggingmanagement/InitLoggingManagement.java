package org.terasoluna.qp.domain.service.loggingmanagement;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.terasoluna.qp.domain.model.Log;
@XmlRootElement(name = "sql")
public class InitLoggingManagement {

	private List<Log> listOfLogginManagements;

	@XmlElement(name = "loggingManagement", type = Log.class)
	public List<Log> getListOfSessionManagements() {
		return this.listOfLogginManagements;
	}

	public void setListOfSessionManagements(List<Log> listOfLogginManagements) {
		this.listOfLogginManagements = listOfLogginManagements;
	}
	
}
