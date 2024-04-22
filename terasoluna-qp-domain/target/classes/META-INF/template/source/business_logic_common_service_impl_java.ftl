/*
 * @(#)BusinessLogicCommonServiceImpl.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
 <#if isDomain == true>
package ${package}.domain.service.common;
<#else>
package ${package}.batch.service.common;
</#if>

import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;

<#if isDomain == true>
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.SystemException;
import java.text.MessageFormat;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.springframework.mail.SimpleMailMessage;
<#else>
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.terasoluna.fw.batch.exception.BatchException;
import java.text.MessageFormat;
</#if>
<#if isDomain == true && isExistSqlCommon == true>
import ${package}.domain.model.common.*;
<#elseif isDomain == false && isExistSqlCommon == true>
import ${package}.batch.model.common.*;
</#if>

<#if isDomain == true>
<#list businessLogic as item>
import ${package}.domain.service.common.${item.businessLogicCode?cap_first}InputBean;
import ${package}.domain.service.common.${item.businessLogicCode?cap_first}OutputBean;
</#list>
<#if isExistAdvancedCommon == true>
import ${package}.domain.service.common.advancecomponentbean.*;
</#if>
<#else>
<#list businessLogic as item>
import ${package}.batch.service.common.${item.businessLogicCode?cap_first}InputBean;
import ${package}.batch.service.common.${item.businessLogicCode?cap_first}OutputBean;
</#list>
<#if isExistAdvancedCommon == true>
import ${package}.batch.service.common.advancecomponentbean.*;
</#if>
</#if>

/**
 * ${project.remark!""}
 *
 * @author ${project.createdByName!""}
 * @version $Revision$
 */
@Service
@Transactional
public class BusinessLogicCommonServiceImpl implements BusinessLogicCommonService {

	private static final Logger log = LoggerFactory.getLogger(BusinessLogicCommonServiceImpl.class);

	<#if isExistSqlCommon == true>
	/**
	 * CommonRepository
	 */
	<#if isDomain == true>
	@Inject
	${package}.domain.repository.common.CommonRepository commonRepository;
	<#else>
	@Inject
	${package}.batch.repository.common.CommonRepository commonRepository;
	</#if>
	</#if>

	<#list lstBlogicCustomize as item>
	/**
	 * ${item.businessLogicName!""}
	 */
	<#if isDomain == true>
	@Inject
	${package}.domain.service.commoncustomize.${item.businessLogicCode?cap_first }Service ${item.businessLogicCode?uncap_first }Service;
	<#else>
	@Inject
	${package}.batch.service.commoncustomize.${item.businessLogicCode?cap_first }Service ${item.businessLogicCode?uncap_first }Service;
	</#if>
	</#list>

	<#if isDomain == true && isExistDecision == true>
	@Inject
	${package}.domain.decision.DecisionLogicDesignCommonService decisionLogicDesignCommonService;
	<#elseif isDomain == false && isExistDecision == true>
	@Inject
	${package}.batch.decision.DecisionLogicDesignCommonService decisionLogicDesignCommonService;
	</#if>

	<#if isDomain == true>
	@Inject
	org.springframework.mail.javamail.JavaMailSender mailSender;
	
	@Inject
	org.springframework.mail.SimpleMailMessage templateMessage;
	
	private SimpleMailMessage message = null;
	</#if>
	
	<#list businessLogic as item>
	/**
     * ${item.businessLogicName?cap_first}
     *
     * @return ${item.businessLogicCode?cap_first}OutputBean
     */
	public ${item.businessLogicCode?cap_first}OutputBean ${item.businessLogicCode?uncap_first}(${item.businessLogicCode?cap_first}InputBean in) {
		${item.businessLogicCode?cap_first}OutputBean ou = new ${item.businessLogicCode?cap_first}OutputBean();

		try {
			<#if item.strDetailsOfServiceImp?has_content>
			${item.strDetailsOfServiceImp}
			</#if>
		<#if isDomain == true>
		} catch (BusinessException be) {
			throw be;
		} catch (Exception e) {
			throw new SystemException("", e);
		}
		<#else>
		} catch (Exception e) {
			throw new BatchException(e);
		}
		</#if>


		return ou;
	}
	
	<#if item.strMethodOfAdvance?has_content>
		${item.strMethodOfAdvance}
	</#if>
	</#list>
}