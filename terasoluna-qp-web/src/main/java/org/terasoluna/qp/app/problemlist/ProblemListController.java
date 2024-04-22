package org.terasoluna.qp.app.problemlist;
import javax.inject.Inject;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ProblemListMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListCriteria;
import org.terasoluna.qp.domain.service.problemlist.ProblemListService;

@Controller
@RequestMapping(value = "problemlist")
@TransactionTokenCheck(value = "problemlist")
@SessionAttributes(types = { ProblemListSearchForm.class })
public class ProblemListController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger( ProblemListController.class);
	private static final String PROBLEM_LIST_SEARCH_FORM_NAME = "problemListSearchForm";
	private static final String PROBLEM_LIST_FORM_NAME = "problemListForm";
	private static final String SEARCH_FORM_PATH ="problemlist/searchForm";
	private static final String SEARCH_ACTION_PATH ="/problemlist/search";
	
	@Inject
	ProblemListService problemListService;
	
	@Inject
	Mapper beanMapper;

	@ModelAttribute(PROBLEM_LIST_SEARCH_FORM_NAME)
	public ProblemListSearchForm setUpProblemListSearchForm() {
		ProblemListSearchForm problemListSearchForm = new ProblemListSearchForm();
		logger.debug("Init form {0}", problemListSearchForm);
		return problemListSearchForm;
	}
	/**
	 * pre-initialization of form backed bean
	 * @return
	 */
	@ModelAttribute(PROBLEM_LIST_FORM_NAME)
	public ProblemListForm setUpProblemListForm() {
		ProblemListForm problemListForm = new ProblemListForm();
		logger.debug("Init form {0}", problemListForm);
		return problemListForm;
	}

	@InitBinder
	public void init() {
		moduleCode = CommonMessageConst.TQP_PROBLEMLIST;
	}
	
	/**
	 * return to search screen
	 * @param problemListSearchForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search", method=RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init,@ModelAttribute(PROBLEM_LIST_SEARCH_FORM_NAME) ProblemListSearchForm problemListSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		checkChangeProject(false);
		CommonModel common = this.initCommon();
		if(init != null){
			sessionStatus.setComplete();
			problemListSearchForm = new ProblemListSearchForm();
			model.addAttribute(PROBLEM_LIST_SEARCH_FORM_NAME, problemListSearchForm);
		}
		problemListSearchForm.setProjectId(common.getWorkingProjectId());
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		ProblemListCriteria problemListCriteria = beanMapper.map(problemListSearchForm, ProblemListCriteria.class);
		Page<ProblemList> page = problemListService.searchProblemList(problemListCriteria, pageable, common.getWorkingLanguageId());
		model.addAttribute("page", page);
		
		return SEARCH_FORM_PATH;
	}

	/**
	 * search message design
	 * @param messageDesignSearchForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(@ModelAttribute(PROBLEM_LIST_SEARCH_FORM_NAME) ProblemListSearchForm problemListSearchForm, Model model, @PageableDefault Pageable pageable) {
		checkChangeProject(false);
		CommonModel common = this.initCommon();
		problemListSearchForm.setProjectId(common.getWorkingProjectId());
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		ProblemListCriteria problemListCriteria = beanMapper.map(problemListSearchForm, ProblemListCriteria.class);
		Page<ProblemList> page = problemListService.searchProblemList(problemListCriteria, pageable, common.getWorkingLanguageId());
		model.addAttribute("page", page);
		
		return SEARCH_FORM_PATH;
	}
	
	@RequestMapping(value = "autofix")
	public String processAutoFix(ProblemListForm problemListForm,Model model, RedirectAttributes redirectAttr) {
		checkChangeProject(false);
		
		problemListService.autoFix(beanMapper.map(problemListForm,ProblemList.class));
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(ProblemListMessageConst.INF_PROBLEMLIST_0013));
		return "redirect:/problemlist/search";
	}
	
	/**
	 * search message design
	 * @param messageDesignSearchForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String processDelete(ProblemListForm problemListForm, Model model, RedirectAttributes redirectAttr) {
		checkChangeProject(false);
		try{
			problemListService.deleteProblem(problemListForm.getProblemId());
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(ProblemListMessageConst.SC_PROBLEMLIST_0012)));
		}catch(BusinessException be){
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
		}
		return "redirect:/problemlist/search";
	}
	
}