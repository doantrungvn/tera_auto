package org.terasoluna.qp.domain.service.language;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.Language;
import org.terasoluna.qp.domain.repository.language.LanguageCriteria;

@Service
public interface LanguageService {

	Language register(Language language, boolean translateFlag, Long accountId);
	
	Collection<Language> findAllLanguage(); 
	
	Language findLanguage(Language language);
	
	int delete(Language language);
	
	int modify(Language language);
	
	Language checkExitsCode(Language language);
	
	void translateLanguage(Language language, AccountProfile accountProfile);
	
	void testConnection(AccountProfile accountProfile) throws Exception;

	Page<Language> findPageByCriteria(LanguageCriteria languageCriteria, Pageable pageable);

}
