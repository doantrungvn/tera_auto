package org.terasoluna.qp.domain.service.accountrole;

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
import org.terasoluna.qp.domain.model.AccountRole;
import org.terasoluna.qp.domain.model.Role;
import org.terasoluna.qp.domain.repository.account.AccountRepository;
import org.terasoluna.qp.domain.repository.accountrole.AccountRoleRepository;
import org.terasoluna.qp.domain.repository.role.RoleRepository;

@Service
public class AccountRoleServiceImpl implements AccountRoleService {

	@Inject
	AccountRoleRepository accountRoleRepository;

	@Inject
	AccountRepository accountRepository;

	@Inject
	RoleRepository roleRepository;
	
	@Inject
	PasswordEncoder passwordEncoder;

	/**
	 * Modify account ' role process
	 * 
	 * @param acountId
	 *            identify
	 * @param lstAccountRole
	 *            List of account ' role
	 */
	@Override
	public void modifyAccountRole(Long accountId,
			List<AccountRole> lstAccountRole, Account account) {
		Account accountExist = accountRepository.findOneByAccountId(accountId);
		if (accountExist == null) {
			throw new BusinessException(ResultMessages.error().add(
					CommonMessageConst.ERR_SYS_0037,
					MessageUtils
							.getMessage(AccountMessageConst.SC_ACCOUNT_0001)));
		}

		//update accountId
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
		// delete role of account.
		accountRoleRepository.deleteByAccountId(accountId);

		/*if (lstAccountRole == null || lstAccountRole.isEmpty()) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0206, MessageUtils.getMessage(AccountRolePermissionConst.SC_ACCOUNTROLEPERMISSION_0026)));
		}*/

		// insert account - role
		if (lstAccountRole != null && lstAccountRole.size() > 0) {
			if (accountRoleRepository.createAccountRole(accountId, lstAccountRole) <= 0) {
				throw new BusinessException(
						ResultMessages
								.error()
								.add(CommonMessageConst.ERR_SYS_0048,
										MessageUtils
												.getMessage(AccountRolePermissionConst.SC_ACCOUNTROLEPERMISSION_0019)));
			}
		}
		
	}
	
	/**
	 * Delete account ' role information
	 * 
	 * @param accountId
	 *            identify
	 */
	@Override
	public void deleteByAccountId(Long accountId) {
		accountRoleRepository.deleteByAccountId(accountId);
	}


	/**
	 * find account by AccountId
	 * 
	 * @param accountId
	 *            identify
	 * @return Account information
	 */
	@Override
	public Account findOneByAccountId(Long accountId) throws BusinessException {
		Account account = accountRepository.findOneByAccountId(accountId);
		if (account == null) {
			throw new BusinessException(ResultMessages.error().add(
					CommonMessageConst.ERR_SYS_0037,
					MessageUtils
							.getMessage(AccountMessageConst.SC_ACCOUNT_0001)));
		}
		return account;
	}

	/**
	 * Find list roles of account
	 * 
	 * @param accountId
	 *            identify
	 * @return List of roles
	 */
	@Override
	public List<Role> getRoleByAccount(Long accountId) {
		List<Role> lstRole = roleRepository.getRoleByAccount(accountId);
		for (int i = 0; i < lstRole.size(); i++) {
			if (lstRole.get(i).getAccountId() != null) {
				lstRole.get(i).setSelected(true);
			} else
				lstRole.get(i).setSelected(false);
		}
		return lstRole;
	}
}
