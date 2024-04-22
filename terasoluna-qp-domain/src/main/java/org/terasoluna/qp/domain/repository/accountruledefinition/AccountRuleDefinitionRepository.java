package org.terasoluna.qp.domain.repository.accountruledefinition;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.AccountRuleDefinition;

public interface AccountRuleDefinitionRepository {
	List<AccountRuleDefinition> findPageByCriteria(@Param("criteria") AccountRuleDefinitionSearchCriteria criteria, @Param("pageable") Pageable pageable);
	
	long countByCriteria(@Param("criteria") AccountRuleDefinitionSearchCriteria criteria);
	
	void register(AccountRuleDefinition accountRuleDefinition);
	
	AccountRuleDefinition getAccountRuleDefinitionByAccountRuleDefinitionId(@Param("accountRuleDefinitionId") Long accountRuleDefinitionId);
	
	boolean modify(AccountRuleDefinition accountRuleDefinition);
	
	boolean delete(AccountRuleDefinition accountRuleDefinition);
	
	AccountRuleDefinition getAccountRuleDefinitionByAccountId(@Param("accountId") Long accountId);
	
	AccountRuleDefinition getAccountRuleDefinitionWhenAuthentication(@Param("accountRuleDefinitionId") Long accountRuleDefinitionId);
	
	AccountRuleDefinition getAccountRuleDefinitionByAccountRuleDefinitionName(@Param("accountRuleDefinitionName") String accountRuleDefinitionName);
	
	List<String> getAppliedUserAccount(@Param("accountRuleDefinitionId") Long accountRuleDefinitionId);
	
	Long countNameCodeByByAccountId(AccountRuleDefinition accountRuleDefinition);
}
