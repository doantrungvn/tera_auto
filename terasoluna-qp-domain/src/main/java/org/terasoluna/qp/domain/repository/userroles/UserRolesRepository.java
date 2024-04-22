package org.terasoluna.qp.domain.repository.userroles;

import java.util.Collection;
import java.util.List;

import org.terasoluna.qp.domain.model.UserRoles;

public interface UserRolesRepository {

	UserRoles findOne(int roleId);

	Collection<UserRoles> getAll();

	//List<Role> searchRole(RoleSearchCriteria roleName);

	void createUserRoles(UserRoles userroles);

	void updateUserRoles(UserRoles userroles);

	void deleteUserRoles(int roleId);
	
	List<UserRoles> getRolesOfAccount(Long accountId);
}
