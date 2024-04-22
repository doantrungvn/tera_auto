package org.terasoluna.qp.domain.repository.accounttheme;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.AccountTheme;

@Repository
public interface AccountThemeRepository {
	public List<AccountTheme> getThemeSetting(Long accountId);
	public boolean deleteByAccountId(Long accountId);
	public void addSetting(AccountTheme accountTheme);

}
