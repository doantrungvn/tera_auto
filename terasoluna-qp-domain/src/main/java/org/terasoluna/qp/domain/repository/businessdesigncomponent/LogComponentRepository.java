package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.LogComponent;

public interface LogComponentRepository {
	Long getSequencesLogComponent(@Param("size") Integer size);

	int registerLogComponent(@Param("LogComponentItems") List<LogComponent> LogComponentItems);

	List<LogComponent> findLogComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<LogComponent> findAllLogComponentByModule(@Param("moduleId") Long moduleId);

	List<LogComponent> findAllLogComponentByModuleCommon(@Param("projectId") Long projectId);
		
}
