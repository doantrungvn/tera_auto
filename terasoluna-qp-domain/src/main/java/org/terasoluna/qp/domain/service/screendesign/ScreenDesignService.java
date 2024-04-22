package org.terasoluna.qp.domain.service.screendesign;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.CodeListDetail;
import org.terasoluna.qp.domain.model.InfoModuleForScreenDesign;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.MenuDesign;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenAction;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenGroupItem;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenTransition;
import org.terasoluna.qp.domain.model.ScreenTransitionBranch;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignOutputBeanForSetting;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRegister;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignSearchCriteria;

public interface ScreenDesignService {

	List<ScreenDesignOutput> getColumnsByTableId(long tableId, Long columnId);

	ScreenDesign getScreenInfoById(Long screenId);

	void design(ScreenDesign screenDesign, ScreenForm[] screenForms,
			ScreenArea[] screenAreas, ScreenGroupItem[] screenGroupItems,
			ScreenItem[] screenItems, Long accountId, Long languageId, Long projectId, AccountProfile accountProfile) throws BusinessException;

	Page<ScreenDesign> findPageByCriteria(ScreenDesignSearchCriteria criteria,
		Pageable pageable, Long languageId);

	List<ScreenDesign> findAllByCriteria(ScreenDesignSearchCriteria criteria);

	List<ScreenDesign> getAllScreenInfoByModuleId(Long moduleId, Long languageId);

	List<ScreenAction> findAllActionByScreenId(List<ScreenDesign> scrDesigns, Long languageId, Long projectId);

	boolean updatePositionByScreenId(List<ScreenDesign> scrDesigns);

	List<ScreenDesign> register(ScreenDesignRegister screenDesignRegister, boolean fromTransition, Long languageId, Long projectId, Long accountId);

	ScreenDesign getScreenDesignById(Long screenId, Long languageId, Long projectId);

	public List<CodeListDetail> getTableCodeListDetailById(Long columnId);

	public List<CodeListDetail> getSystemCodeListDetailById(Long codelistId);

	void deleteScreenDesign(ScreenDesign screenDesign,Boolean deleteObjectHasFk, Long accountId, Long languageId, Long projectId);

	//updateScreenActionIdInScreenItem
	void deleteScreenActionForConnect(Long screenFromId, List<ScreenTransition> listScreenTransition);

	public String getScreenParams(Long screenId);

	//truongld:add for prototy Screen list items
	List<ScreenItem> getAllItemsByScreenId(Long screenId, Long languageId, Long projectId);

	//truongld:add for delete screen_item
	void deleteScreenItem(List<ScreenItem> listScreenItemId);

	//truonglv:add for update screen_item
	void updateScreenItem(List<ScreenItem> listScreenItem, Long accountId, Long projectId);

	//truonglv:add for get information
	ScreenDesign findById(Long screenId, Long languageId, Long projectId);

	void screenDesignChangeStatus(ScreenDesign screenDesign, Long accountId);

	void insertGenerateScreen(List<ScreenDesign> screenDesign);

	void saveTransition(List<ScreenDesign> screeDesigns, List<ScreenTransitionBranch> lstBranchs, List<ScreenTransition> connects, List<ScreenTransition> connectUpdates, List<ScreenTransition> connectNews, List<ScreenTransition> lstDelete, List<InfoModuleForScreenDesign> screenInfo,Long accountId, Long languageId, Long projectId);

	List<ScreenDesign> getAllScreenInfoByProjectId(Long projectId, Long languageId);

	List<ScreenDesign> getAllScreenActionChangeParameter (Long screenId, Long languageId);

	ScreenDesign getScreenDesignByIdForPreview(Long screenId, Integer genType, Long languageId, Long projectId);

	//KhanhTH
	List<ScreenDesignOutputBeanForSetting> getOutputBeanForSetting(Long screenId, Long languageId);

	//KhanhTH
	List<ScreenDesignOutputBeanForSetting> getScreenItemForSetting(Long screenId, Long languageId);

	//KhanhTH
	List<ScreenForm> getAllFormItems(List<ScreenForm> screenForms, List<ScreenArea> screenAreas, List<ScreenItem> screenItems);

	Map<String, String> getProjectStyle(Long projectId);

	public String getMenuBuilder(String menuContent, MenuDesign menuDesign, Project project, int menuType, Long projectId);

	ScreenDesign findScreenById(Long screenId, Long languageId, Long projectId);

	public List<InputBean> buildInputBeanFromScreenId(Long screenId, Integer requestMethod, Long screenFormId, Long projectId, Long languageId);
	
	public List<OutputBean> buildOutputBeanFromScreenId(Long screenId, Long projectId, Long languageId);
	
	public String getCodelistByScreenItem(ScreenItem item);
	
	List<ScreenTransition> findAllTransitionByModuleId(Long moduleId, Long projectId, Long languageId, List<ScreenDesign> scrDesigns);
}
