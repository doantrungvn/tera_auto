package org.terasoluna.qp.domain.repository.permission;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.Permission;

@Repository
public interface PermissionRepository {

	Permission findOne(Long permissionId);

	List<Permission> getAll();

	Collection<Permission> getPermissionById(Long modulId);

	List<Permission> getPermissionOfAccount(Long accountId);
	
	List<Permission> getPermissionOfAccountByRole(Long accountId);
	
	List<Permission> getRoleAndPermissionOfAccount(Long accountId);

	List<Permission> getAuthorityInformation(Long accountId);
	
	List<Permission> findAllModuleCode();
	
	List<Permission> findPermissionOfRole(Long roleId);
	
	List<Permission> getPermissionByRole(Long roleId);
}
