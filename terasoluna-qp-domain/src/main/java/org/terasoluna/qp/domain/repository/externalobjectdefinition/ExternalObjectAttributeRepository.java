package org.terasoluna.qp.domain.repository.externalobjectdefinition;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.repository.commonobjectdefinition.CommonObjectDefinitionSearchCriteria;

public interface ExternalObjectAttributeRepository {
	List<ExternalObjectAttribute> findExternalObjectAttributeByExternalObjectDefinitionId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);

	List<ExternalObjectAttribute> findExternalObjectAttributeByExternalObject(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);

	List<ExternalObjectAttribute> findExternalObjectAttribute(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);

	List<ExternalObjectAttribute> findExternalObjectAttributeFillterDataType(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);

	void registerArray(@Param("externalObjectAttribute") List<ExternalObjectAttribute> externalObjectAttribute);

	void registerArrayWhenModify(@Param("externalObjectAttributesInsert") List<ExternalObjectAttribute> externalObjectAttributesInsert);

	void multiUpdateExternalObjectAttribut(@Param("externalObjectAttributesUpdate") List<ExternalObjectAttribute> externalObjectAttributesUpdate);

	void multiDeleteExternalObjectAttribut(@Param("externalObjectAttributesDelete") List<ExternalObjectAttribute> externalObjectAttributesDelete);

	int deleteExternalObjectAttribute(Long externalObjectDefinitionId);

	List<ExternalObjectAttribute> findAllOfProject(@Param("criteria") ExternalObjectDefinitionSearchCriteria criteria);
	
	List<ExternalObjectAttribute> findAllOfProject(@Param("criteria") CommonObjectDefinitionSearchCriteria criteria);
	
	List<ExternalObjectAttribute> findOnlyExternalObjectAttributeByExternalObjectDefinitionId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
	
	List<ExternalObjectAttribute> findOnlyExternalObjectAttributeByExternalOBId(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);
}
