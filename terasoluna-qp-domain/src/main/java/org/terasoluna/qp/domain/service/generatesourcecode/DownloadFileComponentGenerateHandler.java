package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.DownloadFileComponent;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.repository.generatesourcecode.GenerateSourceCodeRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Component("DownloadFileComponentGenerateHandler")
public class DownloadFileComponentGenerateHandler extends SequenceLogicGenerationHandler {

	@Inject
	BusinessLogicGenerateHandler businessLogicGenerateHandler;

	@Inject
	GenerateSourceCodeRepository generateSourceCodeRepository;

	private static final String NL = "\n\t\t";

	private DownloadFileComponent currentComponent;

	@Inject
	DetailServiceImpHandler detailServiceImpHandler;

	@Override
	public void handle(StringBuilder builder, BLogicHandlerIo param) {
		StringBuilder stringBuilder = new StringBuilder();

		if (this.currentComponent != null) {
			preGencode(stringBuilder, param);
			DownloadFileComponent downloadFileComponent = currentComponent;
			
			String fileName = "";
			List<String> result = new ArrayList<String>();
			if (downloadFileComponent.getFileNameType() == BusinessDesignConst.DownloadFileComponent.PATH_TYPE_CUSTOMIZE) {
				fileName = downloadFileComponent.getFileNameContent();
			} else {
				// gen from formula
				result =  detailServiceImpHandler.generateConditionByFormula(param, currentComponent.getFileNameFormulaDetails());
				fileName = result.get(0);
			}
				
			OutputBean obj = downloadFileComponent.getOutputBean();
			
			String getObject = "";
			
			if (obj != null) {
				getObject = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), true, obj.getOutputBeanId(), 2, "ou", null);
			}
			
			if (obj != null && obj.getDataType() == GenerateSourceCodeConst.DataType.BYTE && obj.getArrayFlg()) {
				stringBuilder.append(NL).append(MessageFormat.format("final HttpHeaders headers{0} = new HttpHeaders();", currentComponent.getDownloadFileComponentId()));
				stringBuilder.append(NL).append(MessageFormat.format("headers{0}.setContentType(MediaType.APPLICATION_OCTET_STREAM);", currentComponent.getDownloadFileComponentId()));

				if (downloadFileComponent.getFileNameType() == BusinessDesignConst.DownloadFileComponent.PATH_TYPE_CUSTOMIZE) {
					stringBuilder.append(NL).append(MessageFormat.format("headers{0}.set(\"Content-Disposition\",  \"attachment; filename=\\\"{1}\\\"\");", currentComponent.getDownloadFileComponentId(), fileName));
				} else {
					if(StringUtils.isNotEmpty(result.get(1))) {
						stringBuilder.append(NL).append(result.get(1));
					}
					stringBuilder.append(NL).append(MessageFormat.format("headers{0}.set(\"Content-Disposition\",  \"attachment; filename=\\\"\"+{1}+\"\\\"\");", currentComponent.getDownloadFileComponentId(), fileName));
				}
				stringBuilder.append(NL).append(MessageFormat.format("ResponseEntity<byte[]> response{1} = new ResponseEntity<byte[]>({0}, headers{1}, HttpStatus.OK);", getObject, currentComponent.getDownloadFileComponentId())); 
				stringBuilder.append(NL).append(MessageFormat.format("{0}.setResponseEntity(response{1});", "ou", currentComponent.getDownloadFileComponentId()));
			} else {
				stringBuilder.append(NL).append("// File content is not byte[] or null").append("\n");
			}
		}
		
		builder.append(stringBuilder);
		postGencode(builder, param);
	}	

	public DownloadFileComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(DownloadFileComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start download file node");
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
			builder.append("// End download file node");
			builder.append(KEY_NL);
		}
	}
}
