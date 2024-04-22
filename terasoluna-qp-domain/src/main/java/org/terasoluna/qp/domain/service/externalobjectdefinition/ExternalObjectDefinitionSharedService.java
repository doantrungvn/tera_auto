package org.terasoluna.qp.domain.service.externalobjectdefinition;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;

@Service
public interface ExternalObjectDefinitionSharedService {

	ExternalObjectDefinition getExternalObjectDefinition(Long objectId, Long modulueId, Long projectId, Long languageId, Integer level);
}
