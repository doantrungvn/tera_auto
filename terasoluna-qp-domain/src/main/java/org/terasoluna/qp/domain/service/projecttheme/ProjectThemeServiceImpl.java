package org.terasoluna.qp.domain.service.projecttheme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ProjectTheme;
import org.terasoluna.qp.domain.repository.projecttheme.ProjectThemeRepository;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Transactional
@Service
public class ProjectThemeServiceImpl implements ProjectThemeService {

	@Inject
	ProjectThemeRepository projectThemeRepository;

	@Inject
	ProjectService projectService;

	@Override
	public Map<String, String> getThemeSetting(Long projectId) {
		List<ProjectTheme> lstTheme = projectThemeRepository.getThemeSetting(projectId);
		HashMap<String, String> mapTheme = new HashMap<String, String>();
		for (ProjectTheme projectTheme : lstTheme) {
			mapTheme.put(projectTheme.getCode(), projectTheme.getValue());
		}
		return mapTheme;
	}

	@Override
	public void saveSetting(Map<String, String> mapTheme, CommonModel common) {

		
		// check exists
		projectService.getProjectInformation(common.getProjectId(), common.getCreatedBy(), true);

		// delete old setting
		projectThemeRepository.deleteByProjectId(common.getProjectId());
		for (Entry<String, String> entry : mapTheme.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			ProjectTheme objTemp = new ProjectTheme();
			objTemp.setProjectId(common.getProjectId());
			objTemp.setCode(key);
			objTemp.setValue(value);
			objTemp.setAccountId(common.getCreatedBy());
			projectThemeRepository.addSetting(objTemp);
		}
	}

}
