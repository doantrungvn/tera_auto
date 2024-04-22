package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.FeedbackComponent;
import org.terasoluna.qp.domain.model.SequenceLogic;


public interface FeedbackComponentRepository {

	Long getSequencesFeedbackComponent(@Param("size") Integer size);

	int registerFeedbackComponent(@Param("feedbackComponentItems") List<FeedbackComponent> feedbackComponentItems);

	List<FeedbackComponent> findFeedbackComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId,@Param("languageId") Long languageId,@Param("projectId") Long projectId);

	List<FeedbackComponent> findFeedbackComponentByModuleId(@Param("languageId") Long languageId, @Param("moduleId") Long moduleId);

	// KhanhTH
	BusinessDesign getBusinessDesignCommonComponentGeneration(@Param("feedbackComponentId") Long feedbackComponentId);

	// KhanhTH
	SequenceLogic getSequenceLogicCommonComponentGeneration(@Param("feedbackComponentId") Long feedbackComponentId);

	List<FeedbackComponent> findFeedbackComponentByProjectId(@Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<FeedbackComponent> findFeedbackComponentByModuleCommon(@Param("languageId") Long languageId, @Param("projectId") Long projectId);
}
