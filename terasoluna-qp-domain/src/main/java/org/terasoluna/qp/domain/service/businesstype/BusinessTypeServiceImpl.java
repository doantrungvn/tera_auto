/*
 * @(#)BusinessTypeServiceImpl.java
 *
 * Copyright (c) 2015 NTTDATA Vietnam.
 */
package org.terasoluna.qp.domain.service.businesstype;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.BusinessTypeMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.BusinessType;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.repository.businesstype.BusinessTypeCriteria;
import org.terasoluna.qp.domain.repository.businesstype.BusinessTypeRepository;
import org.terasoluna.qp.domain.repository.module.ModuleRepository;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class BusinessTypeServiceImpl implements BusinessTypeService {

	@Inject
	public BusinessTypeRepository businessTypeRepository;
	
	@Inject
	ModuleRepository moduleRepository;	
	
	
	@Inject
	ProjectService projectService;
	

	@Inject
	Mapper beanMapper;

	/**
	 * Finds all business type information by search condition
	 * @param criteria BusinessTypeCriteria
	 * @param pageable Pageable
	 * @return List of business types
	 */
	@Override
	public Page<BusinessType> searchBusinessType(BusinessTypeCriteria businessTypeCriteria, Pageable pageable) {

		Long totalCount = businessTypeRepository.countBySearchCriteria(businessTypeCriteria);

		List<BusinessType> businessTypes = null;
		
		if (0 < totalCount) {
			businessTypes = businessTypeRepository.findBySearchCriteria(businessTypeCriteria, pageable);
		} else {
			businessTypes = Collections.emptyList();
		}
		
		Page<BusinessType> page = new PageImpl<BusinessType>(businessTypes, pageable, totalCount);
		return page;
	}
	
	/**
	 * Finds a business type information by identify
	 * @param businessTypeId Long
	 * @exception in case business type does not exist the business exception will be thrown
	 * @return businessType BusinessType
	 */
	@Override
	public BusinessType findBusinessTypeById(BusinessType bType, CommonModel commonModel) {
		BusinessType businessType = businessTypeRepository.findById(bType.getBusinessTypeId());
		if (businessType == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(BusinessTypeMessageConst.SC_BUSINESSTYPE_0004)));
		}
		
		//DungNN - add check belong and design status of working project
		projectService.validateProject(businessType.getCreatedBy(), businessType.getProjectId());
		return businessType;
	}
	
	/**
	 * Find List Module by businessType
	 * @param businessType
	 * @return list module in business type
	 */
	@Override
	public List<Module> findListModule(Long businessTypeId) {
		// Finds all related module information to business type
		return moduleRepository.findAllModuleByBusinessTypeId(businessTypeId);
	}

	/**
	 * Register a business type
	 * @exception in case business type code is duplicated the business exception will be thrown
	 * @param businessType BusinessType
	 */
	@Override
	public void registerBusinessType(BusinessType businessType) {
		
		
		//check exist and design status of working project
		/*projectService.initData(businessType.getProjectId(), businessType.getAccountId());*/
		projectService.validateProject(businessType.getAccountId(), businessType.getProjectId());

		Long totalCount = businessTypeRepository.countNameCodeByBusinessTypeId(businessType);
		ResultMessages resultMessages = ResultMessages.error();
		
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(BusinessTypeMessageConst.SC_BUSINESSTYPE_0001));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(BusinessTypeMessageConst.SC_BUSINESSTYPE_0002));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(BusinessTypeMessageConst.SC_BUSINESSTYPE_0001));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(BusinessTypeMessageConst.SC_BUSINESSTYPE_0002));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			// Setting user information/ last modified
			/*businessType.setCreatedBy(accountId);*/
			businessType.setCreatedDate(FunctionCommon.getCurrentTime());
			/*businessType.setUpdatedBy(accountId);*/
			businessType.setUpdatedDate(FunctionCommon.getCurrentTime());
			businessType.setCreatedBy(businessType.getAccountId());
			businessType.setUpdatedBy(businessType.getAccountId());
			
			businessTypeRepository.register(businessType);
		}
	}

	/**
	 * Modify a business type
	 * @param businessType BusinessType
	 * @return affected rows will be returned
	 */
	@Override
	public void modifyBusinessType(BusinessType businessType) {
		CommonModel commonModel = new CommonModel();
		BusinessType businessTypeForUpdate = findBusinessTypeById(businessType, commonModel);

		businessType.setProjectId(businessType.getProjectId());
		Long totalCount = businessTypeRepository.countNameCodeByBusinessTypeId(businessType);
		
		ResultMessages resultMessages = ResultMessages.error();
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(BusinessTypeMessageConst.SC_BUSINESSTYPE_0001));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(BusinessTypeMessageConst.SC_BUSINESSTYPE_0002));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(BusinessTypeMessageConst.SC_BUSINESSTYPE_0001));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(BusinessTypeMessageConst.SC_BUSINESSTYPE_0002));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			// reflection save changes
			businessTypeForUpdate.setBusinessTypeCode(businessType.getBusinessTypeCode());
			businessTypeForUpdate.setBusinessTypeName(businessType.getBusinessTypeName());
			businessTypeForUpdate.setParentBusinessTypeId(businessType.getParentBusinessTypeId());
			businessTypeForUpdate.setRemark(businessType.getRemark());
			businessTypeForUpdate.setUpdatedDate(businessType.getUpdatedDate());
			businessTypeForUpdate.setUpdatedBy(businessType.getAccountId());
			businessTypeForUpdate.setSysDatetime(FunctionCommon.getCurrentTime());
			
			if (businessTypeRepository.modify(businessTypeForUpdate) <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(BusinessTypeMessageConst.SC_BUSINESSTYPE_0004)));
			}
		}
	}

	/**
	 * Delete a business type
	 * @param businessType BusinessType
	 * @return affected rows will be returned
	 */
	@Override
	public void deleteBusinessType(BusinessType businessType) {
		CommonModel commonModel = new CommonModel();
		BusinessType businessTypeForDelete = findBusinessTypeById(businessType, commonModel);

		if (DateUtils.compare(businessTypeForDelete.getUpdatedDate(), businessType.getUpdatedDate()) == 1) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}
		
		
		// Data is being used by another function
		if (null == businessTypeForDelete.getModules() || businessTypeForDelete.getModules().isEmpty() ) {
			for(BusinessType obj : businessTypeRepository.findAll(businessTypeForDelete.getProjectId())) {
				if(businessTypeForDelete.getBusinessTypeId().equals(obj.getParentBusinessTypeId())){
					throw new BusinessException(ResultMessages.error().add(BusinessTypeMessageConst.ERR_BUSINESSTYPE_0010));
				}
			}
			businessTypeRepository.delete(businessTypeForDelete);
		} else {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0097, MessageUtils.getMessage(CommonMessageConst.TQP_MODULE)));
		}
	}

	/**
	 * Finds all business type information of working project without condition
	 * @return List of business types
	 */
	@Override
	public Collection<BusinessType> findAll(Long projectId) {
		return businessTypeRepository.findAll(projectId);
	}

	/**
	 * Finds all business types information without condition
	 * @return List of business types
	 */
	@Override
	public List<BusinessType> findAllBusinessTypeTree(Long projectId) {
		return businessTypeRepository.findAllBusinessTypeTree(projectId);
	}

	@Override
	public List<BusinessType> findAllBusinessTypeTreeNotThis(Long businessTypeId, Long projectId) {
		return businessTypeRepository.findAllBusinessTypeTreeNotThis(businessTypeId, projectId);
	}
}
