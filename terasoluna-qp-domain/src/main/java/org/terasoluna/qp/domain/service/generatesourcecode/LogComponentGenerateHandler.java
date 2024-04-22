package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.LogComponent;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Component("LogComponentGenerateHandler")
public class LogComponentGenerateHandler extends SequenceLogicGenerationHandler {

	private static final String LOG_TRACE = "{0}.trace({1});";
	private static final String LOG_DEBUG = "{0}.debug({1});";
	private static final String LOG_INFO = "{0}.info({1});";
	private static final String LOG_WARN = "{0}.warn({1});";
	private static final String LOG_ERROR = "{0}.error({1});";

	@Inject
	DetailServiceImpHandler detailServiceImpHandler;

	private LogComponent currentComponent;

	@Override
	public void handle(StringBuilder builder, BLogicHandlerIo param) {
		StringBuilder stringBuilder = new StringBuilder();

		if (this.getCurrentComponent() != null) {
			preGencode(stringBuilder, param);
			LogComponent logComponent = currentComponent;
			
			String message = "";
			List<String> result = new ArrayList<String>();

			if (logComponent.getMessageType() == 0) {
				message = KEY_DOUBLE_QUOTE + logComponent.getMessageContent() + KEY_DOUBLE_QUOTE;
			} else {
				// gen from formula
				result = detailServiceImpHandler.generateConditionByFormula(param, logComponent.getMessageFormulaDetails());
				message = result.get(0);
				stringBuilder.append(KEY_NL).append(result.get(1));
			}

			if(logComponent.getLevel() != null){
				stringBuilder.append(KEY_TAB_2);
				switch (logComponent.getLevel()) {
					case 0:
						stringBuilder.append(MessageFormat.format(LOG_TRACE, "log", message));
						break;
					case 1:
						stringBuilder.append(MessageFormat.format(LOG_DEBUG, "log", message));
						break;
					case 2:
						stringBuilder.append(MessageFormat.format(LOG_INFO, "log", message));
						break;
					case 3:
						stringBuilder.append(MessageFormat.format(LOG_WARN, "log", message));
						break;
					case 4:
						stringBuilder.append(MessageFormat.format(LOG_ERROR, "log", message));
						break;
				}
				stringBuilder.append(KEY_LINE_BREAK);
			}
		}

		postGencode(stringBuilder, param);
		builder.append(stringBuilder);
	}

	public LogComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(LogComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start log node");
			builder.append(KEY_NL);
			
			if(this.currentComponent !=null) {
				builder.append("// Label:" + currentComponent.getLabel());
				builder.append(KEY_NL);
				
				String remark = currentComponent.getRemark();
				if (StringUtils.isNotEmpty(remark)) {
					if (org.springframework.util.StringUtils.countOccurrencesOf(remark, "\n") > 0) {
						remark = remark.replace("\n", KEY_NL);
						builder.append(BusinessDesignConst.MULTI_COMMENT_START).append(KEY_NL).append(remark).append(KEY_NL).append(BusinessDesignConst.MULTI_COMMENT_END).append(KEY_NL);
					} else {
						builder.append(BusinessDesignConst.SINGLE_COMMENT_START).append(SPACE).append(remark).append(KEY_NL);
					}
				}
			}
		}
	}

	@Override
	public void postGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// End log node");
			builder.append(KEY_NL);
		}
	}
}
