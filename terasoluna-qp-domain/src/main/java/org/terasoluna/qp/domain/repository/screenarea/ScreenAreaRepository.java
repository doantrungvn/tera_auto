package org.terasoluna.qp.domain.repository.screenarea;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenAreaSortMapping;

@Repository
public interface ScreenAreaRepository {

	Long insertScreenArea(ScreenArea screenArea);

	Integer getMaxScreenAreaSeqNoByScreenFormId(Long screenFormId);

	List<ScreenArea> getScreenAreaByScreenId(Map<String, Object> sqlParam);

	List<ScreenArea> getScreenAreaByLstScreenId(@Param("lstScreenDesign") List<Long> lstScreenDesign, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<ScreenArea> getLstScreenAreaByScreenId(@Param("screenId") Long screenId, @Param("languageId") Long languageId , @Param("projectId") Long projectId);

	void updateObjectMappingTypeOfScreenArea(@Param("screenArea") ScreenArea screenArea);

	ScreenArea getScreenAreaByAreaId(@Param("screenAreaId") Long screenAreaId);
	
	List<ScreenArea> getLstScreenAreaByScreenFormId(@Param("screenId") Long screenId, @Param("screenFormId") Long screenFormId, @Param("languageId") Long languageId , @Param("projectId") Long projectId);
	
	void insertScreenAreaSortMapping(@Param("areaSorts") List<ScreenAreaSortMapping> areaSorts);
	
	void updateSqlColumnIdOfScreenArea(@Param("screenArea") ScreenArea screenArea);
	
	List<ScreenArea> findScreenAreaByOutputbeanId(@Param("outputbeanId") Long outputbeanId);
	
	List<ScreenArea> findScreenAreaByOutputbeanIdOfTotalCount(@Param("outputbeanId") Long outputbeanId);
	
	List<ScreenArea> findPageableScreenAreaByScreenId(@Param("screenId") Long screenId,@Param("languageId") Long languageId , @Param("projectId") Long projectId);
}
