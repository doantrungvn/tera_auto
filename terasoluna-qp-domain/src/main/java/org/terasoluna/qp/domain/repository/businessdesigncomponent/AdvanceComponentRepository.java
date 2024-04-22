package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.AdvanceComponent;
import org.terasoluna.qp.domain.model.AdvanceInputValue;
import org.terasoluna.qp.domain.model.AdvanceOutputValue;

public interface AdvanceComponentRepository {

	Long getSequencesAdvanceComponent(@Param("size") Integer size);

	Long getSequencesAdvanceInputBean(@Param("size") Integer size);

	Long getSequencesAdvanceOutputBean(@Param("size") Integer size);

	int registerAllAdvanceComponent(@Param("advanceComponentItems") List<AdvanceComponent> AdvanceComponentItems);

	List<AdvanceComponent> findAdvanceComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);
	
	List<AdvanceComponent> findAdvanceComponentByModule(@Param("moduleId") Long moduleId);

	List<AdvanceInputValue> findAdvanceInputValueByBusinessLogic(@Param("businessLogicId") Long businessLogicId);
	
	List<AdvanceInputValue> findAdvanceInputValueByModule(@Param("moduleId") Long moduleId);

	List<AdvanceOutputValue> findAdvanceOutputValueByBusinessLogic(@Param("businessLogicId") Long businessLogicId);
	
	List<AdvanceOutputValue> findAdvanceOutValueByModule(@Param("moduleId") Long moduleId);
	
	List<AdvanceComponent> findAdvanceComponentByModuleCommon(@Param("projectId") Long projectId);
	
	List<AdvanceInputValue> findAdvanceInputValueByModuleCommon(@Param("projectId") Long projectId);

	List<AdvanceOutputValue> findAdvanceOutValueByModuleCommon(@Param("projectId") Long projectId);
}
