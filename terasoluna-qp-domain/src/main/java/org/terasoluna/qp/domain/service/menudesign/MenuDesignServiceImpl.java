package org.terasoluna.qp.domain.service.menudesign;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.MenuDesign;
import org.terasoluna.qp.domain.model.MenuDesignItem;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.repository.languagedesign.LanguageDesignRepository;
import org.terasoluna.qp.domain.repository.menudesign.MenuDesignRepository;
import org.terasoluna.qp.domain.service.messagedesign.MessageDesignService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class MenuDesignServiceImpl implements MenuDesignService {
	
	/*private static final String MESSAGE_TYPE_SCREEN = "sc";*/
	
	/*private static final String DOT = ".";*/
	
	private static final String MENU_DESIGN = "menudesign";
	
	@Inject
	LanguageDesignRepository languageDesignRepository;
	
	@Inject
	MenuDesignRepository menuDesignRepository;
	
	@Inject
	MessageDesignService messageDesignService;
	
	@Inject 
	MenuBuilderService menuBuilderService;
	
	@Inject
	ProjectService projectService; 
	
	private Map<String, Long> mKeyDesignItemClient = new HashMap<String, Long>();
	
	
	
	@Override
	public MenuDesign getMenuDesignInformation(Long projectId, Long languageId) {
		MenuDesign menuDesign = menuDesignRepository.getMenuDesignInformation(new MenuDesign(projectId, languageId));

		if (menuDesign != null) {
			menuDesign.setLanguageId(languageId);
			List<MenuDesignItem> menuDesignItems = menuDesignRepository.findMenuDesignItemByProjectId(menuDesign);
			this.setLevelForMenuDesignItem(menuDesignItems);
			menuDesign.setListMenuDesignItem(menuDesignItems);
		} else {
			menuDesign = new MenuDesign();
			menuDesign.setUpdatedDate(FunctionCommon.getCurrentTime());
			menuDesign.setMenuType(DbDomainConst.MenuDirection.HORIZONTAL);
			menuDesign.setLanguageId(languageId);
			menuDesign.setProjectId(projectId);
		}
		

		return menuDesign;
	}

	@Override
	public void registerMenuDesign(MenuDesign menuDesign, CommonModel common) {
		
		List<MessageDesign> messageDesigns = new ArrayList<MessageDesign>();

		int itemSeqNo = 0;
		Long projectId = menuDesign.getProjectId();
		Long languageId = menuDesign.getLanguageId();
		
		common.setProjectId(projectId);
		common.setDesignStatus(true);
		projectService.validateProject(common);

		Timestamp currentTime = FunctionCommon.getCurrentTime();

		MessageDesign messageDesign = poplateMessageDesign(menuDesign.getHeaderMenuName(), itemSeqNo++, projectId, languageId);

		menuDesign.setSystemTime(currentTime);
		menuDesign.setUpdatedBy(common.getCreatedBy());

		if (menuDesignRepository.checkExists(projectId) > 0) {
			//check message exists
			if (!messageDesignService.checkMessageCodeExist(messageDesign.getMessageCode(), projectId)) {
				messageDesigns.add(messageDesign);
			}
			// Check Concurrence when delete
			menuDesign.setMessageCode(messageDesign.getMessageCode());
			if(!menuDesignRepository.updateMenuDesign(menuDesign)){
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
			menuDesignRepository.deleteMenuDesignItem(projectId, menuDesign.getMenuId());
		} else {
			menuDesign.setHeaderMenuName(messageDesign.getMessageCode());
			messageDesigns.add(messageDesign);
			menuDesign.setCreatedBy(common.getCreatedBy());
			menuDesign.setUpdatedBy(common.getCreatedBy());
			menuDesignRepository.createMenuDesign(menuDesign);
		}

		mKeyDesignItemClient.clear();
		Long menuId = menuDesign.getMenuId();
		Long startSequence;

		List<MenuDesignItem> menuDesignItems = menuDesign.getListMenuDesignItem();

		if(FunctionCommon.isNotEmpty(menuDesignItems)){
			Long sequencesMenuDesignItem = menuDesignRepository.getSequencesMenuDesignItem(menuDesignItems.size() - 1);
			startSequence = sequencesMenuDesignItem - (menuDesignItems.size() - 1);
			Map<String, Long> mapKeyInput = new HashMap<String, Long>();
			
			for (MenuDesignItem menuDesignItem : menuDesignItems) {
				messageDesign = new MessageDesign();
				mapKeyInput.put(menuDesignItem.getMenuItemId(), startSequence);
				mKeyDesignItemClient.put(menuDesignItem.getMenuItemId(), startSequence);
				menuDesignItem.setMenuItemId(startSequence.toString());
				startSequence++;
				if ("".equals(menuDesignItem.getParentMenuItemId())) {
					menuDesignItem.setParentMenuItemId(null);
				}
				if(menuDesignItem.getMenuName() == null){
					menuDesignItem.setMenuName("");
				}

				// map key of parent
				if (mapKeyInput.containsKey(menuDesignItem.getParentMenuItemId())) {
					menuDesignItem.setParentMenuItemId(mapKeyInput.get(menuDesignItem.getParentMenuItemId()).toString());
				}
				if (DbDomainConst.MenuItemType.MENU_ITEM.equals(menuDesignItem.getMenuItemType())) {
					messageDesign = this.poplateMessageDesign(menuDesignItem.getMenuName(), itemSeqNo++, projectId, languageId);
					messageDesigns.add(messageDesign);
					menuDesignItem.setMenuName(messageDesign.getMessageCode());
				}

				menuDesignItem.setMenuId(menuId);
			}
		}
		
		if (FunctionCommon.isNotEmpty(messageDesigns)) {
			messageDesignService.registerMessageDesign(messageDesigns, false);
		}

		menuDesignRepository.createMenuDesignItem(menuDesignItems);
	}
	
	/**
	 * populate new message design
	 * 
	 * @return the new MessageDesign object
	 */
	private MessageDesign poplateMessageDesign(String message, int seqNo, Long projectId, Long languageId) {
		MessageDesign messageDesign = new MessageDesign();
		messageDesign.setMessageString(message);
		messageDesign.setMessageLevel(DbDomainConst.MessageLevel.MENU_DESIGN);
		messageDesign.setMessageCode(this.generateMessageCode(seqNo));
		messageDesign.setMessageType(DbDomainConst.MessageType.LABEL_MESSAGE);
		messageDesign.setProjectId(projectId);
		messageDesign.setGeneratedStatus(DbDomainConst.MessageGeneratedStatus.MANUAL_TRANSLATE);
		messageDesign.setLanguageId(languageId);
		
		return messageDesign;
	}
	
	private String generateMessageCode(int seq){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(DbDomainConst.MessageType.LABEL_MESSAGE);
		stringBuilder.append(MessageDesign.STR_DOT);
		stringBuilder.append(MENU_DESIGN);
		stringBuilder.append(MessageDesign.STR_DOT);
		stringBuilder.append(StringUtils.leftPad(String.valueOf(seq), 4, "0"));
		return stringBuilder.toString();
	}

	@Override
	public void setLevelForMenuDesignItem(List<MenuDesignItem> menuDesignItems) {
		if (FunctionCommon.isEmpty(menuDesignItems)) {
			return;
		}
		// set level of input bean
		Map<String, String> mapTableIndex = new HashMap<String, String>();
		Map<String, Integer> mapSequence = new HashMap<String, Integer>();
		for (MenuDesignItem menuDesignItem : menuDesignItems) {
			String currentGroup = "";
			if (menuDesignItem.getParentMenuItemId() != null) {
				currentGroup = mapTableIndex.get(menuDesignItem.getParentMenuItemId());
			}
			
			menuDesignItem.setGroupId(currentGroup);
			int maxIndex = mapSequence.getOrDefault(menuDesignItem.getGroupId(), 0);
			maxIndex++;
			if (StringUtils.isBlank(menuDesignItem.getParentMenuItemId())) {
				menuDesignItem.setTableIndex(String.valueOf(maxIndex));
			} else {
				menuDesignItem.setTableIndex(currentGroup + "." + maxIndex);
			}
			
			mapTableIndex.put(menuDesignItem.getMenuItemId(), menuDesignItem.getTableIndex());
			mapSequence.put(menuDesignItem.getGroupId(), maxIndex);
		}
	}
	
	@Override
	public MenuDesign getMenuDesignForPreview(Long projectId, Long languageId) {

		MenuDesign menuDesign = menuDesignRepository.getMenuDesignInformation(new MenuDesign(projectId, languageId));

		if (menuDesign == null) {
			return menuDesign;
		}

		menuDesign.setListMenuDesignItem(menuDesignRepository.getMenuDesignItemForPreview(new MenuDesign(projectId, languageId)));

		return menuDesign;
	}
	
	@Override
	public MenuDesign getMenuDesignForGenerateJSP(Long projectId, Long languageId) {
		MenuDesign menuDesign = menuDesignRepository.getMenuDesignInformationForJSP(new MenuDesign(projectId, languageId));
		if (menuDesign == null) {
			return menuDesign;
		}
		menuDesign.setListMenuDesignItem(menuDesignRepository.getMenuDesignItemForGenerateJSP(new MenuDesign(projectId, languageId)));
		return menuDesign;
	}
	
	/**
	 * Delete Menu design.
	 *//*
	public void deleteMenuDesign(MenuDesign menuDesign, Long projectId){
		
		//validate project
		projectService.initData(projectId, getAccountId());
		projectService.validateProject(projectId, true);
		MenuDesign design = menuDesignRepository.findMenuDesignByProjectId(projectId);
		if(design == null){
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_MENUDESIGN)));
		}
		menuDesignRepository.deleteMenuDesignItem(projectId, menuDesign.getMenuId());
		menuDesignRepository.deleteMenuDesign(projectId, menuDesign.getMenuId());
	}*/

	@Override
	public String buildMenu(MenuDesign menuDesign, int pmenuFor) {
		return menuBuilderService.build(menuDesign, pmenuFor);
	}

	@Override
	public MenuDesign getNavigationInformationForJSP(Long projectId, Long screenId, Long headerMenuAction) {
		MenuDesign menuDesign = menuDesignRepository.getNavigationInformationForJSP(projectId, screenId, headerMenuAction);
		if (menuDesign == null) {
			return menuDesign;
		}
		return menuDesign;
	}
}
