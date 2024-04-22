package org.terasoluna.qp.domain.repository.tabledesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.TableDesignDetails;

public interface TableDesignDetailRepository {
	
	TableDesignDetails findOne(long tableDesignDetailId);
	
	List<TableDesignDetails> findAllByTableDesign(long tableDesignId);

	List<TableDesignDetails> getAllInformationByTableDesign(long tableDesignId);
	
	List<TableDesignDetails> findAllKey(long tableDesignId);
	
	void multiCreate(@Param("tableDesignDetails") List<TableDesignDetails> tableDesignDetails);
	
	Long selectMinColumnIdValue(@Param("tableDesignId") Long tableDesignId);

	void create(TableDesignDetails tableDesignDetail);

	void updateTableDesignDetails(@Param("tableDesignDetails") List<TableDesignDetails> tableDesignDetails);
	
	void updateForeignTableDesignDetails(@Param("tableDesignDetails") List<TableDesignDetails> tableDesignDetails);
	
	void update(TableDesignDetails tableDesignDetails);
	
	void delete(long tableDesignDetailId);
	
	void deleteAllByTableDesignId(long tableDesignId);

	Long countBySearchCriteria(TableDesignDetails tableDesignDetail);

	List<TableDesignDetails> findBySearchCriteria(
			@Param("tablelist") TableDesignDetails tableDesignDetail,
			@Param("pageable") Pageable pageable);

	//DungNN - add 20150604
	List<TableDesignDetails> getAllByProjectAndSubArea(@Param("projectId") Long projectId, @Param("areaId") Long areaId);
	
	List<TableDesignDetails> getAllByTableId(@Param("listTableId") List<Long> listTableId);
	
	//VinhHV - add 20160106
	List<TableDesignDetails> getAllTableDesignDetails(@Param("projectId") Long projectId);

	//checkConcurrenceWithSubjectArea
	long checkConcurrenceWithSubjectArea(@Param("tableDesignId") Long tableDesignId, @Param("areaId") Long areaId);

	/**
	 * 
	 * @param listOfId
	 * @author dungnn1
	 */
	void multiDelete(@Param("listOfId") List<Long> listOfId);
	
	List<TableDesignDetails> getAllTableColumnByProject(Long projectId);
	
	List<TableDesignDetails> getTableDesignDetailsByListColumnId(@Param("projectId") Long projectId, @Param("lstColumnId") List<Long> lstColumnId);
	
	//get all table detail id by screen area code to delete.
	List<Long> getToEmptyFromScreen(@Param("projectId") Long projectId, @Param("moduleId") Long moduleId);
	List<TableDesignDetails> findByListId(@Param("tddIds") List<Long> tddIds);
	
	//DungNN - 20150927
	List<TableDesignDetails> findByListTableDesignId(@Param("projectId") Long projectId, @Param("listId") List<Long> listId);
	
	Long getSequencesTableDesignDetails(@Param("size") Integer size);
	
	void registerMultiTableDesignDetailsWithSequence(@Param("tableDesignDetails") List<TableDesignDetails> tableDesignDetails);
	
	List<TableDesignDetails> findDetailsByToColumnForeignKey(@Param("toColumnId") List<Long> toColumnId);
	
	List<TableDesignDetails> findAllByTableDesignAndColumn(@Param("listTableId") List<Long> listTableId, @Param("listColumnId") List<Long> listColumnId);

	List<TableDesignDetails> findByListIdWithBaseType(@Param("ids")List<Long> ids);
	
	List<TableDesignDetails> getListTableDesignDetailByAutoCompleteId(@Param("autocompleteId") Long autocompleteId);
	
	/**
	 * DungNN - 20160730
	 * @param tableDesignDetails
	 */
	void initDefaultForItemType(@Param("tableDesignDetails") List<TableDesignDetails> tableDesignDetails);
}
