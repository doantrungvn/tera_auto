package org.terasoluna.qp.domain.service.accountproject;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.AccountRolePermissionConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountProject;
import org.terasoluna.qp.domain.repository.account.AccountRepository;
import org.terasoluna.qp.domain.repository.accountproject.AccountProjectRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.service.common.SystemService;

@Service
public class AccountProjectServiceImpl implements AccountProjectService {

	@Inject
	AccountProjectRepository accountProjectRepository;

	@Inject
	AccountRepository accountRepository;

	@Inject
	ProjectRepository projectRepository;

	@Inject
	SystemService systemService;
	
	@Inject
	PasswordEncoder passwordEncoder;

	/**
	 * Delete account ' permission
	 * 
	 * @param accountPermission
	 *            account ' permission information
	 */
	@Override
	public void deleteAccountProject(Long accountId) {
		accountProjectRepository.deleteAccountProject(accountId);
		systemService.resetAccountProject();
	}

	/**
	 * return current projectId Modify account ' permission process
	 * 
	 * @param accountId
	 *            identify
	 * @param lstAccountPermission
	 *            list of account ' permission information
	 */
	@Override
	public Long registerAccountProject(Account account, List<AccountProject> lstAccountProject) {

		Long accountId = account.getAccountId();

		Account accountdb = accountRepository.findOneByAccountId(accountId);
		if (accountdb == null) {
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
		
		// DungNN - if remove current project
		boolean unCheckWorking = false;
		Long randomProjectId = null;
		long currentAccountId = account.getCreatedBy();
		
		// delete projects of account.
		accountProjectRepository.deleteAccountProject(accountId);
		// insert account - permission
		if (lstAccountProject != null && lstAccountProject.size() > 0) {
			if (accountProjectRepository.registerAccountProject(accountId, lstAccountProject) <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(AccountRolePermissionConst.SC_ACCOUNTROLEPERMISSION_0019)));
			}

			// DungNN if remove project of this account
			if (FunctionCommon.equals(accountId, currentAccountId)) {
				Long currentProject = account.getWorkingProjectId();
				unCheckWorking = true;
				for (AccountProject accountProject : lstAccountProject) {
					randomProjectId = accountProject.getProjectId();
					if (accountProject.getProjectId().equals(currentProject)) {
						unCheckWorking = false;
						break;
					}
				}
			}
		} else if (FunctionCommon.equals(accountId, currentAccountId)) {
			unCheckWorking = true;
		}
		systemService.resetAccountProject();
		if (unCheckWorking) {
			return randomProjectId;
		}

		return account.getWorkingProjectId();
	}
}
