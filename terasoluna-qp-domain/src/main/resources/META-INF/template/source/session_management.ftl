/*
 * @(#)CustomizeSessionUtils.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
package org.terasoluna.qp.app.common.ultils;

<#if sessionManagementList?size gt 0>
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
<#list sessionManagementList as item>
	<#switch item.dataType>
		<#case 14>
			<#if item.objectIdAutocomplete?has_content>
import ${package?lower_case}.domain.model.${item.objectIdAutocomplete?cap_first};
			</#if>
			<#break>
		<#case 16>
			<#if item.objectIdAutocomplete?has_content>
                <#if item.module_code?has_content>
import ${package?lower_case}.domain.commonobject.${item.module_code?lower_case}.${item.objectIdAutocomplete?cap_first };
                <#else>
import ${package?lower_case}.domain.commonobject.${item.objectIdAutocomplete?cap_first };
                </#if>
			</#if>
			<#break>
		<#case 17>
			<#if item.packageNameExternalObject?has_content && item.objectIdAutocomplete?has_content>
import ${item.packageNameExternalObject?lower_case}.${item.objectIdAutocomplete?cap_first };
			</#if>
			<#break>
	</#switch>
</#list>
</#if>

/**
 * 
 *
 * @author 
 * @version $Revision$
 */
public class CustomizeSessionUtils {

<#if sessionManagementList?size gt 0>
		<@printList sessionManagementList/>
</#if>
<#macro printList sessionManagementList>
	<#list sessionManagementList as item>
		<@printSingle item/>
	</#list>
</#macro>
<#macro printSingle item>
	<#assign dataTypeName = "">
		<#switch item.dataType>
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
			<#case 14>
			<#case 16>
			<#case 17>
				<#if item.objectIdAutocomplete?has_content>
				<#assign dataTypeName = "${item.objectIdAutocomplete?cap_first}">
				<#break>
				</#if>
		</#switch>
	<#if item.arrayFlg == true>
		<#if item.dataType == 1 >
			<#assign dataTypeName = "byte []">
		<#elseif item.dataType == 0 || item.dataType == 14 || item.dataType == 16 || item.dataType == 17 >			
			<#assign dataTypeName = "java.util.List<${dataTypeName}>">
		<#else>
			<#assign dataTypeName = "java.util.List<${dataTypeName}>">
		</#if>
	</#if>
	
	/**
     * Get ${item.sessionManagementName!""}
     *
     * @return ${dataTypeName}
     */
	public static ${dataTypeName} get${item.sessionManagementCode?cap_first}() {
		${dataTypeName} ${item.sessionManagementCode?uncap_first} = (${dataTypeName}) SessionUtils.get("${item.sessionManagementCode?uncap_first}");
		
		if (${item.sessionManagementCode?uncap_first} == null)
			throw new SystemException("", "");
			
		return ${item.sessionManagementCode?uncap_first};
	}
	
</#macro>
}
