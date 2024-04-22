package org.terasoluna.qp.domain.repository.subjectarea;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.SubjectAreaTableDesign;
import org.terasoluna.qp.domain.model.TableDesign;

public interface SubjectAreaTableRepository {

	List<SubjectAreaTableDesign> getAll();

	List<SubjectAreaTableDesign> findOne(SubjectAreaTableDesign subAreaTableDesign);
	
	List<SubjectAreaTableDesign> findBySubjectArea(long subAreaId);
	
	List<SubjectAreaTableDesign> findByTableDesign(long tableDesignId);

	void create(SubjectAreaTableDesign subAreaTableDesign);
	
	void insertArray(@Param("subAreaTableDesigns") List<SubjectAreaTableDesign> subAreaTableDesigns);

	void delete(@Param("subjectAreaTableDesign") List<SubjectAreaTableDesign> subjectAreaTableDesign);
	
	void deleteAllByTableId(long tableDesignId);

	boolean deleteSubAreaDesignTableByAreaId(Long areaId);

	void insertSubAreaDesignTable(Long areaId, TableDesign tb);
	
	void updateSubjectAreaDesignTable(@Param("areaTableDesigns") List<SubjectAreaTableDesign> areaTableDesigns);
}
