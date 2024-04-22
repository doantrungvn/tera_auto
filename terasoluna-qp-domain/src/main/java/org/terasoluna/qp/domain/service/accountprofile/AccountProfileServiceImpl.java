package org.terasoluna.qp.domain.service.accountprofile;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.ChangePassword;
import org.terasoluna.qp.domain.repository.accountprofile.AccountProfileRepository;
import org.terasoluna.qp.domain.service.account.AccountDetails;
import org.terasoluna.qp.domain.service.common.SystemService;

@Transactional
@Service
public class AccountProfileServiceImpl implements AccountProfileService {

	@Inject
	AccountProfileRepository accountProfileRepository;

	@Inject
	PasswordEncoder passwordEncoder;

	@Inject
	SystemService systemService;

	@Override
	public AccountProfile findOne(Long accountId) {
		return accountProfileRepository.findOne(accountId);
	}

	@Inject
	Mapper beanMapper;

	@Override
	public void update(AccountProfile accountProfile) throws BusinessException {
		int result = 0;

		if (DbDomainConst.ConnectionSetting.SYSTEM_SETTING.equals(accountProfile.getConnectionFlg())) {
			// clear setting of user
			accountProfile.setSessionTimeOut(null);
			accountProfile.setIntervalReload(null);
			accountProfile.setProxyLevel(null);
			accountProfile.setProxyHost(null);
			accountProfile.setProxyPort(null);
			accountProfile.setProxyUser(null);
			accountProfile.setProxyPass(null);
			accountProfile.setBingClientId(null);
			accountProfile.setBingClientSecret(null);
		}

		/*
		 * if(DbDomainConst.ProxySetting.NONE_PROXY.equals(accountProfile.getProxyLevel()) || DbDomainConst.ProxySetting.SYSTEM_PROXY.equals(accountProfile.getProxyLevel())){
		 * accountProfile.setProxyHost(null); accountProfile.setProxyPort(null); accountProfile.setProxyUser(null); accountProfile.setProxyPass(null); }
		 */

		if (accountProfile.getAccountId() != null) {
			// accountProfile.setUpdatedBy(SessionUtils.getAccountId());
			accountProfile.setUpdatedDate(FunctionCommon.getCurrentTime());
			result = accountProfileRepository.update(accountProfile);
		} else {
			Account account = this.getLoginAccount();
			accountProfile.setAccountId(account.getAccountId());
			accountProfile.setCreatedBy(accountProfile.getUpdatedBy());
			accountProfile.setUpdatedDate(FunctionCommon.getCurrentTime());
			result = accountProfileRepository.create(accountProfile);
		}
		if (result == 0) {
			// define message
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0072, MessageUtils.getMessage(CommonMessageConst.TQP_ACCOUNTPROFILE)));
		}
	}

	@Override
	public void updatecurrentproject(AccountProfile accountProfile, String projectName) throws BusinessException {
		int result = 0;
		if (accountProfile.getAccountId() != null) {
			result = accountProfileRepository.updatecurrentproject(accountProfile);
			if (result == 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0110, projectName));
			}
		}
	}

	@Override
	public AccountProfile getAccountProfile(Long accountId) throws BusinessException {

		AccountProfile accountProfile = this.findOne(accountId);

		if (accountProfile == null) {
			return getDefaultProfile();
		} else {
			AccountProfile accountProfileDefault = systemService.getDefaultProfile();

			boolean usedSystemSetting = DbDomainConst.ConnectionSetting.SYSTEM_SETTING.equals(accountProfile.getConnectionFlg());

			if (usedSystemSetting || accountProfile.getBingClientId() == null) {
				accountProfile.setBingClientId(accountProfileDefault.getBingClientId());
				accountProfile.setBingClientSecret(accountProfileDefault.getBingClientSecret());
			}
			//
			if (usedSystemSetting || accountProfile.getSessionTimeOut() == null) {
				accountProfile.setSessionTimeOut(accountProfileDefault.getSessionTimeOut());
			}
			//
			if (usedSystemSetting || accountProfile.getIntervalReload() == null) {
				accountProfile.setIntervalReload(accountProfileDefault.getIntervalReload());
			}
			//
			if (usedSystemSetting || accountProfile.getMaxSizeUpload() == null) {
				accountProfile.setMaxSizeUpload(accountProfileDefault.getMaxSizeUpload());
			}

			/*if (accountProfile.getProxyHost() == null) {*/
			/*accountProfile.setProxyLevel(accountProfileDefault.getProxyLevel());
			accountProfile.setProxyHost(accountProfileDefault.getProxyHost());
			accountProfile.setProxyPort(accountProfileDefault.getProxyPort());
			accountProfile.setProxyUser(accountProfileDefault.getProxyUser());
			accountProfile.setProxyPass(accountProfileDefault.getProxyPass());*/
			/*}*/

			accountProfile.setMaxTranslatedItem(accountProfileDefault.getMaxTranslatedItem());
			accountProfile.setNumberBatchForOneExucute(accountProfileDefault.getNumberBatchForOneExucute());
		}

		return accountProfile;
	}

	private Account getLoginAccount() {
		AccountDetails accountDetails = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (accountDetails != null && accountDetails.getAccount() != null) {
			return accountDetails.getAccount();
		} else {
			throw new BusinessException(ResultMessages.error().add(""));
		}
	}

	@Override
	public AccountProfile getDefaultProfile() {
		return systemService.getDefaultProfile();
	}

	@Override
	public ChangePassword getInformationForPasswordForm(long accountId) {
		return accountProfileRepository.getInformationForPasswordForm(accountId);
	}

}
