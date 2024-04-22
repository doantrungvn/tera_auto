/*
 * @(#)${module.moduleCode?cap_first }Service.java
 *
 * Copyright (c) NTTDATA Vietnam.
 */
package ${package}.domain.service.${module.moduleCode?lower_case };

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * ${module.remark!""}
 *
 * @author ${module.author!""}
 * @version $Revision$
 */
@Service
public interface ${module.moduleCode?cap_first }Service {
	
<#list businessLogicLst as item>
	<#if item.patternType?has_content && item.patternType == 1>
		<#if item.lstOutputBean?has_content>
			<#list item.lstOutputBean as output>
				<#if output.parentOutputBeanId?has_content == false && output.dataType?has_content && (output.dataType == 0 || output.dataType == 14 || output.dataType == 16 || output.dataType == 17) >
	/**
     * Get ${item.businessLogicName!""}
     *
     * @return java.util.Map<Integer, Object>
     */
	java.util.Map<Integer, Object> ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputBean in, Pageable pageable);
					<#break>
				</#if>
			</#list>
		<#else>
	/**
     * Get ${item.businessLogicName!""}
     *
     * @return java.util.Map<Integer, Object>
     */
	java.util.Map<Integer, Object> ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputBean in, Pageable pageable);	
		</#if>
	<#else>
	/**
     * Get ${item.businessLogicName!""}
     *
     * @return ${item.businessLogicCode?cap_first}OutputBean
     */
	${item.businessLogicCode?cap_first }OutputBean ${item.businessLogicCode?uncap_first }(${item.businessLogicCode?cap_first }InputBean in);
	</#if>
	
</#list>
}
