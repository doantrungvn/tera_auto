package org.terasoluna.qp.domain.service.importschema;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DatabaseUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.message.ImportSchemaMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ImportSchema;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.TableDesignForeignKey;
import org.terasoluna.qp.domain.model.TableDesignKey;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.service.common.XmlUtils;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeUtil;
import org.terasoluna.qp.domain.service.domaindesign.DomainDesignService;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.DatatypeTag;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.GraphicDbDesign;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.SqlDesignXml;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

@Service
@Transactional
public class ImportSchemaOracleServiceImpl implements ImportSchemaOracleService {
	
	public static final String INCORECT_SID = "66000";
	public static final String INCORECT_PORT = "08006";
	public static final String INCORECT_PASSWORD_AND_USER = "72000";
	
	private static final String SQL_FK = "SELECT CONS.CONSTRAINT_NAME, CONS.TABLE_NAME, COLS.COLUMN_NAME, CONS.R_CONSTRAINT_NAME, CONS_R.TABLE_NAME R_TABLE_NAME, COLS_R.COLUMN_NAME R_COLUMN_NAME FROM USER_CONSTRAINTS CONS LEFT JOIN USER_CONS_COLUMNS COLS ON COLS.CONSTRAINT_NAME = CONS.CONSTRAINT_NAME LEFT JOIN USER_CONSTRAINTS CONS_R ON CONS_R.CONSTRAINT_NAME = CONS.R_CONSTRAINT_NAME LEFT JOIN USER_CONS_COLUMNS COLS_R ON COLS_R.CONSTRAINT_NAME = CONS.R_CONSTRAINT_NAME WHERE CONS.CONSTRAINT_TYPE = 'R'";
	private static final String SQL_IN = "select DISTINCT ui.INDEX_NAME, a.column_name from user_indexes ui INNER JOIN all_ind_columns a ON a.index_name=ui.index_name where ui.table_name='%s' and ui.index_type='NORMAL' and ui.TABLE_OWNER = '%s'";
	private static final String SQL_TABLE = "SELECT * FROM user_tables";
	private static final String SQL_COLUMN = "SELECT * FROM user_tab_columns";
	private static final String SQL_PK = "select col.CONSTRAINT_NAME, col.column_name from user_cons_columns col left join user_constraints const on col.CONSTRAINT_NAME = const.CONSTRAINT_NAME WHERE const.constraint_type = 'P' and const.table_name='%s'";
	private static final String SQL_UN = "SELECT cc.constraint_name, cc.column_name FROM all_constraints c JOIN all_cons_columns cc ON (c.owner = cc.owner AND c.constraint_name = cc.constraint_name) WHERE c.constraint_type = 'U' AND c.table_name = '%s' ORDER BY cc.constraint_name";
	
	@Inject 
	ImportSchemaOracleHandler importSchemaOracleHandler;
	
	@Inject
	DomainDesignService domainDesignService;
	
	@Inject
	TableDesignRepository tableDesignRepository;
	
	@Inject
	TableDesignDetailRepository tableDesignDetailRepository;

	@Override
	public GraphicDbDesign loadGraphicDesign(ImportSchema importSchema, CommonModel common) {
		GraphicDbDesign grpDbDesign = new GraphicDbDesign();
		Connection connection = null;
		
		try {
			connection = importSchemaOracleHandler.createConnection(importSchema);
			
			List<TableDesign> lstTableDesigns = this.getAllTable(importSchema, connection);
			List<TableDesignDetails> lstTableDesignDetails = this.getAllColumns(importSchema, lstTableDesigns, connection);
			List<TableDesignKey> listTableDesignKey = this.getAllPrimaryKey(importSchema, connection, lstTableDesigns);
			List<TableDesignForeignKey> listTableDesignForeignKey = this.getAllForeignKey(importSchema, connection, lstTableDesigns, lstTableDesignDetails);
			
			int numOfTableDetails = FunctionCommon.isEmpty(lstTableDesignDetails) ? 0 : lstTableDesignDetails.size();
			int numOfTable = FunctionCommon.isEmpty(lstTableDesigns) ? 0 : lstTableDesigns.size();
			int numOfKey = FunctionCommon.isEmpty(listTableDesignKey) ? 0 : listTableDesignKey.size();
			int numOfFK = FunctionCommon.isEmpty(listTableDesignForeignKey) ? 0 : listTableDesignForeignKey.size();
			
			for (int i = 0; i < numOfTable && numOfTableDetails > 0; i++) {
				TableDesign tableDesign = lstTableDesigns.get(i);
				// process row
				List<TableDesignDetails> listOfRow = new ArrayList<TableDesignDetails>();
				for (int k = 0; k < numOfTableDetails; k++) {
					TableDesignDetails tableDesignDetails = lstTableDesignDetails.get(k);
					// if table detail is child of table
					if (tableDesignDetails.getTableCode().equals(tableDesign.getTableCode())) {
						// process fk
						List<TableDesignForeignKey> listFK = new ArrayList<TableDesignForeignKey>();
						for (int j = 0; j < numOfFK; j++) {
							TableDesignForeignKey tableDesignForeignKey = listTableDesignForeignKey.get(j);
							if (tableDesignForeignKey.getFromColumnCode().equals(tableDesignDetails.getCode())
									&& tableDesignForeignKey.getFromTableCode().equals(tableDesign.getTableCode())) {
								listFK.add(tableDesignForeignKey);
							}
						}
						tableDesignDetails.setForeignKeys(listFK);
	
						listOfRow.add(tableDesignDetails);
						numOfTableDetails--;
						lstTableDesignDetails.remove(k);// remove
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
			
			// Set null Id table and column
			for (TableDesign tableDesign : lstTableDesigns) {
				tableDesign.setTableDesignId(new Long(0));
				tableDesign.setProjectId(importSchema.getProjectId());
				for (TableDesignDetails designDetails : tableDesign.getDetails()) {
					designDetails.setColumnId(new Long(0));
				}
			}
			
			sqlDesign.setListOfTables(lstTableDesigns);

			sqlDesign.setListOfDatatypes(getListDatatypeTag(importSchema.getProjectId(), common));
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
			grpDbDesign.setNumberOfTable(lstTableDesigns.size());
			grpDbDesign.setXml(ot.toString());
			System.out.println(ot.toString());
			/*connection.close();*/
			return grpDbDesign;
		} catch (SQLException e) {
			if (INCORECT_SID.equalsIgnoreCase(e.getSQLState())) {
				throw new BusinessException(ResultMessages.error().add(ImportSchemaMessageConst.ERR_IMPORT_SCHEMA_0004));
			}else if (INCORECT_PORT.equalsIgnoreCase(e.getSQLState())) {
				throw new BusinessException(ResultMessages.error().add(ImportSchemaMessageConst.ERR_IMPORT_SCHEMA_0006));
			}else if (INCORECT_PASSWORD_AND_USER.equalsIgnoreCase(e.getSQLState())) {
				throw new BusinessException(ResultMessages.error().add(ImportSchemaMessageConst.ERR_IMPORT_SCHEMA_0005));
			}else{
				throw new BusinessException(e.getMessage());
			}
		} finally {
			DatabaseUtils.close(connection);
		}
	}
	
	/**
	 * 
	 * @param importSchema
	 * @param connection
	 * @param tableDesign
	 * @return
	 */
	private List<TableDesignForeignKey> getAllForeignKey(ImportSchema importSchema, Connection connection, List<TableDesign> lstTableDesigns, List<TableDesignDetails> lstTableDesignDetails) {
		List<TableDesignForeignKey> listTableDesignForeignKey = new ArrayList<TableDesignForeignKey>();
			TableDesignForeignKey tableDesignForeignKey = null;
			
			ResultSet resultSet = null;
			Statement statement = null;
			try {
				statement = connection.createStatement();
				resultSet = importSchemaOracleHandler.createResultSet(importSchema, connection, statement, SQL_FK);
				while (resultSet.next()) {
					tableDesignForeignKey = new TableDesignForeignKey();
					tableDesignForeignKey.setForeignKeyCode(resultSet.getString("CONSTRAINT_NAME").toLowerCase());
					
					tableDesignForeignKey.setFromTableCode(FunctionCommon.convertNameToCodeDb(resultSet.getString("table_name")));
					tableDesignForeignKey.setFromColumnCode(FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name")));
					
					tableDesignForeignKey.setToTableCode(FunctionCommon.convertNameToCodeDb(resultSet.getString("r_table_name")));
					tableDesignForeignKey.setToColumnCode(FunctionCommon.convertNameToCodeDb(resultSet.getString("r_column_name")));
					
					listTableDesignForeignKey.add(tableDesignForeignKey);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				/*try {
					resultSet.close();
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}*/
				DatabaseUtils.close(resultSet);
				DatabaseUtils.close(statement);
			}
		return listTableDesignForeignKey;
	}
	
	/**
	 * 
	 * @param importSchema
	 * @param connection
	 * @param tableDesigns
	 * @return
	 */
	private List<TableDesignKey> getAllPrimaryKey(ImportSchema importSchema, Connection connection, List<TableDesign> tableDesigns){
		List<TableDesignKey> listTableDesignKey = new ArrayList<TableDesignKey>();
		for (TableDesign tableDesign : tableDesigns) {
			
			// Load PRIMARY KEY
			this.getPrimaryKey(importSchema, connection, listTableDesignKey, tableDesign);
			
			// Load UNIQUE KEY
			this.getUniqueKey(importSchema, connection, listTableDesignKey, tableDesign);
			
			// Load INDEX Key
			List<TableDesignKey> listTableDesignKeyNew = this.getIndexKey(importSchema, connection, listTableDesignKey, tableDesign);
			if(FunctionCommon.isNotEmpty(listTableDesignKey)){
				for (TableDesignKey tableDesignKey : listTableDesignKey) {
					if(FunctionCommon.isNotEmpty(listTableDesignKeyNew)){
						for (Iterator<TableDesignKey> it = listTableDesignKeyNew.iterator(); it.hasNext(); ) {
							TableDesignKey s = (TableDesignKey) it.next();
							if(tableDesignKey.getCode().equalsIgnoreCase((s.getCode()))){
								it.remove();
							}
							
						}
					}
				}
				
			}
			listTableDesignKey.addAll(listTableDesignKeyNew);
		}
		return listTableDesignKey;
	}
	
	/**
	 * 
	 * @param importSchema
	 * @param connection
	 * @param listTableDesignKey
	 * @param tableDesign
	 */
	private List<TableDesignKey> getIndexKey(ImportSchema importSchema, Connection connection, List<TableDesignKey> listTableDesignKey, TableDesign tableDesign) {
		List<TableDesignKey> listTableDesignKeyNew = new ArrayList<TableDesignKey>();
		TableDesignKey key;
		List<String> keyItems;
		key = new TableDesignKey();
		keyItems = new ArrayList<String>();
		
		ResultSet resultSet = null;
		Statement statement = null;
		try{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = importSchemaOracleHandler.createResultSet(importSchema, connection, statement, String.format(SQL_IN, tableDesign.getTableCode().toUpperCase(), importSchema.getSchemaName().toUpperCase()));
			String contraintNameTemp = StringUtils.EMPTY;
			StringBuilder strKeyItems = new StringBuilder();
			String keyCode = StringUtils.EMPTY;
			String columnCode = StringUtils.EMPTY;
			while (resultSet.next()) {
				keyCode = FunctionCommon.convertNameToCodeDb(resultSet.getString("index_name"));
				if(contraintNameTemp.equalsIgnoreCase(StringUtils.EMPTY)){
					key = new TableDesignKey();
					key.setCode(keyCode);
					key.setType(DbDomainConst.TblDesignKeyType.INDEX);
					key.setTableDesignId(tableDesign.getTableDesignId());
					contraintNameTemp = keyCode;
				}
				
				if(!contraintNameTemp.equalsIgnoreCase(keyCode)){
					key.setKeyItems(keyItems);
					key.setStrKeyItems(strKeyItems.toString().substring(0, strKeyItems.toString().length()-1));
					listTableDesignKeyNew.add(key);
					
					key = new TableDesignKey();
					strKeyItems = new StringBuilder();
					key.setCode(keyCode);
					key.setType(DbDomainConst.TblDesignKeyType.INDEX);
					key.setTableDesignId(tableDesign.getTableDesignId());
					contraintNameTemp = keyCode;
				}
				
				columnCode = FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name"));
				
				keyItems.add(columnCode);
				if(resultSet.isLast()){
					strKeyItems.append(columnCode);
					key.setKeyItems(keyItems);
					key.setStrKeyItems(strKeyItems.toString());
					listTableDesignKeyNew.add(key);
				}else{
					strKeyItems.append(columnCode);
					strKeyItems.append(DomainDatatypeUtil.STR_REGEX);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			/*try {
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
			DatabaseUtils.close(resultSet);
			DatabaseUtils.close(statement);
		}
		return listTableDesignKeyNew;
	}
	
	/**
	 * 
	 * @param importSchema
	 * @param connection
	 * @param listTableDesignKey
	 * @param tableDesign
	 */
	private void getUniqueKey(ImportSchema importSchema, Connection connection, List<TableDesignKey> listTableDesignKey, TableDesign tableDesign) {
		TableDesignKey key;
		List<String> keyItems;
		key = new TableDesignKey();
		keyItems = new ArrayList<String>();

		ResultSet resultSet = null;
		Statement statement = null;
		try{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = importSchemaOracleHandler.createResultSet(importSchema, connection, statement, String.format(SQL_UN, tableDesign.getTableCode().toUpperCase()));
			String contraintNameTemp = StringUtils.EMPTY;
			StringBuilder strKeyItems = new StringBuilder();
			while (resultSet.next()) {
				if(contraintNameTemp.equalsIgnoreCase(StringUtils.EMPTY)){
					key = new TableDesignKey();
					key.setCode(resultSet.getString("constraint_name"));
					key.setType(DbDomainConst.TblDesignKeyType.UNIQUE);
					key.setTableDesignId(tableDesign.getTableDesignId());
					contraintNameTemp = resultSet.getString("constraint_name");
				}
				
				if(!contraintNameTemp.equalsIgnoreCase(resultSet.getString("constraint_name"))){
					key.setKeyItems(keyItems);
					key.setStrKeyItems(strKeyItems.toString().substring(0, strKeyItems.toString().length()-1));
					listTableDesignKey.add(key);
					
					key = new TableDesignKey();
					strKeyItems = new StringBuilder();
					key.setCode(resultSet.getString("constraint_name"));
					key.setType(DbDomainConst.TblDesignKeyType.UNIQUE);
					key.setTableDesignId(tableDesign.getTableDesignId());
					contraintNameTemp = resultSet.getString("constraint_name");
				}
				String columnName = FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name").toLowerCase());
				keyItems.add(columnName);
				if(resultSet.isLast()){
					strKeyItems.append(columnName);
					key.setKeyItems(keyItems);
					key.setStrKeyItems(strKeyItems.toString());
					listTableDesignKey.add(key);
				}else{
					strKeyItems.append(columnName);
					strKeyItems.append(DomainDatatypeUtil.STR_REGEX);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			/*try {
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
			DatabaseUtils.close(resultSet);
			DatabaseUtils.close(statement);
		}
	}
	
	/**
	 * @param importSchema
	 * @param connection
	 * @param listTableDesignKey
	 * @param tableDesign
	 */
	private void getPrimaryKey(ImportSchema importSchema, Connection connection, List<TableDesignKey> listTableDesignKey, TableDesign tableDesign) {
		TableDesignKey key;
		List<String> keyItems;
		key = new TableDesignKey();
		keyItems = new ArrayList<String>();

		ResultSet resultSet = null;
		Statement statement = null;
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = importSchemaOracleHandler.createResultSet(importSchema, connection, statement, String.format(SQL_PK, tableDesign.getTableCode().toUpperCase()));
			StringBuilder strKeyItems = new StringBuilder();
			String columnName = StringUtils.EMPTY;
			while (resultSet.next()) {
				columnName = FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name"));
				keyItems.add(columnName);
				if(resultSet.isLast()){
					strKeyItems.append(columnName);
					key.setCode(FunctionCommon.convertNameToCodeDb(resultSet.getString("CONSTRAINT_NAME")));
					key.setType(DbDomainConst.TblDesignKeyType.PK);
					key.setKeyItems(keyItems);
					key.setStrKeyItems(strKeyItems.toString());
					key.setTableDesignId(tableDesign.getTableDesignId());
					listTableDesignKey.add(key);
				}else{
					strKeyItems.append(columnName);
					strKeyItems.append(DomainDatatypeUtil.STR_REGEX);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			/*try {
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
			DatabaseUtils.close(resultSet);
			DatabaseUtils.close(statement);
		}
	}
	
	/**
	 * 
	 * @param importSchema
	 * @param lstTableDesigns
	 * @param connection
	 * @return
	 */
	private List<TableDesignDetails> getAllColumns(ImportSchema importSchema, List<TableDesign> lstTableDesigns, Connection connection){
		List<TableDesignDetails> tableDesignDetails = new ArrayList<TableDesignDetails>();
		
		ResultSet resultSet = null;
		Statement statement = null;
		TableDesignDetails designDetails = null;
		try {
			statement = connection.createStatement();
			resultSet = importSchemaOracleHandler.createResultSet(importSchema, connection, statement, SQL_COLUMN);
			Long id = 1L;
			while (resultSet.next()) {
				designDetails = new TableDesignDetails();
				designDetails.setColumnId(id++);
				
				Long dataType = new Long(importSchemaOracleHandler.mapDataType(resultSet));

				designDetails.setName(FunctionCommon.convertCodeToNameDb(resultSet.getString("column_name")));
				designDetails.setTableCode(FunctionCommon.convertNameToCodeDb(resultSet.getString("table_name")));
				designDetails.setCode(FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name")));
				
				designDetails.setItemSeqNo(resultSet.getString("column_id") == null ? null : Integer.parseInt(resultSet.getString("column_id")));
				/*designDetails.setDefaultValue(resultSet.getString("data_default"));*/
				designDetails.setDataType(dataType);
				String madatory = resultSet.getString("nullable");
				if("Y".equals(madatory)){
					designDetails.setIsMandatory(DbDomainConst.YesNoFlg.NO);
				}else{
					designDetails.setIsMandatory(DbDomainConst.YesNoFlg.YES);
				}
				for (TableDesign tableDesign : lstTableDesigns) {
					if(designDetails.getTableCode().equals(tableDesign.getTableCode())){
						designDetails.setTableDesignId(tableDesign.getTableDesignId());
					}
				}
				
				// Set maxlength
				/*if(length == null){*/
				switch (importSchemaOracleHandler.mapDataType(resultSet)) {
					case DbDomainConst.BaseType.CHARACTER_VARYING_BASETYPE:
					case DbDomainConst.BaseType.TEXT_BASETYPE:
					case DbDomainConst.BaseType.CHAR_BASETYPE:
						Integer length = resultSet.getString("CHAR_LENGTH") == null ? null : Integer.valueOf(resultSet.getString("CHAR_LENGTH"));
						designDetails.setMaxlength(length);
						break;
						
					case DbDomainConst.BaseType.BIGINT_BASETYPE:
					case DbDomainConst.BaseType.BIGSERIAL_BASETYPE:
					case DbDomainConst.BaseType.INTEGER_BASETYPE:
					case DbDomainConst.BaseType.SERIAL_BASETYPE:
					case DbDomainConst.BaseType.SMALLINT_BASETYPE:
					case DbDomainConst.BaseType.BOOLEAN_BASETYPE:
					case DbDomainConst.BaseType.DATE_BASETYPE:
					case DbDomainConst.BaseType.TIME_BASETYPE:
					case DbDomainConst.BaseType.DATETIME_BASETYPE:
						designDetails.setMaxlength(null);
						break;
					case DbDomainConst.BaseType.NUMERIC_BASETYPE:
					case DbDomainConst.BaseType.CURRENCY_BASETYPE:

						Integer numeric_precision = resultSet.getString("data_precision") == null ? null : Integer.valueOf(resultSet.getString("data_precision"));
						Integer numeric_scale = resultSet.getString("data_scale") == null ? null : Integer.valueOf(resultSet.getString("data_scale"));

						if (!FunctionCommon.isEmpty(numeric_precision)) {
							if (!FunctionCommon.isEmpty(numeric_scale)) {
								designDetails.setDecimalPart(numeric_scale);
								designDetails.setMaxlength(numeric_precision + numeric_scale);
							} else {
								designDetails.setMaxlength(numeric_precision);
							}
						} else {
							designDetails.setDecimalPart(null);
							designDetails.setMaxlength(null);
						}

						break;

				}
				/*}else{
					designDetails.setMaxlength(length);
				}*/
				
				tableDesignDetails.add(designDetails);
			}
			
			/*Long startSequence = new Long(0);
			for (TableDesignDetails tableDesignDetails2 : tableDesignDetails) {
				tableDesignDetails2.setColumnId(startSequence);
				startSequence++;
			}*/
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			/*try {
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
			DatabaseUtils.close(resultSet);
			DatabaseUtils.close(statement);
		}
		
		return tableDesignDetails;
	}
	
	/**
	 * 
	 * @param importSchema
	 * @return
	 */
	private List<TableDesign> getAllTable(ImportSchema importSchema, Connection connection){
		List<TableDesign> tableDesigns = new ArrayList<TableDesign>();
		
		ResultSet resultSet = null;
		Statement statement = null;
		TableDesign tableDesign = null;
		try {
			// Get table information
			statement = connection.createStatement();
			resultSet = importSchemaOracleHandler.createResultSet(importSchema, connection, statement, SQL_TABLE);
			int x = 10;
			int y = 10;
			Long id = 1L;
			while (resultSet.next()) {
				if (id % 5 == 0) {
					y = y+ 200;
					x = 10;
				}
				tableDesign = new TableDesign();
				tableDesign.setTableDesignId(id++);
				tableDesign.setTableName(FunctionCommon.convertCodeToNameDb(resultSet.getString("table_name")));
				tableDesign.setTableCode(FunctionCommon.convertNameToCodeDb(resultSet.getString("table_name")));
				tableDesign.setType(DbDomainConst.TableDesignType.MASTER);
				tableDesign.setX(x);
				tableDesign.setY(y);
				x = x + 200;
				tableDesigns.add(tableDesign);
			}

			/*Long startSequence = new Long(0);
			for (TableDesign tableDesign2 : tableDesigns) {
				tableDesign2.setTableDesignId(startSequence);
				startSequence++;
			}*/
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			/*try {
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
			DatabaseUtils.close(resultSet);
			DatabaseUtils.close(statement);
		}
		return tableDesigns;
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
	private List<DatatypeTag> getListDatatypeTag(Long projectId, CommonModel common) {
		List<DatatypeTag> listOfDatatype = new ArrayList<DatatypeTag>();
		// init
		DatatypeTag datatypeTag = new DatatypeTag();
		datatypeTag.setDbType("oracle");
		// get from DB
		datatypeTag.setListOfGroupDBType(domainDesignService.getAllBasetype(projectId, true));
		//
		listOfDatatype.add(datatypeTag);
		return listOfDatatype;
	}
}
