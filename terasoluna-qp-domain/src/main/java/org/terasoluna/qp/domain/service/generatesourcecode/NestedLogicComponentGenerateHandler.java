package org.terasoluna.qp.domain.service.generatesourcecode;

import org.springframework.stereotype.Component;

@Component("NestedLogicComponentGenerateHandler")
public class NestedLogicComponentGenerateHandler extends SequenceLogicGenerationHandler{

	@Override
	public void handle(StringBuilder builder, BLogicHandlerIo param) {
		StringBuilder content = new StringBuilder();
		String detail = param.getContent() == null ? "" : param.getContent();
		content.append(KEY_NL).append(detail);
		preGencode(builder, param);
		builder.append(content);
		postGencode(builder, param);
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start nested node");
			builder.append(KEY_NL);
			
		}
	}

	@Override
	public void postGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// End nested node");
			builder.append(KEY_NL);
		}
	}

}
