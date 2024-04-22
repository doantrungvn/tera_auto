package org.terasoluna.qp.domain.service.messagedesign;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.repository.messagedesign.MessageDesignCriteria;

public interface MessageDesignService{
	
	/**
	 * Finds all message design information by search condition
	 * @param criteria MessageDesignCriteria
	 * @param pageable Pageable
	 * @return List of message designs
	 */
	Page<MessageDesign> searchMessageDesign(@Param("criteria") MessageDesignCriteria messageDesignCriteria, @Param("pageable") Pageable pageable);
	
	/**
	 * Finds a message design by identify
	 * @param messageDesignId Long
	 * @exception in case message design does not exist the business exception will be thrown
	 * @return messageDesign MessageDesign
	 */
	MessageDesign findMessageDesignById(Long messageDesignId,CommonModel commonModel);
	
	
	/**
	 * Find all message design by message code
	 * @param messageCode String
	 * @return list of message design
	 */
	MessageDesign findAllMessageDesignByCode(MessageDesign messageDesign, int from, CommonModel commonModel);
	
	/**
	 * Register single or multiple message designs
	 * in case of registration message from screen design perspective 
	 * [Message level] must be defined as follows:
	 *  + Message of screen design: 2
	 *  + Message of screen area: 3
	 *  + Message of screen item: 4
	 * @param messageDesign List<MessageDesign>
	 * @param createMessageCode if true -> create new message code
	 */
	List<MessageDesign> registerMessageDesign(List<MessageDesign> messageDesign, boolean createMessageCode);
	
	List<MessageDesign> registerMessageDesign(List<MessageDesign> messageDesign);
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
	public MessageDesign registerMessageDesign(MessageDesign messageDesign);
	
	/**
	 * Modify single or multiple message designs
	 * @param messageDesigns MessageDesign[] 
	 */
	void modifyMessageDesign(Long accountId, MessageDesign... messageDesigns);
	
	/**
	 * Delete a message design
	 * @param messageDesign MessageDesign
	 */
	void delete(MessageDesign messageDesign);
	
	/**
	 * Finds all message design information without condition
	 * @return List of message design
	 */
	Collection<MessageDesign> findAll();
	
	/**
	 * Generate message code
	 * @return Message design with generated message code
	 */
	
	void modifyMultipleMessage(MessageDesign[] messageDesigns);
	
	/**
	 * get all system message of QP
	 * @return
	 */
	List<MessageDesign> getDefaultMessageFromQPSystem();

	boolean checkMessageCodeExist(String messageCode, Long projectId);
	
	boolean checkMessageStringExist(MessageDesign messageDesign);
	
	List<MessageDesign> getMessageByMessageCode(List<MessageDesign> messageDesigns, Long projectId, Long languageId);
}