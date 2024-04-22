package org.terasoluna.qp.domain.service.menudesign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.MenuType;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.HttpServletRequestUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.MenuDesign;
import org.terasoluna.qp.domain.model.MenuDesignItem;
import org.terasoluna.qp.domain.repository.languagedesign.LanguageDesignRepository;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeUtil;

@Component
public class MenuBuilderService {

	@Inject
	LanguageDesignRepository languageDesignRepository;
	
	@Inject
	@Named(value = "CL_LANGUAGE_CODE")
	CodeList codeListLanguage;

	private static final String MENU_DIVIDER = "<li class=\"divider\"></li>";
	private static final String START_NAVBAR_HORIZONTAL = "<div class=\"navbar navbar-default navbar-horizontal\">";
	private static final String START_NAVBAR_VERTICAL = "<div class=\"navbar navbar-default navbar-vertical affix-top\">";
	private static final String END_NAVBAR = "</div>";

	private static final String START_NAVBAR_COLLAPSE = "<div class=\"navbar-menu\">";
	private static final String END_NAVBAR_COLLAPSE = "</div>";
	private static final String NAVBAR_HEADER = "<div class=\"navbar-header\"><a class=\"navbar-brand\" href=\"%s\">%s</a></div>";
	private static final String NAVBAR_HEADER_VERTICAL = "<div class=\"navbar-header\"><span class=\"navbar-brand glyphicon glyphicon-menu-hamburger navbar-brand-ver\"></span><a class=\"navbar-brand\" href=\"%s\" style=\"padding: 15px 0px 0px 7px\" >%s</a></div>";
	private static final String START_LEVEL_0_VERTICAL = "<li class=\"dropdown\"><a href=\"%s\" tabindex=\"0\" data-toggle=\"dropdown\" data-submenu>%s</a><ul class=\"dropdown-menu dropdown-menu-right\">";
	private static final String START_LEVEL_0_HORIZONTAL = "<li class=\"dropdown\"><a href=\"%s\" tabindex=\"0\" data-toggle=\"dropdown\" data-submenu>%s</a><ul class=\"dropdown-menu\">";
	private static final String END_LEVEL_0 = "</ul></li>";

	private static final String START_LEVEL_CHILD = "<li class=\"dropdown-submenu\"><a href=\"%s\" tabindex=\"0\"> %s </a><ul class=\"dropdown-menu\">";
	private static final String END_LEVEL_CHILD = "</ul></li>";

	private static final String ITEM_LEVEL_0 = "<li class=\"dropdown\"><a href=\"%s\" tabindex=\"0\" data-toggle=\"dropdown\" data-submenu>%s</a></li>";
	private static final String ITEM_LEVEL_0_WITHOUTCHILD = "<li><a href=\"%s\" tabindex=\"0\">%s</a></li>";
	private static final String ITEM_LEVEL_CHILD = "<li><a href=\"%s\" tabindex=\"0\">%s</a></li>";

	private static final String START_NAVBAR_NAV = "<ul class=\"nav navbar-nav\">";
	private static final String END_NAVBAR_NAV = "</ul>";

	protected static final String LINK_DEFAULT = "javascript:void(0);";
	
	protected static final String PAGECONTEXT = "${pageContext.request.contextPath}/";
	
	private static final String MESSAGE_CODE = "<qp:message code=\"%s\" />";

	private static final String START_AUTHORIZATION = "<qp:authorization permission=\"%s\">";
	private static final String END_AUTHORIZATION = "</qp:authorization>";

	private int menuType;
	
	public String build(MenuDesign menu, int menuFor) {
		if(menu !=null){
			StringBuilder menuStringBuilder = null;
			
			StringBuilder strUrl = new StringBuilder(StringUtils.defaultString(menu.getUrlRoot(), StringUtils.EMPTY));
			strUrl.append(StringUtils.defaultString(menu.getUrlMainAction(), StringUtils.EMPTY));

			if (DataTypeUtils.equals(menu.getMenuType(), DbDomainConst.MenuDirection.VERTICAL)) {
				menuStringBuilder = new StringBuilder(START_NAVBAR_HORIZONTAL);
				rightMenu(menuStringBuilder, menuFor, menu.getUrlRoot(), menu.getProjectId());
				menuStringBuilder.append(END_NAVBAR);
				menuStringBuilder.append(START_NAVBAR_VERTICAL);
				menuType = DbDomainConst.MenuDirection.VERTICAL;
				
				switch (menuFor) {
					case MenuType.PREVIEW:
						menuStringBuilder.append(String.format(NAVBAR_HEADER_VERTICAL, LINK_DEFAULT, StringUtils.defaultString(menu.getHeaderMenuName(), StringUtils.EMPTY)));
						break;
					case MenuType.JSP:
						String mgsCode = String.format(MESSAGE_CODE, StringUtils.defaultString(menu.getHeaderMenuName(), StringUtils.EMPTY));
						menuStringBuilder.append(String.format(NAVBAR_HEADER_VERTICAL, StringUtils.defaultString(strUrl.toString(), PAGECONTEXT + "home"), mgsCode));
						menu.setUrlRoot(PAGECONTEXT);
						break;
					default:
						menuStringBuilder.append(String.format(NAVBAR_HEADER_VERTICAL, StringUtils.defaultString(strUrl.toString(), LINK_DEFAULT), StringUtils.defaultString(menu.getHeaderMenuName(), StringUtils.EMPTY)));
						break;
				}
			} else {
				menuStringBuilder = new StringBuilder(START_NAVBAR_HORIZONTAL);
				menuType = DbDomainConst.MenuDirection.HORIZONTAL;

				switch (menuFor) {
					case MenuType.PREVIEW:
						menuStringBuilder.append(String.format(NAVBAR_HEADER, LINK_DEFAULT, StringUtils.defaultString(menu.getHeaderMenuName(), StringUtils.EMPTY)));
						break;
					case MenuType.JSP:
						String mgsCode = String.format(MESSAGE_CODE, StringUtils.defaultString(menu.getHeaderMenuName(), StringUtils.EMPTY));
						menuStringBuilder.append(String.format(NAVBAR_HEADER, StringUtils.defaultString(strUrl.toString(), PAGECONTEXT + "home"), mgsCode));
						menu.setUrlRoot(PAGECONTEXT);
						break;
					default:
						menuStringBuilder.append(String.format(NAVBAR_HEADER, StringUtils.defaultString(strUrl.toString(), LINK_DEFAULT), StringUtils.defaultString(menu.getHeaderMenuName(), StringUtils.EMPTY)));
						break;
				}
			}

			menuStringBuilder.append(START_NAVBAR_COLLAPSE);
			menuStringBuilder.append(START_NAVBAR_NAV);
			buildMenu(menuStringBuilder, menu, null, menuFor);
	
			adminMenu(menuStringBuilder, menuFor, menu.getUrlRoot());
	
			menuStringBuilder.append(END_NAVBAR_NAV);
			if (DataTypeUtils.equals(menuType, DbDomainConst.MenuDirection.HORIZONTAL)) {
				rightMenu(menuStringBuilder, menuFor, menu.getUrlRoot(), menu.getProjectId());
			}
			menuStringBuilder.append(END_NAVBAR_COLLAPSE);
			menuStringBuilder.append(END_NAVBAR);
			return menuStringBuilder.toString();
		} else {
			return StringUtils.EMPTY;
		}
	}

	private void endSubMenu(int level, StringBuilder menuStringBuilder) {
		if (level == 0) {
			menuStringBuilder.append(END_LEVEL_0);
		} else {
			menuStringBuilder.append(END_LEVEL_CHILD);
		}
	}

	private void buildMenuItem(StringBuilder menuStringBuilder, MenuDesignItem item, int level, int menuFor, String urlRoot) {
		if (MenuType.PREVIEW == menuFor) {
			if (level == 0) {
				if (CollectionUtils.isNotEmpty(item.getListChild())) {
					menuStringBuilder.append(String.format(ITEM_LEVEL_0, LINK_DEFAULT, StringUtils.defaultString(item.getMenuName(), StringUtils.EMPTY)));
				} else {
					menuStringBuilder.append(String.format(ITEM_LEVEL_0_WITHOUTCHILD, LINK_DEFAULT, StringUtils.defaultString(item.getMenuName(), StringUtils.EMPTY)));
				}
			} else {
				menuStringBuilder.append(String.format(ITEM_LEVEL_CHILD, LINK_DEFAULT, StringUtils.defaultString(item.getMenuName(), StringUtils.EMPTY)));
			}
		} else {
			StringBuilder strUrl = new StringBuilder();
			
			if (StringUtils.isNotBlank(item.getActionUrlCode())) {
				strUrl.append(StringUtils.defaultString(urlRoot, ""));
				strUrl.append(StringUtils.defaultString(GenerateSourceCodeUtil.normalizedURL(item.getActionUrlCode()), ""));
				if (MenuType.PROTOTYPE == menuFor) {
					strUrl.append(".html");
				}
			} else {
				strUrl.append(StringUtils.defaultIfBlank(urlRoot, LINK_DEFAULT));
			}
			
			if (MenuType.PROTOTYPE == menuFor){
				if (level == 0) {
					if (CollectionUtils.isNotEmpty(item.getListChild())) {
						menuStringBuilder.append(String.format(ITEM_LEVEL_0, LINK_DEFAULT, StringUtils.defaultString(item.getMenuName(), StringUtils.EMPTY)));
					} else {
						menuStringBuilder.append(String.format(ITEM_LEVEL_0_WITHOUTCHILD, strUrl, StringUtils.defaultString(item.getMenuName(), StringUtils.EMPTY)));
					}
				} else {
					menuStringBuilder.append(String.format(ITEM_LEVEL_CHILD, strUrl, StringUtils.defaultString(item.getMenuName(), StringUtils.EMPTY)));
				}
			} else {
				
				String mgsCode = String.format(MESSAGE_CODE, StringUtils.defaultString(item.getMenuName(), StringUtils.EMPTY));
				
				String permissionCode = StringUtils.EMPTY;
				if (StringUtils.isNotBlank(item.getActionUrlCode())) {
					permissionCode = GenerateSourceCodeUtil.normalizedURL(item.getActionUrlCode()).replace("/", StringUtils.EMPTY);
					
					//Start add permission
					menuStringBuilder.append(String.format(START_AUTHORIZATION, permissionCode));
					
					if (level == 0) {
						if (CollectionUtils.isNotEmpty(item.getListChild())) {
							menuStringBuilder.append(String.format(ITEM_LEVEL_0, LINK_DEFAULT, mgsCode));
						} else {
							menuStringBuilder.append(String.format(ITEM_LEVEL_0_WITHOUTCHILD, strUrl, mgsCode));
						}
					} else {
						menuStringBuilder.append(String.format(ITEM_LEVEL_CHILD, strUrl, mgsCode));
					}
					
					//End add permission
					menuStringBuilder.append(END_AUTHORIZATION);
				} else {
					if (level == 0) {
						if (CollectionUtils.isNotEmpty(item.getListChild())) {
							menuStringBuilder.append(String.format(ITEM_LEVEL_0, LINK_DEFAULT, mgsCode));
						} else {
							menuStringBuilder.append(String.format(ITEM_LEVEL_0_WITHOUTCHILD, strUrl, mgsCode));
						}
					} else {
						menuStringBuilder.append(String.format(ITEM_LEVEL_CHILD, strUrl, mgsCode));
					}
				}
			}
		}
	}

	private void buildMenu(StringBuilder menuStringBuilder, MenuDesign menuDesign, String strParentID, int menuFor) {
		List<MenuDesignItem> lInput = menuDesign.getListMenuDesignItem();
		String urlRoot = menuDesign.getUrlRoot();
		if (CollectionUtils.isEmpty(lInput))
			return;
		// list child of parent
		List<MenuDesignItem> lItem = new ArrayList<MenuDesignItem>();

		int size = lInput.size();

		for (int i = 0; i < size; i++) {
			MenuDesignItem item = lInput.get(i);
			if (FunctionCommon.equals(item.getParentMenuItemId(), strParentID)) {
				lItem.add(item);
			}
		}

		if (FunctionCommon.isNotEmpty(lItem)) {
			int level = StringUtils.isEmpty(strParentID) ? 0 : 1;
			for (MenuDesignItem item : lItem) {
				// if item is separator
				if (DbDomainConst.MenuItemType.SEPARATOR.equals(item.getMenuItemType())) {
					separator(menuStringBuilder);
					continue;
				}
				// if hasn't child then add item
				if (!hasChilds(item.getMenuItemId(), lInput)) {
					buildMenuItem(menuStringBuilder, item, level, menuFor, urlRoot);
				} else {
					
					if (MenuType.JSP == menuFor){
						String mgsCode = String.format(MESSAGE_CODE, StringUtils.defaultString(item.getMenuName(), StringUtils.EMPTY));
						if (level == 0) {
							if (DataTypeUtils.equals(menuDesign.getMenuType(), DbDomainConst.MenuDirection.HORIZONTAL)) {
								menuStringBuilder.append(String.format(START_LEVEL_0_HORIZONTAL, LINK_DEFAULT, mgsCode));
							} else {
								menuStringBuilder.append(String.format(START_LEVEL_0_VERTICAL, LINK_DEFAULT, mgsCode));
							}
						} else {
							menuStringBuilder.append(String.format(START_LEVEL_CHILD, LINK_DEFAULT, mgsCode));
						}
					}
					else {
						if (level == 0) {
							if (DataTypeUtils.equals(menuDesign.getMenuType(), DbDomainConst.MenuDirection.HORIZONTAL)) {
								menuStringBuilder.append(String.format(START_LEVEL_0_HORIZONTAL, LINK_DEFAULT, StringUtils.defaultString(item.getMenuName(), StringUtils.EMPTY)));
							} else {
								menuStringBuilder.append(String.format(START_LEVEL_0_VERTICAL, LINK_DEFAULT, StringUtils.defaultString(item.getMenuName(), StringUtils.EMPTY)));
							}
	
						} else {
							menuStringBuilder.append(String.format(START_LEVEL_CHILD, LINK_DEFAULT, StringUtils.defaultString(item.getMenuName(), StringUtils.EMPTY)));
						}
					}
					buildMenu(menuStringBuilder, menuDesign, item.getMenuItemId(), menuFor);
					endSubMenu(level, menuStringBuilder);
				}
			}
		} else
			return;
	}

	protected void separator(StringBuilder menuStringBuilder) {
		menuStringBuilder.append(MENU_DIVIDER);
	}

	protected boolean hasChilds(String strParentID, List<MenuDesignItem> lInput) {
		if (CollectionUtils.isEmpty(lInput)) {
			return false;
		}
		for (int i = 0; i < lInput.size(); i++) {
			if (FunctionCommon.equals(lInput.get(i).getParentMenuItemId(), strParentID)) {
				return true;
			}
		}
		return false;
	}

	public void adminMenu(StringBuilder menuStringBuilder, int menuFor, String urlRoot) {
		
		switch (menuFor) {
			case MenuType.JSP:
				if (DbDomainConst.MenuDirection.HORIZONTAL == menuType) {
					menuStringBuilder.append("<qp:authorizations permissions=\"accountSearch,roleSearch,messageSearch,accountruledefinitionSearch\"><li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\"><qp:message code=\"sc.tqp.0014\" /> </a> <ul class=\"dropdown-menu \" role=\"menu\"> <qp:authorization permission=\"accountruledefinitionSearch\"> <li><a href=\"${pageContext.request.contextPath}/accountruledefinition/search?init\"><qp:message code=\"tqp.accountruledefinition\" /></a></li> <li class=\"divider\"></li> </qp:authorization> <qp:authorization permission=\"accountSearch\"> <li><a href=\"${pageContext.request.contextPath}/account/search?init\"><qp:message code=\"tqp.account\" /></a></li> <li class=\"divider\"></li> </qp:authorization> <qp:authorization permission=\"roleSearch\"> <li><a href=\"${pageContext.request.contextPath}/role/search?init\"><qp:message code=\"tqp.rolemanagement\" /></a></li> <li class=\"divider\"></li> </qp:authorization> <qp:authorization permission=\"messageSearch\"> <li><a href=\"${pageContext.request.contextPath}/message/search?init\"><qp:message code=\"tqp.message\" /></a></li> </qp:authorization> </ul> </li> </qp:authorizations>");
				} else {
					menuStringBuilder.append("<qp:authorizations permissions=\"accountSearch,roleSearch,messageSearch,accountruledefinitionSearch\"><li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\"><qp:message code=\"sc.tqp.0014\" /></a> <ul class=\"dropdown-menu dropdown-menu-right\" role=\"menu\"> <qp:authorization permission=\"accountruledefinitionSearch\"> <li><a href=\"${pageContext.request.contextPath}/accountruledefinition/search?init\"><qp:message code=\"tqp.accountruledefinition\" /></a></li> <li class=\"divider\"></li> </qp:authorization> <qp:authorization permission=\"accountSearch\"> <li><a href=\"${pageContext.request.contextPath}/account/search?init\"><qp:message code=\"tqp.account\" /></a></li> <li class=\"divider\"></li> </qp:authorization> <qp:authorization permission=\"roleSearch\"> <li><a href=\"${pageContext.request.contextPath}/role/search?init\"><qp:message code=\"tqp.rolemanagement\" /></a></li> <li class=\"divider\"></li> </qp:authorization> <qp:authorization permission=\"messageSearch\"> <li><a href=\"${pageContext.request.contextPath}/message/search?init\"><qp:message code=\"tqp.message\" /></a></li> </qp:authorization> </ul> </li> </qp:authorizations>");
				}
				break;
			case MenuType.PREVIEW:
				menuStringBuilder.append("<li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\">");
				menuStringBuilder.append(MessageUtils.getMessage(CommonMessageConst.SC_TQP_0014));
				if (DbDomainConst.MenuDirection.VERTICAL == menuType) {
					menuStringBuilder.append("</a><ul class=\"dropdown-menu dropdown-menu-right\" role=\"menu\"> <li><a href=\"javascript:\">");
				} else {
					menuStringBuilder.append("</a><ul class=\"dropdown-menu\" role=\"menu\"> <li><a href=\"javascript:\">");
				}
				menuStringBuilder.append(MessageUtils.getMessage(CommonMessageConst.TQP_ACCOUNTRULEDEFINITION));
				menuStringBuilder.append("</a></li> <li class=\"divider\"></li><li><a href=\"javascript:\">");
				menuStringBuilder.append(MessageUtils.getMessage(CommonMessageConst.TQP_ACCOUNT));
				menuStringBuilder.append("</a></li><li class=\"divider\"></li><li><a href=\"javascript:\">");
				menuStringBuilder.append(MessageUtils.getMessage(CommonMessageConst.TQP_ROLEMANAGEMENT));
				menuStringBuilder.append("</a></li> <li class=\"divider\"></li><li><a href=\"javascript:\">");
				menuStringBuilder.append(MessageUtils.getMessage(CommonMessageConst.TQP_MESSAGE));
				menuStringBuilder.append("</a></li></ul> </li>");
				break;
			case MenuType.PROTOTYPE:
				StringBuilder strUrl = new StringBuilder();
				strUrl.append(StringUtils.defaultString(urlRoot, ""));

				menuStringBuilder.append("<li class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\">");
				menuStringBuilder.append(MessageUtils.getMessage(CommonMessageConst.SC_TQP_0014));
				if (DbDomainConst.MenuDirection.VERTICAL == menuType) {
					menuStringBuilder.append(String.format("</a> <ul class=\"dropdown-menu dropdown-menu-right\" role=\"menu\"> <li><a href=\"%s\">", strUrl+ "accountRuledefinition/searchForm.html"));
				} else {
					menuStringBuilder.append(String.format("</a> <ul class=\"dropdown-menu\" role=\"menu\"> <li><a href=\"%s\">", strUrl.toString() + "accountRuledefinition/searchForm.html"));
				}
				menuStringBuilder.append(MessageUtils.getMessage(CommonMessageConst.TQP_ACCOUNTRULEDEFINITION));
				menuStringBuilder.append(String.format("</a></li> <li class=\"divider\"></li><li><a href=\"%s\">", strUrl.toString() + "account/searchForm.html"));
				menuStringBuilder.append(MessageUtils.getMessage(CommonMessageConst.TQP_ACCOUNT));
				menuStringBuilder.append(String.format("</a></li><li class=\"divider\"></li><li><a href=\"%s\">", strUrl.toString() + "role/searchForm.html"));
				menuStringBuilder.append(MessageUtils.getMessage(CommonMessageConst.TQP_ROLEMANAGEMENT));
				menuStringBuilder.append(String.format("</a></li> <li class=\"divider\"></li><li><a href=\"%s\">", strUrl.toString() + "message/searchForm.html"));
				menuStringBuilder.append(MessageUtils.getMessage(CommonMessageConst.TQP_MESSAGE));
				menuStringBuilder.append("</a></li></ul> </li>");
				break;
		}
	}

	public void rightMenu(StringBuilder menuStringBuilder, int menuType, String urlRoot, Long projectId) {
		menuStringBuilder.append("<ul class=\"nav navbar-nav navbar-side\">");
		menuStringBuilder.append("<li class=\"dropdown\">");
		menuStringBuilder.append("<a href=\"javascript:\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\" style=\"font-size: 16px;\">");
		menuStringBuilder.append("<span class=\"glyphicon glyphicon-globe\"></span>");
		menuStringBuilder.append("</a>");
		menuStringBuilder.append("<ul class=\"dropdown-menu dropdown-nav-left\" role=\"menu\">");

		//Collection<LanguageDesign> listOfLanguage = languageDesignRepository.findLanguageByProjectId(projectId);

		if (MenuType.JSP == menuType) {
			//if (CollectionUtils.isNotEmpty(listOfLanguage)) {
				/*for (LanguageDesign language : listOfLanguage) {
					menuStringBuilder.append("<li>");
					menuStringBuilder.append("<a onclick=\"$.qp.changeSystemLanguage('");
					menuStringBuilder.append(language.getLanguageCode());
					menuStringBuilder.append("_");
					menuStringBuilder.append(language.getCountryCode());
					menuStringBuilder.append("')\" href=\"javascript:\">");
					menuStringBuilder.append("<img src=\"${requestScope['javax.servlet.forward.context_path']}/resources/media/images/");
					menuStringBuilder.append(language.getLanguageCode());
					menuStringBuilder.append("_");
					menuStringBuilder.append(language.getCountryCode());
					menuStringBuilder.append(".png\" style=\"border: 0px;\" />&nbsp;&nbsp;");
					menuStringBuilder.append("<qp:message code=\"${CL_LANGUAGE_CODE.get(item.key) }\"/>");
					menuStringBuilder.append("</a>");
					menuStringBuilder.append("</li>");
				}*/
				menuStringBuilder.append("<c:forEach items=\"${CL_LANGUAGE_LIST}\" var=\"item\">");
				menuStringBuilder.append("<li>");
				menuStringBuilder.append("<a onclick=\"$.qp.changeSystemLanguage('${item.key}')\" href=\"javascript:\"><img src=\"${pageContext.request.contextPath}/resources/media/images/${item.key}.png\" style=\"border: 0px;\" />&nbsp;&nbsp;<qp:message code=\"${CL_LANGUAGE_CODE.get(item.key) }\"/></a>");
				menuStringBuilder.append("</li>");
				menuStringBuilder.append("</c:forEach>");

			//}

			menuStringBuilder.append("</ul>");
			menuStringBuilder.append("</li>");
			menuStringBuilder.append("<li class=\"dropdown\">");
			menuStringBuilder.append("<a href=\"javascript:\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\" style=\"font-size: 16px;\">");
			menuStringBuilder.append("<span class=\"glyphicon glyphicon-user\"></span>");
			menuStringBuilder.append("</a>");
			menuStringBuilder.append("<ul class=\"dropdown-menu dropdown-user\">");

			menuStringBuilder.append("<li>");
			menuStringBuilder.append("<a class=\"qp-link-popup\" href=\"${pageContext.request.contextPath}/accountprofile/modifyUserSetting\">");
			menuStringBuilder.append("<span class=\"glyphicon glyphicon-user\"></span>&nbsp;&nbsp;<qp:message code=\"sc.tqp.0009\"/>");
			menuStringBuilder.append("</a>");
			menuStringBuilder.append("</li>");

			menuStringBuilder.append("<li>");
			menuStringBuilder.append("<a class=\"qp-link-popup\" href=\"${pageContext.request.contextPath}/accountprofile/modifySystemSetting\">");
			menuStringBuilder.append("<span class=\"glyphicon glyphicon-cog\"></span>&nbsp;&nbsp;<qp:message code=\"sc.tqp.0010\" />");
			menuStringBuilder.append("</a>");
			menuStringBuilder.append("</li>");

			menuStringBuilder.append("<li>");
			menuStringBuilder.append("<a class=\"qp-link-popup\" href=\"${pageContext.request.contextPath}/accountprofile/modifyTheme\">");
			menuStringBuilder.append("<span class=\"glyphicon glyphicon-text-color\"></span>&nbsp;&nbsp;<qp:message code=\"sc.tqp.0015\" />");
			menuStringBuilder.append("</a>");
			menuStringBuilder.append("</li>");

			menuStringBuilder.append(" <li class=\"divider\"></li>");
			menuStringBuilder.append("<li>");
			menuStringBuilder.append("<a href=\"javascript:document.getElementById('logOutForm').submit();\">");
			menuStringBuilder.append("<span class=\"glyphicon glyphicon-log-out\"></span>&nbsp;&nbsp;<qp:message code=\"sc.tqp.0002\" />");
			menuStringBuilder.append("</a> ");
			menuStringBuilder.append("<form:form method=\"POST\" action=\"${pageContext.request.contextPath}/logout\" id=\"logOutForm\"></form:form>");
		} else {
			Collection<LanguageDesign> listOfLanguage = languageDesignRepository.findLanguageByProjectId(projectId);
			Map<String, String> clLanguage = codeListLanguage.asMap();
			StringBuilder url = new StringBuilder();
			if (CollectionUtils.isNotEmpty(listOfLanguage)) {
				if (MenuType.PREVIEW == menuType) {
					url.append(HttpServletRequestUtils.getRequest().getContextPath());
					url.append("/resources/");
				} else {
					url.append(StringUtils.defaultIfBlank(urlRoot, StringUtils.EMPTY));
				}

				for (LanguageDesign language : listOfLanguage) {
					menuStringBuilder.append("<li>");
					menuStringBuilder.append("<a href=\"javascript:\">");
					menuStringBuilder.append("<img src=\"");
					menuStringBuilder.append(url);
					menuStringBuilder.append("media/images/");
					menuStringBuilder.append(language.getLanguageCode());
					menuStringBuilder.append(DbDomainConst.SEPARATE_LANGUAGE_COUNTRY);
					menuStringBuilder.append(language.getCountryCode());
					menuStringBuilder.append(".png\" style=\"border: 0px;\" />&nbsp;&nbsp;");
					menuStringBuilder.append(MessageUtils.getMessage(clLanguage.get(language.getLanguageCode()+DbDomainConst.SEPARATE_LANGUAGE_COUNTRY+language.getCountryCode())));
					menuStringBuilder.append("</a>");
					menuStringBuilder.append("</li>");
				}
			}

			menuStringBuilder.append("</ul>");
			menuStringBuilder.append("</li>");
			menuStringBuilder.append("<li class=\"dropdown\">");
			menuStringBuilder.append("<a href=\"javascript:\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\" style=\"font-size: 16px;\">");
			menuStringBuilder.append("<span class=\"glyphicon glyphicon-user\"></span>");
			menuStringBuilder.append("</a>");
			menuStringBuilder.append("<ul class=\"dropdown-menu dropdown-user dropdown-nav-left\">");

			
			if (MenuType.PREVIEW == menuType) {
				menuStringBuilder.append("<li>");
				menuStringBuilder.append("<a href=\"javascript:void(0)\">");
				menuStringBuilder.append("<span class=\"glyphicon glyphicon-user\"></span>&nbsp;&nbsp;");
				menuStringBuilder.append(MessageUtils.getMessage("sc.tqp.0009"));
				menuStringBuilder.append("</a>");
				menuStringBuilder.append("</li>");
	
				menuStringBuilder.append("<li>");
				menuStringBuilder.append("<a href=\"javascript:void(0)\">");
				menuStringBuilder.append("<span class=\"glyphicon glyphicon-cog\"></span>&nbsp;&nbsp;");
				menuStringBuilder.append(MessageUtils.getMessage("sc.tqp.0010"));
				menuStringBuilder.append("</a>");
				menuStringBuilder.append("</li>");
	
				menuStringBuilder.append("<li>");
				menuStringBuilder.append("<a href=\"javascript:void(0)\">");
				menuStringBuilder.append("<span class=\"glyphicon glyphicon-text-color\"></span>&nbsp;&nbsp;");
				menuStringBuilder.append(MessageUtils.getMessage("sc.tqp.0015"));
				menuStringBuilder.append("</a>");
				
				menuStringBuilder.append("</li>");
				menuStringBuilder.append(" <li class=\"divider\"></li>");
				menuStringBuilder.append("<li>");
				menuStringBuilder.append("<a href=\"javascript:void(0)\">");
				menuStringBuilder.append("<span class=\"glyphicon glyphicon-log-out\"></span>&nbsp;&nbsp;");
				menuStringBuilder.append(MessageUtils.getMessage("sc.tqp.0002"));
				menuStringBuilder.append("</a> ");
				
			} else {
				menuStringBuilder.append("<li>");
				menuStringBuilder.append(String.format("<a class=\"qp-link-popup\" href=\"%saccountProfile/profileForm.html\">", url.toString()));
				menuStringBuilder.append("<span class=\"glyphicon glyphicon-user\"></span>&nbsp;&nbsp;");
				menuStringBuilder.append(MessageUtils.getMessage("sc.tqp.0009"));
				menuStringBuilder.append("</a>");
				menuStringBuilder.append("</li>");

				menuStringBuilder.append("<li>");
				menuStringBuilder.append(String.format("<a class=\"qp-link-popup\" href=\"%saccountProfile/settingForm.html\">", url.toString()));
				menuStringBuilder.append("<span class=\"glyphicon glyphicon-cog\"></span>&nbsp;&nbsp;");
				menuStringBuilder.append(MessageUtils.getMessage("sc.tqp.0010"));
				menuStringBuilder.append("</a>");
				menuStringBuilder.append("</li>");

				menuStringBuilder.append("<li>");
				menuStringBuilder.append(String.format("<a class=\"qp-link-popup\" href=\"%saccountProfile/themeForm.html\">", url.toString()));
				menuStringBuilder.append("<span class=\"glyphicon glyphicon-text-color\"></span>&nbsp;&nbsp;");
				menuStringBuilder.append(MessageUtils.getMessage("sc.tqp.0015"));
				menuStringBuilder.append("</a>");

				menuStringBuilder.append("</li>");
				menuStringBuilder.append(" <li class=\"divider\"></li>");
				menuStringBuilder.append("<li>");
				menuStringBuilder.append(String.format("<a href=\"%slogin.html\">", url.toString()));
				menuStringBuilder.append("<span class=\"glyphicon glyphicon-log-out\"></span>&nbsp;&nbsp;");
				menuStringBuilder.append(MessageUtils.getMessage("sc.tqp.0002"));
				menuStringBuilder.append("</a> ");
			}
			
		}
		menuStringBuilder.append("</li>");
		menuStringBuilder.append("</ul>");
		menuStringBuilder.append("</li>");
		menuStringBuilder.append("</ul>");
	}
}