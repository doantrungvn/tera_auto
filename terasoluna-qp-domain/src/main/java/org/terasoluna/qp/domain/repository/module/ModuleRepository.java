package org.terasoluna.qp.domain.repository.module;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.Module;
@Repository
public interface ModuleRepository {
	
	List<Module> findPageByCriteria(@Param("criteria") ModuleCriteria criteria, @Param("pageable") Pageable pageable);

	long countByCriteria(@Param("criteria") ModuleCriteria criteria);

	void register(Module module);
	
	int modify(Module module);

	int delete(Module module);

	Module findById(@Param("moduleId") Long moduleId);
	
	Module findByCode(Module module);

	Collection<Module> findAll();

	List<Module> findByAllPermission();

	List<Module> findAllModuleByTableMappingId(@Param("tableMappingId") Long tableMappingId);
	
	List<Module> findAllModuleByBusinessTypeId(@Param("businessTypeId") Long businessTypeId);

	List<Module> findAllModuleOfProject(@Param("projectId") Long projectId, @Param("status") Integer status);
	
	List<Module> findAllModuleOfOnline(@Param("projectId") Long projectId, @Param("moduleType") Integer moduleType);
	
	Long countNameCodeByModuleId(Module module);
	
	HashMap<String, Long> countReferenceByModuleId(Long moduleId);
	
	Long selectMaxId();
	
	int deleteAssociatedModule(Module module);
	
	int modifyDesignStatus(@Param("module") Module module);
	
	long validateChangeStatusToFixed(Long moduleId);
	
	int switchDesignStatusToUnderDesign(@Param("listModuleId")List<Long> listModuleId);
	
	List<Module> findListModuleDependency (@Param("moduleId")Long moduleId,@Param("projectId") Long projectId);
	
	List<Module> getAllModule(@Param("projectId") Long projectId ,@Param("listOfModule") List<Long> listOfModule);
	
	List<Module> findAllDistincModuleByProjectId(@Param("projectId") Long projectId);

}
