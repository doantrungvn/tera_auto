package org.terasoluna.qp.domain.service.decisiontable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.CommonObjectAttribute;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableSearchCriteria;

public interface DecisionTableService {

	Page<DecisionTable> searchDecisionTable(DecisionTableSearchCriteria decisionTableSearchCriteria, Pageable pageable);

	DecisionTable findOneByDecisionTbId(Long decisionTbId);

	List<BusinessDesign> getListBusinessDesign(DecisionTable decisionTable);

	List<ExternalObjectAttribute> findExternalObjectAttributeByCommonObject(Long externalObjectDefinitionId, Long currentLanguageId, Long currentProjectId);

	List<CommonObjectAttribute> findCommonObjectAttributeByCommonObject(Long commonObjectDefinitionId, Long currentLanguageId, Long currentProjectId);

	void insertProblemList(DecisionTable decisionTable, List<ProblemList> listOfProblem, CommonModel common);

	void registerDecisionTable(DecisionTable decisionTable, CommonModel common);

	void modifyDecisionTable(DecisionTable decisionTable, CommonModel common);

	void deleteDecisionTable(DecisionTable decisionTable, Boolean deleteObjectHasFk, CommonModel common);

	void modifyDesignStatus(DecisionTable decisionTable, CommonModel common);

}
