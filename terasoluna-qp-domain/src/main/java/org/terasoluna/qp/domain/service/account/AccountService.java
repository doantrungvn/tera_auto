package org.terasoluna.qp.domain.service.account;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.ChangePassword;
import org.terasoluna.qp.domain.model.Role;
import org.terasoluna.qp.domain.repository.account.AccountSearchCriteria;

public interface AccountService {
	Account findOneByUsername(String username);

	Account register(Account account) throws BusinessException;

	Account findOneByAccountId(Long accountId) throws BusinessException;

	void modify(Account account) throws BusinessException;

	void delete(Account account) throws BusinessException;

	void changePassword(ChangePassword form, Account account) throws BusinessException;

	Page<Account> findPageByCriteria(AccountSearchCriteria sampleCriteria, Pageable pageable);
	
	List<Role> getListActiveRolesForAccountRegisterAndModify();
	
	long getCurrentValueAccountSequence();
	
	Account getAccountWhenAuthenticationFailed(String username);
}
