package org.terasoluna.qp.domain.repository.projecttheme;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.ProjectTheme;

@Repository
public interface ProjectThemeRepository {
	public List<ProjectTheme> getThemeSetting(Long projectId);
	public boolean deleteByProjectId(Long projectId);
	public void addSetting(ProjectTheme projectTheme);
	public void insertStyleDelete(@Param("lstProjectTheme") List<ProjectTheme> lstProjectTheme);

}
