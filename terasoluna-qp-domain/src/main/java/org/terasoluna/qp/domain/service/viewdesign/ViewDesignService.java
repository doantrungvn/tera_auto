package org.terasoluna.qp.domain.service.viewdesign;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignFunctionGroup;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignSearchCriteria;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignCompound;


public interface ViewDesignService{
	Page<SqlDesign> findPageByCriteria(SqlDesignSearchCriteria criteria, Pageable pageable, CommonModel common);

	void registerViewDesign(SqlDesignCompound sqlDesignCompound, CommonModel common);

	boolean delete(SqlDesignCompound autocompleteDesignCompound, CommonModel common);

	SqlDesign findOneById(Long sqlDesignId);

	SqlDesignCompound findCompoundById(Long sqlDesignId);

	void modifyViewDesign(SqlDesignCompound autocomplete, CommonModel common);

	void modifyAdvancedViewDesign(SqlDesignCompound autocomplete, CommonModel common);
	
	void registerAdvancedViewDesign(SqlDesignCompound autocomplete, CommonModel common);

	List<SqlDesignFunctionGroup> findAllFunctionCode(String dialect);

	List<Map<String , String>> getLstTableDesignDetails(String[] tblDesignIds, Long projectId);

	void modifyDesignStatus(SqlDesignCompound sqlDesignCompound, CommonModel common);
}
