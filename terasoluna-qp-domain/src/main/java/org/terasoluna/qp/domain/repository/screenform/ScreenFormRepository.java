package org.terasoluna.qp.domain.repository.screenform;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.ScreenForm;

@Repository
public interface ScreenFormRepository {

	Long insertScreenForm(ScreenForm screenForm);
	
	Integer getMaxFormSeqNoByScreenId(Long screenId);
	
	List<ScreenForm> getScreenFormByScreenId(Long screenId);
	
	List<ScreenForm> getScreenFormByLstScreenId(@Param("lstScreenId") List<Long> lstScreenId);
	
	ScreenForm getById ( @Param("screenFormId") Long screenFormId);
}
