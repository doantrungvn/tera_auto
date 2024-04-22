package org.terasoluna.qp.domain.repository.message;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.Language;
import org.terasoluna.qp.domain.model.Message;

public interface MessageRepository {

	Message findOne(Long messageId);

	List<Message> findPageByCriteria(@Param("criteria") MessageSearchCriteria criteria, @Param("pageable") Pageable pageable);

	long countByCriteria(@Param("criteria") MessageSearchCriteria criteria);

	void modify(Message message);

	Collection<Message> findAllModuleResource();

	void updateByTranslate(@Param("messages") List<Message> messages);

	/** get message for translate */
	List<Message> findToTranslate(@Param("language") Language language);

	/**
	 * get all message of module common -> clone to production
	 * @return
	 */
	List<Message> getDefaultForExportProject(@Param("projectId") Long projectId, @Param("locale") String locale);
	
	/** Count message by language */
	Integer countByLanguage(@Param("language") Language language);
}
