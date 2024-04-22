package org.terasoluna.qp.domain.service.commonobjectdefinition;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonObjectDefinition;

@Service
public interface CommonObjectDefinitionSharedService {
	CommonObjectDefinition getCommonObjectDefinition(Long objectId, Long modulueId, Long projectId, Long languageId, Integer level);
}
