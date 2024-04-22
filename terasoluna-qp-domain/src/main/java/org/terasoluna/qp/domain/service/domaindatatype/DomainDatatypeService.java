package org.terasoluna.qp.domain.service.domaindatatype;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.DomainDatatype;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.repository.domaindatatype.DomainDatatypeCriteria;

public interface DomainDatatypeService {
	Page<DomainDatatype> findPageByCriteria(DomainDatatypeCriteria searchCriteria, Pageable pageable);

	DomainDatatype findOne(long id, int flag);
	
	void deleteDomainDatatype(long id);
	
	void updateDomainDatatype(DomainDatatype obj);
	
	List<Module> findAllModuleByTableMappingId(long id); 
	
	Map<Integer, List<DomainDesign>> listOfDomainDesign(Long projectId);
	
	/**
	 * list domain and Radio, Select, Checkbox and autocomplete 
	 * @return
	 */
	Map<String, String> listOfDomainDesignExt(Long projectId);
	
	List<TableDesignDetails> listOfTableDesignDetails(long tableDesignId);
	
	DomainDatatype checkExistNameOrCodeOnProject(DomainDatatypeCriteria searchCriteria);

}
