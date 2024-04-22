/*
 * @(#)MessagesServiceImpl.java
 *
 * Copyright (c) 2015 NTTDATA Vietnam.
 */
package org.terasoluna.qp.domain.service.message;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.Language;
import org.terasoluna.qp.domain.model.Message;
import org.terasoluna.qp.domain.repository.message.MessageRepository;
import org.terasoluna.qp.domain.repository.message.MessageSearchCriteria;
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

	@Inject
	MessageRepository messageRepository;
	
	/**
	 * Finds all messages with input search condition
	 * @param messageSearchCriteria Search criteria
	 * @param pageable Pageable
	 * @return page Search output result
	 */
	public Message findOne(Long messageId) {
		Message message = messageRepository.findOne(messageId);
		if (message == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage("tqp.message")));
		}
		return message;
	}
	
	
	/**
	 * Finds all messages with input search condition
	 * @param messageSearchCriteria Search criteria
	 * @param pageable Pageable
	 * @return page Search output result
	 */
	@Override
	public Page<Message> findPageByCriteria(
			MessageSearchCriteria messageSearchCriteria, Pageable pageable) {
		long totalCount = messageRepository.countByCriteria(messageSearchCriteria);
		
		List<Message> messages = null;
		if (0 < totalCount) {
			messages = messageRepository.findPageByCriteria(messageSearchCriteria, pageable);
		} else {
			messages = Collections.emptyList();
		}
		
		Page<Message> page = new PageImpl<Message>(messages, pageable, totalCount);
		
		return page;
	}

	/**
	 * Update message string for all changes
	 * @param messages Messages[]
	 */
	@Override
	public void modifyMessages(List<Message> messages) {
		for(Message msg : messages){
			Message message = findOne(msg.getMessageId());
			message.setMessageString(msg.getMessageString());
			messageRepository.modify(message);
		}
	}

	/**
	 * Finds all module resource
	 * @return List of message
	 */
	@Override
	public Collection<Message> findAllModuleResource() {
		return messageRepository.findAllModuleResource();
	}


	@Override
	public Integer countByLanguage(Language language) {
		return messageRepository.countByLanguage(language);
	}
}
