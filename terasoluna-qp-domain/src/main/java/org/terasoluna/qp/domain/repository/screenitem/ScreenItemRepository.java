package org.terasoluna.qp.domain.repository.screenitem;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemCodelist;

@Repository
public interface ScreenItemRepository {

	Long insertScreenItem(ScreenItem screenItem);

	List<ScreenItem> getScreenItemByScreenAreaId(Long screenAreaId);

	Integer getMaxSeqNoByScreenAreaId(Long screenAreaId);

	List<ScreenItem> getScreenItemSingleByScreenAreaId(Map<String, Object> params);

	List<ScreenItem> getScreenItemGroupByScreenAreaId(Map<String, Object> params);

	//DungNN - 20150821 - Get all screen to generate db
	List<ScreenItem> getAllScreenItemInfoByModuleIdForGenerateDb(@Param("moduleId") Long moduleId, @Param("languageId") Long languageId);

	List<ScreenItem> getAllScreenItemByLstScreenId(@Param("lstScreenId") List<Long> lstScreenId, @Param("languageId") Long languageId);

	List<ScreenItem> getAllScreenItemByLstScreenAreaId(@Param("lstScreenArea") List<ScreenArea> lstScreenArea, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	//VinhHV
	List<ScreenItem> getAllScreenItemByScreenIdForGenScreenDesign(@Param("screenId") Long screenId, @Param("languageId") Long languageId);

	List<OutputBean> getOutputBeanByScreenItems (@Param("screenItems") List<ScreenItem> screenItems);

	List<ScreenItem> getListScreenItemByScreenId(@Param("screenId") Long screenId);

	List<ScreenItem> getListScreenItemByScreenIdAndAjax(@Param("screenId") Long screenId, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<OutputBean> getAllOutputBeanByScreenItemIds(@Param("listScreenItemIds") List<ScreenItem> listScreenItemIds,@Param("listBusinessDesigns") List<BusinessDesign> listBusinessDesigns);

	List<OutputBean> getParentOutputBeanByOutputBeanId(@Param("lstOutput") List<OutputBean> lstOutput);

	List<InputBean> getAllInputBeanByScreenItemIds(@Param("listScreenItemIds") List<ScreenItem> listScreenItemIds);

	List<ScreenItem> getAllScreenItemHidden(Long screenId);

	//VinhHV - 20160303
	List<OutputBean> getAllOutputBeanByScreenId (@Param("screenId") Long screenId);

	ScreenItem getScreenItemById(@Param("screenItemId") Long screenItemId);

	List<ScreenItem> getAllScreenItemByBusinessLogicId(@Param("businessLogicId") Long businessLogicId, @Param("languageId") Long languageId);
	
	List<ScreenItemCodelist> getItemCodelist(@Param("screenItemId") Long screenItemId);
	
	List<ScreenItemCodelist> getItemCodelistByItemName(@Param("screenItemName") String screenItemName, @Param("screenId") Long screenId);
	
	List<ScreenItem> getListScreenItemByAutocompleteId(@Param("autocompleteId") Long autocompleteId);
	
	List<ScreenItem> getListInputOfItemByAutocompleteId(@Param("autocompleteId") Long autocompleteId);
}
