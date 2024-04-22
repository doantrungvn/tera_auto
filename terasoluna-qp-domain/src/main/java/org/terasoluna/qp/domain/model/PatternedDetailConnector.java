package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class PatternedDetailConnector implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long connectorSource;

	private Long connectorDest;

	private Integer connectorType;

	private Long patternedComponentId;

	public Long getConnectorSource() {
		return connectorSource;
	}

	public void setConnectorSource(Long connectorSource) {
		this.connectorSource = connectorSource;
	}

	public Long getConnectorDest() {
		return connectorDest;
	}

	public void setConnectorDest(Long connectorDest) {
		this.connectorDest = connectorDest;
	}

	public Integer getConnectorType() {
		return connectorType;
	}

	public void setConnectorType(Integer connectorType) {
		this.connectorType = connectorType;
	}

	public Long getPatternedComponentId() {
	    return patternedComponentId;
    }

	public void setPatternedComponentId(Long patternedComponentId) {
	    this.patternedComponentId = patternedComponentId;
    }

}
