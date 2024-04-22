package org.terasoluna.qp.domain.service.screenitemstatus;

import java.util.List;

import org.terasoluna.qp.domain.model.FormulaDefinition;


public interface ScreenItemStatusService {

	/*Page<CodeList> getBySearchCriteria(CodeListSearchCriteria criteria, Pageable pageable);*/

	/*CodeList getCodeList(long codeListId);*/

	void modifyScreenItemStatus(List<FormulaDefinition> formulaDefinitions, Long projectId, Long screenId);
	
	/*void modifyCodelist(CodeList codeList);

	CodeList deleteCodelist(CodeList codeList);
	
	CodeListDetail[] getCodeListDetailByCodeListId(long codeListId);*/

}
