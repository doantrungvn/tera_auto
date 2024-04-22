package org.terasoluna.qp.domain.repository.businesstype;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.BusinessType;

public interface BusinessTypeRepository {
	
	List<BusinessType> findAll(@Param("projectId") Long projectId);
	
	Long countBySearchCriteria(@Param("criteria")BusinessTypeCriteria criteria);
	
	List<BusinessType> findAllBySearchCriteria();
	
	List<BusinessType> findBySearchCriteria(@Param("criteria") BusinessTypeCriteria criteria, @Param("pageable") Pageable pageable);
	
	BusinessType findById(Long businessTypeId);
	
	BusinessType findByCode(BusinessType businessType);
	
	void register(BusinessType businessType);
	
	int modify(BusinessType businessType);
	
	int delete(BusinessType businessType);
	/**
     * Finds all of the business types
     * @return a List of business types
     */
	public List<BusinessType> findAllBusinessTypeTree(@Param("projectId") Long projectId);
	
	public List<BusinessType> findAllBusinessTypeTreeNotThis(@Param("businessTypeId") Long businessTypeId, @Param("projectId") Long projectId);
	
	Long countNameCodeByBusinessTypeId(BusinessType businessType);
	
	BusinessType selectFirstBusinessType();
}
