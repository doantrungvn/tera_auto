package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.ExecutionComponent;
import org.terasoluna.qp.domain.model.ExecutionInputValue;
import org.terasoluna.qp.domain.model.ExecutionOutputValue;

public interface ExecutionComponentRepository {
	Long getSequencesExecutionComponent(@Param("size") Integer size);

	Long getSequencesExecutionInputValue(@Param("size") Integer size);

	Long getSequencesExecutionOutputValue(@Param("size") Integer size);

	int registerExecutionComponent(@Param("executionComponentItems") List<ExecutionComponent> executionComponentItems);

	List<ExecutionComponent> findExecutionComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<ExecutionComponent> findExecutionComponentByModuleId(@Param("moduleId") Long moduleId);

	int registerExecutionInputValue(@Param("executionInputValueItems") List<ExecutionInputValue> executionInputValueItems);

	int registerExecutionOutputValue(@Param("executionOutputValueItems") List<ExecutionOutputValue> executionOutputValueItems);

	List<ExecutionInputValue> findExecutionInputValueByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<ExecutionInputValue> findExecutionInputValueByModuleId(@Param("moduleId") Long moduleId);

	List<ExecutionOutputValue> findExecutionOutputValueByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<ExecutionOutputValue> findExecutionOutputValueByModuleId(@Param("moduleId") Long moduleId);

	boolean deleteExecutionInputBySqlDesignInputId(Long sqlDesignInputId);

	boolean deleteExecutionOutputBySqlDesignOutputId(Long sqlDesignOutputId);
	
	List<ExecutionComponent> findExecutionComponentByProjectId(@Param("projectId") Long projectId);

	List<ExecutionInputValue> findExecutionInputValueByProjectId(@Param("projectId") Long projectId);

	List<ExecutionOutputValue> findExecutionOutputValueByProjectId(@Param("projectId") Long projectId);
	
	List<ExecutionComponent> findExecutionComponentByModuleCommon(@Param("projectId") Long projectId);

	List<ExecutionInputValue> findExecutionInputValueByModuleCommon(@Param("projectId") Long projectId);

	List<ExecutionOutputValue> findExecutionOutputValueByModuleCommon(@Param("projectId") Long projectId);
	
}
