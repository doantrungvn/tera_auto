package org.terasoluna.qp.domain.service.functionmaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.ProblemList;

public class ImpactOfFunctionMaster {

	private List<BusinessDesign> lstBusinessDesigns = new ArrayList<BusinessDesign>();
	
	private List<DecisionTable> lstDecisionTables = new ArrayList<DecisionTable>();
	
	private List<FunctionMethodInput> lstDeletedInputs = new ArrayList<FunctionMethodInput>();
	
	private List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
	
	private Map<String,BusinessDesign> mapUsedBusinessDesign = new HashMap<String,BusinessDesign>();
	
	private Map<String,DecisionTable> mapUsedDecisionTable = new HashMap<String,DecisionTable>();

	public List<BusinessDesign> getLstBusinessDesigns() {
	    return lstBusinessDesigns;
    }

	public void setLstBusinessDesigns(List<BusinessDesign> lstBusinessDesigns) {
	    this.lstBusinessDesigns = lstBusinessDesigns;
    }

	public List<DecisionTable> getLstDecisionTables() {
	    return lstDecisionTables;
    }

	public void setLstDecisionTables(List<DecisionTable> lstDecisionTables) {
	    this.lstDecisionTables = lstDecisionTables;
    }

	public List<FunctionMethodInput> getLstDeletedInputs() {
	    return lstDeletedInputs;
    }

	public void setLstDeletedInputs(List<FunctionMethodInput> lstDeletedInputs) {
	    this.lstDeletedInputs = lstDeletedInputs;
    }

	public List<ProblemList> getLstProblemLists() {
	    return lstProblemLists;
    }

	public void setLstProblemLists(List<ProblemList> lstProblemLists) {
	    this.lstProblemLists = lstProblemLists;
    }

	public Map<String,BusinessDesign> getMapUsedBusinessDesign() {
	    return mapUsedBusinessDesign;
    }

	public void setMapUsedBusinessDesign(Map<String,BusinessDesign> mapUsedBusinessDesign) {
	    this.mapUsedBusinessDesign = mapUsedBusinessDesign;
    }

	public Map<String,DecisionTable> getMapUsedDecisionTable() {
	    return mapUsedDecisionTable;
    }

	public void setMapUsedDecisionTable(Map<String,DecisionTable> mapUsedDecisionTable) {
	    this.mapUsedDecisionTable = mapUsedDecisionTable;
    }
}
