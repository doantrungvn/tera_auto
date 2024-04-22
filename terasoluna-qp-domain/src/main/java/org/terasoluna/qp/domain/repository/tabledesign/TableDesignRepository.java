package org.terasoluna.qp.domain.repository.tabledesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.Autocomplete;
import org.terasoluna.qp.domain.model.BusinessLogic;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.service.common.AutocompleteInput;

public interface TableDesignRepository {
	
	Long getSequencesTableDesign(@Param("size") Integer size);
	
	List<TableDesign> findPageByCriteria(
			@Param("criteria") TableDesignCriteria criteria,
			@Param("pageable") Pageable pageable);
	
	List<TableDesign> findTableDDL(@Param("criteria") TableDesignCriteria criteria);
	
	long countByCriteria(@Param("criteria") TableDesignCriteria criteria);
	
	Autocomplete getAllCodeList(AutocompleteInput autoComplete);
	
	Autocomplete getAllCodeListDetail(AutocompleteInput autoComplete);
	
	Autocomplete getAllSqlBuilderAC(AutocompleteInput autoComplete);
	
	Autocomplete getAllSqlBuilderACLoadModify(AutocompleteInput autoComplete);
	
	Autocomplete getAllAutocompleteACLoadModify(AutocompleteInput autoComplete);

	TableDesign findOneById(Long tableDesignId);
	
	List<TableDesignDetails> getTableDesignDetailsByTableId(Long tableDesignId);

	boolean modifyTableDesign(TableDesign tableDesign);
	
	boolean modifyTableDesignCommon(TableDesign tableDesign);
	
	int updateItem(TableDesign tableDesign);
	
	void create(TableDesign tableDesign);
	
	void createTableUseId(TableDesign tableDesign);
	
	void multiCreateTable(@Param("tableDesigns") List<TableDesign> tableDesigns);

	void delete(long tableDesignId);

	long multiDelete(@Param("tableDesigns") List<TableDesign> listOfTables);
	
	List<TableDesign> findAll();

	List<TableDesign> getTableDesignBySubjectAreaId(Long areaId);
	
	List<TableDesign> getTableDesignByProjectId(Long projectId);
	
	//DungNN - add 20150604
	List<TableDesign> getTableDesignByProjectAndSubArea(@Param("projectId") Long projectId,@Param("areaId") Long areaId);
	
	List<TableDesign> getTableDesignByTableId(@Param("listTableId") List<Long> listTableId);
	
	//check when delele
	long checkForeikeyWhenDelete(Long tableDesignId);
	
	int modifyDesignStatus(@Param("tableDesign") TableDesign tableDesign);
	
	List<BusinessLogic> getAllBLogicAffected(@Param("listColumnId") List<Long> listColumnId, @Param("listColumnIdAutoFix") List<Long> listColumnIdAutoFix, @Param("listColumnIdDelete") List<Long> listColumnIdDelete);
	
	List<BusinessLogic> getAllBLogicAffectedByTableDesignId(@Param("tableDesignId") Long tableDesignId);
	
	List<BusinessLogic> getAllBLogicAffectedByColumnIds(@Param("columnIds") List<TableDesignDetails> columnIds);
	
	List<SqlDesign> getAllSqlAffected(@Param("listColumnId") List<Long> listColumnId, @Param("projectId") Long projectId);
	
	Long countNameCodeByProjectId(TableDesign tableDesign);
	
	long multiModify(@Param("tableDesigns") List<TableDesign> listOfTables);
	
	List<TableDesign> getLstTableDesignDetails(@Param("tblDesignIds") String[] tblDesignIds, @Param("projectId") Long projectId);
	
	List<TableDesign> findAllTableDesignsBySqlDesignId(Long sqlDesignId);
	
	Autocomplete getLabelValueBySqlId(@Param("sqlDesignId") Long sqlDesignId);

	List<TableDesign> findAllTableDesignsByModuleId(Long moduleId);
	
	String getNameByTableId(@Param("tableId")Long tableId);
}

