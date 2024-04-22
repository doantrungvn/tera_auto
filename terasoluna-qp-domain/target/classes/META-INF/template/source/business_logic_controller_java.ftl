/*
 * @(#)${module.moduleCode?cap_first }Controller.java
 *
 * Copyright (c) NTTDATA Vietnam.
 */
package ${package}.app.${module.moduleCode?lower_case };

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.FunctionMasterUtils;
import org.terasoluna.qp.app.common.ultils.DateCustomConverter;
<#assign sessionAttributes = "">

<#list businessLogicLst as item>
import ${package}.domain.service.${module.moduleCode?lower_case }.${item.businessLogicCode?cap_first }OutputBean;
import ${package}.domain.service.${module.moduleCode?lower_case }.${item.businessLogicCode?cap_first }InputBean;
	<#list item.lstDataSourceOutputBean as dataSrc>
import ${package}.domain.service.${module.moduleCode?lower_case }.${item.businessLogicCode?cap_first }DataSourceOutputBean;
		<#assign sessionAttributes = "${sessionAttributes} , ${item.businessLogicCode?cap_first }DataSourceOutputBean.class">
		<#break>
	</#list>
</#list>
import ${package}.domain.service.${module.moduleCode?lower_case }.${module.moduleCode?cap_first }Service;
import org.terasoluna.qp.app.common.ultils.CustomizeSessionUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
${strSessionImport }

/**
 * ${module.remark!""}
 *
 * @author ${module.author!""}
 * @version $Revision$
 */
@Controller
@RequestMapping(value = "${module.moduleCode?lower_case }")
@TransactionTokenCheck(value = "${module.moduleCode?lower_case }")
<#if sessionProcessSearch?has_content>
@SessionAttributes(types = { ${sessionProcessSearch } ${sessionAttributes}})
</#if>
public class ${module.moduleCode?cap_first }Controller {
	private static final Logger logger = LoggerFactory.getLogger(${module.moduleCode?cap_first }Controller.class);

	/**
	 * 
	 */
	@Inject
	${module.moduleCode?cap_first }Service service;
	
	/**
	 * 
	 */
	@Inject
	Mapper beanMapper;
	
	/**
	 * 
	 */
	private DateCustomConverter dtConvert = new DateCustomConverter();
	
	/**
	 * Is Business check existence 
	 *
	 */
	private final String IS_EXISTENCE = "2";
	
	/**
	 * Business check exception 
	 *
	 */
	private final String BCHECK = "0";
	
	/**
	 * 
	 */
	private final String EXCEPTION = "1";
	
	<#list businessLogicLst as item>
		
		<#switch item.returnType>
			<#case 0 >
			<#case 1 >
			
	<#switch item.patternType>
			<#case 0 >
				<#if item.returnType == 0 || (item.returnType == 1 && item.requestMethod ==  0)>
	/**
     * ${item.businessLogicName}
     *
     * @return view
     */
	@RequestMapping(value = "${item.businessLogicCode?lower_case }",  method = RequestMethod.GET)
	public String ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm, RedirectAttributes redirectAttr, Model model) {
		String destination = "";
		${item.businessLogicCode?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean = beanMapper.map(${item.businessLogicCode?uncap_first }InputForm, ${item.businessLogicCode?cap_first }InputBean.class);
		${item.businessLogicCode?cap_first }OutputForm ${item.businessLogicCode?uncap_first }OutputForm = new ${item.businessLogicCode?cap_first }OutputForm();
		
		${item.strSessionInput }
	
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first }OutputBean  = new ${item.businessLogicCode?cap_first }OutputBean();
		
		try {
			 ${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);
		
			<#-- Adding data source -->
			<#if item.strDataSourceContent?has_content>
			${item.strDataSourceContent }
			model.addAttribute("${item.businessLogicCode?uncap_first }DataSourceOutputBean", ${item.businessLogicCode?uncap_first }DataSourceOutputBean);
			</#if>
			
			${item.strSessionOutput }
			
			<#if item.strResultMsg?has_content>
			redirectAttr.addFlashAttribute("message", ${item.businessLogicCode?uncap_first }OutputBean.getResultMessages());
			</#if>
		
			switch (${item.businessLogicCode?uncap_first }OutputBean.getNavigatorId()) {
				
			<#list item.lstNavigatorDetails as navigator>
				case "${navigator.navigatorId }" :
					// Assign setting 
					${navigator.navigatorAssignContent }
					destination = "${navigator.navigatorDirect?lower_case}";
				break;
			</#list>
		
				default:
					destination = "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case}";
					break;
			}
		} catch (BusinessException ex) {
						
			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			model.addAttribute("${item.businessLogicCode?uncap_first }OutputForm", ${item.businessLogicCode?uncap_first }OutputForm);
			
			if(ex.getCause() != null && ex.getCause().getMessage() != null) {
				String[] err = ex.getCause().getMessage().split("_");
				
				if (err[0].equals(BCHECK)) {
					if (IS_EXISTENCE.equals(err[1])) {
						return "common/error/businessCheck";
					} else {
						return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
					}
				} else if (err[0].equals(EXCEPTION)) {
				<#if item.lstExceptionDetails?has_content>
					switch (err[1]) {
				
					<#list item.lstExceptionDetails as exception>
						case "${exception.exceptionId }" :
							// Assign setting 
							${exception.exceptionAssignContent }
							return "${exception.exceptionDirect?lower_case}";
					</#list>
					}
				</#if>
				}
			}

			return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
		}
		
		return destination;
	}				

				<#elseif item.returnType == 1 && item.requestMethod ==  4 >
	/**
     * ${item.businessLogicName}
     *
     * @return view
     */
	@RequestMapping(value = "${item.businessLogicCode?lower_case }",  method = RequestMethod.POST)
	public String ${item.businessLogicCode?uncap_first }(@Validated({ Default.class }) ${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
				String destination = "";
		${item.businessLogicCode?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean = beanMapper.map(${item.businessLogicCode?uncap_first }InputForm, ${item.businessLogicCode?cap_first }InputBean.class);
		${item.businessLogicCode?cap_first }OutputForm ${item.businessLogicCode?uncap_first }OutputForm = new ${item.businessLogicCode?cap_first }OutputForm();
		
		${item.strSessionInput }
		
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first }OutputBean  = new ${item.businessLogicCode?cap_first }OutputBean();
		
		try {
			${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);
			
			<#-- Adding data source -->
			<#if item.strDataSourceContent?has_content>
			${item.strDataSourceContent }
			model.addAttribute("${item.businessLogicCode?uncap_first }DataSourceOutputBean", ${item.businessLogicCode?uncap_first }DataSourceOutputBean);
			</#if>
			
			${item.strSessionOutput }
			
			<#if item.strResultMsg?has_content>
			redirectAttr.addFlashAttribute("message", ${item.businessLogicCode?uncap_first }OutputBean.getResultMessages());
			</#if>
		
			switch (${item.businessLogicCode }OutputBean.getNavigatorId()) {
				
			<#list item.lstNavigatorDetails as navigator>
				case "${navigator.navigatorId }" :
					// Assign setting 
					${navigator.navigatorAssignContent }
					destination = "${navigator.navigatorDirect?lower_case}";
				break;
			</#list>
		
				default:
					<#if item.screenCode?has_content>
					destination = "redirect:/${module.moduleCode?lower_case }/${item.screenCode?lower_case }";
					</#if>
					break;
			}
		} catch (BusinessException ex) {
			
			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			model.addAttribute("${item.businessLogicCode?uncap_first }OutputForm", ${item.businessLogicCode?uncap_first }OutputForm);
			
			if(ex.getCause() != null && ex.getCause().getMessage() != null) {
				String[] err = ex.getCause().getMessage().split("_");
				
				if (err[0].equals(BCHECK)) {
					if (IS_EXISTENCE.equals(err[1])) {
						return "common/error/businessCheck";
					} else {
						return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
					}
				} else if (err[0].equals(EXCEPTION)) {
				<#if item.lstExceptionDetails?has_content>
					switch (err[1]) {
				
					<#list item.lstExceptionDetails as exception>
						case "${exception.exceptionId }" :
							// Assign setting 
							${exception.exceptionAssignContent }
							return "${exception.exceptionDirect?lower_case}";
					</#list>
					}
				</#if>
				}
			}

			return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
		}
		
		return destination;
	}
				</#if>		

				<#break>
		
			<#-- Search screen -->
			<#case 1 >
				<#if item.returnType == 0>
	/**
     * ${item.businessLogicName}
     *
     * @return view
     */
	@RequestMapping(value = "${item.businessLogicCode?lower_case }",  method = RequestMethod.GET)
	public String ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode }InputForm, RedirectAttributes redirectAttr , Model model, @PageableDefault Pageable pageable) {
				<#else>
	/**
     * ${item.businessLogicName}
     *
     * @return view
     */
	@RequestMapping(value = "${item.businessLogicCode?lower_case }",  method = RequestMethod.POST)
	public String ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm, RedirectAttributes redirectAttr , Model model, @PageableDefault Pageable pageable) {
		model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
				</#if>
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case}"), pageable.getSort());
		${item.businessLogicCode?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean = beanMapper.map(${item.businessLogicCode?uncap_first }InputForm, ${item.businessLogicCode?cap_first }InputBean.class);
		java.util.Map<Integer, Object> map = new java.util.HashMap<Integer, Object>();
		${item.strSessionInput }
		
		${item.businessLogicCode?cap_first }OutputForm ${item.businessLogicCode?uncap_first }OutputForm = new ${item.businessLogicCode?cap_first }OutputForm();
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first }OutputBean  = new ${item.businessLogicCode?cap_first }OutputBean();
		
		try {
			<#if item.lstOutputBean?has_content>
				<#list item.lstOutputBean as output>
					<#if output.parentOutputBeanId?has_content == false && output.dataType?has_content && (output.dataType == 0 || output.dataType == 14 || output.dataType == 16 || output.dataType == 17)>
			map = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean, pageable);
						<#if item.bdModelPageAttr?has_content>
			${item.bdModelPageAttr}
						</#if>
						<#break>
					</#if>
				</#list>
			<#else>
			map = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean, pageable);
			model.addAttribute("page", map.get(1));
			</#if>

			<#assign outputBean = "">
			<#if item.strDataSourceContent?has_content>
			<#assign outputBean = "${item.businessLogicCode?uncap_first }OutputBean">
			${item.businessLogicCode?uncap_first }OutputBean = (${item.businessLogicCode?cap_first }OutputBean)map.get(0);
			${item.strDataSourceContent }
			model.addAttribute("${item.businessLogicCode?uncap_first }DataSourceOutputBean", ${item.businessLogicCode?uncap_first }DataSourceOutputBean);
			</#if>
	
			<#if item.returnType == 0>
				<#-- Setting model attribute for initial display search -->
				<#list businessLogicLst as bl>
					<#if bl.patternType?has_content && bl.patternType == 1 && bl.returnType != 0>
			model.addAttribute("${bl.businessLogicCode?uncap_first }InputForm", new ${bl.businessLogicCode?cap_first }InputForm());
					<#break>
					</#if>
				</#list>
			</#if>

            if(map.get(0) != null) {
                ${item.businessLogicCode?uncap_first }OutputForm = beanMapper.map(map.get(0), ${item.businessLogicCode?cap_first }OutputForm.class);
            }
            
            model.addAttribute("${item.businessLogicCode?uncap_first }OutputForm", ${item.businessLogicCode?uncap_first }OutputForm);
		} catch (BusinessException ex) {
		    if(map.get(0) != null) {
                ${item.businessLogicCode?uncap_first }OutputForm = beanMapper.map(map.get(0), ${item.businessLogicCode?cap_first }OutputForm.class);
            }
			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("${item.businessLogicCode?uncap_first }OutputForm", ${item.businessLogicCode?uncap_first }OutputForm);
			
			if(ex.getCause() != null && ex.getCause().getMessage() != null) {
				String[] err = ex.getCause().getMessage().split("_");
				
				if (err[0].equals(BCHECK)) {
					if (IS_EXISTENCE.equals(err[1])) {
						return "common/error/businessCheck";
					} else {
						return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
					}
				} else if (err[0].equals(EXCEPTION)) {
				<#if item.lstExceptionDetails?has_content>
					switch (err[1]) {
				
					<#list item.lstExceptionDetails as exception>
						case "${exception.exceptionId }" :
							// Assign setting 
							${exception.exceptionAssignContent }
							return "${exception.exceptionDirect?lower_case}";
					</#list>
					}
				</#if>
				}
			}

			return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
		}
		
		return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case}";
	}
				<#break>
				
			<#-- Register screen -->
			<#case 2 >
			<#-- View screen -->
			<#case 3 >
			<#-- Modify screen -->
			<#case 4 >
				<#if item.returnType == 0>
	/**
     * ${item.businessLogicName}
     *
     * @return view
     */
	@RequestMapping(value = "${item.businessLogicCode?lower_case }",  method = RequestMethod.GET)
	public String ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm, RedirectAttributes redirectAttr, Model model) {
		<#-- Setting result error for validate -->
				<#else>
	/**
     * ${item.businessLogicName}
     *
     * @return view
     */
	@RequestMapping(value = "${item.businessLogicCode?lower_case }",  method = RequestMethod.POST)
	public String ${item.businessLogicCode?uncap_first }(@Validated({ Default.class }) ${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		<#if item.patternType ==  2 || item.patternType == 4>
		if (result.hasErrors()) {
			model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case}";
		}
		</#if>
				</#if>
		String destination = "";
		
		${item.businessLogicCode?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean = beanMapper.map(${item.businessLogicCode?uncap_first }InputForm, ${item.businessLogicCode?cap_first }InputBean.class);
		${item.businessLogicCode?cap_first }OutputForm ${item.businessLogicCode?uncap_first }OutputForm = new ${item.businessLogicCode?cap_first }OutputForm();
		${item.strSessionInput }
		
					<#if item.returnType == 1>
						<#if item.navigateType?has_content && item.navigateType == 0>
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first }OutputBean  = new ${item.businessLogicCode?cap_first }OutputBean();
		
		try {
			${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);
			
			<#-- Adding data source -->
			<#if item.strDataSourceContent?has_content>
			${item.strDataSourceContent }
			model.addAttribute("${item.businessLogicCode?uncap_first }DataSourceOutputBean", ${item.businessLogicCode?uncap_first }DataSourceOutputBean);
			</#if>
			
			${item.strSessionOutput }	
	
								<#if item.strResultMsg?has_content>
			redirectAttr.addFlashAttribute("message", ${item.businessLogicCode?uncap_first }OutputBean.getResultMessages());
								</#if>
			
			switch (${item.businessLogicCode?uncap_first }OutputBean.getNavigatorId()) {
				
			<#list item.lstNavigatorDetails as navigator>
				case "${navigator.navigatorId }" :
					// Assign setting 
					${navigator.navigatorAssignContent }
					destination = "${navigator.navigatorDirect?lower_case}";
				break;
			</#list>
		
				default:
					<#if item.screenCode?has_content>
					destination = "redirect:/${module.moduleCode?lower_case }/${item.screenCode?lower_case }";
					</#if>
					break;
			}
		} catch (BusinessException ex) {
		
			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			model.addAttribute("${item.businessLogicCode?uncap_first }OutputForm", ${item.businessLogicCode?uncap_first }OutputForm);
			
			if(ex.getCause() != null && ex.getCause().getMessage() != null) {
				String[] err = ex.getCause().getMessage().split("_");
				
				if (err[0].equals(BCHECK)) {
					if (IS_EXISTENCE.equals(err[1])) {
						return "common/error/businessCheck";
					} else {
						return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
					}
				} else if (err[0].equals(EXCEPTION)) {
				<#if item.lstExceptionDetails?has_content>
					switch (err[1]) {
				
					<#list item.lstExceptionDetails as exception>
						case "${exception.exceptionId }" :
							// Assign setting 
							${exception.exceptionAssignContent }
							return "${exception.exceptionDirect?lower_case}";
					</#list>
					}
				</#if>
				}
			}

			return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
		}
		
		return destination;
						<#else>
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first }OutputBean  = new ${item.businessLogicCode?cap_first }OutputBean();
		
		try {
			${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);
			<#-- Adding data source -->
			<#if item.strDataSourceContent?has_content>
			${item.strDataSourceContent }
			model.addAttribute("${item.businessLogicCode?uncap_first }DataSourceOutputBean", ${item.businessLogicCode?uncap_first }DataSourceOutputBean);
			</#if>
			
			${item.strSessionOutput }
			
								<#if item.strResultMsg?has_content>
			redirectAttr.addFlashAttribute("message", ${item.businessLogicCode?uncap_first }OutputBean.getResultMessages());
								</#if>
	
			switch (${item.businessLogicCode?uncap_first }OutputBean.getNavigatorId()) {
			
			<#list item.lstNavigatorDetails as navigator>
				case "${navigator.navigatorId }" :
					// Assign setting 
					${navigator.navigatorAssignContent }
					destination = "${navigator.navigatorDirect?lower_case}";
				break;
			</#list>
		
				default:
					<#if item.screenCode?has_content>
					destination = "forward:/${module.moduleCode?lower_case }/${item.screenCode?lower_case }";
					</#if>
					break;
			}
		} catch (BusinessException ex) {
						
			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			model.addAttribute("${item.businessLogicCode?uncap_first }OutputForm", ${item.businessLogicCode?uncap_first }OutputForm);
			
			if(ex.getCause() != null && ex.getCause().getMessage() != null) {
				String[] err = ex.getCause().getMessage().split("_");
				
				if (err[0].equals(BCHECK)) {
					if (IS_EXISTENCE.equals(err[1])) {
						return "common/error/businessCheck";
					} else {
						return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
					}
				} else if (err[0].equals(EXCEPTION)) {
				<#if item.lstExceptionDetails?has_content>
					switch (err[1]) {
				
					<#list item.lstExceptionDetails as exception>
						case "${exception.exceptionId }" :
							// Assign setting 
							${exception.exceptionAssignContent }
							return "${exception.exceptionDirect?lower_case}";
					</#list>
					}
				</#if>
				}
			}

			return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
		}
		
		return destination;
						</#if>
					<#else>
			<#assign outputBean = "${item.businessLogicCode?uncap_first }OutputBean">
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first }OutputBean  = new ${item.businessLogicCode?cap_first }OutputBean();
		
		try {
			${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);
			<#-- Adding data source -->
			<#if item.strDataSourceContent?has_content>
			${item.strDataSourceContent }
			model.addAttribute("${item.businessLogicCode?uncap_first }DataSourceOutputBean", ${item.businessLogicCode?uncap_first }DataSourceOutputBean);
			</#if>
			
			${item.strSessionOutput }
			
			<#-- Setting setter/ getter of output to input -->
			<#if item.strOutMappingInContent?has_content>
			${item.strOutMappingInContent }
			</#if>
	
			${item.businessLogicCode?uncap_first }OutputForm = beanMapper.map(${item.businessLogicCode?uncap_first }OutputBean, ${item.businessLogicCode?cap_first }OutputForm.class);
	
			switch (${item.businessLogicCode?uncap_first }OutputBean.getNavigatorId()) {
			
			<#list item.lstNavigatorDetails as navigator>
				case "${navigator.navigatorId }" :
					// Assign setting 
					${navigator.navigatorAssignContent }
					destination = "${navigator.navigatorDirect?lower_case}";
				break;
			</#list>
		
				default:
					<#if item.screenCode?has_content>
					destination = "${module.moduleCode?lower_case }/${item.screenCode?lower_case }";
					</#if>
					break;
			}

			model.addAttribute("${item.businessLogicCode?uncap_first }OutputForm", ${item.businessLogicCode?uncap_first }OutputForm);
		} catch (BusinessException ex) {
		
			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			model.addAttribute("${item.businessLogicCode?uncap_first }OutputForm", ${item.businessLogicCode?uncap_first }OutputForm);
			
			if(ex.getCause() != null && ex.getCause().getMessage() != null) {
				String[] err = ex.getCause().getMessage().split("_");
				
				if (err[0].equals(BCHECK)) {
					if (IS_EXISTENCE.equals(err[1])) {
						return "common/error/businessCheck";
					} else {
						return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
					}
				} else if (err[0].equals(EXCEPTION)) {
				<#if item.lstExceptionDetails?has_content>
					switch (err[1]) {
				
					<#list item.lstExceptionDetails as exception>
						case "${exception.exceptionId }" :
							// Assign setting 
							${exception.exceptionAssignContent }
							return "${exception.exceptionDirect?lower_case}";
					</#list>
					}
				</#if>
				}
			}

			return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
		}
		
		return destination;
					</#if>
	}
				<#break>

			<#-- Delete process -->
			<#case 5 >
				<#if item.returnType == 0>
	/**
     * ${item.businessLogicName}
     *
     * @return view
     */
	@RequestMapping(value = "${item.businessLogicCode?lower_case }",  method = RequestMethod.GET)
				<#else>
	/**
     * ${item.businessLogicName}
     *
     * @return view
     */
	@RequestMapping(value = "${item.businessLogicCode?lower_case }",  method = RequestMethod.POST)
				</#if>
	public String ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm, Model model, RedirectAttributes redirectAttr) {
		${item.businessLogicCode?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean = beanMapper.map(${item.businessLogicCode?uncap_first }InputForm, ${item.businessLogicCode?cap_first }InputBean.class);
		${item.businessLogicCode?cap_first }OutputForm ${item.businessLogicCode?uncap_first }OutputForm = new ${item.businessLogicCode?cap_first }OutputForm();
		String destination = "";
		${item.strSessionInput }
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first }OutputBean  = new ${item.businessLogicCode?cap_first }OutputBean();
		
		try {
			${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);
			<#-- Adding data source -->
			<#if item.strDataSourceContent?has_content>
			${item.strDataSourceContent }
			model.addAttribute("${item.businessLogicCode?uncap_first }DataSourceOutputBean", ${item.businessLogicCode?uncap_first }DataSourceOutputBean);
			</#if>
			
			${item.strSessionOutput }
			
			switch (${item.businessLogicCode?uncap_first }OutputBean.getNavigatorId()) {
	
			<#list item.lstNavigatorDetails as navigator>
				case "${navigator.navigatorId }" :
					// Assign setting 
					${navigator.navigatorAssignContent }
					destination = "${navigator.navigatorDirect?lower_case}";
				break;
			</#list>
		
				default:
					destination = DbDomainConst.REDIRECT_DELETION_SUCCESS;
					break;
			}
	
						<#if item.strResultMsg?has_content>
			redirectAttr.addFlashAttribute("message", ${item.businessLogicCode?uncap_first }OutputBean.getResultMessages());
						</#if>
		} catch (BusinessException ex) {
		
			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			model.addAttribute("${item.businessLogicCode?uncap_first }OutputForm", ${item.businessLogicCode?uncap_first }OutputForm);
			
			if(ex.getCause() != null && ex.getCause().getMessage() != null) {
				String[] err = ex.getCause().getMessage().split("_");
				
				if (err[0].equals(BCHECK)) {
					if (IS_EXISTENCE.equals(err[1])) {
						return "common/error/businessCheck";
					} else {
						return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
					}
				} else if (err[0].equals(EXCEPTION)) {
				<#if item.lstExceptionDetails?has_content>
					switch (err[1]) {
				
					<#list item.lstExceptionDetails as exception>
						case "${exception.exceptionId }" :
							// Assign setting 
							${exception.exceptionAssignContent }
							return "${exception.exceptionDirect?lower_case}";
					</#list>
					}
				</#if>
				}
			}

			return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
		}
		return destination;
	}
				<#break>
				
			<#case 6 >
			<#case 7 >
			<#case 8 >
			<#case 9 >
				<#if item.returnType == 0>
	/**
     * ${item.businessLogicName}
     *
     * @return view
     */
	@RequestMapping(value = "${item.businessLogicCode?lower_case }",  method = RequestMethod.GET)
	public String ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
				<#else>
	/**
     * ${item.businessLogicName}
     *
     * @return view
     */
	@RequestMapping(value = "${item.businessLogicCode?lower_case }",  method = RequestMethod.POST)
	public String ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
				</#if>

		String destination = "";

		${item.businessLogicCode?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean = beanMapper.map(${item.businessLogicCode?uncap_first }InputForm, ${item.businessLogicCode?cap_first }InputBean.class);
		${item.businessLogicCode?cap_first }OutputForm ${item.businessLogicCode?uncap_first }OutputForm = new ${item.businessLogicCode?cap_first }OutputForm();
		${item.strSessionInput }
					<#if item.returnType == 1>
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first}OutputBean = new ${item.businessLogicCode?cap_first }OutputBean();
						<#if item.navigateType?has_content && item.navigateType == 0>
		try {
			${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);
			<#-- Adding data source -->
			<#if item.strDataSourceContent?has_content>
			${item.strDataSourceContent }
			model.addAttribute("${item.businessLogicCode?uncap_first }DataSourceOutputBean", ${item.businessLogicCode?uncap_first }DataSourceOutputBean);
			</#if>
			
			${item.strSessionOutput }
			
			<#if item.strUndoModelSetting?has_content>
			${item.strUndoModelSetting }
			</#if>
			
								<#if item.strResultMsg?has_content>
			redirectAttr.addFlashAttribute("message", ${item.businessLogicCode?uncap_first }OutputBean.getResultMessages());
								</#if>
			
			switch (${item.businessLogicCode?uncap_first }OutputBean.getNavigatorId()) {
				
			<#list item.lstNavigatorDetails as navigator>
				case "${navigator.navigatorId }" :
					// Assign setting 
					${navigator.navigatorAssignContent }
					destination = "${navigator.navigatorDirect?lower_case}";
				break;
			</#list>
		
				default:
					<#if item.screenCode?has_content>
					destination = "redirect:/${module.moduleCode?lower_case }/${item.screenCode?lower_case}";
					</#if>
					break;
			}
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			model.addAttribute("${item.businessLogicCode?uncap_first }OutputForm", ${item.businessLogicCode?uncap_first }OutputForm);
			
			if(ex.getCause() != null && ex.getCause().getMessage() != null) {
				String[] err = ex.getCause().getMessage().split("_");
				
				if (err[0].equals(BCHECK)) {
					if (IS_EXISTENCE.equals(err[1])) {
						return "common/error/businessCheck";
					} else {
						return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
					}
				} else if (err[0].equals(EXCEPTION)) {
				<#if item.lstExceptionDetails?has_content>
					switch (err[1]) {
				
					<#list item.lstExceptionDetails as exception>
						case "${exception.exceptionId }" :
							// Assign setting 
							${exception.exceptionAssignContent }
							return "${exception.exceptionDirect?lower_case}";
					</#list>
					}
				</#if>
				}
			}

			return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
		}
		
		return destination;
						<#else>
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first }OutputBean  = new ${item.businessLogicCode?cap_first }OutputBean();
		
		try {
			${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);
			<#-- Adding data source -->
			<#if item.strDataSourceContent?has_content>
			${item.strDataSourceContent }
			model.addAttribute("${item.businessLogicCode?uncap_first }DataSourceOutputBean", ${item.businessLogicCode?uncap_first }DataSourceOutputBean);
			</#if>
			
			${item.strSessionOutput }
			
			<#if item.strUndoModelSetting?has_content>
			${item.strUndoModelSetting }
			</#if>
			
								<#if item.strResultMsg?has_content>
			redirectAttr.addFlashAttribute("message", ${item.businessLogicCode?uncap_first }OutputBean.getResultMessages());
								</#if>
	
			switch (${item.businessLogicCode?uncap_first }OutputBean.getNavigatorId()) {
			
			<#list item.lstNavigatorDetails as navigator>
				case "${navigator.navigatorId }" :
					// Assign setting 
					${navigator.navigatorAssignContent }
					destination = "${navigator.navigatorDirect?lower_case}";
				break;
			</#list>
		
				default:
					<#if item.screenCode?has_content>
					destination = "forward:/${module.moduleCode?lower_case}/${item.screenCode?lower_case}";
					</#if>
					break;
			}
		} catch (BusinessException ex) {

			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			model.addAttribute("${item.businessLogicCode?uncap_first }OutputForm", ${item.businessLogicCode?uncap_first }OutputForm);
			
			if(ex.getCause() != null && ex.getCause().getMessage() != null) {
				String[] err = ex.getCause().getMessage().split("_");
				
				if (err[0].equals(BCHECK)) {
					if (IS_EXISTENCE.equals(err[1])) {
						return "common/error/businessCheck";
					} else {
						return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
					}
				} else if (err[0].equals(EXCEPTION)) {
				<#if item.lstExceptionDetails?has_content>
					switch (err[1]) {
				
					<#list item.lstExceptionDetails as exception>
						case "${exception.exceptionId }" :
							// Assign setting 
							${exception.exceptionAssignContent }
							return "${exception.exceptionDirect?lower_case}";
					</#list>
					}
				</#if>
				}
			}

			return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
		}
		
		return destination;
						</#if>
					<#else>
			<#assign outputBean = "${item.businessLogicCode?uncap_first }OutputBean">
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first }OutputBean  = new ${item.businessLogicCode?cap_first }OutputBean();
		
		try {
			${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);
			<#-- Adding data source -->
			<#if item.strDataSourceContent?has_content>
			${item.strDataSourceContent }
			model.addAttribute("${item.businessLogicCode?uncap_first }DataSourceOutputBean", ${item.businessLogicCode?uncap_first }DataSourceOutputBean);
			</#if>
			
			${item.strSessionOutput }
			
			<#-- Setting setter/ getter of output to input -->
			<#if item.strOutMappingInContent?has_content>
			${item.strOutMappingInContent }
			</#if>
	
			<#if item.strUndoModelSetting?has_content>
			${item.strUndoModelSetting }
			</#if>
	
			${item.businessLogicCode?uncap_first }OutputForm = beanMapper.map(${item.businessLogicCode?uncap_first }OutputBean, ${item.businessLogicCode?cap_first }OutputForm.class);
	
			switch (${item.businessLogicCode }OutputBean.getNavigatorId()) {
			
			<#list item.lstNavigatorDetails as navigator>
				case "${navigator.navigatorId }" :
					// Assign setting 
					${navigator.navigatorAssignContent }
					destination = "${navigator.navigatorDirect?lower_case}";
				break;
			</#list>
		
				default:
					<#if item.screenCode?has_content>
					destination = "${module.moduleCode?lower_case}/${item.screenCode?lower_case}";
					</#if>
					break;
			}
	
			model.addAttribute("${item.businessLogicCode?uncap_first }OutputForm", ${item.businessLogicCode?uncap_first }OutputForm);
		} catch (BusinessException ex) {

			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			model.addAttribute("${item.businessLogicCode?uncap_first }OutputForm", ${item.businessLogicCode?uncap_first }OutputForm);
			
			if(ex.getCause() != null && ex.getCause().getMessage() != null) {
				String[] err = ex.getCause().getMessage().split("_");
				
				if (err[0].equals(BCHECK)) {
					if (IS_EXISTENCE.equals(err[1])) {
						return "common/error/businessCheck";
					} else {
						return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
					}
				} else if (err[0].equals(EXCEPTION)) {
				<#if item.lstExceptionDetails?has_content>
					switch (err[1]) {
				
					<#list item.lstExceptionDetails as exception>
						case "${exception.exceptionId }" :
							// Assign setting 
							${exception.exceptionAssignContent }
							return "${exception.exceptionDirect?lower_case}";
					</#list>
					}
				</#if>
				}
			}

			return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
		}
		
		return destination;
					</#if>
	}
				<#break>
				
			<#case 10 >
			<#case 11 >
	/**
     * ${item.businessLogicName}
     *
     * @return view
     */
	@RequestMapping(value = "${item.businessLogicCode?lower_case }",  method = RequestMethod.POST)
	public String ${item.businessLogicCode?uncap_first }(@Validated({ Default.class }) ${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {		
		${item.businessLogicCode?cap_first }OutputForm ${item.businessLogicCode?uncap_first }OutputForm = new ${item.businessLogicCode?cap_first }OutputForm();
		String destination = "";

		if (result.hasErrors()) {
			model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
		}
		
		${item.businessLogicCode?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean = beanMapper.map(${item.businessLogicCode?uncap_first }InputForm, ${item.businessLogicCode?cap_first }InputBean.class);
		${item.strSessionInput }
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first}OutputBean = new ${item.businessLogicCode?cap_first }OutputBean();
		try {
			${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);
			<#-- Adding data source -->
			<#if item.strDataSourceContent?has_content>
			${item.strDataSourceContent }
			model.addAttribute("${item.businessLogicCode?uncap_first }DataSourceOutputBean", ${item.businessLogicCode?uncap_first }DataSourceOutputBean);
			</#if>
			
			${item.strSessionOutput }
				
					<#if item.strResultMsg?has_content>
			redirectAttr.addFlashAttribute("message", ${item.businessLogicCode?uncap_first }OutputBean.getResultMessages());
					</#if>
			
			switch (${item.businessLogicCode?uncap_first }OutputBean.getNavigatorId()) {
				
			<#list item.lstNavigatorDetails as navigator>
				case "${navigator.navigatorId }" :
					// Assign setting 
					${navigator.navigatorAssignContent }
					destination = "${navigator.navigatorDirect?lower_case}";
				break;
			</#list>
		
				default:
					<#if item.screenCode?has_content>
					destination = "redirect:/${module.moduleCode?lower_case }/${item.screenCode?lower_case}";
					</#if>
					break;
			}
		} catch (BusinessException ex) {
			
			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			model.addAttribute("${item.businessLogicCode?uncap_first }OutputForm", ${item.businessLogicCode?uncap_first }OutputForm);
			
			if(ex.getCause() != null && ex.getCause().getMessage() != null) {
				String[] err = ex.getCause().getMessage().split("_");
				
				if (err[0].equals(BCHECK)) {
					if (IS_EXISTENCE.equals(err[1])) {
						return "common/error/businessCheck";
					} else {
						return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
					}
				} else if (err[0].equals(EXCEPTION)) {
				<#if item.lstExceptionDetails?has_content>
					switch (err[1]) {
				
					<#list item.lstExceptionDetails as exception>
						case "${exception.exceptionId }" :
							// Assign setting 
							${exception.exceptionAssignContent }
							return "${exception.exceptionDirect?lower_case}";
					</#list>
					}
				</#if>
				}
			}

			return "${module.moduleCode?lower_case }/${getScreenCode(item, lstScreen)?lower_case }";
		}
		
		return destination;
	}
			<#break>
		</#switch>
			
				<#break>
				
			<#-- screen ajax process -->
			<#case 2 >
	/**
     * ${item.businessLogicName}
     *
     * @return ${item.businessLogicCode?cap_first }OutputBean
     */
	@RequestMapping(value = "${item.businessLogicCode?lower_case }", method = RequestMethod.GET)
	@ResponseBody
	public ${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean) {

		// Convert to Object
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first }OutputBean  = new ${item.businessLogicCode?cap_first }OutputBean();
		
		try {
			${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);
			${item.strSessionOutput }
		} catch (BusinessException ex) {
			
		}

		return ${item.businessLogicCode?uncap_first }OutputBean;
	}
				<#break>
				
			<#-- download process -->
			<#case 3 >
	/**
     * ${item.businessLogicName}
     *
     * @return file download
     */
	@RequestMapping(value = "${item.businessLogicCode?uncap_first }", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean) {
	
		// Convert to Object
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first }OutputBean  = new ${item.businessLogicCode?cap_first }OutputBean();
		
		try {
			${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);
			${item.strSessionOutput }
		} catch (BusinessException ex) {
			
		}

		return ${item.businessLogicCode?uncap_first }OutputBean.getResponseEntity();
	}
				<#break>
		</#switch>
	</#list>
}

<#macro printModelRefer isComfirmBlogic businesItem businessLogicLst outputCode>
	<#list businessLogicLst as item>
		<#if isComfirmBlogic >
			<#if item.returnType == 1 && businesItem.patternType == 2 && item.patternType == 6>
		${item.businessLogicCode?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean = new ${item.businessLogicCode?cap_first }InputBean();
		<#-- Setting setter/ getter of output to input -->
		<#if businesItem.strOutMappingInContent?has_content>
		${businesItem.strOutMappingInContent }
		</#if>
		${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm = beanMapper.map(${item.businessLogicCode?uncap_first }InputBean, ${item.businessLogicCode?cap_first }InputForm.class);
		model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			<#elseif item.returnType == 1 && businesItem.patternType == 4 && item.patternType == 7>
		${item.businessLogicCode?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean = new ${item.businessLogicCode?cap_first }InputBean();
		<#-- Setting setter/ getter of output to input -->
		<#if businesItem.strOutMappingInContent?has_content>
		${businesItem.strOutMappingInContent }
		</#if>
		${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm = beanMapper.map(${item.businessLogicCode?uncap_first }InputBean, ${item.businessLogicCode?cap_first }InputForm.class);
		model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			</#if>
		<#else>
			<#if businesItem.screenId?has_content && item.screenId?has_content && item.returnType == 1 && businesItem.screenId == item.screenId>
		${item.businessLogicCode?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean = new ${item.businessLogicCode?cap_first }InputBean();
		<#-- Setting setter/ getter of output to input -->
		<#if businesItem.strOutMappingInContent?has_content>
		${businesItem.strOutMappingInContent }
		</#if>
		${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm = beanMapper.map(${item.businessLogicCode?uncap_first }InputBean, ${item.businessLogicCode?cap_first }InputForm.class);
		model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
			</#if>
		</#if>
	</#list>
</#macro>

<#function getScreenCode businesItem lstScreen>
	<#local screenCodeStr = "">
	<#list lstScreen as item>
		<#if item.screenId?has_content && businesItem.screenId?has_content && businesItem.screenId == item.screenId>
			<#local screenCodeStr = item.screenCode>
			<#break>
		</#if>
	</#list>

	<#return screenCodeStr>
</#function>