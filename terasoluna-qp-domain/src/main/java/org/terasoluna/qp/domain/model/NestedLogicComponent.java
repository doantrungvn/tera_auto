package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class NestedLogicComponent implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long nestedLogicComponentId;

	private String label;

	private String remark;

	private String sequenceLogicId;

	private List<SequenceLogic> arrComponent = new ArrayList<SequenceLogic>();

	private List<SequenceConnector> arrConnection = new ArrayList<SequenceConnector>();

	private String level;

	public Long getNestedLogicComponentId() {
		return nestedLogicComponentId;
	}

	public void setNestedLogicComponentId(Long nestedLogicComponentId) {
		this.nestedLogicComponentId = nestedLogicComponentId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSequenceLogicId() {
		return sequenceLogicId;
	}

	public void setSequenceLogicId(String sequenceLogicId) {
		this.sequenceLogicId = sequenceLogicId;
	}

	public List<SequenceLogic> getArrComponent() {
		return arrComponent;
	}

	public void setArrComponent(List<SequenceLogic> arrComponent) {
		this.arrComponent = arrComponent;
	}

	public List<SequenceConnector> getArrConnection() {
		return arrConnection;
	}

	public void setArrConnection(List<SequenceConnector> arrConnection) {
		this.arrConnection = arrConnection;
	}

	public String getLevel() {
	    return level;
    }

	public void setLevel(String level) {
	    this.level = level;
    }

}
