package org.terasoluna.qp.domain.repository.moduletablemapping;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ModuleTableMapping;
@Repository
public interface ModuleTableMappingRepository {
	
	ModuleTableMapping findOne(@Param("moduleTableMappingId") Long moduleTableMappingId);
	
	void register(ModuleTableMapping moduleTableMapping);
	
	void registerArray(@Param("moduleTableMapping") ModuleTableMapping[] moduleTableMapping);
	
	int modify(ModuleTableMapping moduleTableMapping);
	
	ModuleTableMapping[] findModuleTableMappingByModuleId(@Param("moduleId") Long moduleId);
	
	int deleteModuleTableMappingByModuleId(@Param("moduleId") Long moduleId);
	
	int delete(ModuleTableMapping moduleTableMapping);
	
	int deleteByModuleId(Long moduleId);
	
	int deleteAllByModuleTableMappingArrayAndModuleId(@Param("moduleId") Long moduleId, @Param ("moduleTableMappings") ModuleTableMapping[] moduleTableMapping);
	
	List<Module> findByDomainTblMappingId(@Param("domainTblMappingId") Long domainTblMappingId);
}
