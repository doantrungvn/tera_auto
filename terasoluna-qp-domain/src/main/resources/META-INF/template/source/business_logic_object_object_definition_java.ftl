/*
 * @(#)${objectdefinition.objectDefinitionCode?cap_first }.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
<#if businessLogic.blogicType == 0>
package ${package}.domain.service.${module.moduleCode?lower_case }.${businessLogic.businessLogicCode?lower_case }objectdefinition;
</#if>
<#if businessLogic.blogicType == 2>
package ${package}.domain.service.${module.moduleCode?lower_case }.${businessLogic.businessLogicCode?lower_case }objectdefinition;
</#if>
<#if businessLogic.blogicType == 1>
package ${package}.domain.service.common.${businessLogic.businessLogicCode?lower_case }objectdefinition;
</#if>

import java.io.Serializable;
import java.util.List;

/**
 * ${businessLogic.remark!""}
 *
 * @author ${businessLogic.author!""}
 * @version $Revision$
 */
public class ${objectdefinition.objectDefinitionCode?cap_first } implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	<#if objectdefinition.singleList?size gt 0>
		<@printSingleList objectdefinition.singleList/>
	</#if>
}

<#macro printSingleList lstObjectDefinition>
	<#list lstObjectDefinition as item>
		<@printSingle item/>
	</#list>
</#macro>

<#macro printSingle item>
	<#assign itemCode = "${item.objectDefinitionCode}">
	<#assign dataTypeName = "">
	<#assign newListObj = " ">
	<#assign newObj = " ">
	
		<#switch item.dataType>
			<#case 0 >
				<#assign dataTypeName = "${item.objectDefinitionCode?cap_first}">
				<#break>
			<#case 14>
				<#if item.tblDesignCode?has_content>
					<#assign dataTypeName = "${package}.domain.model.${item.tblDesignCode?cap_first}">
				</#if>
				<#break>
			<#case 16>
				<#if item.commonObjDefiCode?has_content>
					<#if item.moduleCode?has_content>
                        <#assign dataTypeName = "${package}.domain.commonobject.${item.moduleCode?lower_case}.${item.commonObjDefiCode?cap_first}">
                    <#else>
                        <#assign dataTypeName = "${package}.domain.commonobject.${item.commonObjDefiCode?cap_first}">
                    </#if>
				</#if>
				<#break>
			<#case 17>
				<#if item.externalObjDefiCode?has_content>
					<#assign dataTypeName = "${item.packageNameObjExt?lower_case}.${item.externalObjDefiCode?cap_first}">
				</#if>
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
			<#case 9>
				<#assign dataTypeName = "String">
				<#break>
			<#case 10>
				<#assign dataTypeName = "java.math.BigDecimal">
				<#break>
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
		</#switch>

	<#if item.arrayFlg == true>
		<#if item.dataType == 1 >
			<#assign dataTypeName = "byte []">
		<#elseif item.dataType == 0 || item.dataType == 14 || item.dataType == 16 || item.dataType == 17 >
			<#assign newListObj = "= new java.util.ArrayList<${dataTypeName}>()">
			<#assign dataTypeName = "java.util.List<${dataTypeName}>">
		<#else>
			<#assign dataTypeName = "java.util.List<${dataTypeName}>">
		</#if>
	<#elseif item.dataType == 0 || item.dataType == 14 || item.dataType == 16 || item.dataType == 17 >
		<#if item.externalObjDefiCode?has_content>
			<#if item.dataType == 17 && item.externalObjDefiCode == "multipartFile">
			</#if>
		<#else>
			<#if item.dataType != 17>
				<#assign newObj = "= new ${dataTypeName}()">
			</#if>
		</#if>
	</#if>

	/**
     * ${item.objectDefinitionName!""}
     *
     */
	private ${dataTypeName} ${itemCode?uncap_first} ${newListObj} ${newObj};

	/**
     * Get ${item.objectDefinitionName!""}
     *
     * @return ${dataTypeName}
     */
	public ${dataTypeName} get${itemCode?cap_first}() {
		return ${itemCode?uncap_first};
	}
		
	/**
     * Set ${item.objectDefinitionName!""}
     *
     */	
	public void set${itemCode?cap_first}(${dataTypeName} ${itemCode?uncap_first}) {
		this.${itemCode?uncap_first} = ${itemCode?uncap_first};
	}
</#macro>