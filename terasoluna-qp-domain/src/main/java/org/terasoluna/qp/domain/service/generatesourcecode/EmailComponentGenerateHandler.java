package org.terasoluna.qp.domain.service.generatesourcecode;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.EmailComponent;
import org.terasoluna.qp.domain.model.EmailRecipient;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Component("EmailComponentGenerateHandler")
public class EmailComponentGenerateHandler extends SequenceLogicGenerationHandler {

	private EmailComponent currentComponent;
	
	@Inject
	private DetailServiceImpHandler detailServiceImpHandler;
	
	private String STR_ADDRESS_MAIL_SEND = "message.%s(new String[]{%s});";
	
	/**
	 * @return the currentComponent
	 */
	public EmailComponent getCurrentComponent() {
		return currentComponent;
	}

	/**
	 * @param currentComponent the currentComponent to set
	 */
	public void setCurrentComponent(EmailComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	@Override
	public void handle(StringBuilder builder, BLogicHandlerIo param) {
		
		if (currentComponent != null) {
			preGencode(builder, param);
			
			builder.append("message = new SimpleMailMessage(templateMessage);");
			builder.append(KEY_NL);
			builder.append("// Mail header");
			builder.append(KEY_NL);
			
			if(CollectionUtils.isNotEmpty(currentComponent.getEmailRecipients())) {
				StringBuilder toStrBDSetget = new StringBuilder(StringUtils.EMPTY);
				StringBuilder toStrBD = new StringBuilder(StringUtils.EMPTY);
				StringBuilder ccStrBDSetget = new StringBuilder(StringUtils.EMPTY);
				StringBuilder ccStrBD = new StringBuilder(StringUtils.EMPTY);
				StringBuilder bcStrBDSetget = new StringBuilder(StringUtils.EMPTY);
				StringBuilder bcStrBD = new StringBuilder(StringUtils.EMPTY);
				for (EmailRecipient iterEmailRecp : currentComponent.getEmailRecipients()) {
					// Input by hand
					switch (iterEmailRecp.getType()) {
					// to
					case 0:
						this.buildHeaderMailAddress(param, iterEmailRecp, toStrBD, toStrBDSetget);
						break;
					// cc
					case 1:
						this.buildHeaderMailAddress(param, iterEmailRecp, ccStrBD, ccStrBDSetget);
						break;
					// bc
					case 2:
						this.buildHeaderMailAddress(param, iterEmailRecp, bcStrBD, bcStrBDSetget);
						break;
					}
				}

				// address header
				// to
				if(StringUtils.isNotEmpty(toStrBDSetget.toString())) builder.append(toStrBDSetget.toString());
				if(StringUtils.isNotEmpty(toStrBD.toString())) {
					builder.append(String.format(STR_ADDRESS_MAIL_SEND, "setTo", toStrBD.toString()));
					builder.append(KEY_NL);
				}
				// cc
				if(StringUtils.isNotEmpty(ccStrBDSetget.toString())) builder.append(ccStrBDSetget.toString());
				if(StringUtils.isNotEmpty(ccStrBD.toString())) {
					builder.append(String.format(STR_ADDRESS_MAIL_SEND, "setCc", ccStrBD.toString()));
					builder.append(KEY_NL);
				}
				// bc
				if(StringUtils.isNotEmpty(bcStrBDSetget.toString())) builder.append(bcStrBDSetget.toString());
				if(StringUtils.isNotEmpty(bcStrBD.toString())) {
					builder.append(String.format(STR_ADDRESS_MAIL_SEND, "setBcc", bcStrBD.toString()));
					builder.append(KEY_NL);
				}
			}
			
			// subject

			if (currentComponent.getSubjectType() == 0) {
				builder.append("message.setSubject(").append("\"").append(StringUtils.isNotEmpty(currentComponent.getSubjectContent())
						? currentComponent.getSubjectContent():StringUtils.EMPTY).append("\"").append(");");
			} else if (currentComponent.getSubjectType() == 1 && currentComponent.getSubjectType() != null) {
				builder.append("message.setSubject(");
				// Using formular
				List<String> subject = detailServiceImpHandler.generateConditionByFormula(param, currentComponent.getSubjectFormulaDetails());
				// Setting setter/ getter
				if(StringUtils.isNotEmpty(subject.get(1))) builder.append(subject.get(1));
				if(StringUtils.isNotEmpty(subject.get(0))) builder.append(subject.get(0));
				
				builder.append(");");
			}
			builder.append(KEY_NL);
			
			// send date
			builder.append("message.setSentDate(new java.util.Date(System.currentTimeMillis()));");
			builder.append(KEY_NL);
			// Mail content
			builder.append("// Mail content");
			builder.append(KEY_NL);
			builder.append("message.setText(").append("\"");
			String content = currentComponent.getEmailContent().getContent().replace("\n", "\\n");
			builder.append(content);
			builder.append("\"").append(");");
			// Call method send mail
			builder.append(KEY_NL);
			builder.append("// Send mail");
			builder.append(KEY_NL);
			builder.append("mailSender.send(message);");
			
			postGencode(builder, param);
		}
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start email node");
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

	private void buildHeaderMailAddress(BLogicHandlerIo param, EmailRecipient iterEmailRecp, StringBuilder strBD, StringBuilder strBDSetget) {
		List<String> resultFormula = null;
		if(iterEmailRecp.getRecipientType() == 0) {
			if(StringUtils.isEmpty(strBD.toString())) {
				strBD.append("\"").append(iterEmailRecp.getRecipientContent()).append("\"");
			} else {
				strBD.append(",").append("\"").append(iterEmailRecp.getRecipientContent()).append("\"");
			}
		} else if(iterEmailRecp.getRecipientType() == 1 && iterEmailRecp.getRecipientFormulaId() != null) {
			// Using formular
			resultFormula = detailServiceImpHandler.generateConditionByFormula(param, iterEmailRecp.getRecipientFormulaDetails());
			// Setting setter/ getter
			if(StringUtils.isNotEmpty(resultFormula.get(1))) strBDSetget.append(resultFormula.get(1)).append(KEY_NL);
			// Setter email address
			if(StringUtils.isEmpty(strBD.toString())) {
				strBD.append(resultFormula.get(0));
			} else {
				strBD.append(", ").append(resultFormula.get(0));
			}
		}
	}
	
	@Override
	public void postGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// End email node");
			builder.append(KEY_NL);
		}
	}

}
