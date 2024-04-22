/*
 * @${businessLogic.businessLogicCode?cap_first}Service.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
<#if isDomain == true>
package ${package}.domain.service.commoncustomize;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import ${package}.domain.service.commoncustomize.${businessLogic.businessLogicCode?cap_first}InputBean;
import ${package}.domain.service.commoncustomize.${businessLogic.businessLogicCode?cap_first}OutputBean;
<#else>
package ${package}.batch.service.commoncustomize;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import ${package}.batch.service.commoncustomize.${businessLogic.businessLogicCode?cap_first}InputBean;
import ${package}.batch.service.commoncustomize.${businessLogic.businessLogicCode?cap_first}OutputBean;
</#if>

/**
 * ${project.remark!""}
 *
 * @author ${project.createdByName!""}
 * @version $Revision$
 */
@Service
public interface ${businessLogic.businessLogicCode?cap_first}Service {
	
	/**
     * ${businessLogic.businessLogicName?uncap_first }
     *
     * @return ${businessLogic.businessLogicCode?cap_first}OutputBean
     */
	${businessLogic.businessLogicCode?cap_first}OutputBean ${businessLogic.businessLogicCode?uncap_first }(${businessLogic.businessLogicCode?cap_first}InputBean ${businessLogic.businessLogicCode?uncap_first}InputBean);
	
}