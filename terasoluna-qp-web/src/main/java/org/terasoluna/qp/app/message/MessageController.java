/*
 * @(#)MessagesController.java
 *
 * Copyright (c) 2015 NTTDATA Vietnam.
 */
package org.terasoluna.qp.app.message;

import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
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
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.domain.model.Message;
import org.terasoluna.qp.domain.repository.message.MessageSearchCriteria;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.language.LanguageService;
import org.terasoluna.qp.domain.service.message.MessageService;

@Controller
@RequestMapping(value = "message")
@SessionAttributes(types = { MessageForm.class })
public class MessageController {

	@Inject
	LanguageService languageService;

	@Inject
	MessageService messageService;

	@Inject
	Mapper beanMapper;

	@Inject
	SystemService systemService;
	
	@Inject
	MessageFormValidator messageFormValidator;

	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * 
	 * @param webDataBinder
	 *            WebDataBinder
	 */
	@InitBinder("messageForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(messageFormValidator);
//		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * Pre-initialization of form backed bean
	 * 
	 * @param model
	 * @return
	 */
	@ModelAttribute
	public MessageForm setUpForm() {
		MessageForm messageForm = new MessageForm();
		return messageForm;
	}

	/**
	 * Pre-initialization of language
	 * 
	 * @param model
	 * @return languages Collection
	 */
	/*
	 * @ModelAttribute("languages") public Collection<Language> setUpLanguage() { Collection<Language> languages = languageService.findAllLanguage(); return languages; }
	 */

	/**
	 * pre-initialization of module resource
	 * 
	 * @return moduleResources Collection
	 */
	@ModelAttribute("moduleResources")
	public Collection<Message> setUpModuleResource() {
		Collection<Message> moduleResources = messageService.findAllModuleResource();
		return moduleResources;
	}

	/**
	 * Display search message screen process
	 * 
	 * @param messageForm
	 *            MessageForm
	 * @param model
	 *            Model
	 * @param pageable
	 *            Pageable
	 * @return "message/searchForm"
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @RequestParam(value = "check", required = false) String check, MessageForm messageForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if (init != null) {
			sessionStatus.setComplete();
			messageForm = new MessageForm();
			model.addAttribute("messageForm", messageForm);
		}
		if (check != null) {
			model.addAttribute("messageForm", messageForm);
			if (messageForm.getPageNumber() != null) {
				pageable = new PageRequest(messageForm.getPageNumber(), messageForm.getPageSize(), pageable.getSort());
				MessageSearchCriteria criteria = beanMapper.map(messageForm, MessageSearchCriteria.class);
				Page<Message> page = messageService.findPageByCriteria(criteria, pageable);
				model.addAttribute("page", page);
				return "message/searchForm";
			}
		}
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/message/search"), pageable.getSort());
		MessageSearchCriteria criteria = beanMapper.map(messageForm, MessageSearchCriteria.class);
		Page<Message> page = messageService.findPageByCriteria(criteria, pageable);
		model.addAttribute("page", page);
		return "message/searchForm";
	}

	/**
	 * Search message process
	 * 
	 * @param messageForm
	 *            MessageForm
	 * @param model
	 *            Model
	 * @param pageable
	 *            Pageable
	 * @return "message/searchForm"
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(MessageForm messageForm, BindingResult result, Model model, @PageableDefault Pageable pageable) {
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/message/search"), pageable.getSort());
		MessageSearchCriteria criteria = beanMapper.map(messageForm, MessageSearchCriteria.class);
		Page<Message> page = messageService.findPageByCriteria(criteria, pageable);
		model.addAttribute("page", page);

		return "message/searchForm";
	}

	/**
	 * Modify message process
	 * 
	 * @param messageForm
	 *            MessageForm
	 * @param result
	 *            BindingResult
	 * @param model
	 *            Model
	 * @param redirectAttr
	 *            RedirectAttributes
	 * @param pageable
	 *            Pageable
	 * @return "redirect:search?init"
	 */
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated @ModelAttribute MessageForm messageForm, BindingResult result, Model model, RedirectAttributes redirectAttr, @PageableDefault Pageable pageable) {
		if (result.hasErrors()) {
		    //Fix bug message validate
			ValidationUtils.setBindingResult(result, model);
			
			pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/message/search"), pageable.getSort());
			MessageSearchCriteria criteria = beanMapper.map(messageForm, MessageSearchCriteria.class);
			Page<Message> page = messageService.findPageByCriteria(criteria, pageable);
			for (Message message : page.getContent()) {
				for (Message msg : messageForm.getListMessage()) {
					if (FunctionCommon.equals(message.getMessageId(), msg.getMessageId())) {
						message.setMessageString(msg.getMessageString());
					} else {
						continue;
					}
				}
			}
			model.addAttribute("page", page);

			return "message/searchForm";
		}
		try {
			if (CollectionUtils.isNotEmpty(messageForm.getListMessage())) {
				messageService.modifyMessages(messageForm.getListMessage());
			}
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/message/search"), pageable.getSort());
			MessageSearchCriteria criteria = beanMapper.map(messageForm, MessageSearchCriteria.class);
			Page<Message> page = messageService.findPageByCriteria(criteria, pageable);
			model.addAttribute("page", page);
			return "message/searchForm";
		}

		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(CommonMessageConst.TQP_MESSAGE)));
		return "redirect:search?check";
	}

	@RequestMapping(value = "reload", method = RequestMethod.GET)
	public String reloadMessage(RedirectAttributes redirectAttr, @PageableDefault Pageable pageable) {
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_MESSAGE)));
		MessageUtils.reloadMessage = true;
		return "redirect:search?init";
	}

}
