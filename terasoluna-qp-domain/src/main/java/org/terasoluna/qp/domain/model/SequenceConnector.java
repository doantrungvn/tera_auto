package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class SequenceConnector implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6395483931302463073L;

	private Long sequenceConnectorId;

	private String connectorSource;

	private String connectorDest;

	private String connectorType;

	private Boolean isParent = true;

	public Long getSequenceConnectorId() {
		return sequenceConnectorId;
	}

	public void setSequenceConnectorId(Long sequenceConnectorId) {
		this.sequenceConnectorId = sequenceConnectorId;
	}

	public String getConnectorSource() {
		return connectorSource;
	}

	public void setConnectorSource(String connectorSource) {
		this.connectorSource = connectorSource;
	}

	public String getConnectorDest() {
		return connectorDest;
	}

	public void setConnectorDest(String connectorDest) {
		this.connectorDest = connectorDest;
	}

	public String getConnectorType() {
		return connectorType;
	}

	public void setConnectorType(String connectorType) {
		this.connectorType = connectorType;
	}

	public Boolean getIsParent() {
	    return isParent;
    }

	public void setIsParent(Boolean isParent) {
	    this.isParent = isParent;
    }

}
