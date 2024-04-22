package org.terasoluna.qp.domain.repository.screenitemstatus;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.FormulaDefinition;
import org.terasoluna.qp.domain.model.ScreenItemStatus;

@Repository
public interface ScreenItemStatusRepository {

	/*void insertScreenItemCodelist(ScreenItemCodelist screenItemCodelist);*/
	/*void insertListScreenItemStatus(@Param("lstScreenItemStatus") List<ScreenItemStatus> lstScreenItemStatus);*/
	
	/*List<ScreenItemCodelist> getScreenItemCodelistByScreenItemId(Long screenItemId);
	
	List<ScreenItemCodelistDetail> getScreenItemCodelistByProject(Long projectId);*/
	
	void registerScreenItemStatus(@Param("screenItemStatuses") List<ScreenItemStatus> screenItemStatuses);
	
	void deleteAllScreenItemStatusByFormulaDefinitionId(@Param("lstFormulaDefinition") List<FormulaDefinition> lstFormulaDefinition);
	
	List<ScreenItemStatus> getScreenItemStatusByFormulaDefinitionId(@Param("lstFormulaDefinition") List<FormulaDefinition> lstFormulaDefinition);
	
	
}
