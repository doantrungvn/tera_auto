package org.terasoluna.qp.app.sample;

import java.util.List;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.domain.model.Sample;
import org.terasoluna.qp.domain.model.SampleType;
import org.terasoluna.qp.domain.repository.sample.SampleCriteria;
import org.terasoluna.qp.domain.service.sample.SampleService;
import org.terasoluna.qp.domain.service.sampletype.SampleTypeService;

@Controller
@RequestMapping(value = "sample")
@SessionAttributes(types=SampleSearchForm.class)
public class SampleController {	
	@Inject
	SampleTypeService sampleTypeService;

	@Inject
	SampleService sampleService;

	@Inject
	Mapper beanMapper;

	/**
	 * pre-initialization of form backed bean
	 * @return
	 */
	@ModelAttribute
	public SampleForm setUpForm() {
		SampleForm obj = new SampleForm();
		return obj;
	}

	@ModelAttribute("SampleTypes")
	public List<SampleType> setUpSampleTypes() {
		List<SampleType> listOfSampleType = sampleTypeService.getSampleTypes();
		return listOfSampleType;
	}
	
	/**
	 * pre-initialization of form backed bean
	 * @return
	 */
	@ModelAttribute
	public SampleSearchForm setUpSearchForm() {
		SampleSearchForm obj = new SampleSearchForm();
		return obj;
	}

	/**
	 * return to search screen
	 * @param sampleSearchForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search", params="init")
	public String displaySearch(@ModelAttribute SampleSearchForm sampleSearchForm, Model model, @PageableDefault Pageable pageable) {
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/sample/search"), pageable.getSort());
		SampleCriteria sampleCriteria = beanMapper.map(sampleSearchForm, SampleCriteria.class);
		Page<Sample> pageSample = sampleService.findPageByCriteria(
				sampleCriteria, pageable);
		model.addAttribute("page", pageSample);

		return "sample/searchForm";
	}
		
	/**
	 * search samples
	 * @param sampleSearchForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search")
	public String processSearch(@ModelAttribute SampleSearchForm sampleSearchForm, Model model, @PageableDefault Pageable pageable) {
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/sample/search"), pageable.getSort());
		SampleCriteria sampleCriteria = beanMapper.map(sampleSearchForm, SampleCriteria.class);
		Page<Sample> pageSample = sampleService.findPageByCriteria(sampleCriteria, pageable);
		model.addAttribute("page", pageSample);

		return "sample/searchForm";
	}	

	/**
	 * return to create a new sample screen
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "register")
	public String displayRegister(@ModelAttribute SampleForm sampleForm ,Model model) {
		List<SampleType> listOfSampleType = sampleTypeService.getSampleTypes();
		model.addAttribute("SampleTypes", listOfSampleType);			
		return "sample/registerForm";
	}

	/**
	 * Create a new sample
	 * @param sampleForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated @ModelAttribute SampleForm sampleForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {

		if (result.hasErrors()) {
			return displayRegister(sampleForm, model);
		}
		Sample sample = beanMapper.map(sampleForm, Sample.class);
		sampleService.addSample(sample);
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add("inf.sys.0002", "Sample"));
		return "redirect:search?init";
	}

	/**
	 * return view screen
	 * @param sampleForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "view")
	public String displayView(SampleForm sampleForm, Model model) {
		Sample sample = sampleService.findOne(sampleForm.getColumnId());
		model.addAttribute("sample", sample);
		return "sample/viewForm";
	}

	/**
	 * delete a sample
	 * @param sampleForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String processDelete(SampleForm sampleForm, Model model) {
		sampleService.deleteSample(sampleForm.getColumnId());
		return "refreshForm";
	}

	/**
	 * Return to edit a sample screen
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "modify")
	public String displayModify(@RequestParam("id") Integer id, Model model) {
		SampleForm obj = new SampleForm();
		if (id != null) {
			Sample sample = sampleService.findOne(id);
			obj = beanMapper.map(sample, SampleForm.class);
		}

		model.addAttribute("sampleForm", obj);
		return "sample/modifyForm";
	}

	/**
	 * Edit a sample
	 * @param sampleForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated @ModelAttribute SampleForm sampleForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		if (result.hasErrors()) {
			return displayModify(sampleForm.getColumnId(), model);
		}
		Sample sample = beanMapper.map(sampleForm, Sample.class);
		sampleService.updateSample(sample);
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add("inf.sys.0003", "Sample"));
		return "redirect:search?init";
	}
}
