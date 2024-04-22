package org.terasoluna.qp.domain.service.accounttheme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.qp.domain.model.AccountTheme;
import org.terasoluna.qp.domain.repository.accounttheme.AccountThemeRepository;

@Transactional
@Service
public class AccountThemeServiceImpl implements AccountThemeService {

	@Inject
	AccountThemeRepository accountThemeRepository;

	@Override
	public Map<String, String> getThemeSetting(Long accountId) {
		List<AccountTheme> lstTheme = accountThemeRepository.getThemeSetting(accountId);
		HashMap<String, String> mapTheme = new HashMap<String, String>();
		for (AccountTheme accountTheme : lstTheme) {
			mapTheme.put(accountTheme.getCode(), accountTheme.getValue());
		}
		return mapTheme;
	}

	@Override
	public void saveSetting(Map<String, String> mapTheme, Long accountId) {
		// delete old setting
		accountThemeRepository.deleteByAccountId(accountId);
		for (Entry<String, String> entry : mapTheme.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			AccountTheme objTemp = new AccountTheme();
			objTemp.setAccountId(accountId);
			objTemp.setCode(key);
			objTemp.setValue(value);
			accountThemeRepository.addSetting(objTemp);
		}
	}

}
