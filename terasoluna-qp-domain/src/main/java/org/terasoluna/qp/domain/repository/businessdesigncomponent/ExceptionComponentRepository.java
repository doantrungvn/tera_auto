package org.terasoluna.qp.domain.repository.businessdesigncomponent;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.ExceptionComponent;
import org.terasoluna.qp.domain.model.ExceptionDetail;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.SequenceLogic;


public interface ExceptionComponentRepository {

	Long getSequencesExceptionComponent(@Param("size") Integer size);

	Long getSequencesExceptionDetail(@Param("size") Integer size);

	int registerExceptionComponent(@Param("exceptionComponentItems") List<ExceptionComponent> exceptionComponentItems);

	List<ExceptionComponent> findExceptionComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId,@Param("languageId") Long languageId,@Param("projectId") Long projectId);
	
	List<ExceptionComponent> findExceptionComponentByLstBusinessLogic(@Param("lstBusinessDesign") List<BusinessDesign> lstBusinessDesign,@Param("languageId") Long languageId,@Param("projectId") Long projectId);
	
	List<ExceptionDetail> findExceptionDetailByBusinessLogic(@Param("businessLogicId") Long businessLogicId,@Param("languageId") Long languageId,@Param("projectId") Long projectId);

	List<ExceptionDetail> findAllExceptionbDetailByUsedBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	boolean deleteExceptionInputById(@Param("inputBeanId") Long inputBeanId, @Param("businessLogicId") Long businessLogicId);
	
	List<ExceptionComponent> findAllExceptionComponentByModuleId(@Param("languageId") Long languageId, @Param("moduleId") Long moduleId);

	List<ExceptionDetail> findAllExceptionDetailByModuleId(@Param("languageId") Long languageId, @Param("moduleId") Long moduleId);
	
	BusinessDesign getBusinessDesignCommonComponentGeneration(@Param("exceptionComponentId") Long exceptionComponentId);

	SequenceLogic getSequenceLogicCommonComponentGeneration(@Param("exceptionComponentId") Long exceptionComponentId);

	Module getModuleCommonComponentGeneration(@Param("exceptionComponentId") Long exceptionComponentId);

	List<ExceptionComponent> findAllExceptionComponentByProjectId(@Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<ExceptionDetail> findAllExceptionDetailByProjectId(@Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<ExceptionComponent> findAllExceptionComponentByModuleCommon(@Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<ExceptionDetail> findAllExceptionDetailByModuleCommon(@Param("languageId") Long languageId, @Param("projectId") Long projectId);
	
	void modifyExceptionNodeWhenModifyBLogic(@Param("businessDesign") BusinessDesign businessDesign);
	
	void deleteExceptionInputValueWhenModifyBLogic(@Param("lstInputBeans") List<InputBean> lstInputBeans);
	
	void modifyExceptionInputValueWhenModifyBLogic(@Param("lstInputBeans") List<InputBean> lstInputBeans);
	
	List<ExceptionComponent> findExceptionComponentByNavigatorBlogicId(@Param("businessLogicId") Long businessLogicId);

	List<ExceptionDetail> findExceptionDetailByNavigatorBlogicId(@Param("businessLogicId") Long businessLogicId);

	int deleteExceptionDetailByNavigatorComponent(@Param("lstComponents") List<ExceptionComponent> lstComponents);

	int deleteExceptionDetailByNavigatorBlogicId(@Param("businessLogicId") Long businessLogicId);
}
