package org.terasoluna.qp.domain.repository.codelist;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.CodeListDetail;

public interface CodeListDetailRepository {
	
	void registerArray(@Param("codeListDetail") ArrayList<CodeListDetail> codeListDetail);
	
	//get array codelist detail by codelistId
	CodeListDetail[] findCodeListDetailByCodeListId(@Param("codeListId") Long codeListId);
	
	//get list codelist detail by screen id
	List<CodeListDetail> findAllByScreenId(@Param("screenId") Long screenId);
	
	//get list codelist detail by domain datatype id
	List<CodeListDetail> findAllByDomainTblMappingId(@Param("domainTblMappingId") Long domainTblMappingId);
	
	//get codeListDetail by codelist DetailId
	CodeListDetail findCodeListDetail(@Param("clDeatailId") Long codeListDetailId);
	
	//modify codelistDetail
	int modifyCodeListDetail(CodeListDetail codeListDetail);

	List<CodeListDetail> getCodeListDetail(@Param("codeListId") long codeListId);
	//delete codelistdetail by codelistId and codelistDetailId cannot exist in insert or update when modify
	int deleteAllByCodeListDetailArrayAndCodeListId(@Param("codeListId") Long codeListId, @Param ("codeListDetails") CodeListDetail[] codeListDetail);
	
	//delete codelistDetail
	//int deleteAllCodeListDetailByCodeListId(@Param("codeListId") Long codeListId);
	
	//registercodelistDetail
	long registerCodeListDetail(CodeListDetail codeListDetail);

	int deleteCodeListDetail(Long codeListId);
	
	List<CodeListDetail> findCodeListDetailByProject(Long projectId);

}
