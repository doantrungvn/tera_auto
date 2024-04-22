/*
 * @(#)DecisionLogicDesignCommonService.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
package ${package}.${place};

import java.io.Serializable;
import java.util.List;
import java.text.ParseException;

import org.springframework.stereotype.Service;
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
public interface DecisionLogicDesignCommonService {
	
	<#list lstDecision as item>
	/**
     * ${item.decisionTbName!""}
     *
     */
	${item.decisionTbName?cap_first}OutputBean ${item.decisionTbCode?uncap_first}(${item.decisionTbName?cap_first}InputBean ${item.decisionTbCode?uncap_first}InputBean) throws ParseException;
	
	</#list>
}