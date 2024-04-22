package org.terasoluna.qp.domain.service.generateddl;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.codelist.ReloadableCodeList;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.CodeList;
import org.terasoluna.qp.domain.model.CodeListDetail;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.Message;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ProjectTheme;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.TableDesignForeignKey;
import org.terasoluna.qp.domain.model.TableDesignKey;
import org.terasoluna.qp.domain.repository.codelist.CodeListDetailRepository;
import org.terasoluna.qp.domain.repository.codelist.CodeListRepository;
import org.terasoluna.qp.domain.repository.languagedesign.LanguageDesignRepository;
import org.terasoluna.qp.domain.repository.message.MessageRepository;
import org.terasoluna.qp.domain.repository.messagedesign.MessageDesignRepository;
import org.terasoluna.qp.domain.repository.projecttheme.ProjectThemeRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignForeignKeyRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignKeyRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.service.autocomplete.SqlBuilderService;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeUtil;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeUtil;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.TableDesignUtil;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignService;

/**
 * Generate DDL
 * 
 * @author VyBQ
 */
@Service
@Transactional
public class GenerateDDLServiceImpl implements GenerateDDLService {

	@Inject
	ProjectService projectService;

	@Inject
	TableDesignRepository tableDesignRepository;

	@Inject
	TableDesignDetailRepository tableDesignDetailRepository;

	@Inject
	TableDesignForeignKeyRepository tableDesignForeignKeyRepository;

	@Inject
	TableDesignKeyRepository tableDesignKeyRepository;

	@Inject
	MessageDesignRepository messageDesignRepository;

	@Inject
	MessageRepository messageRepository;

	@Inject
	LanguageDesignRepository languageDesignRepository;

	@Inject
	ScreenDesignRepository screenDesignRepository;

	@Inject
	SqlDesignRepository sqlDesignRepository;

	@Inject
	SqlBuilderService sqlBuilderService;

	@Inject
	SqlDesignService sqlDesignService;
	
	@Inject
	CodeListRepository codeListRepository;
	
	@Inject
	CodeListDetailRepository codeListDetailRepository;
	
	@Inject
	SystemService systemService;
	
	@Inject
	ProjectThemeRepository projectThemeRepository;
	
	@Inject
	@Named("CL_LANGUAGE_LIST")
	private ReloadableCodeList languageList;
	
	protected int maxLengthOfSeqName;
	/*protected String dateFormat;
	protected String dateTimeFormat;
	protected String timeFormat;*/

	private static final String APOSTROPHE = "'";
	private static final String DOUBLE_APOSTROPHE = "''";
	
	private static final String COMMENT_COLUMN = "COMMENT ON COLUMN {0}.{1} IS ";
	private static final String COMMENT_TABLE = "COMMENT ON TABLE {0} IS ";
	private static final String INSERT_MESSAGE_TEMPLATE = "INSERT INTO qp_message(message_code, message_string, language_code, country_code, module_resource) VALUES ({0}, {1}, {2}, {3}, {4});";
	private static final String INSERT_LANGUAGE_TEMPLATE = "INSERT INTO qp_language(language_code, language_name, country_code, region_code) VALUES ({0}, {1}, {2}, {3});";
	private static final String INSERT_PERMISSION_TEMPLATE = "INSERT INTO qp_permission(permission_name, permission_code, sort_key, remark, action_path, created_by, created_date, updated_by, updated_date, module_code) VALUES ({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9});";
	private static final String INSERT_ROLE_PERMISSION_TEMPLATE = "INSERT INTO qp_role_permission(role_id, permission_id) SELECT 1, permission_id FROM qp_permission ;";
	private static final String INSERT_RESOURCE = "INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES({0}, {1}, {2},'''','''','''','''','''',1,1);";
	private static final String INSERT_ACCOUNT_PROFILE = "INSERT INTO qp_account_profile (account_id,integer_format,float_format,currency_format,currency_code,currency_code_position,date_format,datetime_format,time_format,default_language,session_timeout,proxy_host,proxy_port,proxy_user,proxy_password,bing_client_id,bing_client_secret,interval_reload)VALUES ({0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13},{14},{15},{16},{17});";
	private static final String INSERT_ACCOUNT_THEME = "INSERT INTO qp_account_theme (account_theme_id, account_id, code, value_, created_by, created_date, updated_by, updated_date) VALUES ({0}, 1, {1}, {2}, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);";

	private static final String SET_DEFINE_OFF = "SET DEFINE OFF";

	private static final String SET_DEFINE_ON = "SET DEFINE ON";
	
	private static final String PREFIX_SEQ = "seq_";

	private static final String ALL_TABLE_DESIGN = "0";
	
	protected String ALTER_FK_TEMPLATE;
	protected String CREATE_SEQ_TEMPLATE;
	protected String ALTER_PK_TEMPLATE;
	protected String ALTER_UNIQUE_TEMPLATE;
	protected String ALTER_INDEX_TEMPLATE;
	protected String CREATE_TABLE_COL_PK_DEFAULT_TEMPLATE;
	protected String ALTER_SEQ_TO_TABLE_TEMPLATE;
	protected String CREATE_TABLE_TEMPLATE;
	protected String DROP_SEQ_TEMPLATE;
	protected String DROP_TABLE_TEMPLATE;
	
	protected GenerateUniqueKey generateUniqueKey;

	private HashMap<String, String> mapSeq = new HashMap<>();
	
	public GenerateDDLServiceImpl() {
		initMapSeq();
	}
	
	/**
	 * Generate SQL for project
	 * 
	 * @param project
	 *			Project information
	 * @return String sql query
	 */
	@Override
	public StringBuilder generateSQL(Project project, LanguageDesign languageDesign, String generateMode, List<Long> listTableId, boolean isGenDrop, CommonModel commonModel) {
		initData();

		AccountProfile accountProfile = systemService.getDefaultProfile();
		maxLengthOfSeqName = accountProfile.getSqlCodeMaxLengthByDbType(String.valueOf(project.getDbType()));
		
		if (DataTypeUtils.equals(DbDomainConst.DatabaseType.ORACLE, project.getDbType())) {
			generateUniqueKey = new GenerateUniqueKey(maxLengthOfSeqName - 4 , maxLengthOfSeqName - 4);//for tri_
			/*dateFormat = accountProfile.getDateFormat();
			dateTimeFormat = accountProfile.getDateTimeFormat();
			timeFormat = accountProfile.getTimeFormat();*/
			
			//cov
			
		} else {
			generateUniqueKey = new GenerateUniqueKey(maxLengthOfSeqName, maxLengthOfSeqName);
		}
		
		
		List<TableDesign> tableDesignQP = new ArrayList<TableDesign>();
		List<TableDesign> listTablesDb = null;
		List<TableDesignDetails> listColumns = null;
		List<TableDesignKey> listKeys = null;
		List<TableDesignForeignKey> listForeignKeysDb = null;
		List<ScreenDesign> lstScreenDesign = null;
		List<LanguageDesign> lstLanguageDesigns = null;
		List<MessageDesign> lstMessageDesign = null;
		List<Message> listOfMessageDefault = null;
		List<String[]> allSystemCodelists = null;
		List<Module> listModules = null;
		List<Message> listOfMessageCurrent = null;
		List<Message> listOfMessageByLocale = null;
		String locale = null;
		
		if(generateMode.equals(ALL_TABLE_DESIGN)){
		
			listTablesDb = tableDesignRepository.getTableDesignByProjectAndSubArea(project.getProjectId(), null);
			
			listColumns = tableDesignDetailRepository.getAllByProjectAndSubArea(project.getProjectId(), DomainDatatypeConst.SEARCH_ALL_TABLE_DESIGN);
	
			listKeys = tableDesignKeyRepository.getAllByProjectAndSubArea(project.getProjectId(), DomainDatatypeConst.SEARCH_ALL_TABLE_DESIGN);
	
			listForeignKeysDb = tableDesignForeignKeyRepository.getFromForeignKeyInfo(project.getProjectId());
		} else{
			
			listTablesDb = tableDesignRepository.getTableDesignByTableId(listTableId);
			
			listColumns = tableDesignDetailRepository.getAllByTableId(listTableId);
			
			listKeys = tableDesignKeyRepository.getAllByTableId(listTableId);
			
			listForeignKeysDb = tableDesignForeignKeyRepository.getFromForeignKeyInfoByTableId(listTableId);
		}

		List<TableDesign> listTables = new ArrayList<TableDesign>();
		
		List<TableDesignDetails> listColumnsTable;
		
		if(CollectionUtils.isNotEmpty(listTablesDb)) {
			for (TableDesign table : listTablesDb) {
				if (DbDomainConst.TableDesignType.QP_TABLE.equals(table.getType())) {
					tableDesignQP.add(table);
				}else{
					listTables.add(table);
				}
			}
		}
		
		if(this.hasContentTable("qp_permission", tableDesignQP)){
			lstScreenDesign = screenDesignRepository.getAllScreenOfProjectId(project.getProjectId());
		}

		boolean genQpLanguage = false;
		if(this.hasContentTable("qp_language", tableDesignQP)){
			lstLanguageDesigns = languageDesignRepository.findLanguageByProjectId(project.getProjectId());
			//KhanhTH
			allSystemCodelists = getAllSystemCodelist(project, lstLanguageDesigns);
			//KhanhTH
			listModules = projectService.findAllModuleOfProject(project.getProjectId());
			genQpLanguage = true;
		}

		if(this.hasContentTable("qp_message", tableDesignQP)){
			locale = languageDesign.getLanguageCode() + "_" + languageDesign.getCountryCode();
			Map<String, String> clLanguage = languageList.asMap();
			if (!clLanguage.containsKey(locale)) {
				locale = "en_US";
			}
			//locale = "en_US";
			listOfMessageDefault = messageRepository.getDefaultForExportProject(project.getProjectId(), locale);
			lstMessageDesign = messageDesignRepository.getAllMessageDesignByProjectId(project.getProjectId());
			
			if(!genQpLanguage){
				lstLanguageDesigns = languageDesignRepository.findLanguageByProjectId(project.getProjectId());
				allSystemCodelists = getAllSystemCodelist(project, lstLanguageDesigns);
				listModules = projectService.findAllModuleOfProject(project.getProjectId());
				/*lstLanguageDesigns = null;*/
			}
		}

		InputStream is = null;
		StringBuilder sqlFinal = new StringBuilder();
		
		// prepare data for table
		for (TableDesign table : listTables) {
			listColumnsTable = new ArrayList<TableDesignDetails>();
			// set list columns to corresponding table
			for (TableDesignDetails detail : listColumns) {
				if (table.getTableDesignId().equals(detail.getTableDesignId())) {
					detail.setCode(detail.getCode().toLowerCase());
					buildSeqCode(detail);
					listColumnsTable.add(detail);
				}
			}
			// set column name to corresponding keys
			// set list keys to corresponding tables
			for (TableDesignKey key : listKeys) {
				if (table.getTableDesignId().equals(key.getTableDesignId())) {
					List<String> listKeyColumns = DomainDatatypeUtil.convertStringToArrayList(key.getStrKeyItems(), DomainDatatypeUtil.STR_REGEX);
					for (String colKey : listKeyColumns) {
						key.getKeyItems().add(colKey.toLowerCase());
					}
					key.setCode(key.getCode().toLowerCase());
					table.getTableKey().add(key);
				}
			}
			table.setTableCode(table.getTableCode().toLowerCase());
			Collections.sort(listColumnsTable);
			table.setDetails(listColumnsTable);
		}
		
		try {
			if (DbDomainConst.DatabaseType.PostgreSQL.equals(project.getDbType())) {
				// generate common function
				is = GenerateDDLService.class.getResourceAsStream("/META-INF/templatescript/functionCommonPostgre.sql");
				sqlFinal.append(IOUtils.toString(is, DbDomainConst.CHARACTER_ENCODING));

				// generate drop seq & table sql
				if (isGenDrop) {
					for (TableDesign table : listTables) {
						//buildSeqCode(table);
						
						String sqlSeq = buildSQLDropSeq(table, false);
						sqlFinal.append(sqlSeq);

						sqlFinal.append(MessageFormat.format(DROP_TABLE_TEMPLATE, table.getTableCode()));
						sqlFinal.append(StringUtils.LF);
					}
						if(generateMode.equals(ALL_TABLE_DESIGN)){
							is = GenerateDDLService.class.getResourceAsStream("/META-INF/templatescript/scriptDropTableWithPostgre.sql");
							sqlFinal.append(IOUtils.toString(is, DbDomainConst.CHARACTER_ENCODING));
						}else{
							sqlFinal.append(this.dropDDLQPTable(tableDesignQP, project.getDbType()));
						}
				}
			// Gen Oracle
			} else {
				// generate common function
				is = GenerateDDLService.class.getResourceAsStream("/META-INF/templatescript/functionCommonOracle.sql");
				sqlFinal.append(IOUtils.toString(is, DbDomainConst.CHARACTER_ENCODING));

				// generate drop seq & table sql
				if (isGenDrop) {
					sqlFinal.append(StringUtils.LF);
					sqlFinal.append("declare");
					sqlFinal.append(StringUtils.LF);
					sqlFinal.append("\tbegin\n");
	
					for (TableDesign table : listTables) {
						//buildSeqCode(table);
						
						String sqlSeq = buildSQLDropSeq(table, false);
						sqlFinal.append("\t");
						sqlFinal.append(sqlSeq);
	
						sqlFinal.append(MessageFormat.format(DROP_TABLE_TEMPLATE, table.getTableCode()));
						sqlFinal.append(StringUtils.LF);
					}

					if(generateMode.equals(ALL_TABLE_DESIGN)){
						is = GenerateDDLService.class.getResourceAsStream("/META-INF/templatescript/scriptDropTableWithOracle.sql");
						sqlFinal.append(IOUtils.toString(is, DbDomainConst.CHARACTER_ENCODING));
					}else{
						sqlFinal.append(this.dropDDLQPTable(tableDesignQP, project.getDbType()));
					}

					sqlFinal.append(StringUtils.LF);
					sqlFinal.append("end;");
					sqlFinal.append(StringUtils.LF);
					sqlFinal.append("/");
					sqlFinal.append(StringUtils.LF);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			IOUtils.closeQuietly(is);
		}
		
		if(CollectionUtils.isNotEmpty(listTables)) {
			// generate create database;
			// sqlFinal.append(MessageFormat.format(POSTGRE_CREATE_DATABASE_TEMPLATE,
			// project.getProjectCode()));
			// sqlFinal.append(StringUtils.LF);
			
			// generate create seq sql
			for (TableDesign table : listTables) {
				String sqlSeq = buildSQLCreateSeq(table);
				
				if (null != sqlSeq && sqlSeq.length() != 0) {
					sqlFinal.append(sqlSeq);
					sqlFinal.append(StringUtils.LF);
				}
			}
	
			// generate create table
			for (TableDesign table : listTables) {
				sqlFinal.append(buildSQLCreateTable(table));
				sqlFinal.append(StringUtils.LF);
			}
	
			// generate comment of table
			for (TableDesign table : listTables) {
				String sqlSeq = this.buildSQLCreateCommentTable(table);
				if (null != sqlSeq && sqlSeq.length() != 0) {
					sqlFinal.append(sqlSeq);
					sqlFinal.append(StringUtils.LF);
				}
			}
	
			// generate comment for each column of table
			for (TableDesign table : listTables) {
				String sqlSeq = this.buildSQLCreateCommentColumn(table);
				if (null != sqlSeq && sqlSeq.length() != 0) {
					sqlFinal.append(sqlSeq);
					sqlFinal.append(StringUtils.LF);
				}
			}
	
			// generate alter pk sql
			for (TableDesign tbl : listTables) {
				String sqlSeq = buildSQLCreateKeys(tbl);
				if (null != sqlSeq && sqlSeq.length() != 0) {
					sqlFinal.append(sqlSeq);
					sqlFinal.append(StringUtils.LF);
				}
			}
			
			
		}
		// generate create table language and table message

		try {
			if (DbDomainConst.DatabaseType.PostgreSQL.equals(project.getDbType())) {
				if(generateMode.equals(ALL_TABLE_DESIGN)){
					is = GenerateDDLService.class.getResourceAsStream("/META-INF/templatescript/scriptInsertTableWithPostgre.sql");
					sqlFinal.append(IOUtils.toString(is, DbDomainConst.CHARACTER_ENCODING));
				}else{
					sqlFinal.append(this.createDDLQPTable(tableDesignQP, project.getDbType()));
				}
			} else {
				if(generateMode.equals(ALL_TABLE_DESIGN)){
					is = GenerateDDLService.class.getResourceAsStream("/META-INF/templatescript/scriptInsertTableWithOracle.sql");
					sqlFinal.append(IOUtils.toString(is, DbDomainConst.CHARACTER_ENCODING));
				}else{
					sqlFinal.append(this.createDDLQPTable(tableDesignQP, project.getDbType()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			IOUtils.closeQuietly(is);
		}

		if(generateMode.equals(ALL_TABLE_DESIGN)){
			List<SqlDesign> viewDesignList = sqlDesignRepository.getAllViewDesignByProjectId(project.getProjectId());
			/*sqlBuilderService.initData(this.workingProjectId, this.workingLanguageId, this.accountId);*/
			for (SqlDesign sqlDesign : viewDesignList) {
				sqlDesign.setSqlText(sqlBuilderService.buildSql(sqlDesignService.findCompoundById(sqlDesign.getSqlDesignId()),String.valueOf(project.getDbType()), commonModel));
				sqlFinal.append(StringUtils.appendIfMissing(sqlDesign.getSqlText(), ";"));
			}
		}
		
		//generate FK
		if (CollectionUtils.isNotEmpty(listForeignKeysDb))  {
		List<TableDesignForeignKey> listForeignKeys = new ArrayList<TableDesignForeignKey>(); 
		for (TableDesignForeignKey fk : listForeignKeysDb) {
			for (TableDesign tbl : listTables) {
				if (StringUtils.equalsIgnoreCase(fk.getFromTableCode(), tbl.getTableCode())
						&& StringUtils.equalsIgnoreCase(fk.getFromTableCode(), tbl.getTableCode())) {
					listForeignKeys.add(fk);
					break;
				}
			}
		}
		
		// generate alter fk sql
		if (CollectionUtils.isNotEmpty(listForeignKeys)) 
			sqlFinal.append(buildSQLCreateFK(listForeignKeys));
		}
		
		String projectName = APOSTROPHE + prepareString(project.getProjectName()) + APOSTROPHE;
		String moduleHome = APOSTROPHE + CommonMessageConst.TQP_HOMEPAGE + APOSTROPHE;
		String messageCode = null, messageString = null, languageCode = null, countryCode = null, moduleCode = null;

		if (DbDomainConst.DatabaseType.ORACLE.equals(project.getDbType())) {
			sqlFinal.append(StringUtils.LF);
			sqlFinal.append(SET_DEFINE_OFF);
			sqlFinal.append(StringUtils.LF);
		}
		
		// generate insert table language
		if (CollectionUtils.isNotEmpty(lstLanguageDesigns)) {
			String languageName = null, regionCode = null;
			for (LanguageDesign language : lstLanguageDesigns) {
				if (StringUtils.isNotBlank(language.getLanguageCode())) {
					languageCode = APOSTROPHE + language.getLanguageCode() + APOSTROPHE;
				}
				if (StringUtils.isNotBlank(language.getLanguageName())) {
					languageName = APOSTROPHE + prepareString(language.getLanguageName()) + APOSTROPHE;
				}
				if (StringUtils.isNotBlank(language.getCountryCode())) {
					countryCode = APOSTROPHE + language.getCountryCode() + APOSTROPHE;
				}
				if (StringUtils.isNotBlank(language.getRegionCode())) {
					regionCode = APOSTROPHE + language.getRegionCode() + APOSTROPHE;
				} else {
					regionCode = DOUBLE_APOSTROPHE;
				}
				
				if (genQpLanguage) {
					sqlFinal.append(StringUtils.LF);
					sqlFinal.append(MessageFormat.format(INSERT_LANGUAGE_TEMPLATE, languageCode, languageName, countryCode, regionCode));
				}
				
				sqlFinal.append(StringUtils.LF);
				sqlFinal.append(MessageFormat.format(INSERT_MESSAGE_TEMPLATE, "'sc.homepage.0000'", projectName, languageCode, countryCode, moduleHome));
				sqlFinal.append(StringUtils.LF);

				if (CollectionUtils.isNotEmpty(listModules)) {
					for (Module md : listModules) {
						sqlFinal.append(MessageFormat.format(INSERT_MESSAGE_TEMPLATE, 
							GenerateSourceCodeUtil.normalizedURL(APOSTROPHE + project.getProjectCode() + DbDomainConst.CHARACTER_DOT + md.getModuleCode() + APOSTROPHE),
							APOSTROPHE + prepareString(md.getModuleName()) + APOSTROPHE, languageCode, countryCode, "'tqp.sys'"));
						sqlFinal.append(StringUtils.LF);
					}
				}
				
				locale = language.getLanguageCode()  + "_" + language.getCountryCode();
				listOfMessageByLocale = messageRepository.getDefaultForExportProject(project.getProjectId(), locale);
				listOfMessageCurrent = (listOfMessageByLocale == null || listOfMessageByLocale.isEmpty()) ? listOfMessageDefault: listOfMessageByLocale;
				// generate qp message
				if (FunctionCommon.isNotEmpty(listOfMessageCurrent)) {
					for (Message mess : listOfMessageCurrent) {
						messageCode = null;
						messageString = APOSTROPHE + StringUtils.EMPTY + APOSTROPHE;
						moduleCode = null;

						if (StringUtils.isNotBlank(mess.getMessageCode())) {
							messageCode = APOSTROPHE + mess.getMessageCode() + APOSTROPHE;
						}
						if (StringUtils.isNotBlank(mess.getMessageString())) {
							messageString = APOSTROPHE + prepareString(mess.getMessageString()) + APOSTROPHE;
						}

						if (StringUtils.isNotBlank(mess.getModuleResource())) {
							moduleCode = APOSTROPHE + mess.getModuleResource() + APOSTROPHE;
						}
						
						/*sqlFinal.append(StringUtils.LF);*/
						sqlFinal.append(MessageFormat.format(INSERT_MESSAGE_TEMPLATE, messageCode, messageString, languageCode, countryCode, moduleCode));
						sqlFinal.append(StringUtils.LF);
					}
				}
			}
		}

		// init default data
		sqlFinal.append(StringUtils.LF);
		try {
			if (DbDomainConst.DatabaseType.PostgreSQL.equals(project.getDbType())) {
				if(generateMode.equals(ALL_TABLE_DESIGN)){
					is = GenerateDDLService.class.getResourceAsStream("/META-INF/templatescript/scriptInitDataWithPostgre.sql");
					sqlFinal.append(IOUtils.toString(is, DbDomainConst.CHARACTER_ENCODING));
				}else{
					sqlFinal.append(this.createInitDataQPTable(tableDesignQP, project.getDbType()));
				}
			} else {
				if(generateMode.equals(ALL_TABLE_DESIGN)){
					is = GenerateDDLService.class.getResourceAsStream("/META-INF/templatescript/scriptInitDataWithOracle.sql");
					sqlFinal.append(IOUtils.toString(is, DbDomainConst.CHARACTER_ENCODING));
				}else{
					sqlFinal.append(this.createInitDataQPTable(tableDesignQP, project.getDbType()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			IOUtils.closeQuietly(is);
		}
		if(this.hasContentTable("qp_resources", tableDesignQP)){
			// QuyND: Init data format and default language for user setting
			/*sqlFinal.append(StringUtils.LF);*/
			sqlFinal.append(MessageFormat.format(INSERT_RESOURCE, "\'systemSettings\'", "\'defaultLanguage\'",
					APOSTROPHE + languageDesign.getLanguageCode() + "_" + languageDesign.getCountryCode() + APOSTROPHE));
			sqlFinal.append(StringUtils.LF);
		}
		
		String integerFormat = "#,###"; 
		String floatFormat = "#,###.###";
		String dateFormat = "1";
		String dateTimeFormat = "1";
		String timeFormat = "1";
		String currencyCode = "$";
		String currencyFormat = "#,###.###";
		String currencyCodePosition = "p";
		if(project.getDataFormat() != null){
			integerFormat = project.getDataFormat().getIntegerFormat();
			floatFormat = project.getDataFormat().getDecimalFormat();
			dateFormat = project.getDataFormat().getDateFormat();
			dateTimeFormat = project.getDataFormat().getDateTimeFormat();
			timeFormat = project.getDataFormat().getTimeFormat();
			currencyCode = project.getDataFormat().getCurrencyCode();
			currencyFormat = project.getDataFormat().getCurrencyFormat();
			currencyCodePosition = project.getDataFormat().getCurrencyCodePosition();
		}
		
		if(this.hasContentTable("qp_resources", tableDesignQP)){
			sqlFinal.append(MessageFormat.format(INSERT_RESOURCE, "\'systemSettings\'", "\'integerFormat\'",APOSTROPHE + integerFormat + APOSTROPHE));
			sqlFinal.append(StringUtils.LF);
			sqlFinal.append(MessageFormat.format(INSERT_RESOURCE, "\'systemSettings\'", "\'floatFormat\'", APOSTROPHE + floatFormat +APOSTROPHE));
			sqlFinal.append(StringUtils.LF);
			sqlFinal.append(MessageFormat.format(INSERT_RESOURCE, "\'systemSettings\'", "\'dateFormat\'", APOSTROPHE + dateFormat + APOSTROPHE));
			sqlFinal.append(StringUtils.LF);
			sqlFinal.append(MessageFormat.format(INSERT_RESOURCE, "\'systemSettings\'", "\'dateTimeFormat\'", APOSTROPHE + dateTimeFormat + APOSTROPHE));
			sqlFinal.append(StringUtils.LF);
			sqlFinal.append(MessageFormat.format(INSERT_RESOURCE, "\'systemSettings\'", "\'timeFormat\'", APOSTROPHE + timeFormat + APOSTROPHE));
			sqlFinal.append(StringUtils.LF);
			sqlFinal.append(MessageFormat.format(INSERT_RESOURCE, "\'systemSettings\'", "\'currencyCode\'", APOSTROPHE + currencyCode + APOSTROPHE));
			sqlFinal.append(StringUtils.LF);
			sqlFinal.append(MessageFormat.format(INSERT_RESOURCE, "\'systemSettings\'", "\'currencyFormat\'", APOSTROPHE+ currencyFormat + APOSTROPHE));
			sqlFinal.append(StringUtils.LF);
			sqlFinal.append(MessageFormat.format(INSERT_RESOURCE, "\'systemSettings\'", "\'currencyCodePosition\'", APOSTROPHE + currencyCodePosition + APOSTROPHE));
			sqlFinal.append(StringUtils.LF);
		}
		if(this.hasContentTable("qp_account_profile", tableDesignQP)){
			// QuyND: init admin account profile
			/*sqlFinal.append(StringUtils.LF);*/
			sqlFinal.append(MessageFormat.format(INSERT_ACCOUNT_PROFILE, "1", 
					APOSTROPHE + integerFormat + APOSTROPHE, APOSTROPHE + floatFormat + APOSTROPHE,
					APOSTROPHE + currencyFormat + APOSTROPHE,APOSTROPHE + currencyCode + APOSTROPHE,
					APOSTROPHE + currencyCodePosition + APOSTROPHE, APOSTROPHE + dateFormat + APOSTROPHE, APOSTROPHE + dateTimeFormat + APOSTROPHE,
					APOSTROPHE + timeFormat + APOSTROPHE, APOSTROPHE + languageDesign.getLanguageCode() + "_" + languageDesign.getCountryCode() + APOSTROPHE,
					"\'30\'", "\'127.0.0.1\'", "\'8080\'", "\' \'", "\' \'","\'terasolunaqp\'","\'4rWibW6YWNmDP5DSWvg3e0DFMUBR6kL3mTKcI+DpcQE=\'", "\'60\'"));
			sqlFinal.append(StringUtils.LF);
		}
		// NinhNV: Insert content table account_theme
		if(this.hasContentTable("qp_account_theme", tableDesignQP)){
			List<ProjectTheme> projectThemes = projectThemeRepository.getThemeSetting(project.getProjectId());
			if (projectThemes != null) {
				for (int i = 0; i < projectThemes.size(); i++) {
					ProjectTheme projectTheme = projectThemes.get(i);
					sqlFinal.append(MessageFormat.format(INSERT_ACCOUNT_THEME, i, 
							APOSTROPHE + projectTheme.getCode() + APOSTROPHE, APOSTROPHE + projectTheme.getValue() + APOSTROPHE));
					sqlFinal.append(StringUtils.LF);
				}
			}
		}
		
		// generate insert table message
		if (FunctionCommon.isNotEmpty(lstMessageDesign)) {

			for (MessageDesign mess : lstMessageDesign) {
				messageCode = null;
				messageString = APOSTROPHE + StringUtils.EMPTY + APOSTROPHE;
				languageCode = null;
				countryCode = null;
				moduleCode = null;

				if (StringUtils.isNotBlank(mess.getMessageCode())) {
					messageCode = APOSTROPHE + mess.getMessageCode() + APOSTROPHE;
				}
				if (StringUtils.isNotBlank(mess.getMessageString())) {
					messageString = APOSTROPHE + prepareString(mess.getMessageString()) + APOSTROPHE;
				}
				if (StringUtils.isNotBlank(mess.getLanguageCode())) {
					languageCode = APOSTROPHE + mess.getLanguageCode() + APOSTROPHE;
				}
				if (StringUtils.isNotBlank(mess.getCountryCode())) {
					countryCode = APOSTROPHE + mess.getCountryCode() + APOSTROPHE;
				}
				if (StringUtils.isNotBlank(mess.getModuleCode())) {
					moduleCode = APOSTROPHE + mess.getModuleCode() + APOSTROPHE;
				} else {
					moduleCode = APOSTROPHE + CommonMessageConst.TQP_SYS + APOSTROPHE;
				}
				/*sqlFinal.append(StringUtils.LF);*/
				sqlFinal.append(MessageFormat.format(INSERT_MESSAGE_TEMPLATE, messageCode, messageString, languageCode, countryCode, moduleCode));
				sqlFinal.append(StringUtils.LF);
			}
		}
		
		// KhanhTH: Generate all system codelist
		if(FunctionCommon.isNotEmpty(allSystemCodelists)) {
			for(String[] codelist : allSystemCodelists) {
				messageCode = null;
				messageString = APOSTROPHE + StringUtils.EMPTY + APOSTROPHE;
				languageCode = null;
				countryCode = null;
				moduleCode = null;

				if (StringUtils.isNotBlank(codelist[0])) {
					messageCode = APOSTROPHE + codelist[0] + APOSTROPHE;
				}
				if (StringUtils.isNotBlank(codelist[1])) {
					messageString = APOSTROPHE + prepareString(codelist[1]) + APOSTROPHE;
				}
				if (StringUtils.isNotBlank(codelist[2])) {
					languageCode = APOSTROPHE + codelist[2] + APOSTROPHE;
				}
				if (StringUtils.isNotBlank(codelist[3])) {
					countryCode = APOSTROPHE + codelist[3] + APOSTROPHE;
				}
				if (StringUtils.isNotBlank(codelist[4])) {
					moduleCode = APOSTROPHE + codelist[4] + APOSTROPHE;
				} else {
					moduleCode = APOSTROPHE + CommonMessageConst.TQP_SYS + APOSTROPHE;
				}
				/*sqlFinal.append(StringUtils.LF);*/
				sqlFinal.append(MessageFormat.format(INSERT_MESSAGE_TEMPLATE, messageCode, messageString, languageCode, countryCode, moduleCode));
				sqlFinal.append(StringUtils.LF);
			}
		}

		// generate insert table permission
		if (FunctionCommon.isNotEmpty(lstScreenDesign)) {
			String perName = null, perCode = null, remark = null, actionPath = null;
			moduleCode = null;
			String currentTime = "CURRENT_TIMESTAMP";
			Long createBy = 1L, sortKey = 1L;
			String currentModuleCode = StringUtils.EMPTY;
			for (ScreenDesign screen : lstScreenDesign) {

				currentModuleCode = screen.getModuleCode();
				remark = null;
				perCode = null;
				actionPath = null;

				if (screen.getMessageDesign() != null && StringUtils.isNoneBlank(screen.getMessageDesign().getMessageCode())) {
					perName = APOSTROPHE + screen.getMessageDesign().getMessageCode() + APOSTROPHE;
				} else {
					perName = APOSTROPHE + screen.getScreenCode() + APOSTROPHE;
				}

				if (StringUtils.isNotBlank(screen.getScreenCode())) {
					perCode = GenerateSourceCodeUtil.normalizedURL(APOSTROPHE + currentModuleCode + screen.getScreenCode() + APOSTROPHE);
				}

				if (StringUtils.isNotBlank(screen.getRemark())) {
					remark = APOSTROPHE + prepareString(screen.getRemark()) + APOSTROPHE;
				}

				if (StringUtils.isNotBlank(screen.getScreenUrlCode())) {
					actionPath = GenerateSourceCodeUtil.normalizedURL(APOSTROPHE + currentModuleCode + "/" + screen.getScreenUrlCode() + APOSTROPHE);
				}

				moduleCode = APOSTROPHE + currentModuleCode + APOSTROPHE;

				/*sqlFinal.append(StringUtils.LF);*/
				sqlFinal.append(MessageFormat.format(INSERT_PERMISSION_TEMPLATE, perName, perCode, sortKey, remark, actionPath, 
						createBy, currentTime, createBy, currentTime, moduleCode));
				sqlFinal.append(StringUtils.LF);
			}
		}

		// insert permission for menuUser

		if(this.hasContentTable("qp_role_permission", tableDesignQP)){
			// generate insert table role_permission
			/*sqlFinal.append(StringUtils.LF);*/
			sqlFinal.append(INSERT_ROLE_PERMISSION_TEMPLATE);
			sqlFinal.append(StringUtils.LF);
		}

		if (DbDomainConst.DatabaseType.ORACLE.equals(project.getDbType())) {
			sqlFinal.append(SET_DEFINE_ON);
			sqlFinal.append(StringUtils.LF);
			sqlFinal.append("commit;");
		}

		return sqlFinal;
	}
	
	/**
	 * 
	 * @param dataType
	 * @return
	 */
	@Override
	public StringBuilder generateSQLForLogging(Integer dataType){
		StringBuilder stringBuilder = new StringBuilder();
		try {
			String path = "";
			if(DbDomainConst.DatabaseType.PostgreSQL.equals(dataType)){
				path += "/META-INF/templatescript/logbackPostgre.sql";
			}else{
				path += "/META-INF/templatescript/logbackOracle.sql";
			}
			InputStream is = null;
			is = GenerateDDLService.class.getResourceAsStream(path);
			stringBuilder.append(IOUtils.toString(is, DbDomainConst.CHARACTER_ENCODING));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringBuilder;
	}
	
	/**
	 * 
	 * @param listQPTable
	 * @return
	 */
	private String createDDLQPTable(List<TableDesign> listQPTable, Integer dataType){
		String path = "";
		if(DbDomainConst.DatabaseType.PostgreSQL.equals(dataType)){
			path += "/META-INF/templatescript/scriptInsertTableWithPostgre_{0}.sql";
		}else{
			path += "/META-INF/templatescript/scriptInsertTableWithOracle_{0}.sql";
		}
		StringBuilder stringBuilder = new StringBuilder();
		InputStream is = null;
		for (TableDesign tableDesign : listQPTable) {
			String pathFull = MessageFormat.format(path, tableDesign.getTableCode());
			is = GenerateDDLService.class.getResourceAsStream(pathFull);
			try {
				if(is != null){
					stringBuilder.append(IOUtils.toString(is, DbDomainConst.CHARACTER_ENCODING));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 
	 * @param listQPTable
	 * @return
	 */
	private String dropDDLQPTable(List<TableDesign> listQPTable, Integer dataType){
		StringBuilder stringBuilder = new StringBuilder();
		for (TableDesign table : listQPTable) {
			//buildSeqCode(table);
			
			String sqlSeq = buildSQLDropSeq(table, true);
			stringBuilder.append(sqlSeq);
			
			stringBuilder.append(MessageFormat.format(DROP_TABLE_TEMPLATE, table.getTableCode()));
			stringBuilder.append(StringUtils.LF);
		}
		return stringBuilder.toString();
	}

	/**
	 * 
	 * @param listQPTable
	 * @return
	 */
	private String createInitDataQPTable(List<TableDesign> listQPTable, Integer dataType){
		String path = "";
		if(DbDomainConst.DatabaseType.PostgreSQL.equals(dataType)){
			path += "/META-INF/templatescript/scriptInitDataWithPostgre_{0}.sql";
		}else{
			path += "/META-INF/templatescript/scriptInitDataWithOracle_{0}.sql";
		}
		StringBuilder stringBuilder = new StringBuilder();
		InputStream is = null;
		for (TableDesign tableDesign : listQPTable) {
			String pathFull = MessageFormat.format(path, tableDesign.getTableCode());
			is = GenerateDDLService.class.getResourceAsStream(pathFull);
			try {
				if(is != null){
					stringBuilder.append(IOUtils.toString(is, DbDomainConst.CHARACTER_ENCODING));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 
	 * @param tableCode
	 * @param listTable
	 * @return
	 */
	private boolean hasContentTable(String tableCode, List<TableDesign> listTable){
		
		for (TableDesign tableDesign : listTable) {
			if(tableCode.equals(tableDesign.getTableCode())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Build sql query for creating foreign keys
	 * 
	 * @param List
	 *			<TableDesignForeignKey> list foreign keys
	 * @return String sql query
	 */
	protected String buildSQLCreateFK(List<TableDesignForeignKey> listForeignKeys) {
		StringBuilder sql = new StringBuilder();
		for (TableDesignForeignKey key : listForeignKeys) {
			
			if (StringUtils.isNoneBlank(key.getForeignKeyCode())
					&& StringUtils.isNoneBlank(key.getFromColumnCode())
					&& StringUtils.isNoneBlank(key.getToColumnCode())) {
				sql.append(StringUtils.LF);
				sql.append(MessageFormat.format(ALTER_FK_TEMPLATE, key.getFromTableCode().toLowerCase(), 
						generateUniqueKey.calculateCodeForManual(key.getForeignKeyCode().toLowerCase()), 
						key.getFromColumnCode().toLowerCase(), key.getToTableCode().toLowerCase(), key.getToColumnCode().toLowerCase()));
				sql.append(StringUtils.LF);
			}
		}
		return sql.toString();
		
	}

	/**
	 * Build sql query for creating sequences
	 * 
	 * @param TableDesign
	 *			table information
	 * @return String sql query
	 */
	protected String buildSQLCreateSeq(TableDesign table) {
		StringBuilder sql = new StringBuilder();
		for (TableDesignDetails column : table.getDetails()) {
			if (1 == column.getAutoIncrementFlag()) {
				sql.append(StringUtils.LF);
				sql.append(MessageFormat.format(CREATE_SEQ_TEMPLATE, column.getSeqCode()));
				sql.append(StringUtils.LF);
				column.setDefaultValue(StringUtils.EMPTY);
			} else if (1 == column.isPrimaryKey() && TableDesignUtil.checkSpecialBasetype(column.getBaseType()) && StringUtils.isNoneBlank(column.getDefaultValue())) {
				sql.append(StringUtils.LF);
				sql.append(MessageFormat.format(CREATE_SEQ_TEMPLATE, column.getSeqCode()));
				sql.append(StringUtils.LF);
			} else {
				column.setSeqCode(StringUtils.EMPTY);
			}
		}
		return sql.toString();
	}
	
	private String buildSQLDropSeq(TableDesign table, boolean isQPTable) {
		StringBuilder sql = new StringBuilder();
		
		if (isQPTable) {
			String seq = mapSeq.get(table.getTableCode());
			
			if (StringUtils.isNotEmpty(seq)) {
				sql.append(StringUtils.LF);
				sql.append(MessageFormat.format(DROP_SEQ_TEMPLATE, seq));
				sql.append(StringUtils.LF);
			};
		} else {
			for (TableDesignDetails column : table.getDetails()) {
				if (1 == column.getAutoIncrementFlag()
						|| 1 == column.isPrimaryKey() && TableDesignUtil.checkSpecialBasetype(column.getBaseType()) && StringUtils.isNoneBlank(column.getDefaultValue())) {
					sql.append(StringUtils.LF);
					sql.append(MessageFormat.format(DROP_SEQ_TEMPLATE, column.getSeqCode()));
					sql.append(StringUtils.LF);
				}
			}
		}
		
		return sql.toString();
	}
	
	private void buildSeqCode(TableDesignDetails column){
		/*for (TableDesignDetails column : table.getDetails()) {*/
			if (StringUtils.isBlank(column.getSeqCode())) {
				column.setSeqCode(PREFIX_SEQ + GenerateUniqueKey.generateRandomInteger());
			} else {
				column.setSeqCode(generateUniqueKey.calculateCodeForManual(column.getSeqCode()));
			}
		/*}*/
	}

	/**
	 * Build sql query for creating keys
	 * 
	 * @param TableDesign
	 *			table information
	 * @return String sql query
	 */
	protected String buildSQLCreateKeys(TableDesign table) {
		StringBuilder sql = new StringBuilder(StringUtils.EMPTY);
		for (TableDesignKey key : table.getTableKey()) {
			switch (key.getType()) {
			case 1:
				sql.append(MessageFormat.format(ALTER_PK_TEMPLATE, table.getTableCode(), key.getCode(), 
						key.getKeyItems().toString().replace("[", StringUtils.EMPTY).replace("]", StringUtils.EMPTY)));
				sql.append(StringUtils.LF);
				break;
			case 4:
				sql.append(MessageFormat.format(ALTER_UNIQUE_TEMPLATE, table.getTableCode(), key.getCode(), 
						key.getKeyItems().toString().replace("[", StringUtils.EMPTY).replace("]", StringUtils.EMPTY)));
				sql.append(StringUtils.LF);
				break;
			case 8:
				sql.append(MessageFormat.format(ALTER_INDEX_TEMPLATE, key.getCode(), table.getTableCode(), 
						key.getKeyItems().toString().replace("[", StringUtils.EMPTY).replace("]", StringUtils.EMPTY)));
				sql.append(StringUtils.LF);
				break;
			}
		}
		return sql.toString();
	}

	/**
	 * Create comment for column.
	 * 
	 * @param TableDesign
	 *			table information
	 * @return String sql query
	 */
	private String buildSQLCreateCommentColumn(TableDesign table) {
		StringBuilder sql = new StringBuilder(StringUtils.EMPTY);

		for (TableDesignDetails tableDesignDetails : table.getDetails()) {
			if (null != tableDesignDetails.getRemark() && tableDesignDetails.getRemark().length() != 0) {
				sql.append(MessageFormat.format(COMMENT_COLUMN, table.getTableCode(), tableDesignDetails.getCode()));
				sql.append("'");
				sql.append(tableDesignDetails.getRemark());
				sql.append("';");
				sql.append(StringUtils.LF);
			}
		}
		return sql.toString();
	}

	/**
	 * Create comment for column.
	 * 
	 * @param TableDesign
	 *			table information
	 * @return String sql query
	 */
	private String buildSQLCreateCommentTable(TableDesign table) {
		StringBuilder sql = new StringBuilder(StringUtils.EMPTY);
		if (null != table.getRemark() && table.getRemark().length() != 0) {
			sql.append(MessageFormat.format(COMMENT_TABLE, table.getTableCode()));
			sql.append("'");
			sql.append(table.getRemark());
			sql.append("';");
			sql.append(StringUtils.LF);
		}
		return sql.toString();
	}

	/**
	 * Build sql query for creating tables
	 * 
	 * @param TableDesign
	 *			table information
	 * @return String sql query
	 */
	protected String buildSQLCreateTable(TableDesign table) {
		StringBuilder sql = new StringBuilder();
		StringBuilder sqlCols = new StringBuilder();
		int index = 0;
		for (TableDesignDetails col : table.getDetails()) {
			String notNull = StringUtils.EMPTY;
			String defaultValue = StringUtils.EMPTY;
			String baseType = StringUtils.EMPTY;
			if (1 == col.getIsMandatory()) {
				notNull = " NOT NULL";
			}
			baseType = convertBaseTypeToDb(col);
			index++;
			if (null != baseType && baseType.length() != 0) {
				sqlCols.append(MessageFormat.format(CREATE_TABLE_COL_PK_DEFAULT_TEMPLATE, col.getCode(), baseType, defaultValue, notNull));

				if (StringUtils.isNoneBlank(col.getSeqCode())) {
					sqlCols.append(MessageFormat.format(ALTER_SEQ_TO_TABLE_TEMPLATE, col.getSeqCode()));
				}

				if (index != table.getDetails().size()) {
					sqlCols.append(",");
				}
			}
		}
		sql.append(MessageFormat.format(CREATE_TABLE_TEMPLATE, table.getTableCode(), sqlCols.toString()));
		return sql.toString();
	}

	/**
	 * @param column
	 * @param result
	 */
	protected void getDefaultValueText(TableDesignDetails column, StringBuilder result) {
		// Default value
		if (null != column.getDefaultValue() && column.getDefaultValue().length() != 0) {
			result.append(" DEFAULT ");
			result.append("'");
			result.append(column.getDefaultValue());
			result.append("'");
			result.append(StringUtils.SPACE);
			column.setSeqCode(StringUtils.EMPTY);
		}
	}

	/**
	 * @param column
	 * @param result
	 */
	protected void getDefaultNumeric(TableDesignDetails column, StringBuilder result) {
		// Default value
		if (null != column.getDefaultValue() && column.getDefaultValue().length() != 0) {
			result.append(" DEFAULT ");
			result.append(column.getDefaultValue());
			result.append(StringUtils.SPACE);
			column.setSeqCode(StringUtils.EMPTY);
		}
	}
	
	// KhanhTH
	private List<String[]> getAllSystemCodelist(Project project, List<LanguageDesign> listLanguageDesign) {
		List<CodeList> codelistByProject = codeListRepository.getCodeListByProject(project.getProjectId());
		List<CodeListDetail> codelistDetailByProject = codeListDetailRepository.findCodeListDetailByProject(project.getProjectId());
		/*List<LanguageDesign> listLanguageDesign = languageDesignRepository.findLanguageByProjectId(project.getProjectId());*/
		List<String[]> listResult = new ArrayList<String[]>();
		
		for(LanguageDesign languageDesign : listLanguageDesign) {
			for(CodeList codeList : codelistByProject) {
				int counter = 1;
				
				for(CodeListDetail codeListDetail : codelistDetailByProject) {
					if(FunctionCommon.equals(codeList.getCodeListId(), codeListDetail.getCodeListId())) {
						String[] tempArray = new String[5];
						StringBuilder messageCode = new StringBuilder();
						messageCode.append("cl.");
						messageCode.append(codeList.getCodeListCode() + "." + counter);
						tempArray[0] = messageCode.toString();
						counter++;
						
						if(codeListDetail.getName() != null) {
							tempArray[1] = codeListDetail.getName();
						} else {
							tempArray[1] = codeListDetail.getValue();
						}
						
						tempArray[2] = languageDesign.getLanguageCode();
						tempArray[3] = languageDesign.getCountryCode();
						tempArray[4] = "tqp.codelist";
						listResult.add(tempArray);
					}
				}
			}
		}

		return listResult;
	}
	
	private String prepareString(String strInput) {
		return StringUtils.replace(strInput, APOSTROPHE, DOUBLE_APOSTROPHE);
	}
	@Override
	public void initData() {
	}
	
	private void initMapSeq(){
		mapSeq.put("qp_account_login_attempt", "qp_account_login_attempt_seq");
		mapSeq.put("qp_account_rule_definition", "qp_account_rule_definition_seq");
		mapSeq.put("qp_account_theme", "qp_account_theme_seq");
		mapSeq.put("qp_account", "qp_account_seq");
		mapSeq.put("qp_message", "qp_message_seq");
		mapSeq.put("qp_permission", "qp_permission_seq");
		mapSeq.put("qp_resources", "qp_resources_seq");
		mapSeq.put("qp_role", "qp_role_seq");
		
	}
	
	@Override
	public String convertBaseTypeToDb(TableDesignDetails column) {
		return StringUtils.EMPTY;
	}
}
