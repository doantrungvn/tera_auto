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
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignKeyRepository;
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
public class ImportSchemaPostgreServiceImpl implements ImportSchemaPostgreService {

	public static final String INCORECT_DATABASE_ERR_CODE = "3D000";
	public static final String INCORECT_HOST_ERR_CODE = "08001";
	public static final String INCORECT_PASSWORD_AND_USER_ERR_CODE = "28P01";

	private static final String SQL_UN = "SELECT * FROM (SELECT DISTINCT ON (kcu.column_name) tc.constraint_name, kcu.column_name FROM information_schema.table_constraints AS tc JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name WHERE constraint_type = 'UNIQUE' AND tc.table_name='%s' ) temp order by constraint_name";
	private static final String SQL_PK = "SELECT DISTINCT tc.constraint_name, kcu.column_name FROM information_schema.table_constraints AS tc JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name WHERE constraint_type = 'PRIMARY KEY' AND tc.table_name = '%s'";
	private static final String SQL_FK = "SELECT tc.constraint_name AS constraint_name, kcu.table_name AS from_table_name, kcu.column_name AS from_column_name, ccu.table_name AS to_table_name, ccu.column_name AS to_column_name FROM information_schema.table_constraints AS tc  JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name WHERE constraint_type = 'FOREIGN KEY' AND tc.table_name = '%s'";
	private static final String SQL_IN = "SELECT t.relname as table_name, i.relname as index_name, a.attname as column_name FROM pg_class t, pg_class i, pg_index ix, pg_attribute a WHERE t.oid = ix.indrelid and i.oid = ix.indexrelid and a.attrelid = t.oid and a.attnum = ANY(ix.indkey) and t.relkind = 'r' and t.relname = '%s' order by t.relname, i.relname";
	private static final String SQL_TABLE = "SELECT * FROM information_schema.tables where table_schema='%s'";
	private static final String SQL_COLUMN = "SELECT * FROM information_schema.columns WHERE table_schema='%s' ORDER BY table_name";

	@Inject
	ImportSchemaPostgreHandler importSchemaPostgreHandler;

	@Inject
	DomainDesignService domainDesignService;

	@Inject
	TableDesignRepository tableDesignRepository;

	@Inject
	TableDesignDetailRepository tableDesignDetailRepository;

	@Inject
	TableDesignKeyRepository tableDesignKeyRepository;

	@Override
	public GraphicDbDesign loadGraphicDesign(ImportSchema importSchema, CommonModel common) {
		GraphicDbDesign grpDbDesign = new GraphicDbDesign();
		Connection connection = null;
		try {
			connection = importSchemaPostgreHandler.createConnection(importSchema);

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
							if (tableDesignForeignKey.getFromColumnCode().equals(tableDesignDetails.getCode()) && tableDesignForeignKey.getFromTableCode().equals(tableDesign.getTableCode())) {
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
				jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, DbDomainConst.CHARACTER_ENCODING);
				jaxbMarshaller.setProperty(CharacterEscapeHandler.class.getName(), new XmlUtils());
				jaxbMarshaller.marshal(sqlDesign, ot);
			} catch (JAXBException e) {
				throw new BusinessException(e.getMessage());
			}
			grpDbDesign.setNumberOfTable(lstTableDesigns.size());
			grpDbDesign.setXml(ot.toString());
			System.out.println(ot.toString());
			/* connection.close(); */
			return grpDbDesign;
		} catch (SQLException e1) {
			if (INCORECT_DATABASE_ERR_CODE.equalsIgnoreCase(e1.getSQLState())) {
				throw new BusinessException(ResultMessages.error().add(ImportSchemaMessageConst.ERR_IMPORT_SCHEMA_0001, importSchema.getDbName()));
			} else if (INCORECT_HOST_ERR_CODE.equalsIgnoreCase(e1.getSQLState())) {
				throw new BusinessException(ResultMessages.error().add(ImportSchemaMessageConst.ERR_IMPORT_SCHEMA_0002, importSchema.getDbHostName()));
			} else if (INCORECT_PASSWORD_AND_USER_ERR_CODE.equalsIgnoreCase(e1.getSQLState())) {
				throw new BusinessException(ResultMessages.error().add(ImportSchemaMessageConst.ERR_IMPORT_SCHEMA_0003, importSchema.getDbUser()));
			} else {
				throw new BusinessException(e1.getMessage());
			}
		} finally {
			DatabaseUtils.close(connection);
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
			resultSet = importSchemaPostgreHandler.createResultSet(importSchema, connection, statement, String.format(SQL_COLUMN, importSchema.getSchemaName()));
			Long id = 1L;
			while (resultSet.next()) {
				designDetails = new TableDesignDetails();
				designDetails.setColumnId(id++);
				Long dataType = new Long(importSchemaPostgreHandler.mapDataType(resultSet));
				designDetails.setName(FunctionCommon.convertCodeToNameDb(resultSet.getString("column_name")));
				designDetails.setTableCode(FunctionCommon.convertNameToCodeDb(resultSet.getString("table_name")));
				designDetails.setCode(FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name")));
				designDetails.setItemSeqNo(resultSet.getString("ordinal_position") == null ? null : Integer.parseInt(resultSet.getString("ordinal_position")));
				/*designDetails.setDefaultValue(resultSet.getString("column_default"));*/
				designDetails.setDataType(dataType);
				String madatory = resultSet.getString("is_nullable");
				if("YES".equalsIgnoreCase(madatory)){
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
					switch (importSchemaPostgreHandler.mapDataType(resultSet)) {
						case DbDomainConst.BaseType.CHARACTER_VARYING_BASETYPE:
						case DbDomainConst.BaseType.TEXT_BASETYPE:
						case DbDomainConst.BaseType.CHAR_BASETYPE:
							Integer length = resultSet.getString("character_maximum_length") == null ? null : Integer.valueOf(resultSet.getString("character_maximum_length"));
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
							Integer numeric_precision = resultSet.getString("numeric_precision") == null ? null : Integer.valueOf(resultSet.getString("numeric_precision"));
							Integer numeric_scale = resultSet.getString("numeric_scale") == null ? null : Integer.valueOf(resultSet.getString("numeric_scale"));
							
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
				/*} else {
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
			DatabaseUtils.close(resultSet);
			DatabaseUtils.close(statement);
			/*try {
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
		}
		
		return tableDesignDetails;
	}

	/**
	 * 
	 * @param importSchema
	 * @return
	 */
	private List<TableDesign> getAllTable(ImportSchema importSchema, Connection connection) {
		List<TableDesign> tableDesigns = new ArrayList<TableDesign>();

		ResultSet resultSet = null;
		Statement statement = null;
		TableDesign tableDesign = null;
		try {
			// Get table information
			statement = connection.createStatement();
			resultSet = importSchemaPostgreHandler.createResultSet(importSchema, connection, statement, String.format(SQL_TABLE, importSchema.getSchemaName()));
			int x = 10;
			int y = 10;
			Long id = 1L;
			while (resultSet.next()) {
				if (id % 5 == 0) {
					y = y + 200;
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DatabaseUtils.close(resultSet);
			DatabaseUtils.close(statement);
		}
		return tableDesigns;
	}

	/**
	 * 
	 * @param importSchema
	 * @param connection
	 * @param tableDesigns
	 * @return
	 */
	private List<TableDesignKey> getAllPrimaryKey(ImportSchema importSchema, Connection connection, List<TableDesign> tableDesigns) {
		List<TableDesignKey> listTableDesignKey = new ArrayList<TableDesignKey>();
		for (TableDesign tableDesign : tableDesigns) {

			// Load PRIMARY KEY
			this.getPrimaryKey(importSchema, connection, listTableDesignKey, tableDesign);

			// Load UNIQUE KEY
			this.getUniqueKey(importSchema, connection, listTableDesignKey, tableDesign);

			// Load INDEX Key
			List<TableDesignKey> listTableDesignKeyNew = this.getIndexKey(importSchema, connection, listTableDesignKey, tableDesign);
			if (FunctionCommon.isNotEmpty(listTableDesignKey)) {
				for (TableDesignKey tableDesignKey : listTableDesignKey) {
					if (FunctionCommon.isNotEmpty(listTableDesignKeyNew)) {
						for (Iterator<TableDesignKey> it = listTableDesignKeyNew.iterator(); it.hasNext();) {
							TableDesignKey s = (TableDesignKey) it.next();
							if (tableDesignKey.getCode().equalsIgnoreCase((s.getCode()))) {
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
		/* String tableName = tableDesign.getTableCode(); */

		ResultSet resultSet = null;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			resultSet = importSchemaPostgreHandler.createResultSet(importSchema, connection, statement, String.format(SQL_IN, tableDesign.getTableCode()));
			String contraintNameTemp = StringUtils.EMPTY;
			StringBuilder strKeyItems = new StringBuilder();
			while (resultSet.next()) {
				if (contraintNameTemp.equalsIgnoreCase(StringUtils.EMPTY)) {
					key = new TableDesignKey();
					key.setCode(resultSet.getString("index_name"));
					key.setType(DbDomainConst.TblDesignKeyType.INDEX);
					key.setTableDesignId(tableDesign.getTableDesignId());
					contraintNameTemp = resultSet.getString("index_name");
				}

				if (!contraintNameTemp.equalsIgnoreCase(resultSet.getString("index_name"))) {
					key.setKeyItems(keyItems);
					key.setStrKeyItems(strKeyItems.toString().substring(0, strKeyItems.toString().length() - 1));
					listTableDesignKeyNew.add(key);

					key = new TableDesignKey();
					strKeyItems = new StringBuilder();
					key.setCode(resultSet.getString("index_name"));
					key.setType(DbDomainConst.TblDesignKeyType.INDEX);
					key.setTableDesignId(tableDesign.getTableDesignId());
					contraintNameTemp = resultSet.getString("index_name");
				}

				keyItems.add(FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name")));
				if (resultSet.isLast()) {
					strKeyItems.append(FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name")));
					key.setKeyItems(keyItems);
					key.setStrKeyItems(strKeyItems.toString());
					listTableDesignKeyNew.add(key);
				} else {
					strKeyItems.append(FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name")));
					strKeyItems.append(DomainDatatypeUtil.STR_REGEX);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/*
			 * try { resultSet.close(); statement.close(); } catch (SQLException
			 * e) { e.printStackTrace(); }
			 */
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
		try {
			statement = connection.createStatement();
			resultSet = importSchemaPostgreHandler.createResultSet(importSchema, connection, statement, String.format(SQL_UN, tableDesign.getTableCode()));
			String contraintNameTemp = StringUtils.EMPTY;
			StringBuilder strKeyItems = new StringBuilder();
			while (resultSet.next()) {
				if (contraintNameTemp.equalsIgnoreCase(StringUtils.EMPTY)) {
					key = new TableDesignKey();
					key.setCode(resultSet.getString("constraint_name").toLowerCase());
					key.setType(DbDomainConst.TblDesignKeyType.UNIQUE);
					key.setTableDesignId(tableDesign.getTableDesignId());
					contraintNameTemp = resultSet.getString("constraint_name").toLowerCase();
				}

				if (!contraintNameTemp.equalsIgnoreCase(resultSet.getString("constraint_name").toLowerCase())) {
					key.setKeyItems(keyItems);
					key.setStrKeyItems(strKeyItems.toString().substring(0, strKeyItems.toString().length() - 1));
					listTableDesignKey.add(key);

					key = new TableDesignKey();
					strKeyItems = new StringBuilder();
					key.setCode(resultSet.getString("constraint_name").toLowerCase());
					key.setType(DbDomainConst.TblDesignKeyType.UNIQUE);
					key.setTableDesignId(tableDesign.getTableDesignId());
					contraintNameTemp = resultSet.getString("constraint_name").toLowerCase();
				}

				keyItems.add(FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name")));
				if (resultSet.isLast()) {
					strKeyItems.append(FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name")));
					key.setKeyItems(keyItems);
					key.setStrKeyItems(strKeyItems.toString());
					listTableDesignKey.add(key);
				} else {
					strKeyItems.append(FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name")));
					strKeyItems.append(DomainDatatypeUtil.STR_REGEX);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/*
			 * try { resultSet.close(); statement.close(); } catch (SQLException
			 * e) { e.printStackTrace(); }
			 */
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
		/* String tableName = tableDesign.getTableCode(); */

		/*
		 * String queryPrimaryKey = "SELECT DISTINCT" + " tc.constraint_name," +
		 * " kcu.column_name" + " FROM" +
		 * " information_schema.table_constraints AS tc" +
		 * " JOIN information_schema.key_column_usage AS kcu" +
		 * " ON tc.constraint_name = kcu.constraint_name" +
		 * " JOIN information_schema.constraint_column_usage AS ccu" +
		 * " ON ccu.constraint_name = tc.constraint_name" +
		 * " WHERE constraint_type = 'PRIMARY KEY' AND tc.table_name = '"
		 * +tableName+"'";
		 */
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			resultSet = importSchemaPostgreHandler.createResultSet(importSchema, connection, statement, String.format(SQL_PK, tableDesign.getTableCode()));
			StringBuilder strKeyItems = new StringBuilder();
			while (resultSet.next()) {
				keyItems.add(FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name")));
				if (resultSet.isLast()) {
					strKeyItems.append(FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name")));
					key.setCode(resultSet.getString("constraint_name"));
					key.setType(DbDomainConst.TblDesignKeyType.PK);
					key.setKeyItems(keyItems);
					key.setStrKeyItems(strKeyItems.toString());
					key.setTableDesignId(tableDesign.getTableDesignId());
					listTableDesignKey.add(key);
				} else {
					strKeyItems.append(FunctionCommon.convertNameToCodeDb(resultSet.getString("column_name")));
					strKeyItems.append(DomainDatatypeUtil.STR_REGEX);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/*
			 * try { resultSet.close(); statement.close(); } catch (SQLException
			 * e) { e.printStackTrace(); }
			 */
			DatabaseUtils.close(resultSet);
			DatabaseUtils.close(statement);
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

		for (TableDesign tableDesign : lstTableDesigns) {
			/* String tableName = tableDesign.getTableCode(); */
			TableDesignForeignKey tableDesignForeignKey = null;

			ResultSet resultSet = null;
			Statement statement = null;
			try {
				statement = connection.createStatement();
				resultSet = importSchemaPostgreHandler.createResultSet(importSchema, connection, statement, String.format(SQL_FK, tableDesign.getTableCode()));
				while (resultSet.next()) {
					tableDesignForeignKey = new TableDesignForeignKey();
					tableDesignForeignKey.setForeignKeyCode(resultSet.getString("constraint_name").toLowerCase());

					tableDesignForeignKey.setFromTableCode(resultSet.getString("from_table_name").replace(" ", "_"));
					tableDesignForeignKey.setFromColumnCode(resultSet.getString("from_column_name").replace(" ", "_"));

					tableDesignForeignKey.setToTableCode(resultSet.getString("to_table_name").replace(" ", "_"));
					tableDesignForeignKey.setToColumnCode(resultSet.getString("to_column_name").replace(" ", "_"));

					listTableDesignForeignKey.add(tableDesignForeignKey);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				/*
				 * try { resultSet.close(); statement.close(); } catch
				 * (SQLException e) { e.printStackTrace(); }
				 */
				DatabaseUtils.close(resultSet);
				DatabaseUtils.close(statement);
			}
		}
		return listTableDesignForeignKey;
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
		datatypeTag.setDbType("postgresql");
		// get from DB
		datatypeTag.setListOfGroupDBType(domainDesignService.getAllBasetype(projectId, true));
		//
		listOfDatatype.add(datatypeTag);
		return listOfDatatype;
	}
}
