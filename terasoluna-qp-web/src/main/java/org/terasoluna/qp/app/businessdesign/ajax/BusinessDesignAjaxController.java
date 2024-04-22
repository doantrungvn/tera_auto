package org.terasoluna.qp.app.businessdesign.ajax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.FunctionMaster;
import org.terasoluna.qp.domain.model.FunctionMethod;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.FunctionMethodOutput;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ScreenItemOutput;
import org.terasoluna.qp.domain.model.TableDesignDetailsOutput;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignHelper;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignService;
import org.terasoluna.qp.domain.service.commonobjectdefinition.CommonObjectDefinitionSharedService;
import org.terasoluna.qp.domain.service.externalobjectdefinition.ExternalObjectDefinitionSharedService;
import org.terasoluna.qp.domain.service.functionmaster.FunctionMasterService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignService;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignCompound;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignService;

@Controller
@RequestMapping(value="businessdesign")
public class BusinessDesignAjaxController {

	@Inject
	Mapper beanMapper;

	@Inject
	public BusinessDesignService businessDesignService;

	@Inject
	public FunctionMasterService functionMasterService;

	@Inject
	SqlDesignService sqlDesignService;

	@Inject
	CommonObjectDefinitionSharedService commonObjectDefinitionSharedService;

	@Inject
	ExternalObjectDefinitionSharedService externalObjectDefinitionSharedService;

	@Inject
	ScreenDesignService screenDesignService;

	@InitBinder
	public void init() {
//		businessDesignService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
//		businessDesignService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
//		businessDesignService.setAccountId(SessionUtils.getCurrentAccount().getAccountId());
//
//		functionMasterService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
//		functionMasterService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
//		functionMasterService.setAccountId(SessionUtils.getCurrentAccount().getAccountId());
//
//		sqlDesignService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
//		sqlDesignService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
//		sqlDesignService.setAccountId(SessionUtils.getCurrentAccount().getAccountId());
	}

	@RequestMapping(value="getColumnsOfTableDesign", method=RequestMethod.GET)
	@ResponseBody
	public List<TableDesignDetailsOutput> getColumnsOfTableDesign(@RequestParam("tableId") Long tableId) {
		List<TableDesignDetailsOutput> lstDesignDetailsOutputs = new ArrayList<TableDesignDetailsOutput>();
		lstDesignDetailsOutputs = businessDesignService.getColumnsByTableId(tableId);
		for(TableDesignDetailsOutput objDetail : lstDesignDetailsOutputs){
			//			objDetail.setColumnName(HtmlUtils.htmlEscape(objDetail.getColumnName()));
			objDetail.setColumnName(objDetail.getColumnName());
			if(objDetail.getBaseType()!= null){
				if(objDetail.getBaseType().equals(DbDomainConst.BaseType.BINARY_BASETYPE)){
					objDetail.setArrayFlg(true);
				}
				objDetail.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(objDetail.getBaseType().intValue()));
			}
		}
		return lstDesignDetailsOutputs;
	}

	/*@RequestMapping(value="getAttributesOfCommonObjectDefinition", method=RequestMethod.GET)
	@ResponseBody
	public List<CommonObjectAttribute> getAttributesOfCommonObjectDefinition(@RequestParam("commonObjectDefinitionId") Long commonObjectDefinitionId) {
		List<CommonObjectAttribute> lstCommonObjectAttributes = new ArrayList<CommonObjectAttribute>();

		Long projectId = SessionUtils.getCurrentProjectId();
		Long languageId = SessionUtils.getCurrentLanguageId();

		CommonObjectDefinition commonObjectDefinition = commonObjectDefinitionSharedService.getCommonObjectDefinition(commonObjectDefinitionId, null, projectId, languageId,null);

		for(CommonObjectAttribute objDetail : commonObjectDefinition.getCommonObjectAttributes()){
			lstCommonObjectAttributes.add(objDetail);
		}
		return lstCommonObjectAttributes;
	}*/

	/*@RequestMapping(value="getAttributesOfExternalObjectDefinition", method=RequestMethod.GET)
	@ResponseBody
	public List<ExternalObjectAttribute> getAttributesOfExternalObjectDefinition(@RequestParam("externalObjectDefinitionId") Long externalObjectDefinitionId) {
		List<ExternalObjectAttribute> lstExternalObjectAttributes = new ArrayList<ExternalObjectAttribute>();

		Long projectId = SessionUtils.getCurrentProjectId();
		Long languageId = SessionUtils.getCurrentLanguageId();

		ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionSharedService.getExternalObjectDefinition(externalObjectDefinitionId, null, projectId, languageId,1);

		for(ExternalObjectAttribute objDetail : externalObjectDefinition.getExternalObjectAttributes()){
			lstExternalObjectAttributes.add(objDetail);
		}

		return lstExternalObjectAttributes;
	}*/

	@RequestMapping(value="getScreenInformationBD", method=RequestMethod.GET)
	@ResponseBody
	public List<ScreenItemOutput> getScreenInformationBD(@RequestParam("screenId") Long screenId,@RequestParam("type") Integer type) {
	    CommonModel commonModel = initCommonModel();
		List<ScreenItemOutput> lstScreenItemOutputs = businessDesignService.getScreenItemByScreenId(screenId,type, commonModel);
		for(ScreenItemOutput obj : lstScreenItemOutputs) {
			//			obj.setFormCode(HtmlUtils.htmlEscape(obj.getFormCode()));
			//			obj.setAreaName(HtmlUtils.htmlEscape(obj.getAreaName()));
			//			obj.setAreaType(HtmlUtils.htmlEscape(BusinessDesignHelper.getAreaType(Integer.valueOf(obj.getAreaType()))));
			//			obj.setItemName(HtmlUtils.htmlEscape(obj.getItemName()));

			obj.setFormCode(obj.getFormCode());
			obj.setAreaName(obj.getAreaName());
			obj.setAreaType(BusinessDesignHelper.getAreaType(Integer.valueOf(obj.getAreaType())));
			obj.setItemName(obj.getItemName());
			obj.setLogicalDataTypeMessageString(MessageUtils.getMessage(FunctionCommon.getCodeListLabelFromValue("CL_QP_ITEMTYPE", obj.getLogicalDataType().toString())) );
		}
		return lstScreenItemOutputs;

	}


    @RequestMapping(value="getInformationOfCommonBusinessLogic", method=RequestMethod.GET)
	@ResponseBody
	public BusinessDesign getInformationOfCommonBusinessLogic(@RequestParam("businessDesignId") Long businessDesignId) {
        CommonModel commonModel = initCommonModel();
		BusinessDesign objBusinessDesign = businessDesignService.getInformationOfCommonBusinessLogic(businessDesignId, commonModel);
		return objBusinessDesign;

	}

	@RequestMapping(value="getDataDecisionComp", method=RequestMethod.GET)
	@ResponseBody
	public List<Object> getDataDecisionComp(@RequestParam("decisionTbId") Long decisionTbId) {
		List<Object> lstDecisionComp = businessDesignService.findDataDecisionComp(decisionTbId);
		return lstDecisionComp;
	}

	@RequestMapping(value="getStandardCheckFWOfBusinessLogic", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,String> getStandardCheckFWOfBusinessLogic() {
		List<String> lstMessageCode = new ArrayList<String>();
		for (Map.Entry<String, String> entry : ValidationUtils.mapAnnotation.entrySet()) {
			String value = entry.getValue();
			lstMessageCode.add(value);
		}
		CommonModel commonModel = initCommonModel();
		Map<String,String> dataReturn = businessDesignService.getStandardCheckFWOfBusinessLogic(lstMessageCode, commonModel);
		return dataReturn;
	}
	@RequestMapping(value="getFuntionOfProject", method=RequestMethod.GET)
	@ResponseBody
	public List<FunctionMaster> getFuntionOfProject() {
	    CommonModel commonModel = initCommonModel();
//		functionMasterService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
		List<FunctionMaster> lstFunctionMasters = functionMasterService.loadAllFunctionMasterByProject(commonModel);
		return lstFunctionMasters;
	}

	@RequestMapping(value = "getFunctionMastersOfProject", method = RequestMethod.GET)
	@ResponseBody
	public List<FunctionMaster> getFunctionMasterOfProject(){
//		functionMasterService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
	    CommonModel commonModel = initCommonModel();
		List<FunctionMaster> functionMasters = functionMasterService.loadAllFunctionMasterByProjectExcludeFuntionName(commonModel);
		return functionMasters;
	}

	@RequestMapping(value = "getFuntionMethodByFunctionMasterId",  method = RequestMethod.GET)
	@ResponseBody
	public List<FunctionMethod> getMethodsByFunctionMasterId(@RequestParam("functionMasterId") Long functionMasterId){
		return functionMasterService.findFuntionMethodByFunctionMasterId(functionMasterId);
	}

	@RequestMapping(value="findFunctionMethodInputByFunctionMethodId",method = RequestMethod.GET)
	@ResponseBody
	public List<FunctionMethodInput> findFunctionMethodInputByFunctionMethodId(@RequestParam("functionMethodId") Long functionMethodId) {
		List<FunctionMethodInput> methodInputs = this.functionMasterService.findFunctionMethodInputByFunctionMethodId(functionMethodId);
		return methodInputs;
	}

	@RequestMapping(value="findFunctionMethodOutputByFunctionMethodId",method = RequestMethod.GET)
	@ResponseBody
	public List<FunctionMethodOutput> findFunctionMethodOutputByFunctionMethodId(@RequestParam("functionMethodId") Long functionMethodId) {
		List<FunctionMethodOutput> methodOutputs = this.functionMasterService.findFunctionMethodOutputByFunctionMethodId(functionMethodId);
		return methodOutputs;
	}

	@RequestMapping(value="getSQLDesignById", method=RequestMethod.GET)
	@ResponseBody
	public SqlDesignCompound getSQLDesignById(@RequestParam("sqlDesignId") Long sqlDesignId) {
		SqlDesignCompound sqlDesignCompound = new SqlDesignCompound();
		try{
			sqlDesignCompound = sqlDesignService.findCompoundById(sqlDesignId);
		}catch(BusinessException be){
		}
		return sqlDesignCompound;
	}
	@RequestMapping(value="getInputBeanOfBusinessLogic", method=RequestMethod.GET)
	@ResponseBody
	public BusinessDesign getInputBeanOfBusinessLogic(@RequestParam("businessDesignId") Long businessDesignId) {
	    CommonModel commonModel = initCommonModel();
		BusinessDesign businessLogic = businessDesignService.findInputBeanOfBusinessLogic(businessDesignId, commonModel);
		return businessLogic;

	}

	@RequestMapping(value="getInputBeanFromScreenId", method=RequestMethod.GET)
	@ResponseBody
	public List<InputBean> getInputBeanFromScreenId(@RequestParam("screenId") Long screenId, @RequestParam("requestMethod") Integer requestMethod, @RequestParam("screenFormId") Long screenFormId, @RequestParam("projectId") Long projectId, @RequestParam("languageId") Long languageId) {
		List<InputBean> lstInputBeans = screenDesignService.buildInputBeanFromScreenId(screenId, requestMethod, screenFormId,projectId,languageId);
		return lstInputBeans;
	}
	
	@RequestMapping(value="getOutputBeanFromScreenId", method=RequestMethod.GET)
	@ResponseBody
	public List<OutputBean> getOutputBeanFromScreenId(@RequestParam("screenId") Long screenId, @RequestParam("projectId") Long projectId, @RequestParam("languageId") Long languageId) {
		List<OutputBean> lstOutputBeans = screenDesignService.buildOutputBeanFromScreenId(screenId, projectId, languageId);
		return lstOutputBeans;
	}
	
	/**
	 * Init common Model
	 * @return
	 */
	private CommonModel initCommonModel() {
        CommonModel commonModel = new CommonModel();
        commonModel.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
        commonModel.setWorkingProjectId(SessionUtils.getCurrentProjectId());

        Long accountId = SessionUtils.getAccountId();
        commonModel.setCreatedBy(accountId);
        commonModel.setUpdatedBy(accountId);
        return commonModel;
    }
}
