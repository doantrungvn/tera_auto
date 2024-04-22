package org.terasoluna.qp.domain.service.role;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.Role;
import org.terasoluna.qp.domain.repository.role.RoleSearchCriteria;

public interface RoleService {

	Role findOne(Long roleId);

	Page<Role> searchRoles(RoleSearchCriteria role, Pageable pageable);
	
	List<Role> findRoleOfAccount(Long accountId);
	
	Role findRoleInfoAndPermission(Long roleId);
	
	Role displayRegister();
	
	Role displayModify(Long roleId);
	
	List<Role> getRolePermission();

	void register(Role role);

	void modify(Role role);

	void delete(Long roleId);
	
	List<String> getRoleAppliedUserAccount(Long roleId);

}
