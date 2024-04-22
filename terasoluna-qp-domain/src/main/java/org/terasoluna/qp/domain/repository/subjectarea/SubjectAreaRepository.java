package org.terasoluna.qp.domain.repository.subjectarea;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.SubjectArea;

public interface SubjectAreaRepository {

	List<SubjectArea> findPageBySearchCriteria(
			@Param("criteria") SubjectAreaSearchCriteria criteria,
			@Param("pageable") Pageable pageable);

	long countBySearchCriteria(@Param("criteria") SubjectAreaSearchCriteria criteria);
	
	SubjectArea findOneById(Long areaId);
	
	List<SubjectArea> getAllByProjectId(long projectId);
	
	List<SubjectArea> getAll();
	
	List<SubjectArea> getAllSubAreaByTableId(@Param("tableId") long tableId);
	
	Integer getExitsSubAreaNameOrCode(SubjectArea subjectArea);
	
	boolean updateAreaInforById(SubjectArea area);
	
	List<SubjectArea> getAllPosLstByProjectId(Long projectId);
	
	void insertSubAreaInfor(SubjectArea area);
	
	boolean updatePosByAreaId(@Param("posList") List<SubjectArea> positionList);
	
	boolean deleteSubArea(SubjectArea subjectArea);

}
