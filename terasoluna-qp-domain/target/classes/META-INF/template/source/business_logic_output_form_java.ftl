/*
 * @(#)${businessLogic.businessLogicCode?cap_first }OutputForm.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
package ${package}.app.${module.moduleCode?lower_case };

import java.io.Serializable;
import java.util.List;
<#if singleOutputList?size gt 0>
	<#list singleOutputList as item>
		<#switch item.dataType>
			<#case 0 >
import ${package}.app.${module.moduleCode?lower_case }.${businessLogic.businessLogicCode?lower_case }outputform.${item.outputBeanCode?cap_first};
				<#break>
		</#switch>
	</#list>
</#if>

/**
 * ${businessLogic.remark!""}
 *
 * @author ${businessLogic.author!""}
 * @version $Revision$
 */
public class ${businessLogic.businessLogicCode?cap_first }OutputForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	<#if singleOutputList?size gt 0>
		<@printSingleList singleOutputList/>
	</#if>
}

<#macro printSingleList lstOutputBean>
	<#list lstOutputBean as item>
		<@printSingle item/>
	</#list>
</#macro>

<#macro printSingle item>
	<#assign itemCode = "${item.outputBeanCode}">
	<#assign dataTypeName = "">
	<#assign newObj = " ">
	<#assign newListObj = " ">

		<#switch item.dataType>
			<#case 0 >
				<#assign dataTypeName = "${item.outputBeanCode?cap_first}">
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
			<#case 10>
			<#case 9>
			<#case 11>
			<#case 12>
			<#case 13>
			<#case 15>
				<#assign dataTypeName = "String">
				<#break>
		</#switch>
	<#if item.arrayFlg == true && item.dataType != 1>
		<#if item.dataType == 0 || item.dataType == 14 || item.dataType == 16 || item.dataType == 17>
			<#if item.externalObjDefiCode?has_content>
				<#if item.dataType == 17 && item.externalObjDefiCode == "multipartFile">
				</#if>
			<#else>
				<#if item.dataType != 17>
				<#assign newListObj = "= new java.util.ArrayList<${dataTypeName}>()">
				</#if>
			</#if>
		</#if>
		<#assign dataTypeName = "java.util.List<${dataTypeName}>">
	<#elseif item.arrayFlg == true && item.dataType == 1 && (businessLogic.patternType == 1 || (businessLogic.patternType == 3 && businessLogic.returnType == 0)) >
		<#assign dataTypeName = "String">
	<#elseif item.arrayFlg == false>
		<#if item.dataType == 0 || item.dataType == 14 || item.dataType == 16 || item.dataType == 17>
			<#if item.externalObjDefiCode?has_content>
				<#if item.dataType == 17 && item.externalObjDefiCode == "multipartFile">
				</#if>
			<#else>
				<#if item.dataType != 17>
					<#assign newObj = "= new ${dataTypeName}()">
				</#if>
			</#if>
		</#if>
	</#if>
	
	/**
     * ${item.outputBeanCode!""}
     *
     */
	private ${dataTypeName} ${itemCode?uncap_first} ${newListObj} ${newObj};
	
	/**
     * Get ${item.outputBeanCode!""}
     *
     * @return ${dataTypeName}
     */
	public ${dataTypeName} get${itemCode?cap_first}() {
		return ${itemCode?uncap_first};
	}

	/**
     * Set ${item.outputBeanCode!""}
     *
     */	
	public void set${itemCode?cap_first}(${dataTypeName} ${itemCode?uncap_first}) {
		this.${itemCode?uncap_first} = ${itemCode?uncap_first};
	}
</#macro>