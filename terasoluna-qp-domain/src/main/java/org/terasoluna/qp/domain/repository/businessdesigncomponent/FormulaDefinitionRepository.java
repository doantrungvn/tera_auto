package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.DecisionTableConditionItem;
import org.terasoluna.qp.domain.model.FormulaDefinition;
import org.terasoluna.qp.domain.model.FormulaDetail;
import org.terasoluna.qp.domain.model.FormulaMethodInput;
import org.terasoluna.qp.domain.model.FormulaMethodOutput;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.ScreenForm;

public interface FormulaDefinitionRepository {

	Long getSequencesFormulaDefinition(@Param("size") Integer size);

	int registerFormulaDefinition(@Param("formulaDefinitionItems") List<FormulaDefinition> formulaDefinitionItems);

	int registerOneFormulaDefinition(FormulaDefinition formulaDefinitionItem);

	List<FormulaDefinition> findFormulaDefinitionByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	Long getSequencesFormulaDetail(@Param("size") Integer size);

	int registerFormulaDetails(@Param("formulaDetailItems") List<FormulaDetail> formulaDetailItems);

	int registerFormulaDetailsForCheckbox(@Param("formulaDetailItems") List<FormulaDetail> formulaDetailItems);
	
	List<FormulaDetail> findFormulaDetailsByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<FormulaMethodInput> findFormulaMethodInputsByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<FormulaMethodOutput> findFormulaMethodOutputsByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	int deleteFormulaDefinitionByBusinessLogicId(@Param("businessLogicId") Long businessLogicId);

	List<FormulaDetail> findFormulaDetailsByListDecisionItemId(@Param("conditionItem") List<DecisionTableConditionItem> conditionItem);

	List<FormulaMethodInput> findFormulaMethodInputsByListDecisionItemId(@Param("conditionItem") List<DecisionTableConditionItem> conditionItem);

	List<FormulaMethodOutput> findFormulaMethodOutputsByListDecisionItemId(@Param("conditionItem") List<DecisionTableConditionItem> conditionItem);

	int deleteFormulaDefinitionByDecisionTableId(@Param("conditionItem") List<DecisionTableConditionItem> conditionItem);

	List<FormulaDefinition> findFormulaDefinitionForNodeByProject(@Param("projectId") Long projectId);

	List<FormulaDetail> findFormulaDetailsForNodeByProject(@Param("projectId") Long projectId);

	List<FormulaMethodInput> findFormulaMethodInputsForNodeByProject(@Param("projectId") Long projectId);

	List<FormulaMethodOutput> findFormulaMethodOutputsForNodeByProject(@Param("projectId") Long projectId);

	void registerScreenItemStatus(@Param("formulaDefinitions") List<FormulaDefinition> formulaDefinitions);

	List<FormulaDefinition> getAllFormulaDefinition(@Param("projectId") Long projectId);

	List<FormulaDefinition> getFormulaDefinitionByScreenFormId(@Param("lstScreenForm") List<ScreenForm> lstScreenForm);

	int deleteFormulaDetailsByFormulaDefinitionId(@Param("lstFormulaDefinition") List<FormulaDefinition> lstFormulaDefinition);

	int deleteFormulaDefinitionByScreenFormId(@Param("lstScreenForm") List<ScreenForm> lstScreenForm);

	List<FormulaDetail> getFormulaDetailByFormulaDefinition(@Param("lstFormulaDefinition") List<FormulaDefinition> lstFormulaDefinition);

	int deleleFormulaMethodInputByFormulaDetailId(@Param("lstFormulaDetail") List<FormulaDetail> lstFormulaDetail);

	int deleleFormulaMethodOutputByFormulaDetailId(@Param("lstFormulaDetail") List<FormulaDetail> lstFormulaDetail);

	List<FormulaDefinition> findFormulaDefinitionIfNodeByProject(@Param("projectId") Long projectId);

	List<FormulaDetail> findFormulaDetailsIfNodeByProject(@Param("projectId") Long projectId);

	List<FormulaMethodInput> findFormulaMethodInputsIfNodeByProject(@Param("projectId") Long projectId);

	List<FormulaMethodOutput> findFormulaMethodOutputsIfNodeByProject(@Param("projectId") Long projectId);

	List<FormulaDetail> findFormulaDetailsByListScreenForms(@Param("screenForms") List<ScreenForm> screenForms);

	List<FormulaDefinition> findFormulaDefinitionAssignNodeByProject(@Param("projectId") Long projectId);

	List<FormulaDetail> findFormulaDetailsAssignNodeByProject(@Param("projectId") Long projectId);

	List<FormulaMethodInput> findFormulaMethodInputsAssignNodeByProject(@Param("projectId") Long projectId);

	List<FormulaMethodOutput> findFormulaMethodOutputsAssignNodeByProject(@Param("projectId") Long projectId);

	List<FormulaDefinition> findFormulaDefinitionBusinessCheckNodeByProject(@Param("projectId") Long projectId);

	List<FormulaDetail> findFormulaDetailsBusinessCheckNodeByProject(@Param("projectId") Long projectId);

	List<FormulaMethodInput> findFormulaMethodInputsBusinessCheckNodeByProject(@Param("projectId") Long projectId);

	List<FormulaMethodOutput> findFormulaMethodOutputsBusinessCheckNodeByProject(@Param("projectId") Long projectId);

	List<FormulaMethodInput> findFormulaMethodInputsByFormulaDetails(@Param("lstFormulaDetails") List<FormulaDetail> lstFormulaDetails);

	List<FormulaMethodOutput> findFormulaMethodOutputsByFormulaDetails(@Param("lstFormulaDetails") List<FormulaDetail> lstFormulaDetails);

	List<FormulaDetail> findFormulaDetailsByModule(@Param("moduleId") Long moduleId);

	List<FormulaDetail> findFormulaDetailsByModuleCommon(@Param("projectId") Long projectId);

	Long getSequencesFormulaMethodInput(@Param("size") Integer size);
	
	Long getSequencesFormulaMethodOutput(@Param("size") Integer size);

	void updateImpactChangeWhenDeletedInput(@Param("lstMethodInputIds") List<FunctionMethodInput> lstMethodInputIds);
}
