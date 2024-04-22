package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.DecisionComponent;
import org.terasoluna.qp.domain.model.DecisionInputValue;
import org.terasoluna.qp.domain.model.DecisionOutputValue;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.DecisionTableInputBean;
import org.terasoluna.qp.domain.model.DecisionTableOutputBean;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableInputBeanRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableOutputBeanRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.generatesourcecode.CommonComponentGenerateHandler.TypeOfDataType;

@Component("DecisionComponentGenerateHandler")
public class DecisionComponentGenerateHandler extends SequenceLogicGenerationHandler {

	@Inject
	DecisionTableRepository decisionTableRepository;
	
	@Inject
	DecisionTableInputBeanRepository decisionTableInputBeanRepository;

	@Inject
	DecisionTableOutputBeanRepository decisionTableOutputBeanRepository;
	
	@Inject
	DetailServiceImpHandler detailServiceImpHandler;

	private DecisionTable decisionInfor;
	private DecisionComponent currentComponent = new DecisionComponent();
	private static final String NL = "\n\t\t";
	private static final String INPUT_SETTER = "{0}({1});";
	private static final String OBJECT_DEFINITION_BLOGIC_SETTER = "{0}({1});";
	private static final String INIT_DECISION_INPUT = "{0}InputBean {1} = new {0}InputBean();";
	private static final String INIT_DECISION_OUTPUT = "{0}OutputBean {1} = decisionLogicDesignCommonService.{2}({3});";
	
	private static Map<Long, BusinessDesign> mBusinessLogicId = new HashMap<Long, BusinessDesign>();
	private static int count = 0;
	
	/**
	 * @return the currentComponent
	 */
	public DecisionComponent getCurrentComponent() {
		return currentComponent;
	}

	/**
	 * @param currentComponent the currentComponent to set
	 */
	public void setCurrentComponent(DecisionComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	public void handle(StringBuilder logicStringBuilder, BLogicHandlerIo paramIO) {
		
		// Content of decision
		StringBuilder strGenDecisionDetails = new StringBuilder(NL);
		// Processing for component current
		if (currentComponent!= null) {
			preGencode(logicStringBuilder, paramIO);
			if (!mBusinessLogicId.containsKey(paramIO.getBusinessDesign().getBusinessLogicId())) {
				count = 0;
				mBusinessLogicId.put(paramIO.getBusinessDesign().getBusinessLogicId(), paramIO.getBusinessDesign());
			} else {
				// More decision note in the blogic -> maybe duplicate
				count++;
			}
			
			this.decisionInfor = decisionTableRepository.findOneByDecisionTbId(currentComponent.getDecisionTableId());
			
			String scopePakageOfParent = decisionInfor.getModuleId()==null?StringUtils.EMPTY:decisionInfor.getModuleCode()+ ".";
			
			String decisionTableCode = decisionInfor.getDecisionTbCode();

			String inputSyntax = "input" + GenerateSourceCodeUtil.normalizedClassName(decisionTableCode)+count;
			
			// Initial decision input bean
			String place = ".domain.decision.";
			if(BusinessDesignConst.MODULE_TYPE_BATCH.equals(paramIO.getModule().getModuleType())) {
				place = ".batch.decision.";
			}
			
			strGenDecisionDetails.append(
					MessageFormat.format(INIT_DECISION_INPUT, GenerateSourceCodeUtil.normalizedPackageName(paramIO.getProject().getPackageName()
							+ place +scopePakageOfParent)+GenerateSourceCodeUtil.normalizedClassName(decisionTableCode), inputSyntax));
			
			List<DecisionTableInputBean> lstDecisionInputBean = decisionTableInputBeanRepository.findAllDecisionInputBeanByListDecisionId(Arrays.asList(this.decisionInfor));
			// Get all output bean of decision
			List<DecisionTableOutputBean> lstDecisionOutputBean = decisionTableOutputBeanRepository.findAllDecisionOutputBeanByListDecisionId(Arrays.asList(this.decisionInfor));

			Map<String, List<?>> mAllBlogicCurrent = paramIO.getmAllParentAndSeflByLevelOfInOutObj();
			Map<String, List<?>> mAllDecisionRefer = detailServiceImpHandler
					.getAllParentAndSeflByLevelOfInOutObj(2, lstDecisionInputBean, null, lstDecisionOutputBean);
			String instanceNmObj = StringUtils.EMPTY;
			
			// Build setting value from parameter to input of decision input bean
			List<DecisionInputValue> lstInputValue = currentComponent.getParameterInputBeans();
			for (DecisionInputValue in : lstInputValue) {
				strGenDecisionDetails.append(NL);
				DecisionTableInputBean decisionIn =  (DecisionTableInputBean) getObjByTypeScope(0, 
						mAllDecisionRefer.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID + in.getDecisionInputBeanId().toString()),
						in.getDecisionInputBeanId().toString());

				if(decisionIn == null) continue;
				
				strGenDecisionDetails.append(NL);
				
				if (decisionIn != null && BusinessDesignConst.DataType.OBJECT.equals(decisionIn.getDataType()) 
						|| BusinessDesignConst.DataType.ENTITY.equals(decisionIn.getDataType()) 
						|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(decisionIn.getDataType()) 
						|| BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(decisionIn.getDataType())) {
					
					String getterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(2, mAllDecisionRefer, true, 
							decisionIn.getDecisionInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, null);
					// Get name of instance object
					instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.DECISION , 
							BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID,decisionIn);
					// Get new instance of object data type
					String instanceOf = "new " + getPackageName(false , paramIO , 0 , decisionIn) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()";
					strGenDecisionDetails.append(String.format("if (%s == null){", getterInReferParent)).append("\n\t\t\t");
					
					String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(2, mAllDecisionRefer, false, 
							decisionIn.getDecisionInputBeanId(), 0, inputSyntax, null)+"("+instanceOf+");";
					strGenDecisionDetails.append("\t").append(setterInReferParent).append("\n\t\t}");

					continue;
				}
				
				String setterOfDecisionIn = detailServiceImpHandler.getterAndSetterOfParameter(2, mAllDecisionRefer, false,
						decisionIn.getDecisionInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, null);
				
				String getterOfParameter = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), true,
						in.getParameterId(), in.getParameterScope(), 
						DetailServiceImpHandler.mNameParameterScope.getOrDefault(in.getParameterScope(), StringUtils.EMPTY), in.getLstParameterIndex());
				
				if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(in.getParameterScope())) {
					InputBean tm = (InputBean) detailServiceImpHandler.getObjByTypeScope(0, in.getParameterScope(), in.getParameterId(), 
							paramIO.getmAllParentAndSeflByLevelOfInOutObj().get(in.getParameterScope().toString() + in.getParameterId()));
					getterOfParameter = BusinessLogicGenerateHelper.getContentByCastDataType(decisionIn.getDataType(), tm.getDataType(), getterOfParameter);
				} else if(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(in.getParameterScope())) {
					ObjectDefinition tm = (ObjectDefinition) detailServiceImpHandler.getObjByTypeScope(0, in.getParameterScope(), in.getParameterId(), 
							paramIO.getmAllParentAndSeflByLevelOfInOutObj().get(in.getParameterScope().toString() + in.getParameterId()));
					getterOfParameter = BusinessLogicGenerateHelper.getContentByCastDataType(decisionIn.getDataType(), tm.getDataType(), getterOfParameter);
				} else if(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(in.getParameterScope())) {
					OutputBean tm = (OutputBean) detailServiceImpHandler.getObjByTypeScope(0, in.getParameterScope(), in.getParameterId(), 
							paramIO.getmAllParentAndSeflByLevelOfInOutObj().get(in.getParameterScope().toString() + in.getParameterId()));
					getterOfParameter = BusinessLogicGenerateHelper.getContentByCastDataType(decisionIn.getDataType(), tm.getDataType(), getterOfParameter);
				}

				strGenDecisionDetails.append(MessageFormat.format(INPUT_SETTER, setterOfDecisionIn, getterOfParameter));
			}

			// Build setting value for output 
			strGenDecisionDetails.append("\n\t\t");
			List<DecisionOutputValue> lstOutputValue = currentComponent.getParameterOutputBeans();
			
			if (CollectionUtils.isNotEmpty(lstOutputValue)) {
				String outputSyntax = "output" + GenerateSourceCodeUtil.normalizedClassName(decisionTableCode)+count;
				strGenDecisionDetails.append(
						MessageFormat.format(
								INIT_DECISION_OUTPUT, GenerateSourceCodeUtil.normalizedPackageName(paramIO.getProject().getPackageName()
										+ place +scopePakageOfParent)+GenerateSourceCodeUtil.normalizedClassName(decisionTableCode), 
										outputSyntax, decisionTableCode, inputSyntax));

				for (DecisionOutputValue ou : lstOutputValue) {

					strGenDecisionDetails.append(NL);
					DecisionTableOutputBean decisionOut = (DecisionTableOutputBean) getObjByTypeScope(2, 
							mAllDecisionRefer.get(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID + ou.getDecisionOutputBeanId().toString()),
							ou.getDecisionOutputBeanId().toString());
					Object obj = NavigatorComponentGenerateHandler.getObjByTypeScope(ou.getTargetScope(), 
							mAllBlogicCurrent.get(ou.getTargetScope() + ou.getTargetId()), ou.getTargetId());

					String codeSyntax = StringUtils.EMPTY;
					boolean arrayFlg = false;
					int dataType = -1;
					switch (ou.getTargetScope()) {
						case 0: codeSyntax = paramIO.getBlogicInSyntax(); 
							InputBean in = (InputBean) obj; arrayFlg = in.getArrayFlg();
							dataType = in.getDataType();
							break;
						case 1: codeSyntax = paramIO.getBlogicObSyntax(); 
							ObjectDefinition ob = (ObjectDefinition) obj; arrayFlg = ob.getArrayFlg();
							dataType = ob.getDataType();
							break;
						case 2: codeSyntax = paramIO.getBlogicOutputSyntax();  
							OutputBean oub = (OutputBean) obj; arrayFlg = oub.getArrayFlg();
							dataType = oub.getDataType();
							break;
					}
					
					if (obj != null && BusinessDesignConst.DataType.OBJECT.equals(dataType) 
							|| BusinessDesignConst.DataType.ENTITY.equals(dataType) 
							|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(dataType) 
							|| BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(dataType)) {
						
						String getterInCurrParent = detailServiceImpHandler.getterAndSetterOfParameter(GenerateSourceCodeConst.GenerateScope.BLOGIC, 
								mAllBlogicCurrent, true, ou.getTargetId(), ou.getTargetScope(), codeSyntax, null);
						// Get name of instance object
						instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC , ou.getTargetScope(), obj);
						// Get new instance of object data type
						String instanceOf = "new " + getPackageName(true , paramIO , ou.getTargetScope() , obj) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()";
						strGenDecisionDetails.append(String.format("if (%s == null){", getterInCurrParent)).append("\n\t\t\t");
						
						String setterInCurrParent = detailServiceImpHandler.getterAndSetterOfParameter(GenerateSourceCodeConst.GenerateScope.BLOGIC, mAllBlogicCurrent, 
								false, ou.getTargetId(), ou.getTargetScope(), codeSyntax, null)+"("+instanceOf+");";
						
						strGenDecisionDetails.append("\t").append(setterInCurrParent).append("\n\t\t}");

						continue;
					}
					
					String setterOfParameter = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, false,
							ou.getTargetId(), ou.getTargetScope(), codeSyntax, ou.getLstTargetIndex());
					
					String getterOfDecisionOut = detailServiceImpHandler.getterAndSetterOfParameter(2, mAllDecisionRefer, true,
							decisionOut.getDecisionOutputBeanId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, outputSyntax, null);
					
					if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(ou.getTargetScope())) {
						InputBean tm = (InputBean) detailServiceImpHandler.getObjByTypeScope(0, ou.getTargetScope(), ou.getTargetId(), 
								paramIO.getmAllParentAndSeflByLevelOfInOutObj().get(ou.getTargetScope().toString() + ou.getTargetId()));
						getterOfDecisionOut = BusinessLogicGenerateHelper.getContentByCastDataType(tm.getDataType(), decisionOut.getDataType(), getterOfDecisionOut);
					} else if(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(ou.getTargetScope())) {
						ObjectDefinition tm = (ObjectDefinition) detailServiceImpHandler.getObjByTypeScope(0, ou.getTargetScope(), ou.getTargetId(), 
								paramIO.getmAllParentAndSeflByLevelOfInOutObj().get(ou.getTargetScope().toString() + ou.getTargetId()));
						getterOfDecisionOut = BusinessLogicGenerateHelper.getContentByCastDataType(tm.getDataType(), decisionOut.getDataType(), getterOfDecisionOut);
					} else if(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(ou.getTargetScope())) {
						OutputBean tm = (OutputBean) detailServiceImpHandler.getObjByTypeScope(0, ou.getTargetScope(), ou.getTargetId(), 
								paramIO.getmAllParentAndSeflByLevelOfInOutObj().get(ou.getTargetScope().toString() + ou.getTargetId()));
						getterOfDecisionOut = BusinessLogicGenerateHelper.getContentByCastDataType(tm.getDataType(), decisionOut.getDataType(), getterOfDecisionOut);
					}

					if(arrayFlg) {
						strGenDecisionDetails.append(String.format(setterOfParameter, getterOfDecisionOut)+(";")).append("\n\t\t\t");
					} else {
						strGenDecisionDetails.append(MessageFormat.format(OBJECT_DEFINITION_BLOGIC_SETTER, setterOfParameter, getterOfDecisionOut));
					}
				}
			} else {
				strGenDecisionDetails.append("decisionLogicDesignCommonService." + GenerateSourceCodeUtil.normalizedMethodName(decisionTableCode) 
						+ "(" + inputSyntax + ");");
			}
			
			currentComponent.setStrGenDecisionDetails(strGenDecisionDetails.toString());
		}

		logicStringBuilder.append(strGenDecisionDetails.toString());

		postGencode(logicStringBuilder, paramIO);
	}

	/**
	 * Get package object by type cope
	 * 
	 * @param isBlogic
	 * @param paramIO
	 * @param typeScope
	 * @param obj
	 * @return
	 */
	private String getPackageName(Boolean isBlogic, BLogicHandlerIo paramIO , int typeScope, Object obj) {

		StringBuilder pakageName = new StringBuilder(paramIO.getProject().getPackageName());
		String pakageExternal = StringUtils.EMPTY;
		String code = null;
		int dataType = -1;
		String place = ".domain.decision.";
		String moduleCode = StringUtils.EMPTY;
		
		if(BusinessDesignConst.MODULE_TYPE_BATCH.equals(paramIO.getModule().getModuleType())) {
			place = ".batch.decision.";
		}
		
		if(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(typeScope) && isBlogic && obj instanceof InputBean) {
			InputBean in = (InputBean) obj;
			dataType = in.getDataType();
			pakageExternal = in.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_INPUT_BEAN;
			moduleCode = in.getModuleCode();
		} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(typeScope) && isBlogic && obj instanceof ObjectDefinition) {
			ObjectDefinition objDef = (ObjectDefinition) obj;
			dataType = objDef.getDataType();
			pakageExternal = objDef.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_OBJ_DEFINITION;
			moduleCode = objDef.getModuleCode();
		} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(typeScope) && isBlogic && obj instanceof OutputBean) {
			OutputBean out = (OutputBean) obj;
			dataType = out.getDataType();
			pakageExternal = out.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN;
			moduleCode = out.getModuleCode();
		} else if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(typeScope) && !isBlogic && obj instanceof DecisionTableInputBean) {
			DecisionTableInputBean out = (DecisionTableInputBean) obj;
			dataType = out.getDataType();
			pakageExternal = out.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_INPUT_BEAN;
		}  else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(typeScope) && !isBlogic && obj instanceof DecisionTableOutputBean) {
			DecisionTableOutputBean out = (DecisionTableOutputBean) obj;
			dataType = out.getDataType();
			pakageExternal = out.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN;
		}

		switch (dataType) {
			case TypeOfDataType.OBJECT :
				if(isBlogic) {
					pakageName.append(place).append(paramIO.getModule().getModuleCode()).append(".").append(paramIO.getBusinessDesign().getBusinessLogicCode()).append(code).append(".");
				} else {
					pakageName.append(place).append(StringUtils.isEmpty(this.decisionInfor.getModuleCode())?StringUtils.EMPTY:this.decisionInfor.getModuleCode()+".").append(this.decisionInfor.getDecisionTbCode()+code).append(".");
				}
				
				break;
			case TypeOfDataType.ENTITY :
				if(BusinessDesignConst.MODULE_TYPE_ONLINE.equals(paramIO.getModule().getModuleType())) {
					pakageName.append(".domain.model.");
				} else {
					pakageName.append(".batch.model.");
				}
				break;
			case TypeOfDataType.COMMON_OBJECT :
				if(BusinessDesignConst.MODULE_TYPE_ONLINE.equals(paramIO.getModule().getModuleType())) {
					pakageName.append(".domain.commonobject.");
				} else {
					pakageName.append(".batch.commonobject.");
				}
				
				if(StringUtils.isNotEmpty(moduleCode)) {
					pakageName.append(moduleCode).append(".");
				}
				break;
			case TypeOfDataType.EXTERNAL_OBJECT :
				pakageName.setLength(0);
				pakageName.append(pakageExternal).append(".");
				break;
		}

		return GenerateSourceCodeUtil.normalizedPackageName(pakageName.toString());
	}
	
	private static Object getObjByTypeScope(int typeScope, List<?> lstBbj, String id) {
		String idTmp = StringUtils.EMPTY;
		
		if (CollectionUtils.isNotEmpty(lstBbj)) {
			for(Object obj : lstBbj) {
				switch (typeScope) {
				case 0:
					DecisionTableInputBean ib = (DecisionTableInputBean) obj;
					idTmp = ib.getDecisionInputBeanId();
					break;
				case 2:
					DecisionTableOutputBean ob = (DecisionTableOutputBean) obj;
					idTmp = ob.getDecisionOutputBeanId();
					break;
				}
				
				if (DataTypeUtils.equals(idTmp, id)) {
					return obj;
				}
			}
		}
		
		return new Object();
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start decision node");
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
			builder.append("// End decision node");
			builder.append(KEY_NL);
		}
	}
}
