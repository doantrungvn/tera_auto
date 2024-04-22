package org.terasoluna.qp.domain.service.businessdesign;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;

@Service
public interface CommonBusinessDesignShareService {

	public ImpactChangeOfCommonBlogic detectListAffectedWhenModify(BusinessDesign businessDesign,CommonModel common,Boolean isRunBatch);
	
	public void detectListAffectedWhenModifyOfBatch(Long businessLogicId,CommonModel common);
	
	public ImpactChangeOfCommonBlogic detectListAffectedWhenDelete(BusinessDesign businessDesign,CommonModel common,Boolean isRunBatch);
	
	public void detectListAffectedWhenDeleteOfBatch(Long businessLogicId,String businessLogicCode,CommonModel common);

}
