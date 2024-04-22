package org.terasoluna.qp.domain.repository.impactchange;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.DecisionTableConditionItem;
import org.terasoluna.qp.domain.model.FormulaDetail;
import org.terasoluna.qp.domain.model.FormulaMethodInput;
import org.terasoluna.qp.domain.model.FormulaMethodOutput;
import org.terasoluna.qp.domain.model.FunctionMethod;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.SequenceLogic;

public interface ImpactFunctionMasterRepository {
	List<DecisionTable> findDecisionTableByLstProblemLists(@Param("lstProblemLists") List<ProblemList> lstProblemLists);

	List<DecisionTable> findDecisionTableByUsingFunctionMethod(@Param("lstFunctionMethodIds") List<Long> lstFunctionMethodIds);
	
	FunctionMethod findOneFuntionMethodById(@Param("functionMethodId")Long functionMethodId);
	
	List<SequenceLogic>	findSequenceLogicAndFormulaDetailByUsingLstFunctionMethod(@Param("lstFunctionMethods")List<FunctionMethod> lstFunctionMethods);

	List<BusinessDesign> findBusinessDesignByLstProblemLists(@Param("lstProblemLists")List<ProblemList> lstProblemLists);
	
	List<SequenceLogic>	findSequenceLogicByUsingFunctionMethod(@Param("functionMethodId")Long functionMethodId);
	
	List<FormulaMethodInput> findMethodInputsByLstFunctionMethods(@Param("lstFunctionMethods") List<FunctionMethod> lstFunctionMethods);

	List<FormulaMethodOutput> findMethodOutputsByLstFunctionMethods(@Param("lstFunctionMethods") List<FunctionMethod> lstFunctionMethods);
	
	List<FormulaDetail> findFormulaDetailsByLstFunctionMethods(@Param("lstFunctionMethods") List<FunctionMethod> lstFunctionMethods);
	
	List<DecisionTableConditionItem> findConditionItemByByUsingLstFunctionMethod(@Param("lstFunctionMethods")List<FunctionMethod> lstFunctionMethods);
}
