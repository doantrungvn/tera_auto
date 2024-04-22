package org.terasoluna.qp.domain.repository.domaindatatype;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.DomainDatatypeCodelist;

@Repository
public interface DomainDatatypeCodelistRepository {

	List<DomainDatatypeCodelist> findAllByDomainDatatype(@Param("domainDatatypeId") long domainDatatypeId);

	List<DomainDatatypeCodelist> findAllByDomainDatatypeItem(@Param("domainDatatypeItemId") long domainDatatypeItemId);
	
	List<DomainDatatypeCodelist> findAllByScreenId(@Param("screenId") long screenId);
	
	void deleteAllByDomainDatatype(long id);

	void insertDomainDatatypeCodelist(DomainDatatypeCodelist modelObj);
	
	
}
