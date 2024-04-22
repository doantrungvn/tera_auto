package org.terasoluna.qp.domain.repository.codelist;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.Autocomplete;
import org.terasoluna.qp.domain.model.CodeList;
import org.terasoluna.qp.domain.model.Module;

public interface CodeListRepository {
	List<CodeList> getBySearchCriteria(@Param("codeList") CodeListSearchCriteria codeList,
			@Param("pageable") Pageable pageable);

	Long countBySearchCriteria(@Param("codeList") CodeListSearchCriteria criteria);
	
	//get one Codelist
	CodeList getCodeList(long codeListId);

	//register codelist
	void registerCodelist(CodeList codeList);
	//modify codelist
	boolean modifyCodelist(CodeList codeList);

	int deleteCodelist(Long codeListId);

	CodeList checkExitsNameOrCode(CodeList codelist);
	
	Long countNameCodeByCodeListId(CodeList codeList);
	
	long checkCodelistUsedByOther(Long codeListId);
	
	List<Autocomplete> getDomainDesignUsedCodelist(Long codeListId);
	
	List<Autocomplete> getTableDesignUsedCodelist(Long codeListId);
	
	List<Autocomplete> getScreenItemUsedCodelist(@Param("languageId") Long languageId,@Param("codeListId") Long codeListId);
	
	int countByModuleId(@Param("module") Module module);
	
	List<Long> getAllCodeListId(@Param("projectId") Long projectId);
	
	Long[] checkCodeListExists(@Param("listOfId") List<Long> listOfId);
	
	List<CodeList> getCodeListByProject(Long projectId);
}
