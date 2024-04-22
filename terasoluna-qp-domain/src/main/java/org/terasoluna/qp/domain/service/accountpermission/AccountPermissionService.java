package org.terasoluna.qp.domain.service.accountpermission;

import java.util.List;

import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountPermission;

public interface AccountPermissionService {
	void deleteByAccountPermisson(AccountPermission accountPermission);
	
	Account findOneByAccountId(Long accountId);
	
	void modify(Long accountId, List<AccountPermission> lstAccountPermission, Account account);
}
