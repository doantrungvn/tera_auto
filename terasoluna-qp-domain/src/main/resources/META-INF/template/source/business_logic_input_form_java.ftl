/*
 * @(#)${businessLogic.businessLogicCode?cap_first }InputForm.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
package ${package?lower_case}.app.${module.moduleCode?lower_case };

import java.io.Serializable;
<#if singleInputList?size gt 0>
<#list singleInputList as item>
	<#switch item.dataType>
		<#case 0 >
import ${package}.app.${module.moduleCode?lower_case }.${businessLogic.businessLogicCode?lower_case}inputform.${item.inputBeanCode?cap_first};
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
public class ${businessLogic.businessLogicCode?cap_first }InputForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	<#if singleInputList?size gt 0>
		<@printSingleList singleInputList/>
	</#if>
}

<#macro printSingleList lstInputBean>
	<#list lstInputBean as item>
		<@printSingle item/>
	</#list>
</#macro>

<#macro printSingle item>
	<#assign validAnotation = "">
	<#assign newObj = " ">
	<#assign itemCode = "${item.inputBeanCode?uncap_first}">
	<#assign dataTypeName = "">
		<#switch item.dataType>
			<#case 0 >
				<#assign validAnotation = "@javax.validation.Valid">
				<#assign dataTypeName = "${item.inputBeanCode?cap_first}">
				<#break>
			<#case 14>
				<#if item.tblDesignCode?has_content>
					<#assign dataTypeName = "${package?lower_case}.domain.model.${item.tblDesignCode?cap_first}">
				</#if>
				<#break>
			<#case 16>
				<#if item.commonObjDefiCode?has_content>
					<#assign validAnotation = "@javax.validation.Valid">
					<#if item.moduleCode?has_content>
                        <#assign dataTypeName = "${package?lower_case}.domain.commonobject.${item.moduleCode?lower_case}.${item.commonObjDefiCode?cap_first}">
                    <#else>
                        <#assign dataTypeName = "${package?lower_case}.domain.commonobject.${item.commonObjDefiCode?cap_first}">
                    </#if>
				</#if>
				<#break>
			<#case 17>
				<#if item.externalObjDefiCode?has_content>
					<#assign validAnotation = "@javax.validation.Valid">
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
	<#if item.arrayFlg == true>
		<#if item.dataType != 1>
			<#assign dataTypeName = "java.util.List<${dataTypeName}>">
		<#else>
			<#assign dataTypeName = "byte []">
		</#if>
	<#elseif item.dataType == 0 || item.dataType == 14 || item.dataType == 16 || item.dataType == 17>
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
     * ${item.inputBeanName!""}
     *
     */
	<#if validAnotation?length gt 0 && item.dataType != 14 && item.dataType != 16 && item.dataType != 17>
	${validAnotation}
	</#if>
	<#if item.validateStandar?length gt 0 && item.dataType != 14 && item.dataType != 16 && item.dataType != 17>
	${item.validateStandar}
	</#if>
	private ${dataTypeName} ${itemCode?uncap_first}${newObj};
	
	/**
     * Get ${item.inputBeanName!""}
     *
     * @return ${dataTypeName}
     */
	public ${dataTypeName} get${itemCode?cap_first}() {
		return ${itemCode?uncap_first};
	}
		
	/**
     * Set ${item.inputBeanName!""}
     *
     */	
	public void set${itemCode?cap_first}(${dataTypeName} ${itemCode?uncap_first}) {
		this.${itemCode?uncap_first} = ${itemCode?uncap_first};
	}
</#macro>