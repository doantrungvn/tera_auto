/*
 * @(#)${item.businessLogicCode?cap_first }BLogic.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
package ${package}.batch.${module.moduleCode?lower_case}.${item.businessLogicCode?lower_case};

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.batch.util.BatchUtil;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.file.FileCollector;
import jp.terasoluna.fw.batch.blogic.BLogic;
import jp.terasoluna.fw.collector.util.CollectorUtility;
import jp.terasoluna.fw.file.dao.FileLineWriter;
import jp.terasoluna.fw.file.dao.FileQueryDAO;
import jp.terasoluna.fw.file.dao.FileUpdateDAO;
import jp.terasoluna.fw.file.util.FileControl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.validation.Validator;

<#if isExistSql == true>
import ${package}.batch.repository.${module.moduleCode?lower_case }.${module.moduleCode?cap_first }Repository;
import ${package}.batch.${module.moduleCode?lower_case }.*;
import ${package}.batch.model.${module.moduleCode?lower_case }.*;
</#if>
<#if isExistSqlCommon == true>
import ${package}.batch.model.common.*;
</#if>

<#if isExistObjObjDef == true>
import ${package}.batch.${module.moduleCode?lower_case }.${item.businessLogicCode?lower_case}objectdefinition.*;
</#if>
<#if isExistAdvancedNormal == true>
import ${package}.batch.service.${module.moduleCode?lower_case}.advancecomponentbean.*;
</#if>

/**
 * ${module.remark!""}
 *
 * @author ${module.author!""}
 * @version $Revision$
 */
@Component
public class ${item.businessLogicCode?cap_first }BLogic implements BLogic {
	
	private static final Logger log = LoggerFactory.getLogger(${item.businessLogicCode?cap_first }BLogic.class);
	
	private static final int BATCH_NORMAL_END = 0;
	
	<#if isExistSqlCommon == true>
	@Inject
	${package}.batch.repository.common.CommonRepository commonRepository;
	</#if>

	 <#if isExistSql == true>
	/**
	 * ${module.moduleCode?cap_first }Repository
	 */
	@Inject
	${module.moduleCode?cap_first }Repository ${module.moduleCode?uncap_first }Repository;
	</#if>
	
	/**
	 * BusinessLogicCommonService
	 */
	@Inject
	${package}.batch.service.common.BusinessLogicCommonService businessLogicCommonService;

	<#if isExistDecision == true>
	@Inject
	${package}.batch.decision.DecisionLogicDesignCommonService decisionLogicDesignCommonService;
	</#if>
	
	/**
	 * FileUpdateDAO
	 */
	@Inject
	@Named("csvFileUpdateDAO")
	FileUpdateDAO csvFileUpdateDAO;

	/**
	 * FileQueryDAO
	 */
	@Inject
	@Named ("csvFileQueryDAO")
	FileQueryDAO csvFileQueryDAO;

	@Inject
	Validator validator;

	/**
	 * PlatformTransactionManager
	 */
	@Inject
	PlatformTransactionManager transactionManager;

	/**
	 * FileControl
	 */
	@Inject
	FileControl fileControl;

	@Inject
	org.springframework.mail.javamail.JavaMailSender mailSender;
	
	@Inject
	org.springframework.mail.SimpleMailMessage templateMessage;
	
	private org.springframework.mail.SimpleMailMessage message = null;
	
	/**
	 * Execute batch
	 *
	 * @return status
	 */
	public int execute(BLogicParam param) {

		try {
		<#if item.lstInputBean?size gt 0>
			/** Build setter/getter for for bath argument */
		</#if>

    	<#if parseInput?has_content>
			${parseInput}
		</#if>
    	<#if item.strDetailsOfServiceImp?has_content>
			${item.strDetailsOfServiceImp}
		</#if>

		if (log.isDebugEnabled()) {
			log.debug("end:${item.businessLogicCode?cap_first }BLogic");
		}

		} catch (BatchException e) {
			throw e;
		} catch (Exception e) {
 			throw new BatchException(e);
		}

		return BATCH_NORMAL_END;
	}

	<#if item.strMethodOfAdvance?has_content>
		${item.strMethodOfAdvance}
	</#if>

	public static void main(String[] args) {
		System.out.println();
	}
}