/*
 * @(#)${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first + "InputBean"}.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
<#assign dataType_h = {"1":"Byte","2":"Short","3":"Integer","4":"Long","5":"Float","6":"Double","7":"Character","8":"Boolean","9":"String","10":"java.math.BigDecimal","11":"java.sql.Timestamp","12":"java.sql.Timestamp","13":"java.sql.Time","15":"java.sql.Date"}>
<#if moduleCode?has_content>
package ${projectCode?lower_case}.batch.model.${moduleCode?lower_case};
<#else>
package ${projectCode?lower_case}.batch.model.common;
</#if>

/**
 * ${sqlDesignCompound.sqlDesign.remark!""}
 *
 * @author ${sqlDesignCompound.sqlDesign.author!""}
 * @version $Revision$
 */
public class ${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first + "InputBean"} {
<#list sqlDesignCompound.sqlDesignInputs as sqlDesignInput>
	<#if (sqlDesignInput.designType)?? && sqlDesignInput.designType != 1>
	/**
	 * ${sqlDesignInput.sqlDesignInputName!""}
	 */
	<#if sqlDesignInput.dataType != 0 && sqlDesignInput.dataType != 14 && sqlDesignInput.dataType != 16 &&sqlDesignInput.dataType != 17 && !(sqlDesignInput.sqlDesignInputParentId?has_content)>
		<#if sqlDesignInput.arrayFlag==1>
			<#if sqlDesignInput.dataType == 1>
	private byte[] ${sqlDesignInput.sqlDesignInputCode?uncap_first};
			<#else>
	private java.util.List<${dataType_h[sqlDesignInput.dataType?string]}> ${sqlDesignInput.sqlDesignInputCode?uncap_first};
			</#if>
		<#else>
	private ${dataType_h[sqlDesignInput.dataType?string]} ${sqlDesignInput.sqlDesignInputCode?uncap_first};	
		</#if>
	<#elseif sqlDesignInput.dataType == 0 && !(sqlDesignInput.sqlDesignInputParentId?has_content)>
    	<#if sqlDesignInput.designType == 0>
    		<#if !moduleCode?? || moduleCode == ''>
    			<#assign object_path = "${projectCode?lower_case}.batch.model.common.${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}InputBean">
			<#else>
				<#assign object_path = "${projectCode?lower_case}.batch.model.${moduleCode?lower_case}.${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}InputBean">
			</#if>
    	<#else>
    		<#if !moduleCode?? || moduleCode == ''>
    			<#assign object_path = "${projectCode?lower_case}.batch.model.common">
    		<#else>
    			<#assign object_path = "${projectCode?lower_case}.batch.model.${moduleCode?lower_case}">
    		</#if>
    	</#if>
		<#if sqlDesignInput.arrayFlag==1>
	private java.util.List<${object_path?lower_case}.${sqlDesignInput.sqlDesignInputCode?cap_first}> ${sqlDesignInput.sqlDesignInputCode?uncap_first};	
		<#else>
	private ${object_path?lower_case}.${sqlDesignInput.sqlDesignInputCode?cap_first} ${sqlDesignInput.sqlDesignInputCode?uncap_first} = new ${object_path?lower_case}.${sqlDesignInput.sqlDesignInputCode?cap_first}();	
		</#if>
	<#elseif sqlDesignInput.dataType == 14 && !(sqlDesignInput.sqlDesignInputParentId?has_content)>
		<#if sqlDesignInput.arrayFlag==1>
			<#if sqlDesignInput.packageName?? && sqlDesignInput.sqlDesignInputCode == "conditions">
	private java.util.List<${sqlDesignInput.packageName}.ConditionModel> conditions;
			<#else>
	private java.util.List<${projectCode?lower_case}.batch.model.<#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if>> ${sqlDesignInput.sqlDesignInputCode?uncap_first};				
			</#if>
		<#else>
	private ${projectCode?lower_case}.batch.model.<#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if> ${sqlDesignInput.sqlDesignInputCode?uncap_first};	
		</#if>
	<#elseif sqlDesignInput.dataType == 16 && !(sqlDesignInput.sqlDesignInputParentId?has_content)>
		<#if sqlDesignInput.arrayFlag==1>
	private java.util.List<${projectCode?lower_case}.batch.commonobject.<#if (sqlDesignInput.moduleCode)??>${sqlDesignInput.moduleCode?lower_case}.</#if><#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if>> ${sqlDesignInput.sqlDesignInputCode?uncap_first};	
		<#else>
	private ${projectCode?lower_case}.batch.commonobject.<#if (sqlDesignInput.moduleCode)??>${sqlDesignInput.moduleCode?lower_case}.</#if><#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if> ${sqlDesignInput.sqlDesignInputCode?uncap_first};	
		</#if>
	<#elseif sqlDesignInput.dataType == 17 && !(sqlDesignInput.sqlDesignInputParentId?has_content)>
		<#if sqlDesignInput.arrayFlag==1>
	private java.util.List<<#if sqlDesignInput.pakageExternal??>${sqlDesignInput.pakageExternal?lower_case+"."}</#if><#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if>> ${sqlDesignInput.sqlDesignInputCode?uncap_first};	
		<#else>
	private <#if sqlDesignInput.pakageExternal??>${sqlDesignInput.pakageExternal?lower_case+"."}</#if><#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if> ${sqlDesignInput.sqlDesignInputCode?uncap_first};	
		</#if>
	</#if>
	</#if>
</#list>

<#list sqlDesignCompound.sqlDesignInputs as sqlDesignInput>	
	<#if (sqlDesignInput.designType)?? && sqlDesignInput.designType != 1>
	<#if sqlDesignInput.dataType != 0 && sqlDesignInput.dataType != 14 && sqlDesignInput.dataType != 16 && sqlDesignInput.dataType != 17 && !(sqlDesignInput.sqlDesignInputParentId?has_content)>
		<#if sqlDesignInput.arrayFlag==1>
			<#if sqlDesignInput.dataType == 1>
	/**
     * Get ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @return ${sqlDesignInput.sqlDesignInputName!""}
     */
	public byte[] get${sqlDesignInput.sqlDesignInputCode?cap_first}(){
		return this.${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @param ${sqlDesignInput.sqlDesignInputName!""}
     */
	public void set${sqlDesignInput.sqlDesignInputCode?cap_first}(byte[] ${sqlDesignInput.sqlDesignInputCode?uncap_first}){
		this.${sqlDesignInput.sqlDesignInputCode?uncap_first} = ${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
			<#else>
	/**
     * Get ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @return ${sqlDesignInput.sqlDesignInputName!""}
     */
	public java.util.List<${dataType_h[sqlDesignInput.dataType?string]}> get${sqlDesignInput.sqlDesignInputCode?cap_first}(){
		return this.${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @param ${sqlDesignInput.sqlDesignInputName!""}
     */
	public void set${sqlDesignInput.sqlDesignInputCode?cap_first}(java.util.List<${dataType_h[sqlDesignInput.dataType?string]}> ${sqlDesignInput.sqlDesignInputCode?uncap_first}){
		this.${sqlDesignInput.sqlDesignInputCode?uncap_first} = ${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
			</#if>
		<#else>
	/**
     * Get ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @return ${sqlDesignInput.sqlDesignInputName!""}
     */
	public ${dataType_h[sqlDesignInput.dataType?string]} get${sqlDesignInput.sqlDesignInputCode?cap_first}(){
		return this.${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}	
		
	/**
     * Set ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @param ${sqlDesignInput.sqlDesignInputName!""}
     */
	public void set${sqlDesignInput.sqlDesignInputCode?cap_first}(${dataType_h[sqlDesignInput.dataType?string]} ${sqlDesignInput.sqlDesignInputCode?uncap_first}){
		this.${sqlDesignInput.sqlDesignInputCode?uncap_first} = ${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		</#if>
	<#elseif sqlDesignInput.dataType == 0 && !(sqlDesignInput.sqlDesignInputParentId?has_content)>
	     <#if sqlDesignInput.designType == 0>
	     	<#if !moduleCode?? || moduleCode == ''>
	     		<#assign object_path = "${projectCode?lower_case}.batch.model.common.${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}InputBean">
			<#else>
				<#assign object_path = "${projectCode?lower_case}.batch.model.${moduleCode?lower_case}.${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}InputBean">	
			</#if>
        <#else>
        	<#if !moduleCode?? || moduleCode == ''>
        		<#assign object_path = "${projectCode?lower_case}.batch.model.common">
        	<#else>
        		<#assign object_path = "${projectCode?lower_case}.batch.model.${moduleCode?lower_case}">	
			</#if>
        </#if>
		<#if sqlDesignInput.arrayFlag==1>
	/**
     * Get ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @return ${sqlDesignInput.sqlDesignInputName!""}
     */
	public java.util.List<${object_path?lower_case}.${sqlDesignInput.sqlDesignInputCode?cap_first}> get${sqlDesignInput.sqlDesignInputCode?cap_first}(){
		return this.${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @param ${sqlDesignInput.sqlDesignInputName!""}
     */
	public void set${sqlDesignInput.sqlDesignInputCode?cap_first}(java.util.List<${object_path?lower_case}.${sqlDesignInput.sqlDesignInputCode?cap_first}> ${sqlDesignInput.sqlDesignInputCode?uncap_first}){
		this.${sqlDesignInput.sqlDesignInputCode?uncap_first} = ${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}	
		<#else>
	/**
     * Get ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @return ${sqlDesignInput.sqlDesignInputName!""}
     */
	public ${object_path?lower_case}.${sqlDesignInput.sqlDesignInputCode?cap_first} get${sqlDesignInput.sqlDesignInputCode?cap_first}(){
		return this.${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @param ${sqlDesignInput.sqlDesignInputName!""}
     */
	public void set${sqlDesignInput.sqlDesignInputCode?cap_first}(${object_path?lower_case}.${sqlDesignInput.sqlDesignInputCode?cap_first} ${sqlDesignInput.sqlDesignInputCode?uncap_first}){
		this.${sqlDesignInput.sqlDesignInputCode?uncap_first} = ${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}	
		</#if>
	<#elseif sqlDesignInput.dataType == 14 && !(sqlDesignInput.sqlDesignInputParentId?has_content)>
		<#if sqlDesignInput.arrayFlag==1>
			<#if sqlDesignInput.packageName?? && sqlDesignInput.sqlDesignInputCode == "conditions">
	/**
     * Get ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @return ${sqlDesignInput.sqlDesignInputName!""}
     */
	public java.util.List<${sqlDesignInput.packageName}.ConditionModel> getConditions(){
		return this.conditions;
	}
		
	/**
     * Set ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @param ${sqlDesignInput.sqlDesignInputName!""}
     */
	public void setConditions(java.util.List<${sqlDesignInput.packageName}.ConditionModel> conditions){
		this.conditions = conditions;
	}
			<#else>
	/**
     * Get ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @return ${sqlDesignInput.sqlDesignInputName!""}
     */
	public java.util.List<${projectCode?lower_case}.batch.model.<#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if>> get${sqlDesignInput.sqlDesignInputCode?cap_first}(){
		return this.${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @param ${sqlDesignInput.sqlDesignInputName!""}
     */
	public void set${sqlDesignInput.sqlDesignInputCode?cap_first}(java.util.List<${projectCode?lower_case}.batch.model.<#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if>> ${sqlDesignInput.sqlDesignInputCode?uncap_first}){
		this.${sqlDesignInput.sqlDesignInputCode?uncap_first} = ${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}		
			</#if>
		<#else>
	/**
     * Get ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @return ${sqlDesignInput.sqlDesignInputName!""}
     */
	public ${projectCode?lower_case}.batch.model.<#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if> get${sqlDesignInput.sqlDesignInputCode?cap_first}(){
		return this.${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @param ${sqlDesignInput.sqlDesignInputName!""}
     */
	public void set${sqlDesignInput.sqlDesignInputCode?cap_first}(${projectCode?lower_case}.batch.model.<#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if> ${sqlDesignInput.sqlDesignInputCode?uncap_first}){
		this.${sqlDesignInput.sqlDesignInputCode?uncap_first} = ${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		</#if>
	<#elseif sqlDesignInput.dataType == 16 && !(sqlDesignInput.sqlDesignInputParentId?has_content)>
		<#if sqlDesignInput.arrayFlag==1>
	/**
     * Get ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @return ${sqlDesignInput.sqlDesignInputName!""}
     */
	public java.util.List<${projectCode?lower_case}.batch.commonobject.<#if (sqlDesignInput.moduleCode)??>${sqlDesignInput.moduleCode?lower_case}.</#if><#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if>> get${sqlDesignInput.sqlDesignInputCode?cap_first}(){
		return this.${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @param ${sqlDesignInput.sqlDesignInputName!""}
     */
	public void set${sqlDesignInput.sqlDesignInputCode?cap_first}(java.util.List<${projectCode?lower_case}.batch.commonobject.<#if (sqlDesignInput.moduleCode)??>${sqlDesignInput.moduleCode?lower_case}.</#if><#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if>> ${sqlDesignInput.sqlDesignInputCode?uncap_first}){
		this.${sqlDesignInput.sqlDesignInputCode?uncap_first} = ${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		<#else>
	/**
     * Get ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @return ${sqlDesignInput.sqlDesignInputName!""}
     */
	public ${projectCode?lower_case}.batch.commonobject.<#if (sqlDesignInput.moduleCode)??>${sqlDesignInput.moduleCode?lower_case}.</#if><#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if> get${sqlDesignInput.sqlDesignInputCode?cap_first}(){
		return this.${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @param ${sqlDesignInput.sqlDesignInputName!""}
     */
	public void set${sqlDesignInput.sqlDesignInputCode?cap_first}(${projectCode?lower_case}.batch.commonobject.<#if (sqlDesignInput.moduleCode)??>${sqlDesignInput.moduleCode?lower_case}.</#if><#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if> ${sqlDesignInput.sqlDesignInputCode?uncap_first}){
		this.${sqlDesignInput.sqlDesignInputCode?uncap_first} = ${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		</#if>	
	<#elseif sqlDesignInput.dataType == 17 && !(sqlDesignInput.sqlDesignInputParentId?has_content)>
		<#if sqlDesignInput.arrayFlag==1>
	/**
     * Get ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @return ${sqlDesignInput.sqlDesignInputName!""}
     */
	public java.util.List<<#if sqlDesignInput.pakageExternal??>${sqlDesignInput.pakageExternal?lower_case+"."}</#if><#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if>> get${sqlDesignInput.sqlDesignInputCode?cap_first}(){
		return this.${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @param ${sqlDesignInput.sqlDesignInputName!""}
     */
	public void set${sqlDesignInput.sqlDesignInputCode?cap_first}(java.util.List<<#if sqlDesignInput.pakageExternal??>${sqlDesignInput.pakageExternal?lower_case+"."}</#if><#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if>> ${sqlDesignInput.sqlDesignInputCode?uncap_first}){
		this.${sqlDesignInput.sqlDesignInputCode?uncap_first} = ${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		<#else>
	/**
     * Get ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @return ${sqlDesignInput.sqlDesignInputName!""}
     */
	public <#if sqlDesignInput.pakageExternal??>${sqlDesignInput.pakageExternal?lower_case+"."}</#if><#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if> get${sqlDesignInput.sqlDesignInputCode?cap_first}(){
		return this.${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		
	/**
     * Set ${sqlDesignInput.sqlDesignInputName!""}
     *
     * @param ${sqlDesignInput.sqlDesignInputName!""}
     */
	public void set${sqlDesignInput.sqlDesignInputCode?cap_first}(<#if sqlDesignInput.pakageExternal??>${sqlDesignInput.pakageExternal?lower_case+"."}</#if><#if (sqlDesignInput.objectTypeCode)??>${sqlDesignInput.objectTypeCode?cap_first}</#if><#if !(sqlDesignInput.objectTypeCode)??>${sqlDesignInput.sqlDesignInputName?capitalize?replace("","")}</#if> ${sqlDesignInput.sqlDesignInputCode?uncap_first}){
		this.${sqlDesignInput.sqlDesignInputCode?uncap_first} = ${sqlDesignInput.sqlDesignInputCode?uncap_first};
	}
		</#if>
	</#if>
	</#if>
</#list>
}
