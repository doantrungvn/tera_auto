package org.terasoluna.qp.domain.repository.commonobjectdefinition;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.CommonObjectAttribute;

public interface CommonObjectAttributeRepository {

	List<CommonObjectAttribute> findCommonObjectAttributeByCommonObjectDefinitionId(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);

	List<CommonObjectAttribute> findCommonObjectAttributeByCommonObject(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);

	List<CommonObjectAttribute> findCommonObjectAttribute(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);

	List<CommonObjectAttribute> findCommonObjectAttributeFillterDataType(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);

	void registerArray(@Param("commonObjectAttribute") List<CommonObjectAttribute> commonObjectAttribute);

	void registerArrayWhenModify(@Param("commonObjectAttributesInsert") List<CommonObjectAttribute> commonObjectAttributesInsert);

	void multiUpdateCommonObjectAttribut(@Param("commonObjectAttributesUpdate") List<CommonObjectAttribute> commonObjectAttributesUpdate);

	void multiDeleteCommonObjectAttribut(@Param("commonObjectAttributesDelete") List<CommonObjectAttribute> commonObjectAttributesDelete);

	int deleteCommonObjectAttribute(Long commonObjectDefinitionId);

	List<CommonObjectAttribute> findAllOfProject(@Param("criteria") CommonObjectDefinitionSearchCriteria criteria);
	
	List<CommonObjectAttribute> findAllByModuleType(@Param("projectId") Long projectId, @Param("languageId") Long languageId, @Param("moduleType") Integer moduleType);

	List<CommonObjectAttribute> findAllCommonObjAttrByModuleType(@Param("projectId") Long projectId, @Param("languageId") Long languageId, @Param("moduleType") Integer moduleType);
}
