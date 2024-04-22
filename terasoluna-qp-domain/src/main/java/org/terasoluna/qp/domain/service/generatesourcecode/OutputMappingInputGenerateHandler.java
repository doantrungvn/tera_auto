package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenItemOutput;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.generatesourcecode.GenerateSourceCodeRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.screenitem.ScreenItemRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Component("OutputMappingInputGenerateHandler")
public class OutputMappingInputGenerateHandler extends SequenceLogicGenerationHandler {

	@Inject
	DetailServiceImpHandler detailServiceImpHandler;
	
	@Inject
	BusinessLogicGenerateHandler businessLogicGenerateHandler;
	
	@Inject
	ScreenItemRepository screenItemRepository;
	
	@Inject
	GenerateSourceCodeRepository generateSourceCodeRepository;
	
	@Inject
	BusinessDesignRepository businessDesignRepository;
	
	@Inject
	ScreenDesignRepository screenDesignRepository;
	
	private static final String NL_TAB = "\n\t\t\t";
	private static final String INIT_OBJECT = "{0} {1} = new {0}();";
	private static final boolean IS_GETTER = true;
	private static final boolean IS_SETTER = false;
	private static final String INPUT_SETTER = "{0}({1});";
	private Project project;
	private Module moduleCurr;
	private BusinessDesign blogicCurr;
	private Module moduleRef;
	private BusinessDesign blogicRef;
	private String blogicCodeForGet;
	private String blogicCodeForSet;
	private Map<String, OutputBean> mScreenOutput;
	private List<ScreenItemOutput> lstScreenItemMapping;
	private StringBuilder strBuilder;
	private Map<String, InputBean> mInputbean;
	private List<?> lstOutputBean;

	@Override
	public void handle(StringBuilder strBdlr, BLogicHandlerIo param) {

		if(param != null && param.getBusinessDesign().getScreenId() != null) {
			
			List<BusinessDesign> lstScrAcToBlogic = generateSourceCodeRepository.getAllScreenActionIsPostByScreenId(param.getBusinessDesign().getScreenId());
			List<InputBean> lstAllInput = generateSourceCodeRepository.findAllInputBeanByBusinessIdLst(lstScrAcToBlogic);
			
			StringBuilder sbd = null;
			BusinessDesign bdTmp = null;
			
			// Build setting model from undo to redirect
			if(CollectionUtils.isNotEmpty(param.getModule().getListBusinessDesign()) && param.getBusinessDesign().getPatternType().equals(6) 
					&& param.getBusinessDesign().getReturnType().equals(1) && StringUtils.isEmpty(param.getBusinessDesign().getScreenCode())) {
				bdTmp = getBussinessUndoByPatern(param.getModule().getListBusinessDesign(), param.getBusinessDesign().getPatternType());
			} else if(CollectionUtils.isNotEmpty(param.getModule().getListBusinessDesign()) && param.getBusinessDesign().getPatternType().equals(7) && param.getBusinessDesign().getReturnType().equals(1) && StringUtils.isEmpty(param.getBusinessDesign().getScreenCode())) {
				bdTmp = getBussinessUndoByPatern(param.getModule().getListBusinessDesign(), param.getBusinessDesign().getPatternType());
			}
			
			if(bdTmp != null) {
				sbd = new StringBuilder(StringUtils.EMPTY);
				sbd.append(MessageFormat.format("{0}InputForm {1}InputForm  = beanMapper.map({2} , {0}InputForm.class);", GenerateSourceCodeUtil.normalizedClassName(bdTmp.getBusinessLogicCode()), StringUtils.uncapitalize(bdTmp.getBusinessLogicCode()), StringUtils.uncapitalize(param.getBusinessDesign().getBusinessLogicCode())+"InputBean"));
				sbd.append("\n\t\t");
				sbd.append(MessageFormat.format("model.addAttribute(\"{0}InputForm\", {0}InputForm);", StringUtils.uncapitalize(bdTmp.getBusinessLogicCode())));
				sbd.append("\n\t\t");
				param.getBusinessDesign().setStrUndoModelSetting(sbd.toString());
			}
			
			if(CollectionUtils.isNotEmpty(lstScrAcToBlogic) && CollectionUtils.isNotEmpty(lstAllInput) && !(DbDomainConst.ScreenPatternType.SEARCH.equals(param.getBusinessDesign().getPatternType()) && BusinessDesignConst.RETURN_TYPE_SCREEN.equals(param.getBusinessDesign().getReturnType()))) {

				for (BusinessDesign businessDesign : lstScrAcToBlogic) {
					List<InputBean> listInputBean = new ArrayList<InputBean>();
					for (InputBean inputBean : lstAllInput) {
						if(businessDesign.getBusinessLogicId().equals(inputBean.getBusinessLogicId())){
							listInputBean.add(inputBean);
						}
					}
					
					//List<ScreenItemOutput> lstScreenItemMapping = businessDesignRepository.findAllScreenItemMappingByOutputBeanId(param.getBusinessDesign().getBusinessLogicId());
					List<ScreenItemOutput> lstScreenItemMapping = businessDesignRepository.findAllScreenItemMappingByOutputBeanBlogic(param.getBusinessDesign().getBusinessLogicId());
					
					if (CollectionUtils.isNotEmpty(listInputBean)) {

						Map<String, OutputBean> mScreenOutput = new HashMap<String, OutputBean>();
						if(CollectionUtils.isNotEmpty(param.getBusinessDesign().getLstNonDataSourceOutputBean())) {
							for (OutputBean ouBean : param.getBusinessDesign().getLstNonDataSourceOutputBean()) mScreenOutput.put(ouBean.getOutputBeanId(), ouBean);
						}

						Map<String, InputBean> mInputBean = new HashMap<String, InputBean>();
						for (InputBean in : listInputBean) mInputBean.put(in.getInputBeanId(), in);

						this.initial(param, lstScreenItemMapping, mScreenOutput, strBdlr, param.getBusinessDesign().getLstNonDataSourceOutputBean(), businessDesign, mInputBean);

						Map<String, List<?>> mAllBlogicRefer = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(GenerateSourceCodeConst.BUSINESS_LOGIC, listInputBean, null, null);

						strBuilder.append("\n\t\t");
						strBuilder.append("\n\t\t").append("// Start mapping output to input");
						strBuilder.append("\n\t\t");

						strBuilder.append(MessageFormat.format(INIT_OBJECT, GenerateSourceCodeUtil.normalizedClassName(blogicRef.getBusinessLogicCode())+"InputBean", StringUtils.uncapitalize(blogicRef.getBusinessLogicCode())+"InputBean")).append("\n\t\t");
						
						
						List<InputBean> listInputTopLevel = getInputBeanTopLevel(listInputBean);
						Map<Integer, String> mParentSyntax = new HashMap<Integer, String>();
						mParentSyntax.put(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, blogicCodeForGet);
						mParentSyntax.put(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, blogicCodeForSet);

						// Build output mapping to input for item at level top is not object				
						buidlMappingPropertiesOfObj(listInputTopLevel, mAllBlogicRefer, param.getmAllParentAndSeflByLevelOfInOutObj(), mParentSyntax);

						// Build output mapping to input for item remain
						// Object and properties
						List<InputBean> listInputIsObj = getObjectListInputBeanMapping(listInputBean);
						buidlMappingObjOfInput(listInputIsObj, mAllBlogicRefer, param.getmAllParentAndSeflByLevelOfInOutObj(), mParentSyntax);
						strBuilder.append("\n\t\t");
						strBuilder.append(MessageFormat.format("{0}InputForm {1}InputForm  = beanMapper.map({2} , {0}InputForm.class);", GenerateSourceCodeUtil.normalizedClassName(blogicRef.getBusinessLogicCode()), StringUtils.uncapitalize(blogicRef.getBusinessLogicCode()), StringUtils.uncapitalize(blogicRef.getBusinessLogicCode())+"InputBean"));
						strBuilder.append("\n\t\t");
						strBuilder.append(MessageFormat.format("model.addAttribute(\"{0}InputForm\", {0}InputForm);", StringUtils.uncapitalize(blogicRef.getBusinessLogicCode())));
						strBuilder.append("\n\t\t").append("// End mapping output to input");
						// Setting for business
						if(StringUtils.isNotEmpty(strBuilder.toString())){
							param.getBusinessDesign().setStrOutMappingInContent(param.getBusinessDesign().getStrOutMappingInContent() + strBuilder.toString());
						}

						strBuilder.setLength(0);
					}
				}
			}
		}
	}

	private void buidlMappingObjOfInput(List<InputBean> listInputIsObj, Map<String, List<?>> mAllBlogicRefer,
			Map<String, List<?>> mAllBlogicCurr, Map<Integer, String> mParentSyntax) {

		if (CollectionUtils.isNotEmpty(listInputIsObj)) {

			for (InputBean inbeanRefer : listInputIsObj) {
				
				String getterParentInputList = "";
				String parentInputForSet = "";
				String parentOutputForGet = "";
				// Start is false
				Boolean isParentArray = false;
				String instanceNmObj = "";
				
				// Get output current for getter
				OutputBean outbeanCurrent = null;
				OutputBean tmp = null;
				
				if(DbDomainConst.LogicDataType.FILEUPLOAD.toString().equals(inbeanRefer.getLogicalDataType())) continue;
				
				if (CollectionUtils.isNotEmpty(inbeanRefer.getSingleList())) {
					tmp = getOutputBean(inbeanRefer.getSingleList().get(0));
				} else if (CollectionUtils.isNotEmpty(inbeanRefer.getObjectList())) {
					tmp = getOutputBean(inbeanRefer.getObjectList().get(0).getSingleList().get(0));
				}

				if(tmp != null) {
					outbeanCurrent = mScreenOutput.getOrDefault(tmp.getParentOutputBeanId(), null);
				}
				
				if(Boolean.FALSE.equals(inbeanRefer.getArrayFlg())) {
					
					parentInputForSet = StringUtils.uncapitalize(mParentSyntax.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID));
					
					String getterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, mParentSyntax.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID), null);
					// Get name of instance object
					instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inbeanRefer);
					// Get new instance of object data type
					String instanceOf = "new " + getPackageName(moduleRef, blogicRef, inbeanRefer) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()";
					strBuilder.append(String.format("if (%s == null){", StringUtils.uncapitalize(getterInReferParent))).append(NL_TAB);

					String setterInReferParent = StringUtils.uncapitalize(detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, mParentSyntax.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID), null)+"("+instanceOf+");");

					strBuilder.append(setterInReferParent).append("\n\t\t}").append("\n\t\t");
				} else {
					strBuilder.append("\n\t");
					// Marking value
					isParentArray = true;
					// Marking parent syntax for set
					parentInputForSet = "temp"+ GenerateSourceCodeUtil.normalizedClassName(blogicRef.getBusinessLogicCode());
					// Get name of instance object
					instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inbeanRefer);
					// Get new instance of object data type
					String instanceOf = "new java.util.ArrayList<" + getPackageName(moduleRef, blogicRef, inbeanRefer) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + ">()";
					String setterInReferParent = StringUtils.uncapitalize(detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), 0, mParentSyntax.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID), null)+"("+instanceOf+");");

					// Using for add one item into the list object
					getterParentInputList = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), 0, mParentSyntax.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID), null);
					
					strBuilder.append(String.format("if (%s == null){", StringUtils.uncapitalize(getterParentInputList))).append(NL_TAB);
					
					strBuilder.append(setterInReferParent).append("\n\t\t").append("}").append("\n\t\t");
					
					if(outbeanCurrent != null){
						// Build for each from list - list
						String getterParamOfParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurr, IS_GETTER, outbeanCurrent.getOutputBeanId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, mParentSyntax.get(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID), null);
						// Marking parent syntax for get
						parentOutputForGet =  detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, outbeanCurrent);
						// initial for new instant object from parameter is list object
						String declareObj = getPackageName(moduleCurr, blogicCurr, outbeanCurrent) + GenerateSourceCodeUtil.normalizedClassName(parentOutputForGet);
						
						String setterParentInputList = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), 0, mParentSyntax.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID), null);
						strBuilder.append("\n\t\t").append(String.format("%s(new java.util.ArrayList<%s>()) ;", setterParentInputList, getPackageName(moduleRef, blogicRef, inbeanRefer) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj))).append(NL_TAB);
						
						strBuilder.append("\n\t\t").append(String.format("for (%s "+ StringUtils.uncapitalize(parentOutputForGet)+" : %s) {", declareObj, StringUtils.uncapitalize(getterParamOfParent))).append("\n\t\t\t");
						// Build new temporary object from input refer
						strBuilder.append(MessageFormat.format(INIT_OBJECT, getPackageName(moduleRef, blogicRef, inbeanRefer) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj), StringUtils.uncapitalize(parentInputForSet))).append("\n\t\t\t");
						// In the case item is end of list at here
						strBuilder.append(getterParentInputList + ".add("+parentInputForSet+");").append("\n\t\t");
					} else if(outbeanCurrent == null && isExistDefaultValue(inbeanRefer.getSingleList())){
						parentOutputForGet = StringUtils.EMPTY;
						// Build new temporary object from input refer
						strBuilder.append(MessageFormat.format(INIT_OBJECT, getPackageName(moduleRef, blogicRef, inbeanRefer) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj), StringUtils.uncapitalize(parentInputForSet))).append("\n\t\t\t");
						// In the case item is end of list at here
						strBuilder.append(getterParentInputList + ".add("+parentInputForSet+");").append("\n\t\t");
					} else {
						continue;
					}
				}

				Map<String, List<?>> mAllBlogicCurrPre = null;
				
				Map<Integer, String> mParentSyntaxPre = mParentSyntax;

				if(StringUtils.isNoneEmpty(parentOutputForGet)) {
					mParentSyntaxPre.put(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, parentOutputForGet);
					List<?> lstObj = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, outbeanCurrent.getOutputBeanId(), lstOutputBean);
					mAllBlogicCurrPre = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, null, null, lstObj);
				} else {
					mParentSyntaxPre.put(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, mParentSyntax.get(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID));
					mAllBlogicCurrPre = mAllBlogicCurr;
				}

				mParentSyntaxPre.put(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, parentInputForSet);
				
				if(CollectionUtils.isNotEmpty(inbeanRefer.getObjectList())){
					List<?> listObjInputOfParent = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inbeanRefer.getInputBeanId(), inbeanRefer.getObjectList());
					Map<String, List<?>> mAllBlogicReferPre = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, listObjInputOfParent, null, null);
					buidlMappingObjOfInput(inbeanRefer.getObjectList(), mAllBlogicReferPre, mAllBlogicCurrPre, mParentSyntaxPre);
				}
				
				// Build output mapping to input for item at level top is not object
				if(isParentArray) {
					List<?> lstObj = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inbeanRefer.getInputBeanId(), inbeanRefer.getSingleList());
					Map<String, List<?>> mAllBlogicReferPre = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, lstObj, null, null);
					buidlMappingPropertiesOfObj(inbeanRefer.getSingleList(), mAllBlogicReferPre, mAllBlogicCurrPre, mParentSyntaxPre);
					if(outbeanCurrent != null) strBuilder.append(String.format("\n\t\t}"));
				} else {
					buidlMappingPropertiesOfObj(inbeanRefer.getSingleList(), mAllBlogicRefer, mAllBlogicCurrPre, mParentSyntaxPre);
				}
			}
		}
	}

	private void buidlMappingPropertiesOfObj(List<InputBean> listInputTopLevel, Map<String, List<?>> mParentAndSeflIn, Map<String, List<?>> mParentAndSeflOut, Map<Integer, String> mParentSyntax) {
		if(CollectionUtils.isNotEmpty(listInputTopLevel)) {
			String getter = "";
			for (InputBean inputBean : listInputTopLevel) {
				OutputBean ou = getOutputBean(inputBean);
				if(ou!= null) {
					getter = detailServiceImpHandler.getterAndSetterOfParameter(0, mParentAndSeflOut, IS_GETTER, ou.getOutputBeanId(), 2, mParentSyntax.get(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID) , null);
					// Cast data type if happen
					getter = BusinessLogicGenerateHelper.getContentByCastDataType(inputBean.getDataType(), ou.getDataType(), getter);
					
					if(DbDomainConst.LogicDataType.CHECKBOX.toString().equals(inputBean.getLogicalDataType()) && BusinessDesignConst.DataType.STRING.equals(inputBean.getDataType()) && Boolean.TRUE.equals(inputBean.getArrayFlg())) {
						getter = String.format("java.util.Arrays.asList(FunctionMasterUtils.QpString.toArray(%s, \";\"))", getter);
					} 
					// setter of child
					String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(0, mParentAndSeflIn, IS_SETTER, inputBean.getInputBeanId(), 0, mParentSyntax.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID), null);
					strBuilder.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append("\n\t\t");
				} else if(StringUtils.isNotEmpty(inputBean.getDefaultValue()) && !StringUtils.trim(inputBean.getDefaultValue()).equals("now()")) {
					// Don't any output mapping for input and exist default value
					if(GenerateSourceCodeConst.SYSTEM_DATETIME_TYPE.equals(StringUtils.trim(inputBean.getDefaultValue()))) {
						getter = buildValueForSystemDateTime(inputBean.getDataType());
					} else {
						getter = getConvertByDataType(inputBean.getDataType(), inputBean.getDefaultValue());
					} 

					if(DbDomainConst.LogicDataType.CHECKBOX.toString().equals(inputBean.getLogicalDataType()) && BusinessDesignConst.DataType.STRING.equals(inputBean.getDataType()) && Boolean.TRUE.equals(inputBean.getArrayFlg())) {
						getter = String.format("java.util.Arrays.asList(FunctionMasterUtils.QpString.toArray(%s, \";\"))", getter);
					}
					String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(0, mParentAndSeflIn, IS_SETTER, inputBean.getInputBeanId(), 0, mParentSyntax.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID), null);
					strBuilder.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append("\n\t\t");
				}
			}
		}
	}

	private String buildValueForSystemDateTime(int dataType) {
		String result = StringUtils.EMPTY;
		
		switch (dataType) {
			case GenerateSourceCodeConst.DataType.TIME:
				result = "new java.sql.Time(System.currentTimeMillis())";
				break;
			case GenerateSourceCodeConst.DataType.DATE:
				result = "new java.sql.Date(System.currentTimeMillis())";
				break;
			case GenerateSourceCodeConst.DataType.TIMESTAMP:
			case GenerateSourceCodeConst.DataType.DATETIME: 
				result = "new java.sql.Timestamp(System.currentTimeMillis())";
				break;
		}

		return result;
	}
	
	private boolean isExistDefaultValue(List<InputBean> lstInput) {
		boolean isExist = false;
		
		if(CollectionUtils.isNotEmpty(lstInput)) {
			for (InputBean inputBean : lstInput) {
				if(StringUtils.isNotEmpty(inputBean.getDefaultValue())) {
					isExist = true;
					break;
				}
			}
		}
		
		return isExist;
	}
	
	private String getConvertByDataType(int dataType, String value) {
		
		StringBuilder valueProcess = new StringBuilder(StringUtils.EMPTY);
		
		switch (dataType) {
		
		case GenerateSourceCodeConst.DataType.BYTE :
			valueProcess.append("(byte)").append(value);
			break;
		case GenerateSourceCodeConst.DataType.SHORT :
			valueProcess.append("(short)").append(value);
			break;
			
		case GenerateSourceCodeConst.DataType.INTEGER :
			valueProcess.append("(int)").append(value);
			break;
			
		case GenerateSourceCodeConst.DataType.LONG :
			valueProcess.append("(long)").append(value);
			break;
			
		case GenerateSourceCodeConst.DataType.FLOAT :
			valueProcess.append("(float)").append(value);
			break;
			
		case GenerateSourceCodeConst.DataType.DOUBLE :
			valueProcess.append("(double)").append(value);
			break;
			
		case GenerateSourceCodeConst.DataType.CHARACTER :
			valueProcess.append("\'").append(value).append("\'");
			break;
			
		case GenerateSourceCodeConst.DataType.BOOLEAN :
			valueProcess.append(value);
			break;
			
		case GenerateSourceCodeConst.DataType.STRING :
			valueProcess.append("\"").append(value).append("\"");
			break;
			
		case GenerateSourceCodeConst.DataType.BIGDECIMAL :
			valueProcess.append("new java.math.BigDecimal(\""+value+"\")");
			break;

		case GenerateSourceCodeConst.DataType.TIMESTAMP :
		case GenerateSourceCodeConst.DataType.DATETIME :
			valueProcess.append("(java.sql.Timestamp) dtConvert.convert(null, \""+value+"\", java.sql.Timestamp.class, String.class)");
			break;
			
		case GenerateSourceCodeConst.DataType.TIME :
			valueProcess.append("(java.sql.Time) dtConvert.convert(null, \""+value+"\", java.sql.Time.class, String.class)");
			break;
			
		case GenerateSourceCodeConst.DataType.DATE :
			valueProcess.append("(java.sql.Date) dtConvert.convert(null, \""+value+"\", java.sql.Date.class, String.class)");
			break;

		default:
			break;
		}
		
		return valueProcess.toString();
	}
	
	private OutputBean getOutputBean(InputBean inputBean) {
		OutputBean ou = null;
		if(CollectionUtils.isNotEmpty(lstScreenItemMapping)) {
			for (ScreenItemOutput screenItemOutput : lstScreenItemMapping) {
				if(DataTypeUtils.equals(screenItemOutput.getScreenItemId(), inputBean.getScreenItemId())) {
					ou = mScreenOutput.getOrDefault(screenItemOutput.getOutputBeanId().toString(), null);
					break;
				}
			}
		}
		
		return ou;
	}
	
	private String getPackageName(Module module, BusinessDesign blogic, Object obj) {
		StringBuilder pakageName = new StringBuilder(project.getPackageName());
		int dataType = -1;
		String code = null;
		String pakageExternal = StringUtils.EMPTY;

		if (obj instanceof InputBean) {
			InputBean in = (InputBean) obj;
			dataType = in.getDataType();
			code = "InputBean";
			pakageExternal = in.getPackageNameObjExt();
		} else if (obj instanceof OutputBean) {
			OutputBean ou = (OutputBean) obj;
			dataType = ou.getDataType();
			code = "OutputBean";
			pakageExternal = ou.getPackageNameObjExt();
		}

		switch (dataType) {

			case GenerateSourceCodeConst.DataType.OBJECT:
				pakageName.append(".domain").append(".service").append(".").append(StringUtils.uncapitalize(module.getModuleCode())).append(".").append(StringUtils.uncapitalize(blogic.getBusinessLogicCode())).append(code).append(".");
				break;
			case GenerateSourceCodeConst.DataType.ENTITY:
				pakageName.append(".domain").append(".model").append(".");
				break;
				
			case GenerateSourceCodeConst.DataType.COMMON_OBJECT :
				pakageName.append(".domain.commonobject.");
				break;
			case GenerateSourceCodeConst.DataType.EXTERNAL_OBJECT :
				pakageName.append(".").append(pakageExternal).append(".");
				break;
		}

		return GenerateSourceCodeUtil.normalizedPackageName(pakageName.toString());
	}

	public List<InputBean> getObjectListInputBeanMapping(List<InputBean> inputBeanList) {

		boolean isNotExistMapping = false;
		List<InputBean> listInputObject = new ArrayList<InputBean>();
		for (InputBean inputBean : inputBeanList) {
			if (inputBean.getParentInputBeanId() == null 
					&& ( BusinessDesignConst.DataType.OBJECT.equals(inputBean.getDataType())
					|| BusinessDesignConst.DataType.ENTITY.equals(inputBean.getDataType())
					|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(inputBean.getDataType())
					|| BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(inputBean.getDataType()))
					&& !DbDomainConst.LogicDataType.FILEUPLOAD.toString().equals(inputBean.getLogicalDataType())) {
				settingFieldObjectOutputBean(inputBean, inputBeanList, isNotExistMapping);
				listInputObject.add(inputBean);
			}
		}

		return listInputObject;
	}

	public void settingFieldObjectOutputBean(InputBean item, List<InputBean> inputBeanList, boolean isNotExistMapping) {
		
		List<InputBean> listInputSingle = new ArrayList<InputBean>();
		List<InputBean> listInputObject = new ArrayList<InputBean>();
		
		for (InputBean inputBean : inputBeanList) {
			if (item.getInputBeanId().equals(inputBean.getParentInputBeanId())) {			//	OutputBean ou = getOutputBean(inputBean);
				
				if((BusinessDesignConst.DataType.OBJECT.equals(inputBean.getDataType())
						|| BusinessDesignConst.DataType.ENTITY.equals(inputBean.getDataType())
						|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(inputBean.getDataType())
						|| BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(inputBean.getDataType()))
						&& !DbDomainConst.LogicDataType.FILEUPLOAD.equals(inputBean.getLogicalDataType())) {

					settingFieldObjectOutputBean(inputBean, inputBeanList, isNotExistMapping);
					
					// If object not exist one at least properties mapping with output bean then not set.
					if(CollectionUtils.isNotEmpty(inputBean.getSingleList())) listInputObject.add(inputBean);
				} else {
					// Check item had setting mapping
					listInputSingle.add(inputBean);
				}
			}
		}

		item.setSingleList(listInputSingle);
		item.setObjectList(listInputObject);
	}
	
	public static List<InputBean> getInputBeanTopLevel(List<InputBean> inputBeanList) {
		List<InputBean> listInputSingle = new ArrayList<InputBean>();
		for (InputBean inputBean : inputBeanList) {
			if (inputBean.getParentInputBeanId() == null 
					&& !BusinessDesignConst.DataType.OBJECT.equals(inputBean.getDataType())
					&& !BusinessDesignConst.DataType.ENTITY.equals(inputBean.getDataType())
					&& !BusinessDesignConst.DataType.COMMON_OBJECT.equals(inputBean.getDataType())
					&& !BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(inputBean.getDataType())) {
				listInputSingle.add(inputBean);    }
		}

		return listInputSingle;
	}

	private void initial(BLogicHandlerIo param, List<ScreenItemOutput> lstScreenItemMapping, Map<String, OutputBean> mScreenOutput, StringBuilder strBuilder, List<OutputBean> lstOutputBean, BusinessDesign businessRef, Map<String, InputBean> mInputBean) {
		project = param.getProject();
		moduleCurr = param.getModule();
		blogicCurr = param.getBusinessDesign();
		blogicCodeForGet = blogicCurr.getBusinessLogicCode() + "OutputBean";
		moduleRef = new Module();
		moduleRef.setModuleCode((param.getScreenDesign() != null)? param.getScreenDesign().getModuleCode():StringUtils.EMPTY);
		blogicRef = businessRef;
		blogicCodeForSet = blogicRef.getBusinessLogicCode() + "InputBean";
		this.lstScreenItemMapping = lstScreenItemMapping;
		this.mScreenOutput = mScreenOutput;
		this.strBuilder = strBuilder;
		this.lstOutputBean = lstOutputBean;
		this.mInputbean = mInputBean;
	}

	public BusinessDesign getBussinessUndoByPatern(List<BusinessDesign> lstBlogic, Integer pattern){
		BusinessDesign blogicResut  = null;
		
		if(CollectionUtils.isNotEmpty(lstBlogic)) {
			for (BusinessDesign bd : lstBlogic) {
				if(pattern.equals(6) && bd.getPatternType().equals(10)) {
					blogicResut = bd;
					break;
				} else if(pattern.equals(7) && bd.getPatternType().equals(11)) {
					blogicResut = bd;
					break;
				}
			}
		}
		
		return blogicResut;
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
	}

	@Override
	public void postGencode(StringBuilder builder, BLogicHandlerIo param) {
	}

}