package org.terasoluna.qp.domain.service.impactchange;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.CommonObjectDefinition;

@Service
public interface ImpactCommonObjectShareService {
	
	public void detectListAffectedWhenDeleteOfBatch(Long commonObjectDefinitionId, String commonObjectDefinitionCode, CommonModel common);

	public ImpactChangeDesign detectListAffectedWhenDelete(CommonObjectDefinition commonObjectDefinition, CommonModel common, Boolean isRunBatch);

	public void detectListAffectedWhenModifyOfBatch(Long commonObjectDefinitionId, CommonModel common);

	public ImpactChangeDesign detectListAffectedWhenModify(CommonObjectDefinition commonObjectDefinition, CommonModel common, Boolean isRunBatch);

}
