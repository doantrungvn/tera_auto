package org.terasoluna.qp.domain.repository.externalobjectdefinition;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectDefinitionSearchCriteria;

public interface ExternalObjectDefinitionRepository {

	Long countBySearchCriteria(@Param("criteria") ExternalObjectDefinitionSearchCriteria criteria);

	List<ExternalObjectDefinition> findPageBySearchCriteria(@Param("criteria") ExternalObjectDefinitionSearchCriteria criteria, @Param("pageable") Pageable pageable);
	
	ExternalObjectDefinition findExternalObjectDefinition(Long externalObjectDefinitionSearchCriteriaId);
	
	void registerExternalObjectDefinition(ExternalObjectDefinition externalObjectDefinition);
	
	long checkExternalObjectDefinitionUsedByOther(Long externalObjectDefinitionId);
	
	int deleteExternalObjectDefinition(Long externalObjectDefinitionId);
	
	Long countNameCodeByExternalObjectDefinitionId(ExternalObjectDefinition externalObjectDefinition);
	
	boolean modifyExternalObjectDefinition(ExternalObjectDefinition externalObjectDefinition);
	
	List<ExternalObjectDefinition> findAllOfProject(@Param("criteria") ExternalObjectDefinitionSearchCriteria criteria);
	
	List<ExternalObjectDefinition> findAllOfProject(@Param("criteria") CommonObjectDefinitionSearchCriteria criteria);

	Long getSequencesExternalObjectAttribute(int i);
	
	ExternalObjectDefinition findExtObjDefIsMultipartFileByProjectId(@Param("projectId") Long projectId);
	
	int checkExternalObjectDefinitionById(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	void modifyExternalObNameForAffectedFunction(ExternalObjectDefinition externalObjectDefinition);
	
	List<ExternalObjectDefinition> findExternalObjectByUsingExternalObject(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
}
