package org.terasoluna.qp.domain.service.tabledesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;

public interface TableDesignDetailService {
	TableDesignDetails findOne(long tableDesignDetailId);

	List<TableDesignDetails> findAllByTableDesign(long tableDesignId);
	
	List<TableDesignDetails> getAllInformationByTableDesign(long tableDesignId);

	List<TableDesignDetails> getAll();

	List<TableDesignDetails> findAllKey(long tableDesignId);

	void create(TableDesign tableDesignDetail);

	void update(TableDesignDetails tableDesignDetail);
	
	void delete(long tableDesignDetailId);

	void deleteAllByTableDesignId(long tableDesignId);

	Long countBySearchCriteria(TableDesignDetails tableDesignDetail);

	List<TableDesignDetails> findBySearchCriteria(
			@Param("tablelist") TableDesignDetails tableDesignDetail,
			@Param("pageable") Pageable pageable);

	// DungNN - add 20150604
	List<TableDesignDetails> getAllByProjectAndSubArea(Long projectId, Long areaId);

	// checkConcurrenceWithSubjectArea
	long checkConcurrenceWithSubjectArea(Long tableDesignId, Long areaId);

	List<TableDesignDetails> findByListId(@Param("tddIds") List<Long> tddIds);
	
	/**
	 * 20150927 - get table design detail by list table id
	 * @param tddIds
	 * @return
	 * @author dungnn1
	 */
	List<TableDesignDetails> findByListTableDesignId(Long projectId, List<Long> listId);
}
