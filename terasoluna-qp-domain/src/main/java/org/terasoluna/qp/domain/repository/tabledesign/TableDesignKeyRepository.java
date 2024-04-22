package org.terasoluna.qp.domain.repository.tabledesign;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.TableDesignKey;
import org.terasoluna.qp.domain.model.TableDesignKeyItem;

public interface TableDesignKeyRepository {
	
	Long getSequencesTableDesignKey(@Param("size") Integer size);

	TableDesignKey findOne(long tableDesignKeyId);

	Collection<TableDesignKey> getAll();
	
	void create(TableDesignKey tableDesignKeys);

	void createArray(@Param("tableDesignKeys") List<TableDesignKey> tableDesignKeys);
	
	void insertArray(@Param("tableDesignKeyItems") List<TableDesignKeyItem> tableDesignKeyItems);
	
	void deleteKeyItemsByColumnId(@Param("columnIdStart") Long columnIdStart, @Param("columnIdEnd") Long columnIdEnd, @Param("tableId") Long tableId);
	
	Long selectMinTblDesignKeyValue(@Param("tableDesignId") Long tableDesignId);

	void update(TableDesignKey tableDesignKey);

	void delete(long tableDesignKeyId);
	
	void deleteAllKeyItems(long tableDesignKeyId);
	
	void deleteByTableDesign(long tableDesignId);
	
	void deleteAllKeyItemsByTableDesign(long tableDesignId);

	Long countBySearchCriteria(TableDesignKey tableDesignKey);

	List<TableDesignKey> findBySearchCriteria(
			@Param("tabledesignkey") TableDesignKey tabledesignkey,
			@Param("pageable") Pageable pageable);
	
	void createTableKeyItem(TableDesignKeyItem tableDesignKeyItem);
	
	List<TableDesignKey> findAllByTableDesign(long tableDesignId);

	//DungNN - add 20150604
	List<TableDesignKey> getAllByProjectAndSubArea(@Param("projectId") Long projectId,@Param("areaId") Long areaId);
	
	List<TableDesignKey> getAllByTableId(@Param("listTableId") List<Long> listTableId);
	
	List<TableDesignKeyItem> findAllByTableDesignKey(@Param("tableDesignKeyId") Long tableDesignKeyId);
	
	List<TableDesignKeyItem> findAllKeyItemByKeyId(@Param("tableId") Long tableId);

	List<TableDesignKey> findAllKeyByProjectId(Long projectId);
	
	List<TableDesignKeyItem> findKeyItem(@Param("projectId") Long projectId);
	
	boolean deleteKeyByTableDesignId(Long tableDesignId);
	
	/**
	 * 
	 * @param listOfId
	 * @author dungnn1
	 */
	void multiDelete(@Param("listOfId") List<Long> listOfId);
	
	//VinhHV - add 20160106
	List<TableDesignKey> getAllByProject(@Param("projectId") Long projectId);
}
