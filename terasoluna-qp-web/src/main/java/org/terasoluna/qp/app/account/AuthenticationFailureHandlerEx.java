package org.terasoluna.qp.app.account;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.AccountRuleDefinitionMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountLoginAttempt;
import org.terasoluna.qp.domain.model.AccountRuleDefinition;
import org.terasoluna.qp.domain.service.account.AccountService;
import org.terasoluna.qp.domain.service.accountloginattempt.AccountLoginAttemptService;
import org.terasoluna.qp.domain.service.accountruledefinition.AccountRuleDefinitionService;

public class AuthenticationFailureHandlerEx extends SimpleUrlAuthenticationFailureHandler {
	@Inject
	AccountService accountService;
	@Inject
	AccountRuleDefinitionService accountRuleDefinitionService;
	@Inject
	AccountLoginAttemptService accountLoginAttemptService;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		loginFailed(request, response, exception);
		
		super.onAuthenticationFailure(request, response, exception);
	}
	
	public void loginFailed(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
		if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
			String username = request.getParameter("j_username");
			
			Account account = accountService.getAccountWhenAuthenticationFailed(username);
			
			// Error when input user name in database
			if(account != null) {
				AccountRuleDefinition accountRuleDefinition = accountRuleDefinitionService.getAccountRuleDefinitionWhenAuthentication(account.getAccountRuleDefinitionId());
				
				if(accountRuleDefinition != null) {
					Calendar calendar = Calendar.getInstance();
					Timestamp passwordLastUpdated = account.getUpdatedDate();
					int passwordExpiredDay = accountRuleDefinition.getLifeTime();
					calendar.setTime(passwordLastUpdated);
					calendar.add(Calendar.DATE, passwordExpiredDay);
					
					//// Check account lock after continuous login error
					AccountLoginAttempt accountLoginAttempt = accountLoginAttemptService.getAccountLoginAttemptByAccountId(account.getAccountId());
					if(accountLoginAttempt != null && accountLoginAttempt.getLoginContinuousFailureCount() > 0 && accountRuleDefinition.getLoginContinuousFailureCount() > 0) {
						if(accountLoginAttempt.getLoginContinuousFailureCount() >= accountRuleDefinition.getLoginContinuousFailureCount()) {
							Date today = new Date();
							Timestamp currentDatetime = new Timestamp(today.getTime());
							Timestamp lastLogin = accountLoginAttempt.getUpdatedDate();
							long passedTimeInSecond = (currentDatetime.getTime() - lastLogin.getTime()) / 1000;

							long remainingSecond = accountRuleDefinition.getAccountLockTime() * 60 - passedTimeInSecond;
							
							long remainMinute = remainingSecond / 60;
							long remainSecond = remainingSecond % 60;
							
							if((remainMinute > 0 || remainSecond > 0) && accountLoginAttempt.getLoginContinuousFailureCount() > 0 &&
									accountLoginAttempt.getLoginContinuousFailureCount() >= accountRuleDefinition.getLoginContinuousFailureCount()) {
								setDefaultFailureUrl("/login?error=true&message=" + MessageUtils.getMessage(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0047, remainMinute, remainSecond));
							} else if (remainMinute <= 0 && remainSecond <= 0 && accountLoginAttempt.getLoginContinuousFailureCount() > 0 &&
									accountLoginAttempt.getLoginContinuousFailureCount() >= accountRuleDefinition.getLoginContinuousFailureCount()) {
								accountLoginAttempt.setGenerationsCount(accountLoginAttempt.getGenerationsCount());
								accountLoginAttempt.setLoginContinuousFailureCount(accountLoginAttempt.getLoginContinuousFailureCount());
								accountLoginAttempt.setAccountId(account.getAccountId());
								
								accountLoginAttemptService.modify(accountLoginAttempt);
								
								setDefaultFailureUrl("/login?error=true&message=" + MessageUtils.getMessage(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0047, accountRuleDefinition.getAccountLockTime(), "0"));
							}
						} else {
							accountLoginAttempt.setGenerationsCount(accountLoginAttempt.getGenerationsCount());
							accountLoginAttempt.setLoginContinuousFailureCount(accountLoginAttempt.getLoginContinuousFailureCount() + 1);
							accountLoginAttempt.setAccountId(account.getAccountId());
							
							accountLoginAttemptService.modify(accountLoginAttempt);
							
							setDefaultFailureUrl("/login?error=true&message=" + MessageUtils.getMessage(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0050));
						}
					} else {
						// If there is not account, insert new data record
						// Else update current data record
						if (accountLoginAttempt == null) {
							AccountLoginAttempt newAccountLoginAttempt = new AccountLoginAttempt();
							
							newAccountLoginAttempt.setAccountId(account.getAccountId());
							newAccountLoginAttempt.setGenerationsCount(0);
							newAccountLoginAttempt.setLoginContinuousFailureCount(1);
							
							accountLoginAttemptService.register(newAccountLoginAttempt);
							
							setDefaultFailureUrl("/login?error=true&message=" + MessageUtils.getMessage(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0050));
						} else {
							accountLoginAttempt.setGenerationsCount(accountLoginAttempt.getGenerationsCount());
							accountLoginAttempt.setLoginContinuousFailureCount(accountLoginAttempt.getLoginContinuousFailureCount() + 1);
							accountLoginAttempt.setAccountId(account.getAccountId());
							
							accountLoginAttemptService.modify(accountLoginAttempt);
							
							setDefaultFailureUrl("/login?error=true&message=" + MessageUtils.getMessage(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0050));
						}
					}
				} else {
					setDefaultFailureUrl("/login?error=true&message=" + MessageUtils.getMessage(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0050));
				}
			}
			
			// Error when input user name not in database
			else {
				setDefaultFailureUrl("/login?error=true&message=" + MessageUtils.getMessage(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0050));
			}
		}
		
		if (exception.getClass().isAssignableFrom(LockedException.class)) {
			setDefaultFailureUrl("/login?error=true&message=" + MessageUtils.getMessage(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0052));
		}
		
		if (exception.getClass().isAssignableFrom(SessionAuthenticationException.class)) {
			setDefaultFailureUrl("/login?error=true&message=" + MessageUtils.getMessage(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0055));
		}
	}
}
