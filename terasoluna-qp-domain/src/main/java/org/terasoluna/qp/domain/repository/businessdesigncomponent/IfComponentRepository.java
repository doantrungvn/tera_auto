package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.IfComponent;


public interface IfComponentRepository {

	Long getSequencesIfComponent(@Param("size") Integer size);

	int registerIfComponent(@Param("ifComponentItems") List<IfComponent> ifComponentItems);

	List<IfComponent> findIfComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<IfComponent> findIfComponentByModuleId(@Param("moduleId") Long moduleId);

	List<IfComponent> findIfComponentByProject(@Param("projectId") Long projectId);

	List<IfComponent> findIfComponentByModuleCommon(@Param("languageId") Long languageId, @Param("projectId") Long projectId);
}
