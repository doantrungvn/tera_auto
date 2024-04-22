package org.terasoluna.qp.domain.service.screendesign;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ZipUtils;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CodeList;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.FormulaDefinition;
import org.terasoluna.qp.domain.model.FormulaDetail;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.MenuDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.NavigatorComponent;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ProjectItem;
import org.terasoluna.qp.domain.model.ScreenAction;
import org.terasoluna.qp.domain.model.ScreenActionParam;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenAreaEvent;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenDesignForJSP;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenFormTabGroup;
import org.terasoluna.qp.domain.model.ScreenFormTabs;
import org.terasoluna.qp.domain.model.ScreenGroupItem;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemAutocompleteInput;
import org.terasoluna.qp.domain.model.ScreenItemEvent;
import org.terasoluna.qp.domain.model.ScreenItemEventMapping;
import org.terasoluna.qp.domain.model.ScreenItemStatus;
import org.terasoluna.qp.domain.model.TableTempForJSP;
import org.terasoluna.qp.domain.model.TempScreenDesign;
import org.terasoluna.qp.domain.repository.autocomplete.AutocompleteDesignRepository;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.FormulaDefinitionRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.NavigationComponentRepository;
import org.terasoluna.qp.domain.repository.codelist.CodeListDetailRepository;
import org.terasoluna.qp.domain.repository.codelist.CodeListRepository;
import org.terasoluna.qp.domain.repository.module.ModuleRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.repository.screenaction.ScreenActionRepository;
import org.terasoluna.qp.domain.repository.screenactionparam.ScreenActionParamRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.screenitem.ScreenItemRepository;
import org.terasoluna.qp.domain.repository.screenitemcodelist.ScreenItemCodelistRepository;
import org.terasoluna.qp.domain.repository.screenitemstatus.ScreenItemStatusRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignInputRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeUtil;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerationHandler;
import org.terasoluna.qp.domain.service.menudesign.MenuDesignService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignConst.ScreenActionConst;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Handler for generating BLogic class
 * 
 * @author tung
 * @version 2.0
 */
@Component(value = "ScreenGenerateHandler")
public class ScreenGenerateHandler extends GenerationHandler {

	@Inject
	ScreenDesignRepository screenDesignRepository;

	@Inject
	ModuleRepository moduleRepository;

	@Inject
	ScreenDesignService screenDesignService;

	@Inject
	BusinessDesignRepository businessDesignRepository;

	@Inject
	AutocompleteDesignRepository autocompleteDesignRepository;

	@Inject
	SqlDesignRepository sqlDesignRepository;

	@Inject
	TableDesignRepository tableDesignRepository;

	@Inject
	CodeListRepository codeListRepository;

	@Inject
	ScreenItemCodelistRepository screenItemCodelistRepository;

	@Inject
	CodeListDetailRepository codeListDetailRepository;

	@Inject
	ScreenItemRepository screenItemRepository;

	@Inject
	MenuDesignService menuDesignService;

	@Inject
	ScreenActionParamRepository screenActionParamRepository;

	@Inject
	SystemService systemService;

	@Inject
	ProjectRepository projectRepository;
	
	@Inject
	SqlDesignInputRepository sqlDesignInputRepository;
	
	@Inject
	ScreenActionRepository screenActionRepository;
	
	@Inject
	NavigationComponentRepository navigationComponentRepository;

	@Inject
	Mapper beanMapper;
	
	@Inject
	FormulaDefinitionRepository formulaDefinitionRepository;
	
	@Inject
	ScreenItemStatusRepository screenItemStatusRepository;

	/* private Log logger = GetLogger.getLogger(this); */
	private static final String TEMPLATE_SCREEN_HEADER = "screen_header_new.ftl";
	//private static final String TEMPLATE_SCREEN_HEADER_VERTICAL = "screen_header_new_vertical.ftl";
	private static final String TEMPLATE_SCREEN_HEADER_JSP = "screen_header_new_jsp.ftl";
	private static final String TEMPLATE_SCREEN_FOOTER = "screen_footer_new.ftl";
	private static final String TEMPLATE_SCREEN_COMMON_DELETE = "screen_common_delete.ftl";
	//private static final String TEMPLATE_SCREEN_FOOTER_VERTICAL = "screen_footer_new_vertical.ftl";
	private static final String TEMPLATE_SCREEN_FOOTER_JSP = "screen_footer_new_jsp.ftl";
	private static final String TEMPLATE_SCREEN_BODY = "screen_body_new.ftl";
	private static final String TEMPLATE_SCREEN_BODY_JSP = "screen_body_new_jsp.ftl";
	//private static final String TEMPLATE_SCREEN_BODY_VERTICAL = "screen_body_new_vertical.ftl";
	private static final String TEMPLATE_SCREEN_HEADER_FOR_VIEW = "screen_header_new_for_view.ftl";

	private static final String TEMPLATE_LOGIN = "login.ftl";
	/* private static final String TEMPLATE_LOGIN_VERTICAL = "login_vertical.ftl"; */
	private static final String TEMPLATE_HOME = "home.ftl";
	/* private static final String TEMPLATE_HOME_VERTICAL = "home_vertical.ftl"; */
	private static final String TEMPLATE_JAVASCRIPT_PROCESS = "javascriptProcess.ftl";
	private static final String TEMPLATE_JAVASCRIPT_INIT = "javascriptInit.ftl";
	private static final String TEMPLATE_HEADER_JSP = "header_jsp.ftl";
	private static final String TEMPLATE_FOOTER_JSP = "footer_jsp.ftl";
	private static final String TEMPLATE_LAYOUT_TEMPLATE_JSP = "template_jsp.ftl";
	
	private static final Integer BEAN_TYPE_OUTPUT = 0;
	private static final Integer BEAN_TYPE_INPUT = 1;
	
	private static final Integer TEMPLATE_TYPE_NORMAL = 1;
	private static final Integer TEMPLATE_TYPE_POPUP = 2;
	
	private static final Integer MAPPING_TYPE_OPTION_SUBMIT = 0;
	private static final Integer MAPPING_TYPE_OPTION_DISPLAY = 1;
	private static final Integer MAPPING_TYPE_DATA_SOURCE = 2;
	private static final Integer MAPPING_TYPE_DISPLAY = 3;

	private static final Integer DESIGN_MODE_PROTOTYPE = 1;
	private static final Integer DESIGN_MODE_BUSINESS = 2;
	
	private final Integer SHOW_BLANK_ITEM = 1;
	
	/* private static final String TEMPLATE_SCREEN_HEADER_LAYOUT_NEW_JSP = "screen_header_layouts_new_jsp.ftl"; */
	
	private static final String ENCODE_UTF8 = "UTF-8";

	private static String rootDir = "";
	
	private static String rootDirHTML = "";
	
	private String tempDir = "";

	private String sourcePath;

	private static String path = "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator + "views" + File.separator + "layout" + File.separator;
	
	private static final List<String> listCommonPopup = Arrays.asList("viewForm.html", "passwordForm.html", "profileForm.html", "settingForm.html", "themeForm.html");
	
	private Long currentLanguageId;
	
	public void importData(Account currentAccount, Long currentLanguageId, Long currentProjectId){
		this.currentLanguageId = currentLanguageId;
	}

	public String getRootDir() {
		return rootDir;
	}
	
	public String getRootDirHTML() {
		return rootDirHTML;
	}

	public String init(String path, String name, String pathTemp) {
		String folderId = GenerateUniqueKey.generateWithDatePrefix();
		tempDir = pathTemp;
		rootDir = path + name + folderId + File.separator;
		rootDirHTML = path + name + folderId + "-html" + File.separator;
		File folder = new File(rootDir);
		File folderHTML = new File(rootDirHTML);
		try {
			FileUtilsQP.forceMkdir(folder);
			FileUtilsQP.forceMkdir(folderHTML);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return folderId;
	}

	public void generateProjectInfo(Project project, List<Module> listOfModules, String menuContent, MenuDesign menuDesign, Long languageId, Account currentAccount ) {
		/**
		 * init configuration
		 */
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			Template tempLogin = null;
			Template tempHome = null;

			/*
			 * switch (menuDesign.getMenuType()) { case DbDomainConst.MenuType.HORIZONTAL:
			 */
			tempLogin = this.getTemplate(TEMPLATE_LOGIN);
			tempHome = this.getTemplate(TEMPLATE_HOME);
			/*
			 * break;
			 * 
			 * case DbDomainConst.MenuType.VERTICAL: tempLogin = this.getTemplate(TEMPLATE_LOGIN_VERTICAL); tempHome = this.getTemplate(TEMPLATE_HOME_VERTICAL); break; }
			 */

			/*
			 * Template tempHeader = this.getTemplate(TEMPLATE_SCREEN_HEADER); Template tempFooter = this.getTemplate(TEMPLATE_SCREEN_FOOTER);
			 */
			
			List<ProjectItem> lstProjectItem = projectRepository.getProjectItemByProjectId(project.getProjectId(), languageId);
			List<ProjectItem> listLogoLeft = new ArrayList<ProjectItem>();
			List<ProjectItem> listLogoRight = new ArrayList<ProjectItem>();
			List<ProjectItem> listHeaderLeft = new ArrayList<ProjectItem>();
			List<ProjectItem> listHeaderRight = new ArrayList<ProjectItem>();
			List<ProjectItem> listFooterLeft = new ArrayList<ProjectItem>();
			List<ProjectItem> listFooterRight = new ArrayList<ProjectItem>();

			if (lstProjectItem != null) {
				for (ProjectItem projectItem : lstProjectItem) {
					if (projectItem.getItemPosition() == 0) {
						listLogoLeft.add(projectItem);
					} else if (projectItem.getItemPosition() == 1) {
						listLogoRight.add(projectItem);
					} else if (projectItem.getItemPosition() == 2) {
						listHeaderLeft.add(projectItem);
					} else if (projectItem.getItemPosition() == 3) {
						listHeaderRight.add(projectItem);
					} else if (projectItem.getItemPosition() == 4) {
						listFooterLeft.add(projectItem);
					} else {
						listFooterRight.add(projectItem);
					}
				}
				data.put("listLogoLeft", listLogoLeft);
				data.put("listLogoRight", listLogoRight);
				data.put("listHeaderLeft", listHeaderLeft);
				data.put("listHeaderRight", listHeaderRight);
				data.put("listFooterLeft", listFooterLeft);
				data.put("listFooterRight", listFooterRight);
			}
			data.put("pageContext", "${pageContext.request.contextPath}");
			data.put("projectStyle", screenDesignService.getProjectStyle(project.getProjectId()));
			data.put("projectName", project.getProjectName());
			data.put("listOfModules", listOfModules);
			data.put("menuContent", menuContent);
			data.put("headerMenuName", menuDesign.getHeaderMenuName());
			data.put("userName", currentAccount.getUsername());
			data.put("dateTime", FunctionCommon.getCurrentTime());
			// Get url of home page
			if(StringUtils.isNotBlank(menuDesign.getUrlHomePage())) {
				data.put("urlHomePage", menuDesign.getUrlHomePage());
			} else {
				data.put("urlHomePage", "home");
			}
			// Get url of main action page
			if(StringUtils.isNotBlank(menuDesign.getUrlMainAction())) {
				data.put("urlMainActionPage", menuDesign.getUrlMainAction());
			} else {
				data.put("urlMainActionPage", "home");
			}
			
			FileOutputStream fileStream = null;

			// login
			fileStream = new FileOutputStream(new File(rootDir + File.separator + "login.html"));
			OutputStreamWriter outLogin = new OutputStreamWriter(fileStream, ENCODE_UTF8);
			tempLogin.process(data, outLogin);
			outLogin.flush();
			IOUtils.closeQuietly(outLogin);
			IOUtils.closeQuietly(fileStream);
			// home
			fileStream = new FileOutputStream(new File(rootDir + File.separator + "home.html"));
			OutputStreamWriter outHome = new OutputStreamWriter(fileStream, ENCODE_UTF8);
			tempHome.process(data, outHome);
			outHome.flush();
			IOUtils.closeQuietly(outHome);
			IOUtils.closeQuietly(fileStream);

			// header
			/*
			 * Writer outHeader = new FileWriter(new File(rootDir + File.separator + "header.html")); tempHeader.process(data, outHeader); outHeader.flush(); IOUtils.closeQuietly(outHeader); //footer Writer outFooter = new FileWriter(new File(rootDir + File.separator + "footer.html")); tempFooter.process(data, outFooter); outFooter.flush(); IOUtils.closeQuietly(outFooter);
			 */
		} catch (Exception e) {
            e.printStackTrace();
		}
	}

	public String createModuleFolderHTML(String moduleName,int type) {
		return createDirectory(moduleName,type);
	}

	public void generateHTML(TempScreenDesign tempScreenDesign, String outputDir, String projectName, List<Module> listOfModules, String menuContent, int menuType, 
								Long projectId, Long languageId, Account currentAccount, int index,String outputDirHTML, MenuDesign menuDesign, Module module) {
		int countButton = 0;
		List<ScreenDesign> getAllOfScreen = screenDesignRepository.getAllScreenInfoByProjectId(projectId, languageId);
		/* conf.setDirectoryForTemplateLoading(new File(resourcePath)); */
		FileOutputStream fileStream = null;
		OutputStreamWriter out = null;
		FileOutputStream fileStreamCommonDelete = null;
		OutputStreamWriter outCommonDelete = null;
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			for (int i = 0; i < tempScreenDesign.getScreenItems().length; i++) {
				String styleTdOfLayout = "";
				if(tempScreenDesign.getScreenItems()[i].getScreenArea() != null && tempScreenDesign.getScreenItems()[i].getScreenArea().getInputStyle() != null ) {
					styleTdOfLayout = tempScreenDesign.getScreenItems()[i].getScreenArea().getInputStyle();
				}
				tempScreenDesign.getScreenItems()[i].setModuleId(tempScreenDesign.getModuleId());
				//Link 
				if (tempScreenDesign.getScreenItems()[i].getLogicalDataType() != null && 
						(DbDomainConst.LogicDataType.LINK_DYNAMIC.equals(tempScreenDesign.getScreenItems()[i].getLogicalDataType()) || DbDomainConst.LogicDataType.LINK.equals(tempScreenDesign.getScreenItems()[i].getLogicalDataType()))) {
					Pattern p = Pattern.compile(".*\\\"(.*)\\\".*");
					Matcher m = p.matcher(tempScreenDesign.getScreenItems()[i].getElement());
					String parentLocation = "";
					if(tempScreenDesign.getScreenItems()[i].getScreenAction() != null && tempScreenDesign.getScreenItems()[i].getScreenAction().getActionType() != null && ScreenActionConst.ACTION_TYPE_BLOGIC.equals(tempScreenDesign.getScreenItems()[i].getScreenAction().getActionType())) {
						tempScreenDesign.getScreenItems()[i].getScreenAction().setToScreenTemplateType(tempScreenDesign.getScreenItems()[i].getScreenAction().getToBlogicScreenTemplateType());
					}  
					if(tempScreenDesign.getScreenItems()[i].getScreenAction() != null && tempScreenDesign.getScreenItems()[i].getScreenAction().getToScreenCode() != null && 
							tempScreenDesign.getScreenItems()[i].getScreenAction().getToScreenTemplateType() != null && tempScreenDesign.getScreenItems()[i].getScreenAction().getToScreenTemplateType().equals(TEMPLATE_TYPE_NORMAL)) {
						// => close popup use parentLocation
						parentLocation = "parent.location.href='"+tempScreenDesign.getScreenItems()[i].getScreenAction().getToScreenCode()+".html'";
						data.put("destinationTemplateType", "normal");
					} else {
						data.put("destinationTemplateType", "popup");
					}
					
					if (m.find()) {
							//KhangTM:								
							String onClickFuncStr = "javascript:checkEnableConfirm('url','enableConfirm','messageConfirm', this);";
							String url = m.group(1);
							String enableConfirmStr = (tempScreenDesign.getScreenItems()[i].getEnableConfirm()==null) ? "0" : tempScreenDesign.getScreenItems()[i].getEnableConfirm().toString();
							String messageConfirmStr =(tempScreenDesign.getScreenItems()[i].getMessageConfirm()==null) ? "" : tempScreenDesign.getScreenItems()[i].getMessageConfirm().getMessageString();
							String messageString = (tempScreenDesign.getScreenItems()[i].getMessageDesign() == null) ? "" : tempScreenDesign.getScreenItems()[i].getMessageDesign().getMessageString();
							String onClickFunc = onClickFuncStr.replaceAll("url", url).replaceAll("enableConfirm", enableConfirmStr).replaceAll("messageConfirm", messageConfirmStr);
							// Navigate to screen
							if (tempScreenDesign.getScreenItems()[i].getScreenAction() != null && tempScreenDesign.getScreenItems()[i].getScreenAction().getActionType() != null && tempScreenDesign.getScreenItems()[i].getScreenAction().getActionType().equals(0)) {
								for (ScreenDesign screenDestination : getAllOfScreen) {
									if (tempScreenDesign.getScreenItems()[i].getScreenAction().getToScreenId() != null && tempScreenDesign.getScreenItems()[i].getScreenAction().getToScreenId().equals(screenDestination.getScreenId())) {

										// Within module
										if (tempScreenDesign.getScreenItems()[i].getModuleId().equals(screenDestination.getModuleId())) {
											// 
											if (screenDestination.getTemplateType().equals(DbDomainConst.TemplateType.NORMAL)) {
												tempScreenDesign.getScreenItems()[i].setElement("<a "+parentLocation+" onmouseover=\"this.setAttribute('style', '" + tempScreenDesign.getScreenItems()[i].getHoverStyle() + "')\" onmouseout=\"this.setAttribute('style', '" + tempScreenDesign.getScreenItems()[i].getStyle() + "')\" href=\"" + onClickFunc + "\">" + messageString + "</a>");
											} else {
												tempScreenDesign.getScreenItems()[i].setElement("<a "+parentLocation+" onmouseover=\"this.setAttribute('style', '" + tempScreenDesign.getScreenItems()[i].getHoverStyle() + "')\" onmouseout=\"this.setAttribute('style', '" + tempScreenDesign.getScreenItems()[i].getStyle() + "')\" class=\"qp-link-popup\" href=\"" +  m.group(1) + "\">" + messageString + "</a>");
											}
										}

										// Different module => set href of [a]
										else {
											url = ".." + "/" + screenDestination.getModuleCode() + "/" + m.group(1);
											onClickFunc = onClickFuncStr.replaceAll("url", url).replaceAll("enableConfirm", enableConfirmStr).replaceAll("messageConfirm", messageConfirmStr);
											if (screenDestination.getTemplateType().equals(DbDomainConst.TemplateType.NORMAL)) {
												tempScreenDesign.getScreenItems()[i].setElement("<a "+parentLocation+" onmouseover=\"this.setAttribute('style', '" + tempScreenDesign.getScreenItems()[i].getHoverStyle() + "')\" onmouseout=\"this.setAttribute('style', '" + tempScreenDesign.getScreenItems()[i].getStyle() + "')\" href=\"" + onClickFunc + "\">" + messageString + "</a>");
											} else {
												tempScreenDesign.getScreenItems()[i].setElement("<a "+parentLocation+" onmouseover=\"this.setAttribute('style', '" + tempScreenDesign.getScreenItems()[i].getHoverStyle() + "')\" onmouseout=\"this.setAttribute('style', '" + tempScreenDesign.getScreenItems()[i].getStyle() + "')\" class=\"qp-link-popup\" href=\".." + "/" + screenDestination.getModuleCode() + "/" + m.group(1) + "\">" + messageString + "</a>");
											}
										}
									}
								}
							}
							// Navigate to blogic => load again
							if (tempScreenDesign.getScreenItems()[i].getScreenAction() != null && tempScreenDesign.getScreenItems()[i].getScreenAction().getActionType() != null && tempScreenDesign.getScreenItems()[i].getScreenAction().getActionType().equals(1)) {
								tempScreenDesign.getScreenItems()[i].setElement("<a "+parentLocation+" onmouseover=\"this.setAttribute('style', '" + tempScreenDesign.getScreenItems()[i].getHoverStyle() + "')\" onmouseout=\"this.setAttribute('style', '" + tempScreenDesign.getScreenItems()[i].getStyle() + "')\" href=\"\">" + messageString + "</a>");
							}
					}
					// Target screen
					ScreenDesign screenDesign = screenDesignRepository.getScreenDesignByScreenActionId(tempScreenDesign.getScreenItems()[i].getScreenActionId());
					if (screenDesign != null) {
						if (screenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.REGISTER)) {
							data.put("glyphicon", "glyphicon-plus");
						}
						if (screenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.SEARCH)) {
							data.put("glyphicon", "glyphicon-search");
						}
					}
					/*data.put("widthSpan", tempScreenDesign.getScreenItems()[i].getWidth() + tempScreenDesign.getScreenItems()[i].getWidthUnit());*/
				}
				
				//Button
				else if (DbDomainConst.LogicDataType.BUTTON.equals(tempScreenDesign.getScreenItems()[i].getLogicalDataType()) && !tempScreenDesign.getScreenItems()[i].getLogicalDataType().equals(null)) {
					Pattern p = Pattern.compile(".*\\\'(.*)\\\'.*");
					Matcher m = p.matcher(tempScreenDesign.getScreenItems()[i].getElement());
					if (m.find()) {
						String parentLocation = "";
						if(tempScreenDesign.getScreenItems()[i].getScreenAction() != null && tempScreenDesign.getScreenItems()[i].getScreenAction().getActionType() != null && ScreenActionConst.ACTION_TYPE_BLOGIC.equals(tempScreenDesign.getScreenItems()[i].getScreenAction().getActionType())) {
							tempScreenDesign.getScreenItems()[i].getScreenAction().setToScreenTemplateType(tempScreenDesign.getScreenItems()[i].getScreenAction().getToBlogicScreenTemplateType());
						} 
						if(tempScreenDesign.getScreenItems()[i].getScreenAction() != null && tempScreenDesign.getScreenItems()[i].getScreenAction().getToScreenCode() != null && 
								tempScreenDesign.getScreenItems()[i].getScreenAction().getToScreenTemplateType() != null &&
								tempScreenDesign.getScreenItems()[i].getScreenAction().getToScreenTemplateType().equals(TEMPLATE_TYPE_NORMAL)) {
							// => close popup use parentLocation
							parentLocation = "parent.location.href='"+tempScreenDesign.getScreenItems()[i].getScreenAction().getToScreenCodeByNavigateBlogic()+".html'"; 
							data.put("destinationTemplateType", "normal");
						} else {
							data.put("destinationTemplateType", "popup");
						}
						//KhangTM:
						String onClickFuncStr = "checkEnableConfirm('url','enableConfirm','messageConfirm', this);";
						String url = m.group(1);
						String enableConfirmStr = (tempScreenDesign.getScreenItems()[i].getEnableConfirm()==null) ? "0" : tempScreenDesign.getScreenItems()[i].getEnableConfirm().toString();
						String messageConfirmStr =(tempScreenDesign.getScreenItems()[i].getMessageConfirm()==null) ? "" : tempScreenDesign.getScreenItems()[i].getMessageConfirm().getMessageString();
						String messageString = (tempScreenDesign.getScreenItems()[i].getMessageDesign() == null) ? "" : tempScreenDesign.getScreenItems()[i].getMessageDesign().getMessageString();
						String onClickFunc = onClickFuncStr.replaceAll("url", url).replaceAll("enableConfirm", enableConfirmStr).replaceAll("messageConfirm", messageConfirmStr);
							
							String buttonType = "";
							//Default or Save
							if( (tempScreenDesign.getScreenItems()[i].getButtonType() != null && (ScreenDesignConst.ButtonType.BUTTON_TYPE_DEFAULT.equals(tempScreenDesign.getScreenItems()[i].getButtonType()) || ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE.equals(tempScreenDesign.getScreenItems()[i].getButtonType()))) 
									|| tempScreenDesign.getScreenItems()[i].getButtonType() == null) {
								buttonType = "qp-button qp-button-type";
							}
							//Delete
							else if(tempScreenDesign.getScreenItems()[i].getButtonType() != null && ScreenDesignConst.ButtonType.BUTTON_TYPE_DELETE.equals(tempScreenDesign.getScreenItems()[i].getButtonType())) {
								buttonType = "qp-button-type-warning";
							} 
							//Client
							else if(tempScreenDesign.getScreenItems()[i].getButtonType() != null && ScreenDesignConst.ButtonType.BUTTON_TYPE_CLIENT.equals(tempScreenDesign.getScreenItems()[i].getButtonType())) {
								buttonType = "qp-button qp-button-type-client";
							}
							
							String buttonIcon = "<i class=\"" + tempScreenDesign.getScreenItems()[i].getIcon() + "\"></i>";
							String buttonContent = messageString;
							String buttonContentTotal = "";
							if ((tempScreenDesign.getScreenItems()[i].getShowLabel() != null && ScreenDesignConst.ShowLabel.SHOW_LABEL.equals(tempScreenDesign.getScreenItems()[i].getShowLabel())) || 
									tempScreenDesign.getScreenItems()[i].getShowLabel() == null) {
								if(StringUtils.isNotEmpty(tempScreenDesign.getScreenItems()[i].getIcon())) {
									buttonContentTotal += buttonIcon;
									if(StringUtils.isNotEmpty(buttonContent)) {
										buttonContentTotal += "&nbsp;&nbsp;";
									}
									buttonContentTotal += buttonContent;
								} else {
									buttonContentTotal += buttonContent;
								}
							} else {
								buttonContentTotal += buttonIcon;
							}
							
							// Navigate to screen
							//////////////////////// To screen type = normal
							String toModuleCode = "";
							if(tempScreenDesign.getScreenItems()[i].getScreenAction() != null && tempScreenDesign.getScreenItems()[i].getScreenAction().getToModuleCodeByNavigateBlogic() != null) {
								toModuleCode = tempScreenDesign.getScreenItems()[i].getScreenAction().getToModuleCodeByNavigateBlogic();
							} else {
								if(tempScreenDesign.getScreenItems()[i].getScreenAction() != null) {
									toModuleCode = tempScreenDesign.getScreenItems()[i].getScreenAction().getToModuleCode();
								}
							}
							Integer navigateToTemplateType;
							if(tempScreenDesign.getScreenItems()[i].getScreenAction() != null && tempScreenDesign.getScreenItems()[i].getScreenAction().getToTemplateTypeByNavigateBlogic() != null) {
								navigateToTemplateType = tempScreenDesign.getScreenItems()[i].getScreenAction().getToTemplateTypeByNavigateBlogic();
							} else {
								if(tempScreenDesign.getScreenItems()[i].getScreenAction() != null) {
									navigateToTemplateType = tempScreenDesign.getScreenItems()[i].getScreenAction().getToScreenTemplateType();
								} else {
									//fix code
									navigateToTemplateType = 3;
								}
							}
							Long toModuleId;
							if(tempScreenDesign.getScreenItems()[i].getScreenAction() != null && tempScreenDesign.getScreenItems()[i].getScreenAction().getToModuleIdByNavigateBlogic() != null) {
								toModuleId = tempScreenDesign.getScreenItems()[i].getScreenAction().getToModuleIdByNavigateBlogic();
							} else {
								if(tempScreenDesign.getScreenItems()[i].getScreenAction() != null) {
									toModuleId = tempScreenDesign.getScreenItems()[i].getScreenAction().getToModuleId();
								} else {
									//fix code
									toModuleId = tempScreenDesign.getModuleId();
								}
								
							}
							String attrPopUp = "";
							if(TEMPLATE_TYPE_POPUP.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_NORMAL.equals(navigateToTemplateType) && tempScreenDesign.getScreenItems()[i].getEnableConfirm() != null && tempScreenDesign.getScreenItems()[i].getEnableConfirm().equals(1)) {
								attrPopUp = " attrpopup='true' ";
								data.put("parentLocation", "parent.location.href='"+tempScreenDesign.getScreenItems()[i].getScreenAction().getToScreenCodeByNavigateBlogic()+".html'");
							}
							
							if (TEMPLATE_TYPE_NORMAL.equals(navigateToTemplateType)) {
								// Within module
								if (tempScreenDesign.getScreenItems()[i].getModuleId().equals(toModuleId)) {
									tempScreenDesign.getScreenItems()[i].setElement("<button "+attrPopUp+" style=\""+styleTdOfLayout+";width:100%; "+tempScreenDesign.getScreenItems()[i].getStyle()+"  \" type=\"button\" onclick=\" "+onClickFunc+"\" class=\"btn qp-button "+buttonType+" \" value=\"\" style=\"" + tempScreenDesign.getScreenItems()[i].getStyle() + "\">"+buttonContentTotal+"</button>");
								}
								// Different module => set class of [button]
								else {
									url = ".." + "/" + toModuleCode + "/" + m.group(1);
									onClickFunc = onClickFuncStr.replaceAll("url", url).replaceAll("enableConfirm", enableConfirmStr).replaceAll("messageConfirm", messageConfirmStr);
									if (DbDomainConst.TemplateType.NORMAL.equals(tempScreenDesign.getScreenItems()[i].getScreenAction().getToTemplateTypeByNavigateBlogic())) {
										tempScreenDesign.getScreenItems()[i].setElement("<button "+attrPopUp+" style=\""+styleTdOfLayout+";width:100%; "+tempScreenDesign.getScreenItems()[i].getStyle()+"  \" type=\"button\" onclick=\" "+onClickFunc+"\" class=\"btn qp-button "+buttonType+" \" value=\"\" style=\"" + tempScreenDesign.getScreenItems()[i].getStyle() + "\">"+buttonContentTotal+"</button>");
									} else {
										if (DbDomainConst.TemplateType.NORMAL.equals(tempScreenDesign.getTemplateType())) {
											tempScreenDesign.getScreenItems()[i].setElement("<button "+attrPopUp+" style=\""+styleTdOfLayout+";width:100%; "+tempScreenDesign.getScreenItems()[i].getStyle()+"   \" type=\"button\" onclick=\" "+onClickFunc+"\" class=\"btn qp-button "+buttonType+" \" value=\"\" style=\"" + tempScreenDesign.getScreenItems()[i].getStyle() + "\">"+buttonContentTotal+"</button>");
										} 
									}
								}
							}
							///////////////////// To screen template type = popup
							else if (TEMPLATE_TYPE_POPUP.equals(navigateToTemplateType)) {
								if (tempScreenDesign.getScreenItems()[i].getModuleId().equals(toModuleId)) {
									//Source is normal or pop-op => parentLocation
									tempScreenDesign.getScreenItems()[i].setElement("<a "+attrPopUp+" style=\""+styleTdOfLayout+";width:100%;color:white; "+tempScreenDesign.getScreenItems()[i].getStyle()+"  \" "+parentLocation+"  class=\"btn qp-button qp-link-popup-text\" href=\"" + url + "\">" + buttonContentTotal + "</a>");
								}
								// Different module => set class of [button]
								else {
									url = ".." + "/" + toModuleCode+ "/" + m.group(1);
									onClickFunc = onClickFuncStr.replaceAll("url", url).replaceAll("enableConfirm", enableConfirmStr).replaceAll("messageConfirm", messageConfirmStr);
									if (DbDomainConst.TemplateType.NORMAL.equals(tempScreenDesign.getScreenItems()[i].getScreenAction().getToTemplateTypeByNavigateBlogic())) {
										tempScreenDesign.getScreenItems()[i].setElement("<a "+attrPopUp+" style=\""+styleTdOfLayout+";width:100%;color:white; "+tempScreenDesign.getScreenItems()[i].getStyle()+"  \" "+parentLocation+"  class=\"btn qp-button  qp-link-popup-text\" href=\"" + url + "\">" + buttonContentTotal + "</a>");
									} else {
										if (DbDomainConst.TemplateType.NORMAL.equals(tempScreenDesign.getTemplateType())) {
										tempScreenDesign.getScreenItems()[i].setElement("<a "+attrPopUp+" style=\""+styleTdOfLayout+";width:100%;color:white; "+tempScreenDesign.getScreenItems()[i].getStyle()+"  \" "+parentLocation+"  class=\"btn qp-button qp-link-popup-text\" href=\"" + url + "\">" + buttonContentTotal + "</a>");
										} 
									}
								}
							}
							//Button Delete
							else {
								ScreenDesign toscreen = new ScreenDesign();
								if(tempScreenDesign.getScreenItems()[i].getScreenAction() != null) {
									toscreen = screenDesignRepository.findById(tempScreenDesign.getScreenItems()[i].getScreenAction().getToBlogicInScreenId(), languageId, projectId);
								}
								
								//KhangTM:
								/*String toScreenCode = "";
								if(toscreen != null && toScreenCode != null) {
									toScreenCode = toscreen.getScreenCode();
								}*/
								/*url = tempScreenDesign.getScreenCode() + ".html";*/
								url = "commonDeleteSuccess.html";
								onClickFunc = onClickFuncStr.replaceAll("url", url).replaceAll("enableConfirm", enableConfirmStr).replaceAll("messageConfirm", messageConfirmStr);
											
								if(toscreen != null && toscreen.getTemplateType() != null && DbDomainConst.TemplateType.POPUP.equals(toscreen.getTemplateType())) {
									tempScreenDesign.getScreenItems()[i].setElement("<a "+attrPopUp+" style=\""+styleTdOfLayout+";width:100%;color:white; "+tempScreenDesign.getScreenItems()[i].getStyle()+"  \" "+parentLocation+"   class=\"btn qp-button qp-link-popup-text\" href=\"" + url + "\">" + buttonContentTotal + "</a>");
								} else {
									tempScreenDesign.getScreenItems()[i].setElement("<button "+attrPopUp+" style=\""+styleTdOfLayout+";width:100%; "+tempScreenDesign.getScreenItems()[i].getStyle()+"  \" type=\"button\" onclick=\" "+onClickFunc+" \"  class=\"btn qp-button "+buttonType+"\" value=\"\" style=\"" + tempScreenDesign.getScreenItems()[i].getStyle() + "\">"+buttonContentTotal+"</button>");
								}
								
								/*if(DbDomainConst.ScreenPatternType.VIEW.equals(tempScreenDesign.getScreenPatternType())) {
									tempScreenDesign.getScreenItems()[i].setElement("<button type=\"button\" class=\"btn qp-button-warning qp-dialog-confirm\" value=\"\" style=\"" + tempScreenDesign.getScreenItems()[i].getStyle() + "\">"+buttonContentTotal+"</button>");
								}*/
								
								if(DbDomainConst.ScreenPatternType.VIEW.equals(tempScreenDesign.getScreenPatternType())) {
									tempScreenDesign.getScreenItems()[i].setElement("<button "+attrPopUp+" style=\""+styleTdOfLayout+";width:100%; "+tempScreenDesign.getScreenItems()[i].getStyle()+"  \" type=\"button\" onclick=\"" + onClickFunc + "\" class=\"btn qp-button "+buttonType+"\" value=\"\" style=\"" + tempScreenDesign.getScreenItems()[i].getStyle() + "\">"+buttonContentTotal+"</button>");
								}
							}
								
					}
					/*data.put("widthSpan", tempScreenDesign.getScreenItems()[i].getWidth() + tempScreenDesign.getScreenItems()[i].getWidthUnit());*/
				}
			}
			List<ScreenItem> lstItemButton = new ArrayList<ScreenItem>();
			for (ScreenArea area : tempScreenDesign.getScreenAreas()) {
				for(ScreenItem item : area.getItems()) {
					 if (DbDomainConst.LogicDataType.BUTTON.equals(item.getLogicalDataType()) && !item.equals(null)) {
						 lstItemButton.add(item);
					 }
				}
			}
			for(int i = 0 ; i < lstItemButton.size(); i++) {
				for(int j = i+1; j < lstItemButton.size() - 1; j++) {
					if(lstItemButton.get(i).getScreenArea().getScreenAreaId().equals(lstItemButton.get(j).getScreenArea().getScreenAreaId())) {
						data.put("countButton", countButton);
					}
				}
			}
			
			List<ProjectItem> lstProjectItem = projectRepository.getProjectItemByProjectId(projectId, languageId);
			List<ProjectItem> listLogoLeft = new ArrayList<ProjectItem>();
			List<ProjectItem> listLogoRight = new ArrayList<ProjectItem>();
			List<ProjectItem> listHeaderLeft = new ArrayList<ProjectItem>();
			List<ProjectItem> listHeaderRight = new ArrayList<ProjectItem>();
			List<ProjectItem> listFooterLeft = new ArrayList<ProjectItem>();
			List<ProjectItem> listFooterRight = new ArrayList<ProjectItem>();

			if (lstProjectItem != null) {
				for (ProjectItem projectItem : lstProjectItem) {
					if (projectItem.getItemPosition() == 0) {
						listLogoLeft.add(projectItem);
					} else if (projectItem.getItemPosition() == 1) {
						listLogoRight.add(projectItem);
					} else if (projectItem.getItemPosition() == 2) {
						listHeaderLeft.add(projectItem);
					} else if (projectItem.getItemPosition() == 3) {
						listHeaderRight.add(projectItem);
					} else if (projectItem.getItemPosition() == 4) {
						listFooterLeft.add(projectItem);
					} else {
						listFooterRight.add(projectItem);
					}
				}
				data.put("listLogoLeft", listLogoLeft);
				data.put("listLogoRight", listLogoRight);
				data.put("listHeaderLeft", listHeaderLeft);
				data.put("listHeaderRight", listHeaderRight);
				data.put("listFooterLeft", listFooterLeft);
				data.put("listFooterRight", listFooterRight);
			}
			Map<String,String> projectStyle = screenDesignService.getProjectStyle(projectId);
			tempScreenDesign.setModuleCode(GenerateSourceCodeUtil.normalizedURL(tempScreenDesign.getModuleCode()));
			data.put("projectStyle", projectStyle);
			data.put("screenDesignForm", tempScreenDesign);
			data.put("projectName", projectName);
			data.put("listOfModules", listOfModules);
			data.put("menuContent", menuContent);
			data.put("checkHTML", 1);
			// Get url of home page
			if(StringUtils.isNotBlank(menuDesign.getUrlHomePage())) {
				data.put("urlHomePage", "..\\" + menuDesign.getUrlHomePage());
			} else {
				data.put("urlHomePage", "..\\home");
			}
			// Get url of main action page
			if(StringUtils.isNotBlank(menuDesign.getUrlMainAction())) {
				data.put("urlMainActionPage", "..\\" + menuDesign.getUrlMainAction());
			} else {
				data.put("urlMainActionPage", "..\\home");
			}
			data.put("pageContext", "${pageContext.request.contextPath}");
			data.put("userName", currentAccount.getUsername());
			data.put("dateTime", FunctionCommon.getCurrentTime());			
			data.put("messageSuccess", module.getModuleName() + " deleted successfully");
			data.put("redirectToSearch", "search" + module.getModuleCode());
			
			/* data.put("glyphicon", glyphicon); */

			Template tempScreenHeaderForView = this.getTemplate(TEMPLATE_SCREEN_HEADER_FOR_VIEW);
			Template tempScreenHeader = this.getTemplate(TEMPLATE_SCREEN_HEADER);
			Template tempScreenBody = this.getTemplate(TEMPLATE_SCREEN_BODY);
			Template tempScreenFooter = this.getTemplate(TEMPLATE_SCREEN_FOOTER);
			Template tempScreenCommonDelete = this.getTemplate(TEMPLATE_SCREEN_COMMON_DELETE);

			fileStream = new FileOutputStream(new File(outputDir + tempScreenDesign.getScreenCode() + ".html"));
			out = new OutputStreamWriter(fileStream, ENCODE_UTF8);
			
			// Resource = normal
			if (tempScreenDesign.getTemplateType().equals(DbDomainConst.TemplateType.NORMAL)) {
				tempScreenHeader.process(data, out);
				tempScreenBody.process(data, out);
				tempScreenFooter.process(data, out);
			} else {
				tempScreenHeaderForView.process(data, out);
				tempScreenBody.process(data, out);
			}
			out.flush();
			
			//Common delete successful
			fileStreamCommonDelete = new FileOutputStream(new File(outputDir + "commonDeleteSuccess.html"));
			outCommonDelete = new OutputStreamWriter(fileStreamCommonDelete, ENCODE_UTF8);
			tempScreenCommonDelete.process(data, outCommonDelete);
			outCommonDelete.flush();
			
			if(menuDesign.getGenOneTime() != null && menuDesign.getGenOneTime()) {
				String strHeaderHTML = generateHeaderHTML(tempScreenHeader,data,tempScreenDesign);
				String strFooterHTML = generateFooterHTML(tempScreenFooter,data,tempScreenDesign);
				String urlHomePage = menuDesign.getUrlHomePage() + ".html";
				FileUtilsQP.deleteQuietly(new File(outputDirHTML + File.separator + "header.html"));
				FileUtilsQP.deleteQuietly(new File(outputDirHTML + File.separator + "footer.html"));
				File fileSource = new File(getTempDir() + "html" + File.separator);
				File fileDestination = new File(outputDirHTML);
				FileUtilsQP.copyDirectoryToDirectory(fileSource, fileDestination);
				replaceContentHeaderFooter(outputDirHTML,strHeaderHTML, strFooterHTML, null, urlHomePage);
				menuDesign.setGenOneTime(false);
			}
		 } catch (Exception e) {
            e.printStackTrace();
	     }
		finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(fileStream);
			IOUtils.closeQuietly(outCommonDelete);
			IOUtils.closeQuietly(fileStreamCommonDelete);
		}
	}

	public String createDirectory(String name,int type) {
		if(type == 1) {
			StringBuilder fullPath = new StringBuilder(rootDir);
			fullPath.append(name);
			File folder = new File(fullPath.toString());
			try {
				FileUtilsQP.forceMkdir(folder);
			} catch (IOException e) {
				e.printStackTrace();
			}
			fullPath.append(File.separator);
			return fullPath.toString();
		}
		else {
			StringBuilder fullPath = new StringBuilder(rootDirHTML);
			File folder = new File(fullPath.toString());
			try {
				FileUtilsQP.forceMkdir(folder);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return fullPath.toString();
		}
	}

	/**
	 * zip prototype and common css + js
	 */
	public void zipPrototype(String path, String fileName, int generateType) {

		String zipDirName = path + fileName;
		String pathTemp = getTempDir() + "prototype" + File.separator;
		String pathTempHTML = this.createModuleFolderHTML(null,0) + "html" + File.separator;
		
		List<String> includeSource = new ArrayList<String>();
		//includeSource.add(rootDir);
		includeSource.addAll(FileUtilsQP.listChildsFileName(rootDir));
		if (generateType == 1) {
			includeSource.addAll(FileUtilsQP.listChildsFileName(pathTemp));
			includeSource.addAll(FileUtilsQP.listChildsFileName(pathTempHTML));
		}
		/* ZipUtils zipUtil = new ZipUtils(); */
		try {
			ZipUtils.Zip(includeSource, zipDirName);
		} catch (Exception ex) {
			// some errors occurred
			ex.printStackTrace();
		} finally {
			// after zip then delete folder
			FileUtilsQP.deleteQuietly(new File(rootDir));
		}
	}

	public TempScreenDesign generateJSP(String outputDir, Module module, ScreenDesign screenDesign, List<OutputBean> listOfOutputBean, List<InputBean> listOfInputBean, 
												Project project, Long languageId, GenerateSourceCode generateSourceCode,String pathModuleJavascript, 
												List<ScreenItemAutocompleteInput> listAutocompleteInputByScreenId, Boolean isRegisterConfirm, List<InputBean> lstInputBeanForRegisterConfirm,
												List<ScreenItem> listScreenItemConfirm,String menuContent, int countButtonSubmit, MenuDesign menuDesignNavigateJSP,CommonModel commonModel) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		String modelAttributeOut = "";
		if (screenDesign.getDesignMode().equals(DbDomainConst.DesignMode.BUSINESS)) {
			/*businessDesigns = businessDesignRepository.findBlogicByScreenFormId(screenDesign.getScreenForms());*/
			for(BusinessDesign bDesign : screenDesign.getBusinessProcessesDefault()) {
				bDesign.setBusinessLogicCode(GenerateSourceCodeUtil.normalizedVariantName(bDesign.getBusinessLogicCode()));
			}
			data.put("businessDesigns", screenDesign.getBusinessProcessesDefault());
			
			if(screenDesign.getBusinessProcessesDefault() != null && screenDesign.getBusinessProcessesDefault().size() > 0) {
				StringBuffer maForSubmit = new StringBuffer();
				maForSubmit.append(screenDesign.getBusinessProcessesDefault().get(0).getBusinessLogicCode());
				maForSubmit.append("InputForm");
				data.put("maForRegister", GenerateSourceCodeUtil.normalizedPropertiesJSP(maForSubmit.toString()));
			} else if(screenDesign.getBusinessInitsDefault() != null && screenDesign.getBusinessInitsDefault().size() > 0) {
				StringBuffer maForSubmit = new StringBuffer();
				maForSubmit.append(screenDesign.getBusinessInitsDefault().get(0).getBusinessLogicCode());
				maForSubmit.append("InputForm");
				data.put("maForRegister", GenerateSourceCodeUtil.normalizedPropertiesJSP(maForSubmit.toString()));
			} else {
				data.put("maForRegister", "null");
			}
			
			if(screenDesign.getBusinessInitsDefault() != null && screenDesign.getBusinessInitsDefault().size() > 0) {
				String blogicCodeForDisplay = screenDesign.getBusinessInitsDefault().get(0).getBusinessLogicCode();
				StringBuffer maForDisplay = new StringBuffer();
				maForDisplay.append(blogicCodeForDisplay);
				maForDisplay.append("OutputForm");
				modelAttributeOut = maForDisplay.toString();
				data.put("blogicCodeForDisplay", blogicCodeForDisplay);
				
				StringBuffer strForDisplay = new StringBuffer(maForDisplay.toString());
				if(strForDisplay.toString() != "") {
					strForDisplay.setCharAt(0, Character.toLowerCase(strForDisplay.charAt(0)));
				}
				data.put("maForDisplay", strForDisplay.toString());
			} else {
				data.put("maForDisplay", "null");
			}
			
			
		} else {
			data.put("maForDisplay", "null");
			data.put("blogicCode", "null");
		}
		
		// Vinh-HV
		ScreenDesignForJSP screenDesignForJSP = mappingView(screenDesign, modelAttributeOut);
		screenDesignForJSP.setModuleId(module.getModuleId());
		TempScreenDesign tempScreenDesign = beanMapper.map(screenDesignForJSP, TempScreenDesign.class);
		
		String glyphicon = "";
		FileOutputStream fileStream = null;
		OutputStreamWriter out = null;
		try {
			List<ScreenItem> itemEvents = new ArrayList<ScreenItem>();
			List<ScreenItem> buttons = new ArrayList<ScreenItem>();

			for (ScreenForm screenForm : tempScreenDesign.getScreenForms()) {
				for (ScreenItem item : tempScreenDesign.getScreenItems()) {
					if (DbDomainConst.LogicDataType.LINK.equals(item.getLogicalDataType()) && !item.getLogicalDataType().equals(null)) {
						// Target screen
						ScreenDesign sd = screenDesignRepository.getScreenDesignByScreenActionId(item.getScreenActionId());
						if (sd != null) {
							if (sd.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.REGISTER)) {
								/*data.put("glyphicon", "glyphicon-plus");*/
								glyphicon = "glyphicon-plus";
							}
							if (sd.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.SEARCH)) {
								/*data.put("glyphicon", "glyphicon-search");*/
								glyphicon = "glyphicon-search";
							}
						}
					}
					item = setParentOfScreenItem(item, listOfOutputBean, listOfInputBean,isRegisterConfirm,lstInputBeanForRegisterConfirm);

					if (item.getParentOutputBeanCode() != null && item.getParentOutputBeanArrayFlag().equals("t")) {
						data.put("parentOutputBean", item.getParentOutputBeanCode());
					}
					
					if(screenDesign.getBusinessProcessesDefault() != null && screenDesign.getBusinessProcessesDefault().size() > 0) {
						item.setElement(getElementJSP(item, screenDesign.getScreenPatternType(), module.getModuleCode(), tempScreenDesign, screenForm, project, modelAttributeOut, listOfOutputBean,listOfInputBean,listAutocompleteInputByScreenId,countButtonSubmit,glyphicon));
					} else {
						item.setElement(getElementHTML(item, screenDesign.getScreenPatternType(), module.getModuleCode(), tempScreenDesign, screenForm, project, modelAttributeOut, listOfOutputBean,listOfInputBean,listAutocompleteInputByScreenId,countButtonSubmit,glyphicon));
					}
					
					item.setElementTemplate(getElementTemplate(item, screenDesign.getScreenPatternType(), module.getModuleCode(), tempScreenDesign, screenForm, project, modelAttributeOut, listOfOutputBean,listAutocompleteInputByScreenId,countButtonSubmit,glyphicon));
					item.setTrTemplate(item.getElementTemplate());
					if (FunctionCommon.isNotEmpty(item.getScreenItemEvents())) {
						itemEvents.add(item);
					}

					if (item.getScreenAction() != null && ScreenActionConst.SUBMIT_METHOD_TYPE_POST.equals(item.getScreenAction().getSubmitMethodType())) {
						buttons.add(item);
					}
				}
			}
				
			List<ScreenArea> screenAreas = new ArrayList<ScreenArea>();
			for (int i = 0; i < tempScreenDesign.getScreenAreas().length; i++) {
				if (FunctionCommon.isNotEmpty(tempScreenDesign.getScreenAreas()[i].getScreenAreaEvents())) {
					screenAreas.add(tempScreenDesign.getScreenAreas()[i]);
				}
			}
			
			for (ScreenItem item : tempScreenDesign.getScreenItems()) {
				if(item.getLogicalDataType() != null && item.getLogicalDataType().equals(DbDomainConst.LogicDataType.FILEUPLOAD)) {
					data.put("enctype", "enctype=\"multipart/form-data\" ");
					break;
				}
			}
			
			//list for onchange event
			List<String> lstStr = new ArrayList<String>();
			for (ScreenItem item : tempScreenDesign.getScreenItems()) {
				for(ScreenItemAutocompleteInput atcInput : listAutocompleteInputByScreenId) {
					if(item.getScreenItemId().equals(atcInput.getScreenItemId()) && atcInput.getInputId() != null) {
						String[] str = atcInput.getScreenItemCode().split("\\.");
						String newStr = str[str.length - 1].toString();
						/*lstStr.add(newStr);*/
						item.setInputOnchange(newStr);
					}
				}
			}
			data.put("lstStr", lstStr);
			
			Map<String, String> projectStyle = new HashMap<String, String>();
			String userName = "";
			Timestamp dateTime = FunctionCommon.getCurrentTime();
			String pageContext = "${pageContext.request.contextPath}";
			projectStyle = screenDesignService.getProjectStyle(project.getProjectId());
			// Check mapping ouputBean
			data.put("projectStyle", projectStyle);
			data.put("listOfOutputBean", listOfOutputBean);
			tempScreenDesign.setScreenCode(GenerateSourceCodeUtil.normalizedURL(tempScreenDesign.getScreenCode()));
			data.put("screenDesignForm", tempScreenDesign);
			
			// Start - Enhance code for gen <c:forEach>
			int indexForForEachInput = 0;
			int indexForForEachOutput = 0;
			String parentInputForForEach = "";
			String parentOutputForForEach = "";
			
			for(ScreenForm form : tempScreenDesign.getScreenForms()) {
				for(ScreenArea area : tempScreenDesign.getScreenAreas()) {
					if(form.getScreenFormId() != null && area.getScreenFormId() != null && form.getScreenFormId().equals(area.getScreenFormId())) {
						if(area.getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
							for(ScreenItem item : area.getItems()) {
								if(item.getScreenAreaId().equals(area.getScreenAreaId())) {
									//Parent output
									if(indexForForEachOutput == 0) {
										if(item.getParentOutputBeanCode() != null && item.getParentOutputBeanArrayFlag().equals("t")) {
											parentOutputForForEach = item.getParentOutputBeanCode();
											indexForForEachOutput++;
										}
									}
									//Parent input
									if(indexForForEachInput == 0) {
										if(item.getParentInputBeanCode() != null && item.getParentInputBeanArrayFlag().equals("t")) {
											parentInputForForEach = item.getParentInputBeanCode();
											indexForForEachInput++;
										}
									}
								}
							}
						}
					}
				}
			}
			data.put("parentInputForForEach", parentInputForForEach);
			data.put("parentOutputForForEach", parentOutputForForEach);
			// End - Enhance code for gen <c:forEach>
			
			data.put("screenCode", tempScreenDesign.getMessageDesign().getMessageCode());
			data.put("screenName", tempScreenDesign.getMessageDesign().getMessageString());
			data.put("pageContext", pageContext);
			
			if(module.getModuleCode() != null) {
				data.put("moduleCode", GenerateSourceCodeUtil.normalizedURL(module.getModuleCode()));
			} else {
				data.put("moduleCode", "");
			}
			data.put("projectCode", GenerateSourceCodeUtil.normalizedURL(project.getProjectCode()));
			data.put("moduleName", module.getModuleName());
			data.put("menuContent", menuContent);
			data.put("screenItemEvents", itemEvents);
			data.put("screenAreaEvents", screenAreas);
			data.put("messageRequire", MessageUtils.getMessage("sc.screendesign.0380"));
			data.put("buttons", buttons);
			data.put("dollar", "$");
			data.put("pageContent", "${page.content}");
			data.put("page", "page");
			data.put("pageSize", "(page.number * page.size)");
			data.put("userName", userName);
			data.put("dateTime", dateTime);
			data.put("pathModuleJavascript", pathModuleJavascript);
			data.put("error", "<form:errors path=\"*\" cssClass=\"alert qp-error\" delimiter=\"<br/>\" element=\"div\" cssStyle=\"\" />");
			data.put("status", "[${status.index}].");
			data.put("tdNotComponentRow", "<td class=\"qp-output-fixlength tableIndex\">${status.count}</td>");
			data.put("tdNotComponentRowSearch", "<td><qp:formatInteger value=\"${(page.number * page.size) + status.count}\" /></td>");
			
			for(ScreenForm form : tempScreenDesign.getScreenForms()) {
				for(ScreenArea area : tempScreenDesign.getScreenAreas()) {
					if(form.getScreenFormId().equals(area.getScreenFormId())) {
						for(ScreenItem item : area.getItems()) {
							if(item.getScreenAreaId().equals(area.getScreenAreaId())) {
								if(area.getTblComponentRow() != null && area.getTblComponentRow() > 1) {
									data.put("tdComponentRow", "<td rowspan=\""+area.getTblComponentRow()+"\" class=\"qp-output-fixlength tableIndex\">${status.count}</td>");
									data.put("tdComponentRowSearch", "<td rowspan=\""+area.getTblComponentRow()+"\"><qp:formatInteger v"
											+ "alue=\"${(page.number * page.size) + status.count}\" /></td>");
								}
							}
						}
					}
				}
			}
			
			
			List<ScreenForm> listOfScreenForm = new ArrayList<ScreenForm>();
			for (ScreenForm screenForm : tempScreenDesign.getScreenForms()) {
				data.put("formCode", screenForm.getFormCode());
				listOfScreenForm.add(screenForm);
			}

			List<BusinessDesign> listOfBDesign = businessDesignRepository.getListBLogicByScreenFormId(listOfScreenForm);
			List<NavigatorComponent> listNavigateComponent = navigationComponentRepository.findNavigationComponentByLstBusinessLogic(listOfBDesign,languageId,project.getProjectId());
			
			//Only use for confirm screen
			if(tempScreenDesign.getConfirmationType() != null && tempScreenDesign.getConfirmationType().equals(ScreenDesignConst.ConfirmType.SCREEN)) {
					for(NavigatorComponent naviCompo : listNavigateComponent) {
						/*if((naviCompo.getNavigatorToScreenPatternType().equals(DbDomainConst.ScreenPatternType.SEARCH) || naviCompo.getNavigatorToScreenPatternType().equals(DbDomainConst.ScreenPatternType.VIEW))) {*/
								for(BusinessDesign bDesign : listOfBDesign) {
									if(bDesign.getBusinessLogicId().equals(naviCompo.getNavigatorFromId())) {
										if(BusinessDesignConst.REQUEST_METHOD_PROCESSING.equals(bDesign.getRequestMethod()) && BusinessDesignConst.DESIGN_MODE_AUTO.equals(bDesign.getDesignMode())) {
											data.put("blogicCode", GenerateSourceCodeUtil.normalizedURL(bDesign.getBusinessLogicCode()));
											break;
										}
									}
								}
							}
						/*}*/
			} else {
				if (CollectionUtils.isNotEmpty(listOfBDesign)) {
					for (BusinessDesign businessDesign : listOfBDesign) {
						if(BusinessDesignConst.REQUEST_METHOD_PROCESSING.equals(businessDesign.getRequestMethod()) && BusinessDesignConst.DESIGN_MODE_AUTO.equals(businessDesign.getDesignMode())) {
							data.put("blogicCode", GenerateSourceCodeUtil.normalizedURL(businessDesign.getBusinessLogicCode()));
							break;
						}
					}
				}
			}
			
			LanguageDesign lang = new LanguageDesign();
			lang.setLanguageId(languageId);
			List<ScreenItem> screenItemHiddensSingle = new ArrayList<ScreenItem>();
			List<ScreenItem> screenItemHiddensList = new ArrayList<ScreenItem>();
			List<ScreenItem> screenItemHiddens = screenDesignRepository.getScreenHiddenItemByScreenId(tempScreenDesign.getScreenId(), lang, project.getProjectId());
			for(ScreenItem itemHidden : screenItemHiddens) {
				itemHidden = setParentOfScreenItem(itemHidden, listOfOutputBean, listOfInputBean, null, null);
				if(itemHidden.getOutputBeanCode() != null) {
					itemHidden.setOutputBeanCode(GenerateSourceCodeUtil.normalizedPropertiesJSP(itemHidden.getOutputBeanCode()));
				}
				if(itemHidden.getParentOutputBeanCode() != null) {
					itemHidden.setParentOutputBeanCode(GenerateSourceCodeUtil.normalizedPropertiesJSP(itemHidden.getParentOutputBeanCode()));
				}
				if(itemHidden.getInputBeanCode() != null) {
					itemHidden.setInputBeanCode(GenerateSourceCodeUtil.normalizedPropertiesJSP(itemHidden.getInputBeanCode()));
				}
				if(itemHidden.getParentInputBeanCode() != null) {
					itemHidden.setParentInputBeanCode(GenerateSourceCodeUtil.normalizedPropertiesJSP(itemHidden.getParentInputBeanCode()));
				}
				
				if(itemHidden.getScreenArea().getAreaType() != null && itemHidden.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
					screenItemHiddensList.add(itemHidden);
				}
				if(itemHidden.getScreenArea().getAreaType() != null && itemHidden.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.SINGLE_ENTITY)) {
					screenItemHiddensSingle.add(itemHidden);
				}
				
			}
			// Add hidden attribute for area
			data.put("screenItemHiddensList", screenItemHiddensList);
			data.put("screenItemHiddensSingle", screenItemHiddensSingle);
			
			data.put("processJS", "process" +tempScreenDesign.getScreenCode()+  ".js");
			data.put("initJS", "init" +tempScreenDesign.getScreenCode()+  ".js");
			// Get url main action for class breadcrumb
			MenuDesign menuDesign = menuDesignService.getMenuDesignForGenerateJSP(project.getProjectId(), currentLanguageId);
			if (menuDesign != null) {
				data.put("pageContext", pageContext);
				if (menuDesignNavigateJSP != null) {
					if (StringUtils.isNotBlank(menuDesignNavigateJSP.getUrlMainAction())) {
						data.put("urlMainAction", menuDesign.getModuleCode() + "/" + GenerateSourceCodeUtil.normalizedURL(menuDesignNavigateJSP.getUrlMainAction()));
					} else {
						data.put("urlMainAction", "home");
					}
				} else {
					data.put("urlMainAction", "home");
				}
			} else {
				data.put("urlMainAction", "home");
			}
			/*Template tempScreenHeaderJSP = this.getTemplate(TEMPLATE_SCREEN_HEADER_JSP);
			Template tempScreenBodyJSP = this.getTemplate(TEMPLATE_SCREEN_BODY_JSP);
			Template tempScreenFooterJSP = this.getTemplate(TEMPLATE_SCREEN_FOOTER_JSP);*/
			/*fileStream = new FileOutputStream(new File(outputDir + tempScreenDesign.getScreenCode() + ".jsp"));
			out = new OutputStreamWriter(fileStream, ENCODE_UTF8);*/
			
			this.process(data, TEMPLATE_SCREEN_HEADER_JSP, outputDir + GenerateSourceCodeUtil.normalizedURL(tempScreenDesign.getScreenCode())  + ".jsp");
			this.process(data, TEMPLATE_SCREEN_BODY_JSP, outputDir + GenerateSourceCodeUtil.normalizedURL(tempScreenDesign.getScreenCode()) + ".jsp",true);
			this.process(data, TEMPLATE_SCREEN_FOOTER_JSP, outputDir + GenerateSourceCodeUtil.normalizedURL(tempScreenDesign.getScreenCode())+ ".jsp",true);
			
			/*tempScreenHeaderJSP.process(data, out);
			tempScreenBodyJSP.process(data, out);
			tempScreenFooterJSP.process(data, out);
			out.flush();*/
		} catch (Exception e) {
            GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(fileStream);
		}
		return tempScreenDesign;
	}

	private void generateHeaderJSP(Template tempHeaderJSP, GenerateSourceCode generateSourceCode, Map<String, String> projectStyle, List<ProjectItem> listLogoLeft, List<ProjectItem> listLogoRight, List<ProjectItem> listHeaderLeft, List<ProjectItem> listHeaderRight, String userName, Timestamp dateTime, String pageContext, String menuContent, MenuDesign menuDesign, MenuDesign menuDesignNavigateJSP) throws TemplateException, IOException {
		String source = "";
		String urlHomePage = "";
		source = generateSourceCode.getSourcePathWeb() + path;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("projectStyle", projectStyle);
		data.put("listLogoLeft", listLogoLeft);
		data.put("listLogoRight", listLogoRight);
		data.put("listHeaderLeft", listHeaderLeft);
		data.put("listHeaderRight", listHeaderRight);
		data.put("dateTime", dateTime);
		if (menuDesignNavigateJSP != null) {
			if (StringUtils.isNotBlank(menuDesignNavigateJSP.getUrlHomePage())) {
				urlHomePage = GenerateSourceCodeUtil.normalizedURL(menuDesign.getModuleCode() + "/" + menuDesignNavigateJSP.getUrlHomePage());
			} else {
				urlHomePage = "home";
			}
		} else {
			urlHomePage = "home";
		}
		
		data.put("urlHome", urlHomePage);
		data.put("pageContext", pageContext);
		data.put("menuContent", menuContent);
		data.put("menuDesign", menuDesign);
		FileOutputStream fileStream = null;
		OutputStreamWriter out = null;
		try {
			/*fileStream = new FileOutputStream(new File(source + "header.jsp"));
			out = new OutputStreamWriter(fileStream, ENCODE_UTF8);
			tempHeaderJSP.process(data, out);
			out.flush();*/
			
			this.process(data, TEMPLATE_HEADER_JSP, source + "header.jsp");
			
		} catch (Exception e) {
            GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		}
		finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(fileStream);
		}
	}
	
	private void generateTemplateJSP(Template tempTemplateJSP, GenerateSourceCode generateSourceCode,String pageContext, String menuContent, MenuDesign menuDesign, MenuDesign menuDesignNavigateJSP, Project project) throws TemplateException, IOException {
		String source = "";
		String urlHomePage = "";
		source = generateSourceCode.getSourcePathWeb() + path;
		Map<String, Object> data = new HashMap<String, Object>();
		if (menuDesignNavigateJSP != null) {
			if (StringUtils.isNotBlank(menuDesignNavigateJSP.getUrlMainAction())) {
				data.put("urlHomePage", GenerateSourceCodeUtil.normalizedURL(menuDesign.getModuleCode() + "/" + menuDesignNavigateJSP.getUrlHomePage()));
			} else {
				data.put("urlHomePage", "home");
			}
		} else {
			data.put("urlHomePage", "home");
		}
		data.put("CURRENT_PROJECT_ID", "${f:h(sessionScope.CURRENT_PROJECT.projectId)}");
		data.put("CURRENT_LANGUAGE_DESIGN_ID", "${f:h(sessionScope.CURRENT_PROJECT.defaultLanguageId)}");
		data.put("INTERVAL_RELOAD", "${f:h(sessionScope.ACCOUNT_PROFILE.intervalReload)}");
		data.put("SQL_CODE_MAX_VAL", "${f:h(sessionScope.CURRENT_PROJECT.codeMaxVal)}");
		data.put("urlHome", urlHomePage);
		data.put("pageContext", pageContext);
		data.put("menuContent", menuContent);
		data.put("menuDesign", menuDesign);
		data.put("project", project);
		FileOutputStream fileStream = null;
		OutputStreamWriter out = null;
		try {
			/*fileStream = new FileOutputStream(new File(source + "template.jsp"));
			out = new OutputStreamWriter(fileStream, ENCODE_UTF8);
			tempTemplateJSP.process(data, out);
			out.flush();*/
			
			this.process(data, TEMPLATE_LAYOUT_TEMPLATE_JSP, source + "template.jsp");
			
			
		} catch (Exception e) {
            GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		}
		finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(fileStream);
		}
	}

	private void generateFooterJSP(Template tempFooterJSP, GenerateSourceCode generateSourceCode, Map<String, String> projectStyle, List<ProjectItem> listFooterLeft, List<ProjectItem> listFooterRight, String userName, Timestamp dateTime, String pageContext, String menuContent) throws TemplateException, IOException {
		String source = "";
		source = generateSourceCode.getSourcePathWeb() + path;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("projectStyle", projectStyle);
		data.put("listFooterLeft", listFooterLeft);
		data.put("listFooterRight", listFooterRight);
		data.put("dateTime", dateTime);
		data.put("pageContext", pageContext);
		data.put("menuContent", menuContent);
		FileOutputStream fileStream = null;
		OutputStreamWriter out = null;
		try {
			/*fileStream = new FileOutputStream(new File(source + "footer.jsp"));
			out = new OutputStreamWriter(fileStream, ENCODE_UTF8);
			tempFooterJSP.process(data, out);
			out.flush();*/
			
			this.process(data, TEMPLATE_FOOTER_JSP, source + "footer.jsp");
			
		} catch (Exception e) {
            GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		}
		finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(fileStream);
		}
	}
	
	@SuppressWarnings("unused")
	private String getElementJSP(ScreenItem screenItem, int screenPatternType, String moduleCode, TempScreenDesign tempScreenDesign, ScreenForm screenForm, Project project, String modelAttributeOut, 
									List<OutputBean> listOfOutputBean,List<InputBean> listOfInputBean, List<ScreenItemAutocompleteInput> listAutocompleteInputByScreenId, int countButtonSubmit, String glyphicon) {
		
		int index = 1;
		
		String atcInputStr = "";
		/*for(ScreenItemAutocompleteInput atcInput : listAutocompleteInputByScreenId) {
			if(screenItem.getScreenItemId().equals(atcInput.getScreenItemId()) && atcInput.getInputId() != null) {
				String strIndex = String.format("%02d", index);
				String[] str = atcInput.getScreenItemCode().split("\\.");
				String newStr = str[str.length - 1].toString();
				atcInputStr += " arg"+strIndex+"=\"${"+modelAttributeOut+"."+newStr+"}\"";
				index++;
			}
		}*/
		
		String newPackageName = project.getPackageName().replaceAll("\\.", "_") + "_";
		
		String pathFrom = "";
		String pathTo = "";
		String value = "";
		String path = "";
		String valueForEach = "";
		
		String valueNew = "";
		String pathNew = "";
		String valueElement = "";
		
		String parentOutputBean = GenerateSourceCodeUtil.normalizedPropertiesJSP((screenItem.getParentOutputBeanCode() != null) ? (screenItem.getParentOutputBeanCode()+".") : (""));
		String outputBean = GenerateSourceCodeUtil.normalizedPropertiesJSP((screenItem.getOutputBeanCode() != null) ? (screenItem.getOutputBeanCode()) : (""));
		
		String parentInputBean = GenerateSourceCodeUtil.normalizedPropertiesJSP((screenItem.getParentInputBeanCode() != null) ? (screenItem.getParentInputBeanCode() + ".") : (""));
		String inputBean = GenerateSourceCodeUtil.normalizedPropertiesJSP((screenItem.getInputBeanCode() != null) ? (screenItem.getInputBeanCode()) : (""));

		if (screenItem.getScreenArea() != null &&  screenItem.getScreenArea().getAreaType() != null && screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
			if (StringUtils.isNotBlank(outputBean)) {
				value = " value=" + '"' + "${" + "item." + outputBean + "}" + '"';
				valueNew = "${" + "item." + outputBean + "}";
				valueForEach = "item." + outputBean;
				valueElement = outputBean;
			} else {
				/*value = " value=" + '"' + "${" + "item." + outputBean + "}" + '"';*/
				valueForEach = "item";
			}
		}  else {
			if (StringUtils.isNotBlank(parentOutputBean) && StringUtils.isNotBlank(outputBean)) {
				value = " value=" + '"' + "${" + modelAttributeOut + "." + parentOutputBean + outputBean + "}" + '"';
				valueNew = "${" + modelAttributeOut + "." + parentOutputBean + outputBean + "}";
				valueForEach = modelAttributeOut + "." + parentOutputBean + outputBean;
				valueElement = parentOutputBean + outputBean;
			}
		}

		if (!StringUtils.isNotBlank(parentOutputBean) && StringUtils.isNotBlank(outputBean)) {
			value = " value=" + '"' + "${" + modelAttributeOut + "." + outputBean + "}" + '"';
			valueNew = "${" + modelAttributeOut + "." + outputBean + "}";
			valueForEach = modelAttributeOut + "." + outputBean;
			valueElement = outputBean;
		}
		if (!StringUtils.isNotBlank(parentOutputBean) && !StringUtils.isNotBlank(outputBean) || DbDomainConst.ScreenPatternType.REGISTER.equals(tempScreenDesign.getScreenPatternType())) {
			value = "";
		}
		
		String nameElement = "";
		
		if (StringUtils.isNotBlank(parentInputBean) && StringUtils.isNotBlank(inputBean)) {
			path = "path=" + '"' + "" + parentInputBean + inputBean + "" + '"';
			pathNew = "" + parentInputBean + inputBean + "";
			nameElement = parentInputBean + inputBean;
		}
		if (!StringUtils.isNotBlank(parentInputBean) && StringUtils.isNotBlank(inputBean)) {
			path = "path=" + '"' + "" + inputBean + "" + '"';
			pathNew = "" + inputBean + "";
			nameElement = inputBean;
		}
		
		// Search From-To 
		
		if(screenItem.getInputBeans().size() > 1) {
			for(InputBean input : listOfInputBean) {
				for(InputBean in : screenItem.getInputBeans()) {
					if(in.getInputBeanCode().equals(input.getInputBeanCode())) {
						if (StringUtils.isNotBlank(parentInputBean) && StringUtils.isNotBlank(inputBean)) {
							if(in.getInputBeanCode().endsWith("From")) {
								pathFrom = "path=" + '"' + "" + parentInputBean + in.getInputBeanCode() + "" + '"';
							}
							if(in.getInputBeanCode().endsWith("To")) {
								pathTo = "path=" + '"' + "" + parentInputBean + in.getInputBeanCode() + "" + '"';
							}
						}
						if (!StringUtils.isNotBlank(parentInputBean) && StringUtils.isNotBlank(inputBean)) {
							if(in.getInputBeanCode().endsWith("From")) {
								pathFrom = "path=" + '"' + "" + in.getInputBeanCode() + "" + '"';
							}
							if(in.getInputBeanCode().endsWith("To")) {
								pathTo = "path=" + '"' + "" + in.getInputBeanCode() + "" + '"';
							}
						}
					}
				}
			}
		}
		
		if (!StringUtils.isNotBlank(parentInputBean) && !StringUtils.isNotBlank(inputBean)) {
			path = "";
			pathNew = "";
		}
		
		JsonFactory json = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(json);
		TypeReference<ScreenDesignOutput> typeRef = new TypeReference<ScreenDesignOutput>() {
		};

		ScreenDesignOutput item = new ScreenDesignOutput();
		String jsonString = "";
		if(screenItem.getValue() != null) {
			jsonString = FunctionCommon.getStringJson(screenItem.getValue());
		}
		
		try {
			item = mapper.readValue(jsonString, typeRef);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (StringUtils.isEmpty(item.getDatatype())) {
			return "";
		}
		if(item.getLocalCodelist() != null) {
			screenItem.setLocalCodelist(item.getLocalCodelist());
		}
		
		int indexOfParams = 0;
		String requestParam = "";
		String preRequest = "";
		if (screenItem.getScreenActionId() != null) {
			List<ScreenActionParam> screenActionParamButton = screenActionParamRepository.findAllActionParamByScreenActionId(screenItem.getScreenActionId(),project.getProjectId());
			List<ScreenActionParam> params = new ArrayList<ScreenActionParam>();
			if (FunctionCommon.isNotEmpty(screenActionParamButton)) {
				for (ScreenActionParam screenActionParam : screenActionParamButton) {
					if (screenActionParam.getScreenItemCode() != null) {
						String[] str = screenActionParam.getScreenItemCode().split("\\.");
						ScreenActionParam screenactionparam = new ScreenActionParam();

						if (str != null && str.length > 0) {
							screenactionparam.setScreenItemCode(str[str.length - 1]);
							screenactionparam.setAreaCodeOfItem(str[str.length - 2]);
							screenactionparam.setFormCodeOfItem(str[str.length - 3]);
						}
						
						screenactionparam.setAreaIdOfItem(screenActionParam.getAreaIdOfItem());
						screenactionparam.setAreaTypeOfItem(screenActionParam.getAreaTypeOfItem());
						screenactionparam.setActionParamCode(screenActionParam.getActionParamCode());
						screenactionparam.setItemPhysicalDataType(screenActionParam.getItemPhysicalDataType());
						screenactionparam.setItemId(screenActionParam.getItemId());
						params.add(screenactionparam);
					}
				}
			}
			List<ScreenActionParam> lstOutputByItemId = new ArrayList<ScreenActionParam>();
			List<Long> arrLong = new ArrayList<Long>();
			for(ScreenActionParam arr : params) {
				arrLong.add(arr.getItemId());
			}
			if(params.size() > 0) {
				lstOutputByItemId = screenActionParamRepository.getOutputByItemId(params);
			}
			
			for(ScreenActionParam itemHaveOutput : lstOutputByItemId) {
				for(ScreenActionParam itemDontOutput : params) {
					if(itemHaveOutput.getItemId() != null && itemDontOutput.getItemId() != null && itemHaveOutput.getItemId().equals(itemDontOutput.getItemId())) {
						itemDontOutput.setOutputIdOfItem(itemHaveOutput.getOutputIdOfItem());
						itemDontOutput.setOutputPhysicalDataTypeOfItem(itemHaveOutput.getOutputPhysicalDataTypeOfItem());
					}
				}
			}
			
			for (ScreenActionParam sap : params) {
				if(indexOfParams == 0) {
					preRequest = "?";
				} else {
					preRequest = "&";
				}
				if (sap.getActionParamCode() != null && sap.getScreenItemCode() != null) {
					
					/*String requestCode = sap.getScreenItemCode();*/
					/*if (screenItem.getScreenAction() != null && screenItem.getScreenAction().getActionType() != null && screenItem.getScreenAction().getActionType().equals(1)) {*/
						// get input code blogic by id
					String requestCode = "";
					if (!StringUtils.isEmpty(sap.getActionParamCode())) {
						try {
							InputBean input =  businessDesignRepository.findInputBeanById(Long.parseLong(sap.getActionParamCode()), currentLanguageId, project.getProjectId());
							requestCode = input.getInputBeanCode();
						} catch(Exception ex) {
							
						}
					}
					/*} */
					
					if (screenItem.getScreenArea() != null && DbDomainConst.AreaType.LIST_ENTITIES.equals(screenItem.getScreenArea().getAreaType())
						 && sap.getAreaIdOfItem() != null && sap.getAreaIdOfItem().equals(screenItem.getScreenArea().getScreenAreaId())) {
						OutputBean outputBeanTmp = new OutputBean();
						for (OutputBean out : listOfOutputBean) {
							if (out.getItemCodeMapping().equals(sap.getScreenItemCode()) && out.getAreaCodeMapping().equals(sap.getAreaCodeOfItem()) && out.getFormCodeMapping().equals(sap.getFormCodeOfItem())) {
								outputBeanTmp = out;
								break;
							}
						}
						if(outputBeanTmp != null) {
							String outputTmp = (outputBeanTmp.getOutputBeanCode() != null) ? (outputBeanTmp.getOutputBeanCode()) : ("");
							String qpFormat = "";
							if(sap.getOutputPhysicalDataTypeOfItem() != null && sap.getOutputPhysicalDataTypeOfItem().equals(DbDomainConst.BaseType.DATE_BASETYPE)) {
								qpFormat = "=<qp:formatDate value=\"${item." + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else if(sap.getOutputPhysicalDataTypeOfItem() != null && sap.getOutputPhysicalDataTypeOfItem().equals(DbDomainConst.BaseType.TIME_BASETYPE)) {
								qpFormat = "=<qp:formatTime value=\"${item." + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else if(sap.getOutputPhysicalDataTypeOfItem() != null && sap.getOutputPhysicalDataTypeOfItem().equals(DbDomainConst.BaseType.DATETIME_BASETYPE)) {
								qpFormat = "=<qp:formatDateTime value=\"${item." + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else {
								qpFormat = "=${item." + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}";
							}
							requestParam += preRequest + requestCode + qpFormat + "";
							indexOfParams++;
						}
					} else {
						OutputBean  outputBeanTmp = new OutputBean();
						for (OutputBean out : listOfOutputBean) {
							if (out.getItemCodeMapping().equals(sap.getScreenItemCode())) {
								outputBeanTmp = out;
								break;
							}
						}
						if(outputBeanTmp != null) {
							String parentOutputBeanTmp = (outputBeanTmp.getParentOutputBeanCode() != null) ? (outputBeanTmp.getParentOutputBeanCode()+".") : ("");
							String outputTmp = (outputBeanTmp.getOutputBeanCode() != null) ? (outputBeanTmp.getOutputBeanCode()) : ("");
							
							String qpFormat = "";
							if(sap.getOutputPhysicalDataTypeOfItem() != null && sap.getOutputPhysicalDataTypeOfItem().equals(DbDomainConst.BaseType.DATE_BASETYPE)) {
								qpFormat = "=<qp:formatDate value=\"${"+modelAttributeOut+ "." + GenerateSourceCodeUtil.normalizedPropertiesJSP(parentOutputBeanTmp) + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else if(sap.getOutputPhysicalDataTypeOfItem() != null && sap.getOutputPhysicalDataTypeOfItem().equals(DbDomainConst.BaseType.TIME_BASETYPE)) {
								qpFormat = "=<qp:formatTime value=\"${"+modelAttributeOut+ "." + GenerateSourceCodeUtil.normalizedPropertiesJSP(parentOutputBeanTmp) + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else if(sap.getOutputPhysicalDataTypeOfItem() != null && sap.getOutputPhysicalDataTypeOfItem().equals(DbDomainConst.BaseType.DATETIME_BASETYPE)) {
								qpFormat = "=<qp:formatDateTime value=\"${"+modelAttributeOut+ "." + GenerateSourceCodeUtil.normalizedPropertiesJSP(parentOutputBeanTmp) + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else {
								qpFormat = "=${"+modelAttributeOut+ "." + GenerateSourceCodeUtil.normalizedPropertiesJSP(parentOutputBeanTmp) + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}";
							}
							requestParam += preRequest + requestCode + qpFormat + "";
							indexOfParams++;
						}
					
					}
				}
			}
		}
		
		//Get default value
		String defaultValue = "";
		if(screenItem.getDefaultValue() != null && StringUtils.isNotBlank(screenItem.getDefaultValue())) {
			defaultValue = screenItem.getDefaultValue();
		} else {
			defaultValue = "null";
		}
		if(tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.REGISTER)) {
			if(defaultValue != null && StringUtils.isNotBlank(defaultValue) && defaultValue != "null") {
				value = "value=\""+defaultValue+"\" ";
				valueNew = defaultValue;
			} else {
				value = "";
			}
		}
		
		
		String enableConfirm = "";
		String messageId = "";
		if(screenItem.getEnableConfirm() != null && ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM.equals(screenItem.getEnableConfirm()) && screenItem.getMessageConfirm() != null) {
			enableConfirm = "qp-link-dialog-confirm";
			messageId = "messageId="+"\""+ screenItem.getMessageConfirm().getMessageCode()+"\"";
		}
		
		// Set for data source type
		
		/*String valueSelect = "";
		if(valueForEach != null && StringUtils.isNotBlank(valueForEach)) {
			valueSelect = valueForEach;
		} else {
			valueSelect = "null";
		}*/
		String optionSubmit = "";
		String optionDisplay = "";
		String dataSource = "";
		String parentOutputBeanSelect = "";
		String outputBeanSelect = "";
		String blogicCodeHasDataSource = "";
		for(OutputBean output : listOfOutputBean) {
			if(output.getScreenItemId().equals(Long.parseLong(item.getScreenItemId()))) {
				if(output.getMappingType() != null && output.getMappingType().equals(MAPPING_TYPE_OPTION_SUBMIT)) {
					optionSubmit = output.getOutputBeanCode();
				} else if(output.getMappingType() != null && output.getMappingType().equals(MAPPING_TYPE_OPTION_DISPLAY)) {
					optionDisplay = output.getOutputBeanCode();
				} else if(output.getMappingType() != null && output.getMappingType().equals(MAPPING_TYPE_DATA_SOURCE)) {
					blogicCodeHasDataSource = output.getBusinessLogicCode();
					parentOutputBeanSelect = (output.getParentOutputBeanCode() != null) ? (output.getParentOutputBeanCode()+".") : ("");
					outputBeanSelect = (output.getOutputBeanCode() != null) ? (output.getOutputBeanCode()) : ("");
					dataSource = parentOutputBeanSelect + outputBeanSelect;
				} 
			}
		}
		
		String maxLength = "";
		if(item.getMaxlength() != null && item.getMaxlength() != "" && Integer.parseInt(item.getMaxlength()) > 0) {
			maxLength = "maxlength=\"" + item.getMaxlength() + "\"";
		}
		
		//Set mapping type for screenItem
		String element = "";
		int dataType = Integer.parseInt(item.getDatatype());
		switch (dataType) {
		case 0:
			String mustMatch = "";
			if(screenItem.getAllowAnyInput() != null && screenItem.getAllowAnyInput().equals(0)) {
				mustMatch = "mustMatch=\"false\"";
			} else {
				mustMatch = "mustMatch=\"true\"";
			}
			
			String displayValueForAu = "";
			String valueForAu = "";
			for(OutputBean out : listOfOutputBean) {
				if(out.getScreenItemId() != null && screenItem.getScreenItemId() != null) {
					// Display = displayValue
					if(out.getScreenItemId().equals(screenItem.getScreenItemId()) && out.getMappingType() != null && DbDomainConst.MappingType.DISPLAY.equals(out.getMappingType())) {
						String parent = (out.getParentOutputBeanCode() != null) ? (out.getParentOutputBeanCode()) : ("");
						String output = (out.getOutputBeanCode() != null) ? (out.getOutputBeanCode()) : ("");
						if(output != null && output != "") {
							parent += ".";
						}
						displayValueForAu = modelAttributeOut + "." + parent + output;
						if(output == null || output == "") {
							displayValueForAu = "";
						}
					}
					// Select = Submit = value
					if(out.getScreenItemId().equals(screenItem.getScreenItemId()) && out.getMappingType() != null && DbDomainConst.MappingType.SELECT.equals(out.getMappingType())) {
						String parent = (out.getParentOutputBeanCode() != null) ? (out.getParentOutputBeanCode()) : ("");
						String output = (out.getOutputBeanCode() != null) ? (out.getOutputBeanCode()) : ("");
						if(parent != null && parent != "") {
							if(out.getParentOutputBeanArrayFlag() != null && out.getParentOutputBeanArrayFlag().equals("t")) {
								parent += "[status.index].";
							} else {
								parent += ".";
							}
							valueForAu = modelAttributeOut + "." + parent + output;
						} else {
							valueForAu = modelAttributeOut + "." + output;
						}
						if(output == null || output == "") {
							valueForAu = "";
						}
					}
				}
			}
			
			String submitColumn = "";
			
			AutocompleteDesign autocompleteDesign = autocompleteDesignRepository.getAutocompleteDesignByScreenItem(screenItem.getAutocompleteId());
			
			if(autocompleteDesign != null) {
				String[] displayColumn = autocompleteDesign.getDisplayColumnFlag().split(",");
			
				submitColumn = autocompleteDesign.getSubmitColumn();
				submitColumn = "output" + String.format("%2s", submitColumn).replace(' ', '0');
				
				String display = "";
				for(int i = 2, j = 1 ; i < displayColumn.length ; i++, j++) {
					if (displayColumn[i].equals("1")) {
						display += "output" + String.format("%2s", j).replace(' ', '0');
						
						if (i < displayColumn.length - 1) {
							display += ",";
						}
					}
				}
				
				display = display.substring(0, display.length() - 1);
				
				if (DbDomainConst.ScreenPatternType.MODIFY.equals(tempScreenDesign.getScreenPatternType())) {
					if(screenItem.getAllowAnyInput() != null && screenItem.getAllowAnyInput().equals("1")) {
						element = "<qp:autocomplete displayValue=\""+displayValueForAu+"\" value=\"${"+valueForAu+"}\" selectSqlId=\"QP_ATC_"+autocompleteDesign.getAutocompleteId()+"\" name=\""+ nameElement+"\" optionValue=\""+submitColumn+"\" optionLabel=\""+display+"\" "+mustMatch+" "+atcInputStr+"></qp:autocomplete>";
					} else {
						element = "<qp:autocomplete displayValue=\""+displayValueForAu+"\" value=\"${"+valueForAu+"}\" selectSqlId=\"QP_ATC_"+autocompleteDesign.getAutocompleteId()+"\" name=\""+ nameElement+"\" optionValue=\""+submitColumn+"\" optionLabel=\""+display+"\" "+mustMatch+" "+atcInputStr+"></qp:autocomplete>";
					}
				} else {
					if(screenItem.getAllowAnyInput() != null && screenItem.getAllowAnyInput().equals("1")) {
						element = "<qp:autocomplete selectSqlId=\"QP_ATC_"+autocompleteDesign.getAutocompleteId()+"\" name=\""+ nameElement+"\" optionValue=\""+submitColumn+"\" optionLabel=\""+display+"\" "+mustMatch+" "+atcInputStr+"></qp:autocomplete>";
					} else {
						element = "<qp:autocomplete selectSqlId=\"QP_ATC_"+autocompleteDesign.getAutocompleteId()+"\" name=\""+ nameElement+"\" optionValue=\""+submitColumn+"\" optionLabel=\""+display+"\" "+mustMatch+" "+atcInputStr+"></qp:autocomplete>";
					}
				}
			}
			else {
				element = "<div class=\"input-group\" style=\"width:100% "+ item.getStyle() +"\"><input type=\"text\" class=\"form-control\" ";
				element += " name=\"" + item.getColumnname() + "\" "+maxLength+" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" "+mustMatch+" minlength=\"0\" placeholder=\"\" autocomplete=\"off\">" + "	<input type=\"hidden\" value=\"\">" + "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" + "		<span class=\"caret\"></span> " + "	</span>" + "</div>";
			}
			break;
		case 1:
			
			if(item.getEnablePassword() != null && item.getEnablePassword().equals("1")) {
				element = "<form:input type=\"password\" style=\"width:100%; " + item.getStyle() + "\" cssClass=\"form-control qp-input-text\" " + path + " "+maxLength+" />";
			} else {
				element = "<form:input type=\"text\" style=\"width:100%; " + item.getStyle() + "\" cssClass=\"form-control qp-input-text\" " + path + " "+maxLength+" />";
			}
			
			break;
		case 15:
		case 16:
		case 18:
			element = "<form:input style=\"width:100%; " + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-text\" " + path + " "+maxLength+"/>";
			break;
		case 21:
			//SQL Builder
			if(item.getDatasourcetype().equals("1") || item.getDatasourcetype().equals("")) {
				if(screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
					element += "<label style=\"" + item.getStyle() + "\">${item."+valueElement+"}</label>";
				} else {
					if (StringUtils.isNotBlank(parentOutputBean) && StringUtils.isNotBlank(outputBean)) {
						valueElement = parentOutputBean + outputBean;
					}
					element += "<label style=\"" + item.getStyle() + "\">${"+modelAttributeOut+"."+valueElement+"}</label>";
				}
			}
			//Codelist
			else if(item.getDatasourcetype().equals("2")){
				StringBuilder codelistCodeNew = new StringBuilder();
				if (item.getLocalCodelist().equals(String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST))) {
					CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(codelist.getCodeListCode());
				}
				if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(screenItem.getScreenDesignCodeCodeListId() + "_" + screenItem.getScreenItemIdCodeListId());
				}
				
				String lastCodelist = "CL_" + (codelistCodeNew.toString().toUpperCase());
				
				element += "\n<c:if test=\"${not empty "+lastCodelist+".get("+valueForEach+".toString())}\" >";
				element += "\n\t<label style=\"" + item.getStyle() + "\"><qp:message code=\"${"+lastCodelist+".get("+valueForEach+".toString())}\"/></label>";
				element += "\n</c:if>";
				element += "\n<c:if test=\"${empty "+lastCodelist+".get("+valueForEach+".toString())}\" >";
				element += "\n\t<c:set var=\"split\" value=\"\" />";
				if(screenItem.getScreenArea() != null && screenItem.getScreenArea().getAreaType() != null && DbDomainConst.AreaType.LIST_ENTITIES.equals(screenItem.getScreenArea().getAreaType())) {
					element += "\n\t<c:if test=\"${fn:contains(item."+outputBean+",';')}\">";
				} else {
					element += "\n\t<c:if test=\"${fn:contains("+valueForEach+",';')}\">";
				}
				element += "\n\t\t<c:set var=\"split\" value=\";\" />";
				element += "\n\t</c:if>";
				element += "\n\t\t<c:forTokens items=\"${"+valueForEach+"}\" delims=\";\" var=\"itemDelim\">";
				element += "\n\t\t\t<label style=\"" + item.getStyle() + "\"><qp:message code=\"${"+lastCodelist+".get(itemDelim.toString())}\"/>${split}</label>";
				element += "\n\t\t</c:forTokens>";
				element += "\n</c:if>";
			}
			
			break;
		case 2:
			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<form:input style=\"" + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-from qp-input-integer pull-left\"  " + pathFrom + "  "+maxLength+" />" + "" + "<div class=\"qp-separator-from-to\">~</div>" + "<form:input type=\"text\" cssClass=\"form-control qp-input-to qp-input-integer pull-right\"";
					element += " " + pathTo + " "+maxLength+" />" + "</div>";
				} else {
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<form:input style=\"" + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-integer pull-left\"    " + pathFrom + " "+maxLength+" />";
				}
			} else {
				element = "<form:input style=\"" + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-integer\"  " + path + " "+maxLength+" />";
			}
			break;
		case 3:
			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<form:input style=\"" + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-from qp-input-float pull-left\"  " + pathFrom + " "+maxLength+" />" + "" + "<div class=\"qp-separator-from-to\">~</div>" + "<form:input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" cssClass=\"form-control qp-input-to qp-input-float pull-right\"";
					element += "  " + pathTo + " "+maxLength+" />" + "</div>";
				} else {
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<form:input style=\"" + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-float pull-left\"  " + pathFrom + " "+maxLength+"/>";
				}
			} else {
				element = "<form:input style=\"" + item.getStyle() + "\" type=\"text\"  cssClass=\"form-control qp-input-float\"  " + path + " "+maxLength+"/>";
			}
			break;
		case 8:
			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<form:input style=\"" + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-from qp-input-currency pull-left\"  " + pathFrom + " "+maxLength+"/>" + "" + "<div class=\"qp-separator-from-to\">~</div>" + "<form:input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" cssClass=\"form-control qp-input-to qp-input-currency pull-right\"";
					element += "  " + pathTo + " "+maxLength+" />" + "</div>";
				} else {
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<form:input style=\"" + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-from qp-input-currency pull-left\"  " + pathFrom + " "+maxLength+"/>";
				}
			} else {
				element = "<form:input style=\"" + item.getStyle() + "\" type=\"text\"  cssClass=\"form-control qp-input-currency\"  " + path + " "+maxLength+"/>";
			}
			break;
		case 4:
			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div>" + "<div class=\"input-group date qp-input-from-datepicker pull-left\">";
					element += "<form:input type=\"text\" class=\"form-control qp-input-from\"";
					element += "  " + pathFrom + " "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>" + "<div class=\"qp-separator-from-to\">~</div>" + "<div class=\"input-group date qp-input-from-datepicker pull-left\">";
					element += "<form:input  type=\"text\" class=\"form-control\" ";
					element += "  " + pathTo + " "+maxLength+" style=\"" + item.getStyle() + "\" />";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				} else {
					element += "<div>" + "<div class=\"input-group date qp-input-datepicker pull-left\">";
					element += "<form:input type=\"text\" class=\"form-control qp-input-from\"";
					element += "  " + pathFrom + " "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				}
			} else {
				element += "<div class=\"input-group date qp-input-datepicker\">" + "<span><form:input type=\"text\" class=\"form-control\"  " + path + " "+maxLength+" style=\"" + item.getStyle() + "\" /></span>";
				element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
			}
			break;
		case 14:

			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div>" + "<div class=\"input-group date qp-input-from-datetimepicker-detail pull-left\">";
					element += "<form:input  type=\"text\" class=\"form-control\"";
					element += " " + pathFrom + " "+maxLength+" style=\"" + item.getStyle() + "\" />";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>" + "<div class=\"qp-separator-from-to\">~</div>" + "<div class=\"input-group date qp-input-to-datetimepicker-detail pull-rigth\">";
					element += "<form:input  type=\"text\" class=\"form-control\"";
					element += " " + pathTo + " "+maxLength+" style=\"" + item.getStyle() + "\" />";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				} else {
					element += "<div>" + "<div class=\"input-group date qp-input-datetimepicker-detail pull-left\">";
					element += "<form:input  type=\"text\" class=\"form-control\"";
					element += " " + pathFrom + " "+maxLength+" style=\"" + item.getStyle() + "\" />";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				}
			} else {
				element += "<div class=\"input-group date qp-input-datetimepicker-detail\">" + "<span><form:input type=\"text\" class=\"form-control\"  " + path + " "+maxLength+" style=\"" + item.getStyle() + "\" /></span>";
				element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
			}

			break;
		case 9:

			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div>" + "<div class=\"input-group date qp-input-from-timepicker pull-left\">";
					element += "<form:input  type=\"text\" class=\"form-control\" ";
					element += "  " + pathFrom + " "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>" + "<div class=\"qp-separator-from-to\">~</div>" + "<div class=\"input-group date qp-input-to-timepicker pull-rigth\">";
					element += "<form:input  type=\"text\" class=\"form-control\"";
					element += "  " + pathTo + " "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>";
					element += "</div>";
				} else {
					element += "<div>" + "<div class=\"input-group date qp-input-timepicker pull-left\">";
					element += "<form:input  type=\"text\" class=\"form-control\" ";
					element += "  " + pathFrom + " "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>";
					element += "</div>";
				}
			} else {
				element += "<div class=\"input-group date qp-input-timepicker\">" + "<span><form:input type=\"text\" class=\"form-control\"  " + path + " "+maxLength+" style=\"" + item.getStyle() + "\"/></span>";
				element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>";
			}
			break;
		//Radio
		case 5:
			//Datasource
			if (item.getDatasourcetype().equals("1")) {
				/*element += "<form:radiobuttons path=\""+nameElement+"\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\" cssClass=\"qp-input-radio-margin qp-input-radio\"></form:radiobuttons>";*/
				element += "<c:forEach var=\"item\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\"><label>";
				element += "\n\t<form:radiobutton path=\""+nameElement+"\" value=\"${item."+optionSubmit+"}\" cssClass=\"qp-input-radio-margin qp-input-radio\" />${item."+optionDisplay+"}</label>";
				element += "\n</c:forEach>";
				
			} 
			//Codelist
			else if (item.getDatasourcetype().equals("2")) {
				StringBuilder codelistCodeNew = new StringBuilder();
				if (String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST).equals(item.getLocalCodelist()) && StringUtils.isNoneBlank(item.getCodelistCode())) {
					StringBuilder sb = new StringBuilder();
					CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(codelist.getCodeListCode());
					String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
					sb.append("\n<c:forEach var=\"item\" items=\"${"+nameCodelist+"}\">");
					sb.append("\n\t<form:radiobutton path=\""+nameElement+"\" value=\"${item.key}\" cssClass=\"qp-input-radio-margin qp-input-radio\" />&nbsp;<qp:message code=\"${"+nameCodelist+".get(item.key)}\" />");
					sb.append("\n</c:forEach>");
					element = sb.toString();
				} else if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(tempScreenDesign.getScreenCode() + "_"  + screenItem.getScreenItemId());
					String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
					element += "<form:radiobuttons path=\""+nameElement+"\" items=\"${"+nameCodelist+"}\" cssClass=\"qp-input-radio-margin qp-input-radio\"></form:radiobuttons>";
				}
				
			}
			else {
				element += "<label "+item.getStyle()+"> <input type=\"radio\" class=\"qp-input-radio qp-input-radio-margin\"";
				element += " name=\"" + item.getColumnname() + "\"/>"+MessageUtils.getMessage("sc.screendesign.0137") + "1</label>";
				element += "<label "+item.getStyle()+"> <input type=\"radio\" class=\"qp-input-radio qp-input-radio-margin\" ";
				element += " name=\"" + item.getColumnname() + "\"/>"+MessageUtils.getMessage("sc.screendesign.0137") + "2</label>";
			}
			break;
		//Checkbox
		case 6:
			// 8 = Boolean
			if("8".equals(item.getPhysicaldatatype())) {
				StringBuilder sb = new StringBuilder();
				if(tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.MODIFY)) {
					sb.append("<form:checkbox value=\"true\" path=\""+nameElement+"\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\"/>");
					element = sb.toString();
				/*} else if(tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.REGISTER)) {*/
				} else if (tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.SEARCH)) {
					element += "<form:checkbox value=\"true\" path=\""+nameElement+"\" value=\"true\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\"/>&nbsp True";
					element += "<form:checkbox value=\"true\" path=\""+nameElement+"\" value=\"false\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\"/>&nbsp False";
				}
				else {
					element = "<form:checkbox value=\"true\" path=\""+nameElement+"\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\"/>";
				}
			} else {
				//Datasource
				if (item.getDatasourcetype().equals("1")) {
					/*element += "<form:checkboxes path=\""+nameElement+"\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\"></form:checkboxes>";*/
					element += "<c:forEach var=\"item\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\"><label>";
					element += "\n\t<form:checkbox path=\""+nameElement+"\" value=\"${item."+optionSubmit+"}\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\" />${item."+optionDisplay+"}</label>";
					element += "\n</c:forEach>";
				}
				//Codelist
				else if (item.getDatasourcetype().equals("2")) {
					StringBuilder sb = new StringBuilder();
					StringBuilder codelistCodeNew = new StringBuilder();
					if (item.getLocalCodelist().equals(String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST)) && StringUtils.isNoneBlank(item.getCodelistCode())) {
						CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
						codelistCodeNew.append(newPackageName);
						codelistCodeNew.append(codelist.getCodeListCode());
						String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
						sb.append("\n<c:forEach var=\"item\" items=\"${"+nameCodelist+"}\">");
						sb.append("\n\t<form:checkbox path=\""+nameElement+"\" value=\"${item.key}\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\" />&nbsp;<qp:message code=\"${"+nameCodelist+".get(item.key)}\" />");
						sb.append("\n</c:forEach>");
						element = sb.toString();
					} else if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
						codelistCodeNew.append(newPackageName);
						codelistCodeNew.append(tempScreenDesign.getScreenCode() + "_"  + screenItem.getScreenItemId());
						String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
						element += "<form:checkboxes path=\""+nameElement+"\" items=\"${"+nameCodelist+"}\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\"></form:checkboxes>";
					}
				}
				else {
					element += "<label "+item.getStyle()+"><input type=\"checkbox\" class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					element += " name=\"" + item.getColumnname() + "\" />"+ MessageUtils.getMessage("sc.screendesign.0137") + "1</label>";
					element += "<label "+item.getStyle()+"><input type=\"checkbox\" class=\"qp-input-checkbox-margin qp-input-checkbox\" ";
					element += " name=\"" + item.getColumnname() + "\" />"+ MessageUtils.getMessage("sc.screendesign.0137") + "2</label>";
				}
			}
			
			break;
		//Select
		case 7:
			String blankItem = "";
			if(screenItem.getShowBlankItem() != null && SHOW_BLANK_ITEM.equals(screenItem.getShowBlankItem())) {
				blankItem = "<option value=\"\"></option>";
			}
			if (item.getDatasourcetype().equals("1")) {
				/*element += "<form:select path=\""+nameElement+"\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\" cssClass=\"form-control qp-input-select\"></form:select>";*/
				
				element += "<form:select path=\""+nameElement+"\" cssClass=\"form-control qp-input-select\">";
				element += blankItem;
				element += "\n\t<c:forEach var=\"item\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\">";
				element += "\n\t\t<form:option value=\"${item."+optionSubmit+"}\">${item."+optionDisplay+"}</form:option>";
				element += "\n\t</c:forEach>";
				element += "</form:select>";
				
			} else if (item.getDatasourcetype().equals("2")) {
				StringBuilder codelistCodeNew = new StringBuilder();
				if (item.getLocalCodelist().equals(String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST)) && StringUtils.isNoneBlank(item.getCodelistCode())) {
					CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(codelist.getCodeListCode());
					
				} else if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
					
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(tempScreenDesign.getScreenCode() + "_"  + screenItem.getScreenItemId());
				}
				String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
				element += "<form:select path=\""+nameElement+"\" cssClass=\"form-control qp-input-select\" >";
				element += blankItem;
				element += "\n\t<c:forEach var=\"item\" items=\"${"+nameCodelist+"}\">";
				element += "\n\t\t<form:option value=\"${item.key}\"><qp:message code=\"${"+nameCodelist+".get(item.key)}\"/></form:option>";
				element += "\n\t</c:forEach>";
				element += "</form:select>";
			}
			else {
				element += "<select name=\"" + item.getColumnname() + "\" class=\"form-control qp-input-select\" >";
				element += "<option></option><option>" + MessageUtils.getMessage("sc.screendesign.0137") + "1</option><option>" + MessageUtils.getMessage("sc.screendesign.0137") + "2</option>";
				element += "</select>";
			}

			break;
		case 10:
			/*if(tempScreenDesign.getScreenPatternType() == 2) {
				element += "<textarea class=\"form-control\" name=\""+ pathNew +"\" rows=\"\" cols=\"\"></textarea>";
			} else {
				element += "<textarea class=\"form-control\" name=\""+ pathNew +"\" rows=\"\" cols=\"\">"+ valueNew +"</textarea>";
			}*/
			element += "<form:textarea path=\""+pathNew+"\" cssClass=\"form-control qp-input-textarea\"></form:textarea>";
			break;
			
		case 22:
		case 11:
			Integer navigateToScreenTemplateTypeLink;
			if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getToBlogicScreenTemplateType() != null) {
				navigateToScreenTemplateTypeLink = screenItem.getScreenAction().getToBlogicScreenTemplateType();
			} else {
				navigateToScreenTemplateTypeLink = 3;
			}
			
			if(screenItem.getEnableConfirm() != null && ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM.equals(screenItem.getEnableConfirm()) && screenItem.getMessageConfirm() != null) {
				enableConfirm = "qp-link-dialog-confirm";
				messageId = "messageId="+"\""+ screenItem.getMessageConfirm().getMessageCode()+"\"";
			}
			
			StringBuilder codelistCodeNew = new StringBuilder();
			if (item.getLocalCodelist().equals(String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST)) && StringUtils.isNoneBlank(item.getCodelistCode())) {
				CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
				codelistCodeNew.append(newPackageName);
				codelistCodeNew.append(codelist.getCodeListCode());
			} else if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
				codelistCodeNew.append(newPackageName);
				codelistCodeNew.append(screenItem.getScreenDesignCodeCodeListId() + "_" + screenItem.getScreenItemIdCodeListId());
			}
			String lastCodelist = "CL_" + (codelistCodeNew.toString().toUpperCase());
			
			String classPopUp = "qp-link-popup";
			
			String qpFormat = "";
			if(screenItem.getPhysicalDataType() != null) {
				if (screenItem.getPhysicalDataType().equals(1) || screenItem.getPhysicalDataType().equals(2) || screenItem.getPhysicalDataType().equals(3)) {
					qpFormat = "qp:formatText";
				} else if (screenItem.getPhysicalDataType().equals(5) || screenItem.getPhysicalDataType().equals(6) || screenItem.getPhysicalDataType().equals(7)) {
					qpFormat = "qp:formatInteger";
				} else if (screenItem.getPhysicalDataType().equals(4) || screenItem.getPhysicalDataType().equals(16) || screenItem.getPhysicalDataType().equals(17)) { 
					qpFormat = "qp:formatFloat";
				} else if (screenItem.getPhysicalDataType().equals(10)) {
					qpFormat = "qp:formatDate";
				} else if (screenItem.getPhysicalDataType().equals(15)) {
					qpFormat = "qp:formatCurrency";
				} else if (screenItem.getPhysicalDataType().equals(10)) {
					qpFormat = "qp:formatDate";
				} else if (screenItem.getPhysicalDataType().equals(11)) {
					qpFormat = "qp:formatTime";
				} else if (screenItem.getPhysicalDataType().equals(12)) {
					qpFormat = "qp:formatDateTime";
				} else {
					qpFormat = "qp:formatText";
				}
			} else {
				qpFormat = "qp:formatText";
			}
			
			String hoverStyle = "";
			if(item.getHoverStyle() != null && item.getHoverStyle() != "") {
				hoverStyle = " onmouseover=\"this.setAttribute('style', '" + item.getHoverStyle() + "')\" ";
			}
			String style = "";
			String mouseout = "";
			if(item.getStyle() != null && item.getStyle() != "") {
				mouseout = " onmouseout=\"this.setAttribute('style', '" + item.getStyle() + "')\" ";
				style = " style=\"" + item.getStyle() + "\" ";
			}
			StringBuffer strPermission = new StringBuffer();
			String strBlogicCode = new String();
			
			if(screenItem.getScreenAction().getNavigateToBlogicCode() != null && screenItem.getScreenAction().getNavigateToBlogicCode() != "") {
				strPermission = new StringBuffer(screenItem.getScreenAction().getNavigateToBlogicCode());
				strPermission.setCharAt(0, Character.toUpperCase(strPermission.charAt(0)));
				strBlogicCode = GenerateSourceCodeUtil.normalizedURL(screenItem.getScreenAction().getNavigateToBlogicCode());
			}
			String toModuleCode = "";
			if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getToModuleCode() != null) {
				toModuleCode = screenItem.getScreenAction().getToModuleCode();
			}
			
			String forDisplay = "";
			//forDisplay
			
			String elementNameCodelist = "";
			if(screenItem.getScreenArea().getAreaType() != null && screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
				if(screenItem.getLogicalDataType() == 22) {
					if(screenItem.getDataSourceType() != null && screenItem.getDataSourceType().equals(2)) {
						elementNameCodelist = "${"+lastCodelist+".get(item."+screenItem.getOutputBeanCode()+".toString())}";
						forDisplay = "<qp:message code=\""+elementNameCodelist+"\" />";
					} else {
						elementNameCodelist = "${item."+screenItem.getOutputBeanCode()+"}";
						forDisplay = "<"+qpFormat+" value=\""+elementNameCodelist+"\" />";
					}
				} 
				if(screenItem.getLogicalDataType() == 11) {
					forDisplay = "<qp:message code=\"" + item.getLabel() + "\"/>";
				}
			} else {
				if(screenItem.getLogicalDataType() == 22) {
					if(screenItem.getDataSourceType() != null && screenItem.getDataSourceType().equals(2)) {
						elementNameCodelist = "${"+lastCodelist+".get("+screenItem.getOutputBeanCode()+".toString())}";
						forDisplay = "<qp:message code=\""+elementNameCodelist+"\" />";
					} else {
						elementNameCodelist = "${"+screenItem.getOutputBeanCode()+"}";
						forDisplay = "<"+qpFormat+" value=\""+elementNameCodelist+"\" />";
					}
				} 
				if(screenItem.getLogicalDataType() == 11) {
					forDisplay = "<qp:message code=\"" + item.getLabel() + "\"/>";
				} 
			}
			
			element += "<qp:authorization permission=\""+GenerateSourceCodeUtil.normalizedURL(toModuleCode + strPermission.toString())+"\">";
			/*element += "<span class=\"qp-link-header-icon glyphicon "+glyphicon+"\"></span>";*/
			if((TEMPLATE_TYPE_NORMAL.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_NORMAL.equals(navigateToScreenTemplateTypeLink)) || 
					(TEMPLATE_TYPE_POPUP.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_POPUP.equals(navigateToScreenTemplateTypeLink))) {
				if(screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
					element += "<a "+messageId+" class=\" "+enableConfirm+"\" "+hoverStyle+" "+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(toModuleCode)  + "/" +  strBlogicCode.toString() + requestParam + "\" "+style+">"+forDisplay+"</a>";
				} else {
					element += "<a "+messageId+" class=\" "+enableConfirm+"\" "+hoverStyle+" "+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(toModuleCode) + "/" + strBlogicCode.toString() + requestParam + "\" "+style+">"+forDisplay+"</a>";
				}
			}
			if(TEMPLATE_TYPE_NORMAL.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_POPUP.equals(navigateToScreenTemplateTypeLink)) {
				if(screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
					element += "<a "+messageId+" class=\""+classPopUp+""+enableConfirm+"\" "+hoverStyle+" "+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(toModuleCode) + "/" + strBlogicCode.toString() + requestParam + "\" "+style+">"+forDisplay+"</a>";
				} else {
					element += "<a "+messageId+" class=\""+classPopUp+""+enableConfirm+"\" "+hoverStyle+""+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(toModuleCode) + "/" + strBlogicCode.toString() + requestParam + "\" "+style+">"+forDisplay+"</a>";
				}
			}
			if(TEMPLATE_TYPE_POPUP.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_NORMAL.equals(navigateToScreenTemplateTypeLink)) {
				if(screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
					element += "<a "+messageId+" class=\" "+enableConfirm+" qp-link-popup-navigate\" "+hoverStyle+" "+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(toModuleCode) + "/" + strBlogicCode.toString() + requestParam + "\" "+style+">"+forDisplay+"</a>";
				} else {
					element += "<a "+messageId+" class=\" "+enableConfirm+" qp-link-popup-navigate\" "+hoverStyle+" "+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(toModuleCode) + "/" + strBlogicCode.toString() + requestParam + "\" "+style+">"+forDisplay+"</a>";
				}
			}
			//Do not setting navigate to
			if(navigateToScreenTemplateTypeLink == null || navigateToScreenTemplateTypeLink == 3) {
				element += "<a "+messageId+" class=\" "+enableConfirm+"\" "+hoverStyle+" "+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(toModuleCode)  + "/" +  strBlogicCode.toString() + requestParam + "\" "+style+">"+forDisplay+"</a>";
			}
			element += "</qp:authorization>";

			break;
		case 12:
			if(!tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.SEARCH)) {
				element += "<form:input cssClass=\"qp-input-file\" type=\"file\"  " + path + " style=\"" + item.getStyle() + "\"/>";
			}
			
			break;
		case 13:
			String beforePost = "beforePostNotConfirm(this)";
			
			Integer navigateToScreenTemplateTypeButton;
			if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getToBlogicScreenTemplateType() != null) {
				navigateToScreenTemplateTypeButton = screenItem.getScreenAction().getToBlogicScreenTemplateType();
			} else {
				navigateToScreenTemplateTypeButton = 3;
			}
			
			StringBuffer strPermissionButton = new StringBuffer();
			if(screenItem.getScreenAction().getNavigateToBlogicCode() != null && screenItem.getScreenAction().getNavigateToBlogicCode() != "") {
				strPermissionButton = new StringBuffer(screenItem.getScreenAction().getNavigateToBlogicCode());
				strPermissionButton.setCharAt(0, Character.toUpperCase(strPermissionButton.charAt(0)));
			}
			String toModuleCodeButton = "";
			if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getToModuleCode() != null) {
				toModuleCodeButton = screenItem.getScreenAction().getToModuleCode();
			}
			element += "<qp:authorization permission=\""+GenerateSourceCodeUtil.normalizedURL(toModuleCodeButton + strPermissionButton.toString())+"\">";
			if(screenItem.getEnableConfirm() != null && ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM.equals(screenItem.getEnableConfirm()) && screenItem.getMessageConfirm() != null) {
				enableConfirm = "qp-dialog-confirm";
				messageId = "messageId="+"\""+ screenItem.getMessageConfirm().getMessageCode()+"\"";
				beforePost = "beforePostHaveConfirm(this)";
			}
			
			String buttonType = "";
			//Default or Save
			if( (screenItem.getButtonType() != null && (ScreenDesignConst.ButtonType.BUTTON_TYPE_DEFAULT.equals(screenItem.getButtonType()) || ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE.equals(screenItem.getButtonType()))) 
					|| screenItem.getButtonType() == null) {
				buttonType = "qp-button qp-button-type";
			}
			//Delete
			else if(screenItem.getButtonType() != null && ScreenDesignConst.ButtonType.BUTTON_TYPE_DELETE.equals(screenItem.getButtonType())) {
				buttonType = "qp-button-type-warning";
			} 
			//Client
			else if(screenItem.getButtonType() != null && ScreenDesignConst.ButtonType.BUTTON_TYPE_CLIENT.equals(screenItem.getButtonType())) {
				buttonType = "qp-button qp-button-type-client";
			}
			
			String buttonIcon = "<i class=\"" + item.getIcon() + "\"></i>";
			String buttonContent = "";
			if(StringUtils.isNotBlank(item.getLabelText())) {
				buttonContent = "<qp:message code=\""+item.getLabel()+"\" />";
			}
			String buttonContentTotal = "";
			if (StringUtils.isNotEmpty(item.getIcon()) && item.getIcon() != "" && item.getIcon() != null) {
				buttonContentTotal += buttonIcon;
				if (StringUtils.isNotEmpty(item.getLabelText()) && item.getLabelText() != "" && 
						((item.getShowLabel() != null && "1".equals(item.getShowLabel())) || (item.getShowLabel() == null || item.getShowLabel() == "" ))) {
					buttonContentTotal += "&nbsp;&nbsp;";
				}
			} 
			if (StringUtils.isNotEmpty(item.getLabelText()) && item.getLabelText() != "" && 
					((item.getShowLabel() != null && "1".equals(item.getShowLabel())) || (item.getShowLabel() == null || item.getShowLabel() == "" ))) {
				buttonContentTotal += buttonContent;
			}
			
			//Button is submit
			if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getSubmitMethodType() != null && screenItem.getScreenAction().getSubmitMethodType().equals(ScreenActionConst.SUBMIT_METHOD_TYPE_POST)) {
				String blogicCode = "";
				if(screenItem.getScreenAction().getNavigateToBlogicCode() != null && screenItem.getScreenAction().getNavigateToBlogicCode() != "") {
					blogicCode = GenerateSourceCodeUtil.normalizedURL(screenItem.getScreenAction().getNavigateToBlogicCode());
				}
				
				String action = "";
				action = "${pageContext.request.contextPath}/"+GenerateSourceCodeUtil.normalizedURL(toModuleCodeButton)+"/"+blogicCode+ requestParam;
				
				String areaEventId = "";
				List<ScreenArea> screenAreas = new ArrayList<ScreenArea>();
				for (int i = 0; i < tempScreenDesign.getScreenAreas().length; i++) {
					if (tempScreenDesign.getScreenAreas()[i].getScreenAreaEvents() != null && tempScreenDesign.getScreenAreas()[i].getScreenAreaEvents().size() > 0) {
						screenAreas.add(tempScreenDesign.getScreenAreas()[i]);
					}
				}
				for(ScreenArea area : screenAreas) {
					for(ScreenAreaEvent areaEvent : area.getScreenAreaEvents()) {
						areaEventId = String.valueOf(areaEvent.getScreenAreaEventId());
					}
				}
				String eventArea = "";
				if(StringUtils.isNotEmpty(areaEventId)) {
					eventArea = "onclick=\"eventArea"+areaEventId+"\"";
				}
				element += "<button "+messageId+" name=\""+screenItem.getItemCode()+"\" "+eventArea+" type=\"button\" button-href=\'"+action+"\' onclick=\" "+beforePost+" \" class=\"btn "+enableConfirm+" "+buttonType+" \" style=\""+ item.getStyle() +"\">"+buttonContentTotal+"</button>";
				
			} 
			//Button is not submit
			else if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getSubmitMethodType() != null && screenItem.getScreenAction().getSubmitMethodType().equals(ScreenActionConst.SUBMIT_METHOD_TYPE_GET)) {
				String blogicCode = "";
				if(screenItem.getScreenAction().getNavigateToBlogicCode() != null && screenItem.getScreenAction().getNavigateToBlogicCode() != "") {
					blogicCode = GenerateSourceCodeUtil.normalizedURL(screenItem.getScreenAction().getNavigateToBlogicCode());
				}
				String action = "";
				action = "${pageContext.request.contextPath}/"+GenerateSourceCodeUtil.normalizedURL(toModuleCodeButton)+"/"+blogicCode+ requestParam;
				String beforeGet = "";
				if(screenItem.getEnableConfirm() != null && ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM.equals(screenItem.getEnableConfirm()) && screenItem.getMessageConfirm() != null) {
					beforeGet = "beforeGetHaveConfirm(this)";
				} else {
					beforeGet = "beforeGetNotConfirm(this)";
				}
				if(TEMPLATE_TYPE_NORMAL.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_NORMAL.equals(navigateToScreenTemplateTypeButton)) {
					element += "<button "+messageId+" type=\"button\" button-href=\'"+action+"\' onclick=\""+beforeGet+"\" class=\"btn "+buttonType+" "+enableConfirm+" \" style=\""+ item.getStyle() +"\">"+buttonContentTotal+"</button>";
				}
				if(TEMPLATE_TYPE_POPUP.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_POPUP.equals(navigateToScreenTemplateTypeButton)) {
					element += "<a href=\'"+action+"\' class=\"btn qp-button qp-link-button qp-link-popup-text\" type=\"submit\">"+buttonContentTotal+"</a>";
				}
				if(TEMPLATE_TYPE_NORMAL.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_POPUP.equals(navigateToScreenTemplateTypeButton)) {
					element += "<a href=\'"+action+"\' class=\"btn qp-button qp-link-button qp-link-popup\" type=\"submit\">"+buttonContentTotal+"</a>";
				}
				if(TEMPLATE_TYPE_POPUP.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_NORMAL.equals(navigateToScreenTemplateTypeButton)) {
					element += "<a href=\'"+action+"\' class=\"btn qp-button qp-link-button qp-link-popup-navigate\" type=\"submit\">"+buttonContentTotal+"</a>";
				}
				
			}
			else{
				element += "<input "+messageId+" type=\"button\" class=\"btn "+enableConfirm+" "+buttonType+" \" value=\"" + item.getLabelText() + "\""+ item.getLabelText() +"/>";
			}
			element += "</qp:authorization>";
			break;
		case 20:
			String id = FunctionCommon.convertNameToCodeDb(item.getLabelText().replace("lbl", ""));
			
			String mandatory = "&nbsp;&nbsp;<span class=\"qp-required-field\"><qp:message code=\"sc.sys.0029\" /></span>";
			String styleStaticLabel = "";
			if (item.getStyle() != "" && item.getStyle() != null) {
				styleStaticLabel = "  style=\"" + item.getStyle() + "\" ";
			}
			
			if (screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.SINGLE_ENTITY)) {
				if(item.getMandatory() != null && "true".equals(item.getMandatory())) {
					element += "<label "+styleStaticLabel+"><qp:message code=\"" + item.getLabel() + "\" />"+mandatory+"</label>";
				} else {
					element += "<label "+styleStaticLabel+"><qp:message code=\"" + item.getLabel() + "\" /></label>";
				}
			} else {
				if(item.getMandatory() != null && "true".equals(item.getMandatory())) {
					element += "<label style=\"cursor: pointer; " + item.getStyle() + "\"><qp:message code=\"" + item.getLabel() + "\" />"+mandatory+"</label>";
				} else {
					element += "<label style=\"cursor: pointer; " + item.getStyle() + "\"><qp:message code=\"" + item.getLabel() + "\" /></label>";
				}
				
			}
			break;
		case 23 : 
			if(screenItem.getCustomItemContent() != null && screenItem.getCustomItemContent() != "") {
				element += "<qp:custom>"+screenItem.getCustomItemContent()+"</qp:custom>";
			}
			break;
		}
		
		// gen screen item condtion
		StringBuilder sb = new StringBuilder();
		if(screenItem.getScreenItemStatusLst() != null && screenItem.getScreenItemStatusLst().size() > 0){
			int tabIndex = 0;
			for(ScreenItemStatus screenItemStatus : screenItem.getScreenItemStatusLst()){
				String condition = screenItemStatus.getFormulaCondition();
				if( StringUtils.isNotEmpty(condition)) {				
					sb.append("<c:if test=\"" + condition+ "\">").append("\n");		
					sb.append(element).append("\n");
					sb.append("</c:if>").append("\n");		
				}
			}
		}
		else {
			sb.append(element);
		}
		return sb.toString();
	}

	@SuppressWarnings("unused")
	private String getElementTemplate(ScreenItem screenItem, int screenPatternType, String moduleCode, TempScreenDesign tempScreenDesign, ScreenForm screenForm, Project project, String modelAttributeOut, 
									List<OutputBean> listOfOutputBean, List<ScreenItemAutocompleteInput> listAutocompleteInputByScreenId, int countButtonSubmit, String glyphicon) {
		
		String atcInputStr = "";
		int index = 1;
		
		for(ScreenItemAutocompleteInput atcInput : listAutocompleteInputByScreenId) {
			if(screenItem.getScreenItemId().equals(atcInput.getScreenItemId()) && atcInput.getInputId() != null) {
				String strIndex = String.format("%02d", index);
				String[] str = atcInput.getScreenItemCode().split("\\.");
				String newStr = str[str.length - 1].toString();
				atcInputStr += " arg"+strIndex+"=\"${"+modelAttributeOut+"."+newStr+"}\"";
				index++;
			}
		}
		
		String newPackageName = project.getPackageName().replaceAll("\\.", "_") + "_";
		
		String pathFrom = "";
		String pathTo = "";
		String value = "";
		String path = "";
		String valueForEach = "";
		
		String valueNew = "";
		String pathNew = "";
		String valueElement = "";
		
		String parentOutputBean = (screenItem.getParentOutputBeanCode() != null) ? (screenItem.getParentOutputBeanCode()+".") : ("");
		String outputBean = (screenItem.getOutputBeanCode() != null) ? (screenItem.getOutputBeanCode()) : ("");
		
		String parentInputBean = (screenItem.getParentInputBeanCode() != null) ? (screenItem.getParentInputBeanCode() + ".") : ("");
		String inputBean = (screenItem.getItemCode() != null) ? (screenItem.getItemCode()) : ("");

		if (screenItem.getScreenArea() != null &&  screenItem.getScreenArea().getAreaType() != null && screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
			if (StringUtils.isNotBlank(outputBean)) {
				value = " value=" + '"' + "${" + "item." + outputBean + "}" + '"';
				valueNew = "${" + "item." + outputBean + "}";
				valueForEach = "item." + outputBean;
				valueElement = outputBean;
			} else {
				/*value = " value=" + '"' + "${" + "item." + outputBean + "}" + '"';*/
				valueForEach = "item";
			}
		} else {
			if (StringUtils.isNotBlank(parentOutputBean) && StringUtils.isNotBlank(outputBean)) {
				value = " value=" + '"' + "${" + modelAttributeOut + "." + parentOutputBean + outputBean + "}" + '"';
				valueNew = "${" + modelAttributeOut + "." + parentOutputBean + outputBean + "}";
				valueForEach = modelAttributeOut + "." + parentOutputBean + outputBean;
				valueElement = parentOutputBean + outputBean;
			}
		}

		if (!StringUtils.isNotBlank(parentOutputBean) && StringUtils.isNotBlank(outputBean)) {
			value = " value=" + '"' + "${" + modelAttributeOut + "." + outputBean + "}" + '"';
			valueNew = "${" + modelAttributeOut + "." + outputBean + "}";
			valueForEach = modelAttributeOut + "." + outputBean;
			valueElement = outputBean;
		}
		if (!StringUtils.isNotBlank(parentOutputBean) && !StringUtils.isNotBlank(outputBean) || DbDomainConst.ScreenPatternType.REGISTER.equals(tempScreenDesign.getScreenPatternType())) {
			value = "";
		}
		
		String nameElement = "";
		
		if (StringUtils.isNotBlank(parentInputBean) && StringUtils.isNotBlank(inputBean)) {
			path = "path=" + '"' + "" + parentInputBean + inputBean + "" + '"';
			pathNew = "" + parentInputBean + inputBean + "";
			pathFrom = "path=" + '"' + "" + parentInputBean + inputBean + "" + "From" + '"';
			pathTo = "path=" + '"' + "" + parentInputBean + inputBean + "" + "To" + '"';
			nameElement = parentInputBean + inputBean;
		}
		if (!StringUtils.isNotBlank(parentInputBean) && StringUtils.isNotBlank(inputBean)) {
			path = "path=" + '"' + "" + inputBean + "" + '"';
			pathNew = "" + inputBean + "";
			pathFrom = "path=" + '"' + "" + inputBean + "" + "From" + '"';
			pathTo = "path=" + '"' + "" + inputBean + "" + "To" + '"';
			nameElement = inputBean;
		}
		if (!StringUtils.isNotBlank(parentInputBean) && !StringUtils.isNotBlank(inputBean)) {
			path = "";
			pathNew = "";
		}
		
		JsonFactory json = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(json);
		TypeReference<ScreenDesignOutput> typeRef = new TypeReference<ScreenDesignOutput>() {
		};

		ScreenDesignOutput item = new ScreenDesignOutput();
		String jsonString = "";
		if(screenItem.getValue() != null) {
			jsonString = FunctionCommon.getStringJson(screenItem.getValue());
		}
		
		try {
			item = mapper.readValue(jsonString, typeRef);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (StringUtils.isEmpty(item.getDatatype())) {
			return "";
		}
		if(item.getLocalCodelist() != null) {
			screenItem.setLocalCodelist(item.getLocalCodelist());
		}
		
		int indexOfParams = 0;
		String requestParam = "";
		String preRequest = "";
		if (screenItem.getScreenActionId() != null) {
			List<ScreenActionParam> screenActionParamButton = screenActionParamRepository.findAllActionParamByScreenActionId(screenItem.getScreenActionId(), project.getProjectId());
			List<ScreenActionParam> params = new ArrayList<ScreenActionParam>();
			if (FunctionCommon.isNotEmpty(screenActionParamButton)) {
				for (ScreenActionParam screenActionParam : screenActionParamButton) {
					if (screenActionParam.getScreenItemCode() != null) {
						String[] str = screenActionParam.getScreenItemCode().split("\\.");
						ScreenActionParam screenactionparam = new ScreenActionParam();

						if (str != null && str.length > 0) {
							screenactionparam.setScreenItemCode(str[str.length - 1]);
						}
						screenactionparam.setActionParamCode(screenActionParam.getActionParamCode());
						params.add(screenactionparam);
					}
				}
			}

			for (ScreenActionParam sap : params) {
				if(indexOfParams == 0) {
					preRequest = "?";
				} else {
					preRequest = "&";
				}
				if (sap.getActionParamCode() != null && sap.getScreenItemCode() != null) {
					
					/*String requestCode = sap.getScreenItemCode();*/
					/*if (screenItem.getScreenAction() != null && screenItem.getScreenAction().getActionType() != null && screenItem.getScreenAction().getActionType().equals(1)) {*/
						// get input code blogic by id
					String requestCode = "";
					if (!StringUtils.isEmpty(sap.getActionParamCode())) {
						try {
							InputBean input =  businessDesignRepository.findInputBeanById(Long.parseLong(sap.getActionParamCode()), currentLanguageId, project.getProjectId());
							requestCode = input.getInputBeanCode();
						} catch(Exception ex) {
							
						}
					}
					/*} */
					
					if (screenItem.getScreenArea() != null && DbDomainConst.AreaType.LIST_ENTITIES.equals(screenItem.getScreenArea().getAreaType())
						 && sap.getAreaIdOfItem() != null && sap.getAreaIdOfItem().equals(screenItem.getScreenArea().getScreenAreaId())) {
						OutputBean outputBeanTmp = new OutputBean();
						for (OutputBean out : listOfOutputBean) {
							if (out.getItemCodeMapping().equals(sap.getScreenItemCode()) && out.getAreaCodeMapping().equals(sap.getAreaCodeOfItem()) && out.getFormCodeMapping().equals(sap.getFormCodeOfItem())) {
								outputBeanTmp = out;
								break;
							}
						}
						if(outputBeanTmp != null) {
							String outputTmp = (outputBeanTmp.getOutputBeanCode() != null) ? (outputBeanTmp.getOutputBeanCode()) : ("");
							String qpFormat = "";
							if(screenItem.getPhysicalDataType() != null && screenItem.getPhysicalDataType().equals(DbDomainConst.BaseType.DATE_BASETYPE)) {
								qpFormat = "=<qp:formatDate value=\"${item." + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else if(screenItem.getPhysicalDataType() != null && screenItem.getPhysicalDataType().equals(DbDomainConst.BaseType.TIME_BASETYPE)) {
								qpFormat = "=<qp:formatTime value=\"${item." + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else if(screenItem.getPhysicalDataType() != null && screenItem.getPhysicalDataType().equals(DbDomainConst.BaseType.DATETIME_BASETYPE)) {
								qpFormat = "=<qp:formatDateTime value=\"${item." + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else {
								qpFormat = "=${item." + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}";
							}
							requestParam += preRequest + requestCode + qpFormat + "";
							indexOfParams++;
						}
					} else {
						OutputBean  outputBeanTmp = new OutputBean();
						for (OutputBean out : listOfOutputBean) {
							if (out.getItemCodeMapping().equals(sap.getScreenItemCode())) {
								outputBeanTmp = out;
								break;
							}
						}
						
						String parentOutputBeanTmp = (outputBeanTmp.getParentOutputBeanCode() != null) ? (outputBeanTmp.getParentOutputBeanCode()+".") : ("");
						String outputTmp = (outputBeanTmp.getOutputBeanCode() != null) ? (outputBeanTmp.getOutputBeanCode()) : ("");
						
						requestParam += preRequest + requestCode + "=${ "+modelAttributeOut + "." + GenerateSourceCodeUtil.normalizedPropertiesJSP(parentOutputBeanTmp) + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}" + "";
						indexOfParams++;
					
					}
				}
			}
		}
		
		//Get default value
		String defaultValue = "";
		if(screenItem.getDefaultValue() != null && StringUtils.isNotBlank(screenItem.getDefaultValue())) {
			defaultValue = screenItem.getDefaultValue();
		} else {
			defaultValue = "null";
		}
		if(tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.REGISTER)) {
			if(defaultValue != null && StringUtils.isNotBlank(defaultValue) && defaultValue != "null") {
				value = "value=\""+defaultValue+"\" ";
				valueNew = defaultValue;
			} else {
				value = "";
			}
		}
		
		
		String enableConfirm = "";
		String messageId = "";
		if(screenItem.getEnableConfirm() != null && ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM.equals(screenItem.getEnableConfirm()) && screenItem.getMessageConfirm() != null) {
			enableConfirm = "qp-link-dialog-confirm";
			messageId = "messageId="+"\""+ screenItem.getMessageConfirm().getMessageCode()+"\"";
		}
		
		// Set for data source type
		
		String valueSelect = "";
		if(valueForEach != null && StringUtils.isNotBlank(valueForEach)) {
			valueSelect = valueForEach;
		} else {
			valueSelect = "null";
		}
		String optionSubmit = "";
		String optionDisplay = "";
		String dataSource = "";
		String parentOutputBeanSelect = "";
		String outputBeanSelect = "";
		String blogicCodeHasDataSource = "";
		for(OutputBean output : listOfOutputBean) {
			if(output.getScreenItemId().equals(Long.parseLong(item.getScreenItemId()))) {
				if(output.getMappingType() != null && output.getMappingType().equals(MAPPING_TYPE_OPTION_SUBMIT)) {
					optionSubmit = output.getOutputBeanCode();
				} else if(output.getMappingType() != null && output.getMappingType().equals(MAPPING_TYPE_OPTION_DISPLAY)) {
					optionDisplay = output.getOutputBeanCode();
				} else if(output.getMappingType() != null && output.getMappingType().equals(MAPPING_TYPE_DATA_SOURCE)) {
					blogicCodeHasDataSource = output.getBusinessLogicCode();
					parentOutputBeanSelect = (output.getParentOutputBeanCode() != null) ? (output.getParentOutputBeanCode()+".") : ("");
					outputBeanSelect = (output.getOutputBeanCode() != null) ? (output.getOutputBeanCode()) : ("");
					dataSource = parentOutputBeanSelect + outputBeanSelect;
				} 
			}
		}
		
		String maxLength = "";
		if(item.getMaxlength() != null && item.getMaxlength() != "" && Integer.parseInt(item.getMaxlength()) > 0) {
			maxLength = "maxlength=\"" + item.getMaxlength() + "\"";
		}
		
		//Set mapping type for screenItem
		String element = "";
		int dataType = Integer.parseInt(item.getDatatype());
		switch (dataType) {
		case 0:
			String mustMatch = "";
			if(screenItem.getAllowAnyInput() != null && screenItem.getAllowAnyInput().equals(0)) {
				mustMatch = "mustMatch=\"false\"";
			} else {
				mustMatch = "mustMatch=\"true\"";
			}
			
			String displayValueForAu = "";
			String valueForAu = "";
			for(OutputBean out : listOfOutputBean) {
				if(out.getScreenItemId() != null && screenItem.getScreenItemId() != null) {
					if(out.getScreenItemId().equals(screenItem.getScreenItemId())) {
						String parent = (screenItem.getParentOutputBeanCode() != null) ? (screenItem.getParentOutputBeanCode()) : ("");
						String output = (screenItem.getOutputBeanCode() != null) ? (screenItem.getOutputBeanCode()) : ("");
						if(out.getMappingType() != null && "1".equals(out.getMappingType())) {
							displayValueForAu = modelAttributeOut + "." + parent + output;
						}
						else {
							if(out.getParentOutputBeanArrayFlag() != null && out.getParentOutputBeanArrayFlag().equals("t")) {
								parent += "[status.index].";
							} else {
								parent += ".";
							}
							valueForAu = modelAttributeOut + "." + parent + output;
						}
					}
				}
			}
			
			String submitColumn = "";
			
			AutocompleteDesign autocompleteDesign = autocompleteDesignRepository.getAutocompleteDesignByScreenItem(screenItem.getAutocompleteId());
			
			if(autocompleteDesign != null) {
				String[] displayColumn = autocompleteDesign.getDisplayColumnFlag().split(",");
			
				submitColumn = autocompleteDesign.getSubmitColumn();
				submitColumn = "output" + String.format("%2s", submitColumn).replace(' ', '0');
				
				String display = "";
				for(int i = 2, j = 1 ; i < displayColumn.length ; i++, j++) {
					if (displayColumn[i].equals("1")) {
						display += "output" + String.format("%2s", j).replace(' ', '0');
						
						if (i < displayColumn.length - 1) {
							display += ",";
						}
					}
				}
				
				display = display.substring(0, display.length() - 1);
				
				if (DbDomainConst.ScreenPatternType.MODIFY.equals(tempScreenDesign.getScreenPatternType())) {
					if(screenItem.getAllowAnyInput() != null && screenItem.getAllowAnyInput().equals("1")) {
						element = "<qp:autocomplete displayValue=\""+displayValueForAu+"\" value=\" ${"+valueForAu+"} \" selectSqlId=\"QP_ATC_"+autocompleteDesign.getAutocompleteId()+"\" name=\""+ nameElement+"\" optionValue=\""+submitColumn+"\" optionLabel=\""+display+"\" "+mustMatch+" "+atcInputStr+"></qp:autocomplete>";
					} else {
						element = "<qp:autocomplete displayValue=\""+displayValueForAu+"\" value=\" ${"+valueForAu+"} \" selectSqlId=\"QP_ATC_"+autocompleteDesign.getAutocompleteId()+"\" name=\""+ nameElement+"\" optionValue=\""+submitColumn+"\" optionLabel=\""+display+"\" "+mustMatch+" "+atcInputStr+"></qp:autocomplete>";
					}
				} else {
					if(screenItem.getAllowAnyInput() != null && screenItem.getAllowAnyInput().equals("1")) {
						element = "<qp:autocomplete selectSqlId=\"QP_ATC_"+autocompleteDesign.getAutocompleteId()+"\" name=\""+ nameElement+"\" optionValue=\""+submitColumn+"\" optionLabel=\""+display+"\" "+mustMatch+" "+atcInputStr+"></qp:autocomplete>";
					} else {
						element = "<qp:autocomplete selectSqlId=\"QP_ATC_"+autocompleteDesign.getAutocompleteId()+"\" name=\""+ nameElement+"\" optionValue=\""+submitColumn+"\" optionLabel=\""+display+"\" "+mustMatch+" "+atcInputStr+"></qp:autocomplete>";
					}
				}
			}
			else {
				element = "<div class=\"input-group\" style=\"width:100% "+ item.getStyle() +"\"><input type=\"text\" class=\"form-control\" ";
				element += " name=\"" + item.getColumnname() + "\" "+maxLength+" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" "+mustMatch+" minlength=\"0\" placeholder=\"\" autocomplete=\"off\">" + "	<input type=\"hidden\" value=\"\">" + "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" + "		<span class=\"caret\"></span> " + "	</span>" + "</div>";
			}
			break;
		case 1:
			
			if(item.getEnablePassword() != null && item.getEnablePassword().equals("1")) {
				element = "<form:input type=\"password\" style=\"width:100%; " + item.getStyle() + "\" cssClass=\"form-control qp-input-text\" " + path + " "+maxLength+" />";
			} else {
				element = "<form:input type=\"text\" style=\"width:100%; " + item.getStyle() + "\" cssClass=\"form-control qp-input-text\" " + path + " "+maxLength+" />";
			}
			
			break;
		case 15:
		case 16:
		case 18:
			element = "<form:input style=\"width:100%; " + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-text\" " + path + " "+maxLength+" />";
			break;
		case 21:
			//SQL Builder
			if(item.getDatasourcetype().equals("1") || item.getDatasourcetype().equals("")) {
				if(screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
					element += "<label style=\"" + item.getStyle() + "\">${item."+valueElement+"}</label>";
				} else {
					if (StringUtils.isNotBlank(parentOutputBean) && StringUtils.isNotBlank(outputBean)) {
						valueElement = parentOutputBean + outputBean;
					}
					element += "<label style=\"" + item.getStyle() + "\">${"+modelAttributeOut+"."+valueElement+"}</label>";
				}
			}
			//Codelist
			else if(item.getDatasourcetype().equals("2")){
				StringBuilder codelistCodeNew = new StringBuilder();
				if (item.getLocalCodelist().equals(String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST))) {
					CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(codelist.getCodeListCode());
				} else if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
					codelistCodeNew.append(newPackageName);
					
					codelistCodeNew.append(screenItem.getScreenDesignCodeCodeListId() + "_" + screenItem.getScreenItemIdCodeListId());
				}
				
				String lastCodelist = "CL_" + (codelistCodeNew.toString().toUpperCase());
				
				element += "\n<c:if test=\"${not empty "+lastCodelist+".get("+valueForEach+".toString())}\" >";
				element += "\n\t<label style=\"" + item.getStyle() + "\"><qp:message code=\"${"+lastCodelist+".get("+valueForEach+".toString())}\"/></label>";
				element += "\n</c:if>";
				element += "\n<c:if test=\"${empty "+lastCodelist+".get("+valueForEach+".toString())}\" >";
				element += "\n\t<c:set var=\"split\" value=\"\" />";
				if(screenItem.getScreenArea() != null && screenItem.getScreenArea().getAreaType() != null && DbDomainConst.AreaType.LIST_ENTITIES.equals(screenItem.getScreenArea().getAreaType())) {
					element += "\n\t<c:if test=\"${fn:contains(item."+outputBean+",';')}\">";
				} else {
					element += "\n\t<c:if test=\"${fn:contains("+valueForEach+",';')}\">";
				}
				element += "\n\t\t<c:set var=\"split\" value=\";\" />";
				element += "\n\t</c:if>";
				element += "\n\t\t<c:forTokens items=\"${"+valueForEach+"}\" delims=\";\" var=\"itemDelim\">";
				element += "\n\t\t\t<label style=\"" + item.getStyle() + "\"><qp:message code=\"${"+lastCodelist+".get(itemDelim.toString())}\"/>${split}</label>";
				element += "\n\t\t</c:forTokens>";
				element += "\n</c:if>";
			}
			
			break;
		case 2:
			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<form:input style=\"" + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-from qp-input-integer pull-left\"  " + pathFrom + "  "+maxLength+" />" + "" + "<div class=\"qp-separator-from-to\">~</div>" + "<form:input type=\"text\" cssClass=\"form-control qp-input-to qp-input-integer pull-right\"";
					element += " " + pathTo + " "+maxLength+" />" + "</div>";
				} else {
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<form:input style=\"" + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-integer pull-left\"    " + pathFrom + " "+maxLength+" />";
				}
			} else {
				element = "<form:input style=\"" + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-integer\"  " + path + " "+maxLength+"/>";
			}
			break;
		case 3:
			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<form:input style=\"" + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-from qp-input-float pull-left\"  " + pathFrom + " "+maxLength+" />" + "" + "<div class=\"qp-separator-from-to\">~</div>" + "<form:input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" cssClass=\"form-control qp-input-to qp-input-float pull-right\"";
					element += "  " + pathTo + " "+maxLength+"/>" + "</div>";
				} else {
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<form:input style=\"" + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-float pull-left\"  " + pathFrom + " "+maxLength+"/>";
				}
			} else {
				element = "<form:input style=\"" + item.getStyle() + "\" type=\"text\"  cssClass=\"form-control qp-input-float\"  " + path + " "+maxLength+"/>";
			}
			break;
		case 8:
			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<form:input style=\"" + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-from qp-input-currency pull-left\"  " + pathFrom + " "+maxLength+"/>" + "" + "<div class=\"qp-separator-from-to\">~</div>" + "<form:input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" cssClass=\"form-control qp-input-to qp-input-currency pull-right\"";
					element += "  " + pathTo + " "+maxLength+"/>" + "</div>";
				} else {
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<form:input style=\"" + item.getStyle() + "\" type=\"text\" cssClass=\"form-control qp-input-from qp-input-currency pull-left\"  " + pathFrom + " "+maxLength+" />";
				}
			} else {
				element = "<form:input style=\"" + item.getStyle() + "\" type=\"text\"  cssClass=\"form-control qp-input-currency\"  " + path + " "+maxLength+"/>";
			}
			break;
		case 4:
			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div>" + "<div class=\"input-group date qp-input-from-datepicker pull-left\">";
					element += "<form:input type=\"text\" class=\"form-control qp-input-from\"";
					element += "  " + pathFrom + " "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>" + "<div class=\"qp-separator-from-to\">~</div>" + "<div class=\"input-group date qp-input-from-datepicker pull-left\">";
					element += "<form:input  type=\"text\" class=\"form-control\" ";
					element += "  " + pathTo + " "+maxLength+" style=\"" + item.getStyle() + "\" />";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				} else {
					element += "<div>" + "<div class=\"input-group date qp-input-datepicker pull-left\">";
					element += "<form:input type=\"text\" class=\"form-control qp-input-from\"";
					element += "  " + pathFrom + " "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				}
			} else {
				element += "<div class=\"input-group date qp-input-datepicker\">" + "<span><form:input type=\"text\" class=\"form-control\"  " + path + " "+maxLength+" style=\"" + item.getStyle() + "\" /></span>";
				element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
			}
			break;
		case 14:

			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div>" + "<div class=\"input-group date qp-input-from-datetimepicker-detail pull-left\">";
					element += "<form:input  type=\"text\" class=\"form-control\"";
					element += " " + pathFrom + " "+maxLength+" style=\"" + item.getStyle() + "\" />";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>" + "<div class=\"qp-separator-from-to\">~</div>" + "<div class=\"input-group date qp-input-to-datetimepicker-detail pull-rigth\">";
					element += "<form:input  type=\"text\" class=\"form-control\"";
					element += " " + pathTo + " "+maxLength+" style=\"" + item.getStyle() + "\" />";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				} else {
					element += "<div>" + "<div class=\"input-group date qp-input-datetimepicker-detail pull-left\">";
					element += "<form:input  type=\"text\" class=\"form-control\"";
					element += " " + pathFrom + " "+maxLength+" style=\"" + item.getStyle() + "\" />";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				}
			} else {
				element += "<div class=\"input-group date qp-input-datetimepicker-detail\">" + "<span><form:input type=\"text\" class=\"form-control\"  " + path + " "+maxLength+" style=\"" + item.getStyle() + "\" /></span>";
				element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
			}

			break;
		case 9:

			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div>" + "<div class=\"input-group date qp-input-from-timepicker pull-left\">";
					element += "<form:input  type=\"text\" class=\"form-control\" ";
					element += "  " + pathFrom + " "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>" + "<div class=\"qp-separator-from-to\">~</div>" + "<div class=\"input-group date qp-input-to-timepicker pull-rigth\">";
					element += "<form:input  type=\"text\" class=\"form-control\"";
					element += "  " + pathTo + " "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>";
					element += "</div>";
				} else {
					element += "<div>" + "<div class=\"input-group date qp-input-timepicker pull-left\">";
					element += "<form:input  type=\"text\" class=\"form-control\" ";
					element += "  " + pathFrom + " "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>";
					element += "</div>";
				}
			} else {
				element += "<div class=\"input-group date qp-input-timepicker\">" + "<span><form:input type=\"text\" class=\"form-control\"  " + path + " "+maxLength+" style=\"" + item.getStyle() + "\"/></span>";
				element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>";
			}
			break;
		//Radio
		case 5:
			//Datasource
			if (item.getDatasourcetype().equals("1")) {
				/*element += "<form:radiobuttons path=\""+nameElement+"\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\" cssClass=\"qp-input-radio-margin qp-input-radio\"></form:radiobuttons>";*/
				element += "<c:forEach var=\"item\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\"><label>";
				element += "\n\t<form:radiobutton path=\""+nameElement+"\" value=\"${item."+optionSubmit+"}\" cssClass=\"qp-input-radio-margin qp-input-radio\" />&nbsp;<qp:message code=\"${item."+optionDisplay+"}\" /></label>";
				element += "\n</c:forEach>";
				
			} 
			//Codelist
			else if (item.getDatasourcetype().equals("2")) {
				StringBuilder codelistCodeNew = new StringBuilder();
				if (item.getLocalCodelist().equals(String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST)) && StringUtils.isNoneBlank(item.getCodelistCode())) {
					StringBuilder sb = new StringBuilder();
					CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(codelist.getCodeListCode());
					String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
					sb.append("\n<c:forEach var=\"item\" items=\"${"+nameCodelist+"}\">");
					sb.append("\n\t<form:radiobutton path=\""+nameElement+"\" value=\"${item.key}\" cssClass=\"qp-input-radio-margin qp-input-radio\" />&nbsp;<qp:message code=\"${"+nameCodelist+".get(item.key)}\" />");
					sb.append("\n</c:forEach>");
					element = sb.toString();
				} else if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(tempScreenDesign.getScreenCode() + "_"  + screenItem.getScreenItemId());
					String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
					element += "<form:radiobuttons path=\""+nameElement+"\" items=\"${"+nameCodelist+"}\" cssClass=\"qp-input-radio-margin qp-input-radio\"></form:radiobuttons>";
				}
				
			}
			else {
				element += "<label "+item.getStyle()+"> <input type=\"radio\" class=\"qp-input-radio qp-input-radio-margin\"";
				element += " name=\"" + item.getColumnname() + "\"/>"+MessageUtils.getMessage("sc.screendesign.0137") + "1</label>";
				element += "<label "+item.getStyle()+"> <input type=\"radio\" class=\"qp-input-radio qp-input-radio-margin\" ";
				element += " name=\"" + item.getColumnname() + "\"/>"+MessageUtils.getMessage("sc.screendesign.0137") + "2</label>";
			}
			break;
		//Checkbox
		case 6:
			// 8 = Boolean
			if("8".equals(item.getPhysicaldatatype())) {
				StringBuilder sb = new StringBuilder();
				if(tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.MODIFY)) {
					sb.append("<form:checkbox value=\"true\" path=\""+nameElement+"\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\"/>");
					element = sb.toString();
				/*} else if(tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.REGISTER)) {*/
				} else if (tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.SEARCH)) {
					element += "<form:checkbox value=\"true\" path=\""+nameElement+"\" value=\"true\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\"/> &nbsp True";
					element += "<form:checkbox value=\"true\" path=\""+nameElement+"\" value=\"false\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\"/> &nbsp False";
				}
				else {
					element = "<form:checkbox value=\"true\" path=\""+nameElement+"\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\"/>";
				}
			} else {
				//Datasource
				if (item.getDatasourcetype().equals("1")) {
					/*element += "<form:checkboxes path=\""+nameElement+"\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\"></form:checkboxes>";*/
					element += "<c:forEach var=\"item\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\"><label>";
					element += "\n\t<form:checkbox path=\""+nameElement+"\" value=\"${item."+optionSubmit+"}\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\" />&nbsp;<qp:message code=\"${item."+optionDisplay+"}\" /></label>";
					element += "\n</c:forEach>";
				}
				//Codelist
				else if (item.getDatasourcetype().equals("2")) {
					StringBuilder sb = new StringBuilder();
					StringBuilder codelistCodeNew = new StringBuilder();
					if (item.getLocalCodelist().equals(String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST)) && StringUtils.isNoneBlank(item.getCodelistCode())) {
						CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
						codelistCodeNew.append(newPackageName);
						codelistCodeNew.append(codelist.getCodeListCode());
						String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
						sb.append("\n<c:forEach var=\"item\" items=\"${"+nameCodelist+"}\">");
						sb.append("\n\t<form:checkbox path=\""+nameElement+"\" value=\"${item.key}\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\" />&nbsp;<qp:message code=\"${"+nameCodelist+".get(item.key)}\" />");
						sb.append("\n</c:forEach>");
						element = sb.toString();
					} else if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
						codelistCodeNew.append(newPackageName);
						codelistCodeNew.append(tempScreenDesign.getScreenCode() + "_"  + screenItem.getScreenItemId());
						String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
						element += "<form:checkboxes path=\""+nameElement+"\" items=\"${"+nameCodelist+"}\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\"></form:checkboxes>";
					}
				}
				else {
					element += "<label "+item.getStyle()+"><input type=\"checkbox\" class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					element += " name=\"" + item.getColumnname() + "\" />"+ MessageUtils.getMessage("sc.screendesign.0137") + "1</label>";
					element += "<label "+item.getStyle()+"><input type=\"checkbox\" class=\"qp-input-checkbox-margin qp-input-checkbox\" ";
					element += " name=\"" + item.getColumnname() + "\" />"+ MessageUtils.getMessage("sc.screendesign.0137") + "2</label>";
				}
			}
			
			break;
		//Select
		case 7:
			String blankItem = "";
			if(screenItem.getShowBlankItem() != null && SHOW_BLANK_ITEM.equals(screenItem.getShowBlankItem())) {
				blankItem = "<option value=\"\"></option>";
			}
			if (item.getDatasourcetype().equals("1")) {
				/*element += "<form:select path=\""+nameElement+"\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\" cssClass=\"form-control qp-input-select\"></form:select>";*/
				element += "<form:select path=\""+nameElement+"\" cssClass=\"form-control qp-input-select\">";
				element += blankItem;
				element += "\n\t<c:forEach var=\"item\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\">";
				element += "\n\t\t<form:option value=\"${item."+optionSubmit+"}\">&nbsp;<qp:message code=\"${item."+optionDisplay+"}\"/></form:option>";
				element += "\n\t</c:forEach>";
				element += "</form:select>";
			} else if (item.getDatasourcetype().equals("2")) {
				StringBuilder codelistCodeNew = new StringBuilder();
				if (item.getLocalCodelist().equals(String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST)) && StringUtils.isNoneBlank(item.getCodelistCode())) {
					CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(codelist.getCodeListCode());
					
				} else if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
					
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(tempScreenDesign.getScreenCode() + "_"  + screenItem.getScreenItemId());
				}
				String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
				element += "<form:select path=\""+nameElement+"\" cssClass=\"form-control qp-input-select\" >";
				element += blankItem;
				element += "\n\t<c:forEach var=\"item\" items=\"${"+nameCodelist+"}\">";
				element += "\n\t\t<form:option value=\"${item.key}\"><qp:message code=\"${"+nameCodelist+".get(item.key)}\"/></form:option>";
				element += "\n\t</c:forEach>";
				element += "</form:select>";
			}
			else {
				element += "<select name=\"" + item.getColumnname() + "\" class=\"form-control qp-input-select\" >";
				element += "<option></option><option>" + MessageUtils.getMessage("sc.screendesign.0137") + "1</option><option>" + MessageUtils.getMessage("sc.screendesign.0137") + "2</option>";
				element += "</select>";
			}

			break;
		case 10:
			element += "<form:textarea path=\""+pathNew+"\" cssClass=\"form-control qp-input-textarea\"></form:textarea>";
			break;
			
		case 22:
		case 11:
			Integer navigateToScreenTemplateTypeLink;
			if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getToBlogicScreenTemplateType() != null) {
				navigateToScreenTemplateTypeLink = screenItem.getScreenAction().getToBlogicScreenTemplateType();
			} else {
				navigateToScreenTemplateTypeLink = 3;
			}
			
			if(screenItem.getEnableConfirm() != null && ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM.equals(screenItem.getEnableConfirm()) && screenItem.getMessageConfirm() != null) {
				enableConfirm = "qp-link-dialog-confirm";
				messageId = "messageId="+"\""+ screenItem.getMessageConfirm().getMessageCode()+"\"";
			}
			
			StringBuilder codelistCodeNew = new StringBuilder();
			if (item.getLocalCodelist().equals(String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST)) && StringUtils.isNoneBlank(item.getCodelistCode())) {
				CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
				codelistCodeNew.append(newPackageName);
				codelistCodeNew.append(codelist.getCodeListCode());
			} else if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
				codelistCodeNew.append(newPackageName);
				codelistCodeNew.append(screenItem.getScreenDesignCodeCodeListId() + "_" + screenItem.getScreenItemIdCodeListId());
			}
			String lastCodelist = "CL_" + (codelistCodeNew.toString().toUpperCase());
			
			String elementName = "";
			if(screenItem.getDataSourceType() != null && screenItem.getDataSourceType().equals(2)) {
				elementName = "${"+lastCodelist+".get(item."+screenItem.getOutputBeanCode()+".toString())}";
			} else {
				elementName = "${item."+screenItem.getOutputBeanCode()+"}";
			}
			
			String classPopUp = "qp-link-popup";
			
			String qpFormat = "";
			if(screenItem.getPhysicalDataType() != null) {
				if (screenItem.getPhysicalDataType().equals(1) || screenItem.getPhysicalDataType().equals(2) || screenItem.getPhysicalDataType().equals(3)) {
					qpFormat = "qp:formatText";
				} else if (screenItem.getPhysicalDataType().equals(5) || screenItem.getPhysicalDataType().equals(6) || screenItem.getPhysicalDataType().equals(7)) {
					qpFormat = "qp:formatInteger";
				} else if (screenItem.getPhysicalDataType().equals(4) || screenItem.getPhysicalDataType().equals(16) || screenItem.getPhysicalDataType().equals(17)) { 
					qpFormat = "qp:formatFloat";
				} else if (screenItem.getPhysicalDataType().equals(10)) {
					qpFormat = "qp:formatDate";
				} else if (screenItem.getPhysicalDataType().equals(15)) {
					qpFormat = "qp:formatCurrency";
				} else if (screenItem.getPhysicalDataType().equals(10)) {
					qpFormat = "qp:formatDate";
				} else if (screenItem.getPhysicalDataType().equals(11)) {
					qpFormat = "qp:formatTime";
				} else if (screenItem.getPhysicalDataType().equals(12)) {
					qpFormat = "qp:formatDateTime";
				} else {
					qpFormat = "qp:formatText";
				}
			} else {
				qpFormat = "qp:formatText";
			}
			
			String hoverStyle = "";
			if(item.getHoverStyle() != null && item.getHoverStyle() != "") {
				hoverStyle = " onmouseover=\"this.setAttribute('style', '" + item.getHoverStyle() + "')\" ";
			}
			String style = "";
			String mouseout = "";
			if(item.getStyle() != null && item.getStyle() != "") {
				mouseout = " onmouseout=\"this.setAttribute('style', '" + item.getStyle() + "')\" ";
				style = " style=\"" + item.getStyle() + "\" ";
			}
			StringBuffer strPermission = new StringBuffer();
			String strBlogicCode = new String();
			
			if(screenItem.getScreenAction().getNavigateToBlogicCode() != null && screenItem.getScreenAction().getNavigateToBlogicCode() != "") {
				strPermission = new StringBuffer(screenItem.getScreenAction().getNavigateToBlogicCode());
				strPermission.setCharAt(0, Character.toUpperCase(strPermission.charAt(0)));
				strBlogicCode = GenerateSourceCodeUtil.normalizedURL(screenItem.getScreenAction().getNavigateToBlogicCode());
			}
			
			element += "<qp:authorization permission=\""+GenerateSourceCodeUtil.normalizedURL(moduleCode + strPermission.toString())+"\">";
			/*element += "<span class=\"qp-link-header-icon glyphicon "+glyphicon+"\"></span>";*/
			if((TEMPLATE_TYPE_NORMAL.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_NORMAL.equals(navigateToScreenTemplateTypeLink)) || 
					(TEMPLATE_TYPE_POPUP.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_POPUP.equals(navigateToScreenTemplateTypeLink))) {
				if(screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
					element += "<a "+messageId+" class=\" "+enableConfirm+"\" "+hoverStyle+" "+mouseout+" href=\'${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(moduleCode)  + "/" +  strBlogicCode.toString() + requestParam + "\' "+style+"><"+qpFormat+" value=\""+elementName+"\" /></a>";
				} else {
					element += "<a "+messageId+" class=\" "+enableConfirm+"\" "+hoverStyle+" "+mouseout+" href=\'${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(moduleCode) + "/" + strBlogicCode.toString() + requestParam + "\' "+style+"><qp:message code=\"" + item.getLabel() + "\"/></a>";
				}
			}
			if(TEMPLATE_TYPE_NORMAL.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_POPUP.equals(navigateToScreenTemplateTypeLink)) {
				if(screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
					element += "<a "+messageId+" class=\" "+enableConfirm+" "+classPopUp+"\" "+hoverStyle+" "+mouseout+" href=\'${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(moduleCode) + "/" + strBlogicCode.toString() + requestParam + "\' "+style+"><"+qpFormat+" value=\""+elementName+"\" /></a>";
				} else {
					element += "<a "+messageId+" class=\" "+classPopUp+"\" "+hoverStyle+" "+mouseout+" href=\'${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(moduleCode) + "/" + strBlogicCode.toString() + requestParam + "\' "+style+"><qp:message code=\"" + item.getLabel() + "\"/></a>";
				}
			}
			if(TEMPLATE_TYPE_POPUP.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_NORMAL.equals(navigateToScreenTemplateTypeLink)) {
				if(screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
					element += "<a "+messageId+" class=\" "+enableConfirm+" qp-link-popup-navigate\" "+hoverStyle+" "+mouseout+" href=\'${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(moduleCode) + "/" + strBlogicCode.toString() + requestParam + "\' "+style+"><"+qpFormat+" value=\""+elementName+"\" /></a>";
				} else {
					element += "<a "+messageId+" class=\" "+enableConfirm+" qp-link-popup-navigate\" "+hoverStyle+" "+mouseout+" href=\'${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(moduleCode) + "/" + strBlogicCode.toString() + requestParam + "\' "+style+"><qp:message code=\"" + item.getLabel() + "\"/></a>";
				}
			}
			//Do not setting navigate to
			if(navigateToScreenTemplateTypeLink == null || navigateToScreenTemplateTypeLink == 3) {
				element += "<a "+messageId+" class=\" "+enableConfirm+"\" "+hoverStyle+" "+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(moduleCode)  + "/" +  strBlogicCode.toString() + requestParam + "\" "+style+"><qp:message code=\"" + item.getLabel() + "\"/></a>";
			}
						
			element += "</qp:authorization>";

			break;
		case 12:
			if(!tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.SEARCH)) {
				element += "<form:input cssClass=\"qp-input-file\" type=\"file\"  " + path + " style=\"" + item.getStyle() + "\"/>";
			}
			
			break;
		case 13:
			String beforePost = "beforePostNotConfirm(this)";
			
			Integer navigateToScreenTemplateTypeButton;
			if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getToBlogicScreenTemplateType() != null) {
				navigateToScreenTemplateTypeButton = screenItem.getScreenAction().getToBlogicScreenTemplateType();
			} else {
				navigateToScreenTemplateTypeButton = 3;
			}
			
			StringBuffer strPermissionButton = new StringBuffer();
			if(screenItem.getScreenAction().getNavigateToBlogicCode() != null && screenItem.getScreenAction().getNavigateToBlogicCode() != "") {
				strPermissionButton = new StringBuffer(screenItem.getScreenAction().getNavigateToBlogicCode());
				strPermissionButton.setCharAt(0, Character.toUpperCase(strPermissionButton.charAt(0)));
			}
			String toModuleCodeButton = "";
			if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getToModuleCode() != null) {
				toModuleCodeButton = screenItem.getScreenAction().getToModuleCode();
			}
			element += "<qp:authorization permission=\""+GenerateSourceCodeUtil.normalizedURL(toModuleCodeButton + strPermissionButton.toString())+"\">";
			if(screenItem.getEnableConfirm() != null && ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM.equals(screenItem.getEnableConfirm()) && screenItem.getMessageConfirm() != null) {
				enableConfirm = "qp-dialog-confirm";
				messageId = "messageId="+"\""+ screenItem.getMessageConfirm().getMessageCode()+"\"";
				beforePost = "beforePostHaveConfirm(this)";
			}
			
			String buttonType = "";
			//Default or Save
			if( (screenItem.getButtonType() != null && (ScreenDesignConst.ButtonType.BUTTON_TYPE_DEFAULT.equals(screenItem.getButtonType()) || ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE.equals(screenItem.getButtonType()))) 
					|| screenItem.getButtonType() == null) {
				buttonType = "qp-button qp-button-type";
			}
			//Delete
			else if(screenItem.getButtonType() != null && ScreenDesignConst.ButtonType.BUTTON_TYPE_DELETE.equals(screenItem.getButtonType())) {
				buttonType = "qp-button-type-warning";
			} 
			//Client
			else if(screenItem.getButtonType() != null && ScreenDesignConst.ButtonType.BUTTON_TYPE_CLIENT.equals(screenItem.getButtonType())) {
				buttonType = "qp-button qp-button-type-client";
			}
			
			String buttonIcon = "<i class=\"" + item.getIcon() + "\"></i>";
			String buttonContent = "";
			if(StringUtils.isNotBlank(item.getLabelText())) {
				buttonContent = "<qp:message code=\""+item.getLabel()+"\" />";
			}
			String buttonContentTotal = "";
			if (StringUtils.isNotEmpty(item.getIcon()) && item.getIcon() != "" && item.getIcon() != null) {
				buttonContentTotal += buttonIcon;
				if (StringUtils.isNotEmpty(item.getLabelText()) && item.getLabelText() != "" && 
						((item.getShowLabel() != null && "1".equals(item.getShowLabel())) || (item.getShowLabel() == null || item.getShowLabel() == "" ))) {
					buttonContentTotal += "&nbsp;&nbsp;";
				}
			} 
			if (StringUtils.isNotEmpty(item.getLabelText()) && item.getLabelText() != "" && 
					((item.getShowLabel() != null && "1".equals(item.getShowLabel())) || (item.getShowLabel() == null || item.getShowLabel() == "" ))) {
				buttonContentTotal += buttonContent;
			}
			
			//Button is submit
			if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getSubmitMethodType() != null && screenItem.getScreenAction().getSubmitMethodType().equals(ScreenActionConst.SUBMIT_METHOD_TYPE_POST)) {
				String blogicCode = "";
				if(screenItem.getScreenAction().getNavigateToBlogicCode() != null && screenItem.getScreenAction().getNavigateToBlogicCode() != "") {
					blogicCode = GenerateSourceCodeUtil.normalizedURL(screenItem.getScreenAction().getNavigateToBlogicCode());
				}
				
				String action = "";
				action = "${pageContext.request.contextPath}/"+GenerateSourceCodeUtil.normalizedURL(toModuleCodeButton)+"/"+blogicCode+ requestParam;
				
				String areaEventId = "";
				List<ScreenArea> screenAreas = new ArrayList<ScreenArea>();
				for (int i = 0; i < tempScreenDesign.getScreenAreas().length; i++) {
					if (tempScreenDesign.getScreenAreas()[i].getScreenAreaEvents() != null && tempScreenDesign.getScreenAreas()[i].getScreenAreaEvents().size() > 0) {
						screenAreas.add(tempScreenDesign.getScreenAreas()[i]);
					}
				}
				for(ScreenArea area : screenAreas) {
					for(ScreenAreaEvent areaEvent : area.getScreenAreaEvents()) {
						areaEventId = String.valueOf(areaEvent.getScreenAreaEventId());
					}
				}
				String eventArea = "";
				if(StringUtils.isNotEmpty(areaEventId)) {
					eventArea = "onclick=\"eventArea"+areaEventId+"\"";
				}
				element += "<button "+messageId+" name=\""+screenItem.getItemCode()+"\" "+eventArea+" type=\"button\" button-href=\""+action+"\" onclick=\" "+beforePost+" \" class=\"btn "+enableConfirm+" "+buttonType+" \" style=\""+ item.getStyle() +"\">"+buttonContentTotal+"</button>";
				
			} 
			//Button is not submit
			else if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getSubmitMethodType() != null && screenItem.getScreenAction().getSubmitMethodType().equals(ScreenActionConst.SUBMIT_METHOD_TYPE_GET)) {
				String blogicCode = "";
				if(screenItem.getScreenAction().getNavigateToBlogicCode() != null && screenItem.getScreenAction().getNavigateToBlogicCode() != "") {
					blogicCode = GenerateSourceCodeUtil.normalizedURL(screenItem.getScreenAction().getNavigateToBlogicCode());
				}
				String action = "";
				action = "${pageContext.request.contextPath}/"+GenerateSourceCodeUtil.normalizedURL(toModuleCodeButton)+"/"+blogicCode+ requestParam;
				String beforeGet = "";
				if(screenItem.getEnableConfirm() != null && ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM.equals(screenItem.getEnableConfirm()) && screenItem.getMessageConfirm() != null) {
					beforeGet = "beforeGetHaveConfirm(this)";
				} else {
					beforeGet = "beforeGetNotConfirm(this)";
				}
				if(TEMPLATE_TYPE_NORMAL.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_NORMAL.equals(navigateToScreenTemplateTypeButton)) {
					element += "<button "+messageId+" type=\"button\" button-href=\""+action+"\" onclick=\""+beforeGet+"\" class=\"btn "+buttonType+" "+enableConfirm+" \" style=\""+ item.getStyle() +"\">"+buttonContentTotal+"</button>";
				}
				if(TEMPLATE_TYPE_POPUP.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_POPUP.equals(navigateToScreenTemplateTypeButton)) {
					element += "<a href=\""+action+"\" class=\"btn qp-button qp-link-button qp-link-popup-text\" type=\"submit\">"+buttonContentTotal+"</a>";
				}
				if(TEMPLATE_TYPE_NORMAL.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_POPUP.equals(navigateToScreenTemplateTypeButton)) {
					element += "<a href=\""+action+"\" class=\"btn qp-button qp-link-button qp-link-popup\" type=\"submit\">"+buttonContentTotal+"</a>";
				}
				if(TEMPLATE_TYPE_POPUP.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_NORMAL.equals(navigateToScreenTemplateTypeButton)) {
					element += "<a href=\""+action+"\" class=\"btn qp-button qp-link-button qp-link-popup-navigate\" type=\"submit\">"+buttonContentTotal+"</a>";
				}
				
			}
			else{
				element += "<input "+messageId+" type=\"button\" class=\"btn "+enableConfirm+" "+buttonType+" \" value=\"" + item.getLabelText() + "\""+ item.getLabelText() +"/>";
			}
			element += "</qp:authorization>";
			break;
		case 20:
			String id = FunctionCommon.convertNameToCodeDb(item.getLabelText().replace("lbl", ""));
			
			String mandatory = "&nbsp;&nbsp;<span class=\"qp-required-field\"><qp:message code=\"sc.sys.0029\" /></span>";
			String styleLabel = "";
			if (item.getStyle() != "" && item.getStyle() != null) {
				styleLabel = "  style=\"" + item.getStyle() + "\" ";
			}
			if (screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.SINGLE_ENTITY)) {
				if(item.getMandatory() != null && "true".equals(item.getMandatory())) {
					element += "<label "+styleLabel+"><qp:message code=\"" + item.getLabel() + "\" />"+mandatory+"</label>";
				} else {
					element += "<label "+styleLabel+"><qp:message code=\"" + item.getLabel() + "\" /></label>";
				}
			} else {
				if(item.getMandatory() != null && "true".equals(item.getMandatory())) {
					element += "<label style=\"cursor: pointer; " + item.getStyle() + "\"><qp:message code=\"" + item.getLabel() + "\" />"+mandatory+"</label>";
				} else {
					element += "<label style=\"cursor: pointer; " + item.getStyle() + "\"><qp:message code=\"" + item.getLabel() + "\" /></label>";
				}
				
			}
			break;
		}
		
		// gen screen item condtion
		StringBuilder sb = new StringBuilder();
		if(screenItem.getScreenItemStatusLst() != null && screenItem.getScreenItemStatusLst().size() > 0){
			int tabIndex = 0;
			for(ScreenItemStatus screenItemStatus : screenItem.getScreenItemStatusLst()){
				String condition = screenItemStatus.getFormulaCondition();
				if( StringUtils.isNotEmpty(condition)) {				
					sb.append("<c:if test=\"" + condition+ "\">").append("\n");		
					sb.append(element).append("\n");
					sb.append("</c:if>").append("\n");		
				}
			}
		}
		else {
			sb.append(element);
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unused")
	private String getElementHTML(ScreenItem screenItem, int screenPatternType, String moduleCode, TempScreenDesign tempScreenDesign, ScreenForm screenForm, Project project, String modelAttributeOut, 
									List<OutputBean> listOfOutputBean,List<InputBean> listOfInputBean, List<ScreenItemAutocompleteInput> listAutocompleteInputByScreenId, int countButtonSubmit, String glyphicon) {
		
		String atcInputStr = "";
		int index = 1;
		
		for(ScreenItemAutocompleteInput atcInput : listAutocompleteInputByScreenId) {
			if(screenItem.getScreenItemId().equals(atcInput.getScreenItemId()) && atcInput.getInputId() != null) {
				String strIndex = String.format("%02d", index);
				String[] str = atcInput.getScreenItemCode().split("\\.");
				String newStr = str[str.length - 1].toString();
				atcInputStr += " arg"+strIndex+"=\"${"+modelAttributeOut+"."+newStr+"}\"";
				index++;
			}
		}
		
		String newPackageName = project.getPackageName().replaceAll("\\.", "_") + "_";
		
		String pathFrom = "";
		String pathTo = "";
		String value = "";
		String path = "";
		String valueForEach = "";
		
		String valueNew = "";
		String pathNew = "";
		String valueElement = "";
		
		String parentOutputBean = GenerateSourceCodeUtil.normalizedPropertiesJSP((screenItem.getParentOutputBeanCode() != null) ? (screenItem.getParentOutputBeanCode()+".") : (""));
		String outputBean = GenerateSourceCodeUtil.normalizedPropertiesJSP((screenItem.getOutputBeanCode() != null) ? (screenItem.getOutputBeanCode()) : (""));
		
		String parentInputBean = GenerateSourceCodeUtil.normalizedPropertiesJSP((screenItem.getParentInputBeanCode() != null) ? (screenItem.getParentInputBeanCode() + ".") : (""));
		String inputBean = GenerateSourceCodeUtil.normalizedPropertiesJSP((screenItem.getInputBeanCode() != null) ? (screenItem.getInputBeanCode()) : (""));

		if (screenItem.getScreenArea() != null &&  screenItem.getScreenArea().getAreaType() != null && screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
			if (StringUtils.isNotBlank(outputBean)) {
				value = " value=" + '"' + "${" + "item." + outputBean + "}" + '"';
				valueNew = "${" + "item." + outputBean + "}";
				valueForEach = "item." + outputBean;
				valueElement = outputBean;
			} else {
				/*value = " value=" + '"' + "${" + "item." + outputBean + "}" + '"';*/
				valueForEach = "item";
			}
		}  else {
			if (StringUtils.isNotBlank(parentOutputBean) && StringUtils.isNotBlank(outputBean)) {
				value = " value=" + '"' + "${" + modelAttributeOut + "." + parentOutputBean + outputBean + "}" + '"';
				valueNew = "${" + modelAttributeOut + "." + parentOutputBean + outputBean + "}";
				valueForEach = modelAttributeOut + "." + parentOutputBean + outputBean;
				valueElement = parentOutputBean + outputBean;
			}
		}

		if (!StringUtils.isNotBlank(parentOutputBean) && StringUtils.isNotBlank(outputBean)) {
			value = " value=" + '"' + "${" + modelAttributeOut + "." + outputBean + "}" + '"';
			valueNew = "${" + modelAttributeOut + "." + outputBean + "}";
			valueForEach = modelAttributeOut + "." + outputBean;
			valueElement = outputBean;
		}
		if (!StringUtils.isNotBlank(parentOutputBean) && !StringUtils.isNotBlank(outputBean) || DbDomainConst.ScreenPatternType.REGISTER.equals(tempScreenDesign.getScreenPatternType())) {
			value = "";
		}
		
		String nameElement = "";
		
		if (StringUtils.isNotBlank(parentInputBean) && StringUtils.isNotBlank(inputBean)) {
			path = "path=" + '"' + "" + parentInputBean + inputBean + "" + '"';
			pathNew = "" + parentInputBean + inputBean + "";
			nameElement = parentInputBean + inputBean;
		}
		if (!StringUtils.isNotBlank(parentInputBean) && StringUtils.isNotBlank(inputBean)) {
			path = "path=" + '"' + "" + inputBean + "" + '"';
			pathNew = "" + inputBean + "";
			nameElement = inputBean;
		}
		
		// Search From-To 
		if(screenItem.getInputBeans().size() > 1) {
			for(InputBean input : listOfInputBean) {
				for(InputBean in : screenItem.getInputBeans()) {
					if(in.getInputBeanCode().equals(input.getInputBeanCode())) {
						if (StringUtils.isNotBlank(parentInputBean) && StringUtils.isNotBlank(inputBean)) {
							if(in.getInputBeanCode().endsWith("From")) {
								pathFrom = "path=" + '"' + "" + parentInputBean + in.getInputBeanCode() + "" + '"';
							}
							if(in.getInputBeanCode().endsWith("To")) {
								pathTo = "path=" + '"' + "" + parentInputBean + in.getInputBeanCode() + "" + '"';
							}
						}
						if (!StringUtils.isNotBlank(parentInputBean) && StringUtils.isNotBlank(inputBean)) {
							if(in.getInputBeanCode().endsWith("From")) {
								pathFrom = "path=" + '"' + "" + in.getInputBeanCode() + "" + '"';
							}
							if(in.getInputBeanCode().endsWith("To")) {
								pathTo = "path=" + '"' + "" + in.getInputBeanCode() + "" + '"';
							}
						}
					}
				}
			}
		}
		
		if (!StringUtils.isNotBlank(parentInputBean) && !StringUtils.isNotBlank(inputBean)) {
			path = "";
			pathNew = "";
		}
		
		JsonFactory json = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(json);
		TypeReference<ScreenDesignOutput> typeRef = new TypeReference<ScreenDesignOutput>() {
		};

		ScreenDesignOutput item = new ScreenDesignOutput();
		String jsonString = "";
		if(screenItem.getValue() != null) {
			jsonString = FunctionCommon.getStringJson(screenItem.getValue());
		}
		
		try {
			item = mapper.readValue(jsonString, typeRef);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (StringUtils.isEmpty(item.getDatatype())) {
			return "";
		}
		if(item.getLocalCodelist() != null) {
			screenItem.setLocalCodelist(item.getLocalCodelist());
		}
		
		int indexOfParams = 0;
		String requestParam = "";
		String preRequest = "";
		if (screenItem.getScreenActionId() != null) {
			List<ScreenActionParam> screenActionParamButton = screenActionParamRepository.findAllActionParamByScreenActionId(screenItem.getScreenActionId(),project.getProjectId());
			List<ScreenActionParam> params = new ArrayList<ScreenActionParam>();
			if (FunctionCommon.isNotEmpty(screenActionParamButton)) {
				for (ScreenActionParam screenActionParam : screenActionParamButton) {
					if (screenActionParam.getScreenItemCode() != null) {
						String[] str = screenActionParam.getScreenItemCode().split("\\.");
						ScreenActionParam screenactionparam = new ScreenActionParam();

						if (str != null && str.length > 0) {
							screenactionparam.setScreenItemCode(str[str.length - 1]);
							screenactionparam.setAreaCodeOfItem(str[str.length - 2]);
							screenactionparam.setFormCodeOfItem(str[str.length - 3]);
						}
						
						screenactionparam.setAreaIdOfItem(screenActionParam.getAreaIdOfItem());
						screenactionparam.setAreaTypeOfItem(screenActionParam.getAreaTypeOfItem());
						screenactionparam.setActionParamCode(screenActionParam.getActionParamCode());
						screenactionparam.setItemPhysicalDataType(screenActionParam.getItemPhysicalDataType());
						screenactionparam.setItemId(screenActionParam.getItemId());
						params.add(screenactionparam);
					}
				}
			}
			List<ScreenActionParam> lstOutputByItemId = new ArrayList<ScreenActionParam>();
			List<Long> arrLong = new ArrayList<Long>();
			for(ScreenActionParam arr : params) {
				arrLong.add(arr.getItemId());
			}
			if(params.size() > 0) {
				lstOutputByItemId = screenActionParamRepository.getOutputByItemId(params);
			}
			
			for(ScreenActionParam itemHaveOutput : lstOutputByItemId) {
				for(ScreenActionParam itemDontOutput : params) {
					if(itemHaveOutput.getItemId() != null && itemDontOutput.getItemId() != null && itemHaveOutput.getItemId().equals(itemDontOutput.getItemId())) {
						itemDontOutput.setOutputIdOfItem(itemHaveOutput.getOutputIdOfItem());
						itemDontOutput.setOutputPhysicalDataTypeOfItem(itemHaveOutput.getOutputPhysicalDataTypeOfItem());
					}
				}
			}
			
			for (ScreenActionParam sap : params) {
				if(indexOfParams == 0) {
					preRequest = "?";
				} else {
					preRequest = "&";
				}
				if (sap.getActionParamCode() != null && sap.getScreenItemCode() != null) {
					
					/*String requestCode = sap.getScreenItemCode();*/
					/*if (screenItem.getScreenAction() != null && screenItem.getScreenAction().getActionType() != null && screenItem.getScreenAction().getActionType().equals(1)) {*/
						// get input code blogic by id
					String requestCode = "";
					if (!StringUtils.isEmpty(sap.getActionParamCode())) {
						try {
							InputBean input =  businessDesignRepository.findInputBeanById(Long.parseLong(sap.getActionParamCode()), currentLanguageId, project.getProjectId());
							requestCode = input.getInputBeanCode();
						} catch(Exception ex) {
							
						}
					}
					/*} */
					
					if (screenItem.getScreenArea() != null && DbDomainConst.AreaType.LIST_ENTITIES.equals(screenItem.getScreenArea().getAreaType())
						 && sap.getAreaIdOfItem() != null && sap.getAreaIdOfItem().equals(screenItem.getScreenArea().getScreenAreaId())) {
						OutputBean outputBeanTmp = new OutputBean();
						for (OutputBean out : listOfOutputBean) {
							if (out.getItemCodeMapping().equals(sap.getScreenItemCode()) && out.getAreaCodeMapping().equals(sap.getAreaCodeOfItem()) && out.getFormCodeMapping().equals(sap.getFormCodeOfItem())) {
								outputBeanTmp = out;
								break;
							}
						}
						if(outputBeanTmp != null) {
							String outputTmp = (outputBeanTmp.getOutputBeanCode() != null) ? (outputBeanTmp.getOutputBeanCode()) : ("");
							String qpFormat = "";
							if(sap.getOutputPhysicalDataTypeOfItem() != null && sap.getOutputPhysicalDataTypeOfItem().equals(DbDomainConst.BaseType.DATE_BASETYPE)) {
								qpFormat = "=<qp:formatDate value=\"${item." + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else if(sap.getOutputPhysicalDataTypeOfItem() != null && sap.getOutputPhysicalDataTypeOfItem().equals(DbDomainConst.BaseType.TIME_BASETYPE)) {
								qpFormat = "=<qp:formatTime value=\"${item." + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else if(sap.getOutputPhysicalDataTypeOfItem() != null && sap.getOutputPhysicalDataTypeOfItem().equals(DbDomainConst.BaseType.DATETIME_BASETYPE)) {
								qpFormat = "=<qp:formatDateTime value=\"${item." + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else {
								qpFormat = "=${item." + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}";
							}
							requestParam += preRequest + requestCode + qpFormat + "";
							indexOfParams++;
						}
					} else {
						OutputBean  outputBeanTmp = new OutputBean();
						for (OutputBean out : listOfOutputBean) {
							if (out.getItemCodeMapping().equals(sap.getScreenItemCode())) {
								outputBeanTmp = out;
								break;
							}
						}
						if(outputBeanTmp != null) {
							String parentOutputBeanTmp = (outputBeanTmp.getParentOutputBeanCode() != null) ? (outputBeanTmp.getParentOutputBeanCode()+".") : ("");
							String outputTmp = (outputBeanTmp.getOutputBeanCode() != null) ? (outputBeanTmp.getOutputBeanCode()) : ("");
							
							String qpFormat = "";
							if(sap.getOutputPhysicalDataTypeOfItem() != null && sap.getOutputPhysicalDataTypeOfItem().equals(DbDomainConst.BaseType.DATE_BASETYPE)) {
								qpFormat = "=<qp:formatDate value=\"${"+modelAttributeOut+ "." + GenerateSourceCodeUtil.normalizedPropertiesJSP(parentOutputBeanTmp) + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else if(sap.getOutputPhysicalDataTypeOfItem() != null && sap.getOutputPhysicalDataTypeOfItem().equals(DbDomainConst.BaseType.TIME_BASETYPE)) {
								qpFormat = "=<qp:formatTime value=\"${"+modelAttributeOut+ "." + GenerateSourceCodeUtil.normalizedPropertiesJSP(parentOutputBeanTmp) + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else if(sap.getOutputPhysicalDataTypeOfItem() != null && sap.getOutputPhysicalDataTypeOfItem().equals(DbDomainConst.BaseType.DATETIME_BASETYPE)) {
								qpFormat = "=<qp:formatDateTime value=\"${"+modelAttributeOut+ "." + GenerateSourceCodeUtil.normalizedPropertiesJSP(parentOutputBeanTmp) + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}\"/>";
							} else {
								qpFormat = "=${"+modelAttributeOut+ "." + GenerateSourceCodeUtil.normalizedPropertiesJSP(parentOutputBeanTmp) + GenerateSourceCodeUtil.normalizedPropertiesJSP(outputTmp) + "}";
							}
							requestParam += preRequest + requestCode + qpFormat + "";
							indexOfParams++;
						}
					
					}
				}
			}
		}
		
		//Get default value
		String defaultValue = "";
		if(screenItem.getDefaultValue() != null && StringUtils.isNotBlank(screenItem.getDefaultValue())) {
			defaultValue = screenItem.getDefaultValue();
		} else {
			defaultValue = "null";
		}
		if(tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.REGISTER)) {
			if(defaultValue != null && StringUtils.isNotBlank(defaultValue) && defaultValue != "null") {
				value = "value=\""+defaultValue+"\" ";
				valueNew = defaultValue;
			} else {
				value = "";
			}
		}
		
		
		String enableConfirm = "";
		String messageId = "";
		if(screenItem.getEnableConfirm() != null && ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM.equals(screenItem.getEnableConfirm()) && screenItem.getMessageConfirm() != null) {
			enableConfirm = "qp-link-dialog-confirm";
			messageId = "messageId="+"\""+ screenItem.getMessageConfirm().getMessageCode()+"\"";
		}
		
		// Set for data source type
		
		String optionSubmit = "";
		String optionDisplay = "";
		String dataSource = "";
		String parentOutputBeanSelect = "";
		String outputBeanSelect = "";
		String blogicCodeHasDataSource = "";
		for(OutputBean output : listOfOutputBean) {
			if(output.getScreenItemId().equals(Long.parseLong(item.getScreenItemId()))) {
				if(output.getMappingType() != null && output.getMappingType().equals(MAPPING_TYPE_OPTION_SUBMIT)) {
					optionSubmit = output.getOutputBeanCode();
				} else if(output.getMappingType() != null && output.getMappingType().equals(MAPPING_TYPE_OPTION_DISPLAY)) {
					optionDisplay = output.getOutputBeanCode();
				} else if(output.getMappingType() != null && output.getMappingType().equals(MAPPING_TYPE_DATA_SOURCE)) {
					blogicCodeHasDataSource = output.getBusinessLogicCode();
					parentOutputBeanSelect = (output.getParentOutputBeanCode() != null) ? (output.getParentOutputBeanCode()+".") : ("");
					outputBeanSelect = (output.getOutputBeanCode() != null) ? (output.getOutputBeanCode()) : ("");
					dataSource = parentOutputBeanSelect + outputBeanSelect;
				} 
			}
		}
		
		String maxLength = "";
		if(item.getMaxlength() != null && item.getMaxlength() != "" && Integer.parseInt(item.getMaxlength()) > 0) {
			maxLength = "maxlength=\"" + item.getMaxlength() + "\"";
		}
		
		//Set mapping type for screenItem
		String element = "";
		int dataType = Integer.parseInt(item.getDatatype());
		switch (dataType) {
		case 0:
			String mustMatch = "";
			if(screenItem.getAllowAnyInput() != null && screenItem.getAllowAnyInput().equals(0)) {
				mustMatch = "mustMatch=\"false\"";
			} else {
				mustMatch = "mustMatch=\"true\"";
			}
			
			String displayValueForAu = "";
			String valueForAu = "";
			for(OutputBean out : listOfOutputBean) {
				if(out.getScreenItemId() != null && screenItem.getScreenItemId() != null) {
					// Display = displayValue
					if(out.getScreenItemId().equals(screenItem.getScreenItemId()) && out.getMappingType() != null && DbDomainConst.MappingType.DISPLAY.equals(out.getMappingType())) {
						String parent = (out.getParentOutputBeanCode() != null) ? (out.getParentOutputBeanCode()) : ("");
						String output = (out.getOutputBeanCode() != null) ? (out.getOutputBeanCode()) : ("");
						if(output != null && output != "") {
							parent += ".";
						}
						displayValueForAu = modelAttributeOut + "." + parent + output;
						if(output == null || output == "") {
							displayValueForAu = "";
						}
					}
					// Select = Submit = value
					if(out.getScreenItemId().equals(screenItem.getScreenItemId()) && out.getMappingType() != null && DbDomainConst.MappingType.SELECT.equals(out.getMappingType())) {
						String parent = (out.getParentOutputBeanCode() != null) ? (out.getParentOutputBeanCode()) : ("");
						String output = (out.getOutputBeanCode() != null) ? (out.getOutputBeanCode()) : ("");
						if(parent != null && parent != "") {
							if(out.getParentOutputBeanArrayFlag() != null && out.getParentOutputBeanArrayFlag().equals("t")) {
								parent += "[status.index].";
							} else {
								parent += ".";
							}
							valueForAu = modelAttributeOut + "." + parent + output;
						} else {
							valueForAu = modelAttributeOut + "." + output;
						}
						if(output == null || output == "") {
							valueForAu = "";
						}
					}
				}
			}
			
			String submitColumn = "";
			
			AutocompleteDesign autocompleteDesign = autocompleteDesignRepository.getAutocompleteDesignByScreenItem(screenItem.getAutocompleteId());
			
			if(autocompleteDesign != null) {
				String[] displayColumn = autocompleteDesign.getDisplayColumnFlag().split(",");
			
				submitColumn = autocompleteDesign.getSubmitColumn();
				submitColumn = "output" + String.format("%2s", submitColumn).replace(' ', '0');
				
				String display = "";
				for(int i = 2, j = 1 ; i < displayColumn.length ; i++, j++) {
					if (displayColumn[i].equals("1")) {
						display += "output" + String.format("%2s", j).replace(' ', '0');
						
						if (i < displayColumn.length - 1) {
							display += ",";
						}
					}
				}
				
				display = display.substring(0, display.length() - 1);
				
				if (DbDomainConst.ScreenPatternType.MODIFY.equals(tempScreenDesign.getScreenPatternType())) {
					if(screenItem.getAllowAnyInput() != null && screenItem.getAllowAnyInput().equals("1")) {
						element = "<qp:autocomplete displayValue=\""+displayValueForAu+"\" value=\"${"+valueForAu+"}\" selectSqlId=\"QP_ATC_"+autocompleteDesign.getAutocompleteId()+"\" name=\""+ nameElement+"\" optionValue=\""+submitColumn+"\" optionLabel=\""+display+"\" "+mustMatch+" "+atcInputStr+"></qp:autocomplete>";
					} else {
						element = "<qp:autocomplete displayValue=\""+displayValueForAu+"\" value=\"${"+valueForAu+"}\" selectSqlId=\"QP_ATC_"+autocompleteDesign.getAutocompleteId()+"\" name=\""+ nameElement+"\" optionValue=\""+submitColumn+"\" optionLabel=\""+display+"\" "+mustMatch+" "+atcInputStr+"></qp:autocomplete>";
					}
				} else {
					if(screenItem.getAllowAnyInput() != null && screenItem.getAllowAnyInput().equals("1")) {
						element = "<qp:autocomplete selectSqlId=\"QP_ATC_"+autocompleteDesign.getAutocompleteId()+"\" name=\""+ nameElement+"\" optionValue=\""+submitColumn+"\" optionLabel=\""+display+"\" "+mustMatch+" "+atcInputStr+"></qp:autocomplete>";
					} else {
						element = "<qp:autocomplete selectSqlId=\"QP_ATC_"+autocompleteDesign.getAutocompleteId()+"\" name=\""+ nameElement+"\" optionValue=\""+submitColumn+"\" optionLabel=\""+display+"\" "+mustMatch+" "+atcInputStr+"></qp:autocomplete>";
					}
				}
			}
			else {
				element = "<div class=\"input-group\" style=\"width:100% "+ item.getStyle() +"\"><input type=\"text\" class=\"form-control\" ";
				element += " name=\"" + item.getColumnname() + "\" "+maxLength+" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" "+mustMatch+" minlength=\"0\" placeholder=\"\" autocomplete=\"off\">" + "	<input type=\"hidden\" value=\"\">" + "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" + "		<span class=\"caret\"></span> " + "	</span>" + "</div>";
			}
			break;
		case 1:
			
			if(item.getEnablePassword() != null && item.getEnablePassword().equals("1")) {
				element = "<input type=\"password\" style=\"width:100%; " + item.getStyle() + "\" class=\"form-control qp-input-text\" name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" />";
			} else {
				element = "<input type=\"text\" style=\"width:100%; " + item.getStyle() + "\" class=\"form-control qp-input-text\" name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" />";
			}
			
			break;
		case 15:
		case 16:
		case 18:
			element = "<input style=\"width:100%; " + item.getStyle() + "\" type=\"text\" class=\"form-control qp-input-text\" name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+"/>";
			break;
		case 21:
			//SQL Builder
			if(item.getDatasourcetype().equals("1") || item.getDatasourcetype().equals("")) {
				if(screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
					element += "<label style=\"" + item.getStyle() + "\">${item."+valueElement+"}</label>";
				} else {
					if (StringUtils.isNotBlank(parentOutputBean) && StringUtils.isNotBlank(outputBean)) {
						valueElement = parentOutputBean + outputBean;
					}
					element += "<label style=\"" + item.getStyle() + "\">${"+modelAttributeOut+"."+valueElement+"}</label>";
				}
			}
			//Codelist
			else if(item.getDatasourcetype().equals("2")){
				StringBuilder codelistCodeNew = new StringBuilder();
				if (item.getLocalCodelist().equals(String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST))) {
					CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(codelist.getCodeListCode());
				} else if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
					codelistCodeNew.append(newPackageName);
					
					codelistCodeNew.append(screenItem.getScreenDesignCodeCodeListId() + "_" + screenItem.getScreenItemIdCodeListId());
				}
				
				String lastCodelist = "CL_" + (codelistCodeNew.toString().toUpperCase());
				
				element += "\n<c:if test=\"${not empty "+lastCodelist+".get("+valueForEach+".toString())}\" >";
				element += "\n\t<label style=\"" + item.getStyle() + "\"><qp:message code=\"${"+lastCodelist+".get("+valueForEach+".toString())}\"/></label>";
				element += "\n</c:if>";
				element += "\n<c:if test=\"${empty "+lastCodelist+".get("+valueForEach+".toString())}\" >";
				element += "\n\t<c:set var=\"split\" value=\"\" />";
				if(screenItem.getScreenArea() != null && screenItem.getScreenArea().getAreaType() != null && DbDomainConst.AreaType.LIST_ENTITIES.equals(screenItem.getScreenArea().getAreaType())) {
					element += "\n\t<c:if test=\"${fn:contains(item."+outputBean+",';')}\">";
				} else {
					element += "\n\t<c:if test=\"${fn:contains("+valueForEach+",';')}\">";
				}
				element += "\n\t\t<c:set var=\"split\" value=\";\" />";
				element += "\n\t</c:if>";
				element += "\n\t\t<c:forTokens items=\"${"+valueForEach+"}\" delims=\";\" var=\"itemDelim\">";
				element += "\n\t\t\t<label style=\"" + item.getStyle() + "\"><qp:message code=\"${"+lastCodelist+".get(itemDelim.toString())}\"/>${split}</label>";
				element += "\n\t\t</c:forTokens>";
				element += "\n</c:if>";
			}
			
			break;
		case 2:
			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<input style=\"" + item.getStyle() + "\" type=\"text\" class=\"form-control qp-input-from qp-input-integer pull-left\" name=\"\" value=\"\" "+maxLength+" />" + "" + "<div class=\"qp-separator-from-to\">~</div>" + "<input type=\"text\" class=\"form-control qp-input-to qp-input-integer pull-right\"";
					element += " name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" />" + "</div>";
				} else {
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<input style=\"" + item.getStyle() + "\" type=\"text\" class=\"form-control qp-input-integer pull-left\" name=\""+screenItem.getItemCode()+"\" value=\""+valueNew+"\" "+maxLength+"/>";
				}
			} else {
				element = "<input style=\""+item.getStyle()+"\" type=\"text\" class=\"form-control qp-input-integer\" name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" />";
			}
			break;
		case 3:
			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<input style=\"" + item.getStyle() + "\" type=\"text\" class=\"form-control qp-input-from qp-input-float pull-left\" name=\""+screenItem.getItemCode()+"\" value=\""+valueNew+"\" "+maxLength+"/>" + "" + "<div class=\"qp-separator-from-to\">~</div>" + "<input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" class=\"form-control qp-input-to qp-input-float pull-right\"";
					element += " name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+"/>" + "</div>";
				} else {
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<input style=\"" + item.getStyle() + "\" type=\"text\" class=\"form-control qp-input-float pull-left\" name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" />";
				}
			} else {
				element = "<input style=\"" + item.getStyle() + "\" type=\"text\"  class=\"form-control qp-input-float\" name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" />";
			}
			break;
		case 8:
			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<input style=\"" + item.getStyle() + "\" type=\"text\" class=\"form-control qp-input-from qp-input-currency pull-left\" name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" />" + "" + "<div class=\"qp-separator-from-to\">~</div>" + "<input  ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" class=\"form-control qp-input-to qp-input-currency pull-right\"";
					element += " name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" />" + "</div>";
				} else {
					element += "<div style=\"width:100% " + item.getStyle() + "\">" + "<input style=\"" + item.getStyle() + "\" type=\"text\" class=\"form-control qp-input-from qp-input-currency pull-left\" name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+"/>";
				}
			} else {
				element = "<input style=\"" + item.getStyle() + "\" type=\"text\"  class=\"form-control qp-input-currency\" name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+"/>";
			}
			break;
		case 4:
			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div>" + "<div class=\"input-group date qp-input-from-datepicker pull-left\">";
					element += "<input type=\"text\" class=\"form-control qp-input-from\"";
					element += " name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>" + "<div class=\"qp-separator-from-to\">~</div>" + "<div class=\"input-group date qp-input-from-datepicker pull-left\">";
					element += "<input  type=\"text\" class=\"form-control\" ";
					element += " name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" style=\"" + item.getStyle() + "\" />";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				} else {
					element += "<div>" + "<div class=\"input-group date qp-input-datepicker pull-left\">";
					element += "<input type=\"text\" class=\"form-control qp-input-from\"";
					element += " name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				}
			} else {
				element += "<div class=\"input-group date qp-input-datepicker\">" + "<span><input type=\"text\" class=\"form-control\" name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" style=\"" + item.getStyle() + "\" /></span>";
				element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
			}
			break;
		case 14:
			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div>"+"<div class=\"input-group date qp-input-from-datetimepicker-detail pull-left\">";
					element += "<input type=\"text\" class=\"form-control\"";
					element += " name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" style=\"" + item.getStyle() + "\" />";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>" + "<div class=\"qp-separator-from-to\">~</div>" + "<div class=\"input-group date qp-input-to-datetimepicker-detail pull-rigth\">";
					element += "<input  type=\"text\" class=\"form-control\"";
					element += " name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" style=\"" + item.getStyle() + "\" />";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				} else {
					element += "<div>" + "<div class=\"input-group date qp-input-datetimepicker-detail pull-left\">";
					element += "<input type=\"text\" class=\"form-control\"";
					element += " name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" style=\"" + item.getStyle() + "\" />";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				}
			} else {
				element += "<div class=\"input-group date qp-input-datetimepicker-detail\">" + "<span><input type=\"text\" class=\"form-control\" name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" style=\"" + item.getStyle() + "\" /></span>";
				element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
			}

			break;
		case 9:
			if (screenPatternType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")){
					element += "<div>" + "<div class=\"input-group date qp-input-from-timepicker pull-left\">";
					element += "<input  type=\"text\" class=\"form-control\" ";
					element += " name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>" + "<div class=\"qp-separator-from-to\">~</div>" + "<div class=\"input-group date qp-input-to-timepicker pull-rigth\">";
					element += "<input  type=\"text\" class=\"form-control\"";
					element += " name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>";
					element += "</div>";
				} else {
					element += "<div>" + "<div class=\"input-group date qp-input-timepicker pull-left\">";
					element += "<input type=\"text\" class=\"form-control\" ";
					element += " name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" style=\"" + item.getStyle() + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>";
					element += "</div>";
				}
			} else {
				element += "<div class=\"input-group date qp-input-timepicker\">" + "<span><input type=\"text\" class=\"form-control\" name=\""+nameElement+"\" value=\""+valueNew+"\" "+maxLength+" style=\"" + item.getStyle() + "\"/></span>";
				element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>";
			}
			break;
		//Radio
		case 5:
			//Datasource
			if (item.getDatasourcetype().equals("1")) {
				/*element += "<form:radiobuttons path=\""+nameElement+"\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\" cssClass=\"qp-input-radio-margin qp-input-radio\"></form:radiobuttons>";*/
				element += "<c:forEach var=\"item\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\"><label>";
				element += "\n\t<input type=\"radio\" name=\""+nameElement+"\" value=\""+valueNew+"\" class=\"qp-input-radio-margin qp-input-radio\" />${item."+optionDisplay+"}</label>";
				element += "\n</c:forEach>";
			} 
			//Codelist
			else if (item.getDatasourcetype().equals("2")) {
				StringBuilder codelistCodeNew = new StringBuilder();
				if (String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST).equals(item.getLocalCodelist()) && StringUtils.isNoneBlank(item.getCodelistCode())) {
					StringBuilder sb = new StringBuilder();
					CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(codelist.getCodeListCode());
					String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
					sb.append("\n<c:forEach var=\"item\" items=\"${"+nameCodelist+"}\">");
					sb.append("\n\t<input type=\"radio\" name=\""+nameElement+"\" value=\""+valueNew+"\" class=\"qp-input-radio-margin qp-input-radio\" />&nbsp;<qp:message code=\"${"+nameCodelist+".get(item.key)}\" />");
					sb.append("\n</c:forEach>");
					element = sb.toString();
				} else if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
					StringBuilder sb = new StringBuilder();
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(tempScreenDesign.getScreenCode() + "_"  + screenItem.getScreenItemId());
					String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
					sb.append("\n<c:forEach var=\"item\" items=\"${"+nameCodelist+"}\">");
					sb.append("\n\t<input type=\"radio\" name=\""+nameElement+"\" value=\""+valueNew+"\" class=\"qp-input-radio-margin qp-input-radio\" />&nbsp;<qp:message code=\"${"+nameCodelist+".get(item.key)}\" />");
					sb.append("\n</c:forEach>");
					element = sb.toString();
				}
				
			}
			else {
				element += "<label "+item.getStyle()+"> <input type=\"radio\" class=\"qp-input-radio qp-input-radio-margin\"";
				element += " name=\"" + item.getColumnname() + "\"/>"+MessageUtils.getMessage("sc.screendesign.0137") + "1</label>";
				element += "<label "+item.getStyle()+"> <input type=\"radio\" class=\"qp-input-radio qp-input-radio-margin\" ";
				element += " name=\"" + item.getColumnname() + "\"/>"+MessageUtils.getMessage("sc.screendesign.0137") + "2</label>";
			}
			break;
		//Checkbox
		case 6:
			// 8 = Boolean
			if("8".equals(item.getPhysicaldatatype())) {
				StringBuilder sb = new StringBuilder();
				if(tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.MODIFY)) {
					/*sb.append("<input type=\"checkbox\" name=\""+nameElement+"\" value=\""+valueNew+"\" class=\"qp-input-checkbox-margin qp-input-checkbox\"/>");*/
					
					sb.append("<input type=\"checkbox\" name=\""+nameElement+"\" value=\"true\" class=\"qp-input-checkbox-margin qp-input-checkbox\"/>");
					element = sb.toString();
				/*} else if(tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.REGISTER)) {*/
				} else if (tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.SEARCH)) {
					element += "<input type=\"checkbox\" name=\""+nameElement+"\" value=\"true\" class=\"qp-input-checkbox-margin qp-input-checkbox\"/>&nbsp True";
					element += "<input type=\"checkbox\" name=\""+nameElement+"\" value=\"true\" class=\"qp-input-checkbox-margin qp-input-checkbox\"/>&nbsp False";
				}
				else {
					element = "<input type=\"checkbox\" name=\""+nameElement+"\" value=\"true\" class=\"qp-input-checkbox-margin qp-input-checkbox\"/>";
				}
			} else {
				//Datasource
				if (item.getDatasourcetype().equals("1")) {
					/*element += "<form:checkboxes path=\""+nameElement+"\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\" cssClass=\"qp-input-checkbox-margin qp-input-checkbox\"></form:checkboxes>";*/
					element += "<c:forEach var=\"item\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\"><label>";
					element += "\n\t<input type=\"checkbox\" name=\""+nameElement+"\" value=\""+valueNew+"\" class=\"qp-input-checkbox-margin qp-input-checkbox\" />${item."+optionDisplay+"}</label>";
					element += "\n</c:forEach>";
				}
				//Codelist
				else if (item.getDatasourcetype().equals("2")) {
					StringBuilder sb = new StringBuilder();
					StringBuilder codelistCodeNew = new StringBuilder();
					if (item.getLocalCodelist().equals(String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST)) && StringUtils.isNoneBlank(item.getCodelistCode())) {
						CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
						codelistCodeNew.append(newPackageName);
						codelistCodeNew.append(codelist.getCodeListCode());
						String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
						sb.append("\n<c:forEach var=\"item\" items=\"${"+nameCodelist+"}\">");
						sb.append("\n\t<input type=\"checkbox\" name=\""+nameElement+"\" value=\""+valueNew+"\" class=\"qp-input-checkbox-margin qp-input-checkbox\" />&nbsp;<qp:message code=\"${"+nameCodelist+".get(item.key)}\" />");
						sb.append("\n</c:forEach>");
						element = sb.toString();
					} else if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
						codelistCodeNew.append(newPackageName);
						codelistCodeNew.append(tempScreenDesign.getScreenCode() + "_"  + screenItem.getScreenItemId());
						String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
						sb.append("\n<c:forEach var=\"item\" items=\"${"+nameCodelist+"}\">");
						sb.append("\n\t<input type=\"checkbox\" name=\""+nameElement+"\" value=\""+valueNew+"\" class=\"qp-input-checkbox-margin qp-input-checkbox\" />&nbsp;<qp:message code=\"${"+nameCodelist+".get(item.key)}\" />");
						sb.append("\n</c:forEach>");
						element = sb.toString();
					}
				}
				else {
					element += "<label "+item.getStyle()+"><input type=\"checkbox\" class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					element += " name=\"" + item.getColumnname() + "\" />"+ MessageUtils.getMessage("sc.screendesign.0137") + "1</label>";
					element += "<label "+item.getStyle()+"><input type=\"checkbox\" class=\"qp-input-checkbox-margin qp-input-checkbox\" ";
					element += " name=\"" + item.getColumnname() + "\" />"+ MessageUtils.getMessage("sc.screendesign.0137") + "2</label>";
				}
			}
			
			break;
		//Select
		case 7:
			String blankItem = "";
			if(screenItem.getShowBlankItem() != null && SHOW_BLANK_ITEM.equals(screenItem.getShowBlankItem())) {
				blankItem = "<option value=\"\"></option>";
			}
			if (item.getDatasourcetype().equals("1")) {
				/*element += "<form:select path=\""+nameElement+"\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\" cssClass=\"form-control qp-input-select\"></form:select>";*/
				
				element += "<select name=\""+nameElement+"\" class=\"form-control qp-input-select\">";
				element += blankItem;
				element += "\n\t<c:forEach var=\"item\" items=\"${"+blogicCodeHasDataSource+"DataSourceOutputBean."+dataSource+"}\">";
				element += "\n\t\t<option value=\""+valueNew+"\">${item."+optionDisplay+"}</option>";
				element += "\n\t</c:forEach>";
				element += "</select>";
				
			} else if (item.getDatasourcetype().equals("2")) {
				StringBuilder codelistCodeNew = new StringBuilder();
				if (item.getLocalCodelist().equals(String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST)) && StringUtils.isNoneBlank(item.getCodelistCode())) {
					CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(codelist.getCodeListCode());
					
				} else if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
					
					codelistCodeNew.append(newPackageName);
					codelistCodeNew.append(tempScreenDesign.getScreenCode() + "_"  + screenItem.getScreenItemId());
				}
				String nameCodelist = ("CL_" + codelistCodeNew.toString()).toUpperCase();
				element += "<select name=\""+nameElement+"\" class=\"form-control qp-input-select\" >";
				element += blankItem;
				element += "\n\t<c:forEach var=\"item\" items=\"${"+nameCodelist+"}\">";
				element += "\n\t\t<option value=\""+valueNew+"\"><qp:message code=\"${"+nameCodelist+".get(item.key)}\"/></option>";
				element += "\n\t</c:forEach>";
				element += "</select>";
			}
			else {
				element += "<select name=\""+item.getColumnname()+"\" class=\"form-control qp-input-select\" >";
				element += "<option></option><option>" + MessageUtils.getMessage("sc.screendesign.0137") + "1</option><option>" + MessageUtils.getMessage("sc.screendesign.0137") + "2</option>";
				element += "</select>";
			}

			break;
		case 10:
			
			element += "<textarea name=\""+nameElement+"\" value=\""+valueNew+"\" class=\"form-control qp-input-textarea\"></textarea>";
			break;
			
		case 22:
		case 11:
			Integer navigateToScreenTemplateTypeLink = -1;
			if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getToBlogicScreenTemplateType() != null) {
				navigateToScreenTemplateTypeLink = screenItem.getScreenAction().getToBlogicScreenTemplateType();
			} else {
				navigateToScreenTemplateTypeLink = 3;
			}
			
			if(screenItem.getEnableConfirm() != null && ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM.equals(screenItem.getEnableConfirm()) && screenItem.getMessageConfirm() != null) {
				enableConfirm = "qp-link-dialog-confirm";
				messageId = "messageId="+"\""+ screenItem.getMessageConfirm().getMessageCode()+"\"";
			}
			
			StringBuilder codelistCodeNew = new StringBuilder();
			if (item.getLocalCodelist().equals(String.valueOf(DbDomainConst.CodelistType.SYSTEM_CODE_LIST)) && StringUtils.isNoneBlank(item.getCodelistCode())) {
				CodeList codelist = codeListRepository.getCodeList(Long.parseLong(item.getCodelistCode()));
				codelistCodeNew.append(newPackageName);
				codelistCodeNew.append(codelist.getCodeListCode());
			} else if ((item.getLocalCodelist()).equals(String.valueOf(DbDomainConst.CodelistType.SCREEN_CODE_LIST))) {
				codelistCodeNew.append(newPackageName);
				codelistCodeNew.append(screenItem.getScreenDesignCodeCodeListId() + "_" + screenItem.getScreenItemIdCodeListId());
			}
			String lastCodelist = "CL_" + (codelistCodeNew.toString().toUpperCase());
			
			String elementName = "";
			if(screenItem.getDataSourceType() != null && screenItem.getDataSourceType().equals(2)) {
				elementName = "${"+lastCodelist+".get(item."+screenItem.getOutputBeanCode()+".toString())}";
			} else {
				elementName = "${item."+screenItem.getOutputBeanCode()+"}";
			}
			
			String classPopUp = "qp-link-popup";
			
			String qpFormat = "";
			if(screenItem.getPhysicalDataType() != null) {
				if (screenItem.getPhysicalDataType().equals(1) || screenItem.getPhysicalDataType().equals(2) || screenItem.getPhysicalDataType().equals(3)) {
					qpFormat = "qp:formatText";
				} else if (screenItem.getPhysicalDataType().equals(5) || screenItem.getPhysicalDataType().equals(6) || screenItem.getPhysicalDataType().equals(7)) {
					qpFormat = "qp:formatInteger";
				} else if (screenItem.getPhysicalDataType().equals(4) || screenItem.getPhysicalDataType().equals(16) || screenItem.getPhysicalDataType().equals(17)) { 
					qpFormat = "qp:formatFloat";
				} else if (screenItem.getPhysicalDataType().equals(10)) {
					qpFormat = "qp:formatDate";
				} else if (screenItem.getPhysicalDataType().equals(15)) {
					qpFormat = "qp:formatCurrency";
				} else if (screenItem.getPhysicalDataType().equals(10)) {
					qpFormat = "qp:formatDate";
				} else if (screenItem.getPhysicalDataType().equals(11)) {
					qpFormat = "qp:formatTime";
				} else if (screenItem.getPhysicalDataType().equals(12)) {
					qpFormat = "qp:formatDateTime";
				} else {
					qpFormat = "qp:formatText";
				}
			} else {
				qpFormat = "qp:formatText";
			}
			
			String hoverStyle = "";
			if(item.getHoverStyle() != null && item.getHoverStyle() != "") {
				hoverStyle = " onmouseover=\"this.setAttribute('style', '" + item.getHoverStyle() + "')\" ";
			}
			String style = "";
			String mouseout = "";
			if(item.getStyle() != null && item.getStyle() != "") {
				mouseout = " onmouseout=\"this.setAttribute('style', '" + item.getStyle() + "')\" ";
				style = " style=\"" + item.getStyle() + "\" ";
			}
			StringBuffer strPermission = new StringBuffer();
			String strBlogicCode = new String();
			
			if(screenItem.getScreenAction().getNavigateToBlogicCode() != null && screenItem.getScreenAction().getNavigateToBlogicCode() != "") {
				strPermission = new StringBuffer(screenItem.getScreenAction().getNavigateToBlogicCode());
				strPermission.setCharAt(0, Character.toUpperCase(strPermission.charAt(0)));
				strBlogicCode = GenerateSourceCodeUtil.normalizedURL(screenItem.getScreenAction().getNavigateToBlogicCode());
			}
			String toModuleCode = "";
			if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getToModuleCode() != null) {
				toModuleCode = screenItem.getScreenAction().getToModuleCode();
			}
			element += "<qp:authorization permission=\""+GenerateSourceCodeUtil.normalizedURL(toModuleCode + strPermission.toString())+"\">";
			/*element += "<span class=\"qp-link-header-icon glyphicon "+glyphicon+"\"></span>";*/
			if((TEMPLATE_TYPE_NORMAL.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_NORMAL.equals(navigateToScreenTemplateTypeLink)) || 
					(TEMPLATE_TYPE_POPUP.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_POPUP.equals(navigateToScreenTemplateTypeLink))) {
				if(screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
					element += "<a "+messageId+" class=\" "+enableConfirm+"\" "+hoverStyle+" "+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(toModuleCode)  + "/" +  strBlogicCode.toString() + requestParam + "\" "+style+"><"+qpFormat+" value=\""+elementName+"\" /></a>";
				} else {
					element += "<a "+messageId+" class=\" "+enableConfirm+"\" "+hoverStyle+" "+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(toModuleCode) + "/" + strBlogicCode.toString() + requestParam + "\" "+style+"><qp:message code=\"" + item.getLabel() + "\"/></a>";
				}
			}
			if(TEMPLATE_TYPE_NORMAL.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_POPUP.equals(navigateToScreenTemplateTypeLink)) {
				if(screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
					element += "<a "+messageId+" class=\" "+enableConfirm+" "+classPopUp+"\" "+hoverStyle+" "+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(toModuleCode) + "/" + strBlogicCode.toString() + requestParam + "\" "+style+"><"+qpFormat+" value=\""+elementName+"\" /></a>";
				} else {
					element += "<a "+messageId+" class=\" "+classPopUp+"\" "+hoverStyle+" "+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(toModuleCode) + "/" + strBlogicCode.toString() + requestParam + "\" "+style+"><qp:message code=\"" + item.getLabel() + "\"/></a>";
				}
			}
			if(TEMPLATE_TYPE_POPUP.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_NORMAL.equals(navigateToScreenTemplateTypeLink)) {
				if(screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
					element += "<a "+messageId+" class=\" "+enableConfirm+" qp-link-popup-navigate\" "+hoverStyle+" "+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(toModuleCode) + "/" + strBlogicCode.toString() + requestParam + "\" "+style+"><"+qpFormat+" value=\""+elementName+"\" /></a>";
				} else {
					element += "<a "+messageId+" class=\" "+enableConfirm+" qp-link-popup-navigate\" "+hoverStyle+" "+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(toModuleCode) + "/" + strBlogicCode.toString() + requestParam + "\" "+style+"><qp:message code=\"" + item.getLabel() + "\"/></a>";
				}
			}
			//Do not setting navigate to
			if(navigateToScreenTemplateTypeLink == null || navigateToScreenTemplateTypeLink == 3) {
				element += "<a "+messageId+" class=\" "+enableConfirm+"\" "+hoverStyle+" "+mouseout+" href=\"${pageContext.request.contextPath}/" + GenerateSourceCodeUtil.normalizedURL(toModuleCode)  + "/" +  strBlogicCode.toString() + requestParam + "\" "+style+"><qp:message code=\"" + item.getLabel() + "\"/></a>";
			}
						
			element += "</qp:authorization>";

			break;
		case 12:
			if(!tempScreenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.SEARCH)) {
				element += "<input class=\"qp-input-file\" type=\"file\" name=\""+nameElement+"\" value=\""+valueNew+"\" style=\"" + item.getStyle() + "\"/>";
			}
			break;
		case 13:
			String beforePost = "beforePostNotConfirm(this)";
			Integer navigateToScreenTemplateTypeButton;
			if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getToBlogicScreenTemplateType() != null) {
				navigateToScreenTemplateTypeButton = screenItem.getScreenAction().getToBlogicScreenTemplateType();
			} else {
				navigateToScreenTemplateTypeButton = 3;
			}
			
			StringBuffer strPermissionButton = new StringBuffer();
			if(screenItem.getScreenAction().getNavigateToBlogicCode() != null && screenItem.getScreenAction().getNavigateToBlogicCode() != "") {
				strPermissionButton = new StringBuffer(screenItem.getScreenAction().getNavigateToBlogicCode());
				strPermissionButton.setCharAt(0, Character.toUpperCase(strPermissionButton.charAt(0)));
			}
			String toModuleCodeButton = "";
			if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getToModuleCode() != null) {
				toModuleCodeButton = screenItem.getScreenAction().getToModuleCode();
			}
			element += "<qp:authorization permission=\""+GenerateSourceCodeUtil.normalizedURL(toModuleCodeButton + strPermissionButton.toString())+"\">";
			if(screenItem.getEnableConfirm() != null && ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM.equals(screenItem.getEnableConfirm()) && screenItem.getMessageConfirm() != null) {
				enableConfirm = "qp-dialog-confirm";
				messageId = "messageId="+"\""+ screenItem.getMessageConfirm().getMessageCode()+"\"";
				beforePost = "beforePostHaveConfirm(this)";
			}
			
			String buttonType = "";
			//Default or Save
			if( (screenItem.getButtonType() != null && (ScreenDesignConst.ButtonType.BUTTON_TYPE_DEFAULT.equals(screenItem.getButtonType()) || ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE.equals(screenItem.getButtonType()))) 
					|| screenItem.getButtonType() == null) {
				buttonType = "qp-button qp-button-type";
			}
			//Delete
			else if(screenItem.getButtonType() != null && ScreenDesignConst.ButtonType.BUTTON_TYPE_DELETE.equals(screenItem.getButtonType())) {
				buttonType = "qp-button-type-warning";
			} 
			//Client
			else if(screenItem.getButtonType() != null && ScreenDesignConst.ButtonType.BUTTON_TYPE_CLIENT.equals(screenItem.getButtonType())) {
				buttonType = "qp-button qp-button-type-client";
			}
			
			String buttonIcon = "<i class=\"" + item.getIcon() + "\"></i>";
			String buttonContent = "";
			if(StringUtils.isNotBlank(item.getLabelText())) {
				buttonContent = "<qp:message code=\""+item.getLabel()+"\" />";
			}
			String buttonContentTotal = "";
			if (StringUtils.isNotEmpty(item.getIcon()) && item.getIcon() != "" && item.getIcon() != null) {
				buttonContentTotal += buttonIcon;
				if (StringUtils.isNotEmpty(item.getLabelText()) && item.getLabelText() != "" && 
						((item.getShowLabel() != null && "1".equals(item.getShowLabel())) || (item.getShowLabel() == null || item.getShowLabel() == "" ))) {
					buttonContentTotal += "&nbsp;&nbsp;";
				}
			} 
			if (StringUtils.isNotEmpty(item.getLabelText()) && item.getLabelText() != "" && 
					((item.getShowLabel() != null && "1".equals(item.getShowLabel())) || (item.getShowLabel() == null || item.getShowLabel() == "" ))) {
				buttonContentTotal += buttonContent;
			}
			
			//Button is submit
			if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getSubmitMethodType() != null && screenItem.getScreenAction().getSubmitMethodType().equals(ScreenActionConst.SUBMIT_METHOD_TYPE_POST)) {
				String blogicCode = "";
				if(screenItem.getScreenAction().getNavigateToBlogicCode() != null && screenItem.getScreenAction().getNavigateToBlogicCode() != "") {
					blogicCode = GenerateSourceCodeUtil.normalizedURL(screenItem.getScreenAction().getNavigateToBlogicCode());
				}
				
				String action = "";
				action = "${pageContext.request.contextPath}/"+GenerateSourceCodeUtil.normalizedURL(toModuleCodeButton)+"/"+blogicCode+ requestParam;
				
				String areaEventId = "";
				List<ScreenArea> screenAreas = new ArrayList<ScreenArea>();
				for (int i = 0; i < tempScreenDesign.getScreenAreas().length; i++) {
					if (tempScreenDesign.getScreenAreas()[i].getScreenAreaEvents() != null && tempScreenDesign.getScreenAreas()[i].getScreenAreaEvents().size() > 0) {
						screenAreas.add(tempScreenDesign.getScreenAreas()[i]);
					}
				}
				for(ScreenArea area : screenAreas) {
					for(ScreenAreaEvent areaEvent : area.getScreenAreaEvents()) {
						areaEventId = String.valueOf(areaEvent.getScreenAreaEventId());
					}
				}
				String eventArea = "";
				if(StringUtils.isNotEmpty(areaEventId)) {
					eventArea = "onclick=\"eventArea"+areaEventId+"\"";
				}
				element += "<button "+messageId+" name=\""+screenItem.getItemCode()+"\" "+eventArea+" type=\"button\" button-href=\""+action+"\" onclick=\" "+beforePost+" \" class=\"btn "+enableConfirm+" "+buttonType+" \" style=\""+ item.getStyle() +"\">"+buttonContentTotal+"</button>";
				
			} 
			//Button is not submit
			else if(screenItem.getScreenAction() != null && screenItem.getScreenAction().getSubmitMethodType() != null && screenItem.getScreenAction().getSubmitMethodType().equals(ScreenActionConst.SUBMIT_METHOD_TYPE_GET)) {
				String blogicCode = "";
				if(screenItem.getScreenAction().getNavigateToBlogicCode() != null && screenItem.getScreenAction().getNavigateToBlogicCode() != "") {
					blogicCode = GenerateSourceCodeUtil.normalizedURL(screenItem.getScreenAction().getNavigateToBlogicCode());
				}
				String action = "";
				action = "${pageContext.request.contextPath}/"+GenerateSourceCodeUtil.normalizedURL(toModuleCodeButton)+"/"+blogicCode+ requestParam;
				String beforeGet = "";
				if(screenItem.getEnableConfirm() != null && ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM.equals(screenItem.getEnableConfirm()) && screenItem.getMessageConfirm() != null) {
					beforeGet = "beforeGetHaveConfirm(this)";
				} else {
					beforeGet = "beforeGetNotConfirm(this)";
				}
				if(TEMPLATE_TYPE_NORMAL.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_NORMAL.equals(navigateToScreenTemplateTypeButton)) {
					element += "<button "+messageId+" type=\"button\" button-href=\""+action+"\" onclick=\""+beforeGet+"\" class=\"btn "+buttonType+" "+enableConfirm+" \" style=\""+ item.getStyle() +"\">"+buttonContentTotal+"</button>";
				}
				if(TEMPLATE_TYPE_POPUP.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_POPUP.equals(navigateToScreenTemplateTypeButton)) {
					element += "<a href=\""+action+"\" class=\"btn qp-button qp-link-button qp-link-popup-text\" type=\"submit\">"+buttonContentTotal+"</a>";
				}
				if(TEMPLATE_TYPE_NORMAL.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_POPUP.equals(navigateToScreenTemplateTypeButton)) {
					element += "<a href=\""+action+"\" class=\"btn qp-button qp-link-button qp-link-popup\" type=\"submit\">"+buttonContentTotal+"</a>";
				}
				if(TEMPLATE_TYPE_POPUP.equals(tempScreenDesign.getTemplateType()) && TEMPLATE_TYPE_NORMAL.equals(navigateToScreenTemplateTypeButton)) {
					element += "<a href=\""+action+"\" class=\"btn qp-button qp-link-button qp-link-popup-navigate\" type=\"submit\">"+buttonContentTotal+"</a>";
				}
				
			}
			else{
				element += "<input "+messageId+" type=\"button\" class=\"btn "+enableConfirm+" "+buttonType+" \" value=\"" + item.getLabelText() + "\""+ item.getLabelText() +"/>";
			}
			element += "</qp:authorization>";
			break;
		case 20:
			String id = FunctionCommon.convertNameToCodeDb(item.getLabelText().replace("lbl", ""));
			
			String mandatory = "&nbsp;&nbsp;<span class=\"qp-required-field\"><qp:message code=\"sc.sys.0029\" /></span>";
			String styleStaticLabel = "";
			if (item.getStyle() != "" && item.getStyle() != null) {
				styleStaticLabel = "  style=\"" + item.getStyle() + "\" ";
			}
			
			if (screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.SINGLE_ENTITY)) {
				if(item.getMandatory() != null && "true".equals(item.getMandatory())) {
					element += "<label "+styleStaticLabel+"><qp:message code=\"" + item.getLabel() + "\" />"+mandatory+"</label>";
				} else {
					element += "<label "+styleStaticLabel+"><qp:message code=\"" + item.getLabel() + "\" /></label>";
				}
			} else {
				if(item.getMandatory() != null && "true".equals(item.getMandatory())) {
					element += "<label style=\"cursor: pointer; " + item.getStyle() + "\"><qp:message code=\"" + item.getLabel() + "\" />"+mandatory+"</label>";
				} else {
					element += "<label style=\"cursor: pointer; " + item.getStyle() + "\"><qp:message code=\"" + item.getLabel() + "\" /></label>";
				}
				
			}
			break;
		}
		
		// gen screen item condtion
		StringBuilder sb = new StringBuilder();
		if(screenItem.getScreenItemStatusLst() != null && screenItem.getScreenItemStatusLst().size() > 0){
			int tabIndex = 0;
			for(ScreenItemStatus screenItemStatus : screenItem.getScreenItemStatusLst()){
				String condition = screenItemStatus.getFormulaCondition();
				if( StringUtils.isNotEmpty(condition)) {				
					sb.append("<c:if test=\"" + condition+ "\">").append("\n");		
					sb.append(element).append("\n");
					sb.append("</c:if>").append("\n");		
				}
			}
		}
		else {
			sb.append(element);
		}
		return sb.toString();
	}

	public void generateJavascript(TempScreenDesign tempScreenDesign, String outputDir, String moduleCode, ScreenDesign screenDesign, GenerateSourceCode generateSourceCode) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		FileOutputStream fileStream = null;
		OutputStreamWriter out = null;
		try {
			List<ScreenItem> itemEvents = new ArrayList<ScreenItem>();
			List<ScreenItem> buttons = new ArrayList<ScreenItem>();
			List<ScreenItem> autocompletes = new ArrayList<ScreenItem>();

			for (ScreenItem item : tempScreenDesign.getScreenItems()) {

				if (item.getScreenItemEvents() != null && item.getScreenItemEvents().size() > 0) {
					itemEvents.add(item);
				}

				if (item.getScreenAction() != null && ScreenActionConst.SUBMIT_METHOD_TYPE_POST.equals(item.getScreenAction().getSubmitMethodType())) {
					buttons.add(item);
				}

				if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
					autocompletes.add(item);
				}
			}
			for(ScreenItem item : autocompletes) {
				for(ScreenItemAutocompleteInput autocomInput : item.getScreenItemAutocompleteInputs()) {
					if(autocomInput.getScreenItemCode() != null) {
						String[] str = autocomInput.getScreenItemCode().split("\\.");
						for(ScreenItem itemOfScreen : tempScreenDesign.getScreenItems()) {
							if(itemOfScreen.getScreenForms().getFormCode() != null && itemOfScreen.getScreenArea().getAreaCode() != null && itemOfScreen.getItemCode() != null && str[0] != null && str[1] != null && str[2] != null) {
								if(itemOfScreen.getScreenForms().getFormCode().equals(str[0].toString()) && itemOfScreen.getScreenArea().getAreaCode().equals(str[1].toString()) 
										&& itemOfScreen.getItemCode().equals(str[2].toString())) {
									autocomInput.setLogicalDataTypeDepend(itemOfScreen.getLogicalDataType());
									autocomInput.setScreenItemCodeDepend(itemOfScreen.getItemCode());
									autocomInput.setScreenItemIdDepend(itemOfScreen.getScreenItemId());
									autocomInput.setScreenItemCodeBeDepended(item.getItemCode());
								}
							}
						}
					}
				}
			}
			
			//Get list screen item event mapping 
			List<ScreenItemEventMapping> lstEventMapping = screenDesignRepository.findScreenItemEventMappingByScreenId(tempScreenDesign.getScreenId());
			List<OutputBean> output = new ArrayList<OutputBean>();
			for(ScreenItem item : itemEvents) {
				for(ScreenItemEvent event : item.getScreenItemEvents()) {
					for(ScreenItemEventMapping mapping : lstEventMapping) {
						if(event.getScreenItemEventId().equals(mapping.getScreenItemEventId()) && mapping.getBeanType().equals(BEAN_TYPE_OUTPUT)) {
							OutputBean ou = new OutputBean();
							ou.setOutputBeanId(String.valueOf(mapping.getBeanId()));
							output.add(ou);
						}
					}
				}
			}
			
			List<OutputBean> outputParent = screenItemRepository.getParentOutputBeanByOutputBeanId(output);
			for(OutputBean ou : outputParent) {
				if(ou.getArrayFlg().equals(true)) {
					data.put("parentOutputBeanCode",ou.getOutputBeanCode());
				}
			}
			
			List<ScreenArea> screenAreas = new ArrayList<ScreenArea>();
			for (int i = 0; i < tempScreenDesign.getScreenAreas().length; i++) {
				if (tempScreenDesign.getScreenAreas()[i].getScreenAreaEvents() != null && tempScreenDesign.getScreenAreas()[i].getScreenAreaEvents().size() > 0) {
					screenAreas.add(tempScreenDesign.getScreenAreas()[i]);
				}
			}
			
			tempScreenDesign.setScreenCode(GenerateSourceCodeUtil.normalizedURL(tempScreenDesign.getScreenCode()));
			data.put("screenDesignForm", tempScreenDesign);
			data.put("screenCode", tempScreenDesign.getMessageDesign().getMessageCode());
			data.put("screenName", tempScreenDesign.getMessageDesign().getMessageString());
			data.put("pageContext", "${pageContext.request.contextPath}");
			data.put("moduleCode", GenerateSourceCodeUtil.normalizedURL(moduleCode));
			
			if(autocompletes != null) {
				data.put("autocompletes", autocompletes);
			}
			if(itemEvents != null) {
				for(ScreenItem itemEvent : itemEvents) {
					for(ScreenItemEvent event : itemEvent.getScreenItemEvents()) {
						event.getBusinessLogic().setBusinessLogicCode(GenerateSourceCodeUtil.normalizedURL(event.getBusinessLogic().getBusinessLogicCode()));
					}
				}
				data.put("screenItemEvents", itemEvents);
			}
			if(screenAreas != null) {
				data.put("screenAreaEvents", screenAreas);
			}
			
			
			data.put("messageRequire", MessageUtils.getMessage("sc.screendesign.0380"));
			data.put("buttons", buttons);
			data.put("dollar", "$");
			
			List<ScreenForm> listOfScreenForm = new ArrayList<ScreenForm>();
			for (ScreenForm screenForm : tempScreenDesign.getScreenForms()) {
				data.put("formCode", screenForm.getFormCode());
				listOfScreenForm.add(screenForm);
			}

			List<BusinessDesign> listOfBDesign = businessDesignRepository.getListBLogicByScreenFormId(listOfScreenForm);
			if (CollectionUtils.isNotEmpty(listOfBDesign)) {
				for (BusinessDesign businessDesign : listOfBDesign) {
					if(BusinessDesignConst.REQUEST_METHOD_PROCESSING.equals(businessDesign.getRequestMethod()) && BusinessDesignConst.DESIGN_MODE_AUTO.equals(businessDesign.getDesignMode())) {
						data.put("blogicCode", GenerateSourceCodeUtil.normalizedURL(businessDesign.getBusinessLogicCode()));
						break;
					}
				}
			}
			
			this.process(data, TEMPLATE_JAVASCRIPT_PROCESS, outputDir + "process" +tempScreenDesign.getScreenCode()+  ".js");
			this.process(data, TEMPLATE_JAVASCRIPT_INIT, outputDir + "init" +tempScreenDesign.getScreenCode()+  ".js");
			
			
		}catch (Exception e) {
            GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		}
		finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(fileStream);
		}
	}
	
	public String getParentOuputBean(OutputBean ouputBean, List<OutputBean> outputBeanLst){
		
		String outputBeanCode = "";
		for(OutputBean item : outputBeanLst){
			if(DataTypeUtils.equals(item.getOutputBeanId(), ouputBean.getParentOutputBeanId())){
				if(item.getParentOutputBeanId() == null){
					outputBeanCode = item.getOutputBeanCode();
				}
				else {
					outputBeanCode = getParentOuputBean(item, outputBeanLst) + "." + item.getOutputBeanCode();
	 			}
				break;
			}
		}
		return outputBeanCode;
	}
	
	public Map<String, String> buildMapOutputBean(List<OutputBean> outputBeanLst, String modelAttribute){
		
		Map<String, String> mapOutputBean = new HashMap<String, String>();
		String outputBeanCode = "";
		for(OutputBean ouputBean : outputBeanLst){
			
			if(ouputBean.getParentOutputBeanId() == null){
				outputBeanCode = ouputBean.getOutputBeanCode();
			}
			else {
				outputBeanCode = getParentOuputBean(ouputBean, outputBeanLst) + "." + ouputBean.getOutputBeanCode();
 			}
			outputBeanCode = modelAttribute + "." + outputBeanCode;		
			mapOutputBean.put(ouputBean.getOutputBeanId(), outputBeanCode);			
		}
		return mapOutputBean;		
	}

	public ScreenDesignForJSP mappingView(ScreenDesign screenDesign, String modelAttribute){
		
		
		ScreenForm[] screenForms = screenDesign.getScreenForms();
		ScreenArea[] screenAreas = screenDesign.getScreenAreas();
		ScreenGroupItem[] screenGroupItems = screenDesign.getScreenGroupItems();
		ScreenItem[] screenItems = screenDesign.getScreenItems();
		
		ScreenDesignForJSP form = new ScreenDesignForJSP();		
		List<ScreenForm> screenFormLst = new ArrayList<ScreenForm>();
		for(ScreenForm screenForm : screenForms){
			screenFormLst.add(screenForm);
		}
		
		// get screen status
		List<FormulaDefinition> formulaDefinitions = formulaDefinitionRepository.getFormulaDefinitionByScreenFormId(screenFormLst);
		List<FormulaDetail> lstFormulaDetails = formulaDefinitionRepository.findFormulaDetailsByListScreenForms(screenFormLst);		
		
		List<OutputBean> outputBeanLst = screenItemRepository.getAllOutputBeanByScreenId(screenDesign.getScreenId());
		
		Map<String, String> mapOutputBean = buildMapOutputBean(outputBeanLst, modelAttribute);
		
		List<ScreenItemStatus> screenItemStatusesAll = screenItemStatusRepository.getScreenItemStatusByFormulaDefinitionId(formulaDefinitions);		
		
		// create map formula definitions detail
		Map<Long, List<FormulaDetail>> mapFormulaDetails = new HashMap<Long, List<FormulaDetail>>();
		for(FormulaDetail formulaDetail: lstFormulaDetails){
			if(mapFormulaDetails.containsKey(formulaDetail.getFormulaDefinitionId())){
				mapFormulaDetails.get(formulaDetail.getFormulaDefinitionId()).add(formulaDetail);
			}
			else {
				List<FormulaDetail> newItem = new ArrayList<FormulaDetail>();
				newItem.add(formulaDetail);
				mapFormulaDetails.put(formulaDetail.getFormulaDefinitionId(), newItem);
			}
		}
				
		// create map formula definitions
		Map<Long, FormulaDefinition> mapFormulaDefinitions = new HashMap<Long, FormulaDefinition>();
		for(FormulaDefinition formulaDefinition: formulaDefinitions){
			if(!mapFormulaDefinitions.containsKey(formulaDefinition.getFormulaDefinitionId())){
				String condition = generateConditionByFormula(mapFormulaDetails.get(formulaDefinition.getFormulaDefinitionId()), mapOutputBean);
				formulaDefinition.setFormulaCondition(condition);
				mapFormulaDefinitions.put(formulaDefinition.getFormulaDefinitionId(), formulaDefinition);
			}
		}
		
		// create map screen item status
		Map<Long, List<ScreenItemStatus>> mapScreenItemStatus = new HashMap<Long, List<ScreenItemStatus>>();
		Map<Long, List<ScreenItemStatus>> mapScreenAreaStatus = new HashMap<Long, List<ScreenItemStatus>>();
		for(ScreenItemStatus screenItemStatus: screenItemStatusesAll){
			
			if(screenItemStatus.getItemType() == 2){
				if(mapScreenAreaStatus.containsKey(screenItemStatus.getItemId())){
					mapScreenAreaStatus.get(screenItemStatus.getItemId()).add(screenItemStatus);
				}
				else {
					List<ScreenItemStatus> newItem = new ArrayList<ScreenItemStatus>();
					newItem.add(screenItemStatus);
					mapScreenAreaStatus.put(screenItemStatus.getItemId(), newItem);
				}
			}
			else if(screenItemStatus.getItemType() == 3){
				if(mapScreenItemStatus.containsKey(screenItemStatus.getItemId())){
					mapScreenItemStatus.get(screenItemStatus.getItemId()).add(screenItemStatus);
				}
				else {
					List<ScreenItemStatus> newItem = new ArrayList<ScreenItemStatus>();
					newItem.add(screenItemStatus);
					mapScreenItemStatus.put(screenItemStatus.getItemId(), newItem);
				}
			}
			
		}
		
		form.setScreenId(screenDesign.getScreenId());
		form.setScreenCode(screenDesign.getScreenCode());
		form.setScreenUrlCode(screenDesign.getScreenUrlCode());
		form.setScreenPatternType(screenDesign.getScreenPatternType());
		form.setTemplateType(screenDesign.getTemplateType());
		form.setRemark(screenDesign.getRemark());
		form.setUpdatedDate(screenDesign.getUpdatedDate());
		if(screenDesign.getCompletionType() != null) {
			form.setCompletionType(screenDesign.getCompletionType());
		}
		if(screenDesign.getConfirmationType() != null) {
			form.setConfirmationType(screenDesign.getConfirmationType());
		}

		form.setDesignMode(screenDesign.getDesignMode());
		form.setDesignStatus(screenDesign.getDesignStatus());

		if (screenDesign.getFunctionDesignId() != null) {
			form.setFunctionDesignId(screenDesign.getFunctionDesignId() + "");
		}
		form.setFunctionDesignName(screenDesign.getFunctionDesignName());

		if (screenDesign.getMessageDesign() != null) {
			form.setScreenName(screenDesign.getMessageDesign().getMessageString());
		}

		if (screenDesign.getMessageDesign() != null) {
			form.setMessageDesign(screenDesign.getMessageDesign());
			form.setScreenName(screenDesign.getMessageDesign().getMessageString());
		}

		form.setAreaNonGroup(new ArrayList<ScreenArea>());
		for (int i = 0; i < screenAreas.length; i++) {
			// get area type link header
			if (screenAreas[i].getAreaType().equals(-1)) {
				form.getAreaNonGroup().add(screenAreas[i]);
			}
			if(mapScreenAreaStatus.containsKey(screenAreas[i].getScreenAreaId())){
				
				List<ScreenItemStatus> screenItemStatusLst = mapScreenAreaStatus.get(screenAreas[i].getScreenAreaId());
				List<String> screenStatusConditions = new ArrayList<String>();
				for(ScreenItemStatus item : screenItemStatusLst){
					FormulaDefinition fomular = mapFormulaDefinitions.get(item.getFormulaDefinitionId());
					if(item.getEnabled() != null && item.getEnabled()){
						String condition = fomular.getFormulaCondition();
						if(item.getStatus() == 0){
							condition = "!(" + condition + ")";
						}
						screenStatusConditions.add(condition);
					}
				}
				screenAreas[i].setScreenStatusConditions(screenStatusConditions);
			}
			else {
				screenAreas[i].setScreenStatusConditions( new ArrayList<String>());
			}
		}

		form.setHeaderLinkItems(new ArrayList<ScreenItem>());
		//
		for (int i = 0; i < screenItems.length; i++) {
			for (int j = 0; j < form.getAreaNonGroup().size(); j++) {
				if (screenItems[i].getScreenAreaId().equals(form.getAreaNonGroup().get(j).getScreenAreaId())) {
					form.getHeaderLinkItems().add(screenItems[i]);
				}
			}
			if(mapScreenItemStatus.containsKey(screenItems[i].getScreenItemId())){
				
				List<ScreenItemStatus> screenItemStatusLst = mapScreenItemStatus.get(screenItems[i].getScreenItemId());
				for(ScreenItemStatus item : screenItemStatusLst){
					FormulaDefinition fomular = mapFormulaDefinitions.get(item.getFormulaDefinitionId());
					if(item.getEnabled() != null && item.getEnabled()){
						String condition = fomular.getFormulaCondition();
						if(item.getStatus() == 0){
							condition = "!(" + condition + ")";
						}
						item.setFormulaCondition(condition);
					}
				}
				screenItems[i].setScreenItemStatusLst(screenItemStatusLst);
			}
			else {
				screenItems[i].setScreenItemStatusLst(new ArrayList<ScreenItemStatus>());
			}
		}

		// add screen item to area
		for (int i = 0; i < screenAreas.length; i++) {
			List<ScreenItem> items = new ArrayList<ScreenItem>();

			for (int j = 0; j < screenItems.length; j++) {
				if (screenAreas[i].getScreenAreaId().equals(screenItems[j].getScreenAreaId())) {
					items.add(screenItems[j]);
				}
			}
			screenAreas[i].setItems(items);
		}

		screenGroupItems = checkColRowSpan(screenAreas, screenGroupItems, screenItems);

		for (int i = 0; i < screenGroupItems.length; i++) {
			form.getScreenGroups().put(screenGroupItems[i].getGroupItemId(), screenGroupItems[i]);
		}

		for (int i = 0; i < screenAreas.length; i++) {
			screenAreas[i] = setAreaPosition(screenForms, screenAreas[i], true);
		}

		form.setScreenForms(screenForms);
		form.setScreenAreas(screenAreas);
		form.setScreenGroupItems(screenGroupItems);
		form.setScreenItems(screenItems);
		form.setScreenParameters(screenDesign.getScreenParameters());
		return form;
	}

	public static ScreenGroupItem[] checkColRowSpan(ScreenArea[] screenAreas, ScreenGroupItem[] screenGroupItems, ScreenItem[] screenItems) {

		List<ScreenGroupItem> groups = new ArrayList<ScreenGroupItem>();

		for (int i = 0; i < screenItems.length; i++) {
			if (screenItems[i].getGroupItemId() != null) {
				if ((screenItems[i].getColSpan() != null && screenItems[i].getColSpan() > 1) || (screenItems[i].getRowSpan() != null && screenItems[i].getRowSpan() > 1)) {

					ScreenGroupItem groupItem = new ScreenGroupItem();
					groupItem.setGroupItemId(screenItems[i].getGroupItemId());

					int indexGroup = Arrays.asList(screenGroupItems).indexOf(groupItem);

					if (indexGroup != -1) {
						groupItem = Arrays.asList(screenGroupItems).get(indexGroup);
						List<ScreenItem> items = new ArrayList<ScreenItem>();
						items.add(screenItems[i]);
						groupItem.setItems(items);

						groups.add(groupItem);
					}
				}
			}
		}

		Map<Long, List<TableTempForJSP>> matrix = new Hashtable<Long, List<TableTempForJSP>>();

		for (int i = 0; i < groups.size(); i++) {
			Integer rowspan = (groups.get(i).getItems().get(0).getRowSpan() != null) ? groups.get(i).getItems().get(0).getRowSpan() : 1;
			Integer colspan = (groups.get(i).getItems().get(0).getColSpan() != null) ? groups.get(i).getItems().get(0).getColSpan() : 1;

			ScreenArea area = groups.get(i).getScreenArea();

			if (area == null) {
				ScreenArea screenArea = new ScreenArea();
				screenArea.setScreenAreaId(groups.get(i).getScreenAreaId());
				int indexArea = Arrays.asList(screenAreas).indexOf(screenArea);

				if (indexArea != -1) {
					area = Arrays.asList(screenAreas).get(indexArea);
				}
			}

			// get rowindex
			int rowIndex = groups.get(i).getGroupSeqNo() / area.getTotalCol();
			int colIndex = groups.get(i).getGroupSeqNo() % area.getTotalCol();

			int tempRow = rowIndex;

			int tempRowSpan = 0;
			int tempColSpan = 0;

			// get matrix col, row span
			List<TableTempForJSP> hiddens = new ArrayList<TableTempForJSP>();
			do {
				int tempCol = colIndex;
				while (tempColSpan < colspan) {
					if (tempRow == rowIndex && tempCol == colIndex) {

					} else {
						TableTempForJSP tableTemp = new TableTempForJSP();
						tableTemp.setRowIndex(tempRow);
						tableTemp.setColIndex(tempCol);
						hiddens.add(tableTemp);
					}
					tempCol++;
					tempColSpan++;

				}
				tempCol = 0;
				tempColSpan = 0;

				tempRow++;
				tempRowSpan++;
			} while (tempRowSpan < rowspan);

			if (matrix.containsKey(area.getScreenAreaId())) {
				matrix.get(area.getScreenAreaId()).addAll(hiddens);
			} else {
				matrix.put(area.getScreenAreaId(), hiddens);
			}

		}

		// debug
		/*
		 * for(Long areaId : matrix.keySet()) { System.out.println("Area: " + areaId); for(TableTemp temp : matrix.get(areaId)) { System.out.println("          Row: " + temp.getRowIndex() + "-Col: " +temp.getColIndex()); } }
		 */

		for (int i = 0; i < screenGroupItems.length; i++) {
			if (matrix.containsKey(screenGroupItems[i].getScreenAreaId())) {

				ScreenGroupItem group = screenGroupItems[i];
				ScreenArea area = group.getScreenArea();
				if (area == null) {
					ScreenArea screenArea = new ScreenArea();
					screenArea.setScreenAreaId(group.getScreenAreaId());
					int indexArea = Arrays.asList(screenAreas).indexOf(screenArea);

					if (indexArea != -1) {
						area = Arrays.asList(screenAreas).get(indexArea);
					}
				}

				Integer rowIndex = group.getGroupSeqNo() / area.getTotalCol();
				Integer colIndex = group.getGroupSeqNo() % area.getTotalCol();

				for (TableTempForJSP temp : matrix.get(group.getScreenAreaId())) {
					if (rowIndex.equals(temp.getRowIndex()) && colIndex.equals(temp.getColIndex())) {
						screenGroupItems[i].setStyleColRowSpan("display: none;");
					}
				}
			}
		}

		return screenGroupItems;
	}

	public static ScreenArea setAreaPosition(ScreenForm[] screenForms, ScreenArea screenArea, boolean view) {
		for (ScreenForm screenForm : screenForms) {

			for (int i = 0; i < screenForm.getScreenFormTabGroups().size(); i++) {
				ScreenFormTabGroup groupTab = screenForm.getScreenFormTabGroups().get(i);
				String startHtml = "";
				String endHtml = "";
				String formCode = screenForm.getFormCode() + "-" + groupTab.getTabCode();
				formCode = formCode.replace(" ", "");

				for (int j = 0; j < groupTab.getScreenFormTabs().size(); j++) {
					ScreenFormTabs tab = groupTab.getScreenFormTabs().get(j);

					String[] areaCodes = tab.getAreas().split(",");
					Integer indexArea = Arrays.asList(areaCodes).indexOf(screenArea.getAreaCode());
					if (indexArea != -1) {

						if (j == 0 && indexArea == 0) {
							startHtml = groupTab.getStartHtml();
						}

						if (j == groupTab.getScreenFormTabs().size() - 1 && indexArea == areaCodes.length - 1) {

							endHtml = groupTab.getEndHtml();
						}

						if (tab.getTabDirection().equals(2) || tab.getTabDirection().equals(3)) {

							if (indexArea == 0) {
								String selectedAccordion = "";

								String dataParent = "";

								if (tab.getTabDirection().equals(3)) {
									dataParent = " data-parent=\"#" + formCode + "-tab\" ";
								}

								String title = "        <a data-toggle='collapse' " + dataParent + " href='#" + formCode + "tab-" + j + "'>" + tab.getTabTitle() + " <i class=\"indicator glyphicon  pull-right\"></i></a>";
								if (j == 0 && !view) {
									selectedAccordion = "in";

									title = "<div class='form-inline'>" + "	<span style='cursor: move;' class='.ui-state-dark qp-glyphicon glyphicon glyphicon-sort sortable srcgenTableSort'></span>" + " <span style='cursor: pointer;' onclick='openTabSetting(this)' class='.ui-state-dark qp-glyphicon glyphicon glyphicon-cog'></span>&nbsp;" + title + "</div>";
								}
								startHtml += "  <div class=\"panel panel-default-accordion\">" + "	    <div class=\"panel-heading\">" + "      <span class=\"qp-heading-text\">" + title + "     </span>" + "    </div>" + "    <div id=\"" + formCode + "tab-" + j + "\" class=\"panel-collapse collapse " + selectedAccordion + "\">" + "      <div class=\"panel-body\">";

							} else {
								startHtml = "";
							}

							if (indexArea == areaCodes.length - 1) {
								endHtml += "</div></div></div>";
							} else {
								endHtml += "";
							}

						} else {
							if (indexArea == 0) {
								String activeTab = "";
								if (j == 0) {
									activeTab = " active";
								}
								startHtml += "<div id='" + formCode + "tab-" + j + "' style='overflow: hidden;' class='tab-pane" + activeTab + "'>";
							} else {
								startHtml += "";
							}

							if (indexArea == areaCodes.length - 1) {
								endHtml += "</div>";
							} else {
								endHtml += "";
							}

						}

						screenArea.setStartHtml(startHtml);
						screenArea.setEndHtml(endHtml);
					}
				}
			}
		}
		return screenArea;
	}

	public void handle(GenerateSourceCode generateSourceCode, CommonModel common) {
		List<InputBean> lstInputBeanForRegisterConfirm = new ArrayList<InputBean>();
		Boolean isRegisterConfirm = false;
		MenuDesign menuDesignNavigateJSP = new MenuDesign();
		// Set folder root
		sourcePath = generateSourceCode.getSourcePathWeb();
		String getFolderHomepageController = getFolderHomePageControllerForJSP();
		if (CollectionUtils.isEmpty(generateSourceCode.getModules())) {
			try {
				replaceContentHomePageController(getFolderHomepageController, null);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		// create list online modules
		List<Module> listOfModules = new ArrayList<Module>();
		for(Module item : generateSourceCode.getModules()) {
			if (DbDomainConst.FunctionType.ONLINE.equals(item.getModuleType())) {
				listOfModules.add(item);
			}
		}		
		
		Project project = generateSourceCode.getProject();
		Timestamp dateTime = FunctionCommon.getCurrentTime();
		List<ProjectItem> lstProjectItem = projectRepository.getProjectItemByProjectId(project.getProjectId(), currentLanguageId);
		List<ProjectItem> listLogoLeft = new ArrayList<ProjectItem>();
		List<ProjectItem> listLogoRight = new ArrayList<ProjectItem>();
		List<ProjectItem> listHeaderLeft = new ArrayList<ProjectItem>();
		List<ProjectItem> listHeaderRight = new ArrayList<ProjectItem>();
		List<ProjectItem> listFooterLeft = new ArrayList<ProjectItem>();
		List<ProjectItem> listFooterRight = new ArrayList<ProjectItem>();

		if (lstProjectItem != null) {
			for (ProjectItem projectItem : lstProjectItem) {
				if (projectItem.getItemPosition() == 0) {
					listLogoLeft.add(projectItem);
				} else if (projectItem.getItemPosition() == 1) {
					listLogoRight.add(projectItem);
				} else if (projectItem.getItemPosition() == 2) {
					listHeaderLeft.add(projectItem);
				} else if (projectItem.getItemPosition() == 3) {
					listHeaderRight.add(projectItem);
				} else if (projectItem.getItemPosition() == 4) {
					listFooterLeft.add(projectItem);
				} else {
					listFooterRight.add(projectItem);
				}
			}
		}
		
		Map<String, String> projectStyle = new HashMap<String, String>();
		projectStyle = screenDesignService.getProjectStyle(project.getProjectId());
		String pageContext = "${pageContext.request.contextPath}";
		/*create menu content*/
		String menuContent = StringUtils.EMPTY;
		
		MenuDesign menuDesign = menuDesignService.getMenuDesignForGenerateJSP(project.getProjectId(), currentLanguageId);
		
		if (menuDesign != null) {
			try {
				replaceContentHomePageController(getFolderHomepageController, menuDesign.getUrlMainAction());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			menuDesignNavigateJSP = menuDesignService.getNavigationInformationForJSP(project.getProjectId(), menuDesign.getScreenId(), menuDesign.getHeaderMenuAction());
			menuDesign.setProjectId(project.getProjectId());
			if (menuDesignNavigateJSP != null) {
				if (StringUtils.isNotBlank(menuDesignNavigateJSP.getUrlMainAction())) {
					menuDesign.setUrlMainAction(pageContext + "/" + GenerateSourceCodeUtil.normalizedURL(menuDesign.getModuleCode() + "/" + menuDesignNavigateJSP.getUrlMainAction()));
				} else {
					menuDesign.setUrlMainAction(pageContext + "/home");
				}
			} else {
				menuDesign.setUrlMainAction(pageContext + "/home");
			}
			menuContent = menuDesignService.buildMenu(menuDesign, DbDomainConst.MenuType.JSP);
		} else {
			menuDesign = new MenuDesign();
			menuDesign.setHeaderMenuName(project.getProjectName());
			menuDesign.setMenuType(DbDomainConst.MenuDirection.HORIZONTAL);
			menuDesign.setProjectId(project.getProjectId());
			try {
				replaceContentHomePageController(getFolderHomepageController, menuDesign.getUrlMainAction());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			menuContent = menuDesignService.buildMenu(menuDesign, DbDomainConst.MenuType.JSP);
		}
		String userName = "";
		
		try {
			this.generateHeaderJSP(this.getTemplate(TEMPLATE_HEADER_JSP), generateSourceCode, projectStyle, listLogoLeft, listLogoRight, listHeaderLeft, listHeaderRight, userName, dateTime, pageContext, menuContent, menuDesign, menuDesignNavigateJSP);
			this.generateFooterJSP(this.getTemplate(TEMPLATE_FOOTER_JSP), generateSourceCode, projectStyle, listFooterLeft, listFooterRight, userName, dateTime, pageContext, menuContent);
			this.generateTemplateJSP(this.getTemplate(TEMPLATE_LAYOUT_TEMPLATE_JSP), generateSourceCode, pageContext, menuContent, menuDesign, menuDesignNavigateJSP,project);
		}catch (Exception e1) {
			// TODO Auto-generated catch block
			GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e1);
		}
		
		// Process generate jsp in the case module is online
		if (CollectionUtils.isNotEmpty(listOfModules)) {
			try {
				if (FunctionCommon.isEmpty(listOfModules))
					return;
				
				/* List<OutputBean> listOfOutputBean = screenItemRepository.getOutputBeanByScreenItems(screenItems); */
				List<ScreenDesign> listOfScreenDesign = screenDesignRepository.getAllScreenByModules(listOfModules, generateSourceCode.getLanguageId());
				if (FunctionCommon.isNotEmpty(listOfScreenDesign)) {
					for (Module module : listOfModules) {
						List<ScreenDesign> listToGenerate = new ArrayList<ScreenDesign>();
						for (ScreenDesign screendesign : listOfScreenDesign) {
							if(screendesign.getDesignMode() != null && screendesign.getDesignMode().equals(DESIGN_MODE_BUSINESS)) {
								ScreenDesign screenDesign = new ScreenDesign();
								if(module.getModuleId() != null) {
									if (module.getModuleId().equals(screendesign.getModuleId())) {
										screenDesign = screenDesignService.getScreenDesignByIdForPreview(screendesign.getScreenId(), 1, project.getDefaultLanguageId(), project.getProjectId());
										listToGenerate.add(screenDesign);
									}
								}
							}
							
						}
						ScreenDesign screenConfirm = new ScreenDesign();
						ScreenDesign screenComplete = new ScreenDesign();
						List<Long> listScreenId = new ArrayList<Long>();
						for (ScreenDesign screenDesign : listToGenerate) {
							if(DbDomainConst.ScreenPatternType.VIEW.equals(screenDesign.getScreenPatternType()) && ScreenDesignConst.ConfirmType.SCREEN.equals(screenDesign.getConfirmationType())) {
								screenConfirm.setScreenId(screenDesign.getScreenId());
							}
							if(DbDomainConst.ScreenPatternType.VIEW.equals(screenDesign.getScreenPatternType()) && ScreenDesignConst.ConfirmType.SCREEN.equals(screenDesign.getCompletionType())) {
								screenComplete.setScreenId(screenDesign.getScreenId());
							}
							listScreenId.add(screenDesign.getScreenId());
						}
						
						String newModuleCode =  module.getModuleCode().substring(0, 1).toLowerCase() + module.getModuleCode().substring(1);
						String pathModule = createModuleFolderForJSP(newModuleCode);
						
						/*String pathModule = createModuleFolderForJSP(module.getModuleCode());*/
						String pathModuleJavascript = createModuleFolderForJavascript(newModuleCode);

						
						try {
							List<InputBean> lstInputRegisConfirm = new ArrayList<InputBean>();
							List<ScreenItem> listOfScreenItemPrepare = new ArrayList<ScreenItem>();
							// Prepare data input bean for register or modify scresen
							if(screenConfirm.getScreenId() != null) {
								listOfScreenItemPrepare = screenItemRepository.getListScreenItemByScreenId(screenConfirm.getScreenId());
								List<ScreenAction> lstScreenAction = screenActionRepository.findAllActionByScreenId(screenConfirm.getScreenId());
								List<InputBean> listOfInputBeanPrepare = screenItemRepository.getAllInputBeanByScreenItemIds(listOfScreenItemPrepare);
								for(ScreenItem item : listOfScreenItemPrepare) {
									for(ScreenAction action : lstScreenAction) {
										if(item.getScreenActionId() != null && item.getScreenActionId().equals(action.getScreenActionId())) {
											if((action.getFromScreenPatternType() != null && (action.getFromScreenPatternType().equals(DbDomainConst.ScreenPatternType.VIEW)))
													&& (action.getToScreenPatternType() != null && action.getToScreenPatternType().equals(DbDomainConst.ScreenPatternType.VIEW))) {
												for(InputBean input : listOfInputBeanPrepare) {
													lstInputBeanForRegisterConfirm.add(input);
												}
											}
										}
									}
								}
								//Set list input bean for screen is register or modify have confirm
								
								for(ScreenItem item : listOfScreenItemPrepare) {
									for(ScreenAction action : lstScreenAction) {
										if(item.getScreenActionId() != null && item.getScreenActionId().equals(action.getScreenActionId())) {
											if((action.getFromScreenPatternType() != null && (action.getFromScreenPatternType().equals(DbDomainConst.ScreenPatternType.REGISTER) || action.getFromScreenPatternType().equals(DbDomainConst.ScreenPatternType.MODIFY) || action.getFromScreenPatternType().equals(DbDomainConst.ScreenPatternType.VIEW)))
													&& (action.getToScreenPatternType() != null && action.getToScreenPatternType().equals(DbDomainConst.ScreenPatternType.VIEW))) {
												for(InputBean input : lstInputBeanForRegisterConfirm) {
													lstInputRegisConfirm.add(input);
													/*isRegisterConfirm = true;*/
												}
											}
										}
									}
								}
							}
							
							//Start generate one by one screen
							for (int k = 0; k < listToGenerate.size(); k++) {
								
								ScreenDesign screenDesign = listToGenerate.get(k);
								try {
									List<ScreenItemAutocompleteInput> listAutocompleteInputByScreenId = screenDesignRepository.findAutocompleteInputByScreenId(screenDesign.getScreenId());
									List<ScreenItem> listOfScreenItem = new ArrayList<ScreenItem>();
									if(isRegisterConfirm) {
										listOfScreenItem = screenItemRepository.getListScreenItemByScreenId(screenComplete.getScreenId());
									} else {
										listOfScreenItem = screenItemRepository.getListScreenItemByScreenId(screenDesign.getScreenId());
									}
									
									//List screen item for display on confirm,complete
									List<ScreenItem> listScreenItemConfirm = new ArrayList<ScreenItem>();
									if(DbDomainConst.ScreenPatternType.VIEW.equals(screenDesign.getScreenPatternType()) && screenDesign.getTemplateType() != null && screenDesign.getTemplateType() == 1) {
										for(ScreenItem item : screenDesign.getScreenItems()) {
											//Because output bean mapping => item type = hidden
											if(item.getItemType() != null && item.getItemType().equals(2)) {
												listScreenItemConfirm.add(item);
											}
										}
									}
									
									int countButtonSubmit = 0;
									for(ScreenItem item : screenDesign.getScreenItems()) {
										if(DbDomainConst.LogicDataType.BUTTON.equals(item.getLogicalDataType()) && (item.getScreenAction() != null && item.getScreenAction().getSubmitMethodType().equals(ScreenActionConst.SUBMIT_METHOD_TYPE_POST))) {
											countButtonSubmit ++;
										}
									}
								/*	Long blogic = null;
									if(screenDesign.getBusinessInits() != null && screenDesign.getBusinessInits().size() > 0 &&  screenDesign.getBusinessInits().get(0).getBusinessLogicId() != null) {
										blogic = screenDesign.getBusinessInits().get(0).getBusinessLogicId();
									}*/
									List<OutputBean> listOfOutputBean = screenItemRepository.getAllOutputBeanByScreenItemIds(listOfScreenItem,screenDesign.getBusinessInitsDefault());
									List<InputBean> listOfInputBean = screenItemRepository.getAllInputBeanByScreenItemIds(listOfScreenItem);
									TempScreenDesign tempScreenDesign = generateJSP(pathModule, module, screenDesign, listOfOutputBean, listOfInputBean, generateSourceCode.getProject(),
												currentLanguageId, generateSourceCode, pathModuleJavascript,listAutocompleteInputByScreenId,
												isRegisterConfirm,lstInputRegisConfirm,listScreenItemConfirm,menuContent,countButtonSubmit, menuDesignNavigateJSP,common);
									generateJavascript(tempScreenDesign, pathModuleJavascript, module.getModuleCode(), screenDesign,generateSourceCode);
								} catch (Exception e) {
									e.printStackTrace();
									GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
			}
		}
	}

	public String createModuleFolderForJSP(String moduleName) {
		StringBuilder pathModule = new StringBuilder();
		pathModule.append(File.separator).append("src").append(File.separator).append("main").append(File.separator).append("webapp").append(File.separator).append("WEB-INF").append(File.separator).append("views").append(File.separator).append(GenerateSourceCodeUtil.normalizedURL(moduleName));
		return GenerateSourceCodeUtil.createSaveFileDirectory(pathModule.toString(), sourcePath);
	}

	public String createModuleFolderForJavascript(String moduleName) {
		StringBuilder pathModule = new StringBuilder();
		pathModule.append(File.separator).append("src").append(File.separator).append("main").append(File.separator).append("webapp").append(File.separator).append("resources").append(File.separator).append("app").append(File.separator).append(GenerateSourceCodeUtil.normalizedURL(moduleName));
		return GenerateSourceCodeUtil.createSaveFileDirectory(pathModule.toString(), sourcePath);
	}
	
	public String getFolderHomePageControllerForJSP() {
		StringBuilder pathModule = new StringBuilder();
		pathModule.append(File.separator).append("src").append(File.separator).append("main").append(File.separator).append("java").append(File.separator).append("org").append(File.separator).append("terasoluna").append(File.separator).append("qp").append(File.separator).append("app").append(File.separator).append("homepage");
		return GenerateSourceCodeUtil.createSaveFileDirectory(pathModule.toString(), sourcePath);
	}

	/**
	 * 
	 * @param autocompleteDesign
	 * @return
	 */
	public String setArgForAutocimplete(AutocompleteDesign autocompleteDesign) {
		StringBuilder stringBuilder = new StringBuilder();

		if (autocompleteDesign.getArg01() != null) {
			stringBuilder.append("arg01=");
			stringBuilder.append('"' + autocompleteDesign.getArg01() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg02() != null) {
			stringBuilder.append("arg02=");
			stringBuilder.append('"' + autocompleteDesign.getArg02() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg03() != null) {
			stringBuilder.append("arg03=");
			stringBuilder.append('"' + autocompleteDesign.getArg03() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg04() != null) {
			stringBuilder.append("arg04=");
			stringBuilder.append('"' + autocompleteDesign.getArg04() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg05() != null) {
			stringBuilder.append("arg05=");
			stringBuilder.append('"' + autocompleteDesign.getArg05() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg06() != null) {
			stringBuilder.append("arg06=");
			stringBuilder.append('"' + autocompleteDesign.getArg06() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg07() != null) {
			stringBuilder.append("arg07=");
			stringBuilder.append('"' + autocompleteDesign.getArg07() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg08() != null) {
			stringBuilder.append("arg08=");
			stringBuilder.append('"' + autocompleteDesign.getArg08() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg09() != null) {
			stringBuilder.append("arg09=");
			stringBuilder.append('"' + autocompleteDesign.getArg09() + '"');
			stringBuilder.append(" ");
		}
		if (autocompleteDesign.getArg10() != null) {
			stringBuilder.append("arg10=");
			stringBuilder.append('"' + autocompleteDesign.getArg10() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg11() != null) {
			stringBuilder.append("arg11=");
			stringBuilder.append('"' + autocompleteDesign.getArg11() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg12() != null) {
			stringBuilder.append("arg12=");
			stringBuilder.append('"' + autocompleteDesign.getArg12() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg13() != null) {
			stringBuilder.append("arg13=");
			stringBuilder.append('"' + autocompleteDesign.getArg13() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg14() != null) {
			stringBuilder.append("arg14=");
			stringBuilder.append('"' + autocompleteDesign.getArg14() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg15() != null) {
			stringBuilder.append("arg15=");
			stringBuilder.append('"' + autocompleteDesign.getArg15() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg16() != null) {
			stringBuilder.append("arg16=");
			stringBuilder.append('"' + autocompleteDesign.getArg16() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg17() != null) {
			stringBuilder.append("arg17=");
			stringBuilder.append('"' + autocompleteDesign.getArg17() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg18() != null) {
			stringBuilder.append("arg18=");
			stringBuilder.append('"' + autocompleteDesign.getArg18() + '"');
			stringBuilder.append(" ");
		}

		if (autocompleteDesign.getArg19() != null) {
			stringBuilder.append("arg19=");
			stringBuilder.append('"' + autocompleteDesign.getArg19() + '"');
			stringBuilder.append(" ");
		}
		if (autocompleteDesign.getArg20() != null) {
			stringBuilder.append("arg20=");
			stringBuilder.append('"' + autocompleteDesign.getArg20() + '"');
			stringBuilder.append(" ");
		}

		return stringBuilder.toString();
	}

	/*
	 * private OutputBean checkMapping(ScreenItem screenItem, List<OutputBean> listOfOutputBean) { OutputBean outputBean = new OutputBean(); for(OutputBean outputbean : listOfOutputBean) { if(screenItem.getScreenItemId().equals(outputBean.getScreenItemId())) { return outputBean; } } }
	 */
	
	private ScreenItem setParentOfScreenItem(ScreenItem item, List<OutputBean> listOfOutputBean, List<InputBean> listOfInputBean, Boolean isRegisterConfirm, List<InputBean> lstInputBeanForRegisterConfirm ) {
		for (OutputBean outputBean : listOfOutputBean) {
			if(outputBean.getOutputBeanCode() != null) {
				outputBean.setOutputBeanCode(GenerateSourceCodeUtil.normalizedPropertiesJSP(outputBean.getOutputBeanCode()));
			}
			if(outputBean.getParentOutputBeanCode() != null) {
				outputBean.setParentOutputBeanCode(GenerateSourceCodeUtil.normalizedPropertiesJSP(outputBean.getParentOutputBeanCode()));
			}
			if(outputBean.getGrandParentOutputBeanCode() != null) {
				outputBean.setGrandParentOutputBeanCode(GenerateSourceCodeUtil.normalizedPropertiesJSP(outputBean.getGrandParentOutputBeanCode()));
			}
			if (item.getScreenItemId().equals(outputBean.getScreenItemId())) {

				// No parent
				if (outputBean.getParentOutputBeanId() == null) {
					item.setOutputBeanCode(outputBean.getOutputBeanCode());
				}
				// Have parent
				else {
					// parent is array
					if (outputBean.getParentOutputBeanArrayFlag().equals("t")) {

						item.setParentOutputBeanCode(outputBean.getParentOutputBeanCode());
						item.setParentOutputBeanArrayFlag(outputBean.getParentOutputBeanArrayFlag());
						item.setOutputBeanCode(outputBean.getOutputBeanCode());
					}
					// parent is single
					else {
						// have grandparent
						if (outputBean.getGrandParentOutputBeanId() != null) {
							// grandParent is array
							if (outputBean.getGrandParentOutputBeanArrayFlag().equals("f")) {
								item.setParentOutputBeanArrayFlag(outputBean.getParentOutputBeanArrayFlag());
								item.setParentOutputBeanCode(outputBean.getGrandParentOutputBeanCode());
								item.setOutputBeanCode(outputBean.getParentOutputBeanCode() + "." + outputBean.getOutputBeanCode());
							}
							// grandParent is single
							else {
								item.setOutputBeanCode(outputBean.getGrandParentOutputBeanCode() + "." + outputBean.getParentOutputBeanCode() + "." + outputBean.getOutputBeanCode());
							}
						} else {
							item.setParentOutputBeanArrayFlag(outputBean.getParentOutputBeanArrayFlag());
							item.setParentOutputBeanCode(outputBean.getParentOutputBeanCode());
							item.setOutputBeanCode(outputBean.getOutputBeanCode());
						}
					}
				}
			}
		}

		// Input bean
		List<InputBean> lstInputBean = new ArrayList<InputBean>();
		
		//Autocomplete
		InputBean inputBeanAuto = new InputBean();
		if(item.getLogicalDataType() != null && DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
			inputBeanAuto = getInputBeanOfAutocompelte(item,listOfInputBean);
		}
		
		for (InputBean inputBean : listOfInputBean) {
			if(item.getLogicalDataType() != null && DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType()) 
					&& inputBean.getScreenItemId().equals(item.getScreenItemId())) {
				inputBean.setInputBeanCode(inputBeanAuto.getInputBeanCode());
				inputBean.setParentInputBeanCode(inputBeanAuto.getParentInputBeanCode());
				inputBean.setParentInputBeanArrayFlag(inputBeanAuto.getParentInputBeanArrayFlag());
				inputBean.setParentInputBeanId(inputBeanAuto.getParentInputBeanId());
			}
			
			if(inputBean.getInputBeanCode() != null) {
				inputBean.setInputBeanCode(GenerateSourceCodeUtil.normalizedPropertiesJSP(inputBean.getInputBeanCode()));
			}
			if(inputBean.getParentInputBeanCode() != null) {
				inputBean.setParentInputBeanCode(GenerateSourceCodeUtil.normalizedPropertiesJSP(inputBean.getParentInputBeanCode()));
			}
			if (item.getScreenItemId().equals(inputBean.getScreenItemId())) {
				lstInputBean.add(inputBean);
				// No parent
				if (inputBean.getParentInputBeanId() == null) {
					item.setInputBeanCode(inputBean.getInputBeanCode());
				}
				// Have parent
				else {
					// parent is array
					if (inputBean.getParentInputBeanArrayFlag().equals("t") && DbDomainConst.AreaType.LIST_ENTITIES.equals(item.getScreenArea().getAreaType())) {
						item.setParentInputBeanArrayFlag(inputBean.getParentInputBeanArrayFlag());
						item.setParentInputBeanCode(inputBean.getParentInputBeanCode() + "[${status.index}]");
						item.setInputBeanCode(inputBean.getInputBeanCode());
						item.setParentInputBeanCodeForTemp(inputBean.getParentInputBeanCode());
					}
					// parent is single
					else {
						item.setParentInputBeanArrayFlag(inputBean.getParentInputBeanArrayFlag());
						item.setParentInputBeanCode(inputBean.getParentInputBeanCode());
						item.setInputBeanCode(inputBean.getInputBeanCode());
					}
				}
			}
		}
		item.setInputBeans(lstInputBean);
		if(DbDomainConst.AreaType.LIST_ENTITIES.equals(item.getScreenArea().getAreaType())) {
			if(!DbDomainConst.ScreenPatternType.SEARCH.equals(item.getScreenPatternType())) {
				if(isRegisterConfirm != null && isRegisterConfirm == true) {
					if(lstInputBeanForRegisterConfirm != null) {
						for(InputBean inputInfor : lstInputBeanForRegisterConfirm) {
							if (inputInfor.getParentInputBeanArrayFlag().equals("t") && DbDomainConst.AreaType.LIST_ENTITIES.equals(item.getScreenArea().getAreaType())) {
								item.setParentInputBeanArrayFlag(inputInfor.getParentInputBeanArrayFlag());
								item.setParentInputBeanCode(inputInfor.getParentInputBeanCode() + "[${status.index}]");
								item.setInputBeanCode(inputInfor.getInputBeanCode());
								item.setParentInputBeanCodeForTemp(inputInfor.getParentInputBeanCode());
							}
							// parent is single
							else {
								item.setParentInputBeanArrayFlag(inputInfor.getParentInputBeanArrayFlag());
								item.setParentInputBeanCode(inputInfor.getParentInputBeanCode());
								item.setInputBeanCode(inputInfor.getInputBeanCode());
							}
						}
					}
				}
			}
		}
		
		
		
		return item;
	}
	
	private String generateHeaderHTML(Template tempScreenHeader, Map<String, Object> data, TempScreenDesign tempScreenDesign) throws IOException, TemplateException {
		FileOutputStream fileStream = null;
		OutputStreamWriter out = null;
		String newOutputDir = this.createModuleFolderHTML("header",0);
		
		File fileOutut = new File(newOutputDir + "header.html");
		
		fileStream = new FileOutputStream(fileOutut);
		out = new OutputStreamWriter(fileStream, StandardCharsets.UTF_8);
		tempScreenHeader.process(data, out);
		out.flush();
		return FileUtilsQP.readFileToString(fileOutut, StandardCharsets.UTF_8);
		
	}
	
	private String generateFooterHTML(Template tempScreenFooter, Map<String, Object> data, TempScreenDesign tempScreenDesign) throws IOException, TemplateException {
		FileOutputStream fileStream = null;
		OutputStreamWriter out = null;
		String newOutputDir = this.createModuleFolderHTML("footer",0);
		
		File fileOutut = new File(newOutputDir + "footer.html");
		
		fileStream = new FileOutputStream(fileOutut);
		out = new OutputStreamWriter(fileStream, StandardCharsets.UTF_8);
		tempScreenFooter.process(data, out);
		out.flush();
		
		return FileUtilsQP.readFileToString(fileOutut, StandardCharsets.UTF_8);
	}
	
	private void replaceContentHeaderFooter(String outputDirHTML, String contentHeader,String contentFooter,Integer type, String urlHome) throws FileNotFoundException, IOException {
		
		File folder = new File(outputDirHTML);
		File[] listOfFiles = folder.listFiles();

		if (ArrayUtils.isEmpty(listOfFiles)) {
			return;
		}
		
		StringBuilder contentBody = new StringBuilder();
		StringBuilder contentReplace = new StringBuilder();
		String replaceContentBody;
		
		for (int i = 0; i < listOfFiles.length; i++) {
			String[] lastName = listOfFiles[i].getPath().split("\\\\");
			String lstNameStr = lastName[lastName.length - 1].toString();
			if (listOfFiles[i].isFile()) {
				
					contentBody.append(FileUtils.readFileToString(listOfFiles[i], StandardCharsets.UTF_8));
					if (StringUtils.isBlank(contentBody)) {
						continue;
					}
					
					replaceContentBody = StringUtils.replace(contentBody.toString(), "#{homePage}", "..\\"+urlHome);
					if (!listCommonPopup.contains(lstNameStr)) {
						contentReplace.append(contentHeader);
						contentReplace.append(replaceContentBody);
						contentReplace.append(contentFooter);
					} else {
						contentReplace.append(replaceContentBody);
					}
					FileUtils.write(listOfFiles[i], contentReplace, StandardCharsets.UTF_8);
					contentBody.delete(0, contentBody.length());
					contentReplace.delete(0, contentReplace.length());
			} else if (listOfFiles[i].isDirectory()) {
				if (!lstNameStr.equals("media")) {
					replaceContentHeaderFooter(outputDirHTML + File.separator + lstNameStr,contentHeader, contentFooter,1, urlHome);
				}
			}
			
		}
	}
	
	private void replaceContentHomePageController(String outputHomePageControllerJSP, String urlHome) throws FileNotFoundException, IOException {
		
		File folder = new File(outputHomePageControllerJSP);
		File[] listOfFiles = folder.listFiles();

		if (ArrayUtils.isEmpty(listOfFiles)) {
			return;
		}
		String returnHomePage = "";
		if(urlHome != null && urlHome != "") {
			returnHomePage = "return \"redirect:/"+ GenerateSourceCodeUtil.normalizedURL(urlHome)+"\";";
		} else {
			returnHomePage = "return \"homepage/home\";";
		}
		StringBuilder contentBody = new StringBuilder();
		StringBuilder contentReplace = new StringBuilder();
		String replaceContentBody;
		
		String[] lastName = listOfFiles[0].getPath().split("\\\\");
		String lstNameStr = lastName[lastName.length - 1].toString();
			
		contentBody.append(FileUtils.readFileToString(listOfFiles[0], StandardCharsets.UTF_8));
		
		replaceContentBody = StringUtils.replace(contentBody.toString(), "#{returnHomePage}", returnHomePage);
		if (!listCommonPopup.contains(lstNameStr)) {
			contentReplace.append(replaceContentBody);
		} else {
			contentReplace.append(replaceContentBody);
		}
		
		FileUtils.write(listOfFiles[0], contentReplace, StandardCharsets.UTF_8);

		contentBody.delete(0, contentBody.length());
		contentReplace.delete(0, contentReplace.length());
	}
	
	private String generateConditionByFormula(List<FormulaDetail> lstFormulaDefinitionDetails, Map<String, String> mapOutputBean) {

		String condition = "";
		if (CollectionUtils.isNotEmpty(lstFormulaDefinitionDetails)) {
			for (FormulaDetail detail : lstFormulaDefinitionDetails) {
				if (GenerateSourceCodeConst.formulaType.containsKey(detail.getType())) {
					if(detail.getType() != 6){
						condition += GenerateSourceCodeConst.formulaType.get(detail.getType()) + " ";
					}
					else {
						condition += GenerateSourceCodeConst.formulaType.get(detail.getType());
					}
				} else {
					// if is output bean
					if (detail.getType().equals(23) && mapOutputBean.containsKey(detail.getParameterId())) {
						condition += mapOutputBean.get(detail.getParameterId()) + " ";
					}
					
					// if is function
					else if (detail.getType().equals(17)) {
						// not support
					}
					// if is value
					else if (detail.getType().equals(16)) {
						condition += detail.getValue() + " ";
					}
				}
			}
		}

		return condition;
	}

	public String getTempDir() {
		return StringUtils.appendIfMissing(tempDir, File.separator, File.separator);
	}

	public void setTempDir(String tempDir) {
		tempDir = tempDir;
	}
	
	private InputBean getInputBeanOfAutocompelte(ScreenItem item, List<InputBean> inputBeans) {
		List<InputBean> lstInp = new ArrayList<InputBean>();
		InputBean inputBeanAutocomplete = new InputBean();
		for(InputBean input : inputBeans) {
			if(item.getScreenItemId().equals(input.getScreenItemId()) && input.getScreenItemLogicalDataType() != null && DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(input.getScreenItemLogicalDataType()) && input.getBlogicRequestMethod() != null && input.getBlogicRequestMethod().equals(BusinessDesignConst.REQUEST_METHOD_PROCESSING)) {
				lstInp.add(input);
			}
		}
		if(lstInp != null && lstInp.size() > 1) {
			if(lstInp.get(0) != null && lstInp.get(1) != null && lstInp.get(0).getItemSequenceNo() < lstInp.get(1).getItemSequenceNo()) {
				inputBeanAutocomplete = lstInp.get(0);
			} else {
				inputBeanAutocomplete = lstInp.get(1);
			}
		}
		
		return inputBeanAutocomplete;
	}
	
}
