/*
 * @(#)ModuleService.java
 *
 * Copyright (c) 2015 NTTDATA Vietnam.
 */
package org.terasoluna.qp.domain.service.module;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ModuleTableMapping;
import org.terasoluna.qp.domain.repository.module.ModuleCriteria;

public interface ModuleService{
	
	/**
	 * Finds all module information by search condition
	 * @param criteria moduleCriteria
	 * @param pageable Pageable
	 * @return List of modules
	 */
	Page<Module> searchModule(ModuleCriteria moduleCriteria, Pageable pageable);
	
	/**
	 * Finds a module information by identify
	 * @param moduleId Long
	 * @return module Module
	 */
	Module findModuleById(Long moduleId );
	
	/**
	 * Finds all module information without condition
	 * @return List of modules
	 */
	Collection<Module> findAllModule();
	
	/**
	 * Register a module
	 * @param module Module
	 */
	void registerModule(Module module, CommonModel common);
	
	/**
	 * Delete a business type
	 * @param businessType BusinessType
	 */
	void deleteModule(Module module, CommonModel common);
	
	/**
	 * Modify a module
	 * @param module Module
	 */
	void modifyModule(Module module, CommonModel common);
		
	List<Module> findByAllPermission();
	
	void deleteAssociatedModule(Module module, CommonModel common);
	
	void modifyDesignStatus(Module module, CommonModel common);
	
	/**
	 * check design information belong current project, and check design of status module
	 * @param moduleId
	 * @param checkDesignStatus: if true -> check if module's design status is fixed then throw business exception
	 * @author dungnn1
	 */
	Module validateModule(Long moduleId, Long accountId, Long workingProjectId, boolean checkDesignStatus);
	Module validateModule(Long moduleId, Long accountId, Long workingProjectId);
	
	Module validateModule(Long moduleId, CommonModel commonModel);
	
	long validateChangeStatusToFixed(Long moduleId);

	ModuleTableMapping[] findAllTableInModule(Long moduleId);
	
	List<Module> findListModuleDependency (Long moduleId, Long projectId);
	
	List<Module> getAllModule(Long projectId, List<Long> listModuleId);
	
	List<Module> findAllModuleOfOnline(Long projectId, Integer moduleType);

	List<Module> findAllModule(Long projectId, Integer moduleType);

	void findAllDeletionAffection(Module module, CommonModel common);

}
