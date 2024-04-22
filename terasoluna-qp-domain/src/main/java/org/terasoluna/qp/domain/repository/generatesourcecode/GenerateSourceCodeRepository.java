package org.terasoluna.qp.domain.repository.generatesourcecode;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ModuleTableMapping;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.TableDesignDetails;


public interface GenerateSourceCodeRepository {

	List<BusinessDesign> findAllBusinessLogicByModuleLst(@Param("moduleLst") List<Module> moduleLst, @Param("projectId") Long projectId, @Param("generateType") Integer generateType);

	List<InputBean> findAllInputBeanByBusinessIdLst(@Param("businessDesignLst") List<BusinessDesign> businessDesignLst);
	
	List<InputBean> findAllInputBeanByBusinessId(@Param("businessLogicId") Long businessLogicId);
	
	List<OutputBean> findAllOutputBeanByBusinessIdLst(@Param("businessDesignLst") List<BusinessDesign> businessDesignLst);
	
	List<OutputBean> findAllOutputBeanByBusinessId(@Param("businessLogicId") Long businessLogicId);
	
	List<ObjectDefinition> findAllObjDefinedByBusinessIdLst(@Param("businessDesignLst") List<BusinessDesign> businessDesignLst);
	
	List<ObjectDefinition> findAllObjDefinedByBusinessId(@Param("businessLogicId") Long businessLogicId);
	
	List<TableDesignDetails> findAllTableInformationByProjectId(@Param("projectId") Long projectId);
	
	List<ModuleTableMapping> findAllModuleTableMappingByModuleLst(@Param("moduleLst") List<Module> moduleLst);
	
	List<BusinessDesign> findAllCommonBusinessLogicByProject(@Param("projectId") Long projectId);
	
	List<BusinessDesign> findAllCommonBusinessLogicCustomizeByProject(@Param("projectId") Long projectId);
	
	BusinessDesign findBLogicByBlogicId(@Param("businessLogicId") Long businessLogicId);
	
	List<BusinessDesign> getAllScreenActionIsPostByScreenId(@Param("screenId") Long screenId);
}
