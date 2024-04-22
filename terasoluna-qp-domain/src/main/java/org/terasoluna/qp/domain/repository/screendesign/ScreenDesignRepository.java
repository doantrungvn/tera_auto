package org.terasoluna.qp.domain.repository.screendesign;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CodeListDetail;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ScreenAction;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenAreaEvent;
import org.terasoluna.qp.domain.model.ScreenAreaSortMapping;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenFormTabs;
import org.terasoluna.qp.domain.model.ScreenGroupItem;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemAutocompleteInput;
import org.terasoluna.qp.domain.model.ScreenItemCodelist;
import org.terasoluna.qp.domain.model.ScreenItemEvent;
import org.terasoluna.qp.domain.model.ScreenItemEventMapping;
import org.terasoluna.qp.domain.model.ScreenItemSequence;
import org.terasoluna.qp.domain.model.ScreenParameter;
import org.terasoluna.qp.domain.model.ScreenTransition;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignOutput;

@Repository
public interface ScreenDesignRepository {
	
	//getAllItemsByScreenId
	List<ScreenItem> getAllItemsByScreenId(@Param("screenId")Long screenId, @Param("language") LanguageDesign language);
	
	List<ScreenDesign> findPageBySearchCriteria(
			@Param("criteria") ScreenDesignSearchCriteria criteria,
			@Param("pageable") Pageable pageable);

	long countBySearchCriteria(@Param("criteria") ScreenDesignSearchCriteria criteria);
	
	List<ScreenDesign> getAllScreenInfoByModuleId(@Param("moduleId") Long moduleId, @Param("languageId") Long languageId);
	
	Long countNameCodeByProjectId(Map<String, Object> sqlMap);
	
	ScreenDesign findEnableHomePageByProjectId(@Param("projectId") Long projectId);
	
	ScreenDesign findById(@Param("screenId") Long screenId, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	boolean updatePositionByScreenId(@Param("scrDesigns") List<ScreenDesign> scrDesigns);
	
	boolean updateAllScreenIsDisableHomePageByProjectId(@Param("projectId") Long projectId);
	
	//start delete some tables for delete table screen_item
	void deleteScreenItemByScreenItemId(@Param("listScreenItemId") List<ScreenItem> listScreenItemId);
	//end delete some tables for delete table screen_item
	
	//truonglv:update screen_item
	int updateScreenItem(@Param("listScreenItem") List<ScreenItem> listScreenItem);
	
	//truonglv:updateScreenActionIdInScreenItem
	void updateScreenActionIdByScreenActionIdNotIn(@Param("screenFromId") Long screenFromId , 
			@Param("listScreenTransition") List<ScreenTransition> listScreenTransition);
	
	//truonglv:updateScreenActionIdInScreenItem
	void deleteScreenActionByScreenActionIdNotIn(@Param("screenFromId") Long screenFromId , 
			@Param("listScreenTransition") List<ScreenTransition> listScreenTransition);
	
	//truonglv:updateScreenActionIdInScreenItem
	void updateConnect(@Param("listScreenTransition") List<ScreenTransition> listScreenTransition);
	
	//truonglv:deleteScreenActionParamByScreenActionId
	void deleteScreenActionParamByScreenActionId(@Param("screenFromId") Long screenFromId , @Param("listScreenTransition") List<ScreenTransition> listScreenTransition);
	
	ScreenForm findScreenFormByScreenId(@Param("screenId") Long screenId);
	
	/*ScreenDesign findOne(int moduleId);*/

	List<ScreenDesign> getAll();

	/*List<ScreenDesign> getScreenDesignById(long moduleId);*/
	
	
	Long insertScreenDesign(@Param("listScreenDesgin") List<ScreenDesign> listScreenDesgin);
	
	List<ScreenDesignOutput> getColumnsByTableId(@Param("tableId") long tableId, @Param("columnId") Long columnId);
	
	ScreenDesign getScreenInfoById(Long screenId);
	
	Long screenDesignInsert(ScreenDesign screenDesign);
	
	Long screenFormInsert(ScreenForm screenForm);
	
	Long screenAreaInsert(ScreenArea screenArea);
	
	Long screenGroupItemInsert(ScreenGroupItem screenGroupItem);
	
	Long screenItemInsert(ScreenItem screenItem);
	
	Long screenItemSequenceInsert(ScreenItemSequence screenItemSequence);
	
	Long deleteByScreenId(@Param("screenId") Long screenId, @Param("projectId") Long projectId);
	
	Integer countScreenDesignByScreenCode(Map<String, Object> sqlMap);
	
	Integer countScreenDesignByScreenName(Map<String, Object> sqlMap);
	
	ScreenDesign getScreenDesignById(@Param("screenId") Long screenId, @Param("language") LanguageDesign language, @Param("projectId") Long projectId);
	
	ScreenForm[] getScreenFormByScreenId(@Param("screenId") Long screenId);
	
	ScreenArea[] getScreenAreaByScreenId(@Param("screenId") Long screenId, @Param("language") LanguageDesign language, @Param("projectId") Long projectId);
	
	ScreenArea[] getScreenAreaByScreenIdForGenCode(@Param("screenId") Long screenId, @Param("language") LanguageDesign language, @Param("projectId") Long projectId);
	
	ScreenGroupItem[] getScreenGroupItemByScreenId(@Param("screenId") Long screenId);
	
	ScreenItem[] getScreenItemByScreenId(@Param("screenId") Long screenId, @Param("language") LanguageDesign language, @Param("projectId") Long projectId);
	
	ScreenItem[] getScreenItemByScreenIdForGenCode(@Param("screenId") Long screenId, @Param("language") LanguageDesign language, @Param("projectId") Long projectId);
	
	void screenItemsInsert(@Param("screenItems") List<ScreenItem> screenItems);
	
	Long screenDesignUpdate(ScreenDesign screenDesign);
	
	// Counts for check foreign key to module table
	int countByModuleId(@Param("module") Module module);
	
	Long screenItemGetSequences(@Param("size") Integer size);
	
	ScreenItemCodelist[] getScreenItemCodelistByScreenId(@Param("screenId") Long screenId);
	
	Long screenParameterInsert(ScreenParameter screenParameter);
	
	ScreenParameter[] getScreenParameterByScreenId(@Param("screenId") Long screenId);
	
	void screenHiddenItemsInsert(@Param("screenItems") List<ScreenItem> screenItems);
	
	List<ScreenItem> getScreenHiddenItemByScreenId(@Param("screenId") Long screenId, @Param("language") LanguageDesign language, @Param("projectId") Long projectId);
	
	Long deleteItemByScreenId(@Param("screenId") Long screenId, @Param("projectId") Long projectId);
	
	void screenEventInsert(@Param("screenItemEvents") List<ScreenItemEvent> screenItemEvents);
	
	Long screenItemEventGetSequences(@Param("size") Integer size);
	
	List<ScreenItemEvent> findScreenItemEventByScreenId(@Param("screenId") Long screenId);
	
	List<ScreenItemEventMapping> findScreenItemEventMappingByScreenId(@Param("screenId") Long screenId);
	
	List<ScreenItemEventMapping> findScreenItemEventMappingPreviewByScreenId(@Param("screenId") Long screenId);
	
	Long screenDesignChangeDesignStatus(ScreenDesign screenDesign);
	
	void screenAreaEventInsert(@Param("screenAreaEvents") List<ScreenAreaEvent> screenAreaEvents);
	
	List<ScreenAreaEvent> findScreenAreaEventByScreenId(@Param("screenId") Long screenId);
	
	//DungNN - 20150821 - Get all screen to generate db	
	List<ScreenArea> getAllRegistScreenInfoByModuleIdForGenerateDb(@Param("moduleId") Long moduleId, @Param("languageId") Long languageId);
	int updateDesignMode(@Param("listOfId") List<Long> listOfId, @Param("designStatus") Integer designStatus);
	
	int screenActionDelete(@Param("screenActions") List<ScreenAction> screenActions);
	
	List<ScreenDesign> getAllScreenInfoByProjectId(@Param("projectId") Long projectId, @Param("languageId") Long languageId);
	
	List<ScreenDesign> getAllScreenByScreenIds(@Param("lstScreenId") List<Long> lstScreenId, @Param("languageId") Long languageId);
	
	int updateScreenGroup(@Param("screenDesign") ScreenDesign screenDesign);
	
	//SonLD - update screen item id in Input and Out put of Blogic
	void updateScreenItemIdInBlogic(@Param("screenItems") List<ScreenItem> screenItems);
	
	List<ScreenDesign> findAllScreenDesignsBySqlDesignId(@Param("sqlDesignId")Long sqlDesignId,@Param("languageId")Long languageId);
	
	List<ScreenDesign> getAllScreenActionChangeParameter(@Param("screenId") Long screenId, @Param("languageId") Long languageId);
	
	Long screenDesignGetSequences(@Param("size") Integer size);
	
	Long screenGroupItemGetSequences(@Param("size") Integer size);

	List<ScreenDesign> findAllScreenDesignsByAutocompleteId(@Param("autocompleteId")Long autocompleteId,@Param("languageId")Long currentLanguageId);
	
	List<ScreenDesign> findAllScreenDesignsByLinkedBusinessLogicId(@Param("projectId") Long projectId, @Param("languageId") Long languageId, @Param("businessLogicId") Long businessLogicId);
	
	void multiCreateScreenParameter(@Param("screenParameters")List<ScreenParameter> screenParameters);
	
	void multiInsertscreenItems(@Param("screenItems") List<ScreenItem> screenItems);
	
	List<ScreenFormTabs> findScreenFormTabsByScreenId(@Param("screenId") Long screenId);
	
	List<CodeListDetail> getCodeListDetailByCodeListId(@Param("codeListId") long codeListId);
	
	//VinhHV
	ScreenDesign getScreenDesignByScreenId(@Param("screenId") Long screenId, @Param("languageId") Long languageId);
	
	//KhanhTH
	List<ScreenDesignOutputBeanForSetting> getOutputBeanForSetting(@Param("screenId") Long screenId, @Param("languageId") Long languageId);
	
	//KhanhTH
	List<ScreenDesignOutputBeanForSetting> getScreenItemForSetting(@Param("screenId") Long screenId, @Param("languageId") Long languageId);
	
	//KhanhTH
	List<ScreenForm> getAllFormItems(@Param("screenForms") List<ScreenForm> screenForms, @Param("screenAreas") List<ScreenArea> screenAreas, @Param("screenItems") List<ScreenItem> screenItems);
	
	List<ScreenDesign> getAllScreenByModules(@Param("listModules") List<Module> listModules, @Param("languageId") Long languageId );
	
	List<OutputBean> getAllParentByOutputBeanId(@Param("listOutputBean") List<OutputBean> listOutputBean);
	
	List<ScreenItemAutocompleteInput> findAutocompleteInputByScreenId(@Param("screenId") Long screenId);
	
	ScreenDesign getScreenDesignByScreenActionId(@Param("screenActionId") Long screenActionId);
	
	//DungNN - 20151221 - get all screen of project -> create permission
	List<ScreenDesign> getAllScreenOfProjectId(@Param("projectId") Long projectId);

	List<ScreenDesign> findAllAffactedScreenDesignsByModuleId(@Param("moduleId")Long moduleId,@Param("languageId") Long languageId);
	
	ScreenDesign findScreenDesignByDestinationBlogic(@Param("screenActionId") Long screenActionId);
	
	ScreenDesign findSDByDestinationBlogicTypeScreen(@Param("screenActionId") Long screenActionId);
	
	
	// daipv - 20150413
	int updateInformationByBusinessLogicId(@Param("businessLogicId") Long businessLogicId);
	int updateInputBeanByBusinessLogicId(@Param("businessLogicId") Long businessLogicId);
	int deleteOutputBeanScreenItemMappingByScreenItemId(@Param("screenItemId") Long screenItemId);
	
	List<ScreenItem> getScreenItemIdLoadCodelist(@Param("screenItemCodelist") List<ScreenItem> screenItemLoadCodelist);
	
	void updateScreenDesignCoordinates(@Param("updateScreenDesignCoordinates") List<ScreenDesign> updateScreenDesignCoordinates);
	
	void updateAttributeScreenTransitionId(@Param("lstScreenItems") List<ScreenItem> lstScreenItems);
	List<ScreenAreaSortMapping> findScreenAreaSortByScreenId(@Param("screenId") Long screenId);
	

	void ScreenItemTransitionInsert(ScreenTransition screenTransition);
	
	List<ScreenDesign> findAllScreenDessingByLstBusiness(@Param("businessDesignLst") List<BusinessDesign> businessDesignLst);
	
	String getNameScreenById(@Param("screenId") Long screenId);
	
	List<BusinessDesign> getAllBlogicInitsDefaultByScreenId(@Param("screenId") Long screenId);
	
	List<BusinessDesign> getAllBlogicProcessesDefaultByScreenId(@Param("screenId") Long screenId,@Param("screenForms") ScreenForm[] screenForms);
	
	
}
