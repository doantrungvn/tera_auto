/*
 * @(#)${commonObjectDefinition.commonObjectAttributeCode?cap_first}.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
<#if commonObjectDefinition.module?has_content>
    <#if commonObjectDefinition.module.moduleCode?has_content>
package ${package?lower_case}.${moduleType?lower_case}.commonobject.${commonObjectDefinition.module.moduleCode?lower_case}.${commonObjectDefinition.commonObjectDefinitionCode + "object"?lower_case};
    <#else>
package ${package?lower_case}.${moduleType?lower_case}.commonobject.${commonObjectDefinition.commonObjectDefinitionCode + "object"?lower_case};
    </#if>
<#else>
package ${package?lower_case}.${moduleType?lower_case}.commonobject.${commonObjectDefinition.commonObjectDefinitionCode + "object"?lower_case};
</#if>


import java.io.Serializable;
import java.util.List;

/**
<#if commonObjectDefinition.remark?has_content>
 * ${commonObjectDefinition.remark}
</#if>
 *
<#if commonObjectDefinition.author?has_content>
 * @author ${commonObjectDefinition.author}
</#if>
 * @version $Revision$
 */
public class ${commonObjectAttribute.commonObjectAttributeCode?cap_first} implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	<#if commonObjectAttribute.singleList?size gt 0>
		<@printSingleList commonObjectAttribute.singleList/>
	</#if>
}

<#macro printSingleList lstAttribute>
	<#list lstAttribute as item>
		<@printSingle item/>
	</#list>
</#macro>

<#macro printSingle item>
	<#assign dataTypeName = "">
		<#switch item.dataType>
			<#case 0 >
				<#assign dataTypeName = "${item.commonObjectAttributeCode?cap_first}">
				<#break>
			<#case 1>
				<#assign dataTypeName = "Byte">
				<#break>
			<#case 2>
				<#assign dataTypeName = "Short">
				<#break>
			<#case 3>
				<#assign dataTypeName = "Integer">
				<#break>
			<#case 4>
				<#assign dataTypeName = "Long">
				<#break>
			<#case 5>
				<#assign dataTypeName = "Float">
				<#break>
			<#case 6>
				<#assign dataTypeName = "Double">
				<#break>
			<#case 7>
				<#assign dataTypeName = "Character">
				<#break>
			<#case 8>
				<#assign dataTypeName = "Boolean">
				<#break>
			<#case 10>
				<#assign dataTypeName = "java.math.BigDecimal">
				<#break>	
			<#case 9>
			<#case 11>
			<#case 12>
				<#assign dataTypeName = "java.sql.Timestamp">
				<#break>
			<#case 13>
				<#assign dataTypeName = "java.sql.Time">
				<#break>
			<#case 15>
				<#assign dataTypeName = "java.sql.Date">
				<#break>
			<#case 16>
				<#assign dataTypeName = "${item.commonObjectAttributeCode?cap_first}">
				<#break>
			<#case 17>
				<#assign dataTypeName = "${item.commonObjectAttributeCode?cap_first}">
				<#break>
		</#switch>
	
	<#if item.arrayFlg == true>
		<#if item.dataType == 1 >
			<#assign dataTypeName = "byte []">
		<#else>
			<#assign dataTypeName = "java.util.List<${dataTypeName}>">
		</#if>
	</#if>
	/**
	 * ${item.commonObjectAttributeName}
	 */
	private ${dataTypeName} ${item.commonObjectAttributeCode?uncap_first};
	
	/**
     * Get ${item.commonObjectAttributeName}
     *
     * @return ${item.commonObjectAttributeName}
     */
	public ${dataTypeName} get${item.commonObjectAttributeCode?cap_first}() {
		return ${item.commonObjectAttributeCode?uncap_first};
	}
		
	/**
     * Set ${item.commonObjectAttributeName}
     *
     * @param ${item.commonObjectAttributeName}
     */
	public void set${item.commonObjectAttributeCode?cap_first}(${dataTypeName} ${item.commonObjectAttributeCode?uncap_first}) {
		this.${item.commonObjectAttributeCode?uncap_first} = ${item.commonObjectAttributeCode?uncap_first};
	}
</#macro>