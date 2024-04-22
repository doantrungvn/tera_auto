/*
 * @(#)${module.moduleCode?cap_first }Controller.java
 *
 * Copyright (c) NTTDATA Vietnam.
 */
package ${package}.app.webservice.${module.moduleCode?lower_case };

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;

<#list businessLogicLst as item>
import ${package}.domain.service.${module.moduleCode?lower_case }.${item.businessLogicCode?cap_first }OutputBean;
import ${package}.domain.service.${module.moduleCode?lower_case }.${item.businessLogicCode?cap_first }InputBean;
</#list>
import ${package}.domain.service.${module.moduleCode?lower_case }.${module.moduleCode?cap_first }Service;

/**
 * ${module.remark!""}
 *
 * @author ${module.author!""}
 * @version $Revision$
 */
@RestController
@RequestMapping(value = "${module.moduleCode?lower_case }")
@TransactionTokenCheck(value = "${module.moduleCode?lower_case }")
public class ${module.moduleCode?cap_first }RestController {
	private static final Logger logger = LoggerFactory.getLogger(${module.moduleCode?cap_first }RestController.class);

	@Inject
	${module.moduleCode?cap_first }Service service;
	
	@Inject
	Mapper beanMapper;
	
	<#assign screenCodeRegister = "">
	<#assign screenCodeModify = "">
	<#list businessLogicLst as item>
		<#switch item.requestMethod>
			<#case 0 >

	/**
     * ${item.businessLogicName}
     *
     * @return ${item.businessLogicCode?cap_first }OutputForm
     */
	<#if item.returnType == 4 >	
	@RequestMapping(value = "${item.businessLogicCode?lower_case }", method = { RequestMethod.GET, RequestMethod.HEAD }, produces = MediaType.APPLICATION_JSON_VALUE)
	</#if>
	<#if item.returnType == 5 >		
	@RequestMapping(value = "${item.businessLogicCode?lower_case }", method = { RequestMethod.GET, RequestMethod.HEAD }, produces = MediaType.APPLICATION_XML_VALUE)
	</#if>	
	@ResponseBody
	@ResponseStatus(${item.httpStatusValue!"HttpStatus.OK"})
	public ${item.businessLogicCode?cap_first }OutputForm ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm) {
	
		${item.businessLogicName }InputBean ${item.businessLogicCode?uncap_first }InputBean = beanMapper.map(${item.businessLogicCode?uncap_first }InputForm, ${item.businessLogicCode?cap_first }InputBean.class);
		
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first}OutputBean = new ${item.businessLogicCode?cap_first }OutputBean();
		
		${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);	
	
		${item.businessLogicCode?cap_first }OutputForm responseForm = beanMapper.map(${item.businessLogicCode?uncap_first }OutputBean, ${item.businessLogicCode?cap_first }OutputForm.class);
		
		return responseForm;
	}			
	<#break>
					
			<#case 1 >

	/**
     * ${item.businessLogicName}
     *
     * @return ${item.businessLogicCode?cap_first }OutputForm
     */
	<#if item.returnType == 4 >		
	@RequestMapping(value = "${item.businessLogicCode?lower_case }", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	</#if>
	<#if item.returnType == 5 >
	@RequestMapping(value = "${item.businessLogicCode?lower_case }", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
	</#if>
	@ResponseBody
	@ResponseStatus(${item.httpStatusValue!"HttpStatus.OK"})
	public ${item.businessLogicCode?cap_first }OutputForm ${item.businessLogicCode?uncap_first }(@RequestBody @Validated({ Default.class }) ${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm) {
	
		// create a new object.
		${item.businessLogicName?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean = beanMapper.map(${item.businessLogicCode?uncap_first }InputForm, ${item.businessLogicCode?cap_first }InputBean.class);
		
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first}OutputBean = new ${item.businessLogicCode?cap_first }OutputBean();
		
		${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);	
		
		// create response resource.
		${item.businessLogicCode?cap_first }OutputForm responseForm = beanMapper.map(${item.businessLogicCode?uncap_first }OutputBean, ${item.businessLogicCode?cap_first }OutputForm.class);
		
		return responseForm;
	}
	<#break>
								
			<#case 2 >

	/**
     * ${item.businessLogicName}
     *
     * @return ${item.businessLogicCode?cap_first }OutputForm
     */
	<#if item.returnType == 4 >		
	@RequestMapping(value = "${item.businessLogicCode?lower_case }", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	</#if>
	<#if item.returnType == 5 >
	@RequestMapping(value = "${item.businessLogicCode?lower_case }", method = RequestMethod.PUT, produces = MediaType.APPLICATION_XML_VALUE)
	</#if>
	@ResponseBody
	@ResponseStatus(${item.httpStatusValue!"HttpStatus.OK"})
	public ${item.businessLogicCode?cap_first }OutputForm ${item.businessLogicCode?uncap_first }(@RequestBody @Validated({ Default.class }) ${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm){
	
		// modify object.
		${item.businessLogicName?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean = beanMapper.map(${item.businessLogicCode?uncap_first }InputForm, ${item.businessLogicCode?cap_first }InputBean.class);
		
		${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first}OutputBean = new ${item.businessLogicCode?cap_first }OutputBean();
		
		${item.businessLogicCode?uncap_first }OutputBean = service.${item.businessLogicCode?uncap_first }(${item.businessLogicCode?uncap_first }InputBean);	
		
		// create response resource.
		${item.businessLogicCode?cap_first }OutputForm responseForm = beanMapper.map(${item.businessLogicCode?uncap_first }OutputBean, ${item.businessLogicCode?cap_first }OutputForm.class);
	
		return responseForm;
	}
	<#break>
				
			<#case 3 >

	/**
     * ${item.businessLogicName}
     *
     * @return ${item.businessLogicCode?cap_first }OutputForm
     */
	<#if item.returnType == 4 >		
	@RequestMapping(value = "${item.businessLogicCode?lower_case }", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	</#if>
	<#if item.returnType == 5 >
	@RequestMapping(value = "${item.businessLogicCode?lower_case }", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_XML_VALUE)
	</#if>
	@ResponseStatus(${item.httpStatusValue!"HttpStatus.OK"})
	public void ${item.businessLogicCode?cap_first }(${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode }InputForm) {
	
		// delete the specified object.
		${item.businessLogicName }InputBean ${item.businessLogicCode }InputBean = beanMapper.map(${item.businessLogicCode }InputForm, ${item.businessLogicCode?cap_first }InputBean.class);
		service.${item.businessLogicCode }(${item.businessLogicCode }InputBean);	
	
	}
	<#break>
			
		</#switch>
	</#list>
}

<#macro printModelRefer businesItem businessLogicLst>
	<#list businessLogicLst as item>
		<#if businesItem.screenId?has_content && item.screenId?has_content && item.returnType == 1 && businesItem.screenId == item.screenId>
		${item.businessLogicCode?cap_first }InputBean ${item.businessLogicCode?uncap_first }InputBean = new ${item.businessLogicCode?cap_first }InputBean();
		${item.businessLogicCode?cap_first }InputForm ${item.businessLogicCode?uncap_first }InputForm = beanMapper.map(${item.businessLogicCode }InputBean, ${item.businessLogicCode?cap_first }InputForm.class);
		model.addAttribute("${item.businessLogicCode?uncap_first }InputForm", ${item.businessLogicCode?uncap_first }InputForm);
		</#if>
	</#list>
</#macro>

<#function getScreenCode businesItem businessLogicLst>
	<#local screenCodeStr = "">
	<#list businessLogicLst as item>
		<#switch businesItem.patternType>
			<#case 6 >
				<#if item.screenId?has_content && item.returnType == 0 && item.patternType == 2>
					<#local screenCodeStr = item.screenCode>
				</#if>
				<#break>
			<#case 7 >
				<#if item.screenId?has_content && item.returnType == 0 && item.patternType == 4>
					<#local screenCodeStr = item.screenCode>
				</#if>
				<#break>
		</#switch>
	</#list>

	<#return screenCodeStr>
</#function>
