package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.CommonComponent;
import org.terasoluna.qp.domain.model.CommonInputValue;
import org.terasoluna.qp.domain.model.CommonOutputValue;

public interface CommonComponentRepository {

	Long getSequencesCommonComponent(@Param("size") Integer size);

	Long getSequencesCommonInputValue(@Param("size") Integer size);

	Long getSequencesCommonOutputValue(@Param("size") Integer size);

	int registerCommonComponent(@Param("commonComponentItems") List<CommonComponent> commonComponentItems);

	List<CommonComponent> findCommonComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<CommonInputValue> findCommonInputValueByBusinessLogic(@Param("businessLogicId") Long businessLogicId, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<CommonOutputValue> findCommonOutputValueByBusinessLogic(@Param("businessLogicId") Long businessLogicId, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	boolean deleteCommonInputById(@Param("inputBeanId") Long inputBeanId, @Param("businessLogicId") Long businessLogicId);

	boolean deleteCommonOutputById(@Param("outputBeanId") Long inputBeanId, @Param("businessLogicId") Long businessLogicId);

	List<CommonComponent> findAllCommonComponentByModuleId(@Param("moduleId") Long moduleId);

	List<CommonInputValue> findAllCommonInputValueByModuleId(@Param("moduleId") Long moduleId);

	List<CommonOutputValue> findAllCommonOutputValueByModuleId(@Param("moduleId") Long moduleId);

	List<CommonComponent> findAllCommonComponentByModuleCommon(@Param("projectId") Long projectId);

	List<CommonInputValue> findAllCommonInputValueByModuleCommon(@Param("projectId") Long projectId);

	List<CommonOutputValue> findAllCommonOutputValueByModuleCommon(@Param("projectId") Long projectId);

	List<CommonComponent> findCommonComponentByCommonBlogicId(@Param("businessLogicId") Long businessLogicId);

	List<CommonInputValue> findCommonInputValueByCommonBlogicId(@Param("businessLogicId") Long businessLogicId);

	List<CommonOutputValue> findCommonOutputValueByCommonBlogicId(@Param("businessLogicId") Long businessLogicId);

	int deleteCommonInputValueByCommonComponent(@Param("lstComponents") List<CommonComponent> lstComponents);

	int deleteCommonOutputValueByCommonComponent(@Param("lstComponents") List<CommonComponent> lstComponents);

	int deleteCommonInputValueByCommonBlogicId(@Param("businessLogicId") Long businessLogicId);

	int deleteCommonOutputValueByCommonBlogicId(@Param("businessLogicId") Long businessLogicId);

}
