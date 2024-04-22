package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.BusinessCheckComponent;
import org.terasoluna.qp.domain.model.BusinessCheckDetail;
import org.terasoluna.qp.domain.model.BusinessDetailContent;
import org.terasoluna.qp.domain.model.TableDesignDetails;


public interface BusinessCheckComponentRepository {

	Long getSequencesBusinessCheckComponent(@Param("size") Integer size);

	Long getSequencesBusinessCheckDetail(@Param("size") Integer size);

	Long getSequencesBusinessDetailContent(@Param("size") Integer size);

	int registerBusinessCheckComponent(@Param("businessCheckComponentItems") List<BusinessCheckComponent> businessCheckComponentItems);

	List<BusinessCheckComponent> findBusinessCheckComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<BusinessCheckComponent> findBusinessCheckComponentByModuleId(@Param("moduleId") Long moduleId);

	List<BusinessCheckDetail> findBusinessCheckDetailByModuleId(@Param("moduleId") Long moduleId);

	List<BusinessDetailContent> findBusinessDetailContentByModuleId(@Param("moduleId") Long moduleId);

	int registerBusinessCheckDetails(@Param("businessCheckDetailItems") List<BusinessCheckDetail> businessCheckDetailItems);

	List<BusinessCheckDetail> findBusinessCheckDetailsByBusinessLogic(@Param("businessLogicId") Long businessLogicId,@Param("languageId") Long languageId,@Param("projectId") Long projectId);

	int registerBusinessDetailContents(@Param("businessDetailContentItems") List<BusinessDetailContent> businessDetailContentItems);

	List<BusinessDetailContent> findBusinessDetailContentsByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	int autoUpdateAffectBusinessCheckComp(@Param("lstTableDetail") List<TableDesignDetails> lstTableDetail);

	void deleteAffectBusinessCheckComp(@Param("lstTableDetail") List<TableDesignDetails> lstTableDetails);

	void deleteAffectBusinessCheckCompById(@Param("tableDesignId") Long tableDesignId);

	List<BusinessCheckComponent> findBusinessCheckComponentByModuleCommon(@Param("projectId") Long projectId);

	List<BusinessCheckDetail> findBusinessCheckDetailByModuleCommon(@Param("projectId") Long projectId);

	List<BusinessDetailContent> findBusinessDetailContentByModuleCommon(@Param("projectId") Long projectId);

}
