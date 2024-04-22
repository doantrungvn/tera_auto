package org.terasoluna.qp.domain.repository.accountrole;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.AccountRole;

@Repository
public interface AccountRoleRepository {
	boolean deleteByAccountId(Long accountId);
	int createAccountRole(@Param("accountId") Long accountId, @Param("roleItems") List<AccountRole> roleItems);
}
