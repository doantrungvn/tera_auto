package org.terasoluna.qp.app.account.ajax;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.AccountRuleDefinition;
import org.terasoluna.qp.domain.service.accountruledefinition.AccountRuleDefinitionService;

@Controller
@RequestMapping(value="account")
public class AccountAjaxController {
	@Inject
	AccountRuleDefinitionService accountRuleDefinitionService;
	
	@RequestMapping(value="getAccountRuleDefinition", method=RequestMethod.GET)
	@ResponseBody
	public AccountRuleDefinition getAccountRuleDefinition(@RequestParam("accountRuleDefinitionId") long accountRuleDefinitionId) {
		AccountRuleDefinition accountRuleDefiniton = accountRuleDefinitionService.getAccountRuleDefinitionWhenAuthentication(accountRuleDefinitionId);
		return accountRuleDefiniton;
	}
	
	@RequestMapping(value="getMaxSizeUpload", method=RequestMethod.GET)
	@ResponseBody
	public Integer getMaxSizeUpload() {
		AccountProfile accountProfile = SessionUtils.getCurrentAccountProfile();
		return accountProfile.getMaxSizeUpload() == null ? 30 : accountProfile.getMaxSizeUpload();
	}
}
