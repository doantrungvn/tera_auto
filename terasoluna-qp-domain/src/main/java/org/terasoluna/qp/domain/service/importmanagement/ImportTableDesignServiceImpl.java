package org.terasoluna.qp.domain.service.importmanagement;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.codelist.SimpleMapCodeList;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.YesNoFlg;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.GenerateDocumentUtilsQP;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ImportManagementMessageConst;
import org.terasoluna.qp.app.message.TableDesignMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.Basetype;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.GenerateDocumentItem;
import org.terasoluna.qp.domain.model.ImportManagement;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.SubjectArea;
import org.terasoluna.qp.domain.model.SubjectAreaTableDesign;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.TableDesignForeignKey;
import org.terasoluna.qp.domain.model.TableDesignKey;
import org.terasoluna.qp.domain.model.TableDesignKeyItem;
import org.terasoluna.qp.domain.model.UserDefineCodelist;
import org.terasoluna.qp.domain.model.UserDefineCodelistDetails;
import org.terasoluna.qp.domain.model.ValidationRule;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.domaindatatype.DomainDatatypeRepository;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignRepository;
import org.terasoluna.qp.domain.repository.domaindesign.ResourceRepository;
import org.terasoluna.qp.domain.repository.module.ModuleRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignConditionRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignOutputRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignResultRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignTableItemsRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignTableRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignValueRepository;
import org.terasoluna.qp.domain.repository.subjectarea.SubjectAreaRepository;
import org.terasoluna.qp.domain.repository.subjectarea.SubjectAreaTableRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignForeignKeyRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignKeyRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.repository.tabledesign.UserDefineCodelistRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignHelper;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignShareService;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;
import org.terasoluna.qp.domain.service.domaindesign.DomainDesignService;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst.RDDocumentTypeByProject;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.TableDesignUtil;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.tabledesign.TableDesignService;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

@Service
@Transactional(rollbackFor = Exception.class)
public class ImportTableDesignServiceImpl implements ImportTableDesignService {

	@Inject
	DomainDesignRepository domainDesignRepository;
	
	@Inject
	DomainDesignService domainDesignService;
	
	@Inject
	TableDesignService tableDesignService;
	
	@Inject
	TableDesignRepository tableDesignRepository;
	
	@Inject
	TableDesignDetailRepository tableDesignDetailRepository;
	
	@Inject
	TableDesignKeyRepository tableDesignKeyRepository;
	
	@Inject 
	SystemService systemService;

	@Inject
	SubjectAreaRepository subjectAreaRepository;

	@Inject
	SubjectAreaTableRepository subjectAreaTableRepository;

	@Inject
	SqlDesignRepository sqlDesignRepository;
	
	@Inject
	TableDesignForeignKeyRepository tableDesignForeignKeyRepository;
	
	@Inject
	UserDefineCodelistRepository userDefineCodelistRepository;
	
	@Inject
	DomainDatatypeRepository domainDatatypeRepository;
	
	@Inject
	ProjectRepository projectRepository;
	
	@Inject
	ModuleRepository moduleRepository;
	
	@Inject
	BusinessDesignRepository businessDesignRepository;
	
	@Inject
	ProblemListRepository problemListRepository;
	
	@Inject
	SqlDesignConditionRepository sqlDesignConditionRepository;
	
	@Inject
	SqlDesignResultRepository sqlDesignResultRepository;
	
	@Inject
	SqlDesignTableRepository sqlDesignTableRepository;
	
	@Inject
	SqlDesignTableItemsRepository sqlDesignTableItemsRepository;
	
	@Inject
	SqlDesignValueRepository sqlDesignValueRepository;
	
	@Inject
	ProjectService projectService;
	
	@Inject
	ResourceRepository resourceRepository; 
	
	@Inject
	DomainDesignRepository domainRepository;
	
	@Inject
	BusinessDesignShareService businessDesignShareService;
	
	@Inject 
	SqlDesignOutputRepository sqlDesignOutputRepository;
	
	@Inject
	@Named("CL_TABLE_TYPE")
	SimpleMapCodeList tableType;
	
	@Inject
	@Named("CL_QP_OPERATOR_CODE")
	SimpleMapCodeList operatorCode;
	
	@Inject
	@Named("CL_GENERATE_DOCUMENT_PROJECT_RD_NAME")
	SimpleMapCodeList generateDocumentProjectRDNameCodeList;
	
	private int LIMIT_ROW_IMPORT = 0;
	
	private List<DomainDesign> listOfDomainDesign;
	
	private List<Basetype> listOfBasetype;
	
	private static final String INVERSE_BULLET_SYSBOL = "◘";
	
	private static final String REPLACEMENT_CHARACTER_SYSBOL = "�";

	private Integer maxLengOfCode;
	
	public Integer getMaxLengOfCode() {
		return maxLengOfCode;
	}

	public void setMaxLengOfCode(Integer maxLengOfCode) {
		this.maxLengOfCode = maxLengOfCode;
	}

	@Override
	public Map<String, Object> importData(ImportManagement importManagement, Project project, Long accountId, CommonModel commonModel) {
		
		Long projectId = project.getProjectId();
//		tableDesignService.initData(projectId, accountId);
		String filePath = importManagement.getFilePath();
		boolean isRollback = importManagement.isRollback();
		boolean isDelete = importManagement.isDelete();
		Map<String, List<String[]>> inputData = new HashMap<String, List<String[]>>();
		int importCount = 0;
		LIMIT_ROW_IMPORT = ImportUtils.getLimitRowImport();
		List<String[]> errors = new ArrayList<String[]>();
		String dbType = String.valueOf(project.getDbType());
		
		//get input data to file
		inputData = this.getInputData(filePath);
		
		//set table rule for validate
		List<TableField> listDataTableInfoTableField = setTableFieldRuleForTableDesign();
		List<TableField> listDataTableDetailTableField = setTableFieldRuleForTableDesignDetail();
		
		// Get domain design list and base type list
		listOfDomainDesign = domainDesignRepository.findAllByProjectId(projectId);
		listOfBasetype = domainDesignRepository.getAllBasetypeOnly(projectId);
		
		// Store Table
		List<TableDesign> listTableDesigns = new ArrayList<TableDesign>();
		List<TableDesignDetails> listAllTableDesignDetails = new ArrayList<TableDesignDetails>();
		
		// validate data type and validate business of import data 
		int numberOfSheet = inputData.size() / 2;
		for(int i = 2; i < numberOfSheet + 2; i++) {
			List<String[]> tempListSummary = inputData.get("InfoSheet" + i);
			List<String[]> tempListDetail = inputData.get("DetailSheet" + i);
			
			Map<String, Object> mapSummary = ImportUtils.validate(tempListSummary, listDataTableInfoTableField);
			Map<String, Object> mapDetail = ImportUtils.validate(tempListDetail, listDataTableDetailTableField);
			
			// result after validate
			List<String[]> errorsSummary = (List<String[]>) mapSummary.get(ImportManagementConst.ERR_WHEN_IMPORT);
			List<String[]> rowsDataSummary = new ArrayList<String[]>();
			rowsDataSummary = (List<String[]>) mapSummary.get(ImportManagementConst.IMPORTTING_DATA);
			List<Integer> rowsSummary = (List<Integer>) mapSummary.get(ImportManagementConst.ROWS);
			// Get table design information
			TableDesign tableDesign = convertArrayTableDesign(rowsDataSummary, projectId, accountId);
			
			List<String[]> errorsDetail = (List<String[]>) mapDetail.get(ImportManagementConst.ERR_WHEN_IMPORT);
			List<String[]> rowsDataDetail = new ArrayList<String[]>();
			rowsDataDetail = (List<String[]>) mapDetail.get(ImportManagementConst.IMPORTTING_DATA);
			List<Integer> rowsDetail = (List<Integer>) mapDetail.get(ImportManagementConst.ROWS);
			
			// Get table design details
			List<TableDesignDetails> tableDesignDetail = convertArrayTableDesignDetail(tableDesign, rowsDataDetail);
			listAllTableDesignDetails.addAll(tableDesignDetail);
			
			for (TableDesignDetails tableDesignDetails : tableDesignDetail) {
				tableDesignDetails.setTableDesignCode(tableDesign.getTableCode());
			}
			
			tableDesign.setListTableDesignDetails(tableDesignDetail);
			listTableDesigns.add(tableDesign);
		}
		
		// Set ID for table design details
		Long getSequencesTableDesignDetails = tableDesignDetailRepository.getSequencesTableDesignDetails(listAllTableDesignDetails.size());
		Long startSequenceTableDesignDetails = getSequencesTableDesignDetails - (listAllTableDesignDetails.size() - 1);
		
		for (TableDesignDetails tableDesignDetails : listAllTableDesignDetails) {
			tableDesignDetails.setColumnId(startSequenceTableDesignDetails);
			startSequenceTableDesignDetails++;
		}
		
		// Set Id for table design
		Long getSequencesTableDesign = tableDesignRepository.getSequencesTableDesign(listTableDesigns.size());
		Long startSequenceTable = getSequencesTableDesign - (listTableDesigns.size() - 1);
		
		for (TableDesign tableDesign2 : listTableDesigns) {
			
			tableDesign2.setTableDesignId(startSequenceTable);
			for (TableDesignDetails designDetails : tableDesign2.getListTableDesignDetails()) {
				designDetails.setTableDesignId(startSequenceTable);
			}
			
			// Set table
			startSequenceTable++;
		}
		
		
		for (TableDesign tableDesign : listTableDesigns) {
			// Set Id for FK
			List<TableDesignForeignKey> tableDesignForeignKeys = tableDesign.getTableDesignForeignKeys();
			
			if(FunctionCommon.isNotEmpty(tableDesignForeignKeys)){
				for (TableDesignForeignKey tableDesignForeignKey : tableDesignForeignKeys) {
					// Set Id for Table
					for (TableDesign table : listTableDesigns) {
						if(tableDesignForeignKey.getFromTableCode().equalsIgnoreCase(table.getTableCode())){
							tableDesignForeignKey.setFromTableId(table.getTableDesignId());
						}
						if(tableDesignForeignKey.getToTableCode().equalsIgnoreCase(table.getTableCode())){
							tableDesignForeignKey.setToTableId(table.getTableDesignId());
						}
					}
					
					// Set Id for Column
					for (TableDesignDetails tableDesignDetails : listAllTableDesignDetails) {
						if(tableDesignForeignKey.getFromColumnCode().equalsIgnoreCase(tableDesignDetails.getCode())){
							tableDesignForeignKey.setFromColumnId(tableDesignDetails.getColumnId());
						}
						if(tableDesignForeignKey.getToColumnCode().equalsIgnoreCase(tableDesignDetails.getCode())){
							tableDesignForeignKey.setToColumnId(tableDesignDetails.getColumnId());
						}
					}
				}
			}
		}
		
		
		// Get all table by project
		List<TableDesign> listTableDesignsFromDbNoneColumn = tableDesignRepository.getTableDesignByProjectId(projectId);
		List<TableDesign> listTableDesignsFromDb = new ArrayList<TableDesign>();
		for (TableDesign tableDesign : listTableDesignsFromDbNoneColumn) {
			listTableDesignsFromDb.add(tableDesignService.loadTableDesign(tableDesign.getTableDesignId(), commonModel));
		}
		List<TableDesign> listTableDuplicateFromDB = new ArrayList<TableDesign>();
		List<TableDesign> listTableDuplicateFromImport = new ArrayList<TableDesign>();
		List<TableDesign> listTableInsertNew = new ArrayList<TableDesign>();
		
		List<TableDesign> listTableDelete = new ArrayList<TableDesign>();
		
		boolean check = false;
		
		for (TableDesign tableDesign : listTableDesigns) {
			check = false;
			for (TableDesign table : listTableDesignsFromDb) {
				table.setIsDeleted(false);
				if(table.getTableCode().equals(tableDesign.getTableCode())){
					listTableDuplicateFromImport.add(tableDesign);
					listTableDuplicateFromDB.add(table);
					check = true;
					break;
				}
			}
			if(!check){
				listTableInsertNew.add(tableDesign);
			}
		}
		
		for (TableDesign tableDesign : listTableDesignsFromDb) {
			check = false;
			for (TableDesign table : listTableDuplicateFromDB) {
				if(table.getTableCode().equals(tableDesign.getTableCode())){
					check = true;
					break;
				}
			}
			if(!check){
				tableDesign.setIsDeleted(false);
				if(!DbDomainConst.TableDesignType.QP_TABLE.equals(tableDesign.getType())){
					listTableDelete.add(tableDesign);
				}
			}
		}
		
		List<TableDesign> listTableExport = new ArrayList<TableDesign>();
		
		int rowInserted = 0;
		int totalRowInsert = 0;
		
		if(isRollback){
			boolean hasErr = false;
			listTableExport.addAll(listTableInsertNew);
			if(FunctionCommon.isNotEmpty(listTableInsertNew)){
				if(!this.validateData(listTableInsertNew, true, dbType)){
					//Insert new Table from Import file
					int countErr = 0;
					for (TableDesign tableDesign : listTableInsertNew) {
						try {
							this.processAddNewTables(tableDesign, project, accountId, commonModel);
						} catch (BusinessException ex) {
							tableDesign.setStatusImport("Error");
							tableDesign.setContentStatusImport(ex.getResultMessages().toString());
							countErr++;
							break;
						}
					}
					if(countErr == 0){
						rowInserted = listTableInsertNew.size();
						hasErr = false;
//						for (TableDesign tableDesign : listTableInsertNew) {
//							this.insertKey(tableDesign, project.getProjectId());
//						}
					}else{
						hasErr = true;
					}
				}else{
					hasErr = true;
				}
			}
			
			totalRowInsert = listTableInsertNew.size();
			
			// Process table duplicate. If not affect: delete, else modify.
			List<TableDesign> listTableUsed = this.processTableDuplicate(listTableDuplicateFromImport, listTableDuplicateFromDB, project, accountId, isDelete);
			
			if(!hasErr){
				for (TableDesign tableDesign : listTableInsertNew) {
					
					// Set Id for FK
					List<TableDesignForeignKey> tableDesignForeignKeys = tableDesign.getTableDesignForeignKeys();
					
					if(FunctionCommon.isNotEmpty(tableDesignForeignKeys)){
						for (TableDesignForeignKey tableDesignForeignKey : tableDesignForeignKeys) {
							// Set Id for Table
							for (TableDesign table : listTableUsed) {
								if(tableDesignForeignKey.getFromTableCode().equalsIgnoreCase(table.getTableCode())){
									tableDesignForeignKey.setFromTableId(table.getTableDesignId());
								}
								if(tableDesignForeignKey.getToTableCode().equalsIgnoreCase(table.getTableCode())){
									tableDesignForeignKey.setToTableId(table.getTableDesignId());
								}
							}
							
							// Set Id for Column
							for (TableDesignDetails tableDesignDetails : listAllTableDesignDetails) {
								if(tableDesignForeignKey.getFromColumnCode().equalsIgnoreCase(tableDesignDetails.getCode())){
									tableDesignForeignKey.setFromColumnId(tableDesignDetails.getColumnId());
								}
								if(tableDesignForeignKey.getToColumnCode().equalsIgnoreCase(tableDesignDetails.getCode())){
									tableDesignForeignKey.setToColumnId(tableDesignDetails.getColumnId());
								}
							}
						}
					}
					
					this.insertKey(tableDesign, project.getProjectId());
				}
			}
			
			listTableExport.addAll(listTableUsed);
			
			if(FunctionCommon.isNotEmpty(listTableUsed)){
				if(!this.validateData(listTableUsed, false, dbType) && !hasErr){
					//Insert new Table from Import file
					int countErr = 0;
					for (TableDesign tableDesign : listTableUsed) {
						try {
							tableDesignService.modifyTable(tableDesign, commonModel, project, true);
						} catch (BusinessException ex) {
							tableDesign.setStatusImport("Error");
							tableDesign.setContentStatusImport(ex.getResultMessages().toString());
							countErr++;
							break;
						}
					}
					if(countErr == 0){
						hasErr = false;
					}else{
						hasErr = true;
					}
				}else{
					hasErr = true;
				}
			}
			
			if(!hasErr){
				if(isDelete){
					// Delete from DB
					tableDesignRepository.multiDelete(listTableDelete);
				}
			}
		}else{
			//Insert new Table from Import file
			listTableExport.addAll(listTableInsertNew);
			this.validateData(listTableInsertNew, true, dbType);
			List<TableDesign> inserted = new ArrayList<TableDesign>();
			int count = 0;
			for (TableDesign tableDesign : listTableInsertNew) {
				if(!this.hasErrInTable(tableDesign)){
					try {
						this.processAddNewTables(tableDesign, project, accountId, commonModel);
						inserted.add(tableDesign);
						count++;
					} catch (BusinessException ex) {
						tableDesign.setStatusImport("Error");
						tableDesign.setContentStatusImport(ex.getResultMessages().toString());
						continue;
					}
				}
			}
			
			totalRowInsert = listTableInsertNew.size();
			rowInserted = count;
			
			// Process table duplicate. If not affect: delete, else modify.
			List<TableDesign> listTableUsed = this.processTableDuplicate(listTableDuplicateFromImport, listTableDuplicateFromDB, project, accountId, isDelete);
			
			for (TableDesign tableDesign : inserted) {
				
				// Set Id for FK
				List<TableDesignForeignKey> tableDesignForeignKeys = tableDesign.getTableDesignForeignKeys();
				
				if(FunctionCommon.isNotEmpty(tableDesignForeignKeys)){
					for (TableDesignForeignKey tableDesignForeignKey : tableDesignForeignKeys) {
						// Set Id for Table
						for (TableDesign table : listTableUsed) {
							if(tableDesignForeignKey.getFromTableCode().equalsIgnoreCase(table.getTableCode())){
								tableDesignForeignKey.setFromTableId(table.getTableDesignId());
							}
							if(tableDesignForeignKey.getToTableCode().equalsIgnoreCase(table.getTableCode())){
								tableDesignForeignKey.setToTableId(table.getTableDesignId());
							}
						}
						
						// Set Id for Column
						for (TableDesignDetails tableDesignDetails : listAllTableDesignDetails) {
							if(tableDesignForeignKey.getFromColumnCode().equalsIgnoreCase(tableDesignDetails.getCode())){
								tableDesignForeignKey.setFromColumnId(tableDesignDetails.getColumnId());
							}
							if(tableDesignForeignKey.getToColumnCode().equalsIgnoreCase(tableDesignDetails.getCode())){
								tableDesignForeignKey.setToColumnId(tableDesignDetails.getColumnId());
							}
						}
					}
				}
				this.insertKey(tableDesign, project.getProjectId());
			}
			
			listTableExport.addAll(listTableUsed);
			this.validateData(listTableUsed, true, dbType);
			// Modify table affect.
			for (TableDesign tableDesign : listTableUsed) {
				if(!this.hasErrInTable(tableDesign)){
					try {
						tableDesignService.modifyTable(tableDesign, commonModel, project, true);
					} catch (BusinessException ex) {
						tableDesign.setStatusImport("Error");
						tableDesign.setContentStatusImport(ex.getResultMessages().toString());
						continue;
					}
				}
			}
			if(isDelete){
				// Delete from DB
				tableDesignRepository.multiDelete(listTableDelete);
			}
		}
		
		List<DomainDesign> domainDesigns = domainDesignRepository.findAllByProjectId(projectId);
		
		List<Basetype> listOfBasetype = domainDesignRepository.getAllBasetypeOnly(projectId);
		
		GenerateDocumentItem item = new GenerateDocumentItem();
		
		List<TableDesignForeignKey> listTableDesignForeignKey = new ArrayList<TableDesignForeignKey>();
		
		List<TableDesignDetails> tableDesignDetails = new ArrayList<TableDesignDetails>();
		for (TableDesign table : listTableExport) {
			if(FunctionCommon.isNotEmpty(table.getTableDesignForeignKeys())){
				listTableDesignForeignKey.addAll(table.getTableDesignForeignKeys());
			}
			for (TableDesignDetails tableDesignDetails2 : table.getListTableDesignDetails()) {
				tableDesignDetails2.setTableDesignId(table.getTableDesignId());
			}
			tableDesignDetails.addAll(table.getListTableDesignDetails());
			table.setDetails(table.getListTableDesignDetails());
		}
		
		int numOfTableDetails = FunctionCommon.isEmpty(tableDesignDetails) ? 0 : tableDesignDetails.size();
		int numOfFK = FunctionCommon.isEmpty(listTableDesignForeignKey) ? 0 : listTableDesignForeignKey.size();
		int numOfTable = FunctionCommon.isEmpty(listTableExport) ? 0 : listTableExport.size();
		
		for (int i = 0; i < numOfTable && numOfTableDetails > 0; i++) {
			TableDesign tableDesign = listTableExport.get(i);
			// process row
			List<TableDesignDetails> listOfRow = new ArrayList<TableDesignDetails>();
			for (int k = 0; k < numOfTableDetails; k++) {
				TableDesignDetails designDetails = tableDesignDetails.get(k);
				// if table detail is child of table
				if (designDetails.getTableDesignId().equals(tableDesign.getTableDesignId())) {
					// process fk
					List<TableDesignForeignKey> listFK = new ArrayList<TableDesignForeignKey>();
					for (int j = 0; j < numOfFK; j++) {
						TableDesignForeignKey tableDesignForeignKey = listTableDesignForeignKey.get(j);
						if (tableDesignForeignKey.getFromColumnCode().equals(designDetails.getCode())) {
							listFK.add(tableDesignForeignKey);
						}
					}
					designDetails.setForeignKeys(listFK);
					designDetails.setDataTypeName(this.convertDatypeToDataName(listOfBasetype, designDetails.getDataType()));
					for (DomainDesign domainDesign : domainDesigns) {
						if(domainDesign.getDomainId().equals(designDetails.getDataType())){
							designDetails.setDomainName(domainDesign.getDomainName());
							designDetails.setDataType(new Long(domainDesign.getBaseType()));
							designDetails.setMaxlength(domainDesign.getMaxLength());
							designDetails.setDataTypeName(this.convertDatypeToDataName(listOfBasetype, new Long(domainDesign.getBaseType())));
						}
					}
					listOfRow.add(designDetails);
					numOfTableDetails--;
					tableDesignDetails.remove(k);// remove
					k--;
				}
			}
			Collections.sort(listOfRow);// sort by item_seq_no
			tableDesign.setDetails(listOfRow);
		}
		
		item.setDataLst(listTableExport);
		item.setDocumentItemType(RDDocumentTypeByProject.TABLE_DESIGN);
		item.setDocumentItemParenItemType(GenerateDocumentConst.GENERATE_PROJECT_RD);
		item.setListDomainDesigns(domainDesigns);
		item.setListBasetype(listOfBasetype);
		// Set code list map 
		Map<String, SimpleMapCodeList> codeListMap = new HashMap<String, SimpleMapCodeList>();
		codeListMap.put("tableType", tableType);
		codeListMap.put("operatorCode", operatorCode);
		item.setMapCodeList(codeListMap);
		item.setExcelFolder(new StringBuilder(importManagement.getFilePath()));
		item.setDocumentItemTemplateName("");
		
		GenerateDocumentUtilsQP.currentAccounProfile = importManagement.getAccountProfile();
		GenerateDocumentUtilsQP.processGenerateRDDocumentTableDesignByProjectNew(item, true);
		
		// set return map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put(ImportManagementConst.ERR_WHEN_IMPORT, totalRowInsert);
		returnMap.put(ImportManagementConst.ROW_IMPPORTED, rowInserted);
		return returnMap;
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
	 * 
	 * @param listTableInsertNew
	 * @param projectId
	 * @param accountId
	 */
	private void processAddNewTables(TableDesign tableDesign, Project project, Long accountId, CommonModel commonModel){
		int x = 10;
		int y = 10;
		Long id = 1L;
		if (id % 5 == 0) {
			y = y+ 200;
			x = 10;
		}
		tableDesign.setX(x);
		tableDesign.setY(y);
		tableDesign.setCommonColumn(0);
		x = x + 200;
		this.createTable(tableDesign, accountId, project, commonModel);
	}
	
	public void createTable(TableDesign tableDesign, Long accountId, Project project, CommonModel commonModel) {
		List<Basetype> listOfBasetype = domainRepository.getAllBasetype(project.getProjectId());
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		Long projectId = project.getProjectId();
		
		// Check change status parent
		//projectService.validateProject(projectId);
		//Fix current project 20160613
//		projectService.initData(projectId, accountId);
		commonModel.setProjectId(projectId);
		projectService.validateProject(commonModel);
		
		Long totalTableDuplicate = tableDesignRepository.countNameCodeByProjectId(tableDesign);
		ResultMessages resultMessages = ResultMessages.error();
		
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalTableDuplicate)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0019));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalTableDuplicate)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalTableDuplicate)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0019));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036,MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {

			// Insert to TABLE_DESIGN
			tableDesign.setX(0);
			tableDesign.setY(0);
			tableDesign.setProjectId(projectId);
			tableDesign.setCreatedBy(accountId);
			tableDesign.setUpdatedBy(accountId);
			tableDesign.setCreatedDate(currentTime);
			tableDesign.setUpdatedDate(currentTime);
			tableDesignRepository.createTableUseId(tableDesign);
	
			// Get TABLE_DESIGN_ID
			Long tableDesignId = tableDesign.getTableDesignId();
	
			// Insert to SUBJECT_AREA_DESIGN_TABLE
			List<SubjectAreaTableDesign> subAreaTableDesigns = new ArrayList<SubjectAreaTableDesign>();
	
			SubjectAreaTableDesign areaTableDesign = null;
			
			if (FunctionCommon.isNotEmpty(tableDesign.getSubjectAreas())) {
	
				for (SubjectArea subjectArea : tableDesign.getSubjectAreas()) {
	
					areaTableDesign = new SubjectAreaTableDesign();
	
					areaTableDesign.setSubAreaId(subjectArea.getAreaId());
					areaTableDesign.setTableId(tableDesignId);
	
					subAreaTableDesigns.add(areaTableDesign);
				}
				subjectAreaTableRepository.insertArray(subAreaTableDesigns);
			}
			
			// Insert to TABLE_DESIGN_DETAILS
			List<TableDesignDetails> tableDesignDetails = tableDesign.getListTableDesignDetails();
			this.setBaseTypeForDomainType(tableDesignDetails, listOfBasetype);
			List<UserDefineCodelistDetails> listUserDefineCodelistDetails = new ArrayList<UserDefineCodelistDetails>();
			List<ValidationRule> validationRules = domainDatatypeRepository.findAllValidationRule();
	
			this.processSeqCode(tableDesign.getTableCode(), tableDesignDetails, project);
			this.settingRowBeforeInsert(tableDesignId, tableDesignDetails, listUserDefineCodelistDetails, validationRules, true, projectId);
	
			//tableDesign.setTableDesignId(tableDesign.getTableDesignId());
	
			//this.insertKey(tableDesign, projectId);
		}
	}
	
	public void insertKey(TableDesign tableDesign, Long projectId) {
		
		List<TableDesignDetails> table = tableDesignDetailRepository.findAllByTableDesign(tableDesign.getTableDesignId());
		
		// get key from db
		List<TableDesignKey> listTableDesignKeyFromDB = tableDesignKeyRepository.getAllByProjectAndSubArea(projectId, DomainDatatypeConst.SEARCH_ALL_TABLE_DESIGN);
		
		// key insert
		List<TableDesignKey> listTableDesignKeyInsert = tableDesign.getListTableDesignKey();
		
		ResultMessages resultMessages = ResultMessages.error();
		
		// Insert to TABLE_DESIGN_KEY
		TableDesignKey tableDesignKey = null;
		
		if(FunctionCommon.isNotEmpty(listTableDesignKeyInsert)){
			
			//prepare for all key
//			for (TableDesignKey key : listTableDesignKeyFromDB) {
//				//check exists key code
//				if (isDuplicateKeyName(key.getCode(), key.getKeyId(), listTableDesignKeyInsert)) {
//					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0036, key.getCode()));
//				} 
//			}
			
			List<TableDesignKey> tableDesignKeys = new ArrayList<TableDesignKey>();
			
			for (TableDesignKey key : listTableDesignKeyInsert) {
				
				String keyItems[] = key.getStrKeyItems().split(REPLACEMENT_CHARACTER_SYSBOL);
				if(keyItems.length > 2){
					tableDesignKey = new TableDesignKey();
					tableDesignKey.setCode(keyItems[1]);
					tableDesignKey.setType(Integer.parseInt(keyItems[0]));
					tableDesignKey.setTableDesignId(tableDesign.getTableDesignId());
					tableDesignKeys.add(tableDesignKey);
				}
			}
	
			if(FunctionCommon.isNotEmpty(tableDesignKeys)){
				if(FunctionCommon.isNotEmpty(tableDesignKeys)){
					tableDesignKeyRepository.createArray(tableDesignKeys);
				}
				Long getMinValueTblDesignKeyByTblId = tableDesignKeyRepository.selectMinTblDesignKeyValue(tableDesign.getTableDesignId());
				List<TableDesignKeyItem> tableDesignKeyItems = new ArrayList<TableDesignKeyItem>();
				
		
				// Insert to TABLE_DESIGN_KEY_ITEMS
				TableDesignKeyItem tableDesignKeyItem = null;
				
				for (TableDesignKey key : listTableDesignKeyInsert) {
					String keyItems[] = key.getStrKeyItems().split(REPLACEMENT_CHARACTER_SYSBOL);
					for (int i = 2; i < keyItems.length; i++) {
						for (TableDesignDetails td : table) {
							if(td.getName().equals(keyItems[i].split(INVERSE_BULLET_SYSBOL)[1])){
								tableDesignKeyItem = new TableDesignKeyItem();
								tableDesignKeyItem.setColumnId(td.getColumnId());
								tableDesignKeyItem.setTableDesignKeyId(getMinValueTblDesignKeyByTblId);
								tableDesignKeyItems.add(tableDesignKeyItem);
							}
						}
					}
					getMinValueTblDesignKeyByTblId++;
				}
				if(FunctionCommon.isNotEmpty(tableDesignKeyItems)){
					tableDesignKeyRepository.insertArray(tableDesignKeyItems);
				}
			}
		}
		
		
		// Insert to TABLE_DESIGN_FOREIGN_KEY
		if(FunctionCommon.isNotEmpty(tableDesign.getTableDesignForeignKeys())){
			List<TableDesignForeignKey> tableDesignForeignKeys = new ArrayList<TableDesignForeignKey>();
			TableDesignForeignKey designForeignKey = null;
			int countForeignKey = 1;
			List<Long> toColumnId = new ArrayList<Long>();
			
			// Get collection column id
			for (TableDesignForeignKey foreignKey : tableDesign.getTableDesignForeignKeys()) {
				toColumnId.add(foreignKey.getToColumnId());
			}
			//List<TableDesignDetails> getAllInformationByTableDesign = tableDesignDetailRepository.findDetailsByToColumnForeignKey(toColumnId);
			
			for (TableDesignForeignKey foreignKey : tableDesign.getTableDesignForeignKeys()) {
				designForeignKey = new TableDesignForeignKey();	
				designForeignKey.setFromTableId(tableDesign.getTableDesignId());
				designForeignKey.setToTableId(foreignKey.getToTableId());
				designForeignKey.setToColumnId(foreignKey.getToColumnId());
				designForeignKey.setForeignKeyCode(foreignKey.getForeignKeyCode());
				
				for (TableDesignDetails td : table) {
					if(td.getName().equals(foreignKey.getFromColumnName())){
						designForeignKey.setFromColumnId(td.getColumnId());
					}
				}
				
				tableDesignForeignKeys.add(designForeignKey);
//				if(tableDesignForeignKeyRepository.findDuplicateForeignKey(designForeignKey) != null){
//					resultMessages.add(CommonMessageConst.ERR_SYS_0041,new Object[] { MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0027), countForeignKey }, null);
//				}
//				
//				if(!this.isDataTypeEqual(getAllInformationByTableDesign, foreignKey)){
//					resultMessages.add(TableDesignMessageConst.ERR_TABLEDESIGN_0002, countForeignKey, null);
//				}
				countForeignKey++;
			}
			
			if (resultMessages.isNotEmpty()) {
				throw new BusinessException(resultMessages);
			}else{
				if(FunctionCommon.isNotEmpty(tableDesignForeignKeys)){
					tableDesignForeignKeyRepository.createArray(tableDesignForeignKeys);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param tableDesignDetails
	 */
	private void processSeqCode(String tableCode, List<TableDesignDetails> tableDesignDetails, Project project){
		
		// Set seq when column has auto increment is true
		for (TableDesignDetails tableDesignDetail : tableDesignDetails) {
			tableDesignDetail.setSeqCode(org.apache.commons.lang3.StringUtils.EMPTY);
			
			if(tableDesignDetail.getAutoIncrementFlag() == 1){
				tableDesignDetail.setSeqCode(this.genSeqColumn(tableCode, tableDesignDetail, project));
			}else{
				String keyType = tableDesignDetail.getKeyType();
				if(keyType.charAt(4) == '1'){
					if(DbDomainConst.BaseType.BIGSERIAL_BASETYPE == tableDesignDetail.getDataType().intValue() ||
							DbDomainConst.BaseType.SERIAL_BASETYPE == tableDesignDetail.getDataType().intValue()){
						tableDesignDetail.setSeqCode(this.genSeqColumn(tableCode, tableDesignDetail, project));
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param tableCode
	 * @param tableDetail
	 * @param project
	 * @return
	 */
	private String genSeqColumn(String tableCode, TableDesignDetails tableDetail, Project project){
		AccountProfile accountProfile = systemService.getDefaultProfile();
		String dataType = String.valueOf(project.getDbType());
		setMaxLengOfCode(accountProfile.getSqlCodeMaxLengthByDbType(dataType));
		GenerateUniqueKey generateUniqueKey = new GenerateUniqueKey(DbDomainConst.MAX_LENGTH_OF_NAME, maxLengOfCode);
		return generateUniqueKey.calculateCodeForManual(MessageFormat.format(TableDesignUtil.SEQ_TEMPLATE, tableCode, tableDetail.getCode(), GenerateUniqueKey.generateRandomInteger(), true));
	}
	
	private void settingRowBeforeInsert(Long tableDesignId,List<TableDesignDetails> tableDesignDetails,List<UserDefineCodelistDetails> listUserDefineCodelistDetails,List<ValidationRule> validationRules, boolean status, Long projectId) {
		for (TableDesignDetails tableDetail : tableDesignDetails) {
			if(tableDetail.getCommonColumn() == 1){
				tableDetail.setIsMandatory(1);
			}
			tableDetail.setTableDesignId(tableDesignId);
			tableDetail.setDisplayType(DbDomainConst.DisplayType.USED);
			// Set java type
			tableDetail.setJavaType(BusinessDesignHelper.convertJavaTypeFromBaseType(tableDetail.getBaseType()));
			
			/*if(tableDetail.getKeyType().substring(tableDetail.getKeyType().length() - 1).equals("1")){
				tableDetail.setIsMandatory(1);
			}*/

			if(YesNoFlg.YES.equals(tableDetail.isPrimaryKey()) && tableDetail.getAutoIncrementFlag() == 1){
				tableDetail.setIsMandatory(YesNoFlg.YES);

				//DungNN - 20151026 - if has auto increment then don't set display type
				tableDetail.setDisplayType(DbDomainConst.DisplayType.UNUSED);
			}
			
			// Set display type where column isMadatory
			if(YesNoFlg.YES.equals(tableDetail.getIsMandatory())){
				if(tableDetail.getAutoIncrementFlag() == 0 || StringUtils.isNotBlank(tableDetail.getDefaultValue())){
					tableDetail.setDisplayType(DbDomainConst.DisplayType.USED);
				}
			}

			//DungNN 20150927 - recalculation for data type and group base type
			for (Basetype basetype: listOfBasetype) {
				if (basetype.getBasetyeId().equals(tableDetail.getDataType())) {
					tableDetail.setDataTypeFlg(basetype.getDataTypeFlg());
					tableDetail.setGroupBaseTypeId(basetype.getGroupBaseTypeId());
					break;
				}
			}
			
			TableDesignUtil.initDefaultForItemType(tableDetail, validationRules);
			
			if(DbDomainConst.DataTypeFlag.DOMAIN_DATA.equals(tableDetail.getDataTypeFlg())){
				this.clearValueDomainType(tableDetail);
			}
			insertUserDefineCodelist(listUserDefineCodelistDetails, tableDetail);
		}
		
		tableDesignDetailRepository.registerMultiTableDesignDetailsWithSequence(tableDesignDetails);
		
		if(listUserDefineCodelistDetails.size() > 0){
			userDefineCodelistRepository.createUserDefineCodelistDetails(listUserDefineCodelistDetails);
		}
	}
	
	private void insertUserDefineCodelist(List<UserDefineCodelistDetails> listUserDefineCodelistDetails, TableDesignDetails td) {
		UserDefineCodelist userDefineCodelist;
		UserDefineCodelistDetails userDefineCodelistDetails;
		Integer supportOptionFlg = td.getSupportOptionFlg();
		
		if(supportOptionFlg !=null){
			userDefineCodelist = new UserDefineCodelist();
			userDefineCodelist.setSupportOptionFlg(supportOptionFlg);
			userDefineCodelistRepository.createUserDefineCodelist(userDefineCodelist);
			
			Long codelistId = userDefineCodelist.getCodelistId();
			// Set UserDefineId for Column of Table 
			td.setDatasourceId(codelistId);
			
			String [] userDefineValue = td.getUserDefineValue().split(INVERSE_BULLET_SYSBOL);
			Integer itemSeqNo = 0;
			for (int i = 0; i < userDefineValue.length; i++) {
				String [] userDefineValueRow = userDefineValue[i].split(REPLACEMENT_CHARACTER_SYSBOL);
				userDefineCodelistDetails = new UserDefineCodelistDetails();
				userDefineCodelistDetails.setCodelistId(codelistId);
				userDefineCodelistDetails.setItemSeqNo(itemSeqNo++);
				if(supportOptionFlg.equals(0)){
					userDefineCodelistDetails.setCodelistName(null);
				} else {
					userDefineCodelistDetails.setCodelistName(userDefineValueRow[2]);
				}
				userDefineCodelistDetails.setCodelistValue(userDefineValueRow[1]);
				userDefineCodelistDetails.setDefaultFlg(Integer.parseInt(userDefineValueRow[0]));
				listUserDefineCodelistDetails.add(userDefineCodelistDetails);

				if(userDefineValueRow[0] == "1"){
					td.setDefaultValue(userDefineValueRow[1]);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param tableDesignDetails
	 */
	private void clearValueDomainType(TableDesignDetails tableDesignDetails){
		tableDesignDetails.setConstrainsType(null);
		tableDesignDetails.setDatasourceId(null);
		tableDesignDetails.setDatasourceType(null);
		tableDesignDetails.setFmtCode(null);
		tableDesignDetails.setDefaultValue(null);
		tableDesignDetails.setOperatorCode(null);
		tableDesignDetails.setMaxVal(null);
		tableDesignDetails.setMaxVal(null);
		tableDesignDetails.setMaxlength(null);
		tableDesignDetails.setDecimalPart(null);
	}
	
	private void setBaseTypeForDomainType(List<TableDesignDetails> tableDesignDetails, List<Basetype> listOfBasetype){
		if(FunctionCommon.isNotEmpty(tableDesignDetails)){
			for (TableDesignDetails details : tableDesignDetails) {
				for (Basetype basetype : listOfBasetype) {
					if(details.getDataType().equals(basetype.getBasetyeId())){
						details.setBaseType((int) (long)basetype.getPrimitiveId());
						break;
					}
				}
			}
		}
	}
	/**
	 * 
	 * @param listTableDuplicateFromImport
	 * @param projectId
	 * @return
	 */
	private List<TableDesign> processTableDuplicate(List<TableDesign> listTableDuplicateFromImport, List<TableDesign> listTableDuplicateFromDB, Project project, Long accountId, boolean isDelete){
		List<TableDesign> listTableUsed = new ArrayList<TableDesign>();
		List<TableDesignDetails> listAllTableDesignDetails = new ArrayList<TableDesignDetails>();
		for (TableDesign tableDesign : listTableDuplicateFromDB) {
			listAllTableDesignDetails.addAll(tableDesign.getListTableDesignDetails());
		}
		for (TableDesign tableDesign : listTableDuplicateFromDB) {
			List<TableDesignDetails> listTableDesignDetailsFromDB = tableDesign.getListTableDesignDetails();
			for (TableDesign tableDesignImport : listTableDuplicateFromImport) {
				if(tableDesignImport.getTableCode().equals(tableDesign.getTableCode())){
					tableDesignImport.setTableDesignId(tableDesign.getTableDesignId());
					tableDesignImport.setUpdatedDate(tableDesign.getUpdatedDate());
					
					List<TableDesignDetails> listTableDesignDetailsImport = tableDesignImport.getListTableDesignDetails();
					
					for (TableDesignDetails tableDesignDetailsDB : listTableDesignDetailsFromDB) {
						for (TableDesignDetails tableDesignDetailsImport : listTableDesignDetailsImport) {
							if(tableDesignDetailsDB.getCode().equalsIgnoreCase(tableDesignDetailsImport.getCode())){
								tableDesignDetailsImport.setColumnId(tableDesignDetailsDB.getColumnId());
							}
						}
					}
					
					// Set Id for FK
					List<TableDesignForeignKey> tableDesignForeignKeys = tableDesignImport.getTableDesignForeignKeys();
					
					if(FunctionCommon.isNotEmpty(tableDesignForeignKeys)){
						for (TableDesignForeignKey tableDesignForeignKey : tableDesignForeignKeys) {
							// Set Id for Table
							for (TableDesign table : listTableDuplicateFromDB) {
								if(tableDesignForeignKey.getFromTableCode().equalsIgnoreCase(table.getTableCode())){
									tableDesignForeignKey.setFromTableId(table.getTableDesignId());
								}
								if(tableDesignForeignKey.getToTableCode().equalsIgnoreCase(table.getTableCode())){
									tableDesignForeignKey.setToTableId(table.getTableDesignId());
								}
							}
							
							// Set Id for Column
							for (TableDesignDetails tableDesignDetails : listAllTableDesignDetails) {
								if(tableDesignForeignKey.getFromColumnCode().equalsIgnoreCase(tableDesignDetails.getCode())){
									tableDesignForeignKey.setFromColumnId(tableDesignDetails.getColumnId());
								}
								if(tableDesignForeignKey.getToColumnCode().equalsIgnoreCase(tableDesignDetails.getCode())){
									tableDesignForeignKey.setToColumnId(tableDesignDetails.getColumnId());
								}
							}
						}
					}
					
					listTableUsed.add(tableDesignImport);
				}
			}
		}
		
		return listTableUsed;
	}
	
	/**
	 * 
	 * @param tableDesigns
	 */
	private boolean validateData(List<TableDesign> tableDesigns, boolean isAddNew, String dbType){
		AccountProfile accountProfile = systemService.getDefaultProfile();
		String dataType = dbType;
		String[] reservedWords = systemService.databaseReservedWords(dbType);
		Integer maxLengOfCode = accountProfile.getSqlCodeMaxLengthByDbType(dataType);
		boolean hasErr = false;
		
		for (TableDesign tableDesign : tableDesigns) {
			StringBuilder errContentTable = new StringBuilder();
			
			// Validate Table Name
			if(FunctionCommon.equals(tableDesign.getTableName(), null)){
				errContentTable.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0025, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0019)));
				hasErr = true;
			}else{
				if(tableDesign.getTableName().length() > accountProfile.getNameMaxLength()){
					errContentTable.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0064, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0019), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength()));
					hasErr = true;
				}
				if(!Pattern.matches(accountProfile.getNamePattern(), tableDesign.getTableName())){
					errContentTable.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0126, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0019), accountProfile.getNameMask()));
					hasErr = true;
				}
			}
			
			if(errContentTable.length() != 0){
				errContentTable.append(REPLACEMENT_CHARACTER_SYSBOL);
			}
			
			// Validate Table Code
			if(FunctionCommon.equals(tableDesign.getTableCode(), null)){
				errContentTable.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0025, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020)));
				hasErr = true;
			} else {
				if(tableDesign.getTableCode().length() > maxLengOfCode){
					errContentTable.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0064, MessageUtils.getMessage(MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020), accountProfile.getCodeMinLength(), maxLengOfCode)));
					hasErr = true;
				}
				if(!Pattern.matches(accountProfile.getCodePattern(), tableDesign.getTableCode())){
					errContentTable.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0066, MessageUtils.getMessage(MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020, accountProfile.getCodeMask()))));
					hasErr = true;
				}
				if (FunctionCommon.checkExists(reservedWords, tableDesign.getTableCode())) {
					errContentTable.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0130, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0020)));
					hasErr = true;
				}
			}
			
			if(errContentTable.length() != 0){
				tableDesign.setStatusImport("Error");
				tableDesign.setContentStatusImport(errContentTable.toString());
			}else{
				if(isAddNew){
					tableDesign.setStatusImport("Add New");
				}else{
					tableDesign.setStatusImport("Modify");
				}
			}
			
			List<TableDesignDetails> tableDesignDetails = tableDesign.getListTableDesignDetails();
			
			// Check codelist detail is duplicated or not
			Set<String> valueSet = new HashSet<String>();
			Set<String> nameSet = new HashSet<String>();
			
			
			if(FunctionCommon.isNotEmpty(tableDesignDetails)) {
				for (int i = 1; i <= tableDesignDetails.size(); i++) {
					StringBuilder errContentTableDetails = new StringBuilder();
					String columnName = tableDesignDetails.get(i-1).getName();
					if(FunctionCommon.equals(columnName, null)){
						errContentTableDetails.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0120, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0021), i, MessageUtils.getMessage("sc.tabledesign.0014")));
						hasErr = true;
					}else{
						if(columnName.length() > accountProfile.getNameMaxLength()){
							errContentTableDetails.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0095, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0021), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength() , i));
							hasErr = true;
						}
						if(!Pattern.matches(accountProfile.getNamePattern(), columnName)){
							errContentTableDetails.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0128, MessageUtils.getMessage("sc.tabledesign.0014"),MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0021), i, accountProfile.getNameMask()));
							hasErr = true;
						}
					}
					
					if(errContentTableDetails.length() != 0){
						errContentTableDetails.append(REPLACEMENT_CHARACTER_SYSBOL);
					}
					
					String columnCode = tableDesignDetails.get(i-1).getCode();
					if(FunctionCommon.equals(columnCode, null)){
						errContentTableDetails.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0120, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0022), i , MessageUtils.getMessage("sc.tabledesign.0014")));
						hasErr = true;
					}else{
						if(columnCode.length() > maxLengOfCode){
							errContentTableDetails.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0095, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0022), accountProfile.getCodeMinLength(), maxLengOfCode , i));
							hasErr = true;
						}
						if(!Pattern.matches(accountProfile.getCodePattern(), columnCode)){
							errContentTableDetails.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0107, MessageUtils.getMessage("sc.tabledesign.0014"), MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0022), i));
							hasErr = true;
						}
						if (FunctionCommon.checkExists(reservedWords, columnCode)) {
							errContentTableDetails.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0131, MessageUtils.getMessage("sc.tabledesign.0014"), MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0022), i));
							hasErr = true;
						}
					}
					
					if(errContentTableDetails.length() != 0){
						errContentTableDetails.append(REPLACEMENT_CHARACTER_SYSBOL);
					}
					
					// Check data type
					if(tableDesignDetails.get(i-1).getDataType() == null || tableDesignDetails.get(i-1).getGroupBaseTypeId() == null){
						errContentTableDetails.append(MessageUtils.getMessage(ImportManagementConst.SC_IMPORTMANAGEMENT_0019));
						hasErr = true;
					} 
					
					if (nameSet.contains(columnName)) {
						errContentTableDetails.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0041, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0021), String.valueOf(i)));
						hasErr = true;
					} else {
						if(StringUtils.isNotBlank(columnName)) {
							nameSet.add(columnName);
						}
					}
					
					if(errContentTableDetails.length() != 0){
						errContentTableDetails.append(REPLACEMENT_CHARACTER_SYSBOL);
					}
					
					if (valueSet.contains(columnCode)) {
						errContentTableDetails.append(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0041, MessageUtils.getMessage(TableDesignMessageConst.SC_TABLEDESIGN_0022), String.valueOf(i)));
						hasErr = true;
					} else {
						if(StringUtils.isNotBlank(columnCode)) {
							valueSet.add(columnCode);
						}
					}
					
					if(errContentTableDetails.length() != 0){
						tableDesignDetails.get(i-1).setStatusImport("Error");
						tableDesignDetails.get(i-1).setContentStatusImport(errContentTableDetails.toString());
					}else{
						if(isAddNew){
							tableDesignDetails.get(i-1).setStatusImport("Add New");
						}else{
							tableDesignDetails.get(i-1).setStatusImport("Modify");
						}
					}
				}
			}
		}
		return hasErr;
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	private Map<String, List<String[]>> getInputData(String filePath) {
		Map<String, List<String[]>> inputData = new HashMap<String, List<String[]>>();
		int sheetCounter = 2;
		try {
			// Open file, call library to read file, get shared string table
			// contains all string of excel file, and get XML parser
			OPCPackage pkg = OPCPackage.open(filePath);
			XSSFReader r = new XSSFReader(pkg);
			
			// Read excel file from input stream
			Iterator<InputStream> sheets = r.getSheetsData();
			
			// Skip data source sheet
			sheets.next();
			while(sheets.hasNext()) {
				SharedStringsTable sst = r.getSharedStringsTable();
				XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
				TableDesignSheetHandler handler = new TableDesignSheetHandler(sst);
				parser.setContentHandler(handler);
				
				List<String[]> inputTableInfo = new ArrayList<String[]>();
				List<String[]> inputTableDetail = new ArrayList<String[]>();
				InputStream sheet = sheets.next();
				InputSource inputSource = new InputSource(sheet);
				parser.parse(inputSource);
				sheet.close();
				inputTableInfo = handler.getInputTableInfo();
				
				if(inputTableInfo.size() == 0) {
					throw new BusinessException(ResultMessages.error().add(ImportManagementMessageConst.ERR_IMPORTMANAGEMENT_0005));	
				}
				
				inputTableDetail = handler.getInputTableDetail();
				
				if(inputTableInfo.size() == 0) {
					throw new BusinessException(ResultMessages.error().add(ImportManagementMessageConst.ERR_IMPORTMANAGEMENT_0005));	
				}
				
				inputData.put("InfoSheet" + sheetCounter, inputTableInfo);
				inputData.put("DetailSheet" + sheetCounter, inputTableDetail);
				sheetCounter++;
			}

		} catch (IOException | OpenXML4JException | SAXException e) {
			e.printStackTrace();
			throw new BusinessException(ResultMessages.error().add(ImportManagementMessageConst.ERR_IMPORTMANAGEMENT_0004, MessageUtils.getMessage(ImportManagementMessageConst.SC_IMPORTMANAGEMENT_0003)));
		}
		
		return inputData;
	}
	
	private List<TableField> setTableFieldRuleForTableDesign() {
		List<TableField> columnInfo = new ArrayList<TableField>();
		TableField column = new TableField();
		//set entity id
		column = new TableField();
		column.setColumnName("entity_id");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.ENTITY_ID);
		column.setIndex(0);
		columnInfo.add(0,column);

		//set subject area id
		column = new TableField();
		column.setColumnName("subject_area_id");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.SUBJECT_AREA_ID);
		column.setIndex(1);
		columnInfo.add(1,column);
		
		//set subject area name
		column = new TableField();
		column.setColumnName("subject_area_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.SUBJECT_AREA_NAME);
		column.setIndex(2);
		columnInfo.add(2,column);
		
		//set entity type (lack of data source)
		column = new TableField();
		column.setColumnName("entity_type");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.ENTITY_TYPE);
		column.setIndex(3);
		columnInfo.add(3,column);
		
		//set entity name
		column = new TableField();
		column.setColumnName("entity_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.ENTITY_NAME);
		column.setIndex(4);
		columnInfo.add(4,column);

		//set table name
		column = new TableField();
		column.setColumnName("table_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setPatternCode(ImportManagementConst.PATTERN.YES);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.TABLE_NAME);
		column.setIndex(5);
		columnInfo.add(5,column);
		
		//set description
		column = new TableField();
		column.setColumnName("description");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.DESCRIPTION);
		column.setIndex(6);
		columnInfo.add(6,column);
		
		return columnInfo;
	}
	
	private List<TableField> setTableFieldRuleForTableDesignDetail() {
		List<TableField> columnInfo = new ArrayList<TableField>();
		TableField column = new TableField();
		
		//set item no
		column.setColumnName("item_no");
		column.setDataType(ImportManagementConst.DATA_TYPE.INTEGER);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.ITEM_NO);
		column.setIndex(0);
		columnInfo.add(0,column);

		//set attribute name
		column = new TableField();
		column.setColumnName("attribute_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.ATTRIBUTE_NAME);
		column.setIndex(1);
		columnInfo.add(1,column);
		
		//set column name
		column = new TableField();
		column.setColumnName("column_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setPatternCode(ImportManagementConst.PATTERN.YES);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.COLUMN_NAME);
		column.setIndex(2);
		columnInfo.add(2,column);
		
		//set primary key
		column = new TableField();
		column.setColumnName("primary_key");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.PRIMARY_KEY);
		column.setIndex(3);
		columnInfo.add(3,column);
		
		//set alternate key
		column = new TableField();
		column.setColumnName("alternate_key");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.ALTERNATE_KEY);
		column.setIndex(4);
		columnInfo.add(4,column);
		
		//set foreign key
		column = new TableField();
		column.setColumnName("foreign_key");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.FOREIGN_KEY);
		column.setIndex(5);
		columnInfo.add(5,column);
		
		//set parent entity name
		column = new TableField();
		column.setColumnName("parent_entity_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.PARENT_ENTITY_NAME);
		column.setIndex(6);
		columnInfo.add(6,column);
		
		//set parent table name
		column = new TableField();
		column.setColumnName("parent_table_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setPatternCode(ImportManagementConst.PATTERN.YES);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.PARENT_TABLE_NAME);
		column.setIndex(7);
		columnInfo.add(7,column);
		
		//set parent attribute name
		column = new TableField();
		column.setColumnName("parent_attribute_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.PARENT_ATTRIBUTE_NAME);
		column.setIndex(8);
		columnInfo.add(8,column);
		
		//set parent entity name
		column = new TableField();
		column.setColumnName("parent_column_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setPatternCode(ImportManagementConst.PATTERN.YES);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.PARENT_COLUMN_NAME);
		column.setIndex(9);
		columnInfo.add(9,column);
		
		//set logical data type
		column = new TableField();
		column.setColumnName("logical_data_type");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.DATA_TYPE);
		column.setIndex(10);
		columnInfo.add(10,column);
		
		//set logical data length
		column = new TableField();
		column.setColumnName("parent_attribute_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.DATA_LENGTH);
		column.setIndex(11);
		columnInfo.add(11,column);
		
		//set physical data type
		column = new TableField();
		column.setColumnName("physical_data_type");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setIsMandatory(ImportManagementConst.ISMANDATORY.YES);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.DATA_TYPE);
		column.setIndex(12);
		columnInfo.add(12,column);
		
		//set physical data length
		column = new TableField();
		column.setColumnName("physical_attribute_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.DATA_LENGTH);
		column.setIndex(13);
		columnInfo.add(13,column);
		
		//set domain name
		column = new TableField();
		column.setColumnName("domain_name");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.DOMAIN_NAME);
		column.setIndex(14);
		columnInfo.add(14,column);
		
		//set default value
		column = new TableField();
		column.setColumnName("default_value");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.DEFAULT_VALUE);
		column.setIndex(15);
		columnInfo.add(15,column);

		//set unique constraint
		column = new TableField();
		column.setColumnName("unique_constraint");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.UNIQUE_CONSTRAINT);
		column.setIndex(16);
		columnInfo.add(16,column);
		
		//set null value
		column = new TableField();
		column.setColumnName("null_value");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.NULL_VALUE_CONSTRAINT);
		column.setIndex(17);
		columnInfo.add(17,column);
		
		//set check constraint
		column = new TableField();
		column.setColumnName("check_constraint");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.CHECK_CONSTRAINT);
		column.setIndex(18);
		columnInfo.add(18,column);
		
		//set check constraint value
		column = new TableField();
		column.setColumnName("check_constraint_value");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.CHECK_CONSTRAINT);
		column.setIndex(19);
		columnInfo.add(19,column);
		
		//set description
		column = new TableField();
		column.setColumnName("description");
		column.setDataType(ImportManagementConst.DATA_TYPE.STRING);
		column.setColumnMessage(GenerateDocumentConst.TableDesign.DESCRIPTION);
		column.setIndex(20);
		columnInfo.add(20,column);
		
		return columnInfo;
	}
	
	/**
	 * Convert list of table design information strings to model
	 * @param tempList
	 * @return
	 */
	private TableDesign convertArrayTableDesign(List<String[]> tempList, Long projectId, Long accountId){
		TableDesign tableDesign = new TableDesign();
		List<SubjectArea> subjectAreas = subjectAreaRepository.getAllByProjectId(projectId);
		if(tempList.size() > 0){
			String[] data =  tempList.get(0);
			// Get Subject area 
			List<SubjectArea> lstSubArea = new ArrayList<SubjectArea>();
			String[] subjectAreaID = data[1].split(";");
			String[] subjectAreaName = data[2].split(";");
			if(!(StringUtils.EMPTY.equals(data[1]) && StringUtils.EMPTY.equals(data[2]))){
				for(int i = 0; i < subjectAreaID.length ; i++){
					if(subjectAreaID[i] != null && subjectAreaName[i] != null){
						SubjectArea subArea = new SubjectArea();
						subArea.setAreaId(Long.parseLong(subjectAreaID[i].trim()));
						subArea.setAreaName(subjectAreaName[i]);
						for (SubjectArea obj : subjectAreas) {
							if(obj.getAreaId().equals(subArea.getAreaId())){
								lstSubArea.add(subArea);
							}
						}
					}
				}
			}
			
			if(FunctionCommon.isNotEmpty(lstSubArea)){
				tableDesign.setSubjectAreas(lstSubArea);
			}
			
			// entity type
			tableDesign.setType(getTableTypeFromString(data[3]));
			
			tableDesign.setDesignStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
			
			tableDesign.setTableType(data[3]);
			
			// entity name
			tableDesign.setTableName(data[4]);
			
			// table name
			tableDesign.setTableCode(data[5]);
			
			// remark
			tableDesign.setRemark(data[6]);
			
			tableDesign.setProjectId(projectId);
			tableDesign.setCreatedBy(accountId);
			tableDesign.setUpdatedBy(accountId);
			tableDesign.setCreatedDate(FunctionCommon.getCurrentTime());
			tableDesign.setUpdatedDate(FunctionCommon.getCurrentTime());
		}
		return tableDesign;
	}
	
	/**
	 * Convert list of table design detail information strings to model
	 * @param tempList
	 * @return
	 */
	private List<TableDesignDetails> convertArrayTableDesignDetail(TableDesign tableDesign, List<String[]> tempList){
		List<TableDesignDetails> tableDesignDetail = new ArrayList<TableDesignDetails>();
		List<TableDesignForeignKey> tableDesignForeignKeys = new ArrayList<TableDesignForeignKey>();
		TableDesignForeignKey designForeignKey = null;
		StringBuilder pk = new StringBuilder();
		StringBuilder uk = new StringBuilder();
		int countRow = 0;
		if(tempList.size() > 0){
			for(String[] data : tempList){
				TableDesignDetails tbDesignDetail = new TableDesignDetails();
				
				// Sequence column 0
				tbDesignDetail.setItemSeqNo(Integer.parseInt(data[0])-1);
				
				// Attribute name 1
				tbDesignDetail.setName(data[1]);
				
				// Column name 2
				tbDesignDetail.setCode(data[2]);
				
				// Set data type based on domain (data 14)
				if(StringUtils.isEmpty(data[14])){
					// base type
					
					this.getDataTypeFromString(tbDesignDetail, data[12]);
				} else{
					// domain type
					// get domain id
					tbDesignDetail.setDataTypeName(data[12]);
					this.getDomainIdFromName(tbDesignDetail, data[14]);
				}
				
				// Alternate key 4
				if(!data[4].isEmpty()){
					tbDesignDetail.setAutoIncrementFlag(1);
				}
				else{
					tbDesignDetail.setAutoIncrementFlag(0);
				}
				// PrimaryKey 3, foreign key 5, unique 16
				StringBuilder keyType = new StringBuilder("00");
				if(!data[16].isEmpty()){
					keyType.append("1");
					if(uk.length() == 0){
						uk.append(DbDomainConst.TblDesignKeyType.UNIQUE);
						uk.append(REPLACEMENT_CHARACTER_SYSBOL);
						uk.append(data[2]).append("_").append("uniqueImp");
						uk.append(REPLACEMENT_CHARACTER_SYSBOL);
					}
					uk.append(countRow);
					uk.append(INVERSE_BULLET_SYSBOL);
					uk.append(data[1]);
					uk.append(REPLACEMENT_CHARACTER_SYSBOL);
				}
				else{
					keyType.append("0");
				}
				
				if(!data[5].isEmpty()){
					keyType.append("1");
					designForeignKey = new TableDesignForeignKey();
					designForeignKey.setForeignKeyType(tbDesignDetail.getGroupBaseTypeId());
					designForeignKey.setFromTableCode(tableDesign.getTableCode());
					designForeignKey.setFromTableName(tableDesign.getTableName());
					designForeignKey.setFromColumnCode(tbDesignDetail.getCode());
					designForeignKey.setFromColumnName(tbDesignDetail.getName());
					designForeignKey.setForeignKeyCode("fk_" + tbDesignDetail.getName() + "_" + data[8] + GenerateUniqueKey.generateRandomInteger());
					
					designForeignKey.setToTableCode(data[7]);
					designForeignKey.setToTableName(data[6]);
					designForeignKey.setToColumnCode(data[9]);
					designForeignKey.setToColumnName(data[8]);
					tableDesignForeignKeys.add(designForeignKey);
				}
				else{
					keyType.append("0");
				}
				
				if(!data[3].isEmpty()){
					keyType.append("1");
					if(pk.length() == 0){
						pk.append(DbDomainConst.TblDesignKeyType.UNIQUE);
						pk.append(REPLACEMENT_CHARACTER_SYSBOL);
						pk.append(data[2]).append("_").append(GenerateUniqueKey.generateRandomInteger());
						pk.append(REPLACEMENT_CHARACTER_SYSBOL);
					}
					pk.append(countRow);
					pk.append(INVERSE_BULLET_SYSBOL);
					pk.append(data[1]);
					pk.append(REPLACEMENT_CHARACTER_SYSBOL);
				}
				else{
					keyType.append("0");
				}
				
				// Set key type
				tbDesignDetail.setKeyType(keyType.toString());
				
				// Set data length
				if(StringUtils.isNotEmpty(data[13])){
					tbDesignDetail.setMaxlength(Integer.parseInt(data[13]));
				}
				
				// Set default value
				tbDesignDetail.setDefaultValue(data[15]);
				
				// Set null constraint
				if(!data[17].isEmpty()){
					tbDesignDetail.setIsMandatory(1);
				}
				else{
					tbDesignDetail.setIsMandatory(0);
				}
				
				// Set check constraint and constraint value
				String checkCode = getConstraintCodeFromName(data[18]);
				if(!checkCode.equals("")){
					tbDesignDetail.setOperatorCode(checkCode);
					tbDesignDetail.setConstrainsType(DbDomainConst.ConstrainsType.RANGE);
					switch(checkCode){
						case "1":
							
						case "2":
							
						case "3":
							tbDesignDetail.setMaxVal(data[19]);
							break;
						case "4":
		
						case "5":
					
						case "6":
							
						case "7":
							tbDesignDetail.setMinVal(data[19]);
							break;
						case "8":// between x~y
							String[] values =  data[19].split("~");
							tbDesignDetail.setMinVal(values[0]);
							tbDesignDetail.setMaxVal(values[1]);
							break;
						default:
							break;
					}
				}
				// Set remark
				tbDesignDetail.setRemark(data[20]);
				
				// Add item to table design detail list
				tableDesignDetail.add(tbDesignDetail);
				countRow++;
			}
			List<TableDesignKey> listTableDesignKey = new ArrayList<TableDesignKey>();
			TableDesignKey designKey = null;
			if(pk.length() > 0){
				designKey = new TableDesignKey();
				designKey.setStrKeyItems(pk.toString());
				listTableDesignKey.add(designKey);
			}
			if(uk.length() > 0){
				designKey = new TableDesignKey();
				designKey.setStrKeyItems(uk.toString());
				listTableDesignKey.add(designKey);
			}
			
			tableDesign.setListTableDesignKey(listTableDesignKey);
			tableDesign.setTableDesignForeignKeys(tableDesignForeignKeys);
		}
		return tableDesignDetail;
	}
	
	/**
	 * Parse table type string to integer
	 * @param type
	 * @return
	 */
	private int getTableTypeFromString(String type){
		for(String key : tableType.asMap().keySet()){
			if(type.equals(MessageUtils.getMessage(tableType.asMap().get(key)))){
				return Integer.parseInt(key);
			}
		}
		// Common table
		return 2;
	}
	
	/**
	 * Get data type from string to long 
	 * @param type
	 * @return
	 */
	private void getDataTypeFromString(TableDesignDetails tableDesignDetails, String type){
		tableDesignDetails.setDataTypeFlg(0);
		for(Basetype baseType : listOfBasetype){
			if(baseType.getBasetypeName().equals(type)){
				tableDesignDetails.setDataTypeName(type);
				tableDesignDetails.setDataType(baseType.getBasetyeId());
				tableDesignDetails.setGroupBaseTypeId(baseType.getGroupBaseTypeId());
			}
		}
	}
	
	/**
	 * Get domain id from name
	 * @param name
	 * @return
	 */
	private void getDomainIdFromName(TableDesignDetails tableDesignDetails, String name){
		tableDesignDetails.setDataTypeFlg(1);
		for(DomainDesign domain : listOfDomainDesign ){
			if(domain.getDomainName().equals(name)){
				tableDesignDetails.setDomainName(name);
				tableDesignDetails.setDataType(domain.getDomainId());
				tableDesignDetails.setGroupBaseTypeId(domain.getGroupBasetypeId());
			}
//			else{
//				tableDesignDetails.setContentStatusImport(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_DOMAINDESIGN)));
//			}
		}
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	private String getConstraintCodeFromName(String name){
		if(!StringUtils.isEmpty(name)){
			for(String key : operatorCode.asMap().keySet()){
				if(name.equals(MessageUtils.getMessage(operatorCode.asMap().get(key)))){
					return key;
				}
			}
		}
		return StringUtils.EMPTY;
	}
	
	/**
	 * 
	 * @param tableDesign
	 * @return
	 */
	private boolean hasErrInTable(TableDesign tableDesign){
		if(tableDesign.getContentStatusImport() != null){
			if(!StringUtils.EMPTY.equalsIgnoreCase(tableDesign.getContentStatusImport())){
				return true;
			}
		}
		
		for (TableDesignDetails tableDesignDetails : tableDesign.getListTableDesignDetails()) {
			if(tableDesignDetails.getContentStatusImport() != null){
				if(!StringUtils.EMPTY.equalsIgnoreCase(tableDesignDetails.getContentStatusImport())){
					return true;
				}
			}
		}
		return false;
	} 
}
