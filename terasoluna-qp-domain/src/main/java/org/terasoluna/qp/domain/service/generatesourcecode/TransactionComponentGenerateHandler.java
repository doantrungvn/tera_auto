package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.TransactionComponent;
import org.terasoluna.qp.domain.repository.generatesourcecode.GenerateSourceCodeRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Component("TransactionComponentGenerateHandler")
public class TransactionComponentGenerateHandler extends SequenceLogicGenerationHandler{

	@Inject
	BusinessLogicGenerateHandler businessLogicGenerateHandler;

	@Inject
	GenerateSourceCodeRepository generateSourceCodeRepository;

	// private String TRANSACTION_DEFINITION =
	// "TransactionDefinition {0} = BatchUtil.getTransactionDefinition({1}, {2}, {3}, {4})";
	// private String START_TRANSACTION_WITH_DEF =
	// "BatchUtil.startTransaction(transactionManager, {0});";
	private String START_TRANSACTION = "TransactionStatus {0} = BatchUtil.startTransaction(transactionManager);";
	private String COMMIT_TRANSACTION = "BatchUtil.commitTransaction(transactionManager, {0});";
	private String ROLLBACK_TRANSACTION = "BatchUtil.rollbackTransaction(transactionManager, {0});";
	// private String END_TRANSACTION =
	// "BatchUtil.endTransaction(transactionManager, {0});";
	// private String NEW_ASSIGN = "{0} {1} = {2}";
	// private String ASSIGN = "{0} = {1}";

	@Inject
	DetailServiceImpHandler detailServiceImpHandler;

	private TransactionComponent currentComponent;

	private TransactionComponent prevTransaction = new TransactionComponent();

	public TransactionComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(TransactionComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	@Override
	public void handle(StringBuilder builder, BLogicHandlerIo param) {
		StringBuilder stringBuilder = new StringBuilder();

		TransactionComponent transactionComponent = currentComponent;

		if (transactionComponent != null) {
			preGencode(stringBuilder, param);
			String content = "";
			switch (transactionComponent.getTransactionType()) {
				case 0:
					// start transaction
					content = MessageFormat.format(START_TRANSACTION, "transaction" + transactionComponent.getTransactionComponentId());
					prevTransaction.setTransactionComponentId(transactionComponent.getTransactionComponentId());
					break;
				case 1:
					// roll back transaction
					if (prevTransaction != null) {
						content = MessageFormat.format(ROLLBACK_TRANSACTION, "transaction" + prevTransaction.getTransactionComponentId());
					}
					break;
				case 2:
					// commit transaction
					if (prevTransaction != null) {
						content = MessageFormat.format(COMMIT_TRANSACTION, "transaction" + prevTransaction.getTransactionComponentId());
					}
					break;

			}
			stringBuilder.append(content).append(KEY_LINE_BREAK);
		}
		postGencode(stringBuilder, param);
		builder.append(stringBuilder);
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start transaction node");
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
			builder.append("// End transaction node");
			builder.append(KEY_NL);
		}
	}
}
