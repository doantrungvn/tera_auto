package org.terasoluna.qp.domain.service.businessdesign;

import java.sql.Timestamp;
import java.util.List;

import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.TableDesignDetails;

/**
 * @author hunghx
 *
 */
public interface BusinessDesignShareService {

	void fix(ProblemList problemList);

	void autoUpdateAffectObjectDefinitionBean(List<TableDesignDetails> lstTableDetails);

	void autoUpdateAffectOutputBean(List<TableDesignDetails> lstTableDetails);

	void autoDeleteAffectObjectDefinitionBeanByDeleleTableDesign(Long tableDesignId);

	void autoDeleteAffectOutputBeanDeleleTableDesign(Long tableDesignId);

	void autoDeleteAffectObjectDefinitionBean(List<TableDesignDetails> lstTableDetails);

	void autoDeleteAffectOutputBean(List<TableDesignDetails> lstTableDetails);

	List<ObjectDefinition> findAllInforOfParenObjDefinitionBeanById(Long tableDesignId);

	List<OutputBean> findAllInforOfParenOutBeanById(Long tableDesignId);

	void registerListObjectDefinition(List<ObjectDefinition> objectdefinitionItems);

	void registerListOutputBean(List<OutputBean> outputbeanItems);

	void autoUpdateAffectBusinessCheckComp(List<TableDesignDetails> tableDesignDetails);

	void deleteAffectBusinessCheckComp(List<TableDesignDetails> deleteTableDetails);

	void deleteAffectBusinessCheckCompById(Long tableId);

	void updateDesignStatusOfAffectedBlogic(List<Long> lstAffectedBlogic,Long updatedBy,Timestamp updatedDate);

	public BusinessDesign findInputBeanOfBusinessLogic(Long businessDesignId ,Long workingLanguageId,Long workingProjectId);

	public BusinessDesign findInputBeanOfBusinessLogicForGensource(Long businessDesignId ,Long workingLanguageId,Long workingProjectId);
	
	public ImpactChangeOfStandardBlogic detectListAffectedWhenModify(BusinessDesign businessDesign,CommonModel common,Boolean isRunBatch);
	
	public void detectListAffectedWhenModifyOfBatch(Long businessLogicId,CommonModel common);
	
	public ImpactChangeOfStandardBlogic detectListAffectedWhenDelete(BusinessDesign businessDesign,CommonModel common,Boolean isRunBatch);
	
	public void detectListAffectedWhenDeleteOfBatch(Long businessLogicId,String businessLogicCode,CommonModel common);
}
