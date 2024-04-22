package org.terasoluna.qp.domain.repository.domaindesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.Autocomplete;
import org.terasoluna.qp.domain.model.Basetype;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignOutput;

@Repository
public interface DomainDesignRepository {

	List<DomainDesign> findPageByCriteria(
			@Param("criteria") DomainDesignCriteria criteria,
			@Param("pageable") Pageable pageable);

	long countByCriteria(@Param("criteria") DomainDesignCriteria criteria);

	boolean registerDomain(DomainDesign domain);
	
	boolean registerMultiple(@Param("domainDesigns") List<DomainDesign> domain);

	DomainDesign findOne(@Param("domainId") Long domainId);
	
	List<DomainDesign> findListByScreenDesignOutput(@Param("lstScreenDesignOutput") List<ScreenDesignOutput> lstScreenDesignOutput);

	boolean modifyDomain(DomainDesign domain);

	boolean deleteDomain(@Param("domainId") Long domainId);
	
	int deleteDomainDesignByProjectId(@Param("projectId") Long projectId);

	List<DomainDesign> findAllByProjectId(@Param("projectId") Long projectId);

	List<Basetype> getAllBasetype(@Param("projectId") Long projectId);

	DomainDesign checkExitsDomainNameOrCode(DomainDesign domainDesign);

	long checkForeignKey(@Param("domainId") Long domainId);
	
	List<DomainDesign> getReferenceById(@Param("domainId") Long domainId);

	List<Autocomplete> listOfTableDeignUsed(@Param("domainDesignId") Long domainDesignId);
	
	Long countNameCodeByDomainId(DomainDesign domain);

	List<DomainDesign> findAllDomainDesignsBySqlDesignId(@Param("sqlDesignId")Long sqlDesignId,@Param("projectId")Long projectId);
	
	List<String> getAllValidationRule();
	
	/**
	 * Get base type only from resource
	 * @param projectId
	 * @return
	 */
	List<Basetype> getAllBasetypeOnly(@Param("projectId") Long projectId);

	List<DomainDesign> findAllDomainDesignsByModuleId(@Param("moduleId")Long moduleId,@Param("projectId") Long workingProjectId);
	
	String getNameByDomainId(@Param("domainId") Long domainId);
	
	List<DomainDesign> getListDomainDesignByAutocompleteId(@Param("autocompleteId") Long automompleteId);
}
