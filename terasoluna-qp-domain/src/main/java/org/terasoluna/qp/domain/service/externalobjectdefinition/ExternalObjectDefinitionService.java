package org.terasoluna.qp.domain.service.externalobjectdefinition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectDefinitionSearchCriteria;

@Service
public interface ExternalObjectDefinitionService {

	Page<ExternalObjectDefinition> findPageByCriteria(
			ExternalObjectDefinitionSearchCriteria criteria, Pageable pageable,
			CommonModel common);

	ExternalObjectDefinition registerExternalObjectDefinition(
			ExternalObjectDefinition externalObjectDefinition,
			CommonModel common);

	boolean checkExternalObjectDefinitionById(Long externalObjectDefinitionId);

	ExternalObjectDefinition findExternalObjectDefinition(
			Long externalObjectDefinitionId, Integer level, CommonModel common);

	void modifyExternalObjectDefinition(
			ExternalObjectDefinition externalObjectDefinition,
			CommonModel common);

	ExternalObjectDefinition deleteExternalObjectDefinition(
			ExternalObjectDefinition externalObjectDefinition,
			CommonModel common);

}
