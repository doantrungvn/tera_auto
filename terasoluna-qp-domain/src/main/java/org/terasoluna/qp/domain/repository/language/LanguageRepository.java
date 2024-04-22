package org.terasoluna.qp.domain.repository.language;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.Language;

public interface LanguageRepository {

	void register(Language language);

	/* void registerMessageFromDefaultLanguageCode(Language language); */

	List<Language> findAllLanguage();

	Language findLanguage(Language language);

	int delete(Language language);

	void deleteReferenceMessage(Language language);

	int modify(Language language);

	Language checkExitsCode(Language language);

	List<HashMap<String, String>> findAllBingInfor();

	long countByCriteria(@Param("criteria") LanguageCriteria languageCriteria);

	List<Language> findPageByCriteria(@Param("criteria") LanguageCriteria languageCriteria, @Param("pageable") Pageable pageable);

	Language findLanguageByKey(@Param("criteria") Language language);
}
