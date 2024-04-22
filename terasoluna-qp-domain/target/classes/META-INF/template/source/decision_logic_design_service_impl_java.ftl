/*
 * @(#)DecisionLogicDesignCommonServiceImpl.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
package ${package}.${place};

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.text.ParseException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.qp.app.common.ultils.FunctionMasterUtils;
<#if place?string == "domain.decision">
import org.terasoluna.qp.app.common.ultils.DateUtils;
</#if>
<#list lstDecision as item>
	<#if item.moduleCode?string != "">
import ${package}.${place}.${item.moduleCode?lower_case }.${item.decisionTbName?cap_first}InputBean;
import ${package}.${place}.${item.moduleCode?lower_case }.${item.decisionTbName?cap_first}OutputBean;
	</#if>
</#list>

/**
 * 
 *
 * @author 
 * @version $Revision$
 */
@Service
@Transactional
public class DecisionLogicDesignCommonServiceImpl implements DecisionLogicDesignCommonService {
	
	<#list lstDecision as item>
	/**
     * Get ${item.decisionTbName!""}
     *
     * @return ${item.decisionTbName?cap_first}OutputBean
     */
	public ${item.decisionTbName?cap_first}OutputBean ${item.decisionTbCode?uncap_first}(${item.decisionTbName?cap_first}InputBean in) throws ParseException {
		${item.decisionTbName?cap_first}OutputBean ou = new ${item.decisionTbName?cap_first}OutputBean();
		<#if item.blogicDesignContent?has_content>
		${item.blogicDesignContent}
		</#if>

		return ou;
	}
	
	</#list>
}