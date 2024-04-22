package org.terasoluna.qp.domain.service.codelist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.CodeList;
import org.terasoluna.qp.domain.model.CodeListDetail;
import org.terasoluna.qp.domain.repository.codelist.CodeListSearchCriteria;

public interface CodeListService {

	Page<CodeList> getBySearchCriteria(CodeListSearchCriteria criteria, Pageable pageable);

	CodeList getCodeList(long codeListId);

	CodeList registerCodelist(CodeList codeList, Long accountId, Long currentProjectId);

	void modifyCodelist(CodeList codeList, Long accountId, Long projectId);

	CodeList deleteCodelist(CodeList codeList, Long accountId, Long projectid);
	
	CodeListDetail[] getCodeListDetailByCodeListId(long codeListId);
	
	/**
	 * Get codelist and all data using it
	 * @param codeListId
	 * @return
	 */
	CodeList getCodeListInformation(long codeListId, Long accountId, Long currentProjectId);
	
	/**
	 * 
	 * @param codeListId
	 * @param projectId
	 * @param accountId
	 * @param checkFixdesign
	 * @return
	 */
	CodeList validateCodeList(long codeListId, Long projectId, Long accountId, boolean checkFixdesign);

}
