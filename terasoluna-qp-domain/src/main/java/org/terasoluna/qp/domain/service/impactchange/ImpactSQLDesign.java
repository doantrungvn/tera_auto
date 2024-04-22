package org.terasoluna.qp.domain.service.impactchange;

import java.util.ArrayList;
import java.util.List;

import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.TableDesign;

public class ImpactSQLDesign extends ImpactChangeDesign {

	private List<BusinessDesign> lstUsedBusinessDesign = new ArrayList<BusinessDesign>();
	
	private List<DomainDesign> lstUsedDomainDesign = new ArrayList<DomainDesign>();
	
	private List<TableDesign> lstUsedTableDesign = new ArrayList<TableDesign>();

	public List<BusinessDesign> getLstUsedBusinessDesign() {
		return lstUsedBusinessDesign;
	}

	public void setLstUsedBusinessDesign(List<BusinessDesign> lstUsedBusinessDesign) {
		this.lstUsedBusinessDesign = lstUsedBusinessDesign;
	}

	public ImpactSQLDesign() {
		super();
	}

	public List<DomainDesign> getLstUsedDomainDesign() {
	    return lstUsedDomainDesign;
    }

	public void setLstUsedDomainDesign(List<DomainDesign> lstUsedDomainDesign) {
	    this.lstUsedDomainDesign = lstUsedDomainDesign;
    }

	public List<TableDesign> getLstUsedTableDesign() {
	    return lstUsedTableDesign;
    }

	public void setLstUsedTableDesign(List<TableDesign> lstUsedTableDesign) {
	    this.lstUsedTableDesign = lstUsedTableDesign;
    }
	
}
