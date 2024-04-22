package org.terasoluna.qp.domain.service.impactchange;

public class ImpactChangeDesign {

	private Boolean impactFlag;

	public Boolean getImpactFlag() {
	    return impactFlag;
    }

	public void setImpactFlag(Boolean impactFlag) {
	    this.impactFlag = impactFlag;
    }

	public ImpactChangeDesign() {
	    super();
	    this.impactFlag = false;
    }
}
