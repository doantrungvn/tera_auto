package org.terasoluna.qp.domain.service.accountruledefinition;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.qp.domain.model.AccountRuleDefinition;
import org.terasoluna.qp.domain.repository.accountruledefinition.AccountRuleDefinitionSearchCriteria;

public interface AccountRuleDefinitionService {
	Page<AccountRuleDefinition> findPageByCriteria(AccountRuleDefinitionSearchCriteria sampleCriteria, Pageable pageable);
	
	void register(AccountRuleDefinition accountRuleDefinition) throws BusinessException;
	
	AccountRuleDefinition getAccountRuleDefinitionByAccountRuleDefinitionId(Long accountRuleDefinitionId) throws BusinessException;
	
	void modify(AccountRuleDefinition accountRuleDefinition) throws BusinessException;
	
	void delete(AccountRuleDefinition accountRuleDefinition) throws BusinessException;
	
	AccountRuleDefinition getAccountRuleDefinitionByAccountId(Long accountId) throws BusinessException;
	
	AccountRuleDefinition getAccountRuleDefinitionWhenAuthentication(Long accountRuleDefinitionId);
	
	List<String> getAppliedUserAccount(Long accountRuleDefinitionId);
}
