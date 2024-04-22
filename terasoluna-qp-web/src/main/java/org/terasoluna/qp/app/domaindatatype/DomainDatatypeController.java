package org.terasoluna.qp.app.domaindatatype;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.DomainDatatypeMessage;
import org.terasoluna.qp.domain.model.DomainDatatype;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.repository.domaindatatype.DomainDatatypeCriteria;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeService;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeUtil;


@Controller
@RequestMapping(value = "domaindatatype")
@TransactionTokenCheck(value = "domaindatatype")
@SessionAttributes(types={DomainDatatypeSearchForm.class})
public class DomainDatatypeController extends BaseController{

	private static final String ACTION_SEARCH = "/domaindatatype/search";
	private static final String LINK_SEARCH = "domaindatatype/searchForm";
	private static final String REDIRECT_SEARCH = "redirect:/domaindatatype/search";
	private static final String LINK_VIEW = "domaindatatype/viewForm";
	private static final String LINK_MODIFY = "domaindatatype/modifyForm";
	private static final String LINK_REFRESH = "refreshForm";

	@Inject
	DomainDatatypeService domainDatatypeService;

	@Inject
	Mapper beanMapper;

	@Inject
	DomainDatatypeValidator domainDatatypeValidator;
	
	@InitBinder("domainDatatypeForm")
	public void initBinder(WebDataBinder webDataBinder) {
		// add custom validators to default bean validations
		webDataBinder.addValidators(domainDatatypeValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute
	public DomainDatatypeSearchForm setUpSearchForm() {
		DomainDatatypeSearchForm obj = new DomainDatatypeSearchForm();
		return obj;
	}

	@ModelAttribute
	public DomainDatatypeForm setUpForm() {
		DomainDatatypeForm obj = new DomainDatatypeForm();
		return obj;
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, 
			@ModelAttribute DomainDatatypeSearchForm domainDatatypeSearchForm,
			Model model, @PageableDefault Pageable pageable,SessionStatus sessionStatus) {
		
		if(init != null){
			sessionStatus.setComplete();
			domainDatatypeSearchForm = setUpSearchForm();
			domainDatatypeSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
			model.addAttribute("domainDatatypeSearchForm",domainDatatypeSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		
		checkChangeProject(false);
		//init paging
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH), pageable.getSort());
		
		DomainDatatypeCriteria searchCriteria = beanMapper.map(domainDatatypeSearchForm, DomainDatatypeCriteria.class);
		Page<DomainDatatype> pages = domainDatatypeService.findPageByCriteria(searchCriteria, pageable);
		model.addAttribute("page", pages);

		return LINK_SEARCH;
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(
			@ModelAttribute DomainDatatypeSearchForm domainDatatypeSearchForm,
			Model model, @PageableDefault Pageable pageable) {
		checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH), pageable.getSort());
		domainDatatypeSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		DomainDatatypeCriteria searchCriteria = beanMapper.map(domainDatatypeSearchForm, DomainDatatypeCriteria.class);
		Page<DomainDatatype> pages = domainDatatypeService.findPageByCriteria(searchCriteria, pageable);
		model.addAttribute("page", pages);

		return LINK_SEARCH;
	}

	@RequestMapping(value = "view")
	public String displayView(@RequestParam("id") long id, Model model) {
		int notExistFlg = 1;
		try {
			DomainDatatype domainDatatype = domainDatatypeService.findOne(id, DomainDatatypeConst.VIEW_ACTION);

			model.addAttribute("modelObj", domainDatatype);
			// get list module
			List<Module> listOfModule = domainDatatypeService.findAllModuleByTableMappingId(id);
			model.addAttribute("modules", listOfModule);

			// get list domain design
			Map<String, String> listOfDomain = domainDatatypeService.listOfDomainDesignExt(domainDatatype.getProjectId());
			model.addAttribute("domains", listOfDomain);
			notExistFlg = 0;
			if (domainDatatype.getTableDesignId() == 0) {
				notExistFlg = 2;
				throw new BusinessException(MessageUtils.getMessage(DomainDatatypeMessage.ERR_DOMAINDATATYPE_0011));
			}
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
		}
		model.addAttribute("notExistFlg", notExistFlg);
		return LINK_VIEW;
	}

	@RequestMapping(value = "delete")
	public String processDelete(@RequestParam("id") long id, Model model) {
		domainDatatypeService.deleteDomainDatatype(id);
		return LINK_REFRESH;
	}

	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method=RequestMethod.GET)
	public String displayModify(DomainDatatypeForm domainDatatypeForm, Model model) {
		//if (domainDatatypeForm.getDomainDatatypeId() != 0) {
			DomainDatatype modelObj = null;
			int notExistFlg = 1;
			try {
				checkChangeProject(true);
				//if not exists
				if (domainDatatypeForm == null || DomainDatatypeUtil.isEmpty(domainDatatypeForm.getDomainDatatypeId())) {
					model.addAttribute("notExistFlg", "1");
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, 
							MessageUtils.getMessage(CommonMessageConst.TQP_DOMAINDATATYPE)));
				}
				
				modelObj = domainDatatypeService.findOne(domainDatatypeForm.getDomainDatatypeId(), DomainDatatypeConst.MODIFY_ACTION);
				modelObj.setProjectId(SessionUtils.getCurrentProjectId());
				domainDatatypeForm = beanMapper.map(modelObj, DomainDatatypeForm.class);
				model.addAttribute("domainDatatypeForm", domainDatatypeForm);
				//get list domain desgin
				Map<Integer, List<DomainDesign>> listOfDomainDesign = domainDatatypeService.listOfDomainDesign(modelObj.getProjectId());
				model.addAttribute("listOfDomainDesign", listOfDomainDesign);
				notExistFlg = 0;
				if (domainDatatypeForm.getTableDesignId() == 0) {
					throw new BusinessException(MessageUtils.getMessage(DomainDatatypeMessage.ERR_DOMAINDATATYPE_0011));
				}
			} catch(BusinessException ex){
				model.addAttribute("notExistFlg", notExistFlg);
				model.addAttribute("message", ex.getResultMessages());
			}
			return LINK_MODIFY;
		//} else {
		//	return REDIRECT_SEARCH;
		//}
	}

	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(
			@Validated @ModelAttribute DomainDatatypeForm domainDatatypeForm,
			BindingResult result, RedirectAttributes redirectAttr, Model model) {
		checkChangeProject(true);
		if (result.hasErrors()) {
			//get list domain desgin
			Map<Integer, List<DomainDesign>> listOfDomainDesign = domainDatatypeService.listOfDomainDesign(domainDatatypeForm.getProjectId());
			model.addAttribute("listOfDomainDesign", listOfDomainDesign);

			ValidationUtils.setBindingResult(result,model);
			return LINK_MODIFY;
		}

		DomainDatatype modelObj = beanMapper.map(domainDatatypeForm, DomainDatatype.class);
		modelObj.setProjectId(SessionUtils.getCurrentProjectId());
		try {
			domainDatatypeService.updateDomainDatatype(modelObj);
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(CommonMessageConst.TQP_DOMAINDATATYPE)));
		} catch(BusinessException ex){
			//get list domain desgin
			Map<Integer, List<DomainDesign>> listOfDomainDesign = domainDatatypeService.listOfDomainDesign(domainDatatypeForm.getProjectId());
			model.addAttribute("listOfDomainDesign", listOfDomainDesign);

			model.addAttribute("message", ex.getResultMessages());
			return LINK_MODIFY;
		}

		return REDIRECT_SEARCH;
	}
}
