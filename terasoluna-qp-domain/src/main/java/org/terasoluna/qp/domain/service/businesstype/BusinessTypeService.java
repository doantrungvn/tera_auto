/*
 * @(#)BusinessTypeService.java
 *
 * Copyright (c) 2015 NTTDATA Vietnam.
 */
package org.terasoluna.qp.domain.service.businesstype;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.BusinessType;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.repository.businesstype.BusinessTypeCriteria;

public interface BusinessTypeService{
	
	/**
	 * Finds all business type information by search condition
	 * @param criteria BusinessTypeCriteria
	 * @param pageable Pageable
	 * @return List of business types
	 */
	Page<BusinessType> searchBusinessType(@Param("criteria") BusinessTypeCriteria businessTypeCriteria, @Param("pageable") Pageable pageable);
	
	
	/**
	 * Finds a business type information by identify
	 * @param businessTypeId Long
	 * @return businessType BusinessType
	 */
	BusinessType findBusinessTypeById(BusinessType businessType, CommonModel commonModel);
	
	/**
	 * Register a business type
	 * @param businessType BusinessType
	 */
	void registerBusinessType(BusinessType businessType);
	
	/**
	 * Modify a business type
	 * @param businessType BusinessType
	 */
	void modifyBusinessType(BusinessType businessType);
	
	/**
	 * Delete a business type
	 * @param businessType BusinessType
	 */
	void deleteBusinessType(BusinessType businessType);

	/**
	 * Finds all business types information without condition
	 * @return List of business types
	 */
	public List<BusinessType> findAllBusinessTypeTree(Long projectId);
	
	/**
	 * Finds all business types information not contains this business type
	 * @return List of business types
	 */
	public List<BusinessType> findAllBusinessTypeTreeNotThis(Long businessTypeId, Long projectId);
	
	/**
	 * Finds all business type information without condition
	 * @return List of business types
	 */
	Collection<BusinessType> findAll(Long projectId);
	
	
	/**
	 * Find List Module by businessType
	 * @param businessType
	 * @return
	 */
	List<Module> findListModule(Long businessTypeId);
}
