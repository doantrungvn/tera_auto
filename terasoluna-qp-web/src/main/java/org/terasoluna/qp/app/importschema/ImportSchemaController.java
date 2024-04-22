package org.terasoluna.qp.app.importschema;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.graphicdatabasedesign.GraphicDbDesignForm;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.GraphicDatabaseDesignMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ImportSchema;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.GraphicDatabaseDesignService;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.GraphicDbDesign;
import org.terasoluna.qp.domain.service.importschema.ImportSchemaOracleService;
import org.terasoluna.qp.domain.service.importschema.ImportSchemaPostgreService;

import com.fasterxml.jackson.annotation.JsonProperty;

@Controller
@RequestMapping(value = "importschema")
@TransactionTokenCheck(value = "importschema")
public class ImportSchemaController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ImportSchemaForm.class);
	private static final String IMPORT_SCHEMA_PATH = "importschema/importSchemaForm";
	private static final String SCHEMA_DESIGN_PATH = "importschema/schemaDesignForm";
	private static final String REDIRECT_DATABASE_DESIGN_FORM = "redirect:/graphicdatabasedesign/search?init&subjectAreaId=-2";
	
	@Inject
	Mapper beanMapper;
	
	@Inject
	ImportSchemaPostgreService importSchemaPostgreService;
	
	@Inject
	ImportSchemaOracleService importSchemaOracleService;
	
	@Inject
	GraphicDatabaseDesignService graphicDbDesignService;
	
	@Inject
	ImportSchemaValidator importSchemaValidator;
	
	@Inject 
	SystemService systemService;
	
	@ModelAttribute
	public ImportSchemaForm setUpImportSchemaForm() {
		ImportSchemaForm importSchemaForm = new ImportSchemaForm();
		logger.debug("Init form {0}", importSchemaForm);
		return importSchemaForm;
	}
	
	@InitBinder("importSchemaForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(importSchemaValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ModelAttribute("reservedWords") 
	public String[] initReservedWords() { 
		return  systemService.databaseReservedWords(SessionUtils.getCurrentDatabaseType()); 
	}
	
	@TransactionTokenCheck(value = "importschema", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "importschema", method = RequestMethod.GET)
	public String displayImportShema(@RequestParam(value = "init", required = false) String init, @ModelAttribute ImportSchemaForm importSchemaForm, Model model) {
		if(init != null) {
			importSchemaForm = setUpImportSchemaForm();
			model.addAttribute("importSchemaForm", importSchemaForm);
		}
		Project project = SessionUtils.getCurrentProject();
		importSchemaForm.setProjectName(project.getProjectName());
		importSchemaForm.setProjectCode(project.getProjectCode());
		importSchemaForm.setProjectId(project.getProjectId());
		importSchemaForm.setStatus(project.getStatus());
		importSchemaForm.setRemark(project.getRemark());
		model.addAttribute("importSchemaForm", importSchemaForm);
		return IMPORT_SCHEMA_PATH;
	}
	
	@TransactionTokenCheck(value = "importschema", type = TransactionTokenType.IN)
	@RequestMapping(value = "importschema", method = RequestMethod.POST)
	public String processImportShema(@ModelAttribute @Validated ImportSchemaForm importSchemaForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		Project project = SessionUtils.getCurrentProject();
		
		if (result.hasErrors()) {
			importSchemaForm.setProjectName(project.getProjectName());
			importSchemaForm.setProjectCode(project.getProjectCode());
			importSchemaForm.setProjectId(project.getProjectId());
			importSchemaForm.setStatus(project.getStatus());
			importSchemaForm.setRemark(project.getRemark());
			model.addAttribute("importSchemaForm", importSchemaForm);
			ValidationUtils.setBindingResult(result, model);
			return IMPORT_SCHEMA_PATH;
		}
		ImportSchema importSchema = beanMapper.map(importSchemaForm, ImportSchema.class);
		importSchema.setProjectId(project.getProjectId());
		try {
			CommonModel common = this.initCommon();
			GraphicDbDesign grpDbDesign = new GraphicDbDesign();
			if(DbDomainConst.DatabaseType.PostgreSQL.equals(importSchema.getDbType())){
				grpDbDesign = importSchemaPostgreService.loadGraphicDesign(importSchema, common);
			}else if(DbDomainConst.DatabaseType.ORACLE.equals(importSchema.getDbType())){
				grpDbDesign = importSchemaOracleService.loadGraphicDesign(importSchema, common);
			}

			if (grpDbDesign == null) {
				throw new BusinessException(MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0018, MessageUtils.getMessage("sc.importschema.0002")));
			}
			
			GraphicDbDesignForm graphicDbDesignForm = beanMapper.map (grpDbDesign, GraphicDbDesignForm.class);
			graphicDbDesignForm.setProjectId(project.getProjectId());
			if(FunctionCommon.isEmpty(graphicDbDesignService.converFromXMLToObject(grpDbDesign).getListOfTables())){
				model.addAttribute("message", ResultMessages.info().add("inf.importmanagement.0002"));
			}
			model.addAttribute("graphicDbDesignForm", graphicDbDesignForm);
			model.addAttribute("formJson", DataTypeUtils.toJson(importSchemaForm));
			return SCHEMA_DESIGN_PATH;
		} catch (BusinessException e) {
			importSchemaForm.setProjectName(project.getProjectName());
			importSchemaForm.setProjectCode(project.getProjectCode());
			importSchemaForm.setProjectId(project.getProjectId());
			importSchemaForm.setStatus(project.getStatus());
			importSchemaForm.setRemark(project.getRemark());
			model.addAttribute("importSchemaForm", importSchemaForm);
			model.addAttribute("message", e.getResultMessages());
			return IMPORT_SCHEMA_PATH;
		}
	}
	
	@TransactionTokenCheck(value = "importschema", type = TransactionTokenType.IN)
	@RequestMapping(value = "importToDb", method = RequestMethod.POST,params="jsonBack")
	public String displayModifyBack(RedirectAttributes redirectAttr, Model model,@JsonProperty @RequestParam("formJson")String formJson, @JsonProperty @RequestParam("jSonString")String jSonString) {
		ImportSchemaForm importSchemaForm = null;
		importSchemaForm = DataTypeUtils.toObject(formJson, ImportSchemaForm.class);
		if(importSchemaForm == null){
			importSchemaForm = DataTypeUtils.toObject(jSonString, ImportSchemaForm.class);
		}
		Project project = SessionUtils.getCurrentProject();
		importSchemaForm.setProjectName(project.getProjectName());
		importSchemaForm.setProjectCode(project.getProjectCode());
		importSchemaForm.setProjectId(project.getProjectId());
		importSchemaForm.setStatus(project.getStatus());
		importSchemaForm.setRemark(project.getRemark());
		model.addAttribute("importSchemaForm", importSchemaForm);
		return IMPORT_SCHEMA_PATH;
	}
	
	@TransactionTokenCheck(value = "importschema", type = TransactionTokenType.IN)
	@RequestMapping(value = "importToDb", method = RequestMethod.POST)
	public String importToDb(@ModelAttribute GraphicDbDesignForm graphicDbDesignForm, Model model, RedirectAttributes redirectAttr){
		try
		{
			GraphicDbDesign graphicDbDesign = beanMapper.map(graphicDbDesignForm, GraphicDbDesign.class);
			graphicDbDesign.setProject(SessionUtils.getCurrentProject());
			if(FunctionCommon.isEmpty(graphicDbDesignService.converFromXMLToObject(graphicDbDesign).getListOfTables())){
				model.addAttribute("message", ResultMessages.error().add(CommonMessageConst.ERR_SYS_0104, MessageUtils.getMessage(GraphicDatabaseDesignMessageConst.SC_DATABASEDESIGN_0044)));
				graphicDbDesignForm.setProjectId(SessionUtils.getCurrentProjectId());
				model.addAttribute("graphicDbDesignForm", graphicDbDesignForm);
				return SCHEMA_DESIGN_PATH;
			}else{
				graphicDbDesignService.modifyTableByGraphicDesign(graphicDbDesign, false, null, null, true, 
						SessionUtils.getAccountId(), SessionUtils.getCurrentLanguageDesign(), DbDomainConst.YesNoFlg.YES);
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0011, MessageUtils.getMessage(CommonMessageConst.TQP_IMPORTSCHEMA)));
				return REDIRECT_DATABASE_DESIGN_FORM;
			}
		} catch (BusinessException ex) {
			graphicDbDesignForm.setProjectId(SessionUtils.getCurrentProjectId());
			model.addAttribute("graphicDbDesignForm", graphicDbDesignForm);
			model.addAttribute("message", ex.getResultMessages());
			return SCHEMA_DESIGN_PATH;
		}
	}
}
