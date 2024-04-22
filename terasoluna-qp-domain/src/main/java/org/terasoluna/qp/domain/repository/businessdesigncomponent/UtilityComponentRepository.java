package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.UtilityComponent;

public interface UtilityComponentRepository {
	Long getSequencesUtilityComponent(@Param("size") Integer size);

	int registerUtilityComponent(@Param("utilityComponentItems") List<UtilityComponent> utilityComponentItems);

	List<UtilityComponent> findUtilityComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<UtilityComponent> findAllUtilityComponent(@Param("moduleId") Long moduleId);

	List<UtilityComponent> findAllUtilityComponentByModuleId(@Param("languageId") Long languageId, @Param("moduleId") Long moduleId);

	List<UtilityComponent> findAllUtilityComponentByModuleCommon(@Param("languageId") Long languageId, @Param("projectId") Long projectId);
		
}
