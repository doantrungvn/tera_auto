/*
 * @(#)${functionMethod.functionMethodCode?cap_first }InputBean.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
 package ${packageName?lower_case}.${functionMaster.functionMasterCode?lower_case}.${functionMethod.functionMethodCode?lower_case};

import java.io.Serializable;
<#if singleInputList?size gt 0>
<#list singleInputList as item>
	<#switch item.dataType>
		<#case 0 >
import ${packageName?lower_case}.${functionMaster.functionMasterCode?lower_case}.${functionMethod.functionMethodCode?lower_case}.${functionMethod.functionMethodCode?lower_case}inputbean.${item.methodInputCode?cap_first };
			<#break>
		<#case 16>
			<#if item.commonObjDefiCode?has_content>
				<#if isdomain == true>
					<#if item.moduleCode?has_content>
import ${package?lower_case}.domain.commonobject.${item.moduleCode?lower_case}.${item.commonObjDefiCode?cap_first };
					<#else>
import ${package?lower_case}.domain.commonobject.${item.commonObjDefiCode?cap_first};
					</#if>
				<#else>
					<#if item.moduleCode?has_content>
import ${package?lower_case}.batch.commonobject.${item.moduleCode?lower_case}.${item.commonObjDefiCode?cap_first };
					<#else>
import ${package?lower_case}.batch.commonobject.${item.commonObjDefiCode?cap_first};
					</#if>
				</#if>
			</#if>
			<#break>
	</#switch>
</#list>
</#if>

/**
 * ${functionMaster.remark!""}
 *
 * @author ${functionMaster.author!""}
 * @version $Revision$
 */
public class ${functionMethod.functionMethodCode?cap_first }InputBean implements Serializable {

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
	<#assign itemCode = "${item.methodInputCode?uncap_first}">
	<#assign dataTypeName = "">
	<#assign newListObj = " ">
	<#assign newObj = " ">
		<#switch item.dataType>
			<#case 0 >
				<#assign dataTypeName = "${item.methodInputCode?cap_first}">
				<#break>
			<#case 16>
				<#if item.commonObjDefiCode?has_content>
					<#assign dataTypeName = "${item.commonObjDefiCode?cap_first }">
				</#if>
				<#break>
			<#case 17>
				<#if item.externalObjDefiCode?has_content && item.packageNameObjExt?has_content>
					<#assign dataTypeName = "${item.packageNameObjExt?lower_case}.${item.externalObjDefiCode?cap_first }">
				</#if>
				<#break>
			<#case 1>
				<#assign dataTypeName = "byte">
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
	
	<#if item.arrayFlg == 1>
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
     * ${item.methodInputName!""}
     *
     */
	private ${dataTypeName} ${itemCode?uncap_first} ${newListObj} ${newObj};
	
	/**
     * Get ${item.methodInputName!""}
     *
     * @return ${dataTypeName}
     */
	public ${dataTypeName} get${itemCode?cap_first}() {
		return ${itemCode?uncap_first};
	}
		
	/**
     * Set ${item.methodInputName!""}
     *
     */	
	public void set${itemCode?cap_first}(${dataTypeName} ${itemCode?uncap_first}) {
		this.${itemCode?uncap_first} = ${itemCode?uncap_first};
	}
</#macro>