package org.terasoluna.qp.domain.service.messagedesign;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.MessageLevel;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LanguageDesignMessageConst;
import org.terasoluna.qp.app.message.MessageDesignMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.repository.languagedesign.LanguageDesignRepository;
import org.terasoluna.qp.domain.repository.messagedesign.MessageDesignCriteria;
import org.terasoluna.qp.domain.repository.messagedesign.MessageDesignRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class MessageDesignServiceImpl implements MessageDesignService {
	
	@Inject
	MessageDesignRepository messageDesignRepository;
	
	@Inject
	LanguageDesignRepository languageDesignRepository;

	@Inject
	LanguageDesignService languageDesignService;

	@Inject
	Mapper beanMapper;
	
	@Inject
	ProblemListRepository problemListRepository;
	
	/*@Inject
	@Named("messageIdSequencer")
	Sequencer<String> messageIdSequencer;*/
	
	@Inject
	ProjectService projectService;
	
	@Inject
	ModuleService moduleService;
	
	/*private static final String SCREEN_REF_COUNT = "screen_ref_count";
	
	private static final String SCREEN_AREA_REF_COUNT = "screen_area_ref_count";
	
	private static final String SCREEN_ITEM_REF_COUNT = "screen_item_ref_count";
	
	private static final String BUSINESS_CHECK_SETTING = "business_check_setting";*/
	
	private static final Integer OTHER = 0;
	
	//private static final Integer AUTO = 1;
	
	private static final Integer MANUAL = 2;

	/**
	 * Finds all message design information by search condition
	 * @param criteria MessageDesignCriteria
	 * @param pageable Pageable
	 * @return List of message designs
	 */
	@Override
	public Page<MessageDesign> searchMessageDesign(MessageDesignCriteria messageDesignCriteria, Pageable pageable) {
		Long totalCount = messageDesignRepository.countByCriteria(messageDesignCriteria);

		List<MessageDesign> searchResults = null;
		if (0 < totalCount) {
			searchResults = messageDesignRepository.findPageByCriteria(messageDesignCriteria, pageable);
			//DungNN - improve performance. change using sql -> using java
			if (searchResults != null) {
				//get list message code from result search. after that get screen name
				List<String> listOfMessageCode = new ArrayList<String>();
				for (MessageDesign messageDesign : searchResults) {
					if (MessageLevel.SCREEN.equals(messageDesign.getMessageLevel())) {
//							|| MessageLevel.SCREEN_AREA.equals(messageDesign.getMessageLevel())
//							|| MessageLevel.SCREEN_ITEM.equals(messageDesign.getMessageLevel())) {
						listOfMessageCode.add(messageDesign.getMessageCode());
					}
				}
				//if exist then query db get information
				if (!listOfMessageCode.isEmpty()) {
					List<MessageDesign> listOfMessageDesign = messageDesignRepository.findByLanguageCodeAndMessageCode(messageDesignCriteria, listOfMessageCode);
					if (CollectionUtils.isNotEmpty(listOfMessageDesign)){
						for (MessageDesign messageDesign : searchResults) {
							String messageCode = messageDesign.getMessageCode();
							//compare and set information again
							for (MessageDesign temp : listOfMessageDesign) {
								if (messageCode.equals(temp.getMessageCode()) /*&& messageDesign.getMessageLevel().equals(temp.getMessageLevel())*/) {
									messageDesign.setScreenName(temp.getScreenName());
									break;
								}
							}
						}
					}
				}
			}
		} else {
			searchResults = Collections.emptyList();
		}
		Page<MessageDesign> page = new PageImpl<MessageDesign>(searchResults, pageable, totalCount);
		return page;
	}

	/**
	 * Finds a message design by identify
	 * @param messageDesignId Long
	 * @exception in case message design does not exist the business exception will be thrown
	 * @return messageDesign MessageDesign
	 */
	@Override
	public MessageDesign findMessageDesignById(Long messageDesignId, CommonModel commonModel) {
		MessageDesign messageDesign = messageDesignRepository.findById(messageDesignId);
		if (messageDesign == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0012)));
		}
	/*	projectService.initData(getWorkingProjectId(), getAccountId());
		projectService.validateProject(messageDesign.getProjectId(), checkDesignStatus);*/
		commonModel.setProjectId(messageDesign.getProjectId());
		if (messageDesign.getModuleId() != null) {
			moduleService.validateModule(messageDesign.getModuleId(), commonModel);
		} else {
			
			projectService.validateProject(commonModel);
		}
		//projectService.initData(common.getWorkingProjectId(), messageDesign.getCreatedBy());
		//projectService.validateProject(messageDesign.getProjectId(), accountId, workingProjectId, true);
		
		return messageDesign;
	}
	
	@Override
	public MessageDesign findAllMessageDesignByCode(MessageDesign messageDesign, int from, CommonModel commonModel) {
		List<MessageDesign> messageDesigns = messageDesignRepository.findAllByCode(messageDesign);
		
		
		if (messageDesigns == null || messageDesigns.isEmpty()) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0012)));
		} else {
			messageDesign = messageDesigns.get(OTHER);
			messageDesign.setMessageDesigns(messageDesigns.toArray(new MessageDesign[messageDesigns.size()]));
		}

		if (messageDesign.getModuleId() != null) {
			Module module = moduleService.validateModule(messageDesign.getModuleId(), commonModel);
			messageDesign.setModuleStatus(module.getStatus());
		} else {
			messageDesign.setModuleStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
			commonModel.setProjectId(messageDesign.getProjectId());
			projectService.validateProject(commonModel);
		}
		
		//DungNN - 20151026 - if view then get screen information
		if (DbDomainConst.NavigatorFrom.VIEW == from) {
			messageDesign.setScreenDesigns(messageDesignRepository.findRelatedScreenDesignById(messageDesign));
			messageDesign.setMenuDesign(messageDesignRepository.getMenuDesignInformation(messageDesign));
			messageDesign.setListOfBusinessDesign(messageDesignRepository.getListOfBDesignAffected(messageDesign));
		}
		
		MessageDesign tmpObject = messageDesignRepository.findById(messageDesign.getMessageDesignId());
		messageDesign.setClassFlg(tmpObject.getClassFlg());
		return messageDesign;
	}
	
	/**
	 * Register single or multiple message designs
	 * in case of registration message from screen design perspective 
	 * [Message level] must be defined as follows:
	 *  + Message of screen design: 2
	 *  + Message of screen area: 3
	 *  + Message of screen item: 4
	 * @param messageDesign List<MessageDesign>
	 */
	@Override
	public List<MessageDesign> registerMessageDesign(List<MessageDesign> messageDesignArray, boolean createMessageCode) {
		
		int sizeArray = messageDesignArray.size();
		
		if (sizeArray == 0 ) {
			return messageDesignArray;
		}

		Long fromLanguageId = messageDesignArray.get(0).getLanguageId();
		Long projectId = messageDesignArray.get(0).getProjectId();
		Long accountId = messageDesignArray.get(0).getAccountId();
		
		if (null == fromLanguageId) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0001)));
		}

		LanguageDesign fromLanguageDesignDb = languageDesignRepository.findByPK(fromLanguageId);
		if (fromLanguageDesignDb == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, 
					MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0018)));
		}

		//only check for register message from message design
		if (accountId != null) {
			projectService.validateProject(fromLanguageDesignDb.getProjectId(), accountId, projectId, true);
		}
		List<LanguageDesign> languages = languageDesignRepository.findAllLanguageDesign(fromLanguageId, projectId);

		List<MessageDesign> messageDesigns = new ArrayList<MessageDesign>();

		MessageDesign messageDesign = null;

		Timestamp currentTime = FunctionCommon.getCurrentTime();
		for (MessageDesign item : messageDesignArray) {
			messageDesign = new MessageDesign();
			/*messageDesign.setMessageDesignId(sequence);*/
			messageDesign.setRemark(item.getRemark());
			messageDesign.setCreatedDate(currentTime);
			/*messageDesign.setCreatedBy(accountId);*/
			messageDesign.setProjectId(item.getProjectId());
			messageDesign.setModuleId(item.getModuleId());
			messageDesign.setUpdatedDate(currentTime);	
			messageDesign.setMessageLevel(item.getMessageLevel());
			messageDesign.setMessageType(item.getMessageType());
			messageDesign.setMessageString(item.getMessageString());
			messageDesign.setGeneratedStatus(MANUAL);
			messageDesign.setLanguageId(item.getLanguageId());
			messageDesign.setClassFlg(item.getClassFlg());
			messageDesign.setModuleCode(item.getModuleCode());
			if (createMessageCode) {
				/*item.setMessageCode("msg." + messageIdSequencer.getNext());*/
				/*if (StringUtils.isEmpty(messageDesign.getModuleCode())) {
					item.setMessageCode("msg_" + GenerateUniqueKey.generateUsingSecureRandom());
				} else {
					item.setMessageCode(GenerateUniqueKey.generateAutoCode("msg_", messageDesign.getModuleCode()));
				}*/
				item.setMessageCode(messageDesign.getAutoMessageCode());
			}

			messageDesign.setMessageCode(StringUtils.lowerCase(item.getMessageCode()));
			messageDesigns.add(messageDesign);
			// Reflection to other languages
			if (null != languages && !languages.isEmpty()) {
				for (LanguageDesign languageDesign : languages) {
					try {
						MessageDesign msgTemp = (MessageDesign) messageDesign.clone();
						msgTemp.setLanguageId(languageDesign.getLanguageId());
						msgTemp.setGeneratedStatus(OTHER);
						messageDesigns.add(msgTemp);
						/*messageDesign = new MessageDesign();
						messageDesign.setMessageString(item.getMessageString());
						messageDesign.setLanguageId(languageDesign.getLanguageId());
						messageDesign.setCreatedBy(accountId);
						messageDesign.setProjectId(item.getProjectId());
						messageDesign.setModuleId(item.getModuleId());
						messageDesign.setCreatedDate(currentTime);
						messageDesign.setUpdatedBy(accountId);
						messageDesign.setUpdatedDate(currentTime);	
						messageDesign.setGeneratedStatus(OTHER);
						messageDesign.setMessageCode(item.getMessageCode());
						messageDesign.setMessageLevel(item.getMessageLevel());
						messageDesign.setMessageType(item.getMessageType());
						messageDesign.setRemark(item.getRemark());
						messageDesign.setClassFlg(item.getClassFlg());*/
					} catch (Exception e) {
						throw new BusinessException(ResultMessages.error().add(MessageDesignMessageConst.ERR_MESSAGEDESIGN_0002));
					}
					/*messageDesigns.add(messageDesign);*/
				}
			}
		}
		if (!messageDesigns.isEmpty()) {
			messageDesignRepository.registerMultiple(messageDesigns);
		}
		
		return messageDesignArray;
	}
	
	/**
	 * Register a single message design
	 * in case of registration message from screen design perspective 
	 * [Message level] must be defined as follows:
	 *  + Message of screen design: 2
	 *  + Message of screen area: 3
	 *  + Message of screen item: 4
	 * @param messageDesign MessageDesign
	 * @return messageDesign MessageDesign with sequence No.
	 */
	@Override
	public MessageDesign registerMessageDesign(MessageDesign messageDesign) {
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		projectService.validateProject(messageDesign.getProjectId(), messageDesign.getAccountId() , messageDesign.getProjectId(),true);
		List<LanguageDesign> languages = languageDesignRepository.findAllLanguageDesign(messageDesign.getLanguageId(), messageDesign.getProjectId());

		messageDesign.setCreatedDate(currentTime);
		messageDesign.setUpdatedDate(currentTime);
		messageDesign.setGeneratedStatus(MANUAL);
		/*String messageCode = "msg." + messageIdSequencer.getNext();*/

		/*String messageCode = null;
		if (StringUtils.isEmpty(messageDesign.getModuleCode())) {
			messageCode = "msg_" + GenerateUniqueKey.generateUsingSecureRandom();
		} else {
			messageCode = GenerateUniqueKey.generateAutoCode("msg_", messageDesign.getModuleCode());
		}
		messageDesign.setMessageCode(messageCode);
		*/

		messageDesign.setMessageCode(messageDesign.getAutoMessageCode());

		List<MessageDesign> listMessageDesign = new ArrayList<MessageDesign>();
		listMessageDesign.add(messageDesign);

		// Reflection to other languages
		if (null != languages && !languages.isEmpty()) {
			for (LanguageDesign languageDesign : languages) {
				try {
					
					MessageDesign msgTemp = (MessageDesign) messageDesign.clone();
					msgTemp.setLanguageId(languageDesign.getLanguageId());
					messageDesign.setGeneratedStatus(OTHER);
					listMessageDesign.add(msgTemp);
				} catch (Exception e) {
					throw new BusinessException(ResultMessages.error().add(
							MessageDesignMessageConst.ERR_MESSAGEDESIGN_0002));
				}
			}
		}

		try {
			messageDesignRepository.registerMultiple(listMessageDesign);
		} catch (Exception e) {
			throw new BusinessException(ResultMessages.error().add(MessageDesignMessageConst.ERR_MESSAGEDESIGN_0002));
		}

		return messageDesign;
	}

	/**
	 * Modify single or multiple message designs
	 * @param messageDesigns MessageDesign[] 
	 */
	@Override
	public void modifyMessageDesign(Long accountId, MessageDesign... messageDesigns) {
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		CommonModel common = new CommonModel();

		List<MessageDesign> listOfMessage = new ArrayList<MessageDesign>();
		for (MessageDesign messageDesign : messageDesigns) {
			//dungnn -> need change to improve performance
			common.setWorkingProjectId(messageDesign.getProjectId());
			common.setCreatedBy(messageDesign.getAccountId());
			MessageDesign messageDesignForUpdate = findMessageDesignById(messageDesign.getMessageDesignId(),common);
			// Map from form to messageForUpdate
			messageDesignForUpdate.setFromGeneratedStatus(messageDesign.getFromGeneratedStatus());
			messageDesignForUpdate.setToGeneratedStatus(messageDesign.getToGeneratedStatus());
			messageDesignForUpdate.setFromSelected(messageDesign.getFromSelected());
			messageDesignForUpdate.setToSelected(messageDesign.getToSelected());
			
			// check chi co update fromStatus thi setToStatus = 0
			if(messageDesign.getFromGeneratedStatus() == null) {
				messageDesignForUpdate.setFromGeneratedStatus(OTHER);
			} else {
				messageDesignForUpdate.setFromGeneratedStatus(messageDesign.getFromGeneratedStatus());
			}

			// check chi co update toStatus thi setFromStatus = 0
			if(messageDesign.getToGeneratedStatus() == null) {
				messageDesignForUpdate.setToGeneratedStatus(OTHER);
			} else {
				messageDesignForUpdate.setToGeneratedStatus(messageDesign.getToGeneratedStatus());
			}
			
			// Case1: don't any change in search result
			messageDesignForUpdate.setMessageString(messageDesign.getMessageString());
			messageDesignForUpdate.setSysDatetime(currentTime);
			
			// Case2: if 1 or 2 field is updated
//			if(messageDesignForUpdate.getFromSelected() == true || messageDesignForUpdate.getToSelected() == true){
//				messageDesignForUpdate.setMessageString(messageDesign.getMessageString());
//				messageDesignForUpdate.setUpdatedDate(messageDesign.getUpdatedDate());
//				messageDesignForUpdate.setUpdatedBy(accountId);
//				messageDesignForUpdate.setGeneratedStatus(MANUAL);
//				messageDesignForUpdate.setSysDatetime(currentTime);
//						
//			} // TH3: unchecked in "manual" va set status = 1
//				else if((messageDesignForUpdate.getFromSelected() == false && messageDesignForUpdate.getFromGeneratedStatus() == MANUAL)  || 
//						(messageDesignForUpdate.getToSelected() == false && messageDesignForUpdate.getToGeneratedStatus() == MANUAL)) {
//				messageDesignForUpdate.setMessageString(messageDesign.getMessageString());
//				messageDesignForUpdate.setUpdatedDate(messageDesign.getUpdatedDate());
//				messageDesignForUpdate.setUpdatedBy(accountId);
//				messageDesignForUpdate.setGeneratedStatus(AUTO);
//				messageDesignForUpdate.setSysDatetime(currentTime);
//			} 
			
			if(messageDesign.getSelected()){
				messageDesignForUpdate.setGeneratedStatus(DbDomainConst.MessageGeneratedStatus.MANUAL_TRANSLATE);
			}else{
				if(messageDesignForUpdate.getGeneratedStatus().equals(DbDomainConst.MessageGeneratedStatus.MANUAL_TRANSLATE)){
					messageDesignForUpdate.setGeneratedStatus(DbDomainConst.MessageGeneratedStatus.Copy);
				}
			}

			listOfMessage.add(messageDesignForUpdate);

			// Setting user information/ last modified
			/*if (messageDesignRepository.modify(messageDesignForUpdate) <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0012)));
			}*/
		}
		if (CollectionUtils.isNotEmpty(listOfMessage)) {
			if (messageDesignRepository.modifyMultipleMessage(listOfMessage) <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0012)));
			}
		}
	}

	/**
	 * Delete a message design
	 * @param messageDesign MessageDesign
	 */
	@Override
	public void delete(MessageDesign messageDesign) {
		CommonModel common = new CommonModel();
		common.setWorkingProjectId(messageDesign.getProjectId());
		common.setCreatedBy(messageDesign.getAccountId());
		common.setDesignStatus(true);

		MessageDesign messageDesignForDelete = findMessageDesignById(messageDesign.getMessageDesignId(),common);
		ResultMessages resultMessages = ResultMessages.error();

		//MessageDesign tempObject = messageDesignRepository.findById(messageDesign.getMessageDesignId());
		if (DataTypeUtils.equals(messageDesignForDelete.getClassFlg(), DbDomainConst.QPCommomFlg.YES)) {
			throw new BusinessException(resultMessages.add(CommonMessageConst.ERR_SYS_0118, MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0012)));
		}
		
		//check Concurrence
		if (DateUtils.compare(messageDesignForDelete.getUpdatedDate(), messageDesign.getUpdatedDate()) == 1) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}

		// Data is being used by another function
		messageDesign.setScreenDesigns(messageDesignRepository.findRelatedScreenDesignById(messageDesign));
		messageDesign.setMenuDesign(messageDesignRepository.getMenuDesignInformation(messageDesign));
		messageDesign.setListOfBusinessDesign(messageDesignRepository.getListOfBDesignAffected(messageDesign));
		
		if (messageDesign.isUsed()) {
			throw new BusinessException(resultMessages.add(CommonMessageConst.ERR_SYS_0097));
		} else {
			messageDesignForDelete.setProjectId(messageDesign.getProjectId());
			messageDesignRepository.delete(messageDesignForDelete);
		}
	}

	// modify on modify screen
	@Override
	public void modifyMultipleMessage(MessageDesign[] messageDesigns) {
		if (ArrayUtils.isEmpty(messageDesigns))
			return;
		
		MessageDesign messageDesignFirst = messageDesigns[0];
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		List<MessageDesign> md = messageDesignRepository.findAllByCode(messageDesignFirst);
		//Check exists or not?
		if(CollectionUtils.isEmpty(md)) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0012)));
		}

		MessageDesign messageDesignFirstDb = md.get(0);

		if (messageDesignFirstDb.getModuleId() != null) {
			moduleService.validateModule(messageDesignFirstDb.getModuleId(), messageDesignFirst.getUpdatedBy(), messageDesignFirst.getProjectId());
		} else {
			projectService.validateProject(messageDesignFirst.getUpdatedBy(), messageDesignFirst.getProjectId());
		}

		List<MessageDesign> listOfMessage = new ArrayList<MessageDesign>();

		for(MessageDesign messageDesign : messageDesigns) {
			if(DbDomainConst.MessageLevel.PROJECT.equals(messageDesign.getMessageLevel())) {
				messageDesign.setModuleId(null);
			}

			messageDesign.setSysDatetime(currentTime);
			listOfMessage.add(messageDesign);
		}

		int rowCount = messageDesignRepository.modifyMultipleMessage(listOfMessage);
		
		if (rowCount <= 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0012)));
		}
	}
	
	/**
	 * Finds all message design information without condition
	 * @return List of message design
	 */
	@Override
	public Collection<MessageDesign> findAll() {
		return messageDesignRepository.findAll();
	}

	@Override
	public List<MessageDesign> getDefaultMessageFromQPSystem() {
		return messageDesignRepository.getDefaultMessageFromQPSystem();
	}
	
	@Override
	public List<MessageDesign> registerMessageDesign(List<MessageDesign> messageDesigns) {
		return registerMessageDesign(messageDesigns, true);
	}

	@Override
	public boolean checkMessageCodeExist(String messageCode, Long projectId) {
		if (messageDesignRepository.checkMessageCodeExist(messageCode, projectId) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkMessageStringExist(MessageDesign messageDesign) {
		if (messageDesignRepository.checkMessageStringExist(messageDesign) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<MessageDesign> getMessageByMessageCode(List<MessageDesign> messageDesigns, Long projectId, Long languageId) {
		List<MessageDesign> messageDesignCodes = messageDesignRepository.getMessageDesignByMessageCoe(messageDesigns,projectId,languageId );
		return messageDesignCodes;
	}

}
