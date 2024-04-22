package org.terasoluna.qp.domain.service.accountrole;

import java.util.List;

import org.springframework.stereotype.Service;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountRole;
import org.terasoluna.qp.domain.model.Role;

@Service
public interface AccountRoleService {
	void modifyAccountRole(Long accountId, List<AccountRole> lstAccountRole, Account account);

	void deleteByAccountId(Long accountId);
	
	Account findOneByAccountId(Long accountId) throws BusinessException;
	
	List<Role> getRoleByAccount(Long accountId);
}
