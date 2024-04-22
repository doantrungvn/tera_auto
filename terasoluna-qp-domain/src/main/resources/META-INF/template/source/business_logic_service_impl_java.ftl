/*
 * @(#)${module.moduleCode?cap_first }ServiceImpl.java
 *
 * Copyright (c) NTTDATA Vietnam.
 */
package ${package}.domain.service.${module.moduleCode?lower_case };

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.FunctionMasterUtils;
<#if isExistSqlCommon == true>
import ${package}.domain.model.common.*;
</#if>
<#if isExistBlogicCommon == true>
import ${package}.domain.service.common.*;
</#if>
<#if isExistBlogicCommonCustomize == true>
import ${package}.domain.service.commoncustomize.*;
</#if>
import java.text.MessageFormat;
<#if isExistSql == true>
import ${package}.domain.repository.${module.moduleCode?lower_case }.${module.moduleCode?cap_first }Repository;
import ${package}.domain.model.${module.moduleCode?lower_case }.*;
import ${package}.domain.model.*;
</#if>
<#if isExistAdvancedNormal == true>
import ${package}.domain.service.${module.moduleCode?lower_case}.advancecomponentbean.*;
</#if>

<#if isExistBlogicCommon == true>
//import ${package}.domain.service.common.BusinessLogicCommonService;
</#if>
import org.terasoluna.qp.app.message.CommonMessageConst;

/**
 * ${module.remark!""}
 *
 * @author ${module.author!""}
 * @version $Revision$
 */
@Service
@Transactional
public class ${module.moduleCode?cap_first }ServiceImpl implements ${module.moduleCode?cap_first }Service {
	
	private static final Logger log = LoggerFactory.getLogger(${module.moduleCode?cap_first }ServiceImpl.class);
	
	/**
     * 
     *
     */
	<#if isExistSql == true>
	@Inject
	${module.moduleCode?cap_first }Repository ${module.moduleCode?uncap_first }Repository;
	</#if>
	
	/**
     *
     *
     */
	<#if isExistSqlCommon == true>
	@Inject
	${package}.domain.repository.common.CommonRepository commonRepository;
	</#if>

	<#if isExistDecision == true>
	@Inject
	${package}.domain.decision.DecisionLogicDesignCommonService decisionLogicDesignCommonService;
	</#if>
	
	/**
     * 
     *
     */
	<#if isExistSql == true>
	@Inject
	${package}.domain.service.common.BusinessLogicCommonService businessLogicCommonService;
	</#if>

	<#list lstBlogicCustomize as item>
	/**
     * ${item.businessLogicName?uncap_first }
     *
     */
	@Inject
	${item.businessLogicCode?cap_first }Service ${item.businessLogicCode?uncap_first }Service;
	</#list>

	@Inject
	org.springframework.mail.javamail.JavaMailSender mailSender;
	
	@Inject
	org.springframework.mail.SimpleMailMessage templateMessage;
	
	private SimpleMailMessage message = null;
	
<#list businessLogicLst as item>	
	<#if item.patternType?has_content && item.patternType == 1>
		<#if item.lstOutputBean?has_content>
			<#if item.remark?has_content>
	/**
	* ${item.remark }
	*/
					</#if>
	@Override
	public java.util.Map<Integer, Object> ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputBean in, Pageable pageable) {
		ResultMessages lstInfoMessages = ResultMessages.info();
		ResultMessages lstErrorMessages = ResultMessages.error();
		ResultMessages lstWarningMessages = ResultMessages.warning();
		java.util.Map<Integer, Object> map = new java.util.HashMap<Integer, Object>();
		${item.businessLogicCode?cap_first }OutputBean ou = new ${item.businessLogicCode?cap_first }OutputBean();
		Long totalCount = 0L;

			<#-- Switch by Data type -->
			<#if item.bdDeclarFirstPage?has_content>
		${item.bdDeclarFirstPage}
			</#if>

		try {
			<#if item.strDetailsOfServiceImp?has_content>
		${item.strDetailsOfServiceImp}
			</#if>
			
			<#-- Switch by Data type -->
			<#if item.bdDeclarLastPage?has_content>
		${item.bdDeclarLastPage}	
			</#if>
			<#-- End switch by Data type -->

		} catch (BusinessException be) {
			throw be;
		} catch (Exception e) {
			throw new SystemException("", e);
		}
		
		map.put(0, ou);
		<#if item.bdDeclarMapPage?has_content>
		${item.bdDeclarMapPage}
		</#if>

		return map;
	}
		<#else>
				<#if item.remark?has_content>
	/**
	* ${item.remark }
	*/
				</#if>
	@Override
	public java.util.Map<Integer, Object> ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputBean in, Pageable pageable) {
		ResultMessages lstInfoMessages = ResultMessages.info();
		ResultMessages lstErrorMessages = ResultMessages.error();
		ResultMessages lstWarningMessages = ResultMessages.warning();
		${item.businessLogicCode?cap_first }OutputBean ou = new ${item.businessLogicCode?cap_first }OutputBean();
		java.util.Map<Integer, Object> map = new java.util.HashMap<Integer, Object>();

		try {
		
		
		} catch (BusinessException be) {
			throw be;
		} catch (Exception e) {
			throw new SystemException("", e);
		}

		map.put(0, ou);
		map.put(1, null);

		return map;
	}
		</#if>
	<#else>
	<#if item.remark?has_content>
	/**
	* ${item.remark }
	*/
	</#if>
	@Override
	public ${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputBean in){
		ResultMessages lstInfoMessages = ResultMessages.info();
		ResultMessages lstErrorMessages = ResultMessages.error();
		ResultMessages lstWarningMessages = ResultMessages.warning();
		${item.businessLogicCode?cap_first }OutputBean ou = new ${item.businessLogicCode?cap_first }OutputBean();
			
		try {	
			<#if item.strDetailsOfServiceImp?has_content>
		${item.strDetailsOfServiceImp}
			</#if>
		
		} catch (BusinessException be) {
			throw be;
		} catch (Exception e) {
			throw new SystemException("", e);
		}
		
		return ou;
	}
	</#if>
	
	<#if item.strMethodOfAdvance?has_content>
		${item.strMethodOfAdvance}
	</#if>
	
</#list>
}
