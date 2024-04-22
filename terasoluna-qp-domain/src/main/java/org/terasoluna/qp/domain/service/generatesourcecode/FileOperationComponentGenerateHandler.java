package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.FileOperationComponent;
import org.terasoluna.qp.domain.model.MergeFileDetail;
import org.terasoluna.qp.domain.repository.generatesourcecode.GenerateSourceCodeRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Component("FileOperationComponentGenerateHandler")
public class FileOperationComponentGenerateHandler extends SequenceLogicGenerationHandler {

	@Inject
	BusinessLogicGenerateHandler businessLogicGenerateHandler;

	@Inject
	GenerateSourceCodeRepository generateSourceCodeRepository;
	
	/** copy file */
	private static final String COPY_FILE = "fileControl.copyFile({0}, {1});";
	/** delete file */
	private static final String DELETE_FILE = "fileControl.deleteFile({0});";
	/** rename file */
	private static final String RENAME_FILE = "fileControl.renameFile({0}, {1});";
	/** merge file */
	private static final String MERGE_FILE = "fileControl.mergeFile({0}, {1});";
	/** List<{0}> {1} = new ArrayList<{0}>(); */
	private static final String NEW_LIST_OBJECT = "List<{0}> {1} = new ArrayList<{0}>();";
	/** add list */
	private static final String ADD_OBJECT = "{0}.add({1});";

	@Inject
	DetailServiceImpHandler detailServiceImpHandler;

	private FileOperationComponent currentComponent;

	public FileOperationComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(FileOperationComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	@Override
	public void handle(StringBuilder builder, BLogicHandlerIo param) {
		StringBuilder stringBuilder = new StringBuilder();

		if (currentComponent != null) {
			preGencode(stringBuilder, param);
			
			String sourcePath = "";
			String destinationPath = "";
			String content = "";
			
			List<String> resultSourcePath = new ArrayList<String>();
			List<String> resultDestinationPath = new ArrayList<String>();
			List<String> resultFilePath = new ArrayList<String>();

			switch (currentComponent.getType()) {
				case 0:

					if (currentComponent.getSourcePathType() == 0) {
						sourcePath = KEY_DOUBLE_QUOTE + currentComponent.getSourcePathContent() + KEY_DOUBLE_QUOTE;
					} else {
						// gen from formula
						resultSourcePath = detailServiceImpHandler.generateConditionByFormula(param, currentComponent.getSourcePathFormulaDetails());
						sourcePath = resultSourcePath.get(0);
						if(StringUtils.isNotEmpty(resultSourcePath.get(1))) stringBuilder.append(KEY_TAB_2).append(resultSourcePath.get(1));
					}
					if (currentComponent.getDestinationPathType() == 0) {
						destinationPath = KEY_DOUBLE_QUOTE + currentComponent.getDestinationPathContent() + KEY_DOUBLE_QUOTE;
					} else {
						// gen from formula
						resultDestinationPath = detailServiceImpHandler.generateConditionByFormula(param, currentComponent.getDestinationPathFormulaDetails());
						destinationPath = resultDestinationPath.get(0);
						if(StringUtils.isNotEmpty(resultDestinationPath.get(1))) stringBuilder.append(KEY_TAB_2).append(resultDestinationPath.get(1));
					}
					
					content = MessageFormat.format(COPY_FILE, sourcePath, destinationPath);
					stringBuilder.append(content).append(KEY_LINE_BREAK);
					break;
				case 1:

					if (currentComponent.getSourcePathType() == 0) {
						sourcePath = KEY_DOUBLE_QUOTE + currentComponent.getSourcePathContent() + KEY_DOUBLE_QUOTE;
					} else {
						// gen from formula
						resultSourcePath = detailServiceImpHandler.generateConditionByFormula(param, currentComponent.getSourcePathFormulaDetails());
						sourcePath = resultSourcePath.get(0);
						if(StringUtils.isNotEmpty(resultSourcePath.get(1))) stringBuilder.append(KEY_TAB_2).append(resultSourcePath.get(1));
					}
					
					content = MessageFormat.format(DELETE_FILE, sourcePath);
					stringBuilder.append(content).append(KEY_LINE_BREAK);
					break;
				case 2:

					if (currentComponent.getSourcePathType() == 0) {
						sourcePath = KEY_DOUBLE_QUOTE + currentComponent.getSourcePathContent() + KEY_DOUBLE_QUOTE;
					} else {
						// gen from formula
						resultSourcePath = detailServiceImpHandler.generateConditionByFormula(param, currentComponent.getSourcePathFormulaDetails());
						sourcePath = resultSourcePath.get(0);
						if(StringUtils.isNotEmpty(resultSourcePath.get(1))) stringBuilder.append(KEY_TAB_2).append(resultSourcePath.get(1));
					}
					if (currentComponent.getNewFilenameType() == 0) {
						destinationPath = KEY_DOUBLE_QUOTE + currentComponent.getNewFilenameContent() + KEY_DOUBLE_QUOTE;
					} else {
						// gen from formula
						resultDestinationPath = detailServiceImpHandler.generateConditionByFormula(param, currentComponent.getNewFilenameFormulaDetails());
						destinationPath = resultDestinationPath.get(0);
						if(StringUtils.isNotEmpty(resultDestinationPath.get(1))) stringBuilder.append(KEY_TAB_2).append(resultDestinationPath.get(1));
					}

					content = MessageFormat.format(RENAME_FILE, sourcePath, destinationPath);
					stringBuilder.append(content).append(KEY_LINE_BREAK);
					break;
				case 3:
					
					if (currentComponent.getSourcePathType() == 0) {
						sourcePath = KEY_DOUBLE_QUOTE + currentComponent.getSourcePathContent() + KEY_DOUBLE_QUOTE;
					} else {
						// gen from formula
						resultSourcePath = detailServiceImpHandler.generateConditionByFormula(param, currentComponent.getSourcePathFormulaDetails());
						sourcePath = resultSourcePath.get(0);
						if(StringUtils.isNotEmpty(resultSourcePath.get(1))) stringBuilder.append(KEY_TAB_2).append(resultSourcePath.get(1));
					}
					
					if (currentComponent.getDestinationPathType() == 0) {
						destinationPath = KEY_DOUBLE_QUOTE + currentComponent.getDestinationPathContent() + KEY_DOUBLE_QUOTE;
					} else {
						// gen from formula
						resultDestinationPath = detailServiceImpHandler.generateConditionByFormula(param, currentComponent.getDestinationPathFormulaDetails());
						destinationPath = resultDestinationPath.get(0);
						if(StringUtils.isNotEmpty(resultDestinationPath.get(1))) stringBuilder.append(KEY_TAB_2).append(resultDestinationPath.get(1));
					}
					String listFileLabel = "listFile" + currentComponent.getFileOperationComponentId();
					stringBuilder.append(MessageFormat.format(NEW_LIST_OBJECT, "String", listFileLabel)).append(KEY_LINE_BREAK);
					// Adding source path default
					stringBuilder.append(KEY_TAB_2).append(MessageFormat.format(ADD_OBJECT, listFileLabel, sourcePath)).append(KEY_LINE_BREAK);
					// Adding extern file
					for (MergeFileDetail item : currentComponent.getLstMergeFileDetails()) {
						String filePath = "";
						if (item.getSourcePathType() == 0) {
							filePath = KEY_DOUBLE_QUOTE + item.getSourcePathContent() + KEY_DOUBLE_QUOTE;
						} else {
							// gen from formula
							resultFilePath = detailServiceImpHandler.generateConditionByFormula(param, item.getSourcePathFormulaDetails());
							filePath = resultFilePath.get(0);
							if(StringUtils.isNotEmpty(resultFilePath.get(1))) stringBuilder.append(KEY_TAB_2).append(resultFilePath.get(1));
						}
						stringBuilder.append(KEY_TAB_2).append(MessageFormat.format(ADD_OBJECT, listFileLabel, filePath)).append(KEY_LINE_BREAK);
					}

					content = MessageFormat.format(MERGE_FILE, listFileLabel, destinationPath);
					stringBuilder.append(KEY_TAB).append(content).append(KEY_LINE_BREAK);
					break;
			}

		}
		postGencode(stringBuilder, param);
		builder.append(stringBuilder);
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start File operation node");
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
			builder.append("// End File operation node");
			builder.append(KEY_NL);
		}
	}

}
