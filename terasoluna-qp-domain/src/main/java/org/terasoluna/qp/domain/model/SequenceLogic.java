package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class SequenceLogic implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3532751705575583187L;

	private String sequenceLogicId;

	private String sequenceLogicName;

	private Integer componentType;

	private Long componentId;

	private Integer sequenceNo;

	private Double xCoordinates;

	private Double yCoordinates;

	private String remark;

	private Long businessLogicId;

	private String parentSequenceLogicId;

	private String imagePath;

	private String actionPath;

	private String cssClass;

	private String strData;

	private Integer tempSequenceId;

	private Integer firstPointForEarch;

	private Integer endPointForEarch;

	private String prefixLabel;

	private Boolean groupFlg = false;

	private String relatedSequenceLogicId;
	
	private Boolean flagHaveConnector;
	
	private String businessLogicCode;
	
	private String businessLogicName;
	
	private String moduleName;
	
	private Long moduleId;
	
	//detect impact
	
	private Long formulaDetailId;

	public String getSequenceLogicId() {
		return sequenceLogicId;
	}

	public void setSequenceLogicId(String sequenceLogicId) {
		this.sequenceLogicId = sequenceLogicId;
	}

	public String getSequenceLogicName() {
		return sequenceLogicName;
	}

	public void setSequenceLogicName(String sequenceLogicName) {
		this.sequenceLogicName = sequenceLogicName;
	}

	public Integer getComponentType() {
		return componentType;
	}

	public void setComponentType(Integer componentType) {
		this.componentType = componentType;
	}

	public Long getComponentId() {
		return componentId;
	}

	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Double getxCoordinates() {
		return xCoordinates;
	}

	public void setxCoordinates(Double xCoordinates) {
		this.xCoordinates = xCoordinates;
	}

	public Double getyCoordinates() {
		return yCoordinates;
	}

	public void setyCoordinates(Double yCoordinates) {
		this.yCoordinates = yCoordinates;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getBusinessLogicId() {
		return businessLogicId;
	}

	public void setBusinessLogicId(Long businessLogicId) {
		this.businessLogicId = businessLogicId;
	}

	public String getParentSequenceLogicId() {
		return parentSequenceLogicId;
	}

	public void setParentSequenceLogicId(String parentSequenceLogicId) {
		this.parentSequenceLogicId = parentSequenceLogicId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getActionPath() {
		return actionPath;
	}

	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getStrData() {
		return strData;
	}

	public void setStrData(String strData) {
		this.strData = strData;
	}

	public String getPrefixLabel() {
		return prefixLabel;
	}

	public void setPrefixLabel(String prefixLabel) {
		this.prefixLabel = prefixLabel;
	}

	public Integer getTempSequenceId() {
		return tempSequenceId;
	}

	public void setTempSequenceId(Integer tempSequenceId) {
		this.tempSequenceId = tempSequenceId;
	}

	public Integer getFirstPointForEarch() {
		return firstPointForEarch;
	}

	public void setFirstPointForEarch(Integer firstPointForEarch) {
		this.firstPointForEarch = firstPointForEarch;
	}

	public Integer getEndPointForEarch() {
		return endPointForEarch;
	}

	public void setEndPointForEarch(Integer endPointForEarch) {
		this.endPointForEarch = endPointForEarch;
	}

	public Boolean getGroupFlg() {
		return groupFlg;
	}

	public void setGroupFlg(Boolean groupFlg) {
		this.groupFlg = groupFlg;
	}

	public String getRelatedSequenceLogicId() {
	    return relatedSequenceLogicId;
    }

	public void setRelatedSequenceLogicId(String relatedSequenceLogicId) {
	    this.relatedSequenceLogicId = relatedSequenceLogicId;
    }

	public Boolean getFlagHaveConnector() {
		return flagHaveConnector;
	}

	public void setFlagHaveConnector(Boolean flagHaveConnector) {
		this.flagHaveConnector = flagHaveConnector;
	}

	public String getBusinessLogicCode() {
	    return businessLogicCode;
    }

	public void setBusinessLogicCode(String businessLogicCode) {
	    this.businessLogicCode = businessLogicCode;
    }

	public Long getModuleId() {
	    return moduleId;
    }

	public void setModuleId(Long moduleId) {
	    this.moduleId = moduleId;
    }

	public String getBusinessLogicName() {
	    return businessLogicName;
    }

	public void setBusinessLogicName(String businessLogicName) {
	    this.businessLogicName = businessLogicName;
    }

	public String getModuleName() {
	    return moduleName;
    }

	public void setModuleName(String moduleName) {
	    this.moduleName = moduleName;
    }

	public Long getFormulaDetailId() {
	    return formulaDetailId;
    }

	public void setFormulaDetailId(Long formulaDetailId) {
	    this.formulaDetailId = formulaDetailId;
    }

}
