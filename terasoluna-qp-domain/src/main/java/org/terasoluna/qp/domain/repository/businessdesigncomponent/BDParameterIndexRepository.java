package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.BDParameterIndex;


public interface BDParameterIndexRepository {

	List<BDParameterIndex> findBDParameterIndexByBusinessLogic(@Param("businessLogicId") Long businessLogicId);
	
	List<BDParameterIndex> findBDParameterIndexByModuleId(@Param("moduleId") Long moduleId);
	
	List<BDParameterIndex> findBDParameterIndexByModuleCommon(@Param("projectId") Long projectId);
	
	Long getSequencesBDParameterIndex(@Param("size") Integer size);
}
