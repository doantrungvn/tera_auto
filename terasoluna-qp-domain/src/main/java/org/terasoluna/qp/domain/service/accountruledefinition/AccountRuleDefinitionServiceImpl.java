package org.terasoluna.qp.domain.service.accountruledefinition;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.AccountRuleDefinitionMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.AccountRuleDefinition;
import org.terasoluna.qp.domain.repository.accountruledefinition.AccountRuleDefinitionRepository;
import org.terasoluna.qp.domain.repository.accountruledefinition.AccountRuleDefinitionSearchCriteria;

@Transactional
@Service
public class AccountRuleDefinitionServiceImpl implements AccountRuleDefinitionService {
	@Inject
	AccountRuleDefinitionRepository accountRuleDefinitionRepository;
	
	@Override
	public Page<AccountRuleDefinition> findPageByCriteria(AccountRuleDefinitionSearchCriteria sampleCriteria, Pageable pageable) {
		long totalCount = accountRuleDefinitionRepository.countByCriteria(sampleCriteria);
		List<AccountRuleDefinition> accountRuleDefinition;
		
		if (0 < totalCount) {
			accountRuleDefinition = accountRuleDefinitionRepository.findPageByCriteria(sampleCriteria, pageable);
		} else {
			accountRuleDefinition = Collections.emptyList();
		}
		
		Page<AccountRuleDefinition> page = new PageImpl<AccountRuleDefinition>(accountRuleDefinition, pageable, totalCount);
		
		return page;
	}

	/**
	 * register account design
	 * 
	 * @param account design
	 * @throws BusinessException
	 */
	@Override
	public void register(AccountRuleDefinition accountRuleDefinition) throws BusinessException {
		//Check Name or code exist
		Long totalCount = accountRuleDefinitionRepository.countNameCodeByByAccountId(accountRuleDefinition);

		ResultMessages resultMessages = ResultMessages.error();
		
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0005));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0004));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0005));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0004));
		} 
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		}else{
			accountRuleDefinitionRepository.register(accountRuleDefinition);
		}
	}

	@Override
	public AccountRuleDefinition getAccountRuleDefinitionByAccountRuleDefinitionId(Long accountRuleDefinitionId) {
		AccountRuleDefinition accountRuleDefinition = accountRuleDefinitionRepository.getAccountRuleDefinitionByAccountRuleDefinitionId(accountRuleDefinitionId);
		
		if (accountRuleDefinition == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0039)));
		}
		
		return accountRuleDefinition;
	}

	@Override
	public void modify(AccountRuleDefinition accountRuleDefinition) throws BusinessException {
		AccountRuleDefinition accountRuleDefinitionExist = accountRuleDefinitionRepository.getAccountRuleDefinitionByAccountRuleDefinitionId(accountRuleDefinition.getAccountRuleDefinitionId());
		
		if (accountRuleDefinitionExist == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0039)));
		}
		
		// Check duplicate name and code
		if(!accountRuleDefinitionExist.getAccountRuleDefinitionName().equals(accountRuleDefinition.getAccountRuleDefinitionName()) || !accountRuleDefinitionExist.getAccountRuleDefinitionCode().equals(accountRuleDefinition.getAccountRuleDefinitionCode())){
			Long totalCount = accountRuleDefinitionRepository.countNameCodeByByAccountId(accountRuleDefinition);

			ResultMessages resultMessages = ResultMessages.error();
			
			if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
				resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0005));
			} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
				resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0004));
			} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
				resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0005));
				resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0004));
			} 
			if (resultMessages.isNotEmpty()) {
				throw new BusinessException(resultMessages);
			}
		}
		
		boolean updated = accountRuleDefinitionRepository.modify(accountRuleDefinition);
		
		if (!updated) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}
	}

	@Override
	public void delete(AccountRuleDefinition accountRuleDefinition) throws BusinessException {
		AccountRuleDefinition accountRuleDefinitionExist = accountRuleDefinitionRepository.getAccountRuleDefinitionByAccountRuleDefinitionId(accountRuleDefinition.getAccountRuleDefinitionId());
		
		if (accountRuleDefinitionExist == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0039)));
		}
		
		List<String> listAppliedUserAccount = accountRuleDefinitionRepository.getAppliedUserAccount(accountRuleDefinition.getAccountRuleDefinitionId());
		
		if (listAppliedUserAccount.size() != 0) {
			throw new BusinessException(ResultMessages.error().add(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0053));
		}
		
		boolean updated = accountRuleDefinitionRepository.delete(accountRuleDefinition);
		
		if (!updated) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}
	}

	@Override
	public AccountRuleDefinition getAccountRuleDefinitionByAccountId(Long accountId) throws BusinessException {
		return accountRuleDefinitionRepository.getAccountRuleDefinitionByAccountId(accountId);
	}

	@Override
	public AccountRuleDefinition getAccountRuleDefinitionWhenAuthentication(Long accountRuleDefinitionId) {
		if(accountRuleDefinitionId == null){
			return null;
		}else{
			return accountRuleDefinitionRepository.getAccountRuleDefinitionByAccountRuleDefinitionId(accountRuleDefinitionId);
		}
	}

	@Override
	public List<String> getAppliedUserAccount(Long accountRuleDefinitionId) {
		return accountRuleDefinitionRepository.getAppliedUserAccount(accountRuleDefinitionId);
	}
}
