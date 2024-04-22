package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.MessageParameter;


public interface MessageParameterRepository {
	Long getSequencesMessageParameter(@Param("size") Integer size);

	int registerMessageParameter(@Param("messageParameterItems") List<MessageParameter> messageParameterItems);

	List<MessageParameter> findMessageParameterByBusinessLogic(@Param("businessLogicId") Long businessLogicId,@Param("languageId") Long languageId,@Param("projectId") Long projectId);

	List<MessageParameter> findMessageParameterByModuleId(@Param("languageId") Long languageId, @Param("moduleId") Long moduleId);

	List<MessageParameter> findMessageParameterByProjectId(@Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<MessageParameter> findMessageParameterByModuleCommon(@Param("languageId") Long languageId, @Param("projectId") Long projectId);
}
