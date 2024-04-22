package org.terasoluna.qp.domain.repository.businessdesigncomponent;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.NavigatorComponent;
import org.terasoluna.qp.domain.model.NavigatorDetail;
import org.terasoluna.qp.domain.model.SequenceLogic;


public interface NavigationComponentRepository {

	Long getSequencesNavigationComponent(@Param("size") Integer size);

	Long getSequencesNavigationDetail(@Param("size") Integer size);

	int registerNavigationComponent(@Param("navigatorComponentItems") List<NavigatorComponent> navigatorComponentItems);

	List<NavigatorComponent> findNavigationComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId,@Param("languageId") Long languageId,@Param("projectId") Long projectId);
	
	List<NavigatorComponent> findNavigationComponentByLstBusinessLogic(@Param("lstBusinessDesign") List<BusinessDesign> lstBusinessDesign,@Param("languageId") Long languageId,@Param("projectId") Long projectId);
	
	List<NavigatorDetail> findNavigationDetailByBusinessLogic(@Param("businessLogicId") Long businessLogicId,@Param("languageId") Long languageId,@Param("projectId") Long projectId);

	List<NavigatorDetail> findAllNavigationbDetailByUsedBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	boolean deleteNavigatorInputById(@Param("inputBeanId") Long inputBeanId, @Param("businessLogicId") Long businessLogicId);

	List<NavigatorComponent> findAllNavigationComponentByModuleId(@Param("languageId") Long languageId, @Param("moduleId") Long moduleId);

	List<NavigatorDetail> findAllNavigationDetailByModuleId(@Param("languageId") Long languageId, @Param("moduleId") Long moduleId);

	BusinessDesign getBusinessDesignCommonComponentGeneration(@Param("navigatorComponentId") Long navigatorComponentId);

	SequenceLogic getSequenceLogicCommonComponentGeneration(@Param("navigatorComponentId") Long navigatorComponentId);

	Module getModuleCommonComponentGeneration(@Param("navigatorComponentId") Long navigatorComponentId);

	List<NavigatorComponent> findAllNavigationComponentByProjectId(@Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<NavigatorDetail> findAllNavigationDetailByProjectId(@Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<NavigatorComponent> findAllNavigationComponentByModuleCommon(@Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<NavigatorDetail> findAllNavigationDetailByModuleCommon(@Param("languageId") Long languageId, @Param("projectId") Long projectId);
	
	void modifyNavigatorNodeWhenModifyBLogic(@Param("businessDesign") BusinessDesign businessDesign);
	
	void deleteNavigatorInputValueWhenModifyBLogic(@Param("lstInputBeans") List<InputBean> lstInputBeans);
	
	void modifyNavigatorInputValueWhenModifyBLogic(@Param("lstInputBeans") List<InputBean> lstInputBeans);
	
	List<NavigatorComponent> findNavigatorComponentByNavigatorBlogicId(@Param("businessLogicId") Long businessLogicId);

	List<NavigatorDetail> findNavigatorDetailByNavigatorBlogicId(@Param("businessLogicId") Long businessLogicId);

	int deleteNavigatorDetailByNavigatorComponent(@Param("lstComponents") List<NavigatorComponent> lstComponents);

	int deleteNavigatorDetailByNavigatorBlogicId(@Param("businessLogicId") Long businessLogicId);
}
