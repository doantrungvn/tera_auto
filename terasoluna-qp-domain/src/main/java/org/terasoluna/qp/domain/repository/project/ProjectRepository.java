/*
 * @(#)ProjectRepository.java
 *
 * Copyright (c) 2015 NTTDATA Vietnam.
 */
package org.terasoluna.qp.domain.repository.project;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ProjectItem;
import org.terasoluna.qp.domain.model.ProjectTheme;
import org.terasoluna.qp.domain.model.TableDesign;

public interface ProjectRepository {

	long countBySearchCriteria(@Param("criteria") ProjectCriteria criteria);

	List<Project> findPageBySearchCriteria(@Param("criteria") ProjectCriteria criteria, @Param("pageable") Pageable pageable);

	void register(Project project);
	
	int modify(@Param("project") Project project, @Param("insertMailFlg") Long insertMailFlg);
	
	int synchronizeLicenseDesign(Project project);

	int delete(Project project);
	
	int deleteAssociatedProject(Project project);

	long checkInitQpTable(Long projectId);
		
	List<Project> getAllProjectAssignToAccount(Long accountId);

	Project findById(@Param("projectId")Long projectId, @Param("accountId") Long accountId);
	
	HashMap<String, Long> countReferenceByProjectId(Long projectId);
	
	Long countNameCodeByProjectId(Project project);
	
	List<Project> findAllProjectByAccount(@Param("accountId") Long accountId);
	
	long validateChangeStatusToFixed(Long projectId);
	
	int modifyDesignStatus(Project project);
	
	int modifyAffectChangeDesign(Long projectId);
	
	List<ProjectTheme> findThemeByProjectId(@Param("projectId") Long projectId);
	
	public boolean deleteThemeByProjectId(@Param("projectId") Long projectId);
	
	public void addProjectTheme(ProjectTheme projectTheme);
	
	//KhanhTH
	public void addProjectItem(@Param("list") List<ProjectItem> projectItem);
	
	//KhanhTH
	public void deleteProjectItemByProjectId(@Param("projectId") Long projectId);
	
	//KhanhTH
	public List<ProjectItem> getProjectItemByProjectId(@Param("projectId") Long projectId, @Param("languageId") Long languageId);
	
	public List<TableDesign> getAllTableByProjectId(@Param("projectId") Long projectId, @Param("languageId") Long languageId);

	void modifyDefaultLanguage(Project currentProject);
	
	void deleteInitFunctionMaster(@Param("projectId") Long projectId);
	
	List<Project> findAll();
	
	long checkExistProjectMailAccount(@Param("projectId") Long projectId);
}
