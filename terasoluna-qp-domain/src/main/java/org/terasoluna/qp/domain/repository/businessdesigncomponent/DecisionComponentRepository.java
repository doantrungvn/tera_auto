package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.DecisionComponent;
import org.terasoluna.qp.domain.model.DecisionInputValue;
import org.terasoluna.qp.domain.model.DecisionOutputValue;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.SequenceLogic;


public interface DecisionComponentRepository {

	Long getSequencesDecisionComponent(@Param("size") Integer size);

	Long getSequencesDecisionInputValue(@Param("size") Integer size);

	Long getSequencesDecisionOutputValue(@Param("size") Integer size);

	int registerDecisionComponent(@Param("decisionComponentItems") List<DecisionComponent> decisionComponentItems);

	List<DecisionComponent> findDecisionComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<DecisionInputValue> findDecisionInputValueByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<DecisionOutputValue> findDecisionOutputValueByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<DecisionComponent> findAllDecisionComponentByModuleId(@Param("moduleId") Long moduleId);

	List<DecisionInputValue> findAllDecisionInputValueByModuleId(@Param("moduleId") Long moduleId);

	List<DecisionOutputValue> findAllDecisionOutputValueByModuleId(@Param("moduleId") Long moduleId);

	List<DecisionComponent> findAllDecisionComponentByProjectId(@Param("projectId") Long projectId);

	List<DecisionInputValue> findAllDecisionInputValueByProjectId(@Param("projectId") Long projectId);

	List<DecisionOutputValue> findAllDecisionOutputValueByProjectId(@Param("projectId") Long projectId);
	
	SequenceLogic getSequenceLogicCommonComponentGeneration(@Param("decisionComponentId") Long decisionComponentId);

	BusinessDesign getBusinessDesignCommonComponentGeneration(@Param("decisionComponentId") Long decisionComponentId);

	List<DecisionComponent> findAllDecisionComponentByModuleCommon(@Param("projectId") Long projectId);

	List<DecisionInputValue> findAllDecisionInputValueByModuleCommon(@Param("projectId") Long projectId);

	List<DecisionOutputValue> findAllDecisionOutputValueByModuleCommon(@Param("projectId") Long projectId);
	
	int modifyDecisionComponent(@Param("decisionTable") DecisionTable decisionTable);
	
	List<DecisionComponent> findDecisionComponentByDecisionTbId(@Param("decisionTbId") Long decisionTbId);
	
	List<DecisionInputValue> findDecisionInputValueByDecisionTbId(@Param("decisionTbId") Long decisionTbId);

	List<DecisionOutputValue> findDecisionOutputValueByDecisionTbId(@Param("decisionTbId") Long decisionTbId);
	
	int deleteDecisionInputValueByDecisionComponent(@Param("lstComponents") List<DecisionComponent> lstComponents);
	
	int deleteDecisionOutputValueByDecisionComponent(@Param("lstComponents") List<DecisionComponent> lstComponents);
	
	int deleteDecisionInputValueByDecisionTblId(@Param("decisionTableId") Long decisionTableId);
	
	int deleteDecisionOutputValueByDecisionTblId(@Param("decisionTableId") Long decisionTableId);
}
