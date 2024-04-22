package org.terasoluna.qp.domain.repository.role;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.AccountRole;
import org.terasoluna.qp.domain.model.Role;

@Repository
public interface RoleRepository {

	Role findOne(Long roleId);

	List<Role> findBySearchCriteria(@Param("roleCriteria") RoleSearchCriteria role, @Param("pageable") Pageable pageable);

	Long countBySearchCriteria(RoleSearchCriteria role);

	List<Role> getRoleByAccount(Long accountId);

	List<Role> findRoleOfAccount(Long accountId);

	Long countNameCodeByRoleId(Role role);

	HashMap<String, Long> countReferenceByRoleId(Long roleId);

	List<Role> getRolePermission();

	int register(Role role);

	int modify(Role role);

	int delete(Long roleId);
	
	List<String> getRoleAppliedUserAccount(@Param("roleId") Long roleId);
	
	List<Role> getRoleByListId(@Param("lstAccountRole") List<AccountRole> lstAccountRole);

}
