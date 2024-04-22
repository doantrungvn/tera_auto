package org.terasoluna.qp.domain.service.autocomplete;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.gfw.common.query.QueryEscapeUtils;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.BaseType;
import org.terasoluna.qp.app.common.constants.DbDomainConst.SQLDesignType;
import org.terasoluna.qp.app.common.constants.DbDomainConst.SqlPattern;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignCondition;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOrder;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.model.SqlDesignTable;
import org.terasoluna.qp.domain.model.SqlDesignTableItem;
import org.terasoluna.qp.domain.model.SqlDesignValue;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.repository.accountprofile.AccountProfileRepository;
import org.terasoluna.qp.domain.repository.moduletablemapping.ModuleTableMappingRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.service.autocomplete.function.ToCharFunction;
import org.terasoluna.qp.domain.service.autocomplete.function.ToDateFunction;
import org.terasoluna.qp.domain.service.autocomplete.function.ToNumberFunction;
import org.terasoluna.qp.domain.service.autocomplete.function.ToTimeFunction;
import org.terasoluna.qp.domain.service.autocomplete.function.ToTimestampFunction;
import org.terasoluna.qp.domain.service.autocomplete.function.TruncFunction;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst.JoinType;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst.WhereConditionType;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeUtil;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignCompound;


@Component
public class SqlBuilderServiceImpl implements SqlBuilderService {

	@Inject
	AccountProfileRepository accountProfileRepository;
	
	@Inject
	ProjectRepository projectReponsitory;
	
	@Inject
	ModuleTableMappingRepository moduleTableMappingRepository;
	public static class Const{
		public static final List<String> KEYWORDS = new ArrayList<String>(){
			private static final long serialVersionUID = 1L;

		{
			add("SELECT");
			add("FROM");
			add("WHERE");
			add("ORDER");
			add("BY");
			add("GROUP");
			add("HAVING");
			add("INNER");
			add("JOIN");
			add("LEFT");
			add("RIGHT");
			add("CROSS");
			add("OUTER");
		}};
		public static final String SPACE = " ";
		public static final String SELECT = "SELECT";
		public static final String FROM = "FROM";
		public static final String WHERE = "WHERE";
		public static final String WHERE_BEGIN = "<where>";
		public static final String WHERE_END = "</where>";
		public static final String ORDER_BY = "ORDER BY";
		public static final String HAVING = "HAVING";
		public static final String LINE_BREAK = "\r\n";
		public static final String OPEN_PARENTHESIS = "(";
		public static final String CLOSE_PARENTHESIS = ")";
		public static final String TABLE_COLUMN_SEPARATOR = ".";
		public static final String COMMA = ",";
		public static final String TAB = "    ";
		public static final String ON = "ON";
		public static final String AND = "AND";
		public static final String ValueSeparator = "::";
		public static final String GROUP_BY = "GROUP BY";
		public static final String QUOTE = "'";
		public static final String TO_DATE_FUNCTION = "to_date(''{0}'',''{1}'')";
		public static final String TO_TIMESTAMP_FUNCTION = "to_timestamp(''{0}'',''{1}'')";
		public static final String MYBATIS_IF_PARAM = "<if test=\"{0} != null and {0} != ''''\">";
		public static final String MYBATIS_IF_PARAM2 = "<if test=\"{0} != null and {0} != '''' and {1} != null and {1} != ''''\">";
		public static final String MYBATIS_IF_END = "</if>";
		public static final String INSERT = "INSERT";
		public static final String INTO = "INTO";
		public static final String VALUES = "VALUES";
		public static final String DOT = ".";
		public static final String INPUT_PARAM = "#'{'{0}'}'";
		public static final String UPDATE = "UPDATE";
		public static final String SET = "SET";
		public static final String EQUAL = "=";
		public static final String DELETE = "DELETE";
		public static final String ALIAS = "as";
		public static final String UPPER = "UPPER";
		public static final String PERCENT = "%";
		public static final String ESCAPE = "ESCAPE '~'";
		public static final String QUERY_ESCAPE = "<bind name=\"{0}\" value=\"@org.terasoluna.gfw.common.query.QueryEscapeUtils@toContainingCondition({1})\" />";
		public static final String CDATA_BEGIN = "<![CDATA[";
		public static final String CDATA_END = "]]>";
	}
	public enum JavaDataType {
		Object(0),
		Byte(1),
		Short(2),
		Integer(3),
		Long(4),
		Float(5),
		Double(6),
		Character(7),
		Boolean(8),
		String(9),
		BigDecimal(10),
		Timestamp(11),
		Datetime(12),
		Time(13);
		
		private int value;
		
		JavaDataType(int value){
			this.value=value;
		}
		
		public Boolean equal(int type){
			return this.value == type;
		}
	}
	
	private enum FunctionCode{
		AVG("0"), COUNT("1"), FIRST("2"), LAST("3"),MAX("4"), MIN("5"),SUM("6") ;
		FunctionCode(String code){
			this.code = code;
		}
		private String code;
		public String getCode(){
			return code;
		}
	}
	
	public enum SqlOrderType{
		ASCENDING("0","ASC"),
		DESCENDING("1","DESC");
	
		private String value;
		private String display;

		SqlOrderType(String value,String display){
			this.value = value;
			this.display = display;
		}
		public boolean equals(SqlOrderType sqlOrderType){
			return value.equals(sqlOrderType.value);
		}
		public String getDisplay() {
			return display;
		}
		public static SqlOrderType getByValue(String id) {
			for (final SqlOrderType element : EnumSet.allOf(SqlOrderType.class)) {
				if (element.value.equals(id)) {
					return element;
				}
			}
			return null;
		}
		@Override
	    public String toString() {
	        return this.display;
	    }
	}
	public enum SqlOperator{
		EQUAL("0","="),
		LESS("1","<"),
		LESS_EQUAL("2","<="),
		GREATER("3",">"),
		GREATER_EQUAL("4",">="),
		NOT_EQUAL("5","!="),
		LIKE("6","LIKE"),
		BETWEEN("7","BETWEEN"),
		IS_NULL("8","IS NULL"),
		IS_NOT_NULL("9","IS NOT NULL"),
		ILIKE("10","ILIKE");
		
		private String value = "";
		private String display = "";
		SqlOperator(String value,String text){
			this.value = value;
			this.display = text;
		}
		public boolean equals(SqlOperator sqlOperator){
			return value.equals(sqlOperator.value);
		}
		public static SqlOperator getByValue(String id) {
			for (final SqlOperator element : EnumSet.allOf(SqlOperator.class)) {
				if (element.value.equals(id)) {
					return element;
				}
			}
			return null;
		}
		public String getDisplay() {
			return display;
		}
		@Override
	    public String toString() {
	        return this.display;
	    }
	}
	public enum ValueType{
		PARAMETERS(0), VALUE(1), ENTITY(2);
		private ValueType(Integer number) {
			this.number = number;
		}
		public Integer getNumber() {
			return number;
		}
		private Integer number;
		
	}
	
	@Inject
    @Named("CL_SQL_AGGREGATE_FUNCTIONS")
	private CodeList CL_AggregateFunctions;
	
	@Inject
	@Named("CL_SQL_JOIN_TYPE")
	private CodeList CL_JOIN_TYPE;
	
	@Inject
	@Named("CL_SQL_COMBINING_OPERATOR")
	private CodeList CL_COMBINING_OPERATOR;
	
	@Inject
	private TableDesignDetailRepository tableDesignDetailRepository;
	
	
	@Override
	public String buildSql(SqlDesignCompound sqlDesignCompound,String dialect, CommonModel common) {
		StringBuilder sql = null;
		if(sqlDesignCompound!=null) {
			SqlDesign sqlDesign = sqlDesignCompound.getSqlDesign();
			sql = new StringBuilder();
			
			switch (sqlDesign.getDesignType()) {
			case SQLDesignType.VIEW:
			case SQLDesignType.ADVANCED_VIEW:
				sql.append("CREATE VIEW ");
				sql.append(sqlDesign.getSqlDesignCode());
				sql.append(" AS");
				sql.append(Const.LINE_BREAK);
				break;
			case SQLDesignType.AUTOCOMPLETE:
			case SQLDesignType.ADVANCED_AUTOCOMPLETE:
				if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignInputs())){
					for(int i=0;i<=sqlDesignCompound.getSqlDesignInputs().length-1;i++){
						if(i==0){
							sqlDesignCompound.getSqlDesignInputs()[i].setSqlDesignInputCode("searchKey");
						} else {
							sqlDesignCompound.getSqlDesignInputs()[i].setSqlDesignInputCode("arg"+StringUtils.leftPad(String.valueOf(i), 2,'0'));
						}
					}
				}
				break;
			default:
				break;
			}
			if(StringUtils.isNotEmpty(sqlDesign.getSqlText())) {
				sql.append(sqlDesign.getSqlText());
			}
			
			if(!(sqlDesign == null || 
					sqlDesign.getDesignType() == 1 ||
					sqlDesign.getDesignType() == 3 ||
					sqlDesign.getDesignType() == 5 ||
					StringUtils.isNotEmpty(sqlDesign.getSqlText()))){
				
				/*
				 * comment fix code
				if (sqlDesign.getSqlPattern() != null && sqlDesign.getSqlPattern().equals(SqlPattern.UPDATE)) {
					//Sonpn:
					Long moduleId = sqlDesignCompound.getSqlDesign().getModuleId();
					
					if (moduleId != null ) {
						//get list table mapping
						ModuleTableMapping[] mapping =  moduleTableMappingRepository.findModuleTableMappingByModuleId(moduleId);
						
						if (mapping != null && mapping.length > 1) {
							if (mapping[0].getTableMappingType() != null && mapping[0].getTableMappingType().equals(0)
									&& mapping[1].getTableMappingType() != null && mapping[1].getTableMappingType().equals(1)) {
								
								if (sqlDesignCompound.getSqlDesignTables() != null && sqlDesignCompound.getSqlDesignTables().length > 0) {
									if (mapping[1].getTblDesignId().equals(sqlDesignCompound.getSqlDesignTables()[0].getTableId())) {
										sqlDesign.setSqlPattern(SqlPattern.INSERT);
									}
								}
								
							}
						}
						
					}
				}
				*/
				switch(sqlDesign.getSqlPattern()) {
				case SqlPattern.SELECT:
					buildSelect(sqlDesignCompound,sql, common);
					break;
				case SqlPattern.INSERT:
					buildInsert(sqlDesignCompound,sql, common);
					break;
				case SqlPattern.UPDATE:
					buildUpdate(sqlDesignCompound,sql, common);
					break;
				case SqlPattern.DELETE:
					buildDelete(sqlDesignCompound,sql, common);
					break;
				}
			}
		}
		return sql.toString().trim();
	}
	
	private void buildDelete(SqlDesignCompound sqlDesignCompound,StringBuilder builder, CommonModel common) {
		if(sqlDesignCompound!=null){
			StringBuilder deleteBuilder = builder;
			deleteBuilder.append(Const.DELETE);
			deleteBuilder.append(Const.SPACE);
			deleteBuilder.append(Const.FROM);
			deleteBuilder.append(Const.LINE_BREAK);
			deleteBuilder.append(Const.TAB);
			deleteBuilder.append(sqlDesignCompound.getSqlDesignTable()!=null?sqlDesignCompound.getSqlDesignTable().getTableCode():StringUtils.EMPTY);
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignConditions())){
				deleteBuilder.append(Const.LINE_BREAK);
				deleteBuilder.append(Const.WHERE_BEGIN).append(Const.LINE_BREAK);
				buildConditions(deleteBuilder, Arrays.asList(sqlDesignCompound.getSqlDesignConditions()),sqlDesignCompound.getSqlDesignInputs(),null,sqlDesignCompound.getSqlDesign(), common);
				deleteBuilder.append(Const.WHERE_END);
			}
		}
	}

	private void buildUpdate(SqlDesignCompound sqlDesignCompound,StringBuilder builder, CommonModel common) {
		if(sqlDesignCompound!=null){
			AccountProfile profile = accountProfileRepository.findOne(common.getCreatedBy());
			StringBuilder updateBuilder = builder;
			updateBuilder.append(Const.UPDATE);
			updateBuilder.append(Const.LINE_BREAK);
			updateBuilder.append(Const.TAB);
			updateBuilder.append(sqlDesignCompound.getSqlDesignTable().getTableMissingFlag().equals("1")?StringUtils.EMPTY:sqlDesignCompound.getSqlDesignTable().getTableCode());
			updateBuilder.append(Const.LINE_BREAK);
			updateBuilder.append(Const.SET);
			updateBuilder.append(Const.LINE_BREAK);
			Long tableId = sqlDesignCompound.getSqlDesignTable().getTableId();
			List<TableDesignDetails> tableDesigns = this.tableDesignDetailRepository.findAllByTableDesign(tableId);
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignValues())){
				for(int i=0;i<sqlDesignCompound.getSqlDesignValues().length;i++){
					SqlDesignValue sqlDesignValue = sqlDesignCompound.getSqlDesignValues()[i];
					updateBuilder.append(Const.TAB);
					updateBuilder.append(Const.CDATA_BEGIN);
					updateBuilder.append(sqlDesignCompound.getSqlDesignValues()[i].getColumnMissingFlag().equals("1")?
											StringUtils.EMPTY:sqlDesignCompound.getSqlDesignValues()[i].getColumnCode());
					updateBuilder.append(Const.SPACE);
					updateBuilder.append(Const.EQUAL);
					updateBuilder.append(Const.SPACE);
					updateBuilder.append(buildValueForUpdateSql(sqlDesignCompound, sqlDesignValue, tableDesigns, profile));
					updateBuilder.append(Const.CDATA_END);
					if(i<sqlDesignCompound.getSqlDesignValues().length-1){
						updateBuilder.append(Const.COMMA);
					}
					updateBuilder.append(Const.LINE_BREAK);
				}
			}
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignConditions())){
				updateBuilder.append(Const.LINE_BREAK);
				updateBuilder.append(Const.WHERE_BEGIN).append(Const.LINE_BREAK);
				buildConditions(updateBuilder, Arrays.asList(sqlDesignCompound.getSqlDesignConditions()),sqlDesignCompound.getSqlDesignInputs(),0, sqlDesignCompound.getSqlDesign(),common);
				updateBuilder.append(Const.WHERE_END);
			}
			
			/*
			//Sonpn: add delete for master detail
			Long moduleId = sqlDesignCompound.getSqlDesign().getModuleId();
			
			if (moduleId != null ) {
				//get list table mapping
				ModuleTableMapping[] mapping =  moduleTableMappingRepository.findModuleTableMappingByModuleId(moduleId);
				
				if (mapping != null && mapping.length > 1) {
					if (mapping[0].getTableMappingType() != null && mapping[0].getTableMappingType().equals(0)
							&& mapping[1].getTableMappingType() != null && mapping[1].getTableMappingType().equals(1)) {
						
						if (sqlDesignCompound.getSqlDesignTables() != null && sqlDesignCompound.getSqlDesignTables().length > 0) {
							if (mapping[0].getTblDesignId().equals(sqlDesignCompound.getSqlDesignTables()[0].getTableId())) {
								updateBuilder.append(";");
								updateBuilder.append(Const.LINE_BREAK);
								updateBuilder.append("DELETE FROM " + mapping[1].getTblDesignCode());
								updateBuilder.append(Const.LINE_BREAK);
								updateBuilder.append(Const.WHERE);
								buildConditionsDelete(updateBuilder, Arrays.asList(sqlDesignCompound.getSqlDesignConditions()),sqlDesignCompound.getSqlDesignInputs());
							}
						}
						
					}
				}
				
			}*/
		}
	}
	
	private String buildValueForUpdateSql(SqlDesignCompound sqlDesignCompound, SqlDesignValue sqlDesignValue, List<TableDesignDetails> tableDesigns, AccountProfile profile){
		if(sqlDesignValue.getValueType() == null || sqlDesignValue.getValueType().equals(ValueType.PARAMETERS.getNumber())){
			return buildSqlInsertUpdate(ValueType.PARAMETERS, sqlDesignCompound, sqlDesignValue, profile);
		}
		if(sqlDesignValue.getValueType().equals(ValueType.VALUE.getNumber())){
			return buildSqlInsertUpdate(ValueType.VALUE, sqlDesignCompound, sqlDesignValue, profile);
		}
		if(sqlDesignValue.getValueType().equals(ValueType.ENTITY.getNumber())){
			return buildSqlInsertUpdate(ValueType.ENTITY, sqlDesignCompound, sqlDesignValue, tableDesigns, profile);
		}
		return StringUtils.EMPTY;
	}
	
	@SuppressWarnings("unused")
	private void buildConditionsDelete(StringBuilder selectBuilder, List<SqlDesignCondition> whereConditions,SqlDesignInput[] sqlDesignInputs, CommonModel common) {
		AccountProfile profile = accountProfileRepository.findOne(common.getCreatedBy());
		Project project = this.projectReponsitory.findById(common.getProjectId(), common.getCreatedBy());
		for(int i=0;i<whereConditions.size();i++){
			selectBuilder.append(Const.LINE_BREAK);
			selectBuilder.append(Const.TAB);
			SqlDesignCondition sqlDesignCondition = whereConditions.get(i);
			if(i>0){
				selectBuilder.append(CL_COMBINING_OPERATOR.asMap().get(sqlDesignCondition.getLogicCode()));
				selectBuilder.append(Const.SPACE);
			}
			if(StringUtils.isNotEmpty(sqlDesignCondition.getFunctionCode())){
				selectBuilder.append(CL_AggregateFunctions.asMap().get(sqlDesignCondition.getFunctionCode()));
				selectBuilder.append(Const.OPEN_PARENTHESIS);
			}
			//SonPN comment
			//selectBuilder.append(sqlDesignCondition.getLeftTableCode());
			//selectBuilder.append(Const.TABLE_COLUMN_SEPARATOR);
			//sonpn
			selectBuilder.append(sqlDesignCondition.getLeftColumnCode());
			if(StringUtils.isNotEmpty(sqlDesignCondition.getFunctionCode())){
				selectBuilder.append(Const.CLOSE_PARENTHESIS);
			}
			selectBuilder.append(Const.SPACE);
			selectBuilder.append(changeLikeToILike(sqlDesignCondition, project));
			selectBuilder.append(Const.SPACE);
			if(sqlDesignCondition.getConditionType()==0){
				SqlOperator operator = SqlOperator.getByValue(sqlDesignCondition.getOperatorCode());
				if(operator ==SqlOperator.BETWEEN){
					String[] values = sqlDesignCondition.getValue().split(Const.ValueSeparator);
					switch(sqlDesignCondition.getDataType()){
					case BaseType.CHARACTER_VARYING_BASETYPE:
					case BaseType.TEXT_BASETYPE:
					case BaseType.CHAR_BASETYPE:
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(values[0]);
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(values[1]);
						selectBuilder.append(Const.QUOTE);
						break;
					case BaseType.DATE_BASETYPE:
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,values[0],profile.getDateFormat()));
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,values[1],profile.getDateFormat()));
						break;
					case BaseType.DATETIME_BASETYPE:
					case BaseType.TIMESTAMP_BASETYPE:
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,values[0],profile.getDateTimeFormat()));
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,values[1],profile.getDateTimeFormat()));
						selectBuilder.append(Const.QUOTE);
						break;
					case BaseType.TIME_BASETYPE:
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,values[0],profile.getTimeFormat()));
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,values[1],profile.getTimeFormat()));
						selectBuilder.append(Const.QUOTE);
						break;
					default:
						selectBuilder.append(values[0]);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(values[1]);
						break;
					}
				} else if(operator != SqlOperator.IS_NOT_NULL & operator != SqlOperator.IS_NULL){
					switch(sqlDesignCondition.getDataType()){
					case BaseType.CHARACTER_VARYING_BASETYPE:
					case BaseType.TEXT_BASETYPE:
					case BaseType.CHAR_BASETYPE:
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(sqlDesignCondition.getValue());
						selectBuilder.append(Const.QUOTE);
						break;
					case BaseType.DATE_BASETYPE:
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,sqlDesignCondition.getValue(),profile.getDateFormat()));
						break;
					case BaseType.DATETIME_BASETYPE:
					case BaseType.TIMESTAMP_BASETYPE:
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,sqlDesignCondition.getValue(),profile.getDateTimeFormat()));
						break;
					case BaseType.TIME_BASETYPE:
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,sqlDesignCondition.getValue(),profile.getTimeFormat()));
						break;
					default:
						selectBuilder.append(sqlDesignCondition.getValue());
						break;
					}
				}
			} else if(sqlDesignCondition.getConditionType()==1){
				selectBuilder.append(sqlDesignCondition.getRightTableCode());
				selectBuilder.append(Const.TABLE_COLUMN_SEPARATOR);
				selectBuilder.append(sqlDesignCondition.getRightColumnCode());
			} else if(sqlDesignCondition.getConditionType()==2){
				sqlDesignCondition.setDataType(-1);
				if(SqlOperator.getByValue(sqlDesignCondition.getOperatorCode())==SqlOperator.BETWEEN){
					String[] values = sqlDesignCondition.getValue().split(Const.ValueSeparator);
					for(int j=0;i<values.length;j++){
						values[j]=MessageFormat.format(Const.INPUT_PARAM,this.getInputParam(sqlDesignInputs, values[j]));
					}
					switch(sqlDesignCondition.getDataType()){
					case BaseType.CHARACTER_VARYING_BASETYPE:
					case BaseType.TEXT_BASETYPE:
					case BaseType.CHAR_BASETYPE:
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(values[0]);
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(values[1]);
						selectBuilder.append(Const.QUOTE);
						break;
					case BaseType.DATE_BASETYPE:
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,values[0],profile.getDateFormat()));
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,values[1],profile.getDateFormat()));
						selectBuilder.append(Const.QUOTE);
						break;
					case BaseType.DATETIME_BASETYPE:
					case BaseType.TIMESTAMP_BASETYPE:
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,values[0],profile.getDateTimeFormat()));
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,values[1],profile.getDateTimeFormat()));
						selectBuilder.append(Const.QUOTE);
						break;
					case BaseType.TIME_BASETYPE:
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,values[0],profile.getTimeFormat()));
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,values[1],profile.getTimeFormat()));
						selectBuilder.append(Const.QUOTE);
						break;
					default:
						selectBuilder.append(values[0]);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(values[1]);
						break;
					}
				} else {
					//sqlDesignCondition.setValue(MessageFormat.format(Const.INPUT_PARAM,this.getInputParam(sqlDesignInputs, sqlDesignCondition.getValue())));
					switch(sqlDesignCondition.getDataType()){
					case BaseType.CHARACTER_VARYING_BASETYPE:
					case BaseType.TEXT_BASETYPE:
					case BaseType.CHAR_BASETYPE:
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(sqlDesignCondition.getValue());
						selectBuilder.append(Const.QUOTE);
						break;
					case BaseType.DATE_BASETYPE:
						selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,sqlDesignCondition.getValue(),profile.getDateFormat()));
						break;
					case BaseType.DATETIME_BASETYPE:
					case BaseType.TIMESTAMP_BASETYPE:
						selectBuilder.append(MessageFormat.format(Const.TO_TIMESTAMP_FUNCTION,sqlDesignCondition.getValue(),profile.getDateTimeFormat()));
						break;
					case BaseType.TIME_BASETYPE:
						selectBuilder.append(MessageFormat.format(Const.TO_TIMESTAMP_FUNCTION,sqlDesignCondition.getValue(),profile.getTimeFormat()));
						break;
					default:
						selectBuilder.append(sqlDesignCondition.getValue());
						break;
					}
					
				}
			}
		}
	}
	private String getFormatDateTime(AccountProfile profile, SqlDesignCondition sqlDesignCondition){
		if(StringUtils.isEmpty(sqlDesignCondition.getPatternFormat())){
			return DateUtils.getPatternFormatDateTimeByDataTypeFromProfile(sqlDesignCondition.getDataType(), profile);
		}
		String pattern = DateUtils.getPatternDateSql(sqlDesignCondition.getPatternFormat());
		Integer dataType = sqlDesignCondition.getDataType();
		switch (dataType) {
			case BaseType.DATETIME_BASETYPE:
			case BaseType.TIMESTAMP_BASETYPE:
				pattern = DateUtils.getPatternDateTimeSql(sqlDesignCondition.getPatternFormat());
				break;
			case BaseType.TIME_BASETYPE:
				pattern = DateUtils.getPatternTimeSql(sqlDesignCondition.getPatternFormat());
				break;
		}
		return pattern;
	}
	
	private void buildInsert(SqlDesignCompound sqlDesignCompound,StringBuilder builder, CommonModel common) {
		if(sqlDesignCompound!=null){
			AccountProfile profile = accountProfileRepository.findOne(common.getCreatedBy());
			StringBuilder insertBuilder = builder;
			insertBuilder.append(Const.INSERT);
			insertBuilder.append(Const.SPACE);
			insertBuilder.append(Const.INTO);
			insertBuilder.append(Const.LINE_BREAK);
			insertBuilder.append(Const.TAB);
			insertBuilder.append(sqlDesignCompound.getSqlDesignTable().getTableCode());
			insertBuilder.append(Const.OPEN_PARENTHESIS);
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignValues())){
				for(int i=0;i<sqlDesignCompound.getSqlDesignValues().length;i++){
					if(i>0){
						insertBuilder.append(Const.COMMA);
						insertBuilder.append(Const.SPACE);
					}
					insertBuilder.append(sqlDesignCompound.getSqlDesignValues()[i].getColumnCode());
				}
			}
			insertBuilder.append(Const.CLOSE_PARENTHESIS);
			insertBuilder.append(Const.LINE_BREAK);
			insertBuilder.append(Const.VALUES);
			insertBuilder.append(Const.OPEN_PARENTHESIS);
			if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignValues())){
				for(int i=0;i<sqlDesignCompound.getSqlDesignValues().length;i++){
					if(i>0){
						insertBuilder.append(Const.COMMA);
						insertBuilder.append(Const.SPACE);
					}
					SqlDesignValue sqlDesignValue = sqlDesignCompound.getSqlDesignValues()[i];
					insertBuilder.append(Const.CDATA_BEGIN);
					if(sqlDesignValue.getValueType() == null || sqlDesignValue.getValueType().equals(ValueType.PARAMETERS.getNumber())){
						insertBuilder.append(buildSqlInsertUpdate(ValueType.PARAMETERS, sqlDesignCompound, sqlDesignValue, profile));
					}else if(sqlDesignValue.getValueType().equals(ValueType.VALUE.getNumber())){
						insertBuilder.append(buildSqlInsertUpdate(ValueType.VALUE, sqlDesignCompound, sqlDesignValue, profile));
					}
					insertBuilder.append(Const.CDATA_END);
					
				}
			}
			insertBuilder.append(Const.CLOSE_PARENTHESIS);
			insertBuilder.append(Const.LINE_BREAK);
		}
	}
	
	private String buildSqlInsertUpdate(ValueType valueType, SqlDesignCompound sqlDesignCompound,SqlDesignValue sqlDesignValue, AccountProfile profile){
		String sql = StringUtils.EMPTY;
		switch (valueType) {
		case PARAMETERS:
			sql = MessageFormat.format(Const.INPUT_PARAM,getInputParam(sqlDesignCompound.getSqlDesignInputs(),sqlDesignValue.getParameter()));
			break;
		case VALUE:
			sql = buildValueByDatypeForInsertUpdate(sqlDesignValue, profile);
			break;
		default:
			break;
		}
		return sql;
	}
	
	private String buildValueByDatypeForInsertUpdate(SqlDesignValue sqlDesignValue, AccountProfile profile){
		String valueDefault = new StringBuffer().append(Const.QUOTE).append(sqlDesignValue.getParameter()).append(Const.QUOTE).toString();
		Integer dataType = sqlDesignValue.getDataType();
		switch (dataType) {
			case BaseType.CHARACTER_VARYING_BASETYPE:
			case BaseType.CHAR_BASETYPE:
				return 	valueDefault;
			case BaseType.DATE_BASETYPE:
			case BaseType.DATETIME_BASETYPE:
			case BaseType.TIMESTAMP_BASETYPE:
			case BaseType.TIME_BASETYPE:
				return buildValueForDatetimeTypeInserUpdate(sqlDesignValue, profile);
			default:
				return sqlDesignValue.getParameter();
		}
	}

	private String buildValueForDatetimeTypeInserUpdate(SqlDesignValue sqlDesignValue, AccountProfile profile) {
		String patternFormat = "";
		if(StringUtils.isBlank(sqlDesignValue.getPatternFormat())){
			patternFormat = DateUtils.getPatternFormatDateTimeByDataTypeFromProfile(sqlDesignValue.getDataType(), profile);
		}else{
			patternFormat = sqlDesignValue.getDateTimeFormatByPatternFormat();
		}
		return ToDateFunction.get(patternFormat).date(sqlDesignValue.getParameter()).toDateText();
	}
	
	
	
	private String buildSqlInsertUpdate(ValueType valueType, SqlDesignCompound sqlDesignCompound,SqlDesignValue sqlDesignValue, List<TableDesignDetails> tableDesigns, AccountProfile profile){
		if(!valueType.equals(ValueType.ENTITY)){
			return buildSqlInsertUpdate(valueType, sqlDesignCompound, sqlDesignValue, profile);
		}
		Long columnId = Long.parseLong(sqlDesignValue.getParameter());
		
		for(TableDesignDetails tableDesign : tableDesigns){
			if(tableDesign.getColumnId().equals(columnId)){
				return tableDesign.getCode();
			}
		}
		return StringUtils.EMPTY;
	}
	
	
	private String getInputParam(SqlDesignInput[] sqlDesignInputs,String itemSeqNo){
		SqlDesignInput sqlDesignInput = null;
		if(ArrayUtils.isNotEmpty(sqlDesignInputs)){
			for(int i=0;i<sqlDesignInputs.length;i++){
				if(DataTypeUtils.equals(sqlDesignInputs[i].getGroupIndex(),itemSeqNo)){
					sqlDesignInput = sqlDesignInputs[i];
					break;
				}
			}
			if(sqlDesignInput != null){
				return getInputObjectPath(sqlDesignInputs,sqlDesignInput);
			}
		}	
		return StringUtils.EMPTY;
	}
	
	private String getInputObjectPath(SqlDesignInput[] sqlDesignInputs,SqlDesignInput sqlDesignInput){
		String path= GenerateSourceCodeUtil.normalizedVariantName(sqlDesignInput.getSqlDesignInputCode());
		if(sqlDesignInput.getSqlDesignInputParentId()!=null){
			SqlDesignInput sqlDesignInputParent = null;
			for(int i=0;i<sqlDesignInputs.length;i++){
				if(DataTypeUtils.equals(sqlDesignInputs[i].getSqlDesignInputId(),sqlDesignInput.getSqlDesignInputParentId())){
					sqlDesignInputParent = sqlDesignInputs[i];
					break;
				}
			}
			path = getInputObjectPath(sqlDesignInputs,sqlDesignInputParent) +Const.DOT+ path;
		}
		return path;
	}
	
	private String buildSelect(SqlDesignCompound sqlDesignCompound,StringBuilder builder, CommonModel common){
		StringBuilder selectBuilder = builder;
		Boolean hasGroupBy = false;
		SqlDesign sqlDesign = sqlDesignCompound.getSqlDesign();
		selectBuilder.append(Const.SELECT);
		selectBuilder.append(Const.SPACE);
		List<SqlDesignResult> sqlDesignResults = new ArrayList<SqlDesignResult>();
		for(SqlDesignResult sqlDesignResult:sqlDesignCompound.getSqlDesignResults()){
			if(sqlDesignResult.getEnabledFlag()==1){
				sqlDesignResults.add(sqlDesignResult);
				if(StringUtils.isNotBlank(sqlDesignResult.getFunctionCode())){
					sqlDesignResult.setMappingAlias(StringUtils.lowerCase(CL_AggregateFunctions.asMap().get(sqlDesignResult.getFunctionCode()))+sqlDesignResult.getItemSeqNo());
				} else {
					sqlDesignResult.setMappingAlias(sqlDesignResult.getColumnCode());
				}
			}
		}
		int functionsCount = 0;
		for(int i=0;i<sqlDesignResults.size();i++){
			SqlDesignResult sqlDesignResult = sqlDesignResults.get(i);
			if(i>0){
				selectBuilder.append(Const.COMMA);
				selectBuilder.append(Const.SPACE);
			}
			selectBuilder.append(Const.LINE_BREAK);
			selectBuilder.append(Const.TAB);
			if(StringUtils.isNotBlank(sqlDesignResult.getFunctionCode())){
				functionsCount ++;
				
				selectBuilder.append(CL_AggregateFunctions.asMap().get(sqlDesignResult.getFunctionCode()));
				selectBuilder.append(Const.OPEN_PARENTHESIS);
				selectBuilder.append(sqlDesignResult.getTableCode());
				selectBuilder.append(Const.TABLE_COLUMN_SEPARATOR);
				selectBuilder.append(sqlDesignResult.getColumnCode());
				selectBuilder.append(Const.CLOSE_PARENTHESIS);
				selectBuilder.append(Const.SPACE);
				selectBuilder.append(Const.ALIAS);
				selectBuilder.append(Const.SPACE);
				selectBuilder.append(sqlDesignResult.getMappingAlias());
			} else {
				
				selectBuilder.append(sqlDesignResult.getTableCode());
				selectBuilder.append(Const.TABLE_COLUMN_SEPARATOR);
				selectBuilder.append(sqlDesignResult.getColumnCode());
			}
		}
		if(functionsCount<sqlDesignResults.size() && functionsCount > 0){
			hasGroupBy = true;
		};
		selectBuilder.append(Const.LINE_BREAK);
		selectBuilder.append(Const.FROM);
		selectBuilder.append(Const.LINE_BREAK);
		for(int i=0;i<sqlDesignCompound.getSqlDesignTables().length;i++){
			SqlDesignTable sqlDesignTable = sqlDesignCompound.getSqlDesignTables()[i];
			if(i==0){
				selectBuilder.append(Const.TAB);
				selectBuilder.append(sqlDesignTable.getTableCode());
			}
			if(StringUtils.isNoneEmpty(sqlDesignTable.getJoinTableCode())){
				selectBuilder.append(Const.LINE_BREAK);
				selectBuilder.append(Const.TAB);
				selectBuilder.append(CL_JOIN_TYPE.asMap().get(sqlDesignTable.getJoinType()));
				selectBuilder.append(Const.LINE_BREAK);
				selectBuilder.append(Const.TAB);
				selectBuilder.append(sqlDesignTable.getJoinTableCode());
				selectBuilder.append(Const.LINE_BREAK);
				selectBuilder.append(Const.TAB);
				boolean isNotCrossJoin = !StringUtils.equals(sqlDesignTable.getJoinType(), JoinType.CROSS_JOIN.getCode());
				if(isNotCrossJoin){
					selectBuilder.append(Const.ON);
				}
				if(ArrayUtils.isNotEmpty(sqlDesignTable.getSqlDesignTableItems()) && isNotCrossJoin){
					if(SQLDesignType.VIEW != sqlDesign.getDesignType()){
						selectBuilder.append(Const.LINE_BREAK).append(Const.CDATA_BEGIN);
					}
					for(int j=0;j<sqlDesignTable.getSqlDesignTableItems().length;j++){
						SqlDesignTableItem sqlDesignTableItem = sqlDesignTable.getSqlDesignTableItems()[j];
						selectBuilder.append(Const.LINE_BREAK);
						selectBuilder.append(Const.TAB);
						selectBuilder.append(Const.TAB);
						if(j>0){
							selectBuilder.append(Const.AND);
							selectBuilder.append(Const.SPACE);
						}
						selectBuilder.append(sqlDesignTableItem.getTableCode());
						selectBuilder.append(Const.TABLE_COLUMN_SEPARATOR);
						selectBuilder.append(sqlDesignTableItem.getColumnCode());
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(SqlOperator.getByValue(sqlDesignTableItem.getOperatorCode()));
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(sqlDesignTable.getJoinTableCode());
						selectBuilder.append(Const.TABLE_COLUMN_SEPARATOR);
						selectBuilder.append(sqlDesignTableItem.getJoinColumnCode());
					}
					if(SQLDesignType.VIEW != sqlDesign.getDesignType()){
						selectBuilder.append(Const.LINE_BREAK).append(Const.CDATA_END);
					}
				}
			}
		}
		List<SqlDesignCondition> whereConditions = new ArrayList<SqlDesignCondition>();
		List<SqlDesignCondition> havingConditions = new ArrayList<SqlDesignCondition>();
		for(int i=0;i<sqlDesignCompound.getSqlDesignConditions().length;i++){
			SqlDesignCondition sqlDesignCondition = sqlDesignCompound.getSqlDesignConditions()[i];
			if(StringUtils.isNotEmpty(sqlDesignCondition.getFunctionCode())){
				hasGroupBy = true;
				havingConditions.add(sqlDesignCondition);
			} else {
				whereConditions.add(sqlDesignCondition);
			}
		}
		
		if(CollectionUtils.isNotEmpty(whereConditions)){
			selectBuilder.append(Const.LINE_BREAK);
			selectBuilder.append(SQLDesignType.VIEW == sqlDesign.getDesignType() ? Const.WHERE : Const.WHERE_BEGIN);
			if(SQLDesignType.VIEW != sqlDesign.getDesignType()){
				selectBuilder.append(Const.LINE_BREAK);
			}
			buildConditions(selectBuilder, whereConditions,sqlDesignCompound.getSqlDesignInputs(),sqlDesignCompound.getSqlDesign().getDesignType(), sqlDesignCompound.getSqlDesign(),common);
			if(!(sqlDesignCompound.getSqlDesign().getPageable()==null || sqlDesignCompound.getSqlDesign().getPageable()!=1)){
				selectBuilder.append(Const.LINE_BREAK);
				selectBuilder.append("AND ROWNUM >= #{pageable.offset} AND ROWNUM < #{pageable.offset} + #{pageable.pageSize}");
			}
			if(SQLDesignType.VIEW != sqlDesign.getDesignType()){
				selectBuilder.append(Const.LINE_BREAK);
				selectBuilder.append(Const.WHERE_END);
			}
		}
		
		if(hasGroupBy){
			selectBuilder.append(Const.LINE_BREAK);
			selectBuilder.append(Const.GROUP_BY);
			selectBuilder.append(Const.SPACE);
			int i=0;
			for(SqlDesignResult sqlDesignResult:sqlDesignResults){
				if(StringUtils.isEmpty(sqlDesignResult.getFunctionCode())){
					if(i>0){
						selectBuilder.append(Const.COMMA);
						selectBuilder.append(Const.SPACE);
					}
					selectBuilder.append(sqlDesignResult.getTableCode());
					selectBuilder.append(Const.TABLE_COLUMN_SEPARATOR);
					selectBuilder.append(sqlDesignResult.getColumnCode());
					i++;
				}
			}
			if(CollectionUtils.isNotEmpty(havingConditions)){
				selectBuilder.append(Const.LINE_BREAK);
				selectBuilder.append(Const.HAVING);
				buildConditions(selectBuilder, havingConditions, sqlDesignCompound.getSqlDesignInputs(),null, sqlDesignCompound.getSqlDesign(),common);
			}
		}
		if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignOrders())){
			selectBuilder.append(Const.LINE_BREAK);
			selectBuilder.append(Const.ORDER_BY);
			selectBuilder.append(Const.SPACE);
			for(int i=0;i<sqlDesignCompound.getSqlDesignOrders().length;i++){
				SqlDesignOrder sqlDesignOrder = sqlDesignCompound.getSqlDesignOrders()[i];
				SqlDesignResult sqlDesignResult = sqlDesignCompound.getSqlDesignResults()[(int) (sqlDesignOrder.getSqlDesignResultId()-1)];
				if(i>0){
					selectBuilder.append(Const.COMMA);
					selectBuilder.append(Const.SPACE);
				}
				if(StringUtils.isNotEmpty(sqlDesignResult.getFunctionCode())){
					selectBuilder.append(CL_AggregateFunctions.asMap().get(sqlDesignResult.getFunctionCode()));
					selectBuilder.append(Const.OPEN_PARENTHESIS);
				}
				selectBuilder.append(sqlDesignResult.getTableCode());
				selectBuilder.append(Const.TABLE_COLUMN_SEPARATOR);
				selectBuilder.append(sqlDesignResult.getColumnCode());
				if(StringUtils.isNotEmpty(sqlDesignResult.getFunctionCode())){
					selectBuilder.append(Const.CLOSE_PARENTHESIS);
				}
				selectBuilder.append(Const.SPACE);
				selectBuilder.append(SqlOrderType.getByValue(sqlDesignOrder.getOrderType()));
			}
		}
		return selectBuilder.toString();
	}
	
	private void buildConditions(StringBuilder selectBuilder, List<SqlDesignCondition> whereConditions,SqlDesignInput[] sqlDesignInputs,Integer sqlDesignType,SqlDesign sqlDesign, CommonModel common) {
		AccountProfile profile = accountProfileRepository.findOne(common.getCreatedBy());
		Project project = this.projectReponsitory.findById(common.getWorkingProjectId(), common.getCreatedBy());
		boolean groupFlag = false;
		int idexSearchKeyValue = 0;
		if(SQLDesignType.VIEW != sqlDesign.getDesignType()){
			for(int i=0;i<whereConditions.size();i++){
				SqlDesignCondition sqlDesignCondition = whereConditions.get(i);
				if(isHasLikeOperator(sqlDesignCondition)){
					idexSearchKeyValue++;
					selectBuilder.append(addQueryEscapeForLike(sqlDesignInputs,sqlDesignCondition,idexSearchKeyValue));
				}
			}
		}
		for(int i=0;i<whereConditions.size();i++){
			SqlDesignCondition sqlDesignCondition = whereConditions.get(i);
			if(!(sqlDesignType == null || sqlDesignType != 2 || sqlDesignCondition.getConditionType()!= 2)){
				if(SqlOperator.getByValue(sqlDesignCondition.getOperatorCode())==SqlOperator.BETWEEN){
					String[] values = sqlDesignCondition.getValue().split(Const.ValueSeparator);
					for(int j=0;j<values.length;j++){
						values[j]=this.getInputParam(sqlDesignInputs, values[j]);
					}
					selectBuilder.append(Const.LINE_BREAK).append(MessageFormat.format(Const.MYBATIS_IF_PARAM2,(Object[])values));
				} else {
					selectBuilder.append(Const.LINE_BREAK).append(MessageFormat.format(Const.MYBATIS_IF_PARAM,this.getInputParam(sqlDesignInputs, sqlDesignCondition.getValue())));
				}
				selectBuilder.append(Const.LINE_BREAK);
			}
			
			if(sqlDesignCondition.getGroupType()==1 && !groupFlag){
				selectBuilder.append(Const.LINE_BREAK);
				if(i>0){
					selectBuilder.append(CL_COMBINING_OPERATOR.asMap().get(sqlDesignCondition.getLogicCode()));
					selectBuilder.append(Const.SPACE);
				}
				selectBuilder.append(Const.OPEN_PARENTHESIS);
				groupFlag = true;
			} else if(sqlDesignCondition.getGroupType()==0 || groupFlag){
				if(groupFlag && sqlDesignCondition.getGroupType()==0){
					selectBuilder.append(Const.CLOSE_PARENTHESIS);
					groupFlag = false;
				}
				selectBuilder.append(Const.LINE_BREAK);
				if(i>0){
					selectBuilder.append(CL_COMBINING_OPERATOR.asMap().get(sqlDesignCondition.getLogicCode()));
					selectBuilder.append(Const.SPACE);
				}
			}
			boolean isAddedUpper = false;
			if(SQLDesignType.VIEW != sqlDesign.getDesignType()){
				selectBuilder.append(Const.LINE_BREAK).append(Const.CDATA_BEGIN).append(Const.LINE_BREAK);
			}
			if(isAddUpperCaseForOracle(sqlDesignCondition, project) || isUpperCaseEquaOperater(sqlDesignCondition, project)){
				selectBuilder.append(Const.UPPER).append(Const.OPEN_PARENTHESIS);
				isAddedUpper = true;
			}
			if(checkAutocompleteLeftWhereCondition(sqlDesign, sqlDesignCondition)){
				selectBuilder.append(ToCharFunction.TO_CHAR).append(Const.OPEN_PARENTHESIS);
			}else{
				if(project.isOracle() && sqlDesignCondition.isValueDate() && StringUtils.isNotEmpty(sqlDesignCondition.getFunctionCode()) && !this.checkCountFunctionCode(sqlDesignCondition)){
					selectBuilder.append(TruncFunction.TRUNC).append(Const.OPEN_PARENTHESIS);
				}
				if(project.isOracle() && sqlDesignCondition.isValueTime() && StringUtils.isNotEmpty(sqlDesignCondition.getFunctionCode()) && !this.checkCountFunctionCode(sqlDesignCondition)){
					selectBuilder.append(ToCharFunction.TO_CHAR).append(Const.OPEN_PARENTHESIS);
				}
			}
			
			if(StringUtils.isNotEmpty(sqlDesignCondition.getFunctionCode())){
				selectBuilder.append(CL_AggregateFunctions.asMap().get(sqlDesignCondition.getFunctionCode()));
				selectBuilder.append(Const.OPEN_PARENTHESIS);
			}
			//Create table.column name
			if(sqlDesign.getDesignType() == SQLDesignType.AUTOCOMPLETE && sqlDesignCondition.getConditionType() == WhereConditionType.PARAMETERS.getNumber()){
				selectBuilder.append(buildLeftWhereConditionAutocompleteParam(sqlDesignCondition));
			}else{
				selectBuilder.append(buildValueLeftEntityWhereCondition(project, sqlDesignCondition,profile));
			}
			if(StringUtils.isNotEmpty(sqlDesignCondition.getFunctionCode())){
				selectBuilder.append(Const.CLOSE_PARENTHESIS);
			}
			if(checkAutocompleteLeftWhereCondition(sqlDesign, sqlDesignCondition)){
				selectBuilder.append(Const.CLOSE_PARENTHESIS);
			}else{
				if(project.isOracle() && sqlDesignCondition.isValueTime() && StringUtils.isNotEmpty(sqlDesignCondition.getFunctionCode()) && !this.checkCountFunctionCode(sqlDesignCondition)){
					selectBuilder.append(Const.COMMA).append(Const.QUOTE).append(ToCharFunction.FORMAT_TIME).append(Const.QUOTE).append(Const.CLOSE_PARENTHESIS);
				}
				if(project.isOracle() && sqlDesignCondition.isValueDate() && StringUtils.isNotEmpty(sqlDesignCondition.getFunctionCode()) && !this.checkCountFunctionCode(sqlDesignCondition)){
					selectBuilder.append(Const.CLOSE_PARENTHESIS);
				}
			}
			if(isAddUpperCaseForOracle(sqlDesignCondition, project) || isUpperCaseEquaOperater(sqlDesignCondition, project)){
				selectBuilder.append(Const.CLOSE_PARENTHESIS);
			}
			selectBuilder.append(Const.SPACE);
			selectBuilder.append(changeLikeToILike(sqlDesignCondition, project));
			selectBuilder.append(Const.SPACE);
			if(sqlDesignCondition.getConditionType()==0){
				SqlOperator operator = SqlOperator.getByValue(sqlDesignCondition.getOperatorCode());
				if(operator ==SqlOperator.BETWEEN){
					String[] values = sqlDesignCondition.getValue().split(Const.ValueSeparator);
					switch(sqlDesignCondition.getDataType()){
					case BaseType.CHARACTER_VARYING_BASETYPE:
					case BaseType.TEXT_BASETYPE:
					case BaseType.CHAR_BASETYPE:
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(replaceQuotes(values[0]));
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.QUOTE);
						selectBuilder.append(replaceQuotes(values[1]));
						selectBuilder.append(Const.QUOTE);
						break;
					case BaseType.DATE_BASETYPE:
						selectBuilder.append(truncDateIfProjectIsOracle(sqlDesignCondition,profile, project, values[0]));
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(truncDateIfProjectIsOracle(sqlDesignCondition,profile, project, values[1]));
						break;
					case BaseType.DATETIME_BASETYPE:
					case BaseType.TIMESTAMP_BASETYPE:
						selectBuilder.append(toTimestampForDatetime(sqlDesignCondition, profile, project, values[0]));
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(toTimestampForDatetime(sqlDesignCondition, profile, project, values[1]));
						break;
					case BaseType.TIME_BASETYPE:
						//selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,values[0],DateUtils.getPatternTime(profile.getTimeFormat())));
						selectBuilder.append(toCharTimeIfProjectIsOracle(sqlDesignCondition,profile,project, values[0]));
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(toCharTimeIfProjectIsOracle(sqlDesignCondition,profile,project, values[1]));
						break;
					default:
						selectBuilder.append(values[0]);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(Const.AND);
						selectBuilder.append(Const.SPACE);
						selectBuilder.append(values[1]);
						break;
					}
				} else if(operator != SqlOperator.IS_NOT_NULL & operator != SqlOperator.IS_NULL){
					switch(sqlDesignCondition.getDataType()){
					case BaseType.CHARACTER_VARYING_BASETYPE:
					case BaseType.TEXT_BASETYPE:
					case BaseType.CHAR_BASETYPE:
						if(isHasLikeOperator(sqlDesignCondition)){
							selectBuilder.append(convertValueForLike(sqlDesignCondition, project, sqlDesign));
						}else{
							if(checkCountFunctionCode(sqlDesignCondition)){
								selectBuilder.append(sqlDesignCondition.getValue());
							}
							else if(isUpperCaseEquaOperater(sqlDesignCondition, project)){
								selectBuilder.append(Const.UPPER).append(Const.OPEN_PARENTHESIS).append(Const.QUOTE).append(replaceQuotes(sqlDesignCondition.getValue())).append(Const.QUOTE).append(Const.CLOSE_PARENTHESIS);
							}else{
								selectBuilder.append(Const.QUOTE).append(replaceQuotes(sqlDesignCondition.getValue())).append(Const.QUOTE);
							}
						}
						break;
					case BaseType.DATE_BASETYPE:
						//selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,sqlDesignCondition.getValue(),profile.getDateFormat()));
						selectBuilder.append(truncDateIfProjectIsOracle(sqlDesignCondition, profile, project, sqlDesignCondition.getValue()));
						break;
					case BaseType.DATETIME_BASETYPE:
					case BaseType.TIMESTAMP_BASETYPE:
						selectBuilder.append(toTimestampForDatetime(sqlDesignCondition, profile, project, sqlDesignCondition.getValue()));
						break;
					case BaseType.TIME_BASETYPE:
						//selectBuilder.append(MessageFormat.format(Const.TO_DATE_FUNCTION,sqlDesignCondition.getValue(),DateUtils.getPatternTime(profile.getTimeFormat())));
						selectBuilder.append(toCharTimeIfProjectIsOracle(sqlDesignCondition, profile, project, sqlDesignCondition.getValue()));
						break;
					default:
						selectBuilder.append(convertValueForLike(sqlDesignCondition, project, sqlDesign));
						break;
					}
				}
			} else if(sqlDesignCondition.getConditionType()==1){
				if(isAddUpperCaseForOracle(sqlDesignCondition, project) || isUpperCaseEquaOperater(sqlDesignCondition, project)){
					selectBuilder.append(Const.UPPER).append(Const.OPEN_PARENTHESIS);
				}
				selectBuilder.append(buildValueRightEntityWhereCondition(project,sqlDesignCondition, profile));
				if(isAddUpperCaseForOracle(sqlDesignCondition, project) || isUpperCaseEquaOperater(sqlDesignCondition, project)){
					selectBuilder.append(Const.CLOSE_PARENTHESIS);
				}
			} else if(sqlDesignCondition.getConditionType()==2){
				if(SqlOperator.getByValue(sqlDesignCondition.getOperatorCode())==SqlOperator.BETWEEN){
					String[] values = sqlDesignCondition.getValue().split(Const.ValueSeparator);
					for(int j=0;j<values.length;j++){
						values[j]=MessageFormat.format(Const.INPUT_PARAM,this.getInputParam(sqlDesignInputs, values[j]));
					}
					String valueFirst = values[0];
					String valueSecond = values[1];
					if(project.isOracle() && sqlDesignCondition.isValueDate()){
						valueFirst = TruncFunction.get().text(values[0]).trunc();
						valueSecond = TruncFunction.get().text(values[1]).trunc();
					}
					if(project.isOracle() && sqlDesignCondition.isValueTime()){
						valueFirst = ToCharFunction.get().date(values[0]).toChar();
						valueSecond = ToCharFunction.get().date(values[1]).toChar();
					}
					if(sqlDesign.getDesignType() == SQLDesignType.AUTOCOMPLETE){
						valueFirst = convertParamForAutocomplete(project,sqlDesignCondition, values[0]);
						valueSecond = convertParamForAutocomplete(project, sqlDesignCondition, values[1]);
					}
					selectBuilder.append(valueFirst);
					selectBuilder.append(Const.SPACE);
					selectBuilder.append(Const.AND);
					selectBuilder.append(Const.SPACE);
					selectBuilder.append(valueSecond);
				}else{
					if(isHasLikeOperator(sqlDesignCondition)){
						sqlDesignCondition.setValue(this.getInputParam(sqlDesignInputs, sqlDesignCondition.getValue()));
					}else if(isAddedUpper){
						String valueConvert = MessageFormat.format(Const.INPUT_PARAM,this.getInputParam(sqlDesignInputs, sqlDesignCondition.getValue()));
						StringBuffer valueUpper = new StringBuffer(Const.UPPER).append(Const.OPEN_PARENTHESIS).append(valueConvert).append(Const.CLOSE_PARENTHESIS);
						sqlDesignCondition.setValue(valueUpper.toString());
					}else{
						sqlDesignCondition.setValue(MessageFormat.format(Const.INPUT_PARAM,this.getInputParam(sqlDesignInputs, sqlDesignCondition.getValue())));
					}
					if(project.isOracle() && !this.checkCountFunctionCode(sqlDesignCondition) && sqlDesignCondition.isValueDate()){
						String truncDate = TruncFunction.get().text(sqlDesignCondition.getValue()).trunc();
						selectBuilder.append(truncDate);
					}else if(project.isOracle() && !this.checkCountFunctionCode(sqlDesignCondition) && sqlDesignCondition.isValueTime()){
						String toCharTime = ToCharFunction.get().date(sqlDesignCondition.getValue()).toChar();
						selectBuilder.append(toCharTime);
					}else{
						if(sqlDesign.getDesignType() == SQLDesignType.AUTOCOMPLETE && !this.isStringType(sqlDesignCondition.getDataType())){
							selectBuilder.append(this.convertParamForAutocomplete(project, sqlDesignCondition, sqlDesignCondition.getValue()));
						}else{
							selectBuilder.append(convertValueForLike(sqlDesignCondition, project,sqlDesign));
						}
					}
				}
			}
			if(SQLDesignType.VIEW != sqlDesign.getDesignType()){
				selectBuilder.append(Const.LINE_BREAK).append(Const.CDATA_END).append(Const.LINE_BREAK);
			}
			if(!(sqlDesignType == null || sqlDesignType != 2 || sqlDesignCondition.getConditionType()!= 2)){
				selectBuilder.append(Const.LINE_BREAK); 
				selectBuilder.append(Const.MYBATIS_IF_END);
			}
		}
		if(groupFlag){
			selectBuilder.append(Const.CLOSE_PARENTHESIS);
			groupFlag = false;
		}
	}

	private String replaceQuotes(String value) {
		if(StringUtils.isBlank(value)){
			return value;
		}
		return value.replaceAll("'", "''");
	}

	private boolean checkAutocompleteLeftWhereCondition(SqlDesign sqlDesign,
			SqlDesignCondition sqlDesignCondition) {
		return sqlDesign.getDesignType() == SQLDesignType.AUTOCOMPLETE 
				&& StringUtils.isNotEmpty(sqlDesignCondition.getFunctionCode())
				&& checkIsDatetimeTypes(sqlDesignCondition) 
				&& !this.checkCountFunctionCode(sqlDesignCondition) 
				&& sqlDesignCondition.getConditionType() == WhereConditionType.PARAMETERS.getNumber();
	}

	private boolean checkIsDatetimeTypes(SqlDesignCondition sqlDesignCondition) {
		Integer valueType = sqlDesignCondition.getDataType();
		switch (valueType) {
			case BaseType.DATE_BASETYPE:
			case BaseType.DATETIME_BASETYPE:
			case BaseType.TIME_BASETYPE:
			case BaseType.TIMESTAMP_BASETYPE:
				return true;
			default:
				return false;
		}
	}

	private String buildLeftWhereConditionAutocompleteParam(SqlDesignCondition sqlDesignCondition) {
		String tableColumn = new StringBuffer(sqlDesignCondition.getLeftTableCode()).append(Const.TABLE_COLUMN_SEPARATOR).append(sqlDesignCondition.getLeftColumnCode()).toString();
		if(StringUtils.isNotEmpty(sqlDesignCondition.getFunctionCode())){
			 return tableColumn;
		}
		Integer valueType = sqlDesignCondition.getDataType();
		switch (valueType) {
			case BaseType.DATE_BASETYPE:
			case BaseType.DATETIME_BASETYPE:
			case BaseType.TIME_BASETYPE:
			case BaseType.TIMESTAMP_BASETYPE:
				return ToCharFunction.get().tableName(sqlDesignCondition.getLeftTableCode()).columnName(sqlDesignCondition.getLeftColumnCode()).toCharEntityWithoutFormat();
		default:
			return tableColumn;
		}
	}

	private String convertParamForAutocomplete(Project project,SqlDesignCondition sqlDesignCondition, String param) {
		Integer valueType = sqlDesignCondition.getDataType();
		switch (valueType) {
			case BaseType.BIGINT_BASETYPE:
			case BaseType.BIGSERIAL_BASETYPE:
			case BaseType.BINARY_BASETYPE:
			case BaseType.BYTE_BASETYPE:
			case BaseType.CURRENCY_BASETYPE:
			case BaseType.DOUBLE_BASETYPE:
			case BaseType.FLOAT_BASETYPE:
			case BaseType.INTEGER_BASETYPE:
			case BaseType.NUMERIC_BASETYPE:
			case BaseType.SERIAL_BASETYPE:
			case BaseType.SMALLINT_BASETYPE:	
				return ToNumberFunction.get().variable(param).toNumberVariable();
			case BaseType.BOOLEAN_BASETYPE:
				if(project.isOracle()){
					return ToNumberFunction.get().variable(param).toNumberVariable();
				}
				return param;
		default:
			return param;
		}
	}

	private String buildValueRightEntityWhereCondition(Project project, SqlDesignCondition sqlDesignCondition, AccountProfile profile) {
		String tableColumnNameDefault = new StringBuffer(sqlDesignCondition.getRightTableCode()).append(Const.TABLE_COLUMN_SEPARATOR).append(sqlDesignCondition.getRightColumnCode()).toString();
		
		if(sqlDesignCondition.getConditionType() == WhereConditionType.ENTITY.getNumber() || StringUtils.isNotEmpty(sqlDesignCondition.getFunctionCode())){
			return tableColumnNameDefault;
		}
		if(project.isOracle() && sqlDesignCondition.isValueDate()){
			return truncDateRightEntity(sqlDesignCondition);
		}else if(project.isOracle() && sqlDesignCondition.isValueTime()){
			return toCharTimeRightEntity(sqlDesignCondition);
		}else{
			return tableColumnNameDefault;
		}
	}

	private String truncDateRightEntity(SqlDesignCondition sqlDesignCondition) {
		String tableName = sqlDesignCondition.getRightTableCode();
		String columnName = sqlDesignCondition.getRightColumnCode();
		return TruncFunction.get().tableName(tableName).columnName(columnName).truncEntity();
	}
	
	private String toCharTimeRightEntity(SqlDesignCondition sqlDesignCondition) {
		String tableName = sqlDesignCondition.getRightTableCode();
		String columnName = sqlDesignCondition.getRightColumnCode();
		return ToCharFunction.get().tableName(tableName).columnName(columnName).toCharEntity();
	}
	
	private String buildValueLeftEntityWhereCondition(Project project, SqlDesignCondition sqlDesignCondition, AccountProfile profile) {
		String tableColumn = new StringBuffer(sqlDesignCondition.getLeftTableCode()).append(Const.TABLE_COLUMN_SEPARATOR).append(sqlDesignCondition.getLeftColumnCode()).toString();
		if(sqlDesignCondition.getConditionType() == WhereConditionType.ENTITY.getNumber() || StringUtils.isNotEmpty(sqlDesignCondition.getFunctionCode())){
			return tableColumn;
		}
		if(project.isOracle() && sqlDesignCondition.isValueDate()){
			return truncDateLeftEntity(sqlDesignCondition);
		}else if(project.isOracle() && sqlDesignCondition.isValueTime()){
			return toCharTimeLeftEntity(sqlDesignCondition);
		}else{
			return tableColumn;
		}
	}
	
	private String truncDateLeftEntity(SqlDesignCondition sqlDesignCondition) {
		String tableName = sqlDesignCondition.getLeftTableCode();
		String columnName = sqlDesignCondition.getLeftColumnCode();
		return TruncFunction.get().tableName(tableName).columnName(columnName).truncEntity();
	}
	
	private String toCharTimeLeftEntity(SqlDesignCondition sqlDesignCondition) {
		String tableName = sqlDesignCondition.getLeftTableCode();
		String columnName = sqlDesignCondition.getLeftColumnCode();
		return ToCharFunction.get().tableName(tableName).columnName(columnName).toCharEntity();
	}
	

	private String truncDateIfProjectIsOracle(SqlDesignCondition sqlDesignCondition, AccountProfile profile, Project project, String value) {
		if(this.checkCountFunctionCode(sqlDesignCondition)){
			return value;
		}
		String format = this.getFormatDateTime(profile, sqlDesignCondition);
		String toDateFunction = ToDateFunction.get(format).date(value).toDateText();
		if(project.isOracle()){
			return TruncFunction.get().text(value).truncDate(format);
		}
		return toDateFunction;
	}
	
	private String toTimestampForDatetime(SqlDesignCondition sqlDesignCondition, AccountProfile profile, Project project, String value) {
		if(this.checkCountFunctionCode(sqlDesignCondition)){
			return value;
		}
		String format = this.getFormatDateTime(profile, sqlDesignCondition);
		String toDateFunction = ToTimestampFunction.get(format).value(value).toTimestampValue();
		return toDateFunction;
	}
	
	private String toCharTimeIfProjectIsOracle(SqlDesignCondition sqlDesignCondition, AccountProfile profile, Project project,String value) {
		String format = this.getFormatDateTime(profile, sqlDesignCondition);
		if(this.checkCountFunctionCode(sqlDesignCondition)){
			return value;
		}
		if(project.isOracle()){
			return ToCharFunction.get().date(value).toCharDate(format);
		}
		return ToTimeFunction.get(format).value(value).toTimeValue();
	}

	private String addQueryEscapeForLike( SqlDesignInput[] sqlDesignInputs,SqlDesignCondition sqlDesignCondition, int index) {
		StringBuilder selectBuilder = new StringBuilder();
		selectBuilder.append(Const.LINE_BREAK);
		if(sqlDesignCondition.getConditionType() == 2){
			String inputBean = this.getInputParam(sqlDesignInputs, sqlDesignCondition.getValue());
			selectBuilder.append(MessageFormat.format(Const.QUERY_ESCAPE, inputBean, inputBean));
		}else{
			String keywork = sqlDesignCondition.getValue();
			
			String nameBind = "keySeachValue" + index;
			sqlDesignCondition.setValue(nameBind);
			selectBuilder.append(MessageFormat.format(Const.QUERY_ESCAPE, nameBind, Const.QUOTE + replaceQuotes(keywork) + Const.QUOTE).toString());
		}
		
		return selectBuilder.toString();
	}
	
	private boolean isUpperCaseEquaOperater(SqlDesignCondition sqlDesignCondition, Project project){
		if( 0 != project.getCaseSensitivity()){
			return false;
		}
		if(!SqlOperator.getByValue(sqlDesignCondition.getOperatorCode()).equals(SqlOperator.EQUAL)){
			return false;
		}
		if(!isStringType(sqlDesignCondition.getDataType())){
			return false;
		}
		if(checkCountFunctionCode(sqlDesignCondition)){
			return false;
		}
		return true;
	}
		
	private boolean isStringType(Integer datatype){
		boolean check = false;
	
		switch (datatype) {
		case BaseType.CHARACTER_VARYING_BASETYPE:
		case BaseType.TEXT_BASETYPE:
		case BaseType.CHAR_BASETYPE:
			check = true;
			break;
		}
		return check;
	}
	
	private boolean isHasLikeOperator(SqlDesignCondition sqlDesignCondition){
		if(SqlOperator.getByValue(sqlDesignCondition.getOperatorCode()).equals(SqlOperator.LIKE) && sqlDesignCondition.getConditionType() != 1){
			return true;
		}
		return false;
	}

	private boolean isAddUpperCaseForOracle(SqlDesignCondition sqlDesignCondition, Project project){
		if(!SqlOperator.getByValue(sqlDesignCondition.getOperatorCode()).equals(SqlOperator.LIKE)){
			return false;
		}
		if( 0 != project.getCaseSensitivity()){
			return false;
		}
		if(!project.isOracle()){
			return false;
		}
		return true;
	}
	
	/**
	 * check where condition has count function code
	 * @param sqlDesignCondition
	 * @return true or false
	 */
	private boolean checkCountFunctionCode(SqlDesignCondition sqlDesignCondition){
		return StringUtils.isNotBlank(sqlDesignCondition.getFunctionCode())
				&& StringUtils.equals(sqlDesignCondition.getFunctionCode(),FunctionCode.COUNT.getCode());
	}
	
	private String convertValueForLike(SqlDesignCondition sqlDesignCondition, Project project, SqlDesign sqlDesign){
		String strDefault = sqlDesignCondition.getValue();
		String strDefaultLike =  new StringBuffer().append(MessageFormat.format(Const.INPUT_PARAM, strDefault)).append(Const.SPACE).append(Const.ESCAPE).toString();
		if(!SqlOperator.getByValue(sqlDesignCondition.getOperatorCode()).equals(SqlOperator.LIKE)){
			return strDefault;
		}
		
		if(SQLDesignType.VIEW == sqlDesign.getDesignType()){
			return createQueryLikeForView(project, strDefault);
		}
		
		if(0 != project.getCaseSensitivity()){
			return strDefaultLike;
		}
		if(DbDomainConst.DatabaseType.ORACLE.equals(project.getDbType())){	
			StringBuffer bfStr =   new StringBuffer().append(Const.UPPER).append(Const.OPEN_PARENTHESIS).append(MessageFormat.format(Const.INPUT_PARAM, strDefault)).append(Const.CLOSE_PARENTHESIS).append(Const.SPACE).append(Const.ESCAPE);
			return bfStr.toString();
		}
		
		if(DbDomainConst.DatabaseType.PostgreSQL.equals(project.getDbType())){
			return strDefaultLike;
		}
		return strDefault;
	}

	private String createQueryLikeForView(Project project, String strDefault) {
		String valueViewForLike = QueryEscapeUtils.toContainingCondition(strDefault);
		if(DbDomainConst.DatabaseType.ORACLE.equals(project.getDbType())){
			return new StringBuffer().append(Const.UPPER).append(Const.OPEN_PARENTHESIS).append(Const.QUOTE).append(valueViewForLike).append(Const.QUOTE).append(Const.CLOSE_PARENTHESIS).append(Const.SPACE).append(Const.ESCAPE).toString();
		}else{
			return new StringBuffer().append(Const.QUOTE).append(valueViewForLike).append(Const.QUOTE).append(Const.SPACE).append(Const.ESCAPE).toString();
		}
	}
	
	private String changeLikeToILike(SqlDesignCondition sqlDesignCondition, Project project) {
		if(DbDomainConst.DatabaseType.ORACLE.equals(project.getDbType()) 
				&& project.getCaseSensitivity() == 0 
				&& SqlOperator.getByValue(sqlDesignCondition.getOperatorCode()).equals(SqlOperator.LIKE)){
			
			return SqlOperator.LIKE.getDisplay();
		}
		if(project.getCaseSensitivity() == 0 && SqlOperator.getByValue(sqlDesignCondition.getOperatorCode()).equals(SqlOperator.LIKE)){
			return SqlOperator.ILIKE.getDisplay();
		}
		return SqlOperator.getByValue(sqlDesignCondition.getOperatorCode()).getDisplay();
	}
	
	@Override
	public boolean validate(SqlDesignCompound sqlDesignCompound) {
		Boolean isValid = true;
		if(sqlDesignCompound!=null) {
			
		}
		return isValid;
	}

	private List<SqlDesignResult> parseAdvanceSqlResults(SqlDesign sqlDesign) {
		List<SqlDesignResult> sqlDesignResults = new ArrayList<SqlDesignResult>();
		SqlDesignResult sqlDesignResult = null;
		String sqlText = sqlDesign.getSqlText();
		int selectIndex = sqlText.toLowerCase().indexOf(Const.SELECT.toLowerCase());
		int fromIndex = sqlText.toLowerCase().indexOf(Const.FROM.toLowerCase());
		
		if(fromIndex-1>selectIndex+6){
			String[] splitStrings = sqlText.substring(selectIndex+6, fromIndex-1).split(Const.COMMA);
			int i=1;
			for(String str : splitStrings){
				sqlDesignResult = new SqlDesignResult();
				String[] tableColumn = str.split("\\.");
				if(tableColumn.length>0){
					sqlDesignResult.setTableCode(tableColumn.length>1?tableColumn[0].trim():StringUtils.EMPTY);
					sqlDesignResult.setTableName(tableColumn.length>1?tableColumn[0].trim():StringUtils.EMPTY);
					sqlDesignResult.setColumnCode(tableColumn.length>1?tableColumn[1].trim():tableColumn[0].trim());
					sqlDesignResult.setColumnName(tableColumn.length>1?tableColumn[1].trim():tableColumn[0].trim());
					sqlDesignResult.setItemSeqNo(i);
					sqlDesignResults.add(sqlDesignResult);
				}
				i++;
			}
		}
		
		return sqlDesignResults;
	}

	private List<SqlDesignTable> parseAdvanceSqlTables(SqlDesign sqlDesign) {
		List<SqlDesignTable> sqlDesignTables = new ArrayList<SqlDesignTable>();
		SqlDesignTable sqlDesignTable = null;
		String sqlText = sqlDesign.getSqlText();
		int selectIndex = sqlText.toLowerCase().indexOf(Const.SELECT.toLowerCase());
		int fromIndex = sqlText.toLowerCase().indexOf(Const.FROM.toLowerCase());
		if(fromIndex-1>selectIndex+6){
			String[] splitStrings = sqlText.substring(selectIndex+6, fromIndex-1).split(Const.COMMA);
			List<String> tables = new ArrayList<String>();
			for(String str : splitStrings){
				String[] tableColumn = str.split("\\.");
				if(tableColumn.length>0){
					String tableName = tableColumn.length>1?tableColumn[0].trim():StringUtils.EMPTY;
					if(!tables.contains(tableName)){
						tables.add(tableName);
					}
				}
			}
			for(String tableName : tables){
				sqlDesignTable = new SqlDesignTable();
				sqlDesignTable.setTableCode(tableName);
				sqlDesignTable.setTableName(tableName);
				sqlDesignTables.add(sqlDesignTable);
			}
		}
		return sqlDesignTables;
	}

	@Override
	public void analyzeAdvanced(SqlDesign sqlDesign) {
		if(!(sqlDesign==null || !(sqlDesign.getDesignType() == SQLDesignType.ADVANCED_AUTOCOMPLETE || sqlDesign.getDesignType() == SQLDesignType.ADVANCED_SQL || sqlDesign.getDesignType() == SQLDesignType.ADVANCED_VIEW))){
			sqlDesign.setSqlDesignTables(this.parseAdvanceSqlTables(sqlDesign));
			sqlDesign.setSqlDesignResults(this.parseAdvanceSqlResults(sqlDesign));
		}
	}
	
}

