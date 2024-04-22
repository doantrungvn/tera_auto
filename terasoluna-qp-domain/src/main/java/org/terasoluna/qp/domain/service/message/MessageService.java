package org.terasoluna.qp.domain.service.message;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.Language;
import org.terasoluna.qp.domain.model.Message;
import org.terasoluna.qp.domain.repository.message.MessageSearchCriteria;

public interface MessageService {

	/**
	 * Finds all module resource
	 * @return List of message
	 */
	Collection<Message> findAllModuleResource();
	
	/**
	 * Finds all messages with input search condition
	 * @param messageSearchCriteria Search criteria
	 * @param pageable Pageable
	 * @return page Search output result
	 */
	Page<Message> findPageByCriteria(MessageSearchCriteria messageSearchCriteria, Pageable pageable);

	/**
	 * Update message string for all changes
	 * @param messages Messages[]
	 */
	void modifyMessages(List<Message> messages);
	/**
	 * Count message by language
	 */
	Integer countByLanguage(Language language);
}
