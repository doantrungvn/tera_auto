package org.terasoluna.qp.domain.repository.businessdesign;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.BusinessLogic;
import org.terasoluna.qp.domain.model.CommonObjectAttribute;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.FunctionMethod;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.ItemValidation;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemOutput;
import org.terasoluna.qp.domain.model.SequenceConnector;
import org.terasoluna.qp.domain.model.SequenceLogic;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.TableDesignDetailsOutput;

public interface BusinessDesignRepository {

	long countBySearchCriteria(@Param("criteria") BusinessDesignCriteria criteria);

	List<BusinessDesign> findPageBySearchCriteria(@Param("criteria") BusinessDesignCriteria criteria, @Param("pageable") Pageable pageable, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<BusinessDesign> findBlogicByModuleId(@Param("moduleId") Long moduleId, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<BusinessDesign> findBlogicByCommonBusinessBlogicId(@Param("businessLogicId") Long businessLogicId);

	List<BusinessDesign> findBlogicByNavigatorBusinessBlogicId(@Param("businessLogicId") Long businessLogicId);

	Long countNameCodeExist(BusinessDesign businessDesign);

	BusinessDesign findBusinessLogicInformation(@Param("businessLogicId") Long businessLogicId, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	Long register(BusinessDesign businessDesign);

	Long getSequencesInputBean(@Param("size") Integer size);

	Long getSequencesOutputBean(@Param("size") Integer size);

	Long getSequencesObjectDefinition(@Param("size") Integer size);

	Long getSequencesConnector(@Param("size") Integer size);

	Long getSequencesLogic(@Param("size") Integer size);

	int registerOneInputBean(InputBean inputBean);

	int registerOneOutputBean(OutputBean outputBean);

	int registerOneObjectDefinition(ObjectDefinition objectDefinition);

	int registerInputBean(@Param("inputbeanItems") List<InputBean> inputbeanItems);

	int registerOutputBean(@Param("outputbeanItems") List<OutputBean> outputbeanItems);

	int registerObjectDefinition(@Param("objectdefinitionItems") List<ObjectDefinition> objectdefinitionItems);

	int registerSequenceLogic(@Param("sequenceLogicItems") List<SequenceLogic> sequenceLogicItems);

	int registerSequenceConnector(@Param("sequenceConnectorItems") List<SequenceConnector> sequenceConnectorItems);

	List<InputBean> findInputBean(@Param("businessLogicId") Long businessLogicId, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<OutputBean> findOutputBean(@Param("businessLogicId") Long businessLogicId);

	List<ObjectDefinition> findObjectDefinition(@Param("businessLogicId") Long businessLogicId, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<ObjectDefinition> findObjectDefinitionByModuleId(@Param("moduleId") Long moduleId);

	List<SequenceLogic> findSequenceLogic(Long businessLogicId);

	List<SequenceConnector> findSequenceConnector(Long businessLogicId);

	int modifyBusinessDesign(BusinessDesign businessDesign);

	int modifyInputBean(@Param("inputbeanItems") List<InputBean> inputbeanItems);

	int modifyOutputBean(@Param("outputbeanItems") List<OutputBean> outputbeanItems);

	int modifyObjectDefinition(@Param("objectDefinitionItems") List<ObjectDefinition> objectDefinitionItems);

	int deleteBeforModifyInputBean(@Param("inputbeanItems") List<InputBean> inputbeanItems, @Param("businessLogicId") Long businessLogicId);

	int deleteBeforModifyOutputBean(@Param("outputbeanItems") List<OutputBean> outputbeanItems, @Param("businessLogicId") Long businessLogicId);

	int deleteBeforModifyObjectDefinition(@Param("objectDefinitionItems") List<ObjectDefinition> objectDefinitionItems, @Param("businessLogicId") Long businessLogicId);

	int deleteSequenceByBlogicId(@Param("businessLogicId") Long businessLogicId);

	int deleteSequenceAndComponentByBlogicId(@Param("businessLogicId") Long businessLogicId);

	int deleteObjectDefinitionAndSequenceLogic(@Param("businessLogicId") Long businessLogicId);

	int deleteBusinessDesign(@Param("businessLogicId") Long businessLogicId);

	BusinessDesign findBusinessLoginByScreenId(@Param("screenId") Long screenId);

	List<ScreenArea> findScreenAreaByScreenId(@Param("screenId") Long screenId, @Param("languageId") Long languageId);

	List<ItemValidation> findScreenValidationByScreenId(@Param("businessLogicId") Long businessLogicId, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<TableDesignDetailsOutput> getColumnsByTableIdForBD(@Param("tableId") Long tableId);

	int modifyDesignStatus(BusinessDesign businessDesign);

	List<ScreenItemOutput> getScreenItemOutputByScreenIdForBD(@Param("screenId") Long screenId, @Param("type") Integer type, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<BusinessDesign> findBusinessLogicsByScreenId(@Param("screenId") Long screenId);

	List<InputBean> findColumnInformationOfInputBeans(@Param("lstInputBeanId") List<InputBean> lstInputBeanId);

	List<ObjectDefinition> findColumnInformationOfObjectDefition(@Param("lstObjectDefitionId") List<ObjectDefinition> lstObjectDefitionId);

	List<OutputBean> findColumnInformationOfOutputbean(@Param("lstOuputbeanId") List<OutputBean> lstOuputbeanId);

	List<BusinessDesign> findAllBusinessLogicsBySqlDesignId(Long sqlDesignId);

	int switchDesignStatusToUnderDesign(@Param("businessLogics") List<BusinessLogic> businessLogics);

	int changeNameAndCodeOutputBean(@Param("tableDesigns") List<TableDesign> tableDesigns);

	int changeNameAndCodeObjectDefinition(@Param("tableDesigns") List<TableDesign> tableDesigns);

	Long getSequencesBusinesLogic(@Param("size") Integer size);

	int registerLstBusinessLogic(@Param("lstBusinessDesign") List<BusinessDesign> lstBusinessDesign);

	int updateAffectObjectDefinitionBean(@Param("lstTableDetail") List<TableDesignDetails> lstTableDetail);

	int updateAffectOutputBean(@Param("lstTableDetail") List<TableDesignDetails> lstTableDetail);

	void deleteAffectObjectDefinitionBeanByDeleleTableDesign(@Param("tableDesignId") Long tableDesignId);

	void deleteAffectOutputBeanDeleleTableDesign(@Param("tableDesignId") Long tableDesignId);

	void autoDeleteAffectObjectDefinitionBean(@Param("lstTableDetail") List<TableDesignDetails> lstTableDetails);

	void autoDeleteAffectOutputBean(@Param("lstTableDetail") List<TableDesignDetails> lstTableDetails);

	List<ObjectDefinition> findAllInforOfParenObjDefinitionBeanById(@Param("tableDesignId") Long tableDesignId);

	List<OutputBean> findAllInforOfParenOutBeanById(@Param("tableDesignId") Long tableDesignId);

	int registerListObjectDefinition(@Param("objectdefinitionItems") List<ObjectDefinition> objectdefinitionItems);

	int registerListOutputBean(@Param("outputbeanItems") List<OutputBean> outputbeanItems);

	List<InputBean> findInputBeanByBlogicIds(@Param("languageId") Long languageId, @Param("projectId") Long projectId, @Param("businessDesigns") List<BusinessDesign> businessDesigns);

	List<OutputBean> findOuputBeanByBlogicIds(@Param("languageId") Long languageId, @Param("projectId") Long projectId, @Param("businessDesigns") List<BusinessDesign> businessDesigns);

	void updateFormForBlogic(@Param("blogicId") Long blogicId, @Param("screenFormId") Long screenFormId);

	int updateDesignStatusOfAffectedBlogic(@Param("lstAffectedBlogic") List<Long> lstAffectedBlogic, @Param("updatedBy") Long updatedBy, @Param("updatedDate") Timestamp updatedDate);

	List<BusinessDesign> getListBLogicByScreenFormId(@Param("listOfScreenForm") List<ScreenForm> listOfScreenForm);

	List<ScreenItemOutput> findAllScreenItemMappingByOutputBeanId(@Param("businessLogicId") Long businessLogicId);

	List<ScreenItemOutput> findAllScreenItemMappingByOutputBeanBlogic(@Param("businessLogicId") Long businessLogicId);
	
	int deleteInputBean(@Param("inputbeanItems") List<InputBean> inputbeanItems);

	BusinessDesign findBLogicByScreenId(@Param("screenId") Long screenId);

	BusinessDesign findBLogicByScreenIdForLink(@Param("screenId") Long screenId);

	BusinessDesign findBLogicByBlogicIdForLink(@Param("businessLogicId") Long businessLogicId);

	List<BusinessDesign> findBLogicByProject(@Param("projectId") Long projectId);

	BusinessDesign findBLogicByBlogicIdForLinkToSubmit(@Param("businessLogicId") Long businessLogicId);

	int modifyInputBeanWhenModifyScreenItem(@Param("inputbeanItems") List<InputBean> inputbeanItems);

	List<CommonObjectAttribute> getAttributesByCommonObjectDefinitionIdForBD(Long commonObjectDefinitionId);

	List<ExternalObjectAttribute> getAttributesByExternalObjectDefinitionIdForBD(Long externalObjectDefinitionId);

	BusinessDesign findBDesignGetByScreenId(ScreenDesign screenDesign);

	BusinessDesign findBDesignPostByScreenId(ScreenDesign screenDesign);

	List<BusinessDesign> findBlogicByScreenFormId(@Param("screenForms") ScreenForm[] screenForms);

	InputBean findInputBeanById(@Param("inputBeanId") Long inputBeanId, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	List<BusinessDesign> findBlogicBySessionManagementId(@Param("sessionManagementId") Long sessionManagementId);

	List<BusinessDesign> findAllBusinessLogicsByModuleId(Long moduleId);

	List<InputBean> findInputBeanByParentInputBean(@Param("parentInputBeanId") Long parentInputBeanId);

	List<ScreenItemOutput> findAllScreenItemMappingInputBeanByBusinessLogicId(@Param("businessLogicId") Long businessLogicId, @Param("languageId") Long languageId, @Param("projectId") Long projectId);

	int removeScreenItemMapping(@Param("inputBeanDelete") List<InputBean> inputBeanDelete);

	int removeScreenItemMappingByScreenItemId(@Param("screenItemDelete") List<ScreenItem> screenItemDelete);

	BusinessDesign findDisplayBDesignByScreenId(ScreenDesign screenDesign);
	
	List<BusinessDesign> findAllBlogicProcessByScreenId(@Param("screenId") Long screenId);

	List<BusinessDesign> findAllBlogicInitByScreenId(@Param("screenId") Long screenId);

	List<BusinessDesign> findAllBLogicProcessToNavigatorByScreenId(@Param("screenId") Long screenId);

	List<InputBean> findInputBeanByListScreenItem(@Param("projectId") Long projectId, @Param("lstScreenItem") List<ScreenItem> lstScreenItem);

	List<BusinessDesign> findBlogicByUsingExternalOb(@Param("externalObjectDefinitionId") Long externalObjectDefinitionId);

	List<BusinessDesign> findBlogicByUsingCommonOb (@Param("commonObjectDefinitionId") Long commonObjectDefinitionId);
	
	String getNameBlogicById(@Param("blogicId") Long blogicId);
	
	List<BusinessDesign> findBlogicByUsingFunctionMethod (@Param("lstFunctionMethodIds") List<Long> lstFunctionMethodIds);
	
	List<BusinessDesign> findBlogicByUsingMethodOutput (@Param("lstMethodOutputIds") List<Long> lstMethodOutputIds);
	
	List<BusinessDesign>  findAllBussinessLogicByCommonBlogicId(@Param("commonBlogicId") Long commonBlogicId);
	
	List<BusinessDesign>  findAllBussinessLogicByNavigatorBlogicId(@Param("navigatorBlogicId") Long navigatorBlogicId);
	
	List<SequenceLogic>	findSequenceLogicByUsingLstFunctionMethod(@Param("lstFunctionMethods")List<FunctionMethod> lstFunctionMethods);
}
