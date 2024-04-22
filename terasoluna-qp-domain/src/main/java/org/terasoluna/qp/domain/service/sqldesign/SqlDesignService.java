package org.terasoluna.qp.domain.service.sqldesign;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignFunctionGroup;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignSearchCriteria;


public interface SqlDesignService{
	Page<SqlDesign> findPageByCriteria(SqlDesignSearchCriteria criteria, Pageable pageable, CommonModel common);

	void registerSqlDesign(SqlDesignCompound sqlDesignCompound,  CommonModel common);

	boolean delete(SqlDesignCompound autocompleteDesignCompound, CommonModel common);

	SqlDesign findOneById(Long sqlDesignId);

	SqlDesignCompound findCompoundById(Long sqlDesignId);
	
	SqlDesignCompound findCompoundForGenerationById(Long sqlDesignId);


	void modifySqlDesign(SqlDesignCompound autocomplete, CommonModel common);

	void modifyAdvancedSqlDesign(SqlDesignCompound autocomplete, CommonModel common);
	
	void registerAdvancedSqlDesign(SqlDesignCompound autocomplete, CommonModel common);

	List<SqlDesignFunctionGroup> findAllFunctionCode(String dialect);

	List<Map<String , String>> getLstTableDesignDetails(String[] tblDesignIds, Long projectId);

	void modifyDesignStatus(SqlDesignCompound sqlDesignCompound, CommonModel common);

	void findAllDeletionAffection(SqlDesignCompound sqlDesignCompound, CommonModel common);
	
	SqlDesignResult[] findAllBySqlDesignId(Long sqlDesignId);
	
	SqlDesign findById(Long sqlDesignId);

	SqlDesignResult[] findAllSqlDesignResults(SqlDesignCompound sqlDesignCompound);
}
