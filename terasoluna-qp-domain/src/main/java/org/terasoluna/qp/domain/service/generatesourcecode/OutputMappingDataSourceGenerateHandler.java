package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.OutputBean;

@Component("OutputMappingDataSourceGenerateHandler")
public class OutputMappingDataSourceGenerateHandler extends SequenceLogicGenerationHandler {

	private static final String NL = "\n\t\t";

	@Inject
	DetailServiceImpHandler detailServiceImpHandler;
	
	private BusinessDesign blogicCurr;
	
	private static final boolean IS_GETTER = true;
	private static final boolean IS_SETTER = false;
	
	@Override
	public void handle(StringBuilder strBuilder, BLogicHandlerIo param) {
		
		if(param != null && param.getBusinessDesign() != null) {
			blogicCurr = param.getBusinessDesign();
			
			strBuilder.append(NL);
			strBuilder.append(NL).append("// Start mapping output to data source");
			strBuilder.append(NL);

			List<OutputBean> lstDataSourceOutputBean = param.getBusinessDesign().getLstDataSourceOutputBean();
			if (CollectionUtils.isNotEmpty(lstDataSourceOutputBean)) {
				strBuilder.append(MessageFormat.format("{0}DataSourceOutputBean {1}DataSourceOutputBean = new {0}DataSourceOutputBean();", WordUtils.capitalize(blogicCurr.getBusinessLogicCode()), WordUtils.uncapitalize(blogicCurr.getBusinessLogicCode())));
				
				String outputBeanSyntax = "";
				String businessLogicCodeRefer = "";
				
				for (OutputBean ou : lstDataSourceOutputBean) {
					businessLogicCodeRefer = WordUtils.uncapitalize(blogicCurr.getBusinessLogicCode());
					outputBeanSyntax = businessLogicCodeRefer + "OutputBean";
					String getAttribute = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, ou.getOutputBeanId(), 2, outputBeanSyntax, null);
					String setAttribute = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, ou.getOutputBeanId(), 2, WordUtils.uncapitalize(blogicCurr.getBusinessLogicCode())+"DataSourceOutputBean", null);
					
					strBuilder.append(NL);
					strBuilder.append(MessageFormat.format("{0}({1});", setAttribute, getAttribute));
				}

				strBuilder.append(NL).append("// End mapping output to data source");
				param.getBusinessDesign().setStrDataSourceContent(strBuilder.toString());
			}
		}
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
	}

	@Override
	public void postGencode(StringBuilder builder, BLogicHandlerIo param) {
	}

}