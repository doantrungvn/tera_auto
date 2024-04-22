package org.terasoluna.qp.domain.repository.decisiontable;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.DecisionTable;

public interface DecisionTableRepository {

	DecisionTable findOneByDecisionTbId(Long decisionTbId);

	List<DecisionTable> findPageBySearchCriteria(@Param("criteria") DecisionTableSearchCriteria criteria, @Param("pageable") Pageable pageable);

	long countBySearchCriteria(@Param("criteria") DecisionTableSearchCriteria criteria);

	List<BusinessDesign> findAllBussinessLogicByDecisionId(Long decisionTbId);

	Long countNameCodeExist(DecisionTable decisionTable);

	int modify(DecisionTable decisionTable);

	int modifyDesignStatus(DecisionTable decisionTable);

	Long register(DecisionTable decisionTable);

	HashMap<String, Long> countReferByDecisionTableId(Long decisionTbId);

	void deleteDecisionTable(Long decisionTbId);

	List<DecisionTable> findDecisionByListId(@Param("decisionTableIds") List<Long> decisionTableIds);

	void deleteInputValue(Long decisionInputBeanId);

	void deleteOutputValue(Long decisionOutputBeanId);

	List<DecisionTable> findAllDecisionByProjectId(@Param("projectId") Long projectId);

	List<DecisionTable> findDecisionTableByUsingExternalOb(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);

	List<DecisionTable> findDecisionTableByUsingCommonOb(@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);

	List<DecisionTable> findDecisionTableByUsingFunctionMethod(@Param("lstFunctionMethodIds") List<Long> lstFunctionMethodIds);

	List<DecisionTable> findDecisionTableUsingMethodOutput(@Param("lstMethodOutputIds") List<Long> lstMethodOutputIds);

}
