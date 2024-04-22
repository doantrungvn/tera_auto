package org.terasoluna.qp.domain.repository.domaindatatype;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.DomainDatatypeItem;

@Repository
public interface DomainDatatypeItemRepository {

	List<DomainDatatypeItem> findAllByDomainDatatype(@Param("domainDatatypeId") Long domainDatatypeId);

	DomainDatatypeItem findOneByDomainDatatypeId(@Param("domainDatatypeId") Long domainDatatypeId);

	void updateDomainDatatypeItem(DomainDatatypeItem domainDatatypeItem);

	void insertDomainDatatypeItem(DomainDatatypeItem domainDatatypeItem);

	List<DomainDatatypeItem> getDomainTableMappingItemsByProjectId(Long projectId);
	
	List<DomainDatatypeItem> findByListTableDesignId(@Param("projectId") Long projectId, @Param("listId") List<Long> listId);
}