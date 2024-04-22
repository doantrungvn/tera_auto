package org.terasoluna.qp.domain.service.languagedesign;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.Language;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.repository.languagedesign.LanguageDesignCriteria;

@Service
public interface LanguageDesignService {

	LanguageDesign registerLanguageDesign(LanguageDesign[] languageDesign, Language defaultLanguage, Project currentProject, String defaultLanguageIndex, Long accountId);

	LanguageDesign registerDefaultLanguageDesign(Long projectId, Long accountId);

	// void updateLanguageDesign(LanguageDesign languageDesign);

	void deleteLanguageDesign(LanguageDesign languageDesign, LanguageDesign defaultLanguage, CommonModel commonModel);

	void modifyLanguageDesign(LanguageDesign languageDesign);

	LanguageDesign findByLanguageDesign(LanguageDesign languageDesign);

	Collection<LanguageDesign> findAllLanguageDesign();

	LanguageDesign[] findAllLanguageDesignByProject(Long projectId);

	void translateLanguageDesign(LanguageDesign languageDesign, AccountProfile accountProfile, CommonModel commonModel);

	LanguageDesign getLanguageDefaultForDesign();

	LanguageDesign getLanguageDesignById(Long languageId, Long projectId);

	List<LanguageDesign> findLanguageDesignByProjectId(Long projectId);

	Page<LanguageDesign> findPageByCriteria(LanguageDesignCriteria languageDesignCriteria, Pageable pageable);

	void updateLanguageDesign(LanguageDesign languageDesign, Long defaultLanguageId, CommonModel commonModel);
	
	Boolean testInternetConnection (URL url, Proxy proxy, String proxyUser, String proxyPass) throws IOException;
}
