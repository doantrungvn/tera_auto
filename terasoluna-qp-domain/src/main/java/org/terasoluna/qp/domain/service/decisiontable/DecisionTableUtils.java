package org.terasoluna.qp.domain.service.decisiontable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.DecisionTableConditionGroup;
import org.terasoluna.qp.domain.model.DecisionTableConditionItem;
import org.terasoluna.qp.domain.model.DecisionTableInputBean;
import org.terasoluna.qp.domain.model.DecisionTableItemDesignBean;
import org.terasoluna.qp.domain.model.DecisionTableOutputBean;
import org.terasoluna.qp.domain.model.FormulaDetail;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class DecisionTableUtils {
	
	private static JsonFactory json = new JsonFactory();
	private static ObjectMapper mapper = new ObjectMapper(json);

	public static final class DataTypeInJava {
		public static final int OBJECT = 0;
		public static final int BYTE = 1;
		public static final int SHORT = 2;
		public static final int INTEGER = 3;
		public static final int LONG = 4;
		public static final int FLOAT = 5;
		public static final int DOUBLE = 6;
		public static final int CHAR = 7;
		public static final int BOOLEAN = 8;
		public static final int STRING = 9;
		public static final int BIGDECIMAL = 10;
		public static final int TIMESTAMP = 11;
		public static final int DATETIME = 12;
		public static final int TIME = 13;
		public static final int DATE = 15;
	}
	
	private static final Map<Integer, String> mOperatorHadParam = new HashMap<Integer, String>();
	static {
		mOperatorHadParam.put(1, "{0}.equals({1})");
		mOperatorHadParam.put(2, "{0} < {1}");
		mOperatorHadParam.put(3, "{0} <= {1}");
		mOperatorHadParam.put(4, "{0} > {1}");
		mOperatorHadParam.put(5, "{0} >= {1}");
		mOperatorHadParam.put(6, "!{0}.equals({1})");
	}
	
	private static final Map<Integer, String> mOperatorDefault = new HashMap<Integer, String>();
	static {
		mOperatorDefault.put(1, " == ");
		mOperatorDefault.put(2, " < ");
		mOperatorDefault.put(3, " <= ");
		mOperatorDefault.put(4, " > ");
		mOperatorDefault.put(5, " >= ");
		mOperatorDefault.put(6, " != ");
	}
	
	/**
	 * Converting list object o string json
	 * 
	 * @param objs
	 * @return
	 */
	public static String convertToJsonFromObjList(List<?> objs) {
		StringBuilder sb = new StringBuilder();
		String result = "";
		
		for(int i = 0; i < objs.size(); i++){
			try {
				String tmp = mapper.writeValueAsString(objs.get(i));
				sb.append(tmp).append(",");
				
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		}
		
		result = sb.toString().substring(0, sb.toString().length()-1);
		result = "["+result+"]";

		return result;
		
	}
	
public static List<FormulaDetail> getObjParseFromJson(String strJson) {
		
	List<FormulaDetail> arrObj = null;

	try {
		if(strJson != null && !strJson.isEmpty()) {
			arrObj = mapper.readValue(strJson, 
					TypeFactory.defaultInstance().constructCollectionType(List.class, FormulaDetail.class));
		}
		
	} catch (JsonParseException e) {
		throw new BusinessException("Parse error");
	} catch (JsonMappingException e) {
		throw new BusinessException("Field don't mappigng");
	} catch (IOException e) {
		throw new BusinessException("Error common");
	}
	
	return arrObj;
}
	
	
	/**
	 * Get list object parser from json string
	 * 
	 * @param decisionTable
	 * @return
	 */
	public static List<Object> getObjectParseFromJsons(DecisionTable decisionTable) {
		
		List<Object> arrObj = new ArrayList<Object>();
		
		List<DecisionTableInputBean> inputBeans = null;
		List<DecisionTableOutputBean> outputBeans = null;
		List<DecisionTableItemDesignBean> itemDesigns = null;
		List<DecisionTableConditionGroup> conditionGroups = null;
		List<DecisionTableConditionItem> conditionItems = null;
		
		try {
			String listInputStr = decisionTable.getListInput();
			if(listInputStr != null && !listInputStr.isEmpty()) {
				inputBeans = mapper.readValue(listInputStr, 
						TypeFactory.defaultInstance().constructCollectionType(List.class, DecisionTableInputBean.class));
			}
			
			String listOutputStr = decisionTable.getListOutput();
			if(listOutputStr != null && !listOutputStr.isEmpty()) {
				outputBeans = mapper.readValue(listOutputStr, 
						TypeFactory.defaultInstance().constructCollectionType(List.class, DecisionTableOutputBean.class));
			}
			
			String listItemConditionStr = decisionTable.getListItemCondition();
			if(listItemConditionStr != null && !listItemConditionStr.isEmpty()) {
				itemDesigns = mapper.readValue(listItemConditionStr, 
						TypeFactory.defaultInstance().constructCollectionType(List.class, DecisionTableItemDesignBean.class));
			}
			
			String listItemActionStr = decisionTable.getListItemAction();
			if(listItemActionStr != null && !listItemActionStr.isEmpty()) {
				List<DecisionTableItemDesignBean> itemDesignAct = mapper.readValue(listItemActionStr, 
						TypeFactory.defaultInstance().constructCollectionType(List.class, DecisionTableItemDesignBean.class));
				itemDesigns.addAll(itemDesignAct);
			}
			
			String listConditionGroupStr = decisionTable.getListConditionGroup();
			if(listConditionGroupStr != null && !listConditionGroupStr.isEmpty()) {
				conditionGroups = mapper.readValue(listConditionGroupStr, 
						TypeFactory.defaultInstance().constructCollectionType(List.class, DecisionTableConditionGroup.class));
				
				String listConditionItemStr = decisionTable.getListConditionItem();
				if(listConditionItemStr != null && !listConditionItemStr.isEmpty()) {
					conditionItems = mapper.readValue(listConditionItemStr, 
							TypeFactory.defaultInstance().constructCollectionType(List.class, DecisionTableConditionItem.class));
				}
			}
			
		} catch (JsonParseException e) {
			throw new BusinessException("Parse error");
		} catch (JsonMappingException e) {
			throw new BusinessException("Field don't mapping");
		} catch (IOException e) {
			throw new BusinessException("Error common");
		}
		
		arrObj.add(inputBeans);
		arrObj.add(outputBeans);
		arrObj.add(itemDesigns);
		arrObj.add(conditionGroups);
		arrObj.add(conditionItems);
		arrObj.add(decisionTable);
		
		return arrObj;
	}
	
	public static List<?> toObjectLst(String objJson, Class<?> clazz){

		try {
			List<?> list = mapper.readValue(objJson, 
					TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));

			return list;
		}  catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Divide data for condition && action
	 * 
	 * @param itemDesign
	 * @return itemDesigns
	 */
	public static List<List<DecisionTableItemDesignBean>> getDataItemDesignType(List<DecisionTableItemDesignBean> itemDesign) {
		// Initializing
		List<List<DecisionTableItemDesignBean>> itemDesigns = new ArrayList<List<DecisionTableItemDesignBean>>();
		List<DecisionTableItemDesignBean> itemDesignRule = new ArrayList<DecisionTableItemDesignBean>();
		List<DecisionTableItemDesignBean> itemDesignAction = new ArrayList<DecisionTableItemDesignBean>();
			
		for(DecisionTableItemDesignBean item : itemDesign) {
			// In the case of input bean else output bean
			if(item.getItemType() == 1){
				itemDesignRule.add(item);
			} else {
				itemDesignAction.add(item);
			}
		}
		
		itemDesigns.add(itemDesignRule);
		itemDesigns.add(itemDesignAction);
		
		return itemDesigns;
	}
	
	public static String settingValueByDataType(int dataType, String value) {
		StringBuilder valueProcess = new StringBuilder();
		switch (dataType) {
			case DataTypeInJava.BYTE:
				valueProcess.append("(byte)").append(value);
				break;	
			case DataTypeInJava.SHORT:
				valueProcess.append("(short)").append(value);
				break;	
			case DataTypeInJava.INTEGER:
				valueProcess.append("(int)").append(value);
				break;	
			case DataTypeInJava.LONG:
				valueProcess.append("(long)").append(value);
				break;	
			case DataTypeInJava.FLOAT:
				valueProcess.append("(float)").append(value);
				break;	
			case DataTypeInJava.DOUBLE:
				valueProcess.append("(double)").append(value);
				break;
			case DataTypeInJava.BOOLEAN:
				valueProcess.append(StringUtils.trim(value).equals("0")?"false":"true");
				break;
			case DataTypeInJava.CHAR:
				valueProcess.append("\'").append(value).append("\'");
				break;
			case DataTypeInJava.STRING:
				valueProcess.append("\"").append(value).append("\"");
				break;
			case DataTypeInJava.BIGDECIMAL:
				valueProcess.append("BigDecimal.valueOf(").append(value).append(")");
				break;
			case DataTypeInJava.TIMESTAMP:
				valueProcess.append("DateUtils.parseToTimestamp(").append("\"").append(value).append("\"").append(")");
				break;
			case DataTypeInJava.DATETIME:
			case DataTypeInJava.TIME:
			case DataTypeInJava.DATE:
				valueProcess.append("DateUtils.parseToDate(").append("\"").append(value).append("\"").append(")");
				break;
		}
		
		return valueProcess.toString()==null?"":valueProcess.toString();
	}
	
	public static String getOperatorByDataType(int dataType, int opertatorType) {
		String valueProcess = null;

		switch (dataType) {
			case DataTypeInJava.BYTE:
			case DataTypeInJava.SHORT:
			case DataTypeInJava.INTEGER:
			case DataTypeInJava.LONG:
			case DataTypeInJava.FLOAT:
			case DataTypeInJava.DOUBLE:
			case DataTypeInJava.BOOLEAN:
			case DataTypeInJava.CHAR:
			case DataTypeInJava.STRING:
				valueProcess = mOperatorHadParam.getOrDefault(opertatorType, "");
				break;
			case DataTypeInJava.BIGDECIMAL:
			case DataTypeInJava.TIMESTAMP:
			case DataTypeInJava.DATETIME:
			case DataTypeInJava.TIME:
			case DataTypeInJava.DATE:
				valueProcess = "{0}.compareTo({1}) "+mOperatorDefault.getOrDefault(opertatorType, "")+" 0";
				break;
		}

		return valueProcess.toString()==null?"":valueProcess.toString();
	}
}
