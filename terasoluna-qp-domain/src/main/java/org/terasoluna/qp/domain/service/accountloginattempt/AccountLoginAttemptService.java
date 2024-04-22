package org.terasoluna.qp.domain.service.accountloginattempt;

import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.qp.domain.model.AccountLoginAttempt;

public interface AccountLoginAttemptService {
	AccountLoginAttempt getAccountLoginAttemptByAccountId(long accountId);
	void register(AccountLoginAttempt accountLoginAttempt);
	void modify(AccountLoginAttempt accountLoginAttempt) throws BusinessException;
}
