package org.terasoluna.qp.domain.service.accountpermission;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.AccountMessageConst;
import org.terasoluna.qp.app.message.AccountRolePermissionConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountPermission;
import org.terasoluna.qp.domain.repository.account.AccountRepository;
import org.terasoluna.qp.domain.repository.accountpermission.AccountPermissionRepository;

@Service
public class AccountPermissionServiceImpl implements AccountPermissionService {

	@Inject
	AccountPermissionRepository accountPermissionRepository;

	@Inject
	AccountRepository accountRepository;

	@Inject
	PasswordEncoder passwordEncoder;

	/**
	 * Delete account ' permission
	 * 
	 * @param accountPermission
	 *            account ' permission information
	 */
	@Override
	public void deleteByAccountPermisson(AccountPermission accountPermission) {
		accountPermissionRepository.deleteByAccountPermisson(accountPermission);

	}

	/**
	 * find account by AccountId
	 * 
	 * @param accountId
	 *            identify
	 * @return account information
	 */
	@Override
	public Account findOneByAccountId(Long accountId) throws BusinessException {
		Account account = accountRepository.findOneByAccountId(accountId);
		if (account == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0001)));
		}
		return account;
	}

	/**
	 * Modify account ' permission process
	 * 
	 * @param accountId
	 *            identify
	 * @param lstAccountPermission
	 *            list of account ' permission information
	 */
	@Override
	public void modify(Long accountId, List<AccountPermission> lstAccountPermission, Account account) {
		Account accountExist = accountRepository.findOneByAccountId(accountId);
		if (accountExist == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(AccountRolePermissionConst.SC_ACCOUNTROLEPERMISSION_0015)));
		}

		// update accountId
		account.setSystemTime(FunctionCommon.getCurrentTime());
		boolean updated = false;
		if (account.isSelectedChangePass()) {
			account.setPassword(passwordEncoder.encode(account.getPassword()));
			updated = accountRepository.modifyHaveChangePass(account);
		} else {
			updated = accountRepository.modify(account);
		}
		if (!updated) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}

		// delete permission of account.
		accountPermissionRepository.deleteByAccountId(accountId);
		// insert account - permission
		if (lstAccountPermission != null && lstAccountPermission.size() > 0) {
			if (accountPermissionRepository.registerAccountPermisson(lstAccountPermission) <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(AccountRolePermissionConst.SC_ACCOUNTROLEPERMISSION_0019)));
			}
		}
	}

}
