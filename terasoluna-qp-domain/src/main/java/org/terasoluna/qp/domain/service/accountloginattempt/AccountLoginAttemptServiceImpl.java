package org.terasoluna.qp.domain.service.accountloginattempt;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.AccountLoginAttempt;
import org.terasoluna.qp.domain.repository.accountloginattempt.AccountLoginAttemptRepository;

@Transactional
@Service
public class AccountLoginAttemptServiceImpl implements AccountLoginAttemptService {
	@Inject
	AccountLoginAttemptRepository accountLoginAttemptRepository;
	
	@Override
	public AccountLoginAttempt getAccountLoginAttemptByAccountId(long accountId) {
		AccountLoginAttempt accountLoginAttempt = accountLoginAttemptRepository.getAccountLoginAttemptByAccountId(accountId);
		
		return accountLoginAttempt;
	}

	@Override
	public void register(AccountLoginAttempt accountLoginAttempt) {
		accountLoginAttemptRepository.register(accountLoginAttempt);
		
	}

	@Override
	public void modify(AccountLoginAttempt accountLoginAttempt) throws BusinessException {
		boolean updated = accountLoginAttemptRepository.modify(accountLoginAttempt);
		
		if (!updated) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}
	}

}
