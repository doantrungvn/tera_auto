package org.terasoluna.qp.domain.service.businessdesign;

import java.util.ArrayList;
import java.util.List;

import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.BusinessDesignMessageConst;
import org.terasoluna.qp.domain.model.SequenceConnector;
import org.terasoluna.qp.domain.model.SequenceLogic;
import org.terasoluna.qp.domain.model.ValidationCheckDetail;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class BusinessDesignHelper {
	private static JsonFactory json = new JsonFactory();
	private static ObjectMapper mapper = new ObjectMapper(json);
	private static final String DOT = ".";
	private static final String DOMAIN = "domain";
	private static final String SERVICE = "service";
	private static final String COMMON_SERVICE_CUSTOMIZE = "commoncustomize";
	private static final String OPEN_PARENTHESIS = "(";
	private static final String CLOSE_PARENTHESIS = ")";

	public static List<SequenceLogic> parseSequenceLogic(String strJson) throws BusinessException {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		if (strJson != null && !strJson.isEmpty()) {
			try {
				lstSequenceLogic = mapper.readValue(strJson, TypeFactory.defaultInstance().constructCollectionType(List.class, SequenceLogic.class));
			} catch (Exception ex) {
				throw new BusinessException(ex.getMessage());
			}
		}
		return lstSequenceLogic;
	}

	public static List<ValidationCheckDetail> parseValidationCheckDetail(String strJson) throws BusinessException {
		List<ValidationCheckDetail> lstCheckDetails = new ArrayList<ValidationCheckDetail>();
		if (strJson != null && !strJson.isEmpty()) {
			try {
				lstCheckDetails = mapper.readValue(strJson, TypeFactory.defaultInstance().constructCollectionType(List.class, ValidationCheckDetail.class));
			} catch (Exception ex) {
				throw new BusinessException(ex.getMessage());
			}
		}
		return lstCheckDetails;
	}

	public static List<SequenceConnector> parseSequenceConnector(String strJson) throws BusinessException {
		List<SequenceConnector> lstSequenceConnectors = new ArrayList<SequenceConnector>();
		if (strJson != null && !strJson.isEmpty()) {
			try {
				lstSequenceConnectors = mapper.readValue(strJson, TypeFactory.defaultInstance().constructCollectionType(List.class, SequenceConnector.class));
			} catch (Exception ex) {
				throw new BusinessException(ex.getMessage());
			}
		}
		return lstSequenceConnectors;
	}

	public static SequenceLogic assignStyleComponent(SequenceLogic objLogic, boolean isView) {
		switch (objLogic.getComponentType()) {
			case 1:
				objLogic.setImagePath("start.png");
				objLogic.setCssClass("bdesign-node-one");
				objLogic.setPrefixLabel(MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0001));
				break;
			case 2:
				objLogic.setImagePath("excution.png");
				if (isView) {
					objLogic.setActionPath("openModalExecution(this,true)");
				} else {
					objLogic.setActionPath("openModalExecution(this)");
				}
				objLogic.setCssClass("bdesign-node-default");
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0002) + CLOSE_PARENTHESIS);
				break;
			case 3:
				objLogic.setImagePath("validationcheck.png");
				if (isView) {
					objLogic.setActionPath("openModalValidationCheck(this,true)");
				} else {
					objLogic.setActionPath("openModalValidationCheck(this)");
				}
				objLogic.setCssClass("bdesign-node-default");
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0003) + CLOSE_PARENTHESIS);
				break;
			case 4:
				objLogic.setImagePath("businesscheck.png");
				if (isView) {
					objLogic.setActionPath("openModalBusinessCheck(this,true)");
				} else {
					objLogic.setActionPath("openModalBusinessCheck(this)");
				}
				objLogic.setCssClass("bdesign-node-default");
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0004) + CLOSE_PARENTHESIS);
				break;
			case 5:
				objLogic.setImagePath("decision.png");
				if (isView) {
					objLogic.setActionPath("openModalDecision(this,true)");
				} else {
					objLogic.setActionPath("openModalDecision(this)");
				}
				objLogic.setCssClass("bdesign-node-default");
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0005) + CLOSE_PARENTHESIS);
				break;
			case 6:
				objLogic.setImagePath("advanced.png");
				if (isView) {
					objLogic.setActionPath("openModalAdvance(this,true)");
				} else {
					objLogic.setActionPath("openModalAdvance(this)");
				}
				objLogic.setCssClass("bdesign-node-two");
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0006) + CLOSE_PARENTHESIS);
				break;
			case 7:
				objLogic.setImagePath("common.png");
				if (isView) {
					objLogic.setActionPath("openModalCommon(this,true)");
				} else {
					objLogic.setActionPath("openModalCommon(this)");
				}
				objLogic.setCssClass("bdesign-node-two");
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0007) + CLOSE_PARENTHESIS);
				break;
			case 8:
				objLogic.setImagePath("assign.png");
				if (isView) {
					objLogic.setActionPath("openModalAssign(this,true)");
				} else {
					objLogic.setActionPath("openModalAssign(this)");
				}
				objLogic.setCssClass("bdesign-node-one");
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0008) + CLOSE_PARENTHESIS);
				break;
			case 9:
				objLogic.setImagePath("if.png");
				if (isView) {
					objLogic.setActionPath("openModalIf(this,true)");
				} else {
					objLogic.setActionPath("openModalIf(this)");
				}
				objLogic.setCssClass("bdesign-node-one");
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0009) + CLOSE_PARENTHESIS);
				break;
			case 10:
				objLogic.setImagePath("foreach.png");
				if (isView) {
					objLogic.setActionPath("openModalLoop(this,true)");
				} else {
					objLogic.setActionPath("openModalLoop(this)");
				}
				objLogic.setCssClass("bdesign-node-one");
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0010) + CLOSE_PARENTHESIS);
				break;
			case 11:
				objLogic.setImagePath("feedback.png");
				if (isView) {
					objLogic.setActionPath("openModalFeedback(this,true)");
				} else {
					objLogic.setActionPath("openModalFeedback(this)");
				}
				objLogic.setCssClass("bdesign-node-one");
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0011) + CLOSE_PARENTHESIS);
				break;
			case 12:
				objLogic.setImagePath("navigator.png");
				if (isView) {
					objLogic.setActionPath("openModalNavigator(this,true)");
				} else {
					objLogic.setActionPath("openModalNavigator(this)");
				}
				objLogic.setCssClass("bdesign-node-one");
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0012) + CLOSE_PARENTHESIS);
				break;
			case 13:
				if (!isView) {
					objLogic.setActionPath("deleteNode(this)");
				}
				objLogic.setImagePath("end.png");
				objLogic.setCssClass("bdesign-node-three");
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0013) + CLOSE_PARENTHESIS);
				break;
			case 14:
				objLogic.setImagePath("nestedlogic.png");
				if (isView) {
					objLogic.setActionPath("openModalNestedLogic(this,true)");
				} else {
					objLogic.setActionPath("openModalNestedLogic(this)");
				}
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0126) + CLOSE_PARENTHESIS);
				break;
			case 15:
				objLogic.setImagePath("common.png");
				if (isView) {
					objLogic.setActionPath("openModalFileOperation(this,true)");
				} else {
					objLogic.setActionPath("openModalFileOperation(this)");
				}
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0125) + CLOSE_PARENTHESIS);
				break;
			case 16:
				objLogic.setImagePath("common.png");
				if (isView) {
					objLogic.setActionPath("openModalReadFile(this,true)");
				} else {
					objLogic.setActionPath("openModalReadFile(this)");
				}
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0081)+ CLOSE_PARENTHESIS);
				break;
			case 17:
				objLogic.setImagePath("common.png");
				if (isView) {
					objLogic.setActionPath("openModalExportFile(this,true)");
				} else {
					objLogic.setActionPath("openModalExportFile(this)");
				}
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0026) + CLOSE_PARENTHESIS);
				break;
			case 18:
				objLogic.setImagePath("common.png");
				if (isView) {
					objLogic.setActionPath("openModalTransaction(this,true)");
				} else {
					objLogic.setActionPath("openModalTransaction(this)");
				}
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0127) + CLOSE_PARENTHESIS);
				break;
			case 19:
				objLogic.setImagePath("end.png");
				objLogic.setCssClass("bdesign-node-three");
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0128) + CLOSE_PARENTHESIS);
				break;
			case 20:
				objLogic.setImagePath("email.png");
				if (isView) {
					objLogic.setActionPath("openModalEmail(this,true)");
				} else {
					objLogic.setActionPath("openModalEmail(this)");
				}
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0143) + CLOSE_PARENTHESIS);
				break;
			case 21:
				objLogic.setImagePath("log.png");
				if (isView) {
					objLogic.setActionPath("openModalLog(this,true)");
				} else {
					objLogic.setActionPath("openModalLog(this)");
				}
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0144) + CLOSE_PARENTHESIS);
				break;
			case 22:
				objLogic.setImagePath("utility.png");
				if (isView) {
					objLogic.setActionPath("openModalUtility(this,true)");
				} else {
					objLogic.setActionPath("openModalUtility(this)");
				}
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0148) + CLOSE_PARENTHESIS);
				break;
			case 23:
				objLogic.setImagePath("download.png");
				if (isView) {
					objLogic.setActionPath("openModalDownloadFile(this,true)");
				} else {
					objLogic.setActionPath("openModalDownloadFile(this)");
				}
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0162) + CLOSE_PARENTHESIS);
				break;
			case 24:
				objLogic.setImagePath("exception.png");
				if (isView) {
					objLogic.setActionPath("openModalException(this,true)");
				} else {
					objLogic.setActionPath("openModalException(this)");
				}
				objLogic.setPrefixLabel(OPEN_PARENTHESIS + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BLOGICCOMPONENT_0196) + CLOSE_PARENTHESIS);
				break;
			default:
				break;
		}
		return objLogic;
	}

	// public static String
	// convertMessageParameterToString(List<MessageParameter> lstMessage) {
	// String result = "";
	// for (MessageParameter objMessageParameter : lstMessage) {
	// result += objMessageParameter.getParameterCode() == null ? "" :
	// objMessageParameter.getParameterCode() +
	// BusinessDesignConst.JS_SPLIT_COL;
	// result += objMessageParameter.getParameterCodeAutocomplete() == null ? ""
	// : objMessageParameter.getParameterCodeAutocomplete() +
	// BusinessDesignConst.JS_SPLIT_COL;
	// result += objMessageParameter.getMessageLevel() == null ? "" :
	// objMessageParameter.getMessageLevel() + BusinessDesignConst.JS_SPLIT_COL;
	// result += BusinessDesignConst.JS_SPLIT_ROW;
	// }
	// return result;
	// }

	// public static String
	// convertIfConditionDetailsToString(List<IfConditionDetail>
	// lstConditionDetails){
	// String result = "";
	// for(IfConditionDetail objCondition : lstConditionDetails){
	// result+=objCondition.getCaption() + BusinessDesignConst.JS_SPLIT_COL;
	// result+=objCondition.getRemark() + BusinessDesignConst.JS_SPLIT_COL;
	// result+=BusinessDesignConst.JS_SPLIT_ROW;
	// }
	// return result;
	// }

	// public static String convertNavigatorDetailToString(List<NavigatorDetail>
	// lstDetails) {
	// String result = "";
	// for (NavigatorDetail objDetail : lstDetails) {
	// result += objDetail.getInputBeanId() + BusinessDesignConst.JS_SPLIT_COL;
	// result += objDetail.getInputBeanName() +
	// BusinessDesignConst.JS_SPLIT_COL;
	// result += objDetail.getInputBeanCode() +
	// BusinessDesignConst.JS_SPLIT_COL;
	// result += objDetail.getDataType() + BusinessDesignConst.JS_SPLIT_COL;
	// result += objDetail.getParameterId() + BusinessDesignConst.JS_SPLIT_COL;
	// result += objDetail.getParameterIdAutocomplete() +
	// BusinessDesignConst.JS_SPLIT_COL;
	// result += objDetail.getParameterScope() +
	// BusinessDesignConst.JS_SPLIT_COL;
	// result += BusinessDesignConst.JS_SPLIT_ROW;
	// }
	// return result;
	// }

	/**
	 * convert physical type to java type
	 *
	 * @param physical
	 * @return Integer java type
	 */
	public static Integer convertJavaTypeFromBaseType(int baseType) {
		Integer result = null;
		switch (baseType) {
			case DbDomainConst.BaseType.CHARACTER_VARYING_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE;
				break;
			case DbDomainConst.BaseType.TEXT_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE;
				break;
			case DbDomainConst.BaseType.BYTE_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.BYTE_DATATYPE;
				break;
			case DbDomainConst.BaseType.INTEGER_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.INT_DATATYPE;
				break;
			case DbDomainConst.BaseType.SMALLINT_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.SHORT_DATATYPE;
				break;
			case DbDomainConst.BaseType.BIGINT_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.LONG_DATATYPE;
				break;
			case DbDomainConst.BaseType.SERIAL_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.INT_DATATYPE;
				break;
			case DbDomainConst.BaseType.BIGSERIAL_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.LONG_DATATYPE;
				break;
			case DbDomainConst.BaseType.FLOAT_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.FLOAT_DATATYPE;
				break;
			case DbDomainConst.BaseType.DATE_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.DATE_DATATYPE;
				break;
			case DbDomainConst.BaseType.CHAR_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE;
				break;
				// fix follow ticket #656
			case DbDomainConst.BaseType.NUMERIC_BASETYPE:
			case DbDomainConst.BaseType.CURRENCY_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.BIGDECIMAL_DATATYPE;
				break;
			case DbDomainConst.BaseType.DOUBLE_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.DOUBLE_DATATYPE;
				break;
			case DbDomainConst.BaseType.BOOLEAN_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.BOOLEAN_DATATYPE;
				break;
			case DbDomainConst.BaseType.TIME_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.TIME_DATATYPE;
				break;
			case DbDomainConst.BaseType.DATETIME_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.DATETIME_DATATYPE;
				break;
			case DbDomainConst.BaseType.TIMESTAMP_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.TIMESTAMP_DATATYPE;
				break;
			case DbDomainConst.BaseType.BINARY_BASETYPE:
				result = DbDomainConst.JavaDataTypeOfBlogic.BYTE_DATATYPE;
				break;
			default:
				break;
		}
		
		return result;
	}

	public static List<Integer> convertFmtCodeToType(String fmtCode) {
		List<Integer> result = new ArrayList<Integer>();
		switch (fmtCode) {
			case BusinessDesignConst.ValidationRuleCode.Alphabet:
				result.add(BusinessDesignConst.ValidateType.ALPHABET);
				break;
			case BusinessDesignConst.ValidationRuleCode.Alphanumeric:
				result.add(BusinessDesignConst.ValidateType.ALPHA_NUMERIC);
				break;
			case BusinessDesignConst.ValidationRuleCode.Binary:
				result.add(BusinessDesignConst.ValidateType.BINARY);
				break;
				//		case BusinessDesignConst.ValidationRuleCode.Boolean:
				//			break;
			case BusinessDesignConst.ValidationRuleCode.CreaditCard:
				result.add(BusinessDesignConst.ValidateType.CREDITCARD_NUMBER);
				break;
			case BusinessDesignConst.ValidationRuleCode.Currency:
				result.add(BusinessDesignConst.ValidateType.CURRENCY);
				break;
			case BusinessDesignConst.ValidationRuleCode.Date:
				result.add(BusinessDesignConst.ValidateType.DATE);
				break;
			case BusinessDesignConst.ValidationRuleCode.DateTime:
				result.add(BusinessDesignConst.ValidateType.DATE_TIME);
				break;
			case BusinessDesignConst.ValidationRuleCode.Day:
				result.add(BusinessDesignConst.ValidateType.DAY);
				break;
			case BusinessDesignConst.ValidationRuleCode.Decimal:
				result.add(BusinessDesignConst.ValidateType.DECIMAL);
				break;
			case BusinessDesignConst.ValidationRuleCode.Double:
				result.add(BusinessDesignConst.ValidateType.DOUBLE);
				break;
			case BusinessDesignConst.ValidationRuleCode.Email:
				result.add(BusinessDesignConst.ValidateType.EMAIL);
				break;
			case BusinessDesignConst.ValidationRuleCode.EmCharacter:
				result.add(BusinessDesignConst.ValidateType.EM_CHARACTER);
				break;
			case BusinessDesignConst.ValidationRuleCode.EnCharacter:
				result.add(BusinessDesignConst.ValidateType.EN_CHARACTER);
				break;
			case BusinessDesignConst.ValidationRuleCode.Float:
				result.add(BusinessDesignConst.ValidateType.FLOAT);
				break;
			case BusinessDesignConst.ValidationRuleCode.Hiragana:
				result.add(BusinessDesignConst.ValidateType.HIRAGANA);
				break;
			case BusinessDesignConst.ValidationRuleCode.Hour:
				result.add(BusinessDesignConst.ValidateType.HOUR);
				break;
			case BusinessDesignConst.ValidationRuleCode.Integer:
				result.add(BusinessDesignConst.ValidateType.INTEGER);
				break;
			case BusinessDesignConst.ValidationRuleCode.Kanji:
				result.add(BusinessDesignConst.ValidateType.KANJI);
				break;
			case BusinessDesignConst.ValidationRuleCode.Katakana:
				result.add(BusinessDesignConst.ValidateType.KATAKANA);
				break;
			case BusinessDesignConst.ValidationRuleCode.Long:
				result.add(BusinessDesignConst.ValidateType.LONG);
				break;
			case BusinessDesignConst.ValidationRuleCode.Minute:
				result.add(BusinessDesignConst.ValidateType.MINUTE);
				break;
			case BusinessDesignConst.ValidationRuleCode.Month:
				result.add(BusinessDesignConst.ValidateType.MONTH);
				break;
				//		case BusinessDesignConst.ValidationRuleCode.Name:
				//			break;
				//		case BusinessDesignConst.ValidationRuleCode.Numeric:
				//			break;
			case BusinessDesignConst.ValidationRuleCode.Phone:
				result.add(BusinessDesignConst.ValidateType.PHONE);
				break;
			case BusinessDesignConst.ValidationRuleCode.Postcode:
				result.add(BusinessDesignConst.ValidateType.POSTCODE);
				break;
				//		case BusinessDesignConst.ValidationRuleCode.Remark:
				//			break;
			case BusinessDesignConst.ValidationRuleCode.Second:
				result.add(BusinessDesignConst.ValidateType.SECOND);
				break;
			case BusinessDesignConst.ValidationRuleCode.Space:
				result.add(BusinessDesignConst.ValidateType.SPACE);
				break;
			case BusinessDesignConst.ValidationRuleCode.Symbol:
				result.add(BusinessDesignConst.ValidateType.SYMBOL);
				break;
			case BusinessDesignConst.ValidationRuleCode.Time:
				result.add(BusinessDesignConst.ValidateType.TIME);
				break;
			case BusinessDesignConst.ValidationRuleCode.Timestamp:
				result.add(BusinessDesignConst.ValidateType.TIMESTAMP);
				break;	
			case BusinessDesignConst.ValidationRuleCode.Week:
				result.add(BusinessDesignConst.ValidateType.WEEK);
				break;
			case BusinessDesignConst.ValidationRuleCode.Year:
				result.add(BusinessDesignConst.ValidateType.YEAR);
				break;
			case BusinessDesignConst.ValidationRuleCode.ZenkakuAlphabet:
				result.add(BusinessDesignConst.ValidateType.ZENKAKU_ALPHABET);
				break;
			case BusinessDesignConst.ValidationRuleCode.ZenkakuKatakana:
				result.add(BusinessDesignConst.ValidateType.ZENKAKU_KATAKANA);
				break;
			case BusinessDesignConst.ValidationRuleCode.ZenkakuNumeric:
				result.add(BusinessDesignConst.ValidateType.ZENKAKU_NUMERIC);
				break;
				//		case BusinessDesignConst.ValidationRuleCode.ZenkakuSpaces:
				//			break;
			case BusinessDesignConst.ValidationRuleCode.ZenkakuSymbol:
				result.add(BusinessDesignConst.ValidateType.ZENKAKU_SYMBOL);
				break;
			default:
				break;
		}
		return result;
	}



	/**
	 * generate package name
	 *
	 * @param projectCode
	 * @return string packageName
	 */
	public static String generatePackageName(String projectCode,String packageNm) {
		if (projectCode != null && !"".equals(projectCode)) {
			projectCode = projectCode.toLowerCase();
		}
		StringBuilder packageName = new StringBuilder();
		//String packageNm = SessionUtils.getCurrentProject().getPackageName();
		packageName.append(packageNm).append(DOT).append(DOMAIN).append(DOT).append(SERVICE).append(DOT).append(COMMON_SERVICE_CUSTOMIZE);
		return packageName.toString();
	}

	public static String getAreaType(Integer type) {
		String typeName = "";
		switch (type) {
			case -1:
				typeName = "Header Link";
				break;
			case 0:
				typeName = "Single Entity";
				break;
			case 1:
				typeName = "List Entities";
				break;
			case 2:
				typeName = "Submit Action";
				break;
			case 3:
				typeName = "Free Element";
				break;

			default:
				break;
		}
		return typeName;
	}

	public static String getDataTypeStr(Integer dataType, boolean arrayFlg) {
		String dataTypeStr = "";
		switch (dataType) {
			case 0:
				dataTypeStr = "Object";
				break;
			case 1:
				dataTypeStr = "Byte";
				break;
			case 2:
				dataTypeStr = "Short";
				break;
			case 3:
				dataTypeStr = "Integer";
				break;
			case 4:
				dataTypeStr = "Long";
				break;
			case 5:
				dataTypeStr = "Float";
				break;
			case 6:
				dataTypeStr = "Double";
				break;
			case 7:
				dataTypeStr = "Char";
				break;
			case 8:
				dataTypeStr = "Boolean";
				break;
			case 9:
				dataTypeStr = "String";
				break;
			case 10:
				dataTypeStr = "BigDecimal";
				break;
			case 11:
				dataTypeStr = "Timestamp";
				break;
			case 12:
				dataTypeStr = "Datetime";
				break;
			case 13:
				dataTypeStr = "Time";
				break;
			case 14:
				dataTypeStr = "Entity";
				break;
			case 15:
				dataTypeStr = "Date";
				break;
			default:
				break;
		}
		if (arrayFlg) {
			dataTypeStr += "[]";
		}
		return dataTypeStr;
	}

	public static String getMappingNameOfMappingOuput(Integer mappingType,Integer logicDataType) {
		String type = "";
		switch (mappingType) {
			case 5:
				type = "(To)";
				break;
			case 4:
				type = "(From)";
				break;
			case 3:
				if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(logicDataType))
					type = "(Autocomplete Select)";
				else
					type = "(Option Select)";
				break;
			case 2:
				type = "(Datasource)";
				break;
			case 1:
				if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(logicDataType))
					type = "(Autocomplete Display)";
				else
					type = "(Option Display)";
				break;
			case 0:
				if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(logicDataType))
					type = "(Autocomplete Submit)";
				else
					type = "(Option Submit)";
				break;
			default:
				break;
		}
		return type;
	}

}

