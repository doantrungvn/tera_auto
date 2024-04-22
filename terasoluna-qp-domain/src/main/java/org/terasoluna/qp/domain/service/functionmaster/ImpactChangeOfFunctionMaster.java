package org.terasoluna.qp.domain.service.functionmaster;

import java.util.ArrayList;
import java.util.List;

import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.service.impactchange.ImpactChangeDesign;

public class ImpactChangeOfFunctionMaster extends ImpactChangeDesign{

	private List<BusinessDesign> lstUsedBusinessDesign = new ArrayList<BusinessDesign>();
	
	private List<DecisionTable> lstUsedDecisionTable = new ArrayList<DecisionTable>();

	public List<BusinessDesign> getLstUsedBusinessDesign() {
	    return lstUsedBusinessDesign;
    }

	public void setLstUsedBusinessDesign(List<BusinessDesign> lstUsedBusinessDesign) {
	    this.lstUsedBusinessDesign = lstUsedBusinessDesign;
    }

	public List<DecisionTable> getLstUsedDecisionTable() {
	    return lstUsedDecisionTable;
    }

	public void setLstUsedDecisionTable(List<DecisionTable> lstUsedDecisionTable) {
	    this.lstUsedDecisionTable = lstUsedDecisionTable;
    }
}
