package org.terasoluna.qp.domain.service.autocomplete;

import java.util.List;

import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.service.impactchange.ImpactChangeDesign;

public class ImpactChangeOfAutocompleteDesign extends ImpactChangeDesign{
	
	private List<ScreenDesign> screenDesignsImpacted;	
	private List<TableDesign> tableDesignsImpacted;
	private List<DomainDesign> domainDesignsImpacted;
	public List<ScreenDesign> getScreenDesignsImpacted() {
		return screenDesignsImpacted;
	}
	public void setScreenDesignsImpacted(List<ScreenDesign> screenDesignsImpacted) {
		this.screenDesignsImpacted = screenDesignsImpacted;
	}
	public List<TableDesign> getTableDesignsImpacted() {
		return tableDesignsImpacted;
	}
	public void setTableDesignsImpacted(List<TableDesign> tableDesignsImpacted) {
		this.tableDesignsImpacted = tableDesignsImpacted;
	}
	public List<DomainDesign> getDomainDesignsImpacted() {
		return domainDesignsImpacted;
	}
	public void setDomainDesignsImpacted(List<DomainDesign> domainDesignsImpacted) {
		this.domainDesignsImpacted = domainDesignsImpacted;
	}
}
