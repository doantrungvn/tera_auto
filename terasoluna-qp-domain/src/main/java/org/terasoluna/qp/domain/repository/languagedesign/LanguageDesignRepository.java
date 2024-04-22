package org.terasoluna.qp.domain.repository.languagedesign;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.Language;
import org.terasoluna.qp.domain.model.LanguageDesign;

public interface LanguageDesignRepository {
	
	void register(@Param("languageDesign") LanguageDesign languageDesign, @Param("defaultLanguage") Language defaultLanguage);
	
	/*void registerDefaultLanguageCode(LanguageDesign languageDesign);*/
	
	int updateLanguageDesign(@Param("languageDesign") LanguageDesign languageDesign);
	
	void registerLanguageDesign(LanguageDesign languageDesign);
	
	List<LanguageDesign> findAllLanguageDesign(@Param("languageId") Long languageId, @Param("projectId") Long projectId);
	
	int delete(LanguageDesign languageDesign);
	
	int deleteReferenceMessage(LanguageDesign languageDesign);
	
	int modify(LanguageDesign languageDesign);
	
	LanguageDesign findByCode(LanguageDesign languageDesign);
	
	Long countNameCodeByLanguageCode(LanguageDesign languageDesign);
	
	Long countReferenceByLanguageCode(String languageCode);
	
	Collection<LanguageDesign> findAll();
	
	LanguageDesign findById(String languageCode);
	
	LanguageDesign findByPK(Long languageId);
	
	int modifyReferenceMessageDesign(LanguageDesign languageDesign);

	void registerAll(LanguageDesign[] languageDesigns, Language defaultLanguage);

	void deleteByExceptionalGroup(@Param("exceptionGroup")List<Long> exceptionGroup,@Param("projectId")Long currentProjectId);

	LanguageDesign[] findAllByProjectId(Long projectId);
	
	List<LanguageDesign> findLanguageByProjectId(Long projectId);
	
	List<LanguageDesign> findPageByCriteria(
            @Param("criteria") LanguageDesignCriteria criteria,
            @Param("pageable") Pageable pageable);

	long countByCriteria(@Param("criteria") LanguageDesignCriteria criteria);
	
}
