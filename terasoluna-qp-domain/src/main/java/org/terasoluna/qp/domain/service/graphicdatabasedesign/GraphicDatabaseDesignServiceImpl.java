package org.terasoluna.qp.domain.service.graphicdatabasedesign;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.GraphicDatabaseDesignMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.Basetype;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.BusinessLogic;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenAction;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SubjectAreaTableDesign;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.TableDesignForeignKey;
import org.terasoluna.qp.domain.model.TableDesignKey;
import org.terasoluna.qp.domain.model.TableDesignKeyItem;
import org.terasoluna.qp.domain.model.ValidationRule;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.domaindatatype.DomainDatatypeRepository;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectAttributeRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectDefinitionRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.screenaction.ScreenActionRepository;
import org.terasoluna.qp.domain.repository.screenarea.ScreenAreaRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.screenform.ScreenFormRepository;
import org.terasoluna.qp.domain.repository.screenitem.ScreenItemRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignConditionRepository;
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
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignHelper;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.common.XmlUtils;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeUtil;
import org.terasoluna.qp.domain.service.domaindesign.DomainDesignService;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst;
import org.terasoluna.qp.domain.service.messagedesign.MessageDesignService;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignConst;
import org.terasoluna.qp.domain.service.tabledesign.TableDesignService;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

@Service
@Transactional
public class GraphicDatabaseDesignServiceImpl implements GraphicDatabaseDesignService {

	@Inject
	TableDesignRepository tableDesignRepository;

	@Inject
	TableDesignDetailRepository tableDesignDetailRepository;

	@Inject
	SubjectAreaTableRepository areaTableRepository;

	@Inject
	TableDesignKeyRepository tableDesignKeyRepository;

	@Inject
	TableDesignForeignKeyRepository tableDesignForeignKeyRepository;

	@Inject
	DomainDesignService domainDesignService;

	@Inject
	DomainDesignRepository domainRepository;

	@Inject
	DomainDatatypeRepository domainDatatypeRepository;

	@Inject
	SubjectAreaRepository subjectAreaRepository;

	@Inject
	SubjectAreaTableRepository subjectAreaTableRepository;

	@Inject
	SqlDesignRepository sqlDesignRepository;

	@Inject
	UserDefineCodelistRepository userDefineCodelistRepository;

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
	BusinessDesignRepository businessDesignRepository;

	@Inject
	TableDesignService tableDesignService;

	@Inject
	ScreenAreaRepository screenAreaRepository;

	@Inject
	ScreenItemRepository screenItemRepository;

	@Inject
	ScreenDesignRepository screendesignRepository;

	@Inject
	ScreenFormRepository screenFormRepository;

	@Inject
	ScreenActionRepository screenActionRepository;
	
	@Inject
	ProblemListRepository problemListRepository;

	@Inject
	SystemService systemService;
	
	@Inject
	ProjectService projectService;

	@Inject
	MessageDesignService messageDesignService;
	
	@Inject
	ExternalObjectDefinitionRepository externalObjectDefinitionRepository;
	
	@Inject
	ExternalObjectAttributeRepository externalObjectAttributeRepository;

	Map<Long, Set<ScreenArea>> mapAreaUpdateMappingObjectType = new HashMap<Long, Set<ScreenArea>>();
	
	private final String MESSAGE_TYPE_SCREEN = "sc";
	private final String NOW_VALUE = "now()";
	
	private static final String AUTOCOMPLETE = "Autocomplete";
	
	private Integer maxLengOfCode;
	private String nameMask;

	private void validateInputGraphicDbDesign(List<TableDesign> listOfSubmittedTables, Project project, List<TableDesign> listIdOfTablesToDelete, boolean checkOnDb, Timestamp updatedDate) throws BusinessException {
		ResultMessages resultMessages = ResultMessages.error();
		AccountProfile accountProfile = systemService.getDefaultProfile();
		
		String dataType = String.valueOf(project.getDbType());
		maxLengOfCode = accountProfile.getSqlCodeMaxLengthByDbType(dataType);
		String[] reservedWords = systemService.databaseReservedWords(dataType);

		nameMask = StringUtils.defaultString(accountProfile.getNameMask(), StringUtils.EMPTY);
		
		Long projectId = project.getProjectId();

		if (!FunctionCommon.isEmpty(listOfSubmittedTables)) {
			// get all datatype
			List<Basetype> listOfBasetype = domainRepository.getAllBasetype(projectId);
			
			// Temp check for table name
			List<String> listOfTableName = new ArrayList<String>();
			List<Long> listOfTableIdForName = new ArrayList<Long>();
			// Temp check for table code
			List<String> listOfTableCode = new ArrayList<String>();
			List<Long> listOfTableIdForCode = new ArrayList<Long>();
			// Temp check for key
			List<String> listOfTableKey = new ArrayList<String>();
			List<Long> listOfTableKeyId = new ArrayList<Long>();

			if (checkOnDb) {
				// get table from db
				List<TableDesign> listOfTableFromDB = tableDesignRepository.getTableDesignByProjectAndSubArea(projectId, DomainDatatypeConst.SEARCH_ALL_TABLE_DESIGN);
				// get key from db
				List<TableDesignKey> listTableDesignKeyFromDB = tableDesignKeyRepository.getAllByProjectAndSubArea(projectId, DomainDatatypeConst.SEARCH_ALL_TABLE_DESIGN);	

				if (CollectionUtils.isNotEmpty(listOfTableFromDB)) {
					// prepare for all table
					for (TableDesign table : listOfTableFromDB) {
						
						//DungNN add check Concurrence when delete 
						if (updatedDate != null && DateUtils.compare(table.getUpdatedDate(), updatedDate) == 1) {
							throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
						}
						
						// if table will be delete then not check
						if (TableDesignUtil.checkExistInList(table.getTableDesignId(), listIdOfTablesToDelete)) {
							continue;
						}
						// check exists table name
						if (checkExistMap(table.getTableName(), table.getTableDesignId(), listOfTableName, listOfTableIdForName)) {
							resultMessages.add(CommonMessageConst.ERR_SYS_0036, table.getTableName());
						} else {
							listOfTableName.add(table.getTableName());
							listOfTableIdForName.add(table.getTableDesignId());
						}
						// check exists table code
						if (checkExistMap(table.getTableCode(), table.getTableDesignId(), listOfTableCode, listOfTableIdForCode)) {
							resultMessages.add(CommonMessageConst.ERR_SYS_0036, table.getTableCode());
						} else {
							listOfTableCode.add(table.getTableCode());
							listOfTableIdForCode.add(table.getTableDesignId());
						}
					}
				}
				
				if (CollectionUtils.isNotEmpty(listTableDesignKeyFromDB)) {
					// prepare for all key
					for (TableDesignKey key : listTableDesignKeyFromDB) {
						// if table will be delete then not check
						if (TableDesignUtil.checkExistInList(key.getTableDesignId(), listIdOfTablesToDelete)) {
							continue;
						}
						// check exists key code
						if (checkExistMap(key.getCode(), key.getKeyId(), listOfTableKey, listOfTableKeyId)) {
							resultMessages.add(CommonMessageConst.ERR_SYS_0036, key.getCode());
						} else {
							listOfTableKey.add(key.getCode());
							listOfTableKeyId.add(key.getKeyId());
						}
					}
				}
			}
			// validate for table
			int numOfTableSubmit = FunctionCommon.isEmpty(listOfSubmittedTables) ? 0 : listOfSubmittedTables.size();

			// check empty, exist, length, format
			for (int i = 0; i < numOfTableSubmit; i++) {
				TableDesign tableDesignTemp = listOfSubmittedTables.get(i);

				/** Validate table name**/
				// validate require
				if (StringUtils.isBlank(tableDesignTemp.getTableName())) {
					resultMessages.add(CommonMessageConst.ERR_SYS_0025, MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0101));
				// validate format
				} else if (!Pattern.matches(accountProfile.getNamePattern(), tableDesignTemp.getTableName())) {
					resultMessages.add(CommonMessageConst.ERR_SYS_0126, new Object[] { MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0101), accountProfile.getNameMask() });
				//check exists
				} else if (checkExistMap(tableDesignTemp.getTableName(), tableDesignTemp.getTableDesignId(), listOfTableName, listOfTableIdForName)) {
					//resultMessages.add(CommonMessageConst.ERR_SYS_0036, tableDesignTemp.getTableName());
					resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0117,tableDesignTemp.getTableName(), tableDesignTemp.getTableName(),
							MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0101));
				//check max length
				} else if (tableDesignTemp.getTableName().length() > accountProfile.getNameMaxLength()) {
					resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0005, MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0101), tableDesignTemp.getTableName(), accountProfile.getNameMaxLength());
				} else {
					listOfTableName.add(tableDesignTemp.getTableName());
					listOfTableIdForName.add(tableDesignTemp.getTableDesignId());
				}

				// Validate table code
				// validate require
				if (StringUtils.isBlank(tableDesignTemp.getTableCode())) {
					resultMessages.add(CommonMessageConst.ERR_SYS_0025, MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0100));
				// validate format
				} else if (!Pattern.matches(accountProfile.getCodePattern(), tableDesignTemp.getTableCode())) {
					resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0010, 
							new Object[] { MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0100), tableDesignTemp.getTableName()});
				} else if (tableDesignTemp.getTableCode().length() > maxLengOfCode) {
					resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0005, 
								MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0100), 
								tableDesignTemp.getTableName(),maxLengOfCode);
				} else if (FunctionCommon.checkExists(reservedWords, tableDesignTemp.getTableCode())) {
					resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0121,tableDesignTemp.getTableName(), tableDesignTemp.getTableCode(),
							MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0100));
					
				} else if (checkExistMap(tableDesignTemp.getTableCode(), tableDesignTemp.getTableDesignId(), listOfTableCode, listOfTableIdForCode)) {
					resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0117,tableDesignTemp.getTableName(), tableDesignTemp.getTableCode(),
							MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0100)
					);
				} else if (FunctionCommon.checkExists(ValidationUtils.reserved, tableDesignTemp.getTableCode())) {

					resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0121,tableDesignTemp.getTableName(), tableDesignTemp.getTableCode(),
							MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0100));
				} else {
					listOfTableCode.add(tableDesignTemp.getTableCode());
					listOfTableIdForCode.add(tableDesignTemp.getTableDesignId());
					tableDesignTemp.setTableCode(StringUtils.lowerCase(tableDesignTemp.getTableCode()));
				}

				// Validate remark
				if (StringUtils.isNotBlank(tableDesignTemp.getRemark()) && tableDesignTemp.getRemark().length() > accountProfile.getRemarkMaxLength()) {
					resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0005, MessageUtils.getMessage(CommonMessageConst.SC_SYS_0028), tableDesignTemp.getTableName(), accountProfile.getRemarkMaxLength());
				}

				// validate key of table
				List<TableDesignKey> listTableKey = tableDesignTemp.getTableKey();
				if (listTableKey != null) {
					for (int j = 0; j < listTableKey.size(); j++) {
						TableDesignKey tableDesignKey = listTableKey.get(j);
						// Validate required key code
						if (StringUtils.isBlank(tableDesignKey.getCode())) {
							resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0002, MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0117), tableDesignTemp.getTableName());
						// Validate format
						} else if (!tableDesignKey.getCode().matches(accountProfile.getCodePattern())) {
							resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0010, 
									MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0117), 
									tableDesignTemp.getTableName());
						} else if (tableDesignKey.getCode().length() > maxLengOfCode) {
							resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0005, 
										MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0117), 
											tableDesignTemp.getTableName(), maxLengOfCode);
						// validate exists
						} else if (checkExistMap(tableDesignKey.getCode(), tableDesignKey.getKeyId(), listOfTableKey, listOfTableKeyId)) {
							resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0117, tableDesignTemp.getTableName(), tableDesignKey.getCode(),
									MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0117)
									);
						} else if (FunctionCommon.checkExists(reservedWords, tableDesignKey.getCode())) {
							resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0121, tableDesignTemp.getTableName(), tableDesignKey.getCode(),
									MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0117)
									);
						} else {
							listOfTableKey.add(tableDesignKey.getCode());
							listOfTableKeyId.add(tableDesignKey.getKeyId());

							tableDesignKey.setCode(StringUtils.lowerCase(tableDesignKey.getCode()));
						}

						// validate key item
						if (FunctionCommon.isEmpty(tableDesignKey.getKeyItems())) {
							resultMessages.add("err.databasedesign.0120", tableDesignKey.getCode(), tableDesignTemp.getTableName());
						}
					}
				}
				
				// validate column of table
				List<String> listOfColumnName = new ArrayList<String>();
				List<String> listOfColumnCode = new ArrayList<String>();
				// get infor detail
				List<TableDesignDetails> tableDetails = tableDesignTemp.getDetails();
				// check empty, exists, length, format for detail
				int seqNo = 0;
				for (TableDesignDetails tableDetail : tableDetails) {
					tableDetail.setItemSeqNo(seqNo++);
					
					// validate column name
					// validate require
					if (StringUtils.isBlank(tableDetail.getName())) {
						resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0002, GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0119, tableDesignTemp.getTableName());
					} else if (tableDetail.getName().length() > accountProfile.getNameMaxLength()) {
						//check max length
						resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0006, 
								MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0119),
								tableDetail.getName(),
								tableDesignTemp.getTableName(), 
								accountProfile.getNameMaxLength());
					} else if (!tableDetail.getName().matches(accountProfile.getNamePattern())) {
						// validate format
						resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0004, 
								MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0119), 
								tableDesignTemp.getTableCode(), accountProfile.getNamePattern());
					} else if (DomainDatatypeUtil.checkExistInList(tableDetail.getName(), listOfColumnName)) {
						resultMessages.add("err.databasedesign.0116", tableDesignTemp.getTableName(), tableDetail.getName());
					} else {
						listOfColumnName.add(tableDetail.getName());
					}
					// validate column code
					// validate require
					if (StringUtils.isBlank(tableDetail.getCode())) {
						resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0002, MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0116), tableDesignTemp.getTableName());
					} else if (tableDetail.getCode().length() > maxLengOfCode) {
						resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0006, 
									MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0116),
									tableDetail.getCode(),
									tableDesignTemp.getTableName(),
									maxLengOfCode);
					} else if (FunctionCommon.checkExists(reservedWords, tableDetail.getCode())) {
						resultMessages.add(CommonMessageConst.ERR_SYS_0133, tableDesignTemp.getTableName(), tableDetail.getCode());
					} else if (FunctionCommon.checkExists(ValidationUtils.reserved, tableDetail.getCode())) {
						resultMessages.add(CommonMessageConst.ERR_SYS_0133, tableDesignTemp.getTableName(), tableDetail.getCode());
					}else if (!tableDetail.getCode().matches(accountProfile.getCodePattern())) {
						// Validate format
						resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0010, 
								MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0116), 
								tableDesignTemp.getTableName());
						// check exists
					} else if (DomainDatatypeUtil.checkExistInList(tableDetail.getCode(), listOfColumnCode)) {
						resultMessages.add("err.databasedesign.0108", tableDesignTemp.getTableName(), tableDetail.getCode());
					} else {
						listOfColumnCode.add(tableDetail.getCode());
						tableDetail.setCode(StringUtils.lowerCase(tableDetail.getCode()));
					}

					// Validate remark
					if (!StringUtils.isBlank(tableDetail.getRemark()) && tableDetail.getRemark().length() > accountProfile.getRemarkMaxLength()) {
						resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0006, MessageUtils.getMessage(CommonMessageConst.SC_SYS_0028), tableDetail.getName(), tableDesignTemp.getTableName(), accountProfile.getRemarkMaxLength());
					}

					// prepare something from datatype
					for (Basetype basetype : listOfBasetype) {
						if (basetype.getBasetyeId().equals(tableDetail.getDataType())) {
							tableDetail.setDataTypeFlg(basetype.getDataTypeFlg());
							tableDetail.setFmtCode(basetype.getValidationRule());
							tableDetail.setGroupBaseTypeId(basetype.getGroupBaseTypeId());
							tableDetail.setDatasourceType(basetype.getDatasourceType());
							tableDetail.setConstrainsType(basetype.getConstrainsType());
							tableDetail.setBaseType(basetype.getPrimitiveId());
							if (StringUtils.isNoneBlank(basetype.getLength())) {
								tableDetail.setMaxlength(Integer.valueOf(basetype.getLength()));
							}
							break;
						}
					}

					// validate length if data type is text or char
					if (DbDomainConst.DataTypeFlag.PRIMITIVE.equals(tableDetail.getDataTypeFlg())) {
						if (DataTypeUtils.equals(DomainDatatypeConst.PhysicalDataTypeDetail.TEXT, tableDetail.getDataType()) || DataTypeUtils.equals(DomainDatatypeConst.PhysicalDataTypeDetail.CHAR, tableDetail.getDataType()) || DataTypeUtils.equals(DomainDatatypeConst.PhysicalDataTypeDetail.CHARACTER_VARYING, tableDetail.getDataType())) {
							
							if (FunctionCommon.notEquals(DomainDatatypeConst.PhysicalDataTypeDetail.TEXT, tableDetail.getDataType())) {
								if (FunctionCommon.isEmpty(tableDetail.getMaxlength())) {
									resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0007, MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0031), tableDetail.getName(), tableDesignTemp.getTableName());
								} else if (tableDetail.getMaxlength() > accountProfile.getRemarkMaxLength()) {
									resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0006, MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0031), tableDetail.getName(), tableDesignTemp.getTableName(), accountProfile.getRemarkMaxLength());
								}
							}

							if (StringUtils.isNoneBlank(tableDetail.getDefaultValue())) {
								
								int maxlengthCompare = (FunctionCommon.isEmpty(tableDetail.getMaxlength()) || tableDetail.getMaxlength().intValue() > accountProfile.getRemarkMaxLength()) ? accountProfile.getRemarkMaxLength() : tableDetail.getMaxlength().intValue();
								if (maxlengthCompare < tableDetail.getDefaultValue().length()) {
									resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0006, MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0033), tableDetail.getName(), tableDesignTemp.getTableName(), maxlengthCompare);
								}
							}

						} else if (DataTypeUtils.equals(DomainDatatypeConst.PhysicalDataTypeDetail.INTEGER,tableDetail.getDataType()) 
								|| DataTypeUtils.equals(DomainDatatypeConst.PhysicalDataTypeDetail.SMALLINT,tableDetail.getDataType()) 
								|| DataTypeUtils.equals(DomainDatatypeConst.PhysicalDataTypeDetail.BIGINT,tableDetail.getDataType()) 
								|| DataTypeUtils.equals(DomainDatatypeConst.PhysicalDataTypeDetail.SERIAL,tableDetail.getDataType()) 
								|| DataTypeUtils.equals(DomainDatatypeConst.PhysicalDataTypeDetail.BIGSERIAL,tableDetail.getDataType())
								|| DataTypeUtils.equals(DomainDatatypeConst.PhysicalDataTypeDetail.BYTE,tableDetail.getDataType())
								|| DataTypeUtils.equals( DomainDatatypeConst.PhysicalDataTypeDetail.FLOAT,tableDetail.getDataType())
								|| DataTypeUtils.equals(DomainDatatypeConst.PhysicalDataTypeDetail.DOUBLE,tableDetail.getDataType())
								) {
							// if is integer then validate default
							List<String> checkOutOfRange = TableDesignUtil.checkValidValueOfBaseType(tableDetail.getGroupBaseTypeId(), tableDetail.getDefaultValue());
							if (!FunctionCommon.isEmpty(checkOutOfRange)) {
								resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0008, MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0033), tableDetail.getName(), tableDesignTemp.getTableName(), checkOutOfRange.get(0), checkOutOfRange.get(1));
							}
						} else if ((DataTypeUtils.equals(DomainDatatypeConst.PhysicalDataTypeDetail.DATE,tableDetail.getDataType()) 
								|| DataTypeUtils.equals(DomainDatatypeConst.PhysicalDataTypeDetail.TIME,tableDetail.getDataType()) 
								|| DataTypeUtils.equals(DomainDatatypeConst.PhysicalDataTypeDetail.DATETIME,tableDetail.getDataType()) 
								) && StringUtils.equalsIgnoreCase(NOW_VALUE, tableDetail.getDefaultValue())) {
							tableDetail.setDefaultType(DbDomainConst.DefaultType.FUNCTION);
						}
					}

					tableDetail.setJavaType(BusinessDesignHelper.convertJavaTypeFromBaseType(tableDetail.getBaseType()));
				}
			}
		}
		// if has error then throw ex
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		}
	}

	/**
	 * DungNN modify
	 */
	@Override
	public GraphicDbDesign modifyTableByGraphicDesign(GraphicDbDesign graphicDbDesign, boolean hasProblem, List<BusinessDesign> lstBlogic, List<Long> lstSreenIds,
			boolean generateDefaultDB, Long accountId, LanguageDesign languageDesign, Integer checkProject) throws BusinessException {

		SqlDesignXml sqlDesign = this.converFromXMLToObject(graphicDbDesign);
		if (!generateDefaultDB) {
			this.arrangeTableDesign(graphicDbDesign.getTableDesigns(), sqlDesign.getListOfTables());
		}
		List<TableDesign> listOfSubmittedTables = null;
		
		try {
			Project project = graphicDbDesign.getProject();
			Long projectId = project.getProjectId(); 
			if (DbDomainConst.YesNoFlg.YES.equals(checkProject) ) {
				projectService.validateProject(accountId, projectId);
			}
			
			if (sqlDesign != null) {
				Timestamp currentTime = FunctionCommon.getCurrentTime();
				// get list TableDesign from form
				listOfSubmittedTables = sqlDesign.getListOfTables();

				int numOfTableDesign = CollectionUtils.isEmpty(listOfSubmittedTables) ? 0 : listOfSubmittedTables.size();

				// get table from db
				List<TableDesign> listOfTableFromDB = tableDesignRepository.getTableDesignByProjectAndSubArea(projectId, graphicDbDesign.getSubjectAreaId());
				int numOfTableDesignDB = CollectionUtils.isEmpty(listOfTableFromDB) ? 0 : listOfTableFromDB.size();
				
				// get all row from DB by project and subject area
				List<TableDesignDetails> listTableDesignDetailFromDb = null;
				if (numOfTableDesignDB > 0) {
					listTableDesignDetailFromDb = tableDesignDetailRepository.getAllByProjectAndSubArea(projectId, graphicDbDesign.getSubjectAreaId());
				} else  {
					listTableDesignDetailFromDb = new ArrayList<TableDesignDetails>();
				}

				List<TableDesign> listIdOfTablesToDelete = new ArrayList<TableDesign>();
				if (!generateDefaultDB) {
					// if delete all
					if (FunctionCommon.isEmpty(listOfSubmittedTables) && numOfTableDesignDB > 0) {
						// Check for deleted tables
						for (TableDesign tableDesignDb : listOfTableFromDB) {
							//DungNN add check Concurrence when delete 
							if (DateUtils.compare(tableDesignDb.getUpdatedDate(), graphicDbDesign.getUpdatedDate()) == 1) {
								throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
							}

							// check status design is fixed don't action (modify or remove) or create after graphic design loaded
							if (DbDomainConst.DesignStatus.UNDER_DESIGN.equals(tableDesignDb.getDesignStatus())
									&& DateUtils.compare(tableDesignDb.getCreatedDate(), graphicDbDesign.getUpdatedDate()) == -1) {
								tableDesignDb.setIsDeleted(true);
								listIdOfTablesToDelete.add(tableDesignDb);
							}
						}
						// delete table and everything properties
						if (!FunctionCommon.isEmpty(listIdOfTablesToDelete)) {
							deleteTableDesign(listIdOfTablesToDelete);
						}
						if (hasProblem) {
							tableDesignService.impactChangeDesignSqlDesignByDeleteTable(listIdOfTablesToDelete, accountId, projectId);
							tableDesignService.impactChangeDesignBusinessLogicByDeleteTable(listIdOfTablesToDelete, projectId);
						}
						// Update FK
						this.updateForeignKeyWhenDeleteTables(listIdOfTablesToDelete, listTableDesignDetailFromDb);
						
						return loadGraphicDesign(projectId, graphicDbDesign.getSubjectAreaId());
					}

					// Check for deleted some tables
					for (TableDesign tableDesignDb : listOfTableFromDB) {

						//DungNN add check Concurrence when delete 
						if (DateUtils.compare(tableDesignDb.getUpdatedDate(), graphicDbDesign.getUpdatedDate()) == 1) {
							throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
						}

						boolean removedFlag = true;
						for (int i = 0; i < numOfTableDesign; i++) {
							TableDesign submittedTableDesign = listOfSubmittedTables.get(i);

							if (submittedTableDesign.getTableDesignId().equals(tableDesignDb.getTableDesignId())) {
								removedFlag = false;
								break;
							}
						}
						// check status design is fixed don't action (modify or remove)
						if (removedFlag && DbDomainConst.DesignStatus.UNDER_DESIGN.equals(tableDesignDb.getDesignStatus())
								&& DateUtils.compare(tableDesignDb.getCreatedDate(), graphicDbDesign.getUpdatedDate()) == -1) {
							tableDesignDb.setIsDeleted(true);
							listIdOfTablesToDelete.add(tableDesignDb);
						}
					}

					// delete before and check foreign key
					if (!FunctionCommon.isEmpty(listIdOfTablesToDelete)) {
						// Update FK
						this.updateForeignKeyWhenDeleteTables(listIdOfTablesToDelete, listTableDesignDetailFromDb);
						deleteTableDesign(listIdOfTablesToDelete);
					}
				}

				// validate
				/*validateInputGraphicDbDesign(listOfSubmittedTables, project, listIdOfTablesToDelete, numOfTableDesignDB != 0);*/
				
				validateInputGraphicDbDesign(listOfSubmittedTables, project, listIdOfTablesToDelete, true, graphicDbDesign.getUpdatedDate());
				
				// get all validation rule
				List<ValidationRule> validationRules = domainDatatypeRepository.findAllValidationRule();
				
				// Update X, Y for Sunject Area
				this.setCoordinatesForSubArea(listOfSubmittedTables, graphicDbDesign.getSubjectAreaId());
				
				int numOfTableDetails = FunctionCommon.isEmpty(listTableDesignDetailFromDb) ? 0 : listTableDesignDetailFromDb.size();
				
				List<TableDesignKey> listTableDesignKeyFromDb = null;
				int numOfTableDesignKey = 0;
				List<TableDesignForeignKey> listTableDesignForeignKeyFromDb = null;
				int numOfTableDesignForeignKey = 0;
				if (numOfTableDesignDB > 0) {
					// get key from DB
					listTableDesignKeyFromDb = tableDesignKeyRepository.getAllByProjectAndSubArea(projectId, graphicDbDesign.getSubjectAreaId());
					numOfTableDesignKey = FunctionCommon.isEmpty(listTableDesignKeyFromDb) ? 0 : listTableDesignKeyFromDb.size();
					// get foreign key from DB
					listTableDesignForeignKeyFromDb = tableDesignForeignKeyRepository.getAllByProjectAndSubArea(projectId, graphicDbDesign.getSubjectAreaId());
					numOfTableDesignForeignKey = FunctionCommon.isEmpty(listTableDesignForeignKeyFromDb) ? 0 : listTableDesignForeignKeyFromDb.size();
				}
				// List something will be deleted
				List<Long> listIdOfColumnsToDelete = new ArrayList<Long>();
				List<Long> listIdOfKeysToDelete = new ArrayList<Long>();
				List<Long> listIdOfForeignKeysTodelete = new ArrayList<Long>();
				List<Long> listIdOfUserdefineCodelist = new ArrayList<Long>();

				// list of column will be updated
				List<TableDesignDetails> listOfTableDesignDetailToUpdate = new ArrayList<TableDesignDetails>();
				// list subject area
				List<SubjectAreaTableDesign> listOfSubAreaTableToInsert = new ArrayList<SubjectAreaTableDesign>();
				// list of columns of current table from db
				List<TableDesignDetails> listOfTableDesignDetailDbTemp = null;

				List<TableDesignDetails> listOfTableDesignDetailContentColumnDelete = new ArrayList<TableDesignDetails>();
				List<TableDesignDetails> listOfTableDesignDetailContentColumnAddNew = new ArrayList<TableDesignDetails>();

				Map<Long, Long> mapFk = new HashMap<Long, Long>();

				if (listOfSubmittedTables != null) {
					
					GenerateUniqueKey generateUniqueKey = new GenerateUniqueKey(DbDomainConst.MAX_LENGTH_OF_NAME, maxLengOfCode); 
					
					// Check for updated and new tables
					for (TableDesign submittedTableDesign : listOfSubmittedTables) {
						submittedTableDesign.setListTableDesignDetails(submittedTableDesign.getDetails());
						submittedTableDesign.setIsDeleted(false);
						listOfTableDesignDetailDbTemp = new ArrayList<TableDesignDetails>();

						submittedTableDesign.setProjectId(projectId);
						if (FunctionCommon.isEmpty(submittedTableDesign.getTableDesignId())) {
							// if is create new
							if(submittedTableDesign.getDesignStatus() == null)
								submittedTableDesign.setDesignStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
							
							submittedTableDesign.setCreatedBy(accountId);
							submittedTableDesign.setUpdatedBy(accountId);
							submittedTableDesign.setCreatedDate(currentTime);
							submittedTableDesign.setUpdatedDate(currentTime);
							submittedTableDesign.setProjectId(projectId);
							// create table
							tableDesignRepository.create(submittedTableDesign);
							// insert sub_area_design_table
							SubjectAreaTableDesign subAreaTableDesign = new SubjectAreaTableDesign();
							if (!FunctionCommon.isEmpty(graphicDbDesign.getSubjectAreaId()) && graphicDbDesign.getSubjectAreaId() > 0) {
								subAreaTableDesign.setSubAreaId(graphicDbDesign.getSubjectAreaId());
								subAreaTableDesign.setTableId(submittedTableDesign.getTableDesignId());
								subAreaTableDesign.setxCoordinates(submittedTableDesign.getX());
								subAreaTableDesign.setyCoordinates(submittedTableDesign.getY());
								listOfSubAreaTableToInsert.add(subAreaTableDesign);
							}
						} else {
							submittedTableDesign.setUpdatedBy(accountId);
							submittedTableDesign.setSystemTime(FunctionCommon.getCurrentTime());
							// update table false return
							if (!tableDesignRepository.modifyTableDesign(submittedTableDesign)) {
								throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
							}

							// prepare columns from db
							for (int i = 0; i < numOfTableDetails; i++) {
								TableDesignDetails detailFromDb = listTableDesignDetailFromDb.get(i);
								if (detailFromDb.getTableDesignId().equals(submittedTableDesign.getTableDesignId())) {
									listOfTableDesignDetailDbTemp.add(detailFromDb);
									listTableDesignDetailFromDb.remove(i);
									// decrease one element
									numOfTableDetails--;
									i--;
								}
							}
							// check will be delete column
							for (TableDesignDetails tableDetailDb : listOfTableDesignDetailDbTemp) {
								boolean removedFlag = true;
								for (TableDesignDetails submittedTableDetails : submittedTableDesign.getDetails()) {
									if (submittedTableDetails.getColumnId().equals(tableDetailDb.getColumnId())) {
										removedFlag = false;
										break;
									}
								}
								if (removedFlag) {
									listIdOfColumnsToDelete.add(tableDetailDb.getColumnId());
									listOfTableDesignDetailContentColumnDelete.add(tableDetailDb);
								}
							}
							// Run to check deleted keys
							List<TableDesignKey> listOfTableDesignKeyDb = new ArrayList<TableDesignKey>();
							// prepare key from db
							for (int i = 0; i < numOfTableDesignKey; i++) {
								TableDesignKey keyTemp = listTableDesignKeyFromDb.get(i);
								if (keyTemp.getTableDesignId().equals(submittedTableDesign.getTableDesignId())) {
									listOfTableDesignKeyDb.add(keyTemp);
									listTableDesignKeyFromDb.remove(i);
									// decrease one element
									i--;
									numOfTableDesignKey--;
								}
							}
							// check will be delete key
							for (TableDesignKey tableDesignKeyDb : listOfTableDesignKeyDb) {
								boolean removedFlag = true;
								for (TableDesignKey submittedTableDetailKey : submittedTableDesign.getTableKey()) {
									if (submittedTableDetailKey.getKeyId().equals(tableDesignKeyDb.getKeyId())) {
										removedFlag = false;
										break;
									}
								}
								if (removedFlag)
									listIdOfKeysToDelete.add(tableDesignKeyDb.getKeyId());
							}
						}

						
						// Run to check updated and new columns
						for (TableDesignDetails submittedTableDetails : submittedTableDesign.getDetails()) {
							// reset key
							submittedTableDetails.setBinKeyType(DbDomainConst.TblDesignKeyType.NONE);
							// set table id in case the table is a new one
							submittedTableDetails.setTableDesignId(submittedTableDesign.getTableDesignId());
							submittedTableDetails.setSeqCode(TableDesignUtil.genSeqName(submittedTableDesign.getTableCode(), submittedTableDetails, generateUniqueKey));

							if (FunctionCommon.isEmpty(submittedTableDetails.getColumnId()) || !TableDesignUtil.checkColumnBelong(submittedTableDetails.getColumnId(), listOfTableDesignDetailDbTemp)) {
								// create column
								//if auto increment or has default value then can unused
								if (DbDomainConst.YesNoFlg.YES.equals(submittedTableDetails.getAutoIncrementFlag())
										|| StringUtils.isNotBlank(submittedTableDetails.getDefaultValue())) {
									submittedTableDetails.setDisplayType(DbDomainConst.DisplayType.UNUSED);
								} else {
									submittedTableDetails.setDisplayType(DbDomainConst.DisplayType.USED);
								}

								submittedTableDetails.setColumnId(null);
								//submittedTableDetails.setDisplayType(DbDomainConst.DisplayType.USED);
								
								TableDesignUtil.initDefaultForItemType(submittedTableDetails, validationRules);
								/*
								 * if (DbDomainConst.DataTypeFlag.DOMAIN_DATA.equals(submittedTableDetails.getDataTypeFlg())) { submittedTableDetails.setDatasourceType(null); }
								 */
								listOfTableDesignDetailContentColumnAddNew.add(submittedTableDetails);
								tableDesignDetailRepository.create(submittedTableDetails);
							} else {
								// update column
								// prepare some data not load on graphic design
								for (TableDesignDetails tableDetailDb : listOfTableDesignDetailDbTemp) {
									if (submittedTableDetails.equals(tableDetailDb)) {
										// if column has change base type group or change fmt -> reset default item type
										if (submittedTableDetails.hasBeenChangedBasetype(tableDetailDb)
										/* || submittedTableDetails.hasBeenChangedFmt(tableDetailDb) */) {
											TableDesignUtil.initDefaultForItemType(submittedTableDetails, validationRules);
										} else {
											submittedTableDetails.setItemType(tableDetailDb.getItemType());
										}
										// display type
										if (DbDomainConst.YesNoFlg.YES.equals(submittedTableDetails.getIsMandatory())){
											if (DbDomainConst.YesNoFlg.NO.equals(submittedTableDetails.getAutoIncrementFlag())
													&& StringUtils.isNotBlank(submittedTableDetails.getDefaultValue())) {
												submittedTableDetails.setDisplayType(DbDomainConst.DisplayType.USED);
											} else {
												submittedTableDetails.setDisplayType(tableDetailDb.getDisplayType());
											}
										} else {
											submittedTableDetails.setDisplayType(tableDetailDb.getDisplayType());
										}
										// if don't change datatype
										if (!submittedTableDetails.hasBeenChangedDatatype(tableDetailDb)) {
											// and is primitive then keep something
											if (DbDomainConst.DataTypeFlag.PRIMITIVE.equals(submittedTableDetails.getDataTypeFlg())) {
												submittedTableDetails.setConstrainsType(tableDetailDb.getConstrainsType());
												submittedTableDetails.setDatasourceType(tableDetailDb.getDatasourceType());
												submittedTableDetails.setMinVal(tableDetailDb.getMinVal());
												submittedTableDetails.setMaxVal(tableDetailDb.getMaxVal());
												submittedTableDetails.setOperatorCode(tableDetailDb.getOperatorCode());
												submittedTableDetails.setDatasourceId(tableDetailDb.getDatasourceId());
											}
											// if changed and has configuration user define codelist then delete it
										} else if (DbDomainConst.DatasourceType.CODELIST.equals(tableDetailDb.getDatasourceType())) {
											listIdOfUserdefineCodelist.add(tableDetailDb.getDatasourceId());
										}
										//DungNN - 20160709 - if constrain type is null then set = any
										if (submittedTableDetails.getConstrainsType() == null) {
											submittedTableDetails.setConstrainsType(DbDomainConst.ConstrainsType.NONE);
										}

										break;
									}
								}

								if (DbDomainConst.DataTypeFlag.DOMAIN_DATA.equals(submittedTableDetails.getDataTypeFlg())) {
									submittedTableDetails.setDatasourceType(null);
								}

								/* tableDesignDetailRepository.update(submittedTableDetails); */
								listOfTableDesignDetailToUpdate.add(submittedTableDetails);

								// get fk of this column from DB
								List<TableDesignForeignKey> listOfTemp = new ArrayList<TableDesignForeignKey>();
								if (numOfTableDesignForeignKey > 0) {
									for (TableDesignForeignKey tableDesignForeignKeyDb : listTableDesignForeignKeyFromDb) {
										if (tableDesignForeignKeyDb.getFromColumnId().equals(submittedTableDetails.getColumnId())) {
											listOfTemp.add(tableDesignForeignKeyDb);
										}
									}
								}

								int numFkOfColumn = CollectionUtils.isEmpty(listOfTemp) ? 0 : listOfTemp.size();

								// check will be delete or foreign key
								List<TableDesignForeignKey> submittedTableForeignKeys = submittedTableDetails.getForeignKeys();
								
								int numFkOfColumnSubmit = CollectionUtils.isEmpty(submittedTableForeignKeys) ? 0 : submittedTableForeignKeys.size();
								
								if (numFkOfColumn > 0) {
									if (numFkOfColumnSubmit == 0) {
										// check column has fk from db then delete
										for (TableDesignForeignKey tableDesignForeignKeyDb : listOfTemp) {
											if (tableDesignForeignKeyDb.getFromColumnId().equals(submittedTableDetails.getColumnId())) {
												listIdOfForeignKeysTodelete.add(tableDesignForeignKeyDb.getForeignKeyId());
											}
										}
									} else {
										// if has fk, check on change to delete old
										for (int i = 0; i < numFkOfColumn; i++) {
											TableDesignForeignKey tableDesignForeignKeyDb = listOfTemp.get(i);

											for (int j = 0; j < numFkOfColumnSubmit && numFkOfColumn > 0; j++) {
												TableDesignForeignKey submittedTableForeignKey = submittedTableForeignKeys.get(j);
												if (tableDesignForeignKeyDb.getFromColumnId().equals(submittedTableDetails.getColumnId()) && DataTypeUtils.equals(tableDesignForeignKeyDb.getToColumnCode(), submittedTableForeignKey.getToColumnCode()) && DataTypeUtils.equals(tableDesignForeignKeyDb.getForeignKeyId(), submittedTableForeignKey.getForeignKeyId())) {
													listOfTemp.remove(i);
													i--;
													numFkOfColumn--;
												}
											}
										}
										if (numFkOfColumn > 0) {
											for (TableDesignForeignKey temp : listOfTemp) {
												listIdOfForeignKeysTodelete.add(temp.getForeignKeyId());
											}
										}
									}
								}
							}
						}
					}
					ResultMessages resultMessages = ResultMessages.error();
					for (TableDesign submittedTableDesign : listOfSubmittedTables) {
						for (TableDesignDetails submittedTableDetails : submittedTableDesign.getDetails()) {

							// Run to check updated and new foreign key
							List<TableDesignForeignKey> submittedForeignKeys = submittedTableDetails.getForeignKeys();
							if (CollectionUtils.isNotEmpty(submittedForeignKeys)) {
								// set foreignkey to keytype
								submittedTableDetails.setBinKeyType(submittedTableDetails.getBinKeyType() ^ DbDomainConst.TblDesignKeyType.FK);

								// tableDesignDetailRepository.update(submittedTableDetails);
								int index = listOfTableDesignDetailToUpdate.indexOf(submittedTableDetails);
								if (index < 0) {
									listOfTableDesignDetailToUpdate.add(submittedTableDetails);
								} else {
									listOfTableDesignDetailToUpdate.set(index, submittedTableDetails);
								}
								
								this.setIdForForeignKey(listOfSubmittedTables, submittedForeignKeys);
								
								// Get collection column id
								for (TableDesignForeignKey foreignKey : submittedForeignKeys) {
									foreignKey.setForeignKeyType(submittedTableDetails.getGroupBaseTypeId());
								}
								List<TableDesignDetails> getAllInformationByTableDesign = this.findTableDetailsByColumnId(listOfSubmittedTables);
								
								for (TableDesignForeignKey submittedTableDesignForeignKey : submittedForeignKeys) {
									// set table id in case the table is a new one
									submittedTableDesignForeignKey.setFromTableId(submittedTableDetails.getTableDesignId());
									submittedTableDesignForeignKey.setFromColumnId(submittedTableDetails.getColumnId());

									for (TableDesign submittedTableDesign2 : listOfSubmittedTables) {
										if (submittedTableDesign2.getTableCode().equals(submittedTableDesignForeignKey.getToTableCode())) {
											submittedTableDesignForeignKey.setToTableId(submittedTableDesign2.getTableDesignId());

											for (TableDesignDetails submittedTableDetails2 : submittedTableDesign2.getDetails()) {
												if (submittedTableDetails2.getCode().equals(submittedTableDesignForeignKey.getToColumnCode())) {
													submittedTableDesignForeignKey.setToColumnId(submittedTableDetails2.getColumnId());

													break;
												}
											}
											break;
										}
									}
									if (FunctionCommon.isEmpty(submittedTableDesignForeignKey.getForeignKeyId())) {
										// create key
										if (!TableDesignUtil.checkExistFk(submittedTableDesignForeignKey.getFromColumnId(), submittedTableDesignForeignKey.getToColumnId(), mapFk)) {
											submittedTableDesignForeignKey.setForeignKeyCode(generateUniqueKey.calculateCodeForManual(
													MessageFormat.format(TableDesignForeignKey.FK_CODE_TEMPLATE, submittedTableDesign.getTableCode(), 
															submittedTableDetails.getCode(), submittedTableDesignForeignKey.getToTableCode(), submittedTableDesignForeignKey.getToColumnCode()),
													GenerateUniqueKey.generateRandomInteger(), true));

											if(!this.isDataTypeEqual(getAllInformationByTableDesign, submittedTableDesignForeignKey)){
												resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0122, submittedTableDesign.getTableName(), submittedTableDesignForeignKey.getToTableName(), null);
											}
											
											if (!resultMessages.isNotEmpty()) {
												tableDesignForeignKeyRepository.create(submittedTableDesignForeignKey);
												mapFk.put(submittedTableDesignForeignKey.getFromColumnId(), submittedTableDesignForeignKey.getToColumnId());
											}
										}
									} else {
										// update key
										if(!this.isDataTypeEqual(getAllInformationByTableDesign, submittedTableDesignForeignKey)){
											resultMessages.add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0122, submittedTableDesign.getTableName(), submittedTableDesignForeignKey.getToTableName(), null);
										}
										
										if (!resultMessages.isNotEmpty()) {
											tableDesignForeignKeyRepository.updateWithoutCode(submittedTableDesignForeignKey);
											mapFk.put(submittedTableDesignForeignKey.getFromColumnId(), submittedTableDesignForeignKey.getToColumnId());
										}
									}
								}
							}
						}
						// Run to check updated and new keys
						for (TableDesignKey submittedTableDesignKey : submittedTableDesign.getTableKey()) {
							// set table id in case the table is a new one
							submittedTableDesignKey.setTableDesignId(submittedTableDesign.getTableDesignId());
							if (FunctionCommon.isEmpty(submittedTableDesignKey.getKeyId())) {
								// create key
								tableDesignKeyRepository.create(submittedTableDesignKey);
							} else {
								// update key
								tableDesignKeyRepository.update(submittedTableDesignKey);
							}
							// Delete old key items
							tableDesignKeyRepository.deleteAllKeyItems(submittedTableDesignKey.getKeyId());
							// Rebuild key item
							for (String submittedTableDesignKeyItem : submittedTableDesignKey.getKeyItems()) {
								for (TableDesignDetails submittedTableDetails : submittedTableDesign.getDetails()) {
									if (submittedTableDetails.getCode().equals(submittedTableDesignKeyItem)) {
										TableDesignKeyItem tableDesignKeyItem = new TableDesignKeyItem();
										tableDesignKeyItem.setColumnId(submittedTableDetails.getColumnId());
										tableDesignKeyItem.setTableDesignKeyId(submittedTableDesignKey.getKeyId());
										tableDesignKeyRepository.createTableKeyItem(tableDesignKeyItem);
										// update key_type
										submittedTableDetails.setBinKeyType(submittedTableDetails.getBinKeyType() ^ submittedTableDesignKey.getType());
										/* tableDesignDetailRepository.update(submittedTableDetails); */

										int index = listOfTableDesignDetailToUpdate.indexOf(submittedTableDetails);
										if (index < 0) {
											listOfTableDesignDetailToUpdate.add(submittedTableDetails);
										} else {
											listOfTableDesignDetailToUpdate.set(index, submittedTableDetails);
										}

										break;
									}
								}
							}
						}
					}
					if (resultMessages.isNotEmpty()) {
						throw new BusinessException(resultMessages);
					}
				}
				listTableDesignDetailFromDb = tableDesignDetailRepository.getAllByProjectAndSubArea(projectId, graphicDbDesign.getSubjectAreaId());

				if (CollectionUtils.isNotEmpty(listOfTableDesignDetailToUpdate))
					tableDesignDetailRepository.updateTableDesignDetails(listOfTableDesignDetailToUpdate);

				if (CollectionUtils.isNotEmpty(listOfSubAreaTableToInsert)) {
					subjectAreaTableRepository.insertArray(listOfSubAreaTableToInsert);
				}

				// update
				// Delete Section
				// delete user define code list
				if (CollectionUtils.isNotEmpty(listIdOfUserdefineCodelist))
					userDefineCodelistRepository.multiDeleteUserDefineCodelist(listIdOfUserdefineCodelist);
				// delete columns, table of which was not deleted
				/*
				 * for (Long tableDesignDetailId : listIdOfColumnsToDelete) { tableDesignForeignKeyRepository.deleteByTableDesignDetail(tableDesignDetailId); tableDesignDetailRepository.delete(tableDesignDetailId); }
				 */
				if (CollectionUtils.isNotEmpty(listIdOfColumnsToDelete)) {
					tableDesignService.updateForeignKeyWhenDeleteColumns(listIdOfColumnsToDelete);
					tableDesignDetailRepository.multiDelete(listIdOfColumnsToDelete);
				}
				// delete foreign keys
				/*
				 * for (Long tableDesignForeignKeyId : listIdOfForeignKeysTodelete) { tableDesignForeignKeyRepository.delete(tableDesignForeignKeyId); }
				 */
				if (CollectionUtils.isNotEmpty(listIdOfForeignKeysTodelete))
					tableDesignForeignKeyRepository.multiDelete(listIdOfForeignKeysTodelete);
				// delete keys
				/*
				 * for (Long tableDesignKeyId : listIdOfKeysToDelete) { // Delete all item keys first tableDesignKeyRepository.deleteAllKeyItems(tableDesignKeyId); //Delete key tableDesignKeyRepository.delete(tableDesignKeyId); }
				 */
				if (CollectionUtils.isNotEmpty(listIdOfKeysToDelete))
					tableDesignKeyRepository.multiDelete(listIdOfKeysToDelete);

				// Add problem to problem_list
				if(FunctionCommon.isNotEmpty(listOfSubmittedTables)){
					List<TableDesign> tableDesignsContentColumnDelete = this.arrangeTableDetails(graphicDbDesign, listOfSubmittedTables, listOfTableDesignDetailContentColumnDelete, null, projectId);
					tableDesignService.processAutoSynchDataReferBusinessLogicByDeleteColumn(tableDesignsContentColumnDelete, accountId, projectId);
	
					List<TableDesign> tableDesignsContentColumnAddNew = this.arrangeTableDetails(graphicDbDesign, listOfSubmittedTables, listOfTableDesignDetailContentColumnAddNew, null, projectId);
					tableDesignService.processAutoSynchDataReferBusinessLogicByAddingColumn(tableDesignsContentColumnAddNew);
	
					List<TableDesign> tableDesignsContentColumnChangeType = this.arrangeTableDetails(graphicDbDesign, listOfSubmittedTables, listOfTableDesignDetailToUpdate, listTableDesignDetailFromDb, projectId);
					tableDesignService.processAutoSynchDataReferBusinessLogicByModifyTableDesign(tableDesignsContentColumnChangeType);

					sqlDesignConditionRepository.updateNameAffect(listOfSubmittedTables);
					sqlDesignResultRepository.updateNameAffect(listOfSubmittedTables);
					sqlDesignTableRepository.updateNameAffect(listOfSubmittedTables);
					sqlDesignTableItemsRepository.updateNameAffect(listOfSubmittedTables);
					sqlDesignValueRepository.updateNameAffect(listOfSubmittedTables);
					
					// Delete problem when change Oracle tp Postgres
					problemListRepository.deleteFromResourceTypeOfProject(DbDomainConst.FromResourceType.PROJECT_CHANGE_DB_TYPE, projectId);
				}
			}
			if (!generateDefaultDB) {
				return loadGraphicDesign(projectId, graphicDbDesign.getSubjectAreaId());
			} else {
				if (FunctionCommon.isNotEmpty(lstBlogic)) {
					// generate business logic
					generateBlogic(lstBlogic, listOfSubmittedTables, graphicDbDesign, languageDesign);
				}
				if (FunctionCommon.isNotEmpty(lstSreenIds)) {
					// update design mode
					updateDesignMode(lstSreenIds, DbDomainConst.DesignMode.BUSINESS);
				}
				
				// QuyND: Return list of inserted tables
				graphicDbDesign.setTableDesigns(listOfSubmittedTables);
				return graphicDbDesign;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// if missing check fk then put out message
			if (ex.getMessage().contains("violates foreign key constraint")) {
				ResultMessages resultMessages = ResultMessages.error();
				throw new BusinessException(resultMessages.add(CommonMessageConst.ERR_SYS_0097));
			}
			throw ex;
		}
	}

	/**
	 * @param graphicDbDesign
	 * @param sqlDesign
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public SqlDesignXml converFromXMLToObject(GraphicDbDesign graphicDbDesign) throws BusinessException {
		JAXBContext jaxbContext;
		InputStream is = null;
		SqlDesignXml sqlDesign;
		try {
			jaxbContext = JAXBContext.newInstance(SqlDesignXml.class);
			is = new ByteArrayInputStream(graphicDbDesign.getXml().getBytes("UTF8"));

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			System.err.println("---------------------------XML--------------------------------");
			System.err.println(graphicDbDesign.getXml());
			System.err.println("-----------------------------------------------------------");
			// convert xml to object
			sqlDesign = (SqlDesignXml) jaxbUnmarshaller.unmarshal(is);

		} catch (JAXBException e) {
			e.printStackTrace();
			throw new BusinessException(ResultMessages.error().add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0100));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new BusinessException(ResultMessages.error().add(GraphicDatabaseDesignMessageConst.ERR_DATABASEDESIGN_0100));
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new BusinessException(ResultMessages.error().add(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0088));
				}
			}
		}
		return sqlDesign;
	}

	/**
	 * 
	 * @param affect
	 * @param modify
	 */
	private void arrangeTableDesign(List<TableDesign> affect, List<TableDesign> modify) {
		if (CollectionUtils.isEmpty(affect) || CollectionUtils.isEmpty(modify)) {
			return;
		}
		
		for (TableDesign tableDesignModify : modify) {
			for (TableDesign tableDesignAffect : affect) {
				if (tableDesignAffect.getTableDesignId().equals(tableDesignModify.getTableDesignId())) {
					tableDesignModify.setListBusinessLogics(tableDesignAffect.getListBusinessLogics());
					tableDesignModify.setListSqlDesigns(tableDesignAffect.getListSqlDesigns());
				}
			}
		}
	}

	/**
	 * DungNN modify - change delete table and every one reference to TableDesignRepository.xml
	 * 
	 * @param listIdOfTablesToDelete
	 * @author dungnn1
	 */
	private void deleteTableDesign(List<TableDesign> listOfTablesToDelete) {
		try {
			// Delete all table and element of this
			if (CollectionUtils.isNotEmpty(listOfTablesToDelete))
				tableDesignRepository.multiDelete(listOfTablesToDelete);
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * DungNN edit algorithm
	 * 
	 * @author dungnn1
	 */
	public GraphicDbDesign loadGraphicDesign(Long projectId, Long areaId) throws BusinessException {
		/*System.err.println("Start :" + System.currentTimeMillis());*/

		GraphicDbDesign grpDbDesign = new GraphicDbDesign();
		// if subject area was deleted then load other subject
		if (areaId > 0 && subjectAreaRepository.findOneById(areaId) == null) {
			areaId = DomainDatatypeConst.SEARCH_TABLE_DESIGN_NOT_IN_SUBJECT_AREA;
		}
		/*grpDbDesign.setProject( );*/
		grpDbDesign.setSubjectAreaId(areaId);

		// list table by project and subject area
		List<TableDesign> listTableDesign = tableDesignRepository.getTableDesignByProjectAndSubArea(projectId, areaId);
		grpDbDesign.setUpdatedDate(FunctionCommon.getCurrentTime());

		// get all row
		List<TableDesignDetails> listTableDesignDetails = tableDesignDetailRepository.getAllByProjectAndSubArea(projectId, areaId);
		// get foreign key
		List<TableDesignForeignKey> listTableDesignForeignKey = tableDesignForeignKeyRepository.getAllByProjectAndSubArea(projectId, areaId);
		// get key
		List<TableDesignKey> listTableDesignKey = tableDesignKeyRepository.getAllByProjectAndSubArea(projectId, areaId);

		if(areaId > 0){
			// Load X, Y for table in Subject Area
			List<SubjectAreaTableDesign> areaTableDesigns = subjectAreaTableRepository.findBySubjectArea(areaId);
			// Set X, Y for Subject Area
			for (TableDesign tableDesign : listTableDesign) {
				for (SubjectAreaTableDesign subjectAreaTableDesign : areaTableDesigns) {
					if(subjectAreaTableDesign.getTableId().equals(tableDesign.getTableDesignId())){
						tableDesign.setX(subjectAreaTableDesign.getxCoordinates());
						tableDesign.setY(subjectAreaTableDesign.getyCoordinates());
						break;
					}
				}
			}
		}

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
					// resize list
					listTableDesignKey.remove(n);
					n--;
					numOfKey--;
				}
			}
			tableDesign.setTableKey(listOfKey);
		}

		SqlDesignXml sqlDesign = new SqlDesignXml();
		sqlDesign.setListOfTables(listTableDesign);
		/**
		 * Set Datatype from DB
		 *
		 * 2015-05-21
		 * 
		 * @author dungnn1
		 */
		sqlDesign.setListOfDatatypes(getListDatatypeTag(projectId));
		JAXBContext jaxbContext;
		ByteArrayOutputStream ot = new ByteArrayOutputStream();
		try {
			jaxbContext = JAXBContext.newInstance(SqlDesignXml.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, DbDomainConst.CHARACTER_ENCODING );
			jaxbMarshaller.setProperty(CharacterEscapeHandler.class.getName(), new XmlUtils());
			jaxbMarshaller.marshal(sqlDesign, ot);
		} catch (JAXBException e) {
			throw new BusinessException(e.getMessage());
		}

		grpDbDesign.setXml(ot.toString());
		System.err.println(ot.toString()); 
		System.err.println("End :" + System.currentTimeMillis());
		
		AccountProfile accountProfile = systemService.getDefaultProfile();
		nameMask = StringUtils.defaultString(accountProfile.getNameMask(), StringUtils.EMPTY);

		grpDbDesign.setNameMask(nameMask);
		return grpDbDesign;
	}

	private GraphicDbDesign loadGraphicDesignFromScreenDesign(Long projectId, Long moduleId, Long areaId, Long languageId, List<Long> lstScreenDesign) throws BusinessException {
		System.err.println("Start :" + System.currentTimeMillis());

		GraphicDbDesign grpDbDesign = new GraphicDbDesign();
		// if subject area was deleted then load other subject
		if (areaId > 0 && subjectAreaRepository.findOneById(areaId) == null) {
			areaId = DomainDatatypeConst.SEARCH_TABLE_DESIGN_NOT_IN_SUBJECT_AREA;
		}
		/*grpDbDesign.setProjectId(projectId);*/
		grpDbDesign.setSubjectAreaId(areaId);
		grpDbDesign.setModuleId(moduleId);

		List<TableDesign> listTableDesign = new ArrayList<TableDesign>();
		List<ScreenArea> lstScreenArea = screenAreaRepository.getScreenAreaByLstScreenId(lstScreenDesign, languageId, projectId);

		Long currentSreenId = null;
		if (CollectionUtils.isNotEmpty(lstScreenArea)) {
			List<ScreenItem> lstScreenItems = screenItemRepository.getAllScreenItemByLstScreenAreaId(lstScreenArea, languageId, projectId);
			
			if (CollectionUtils.isNotEmpty(lstScreenItems)) {
				Map<String, TableDesign> mapTable = new HashMap<String, TableDesign>();
				List<TableDesignDetails> listTableDesignDetails = new ArrayList<TableDesignDetails>();
				List<String> lstColumnCodeOfTable = new ArrayList<String>();
				
				
				Integer itemSeqNo = 0;
				int top = 50;
				TableDesign table = null;
				boolean addToList = false;
				for (ScreenArea area : lstScreenArea) {

					Set<String> keyTable = mapTable.keySet();
					
					if (keyTable.contains(area.getAreaCode())) {
						
						if (!DataTypeUtils.equals(currentSreenId, area.getScreenId())) {
							currentSreenId = area.getScreenId();
						}

						for (String key : keyTable) {
							if (key.equals(area.getAreaCode())) {
								table = mapTable.get(key);
								break;
							}
						}
						// add
						listTableDesignDetails = table.getDetails();
						lstColumnCodeOfTable = table.getLstColumnCodeOfTable();
						Integer maxItemSeqNo = 0;
						for (TableDesignDetails obj : listTableDesignDetails) {
							if (maxItemSeqNo < obj.getItemSeqNo()) {
								maxItemSeqNo = obj.getItemSeqNo();
							}
						}
						maxItemSeqNo++;
		
						if(!DbDomainConst.ScreenPatternType.SEARCH.equals(area.getScreenPatternType())) {
							for (ScreenItem item : lstScreenItems) {
								if (item.getScreenAreaId().equals(area.getScreenAreaId()) && !lstColumnCodeOfTable.contains(item.getItemCode())) {
									TableDesignDetails detail = populateTableDesignDetail(item, maxItemSeqNo++);
									if (detail == null)
										continue;
									listTableDesignDetails.add(detail);
									lstColumnCodeOfTable.add(detail.getCode());
								}
							}
						} else {
							for (ScreenItem item : lstScreenItems) {
								if (DataTypeUtils.equals(currentSreenId, item.getScreenId()) && !lstColumnCodeOfTable.contains(item.getItemCode())) {
									TableDesignDetails detail = populateTableDesignDetail(item, maxItemSeqNo++);
									if (detail == null)
										continue;
									listTableDesignDetails.add(detail);
									lstColumnCodeOfTable.add(detail.getCode());
								}
							}
						}
						
					} else {
						if(!DbDomainConst.ScreenPatternType.SEARCH.equals(area.getScreenPatternType()) || !DataTypeUtils.equals(currentSreenId, area.getScreenId())) {
							top = top + 50;
							table = new TableDesign();
							table.setTableDesignId(0L);
							table.setTableName(area.getMessageString());
							if (StringUtils.isBlank(table.getTableName())) {
								table.setTableName(area.getAreaCode());
							}
							table.setTableCode(area.getAreaCode());
							table.setX(top);
							table.setY(top);
							table.setProjectId(projectId);
							table.setDesignStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
							table.setType(DbDomainConst.TableDesignType.MASTER);

							currentSreenId = area.getScreenId();
							addToList = false;
							listTableDesignDetails = new ArrayList<TableDesignDetails>();
							lstColumnCodeOfTable = new ArrayList<String>();
							
							itemSeqNo = 0;
						}
						// set item of table
						boolean flgHaveColumn = false;
						
						if(!DbDomainConst.ScreenPatternType.SEARCH.equals(area.getScreenPatternType())) {
							for (ScreenItem item : lstScreenItems) {
								if (item.getScreenAreaId().equals(area.getScreenAreaId()) && !lstColumnCodeOfTable.contains(item.getItemCode())) {
									TableDesignDetails detail = populateTableDesignDetail(item, itemSeqNo++);
									if (detail == null)
										continue;
									listTableDesignDetails.add(detail);
									lstColumnCodeOfTable.add(detail.getCode());
									flgHaveColumn = true;
								}
							}
						} else {
							for (ScreenItem item : lstScreenItems) {
								if (DataTypeUtils.equals(currentSreenId, item.getScreenId()) && !lstColumnCodeOfTable.contains(item.getItemCode())) {
									TableDesignDetails detail = populateTableDesignDetail(item, itemSeqNo++);
									if (detail == null)
										continue;
									listTableDesignDetails.add(detail);
									lstColumnCodeOfTable.add(detail.getCode());
									flgHaveColumn = true;
								}
							}
						}

						if (flgHaveColumn && !addToList) {
							listTableDesign.add(table);
							mapTable.put(table.getTableCode(), table);
							addToList = true;
						}
					}
					
					if (table != null && CollectionUtils.isNotEmpty(listTableDesignDetails)) {
						Collections.sort(listTableDesignDetails);// sort by item_seq_no
						table.setDetails(listTableDesignDetails);
						table.setLstColumnCodeOfTable(lstColumnCodeOfTable);
					}
				}
			}
		}
		SqlDesignXml sqlDesign = new SqlDesignXml();
		sqlDesign.setListOfTables(listTableDesign);
		/**
		 * Set Datatype from DB
		 *
		 * 2015-05-21
		 * 
		 * @author dungnn1
		 */
		sqlDesign.setListOfDatatypes(getListDatatypeTag(projectId));
		JAXBContext jaxbContext;
		ByteArrayOutputStream ot = new ByteArrayOutputStream();
		try {
			jaxbContext = JAXBContext.newInstance(SqlDesignXml.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, DbDomainConst.CHARACTER_ENCODING );
			jaxbMarshaller.setProperty(CharacterEscapeHandler.class.getName(), new XmlUtils());
			jaxbMarshaller.marshal(sqlDesign, ot);
		} catch (JAXBException e) {
			throw new BusinessException(e.getMessage());
		}

		grpDbDesign.setXml(ot.toString());
		/* System.err.println(ot.toString()); */
		System.err.println("End :" + System.currentTimeMillis());
		
		AccountProfile accountProfile = systemService.getDefaultProfile();
		nameMask = StringUtils.defaultString(accountProfile.getNameMask(), StringUtils.EMPTY);
		grpDbDesign.setNameMask(nameMask);
		return grpDbDesign;
	}

	/**
	 * 
	 * @param item
	 * @return
	 */
	private TableDesignDetails populateTableDesignDetail(ScreenItem item, Integer itemSeqNo) {
		if (StringUtils.isBlank(item.getItemCode()))
			return null;
		TableDesignDetails detail = new TableDesignDetails();
		detail.setCode(item.getItemCode());
		if (item.getMessageDesign() != null && StringUtils.isNotEmpty(item.getMessageDesign().getMessageString())) {
			detail.setName(item.getMessageDesign().getMessageString());
		} else {
			detail.setName(item.getItemCode());
		}
		detail.setKeyType(null);
		detail.setGroupBaseTypeId(null);
		detail.setRemark(null);
		detail.setDefaultValue(null);
		detail.setItemSeqNo(itemSeqNo);
		detail.setAutoIncrementFlag(0);
		detail.setDecimalPart(null);
		if (item.getPhysicalDataType() != null) {
			detail.setDataType(item.getPhysicalDataType().longValue());
		} else {
			detail.setDataType(DbDomainConst.PhysicalDataType.TEXT.longValue());
		}
		
		detail.setBaseType(detail.getDataType().intValue());
		
		if (item.getScreenItemValidation() != null) {
			detail.setMaxlength(item.getScreenItemValidation().getMaxlength());
			detail.setIsMandatory(item.getScreenItemValidation().getMandatoryFlg());
			detail.setMinVal(item.getScreenItemValidation().getMinVal());
			detail.setMaxVal(item.getScreenItemValidation().getMaxVal());
		}
		Integer ZERO = 0;
		if (detail.getMaxlength() == null || ZERO.equals(detail.getMaxlength())) {
			detail.setMaxlength(TableDesignUtil.initializeMaxLengthDefault(item.getPhysicalDataType()));
		}
		detail.setConstrainsType(null);
		detail.setDatasourceType(null);
		detail.setDatasourceId(null);
		detail.setDataTypeFlg(null);
		detail.setItemType(item.getLogicalDataType());
		detail.setDisplayType(null);
		detail.setOperatorCode(null);
		detail.setFmtCode(null);
		detail.setDefaultValue(null);
		detail.setColumnId(0L);
		detail.setScreenItemId(item.getScreenItemId());
		return detail;
	}

	/**
	 * 
	 * get base datatype from db for prepare build xml
	 * 
	 * 2015-05-21
	 * 
	 * @return
	 * @author dungnn1
	 */
	private List<DatatypeTag> getListDatatypeTag(Long projectId) {
		List<DatatypeTag> listOfDatatype = new ArrayList<DatatypeTag>();
		// init
		DatatypeTag datatypeTag = new DatatypeTag();
		datatypeTag.setDbType("postgresql");
		// get from DB
		datatypeTag.setListOfGroupDBType(domainDesignService.getAllBasetype(projectId, true));
		//
		listOfDatatype.add(datatypeTag);

		return listOfDatatype;
	}

	/**
	 * loadAffectChangedDesign
	 */
	public GraphicDbDesign loadAffectChangedDesign(GraphicDbDesign graphicDbDesign, Long projectId) {

		List<TableDesignDetails> listTableDesignDetailFromDb = tableDesignDetailRepository.getAllByProjectAndSubArea(projectId, graphicDbDesign.getSubjectAreaId());
		List<TableDesign> tableDesignUpdate = new ArrayList<TableDesign>();
		List<TableDesign> tableDesignDelete = new ArrayList<TableDesign>();
		List<TableDesign> tableDesignBefore = tableDesignRepository.getTableDesignByProjectAndSubArea(projectId, graphicDbDesign.getSubjectAreaId());
		List<TableDesign> tableDesignAfter = this.converFromXMLToObject(graphicDbDesign).getListOfTables();
		System.out.println(graphicDbDesign.getXml());

		// Arrange Table Design Details
		this.arrangeTableDesignDetails(tableDesignBefore, listTableDesignDetailFromDb);

		boolean check = false;

		for (TableDesign before : tableDesignBefore) {
			check = false;
			if (CollectionUtils.isNotEmpty(tableDesignAfter)) {
				for (TableDesign after : tableDesignAfter) {
					if (before.getTableDesignId().equals(after.getTableDesignId())) {
						after.setIsDeleted(false);
						tableDesignUpdate.add(after);
						check = true;
						break;
					}
				}
			}
			if (!check) {
				before.setIsDeleted(true);
				tableDesignDelete.add(before);
			}
		}

		this.validateInputGraphicDbDesign(tableDesignUpdate, graphicDbDesign.getProject(), tableDesignDelete, true, graphicDbDesign.getUpdatedDate());

		if (FunctionCommon.isNotEmpty(tableDesignDelete)) {
			tableDesignService.loadListAffected(tableDesignDelete, DbDomainConst.FromResourceType.GRAPHIC_DATABASE_DESIGN, projectId);
		}
		if (FunctionCommon.isNotEmpty(tableDesignUpdate)) {
			tableDesignService.loadListAffected(tableDesignUpdate, DbDomainConst.FromResourceType.GRAPHIC_DATABASE_DESIGN, projectId);
		}
		List<TableDesign> listTableDesignAffect = new ArrayList<TableDesign>();
		listTableDesignAffect.addAll(tableDesignDelete);
		listTableDesignAffect.addAll(tableDesignUpdate);

		graphicDbDesign.setTableDesigns(listTableDesignAffect);
		
		List<BusinessLogic> listBusinessLogics = new ArrayList<BusinessLogic>();
		List<SqlDesign> listSqlDesigns = new ArrayList<SqlDesign>();
		
		for (TableDesign tableDesign : listTableDesignAffect) {
			if(FunctionCommon.isNotEmpty(tableDesign.getListBusinessLogics())){
				listBusinessLogics.addAll(tableDesign.getListBusinessLogics());
			}
			if(FunctionCommon.isNotEmpty(tableDesign.getListSqlDesigns())){
				listSqlDesigns.addAll(tableDesign.getListSqlDesigns());
			}
		}
		
		graphicDbDesign.setListBusinessLogics(listBusinessLogics);
		graphicDbDesign.setListSqlDesigns(listSqlDesigns);

		return graphicDbDesign;
	}

	/**
	 * 
	 * @param tableDesign
	 * @param listTableDesignDetail
	 */
	private void arrangeTableDesignDetails(List<TableDesign> tableDesign, List<TableDesignDetails> listTableDesignDetail) {

		List<TableDesignDetails> details = null;
		for (TableDesign design : tableDesign) {

			details = new ArrayList<TableDesignDetails>();
			for (TableDesignDetails tableDesignDetails : listTableDesignDetail) {
				if (tableDesignDetails.getTableDesignId().equals(design.getTableDesignId())) {
					details.add(tableDesignDetails);
				}
			}
			design.setListTableDesignDetails(details);
		}
	}

	/**
	 * 
	 * @param tableDesign
	 * @param listTableDesignDetail
	 * @return
	 */
	private List<TableDesign> arrangeTableDetails(GraphicDbDesign graphicDbDesign, List<TableDesign> tableDesign, List<TableDesignDetails> listTableDesignDetail, List<TableDesignDetails> listTableDesignDetailFromDb, Long projectId) {
		List<Basetype> listOfBasetype = domainRepository.getAllBasetype(projectId);

		List<TableDesign> details = new ArrayList<TableDesign>();
		List<TableDesignDetails> listChangeType = null;
		List<TableDesignDetails> tableDetails = null;

		for (TableDesign design : tableDesign) {
			listChangeType = new ArrayList<TableDesignDetails>();
			tableDetails = new ArrayList<TableDesignDetails>();
			this.setBaseTypeForDomainType(listTableDesignDetail, listOfBasetype);
			for (TableDesignDetails tableDesignDetails : listTableDesignDetail) {
				if (tableDesignDetails.getTableDesignId().equals(design.getTableDesignId())) {
					tableDetails.add(tableDesignDetails);
					if (listTableDesignDetailFromDb != null) {
						for (TableDesignDetails tblDetails : listTableDesignDetailFromDb) {
							if ((!tblDetails.getBaseType().equals(tableDesignDetails.getBaseType()) && (tblDetails.getColumnId().equals(tableDesignDetails.getColumnId())))) {
								tableDesignDetails.setOldBaseType(tblDetails.getBaseType());
								listChangeType.add(tableDesignDetails);
							}
						}
					}
				}
			}
			design.setListTableDesignDetailsChangeType(listChangeType);
			design.setListTableDesignDetails(tableDetails);
			details.add(design);
		}
		return details;
	}

	/**
	 * 
	 * @param tableDesign
	 */
	private void setBaseTypeForDomainType(List<TableDesignDetails> tableDesignDetails, List<Basetype> listOfBasetype) {
		for (Basetype basetype : listOfBasetype) {
			for (TableDesignDetails details : tableDesignDetails) {
				if (details.getDataType().equals(basetype.getBasetyeId())) {
					details.setBaseType(basetype.getPrimitiveId());
				}
			}
		}
	}

	/**
	 * check name or code of table existed
	 * 
	 * @param key
	 * @param value
	 * @param map
	 * @return
	 */
	private boolean checkExistMap(String key, Long value, List<String> listKey, List<Long> listValue) {
		if (FunctionCommon.isEmpty(listKey)) {
			return false;
		}

		int numOfKey = listKey.size();
		for (int i=0;i<numOfKey;i++) {
			if (!StringUtils.equalsIgnoreCase(key, listKey.get(i))) {
				continue;
			}

			// if not ID then true
			if (FunctionCommon.isEmpty(value)) {
				return true;
			}
			// else check not same id
			if (!listValue.get(i).equals(value)) {
				return true;
			}
		}
		return false;
	}

	private void generateBlogic(List<BusinessDesign> lstBusinessDesigns, List<TableDesign> listOfSubmittedTables, GraphicDbDesign graphicDbDesign, LanguageDesign languageDesign) {
		if (lstBusinessDesigns.size() > 0) {
			List<InputBean> lstInputBeans = new ArrayList<InputBean>();
			Map<Long, Long> mapBLogicId = new HashMap<Long, Long>();
			Map<Long, Long> mapBLogicIdTemp = new HashMap<Long, Long>();
			Long sequenceBLogic = businessDesignRepository.getSequencesBusinesLogic(lstBusinessDesigns.size() - 1);
			Long startBLogic = sequenceBLogic - (lstBusinessDesigns.size() - 1);
			Map<String, Long> mapInputBeanId = new HashMap<String, Long>();
			for (BusinessDesign obj : lstBusinessDesigns) {
				mapBLogicIdTemp.put(startBLogic, obj.getBusinessLogicId());
				mapBLogicId.put(obj.getBusinessLogicId(), startBLogic);
				obj.setBusinessLogicId(startBLogic);
				startBLogic++;

				if (obj.getLstInputBean() != null && obj.getLstInputBean().size() > 0) {
					for (InputBean in : obj.getLstInputBean()) {
						lstInputBeans.add(in);
					}
				}
			}
			businessDesignRepository.registerLstBusinessLogic(lstBusinessDesigns);
			if (FunctionCommon.isNotEmpty(listOfSubmittedTables)) {
				List<TableDesignDetails> lstTblDesignDetails = new ArrayList<TableDesignDetails>();
				List<MessageDesign> lstMess = new ArrayList<MessageDesign>();
				for (TableDesign table : listOfSubmittedTables) {
					for (TableDesignDetails details : table.getDetails()) {
						lstTblDesignDetails.add(details);
						MessageDesign messageDesign = poplateMessageDesign(details.getName(), graphicDbDesign, 5, MESSAGE_TYPE_SCREEN, languageDesign);
						lstMess.add(messageDesign);
						details.setMessageDesign(messageDesign);
					}
				}
				if (FunctionCommon.isNotEmpty(lstMess)) {
					messageDesignService.registerMessageDesign(lstMess, false);
				}

				if (lstInputBeans.size() > 0) {
					Long sequenceInputBean = businessDesignRepository.getSequencesInputBean(lstInputBeans.size() - 1);
					Long startSequenceInput = sequenceInputBean - (lstInputBeans.size() - 1);

					for (InputBean in : lstInputBeans) {
						mapInputBeanId.put(in.getInputBeanId(), startSequenceInput);
						String parentInputBeanId = mapInputBeanId.get(in.getParentInputBeanId()) != null ? mapInputBeanId.get(in.getParentInputBeanId()).toString() : null;
						in.setBusinessLogicId(mapBLogicId.get(in.getBusinessLogicId()));
						in.setInputBeanId(startSequenceInput.toString());
						in.setParentInputBeanId(parentInputBeanId);
						for (TableDesignDetails details : lstTblDesignDetails) {
							if (in.getScreenItemId() != null && in.getScreenItemId().equals(details.getScreenItemId())) {
								in.setInputBeanCode(details.getCode());
								String messageCode = details.getMessageDesign() != null ? details.getMessageDesign().getMessageCode() : null;
								in.setInputBeanName(messageCode);
								in.setMessageString(messageCode);
							}
						}
						startSequenceInput++;
					}
					businessDesignRepository.registerInputBean(lstInputBeans);
				}
			} else {
				if (lstInputBeans.size() > 0) {
					Long sequenceInputBean = businessDesignRepository.getSequencesInputBean(lstInputBeans.size() - 1);
					Long startSequenceInput = sequenceInputBean - (lstInputBeans.size() - 1);
					for (InputBean in : lstInputBeans) {
						mapInputBeanId.put(in.getInputBeanId(), startSequenceInput);
						in.setBusinessLogicId(mapBLogicId.get(in.getBusinessLogicId()));
						in.setInputBeanId(startSequenceInput.toString());
						if (in.getFlagUsingTempId() != null && in.getFlagUsingTempId()) {
							Long parentMultipartFileObjectId = mapInputBeanId.get(in.getParentInputBeanId());
							if (parentMultipartFileObjectId != null) {
								in.setParentInputBeanId(parentMultipartFileObjectId.toString());
							}
						}
						startSequenceInput++;
					}
					businessDesignRepository.registerInputBean(lstInputBeans);
				}
			}
			// Update object mapping id for area
			for (BusinessDesign businessDesign : lstBusinessDesigns) {
				Set<ScreenArea> setScreenArea = mapAreaUpdateMappingObjectType.get(mapBLogicIdTemp.get(businessDesign.getBusinessLogicId()));
				if (setScreenArea != null) {
					// update object_mapping_type and object_mapping_id of screen area
					for (ScreenArea area : setScreenArea) {
						area.setObjectMappingType(ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_INPUT_BEAN);
						area.setObjectMappingId(mapInputBeanId.get(area.getObjectMappingId().toString()));

						screenAreaRepository.updateObjectMappingTypeOfScreenArea(area);
					}
				}
			}
		}
	}

	/**
	 * populate new message design
	 * 
	 * @return the new MessageDesign object
	 */
	private MessageDesign poplateMessageDesign(String messageString, GraphicDbDesign graphicDbDesign, Integer messageLevel, String messageType, LanguageDesign languageDesign) {
		MessageDesign messageDesign = new MessageDesign();
		messageDesign.setMessageString(messageString);
		messageDesign.setModuleId(graphicDbDesign.getModuleId());
		messageDesign.setProjectId(graphicDbDesign.getProject().getProjectId());
		messageDesign.setMessageLevel(messageLevel);
		messageDesign.setMessageType(messageType);
		messageDesign.setGeneratedStatus(DbDomainConst.MessageGeneratedStatus.AUTO_TRANSLATE);
		messageDesign.setLanguageId(languageDesign.getLanguageId());

		messageDesign.setMessageCode(messageDesign.getAutoMessageCode());
		return messageDesign;
	}

	private List<BusinessDesign> populateBusinessDesignFromScreen(Long moduleId, List<Long> listScreenIds, LanguageDesign languageDesign, Long accountId, Long projectId) {

		List<ScreenDesign> lstAllScreenDesign = screendesignRepository.getAllScreenInfoByModuleId(moduleId, languageDesign.getLanguageId());
		List<Long> lstAllScreenIdOfModule = new ArrayList<Long>();
		List<ScreenItem> lstScreenItem = new ArrayList<ScreenItem>();
		List<ScreenForm> lstScreenForm = new ArrayList<ScreenForm>();
		List<ScreenAction> lstScreenAction = new ArrayList<ScreenAction>();

		List<Long> lstScreenIdGenerate = new ArrayList<Long>();
		if (FunctionCommon.isNotEmpty(lstAllScreenDesign)) {
			for (ScreenDesign screenDesign : lstAllScreenDesign) {
				lstAllScreenIdOfModule.add(screenDesign.getScreenId());
				if (listScreenIds.contains(screenDesign.getScreenId())) {
					lstScreenIdGenerate.add(screenDesign.getScreenId());
				}
			}
		}
		if (FunctionCommon.isNotEmpty(listScreenIds)) {
			lstScreenItem = screenItemRepository.getAllScreenItemByLstScreenId(listScreenIds, languageDesign.getLanguageId());
			lstScreenForm = screenFormRepository.getScreenFormByLstScreenId(listScreenIds);
			lstScreenAction = screenActionRepository.getScreenActionByLstToScreenId(lstAllScreenIdOfModule);
			List<ScreenArea> lstScreenArea = screenAreaRepository.getScreenAreaByLstScreenId(listScreenIds, languageDesign.getLanguageId(), projectId);
			if (lstScreenArea != null && lstScreenForm != null) {
				for (ScreenForm form : lstScreenForm) {
					List<ScreenArea> areas = new ArrayList<ScreenArea>();
					for (ScreenArea area : lstScreenArea) {
						if (form.getScreenFormId().equals(area.getScreenFormId())) {
							areas.add(area);
						}
					}
					form.setAreas(areas);
				}
			}
		}

		List<ScreenDesign> lstScreenDesign = screendesignRepository.getAllScreenByScreenIds(lstScreenIdGenerate, languageDesign.getLanguageId());
		List<BusinessDesign> lstBusinessDesigns = new ArrayList<BusinessDesign>();
		if (lstScreenDesign != null && lstScreenDesign.size() > 0) {
			for (ScreenDesign screen : lstScreenDesign) {
				// register
				if (ScreenDesignConst.ScreenPatternType.REGISTER.equals(screen.getScreenPatternType())) {
					screen.setScreenTypeName(ScreenDesignConst.REGISTER_SCREEN);
				}
				// modify
				if (ScreenDesignConst.ScreenPatternType.MODIFY.equals(screen.getScreenPatternType())) {
					screen.setScreenTypeName(ScreenDesignConst.MODIFY_SCREEN);
				}
				// view
				if (ScreenDesignConst.ScreenPatternType.VIEW.equals(screen.getScreenPatternType())) {
					if (ScreenDesignConst.ConfirmType.SCREEN.equals(screen.getConfirmationType()) && lstScreenAction != null) {
						ScreenAction screenAction = null;
						for (ScreenAction action : lstScreenAction) {
							if (screen.getScreenId().equals(action.getToScreenId())) {
								screenAction = action;
								break;
							}
						}
						if (screenAction != null) {
							for (ScreenDesign objScreen : lstAllScreenDesign) {
								if (screenAction.getFromScreenId().equals(objScreen.getScreenId())) {
									if (ScreenDesignConst.ScreenPatternType.REGISTER.equals(objScreen.getScreenPatternType())) {
										screen.setScreenTypeName(ScreenDesignConst.CONFIRM_REGISTER_SCREEN);
									} else if (ScreenDesignConst.ScreenPatternType.MODIFY.equals(objScreen.getScreenPatternType())) {
										screen.setScreenTypeName(ScreenDesignConst.CONFIRM_MODIFY_SCREEN);
									}
									break;
								}
							}
						}
					} else if (ScreenDesignConst.CompleteType.SCREEN.equals(screen.getCompletionType()) && lstScreenAction != null) {
						ScreenAction screenAction = null;
						for (ScreenAction action : lstScreenAction) {
							if (screen.getScreenId().equals(action.getToScreenId())) {
								screenAction = action;
								break;
							}
						}
						if (screenAction != null) {
							for (ScreenDesign objScreen : lstAllScreenDesign) {
								if (screenAction.getFromScreenId().equals(objScreen.getScreenId())) {
									if (ScreenDesignConst.ScreenPatternType.REGISTER.equals(objScreen.getScreenPatternType())) {
										screen.setScreenTypeName(ScreenDesignConst.COMPLETE_REGISTER_SCREEN);
									} else if (ScreenDesignConst.ScreenPatternType.MODIFY.equals(objScreen.getScreenPatternType())) {
										screen.setScreenTypeName(ScreenDesignConst.COMPLETE_MODIFY_SCREEN);
									} else if (ScreenDesignConst.ScreenPatternType.VIEW.equals(objScreen.getScreenPatternType())) {
										ScreenAction screenActionView = null;
										for (ScreenAction action : lstScreenAction) {
											if (objScreen.getScreenId().equals(action.getToScreenId())) {
												screenActionView = action;
												break;
											}
										}
										if (screenActionView != null) {
											for (ScreenDesign sd : lstAllScreenDesign) {
												if (screenActionView.getFromScreenId().equals(sd.getScreenId())) {
													if (ScreenDesignConst.ScreenPatternType.REGISTER.equals(sd.getScreenPatternType())) {
														screen.setScreenTypeName(ScreenDesignConst.COMPLETE_REGISTER_SCREEN);
													} else if (ScreenDesignConst.ScreenPatternType.MODIFY.equals(sd.getScreenPatternType())) {
														screen.setScreenTypeName(ScreenDesignConst.COMPLETE_MODIFY_SCREEN);
													}
												}
											}
										}
									}
									break;
								}
							}
						}
					} else {
						screen.setScreenTypeName(ScreenDesignConst.VIEW_SCREEN);
					}
				}
				// search
				if (ScreenDesignConst.ScreenPatternType.SEARCH.equals(screen.getScreenPatternType())) {
					screen.setScreenTypeName(ScreenDesignConst.SEARCH_SCREEN);
				}
			}
			Long tempBLogicId = 0l;
			for (ScreenDesign screen : lstScreenDesign) {
				boolean hasConfirm = false;
				if (ScreenDesignConst.ConfirmType.SCREEN.equals(screen.getConfirmationType())) {
					hasConfirm = true;
				}

				for (ScreenForm form : lstScreenForm) {
					List<ScreenItem> lstItems = new ArrayList<ScreenItem>();
					if (form.getScreenId().equals(screen.getScreenId())) {
						for (ScreenItem item : lstScreenItem) {
							if (item.getScreenId().equals(screen.getScreenId()) && form.getScreenFormId().equals(item.getScreenFormId()) && item.getLogicalDataType() != null && !DbDomainConst.LogicDataType.BUTTON.equals(item.getLogicalDataType()) && !DbDomainConst.LogicDataType.LABEL.equals(item.getLogicalDataType())) {
								lstItems.add(item);
							}
						}

						BusinessDesign businessDesign = null;
						switch (screen.getScreenTypeName()) {
						case ScreenDesignConst.REGISTER_SCREEN:
							// display register
							businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.DISPLAY_REGISTER, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0015), DbDomainConst.BlogicReturnType.INITIAL, BusinessDesignConst.SCREEN_PATTERN_REGISTER, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_INITIAL);
							lstBusinessDesigns.add(businessDesign);
							if (hasConfirm) {
								businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.DISPLAY_CONFIRM_REGISTER, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0015), DbDomainConst.BlogicReturnType.INITIAL, BusinessDesignConst.SCREEN_PATTERN_CONFIRM_REGISTER, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_INITIAL);
								lstBusinessDesigns.add(businessDesign);
							} else {
								businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.PROCESS_REGISTER, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0016), DbDomainConst.BlogicReturnType.SCREEN, BusinessDesignConst.SCREEN_PATTERN_REGISTER, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
								lstBusinessDesigns.add(businessDesign);
							}
							break;
						case ScreenDesignConst.CONFIRM_REGISTER_SCREEN:
							// undo register
							businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.UNDO_REGISTER, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0029), DbDomainConst.BlogicReturnType.SCREEN, BusinessDesignConst.SCREEN_PATTERN_CONFIRM_REGISTER, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
							lstBusinessDesigns.add(businessDesign);
							// process confirm register
							businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.PROCESS_CONFIRM_REGISTER, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0017), DbDomainConst.BlogicReturnType.SCREEN, BusinessDesignConst.SCREEN_PATTERN_CONFIRM_REGISTER, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
							lstBusinessDesigns.add(businessDesign);
							break;
						case ScreenDesignConst.COMPLETE_REGISTER_SCREEN:
							businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.DISPLAY_COMPLETE_REGISTER, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0025), DbDomainConst.BlogicReturnType.INITIAL, BusinessDesignConst.SCREEN_PATTERN_COMPLETE_REGISTER, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_INITIAL);
							lstBusinessDesigns.add(businessDesign);
							break;
						case ScreenDesignConst.SEARCH_SCREEN:
							// display search
							businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.DISPLAY_SEARCH, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0021), DbDomainConst.BlogicReturnType.INITIAL, BusinessDesignConst.SCREEN_PATTERN_SEARCH, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_INITIAL);
							lstBusinessDesigns.add(businessDesign);
							// process search
							businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.PROCESS_SEARCH, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0022), DbDomainConst.BlogicReturnType.SCREEN, BusinessDesignConst.SCREEN_PATTERN_SEARCH, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
							lstBusinessDesigns.add(businessDesign);
							break;
						case ScreenDesignConst.VIEW_SCREEN:
							// display view
							businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.DISPLAY_VIEW, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0023), DbDomainConst.BlogicReturnType.INITIAL, BusinessDesignConst.SCREEN_PATTERN_VIEW, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_INITIAL);
							lstBusinessDesigns.add(businessDesign);
							// process delete
							businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.PROCESS_DELETE, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0024), DbDomainConst.BlogicReturnType.SCREEN, BusinessDesignConst.SCREEN_PATTERN_DELETE, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
							lstBusinessDesigns.add(businessDesign);
							break;
						case ScreenDesignConst.MODIFY_SCREEN:
							// display modify
							businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.DISPLAY_MODIFY, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0018), DbDomainConst.BlogicReturnType.INITIAL, BusinessDesignConst.SCREEN_PATTERN_MODIFY, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_INITIAL);
							lstBusinessDesigns.add(businessDesign);
							if (hasConfirm) {
								// display confirm modify
								businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.DISPLAY_CONFIRM_MODIFY, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0028), DbDomainConst.BlogicReturnType.INITIAL, BusinessDesignConst.SCREEN_PATTERN_CONFIRM_MODIFY, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_INITIAL);
								lstBusinessDesigns.add(businessDesign);
							} else {
								// process modify
								businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.PROCESS_MODIFY, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0019), DbDomainConst.BlogicReturnType.SCREEN, BusinessDesignConst.SCREEN_PATTERN_MODIFY, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
								lstBusinessDesigns.add(businessDesign);
							}
							break;
						case ScreenDesignConst.CONFIRM_MODIFY_SCREEN:
							// undo modify
							businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.UNDO_MODIFY, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0030), DbDomainConst.BlogicReturnType.SCREEN, BusinessDesignConst.SCREEN_PATTERN_CONFIRM_MODIFY, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
							lstBusinessDesigns.add(businessDesign);
							// process confirm modify
							businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.PROCESS_CONFIRM_MODIFY, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0020), DbDomainConst.BlogicReturnType.SCREEN, BusinessDesignConst.SCREEN_PATTERN_CONFIRM_MODIFY, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
							lstBusinessDesigns.add(businessDesign);
							break;
						case ScreenDesignConst.COMPLETE_MODIFY_SCREEN:
							// display complete modify
							businessDesign = populateBlogicAndInputBean(screen, form, moduleId, BusinessDesignConst.DISPLAY_COMPLETE_MODIFY, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0026), DbDomainConst.BlogicReturnType.INITIAL, BusinessDesignConst.SCREEN_PATTERN_COMPLETE_MODIFY, lstItems, tempBLogicId++, accountId, projectId, BusinessDesignConst.REQUEST_METHOD_INITIAL);
							lstBusinessDesigns.add(businessDesign);
							break;
						default:
							break;
						}
					}
				}
			}
		}
		return lstBusinessDesigns;
	}

	private int updateDesignMode(List<Long> listOfId, Integer designStatus) {
		return screendesignRepository.updateDesignMode(listOfId, designStatus);
	}

	/**
	 * 
	 * @param screen
	 * @param moduleId
	 * @param blogicCode
	 * @param returnType
	 * @param patternType
	 * @param lstScreenItem
	 * @return
	 */
	private BusinessDesign populateBlogicAndInputBean(ScreenDesign screen, ScreenForm form, Long moduleId, String blogicCode, String blogicName, Integer returnType, Integer patternType, List<ScreenItem> lstScreenItem, Long tempBLogicId, Long accountId, Long projectId, Integer requestMethod) {
		BusinessDesign blogic = new BusinessDesign();
		Set<ScreenArea> areaUpdateMappingObjectType = new HashSet<ScreenArea>();
		GenerateUniqueKey generateUniqueKey = new GenerateUniqueKey(DbDomainConst.MAX_LENGTH_OF_NAME, DbDomainConst.MAX_LENGTH_OF_CODE);
		/* String blogicNameAndCode = blogicCode + StringUtils.capitalize(screen.getScreenCode()) + StringUtils.capitalize(form.getFormCode()); */
		String messageString = screen.getMessageDesign() == null ? null : screen.getMessageDesign().getMessageString();
		String blogicCodeInsert = generateUniqueKey.generateAutoCode(blogicCode, StringUtils.capitalize(screen.getScreenCode()) + StringUtils.capitalize(form.getFormCode()));
		String blogicNameInsert = generateUniqueKey.generateAutoName(blogicName, " " + messageString + " " + StringUtils.capitalize(form.getFormCode()));

		blogic.setBusinessLogicName(blogicNameInsert);
		blogic.setBusinessLogicCode(blogicCodeInsert);
		blogic.setRequestMethod(requestMethod);
		// check duplicated
		/*
		 * DungNN remove using auto generate Long totalCount = businessDesignRepository.countNameCodeExist(blogic); if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) { Long sequenceBLogic = businessDesignRepository.getSequencesBusinesLogic(0); blogic.setBusinessLogicName(blogicNameAndCode + sequenceBLogic); } else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) { Long sequenceBLogic = businessDesignRepository.getSequencesBusinesLogic(0); blogic.setBusinessLogicCode(blogicNameAndCode + sequenceBLogic); } else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) { Long sequenceBLogic = businessDesignRepository.getSequencesBusinesLogic(0); blogic.setBusinessLogicName(blogicNameAndCode + sequenceBLogic); blogic.setBusinessLogicCode(blogicNameAndCode + sequenceBLogic); }
		 */
		blogic.setBusinessLogicId(tempBLogicId);
		blogic.setReturnType(returnType);
		blogic.setScreenId(screen.getScreenId());
		if (screen.getMessageDesign() != null) {
			blogic.setScreenIdAutocomplete(screen.getMessageDesign().getMessageString());
		}
		blogic.setModuleId(moduleId);
		blogic.setProjectId(projectId);
		blogic.setDesignStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
		blogic.setPatternType(patternType);
		if (ScreenDesignConst.ConfirmType.SCREEN.equals(screen.getConfirmationType())) {
			blogic.setConfirmFlg(true);
		} else {
			blogic.setConfirmFlg(false);
		}
		if (ScreenDesignConst.CompleteType.SCREEN.equals(screen.getCompletionType())) {
			blogic.setCompleteFlg(true);
		} else {
			blogic.setCompleteFlg(false);
		}
		blogic.setRemark(BusinessDesignConst.REMARK);
		blogic.setFunctionDesignId(screen.getFunctionDesignId());
		blogic.setCreatedBy(accountId);
		blogic.setCreatedDate(FunctionCommon.getCurrentTime());
		blogic.setUpdatedBy(accountId);
		blogic.setUpdatedDate(FunctionCommon.getCurrentTime());
		List<InputBean> lstInputBeans = new ArrayList<InputBean>();
		if (BusinessDesignConst.RETURN_TYPE_SCREEN.equals(blogic.getReturnType())) {
			Integer itemSeqNo = 0;
			Map<Long, List<ScreenItem>> mapAreaAndItem = new HashMap<Long, List<ScreenItem>>();
			List<ScreenArea> screenAreaInputs = new ArrayList<ScreenArea>();
			for (int areaIndex = 0; areaIndex < form.getAreas().size(); areaIndex++) {
				ScreenArea area = form.getAreas().get(areaIndex);
				screenAreaInputs.add(area);
			}
	
			for (ScreenArea area : screenAreaInputs) {
				if (form.getScreenFormId().equals(area.getScreenFormId())) {
					List<ScreenItem> lstItemOfThisArea = new ArrayList<ScreenItem>();
					for (ScreenItem item : lstScreenItem) {
						if (area.getScreenAreaId().equals(item.getScreenAreaId())) {
							if (item.getScreenArea() == null) {
								item.setScreenArea(area);
							}
							lstItemOfThisArea.add(item);
						}
					}
					mapAreaAndItem.put(area.getScreenAreaId(), lstItemOfThisArea);
				}
			}
	
			Integer tempInputBeanId = 0, tempParentObjectId = 0, tempParentMultilepartFileId = 0;
			ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionRepository.findExtObjDefIsMultipartFileByProjectId(projectId);
			List<ExternalObjectAttribute> lstExternalObjectAttribute = new ArrayList<ExternalObjectAttribute>();
			if (externalObjectDefinition != null) {
				lstExternalObjectAttribute = externalObjectAttributeRepository.findExternalObjectAttributeByExternalObjectDefinitionId(externalObjectDefinition.getExternalObjectDefinitionId());
			}
			Set<String> hashSetAreaCode = new HashSet<String>();
			Map<String, InputBean> mapAreaCode = new HashMap<String, InputBean>();
	
			for (ScreenArea area : screenAreaInputs) {
				InputBean parentInputBean = null;
				if (form.getScreenFormId().equals(area.getScreenFormId())) {
					List<ScreenItem> lstScreenItemOfThisArea = mapAreaAndItem.get(area.getScreenAreaId()) != null ? mapAreaAndItem.get(area.getScreenAreaId()) : new ArrayList<ScreenItem>();
					for (ScreenItem item : lstScreenItemOfThisArea) {
						if (item.getLogicalDataType() == null || DbDomainConst.LogicDataType.LABEL.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.BUTTON.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.LINK.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.LINK_DYNAMIC.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.CUSTOM_ITEM.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.CUSTOM_SECTION.equals(item.getLogicalDataType()) || (DbDomainConst.LogicDataType.LABEL_DYNAMIC.equals(item.getLogicalDataType()) && !ScreenDesignConst.ScreenItemConst.ITEM_TYPE_HIDDEN.equals(item.getItemType()))) {
							continue;
						}
						ScreenArea screenArea = item.getScreenArea();
						if (screenArea != null) {
							if (parentInputBean == null) {
								// Adding a object parent input bean
								if (!hashSetAreaCode.contains(screenArea.getAreaCode())) {
									parentInputBean = new InputBean();
									parentInputBean.setInputBeanId(tempInputBeanId.toString());
									parentInputBean.setInputBeanCode(screenArea.getAreaCode());
									parentInputBean.setInputBeanName(screenArea.getMessageDesign() == null ? "" : StringUtils.isNotEmpty(screenArea.getMessageDesign().getMessageCode()) ? screenArea.getMessageDesign().getMessageCode() : "");
									parentInputBean.setInputBeanType(BusinessDesignConst.InputBeanType.DEFAULT);
									parentInputBean.setDataType(BusinessDesignConst.DataType.OBJECT);
									if (ScreenDesignConst.AreaType.LIST.equals(screenArea.getAreaType())) {
										parentInputBean.setArrayFlg(Boolean.TRUE);
									} else {
										parentInputBean.setArrayFlg(Boolean.FALSE);
									}
									parentInputBean.setObjectFlg(Boolean.TRUE);
									parentInputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
									// add to list
									lstInputBeans.add(parentInputBean);
									hashSetAreaCode.add(screenArea.getAreaCode());
									mapAreaCode.put(screenArea.getAreaCode(), parentInputBean);
									tempParentObjectId = tempInputBeanId;
									tempInputBeanId++;
								} else {
									parentInputBean = mapAreaCode.get(screenArea.getAreaCode());
									if (parentInputBean != null) {
										tempParentObjectId = Integer.parseInt(parentInputBean.getInputBeanId());
									}
								}
	
								// set object_mapping_id of screen area
								screenArea.setObjectMappingId(Long.parseLong(parentInputBean.getInputBeanId()));
								areaUpdateMappingObjectType.add(screenArea);
							}
	
							// Adding by HungHX - Special case : if add screen item is File Upload .
							if (DbDomainConst.LogicDataType.FILEUPLOAD.equals(item.getLogicalDataType()) && externalObjectDefinition != null) {
								if (CollectionUtils.isNotEmpty(lstExternalObjectAttribute)) {
									// Assign object parent form object definition
									InputBean inputBean = new InputBean();
									inputBean.setInputBeanId(tempInputBeanId.toString());
									inputBean.setInputBeanCode(item.getItemCode());
									inputBean.setInputBeanName(ScreenDesignConst.MULTIPART_FILE);
									inputBean.setScreenItemId(item.getScreenItemId());
									inputBean.setScreenItem(item);
									inputBean.setInputBeanType(BusinessDesignConst.InputBeanType.DEFAULT);
									inputBean.setDataType(BusinessDesignConst.DataType.EXTERNAL_OBJECT);
									inputBean.setArrayFlg(Boolean.FALSE);
									inputBean.setParentInputBeanId(tempParentObjectId.toString());
									inputBean.setObjectType(DbDomainConst.ObjectType.EXTERNAL_OBJECT);
									inputBean.setObjectId(externalObjectDefinition.getExternalObjectDefinitionId());
									inputBean.setObjectFlg(Boolean.TRUE);
									inputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
									inputBean.setFlagUsingTempId(true);
									// add to list
									lstInputBeans.add(inputBean);
									tempParentMultilepartFileId = tempInputBeanId;
									tempInputBeanId++;
	
									// Assign object parent form object attribute
									for (ExternalObjectAttribute extObjAttrIter : lstExternalObjectAttribute) {
										inputBean = new InputBean();
										inputBean.setInputBeanId(tempInputBeanId.toString());
										inputBean.setInputBeanCode(extObjAttrIter.getExternalObjectAttributeCode());
										inputBean.setInputBeanName(extObjAttrIter.getExternalObjectAttributeName());
										inputBean.setInputBeanType(BusinessDesignConst.InputBeanType.ADDED_DEFAULT);
										inputBean.setDataType(extObjAttrIter.getDataType());
										inputBean.setArrayFlg(extObjAttrIter.getArrayFlg());
										inputBean.setParentInputBeanId(tempParentMultilepartFileId.toString());
										inputBean.setObjectFlg(Boolean.FALSE);
										inputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
										inputBean.setFlagUsingTempId(true);
										// add to list
										lstInputBeans.add(inputBean);
										tempInputBeanId++;
									}
								}
								// End adding: Normal case
							} else {
								if (!DbDomainConst.ScreenPatternType.SEARCH.equals(screen.getScreenPatternType())) {
									InputBean inputBean = new InputBean();
									inputBean.setInputBeanCode(item.getItemCode());
									if (item.getMessageDesign() != null) {
										inputBean.setInputBeanName(item.getMessageDesign().getMessageCode());
									} else {
										inputBean.setInputBeanName("");
									}
									if (item.getPhysicalDataType() != null) {
										inputBean.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
									}
									inputBean.setScreenItemId(item.getScreenItemId());
									inputBean.setScreenItem(item);
									inputBean.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
									inputBean.setParentInputBeanId(tempParentObjectId.toString());
									inputBean.setFlagUsingTempId(true);
									if (DbDomainConst.LogicDataType.CHECKBOX.equals(item.getLogicalDataType()) && inputBean.getDataType() != null && inputBean.getDataType().intValue() != DbDomainConst.BaseType.BOOLEAN_BASETYPE) {
										inputBean.setArrayFlg(Boolean.TRUE);
									}
									lstInputBeans.add(inputBean);
									if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
										inputBean = new InputBean();
										inputBean.setInputBeanCode(item.getItemCode() + AUTOCOMPLETE);
										if (item.getMessageDesign() != null) {
											inputBean.setInputBeanName(item.getMessageDesign().getMessageCode());
										} else {
											inputBean.setInputBeanName("");
										}
										inputBean.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
										inputBean.setScreenItemId(item.getScreenItemId());
										inputBean.setScreenItem(item);
										inputBean.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
										inputBean.setParentInputBeanId(tempParentObjectId.toString());
										inputBean.setFlagUsingTempId(true);
										lstInputBeans.add(inputBean);
									}
								} else {
									if (ScreenDesignConst.FromTo.FROM_TO.equals(item.getDisplayFromTo()) && (DbDomainConst.LogicDataType.CURRENCY.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DECIMAL.equals(item.getLogicalDataType()) 
											|| DbDomainConst.LogicDataType.INTEGER.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DATETIME.equals(item.getLogicalDataType()) 
											|| DbDomainConst.LogicDataType.DATE.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.TIME.equals(item.getLogicalDataType()))) {
										InputBean inputBeanFrom = new InputBean();
										inputBeanFrom.setInputBeanCode(item.getItemCode() + "From");
										if (item.getMessageDesign() != null) {
											inputBeanFrom.setInputBeanName(item.getMessageDesign().getMessageCode());
										} else {
											inputBeanFrom.setInputBeanName("");
										}
										if (item.getPhysicalDataType() != null) {
											inputBeanFrom.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
										}
										inputBeanFrom.setScreenItemId(item.getScreenItemId());
										inputBeanFrom.setScreenItem(item);
										inputBeanFrom.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
										inputBeanFrom.setParentInputBeanId(tempParentObjectId.toString());
										inputBeanFrom.setFlagUsingTempId(true);
										lstInputBeans.add(inputBeanFrom);
	
										InputBean inputBeanTo = new InputBean();
										inputBeanTo.setInputBeanCode(item.getItemCode() + "To");
										if (item.getMessageDesign() != null) {
											inputBeanTo.setInputBeanName(item.getMessageDesign().getMessageCode());
										} else {
											inputBeanTo.setInputBeanName("");
										}
										if (item.getPhysicalDataType() != null) {
											inputBeanTo.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
										}
										inputBeanTo.setScreenItemId(item.getScreenItemId());
										inputBeanTo.setScreenItem(item);
										inputBeanTo.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
										inputBeanTo.setParentInputBeanId(tempParentObjectId.toString());
										inputBeanTo.setFlagUsingTempId(true);
										lstInputBeans.add(inputBeanTo);
									} else {
										InputBean inputBean = new InputBean();
										inputBean.setInputBeanCode(item.getItemCode());
										if (item.getMessageDesign() != null) {
											inputBean.setInputBeanName(item.getMessageDesign().getMessageCode());
										} else {
											inputBean.setInputBeanName("");
										}
										if (item.getPhysicalDataType() != null) {
											inputBean.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
										}
										inputBean.setScreenItemId(item.getScreenItemId());
										inputBean.setScreenItem(item);
										inputBean.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
										inputBean.setParentInputBeanId(tempParentObjectId.toString());
										inputBean.setFlagUsingTempId(true);
										if (DbDomainConst.LogicDataType.CHECKBOX.equals(item.getLogicalDataType()) && inputBean.getDataType() != null && inputBean.getDataType().intValue() != DbDomainConst.BaseType.BOOLEAN_BASETYPE) {
											inputBean.setArrayFlg(Boolean.TRUE);
										}
										lstInputBeans.add(inputBean);
										
										if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
											inputBean = new InputBean();
											inputBean.setInputBeanCode(item.getItemCode() + AUTOCOMPLETE);
											if (item.getMessageDesign() != null) {
												inputBean.setInputBeanName(item.getMessageDesign().getMessageCode());
											} else {
												inputBean.setInputBeanName("");
											}
											inputBean.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
											inputBean.setScreenItemId(item.getScreenItemId());
											inputBean.setScreenItem(item);
											inputBean.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
											inputBean.setParentInputBeanId(tempParentObjectId.toString());
											inputBean.setFlagUsingTempId(true);
											lstInputBeans.add(inputBean);
										}
									}
								}
							}
						}
					}
				}
				
				if (FunctionCommon.isNotEmpty(lstInputBeans)) {
					for (InputBean in : lstInputBeans) {
						in.setBusinessLogicId(tempBLogicId);
						in.setItemSequenceNo(itemSeqNo++);
					}
				}
			}
			}
		mapAreaUpdateMappingObjectType.put(blogic.getBusinessLogicId(), areaUpdateMappingObjectType);
		blogic.setScreenFormId(form.getScreenFormId());
		blogic.setLstInputBean(lstInputBeans);
		blogic.setBlogicType(BusinessDesignConst.BLOGIC_TYPE_STANDARD);
		blogic.setDesignMode(BusinessDesignConst.DESIGN_MODE_AUTO);
		return blogic;
	}

	@Override
	public GraphicDbDesign displayConfirmGenerateDbAndBlogic(Long accountId, Long projectId, Long moduleId, Long subjectAreaId, List<Long> listScreenIds, LanguageDesign languageDesign) {
		// make graphic design table
		GraphicDbDesign grpDbDesign = loadGraphicDesignFromScreenDesign(projectId, moduleId, subjectAreaId, languageDesign.getLanguageId(), listScreenIds);
		// make business design
		List<BusinessDesign> lstBusinessDesigns = populateBusinessDesignFromScreen(moduleId, listScreenIds, languageDesign, accountId, projectId);
		grpDbDesign.setLstBusinessDesigns(lstBusinessDesigns);
		
		return grpDbDesign;
	}

	@Override
	public void generateBlogicFromScreen(Long moduleId, List<Long> lstScreenIds, LanguageDesign languageDesign, Long accountId, Long projectId) {
		// gen blogic
		List<BusinessDesign> lstBusinessDesigns = populateBusinessDesignFromScreen(moduleId, lstScreenIds, languageDesign, accountId, projectId);
		generateBlogic(lstBusinessDesigns, null, null, languageDesign);
		// update design mode
		updateDesignMode(lstScreenIds, DbDomainConst.DesignMode.BUSINESS);
	}
	
	/**
	 * 
	 * @param listIdOfTablesToDelete
	 */
	private void updateForeignKeyWhenDeleteTables(List<TableDesign> listIdOfTablesToDelete, List<TableDesignDetails> listTableDesignDetailFromDb){
		List<Long> listColumns = new ArrayList<Long>();
		for (TableDesign tableDesign : listIdOfTablesToDelete) {
			for (TableDesignDetails designDetails : listTableDesignDetailFromDb) {
				if(tableDesign.getTableDesignId().equals(designDetails.getTableDesignId()))
				listColumns.add(designDetails.getColumnId());
			}
		}
		if (CollectionUtils.isNotEmpty(listColumns)) {
			tableDesignService.updateForeignKeyWhenDeleteColumns(listColumns);
		}
	}
	
	private boolean isDataTypeEqual(List<TableDesignDetails> getAllInformationByTableDesign, TableDesignForeignKey foreignKey){
		
		for (TableDesignDetails tableDesignDetails : getAllInformationByTableDesign) {
			if(foreignKey.getToColumnId().equals(tableDesignDetails.getColumnId()) && DataTypeUtils.equals(tableDesignDetails.getGroupBaseTypeId(), foreignKey.getForeignKeyType())){
				return true;
			}
		}
		return false;
	}
	
	private void setIdForForeignKey(List<TableDesign> listOfSubmittedTables, List<TableDesignForeignKey> submittedForeignKeys){
		
		for (TableDesign submittedTableDesign : listOfSubmittedTables) {
			for (TableDesignDetails submittedTableDetails : submittedTableDesign.getDetails()) {
				for (TableDesignForeignKey tableDesignForeignKey : submittedForeignKeys) {
					
					if(tableDesignForeignKey.getToColumnCode() !=null && tableDesignForeignKey.getToColumnCode().equals(submittedTableDetails.getCode())){
						tableDesignForeignKey.setToColumnId(submittedTableDetails.getColumnId());
					}
					
					if(tableDesignForeignKey.getToTableCode() !=null && tableDesignForeignKey.getToTableCode().equals(submittedTableDesign.getTableCode())){
						tableDesignForeignKey.setToTableName(submittedTableDesign.getTableName());
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param listOfSubmittedTables
	 * @return
	 */
	private List<TableDesignDetails> findTableDetailsByColumnId(List<TableDesign> listOfSubmittedTables){
		List<TableDesignDetails> tableDesignDetails = new ArrayList<TableDesignDetails>();
		for (TableDesign submittedTableDesign : listOfSubmittedTables) {
			for (TableDesignDetails submittedTableDetails : submittedTableDesign.getDetails()) {
				tableDesignDetails.add(submittedTableDetails);
			}
		}
		return tableDesignDetails;
	}
	
	/**
	 * 
	 * @param tableDesigns
	 * @param subArea
	 */
	private void setCoordinatesForSubArea(List<TableDesign> tableDesigns, Long subAreaId){
		if(subAreaId != null && subAreaId > 0){
			List<SubjectAreaTableDesign> areaTableDesigns = subjectAreaTableRepository.findBySubjectArea(subAreaId);
			// Set X, Y for Subject Area
			if (CollectionUtils.isNotEmpty(areaTableDesigns)) {
				for (TableDesign tableDesign : tableDesigns) {
					for (SubjectAreaTableDesign subjectAreaTableDesign : areaTableDesigns) {
						if(subjectAreaTableDesign.getTableId().equals(tableDesign.getTableDesignId())){
							subjectAreaTableDesign.setxCoordinates(tableDesign.getX());
							subjectAreaTableDesign.setyCoordinates(tableDesign.getY());
							break;
						}
					}
				}
				// Update X, Y for Subject Area
				subjectAreaTableRepository.updateSubjectAreaDesignTable(areaTableDesigns);
			}
		}
	}
}
