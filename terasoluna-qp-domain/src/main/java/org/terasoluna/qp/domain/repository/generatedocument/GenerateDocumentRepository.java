package org.terasoluna.qp.domain.repository.generatedocument;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.BusinessType;
import org.terasoluna.qp.domain.model.FunctionDesign;
import org.terasoluna.qp.domain.model.GenerateDocumentDomainDesign;

public interface GenerateDocumentRepository {
	
	List<FunctionDesign> findAllFunctionDesignByProjectId(@Param("projectId") Long projectId);
	
	List<BusinessType> findAllBusinessTypeByProjectId(@Param("projectId") Long projectId);
	
	List<GenerateDocumentDomainDesign> findAllDomainByProjectId(@Param("projectId") Long projectId);
}
