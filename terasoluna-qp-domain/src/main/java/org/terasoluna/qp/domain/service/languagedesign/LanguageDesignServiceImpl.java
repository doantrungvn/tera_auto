package org.terasoluna.qp.domain.service.languagedesign;

import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.LocaleUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LanguageDesignMessageConst;
import org.terasoluna.qp.app.message.translator.microsoft.ProxyAuthenticator;
import org.terasoluna.qp.app.message.translator.microsoft.TranslatorUtil;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.Language;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.repository.accountprofile.AccountProfileRepository;
import org.terasoluna.qp.domain.repository.languagedesign.LanguageDesignCriteria;
import org.terasoluna.qp.domain.repository.languagedesign.LanguageDesignRepository;
import org.terasoluna.qp.domain.repository.messagedesign.MessageDesignRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class LanguageDesignServiceImpl implements LanguageDesignService {
	
	@Inject
	LanguageDesignRepository languageDesignRepository;

	@Inject
	MessageDesignRepository messageDesignRepository;

	@Inject
	AccountProfileRepository accountProfileRepository;

	@Inject
	ProjectService projectService;

	@Inject
	Mapper beanMapper;
	
	@Inject
	SystemService systemService;
	
	@Inject
	ProjectRepository projectRepository;
	
	private static final String ENCODING = "UTF-8";
	
	@Override
	public LanguageDesign registerLanguageDesign(LanguageDesign[] languageDesigns, Language defaultLanguage, Project currentProject, String defaultLanguageIndex, Long accountId) {
		List<Long> exceptionGroup = new ArrayList<Long>();
		LanguageDesign languageDesignReturn = null;
		if(ArrayUtils.isNotEmpty(languageDesigns)){
			for(LanguageDesign languageDesign:languageDesigns) {
				if (languageDesign.getLanguageId() == null) {
					String[] str = languageDesign.getLanguageCode().split(DbDomainConst.SEPARATE_LANGUAGE_COUNTRY);
					Timestamp currentTime = FunctionCommon.getCurrentTime();
					languageDesign.setCreatedBy(accountId);
					languageDesign.setCreatedDate(currentTime);
					languageDesign.setUpdatedBy(accountId);
					languageDesign.setUpdatedDate(currentTime);
					languageDesign.setLanguageCode(str[0]);
					languageDesign.setCountryCode(str[1]);
					languageDesign.setProjectId(currentProject.getProjectId());
					
					//check Concurrence
					if (languageDesignRepository.findByCode(languageDesign) != null) {
						throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
					}

					languageDesignRepository.register(languageDesign, defaultLanguage);
				} else if (languageDesignRepository.findByPK(languageDesign.getLanguageId()) == null) {
						throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
				}

				if(DataTypeUtils.equals(defaultLanguageIndex, languageDesign.getItemSeqNo())){
					currentProject.setDefaultLanguageId(languageDesign.getLanguageId());
					languageDesignReturn = languageDesign;
				}
				exceptionGroup.add(languageDesign.getLanguageId());
			}

			if(StringUtils.isBlank(defaultLanguageIndex)){
				languageDesignReturn = languageDesigns[0];
				currentProject.setDefaultLanguageId(languageDesignReturn.getLanguageId());
			}

			projectRepository.modifyDefaultLanguage(currentProject);
			languageDesignRepository.deleteByExceptionalGroup(exceptionGroup,currentProject.getProjectId());
		}

		return languageDesignReturn;
	}
	
	@Override
	public LanguageDesign registerDefaultLanguageDesign(Long projectId, Long accountId) {
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		LanguageDesign defaultLanguage = new LanguageDesign();
		defaultLanguage.setLanguageName("English");
		defaultLanguage.setLanguageCode("en");
		defaultLanguage.setCountryCode("US");
		defaultLanguage.setCreatedBy(accountId);
		defaultLanguage.setCreatedDate(currentTime);
		defaultLanguage.setUpdatedBy(accountId);
		defaultLanguage.setUpdatedDate(currentTime);
		defaultLanguage.setProjectId(projectId);
		defaultLanguage.setItemSeqNo(1);
		languageDesignRepository.register(defaultLanguage, null);
		return defaultLanguage;
	}

	@Override
	public void modifyLanguageDesign(LanguageDesign languageDesign) {
		LanguageDesign languageDesignForUpdate = findByLanguageDesign(languageDesign);
		String[] str = languageDesign.getLanguageCode().split(DbDomainConst.SEPARATE_LANGUAGE_COUNTRY);
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		languageDesignForUpdate.setLanguageCode(str[0]);
		languageDesignForUpdate.setCountryCode(str[1]);
		languageDesignForUpdate.setLanguageName(languageDesign.getLanguageName());
		languageDesignForUpdate.setRegionCode(languageDesign.getRegionCode());
		languageDesignForUpdate.setUpdatedBy(languageDesign.getUpdatedBy());
		languageDesignForUpdate.setUpdatedDate(languageDesign.getUpdatedDate());
		languageDesignForUpdate.setSysDatetime(currentTime);
		
		// Concurrence error
		if(languageDesignRepository.modify(languageDesignForUpdate) <= 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0009)));
		}
	}

	@Override
	public void deleteLanguageDesign(LanguageDesign languageDesign, LanguageDesign defaultLanguage, CommonModel commonModel) {
		LanguageDesign languageDesignForDelete = findByLanguageDesign(languageDesign);
		if (languageDesignForDelete == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, 
					MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0009)));
		} else {
			
			//check Concurrence
			if (DateUtils.compare(languageDesignForDelete.getUpdatedDate(), languageDesign.getUpdatedDate()) == 1) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}

			commonModel.setProjectId(languageDesignForDelete.getProjectId());
			Project project = projectService.validateProject(commonModel);

			/*don't allow delete default*/
			if (DataTypeUtils.equals(project.getDefaultLanguageId(), languageDesign.getLanguageId())) {
				throw new BusinessException(ResultMessages.error().add(LanguageDesignMessageConst.ERR_LANGUAGEDESIGN_0001));
			}
			/*don't allow delete default*/
			if (languageDesignForDelete.getLanguageCode().equals(defaultLanguage.getLanguageCode())) {
				throw new BusinessException(ResultMessages.error().add(LanguageDesignMessageConst.ERR_LANGUAGEDESIGN_0005));
			}

			languageDesignRepository.deleteReferenceMessage(languageDesignForDelete);
			languageDesignRepository.delete(languageDesignForDelete);
		}
	}

	@Override
	public LanguageDesign findByLanguageDesign(LanguageDesign param) {
		// Consistency error
		LanguageDesign languageDesign = new LanguageDesign();
		if(null == param.getLanguageId()) {
			languageDesign = languageDesignRepository.findByCode(param);
			if (languageDesign == null) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, 
						MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0009)));
			}
		} else {
			languageDesign = languageDesignRepository.findByPK(param.getLanguageId());
			if(languageDesign == null){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, 
						MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0009)));
			}
		}
		return languageDesign;
	}	

	@Override
	public Collection<LanguageDesign> findAllLanguageDesign() {
		return languageDesignRepository.findAll();
	}
	
	@Override
	public List<LanguageDesign> findLanguageDesignByProjectId(Long projectId) {
		return languageDesignRepository.findLanguageByProjectId(projectId);
	}
	
	@Override
	public LanguageDesign[] findAllLanguageDesignByProject(Long projectId) {
		return languageDesignRepository.findAllByProjectId(projectId);
	}
	
	// Translate for view screen
	@Override
	public void translateLanguageDesign(LanguageDesign languageDesign, AccountProfile accountProfile, CommonModel commonModel) {

		//check from language
		LanguageDesign fromLanguageDesignDb = languageDesignRepository.findByPK(languageDesign.getLanguageId());
		if (fromLanguageDesignDb == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, 
					MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0018)));
		}
		commonModel.setDesignStatus(true);
		commonModel.setProjectId(fromLanguageDesignDb.getProjectId());
		projectService.validateProject(commonModel);
		
		//check to language
		LanguageDesign toLanguageDesignDb = languageDesignRepository.findByPK(languageDesign.getToLanguageId());
		if (toLanguageDesignDb == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, 
					MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0011)));
		}
		commonModel.setProjectId(toLanguageDesignDb.getProjectId());
		projectService.validateProject(commonModel);

		Long accountId = commonModel.getCreatedBy();
		Long workingProjectId = commonModel.getWorkingProjectId();

		//if language is current language don't for translate
		String[] defaultLanguage = new String[2];
		//AccountProfile accountProfileDefault = systemService.getDefaultProfile();
		
		if (accountProfile == null) {
			accountProfile = systemService.getDefaultProfile();;
		} 

		if (!StringUtils.isEmpty(accountProfile.getDefaultLanguage())) {
			defaultLanguage = accountProfile.getDefaultLanguage().split("_");
		} else {
			LanguageDesign languagedesign = commonModel.getWorkingLanguageDesign();
			defaultLanguage[0] = languagedesign.getLanguageCode();
		}
		
		if(StringUtils.equalsIgnoreCase(languageDesign.getToLanguageCode(), defaultLanguage[0])  && StringUtils.equalsIgnoreCase(languageDesign.getToCountryCode(),defaultLanguage[1])) {
			throw new BusinessException(ResultMessages.error().add(LanguageDesignMessageConst.ERR_LANGUAGEDESIGN_0003));
		}

		Timestamp currentTime = FunctionCommon.getCurrentTime();
		
		/*org.terasoluna.qp.app.message.translator.microsoft.Language fromLanguage = org.terasoluna.qp.app.message.translator.microsoft.Language.fromString(languageDesign.getLanguageCode());
		org.terasoluna.qp.app.message.translator.microsoft.Language toLanguage = org.terasoluna.qp.app.message.translator.microsoft.Language.fromString(languageDesign.getToLanguageCode());
		*/
		/*accountProfile.setProxyHost(accountProfileDefault.getProxyHost());
		accountProfile.setProxyPort(accountProfileDefault.getProxyPort());
		accountProfile.setProxyUser(accountProfileDefault.getProxyUser());
		accountProfile.setProxyPass(accountProfileDefault.getProxyPass());*/
		systemService.initBingTranslate(accountProfile, DbDomainConst.initTranslateFlg.FOR_TRANSLATE);

		// get message of from language
		List<MessageDesign> messageDesigns = messageDesignRepository.findToTranslate(languageDesign, workingProjectId);

		if (messageDesigns == null || messageDesigns.isEmpty()) {
			throw new BusinessException(ResultMessages.error().add(LanguageDesignMessageConst.ERR_LANGUAGEDESIGN_0004));
		}

		String fromLanguage = languageDesign.getLanguageCode() + "-" + languageDesign.getCountryCode();
		String toLanguage = revertOldISOCodes(languageDesign.getToLanguageCode()) + "-" + languageDesign.getToCountryCode();
		
		int numOfPatition = 0;

		int maxTranslate = Integer.parseInt(StringUtils.defaultString(accountProfile.getMaxTranslatedItem(), "1"));
		int maxOneExucute = Integer.parseInt(StringUtils.defaultString(accountProfile.getNumberBatchForOneExucute(), "1"));
		
		List<String> sourceTexts = new ArrayList<String>();
		List<MessageDesign> translatedMessageDesigns = new ArrayList<MessageDesign>();
		
		int numOfMessage = messageDesigns.size();
		
		List<MessageDesign> partition = new ArrayList<MessageDesign>();
		
		for (int j = 0; j < numOfMessage; j++) {

			MessageDesign messageDesign = messageDesigns.get(j);
			if (StringUtils.isNotBlank(messageDesign.getMessageString())) {
				partition.add(messageDesign);
				sourceTexts.add(messageDesign.getMessageString());
			}

			if (j > 0 && j % maxTranslate == 0 || j == numOfMessage - 1) {
				numOfPatition++;
				String[] translatedTexts;
				String[] strArray = (String[]) sourceTexts.toArray(new String[0]);
				try {
					translatedTexts = TranslatorUtil.executeNew(strArray, fromLanguage, toLanguage);
					if (translatedTexts != null && translatedTexts.length == sourceTexts.size()) {
						int i = 0;
						for (MessageDesign translatedMessageDesign : partition) {
							translatedMessageDesign.setMessageString(StringUtils.isBlank(translatedTexts[i]) ? translatedMessageDesign.getMessageString() : translatedTexts[i]);
							translatedMessageDesign.setLanguageCode(languageDesign.getToLanguageCode());
							translatedMessageDesign.setCountryCode(languageDesign.getToCountryCode());
							
							translatedMessageDesign.setCreatedBy(accountId);
							translatedMessageDesign.setCreatedDate(currentTime);
							translatedMessageDesign.setUpdatedBy(accountId);
							translatedMessageDesign.setUpdatedDate(languageDesign.getUpdatedDate());
							translatedMessageDesign.setSysDatetime(currentTime);
							translatedMessageDesign.setGeneratedStatus(DbDomainConst.MessageGeneratedStatus.AUTO_TRANSLATE);
							translatedMessageDesign.setLanguageId(languageDesign.getToLanguageId());
							
							translatedMessageDesign.setProjectId(workingProjectId);
							translatedMessageDesigns.add(translatedMessageDesign);
							i++;
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					throw new BusinessException(ResultMessages.error().add(LanguageDesignMessageConst.ERR_LANGUAGEDESIGN_0002));
				} finally {
					sourceTexts.clear();
					partition.clear();
					strArray = null;
					translatedTexts = null;
				}

				// DungNN modify : if has element and enough element for execute
				if (numOfPatition % maxOneExucute == 0 && !translatedMessageDesigns.isEmpty()) {
					messageDesignRepository.updateByTranslate(translatedMessageDesigns);
					numOfPatition = 0;// reset
					translatedMessageDesigns.clear();
				}
			}
		}
		//commit the last element 
		if (numOfPatition > 0 && !translatedMessageDesigns.isEmpty()) {
			messageDesignRepository.updateByTranslate(translatedMessageDesigns);
		}
	}

	@Override
	public void updateLanguageDesign(LanguageDesign languageDesign, Long languageIdDefaultProject, CommonModel commonModel) {

		List<LanguageDesign> listLanguageProject = this.findLanguageDesignByProjectId(languageDesign.getProjectId());
		for (LanguageDesign temp : listLanguageProject) {
			if (DataTypeUtils.notEquals(languageDesign.getLanguageId(), temp.getLanguageId()) 
					&& DataTypeUtils.equals(languageDesign.getLanguageCode(), temp.getLanguageCode() + "_" + temp.getCountryCode())) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0009)));
			}
		}
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		LanguageDesign obj = languageDesignRepository.findByPK(languageDesign.getLanguageId());

		if (obj == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0009)));
		} else {

			commonModel.setDesignStatus(true);
			commonModel.setProjectId(obj.getProjectId());
			projectService.validateProject(commonModel);

			languageDesign.setSysDatetime(currentTime);
			languageDesign.setUpdatedBy(languageDesign.getCreatedBy());
			languageDesign.setCountryCode(languageDesign.getLanguageCode().split("_")[1]);
			languageDesign.setLanguageCode(languageDesign.getLanguageCode().split("_")[0]);
			if (languageDesignRepository.updateLanguageDesign(languageDesign) <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0009)));
			}
		}
	}

	@Override
	public LanguageDesign getLanguageDefaultForDesign() {
		Language defaultLanguage = LocaleUtils.getDefaultLanguage();
		LanguageDesign param = new LanguageDesign(defaultLanguage.getLanguageCode(), defaultLanguage.getCountryCode());
		
		LanguageDesign languageDesign = languageDesignRepository.findByCode(param);
		if (null == languageDesign) {
			param = new LanguageDesign("en", "US");
			languageDesign = languageDesignRepository.findByCode(param);
		}

		return languageDesign;
	}
	
	@Override
	public LanguageDesign getLanguageDesignById(Long languageid, Long projectId) {
		LanguageDesign languageDesign = languageDesignRepository.findByPK(languageid);
		
		if (null == languageDesign) {
			LanguageDesign param = new LanguageDesign("en", "US");
			param.setProjectId(projectId);
			languageDesign = languageDesignRepository.findByCode(param);
		}
		
		return languageDesign;
	}

	@Override
	public Page<LanguageDesign> findPageByCriteria(LanguageDesignCriteria languageDesignCriteria, Pageable pageable) {
		long totalCount = languageDesignRepository.countByCriteria(languageDesignCriteria);

		List<LanguageDesign> articles;
		if (0 < totalCount) {
			articles = languageDesignRepository.findPageByCriteria(languageDesignCriteria, pageable);
		} else {
			articles = Collections.emptyList();
		}
		Page<LanguageDesign> page = new PageImpl<LanguageDesign>(articles, pageable, totalCount);
		return page;
	}
	
	@Override
	public Boolean testInternetConnection(URL url, Proxy proxy, String proxyUser, String proxyPass) throws IOException{
		final HttpURLConnection uc = getHttpURLConnection(url, proxy, proxyUser, proxyPass);
		uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=" + ENCODING);
		uc.setRequestProperty("Accept-Charset", ENCODING);
		uc.setDoOutput(true);
		try {
			final int responseCode = uc.getResponseCode();
			if (responseCode != 200) {
				return false;
			}
			return true;
		} finally {
			if (uc != null) {
				uc.disconnect();
			}
		}
	}
	
	private HttpURLConnection getHttpURLConnection(URL url, Proxy proxy, String proxyUser, String proxyPass) throws IOException {
		HttpURLConnection httpURLConnection = null;
		if (proxy == null) {
			httpURLConnection = (HttpURLConnection) url.openConnection();
		} else {
			httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
		}
		if (StringUtils.isNotBlank(proxyUser)) {
			String uname_pwd = proxyUser + ":" + proxyPass;
			String authString = "Basic " + new sun.misc.BASE64Encoder().encode(uname_pwd.getBytes());
			httpURLConnection.setRequestProperty("Proxy-Authorization", authString);
			httpURLConnection.addRequestProperty("Https-Proxy-Authorization", authString);
			Authenticator.setDefault(new ProxyAuthenticator(proxyUser, StringUtils.defaultString(proxyPass, StringUtils.EMPTY)));
		}
		return httpURLConnection;
	}
	
	private String revertOldISOCodes(String languageCode) {
		languageCode = StringUtils.lowerCase(languageCode).intern();
		if (languageCode == "iw") {
			return "he";
		} else if (languageCode == "in") {
			return "id";
		} else {
			return languageCode;
		}
	}
}