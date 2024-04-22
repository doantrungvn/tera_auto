package org.terasoluna.qp.domain.service.impactchange;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;

@Service
public interface ImpactExternalObjectShareService {

	public void detectListAffectedWhenDeleteOfBatch(Long externalObjectDefinitionId, String externalObjectDefinitionCode, CommonModel common);

	public ImpactChangeDesign detectListAffectedWhenDelete(ExternalObjectDefinition externalObjectDefinition, CommonModel common, Boolean isRunBatch);

	public void detectListAffectedWhenModifyOfBatch(Long externalObjectDefinitionId, CommonModel common);

	public ImpactChangeDesign detectListAffectedWhenModify(ExternalObjectDefinition externalObjectDefinition, CommonModel common, Boolean isRunBatch);
}
