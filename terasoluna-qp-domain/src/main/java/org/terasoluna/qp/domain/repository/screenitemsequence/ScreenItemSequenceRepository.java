package org.terasoluna.qp.domain.repository.screenitemsequence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.ScreenItemSequence;

@Repository
public interface ScreenItemSequenceRepository {

	void insertScreenItemSequence(ScreenItemSequence screenItemSequence);
	List<ScreenItemSequence> getScreenItemSequenceByScreenAreaId(Long screenAreaId);
	
	void multiInsertScreenItemSequence(@Param("listOfItem") List<ScreenItemSequence> screenItemSequence);
}
