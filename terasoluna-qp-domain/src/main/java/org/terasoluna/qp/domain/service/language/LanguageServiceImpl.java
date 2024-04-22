package org.terasoluna.qp.domain.service.language;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.codelist.ReloadableCodeList;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LanguageMessageConst;
import org.terasoluna.qp.app.message.translator.microsoft.TranslatorUtil;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.Language;
import org.terasoluna.qp.domain.model.Message;
import org.terasoluna.qp.domain.repository.language.LanguageCriteria;
import org.terasoluna.qp.domain.repository.language.LanguageRepository;
import org.terasoluna.qp.domain.repository.message.MessageRepository;
import org.terasoluna.qp.domain.service.accountprofile.AccountProfileService;
import org.terasoluna.qp.domain.service.common.SystemService;

@Service
@Transactional
public class LanguageServiceImpl implements LanguageService {

	@Inject
	LanguageRepository languageRepository;

	@Inject
	MessageRepository messageRepository;

	@Inject
	@Named(value = "CL_LANGUAGE_LIST")
	ReloadableCodeList codeListLanguage;

	@Inject
	Mapper beanMapper;

	@Inject
	SystemService systemService;
	
	@Inject
	AccountProfileService accountProfileService;

	@Override
	public List<Language> findAllLanguage() {
		return languageRepository.findAllLanguage();
	}

	@Override
	public Language register(Language language, boolean translateFlag, Long accountId) {
		String[] str = language.getLanguageCode().split(DbDomainConst.SEPARATE_LANGUAGE_COUNTRY);
		language.setLanguageCode(str[0]);
		language.setCountryCode(str[1]);
		
		// Check duplicate
		if(checkExitsCode(language) != null){
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0036, new Object[] { MessageUtils.getMessage(LanguageMessageConst.LANGUAGE_NAME)}));
		}
		
		// Register language
		languageRepository.register(language);

		// Translate language from English to destination language
		if (translateFlag) {
//			language.setToLanguageCode(language.getLanguageCode());
//			language.setLanguageCode("en");
//			language.setToCountryCode(language.getCountryCode());
//			language.setCountryCode("US");

			this.translateLanguage(language, accountProfileService.getAccountProfile(accountId));
		}

		// Refresh language code list
		// codeListLanguage.refresh();
		return language;
	}

	@Override
	public Language findLanguage(Language param) {
		Language language = languageRepository.findLanguage(param);
		if (language == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage("tqp.language")));
		}
		return language;
	}

	@Override
	public int delete(Language language) {
		int rowCount = 0;
		Language iLanguage = findLanguage(language);
		languageRepository.deleteReferenceMessage(iLanguage);
		rowCount = languageRepository.delete(iLanguage);
		codeListLanguage.refresh();
		return rowCount;
	}

	@Override
	public int modify(Language language) {
		int rowCount = 0;
		Language iLanguage = findLanguage(language);
		rowCount = languageRepository.modify(iLanguage);
		return rowCount;
	}

	@Override
	public Language checkExitsCode(Language language) {
		return languageRepository.checkExitsCode(language);
	}

	/**
	 * Translate from English
	 */
	@Override
	public void translateLanguage(Language language, AccountProfile accountProfile) {
		List<Message> lstMessage = messageRepository.findToTranslate(language);
		language.setLanguageCode(revertOldISOCodes(language.getLanguageCode()));
		// Init translator
		// AccountProfile accountProfileDefault = systemService.getDefaultProfile();

		systemService.initBingTranslate(accountProfile, DbDomainConst.initTranslateFlg.FOR_TRANSLATE);
//		String fromLanguage = language.getLanguageCode() + "-" + language.getCountryCode(); // "en-US";
//		if (StringUtils.isEmpty(language.getToCountryCode())) {
//			Language temp = new Language();
//			temp.setLanguageCode(language.getToLanguageCode());
//			temp = languageRepository.findLanguageByKey(temp);
//			if (temp != null) {
//				language.setToCountryCode(temp.getCountryCode());
//			}
//		}
//		String toLanguage = language.getToLanguageCode() + "-" + language.getToCountryCode(); // language.getLanguageCode() + "-" + language.getCountryCode();
		String fromLanguage = "en-US";
        String toLanguage = language.getLanguageCode() + "-" + language.getCountryCode();
		int numOfPatition = 0;
		int maxTranslate = Integer.parseInt(StringUtils.defaultString(accountProfile.getMaxTranslatedItem(), "1"));
		int maxOneExucute = Integer.parseInt(StringUtils.defaultString(accountProfile.getNumberBatchForOneExucute(), "1"));
		int numOfMessage = lstMessage.size();
		List<String> sourceTexts = new ArrayList<String>();
		List<Message> translatedMessages = new ArrayList<Message>();
		List<Message> partition = new ArrayList<Message>();

		for (int j = 0; j < numOfMessage; j++) {
			Message message = lstMessage.get(j);
			if (StringUtils.isNotBlank(message.getMessageString())) {
				partition.add(message);
				sourceTexts.add(message.getMessageString());
			}

			if (j > 0 && j % maxTranslate == 0 || j == numOfMessage - 1) {
				numOfPatition++;
				String[] translatedTexts;
				String[] strArray = (String[]) sourceTexts.toArray(new String[0]);
				try {
					translatedTexts = TranslatorUtil.executeNew(strArray, fromLanguage, toLanguage);
					if (translatedTexts != null && translatedTexts.length == sourceTexts.size()) {
						int i = 0;
						for (Message translatedMessage : partition) {
							translatedMessage.setMessageString(StringUtils.isBlank(translatedTexts[i]) ? translatedMessage.getMessageString() : translatedTexts[i]);
							i++;
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					throw new BusinessException(ResultMessages.error().add(LanguageMessageConst.ERR_LANGUAGE_0066));
				} finally {
					sourceTexts.clear();
					partition.clear();
					strArray = null;
					translatedTexts = null;
				}

				if (numOfPatition % maxOneExucute == 0 && !translatedMessages.isEmpty()) {
					messageRepository.updateByTranslate(translatedMessages);
					numOfPatition = 0;// reset
					translatedMessages.clear();
				}
			}
		}
		// commit the last element
		if (numOfPatition > 0 && !translatedMessages.isEmpty()) {
			messageRepository.updateByTranslate(translatedMessages);
		}
		messageRepository.updateByTranslate(lstMessage);
	}

	@Override
	public void testConnection(AccountProfile accountProfile) throws Exception {
		systemService.initBingTranslate(accountProfile, DbDomainConst.initTranslateFlg.FOR_TEST_CONNECTION);
		TranslatorUtil.getToken(TranslatorUtil.getClientId(), TranslatorUtil.getClientSecret());
	}

	@Override
	public Page<Language> findPageByCriteria(LanguageCriteria languageCriteria, Pageable pageable) {
		long totalCount = languageRepository.countByCriteria(languageCriteria);

		List<Language> articles;
		if (0 < totalCount) {
			articles = languageRepository.findPageByCriteria(languageCriteria, pageable);
		} else {
			articles = Collections.emptyList();
		}
		Page<Language> page = new PageImpl<Language>(articles, pageable, totalCount);
		return page;
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
