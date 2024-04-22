package org.terasoluna.qp.domain.repository.rolepermission;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.RolePermission;

@Repository
public interface RolePermissionRepository {
	int register(@Param("lstItems") List<RolePermission> lstItems);
	List<RolePermission> getPermissionByRole(Long roleId);
	int delete(Long roleId);
}
