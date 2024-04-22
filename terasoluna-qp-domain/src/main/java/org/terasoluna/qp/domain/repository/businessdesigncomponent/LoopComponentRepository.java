package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.LoopComponent;
import org.terasoluna.qp.domain.model.SequenceLogic;


public interface LoopComponentRepository {
	
	int registerLoopComponent(@Param("loopComponentItems") List<LoopComponent> loopComponentItems);
	
	List<LoopComponent> findLoopComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);
	
	List<LoopComponent> findLoopComponentByModuleId(@Param("moduleId") Long moduleId);
	
	List<LoopComponent> findLoopComponentByProjectId(@Param("projectId") Long projectId);
	
	// KhanhTH
	BusinessDesign getBusinessDesignCommonComponentGeneration(@Param("loopComponentId") Long loopComponentId);
	
	// KhanhTH
	SequenceLogic getSequenceLogicCommonComponentGeneration(@Param("loopComponentId") Long loopComponentId);
	
	List<LoopComponent> findLoopComponentByModuleCommon(@Param("projectId") Long projectId);
	
	Long getSequencesLoopComponent(@Param("size") Integer size);
}
