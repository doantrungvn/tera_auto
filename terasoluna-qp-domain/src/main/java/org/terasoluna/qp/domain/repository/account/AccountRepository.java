package org.terasoluna.qp.domain.repository.account;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.Role;

public interface AccountRepository {

	Account findOneByUsername(String username);

	void register(Account account);
	
	Account findOneByAccountId(Long accountId);
	
	boolean modify(Account account);
	
	boolean modifyHaveChangePass(Account account);
	
	boolean delete(Account account);
	
	List<Account> findPageByCriteria(@Param("criteria") AccountSearchCriteria criteria, @Param("pageable") Pageable pageable);
	
	long countByCriteria(@Param("criteria") AccountSearchCriteria criteria);
	
	List<Role> getListActiveRolesForAccountRegisterAndModify();
	
	long getCurrentValueAccountSequence();
	
	Account getAccountWhenAuthenticationFailed(String username);
}
