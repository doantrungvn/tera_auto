package org.terasoluna.qp.domain.repository.generatesourcecode;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.SequenceConnector;
import org.terasoluna.qp.domain.model.SequenceLogic;

public interface GenerateServiceImpDetailRepository {
	List<SequenceLogic> findSequenceLogicByModuleId(Long moduleId);
	
	List<InputBean> findInputBeanByModuleId(@Param("moduleId") Long moduleId,@Param("languageId") Long languageId, @Param("projectId") Long projectId);
	
	List<OutputBean> findOutputBeanByModuleId(@Param("moduleId") Long moduleId);
	
	List<ObjectDefinition> findObjectDefinitionByModuleId(@Param("moduleId") Long moduleId);
	
	List<SequenceConnector> findSequenceConnectorOfModule(Long moduleId);

	List<SequenceLogic> findAllSequenceLogicByLstBlogic(@Param("blogics") List<BusinessDesign> lstBusinessDesign);
	
	List<SequenceConnector> findAllSequenceConnectorByLstBlogic(@Param("blogics") List<BusinessDesign> lstBusinessDesign);
	
}
