package org.terasoluna.qp.domain.service.account;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.AccountMessageConst;
import org.terasoluna.qp.app.message.AccountProfileMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.ChangePassword;
import org.terasoluna.qp.domain.model.Permission;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.Role;
import org.terasoluna.qp.domain.repository.account.AccountRepository;
import org.terasoluna.qp.domain.repository.account.AccountSearchCriteria;
import org.terasoluna.qp.domain.repository.accountpermission.AccountPermissionRepository;
import org.terasoluna.qp.domain.repository.accountprofile.AccountProfileRepository;
import org.terasoluna.qp.domain.repository.accountrole.AccountRoleRepository;
import org.terasoluna.qp.domain.repository.accounttheme.AccountThemeRepository;
import org.terasoluna.qp.domain.repository.permission.PermissionRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.service.role.RoleService;

@Transactional
@Service
public class AccountServiceImpl implements AccountService {
	@Inject
	AccountRepository accountRepository;
	@Inject
	PasswordEncoder passwordEncoder;
	@Inject
	AccountRoleRepository accountRoleRepository;
	@Inject
	AccountProfileRepository accountProfileRepository;
	@Inject
	AccountThemeRepository accountThemeRepository;
	@Inject
	AccountPermissionRepository accountPermissionRepository;
	@Inject
	RoleService roleService;
	@Inject
	PermissionRepository permissionRepository;
	@Inject
	ProjectRepository projectRepository;
	@Inject
	Mapper beanMapper;

	/**
	 * register account
	 * 
	 * @param account
	 * @throws BusinessException
	 */
	@Override
	public Account register(Account account) throws BusinessException {

		AccountProfile accountprofile = new AccountProfile();
		HashMap<String, String> accountprofileHM = new HashMap<String, String>();
		List<HashMap<String, String>> resources = accountProfileRepository.getDefaultProfile();
		for (HashMap<String, String> map : resources) {
			accountprofileHM.put(map.get("resource_cd"), map.get("value1"));
		}
		beanMapper.map(accountprofileHM, accountprofile);

		Long createdBy = account.getCreatedBy();
		Account accountExist = accountRepository.findOneByUsername(account.getUsername());
		if (accountExist != null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0002)));
		}
		String encodePass = passwordEncoder.encode(account.getPassword());
		account.setPassword(encodePass);
		account.setCreatedBy(createdBy);
		account.setUpdatedBy(createdBy);

		Timestamp currentTime = FunctionCommon.getCurrentTime();
		account.setCreatedDate(currentTime);
		account.setUpdatedDate(currentTime);
		accountRepository.register(account);
		accountprofile.setAccountId(account.getAccountId());
		accountprofile.setCreatedBy(createdBy);
		accountprofile.setUpdatedDate(FunctionCommon.getCurrentTime());
		accountProfileRepository.create(accountprofile);
		return account;
	}

	/**
	 * find account by username
	 * 
	 * @param username
	 */
	@Override
	public Account findOneByUsername(String username) {
		Account account = accountRepository.findOneByUsername(username);
		if (account == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0001)));
		}
		return account;
	}

	/**
	 * find account by AccountId
	 * 
	 * @param accountId
	 */
	@Override
	public Account findOneByAccountId(Long accountId) throws BusinessException {
		Account account = accountRepository.findOneByAccountId(accountId);
		if (account == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0001)));
		}
		// get roles and permission of account
		List<Role> lstRole = roleService.findRoleOfAccount(accountId);
		List<Permission> lstPermission = permissionRepository.getRoleAndPermissionOfAccount(accountId);
		// get distinct module code of permission
		List<String> lstModuleCode = new ArrayList<String>();
		for (Permission obj : lstPermission) {
			if (!lstModuleCode.contains(obj.getModuleCode())) {
				lstModuleCode.add(obj.getModuleCode());
			}
		}
		// get project of account
		List<Project> listProject = projectRepository.findAllProjectByAccount(accountId);
		account.setLstRole(lstRole);
		account.setLstPermission(lstPermission);
		account.setLstModuleCode(lstModuleCode);
		account.setLstProject(listProject);
		return account;
	}

	/**
	 * modify account
	 * 
	 * @param account
	 * @throws BusinessException
	 */
	@Override
	public void modify(Account account) {
		Account accountExist = accountRepository.findOneByAccountId(account.getAccountId());
		if (accountExist == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0001)));
		}
		/* account.setUpdatedBy(SessionUtils.getAccountId()); */
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
	}

	/**
	 * delete account
	 * 
	 * @param account
	 * @throws SystemException
	 */
	@Override
	public void delete(Account account) throws BusinessException {
		Account accountExist = accountRepository.findOneByAccountId(account.getAccountId());
		if (accountExist == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0001)));
		} else {
			accountRepository.delete(account);
		}
	}

	/**
	 * change password
	 * 
	 * @param account
	 * @throws BusinessException
	 */
	@Override
	public void changePassword(ChangePassword form, Account account) throws BusinessException {
		// Account account = SessionUtils.getCurrentAccount();

		if (passwordEncoder.matches(form.getCurrentPassword(), account.getPassword())) {
			account.setPassword(form.getNewPassword());
			account.setUpdatedBy(account.getAccountId());
			account.setSystemTime(FunctionCommon.getCurrentTime());
			account.setSelectedChangePass(true);
			account.setForceChangePassword(false);
			this.modify(account);
		} else {
			throw new BusinessException(ResultMessages.error().add(AccountProfileMessageConst.ERR_ACCOUNTPROFILE_0008));
		}
	}

	/**
	 * change password
	 * 
	 * @param account
	 */
	@Override
	public Page<Account> findPageByCriteria(AccountSearchCriteria sampleCriteria, Pageable pageable) {
		long totalCount = accountRepository.countByCriteria(sampleCriteria);
		List<Account> accounts;
		if (0 < totalCount) {
			accounts = accountRepository.findPageByCriteria(sampleCriteria, pageable);
		} else {
			accounts = Collections.emptyList();
		}
		Page<Account> page = new PageImpl<Account>(accounts, pageable, totalCount);
		return page;
	}

	/**
	 * Get list active roles for account register and modify
	 * 
	 * @return role
	 */
	@Override
	public List<Role> getListActiveRolesForAccountRegisterAndModify() {
		List<Role> listRole = new ArrayList<Role>();
		listRole = accountRepository.getListActiveRolesForAccountRegisterAndModify();
		return listRole;
	}

	@Override
	public long getCurrentValueAccountSequence() {
		long accountId = accountRepository.getCurrentValueAccountSequence();
		return accountId;
	}

	@Override
	public Account getAccountWhenAuthenticationFailed(String username) {
		Account account = accountRepository.getAccountWhenAuthenticationFailed(username);
		return account;
	}
}
