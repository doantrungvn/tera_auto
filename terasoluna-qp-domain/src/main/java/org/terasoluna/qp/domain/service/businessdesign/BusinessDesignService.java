package org.terasoluna.qp.domain.service.businessdesign;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.NestedLogicComponent;
import org.terasoluna.qp.domain.model.PatternedComponent;
import org.terasoluna.qp.domain.model.ScreenItemOutput;
import org.terasoluna.qp.domain.model.TableDesignDetailsOutput;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignCriteria;

@Service
public interface BusinessDesignService {

	Page<BusinessDesign> searchBusinessDesign(BusinessDesignCriteria criteria, Pageable pageable, CommonModel commonModel);

	List<BusinessDesign> findBlogicByModuleId(Long moduleId, CommonModel commonModel);

	BusinessDesign findAllDeletionAffectionOfCommonBlogic(BusinessDesign businessDesign);

	BusinessDesign findAllDeletionAffectionOfNavigatorBlogic(BusinessDesign businessDesign, CommonModel commonModel);

	BusinessDesign findBusinessLogicInformation(Long businessLogicId, boolean isOnlyView, CommonModel commonModel, boolean isGetContent);

	BusinessDesign findBusinessLoginByScreenId(@Param("screenId") Long screenId);

	List<TableDesignDetailsOutput> getColumnsByTableId(Long tableId);

	BusinessDesign findInputBeanOfBusinessLogic(Long businessDesignId, CommonModel commonModel);

	List<ScreenItemOutput> getScreenItemByScreenId(Long screenId,Integer type, CommonModel commonModel);

	List<Object> findDataDecisionComp(Long decisionTbId);

	Map<String, String> getStandardCheckFWOfBusinessLogic(List<String> listOfMesssageCode, CommonModel commonModel);

	List<BusinessDesign> findBusinessLoginsByScreenId(Long screenId);
	
	BusinessDesign getInformationOfCommonBusinessLogic(Long businessLogicId, CommonModel commonModel);

	void registerBusinessLogicDesign(BusinessDesign businessDesign, Long projectId,Long languageId, CommonModel commonModel);

	void modifyBusinessDesignLogic(BusinessDesign businessDesign, CommonModel commonModel);

	void deleteBusinessDesignLogic(Long businessDesignId, Boolean deleteObjectHasFk, Timestamp oldUpdatedDate, CommonModel commonModel);

	void modifyDesignStatus(Long businessLogicId, Timestamp oldUpdatedDate, CommonModel commonModel);

	NestedLogicComponent parseInformationOfNestedlogic(String strData, boolean isOnlyView);

	void checkDesignStatus(BusinessDesign businessDesign);

	List<PatternedComponent> findPatternedComponentOfProject(CommonModel commonModel);
	
	List<BusinessDesign> findAllBlogicProcessByScreenId(Long screenId);
	
	List<BusinessDesign> findAllBlogicInitByScreenId(Long screenId);
	
	List<BusinessDesign> findAllBLogicProcessToNavigatorByScreenId(Long screenId);
	
	void processInpactChangeDesign(Long businessDesignId, Boolean deleteObjectHasFk, List<BusinessDesign> bLogicProcessToNavigatorList, CommonModel commonModel);
	
//	List<BusinessDesign> detectImpactWhenModifyCommonBlogic(BusinessDesign businessDesign,CommonModel commonModel,Boolean flagInsertProblem);
//	
//	List<BusinessDesign> detectImpactWhenDeleteCommonBlogic(BusinessDesign businessDesign,CommonModel commonModel,Boolean flagInsertProblem);
	
//	ImpactOfBusinessDesign detectImpactWhenModifyOnlineBlogic(BusinessDesign businessDesign,CommonModel commonModel,Boolean flagInsertProblem);
//	
//	ImpactOfBusinessDesign detectImpactWhenDeleteOnlineBlogic(BusinessDesign businessDesign,CommonModel commonModel,Boolean flagInsertProblem);
}
