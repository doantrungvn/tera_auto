package org.terasoluna.qp.domain.repository.accountpermission;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.AccountPermission;

@Repository
public interface AccountPermissionRepository {
	boolean deleteByAccountId(Long accountId);
	boolean deleteByAccountPermisson(AccountPermission accountPermission);
	int registerAccountPermisson(@Param("permissionItems") List<AccountPermission> permissionItems);
}
