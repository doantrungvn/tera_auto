/*
 * @(#)BusinessLogicCommonService.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
<#if isDomain == true>
package ${package}.domain.service.common;
<#else>
package ${package}.batch.service.common;
</#if>

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
<#list businessLogic as item>
<#if isDomain == true>
import ${package}.domain.service.common.${item.businessLogicCode?cap_first}InputBean;
import ${package}.domain.service.common.${item.businessLogicCode?cap_first}OutputBean;
<#else>
import ${package}.batch.service.common.${item.businessLogicCode?cap_first}InputBean;
import ${package}.batch.service.common.${item.businessLogicCode?cap_first}OutputBean;
</#if>
</#list>

/**
 * ${project.remark!""}
 *
 * @author ${project.createdByName!""}
 * @version $Revision$
 */
@Service
public interface BusinessLogicCommonService {
	
	<#list businessLogic as item>
	
	/**
     * Get ${item.businessLogicName!""}
     *
     * @return ${item.businessLogicCode?cap_first}OutputBean
     */
	${item.businessLogicCode?cap_first}OutputBean ${item.businessLogicCode?uncap_first}(${item.businessLogicCode?cap_first}InputBean ${item.businessLogicCode?uncap_first}InputBean);
	
	</#list>
}