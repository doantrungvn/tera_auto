/*
 * @(#)ProjectService.java
 *
 * Copyright (c) 2015 NTTDATA Vietnam.
 */
package org.terasoluna.qp.domain.service.project;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ProjectItem;
import org.terasoluna.qp.domain.repository.project.ProjectCriteria;

@Service
public interface ProjectService {
	/**
	 * Finds all project information without condition
	 * 
	 * @return List of projects
	 */
	List<Project> getAllProjectAssignToAccount(Long accountId);

	/**
	 * Finds all project information with search criteria
	 * 
	 * @param criteria
	 *            Project criteria
	 * @return List of all project
	 */
	Page<Project> searchProject(ProjectCriteria criteria, Pageable pageable);

	/**
	 * Register project information
	 * 
	 * @param project
	 *            Project information
	 */
	Project registerProject(Project project);

	/**
	 * Finds a project information with project identify
	 * 
	 * @param criteria
	 *            Project criteria
	 * @return List of all project
	 */
	Project getProjectInformation(Long projectId, Long accountId);

	/**
	 * Modify project information
	 * 
	 * @param project
	 *            Project information
	 */
	void modifyProject(Project project);

	/**
	 * Delete project information
	 * 
	 * @param project
	 *            Project information
	 */
	void deleteProject(Project project);

	/**
	 * Delete project information
	 * 
	 * @param project
	 *            Project information
	 */
	void deleteAssociatedProject(Project project);

	/**
	 * get all project was assigned for user
	 * 
	 * @return
	 * @author dungnn1
	 */
	List<Project> findAllProjectByAccount(Long accountId);

	Project modifyDesignStatus(Project project);

	long validateChangeStatusToFixed(Long projectId);

	List<Module> findAllModuleOfProject(Long projectId);

	List<Module> findAllModuleOfProject(Long projectId, Integer status);

	/**
	 * check design information belong current project, and check design of status project
	 * 
	 * @param projectId
	 * @param accountId
	 * @param workingProjectId
	 * @param checkDesignStatus
	 * @author dungnn1
	 * 
	 * if true -> check if project's design status is fixed then throw business exception
	 */
	void validateProject(Long projectId, Long accountId, Long workingProjectId, boolean checkDesignStatus);

	/**
	 * @param accountId
	 * @param workingProjectId
	 * @author dungnn1
	 */
	void validateProject(Long accountId, Long workingProjectId);

	Project validateProject(CommonModel commonModel);

	Project getProjectInformation(Long projectId, Long accountId, boolean checkDesignStatus);

	Map<String, String> findThemeByProjectId(Long projectId);
	
	Map<String, String> findThemeByProjectIdForHTML(Long projectId);

	void settingTheme(Map<String, String> mapTheme, Long projectId, Long accountId);

	// KhanhTH
	void addProjectItem(Long projectId, List<ProjectItem> projectItem);

	// KhanhTH
	List<ProjectItem> getProjectItemByProjectId(Long projectId, Long languageId);

	boolean checkListAffected(Project project, Boolean flag, int maxLength);

	Map<String, String> findStyleByProjectId(Long projectId);
}
