package org.terasoluna.qp.domain.service.accounttheme;

import java.util.Map;

public interface AccountThemeService {
	public Map<String, String> getThemeSetting(Long accountId);
	public void saveSetting(Map<String, String> mapTheme, Long accountId);
}
