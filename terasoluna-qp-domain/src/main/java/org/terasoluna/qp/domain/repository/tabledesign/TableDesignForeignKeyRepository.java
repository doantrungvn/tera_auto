package org.terasoluna.qp.domain.repository.tabledesign;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.TableDesignForeignKey;

public interface TableDesignForeignKeyRepository {

	TableDesignForeignKey findOne(long tableDesignForeignKeyId);

	Collection<TableDesignForeignKey> getAll();
	
	List<TableDesignForeignKey> findAllByTableDesignDetail(long tableDesignDetailId);
	
	List<TableDesignForeignKey> findAllByTableDesign(long tableId);

	void create(TableDesignForeignKey tableDesignForeignKey);
	
	void createArray(@Param("tableDesignForeignKeys") List<TableDesignForeignKey> tableDesignForeignKeys);
	
	TableDesignForeignKey findDuplicateForeignKey(TableDesignForeignKey tableDesignForeignKey);

	void update(TableDesignForeignKey tableDesignForeignKey);

	void updateWithoutCode(TableDesignForeignKey tableDesignForeignKey);
	
	void delete(long tableDesignForeignKeyId);
	
	void deleteByTableDesignDetail(@Param("listColumnIdDelete") List<Long> listColumnIdDelete);
	
	void deleteByToTableAndColumn(@Param("listTableId") List<Long> listTableId, @Param("listColumnId") List<Long> listColumnId);
	
	void deleteByTableDesign(long tableDesignId);

	Long countBySearchCriteria(TableDesignForeignKey tableDesignForeignKey);

	List<TableDesignForeignKey> findBySearchCriteria(
			@Param("tableDesignForeignKey") TableDesignForeignKey tableDesignForeignKey,
			@Param("pageable") Pageable pageable);

	//DungNN - add 20150604
	List<TableDesignForeignKey> getAllByProjectAndSubArea(@Param("projectId") Long projectId,@Param("areaId") Long areaId);
	
	//VinhHV - add 20160106
	List<TableDesignForeignKey> getAllByProject(@Param("projectId") Long projectId);

	List<TableDesignForeignKey> getFromForeignKeyInfo(Long projectId);
	
	long checkDuplicateKeyCode(@Param("projectId") Long projectId, @Param("fkCode") String fkCode, @Param("tableId") Long tableId);
	
	List<TableDesignForeignKey> getFromForeignKeyInfoByTableId(@Param("listTableId") List<Long> listTableId);
	
	/**
	 * 
	 * @param listOfId
	 * @author dungnn1
	 */
	void multiDelete(@Param("listOfId") List<Long> listOfId);
	
	TableDesignForeignKey findForeignKeyBetweenTwoTables(@Param("tableId") Long tableId,@Param("joinTableId") Long joinTableId);
	
	List<TableDesignForeignKey> getAllForeignKey(Long projectId);
	
	List<TableDesignForeignKey> findForeignKeyByListToColumn(@Param("listColumnId") List<Long> listColumnId);
	
	List<TableDesignForeignKey> findForeignKeyByListColumn(@Param("listColumnId") List<Long> listColumnId);
}
