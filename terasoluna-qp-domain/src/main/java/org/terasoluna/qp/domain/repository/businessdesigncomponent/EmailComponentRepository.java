package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.EmailComponent;
import org.terasoluna.qp.domain.model.EmailContent;
import org.terasoluna.qp.domain.model.EmailRecipient;

public interface EmailComponentRepository {
	Long getSequencesEmailComponent(@Param("size") Integer size);

	int registerEmailComponent(@Param("emailComponentItems") List<EmailComponent> emailComponentItems);

	List<EmailComponent> findEmailComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<EmailRecipient> findEmailRecipientByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<EmailContent> findEmailContentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);
	
	List<EmailComponent> findAllEmailComponent(@Param("moduleId") Long moduleId);
	
	List<EmailRecipient> findAllEmailRecipient(@Param("moduleId") Long moduleId);

	List<EmailContent> findAllEmailContent(@Param("moduleId") Long moduleId);
	
	List<EmailComponent> findAllEmailComponentByProject(@Param("projectId") Long projectId);
	
	List<EmailRecipient> findAllEmailRecipientByProject(@Param("projectId") Long projectId);

	List<EmailContent> findAllEmailContentByProject(@Param("projectId") Long projectId);
	
}
