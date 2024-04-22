package org.terasoluna.qp.app.account;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.AccountRuleDefinitionMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountLoginAttempt;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.AccountRuleDefinition;
import org.terasoluna.qp.domain.model.Permission;
import org.terasoluna.qp.domain.service.account.AccountDetails;
import org.terasoluna.qp.domain.service.account.AccountService;
import org.terasoluna.qp.domain.service.accountloginattempt.AccountLoginAttemptService;
import org.terasoluna.qp.domain.service.accountprofile.AccountProfileService;
import org.terasoluna.qp.domain.service.accountruledefinition.AccountRuleDefinitionService;
import org.terasoluna.qp.domain.service.accounttheme.AccountThemeService;
import org.terasoluna.qp.domain.service.permission.PermissionService;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Use SavedRequestAwareAuthenticationSuccessHandler for keeping link
 * default use : SimpleUrlAuthenticationSuccessHandler
 */
public class AuthenticationSuccessHandlerEx extends SavedRequestAwareAuthenticationSuccessHandler {
	@Inject
	PermissionService permissionService;
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerEx.class);
	@Inject
	AccountProfileService accountProfileService;
	
	@Inject
	AccountThemeService accountThemeService;
	
	@Inject
	AccountService accountService;
	
	@Inject
	AccountRuleDefinitionService accountRuleDefinitionService;
	
	@Inject
	AccountLoginAttemptService accountLoginAttemptService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
 		Authentication authen = setUpSecurityContext(request, authentication);
		super.onAuthenticationSuccess(request, response, authen);
	}
	
	private Authentication setUpSecurityContext(HttpServletRequest request, Authentication authentication) {
		logger.info("Get user 's permission");
		SecurityContextImpl context = new SecurityContextImpl();
		
		// Get user's permission
		AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
		
		// KhanhTH: Login check
		Account account = accountService.findOneByAccountId(accountDetails.getAccount().getAccountId());
		AccountRuleDefinition accountRuleDefinition = accountRuleDefinitionService.getAccountRuleDefinitionWhenAuthentication(account.getAccountRuleDefinitionId());
		Calendar calendar = Calendar.getInstance();
		boolean isGetUserProfile = true;
		//// Check password lifetime
		if(accountRuleDefinition != null) {
			Timestamp passwordLastUpdated = account.getUpdatedDate();
			int passwordExpiredDay = 0;
			
			if(accountRuleDefinition.getLifeTime() > 0) {
				passwordExpiredDay = accountRuleDefinition.getLifeTime();
				calendar.setTime(passwordLastUpdated);
				calendar.add(Calendar.DATE, passwordExpiredDay);
			}
			
			//// Check account lock after continuous login error
			AccountLoginAttempt accountLoginAttempt = accountLoginAttemptService.getAccountLoginAttemptByAccountId(account.getAccountId());
			if(accountLoginAttempt != null && accountRuleDefinition.getLoginContinuousFailureCount() > 0) {
				if(accountLoginAttempt.getLoginContinuousFailureCount() >= accountRuleDefinition.getLoginContinuousFailureCount()) {
					Timestamp currentDatetime = FunctionCommon.getCurrentTime();
					Timestamp lastLogin = accountLoginAttempt.getUpdatedDate();
					long passedTimeInSecond = (currentDatetime.getTime() - lastLogin.getTime()) / 1000;
					
					long remainingSecond = accountRuleDefinition.getAccountLockTime() * 60 - passedTimeInSecond;
					
					long remainMinute = remainingSecond / 60;
					long remainSecond = remainingSecond % 60;
					
					if((remainMinute > 0 || remainSecond > 0) && accountLoginAttempt.getLoginContinuousFailureCount() > 0 &&
							accountLoginAttempt.getLoginContinuousFailureCount() >= accountRuleDefinition.getLoginContinuousFailureCount()) {
						setDefaultTargetUrl("/login?error=true&message=" + MessageUtils.getMessage(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0047, remainMinute, remainSecond));
						isGetUserProfile = false;
					} else {
						accountLoginAttempt.setGenerationsCount(accountLoginAttempt.getGenerationsCount());
						accountLoginAttempt.setLoginContinuousFailureCount(0);
						accountLoginAttempt.setAccountId(account.getAccountId());
						
						accountLoginAttemptService.modify(accountLoginAttempt);
						isGetUserProfile = true;
					}
				} else {
					accountLoginAttempt.setGenerationsCount(accountLoginAttempt.getGenerationsCount());
					accountLoginAttempt.setLoginContinuousFailureCount(0);
					accountLoginAttempt.setAccountId(account.getAccountId());
					
					accountLoginAttemptService.modify(accountLoginAttempt);
					setDefaultTargetUrl("/getUserDetailInfo");
				}
			} else {
				isGetUserProfile = true;
			}
		}
		
		List<Permission> permissions = permissionService.getRoleAndPermissionOfAccount(accountDetails.getAccount().getAccountId());
		
		//// Can only access user profile when password is expired or haven't changed password in case password force change
		if(accountRuleDefinition != null) {
			if ((calendar.getTime().getTime() < FunctionCommon.getCurrentTime().getTime() && accountRuleDefinition.getLifeTime() > 0) || (account.isForceChangePassword()) ) {
				List<Permission> newListPermission = new ArrayList<Permission>();
				
				if (permissions.size() > 0) {
					for (Permission permission : permissions) {
						if (permission.getModuleCode().equals("tqp.accountprofile")) {
							newListPermission.add(permission);
						}
					}
				} else {
					permissions = permissionService.getAll();
					
					for (Permission permission : permissions) {
						if (permission.getModuleCode().equals("tqp.accountprofile")) {
							newListPermission.add(permission);
						}
					}
				}
				
				permissions = null;
				permissions = newListPermission;
			}
		}
		
		if (isGetUserProfile) {
			setDefaultTargetUrl("/getUserDetailInfo");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Permission permission : permissions) {
			GrantedAuthority authority = new SimpleGrantedAuthority(permission.getPermissionCode());
			authorities.add(authority);
		}
		accountDetails.getAccount().setAuthorities(authorities);
		
		AccountProfile accountProfile=accountProfileService.getAccountProfile(account.getAccountId());
		if(accountProfile == null)
		{
			accountProfile = accountProfileService.getDefaultProfile();
			accountProfileService.update(accountProfile);
		}
		
		SessionUtils.set(SessionUtils.ACCOUNT_PROFILE,accountProfile);
		/*SessionUtils.generateTemporaryFolderPath();*/
		// set authentication
		Authentication authenticationNew = new UsernamePasswordAuthenticationToken(accountDetails, UUID.randomUUID(), authorities);
		context.setAuthentication(authenticationNew);
		SecurityContextHolder.setContext(context);
		
		// set theme into session
		setThemeSetting(accountDetails.getAccount().getAccountId());
		
		// set pagesize into session
		setPageSizeSetting(accountProfile.getPagesizeValue());
		
		// set timeout
		if (null != accountProfile.getSessionTimeOut() && accountProfile.getSessionTimeOut().intValue() > 0 ) {
			request.getSession().setMaxInactiveInterval(60*accountProfile.getSessionTimeOut().intValue());
		}
		
		return authenticationNew;
	}
	
	private void setThemeSetting(Long accountId)
	{
		Map<String, String> mapTheme = accountThemeService.getThemeSetting(accountId);
		
		String panelHeader = "";
		String panelBody = "";
		String panelListTable = "";
		String panelListTh = "";
		String panelListTdText = "";
		String panelListTdNumeric = "";
		String panelListTdDate = "";
		String panelListTdDateTime = "";
		String panelListTdNoNumber = "";
		String panelListTdActionColumn = "";
		String panelTableForm = "";
		String panelTableFormTh = "";
		String panelTableFormTd = "";
		String screenStyle = "";
		String menuBgColor = "";
		String menuBrandColor = "";
		String menuBrandSize = "";
		String menuTextColor = "";
		
		String menuSelectedBgColor = "";
		
		String menuStyle = "";
		String menuSelectedStyle = "";
		
		String menuItemStyle = "";
		String menuItemHoverStyle = "";
		String menuItemBgHoverStyle = "";
		String footerStyle = "";
		String logo = "";
		String logoPosition = "";
		String logoWidthHeight = "";
		
		String width = "";
		String height = "";
		String backgroundColor = "";
		
		for(Map.Entry<String, String> entry: mapTheme.entrySet()) {
			if (entry.getKey().indexOf("panel-header-") == 0) {
				panelHeader += entry.getKey().replace("panel-header-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-body-") == 0) {
				panelBody += entry.getKey().replace("panel-body-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-list-table-") == 0) {
				panelListTable += entry.getKey().replace("panel-list-table-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-list-th-") == 0) {
				panelListTh += entry.getKey().replace("panel-list-th-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-list-td-text") == 0) {
				panelListTdText += entry.getKey().replace("panel-list-td-text", "text-align") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("panel-list-td-numeric")) {
				panelListTdNumeric += entry.getKey().replace("panel-list-td-numeric", "text-align") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("panel-list-td-date")) {
				panelListTdDate += entry.getKey().replace("panel-list-td-date", "text-align") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("panel-list-td-date-time")) {
				panelListTdDateTime += entry.getKey().replace("panel-list-td-date-time", "text-align") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("panel-list-td-no-number")) {
				panelListTdNoNumber += entry.getKey().replace("panel-list-td-no-number", "text-align") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("panel-list-td-action-column")) {
				panelListTdActionColumn += entry.getKey().replace("panel-list-td-action-column", "text-align") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-table-form-table-") == 0) {
				panelTableForm += entry.getKey().replace("panel-table-form-table-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-table-form-th-") == 0) {
				panelTableFormTh += entry.getKey().replace("panel-table-form-th-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-table-form-td-") == 0) {
				panelTableFormTd += entry.getKey().replace("panel-table-form-td-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("common-screen-size")) {
				screenStyle += entry.getKey().replace("common-screen-size", "width") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("common-font-family")) {
				screenStyle += entry.getKey().replace("common-font-family", "font-family") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("common-font-size")) {
				screenStyle += entry.getKey().replace("common-font-size", "font-size") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("commom-background-image")) {
				backgroundColor += entry.getKey().replace("commom-background-image", "background-image") + ":url(data:image/jpg;base64," + entry.getValue() + "); ";
				
			}  else if (entry.getKey().equals("common-background-color")) {
				backgroundColor += entry.getKey().replace("common-background-color", "background-color") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("menu-bg-color")) {
				menuBgColor += entry.getValue();
				
			}  else if (entry.getKey().equals("menu-brand-color")) {
				menuBrandColor += entry.getKey().replace("menu-brand-color", "color") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("menu-brand-size")) {
				menuBrandSize += entry.getKey().replace("menu-brand-size", "font-size") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("menu-text-color")) {
				menuTextColor += entry.getKey().replace("menu-text-color", "color") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("menu-font-color")) {
				menuStyle += entry.getKey().replace("menu-font-color", "color") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("menu-font-size")) {
				menuStyle += entry.getKey().replace("menu-font-size", "font-size") + ":" + entry.getValue() + "; ";
				
			}  else if (entry.getKey().equals("menu-selected-bg-color")) {
				menuSelectedBgColor += entry.getValue();
				
			}  else if (entry.getKey().equals("menu-selected-text-color")) {
				menuSelectedStyle += entry.getKey().replace("menu-selected-text-color", "color") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().equals("item-menu-bg-color")) {
				menuItemStyle += entry.getKey().replace("item-menu-bg-color", "background-color") + ":" + entry.getValue() + "; ";
					
			} else if (entry.getKey().equals("item-menu-font-color")) {
				menuItemStyle += entry.getKey().replace("item-menu-font-color", "color") + ":" + entry.getValue() + "; ";
					
			} else if (entry.getKey().equals("item-menu-font-size")) {
				menuItemStyle += entry.getKey().replace("item-menu-font-size", "font-size") + ":" + entry.getValue() + "; ";
					
			} else if (entry.getKey().equals("item-menu-hover-bg-color")) {
				menuItemBgHoverStyle += entry.getValue();
					
			} else if (entry.getKey().equals("item-menu-hover-text-color")) {
				menuItemHoverStyle += entry.getKey().replace("item-menu-hover-text-color", "color") + ":" + entry.getValue() + "; ";
					
			} else if (entry.getKey().equals("footer-text-color")) {
				footerStyle += entry.getKey().replace("footer-text-color", "color") + ":" + entry.getValue() + "; ";
					
			} else if (entry.getKey().equals("footer-text-size")) {
				footerStyle += entry.getKey().replace("footer-text-size", "font-size") + ":" + entry.getValue() + "; ";
					
			} else if (entry.getKey().equals("logo")) {
				logo += entry.getKey().replace("logo", "") + ":" + entry.getValue() + "; ";
					
			} else if (entry.getKey().equals("panel-table-list-td-anotherChild-position")) {
				logoPosition += entry.getValue();
					
			} else if (entry.getKey().equals("panel-table-list-td-firstChild-width")) {
				width += entry.getValue();
					
			} else if (entry.getKey().equals("panel-table-list-td-firstChild-height")) {
				height += entry.getValue();
					
			}
			
		}
		
//		mapTheme.put("logo", logo);
		
		if (StringUtils.isEmpty(logoPosition)) {
			logoPosition = "right";
		}
		
//		mapTheme.put("logoPosition", logoPosition);
		
		if (height.length() > 0) {
			height = "height: " + height + ";";
		} else {
			height = "height: 25px;";
		}
		
		if (width.length() > 0) {
			width = "width: " + width + ";";
		}
		
//		mapTheme.put("backgroundColor", backgroundColor);
//		mapTheme.put("logoWidth", width);
//		mapTheme.put("logoHeight", height);
//		
//		mapTheme.put("footerStyle", footerStyle);
//		mapTheme.put("menuItemBgHoverStyle", menuItemBgHoverStyle);
//		mapTheme.put("menuSelectedBgColor", menuSelectedBgColor);
//		
//		mapTheme.put("menuItemStyle", menuItemStyle);
//		mapTheme.put("menuItemHoverStyle", menuItemHoverStyle);
//		
//		mapTheme.put("menuStyle", menuStyle);
//		mapTheme.put("menuSelectedStyle", menuSelectedStyle);
//		
//		mapTheme.put("menuBgColor", menuBgColor);
//		mapTheme.put("menuBrandColor", menuBrandColor);
//		mapTheme.put("menuBrandSize", menuBrandSize);
//		mapTheme.put("menuTextColor", menuTextColor);
//		
//		mapTheme.put("panelHeader", panelHeader);
//		mapTheme.put("panelBody", panelBody);
//		
//		mapTheme.put("panelListTable", panelListTable);
//		mapTheme.put("panelListTh", panelListTh);
//		mapTheme.put("panelListTdText", panelListTdText);
//		
//		mapTheme.put("panelListTdNumeric", panelListTdNumeric);
//		mapTheme.put("panelListTdDate", panelListTdDate);
//		mapTheme.put("panelListTdDateTime", panelListTdDateTime);
//		mapTheme.put("panelListTdNoNumber", panelListTdNoNumber);
//		mapTheme.put("panelListTdActionColumn", panelListTdActionColumn);
//		
//		mapTheme.put("panelTableForm", panelTableForm);
//		mapTheme.put("panelTableFormTh", panelTableFormTh);
//		mapTheme.put("panelTableFormTd", panelTableFormTd);
//		
//		mapTheme.put("screenStyle", screenStyle);
		
		SessionUtils.set(SessionUtils.THEME_INFOR, mapTheme);
	}
	private void setPageSizeSetting(String pageSize)
	{
		// convert string json to object
		if(pageSize == null || pageSize.length()==0)
		{
			return;
		}
		JsonFactory json = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(json);
		try {
			Map<String, String> mapObject = mapper.readValue(pageSize,new TypeReference<Map<String, String>>() {});
			SessionUtils.set(SessionUtils.PAGESIZE_INFOR, mapObject);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
