package org.terasoluna.qp.domain.repository.importmanagement;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.CodeList;
import org.terasoluna.qp.domain.model.CodeListDetail;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.MessageDesign;

public interface ImportManagementRepository {
	//For message design
	List<Map<String, Object>> getModuleIdByProjectId(@Param("projectId")Long projectId);
	void insertMessageDesign(@Param("listMessageDesignForInsert") List<MessageDesign> listMessageDesignForInsert);
	void updateMessageDesign(@Param("listMessageDesignForUpdate") List<MessageDesign> listMessageDesignForUpdate);
	void deleteMessageDesign(@Param("projectId")Long projectId);
	Long getLanguageIdByLanguageName(@Param("languageName")Long languageName);
	List<Map<String, Object>> getCodeKeyOfMessageDesign(@Param("projectId")Long projectId);
	
	//For domain design
	void insertDomainDesign(@Param("listDomainDesign") List<DomainDesign> listDomainDesign);
	void updateDomainDesign(@Param("listDomainDesign") List<DomainDesign> listDomainDesign);
	void deleteDomainDesign(@Param("domainId") Long domainId);
	List<Map<String, Object>> getCodeKeyOfDomainDesign(@Param("projectId")Long projectId);
	List<Map<String, Object>> getBaseType();
	List<Map<String, Object>> getValidationRule();
	List<Map<String, Object>> getReferenceDomainDesign(@Param("projectId")Long projectId);
	
	//For codelist design
	List<Map<String, Object>> getModuleId(@Param("projectId")Long projectId, @Param("moduleName")String moduleName);
	List<Map<String, Object>> getCodeKeyOfCodelistDesign(@Param("codelistCode")String codelistCode, @Param("projectId")Long projectId);
	List<Map<String, Object>> getReferenceCodelistDesign(@Param("projectId")Long projectId);
	void deleteCodelistDesign(@Param("codelistId") Long codelistId);
	void insertCodelistDesign(@Param("listCodelistDesign") List<CodeList> listCodelistDesign);
	void updateCodelistDesign(@Param("listCodelistDesign") List<CodeList> listCodelistDesign);
	Long getSequencesCodelist();
	void deleteCodelistDetailDesign(@Param("codelistDetailId") Long codelistDetailId);
	void insertCodelistDetailDesign(@Param("listCodelistDetailDesign") List<CodeListDetail> listCodelistDetailDesign);
	void updateCodelistDetailDesign(@Param("listCodelistDetailDesign") List<CodeListDetail> listCodelistDetailDesign);
	Long getCodelistIdByCodelistCode(@Param("codelistCode") String codelistCode, @Param("projectId") Long projectId);
	Long getCodelistDetailId(@Param("codelistId") Long codelistId, @Param("key") String key, @Param("value") String value);
	void deleteCodelistDetailDesignBeforeUpdate(@Param("codelistId") Long codelistId);
}
