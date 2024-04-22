package org.terasoluna.qp.domain.repository.accountloginattempt;

import org.terasoluna.qp.domain.model.AccountLoginAttempt;

public interface AccountLoginAttemptRepository {
	AccountLoginAttempt getAccountLoginAttemptByAccountId(long accountId);
	void register(AccountLoginAttempt accountLoginAttempt);
	boolean modify(AccountLoginAttempt accountLoginAttempt);
}
