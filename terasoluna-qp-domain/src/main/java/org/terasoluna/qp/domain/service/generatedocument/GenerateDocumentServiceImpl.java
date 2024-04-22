package org.terasoluna.qp.domain.service.generatedocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.codelist.SimpleMapCodeList;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.SQLDesignType;
import org.terasoluna.qp.app.common.constants.DbDomainConst.SqlPattern;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP.FileType;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP.Folder;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.GenerateDocumentUtilsQP;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ZipUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.GenerateManagementMessageConst;
import org.terasoluna.qp.app.message.ScreenDesignMessageConst;
import org.terasoluna.qp.app.message.SqlDesignMessageConst;
import org.terasoluna.qp.domain.model.Basetype;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.BusinessType;
import org.terasoluna.qp.domain.model.CodeList;
import org.terasoluna.qp.domain.model.CodeListDetail;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.FormulaDefinition;
import org.terasoluna.qp.domain.model.FunctionDesign;
import org.terasoluna.qp.domain.model.GenerateDocument;
import org.terasoluna.qp.domain.model.GenerateDocumentDomainDesign;
import org.terasoluna.qp.domain.model.GenerateDocumentItem;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenAreaEvent;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemStatus;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignTable;
import org.terasoluna.qp.domain.model.SqlDesignTableItem;
import org.terasoluna.qp.domain.model.SubjectArea;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.TableDesignForeignKey;
import org.terasoluna.qp.domain.model.TableDesignKey;
import org.terasoluna.qp.domain.repository.account.AccountRepository;
import org.terasoluna.qp.domain.repository.accountprofile.AccountProfileRepository;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.FormulaDefinitionRepository;
import org.terasoluna.qp.domain.repository.businesstype.BusinessTypeRepository;
import org.terasoluna.qp.domain.repository.codelist.CodeListDetailRepository;
import org.terasoluna.qp.domain.repository.codelist.CodeListRepository;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignRepository;
import org.terasoluna.qp.domain.repository.functiondesign.FunctionDesignRepository;
import org.terasoluna.qp.domain.repository.generatedocument.GenerateDocumentRepository;
import org.terasoluna.qp.domain.repository.messagedesign.MessageDesignRepository;
import org.terasoluna.qp.domain.repository.module.ModuleRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.repository.screenarea.ScreenAreaRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.screenform.ScreenFormRepository;
import org.terasoluna.qp.domain.repository.screenitem.ScreenItemRepository;
import org.terasoluna.qp.domain.repository.screenitemstatus.ScreenItemStatusRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignConditionRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignGroupByRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignHavingRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignInputRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignOrderRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignOutputRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignResultRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignTableItemsRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignTableRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignValueRepository;
import org.terasoluna.qp.domain.repository.subjectarea.SubjectAreaRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignForeignKeyRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignKeyRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.service.autocomplete.SqlBuilderService;
import org.terasoluna.qp.domain.service.capture.CaptureConst;
import org.terasoluna.qp.domain.service.capture.CaptureService;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeUtil;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst.EDDocumentTypeByModule;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst.EDDocumentTypeByProject;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst.RDDocumentTypeByProject;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst.StringConstant;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignCompound;

/**
 * @author hunghx
 *
 */
@Service
@Transactional
public class GenerateDocumentServiceImpl implements GenerateDocumentService {

	private static Map<String, String> mapItemtype;

	private static Map<Integer, String> mapTabletype;

	public static StringBuilder excelFolderName;

	@Inject
	public GenerateDocumentRepository generateDocumentRepository;

	@Inject
	public BusinessTypeRepository businessTypeRepository;

	@Inject
	public ModuleRepository moduleRepository;

	@Inject
	public FunctionDesignRepository functionDesignRepository;

	@Inject
	DomainDesignRepository domainDesignRepository;

	@Inject
	SqlDesignRepository sqlDesignRepository;

	@Inject
	SqlDesignResultRepository sqlDesignResultRepository;

	@Inject
	SqlDesignTableRepository sqlDesignTableRepository;

	@Inject
	ProjectService projectService;

	@Inject
	TableDesignRepository tableDesignRepository;

	@Inject
	TableDesignDetailRepository tableDesignDetailRepository;

	@Inject
	TableDesignKeyRepository tableDesignKeyRepository;

	@Inject
	TableDesignForeignKeyRepository tableDesignForeignKeyRepository;

	@Inject
	MessageDesignRepository messageDesignRepository;

	@Inject
	SubjectAreaRepository subjectAreaRepository;

	@Inject
	@Named("CL_MESSAGE_TYPE")
	SimpleMapCodeList messageTypeCodeList;

	@Inject
	@Named("CL_GENERATE_DOCUMENT_PROJECT_RD_NAME")
	SimpleMapCodeList generateDocumentProjectRDNameCodeList;

	@Inject
	@Named("CL_GENERATE_DOCUMENT_PROJECT_ED_NAME")
	SimpleMapCodeList generateDocumentProjectEDNameCodeList;

	@Inject
	@Named("CL_GENERATE_DOCUMENT_MODULE_ED_NAME")
	SimpleMapCodeList generateDocumentModuleEDNameCodeList;

	@Inject
	@Named("CL_SCREEN_PARTTERN_TYPES")
	SimpleMapCodeList screenPartternCodeList;

	@Inject
	@Named("CL_TABLE_TYPE_ALL")
	SimpleMapCodeList tableType;

	@Inject
	@Named("CL_QP_OPERATOR_CODE")
	SimpleMapCodeList operatorCode;

	@Inject
	@Named("CL_BD_DATATYPE")
	SimpleMapCodeList dateTypeCodeList;


	@Inject
	SqlDesignTableItemsRepository sqlDesignTableItemRepository;

	@Inject
	SqlDesignConditionRepository sqlDesignConditionRepository;

	@Inject
	SqlDesignGroupByRepository sqlDesignGroupByRepository;

	@Inject
	SqlDesignOrderRepository sqlDesignOrderRepository;

	@Inject
	SqlDesignHavingRepository sqlDesignHavingRepository;

	@Inject
	SqlDesignInputRepository sqlDesignInputRepository;

	@Inject
	SqlDesignOutputRepository sqlDesignOutputRepository;

	@Inject
	SqlDesignValueRepository sqlDesignValueRepository;

	@Inject
	SqlBuilderService sqlBuilderService;

	@Inject
	ScreenDesignRepository screenDesignRepository;

	@Inject
	ScreenItemRepository screenItemRepository;

	@Inject
	ScreenFormRepository screenFormRepository;

	@Inject
	ScreenAreaRepository screenAreaRepository;

	@Inject
	CaptureService captureService;

	@Inject
	SystemService systemService;

	@Inject
	ModuleService moduleService;

	@Inject
	LanguageDesignService languageDesignService;

	@Inject
	CodeListRepository codelistRepository;

	@Inject
	CodeListDetailRepository codelistDetailRepository;

	@Inject
	FormulaDefinitionRepository formulaDefinitionRepository;

	@Inject
	ScreenItemStatusRepository screenItemStatusRepository;

	@Inject
	BusinessDesignRepository businessDesignRepository;
	
	@Inject
    AccountRepository accountRepository;
	
	@Inject
    AccountProfileRepository accountProfileRepository;
	
	@Inject
    ProjectRepository projectRepository;
    

	@Override
	public GenerateDocument getDataForGenerateDocument(GenerateDocument generateDocument, CommonModel common) {

		boolean settingFlag = true;
		if(generateDocument.getSelectType().equals(GenerateDocumentConst.PROJECT_SCOPRE)) {
			switch (generateDocument.getGenerateDocumentItem().getDocumentItemParenItemType()) {
				case GenerateDocumentConst.GENERATE_PROJECT_RD:

					switch (generateDocument.getGenerateDocumentItem().getDocumentItemType()) {

						case GenerateDocumentConst.RDDocumentTypeByProject.BUSINESS_TYPE:
							generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(generateDocumentProjectRDNameCodeList.asMap().get(String.valueOf(RDDocumentTypeByProject.BUSINESS_TYPE)));
							settingFlag = settingGenerateBusinessListRDDocumentByProject(generateDocument, common);
							break;
						case GenerateDocumentConst.RDDocumentTypeByProject.FUNCTION_LIST:
							generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(generateDocumentProjectRDNameCodeList.asMap().get(String.valueOf(RDDocumentTypeByProject.FUNCTION_LIST)));
							settingFlag = settingGenerateFunctionListRDDocumentByProject(generateDocument, common);
							break;
						case GenerateDocumentConst.RDDocumentTypeByProject.DOMAIN_DESIGN:
							generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(generateDocumentProjectRDNameCodeList.asMap().get(String.valueOf(RDDocumentTypeByProject.DOMAIN_DESIGN)));
							settingFlag = settingGenerateDomainDesignRDDocumentByProject(generateDocument, common);
							break;
						case GenerateDocumentConst.RDDocumentTypeByProject.TABLE_DESIGN:
							generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(generateDocumentProjectRDNameCodeList.asMap().get(String.valueOf(RDDocumentTypeByProject.TABLE_DESIGN)));
							settingFlag = settingGenerateTableDesignRDDocumentByProject(generateDocument, common);
							break;
						case GenerateDocumentConst.RDDocumentTypeByProject.PROCESSING_LIST:
							generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(generateDocumentProjectRDNameCodeList.asMap().get(String.valueOf(RDDocumentTypeByProject.PROCESSING_LIST)));
							settingFlag = settingGenerateProcessingListRDDocumentByProject(generateDocument, common);
							break;
						case GenerateDocumentConst.RDDocumentTypeByProject.ONLINE_PROCESSING:
							generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(generateDocumentProjectRDNameCodeList.asMap().get(String.valueOf(RDDocumentTypeByProject.ONLINE_PROCESSING)));
							settingFlag = settingGenerateOnlineProcessingRDDocumentByProject(generateDocument, "", common);
							break;
					}

					break;

				case GenerateDocumentConst.GENERATE_PROJECT_ED:
					switch (generateDocument.getGenerateDocumentItem().getDocumentItemType()) {

						case GenerateDocumentConst.EDDocumentTypeByProject.VIEW_LIST:
							generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(generateDocumentProjectEDNameCodeList.asMap().get(String.valueOf(EDDocumentTypeByProject.VIEW_LIST)));
							settingFlag = settingGenerateViewListEDDocumentByProject(generateDocument, common);
							break;
						case GenerateDocumentConst.EDDocumentTypeByProject.VIEW_DESIGN:
							generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(generateDocumentProjectEDNameCodeList.asMap().get(String.valueOf(EDDocumentTypeByProject.VIEW_DESIGN)));
							settingFlag = settingGenerateViewDesignEDDocumentByProject(generateDocument, common);
							break;
						case GenerateDocumentConst.EDDocumentTypeByProject.MESSAGE_DESIGN:
							generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(generateDocumentProjectEDNameCodeList.asMap().get(String.valueOf(EDDocumentTypeByProject.MESSAGE_DESIGN)));
							settingFlag = settingGenerateMessageDesignEDDocumentByProject(generateDocument, common);
							break;
						case GenerateDocumentConst.EDDocumentTypeByProject.SCREEN_TRANSITION_DIAGRAM:
							generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(generateDocumentProjectEDNameCodeList.asMap().get(String.valueOf(EDDocumentTypeByProject.SCREEN_TRANSITION_DIAGRAM)));
							settingFlag = settingGenerateScreenTransitionEDDocumentByProject(generateDocument, common);
							break;
						case GenerateDocumentConst.EDDocumentTypeByProject.CODE_LIST:
							generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(generateDocumentProjectEDNameCodeList.asMap().get(String.valueOf(EDDocumentTypeByProject.CODE_LIST)));
							settingFlag = settingGenerateCodelistEDDocumentByProject(generateDocument, common);
							break;
					}

					break;

				case GenerateDocumentConst.GENERATE_PROJECT_ID:

					break;
			}

		} else if(generateDocument.getSelectType().equals(GenerateDocumentConst.MODULE_SCOPRE)) {

			for (Module item : generateDocument.getModuleList()) {
				if (item.getSelectedGenerate() == 1) {
					generateDocument.setModule(item);
					break;
				}
			}

			switch (generateDocument.getGenerateDocumentItem().getDocumentItemParenItemType()) {

				case GenerateDocumentConst.GENERATE_MODULE_RD:

					break;

				case GenerateDocumentConst.GENERATE_MODULE_ED:
					// Loop for ed document is selected
					switch (generateDocument.getGenerateDocumentItem().getDocumentItemType()) {

						case GenerateDocumentConst.EDDocumentTypeByModule.SCREEN_DESIGN_DOC:
							settingGenerateScreenDesignEDDocumentByModule(generateDocument, common);
							break;
					}

					break;

				case GenerateDocumentConst.GENERATE_MODULE_ID:

					break;
			}
		}

		// check data setting
		if( !settingFlag ){
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.INF_SYS_0013));
		}

		// Process download zip file in the case document is single
		if(!GenerateDocumentUtilsQP.isMultipleDocumentType(generateDocument.getGenerateDocumentItem())){
			processGenerateDocumentExcelFile(generateDocument, common);
		}

		return generateDocument;
	}

	private void settingGenerateScreenDesignEDDocumentByProject(GenerateDocument generateDocument, CommonModel common) {

		List<ScreenDesign> lstAllScreenDesign = screenDesignRepository.getAllScreenInfoByProjectId(generateDocument.getProject().getProjectId(), common.getWorkingLanguageId());
		List<GenerateDocumentItem> generateDocumentItemLst = new ArrayList<GenerateDocumentItem>();
		int count = 0;
		for (ScreenDesign item : lstAllScreenDesign) {
			GenerateDocumentItem generateItem = new GenerateDocumentItem(); generateDocument.getGenerateDocumentItem();
			// setting document id
			generateItem.setId(item.getScreenId().toString());
			// setting document name
			generateItem.setDocumentItemName(item.getMessageDesign().getMessageString());
			// setting document code
			generateItem.setDocumentItemCode(item.getScreenCode());
			// setting document file name
			generateItem.setDocumentItemFileName(item.getScreenCode() + ".xlsx");
			// Setting default select document at first row
			generateItem.setIsChecked(count == 0?true:false);
			// Clone information from document item
			generateItem.setDocumentItemScopeItemType(generateDocument.getGenerateDocumentItem().getDocumentItemScopeItemType());
			generateItem.setDocumentItemParenItemType(generateDocument.getGenerateDocumentItem().getDocumentItemParenItemType());
			generateItem.setDocumentItemTemplateName(generateDocument.getGenerateDocumentItem().getDocumentItemTemplateName());
			generateItem.setDocumentItemType(generateDocument.getGenerateDocumentItem().getDocumentItemType());
			generateDocumentItemLst.add(generateItem);
			count++;
		}

		// Setting header for item
		//generateDocument.getGenerateDocumentItem().setDocumentItemCollaseName(MessageUtils.getMessage(CommonMessageConst.TQP_SCREENDESIGN));
		// Header name
		//generateDocument.getGenerateDocumentItem().setDocumentItemName(MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005));
		// Header code
		//generateDocument.getGenerateDocumentItem().setDocumentItemCode(MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007));
		// Setting document details
		generateDocument.setGenerateDocumentItemLst(generateDocumentItemLst);
	}

	// setting for bat job
	private boolean settingGenerateScreenTransitionEDDocumentByProject(GenerateDocument generateDocument, String capturePath, CommonModel common) {

		/*List<Module> moduleLst = moduleRepository.findAllModuleOfProject(workingProjectId, null);*/
		List<Module> moduleLst = moduleRepository.findAllModuleOfOnline(common.getWorkingProjectId(), DbDomainConst.FunctionType.ONLINE);
		List<GenerateDocumentItem> generateDocumentItemLst = new ArrayList<GenerateDocumentItem>();

		// capture all screen
		List<Long> moduleIdLst = new ArrayList<Long>();
		moduleIdLst.add(null);
		for (Module item : moduleLst) {
			moduleIdLst.add(item.getModuleId());
		}

		GenerateDocumentUtilsQP.createFolder(capturePath);
		captureService.captureScreenTransDiagram(moduleIdLst, common.getWorkingProjectId(), common.getWorkingLanguageId(), "" ,capturePath);

		GenerateDocumentItem generateItem = new GenerateDocumentItem();
		generateItem.setId(null);
		String itemPath = capturePath + CaptureConst.SCREEN_TRANSITION_DIAGRAM_CAPTURE_NAME + generateItem.getId() + CaptureConst.PNG_EXT;
		generateItem.setCapturePath(itemPath);
		generateDocumentItemLst.add(generateItem);

		for (Module item : moduleLst) {
			generateItem = new GenerateDocumentItem();
			generateItem.setId(item.getModuleId().toString());
			generateItem.setData(item);
			itemPath = capturePath + CaptureConst.SCREEN_TRANSITION_DIAGRAM_CAPTURE_NAME + item.getModuleId() + CaptureConst.PNG_EXT;
			generateItem.setCapturePath(itemPath);
			generateDocumentItemLst.add(generateItem);
		}
		if(CollectionUtils.isNotEmpty(generateDocumentItemLst)) {
			generateDocument.getGenerateDocumentItem().setDataLst(generateDocumentItemLst);
			return true;
		}
		else {
			return false;
		}

	}
	// setting for web
	private boolean settingGenerateScreenTransitionEDDocumentByProject(GenerateDocument generateDocument, CommonModel common) {

		/*List<Module> moduleLst = moduleRepository.findAllModuleOfProject(workingProjectId, null);*/
	    //Fix current project 20160613
		List<Module> moduleLst = moduleRepository.findAllModuleOfOnline(common.getWorkingProjectId(), DbDomainConst.FunctionType.ONLINE);
		
		if(CollectionUtils.isEmpty(moduleLst)) {
			return false;
		}

		List<GenerateDocumentItem> generateDocumentItemLst = new ArrayList<GenerateDocumentItem>();

		// capture all screen
		List<Long> moduleIdLst = new ArrayList<Long>();
		moduleIdLst.add(null);
		for (Module item : moduleLst) {
			moduleIdLst.add(item.getModuleId());
		}
		String capturePath = FileUtilsQP.generateTemporaryFolderPath();
		GenerateDocumentUtilsQP.createFolder(capturePath);
		if( !captureService.checkPhantomJsPath()){
			throw new BusinessException(ResultMessages.error().add(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0025));
		}
		captureService.captureScreenTransDiagram(moduleIdLst, common.getWorkingProjectId(), common.getWorkingLanguageId(), "" ,capturePath);

		GenerateDocumentItem generateItem = new GenerateDocumentItem();
		generateItem.setId(null);
		String itemPath = capturePath + CaptureConst.SCREEN_TRANSITION_DIAGRAM_CAPTURE_NAME + generateItem.getId() + CaptureConst.PNG_EXT;
		generateItem.setCapturePath(itemPath);
		generateDocumentItemLst.add(generateItem);

		for (Module item : moduleLst) {

			generateItem = new GenerateDocumentItem();
			generateItem.setId(item.getModuleId().toString());
			generateItem.setData(item);
			itemPath = capturePath + CaptureConst.SCREEN_TRANSITION_DIAGRAM_CAPTURE_NAME + item.getModuleId() + CaptureConst.PNG_EXT;
			generateItem.setCapturePath(itemPath);

			generateDocumentItemLst.add(generateItem);
		}

		if(CollectionUtils.isNotEmpty(generateDocumentItemLst)) {
			generateDocument.getGenerateDocumentItem().setDataLst(generateDocumentItemLst);
			return true;
		}
		else {
			return false;
		}
	}

	private void settingGenerateScreenDesignEDDocumentByModule(GenerateDocument generateDocument, CommonModel common) {

	    //Fix current project 20160613
		List<ScreenDesign> lstAllScreenDesign = screenDesignRepository.getAllScreenInfoByModuleId(generateDocument.getModule().getModuleId(), common.getWorkingLanguageId());
		List<GenerateDocumentItem> generateDocumentItemLst = new ArrayList<GenerateDocumentItem>();
		int count = 0;
		for (ScreenDesign item : lstAllScreenDesign) {
			GenerateDocumentItem generateItem = new GenerateDocumentItem(); generateDocument.getGenerateDocumentItem();
			// setting document id
			generateItem.setId(item.getScreenId().toString());
			// setting document name
			generateItem.setDocumentItemName(item.getMessageDesign().getMessageString());
			// setting document code
			generateItem.setDocumentItemCode(item.getScreenCode());
			// setting document file name
			generateItem.setDocumentItemFileName(item.getScreenCode() + ".xlsx");
			// Setting default select document at first row
			generateItem.setIsChecked(count == 0?true:false);
			// Clone information from document item
			generateItem.setDocumentItemScopeItemType(generateDocument.getGenerateDocumentItem().getDocumentItemScopeItemType());
			generateItem.setDocumentItemParenItemType(generateDocument.getGenerateDocumentItem().getDocumentItemParenItemType());
			generateItem.setDocumentItemTemplateName(generateDocument.getGenerateDocumentItem().getDocumentItemTemplateName());
			generateItem.setDocumentItemType(generateDocument.getGenerateDocumentItem().getDocumentItemType());
			generateDocumentItemLst.add(generateItem);
			count++;
		}

		// Setting header for item
		generateDocument.getGenerateDocumentItem().setDocumentItemCollaseName(MessageUtils.getMessage(CommonMessageConst.TQP_SCREENDESIGN));
		// Header name
		generateDocument.getGenerateDocumentItem().setDocumentItemName(MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005));
		// Header code
		generateDocument.getGenerateDocumentItem().setDocumentItemCode(MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007));
		// Setting document details
		if(CollectionUtils.isNotEmpty(generateDocumentItemLst)) {
			generateDocument.setGenerateDocumentItemLst(generateDocumentItemLst);
		} else{
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.INF_SYS_0013));
		}
	}

	private boolean settingGenerateBusinessListRDDocumentByProject(GenerateDocument generateDocument, CommonModel common) {

	    //Fix current project 20160613
		List<BusinessType> businessTypeLst = businessTypeRepository.findAll(common.getWorkingProjectId());
		if(CollectionUtils.isNotEmpty(businessTypeLst)) {
			generateDocument.getGenerateDocumentItem().setDataLst(businessTypeLst);
			return true;
		}
		else {
			return false;
		}
	}

	private boolean settingGenerateViewListEDDocumentByProject(GenerateDocument generateDocument, CommonModel common) {
		List<SqlDesign> viewDesignList = sqlDesignRepository.getAllViewDesignByProjectId(common.getWorkingProjectId());

		if(CollectionUtils.isNotEmpty(viewDesignList)) {
			generateDocument.getGenerateDocumentItem().setDataLst(viewDesignList);
			return true;
		}
		else {
			return false;
		}
	}
	private boolean settingGenerateViewDesignEDDocumentByProject(GenerateDocument generateDocument, CommonModel common) {
	    //Fix current project 20160613
		List<SqlDesign> viewDesignList = sqlDesignRepository.getAllViewDesignByProjectId(common.getWorkingProjectId());
		//Fix current project 20160613
		//sqlBuilderService.initData(common.getWorkingProjectId(), common.getWorkingLanguageId(), common.getCreatedBy());
		for (SqlDesign sqlDesign:viewDesignList) {
			sqlDesign.setSqlText(sqlBuilderService.buildSql(this.findSqlDesignCompoundById(sqlDesign.getSqlDesignId()),String.valueOf(generateDocument.getProject().getDbType()), common));

			if (sqlDesign.getDesignType()==SQLDesignType.ADVANCED_VIEW) {
				sqlBuilderService.analyzeAdvanced(sqlDesign);
			} else {
				sqlDesign.setSqlDesignResults(Arrays.asList(sqlDesignResultRepository.findAllBySqlDesignId(sqlDesign.getSqlDesignId())));
				sqlDesign.setSqlDesignTables(Arrays.asList(sqlDesignTableRepository.findAllBySqlDesignId(sqlDesign.getSqlDesignId())));
			}

		}

		if(CollectionUtils.isNotEmpty(viewDesignList)) {
			generateDocument.getGenerateDocumentItem().setDataLst(viewDesignList);
			return true;
		}
		else {
			return false;
		}
	}
	public SqlDesign findSqlDesignById(Long sqlDesignId) {
		SqlDesign sqlDesign = sqlDesignRepository.findOneById(sqlDesignId);
		if(sqlDesign == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN)));
		}
		return sqlDesign;
	}
	public SqlDesignCompound findSqlDesignCompoundById(Long sqlDesignId) {
		SqlDesignCompound sqlDesignCompound = null;
		SqlDesign sqlDesign = this.findSqlDesignById(sqlDesignId);
		sqlDesignCompound = new SqlDesignCompound();
		sqlDesignCompound.setSqlDesign(sqlDesign);
		sqlDesignCompound.setSqlDesignTables(sqlDesignTableRepository.findAllBySqlDesignId(sqlDesignId));
		List<SqlDesignTableItem> sqlDesignTableItems = sqlDesignTableItemRepository.findAllBySqlDesignId(sqlDesignId);

		Map<Long,List<SqlDesignTableItem>> sqlDesignTableItemsMap = new HashMap<Long,List<SqlDesignTableItem>>();
		for(SqlDesignTableItem item:sqlDesignTableItems){
			if(sqlDesignTableItemsMap.get(item.getSqlDesignTableId())==null){
				sqlDesignTableItemsMap.put(item.getSqlDesignTableId(),new ArrayList<SqlDesignTableItem>());
			}
			sqlDesignTableItemsMap.get(item.getSqlDesignTableId()).add(item);
		}

		for(SqlDesignTable sqldesignTable : sqlDesignCompound.getSqlDesignTables()){
			List<SqlDesignTableItem> temporaryList = sqlDesignTableItemsMap.get(sqldesignTable.getSqlDesignTableId());
			if(CollectionUtils.isNotEmpty(temporaryList)) {
				sqldesignTable.setSqlDesignTableItems(temporaryList.toArray(new SqlDesignTableItem[temporaryList.size()]));
			}
		}
		if(SqlPattern.SELECT != sqlDesign.getSqlPattern()){
			sqlDesignCompound.setSqlDesignTable(ArrayUtils.isEmpty(sqlDesignCompound.getSqlDesignTables())?null:sqlDesignCompound.getSqlDesignTables()[0]);
		}
		sqlDesignCompound.setSqlDesignConditions(sqlDesignConditionRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignOrders(sqlDesignOrderRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignGroupBys(sqlDesignGroupByRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignHavings(sqlDesignHavingRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignResults(sqlDesignResultRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignInputs(sqlDesignInputRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignOutputs(sqlDesignOutputRepository.findAllBySqlDesignId(sqlDesignId));
		sqlDesignCompound.setSqlDesignValues(sqlDesignValueRepository.findAllBySqlDesignId(sqlDesignId));
		return sqlDesignCompound;

	}
	private boolean settingGenerateDomainDesignRDDocumentByProject(GenerateDocument item, CommonModel common) {

		List<GenerateDocumentDomainDesign> domainDesigns = generateDocumentRepository.findAllDomainByProjectId(common.getWorkingProjectId());
		List<String> validationRules = domainDesignRepository.getAllValidationRule();
		List<Basetype> listOfBasetype = domainDesignRepository.getAllBasetypeOnly(common.getWorkingProjectId());
		if(CollectionUtils.isNotEmpty(domainDesigns)){
			item.getGenerateDocumentItem().setDataLst(domainDesigns);
			item.getGenerateDocumentItem().setData(validationRules);
			item.getGenerateDocumentItem().setListBasetype(listOfBasetype);
			return true;
		} else{
			return false;
		}
	}

	private boolean settingGenerateMessageDesignEDDocumentByProject(GenerateDocument item, CommonModel common) {
		// Get all design message by projectid
		List<MessageDesign> messageDesigns = messageDesignRepository.getAllMessageDesignByProjectId(common.getWorkingProjectId());

		// Set message type name
		for (MessageDesign messageDesign : messageDesigns) {
			messageDesign.setMessageTypeName(this.settingMessageLevel(messageDesign.getMessageType()));
		}
		// Get all language design by projectid
		//Fix current project 20160613
		//languageDesignService.setWorkingProjectId(common.getWorkingProjectId());
		List<LanguageDesign> languageDesignList = languageDesignService.findLanguageDesignByProjectId(common.getWorkingProjectId());

		if(CollectionUtils.isNotEmpty(messageDesigns)){
			item.getGenerateDocumentItem().setDataLst(messageDesigns);
			item.getGenerateDocumentItem().setLanguageDesignLst(languageDesignList);
			return true;
		} else {
			return false;
		}

	}

	private boolean settingGenerateTableDesignRDDocumentByProject(GenerateDocument item, CommonModel common) {

		//list table by project and subject area
		List<TableDesign> listTableDesign = tableDesignRepository.getTableDesignByProjectAndSubArea(common.getWorkingProjectId(), null);
		// get all row
		List<TableDesignDetails> listTableDesignDetails = tableDesignDetailRepository.getAllTableDesignDetails(common.getWorkingProjectId());
		// get foreign key
		List<TableDesignForeignKey> listTableDesignForeignKey = tableDesignForeignKeyRepository.getAllByProject(common.getWorkingProjectId());
		// get key
		List<TableDesignKey> listTableDesignKey = tableDesignKeyRepository.getAllByProject(common.getWorkingProjectId());

		List<DomainDesign> domainDesigns = domainDesignRepository.findAllByProjectId(common.getWorkingProjectId());

		List<Basetype> listOfBasetype = domainDesignRepository.getAllBasetypeOnly(common.getWorkingProjectId());

		int numOfTableDetails = FunctionCommon.isEmpty(listTableDesignDetails) ? 0 : listTableDesignDetails.size();
		int numOfFK = FunctionCommon.isEmpty(listTableDesignForeignKey) ? 0 : listTableDesignForeignKey.size();
		int numOfTable = FunctionCommon.isEmpty(listTableDesign) ? 0 : listTableDesign.size();
		int numOfKey = FunctionCommon.isEmpty(listTableDesignKey) ? 0 : listTableDesignKey.size();

		for (int i = 0; i < numOfTable && numOfTableDetails > 0; i++) {
			TableDesign tableDesign = listTableDesign.get(i);
			// process row
			List<TableDesignDetails> listOfRow = new ArrayList<TableDesignDetails>();
			for (int k = 0; k < numOfTableDetails; k++) {
				TableDesignDetails tableDesignDetails = listTableDesignDetails.get(k);
				// if table detail is child of table
				if (tableDesignDetails.getTableDesignId().equals(tableDesign.getTableDesignId())) {
					// process fk
					List<TableDesignForeignKey> listFK = new ArrayList<TableDesignForeignKey>();
					for (int j = 0; j < numOfFK; j++) {
						TableDesignForeignKey tableDesignForeignKey = listTableDesignForeignKey.get(j);
						if (tableDesignForeignKey.getFromColumnId().equals(tableDesignDetails.getColumnId())) {
							listFK.add(tableDesignForeignKey);
						}
					}
					tableDesignDetails.setForeignKeys(listFK);
					tableDesignDetails.setDataTypeName(this.convertDatypeToDataName(listOfBasetype, tableDesignDetails.getDataType()));
					for (DomainDesign domainDesign : domainDesigns) {
						if(domainDesign.getDomainId().equals(tableDesignDetails.getDataType())){
							tableDesignDetails.setDomainName(domainDesign.getDomainName());
							tableDesignDetails.setDataType(new Long(domainDesign.getBaseType()));
							tableDesignDetails.setMaxlength(domainDesign.getMaxLength());
							tableDesignDetails.setDataTypeName(this.convertDatypeToDataName(listOfBasetype, new Long(domainDesign.getBaseType())));
						}
					}
					listOfRow.add(tableDesignDetails);
					numOfTableDetails--;
					listTableDesignDetails.remove(k);// remove
					k--;
				}
			}
			Collections.sort(listOfRow);// sort by item_seq_no
			tableDesign.setDetails(listOfRow);
			// Process key
			List<TableDesignKey> listOfKey = new ArrayList<TableDesignKey>();
			for (int n = 0; n < numOfKey; n++) {
				TableDesignKey tableDesignKey = listTableDesignKey.get(n);
				if (tableDesignKey.getTableDesignId().equals(tableDesign.getTableDesignId())) {
					tableDesignKey.setKeyItems(DomainDatatypeUtil.convertStringToArrayList(tableDesignKey.getStrKeyItems(), DomainDatatypeUtil.STR_REGEX));
					listOfKey.add(tableDesignKey);
					//resize list
					listTableDesignKey.remove(n);
					n--;
					numOfKey--;
				}
			}
			tableDesign.setTableKey(listOfKey);

			// process Subject Area
			List<SubjectArea> subjectAreas = new ArrayList<SubjectArea>();
			subjectAreas = subjectAreaRepository.getAllSubAreaByTableId(tableDesign.getTableDesignId());
			tableDesign.setSubjectAreas(subjectAreas);
			tableDesign.setTableType(this.settingTableType(tableDesign.getType()));
		}

		if(CollectionUtils.isNotEmpty(listTableDesign)){
			item.getGenerateDocumentItem().setDataLst(listTableDesign);

			// Set base type list and domain list
			item.getGenerateDocumentItem().setListBasetype(listOfBasetype);
			item.getGenerateDocumentItem().setListDomainDesigns(domainDesigns);

			// Set code list map
			Map<String, SimpleMapCodeList> codeListMap = new HashMap<String, SimpleMapCodeList>();
			codeListMap.put("tableType", tableType);
			codeListMap.put("operatorCode", operatorCode);
			item.getGenerateDocumentItem().setMapCodeList(codeListMap);
			return true;
		} else {
			return false;
		}
	}

	private boolean settingGenerateFunctionListRDDocumentByProject(GenerateDocument generateDocument, CommonModel common) {

		List<Module> moduleLst = moduleRepository.findAllDistincModuleByProjectId(common.getWorkingProjectId());
		List<FunctionDesign> functionDesignLst = functionDesignRepository.findAllFunctionDesignByProjectId(common.getWorkingProjectId());

		if(!CollectionUtils.isEmpty(functionDesignLst)) {

			Set<Long> btIdList = new HashSet<Long>();

			for (Module module : moduleLst) {
				if(module.getBusinessTypeId() == null){
					module.setBusinessTypeId(0L);
					module.setBusinessTypeName(StringUtils.EMPTY);
				}
				List<FunctionDesign> fdLst = new ArrayList<FunctionDesign>();
				for (FunctionDesign fdItem : functionDesignLst) {
					if(module.getModuleId().equals(fdItem.getModuleId())){
						fdLst.add(fdItem);
					}
				}
				module.setListFunctionDesign(fdLst);
				if (!btIdList.contains(module.getBusinessTypeId())) {
					btIdList.add(module.getBusinessTypeId());
				}
			}


			List<BusinessType> businessTypeLst = new ArrayList<BusinessType>();
			for (Long l : btIdList) {
				BusinessType bt = new BusinessType();
				List<Module> md = new ArrayList<Module>();
				for (Module module : moduleLst) {
					if(module.getBusinessTypeId().equals(l) && module.getListFunctionDesign().size() > 0) {
						md.add(module);
						bt.setBusinessTypeId(module.getBusinessTypeId());
						bt.setBusinessTypeName(module.getBusinessTypeName());
					}
				}
				if( md.size() > 0) {
					bt.setModules(md);
					businessTypeLst.add(bt);
				}
			}

			if(CollectionUtils.isNotEmpty(businessTypeLst)) {
				generateDocument.getGenerateDocumentItem().setDataLst(businessTypeLst);
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	private boolean settingGenerateProcessingListRDDocumentByProject(GenerateDocument generateDocument, CommonModel common) {

		List<BusinessDesign> businessDesignLst = functionDesignRepository.findAllBusinessDesignByProjectId(common.getWorkingProjectId());
		List<BusinessType> businessTypeLst = functionDesignRepository.findAllBusinessTypeByProjectId(common.getWorkingProjectId());
		List<BusinessDesign> businessDesignFilteredLst = new ArrayList<BusinessDesign>();

		for (BusinessDesign businessDesign : businessDesignLst) {
			if (businessDesign.getBusinessTypeId() != null ){
				for (BusinessType businessType : businessTypeLst) {
					if (businessDesign.getBusinessTypeId().equals(businessType.getBusinessTypeId().toString())) {
						//businessDesign.setBusinessTypeId(businessType.getBusinessTypeId().toString());
						businessDesign.setBusinessTypeName(businessType.getBusinessTypeName());
						businessDesign.setBusinessTypeCode(businessType.getBusinessTypeCode());
						break;
					}
				}
				businessDesignFilteredLst.add(businessDesign);
			}
		}



		if(CollectionUtils.isNotEmpty(businessDesignFilteredLst)) {
			generateDocument.getGenerateDocumentItem().setDataLst(businessDesignFilteredLst);
			return true;
		} else {
			return false;
		}
	}

	private boolean settingGenerateOnlineProcessingRDDocumentByProject(GenerateDocument generateDocument, String capturePath, CommonModel common) {

		List<BusinessDesign> businessDesignLst = functionDesignRepository.findAllBusinessDesignByProjectId(common.getWorkingProjectId());
		List<BusinessType> businessTypeLst = functionDesignRepository.findAllBusinessTypeByProjectId(common.getWorkingProjectId());
		List<InputBean> lstInputBean = businessDesignRepository.findInputBeanByBlogicIds(common.getWorkingLanguageId(), common.getWorkingProjectId(), businessDesignLst);
		List<OutputBean> lstOutputBean = businessDesignRepository.findOuputBeanByBlogicIds(common.getWorkingLanguageId(), common.getWorkingProjectId(), businessDesignLst);

		Map<Long, List<InputBean>> mapInputBean = new HashMap<Long, List<InputBean>>();
		for(InputBean item : lstInputBean){
			Long key = item.getBusinessLogicId();
			item.setLogicalDataType(dateTypeCodeList.asMap().get(String.valueOf(item.getDataType())));
			if(mapInputBean.containsKey(key)){
				mapInputBean.get(key).add(item);
			}
			else {
				List<InputBean> value = new ArrayList<InputBean>();
				value.add(item);
				mapInputBean.put(key, value);
			}
		}

		Map<Long, List<OutputBean>> mapOutputBean = new HashMap<Long, List<OutputBean>>();
		for(OutputBean item : lstOutputBean){
			Long key = item.getBusinessLogicId();
			item.setLogicalDataType(dateTypeCodeList.asMap().get(String.valueOf(item.getDataType())));
			if(mapOutputBean.containsKey(key)){
				mapOutputBean.get(key).add(item);
			}
			else {
				List<OutputBean> value = new ArrayList<OutputBean>();
				value.add(item);
				mapOutputBean.put(key, value);
			}
		}

		Map<String, BusinessType> mapBusinessType = new HashMap<String, BusinessType>();
		for (BusinessType businessType : businessTypeLst) {
			String key = businessType.getBusinessTypeId().toString();
			mapBusinessType.put(key, businessType);
		}

		List<Long> businessLogicIdLst = new ArrayList<Long>();
		for (BusinessDesign businessDesign : businessDesignLst) {
			businessLogicIdLst.add(businessDesign.getBusinessLogicId());
		}
		if(StringUtils.isEmpty(capturePath)){
			capturePath = FileUtilsQP.generateTemporaryFolderPath();
			GenerateDocumentUtilsQP.createFolder(capturePath);
		}

		if( !captureService.checkPhantomJsPath()){
			throw new BusinessException(ResultMessages.error().add(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0025));
		}
		captureService.captureBusinessDesign(businessLogicIdLst, common.getWorkingProjectId(), common.getWorkingLanguageId(), "" ,capturePath);


		List<GenerateDocumentItem> generateDocumentItemLst = new ArrayList<GenerateDocumentItem>();
		for (BusinessDesign businessDesign : businessDesignLst) {

			// add list input bean
			if( mapInputBean.containsKey(businessDesign.getBusinessLogicId())){
				businessDesign.setLstInputBean(mapInputBean.get(businessDesign.getBusinessLogicId()));
			}
			else {
				businessDesign.setLstInputBean( new ArrayList<InputBean>());
			}
			// add list output bean
			if( mapOutputBean.containsKey(businessDesign.getBusinessLogicId())){
				businessDesign.setLstOutputBean(mapOutputBean.get(businessDesign.getBusinessLogicId()));
			}
			else {
				businessDesign.setLstOutputBean( new ArrayList<OutputBean>());
			}
			// add business type
			if (businessDesign.getBusinessTypeId() != null ){
				if(mapBusinessType.containsKey(businessDesign.getBusinessTypeId())){
					BusinessType businessType = mapBusinessType.get((businessDesign.getBusinessTypeId()));
					businessDesign.setBusinessTypeName(businessType.getBusinessTypeName());
					businessDesign.setBusinessTypeCode(businessType.getBusinessTypeCode());
				}
			}
			GenerateDocumentItem generateItem = new GenerateDocumentItem();
			generateItem.setData(businessDesign);
			String itemPath = capturePath + CaptureConst.BUSINESS_DESIGN_CAPTURE_NAME + businessDesign.getBusinessLogicId() + CaptureConst.PNG_EXT;
			generateItem.setCapturePath(itemPath);
			generateDocumentItemLst.add(generateItem);
		}

		if(CollectionUtils.isNotEmpty(generateDocumentItemLst)) {
			generateDocument.getGenerateDocumentItem().setDataLst(generateDocumentItemLst);
			return true;
		} else {
			return false;
		}
	}

	private String settingMessageLevel(String levelCode){

		//if mapItemtype is null then initial for this
		initItemtypeCodelist();

		return mapItemtype.get(levelCode);
	}

	private String settingTableType(int tableType){

		//if mapItemtype is null then initial for this
		initTableTypeypeCodelist();

		return mapTabletype.get(tableType);
	}

	/**
	 *
	 * @param listOfBasetype
	 * @param dataType
	 * @return
	 */
	private String convertDatypeToDataName(List<Basetype> listOfBasetype, Long dataType){
		for (Basetype basetype : listOfBasetype) {
			if(basetype.getBasetyeId().equals(dataType)){
				return basetype.getBasetypeName();
			}
		}
		return StringUtils.EMPTY;
	}

	/**
	 * return Map contain item type with label multi language
	 * @return
	 * @author dungnn1
	 */
	private void initTableTypeypeCodelist() {
		mapTabletype = new HashMap<Integer, String>();

		for(String key : tableType.asMap().keySet()){
			try {
				mapTabletype.put(Integer.parseInt(key), MessageUtils.getMessage(tableType.asMap().get(key)));
			} catch (Exception ex ) {
				//if don't configuration then show message code
				mapTabletype.put(Integer.parseInt(key), tableType.asMap().get(key));
			}
		}
	}

	/**
	 * return Map contain item type with label multi language
	 * @return
	 * @author dungnn1
	 */
	private void initItemtypeCodelist() {
		mapItemtype = new HashMap<String, String>();

		for(String key : messageTypeCodeList.asMap().keySet()){
			try {
				mapItemtype.put(key, MessageUtils.getMessage(messageTypeCodeList.asMap().get(key)));
			} catch (Exception ex ) {
				//if don't configuration then show message code
				mapItemtype.put(key, messageTypeCodeList.asMap().get(key));
			}
		}
	}

	/*public String generateDatetimeSystem() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		return dateFormat.format(date);
	}*/

	private void settingInforCommonForDocUtil(CommonModel common) {
		GenerateDocumentUtilsQP.currentAccount =  accountRepository.findOneByAccountId(common.getCreatedBy());
		GenerateDocumentUtilsQP.currentAccounProfile = accountProfileRepository.findOne(common.getCreatedBy());
		GenerateDocumentUtilsQP.currentProject = projectRepository.findById(common.getWorkingProjectId(), common.getCreatedBy());
	}

	@Override
	public GenerateDocument processGenerateDocumentExcelFile(GenerateDocument obj, CommonModel common) {
		settingInforCommonForDocUtil(common);
		obj.setExportPath(FileUtilsQP.generateTemporaryFolderPath());

		if(obj.getSelectType().equals(GenerateDocumentConst.PROJECT_SCOPRE)) {
			switch (obj.getGenerateDocumentItem().getDocumentItemParenItemType()) {
				case GenerateDocumentConst.GENERATE_PROJECT_RD:
					excelFolderName = new StringBuilder().append(obj.getExportPath());
					excelFolderName.append(Folder.EXCEL_RD);
					excelFolderName.append(File.separator);
					GenerateDocumentUtilsQP.createFolder(excelFolderName.toString());
					obj.getGenerateDocumentItem().setExcelFolder(excelFolderName);
					GenerateDocumentUtilsQP.processGenerateRDDocumentByProject(obj.getGenerateDocumentItem());
					break;

				case GenerateDocumentConst.GENERATE_PROJECT_ED:
					excelFolderName = new StringBuilder().append(obj.getExportPath());
					excelFolderName.append(Folder.EXCEL_ED);
					excelFolderName.append(File.separator);
					GenerateDocumentUtilsQP.createFolder(excelFolderName.toString());
					obj.getGenerateDocumentItem().setExcelFolder(excelFolderName);
					try {
						GenerateDocumentUtilsQP.processGenerateEDDocumentByProject(obj.getGenerateDocumentItem());
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				case GenerateDocumentConst.GENERATE_PROJECT_ID:

					break;
			}

		} else if(obj.getSelectType().equals(GenerateDocumentConst.MODULE_SCOPRE)) {

			GenerateDocumentItem item = null;

			if(!GenerateDocumentUtilsQP.isMultipleDocumentType(obj.getGenerateDocumentItem())){
				item = obj.getGenerateDocumentItem();
				for (Module iterator : obj.getModuleList()) {
					if (iterator.getSelectedGenerate() == 1) {
						item.setId( String.valueOf(iterator.getModuleId()));
						break;
					}
				}

			} else {
				for (GenerateDocumentItem iterator : obj.getGenerateDocumentItemLst()) {
					if (iterator.getIsChecked() == true) {
						item = iterator;
						break;
					}
				}
			}

			// End update HungHX
			String capturePath = FileUtilsQP.generateTemporaryFolderPath();
			GenerateDocumentUtilsQP.createFolder(capturePath);

			switch (item.getDocumentItemParenItemType()) {

				case GenerateDocumentConst.GENERATE_MODULE_RD:

					break;

				case GenerateDocumentConst.GENERATE_MODULE_ED:
					// Loop for ed document is selected
					
					excelFolderName = new StringBuilder().append(obj.getExportPath());
					excelFolderName.append(Folder.EXCEL_ED);
					excelFolderName.append(File.separator);
					GenerateDocumentUtilsQP.createFolder(excelFolderName.toString());
					item.setExcelFolder(excelFolderName);

					switch (item.getDocumentItemType()) {

						case GenerateDocumentConst.EDDocumentTypeByModule.SCREEN_DESIGN_DOC:
							
							item.setDocumentItemTemplateName(generateDocumentModuleEDNameCodeList.asMap().get(String.valueOf(GenerateDocumentConst.EDDocumentTypeByModule.SCREEN_DESIGN_DOC)));
							setDataForGenDesignDoc(item, common);
							if( !captureService.checkPhantomJsPath()){
								throw new BusinessException(ResultMessages.error().add(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0025));
							}
							//Fix get current project 20160613
							capturePath = captureService.captureScreenDesign( new Long(item.getId()), common.getWorkingProjectId(), common.getWorkingLanguageId(), capturePath);
							item.setCapturePath(capturePath);
							GenerateDocumentUtilsQP.processGenerateEDDocumentByModule(item);
							break;
						case GenerateDocumentConst.EDDocumentTypeByModule.SCREEN_TRANSITION_DIAGRAM:
							
							item.setDocumentItemTemplateName(generateDocumentModuleEDNameCodeList.asMap().get(String.valueOf(GenerateDocumentConst.EDDocumentTypeByModule.SCREEN_TRANSITION_DIAGRAM)));
							setDataForGenScreenTransDoc(item);
							if( !captureService.checkPhantomJsPath()){
								throw new BusinessException(ResultMessages.error().add(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0025));
							}
							//Fix get current project 20160613
							capturePath = captureService.captureScreenTransDiagram( new Long(item.getId()), common.getWorkingProjectId(), common.getWorkingLanguageId(), capturePath);
							item.setCapturePath(capturePath);
							GenerateDocumentUtilsQP.processGenerateEDDocumentByModule(item);
							break;
					}
					break;

				case GenerateDocumentConst.GENERATE_MODULE_ID:

					break;
			}
		}

		if (StringUtils.isNoneBlank(excelFolderName)){
			String exportFath = FileUtilsQP.getExportFolder();
			GenerateDocumentUtilsQP.createFolder(exportFath);
			StringBuilder fileName =  new StringBuilder(obj.getProject().getProjectCode());
			fileName.append(StringConstant.UNDERLINE);
			fileName.append(GenerateUniqueKey.generateAutoCode());
			fileName.append(FileType.ZIP);
			String pathFolderZip = obj.getExportPath();

			try {
				ZipUtils.Zip(pathFolderZip, exportFath+fileName);
				obj.setFileName(fileName.toString());
			} catch (IOException e) {
				obj.setFileName(null);
				e.printStackTrace();
			} finally {
				GenerateDocumentUtilsQP.deleteFile(excelFolderName.toString());
			}
		}

		return obj;
	}
	public void setDataForGenScreenTransDoc(GenerateDocumentItem item){

		Long moduleId = Long.parseLong(item.getId());
		Module module = moduleService.findModuleById(moduleId);
		item.setData(module);

	}

	public void setDataForGenDesignDoc(GenerateDocumentItem item, CommonModel common){

		Long screenId = Long.valueOf(item.getId());

		//Fix current project 20160613
		ScreenDesign screenDesign  = screenDesignRepository.getScreenDesignByScreenId(screenId, common.getWorkingLanguageId());
		List<ScreenForm> screenForms = screenFormRepository.getScreenFormByScreenId(screenId);
		List<ScreenArea> screenAreas = screenAreaRepository.getLstScreenAreaByScreenId(screenId, common.getWorkingLanguageId(), common.getProjectId());
		List<ScreenItem> screenItems = screenItemRepository.getAllScreenItemByScreenIdForGenScreenDesign( screenId , common.getWorkingLanguageId());
		List<ScreenAreaEvent> screenAreaEvents = screenDesignRepository.findScreenAreaEventByScreenId(screenId);

		List<FormulaDefinition> formulaDefinitions = formulaDefinitionRepository.getFormulaDefinitionByScreenFormId(screenForms);
		List<ScreenItemStatus> screenItemStatusesAll = screenItemStatusRepository.getScreenItemStatusByFormulaDefinitionId(formulaDefinitions);

		// create map formula definitions
		Map<Long, List<FormulaDefinition>> mapFormulaDefinitions = new HashMap<Long, List<FormulaDefinition>>();
		for(FormulaDefinition formulaDefinition: formulaDefinitions){
			if(mapFormulaDefinitions.containsKey(formulaDefinition.getScreenFormId())){
				mapFormulaDefinitions.get(formulaDefinition.getScreenFormId()).add(formulaDefinition);
			}
			else {
				List<FormulaDefinition> newItem = new ArrayList<FormulaDefinition>();
				newItem.add(formulaDefinition);
				mapFormulaDefinitions.put(formulaDefinition.getScreenFormId(), newItem);
			}
		}

		// create map screen item status
		Map<Long, List<ScreenItemStatus>> mapScreenItemStatus = new HashMap<Long, List<ScreenItemStatus>>();
		for(ScreenItemStatus screenItemStatus: screenItemStatusesAll){
			if(mapScreenItemStatus.containsKey(screenItemStatus.getItemId())){
				mapScreenItemStatus.get(screenItemStatus.getItemId()).add(screenItemStatus);
			}
			else {
				List<ScreenItemStatus> newItem = new ArrayList<ScreenItemStatus>();
				newItem.add(screenItemStatus);
				mapScreenItemStatus.put(screenItemStatus.getItemId(), newItem);
			}
		}


		Map<Long, List<ScreenItem>> mapScreenItems = new HashMap<Long, List<ScreenItem>>();
		for(ScreenItem screenItem : screenItems){

			if(mapScreenItemStatus.containsKey(screenItem.getScreenItemId())){
				screenItem.setScreenItemStatusLst(mapScreenItemStatus.get(screenItem.getScreenItemId()));
			}
			else {
				screenItem.setScreenItemStatusLst(new ArrayList<ScreenItemStatus>());
			}

			if(mapScreenItems.containsKey(screenItem.getScreenAreaId())){
				mapScreenItems.get(screenItem.getScreenAreaId()).add(screenItem);
			}
			else {
				List<ScreenItem> newItem = new ArrayList<ScreenItem>();
				newItem.add(screenItem);
				mapScreenItems.put(screenItem.getScreenAreaId(), newItem);
			}
		}
		Map<Long, List<ScreenAreaEvent>> mapsSreenAreaEvents = new HashMap<Long, List<ScreenAreaEvent>>();
		for(ScreenAreaEvent event : screenAreaEvents){

			if(mapsSreenAreaEvents.containsKey(event.getScreenAreaId())){
				mapsSreenAreaEvents.get(event.getScreenAreaId()).add(event);
			}
			else {
				List<ScreenAreaEvent> newItem = new ArrayList<ScreenAreaEvent>();
				newItem.add(event);
				mapsSreenAreaEvents.put(event.getScreenAreaId(), newItem);
			}
		}

		// set list item for screen area
		Map<Long, List<ScreenArea>> mapScreenAreas = new HashMap<Long, List<ScreenArea>>();
		for(ScreenArea screenArea : screenAreas){

			// add screen item status to screen area
			if(mapScreenItemStatus.containsKey(screenArea.getScreenAreaId())){
				screenArea.setScreenItemStatusLst(mapScreenItemStatus.get(screenArea.getScreenAreaId()));
			}
			else {
				screenArea.setScreenItemStatusLst(new ArrayList<ScreenItemStatus>());
			}
			// add screen items to screen area
			if(mapScreenItems.containsKey(screenArea.getScreenAreaId())){
				screenArea.setListItems(mapScreenItems.get(screenArea.getScreenAreaId()));
			}
			else {
				screenArea.setListItems(new ArrayList<ScreenItem>());
			}
			// add screen event to screen area
			if(mapsSreenAreaEvents.containsKey(screenArea.getScreenAreaId())){
				screenArea.setScreenAreaEvents(mapsSreenAreaEvents.get(screenArea.getScreenAreaId()));
			}
			else {
				screenArea.setScreenAreaEvents(new ArrayList<ScreenAreaEvent>());
			}

			if(mapScreenAreas.containsKey(screenArea.getScreenFormId())){
				mapScreenAreas.get(screenArea.getScreenFormId()).add(screenArea);
			}
			else {
				List<ScreenArea> newItem = new ArrayList<ScreenArea>();
				newItem.add(screenArea);
				mapScreenAreas.put(screenArea.getScreenFormId(), newItem);
			}

		}
		// set list area for screen form
		for(ScreenForm screenForm : screenForms){

			if(mapFormulaDefinitions.containsKey(screenForm.getScreenFormId())){
				screenForm.setFormulaDefinition(mapFormulaDefinitions.get(screenForm.getScreenFormId()));
			}
			else {
				screenForm.setFormulaDefinition( new ArrayList<FormulaDefinition>());
			}

			if(mapScreenAreas.containsKey(screenForm.getScreenFormId())){
				screenForm.setListScreenAreas(mapScreenAreas.get(screenForm.getScreenFormId()));
			}
			else {
				screenForm.setListScreenAreas(new ArrayList<ScreenArea>());
			}

		}

		screenDesign.setScreenForms(screenForms.toArray(new ScreenForm[screenForms.size()]));
		item.setData(screenDesign);
	}

	@Override
	public String processGenerateAllDocement(Project project, String exportPath, String capturePath, CommonModel common) {

		settingInforCommonForDocUtil(common);

		GenerateDocumentItem generateDocumentItem = new GenerateDocumentItem();
		GenerateDocument generateDocument = new GenerateDocument();
		generateDocument.setProject(project);
		generateDocument.setGenerateDocumentItem(generateDocumentItem);
		String fileName = project.getProjectCode() + GenerateUniqueKey.generateWithDatePrefix();
		String baseExcelFolderName = capturePath + File.separator + fileName + File.separator;
		try {
			GenerateDocumentUtilsQP.createFolder(baseExcelFolderName.toString());
			String templateName = "";
			int itemType;
			// project rd document
			StringBuilder rdFolderName = new StringBuilder().append(baseExcelFolderName + Folder.RD + File.separator);
			GenerateDocumentUtilsQP.createFolder(rdFolderName.toString());
			generateDocument.getGenerateDocumentItem().setExcelFolder(rdFolderName);
			generateDocument.getGenerateDocumentItem().setDocumentItemParenItemType(GenerateDocumentConst.GENERATE_PROJECT_RD);
			// Business list
			templateName = generateDocumentProjectRDNameCodeList.asMap().get(String.valueOf(RDDocumentTypeByProject.BUSINESS_TYPE));
			itemType = RDDocumentTypeByProject.BUSINESS_TYPE;
			generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(templateName);
			generateDocument.getGenerateDocumentItem().setDocumentItemType(itemType);
			boolean flag = settingGenerateBusinessListRDDocumentByProject(generateDocument, common);
			if( flag ){
				GenerateDocumentUtilsQP.processGenerateRDDocumentByProject(generateDocument.getGenerateDocumentItem());
			}
			// Function list
			templateName = generateDocumentProjectRDNameCodeList.asMap().get(String.valueOf(RDDocumentTypeByProject.FUNCTION_LIST));
			itemType = RDDocumentTypeByProject.FUNCTION_LIST;
			generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(templateName);
			generateDocument.getGenerateDocumentItem().setDocumentItemType(itemType);
			flag = settingGenerateFunctionListRDDocumentByProject(generateDocument, common);
			if( flag ){
				GenerateDocumentUtilsQP.processGenerateRDDocumentByProject(generateDocument.getGenerateDocumentItem());
			}
			// Domain define doc
			templateName = generateDocumentProjectRDNameCodeList.asMap().get(String.valueOf(RDDocumentTypeByProject.DOMAIN_DESIGN));
			itemType = RDDocumentTypeByProject.DOMAIN_DESIGN;
			generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(templateName);
			generateDocument.getGenerateDocumentItem().setDocumentItemType(itemType);
			flag = settingGenerateDomainDesignRDDocumentByProject(generateDocument, common);
			if( flag ){
				GenerateDocumentUtilsQP.processGenerateRDDocumentByProject(generateDocument.getGenerateDocumentItem());
			}
			// Processing list
			templateName = generateDocumentProjectRDNameCodeList.asMap().get(String.valueOf(RDDocumentTypeByProject.PROCESSING_LIST));
			itemType = RDDocumentTypeByProject.PROCESSING_LIST;
			generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(templateName);
			generateDocument.getGenerateDocumentItem().setDocumentItemType(itemType);
			flag = settingGenerateProcessingListRDDocumentByProject(generateDocument, common);
			if( flag ){
				GenerateDocumentUtilsQP.processGenerateRDDocumentByProject(generateDocument.getGenerateDocumentItem());
			}
			// Table Design
			templateName = generateDocumentProjectRDNameCodeList.asMap().get(String.valueOf(RDDocumentTypeByProject.TABLE_DESIGN));
			itemType = RDDocumentTypeByProject.TABLE_DESIGN;
			generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(templateName);
			generateDocument.getGenerateDocumentItem().setDocumentItemType(itemType);
			flag = settingGenerateTableDesignRDDocumentByProject(generateDocument, common);
			if( flag ){
				GenerateDocumentUtilsQP.processGenerateRDDocumentByProject(generateDocument.getGenerateDocumentItem());
			}
			// business design
			templateName = generateDocumentProjectRDNameCodeList.asMap().get(String.valueOf(RDDocumentTypeByProject.ONLINE_PROCESSING));
			itemType = RDDocumentTypeByProject.ONLINE_PROCESSING;
			generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(templateName);
			generateDocument.getGenerateDocumentItem().setDocumentItemType(itemType);
			flag = settingGenerateOnlineProcessingRDDocumentByProject(generateDocument, capturePath, common);
			if( flag ){
				GenerateDocumentUtilsQP.processGenerateRDDocumentByProject(generateDocument.getGenerateDocumentItem());
			}

			// project ed document
			StringBuilder edFolderName = new StringBuilder().append(baseExcelFolderName + Folder.ED + File.separator);
			GenerateDocumentUtilsQP.createFolder(edFolderName.toString());
			generateDocument.getGenerateDocumentItem().setExcelFolder(edFolderName);
			generateDocument.getGenerateDocumentItem().setDocumentItemParenItemType(GenerateDocumentConst.GENERATE_PROJECT_ED);
			// View list
			templateName = generateDocumentProjectEDNameCodeList.asMap().get(String.valueOf(EDDocumentTypeByProject.VIEW_LIST));
			itemType = EDDocumentTypeByProject.VIEW_LIST;
			generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(templateName);
			generateDocument.getGenerateDocumentItem().setDocumentItemType(itemType);
			flag = settingGenerateViewListEDDocumentByProject(generateDocument, common);
			if( flag ){
				GenerateDocumentUtilsQP.processGenerateEDDocumentByProject(generateDocument.getGenerateDocumentItem());
			}
			// View design
			templateName = generateDocumentProjectEDNameCodeList.asMap().get(String.valueOf(EDDocumentTypeByProject.VIEW_DESIGN));
			itemType = EDDocumentTypeByProject.VIEW_DESIGN;
			generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(templateName);
			generateDocument.getGenerateDocumentItem().setDocumentItemType(itemType);
			flag = settingGenerateViewDesignEDDocumentByProject(generateDocument, common);
			if( flag ){
				GenerateDocumentUtilsQP.processGenerateEDDocumentByProject(generateDocument.getGenerateDocumentItem());
			}
			// Message design
			templateName = generateDocumentProjectEDNameCodeList.asMap().get(String.valueOf(EDDocumentTypeByProject.MESSAGE_DESIGN));
			itemType = EDDocumentTypeByProject.MESSAGE_DESIGN;
			generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(templateName);
			generateDocument.getGenerateDocumentItem().setDocumentItemType(itemType);
			flag = settingGenerateMessageDesignEDDocumentByProject(generateDocument, common);
			if( flag ){
				GenerateDocumentUtilsQP.processGenerateEDDocumentByProject(generateDocument.getGenerateDocumentItem());
			}
			// screen transition diagram
			templateName = generateDocumentProjectEDNameCodeList.asMap().get(String.valueOf(GenerateDocumentConst.EDDocumentTypeByProject.SCREEN_TRANSITION_DIAGRAM));
			itemType = EDDocumentTypeByProject.SCREEN_TRANSITION_DIAGRAM;
			generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(templateName);
			generateDocument.getGenerateDocumentItem().setDocumentItemType(itemType);
			flag = settingGenerateScreenTransitionEDDocumentByProject(generateDocument, capturePath, common);
			if( flag ){
				GenerateDocumentUtilsQP.processGenerateEDDocumentByProject(generateDocument.getGenerateDocumentItem());
			}
			// code list
			templateName = generateDocumentProjectEDNameCodeList.asMap().get(String.valueOf(GenerateDocumentConst.EDDocumentTypeByProject.CODE_LIST));
			itemType = EDDocumentTypeByProject.CODE_LIST;
			generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(templateName);
			generateDocument.getGenerateDocumentItem().setDocumentItemType(itemType);
			flag = settingGenerateCodelistEDDocumentByProject(generateDocument, common);
			if( flag ){
				GenerateDocumentUtilsQP.processGenerateEDDocumentByProject(generateDocument.getGenerateDocumentItem());
			}

			// Module
			// Screen design
			StringBuilder sdFolderName = new StringBuilder().append(baseExcelFolderName + Folder.ED_SRCEEN_DESIGN + File.separator);
			GenerateDocumentUtilsQP.createFolder(sdFolderName.toString());
			generateDocument.getGenerateDocumentItem().setExcelFolder(sdFolderName);
			templateName = generateDocumentModuleEDNameCodeList.asMap().get(String.valueOf(GenerateDocumentConst.EDDocumentTypeByModule.SCREEN_DESIGN_DOC));
			itemType = EDDocumentTypeByModule.SCREEN_DESIGN_DOC;
			generateDocument.getGenerateDocumentItem().setDocumentItemTemplateName(templateName);
			generateDocument.getGenerateDocumentItem().setDocumentItemType(itemType);
			settingGenerateScreenDesignEDDocumentByProject(generateDocument, common);
			for(GenerateDocumentItem item : generateDocument.getGenerateDocumentItemLst()){
				item.setExcelFolder(sdFolderName);
				setDataForGenDesignDoc(item, common);
				item.setCapturePath(capturePath + item.getId() + CaptureConst.PNG_EXT);
				GenerateDocumentUtilsQP.processGenerateEDDocumentByModule(item);
			}

			if (StringUtils.isNoneBlank(baseExcelFolderName)){
				StringBuilder fileFullName =  new StringBuilder(fileName);
				/*fileName.append(StringConstant.UNDERLINE);
				fileFullName.append(GenerateUniqueKey.generateWithDatePrefix());*/
				fileFullName.append(FileType.ZIP);

				try {
					ZipUtils.Zip(baseExcelFolderName.toString(), exportPath + fileFullName);
					generateDocument.setFileName(fileFullName.toString());
				} catch (IOException e) {
					throw new SystemException("", e);
				}
			}
		}
		catch (Exception ex) {
			throw new SystemException("", ex);
		}
		finally {
			GenerateDocumentUtilsQP.deleteFolder(baseExcelFolderName.toString());
			GenerateDocumentUtilsQP.deleteFolder(capturePath.toString());
		}
		return generateDocument.getFileName();
	}

	private boolean settingGenerateCodelistEDDocumentByProject(GenerateDocument generateDocument, CommonModel common) {
		List<CodeList> listCodelist = codelistRepository.getCodeListByProject(common.getWorkingProjectId());
		Map<String, List<CodeListDetail>> mapData = new HashMap<String, List<CodeListDetail>>();

		for(int i = 0; i < listCodelist.size(); i++) {
			List<CodeListDetail> listCodelistDetail = codelistDetailRepository.getCodeListDetail(listCodelist.get(i).getCodeListId());
			mapData.put(listCodelist.get(i).getCodeListName(), listCodelistDetail);
		}

		if(CollectionUtils.isNotEmpty(listCodelist)){
			generateDocument.getGenerateDocumentItem().setDataLst(listCodelist);
			generateDocument.getGenerateDocumentItem().setData(mapData);
			return true;
		}
		else {
			return false;
		}

	}
}
