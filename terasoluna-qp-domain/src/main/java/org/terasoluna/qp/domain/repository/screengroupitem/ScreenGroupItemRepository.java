package org.terasoluna.qp.domain.repository.screengroupitem;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.ScreenGroupItem;

@Repository
public interface ScreenGroupItemRepository {

	void insertScreenGroupItem(ScreenGroupItem screenGroupItem);
	List<ScreenGroupItem> getScreenGroupItemByScreenAreaId(Long screenAreaId);
	Integer getMaxGroupItemSeqNoByScreenAreaId(Long screenAreaId);
	
	void multiInsertScreenGroupItem(@Param("listScreenGroupItem") List<ScreenGroupItem> screenGroupItem);
	
}
