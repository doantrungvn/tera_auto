package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.ExportFileComponent;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.repository.generatesourcecode.GenerateSourceCodeRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.generatesourcecode.CommonComponentGenerateHandler.TypeOfDataType;

@Component("ExportFileComponentGenerateHandler")
public class ExportFileComponentGenerateHandler extends SequenceLogicGenerationHandler {

	@Inject
	BusinessLogicGenerateHandler businessLogicGenerateHandler;

	@Inject
	GenerateSourceCodeRepository generateSourceCodeRepository;

	private static final String NL_2TAB = "\n\t\t";
	private static final String DOUBLE_QUOTE = "\"";
	private String INIT_FILE_WRITE = "FileLineWriter<{0}> {1} = null;";
	private String ASSIGN_FILE_WRITE = "{0} = csvFileUpdateDAO.execute({1}, {2}.class);";
	private String FOR = "for ({0} {1} : {2})";
	private String CHECK_NOT_NULL = "if( {0} != null )";
	private String PRINT_DATA = "{0}.printDataLine({1});";
	private String CLOSE_COLLECTOR = "CollectorUtility.closeQuietly({0});";
	private String TRY = "try {";
	private String OPEN_CATCH = "} catch (Exception e) {";
	private String CLOSE_CATCH = "}";

	private ExportFileComponent currentComponent;

	private Project project;
	
	@Inject
	DetailServiceImpHandler detailServiceImpHandler;

	public String getClassName(Module module, BusinessDesign businessDesign, ObjectDefinition obj) {
		String className = "";
		String instanceNmObj = "";
		switch (obj.getDataType()) {
			case 0:
			case 14:
			case 16:
			case 17:
				instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, obj);
				className = this.getPackageName(module, businessDesign, obj) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj);
				break;
			case 1:
				className = "Byte";
				break;
			case 2:
				className = "Short";
				break;
			case 3:
				className = "Integer";
				break;
			case 4:
				className = "Long";
				break;
			case 5:
				className = "Float";
				break;
			case 6:
				className = "Double";
				break;
			case 7:
				className = "Character";
				break;
			case 8:
				className = "Boolean";
				break;
			case 9:
				className = "String";
				break;
			case 10:
				className = "java.math.BigDecimal";
				break;
			case 11:
			case 12:
				className = "java.sql.Timestamp";
				break;
			case 13:
				className = "java.sql.Time";
				break;
			case 15:
				className = "java.sql.Date";
				break;
		}
		return className;
	}

	private String getPackageName(Module module, BusinessDesign blogic, Object obj) {
		StringBuilder pakageName = new StringBuilder(project.getPackageName());
		String pakageExternal = "";
		String code = null;
		int dataType = -1;

		if (obj instanceof ObjectDefinition) {
			ObjectDefinition objDef = (ObjectDefinition) obj;
			dataType = objDef.getDataType();
			pakageExternal = objDef.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_OBJ_DEFINITION;
		}

		switch (dataType) {
			case TypeOfDataType.OBJECT :
				if (Boolean.TRUE.equals(blogic.getCustomizeFlg())) {
					pakageName.append(".batch.").append((blogic.getBlogicType().equals(1)?"commonservicecustomize":module.getModuleCode()))
					.append(".").append(blogic.getBusinessLogicCode()).append(code).append(".");
				} else {
					pakageName.append(".batch.").append((blogic.getBlogicType().equals(1)?"commonservice":module.getModuleCode()))
					.append(".").append(blogic.getBusinessLogicCode()).append(code).append(".");
				}
				break;
			case TypeOfDataType.ENTITY :
				pakageName.append(".batch.model.");
				break;
			case TypeOfDataType.COMMON_OBJECT :
				pakageName.append(".batch.commonobject.");
				break;
			case TypeOfDataType.EXTERNAL_OBJECT :
				pakageName = new StringBuilder();
				pakageName.append(pakageExternal).append(".");
				break;
		}

		return GenerateSourceCodeUtil.normalizedPackageName(pakageName.toString());
	}
	
	@Override
	public void handle(StringBuilder builder, BLogicHandlerIo param) {
		StringBuilder stringBuilder = new StringBuilder();

		if (this.currentComponent != null) {
			preGencode(stringBuilder, param);
			ExportFileComponent exportFileComponent = currentComponent;
			String outputFile = "";
			List<String> result = new ArrayList<String>();
			
			if (exportFileComponent.getDestinationPathType() == null || exportFileComponent.getDestinationPathType() == 0) {
				outputFile = DOUBLE_QUOTE + exportFileComponent.getDestinationPathContent() + DOUBLE_QUOTE;
			} else {
				// gen from formula
				result = detailServiceImpHandler.generateConditionByFormula(param, currentComponent.getDestinationPathFormulaDetails());
				outputFile = result.get(0);
			}

			ObjectDefinition obj = exportFileComponent.getObjectDefinition();
			String className = this.getClassName(param.getModule(), param.getBusinessDesign(), obj);
			String getParam = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), true, obj.getObjectDefinitionId(), 1, param.getBlogicObSyntax(), null);
			
			String collectorName = "fileLineWriter" + exportFileComponent.getExportFileComponentId();

			stringBuilder.append(NL_2TAB).append(MessageFormat.format(INIT_FILE_WRITE, className, collectorName));
			stringBuilder.append(NL_2TAB).append(TRY);
			
			if(result.size() > 1 && StringUtils.isNotEmpty(result.get(1))){
				stringBuilder.append(NL_2TAB).append("\t").append(result.get(1));
			}
			
			stringBuilder.append(NL_2TAB).append("\t").append(MessageFormat.format(ASSIGN_FILE_WRITE, collectorName, outputFile, className));
			if (obj.getArrayFlg()) {
				stringBuilder.append(NL_2TAB).append("\t").append(MessageFormat.format(CHECK_NOT_NULL, getParam)).append("{");
				stringBuilder.append(NL_2TAB).append(NL_2TAB).append(MessageFormat.format(FOR, className, "item", getParam)).append("{");
				stringBuilder.append(NL_2TAB).append(NL_2TAB).append("\t").append(MessageFormat.format(PRINT_DATA, collectorName, "item"));
				stringBuilder.append(NL_2TAB).append(NL_2TAB).append("}");
				stringBuilder.append(NL_2TAB).append("\t").append("}");

			} else {
				stringBuilder.append(NL_2TAB).append("\t").append(MessageFormat.format(CHECK_NOT_NULL, getParam)).append("{");
				stringBuilder.append(NL_2TAB).append(NL_2TAB).append(MessageFormat.format(PRINT_DATA, collectorName, getParam));
				stringBuilder.append(NL_2TAB).append("\t").append("}");
			}
			stringBuilder.append(NL_2TAB).append("\t").append(MessageFormat.format(CLOSE_COLLECTOR, collectorName));
			stringBuilder.append(NL_2TAB).append(OPEN_CATCH);
			stringBuilder.append(NL_2TAB).append("\t").append(MessageFormat.format(CLOSE_COLLECTOR, collectorName));
			stringBuilder.append(NL_2TAB).append("\t").append("throw e;");
			stringBuilder.append(NL_2TAB).append(CLOSE_CATCH);
		}
		postGencode(stringBuilder, param);
		builder.append(stringBuilder);
	}

	public ExportFileComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(ExportFileComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		project = param.getProject();
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start export file node");
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
			builder.append("// End export file node");
			builder.append(KEY_NL);
		}
	}
}
