package org.terasoluna.qp.domain.service.projecttheme;

import java.util.Map;

import org.terasoluna.qp.domain.model.CommonModel;

public interface ProjectThemeService {
	public Map<String, String> getThemeSetting(Long projectId);
	public void saveSetting(Map<String, String> mapTheme, CommonModel common);
}
