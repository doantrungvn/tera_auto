package org.terasoluna.qp.domain.service.businessdesign;

import java.util.ArrayList;
import java.util.List;

import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.service.impactchange.ImpactChangeDesign;

public class ImpactChangeOfCommonBlogic extends ImpactChangeDesign {
	
	private List<BusinessDesign> lstUsedBusinessDesign = new ArrayList<BusinessDesign>();

	public List<BusinessDesign> getLstUsedBusinessDesign() {
		return lstUsedBusinessDesign;
	}

	public void setLstUsedBusinessDesign(List<BusinessDesign> lstUsedBusinessDesign) {
		this.lstUsedBusinessDesign = lstUsedBusinessDesign;
	}

	public ImpactChangeOfCommonBlogic() {
	    super();
    }
}
