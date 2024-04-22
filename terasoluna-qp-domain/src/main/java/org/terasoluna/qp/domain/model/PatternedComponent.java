package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PatternedComponent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long patternedComponentId;

	private String image;

	private String name;

	private String remark;

	private Long projectId;

	private List<PatternedDetail> lstPatternedDetails = new ArrayList<PatternedDetail>();

	private List<PatternedDetailConnector> lstPatternedDetailConnectors = new ArrayList<PatternedDetailConnector>();

	private String jsonLstPatternedDetails;

	private String jsonLstPatternedDetailConnectors;

	public Long getPatternedComponentId() {
		return patternedComponentId;
	}

	public void setPatternedComponentId(Long patternedComponentId) {
		this.patternedComponentId = patternedComponentId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public List<PatternedDetail> getLstPatternedDetails() {
		return lstPatternedDetails;
	}

	public void setLstPatternedDetails(List<PatternedDetail> lstPatternedDetails) {
		this.lstPatternedDetails = lstPatternedDetails;
	}

	public List<PatternedDetailConnector> getLstPatternedDetailConnectors() {
		return lstPatternedDetailConnectors;
	}

	public void setLstPatternedDetailConnectors(List<PatternedDetailConnector> lstPatternedDetailConnectors) {
		this.lstPatternedDetailConnectors = lstPatternedDetailConnectors;
	}

	public String getJsonLstPatternedDetails() {
	    return jsonLstPatternedDetails;
    }

	public void setJsonLstPatternedDetails(String jsonLstPatternedDetails) {
	    this.jsonLstPatternedDetails = jsonLstPatternedDetails;
    }

	public String getJsonLstPatternedDetailConnectors() {
	    return jsonLstPatternedDetailConnectors;
    }

	public void setJsonLstPatternedDetailConnectors(String jsonLstPatternedDetailConnectors) {
	    this.jsonLstPatternedDetailConnectors = jsonLstPatternedDetailConnectors;
    }

}
