package org.terasoluna.qp.domain.repository.domaindatatype;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.DomainDatatype;
import org.terasoluna.qp.domain.model.ValidationRule;

@Repository
public interface DomainDatatypeRepository {
	List<DomainDatatype> findPageByCriteria(@Param("criteria") DomainDatatypeCriteria criteria, @Param("pageable") Pageable pageable);
	
	long countByCriteria(@Param("criteria") DomainDatatypeCriteria criteria);
	
	DomainDatatype findOne(@Param("id") long id);
	
	boolean deleteDomainDatatype(@Param("id") long id);
	
	boolean updateDomainDatatype(DomainDatatype modelObj);
	
	void insertDomainDatatype(DomainDatatype modelObj);
	
	DomainDatatype checkExistNameOrCodeOnProject(@Param("criteria") DomainDatatypeCriteria criteria);
	
	List<DomainDatatype> findAllByDomainDesign(@Param("domainDesignId") long id);
	
	List<DomainDatatype> findAllByProjectId(@Param("projectId") Long projectId);
	
	List<ValidationRule> findAllValidationRule();
	
	DomainDatatype findByDomainDesign(@Param("domainId") long domainId);

	List<ValidationRule> findAllValidationRuleByStatus(@Param("status") int status);
	
}
