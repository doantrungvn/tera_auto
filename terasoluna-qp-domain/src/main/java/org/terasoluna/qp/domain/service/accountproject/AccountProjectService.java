package org.terasoluna.qp.domain.service.accountproject;

import java.util.List;

import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountProject;

public interface AccountProjectService {
	void deleteAccountProject(Long accountId);

	Long registerAccountProject(Account account, List<AccountProject> lstAccountProject);
}
