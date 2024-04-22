/*
 * @(#)${parentCode?cap_first}.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
<#assign dataType_h = {"1":"Byte","2":"Short","3":"Integer","4":"Long","5":"Float","6":"Double","7":"Character","8":"Boolean","9":"String","10":"java.math.BigDecimal","11":"java.sql.Timestamp","12":"java.sql.Timestamp","13":"java.sql.Time","15":"java.sql.Date"}>
<#if moduleCode?has_content>
package ${projectCode?lower_case}.batch.model.${moduleCode?lower_case}.${parentCode?lower_case}outputbean;
<#else>
package ${projectCode?lower_case}.batch.model.common.${parentCode?lower_case}outputbean;
</#if>

<#if moduleCode?has_content>
import ${projectCode?lower_case}.batch.model.${moduleCode?lower_case}.*;
<#else>
import ${projectCode?lower_case}.batch.model.common.*;
</#if>

/**
 * 
 *
 * @author 
 * @version $Revision$
 */
public class ${objectCode?cap_first} {
<#list sqlDesignOutputs as sqlDesignOutput>
	<#if (sqlDesignOutput.designType)?? && sqlDesignOutput.designType != 1>
	/**
	 * ${sqlDesignOutput.sqlDesignOutputName!""}
	 */
		<#if sqlDesignOutput.dataType != 0 && sqlDesignOutput.dataType != 14 && sqlDesignOutput.dataType != 16 && sqlDesignOutput.dataType != 17>
	private ${dataType_h[sqlDesignOutput.dataType?string]} ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
		<#elseif sqlDesignOutput.dataType == 0>
	private ${sqlDesignOutput.sqlDesignOutputCode?cap_first} ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};	
		<#elseif sqlDesignOutput.dataType == 14>
	private ${projectCode?lower_case}.batch.model.<#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
		<#elseif sqlDesignOutput.dataType == 16>
	private ${projectCode?lower_case}.batch.commonobject.<#if (sqlDesignOutput.moduleCode)??>${sqlDesignOutput.moduleCode?lower_case}.</#if><#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
		<#elseif sqlDesignOutput.dataType == 17>
	private <#if packageName??>${packageName?lower_case+"."}</#if><#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};	
		</#if>
	</#if>
</#list>
<#list sqlDesignOutputs as sqlDesignOutput>
	<#if (sqlDesignOutput.designType)?? && sqlDesignOutput.designType != 1>
	<#if sqlDesignOutput.dataType != 0 && sqlDesignOutput.dataType != 14>
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
	<#elseif sqlDesignOutput.dataType == 0>
	/**
     * Get ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @return ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public ${sqlDesignOutput.sqlDesignOutputCode?cap_first} get${sqlDesignOutput.sqlDesignOutputCode?cap_first}(){
		return this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @param ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public void set${sqlDesignOutput.sqlDesignOutputCode?cap_first}(${sqlDesignOutput.sqlDesignOutputCode?cap_first} ${sqlDesignOutput.sqlDesignOutputCode?uncap_first}){
		this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first} = ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}	
	<#elseif sqlDesignOutput.dataType == 14>
	/**
     * Get ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @return ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public ${projectCode?lower_case}.batch.model.<#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> get${sqlDesignOutput.sqlDesignOutputCode?cap_first}(){
		return this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @param ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public void set${sqlDesignOutput.sqlDesignOutputCode?cap_first}(${projectCode?lower_case}.batch.model.<#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> ${sqlDesignOutput.sqlDesignOutputCode?uncap_first}){
		this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first} = ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
	<#elseif sqlDesignOutput.dataType == 16>
	/**
     * Get ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @return ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public ${projectCode?lower_case}.batch.commonobject.<#if (sqlDesignOutput.moduleCode)??>${sqlDesignOutput.moduleCode?lower_case}.</#if><#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> get${sqlDesignOutput.sqlDesignOutputCode?cap_first}(){
		return this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @param ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public void set${sqlDesignOutput.sqlDesignOutputCode?cap_first}(${projectCode?lower_case}.batch.commonobject.<#if (sqlDesignOutput.moduleCode)??>${sqlDesignOutput.moduleCode?lower_case}.</#if><#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> ${sqlDesignOutput.sqlDesignOutputCode?uncap_first}){
		this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first} = ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
	<#elseif sqlDesignOutput.dataType == 17>
	/**
     * Get ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @return ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public <#if packageName??>${packageName?lower_case+"."}</#if><#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> get${sqlDesignOutput.sqlDesignOutputCode?cap_first}(){
		return this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignOutput.sqlDesignOutputName!""}
     *
     * @param ${sqlDesignOutput.sqlDesignOutputName!""}
     */
	public void set${sqlDesignOutput.sqlDesignOutputCode?cap_first}(<#if packageName??>${packageName?lower_case+"."}</#if><#if (sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.objectTypeCode?cap_first}</#if><#if !(sqlDesignOutput.objectTypeCode)??>${sqlDesignOutput.sqlDesignOutputName?capitalize?replace("","")}</#if> ${sqlDesignOutput.sqlDesignOutputCode?uncap_first}){
		this.${sqlDesignOutput.sqlDesignOutputCode?uncap_first} = ${sqlDesignOutput.sqlDesignOutputCode?uncap_first};
	}
	</#if>
	</#if>
</#list>
}
