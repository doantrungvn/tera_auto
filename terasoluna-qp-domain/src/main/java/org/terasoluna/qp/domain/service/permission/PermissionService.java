package org.terasoluna.qp.domain.service.permission;

import java.util.Collection;
import java.util.List;

import org.terasoluna.qp.domain.model.Permission;

public interface PermissionService {

	List<Permission> getAll();

	Collection<Permission> getPermissionById(Long moduleId);
	
	List<Permission> getPermissionOfAccount(Long accountId);
	
	List<Permission> getAuthorityInformation(Long accountId);
	
	List<Permission> findAllModuleCode();
	
	List<Permission> getRoleAndPermissionOfAccount(Long accountId);
}
