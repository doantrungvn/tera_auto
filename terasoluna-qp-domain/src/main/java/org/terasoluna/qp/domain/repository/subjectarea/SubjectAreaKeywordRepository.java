package org.terasoluna.qp.domain.repository.subjectarea;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.SubjectAreaKeyword;

public interface SubjectAreaKeywordRepository {

	List<SubjectAreaKeyword> getKeywordList(Long areaId);
	
	void deleteKeyWordNotIn(@Param("areaId") Long areaId , 
			@Param("areaKeywords") List<SubjectAreaKeyword> areaKeywords);
	
	boolean updateKeywordByAreaId(@Param("areaKeywords") List<SubjectAreaKeyword> areaKeywords);
	
	void insertKeyword(@Param("areaId") Long areaId ,
			@Param("areaKeyword") SubjectAreaKeyword areaKeyword);
	
	boolean deleteKeyword(long areaId);

}
