/*
 * @(#)${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first + "OutputBean"}.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
<#assign dataType_h = {"1":"byte[]","2":"Short","3":"Integer","4":"Long","5":"Float","6":"Double","7":"Character","8":"Boolean","9":"String","10":"java.math.BigDecimal","11":"java.sql.Timestamp","12":"java.sql.Timestamp","13":"java.sql.Time","15":"java.sql.Date"}>
<#if moduleCode?has_content>
package ${projectCode?lower_case}.domain.model.${moduleCode?lower_case};
<#else>
package ${projectCode?lower_case}.domain.model.common;
</#if>

<#if moduleCode?has_content>
import ${projectCode?lower_case}.domain.model.${moduleCode?lower_case}.*;
<#else>
import ${projectCode?lower_case}.domain.model.common.*;
</#if>

/**
 * ${sqlDesignCompound.sqlDesign.remark!""}
 *
 * @author ${sqlDesignCompound.sqlDesign.author!""}
 * @version $Revision$
 */
public class ${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first + "OutputBean"} {
<#list sqlDesignCompound.sqlDesignOutputs as sqlDesignOutput>
	<#if (sqlDesignOutput.designType)?? && sqlDesignOutput.designType != 1>
	/**
	 * ${sqlDesignOutput.sqlDesignOutputName!""}
	 */
	<#if sqlDesignOutput.dataType != 0 && sqlDesignOutput.dataType != 14 && sqlDesignOutput.dataType != 16 && sqlDesignOutput.dataType != 17 && !(sqlDesignOutput.sqlDesignOutputParentId?has_content)>
	private ${dataType_h[sqlDesignOutput.dataType?string]} ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	<#elseif sqlDesignOutput.dataType == 0 && !(sqlDesignOutput.sqlDesignOutputParentId?has_content)>
	     <#if sqlDesignOutput.designType == 0>
	     	<#if !moduleCode?? || moduleCode == ''>
	     		<#assign object_path = "${projectCode?lower_case}.domain.model.common.${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}OutputBean">
			<#else>
				<#assign object_path = "${projectCode?lower_case}.domain.model.${moduleCode?lower_case}.${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}OutputBean">
			</#if>
        <#else>
        	<#if !moduleCode?? || moduleCode == ''>
        		<#assign object_path = "${projectCode?lower_case}.domain.model.common">
        	<#else>
        		<#assign object_path = "${projectCode?lower_case}.domain.model.${moduleCode?lower_case}">
        	</#if>
        </#if>
	private ${object_path?lower_case}.${sqlDesignOutput.sqlDesignOutputCode?cap_first} ${sqlDesignOutput.sqlDesignOutputCode?uncap_first} = new ${object_path?lower_case}.${sqlDesignOutput.sqlDesignOutputCode?cap_first}();
	<#elseif sqlDesignOutput.dataType == 14 && !(sqlDesignOutput.sqlDesignOutputParentId?has_content)>
	private ${projectCode?lower_case}.domain.model.<#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	<#elseif sqlDesignOutput.dataType == 16 && !(sqlDesignOutput.sqlDesignOutputParentId?has_content)>
	private ${projectCode?lower_case}.domain.commonobject.<#if (sqlDesignOutput.moduleCode)??>${sqlDesignOutput.moduleCode?lower_case}.</#if><#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};	
	<#elseif sqlDesignOutput.dataType == 17 && !(sqlDesignOutput.sqlDesignOutputParentId?has_content)>
	private <#if sqlDesignOutput.packageName??>${sqlDesignOutput.packageName?lower_case+"."}</#if><#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	</#if>
	</#if>
</#list>

<#list sqlDesignCompound.sqlDesignOutputs as sqlDesignOutput>
	<#if (sqlDesignOutput.designType)?? && sqlDesignOutput.designType != 1>
	<#if sqlDesignOutput.dataType != 0 && sqlDesignOutput.dataType != 14  && sqlDesignOutput.dataType != 16  && sqlDesignOutput.dataType != 17 && !(sqlDesignOutput.sqlDesignOutputParentId?has_content)>
	/**
     * Get ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @return ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public ${dataType_h[sqlDesignOutput.dataType?string]} get${sqlDesignOutput.sqlDesignOutputCode?cap_first}(){
		return this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}	
		
	/**
     * Set ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @param ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public void set${sqlDesignOutput.sqlDesignOutputCode?cap_first}(${dataType_h[sqlDesignOutput.dataType?string]} ${sqlDesignOutput.sqlDesignOutputCode?uncap_first}){
		this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first} = ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
	<#elseif sqlDesignOutput.dataType == 0 && !(sqlDesignOutput.sqlDesignOutputParentId?has_content)>
	     <#if sqlDesignOutput.designType == 0>
	     	<#if !moduleCode?? || moduleCode == ''>
	     		<#assign object_path = "${projectCode?lower_case}.domain.model.common.${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}OutputBean">
	     	<#else>
	     		<#assign object_path = "${projectCode?lower_case}.domain.model.${moduleCode?lower_case}.${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}OutputBean">
			</#if>
        <#else>
        	<#if !moduleCode?? || moduleCode == ''>
	     		 <#assign object_path = "${projectCode?lower_case}.domain.model.common">
	     	<#else>
	     		 <#assign object_path = "${projectCode?lower_case}.domain.model.${moduleCode?lower_case}">
			</#if>
        </#if>
	
	/**
     * Get ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @return ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public ${object_path?lower_case}.${sqlDesignOutput.sqlDesignOutputCode?cap_first} get${sqlDesignOutput.sqlDesignOutputCode?cap_first}(){
		return this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @param ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public void set${sqlDesignOutput.sqlDesignOutputCode?cap_first}(${object_path?lower_case}.${sqlDesignOutput.sqlDesignOutputCode?cap_first} ${sqlDesignOutput.sqlDesignOutputCode?uncap_first}){
		this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first} = ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}	
	<#elseif sqlDesignOutput.dataType == 14 && !(sqlDesignOutput.sqlDesignOutputParentId?has_content)>
	/**
     * Get ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @return ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public ${projectCode?lower_case}.domain.model.<#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> get${sqlDesignOutput.sqlDesignOutputCode?cap_first}(){
		return this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @param ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public void set${sqlDesignOutput.sqlDesignOutputCode?cap_first}(${projectCode?lower_case}.domain.model.<#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> ${sqlDesignOutput.sqlDesignOutputCode?uncap_first}){
		this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first} = ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
	<#elseif sqlDesignOutput.dataType == 16 && !(sqlDesignOutput.sqlDesignOutputParentId?has_content)>
	/**
     * Get ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @return ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public ${projectCode?lower_case}.domain.commonobject.<#if (sqlDesignOutput.moduleCode)??>${sqlDesignOutput.moduleCode?lower_case}.</#if><#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> get${sqlDesignOutput.sqlDesignOutputCode?cap_first}(){
		return this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @param ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public void set${sqlDesignOutput.sqlDesignOutputCode?cap_first}(${projectCode?lower_case}.domain.commonobject.<#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> ${sqlDesignOutput.sqlDesignOutputCode?uncap_first}){
		this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first} = ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
	<#elseif sqlDesignOutput.dataType == 17 && !(sqlDesignOutput.sqlDesignOutputParentId?has_content)>
	/**
     * Get ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @return ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public <#if sqlDesignOutput.packageName??>${sqlDesignOutput.packageName?lower_case+"."}</#if><#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> get${sqlDesignOutput.sqlDesignOutputCode?cap_first}(){
		return this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @param ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public void set${sqlDesignOutput.sqlDesignOutputCode?cap_first}(<#if sqlDesignOutput.packageName??>${sqlDesignOutput.packageName?lower_case+"."}</#if><#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> ${sqlDesignOutput.sqlDesignOutputCode?uncap_first}){
		this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first} = ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
	</#if>
	</#if>
</#list>
}
