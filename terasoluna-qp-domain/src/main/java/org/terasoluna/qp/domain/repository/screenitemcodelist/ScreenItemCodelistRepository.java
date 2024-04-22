package org.terasoluna.qp.domain.repository.screenitemcodelist;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.ScreenItemCodelist;
import org.terasoluna.qp.domain.model.ScreenItemCodelistDetail;

@Repository
public interface ScreenItemCodelistRepository {

	void insertScreenItemCodelist(ScreenItemCodelist screenItemCodelist);
	void insertListScreenItemCodelist(@Param("lstScreenItemCodeList") List<ScreenItemCodelist> lstScreenItemCodeList);
	List<ScreenItemCodelist> getScreenItemCodelistByScreenItemId(Long screenItemId);
	List<ScreenItemCodelistDetail> getScreenItemCodelistByProject(Long projectId);
}
