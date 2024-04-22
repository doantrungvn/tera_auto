package org.terasoluna.qp.app.graphicdatabasedesign;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.SubjectArea;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeUtil;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.GraphicDatabaseDesignService;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.GraphicDbDesign;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.subjectarea.SubjectAreaService;

import com.fasterxml.jackson.annotation.JsonProperty;

@Controller
@RequestMapping(value = "graphicdatabasedesign")
@TransactionTokenCheck(value = "graphicdatabasedesign")
/*@SessionAttributes(types = { GraphicDbDesignForm.class })*/
public class GraphicDatabaseDesignController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(GraphicDatabaseDesignController.class);
	private static final String DATABASE_DESIGN_FORM = "graphicdatabasedesign/databaseDesignForm";
	private static final String VIEW_BLOGIC_LINK = "graphicdatabasedesign/viewListAffectedChangeDesignForm";
	
	//private static final String REDIRECT_DATABASE_DESIGN_FORM = "redirect:/graphicdatabasedesign/search?subjectAreaId=";
	
	@Inject
	SubjectAreaService areaService;
	
	@Inject
	ProjectService projectService;
	
	@Inject
	GraphicDatabaseDesignService graphicDbDesignService;
	
	@Inject
	Mapper beanMapper;

	@Inject 
	SystemService systemService;
	
	@ModelAttribute
	public GraphicDbDesignForm setGraphicDbDesignForm() {
		GraphicDbDesignForm graphicDbDesignForm = new GraphicDbDesignForm();
		logger.debug("area form {}", graphicDbDesignForm);
		return graphicDbDesignForm;
	}
	
	@ModelAttribute("reservedWords") 
	public String[] initReservedWords() { 
		moduleCode = CommonMessageConst.TQP_DATABASEDESIGN;
		return  systemService.databaseReservedWords(SessionUtils.getCurrentDatabaseType()); 
	}
	
	@RequestMapping(value = "search",  method = RequestMethod.GET)
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute GraphicDbDesignForm graphicDbDesignForm, Model model, SessionStatus sessionStatus){

		Long projectId = SessionUtils.getCurrentProjectId();
		Long subjectAreaId = graphicDbDesignForm.getSubjectAreaId();
		if (init != null) {
			sessionStatus.setComplete();
			graphicDbDesignForm = setGraphicDbDesignForm();
			/*graphicDbDesignForm.setSubjectAreaId(subjectAreaId);*/
			subjectAreaId = null;
			model.addAttribute("graphicDbDesignForm", graphicDbDesignForm);
			super.setOldProjectId(projectId);
		}
		
		if (subjectAreaId != null) {
			graphicDbDesignForm.setSubjectAreaId(subjectAreaId);
		}
		
		checkChangeProject(false);

		//get subject area
		return loadGraphicDesign(graphicDbDesignForm, model, projectId);
	}

	/**
	 * @param graphicDbDesignForm
	 * @param model
	 * @param projectId
	 * @return
	 * @throws MappingException
	 */
	private String loadGraphicDesign(GraphicDbDesignForm graphicDbDesignForm, Model model, Long projectId) throws MappingException {
		List<SubjectArea> listOfArea = areaService.getAllByProjectId(projectId);
		
		int numOfAreas = DomainDatatypeUtil.isEmpty(listOfArea) ? 0 : listOfArea.size();
		if (DomainDatatypeUtil.isEmpty(graphicDbDesignForm.getSubjectAreaId())) {
			//find other
			graphicDbDesignForm.setSubjectAreaId (DomainDatatypeConst.SEARCH_TABLE_DESIGN_NOT_IN_SUBJECT_AREA);
			//if has subject area default then find by it
			for (int i = 0; i < numOfAreas; i++)
			{
				SubjectArea subjectArea = listOfArea.get(i);
				if (numOfAreas == 1 || subjectArea.getDefaultFlg() == 1)
				{
					graphicDbDesignForm.setSubjectAreaId(subjectArea.getAreaId());
					break;
				}
			}
		} 
		// get data
		GraphicDbDesign grpDbDesign = graphicDbDesignService.loadGraphicDesign(projectId,graphicDbDesignForm.getSubjectAreaId());
		//keep value
		grpDbDesign.setProjectIdAutocomplete(graphicDbDesignForm.getProjectIdAutocomplete());
		graphicDbDesignForm = beanMapper.map (grpDbDesign, GraphicDbDesignForm.class);
		graphicDbDesignForm.setProjectId(projectId);
		model.addAttribute("areas", listOfArea);
		model.addAttribute("graphicDbDesignForm", graphicDbDesignForm);
		return DATABASE_DESIGN_FORM;
	}

	@RequestMapping(value = "modify", method = { RequestMethod.POST })
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	public String processModify(@Validated @ModelAttribute GraphicDbDesignForm graphicDbDesignForm, BindingResult result, Model model, RedirectAttributes redirectAttr) {
		//System.out.println(graphicDbDesignForm.getXml());
		
		Long projectId = graphicDbDesignForm.getProjectId();
		try {
			checkChangeProject(true);
			//Long projectId = SessionUtils.getCurrentProjectId();

			Project project = projectService.getProjectInformation(projectId, SessionUtils.getAccountId(), true);

			//graphicDbDesignForm.setProjectId(projectId);

			Boolean showImpactFlag =  graphicDbDesignForm.getShowImpactFlag();

			GraphicDbDesign graphicDbDesign = beanMapper.map(graphicDbDesignForm, GraphicDbDesign.class);
			/*System.out.println(graphicDbDesignForm.getXml());*/
			graphicDbDesign.setProject(project);

			graphicDbDesignService.loadAffectChangedDesign(graphicDbDesign, projectId);

			if (this.hasProblem(graphicDbDesign)) {
				if (showImpactFlag) {
					graphicDbDesignForm = beanMapper.map(graphicDbDesign, GraphicDbDesignForm.class);
					graphicDbDesignForm.setShowImpactFlag(showImpactFlag);
					model.addAttribute("formJson", DataTypeUtils.toJson(graphicDbDesignForm));
					model.addAttribute("graphicDbDesignForm", graphicDbDesignForm);
					return VIEW_BLOGIC_LINK; 
				} else {
					return modifyGraphicDesign(graphicDbDesign, true, model, redirectAttr);
				}
			} else {
				return modifyGraphicDesign(graphicDbDesign, false, model, redirectAttr);
			}
		} catch (BusinessException ex) {
			//get subject area
			List<SubjectArea> listOfArea = areaService.getAllByProjectId(projectId);
			model.addAttribute("areas", listOfArea);
			model.addAttribute("message", ex.getResultMessages());
			return DATABASE_DESIGN_FORM;
		}
	}
	
	/**
	 * return viewListAffectedChangeDesignForm screen
	 * 
	 * @param tableDesignForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "modifyWithAffect", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "viewListAffectedChangeDesignForm", method = {RequestMethod.GET})
	public String displayViewBlogicGet(@Validated @ModelAttribute GraphicDbDesignForm graphicDbDesignForm, BindingResult result, Model model){
		
		try {
			super.checkChangeProject(true);

			Long projectId = SessionUtils.getCurrentProjectId();
			graphicDbDesignForm.setProjectId(projectId);
			
			Project project = projectService.getProjectInformation(projectId, SessionUtils.getAccountId(), true);
			GraphicDbDesign graphicDbDesign = beanMapper.map(graphicDbDesignForm, GraphicDbDesign.class);

			graphicDbDesign.setProject(project);
			graphicDbDesignService.loadAffectChangedDesign(graphicDbDesign, projectId);
			graphicDbDesignForm = beanMapper.map(graphicDbDesign, GraphicDbDesignForm.class);
			model.addAttribute("graphicDbDesignForm", graphicDbDesignForm);
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return VIEW_BLOGIC_LINK;
		}
		return VIEW_BLOGIC_LINK;
	}
	
	/**
	 * 
	 * @param tableDesignForm
	 * @param result
	 * @param redirectAttr
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "modifyGraphic", method = RequestMethod.POST,params="isJsonForm")
	public String processModify(Model model, RedirectAttributes redirectAttr,@JsonProperty @RequestParam("formJson")String formJson){
		super.checkChangeProject(true);
		Long projectId = SessionUtils.getCurrentProjectId();
		GraphicDbDesignForm graphicDbDesignForm = DataTypeUtils.toObject(formJson, GraphicDbDesignForm.class);

		try {
			super.checkChangeProject(true);

			graphicDbDesignForm.setProjectId(projectId);
			Project project = projectService.getProjectInformation(projectId, SessionUtils.getAccountId(), true);
			GraphicDbDesign graphicDbDesign = beanMapper.map(graphicDbDesignForm, GraphicDbDesign.class);
			graphicDbDesign.setProject(project);
			return modifyGraphicDesign(graphicDbDesign, true, model, redirectAttr);	
		} catch (BusinessException ex) {
			//get subject area
			List<SubjectArea> listOfArea = areaService.getAllByProjectId(projectId);
			model.addAttribute("areas", listOfArea);
			model.addAttribute("message", ex.getResultMessages());
			return DATABASE_DESIGN_FORM;
		}
	}
	
	
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modifyGraphic", method = RequestMethod.POST,params="jsonBack")
	public String processBack(Model model, RedirectAttributes redirectAttr,@JsonProperty @RequestParam("formJson")String formJson){
		super.checkChangeProject(true);
		Long projectId = SessionUtils.getCurrentProjectId();
		GraphicDbDesignForm graphicDbDesignForm = DataTypeUtils.toObject(formJson, GraphicDbDesignForm.class);
		graphicDbDesignForm.setProjectId(projectId);

		try {
			List<SubjectArea> listOfArea = areaService.getAllByProjectId(projectId);
			model.addAttribute("areas", listOfArea);
			model.addAttribute("graphicDbDesignForm", graphicDbDesignForm);
			return DATABASE_DESIGN_FORM;	
		} catch (BusinessException ex) {
			//get subject area
			List<SubjectArea> listOfArea = areaService.getAllByProjectId(projectId);
			model.addAttribute("areas", listOfArea);
			model.addAttribute("message", ex.getResultMessages());
			return DATABASE_DESIGN_FORM;
		}
	}
	
	/**
	 * 
	 * @param graphicDbDesign
	 * @return
	 */
	private boolean hasProblem(GraphicDbDesign graphicDbDesign){
		
		boolean hasProblem = false;
		List<TableDesign> tableDesigns = graphicDbDesign.getTableDesigns();

		if (CollectionUtils.isNotEmpty(tableDesigns))
		{
			for (TableDesign tableDesign : tableDesigns) {
				if(CollectionUtils.isNotEmpty(tableDesign.getListBusinessLogics()) || CollectionUtils.isNotEmpty(tableDesign.getListSqlDesigns())){
					hasProblem = true;
					break;
				}
			}
		}
		return hasProblem;
	}

	/**
	 * @param graphicDbDesignForm
	 * @param model
	 * @param graphicDbDesign
	 * @return
	 * @throws MappingException
	 */
	private String modifyGraphicDesign(GraphicDbDesign graphicDbDesign, boolean hasProblem, Model model, RedirectAttributes redirectAttr) throws BusinessException {
		try
		{
			graphicDbDesignService.modifyTableByGraphicDesign(graphicDbDesign, hasProblem, null, null, false, 
					SessionUtils.getAccountId(), SessionUtils.getCurrentLanguageDesign(), DbDomainConst.YesNoFlg.YES);
			/*redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(CommonMessageConst.TQP_GRAPHICDATABASEDEISGN)));*/
			model.addAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(CommonMessageConst.TQP_GRAPHICDATABASEDEISGN)));
			/*return DATABASE_DESIGN_FORM/* + graphicDbDesign.getSubjectAreaId()*/;

			GraphicDbDesignForm graphicDbDesignForm = beanMapper.map (graphicDbDesign, GraphicDbDesignForm.class);
			return loadGraphicDesign(graphicDbDesignForm, model, graphicDbDesign.getProject().getProjectId());
			
		} catch (BusinessException ex)
		{
			throw ex;
		}

	}
}
