package org.terasoluna.qp.domain.service.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.WriteXmlUltilsQP;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.translator.microsoft.ProxyCommon;
import org.terasoluna.qp.app.message.translator.microsoft.TranslatorUtil;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.AccountProject;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.repository.accountprofile.AccountProfileRepository;
import org.terasoluna.qp.domain.repository.accountproject.AccountProjectRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.service.generateddl.GenerateDDLService;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SystemServiceImpl implements SystemService, ApplicationListener<ContextRefreshedEvent> {
	@Inject
	AccountProfileRepository accountProfileRepository;

	@Inject
	ProjectRepository projectRepository;

	@Inject
	AccountProjectRepository accountProjectRepository;

	@Inject
	Mapper beanMapper;

	AccountProfile profile;

	String[] oracleReservedWords;
	String[] pgReservedWords;

	Map<Long, List<Project>> mAccountProjects;
	
	public void setDefaultProfile(AccountProfile defaultProfile) {
		this.profile = defaultProfile;
	}

	@Override
	public AccountProfile getDefaultProfile() {
		return this.profile;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.initialize();
	}

	private void initialize() {
		this.profile = this.getSystemProfile();
		mAccountProjects = new ConcurrentHashMap<Long, List<Project>>(16, 0.9f, 1);
		initReservedWords();
		initAcountProject();
	}

	private AccountProfile getSystemProfile() {
		AccountProfile accountProfile = new AccountProfile();
		HashMap<String, String> accountprofileHM = new HashMap<String, String>();
		List<HashMap<String, String>> resources = accountProfileRepository.getDefaultProfile();
		for (HashMap<String, String> map : resources) {
			accountprofileHM.put(map.get("resource_cd"), map.get("value1"));
		}
		beanMapper.map(accountprofileHM, accountProfile);
		return accountProfile;
	}

	private Configuration freemarkerConf;

	/**
	 * DungNN - comment
	 */
	private void initReservedWords() {
		InputStream is = null;
		try {
			is = GenerateDDLService.class.getResourceAsStream("/META-INF/templatescript/keywordsPostgre.sql");
			pgReservedWords = StringUtils.split(IOUtils.toString(is, DbDomainConst.CHARACTER_ENCODING));
			
			if (ArrayUtils.isNotEmpty(pgReservedWords)) {
				java.util.Arrays.sort(pgReservedWords);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}

		try {

			is = GenerateDDLService.class.getResourceAsStream("/META-INF/templatescript/keywordsOracle.sql");
			oracleReservedWords = StringUtils.split(IOUtils.toString(is, DbDomainConst.CHARACTER_ENCODING));
			
			if (ArrayUtils.isNotEmpty(oracleReservedWords)) {
				java.util.Arrays.sort(oracleReservedWords);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}

	}

	@Override
	public Configuration createDefaultFreemarkerConfiguration() {
		if (freemarkerConf == null) {
			freemarkerConf = new Configuration(Configuration.VERSION_2_3_21);
			freemarkerConf.setClassForTemplateLoading(getClass(), GenerateSourceCodeConst.TemplatePath.SOURCE_TEMPLATE);
			freemarkerConf.setDefaultEncoding("UTF-8");
			freemarkerConf.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
			freemarkerConf.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
		}
		return freemarkerConf;
	}

	@Override
	public void modifySystemSetting(AccountProfile accountProfile) {
		// prepare data
		if (accountProfile == null) {
			return;
		}

		if (accountProfile.getSessionTimeOut() == null)
			accountProfile.setSessionTimeOut(0);
		if (accountProfile.getIntervalReload() == null)
			accountProfile.setIntervalReload(0);
		if (accountProfile.getProxyHost() == null)
			accountProfile.setProxyHost(StringUtils.EMPTY);
		if (accountProfile.getProxyPort() == null)
			accountProfile.setProxyPort(StringUtils.EMPTY);
		if (accountProfile.getProxyUser() == null)
			accountProfile.setProxyUser(StringUtils.EMPTY);
		if (accountProfile.getProxyPass() == null)
			accountProfile.setProxyPass(StringUtils.EMPTY);
		if (accountProfile.getBingClientId() == null)
			accountProfile.setBingClientId(StringUtils.EMPTY);
		if (accountProfile.getBingClientSecret() == null)
			accountProfile.setBingClientSecret(StringUtils.EMPTY);
		if (accountProfile.getBatchJobPath() == null)
			accountProfile.setBatchJobPath(StringUtils.EMPTY);
		if (accountProfile.getMaxSizeUpload() == null)
			accountProfile.setMaxSizeUpload(30);

		if (!accountProfileRepository.updateSystemSetting(accountProfile)) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0046, CommonMessageConst.SC_TQP_0010));
		}
		// save to maven setting
		String batchJobPath = accountProfile.getBatchJobPath();
		/*if(StringUtils.isEmpty(batchJobPath)){
			batchJobPath = FileUtilsQP.getBatchJobPath();
		}*/
		batchJobPath = StringUtils.appendIfMissing(batchJobPath, File.separator, File.separator);
		String settingPath =  batchJobPath  + "apache-maven/conf/settings.xml";
		WriteXmlUltilsQP.processWriteMavenSetting(settingPath, accountProfile);
		initialize();
	}

	@Override
	public void initBingTranslate(AccountProfile accountProfile, int initFor) {
		if (accountProfile == null) {
			accountProfile = profile;
		} else if (DbDomainConst.initTranslateFlg.FOR_TRANSLATE == initFor) {
			accountProfile.setProxyHost(profile.getProxyHost());
			accountProfile.setProxyPort(profile.getProxyPort());
			accountProfile.setProxyUser(profile.getProxyUser());
			accountProfile.setProxyPass(profile.getProxyPass());
		}

		TranslatorUtil.init(profile.getServiceUrl(), profile.getArrayServiceUrl(), profile.getArrayJsonObjectProperty(), profile.getDatamarketAccessUri());

		Proxy proxy = null;
		String proxyUser = null;
		String proxyPass = null;

		if (StringUtils.isNotBlank(accountProfile.getProxyHost()) && StringUtils.isNotBlank(accountProfile.getProxyPort())) {
			proxy = ProxyCommon.initProxySystemProxy(accountProfile.getProxyHost(), Integer.parseInt(accountProfile.getProxyPort()));
			proxyUser = accountProfile.getProxyUser();
			proxyPass = accountProfile.getProxyPass();

			TranslatorUtil.setProxyInfomation(proxy, proxyUser, proxyPass);
		} else {
			TranslatorUtil.resetProxyInfomation();
		}

		TranslatorUtil.setClientId(accountProfile.getBingClientId());
		TranslatorUtil.setClientSecret(accountProfile.getBingClientSecret());
	}

	@Override
	public String[] databaseReservedWords(String dbType) {
		if ( DataTypeUtils.equals(DbDomainConst.DatabaseType.ORACLE, dbType)) {
			return oracleReservedWords;
		}
		return pgReservedWords;
	}

	public void initAcountProject() {
		List<Project> projects = projectRepository.findAll();
		List<AccountProject> accountProjects = accountProjectRepository.findAll();
		mAccountProjects.clear();
		if (CollectionUtils.isNotEmpty(accountProjects) && CollectionUtils.isNotEmpty(projects)) {
			for (AccountProject ap : accountProjects) {
				for (Project p: projects) {
					if (DataTypeUtils.equals(p.getProjectId(), ap.getProjectId())) {
						putMapAccountProject(ap.getAccountId(), p);
						break;
					}
				}
			}
			
			for (List<Project> value : mAccountProjects.values()) {
				java.util.Collections.sort(value);
			}
		}
	}

	private void putMapAccountProject(Long accountId, Project p) {
		List<Project> projects = null;
		if (mAccountProjects.containsKey(accountId)) {
			projects = mAccountProjects.get(accountId);
		} else {
			projects = new ArrayList<Project>();
		}
		projects.add(p);
		mAccountProjects.put(accountId, projects);
	}
	
	@Override
	public List<Project> getAllProjectByAccount(Long accountId) {

		if (mAccountProjects == null || mAccountProjects.isEmpty()) {
			return projectRepository.findAllProjectByAccount(accountId);
		}

		List<Project> listProjects =  mAccountProjects.get(accountId);
		if (CollectionUtils.isEmpty(listProjects)) {
			listProjects = projectRepository.findAllProjectByAccount(accountId);

			if (CollectionUtils.isNotEmpty(listProjects)) {
				mAccountProjects.put(accountId, listProjects);
				return listProjects;
			} else {
				return null;
			}
			
		}
		
		return mAccountProjects.get(accountId);
	}

	@Override
	public void resetAccountProject() {
		initAcountProject();
	}
}
