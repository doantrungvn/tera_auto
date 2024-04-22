package org.terasoluna.qp.domain.service.common;

import java.util.List;

import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.Project;

import freemarker.template.Configuration;

public interface SystemService {

	AccountProfile getDefaultProfile();

	Configuration createDefaultFreemarkerConfiguration();

	void modifySystemSetting(AccountProfile accountProfile);

	void initBingTranslate(AccountProfile accountProfile, int initFor);

	String[] databaseReservedWords(String dbType);

	List<Project> getAllProjectByAccount(Long accountId);

	void resetAccountProject();
}
