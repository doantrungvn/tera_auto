package org.terasoluna.qp.domain.repository.commonobjectdefinition;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.CommonObjectDefinition;

public interface CommonObjectDefinitionRepository {

	Long countBySearchCriteria(@Param("criteria") CommonObjectDefinitionSearchCriteria criteria);

	List<CommonObjectDefinition> findPageBySearchCriteria(@Param("criteria") CommonObjectDefinitionSearchCriteria criteria, @Param("pageable") Pageable pageable);
	
	CommonObjectDefinition findCommonObjectDefinition(Long commonObjectDefinitionSearchCriteriaId);
	
	void registerCommonObjectDefinition(CommonObjectDefinition commonObjectDefinition);
	
	long checkCommonObjectDefinitionUsedByOther(Long commonObjectDefinitionId);
	
	int deleteCommonObjectDefinition(Long commonObjectDefinitionId);
	
	Long countNameCodeByCommonObjectDefinitionId(CommonObjectDefinition commonObjectDefinition);
	
	boolean modifyCommonObjectDefinition(CommonObjectDefinition commonObjectDefinition);
	
	Long getSequencesCommonObjectAttribute(@Param("size") Integer size);
	
	
	List<CommonObjectDefinition> findAllOfProject(@Param("criteria") CommonObjectDefinitionSearchCriteria criteria);
	
	List<CommonObjectDefinition> findAllByModuleType(@Param("projectId") Long projectId, @Param("languageId") Long languageId, @Param("moduleType") Integer moduleType);
	
	int checkCommonObjectDefinitionExistById(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	List<CommonObjectDefinition> findCommonObjectByUsingExternalObject(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	void modifyCommonObNameForAffectedFunction(CommonObjectDefinition commonObjectDefinition);
	
	List<CommonObjectDefinition> findCommonObjectByUsingCommonObject(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
}
