package org.terasoluna.qp.domain.service.generatescreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.DesignStatus;
import org.terasoluna.qp.app.common.constants.DbDomainConst.JavaDataTypeOfBlogic;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.domain.model.AssignComponent;
import org.terasoluna.qp.domain.model.AssignDetail;
import org.terasoluna.qp.domain.model.BusinessCheckComponent;
import org.terasoluna.qp.domain.model.BusinessCheckDetail;
import org.terasoluna.qp.domain.model.BusinessDetailContent;
import org.terasoluna.qp.domain.model.ExecutionComponent;
import org.terasoluna.qp.domain.model.ExecutionInputValue;
import org.terasoluna.qp.domain.model.FeedbackComponent;
import org.terasoluna.qp.domain.model.FunctionDesign;
import org.terasoluna.qp.domain.model.IfComponent;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.LoopComponent;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.MessageParameter;
import org.terasoluna.qp.domain.model.ModuleTableMapping;
import org.terasoluna.qp.domain.model.NavigatorComponent;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ScreenAction;
import org.terasoluna.qp.domain.model.ScreenActionParam;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemCodelist;
import org.terasoluna.qp.domain.model.ScreenItemOutput;
import org.terasoluna.qp.domain.model.ScreenParameter;
import org.terasoluna.qp.domain.model.SequenceConnector;
import org.terasoluna.qp.domain.model.SequenceLogic;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignCondition;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.model.SqlDesignTable;
import org.terasoluna.qp.domain.model.SqlDesignTableItem;
import org.terasoluna.qp.domain.model.SqlDesignValue;
import org.terasoluna.qp.domain.model.TableDesignForeignKey;
import org.terasoluna.qp.domain.model.UserDefineCodelistDetails;
import org.terasoluna.qp.domain.model.UtilityComponent;
import org.terasoluna.qp.domain.model.ValidationCheckDetail;
import org.terasoluna.qp.domain.service.autocomplete.SqlBuilderServiceImpl.ValueType;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignHelper;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignConst;

/**
 * @author trungdv
 */
public class GenerateScreenContruct {

	private static final String SPACE = " ";
	private static final Integer LIST_TYPE = 1;
	private static final String FROM_INPUT_TO_OUTPUT = "from_input_to_output";
	private static final String FROM_OBJECTDEFINITION_TO_OUTPUT = "from_objectdefinition_to_output";
	private static final String FROM_INPUT_TO_OBJECTDEFINITION = "from_input_to_objectdefinition";
	private static final String BLANK = "";
	private static final String ONE = "1";

	/**
	 * @param executionComponentId
	 * @param designInput
	 * @param parameterScope
	 * @param parameterId
	 * @return
	 */
	public static ExecutionInputValue setExecutionInputValue(Long executionComponentId, SqlDesignInput designInput, Integer parameterScope, String parameterId) {
		ExecutionInputValue execInput = new ExecutionInputValue();
		execInput.setExecutionComponentId(executionComponentId);
		execInput.setSqlDesignInputId(designInput.getSqlDesignInputId());
		execInput.setSqlDesignInputCode(designInput.getSqlDesignInputCode());
		execInput.setSqlDesignInputName(designInput.getSqlDesignInputName());
		execInput.setDataType(designInput.getDataType());
		if (designInput.getArrayFlag() != null) {
			Boolean arrayFlg = designInput.getArrayFlag() == 1 ? true : false;
			execInput.setArrayFlg(arrayFlg);
		}
		execInput.setItemSequenceNo(designInput.getItemSeqNo().toString());
		execInput.setParameterScope(parameterScope);
		execInput.setParameterId(parameterId);

		return execInput;
	}

	/**
	 * @param sqlDesignTableId
	 * @param tableId
	 * @param columnId
	 * @param itemSeqNo
	 * @return
	 */
	public static SqlDesignTableItem setSqlDesignTableItem(Long sqlDesignTableId, Integer itemSeqNo, TableDesignForeignKey fk) {
		SqlDesignTableItem sqlDesignTableItem = new SqlDesignTableItem();
		sqlDesignTableItem.setSqlDesignTableId(sqlDesignTableId);
		sqlDesignTableItem.setOperatorCode("0");
		sqlDesignTableItem.setTableId(fk.getFromTableId());
//		sqlDesignTableItem.setTableId(fk.getToTableId());
		sqlDesignTableItem.setColumnId(fk.getFromColumnId());
//		sqlDesignTableItem.setColumnId(fk.getToColumnId());
		sqlDesignTableItem.setJoinTableId(fk.getToTableId());
//		sqlDesignTableItem.setJoinTableId(fk.getFromTableId());
		sqlDesignTableItem.setJoinColumnId(fk.getToColumnId());
//		sqlDesignTableItem.setJoinColumnId(fk.getFromColumnId());
		sqlDesignTableItem.setItemSeqNo(itemSeqNo);
		sqlDesignTableItem.setLogicCode(null);
		sqlDesignTableItem.setTableName(fk.getFromTableName());
//		sqlDesignTableItem.setTableName(fk.getToTableName());
		sqlDesignTableItem.setTableCode(fk.getFromTableCode());
//		sqlDesignTableItem.setTableCode(fk.getToTableCode());
		sqlDesignTableItem.setColumnName(fk.getFromColumnName());
//		sqlDesignTableItem.setColumnName(fk.getToColumnName());
		sqlDesignTableItem.setColumnCode(fk.getFromColumnCode());
//		sqlDesignTableItem.setColumnCode(fk.getToColumnCode());
		sqlDesignTableItem.setJoinTableName(fk.getToTableName());
//		sqlDesignTableItem.setJoinTableName(fk.getFromTableName());
		sqlDesignTableItem.setJoinColumnName(fk.getToColumnName());
//		sqlDesignTableItem.setJoinColumnName(fk.getFromColumnName());
		sqlDesignTableItem.setJoinTableCode(fk.getToTableCode());
//		sqlDesignTableItem.setJoinTableCode(fk.getFromTableCode());
		sqlDesignTableItem.setJoinColumnCode(fk.getToColumnCode());
//		sqlDesignTableItem.setJoinColumnCode(fk.getFromColumnCode());
		return sqlDesignTableItem;
	}

	/**
	 * @param sqlDesignId
	 * @param joinType
	 * @param tableId
	 * @param joinTableId
	 * @param itemSeqNo
	 * @param tableName
	 * @param joinTableName
	 * @return
	 */
	public static SqlDesignTable setSqlDesignTable(Long sqlDesignId, Long sqlDesignTableId, String joinType, Long tableId, Long joinTableId, Integer itemSeqNo, String tableName, String joinTableName, String tableCode, String joinTableCode) {
		SqlDesignTable sqlDesignTable = new SqlDesignTable();
		if (sqlDesignTableId != null) {
			sqlDesignTable.setSqlDesignTableId(sqlDesignTableId);
		}
		sqlDesignTable.setSqlDesignId(sqlDesignId);
		sqlDesignTable.setJoinType(joinType);
		sqlDesignTable.setTableId(tableId);
		sqlDesignTable.setJoinTableId(joinTableId);
		sqlDesignTable.setItemSeqNo(itemSeqNo);
		sqlDesignTable.setTableName(tableName);
		sqlDesignTable.setJoinTableName(joinTableName);
		sqlDesignTable.setTableCode(tableCode);
		sqlDesignTable.setJoinTableCode(joinTableCode);
		return sqlDesignTable;
	}

	/**
	 * @param sqlDesignId
	 * @param sqlOutputName
	 * @param sqlOutputCode
	 * @param dataType
	 * @param sqlOutputParentId
	 * @param arrayFlg
	 * @param itemSeqNo
	 * @param index
	 * @return
	 */
	public static SqlDesignOutput setSqlDesignOutput(Long tempSqlDesignOutputId, Long sqlDesignId, String sqlOutputName, String sqlOutputCode, Integer dataType, Long tempSqlOutputParentId, Integer arrayFlg, Integer itemSeqNo, String outputBeanId, String mappingColumn, Boolean searchScreen, Long tableId, Long columnId, Integer designType, Integer objectType) {
		SqlDesignOutput sqlOutput = new SqlDesignOutput();
		sqlOutput.setSqlDesignOutputId(tempSqlDesignOutputId);
		sqlOutput.setSqlDesignId(sqlDesignId);
		sqlOutput.setSqlDesignOutputName(sqlOutputName);
		sqlOutput.setSqlDesignOutputCode(sqlOutputCode);
		sqlOutput.setDataType(dataType);
		sqlOutput.setSqlDesignOutputParentId(tempSqlOutputParentId);
		// TrungDV : don't use arrayFlg, depend on attribute return_type of table sql_design
		sqlOutput.setArrayFlag(arrayFlg);
		// NinhNV: Revert use array flag for DN
//		sqlOutput.setArrayFlag(0);
		sqlOutput.setItemSeqNo(itemSeqNo);
		if (searchScreen != null && searchScreen) {
			sqlOutput.setMappingColumn(mappingColumn);
		} else {
			if (itemSeqNo != null) {
				if (BusinessDesignConst.DataType.ENTITY.equals(dataType)) {
					sqlOutput.setMappingColumn(null);
				} else {
					sqlOutput.setMappingColumn(itemSeqNo.toString());
				}
			}
		}
//		Long tblId = tableId != null ? tableId : 0L;
//		Long colId = columnId != null ? columnId : 0L;
//		sqlOutput.setTableId(tblId);
//		sqlOutput.setColumnId(colId);
		
		sqlOutput.setTableId(tableId);
		sqlOutput.setColumnId(columnId);

		sqlOutput.setObjectDefinitionId(outputBeanId);
		sqlOutput.setOutputBeanId(outputBeanId);
		sqlOutput.setDesignType(designType);
		sqlOutput.setObjectType(objectType);
		return sqlOutput;
	}

	/**
	 * @param sqlDesignId
	 * @param functionCode
	 * @param tableId
	 * @param columnId
	 * @param itemSeqNo
	 * @param tableName
	 * @param columnName
	 * @param enabledFlag
	 * @return
	 */
	public static SqlDesignResult setSqlDesignResult(Long sqlDesignId, String functionCode, Long tableId, Long columnId, Integer itemSeqNo, String tableName, String columnName, Integer enabledFlag, Integer baseType) {
		SqlDesignResult result = new SqlDesignResult();
		result.setSqlDesignId(sqlDesignId);
		result.setTableId(tableId);
		result.setColumnId(columnId);
		result.setItemSeqNo(itemSeqNo);
		result.setFunctionCode(functionCode);
		result.setTableName(tableName);
		result.setColumnName(columnName);
		if (enabledFlag != null && enabledFlag.intValue() == 1) {
			result.setEnabledFlag(enabledFlag);
		} else {
			result.setEnabledFlag(0);
		}
		result.setDataType(baseType);

		return result;
	}

	/**
	 * @param sqlDesignId
	 * @param leftTableId
	 * @param leftColumnId
	 * @param operatorCode
	 * @param itemSeqNo
	 * @param leftTableName
	 * @param leftColumnName
	 * @return
	 */
	public static SqlDesignCondition setSqlDesignCondition(Long sqlDesignId, Long leftTableId, Long leftColumnId, String operatorCode, Integer itemSeqNo, String leftTableName, String leftColumnName, Integer baseType, String valueMapping) {
		SqlDesignCondition condition = new SqlDesignCondition();
		condition.setSqlDesignId(sqlDesignId);
		condition.setLeftTableId(leftTableId);
		condition.setLeftColumnId(leftColumnId);
		condition.setOperatorCode(operatorCode);
		condition.setValue(valueMapping);
		condition.setRightTableId(null);
		condition.setRightColumnId(null);
		condition.setGroupType(0);
		condition.setItemSeqNo(itemSeqNo);
		condition.setConditionType(2);
		condition.setSqlDesignParameterId(null);
		condition.setLeftTableName(leftTableName);
		condition.setLeftColumnName(leftColumnName);
		condition.setRightTableName(null);
		condition.setRightColumnName(null);
		condition.setFunctionCode(null);
		condition.setDataType(baseType);
		return condition;
	}

	/**
	 * @param sqlDesignId
	 * @param inputName
	 * @param inputCode
	 * @param baseType
	 * @param itemSeqNo
	 * @return
	 */
	public static SqlDesignInput setSqlDesignInput(Long sqlDesignId, String inputName, String inputCode, Integer baseType, Integer itemSeqNo, Long columnId, Long tableId, Boolean isEntity, Long sqlDesignInputParentId, Boolean flgSetTableAndColumnId, Integer arrayFlg, Integer designType, Integer objectType, Boolean notGenArray) {
		SqlDesignInput sqlInput = new SqlDesignInput();
		sqlInput.setSqlDesignId(sqlDesignId);
		// template id
		if(itemSeqNo != null) {
			sqlInput.setSqlDesignInputId(itemSeqNo.longValue());
		}
		sqlInput.setSqlDesignInputName(inputName);
		sqlInput.setSqlDesignInputCode(inputCode);
		if (baseType != null) {
			sqlInput.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(baseType));
		}
		if(sqlDesignInputParentId != null) {
			sqlInput.setSqlDesignInputParentId(sqlDesignInputParentId);
		}
		sqlInput.setItemSeqNo(itemSeqNo);
		if(notGenArray != null && notGenArray) {
			sqlInput.setArrayFlag(0);
		} else {
			if(arrayFlg != null) {
				sqlInput.setArrayFlag(arrayFlg);
			} else {
				sqlInput.setArrayFlag(0);
			}
		}
		// special key : default gen arrayFlag is true if datatype is Byte
		if (JavaDataTypeOfBlogic.BYTE_DATATYPE.equals(sqlInput.getDataType())) {
			sqlInput.setArrayFlag(1);
		}
		sqlInput.setTempColumnId(columnId);
		if (isEntity) {
			if (DbDomainConst.ObjectType.OBJECT_OBJECT.equals(objectType)) {
				sqlInput.setDataType(BusinessDesignConst.DataType.OBJECT);
			} else {
				sqlInput.setDataType(BusinessDesignConst.DataType.ENTITY);
			}
		}
		if (flgSetTableAndColumnId) {
			Long tableIdValue = tableId != null ? tableId : 0;
			Long columnIdValue = columnId != null ? columnId : 0;
			sqlInput.setTableId(tableIdValue);
			sqlInput.setColumnId(columnIdValue);
		}
		sqlInput.setDesignType(designType);
		sqlInput.setObjectType(objectType);

		return sqlInput;
	}

	/**
	 * @param screenItem
	 * @param udd
	 * @param codelistSeqNo
	 * @return
	 */
	public static ScreenItemCodelist populateScreenItemCodeList(ScreenDesignDefault screenDesignDefault, ScreenItem screenItem, UserDefineCodelistDetails udd) {
		ScreenItemCodelist screenCodeList = new ScreenItemCodelist();
		screenCodeList.setScreenItemId(screenItem.getScreenItemId());
		screenCodeList.setCodelistSeqNo(udd.getItemSeqNo());
		screenCodeList.setCodelistName(udd.getCodelistName());
		screenCodeList.setCodelistVal(udd.getCodelistValue());
		screenCodeList.setSupportOptionValFlg(udd.getSupportOptionFlg());
		screenCodeList.setCreatedBy(screenDesignDefault.getCreatedBy());
		screenCodeList.setCreatedDate(screenDesignDefault.getCreatedDate());
		screenCodeList.setUpdatedBy(screenDesignDefault.getCreatedBy());
		screenCodeList.setUpdatedDate(screenDesignDefault.getCreatedDate());
		return screenCodeList;
	}
	
	public static List<ScreenItemCodelist> populateScreenItemCodeListForCheckBoxTrueFalse(ScreenDesignDefault screenDesignDefault, ScreenItem screenItem) {
		List<ScreenItemCodelist> lstCodelists = new ArrayList<ScreenItemCodelist>();
		ScreenItemCodelist screenCodeListTrue = new ScreenItemCodelist();
		screenCodeListTrue.setScreenItemId(screenItem.getScreenItemId());
		screenCodeListTrue.setCodelistSeqNo(0);
		screenCodeListTrue.setCodelistName(ScreenDesignConst.ScreenItemConst.VALUE_TRUE);
		screenCodeListTrue.setCodelistVal(ScreenDesignConst.ScreenItemConst.VALUE_TRUE);
		screenCodeListTrue.setSupportOptionValFlg(0);
		screenCodeListTrue.setCreatedBy(screenDesignDefault.getCreatedBy());
		screenCodeListTrue.setCreatedDate(screenDesignDefault.getCreatedDate());
		screenCodeListTrue.setUpdatedBy(screenDesignDefault.getCreatedBy());
		screenCodeListTrue.setUpdatedDate(screenDesignDefault.getCreatedDate());
		lstCodelists.add(screenCodeListTrue);
		
		ScreenItemCodelist screenCodeListFalse = new ScreenItemCodelist();
		screenCodeListFalse.setScreenItemId(screenItem.getScreenItemId());
		screenCodeListFalse.setCodelistSeqNo(1);
		screenCodeListFalse.setCodelistName(ScreenDesignConst.ScreenItemConst.VALUE_FALSE);
		screenCodeListFalse.setCodelistVal(ScreenDesignConst.ScreenItemConst.VALUE_FALSE);
		screenCodeListFalse.setSupportOptionValFlg(0);
		screenCodeListFalse.setCreatedBy(screenDesignDefault.getCreatedBy());
		screenCodeListFalse.setCreatedDate(screenDesignDefault.getCreatedDate());
		screenCodeListFalse.setUpdatedBy(screenDesignDefault.getCreatedBy());
		screenCodeListFalse.setUpdatedDate(screenDesignDefault.getCreatedDate());
		lstCodelists.add(screenCodeListFalse);
		return lstCodelists;
	}

	/**
	 * @param screenDesignDefault
	 * @param screenId
	 * @param item
	 * @param itemSeqNo
	 * @return
	 */
	public static ScreenParameter populateScreenParameter(ScreenDesignDefault screenDesignDefault, Long screenId, ScreenItem item, Integer itemSeqNo) {
		ScreenParameter screenParameter = new ScreenParameter();
		screenParameter.setScreenId(screenId);
		screenParameter.setScreenParamName(item.getItemName());
//		screenParameter.setScreenParamCode(item.getItemCode());
		screenParameter.setScreenParamCode(StringUtils.substring(item.getItemCode(), 0, 1).toLowerCase() + StringUtils.substring(item.getItemCode(), 1));
		screenParameter.setParamSeqNo(itemSeqNo);
		screenParameter.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
		screenParameter.setCreatedBy(screenDesignDefault.getCreatedBy());
		screenParameter.setCreatedDate(screenDesignDefault.getCreatedDate());
		screenParameter.setUpdatedBy(screenDesignDefault.getCreatedBy());
		screenParameter.setUpdatedDate(screenDesignDefault.getCreatedDate());
		screenParameter.setDomainTblMappingId(item.getTblDesignId());
		screenParameter.setDomainTblMappingItemId(item.getColumnId());
		return screenParameter;
	}

	/**
	 * @param screenDesignDefault
	 * @param functionName
	 * @return
	 */
	public static FunctionDesign populateFunctionDesign(Long tempId, ScreenDesignDefault screenDesignDefault, String name, String code) {
		// Register function design
		FunctionDesign function = new FunctionDesign();
		function.setFunctionId(tempId);
		GenerateUniqueKey generateUniqueKey = new GenerateUniqueKey(DbDomainConst.MAX_LENGTH_OF_NAME, DbDomainConst.MAX_LENGTH_OF_CODE);

		function.setFunctionName(generateUniqueKey.calculateName(name, screenDesignDefault.getModuleName(), screenDesignDefault.getSuffix()));
//		function.setFunctionCode(generateUniqueKey.calculateCodeForAuto(prefix, screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()));

		String functionCode = generateUniqueKey.calculateCodeForAuto(code, screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix());
		function.setFunctionCode(StringUtils.substring(functionCode, 0, 1).toLowerCase() + StringUtils.substring(functionCode, 1));
		
		function.setFunctionType(DbDomainConst.FunctionType.ONLINE);
		function.setModuleId(screenDesignDefault.getModuleId());
		function.setRemark(BusinessDesignConst.REMARK);
		function.setActor(null);
		function.setCreatedBy(screenDesignDefault.getCreatedBy());
		function.setCreatedDate(screenDesignDefault.getCreatedDate());
		function.setUpdatedBy(screenDesignDefault.getCreatedBy());
		function.setUpdatedDate(screenDesignDefault.getCreatedDate());
		return function;
	}

	/**
	 * Test Method
	 * @param tempSequenceId
	 * @param sequenceLogicName
	 * @param componenttype
	 * @param sequenceNo
	 * @param xCoordinates
	 * @param yCoordinates
	 * @param businessLogicId
	 * @param firstPointForEarch
	 * @param endPointForEarch
	 * @param relatedSequenceLogicId
	 * @param flagHaveConnector
	 * @return
	 */
	public static SequenceLogic populateSequenceLogicNew(Integer tempSequenceId, String sequenceLogicName, Integer componenttype, Integer sequenceNo, Integer xCoordinates, Integer yCoordinates, Long businessLogicId, Integer firstPointForEarch, Integer endPointForEarch, String relatedSequenceLogicId, Boolean flagHaveConnector) {
		SequenceLogic sequenceLogic = new SequenceLogic();
		sequenceLogic.setTempSequenceId(tempSequenceId);
		sequenceLogic.setSequenceLogicName(SPACE + sequenceLogicName);
		sequenceLogic.setComponentType(componenttype);
		sequenceLogic.setSequenceNo(sequenceNo);
		sequenceLogic.setxCoordinates(xCoordinates.doubleValue());
		sequenceLogic.setyCoordinates(yCoordinates.doubleValue());
		sequenceLogic.setBusinessLogicId(businessLogicId);
		sequenceLogic.setFirstPointForEarch(firstPointForEarch);
		sequenceLogic.setEndPointForEarch(endPointForEarch);
		sequenceLogic.setRemark(BusinessDesignConst.REMARK);
		sequenceLogic.setRelatedSequenceLogicId(relatedSequenceLogicId);
		sequenceLogic.setFlagHaveConnector(flagHaveConnector);
		return sequenceLogic;
	}
	
	/**
	 * @param connectorSource
	 * @param connectorDest
	 * @return
	 */
	public static SequenceConnector populateSequenceConnector(String connectorSource, String connectorDest, String connectorType) {
		SequenceConnector sequenceConnector = new SequenceConnector();
		sequenceConnector.setConnectorSource(connectorSource);
		sequenceConnector.setConnectorDest(connectorDest);
		sequenceConnector.setConnectorType(connectorType);
		return sequenceConnector;
	}

	/**
	 * @param sequenceLogicId
	 * @param label
	 * @param navigatorToId
	 * @return
	 */
	public static NavigatorComponent populateNavigatorComponent(ScreenDesignDefault screenDesignDefault, Long sequenceLogicId, String label, Long navigatorToId, Boolean flagCommonDelete, String navigatorToName, Integer navigatorToType) {
		NavigatorComponent navigatorComponent = new NavigatorComponent();
		navigatorComponent.setNavigatorComponentId(sequenceLogicId);
		navigatorComponent.setSequenceLogicId(sequenceLogicId);
		navigatorComponent.setLabel(label);
		if(flagCommonDelete != null && flagCommonDelete) {
			navigatorComponent.setNavigatorToType(BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_COMMON);
			navigatorComponent.setNavigatorToId(-1L);
		} else {
			navigatorComponent.setNavigatorToType(navigatorToType);
		}
		if(navigatorToId != null) {
			navigatorComponent.setNavigatorToId(navigatorToId);
		}
		navigatorComponent.setTransitionType(BusinessDesignConst.NavigatorComponent.TRANSITION_TYPE_REDIRECT);
		navigatorComponent.setRemark(BusinessDesignConst.REMARK);
		if(StringUtils.isNotBlank(navigatorToName)) {
			navigatorComponent.setNavigatorToName(navigatorToName);
		}
		return navigatorComponent;
	}

	/**
	 * @param screenDesignDefault
	 * @param form
	 * @param area
	 * @param mapItemHidden
	 * @param screenAction
	 * @return
	 */
	public static List<ScreenActionParam> populateScreenActionParam(ScreenDesignDefault screenDesignDefault, ScreenDesign screen, List<ScreenItem> lstScreenItem, ScreenForm form, Map<String, List<ScreenItem>> mapItemHidden, ScreenAction screenAction) {
		List<ScreenActionParam> lstScreenActionParam = new ArrayList<ScreenActionParam>();
		List<ScreenItem> lstItemHidden = new ArrayList<ScreenItem>();
		if (ScreenDesignConst.SEARCH_SCREEN.equals(screen.getScreenTypeName())) {
			lstItemHidden = mapItemHidden.get(ScreenDesignConst.SEARCH_SCREEN);
		} else if (ScreenDesignConst.VIEW_SCREEN.equals(screen.getScreenTypeName())) {
			lstItemHidden = mapItemHidden.get(ScreenDesignConst.VIEW_SCREEN);
		} else if (ScreenDesignConst.MODIFY_SCREEN.equals(screen.getScreenTypeName())) {
			lstItemHidden = mapItemHidden.get(ScreenDesignConst.MODIFY_SCREEN);
		}
		if (lstItemHidden != null && lstItemHidden.size() > 0) {
			int paramSeqNo = 0;
			for (ScreenItem item : lstItemHidden) {
				ScreenItem itemPK = null;
				if (lstScreenItem != null && lstScreenItem.size() > 0) {
					for (ScreenItem obj : lstScreenItem) {
						if (screen.getScreenId().equals(obj.getScreenId()) && obj.getColumnId() != null && obj.getColumnId().equals(item.getColumnId())) {
							itemPK = obj;
							break;
						}
					}
				}
				ScreenActionParam param = new ScreenActionParam();
				param.setScreenActionId(screenAction.getScreenActionId());
				param.setDomainTblMappingId(itemPK.getTblDesignId());
				param.setDomainTblMappingItemId(itemPK.getColumnId());
				param.setActionParamCode(itemPK.getItemCode());
				param.setActionParamName(itemPK.getMessageDesign().getMessageCode());
				param.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
				param.setArrayFlg(null);
				param.setParamSeqNo(paramSeqNo);
				param.setParentScreenActionParamId(null);
				param.setCreatedBy(screenDesignDefault.getCreatedBy());
				param.setCreatedDate(screenDesignDefault.getCreatedDate());
				param.setUpdatedBy(screenDesignDefault.getCreatedBy());
				param.setUpdatedDate(screenDesignDefault.getCreatedDate());
				if (form != null && itemPK != null) {
					param.setScreenItemCode(form.getFormCode() + "." + itemPK.getAreaCode() + "." + itemPK.getItemCode());
				}
				paramSeqNo++;
				lstScreenActionParam.add(param);
			}
		}
		return lstScreenActionParam;
	}

	/**
	 * @param sqlDesignName
	 * @param sqlpattern
	 * @param screenDesignDefault
	 * @param screenDesignObj
	 * @param table
	 * @param isGetTotal
	 * @return
	 */
	public static SqlDesign populateSqlDesign(Integer designType, String sqlDesignName, String sqlDesignCode, Integer sqlpattern, ScreenDesignDefault screenDesignDefault, ScreenDesign screenDesignObj, ModuleTableMapping table, Boolean isGetTotal, Integer pageable) {
		SqlDesign sqlDesign = new SqlDesign();
		GenerateUniqueKey generateUniqueKey = new GenerateUniqueKey(DbDomainConst.MAX_LENGTH_OF_NAME, DbDomainConst.MAX_LENGTH_OF_CODE);
		/*String suffixUID = GenerateUniqueKey.generateRandomInteger();*/
		sqlDesignName = sqlDesignName + SPACE + screenDesignDefault.getModuleName();
		sqlDesign.setSqlDesignName(generateUniqueKey.calculateName(sqlDesignName + SPACE, SPACE, screenDesignDefault.getSuffix()));
		sqlDesignCode = generateUniqueKey.calculateCodeForAuto(WordUtils.capitalize(sqlDesignCode), StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
//		sqlDesign.setSqlDesignCode(StringUtils.substring(sqlDesignCode, 0, 1).toLowerCase() + StringUtils.substring(sqlDesignCode, 1));
		sqlDesign.setSqlDesignCode(StringUtils.lowerCase(sqlDesignCode));
		sqlDesign.setRemark(BusinessDesignConst.REMARK);
		sqlDesign.setDesignType(designType);
		sqlDesign.setOmitOverlap(0);
		sqlDesign.setCreatedBy(screenDesignDefault.getCreatedBy());
		sqlDesign.setUpdatedBy(screenDesignDefault.getCreatedBy());
		sqlDesign.setIsValid(null);
		sqlDesign.setSqlText("");
		sqlDesign.setSqlPattern(sqlpattern);
		sqlDesign.setProjectId(screenDesignDefault.getProjectId());
		sqlDesign.setModuleId(screenDesignDefault.getModuleId());
		sqlDesign.setDesignStatus(DesignStatus.UNDER_DESIGN);
		sqlDesign.setSystemDate(screenDesignDefault.getCreatedDate());
		if ((ScreenDesignConst.SEARCH_SCREEN.equals(screenDesignObj.getScreenTypeName()) && !isGetTotal) || (table != null && table.getTableMappingType().equals(LIST_TYPE))) {
			sqlDesign.setReturnType(1); // Multiple
		} else {
			sqlDesign.setReturnType(0); // single
		}
		sqlDesign.setPageable(pageable);
		return sqlDesign;
	}

	/**
	 * @param sqlDesignId
	 * @param itemSeqNo
	 * @param columnId
	 * @param columnName
	 * @return
	 */
	public static SqlDesignValue setSqlDesignValue(Long sqlDesignId, Integer itemSeqNo, Long columnId, String columnName, String parameterMapping, Integer baseType) {
		SqlDesignValue sqlValue = new SqlDesignValue();
		sqlValue.setSqlDesignId(sqlDesignId);
		sqlValue.setItemSeqNo(itemSeqNo);
		sqlValue.setColumnId(columnId);
		sqlValue.setColumnName(columnName);
		sqlValue.setParameter(parameterMapping);
		sqlValue.setDataType(baseType);
		//Bangnl
		//add value default for value_type column
		sqlValue.setValueType(ValueType.PARAMETERS.getNumber());
		return sqlValue;
	}

	/**
	 * @param executionComponentIdTemp
	 * @param label
	 * @param sqlDesignId
	 * @param sequenceLogicId
	 * @param concurrencyFlg
	 * @return
	 */
	public static ExecutionComponent contructExecutionComponent(Long executionComponentIdTemp, String label, Long sqlDesignId, SqlDesign sqlDesign, Long sequenceLogicId, Boolean concurrencyFlg) {
		ExecutionComponent executionComponent = new ExecutionComponent();
		executionComponent.setExecutionComponentId(executionComponentIdTemp);
		executionComponent.setLabel(label);
		executionComponent.setRemark(BusinessDesignConst.REMARK);
		executionComponent.setSqlDesignId(sqlDesignId);
		if (sqlDesign != null) {
			executionComponent.setSqlDesignCode(sqlDesign.getSqlDesignCode());
			executionComponent.setSqlDesignIdAutocomplete(sqlDesign.getSqlDesignName());
		}
		executionComponent.setSequenceLogicId(sequenceLogicId);
		executionComponent.setConcurrencyFlg(concurrencyFlg);

		return executionComponent;
	}

	/**
	 * @param in
	 * @param checkDetailId
	 * @return
	 */
	public static BusinessDetailContent setBusinessDetailContent(InputBean in, Long checkDetailId) {
		BusinessDetailContent content = new BusinessDetailContent();
		content.setTblDesignId(in.getTblDesignId());
		content.setTblDesignIdAutocomplete(in.getTblDesignName());
		content.setColumnId(in.getColumnId());
		content.setColumnIdAutocomplete(in.getColumnName());
		content.setParameterScope(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
		content.setParameterId(in.getInputBeanId());
		content.setBusinessCheckDetailId(checkDetailId);
		content.setDataType(in.getDataType());
		return content;
	}
	public static BusinessDetailContent setBusinessDetailContentByOb(ObjectDefinition in, Long checkDetailId) {
		BusinessDetailContent content = new BusinessDetailContent();
		content.setTblDesignId(in.getTblDesignId());
		content.setTblDesignIdAutocomplete(in.getTblDesignName());
		content.setColumnId(in.getColumnId());
		content.setColumnIdAutocomplete(in.getColumnName());
		content.setParameterScope(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID);
		content.setParameterId(in.getObjectDefinitionId());
		content.setBusinessCheckDetailId(checkDetailId);
		content.setDataType(in.getDataType());
		return content;
	}
	
	public static BusinessDetailContent setBusinessDetailContentByOu(OutputBean ou, Long checkDetailId) {
		BusinessDetailContent content = new BusinessDetailContent();
		content.setTblDesignId(ou.getTblDesignId());
		content.setTblDesignIdAutocomplete(ou.getTblDesignName());
		content.setColumnId(ou.getColumnId());
		content.setColumnIdAutocomplete(ou.getColumnName());
		content.setParameterScope(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID);
		content.setParameterId(ou.getOutputBeanId());
		content.setBusinessCheckDetailId(checkDetailId);
		content.setDataType(ou.getDataType());
		return content;
	}

	/**
	 * @param label
	 * @param businessCheckComponentIdTemp
	 * @return
	 */
	public static BusinessCheckComponent contructBusinessCheckComponent(String label, Long businessCheckComponentIdTemp) {
		BusinessCheckComponent businessCheckComponent = new BusinessCheckComponent();
		businessCheckComponent.setBusinessCheckComponentId(businessCheckComponentIdTemp);
		businessCheckComponent.setLabel(SPACE + label);
		businessCheckComponent.setRemark(BusinessDesignConst.REMARK);
		businessCheckComponent.setSequenceLogicId(businessCheckComponentIdTemp);
		return businessCheckComponent;
	}

	/**
	 * @param tempCheckDetailId
	 * @param businessCheckType
	 * @param messageCode
	 * @param businessCheckComponentId
	 * @param itemSeqNo
	 * @return
	 */
	public static BusinessCheckDetail contructBusinessCheckDetail(Long tempCheckDetailId, Integer businessCheckType, String messageCode, Long businessCheckComponentId, Integer itemSeqNo) {
		BusinessCheckDetail businessCheckDetail = new BusinessCheckDetail();
		businessCheckDetail.setBusinessCheckDetailId(tempCheckDetailId);
		businessCheckDetail.setBusinessCheckType(businessCheckType);
		businessCheckDetail.setFormulaDefinitionId(null);
		businessCheckDetail.setFormulaDefinitionContent(null);
		businessCheckDetail.setMessageCode(messageCode);
		businessCheckDetail.setAbortFlg(false);
		businessCheckDetail.setBusinessCheckComponentId(businessCheckComponentId);
		businessCheckDetail.setItemSequenceNo(itemSeqNo);
		return businessCheckDetail;
	}

	/**
	 * @param screenDesignDefault
	 * @param tempTargetId
	 * @return
	 */
	public static MessageParameter contructMessageParametter(ScreenDesignDefault screenDesignDefault, ModuleTableMapping table, Integer targetType, Long tempTargetId, Long businessLogicId) {
		MessageParameter mp = new MessageParameter();
		mp.setTargetType(targetType);
		mp.setTargetId(tempTargetId);
		if (table != null && table.getMessageDesign() != null) {
			mp.setParameterCode(table.getMessageDesign().getMessageCode());
		} else if (screenDesignDefault != null && screenDesignDefault.getModuleTableMappings() != null && screenDesignDefault.getModuleTableMappings().length > 0 && screenDesignDefault.getModuleTableMappings()[0].getMessageDesign() != null) {
			mp.setParameterCode(screenDesignDefault.getModuleTableMappings()[0].getMessageDesign().getMessageCode());
		}
		mp.setParameterValue(null);
		mp.setItemSequenceNo(0);
		mp.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
		mp.setBusinessLogicId(businessLogicId);
		return mp;
	}

	/**
	 * @param label
	 * @param sequenceLogicId
	 * @return
	 */
	public static AssignComponent contructAssignComponent(Long assignIdTemp, String label, Long sequenceLogicId) {
		AssignComponent assignComponent = new AssignComponent();
		assignComponent.setAssignComponentId(assignIdTemp);
		assignComponent.setLabel(label);
		assignComponent.setSequenceLogicId(sequenceLogicId);
		assignComponent.setRemark(BusinessDesignConst.REMARK);
		return assignComponent;
	}
	
	/**
	 * 
	 * @param label
	 * @param remark
	 * @param sequenceLogicId
	 * @return
	 */
	public static UtilityComponent contructUtilityComponent(String label, String remark, Long sequenceLogicId){
		UtilityComponent utilityComponent = new UtilityComponent();
		utilityComponent.setType(0);
		utilityComponent.setIndexId(null);
		utilityComponent.setLabel(label);
		utilityComponent.setRemark(remark);
		utilityComponent.setSequenceLogicId(sequenceLogicId);
		return utilityComponent;
	}
	
	/**
	 * 
	 * @param label
	 * @param remark
	 * @param sequenceLogicId
	 * @return
	 */
	public static IfComponent contructIfComponent(String label, String remark, Long sequenceLogicId){
		IfComponent ifComponent = new IfComponent();
		ifComponent.setLabel(label);
		ifComponent.setRemark(remark);
		ifComponent.setSequenceLogicId(sequenceLogicId);
		return ifComponent;
	}

	/**
	 * @param parameterScope
	 * @param parameterId
	 * @param targetScope
	 * @param targetId
	 * @param assignIdTemp
	 * @param in
	 * @param out
	 * @param ob
	 * @param assignFromTo
	 * @return
	 */
	public static AssignDetail setAssignDetail(Integer parameterScope, String parameterId, Integer targetScope, String targetId, Long assignIdTemp, InputBean in, OutputBean out, ObjectDefinition ob, String assignFromTo) {
		AssignDetail assignDetail = new AssignDetail();
		assignDetail.setAssignType(BusinessDesignConst.AssignDetailComponent.ASSIGN_TYPE_PARAMETER);
		assignDetail.setParameterScope(parameterScope);
		assignDetail.setParameterId(parameterId);
		assignDetail.setTargetScope(targetScope);
		assignDetail.setTargetId(targetId);
		assignDetail.setFormulaDefinitionId(null);
		assignDetail.setFormulaDefinitionContent(null);
		assignDetail.setAssignComponentId(assignIdTemp);
		if (FROM_INPUT_TO_OUTPUT.equals(assignFromTo)) {
			if (DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(in.getDataType()) || DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(out.getDataType()) || DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(in.getDataType()) || DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(out.getDataType())) {
				assignDetail.setDataGroup(BLANK);
			} else {
				assignDetail.setDataGroup(ONE);
			}
		} else if (FROM_OBJECTDEFINITION_TO_OUTPUT.equals(assignFromTo)) {
			if (DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ob.getDataType()) || DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(out.getDataType()) || DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ob.getDataType()) || DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(out.getDataType())) {
				assignDetail.setDataGroup(BLANK);
			} else {
				assignDetail.setDataGroup(ONE);
			}
		} else if (FROM_INPUT_TO_OBJECTDEFINITION.equals(assignFromTo)) {
			if (DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(in.getDataType()) || DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ob.getDataType()) || DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(in.getDataType()) || DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ob.getDataType())) {
				assignDetail.setDataGroup(BLANK);
			} else {
				assignDetail.setDataGroup(ONE);
			}
		} else {
			assignDetail.setDataGroup(ONE);
		}
		// special case : default set data group of Total count data
		if((ob != null && ob.getFlagTotalCount() != null && ob.getFlagTotalCount()) || (out != null && out.getFlagTotalCount() != null && out.getFlagTotalCount())) {
			assignDetail.setDataGroup(BLANK);
		}
		return assignDetail;
	}

	/**
	 * @param label
	 * @param table
	 * @param inputbeansRegisted
	 * @param objDefRegisted
	 * @param sequenceLogicId
	 * @return
	 */
	public static LoopComponent contructLoopComponent(String label, ModuleTableMapping table, List<InputBean> inputbeansRegisted, List<ObjectDefinition> objDefRegisted, Long sequenceLogicId, int index) {
		LoopComponent loopComponent = new LoopComponent();
		loopComponent.setLabel(label);
		loopComponent.setLoopType(BusinessDesignConst.LoopComponent.LOOP_TYPE_FOREACH);
		if (inputbeansRegisted != null && inputbeansRegisted.size() > 0) {
			loopComponent.setParameterScope(BusinessDesignConst.LoopComponent.PARAMETER_SCOPE_INPUT_BEAN);
			for (InputBean in : inputbeansRegisted) {
				if (table.getTblDesignId().equals(in.getTblDesignId())) {
					if (DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(in.getDataType()) || DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(in.getDataType())) {
						loopComponent.setParameterId(in.getInputBeanId());
					}
				}
			}
		} else if (objDefRegisted != null && objDefRegisted.size() > 0) {
			loopComponent.setParameterScope(BusinessDesignConst.LoopComponent.PARAMETER_SCOPE_OBJECT_DEFINITION);
			for (ObjectDefinition ob : objDefRegisted) {
				if (table.getTblDesignId().equals(ob.getTblDesignId())) {
					if (DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ob.getDataType()) || DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ob.getDataType())) {
						loopComponent.setParameterId(ob.getObjectDefinitionId());
					}
				}
			}
		}
		loopComponent.setFromScope(BusinessDesignConst.LoopComponent.LOOP_SCOPE_CUSTOMIZE);
		loopComponent.setFromValue("0");
		loopComponent.setToScope(BusinessDesignConst.LoopComponent.LOOP_SCOPE_LENGTH);
		loopComponent.setToValue(null);
		loopComponent.setFormulaDefinitionId(null);
		loopComponent.setFormulaDefinitionContent(null);
		loopComponent.setSequenceLogicId(sequenceLogicId);
		loopComponent.setRemark(BusinessDesignConst.REMARK);
		
		//add default index
		loopComponent.setIndex(BusinessDesignConst.LOOP_INDEX + index);
		return loopComponent;
	}

	/**
	 * @param label
	 * @param messageCode
	 * @param tempSequenceLogicId
	 * @return
	 */
	public static FeedbackComponent contructFeedbackComponent(String label, String messageCode, Long tempSequenceLogicId) {
		FeedbackComponent fb = new FeedbackComponent();
		fb.setFeedbackComponentId(tempSequenceLogicId);
		fb.setLabel(label);
		fb.setMessageCode(messageCode);
		fb.setRemark(BusinessDesignConst.REMARK);
		fb.setType(DbDomainConst.MessageType.INFORMATION_MESSAGE);
		fb.setSequenceLogicId(tempSequenceLogicId);
		return fb;
	}

	/**
	 * @param itemSeqNo
	 * @param screenId
	 * @param itemCode
	 * @param messageDesign
	 * @param screenAreaId
	 * @param groupItemId
	 * @param logicalDataType
	 * @param screenActionId
	 * @param itemType
	 * @param item
	 * @return
	 */
	public static ScreenItem populateScreenItemHidden(ScreenDesignDefault screenDesignDefault, Integer itemSeqNo, Long screenId, String itemCode, String itemName, MessageDesign messageDesign, Long screenAreaId, String areaCode, Long groupItemId, Integer logicalDataType, Long screenActionId, Integer itemType, 
			Integer physicalDataType, Long tblDesignId, Long columnId, String keyType, Boolean isPkHidden) {
		ScreenItem screenItem = new ScreenItem();
		screenItem = new ScreenItem();
		screenItem.setItemSeqNo(itemSeqNo);
		screenItem.setScreenId(screenId);
		screenItem.setItemCode(itemCode);
		screenItem.setItemName(itemName);
		screenItem.setMessageDesign(messageDesign);
		if(messageDesign != null) {
			screenItem.setMessageCode(messageDesign.getMessageCode());
		}
		screenItem.setScreenAreaId(screenAreaId);
		screenItem.setAreaCode(areaCode);
		screenItem.setPhysicalDataType(physicalDataType);
		screenItem.setLogicalDataType(logicalDataType);
		screenItem.setGroupItemId(groupItemId);
		screenItem.setScreenActionId(screenActionId);
		screenItem.setItemType(itemType);
		screenItem.setKeyType(keyType);
		screenItem.setIsPkHidden(isPkHidden);
		screenItem.setTblDesignId(tblDesignId);
		screenItem.setColumnId(columnId);
		screenItem.setCreatedBy(screenDesignDefault.getCreatedBy());
		screenItem.setCreatedDate(screenDesignDefault.getCreatedDate());
		screenItem.setUpdatedBy(screenDesignDefault.getCreatedBy());
		screenItem.setUpdatedDate(screenDesignDefault.getCreatedDate());
		return screenItem;
	}

	/**
	 * @param tempInputbeanId
	 * @param code
	 * @param name
	 * @param dataType
	 * @param arrayFlg
	 * @param parentInputBeanId
	 * @param businessLogicId
	 * @param screenItemId
	 * @param tblDesignId
	 * @param columnId
	 * @param itemSeqNo
	 * @return
	 */
	public static InputBean setInputbean(Integer tempInputbeanId, ScreenDesign screenDesignObj, String code, String name, Integer dataType, Boolean arrayFlg, String parentInputBeanId, Long businessLogicId, Long screenItemId, Long tblDesignId, Long columnId, Integer itemSeqNo, Integer itemType, Integer inputbeanType) {
		InputBean in = new InputBean();
		if (tempInputbeanId != null) {
			in.setInputBeanId(tempInputbeanId.toString());
		}
		in.setInputBeanCode(code);
		in.setMessageString(name);
		in.setInputBeanName(name);
		in.setInputBeanType(inputbeanType);
		in.setDataType(dataType);
		
		// check is Confirm && Complete screen
		if((DbDomainConst.ScreenPatternType.VIEW.equals(screenDesignObj.getScreenPatternType())) 
				&& (ScreenDesignConst.ConfirmType.SCREEN.equals(screenDesignObj.getConfirmationType()) || ScreenDesignConst.CompleteType.SCREEN.equals(screenDesignObj.getCompletionType()))) {
			// special case CHECKBOX : If is check-box in confirm and complete screen, default gen arrayFlg is FALSE
			if (DbDomainConst.LogicDataType.CHECKBOX.equals(itemType)) {
				in.setArrayFlg(false);
			} else {
				// special case BIRNARY : If datatype is byte, default gen arrayFlg is TRUE
				if (DbDomainConst.JavaDataTypeOfBlogic.BYTE_DATATYPE.equals(dataType)) {
					in.setArrayFlg(true);
				} else {
					in.setArrayFlg(arrayFlg);
				}
			}
		} else {
			// special case BIRNARY : If datatype is byte, default gen arrayFlg is TRUE
			if(DbDomainConst.JavaDataTypeOfBlogic.BYTE_DATATYPE.equals(dataType)) {
				in.setArrayFlg(true);
			} else {
				in.setArrayFlg(arrayFlg);
			}
		}
		in.setBusinessLogicId(businessLogicId);
		in.setParentInputBeanId(parentInputBeanId);
		in.setItemSequenceNo(itemSeqNo);
		in.setScreenItemId(screenItemId);
		in.setTblDesignId(tblDesignId);
		in.setColumnId(columnId);

		return in;
	}
	
	/**
	 * @param tempOutputbeanId
	 * @param code
	 * @param name
	 * @param messageCode
	 * @param dataType
	 * @param businessLogicId
	 * @param parentOutputBeanId
	 * @param itemSeqNoOutput
	 * @param tblDesignId
	 * @param columnId
	 * @param groupBaseTypeId
	 * @param screenItemId
	 * @param arrayFlag
	 * @return
	 */
	public static OutputBean contructOutputbean(Long tempOutputbeanId, ScreenDesign screenDesign, String code, String name, String messageCode, Integer dataType, Long businessLogicId, String parentOutputBeanId, 
			Integer itemSeqNoOutput, Long tblDesignId, Long columnId, Integer groupBaseTypeId, Long screenItemId, Boolean arrayFlag, ScreenItem screenItem, Boolean objectFlg, Boolean flagTotalCount, 
			Boolean flgConfirmScreen, List<ScreenItem> lstItemHiddensOfConfirmScreen, Map<String, String> mapOutputbeanIdAndScreenId, Integer datasourceType) {
		OutputBean outputBean = new OutputBean();
		if (tempOutputbeanId != null) {
			outputBean.setOutputBeanId(tempOutputbeanId.toString());
		}
		outputBean.setOutputBeanCode(code);
		// temp
		outputBean.setOutputBeanName(name);
		// temp
		outputBean.setMessageString(messageCode);
		outputBean.setDataType(dataType);
		outputBean.setBusinessLogicId(businessLogicId);
		outputBean.setParentOutputBeanId(parentOutputBeanId);
		outputBean.setItemSequenceNo(itemSeqNoOutput);
		// special case : if datatype is Byte, default gen arrayFlg is true
		if(DbDomainConst.JavaDataTypeOfBlogic.BYTE_DATATYPE.equals(dataType)) {
			outputBean.setArrayFlg(true);
		} else {
			if (arrayFlag == null) {
				outputBean.setArrayFlg(false);
			} else {
				outputBean.setArrayFlg(arrayFlag);
			}
		}
		// some column to check conflict with table design and table design details
		outputBean.setTblDesignId(tblDesignId);
		outputBean.setColumnId(columnId);
		outputBean.setGroupBaseTypeId(groupBaseTypeId);
		outputBean.setImpactStatus(DbDomainConst.ImpactStatus.NO_CONFLICT);
		if(objectFlg != null) {
			outputBean.setObjectFlg(objectFlg);
		} else {
			outputBean.setObjectFlg(true);
		}
		outputBean.setFlagTotalCount(flagTotalCount);
		
		// TrungDV : add lst outputbean_screenitem_mapping
		ArrayList<ScreenItemOutput> lstScreenItemMapping = new ArrayList<ScreenItemOutput>();
		// this map only use in Confirm screen
		if(mapOutputbeanIdAndScreenId == null) {
			mapOutputbeanIdAndScreenId = new HashMap<String, String>();
		}
		if (screenItem != null) {
			ScreenItemOutput screenItemOutput = new ScreenItemOutput();
			if (!DbDomainConst.LogicDataType.RADIO.equals(screenItem.getLogicalDataType())
					&& !DbDomainConst.LogicDataType.CHECKBOX.equals(screenItem.getLogicalDataType())
					&& !DbDomainConst.LogicDataType.SELECT.equals(screenItem.getLogicalDataType())
					&& !DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(screenItem.getLogicalDataType())) {
				screenItemOutput.setScreenItemId(screenItem.getScreenItemId());
				screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_DISPLAY);
				lstScreenItemMapping.add(screenItemOutput);
				mapOutputbeanIdAndScreenId.put(outputBean.getOutputBeanId() + "_" + screenItem.getScreenItemId(), outputBean.getOutputBeanId() + "_" + screenItem.getScreenItemId());
			} else {
//				if (DbDomainConst.DatasourceType.SQL_BUILDER.equals(datasourceType)) {
//				screenItemOutput = new ScreenItemOutput();
//				screenItemOutput.setScreenItemId(screenItem.getScreenItemId());
//				screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_DATASOURCE);
//				lstScreenItemMapping.add(screenItemOutput);
//				}
				screenItemOutput = new ScreenItemOutput();
				screenItemOutput.setScreenItemId(screenItem.getScreenItemId());
				screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_SELECT);
				lstScreenItemMapping.add(screenItemOutput);
			}
		}
		if(flgConfirmScreen != null && flgConfirmScreen && FunctionCommon.isNotEmpty(lstItemHiddensOfConfirmScreen)) {
			Set<String> hashSetKey = new HashSet<String>();
			for(String key : mapOutputbeanIdAndScreenId.keySet()) {
				hashSetKey.add(key);
			}
			for (ScreenItem itemHidden : lstItemHiddensOfConfirmScreen) {
				if (itemHidden.getColumnId().equals(screenItem.getColumnId())) {
					if(!hashSetKey.contains(outputBean.getOutputBeanId() + "_" +itemHidden.getScreenItemId())) {
						ScreenItemOutput screenItemOutput = new ScreenItemOutput();
						screenItemOutput.setScreenItemId(itemHidden.getScreenItemId());
						screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_DISPLAY);
						lstScreenItemMapping.add(screenItemOutput);
						mapOutputbeanIdAndScreenId.put(outputBean.getOutputBeanId() + "_" + itemHidden.getScreenItemId(), outputBean.getOutputBeanId() + "_" + itemHidden.getScreenItemId());
					}
				}
			}
		}
		// special key : if outputbean is total count, map with screen area id
		if(outputBean.getFlagTotalCount()) {
			if(FunctionCommon.isNotEmpty(screenDesign.getScreenAreas())) {
				ScreenArea areaResult = null;
				for(ScreenArea area : screenDesign.getScreenAreas()) {
					if(ScreenDesignConst.AreaTypeAction.PAGEABLE.equals(area.getAreaTypeAction()) && ScreenDesignConst.AreaType.LIST.equals(area.getAreaType())) {
						areaResult = area;
						break;
					}
				}
				if(areaResult != null) {
					ScreenItemOutput screenItemOutput = new ScreenItemOutput();
					screenItemOutput.setScreenItemId(areaResult.getScreenAreaId());
					screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_TOTAL_COUNT);
					lstScreenItemMapping.add(screenItemOutput);
				}
			}

		}
		outputBean.setLstScreenItemMapping(lstScreenItemMapping);
		
		return outputBean;
	}

	/**
	 * @param tempObjDefId
	 * @param code
	 * @param name
	 * @param dataType
	 * @param businessLogicId
	 * @param parentId
	 * @param itemSeqNoObjDef
	 * @param keyType
	 * @param tblDesignId
	 * @param columnId
	 * @param groupBaseTypeId
	 * @param arrayFlg
	 * @param tblDesignName
	 * @param tblDesignCode
	 * @return
	 */
	public static ObjectDefinition contructObjectDefinition(Long tempObjDefId, String code, String name, Integer dataType, Long businessLogicId, String parentId, Integer itemSeqNoObjDef, String keyType, Long tblDesignId, Long columnId, Integer groupBaseTypeId, Boolean arrayFlg, String tblDesignName, String tblDesignCode, Boolean objectFlg, Boolean flagTotalCount, Integer typeListInsertOrUpdate) {
		ObjectDefinition objectDefinition = new ObjectDefinition();
		if (tempObjDefId != null) {
			objectDefinition.setObjectDefinitionId(tempObjDefId.toString());
		}
		objectDefinition.setObjectDefinitionCode(code);
		// temp
		objectDefinition.setObjectDefinitionName(name);
		// temp
		objectDefinition.setDataType(dataType);
		objectDefinition.setBusinessLogicId(businessLogicId);
		objectDefinition.setParentObjectDefinitionId(parentId);
		objectDefinition.setItemSequenceNo(itemSeqNoObjDef);
		objectDefinition.setKeyType(keyType);
		// some column to check conflict with table design and table design
		// details
		objectDefinition.setTblDesignId(tblDesignId);
		objectDefinition.setColumnId(columnId);
		objectDefinition.setGroupBaseTypeId(groupBaseTypeId);
		objectDefinition.setImpactStatus(DbDomainConst.ImpactStatus.NO_CONFLICT);
		if(JavaDataTypeOfBlogic.BYTE_DATATYPE.equals(dataType)) {
			objectDefinition.setArrayFlg(true);
		} else {
			if (arrayFlg == null) {
				objectDefinition.setArrayFlg(false);
			} else {
				objectDefinition.setArrayFlg(arrayFlg);
			}
		}
		objectDefinition.setTblDesignName(tblDesignName);
		objectDefinition.setTblDesignCode(tblDesignCode);
		if(objectFlg != null) {
			objectDefinition.setObjectFlg(objectFlg);
		} else {
			objectDefinition.setObjectFlg(true);
		}
		objectDefinition.setFlagTotalCount(flagTotalCount);
		objectDefinition.setTypeListInsertOrUpdate(typeListInsertOrUpdate);
		return objectDefinition;
	}
	
	/**
	 * Build design type and object type using object definition or output bean 
	 * @param ob
	 * @param mapObjDef
	 * @param ou
	 * @param mapOutputBean
	 */
	public static void buildDesignTypeAndObjectType(ObjectDefinition ob, Map<String, ObjectDefinition> mapObjDef, OutputBean ou, Map<String, OutputBean> mapOutputBean) {
		if (ob != null && mapObjDef != null) {
			if (StringUtils.isBlank(ob.getParentObjectDefinitionId())) {
				ob.setDesignType(DbDomainConst.DesignType.DESIGN_TYPE);
				if (BusinessDesignConst.DataType.ENTITY.equals(ob.getDataType())) {
					ob.setObjectType(DbDomainConst.ObjectType.ENTITY_OBJECT);
				} else if (BusinessDesignConst.DataType.COMMON_OBJECT.equals(ob.getDataType())) {
					ob.setObjectType(DbDomainConst.ObjectType.COMMON_OBJECT);
				} else if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(ob.getDataType())) {
					ob.setObjectType(DbDomainConst.ObjectType.EXTERNAL_OBJECT);
				} else {
					ob.setObjectType(null);
				}
			} else {
				ob.setDesignType(DbDomainConst.DesignType.REFERENCE_TYPE);
				ObjectDefinition parent = mapObjDef.get(ob.getParentObjectDefinitionId());
				if (BusinessDesignConst.DataType.ENTITY.equals(parent.getDataType())) {
					ob.setObjectType(DbDomainConst.ObjectType.ENTITY_ATTRIBUTE);
				} else if (BusinessDesignConst.DataType.COMMON_OBJECT.equals(parent.getDataType())) {
					ob.setObjectType(DbDomainConst.ObjectType.COMMON_ATTRIBUTE);
				} else if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(parent.getDataType())) {
					ob.setObjectType(DbDomainConst.ObjectType.EXTERNAL_ATTRIBUTE);
				} else {
					ob.setObjectType(null);
				}
			}
		} else if (ou != null && mapOutputBean != null) {
			if (StringUtils.isBlank(ou.getParentOutputBeanId())) {
				ou.setDesignType(DbDomainConst.DesignType.DESIGN_TYPE);
				if (BusinessDesignConst.DataType.ENTITY.equals(ou.getDataType())) {
					ou.setObjectType(DbDomainConst.ObjectType.ENTITY_OBJECT);
				} else if (BusinessDesignConst.DataType.COMMON_OBJECT.equals(ou.getDataType())) {
					ou.setObjectType(DbDomainConst.ObjectType.COMMON_OBJECT);
				} else if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(ou.getDataType())) {
					ou.setObjectType(DbDomainConst.ObjectType.EXTERNAL_OBJECT);
				} else {
					ou.setObjectType(null);
				}
			} else {
				ou.setDesignType(DbDomainConst.DesignType.REFERENCE_TYPE);
				OutputBean parent = mapOutputBean.get(ou.getParentOutputBeanId());
				if (BusinessDesignConst.DataType.ENTITY.equals(parent.getDataType())) {
					ou.setObjectType(DbDomainConst.ObjectType.ENTITY_ATTRIBUTE);
				} else if (BusinessDesignConst.DataType.COMMON_OBJECT.equals(parent.getDataType())) {
					ou.setObjectType(DbDomainConst.ObjectType.COMMON_ATTRIBUTE);
				} else if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(parent.getDataType())) {
					ou.setObjectType(DbDomainConst.ObjectType.EXTERNAL_ATTRIBUTE);
				} else {
					ou.setObjectType(null);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param inputBean
	 * @param validateType
	 * @return
	 */
	public static ValidationCheckDetail populateValidationCheckDetail(InputBean inputBean, Integer validateType) {
		ValidationCheckDetail details = new ValidationCheckDetail();
		details.setInputBeanId(inputBean.getInputBeanId());
		details.setInputBeanCode(inputBean.getInputBeanCode());
		details.setValidationType(validateType);
		return details;
	}
	
	
	public static MessageParameter populateMessageParameter(Long businessLogicId, Integer itemSeqNo, String parameterCode, String parameterValue, Integer parameterType) {
		MessageParameter defaultParam = new MessageParameter();
		defaultParam.setBusinessLogicId(businessLogicId);
		defaultParam.setItemSequenceNo(itemSeqNo);
		defaultParam.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
		defaultParam.setParameterCode(parameterCode);
		defaultParam.setParameterValue(parameterValue);
		defaultParam.setParameterType(parameterType);
		defaultParam.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
		return defaultParam;
	}
	
}
