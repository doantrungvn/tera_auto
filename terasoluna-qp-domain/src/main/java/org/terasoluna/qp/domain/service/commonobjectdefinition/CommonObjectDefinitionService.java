package org.terasoluna.qp.domain.service.commonobjectdefinition;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.CommonObjectAttribute;
import org.terasoluna.qp.domain.model.CommonObjectDefinition;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectDefinitionSearchCriteria;

@Service
public interface CommonObjectDefinitionService {
	void setLevelCommonObject(List<CommonObjectAttribute> commonObjectAttributes);

	boolean checkCommonObjectDefinitionExistById(Long commonObjectId);

	Page<CommonObjectDefinition> findPageByCriteria(
			CommonObjectDefinitionSearchCriteria criteria, Pageable pageable,
			CommonModel common);

	CommonObjectDefinition findCommonObjectDefinition(
			Long commonObjectDefinitionSearchCriteriaId, CommonModel common);

	CommonObjectDefinition registerCommonObjectDefinition(
			CommonObjectDefinition commonObjectDefinition, CommonModel common);

	void modifyCommonObjectDefinition(
			CommonObjectDefinition commonObjectDefinition, CommonModel common);

	List<CommonObjectAttribute> findCommonObjectAttributeByCommonObject(
			Long commonObjectDefinitionId, Integer level, CommonModel common);

	List<ExternalObjectAttribute> findExternalObjectAttributeByCommonObject(
			Long externalObjectDefinitionId, CommonModel common);

	CommonObjectDefinition deleteCommonObjectDefinition(
			CommonObjectDefinition commonObjectDefinition, CommonModel common);
}
