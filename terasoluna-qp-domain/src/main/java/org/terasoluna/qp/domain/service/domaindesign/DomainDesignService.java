package org.terasoluna.qp.domain.service.domaindesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.Autocomplete;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.ValidationRule;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignCriteria;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.GroupDataTypeDB;

public interface DomainDesignService {
	Page<DomainDesign> findPageByCriteria(
			@Param("criteria") DomainDesignCriteria criteria,
			@Param("pageable") Pageable pageable);

	void registerDomain(DomainDesign domain, CommonModel common);

	DomainDesign findOne(Long domainId, CommonModel common, boolean checkDesignStatus);

	void modifyDomain(DomainDesign domain, List<Autocomplete> listOfTableDesign, CommonModel common);

	void deleteDomain(DomainDesign domain);

	List<GroupDataTypeDB> getAllBasetype(Long projectId, boolean isFromGraphicDesign);
	
	List<ValidationRule> findAllValidationRule();
	
	List<ValidationRule> findAllValidationRuleByStatys(int status);
	
	List<Autocomplete> listOfTableDeignUsed(Long domainDesignId);

	Long checkForeignKey(long id);

	List<DomainDesign> getReferenceById(long id);
	
	void loadListAffected(DomainDesign domainDesign, boolean isDeleted, CommonModel common);
	
	void insertProblem(DomainDesign domainDesignBeforeEdit, DomainDesign domainDesignAfterEdit, CommonModel common);

	void loadUserDefineCodelist(DomainDesign domainDesign, CommonModel common);
	
	DomainDesign setFmtCodelist(DomainDesign domainDesign);
}
