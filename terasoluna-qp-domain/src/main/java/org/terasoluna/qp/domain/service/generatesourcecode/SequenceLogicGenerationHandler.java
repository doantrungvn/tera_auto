package org.terasoluna.qp.domain.service.generatesourcecode;

import java.util.HashMap;
import java.util.Map;

import org.terasoluna.qp.app.processing.AbstractHandler;
import org.terasoluna.qp.domain.model.CommonModel;

public abstract class SequenceLogicGenerationHandler extends AbstractHandler<BLogicHandlerIo,StringBuilder>{
	protected final String KEY_SPACE = " ";
	protected final String KEY_NEW = "new";
	protected final String KEY_DOUBLE_QUOTE = "\"";
	protected final String KEY_SINGLE_QUOTE = "''";
	protected final String KEY_EQUAL = "=";
	protected final String KEY_GREATER_THAN = ">";
	protected final String KEY_GREATER_EQUAL = ">=";
	protected final String KEY_LESS_THAN = "<";
	protected final String KEY_OPEN_PARENTHESIS = "(";
	protected final String KEY_CLOSE_PARENTHESIS = ")";
	protected final String KEY_OPEN_CURLY_BRACKET = "{";
	protected final String KEY_CLOSE_CURLY_BRACKET = "}";
	protected final String KEY_PERCENT = "%";
	protected final String KEY_SEMI_COLON = ";";
	protected final String KEY_COMMA = ",";
	protected final String KEY_LINE_BREAK = "\r\n";
	protected final String KEY_TAB = "\t";
	protected final String KEY_TAB_2 = "\t\t";
	protected final String KEY_TAB_3 = "\t\t\t";
	protected final String KEY_DOT = ".";
	protected final String KEY_NL = "\n\t\t";
	protected final String KEY_BREAK = "\n";
	protected final String SPACE = " ";

	protected final String OPERATOR_AND = "AND";
	protected final String OPERATOR_OR = "OR";
	protected final String UPPER = "UPPER";

	protected final String PREFIX_SET = "set";
	protected final String PREFIX_GET = "get";

	protected final String SUFFIX_INPUT = "InputBean";
	protected final String SUFFIX_OUTPUT = "OutputBean";

	protected final String PHASE_SET = "{0}.set{1}({2});";
	protected final String PHASE_GET = "{0}.get{1}({2});";
	protected final String PHASE_MESSAGE_FORMAT = "MessageFormat.format(\"{0}\", {1})";
	protected final String PHASE_MESSAGE_UTILS_GET0 = "MessageUtils.getMessage(\"{0}\")";
	protected final String PHASE_IF = "if({0} {1} {2})";
	protected final String PHASE_IF_BOOLEAN = "if({0})";
	protected final String PHASE_THROW_BUSINESS_EX0 = "throw new BusinessException(ResultMessages.error().add(\"{0}\"));";
	protected final String PHASE_THROW_BUSINESS_EX1 = "throw new BusinessException(ResultMessages.error().add(\"{0}\", {1}));";
	protected final String PHASE_THROW_BUSINESS_EX = "throw new BusinessException({0});";
	protected final String PHASE_THROW_BUSINESS_EX_WITHCAUSE = "throw new BusinessException({0},{1});";
	protected final String PHASE_CALL_METHOD_1 = "{0}.{1}({2});";
	protected final String PHASE_CALL_METHOD_2 = "{0}.{1}({2}, {3});";

	protected final Integer CHECK_EXIST = 2;
	protected final Integer NOT_CASE_SENSITIVE = 0;

	protected static final Map<Integer, String> type = new HashMap<Integer, String>();
	static {
		type.put(0, "+");
		type.put(1, "-");
		type.put(2, "*");
		type.put(3, "/");
		type.put(4, "and");
		type.put(5, "or");
		type.put(6, "not");
		type.put(7, "==");
		type.put(8, "<");
		type.put(9, "<=");
		type.put(10, ">");
		type.put(11, ">=");
		type.put(12, "!=");
		type.put(13, "(");
		type.put(14, ")");
		type.put(15, "\"\"");
		type.put(22, "null");
	}

	@Override
	public void handle(BLogicHandlerIo param, CommonModel comon){
		StringBuilder builder = new StringBuilder();
		this.handle(builder,param);
		if(this.callback!=null){
			this.callback.call(builder);
		}
	}

	public abstract void handle(StringBuilder additionParam, BLogicHandlerIo param);

	public abstract void preGencode(StringBuilder additionParam, BLogicHandlerIo param);

	public abstract void postGencode(StringBuilder additionParam, BLogicHandlerIo param);
}
