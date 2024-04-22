/*
 * @(#)${inputBean.decisionInputBeanCode?cap_first }.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
package ${package}.${place}${moduleCode?lower_case }.${decision.decisionTbCode?lower_case }inputbean;

import java.io.Serializable;
import java.util.List;

/**
 * ${decision.remark!""}
 *
 * @author 
 * @version $Revision$
 */
public class ${inputBean.decisionInputBeanCode?cap_first } implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	<#if inputBean.singleList?size gt 0>
		<@printSingleList inputBean.singleList/>
	</#if>
}

<#macro printSingleList lstInputBean>
	<#list lstInputBean as item>
		<@printSingle item/>
	</#list>
</#macro>

<#macro printSingle item>
	<#assign dataTypeName = "">
	<#assign newObj = "">
		<#switch item.dataType>
			<#case 0 >
				<#assign dataTypeName = "${item.decisionInputBeanCode?cap_first}">
				<#break>
			<#case 14>
				<#if item.tblDesignCode?has_content>
					<#if isdomain == true>
						<#assign dataTypeName = "${package}.domain.model.${item.tblDesignCode?cap_first}">
					<#else>
						<#assign dataTypeName = "${package}.batch.model.${item.tblDesignCode?cap_first}">
					</#if>
				</#if>
				<#break>
			<#case 16>
				<#if item.commonObjDefiCode?has_content>
					<#if item.moduleCode?has_content>
						<#if isdomain == true>
							<#assign dataTypeName = "${package}.domain.commonobject.${item.moduleCode?lower_case}.${item.commonObjDefiCode?cap_first}">
						<#else>
							<#assign dataTypeName = "${package}.batch.commonobject.${item.moduleCode?lower_case}.${item.commonObjDefiCode?cap_first}">
						</#if>
                    <#else>
						<#if isdomain == true>
                        	<#assign dataTypeName = "${package}.domain.commonobject.${item.commonObjDefiCode?cap_first}">
						<#else>
                        	<#assign dataTypeName = "${package}.batch.commonobject.${item.commonObjDefiCode?cap_first}">
						</#if>
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
	
	<#if item.dataType == 0 || item.dataType == 14 || item.dataType == 16 || item.dataType == 17 >
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
     * ${item.decisionInputBeanName!""}
     *
     */
	private ${dataTypeName} ${item.decisionInputBeanCode?uncap_first} ${newObj};
	
	/**
     * Get ${item.decisionInputBeanName!""}
     *
     * @return ${dataTypeName}
     */
	public ${dataTypeName} get${item.decisionInputBeanCode?cap_first}() {
		return ${item.decisionInputBeanCode?uncap_first};
	}
		
	/**
     * Set ${item.decisionInputBeanName!""}
     *
     */	
	public void set${item.decisionInputBeanCode?cap_first}(${dataTypeName} ${item.decisionInputBeanCode?uncap_first}) {
		this.${item.decisionInputBeanCode?uncap_first} = ${item.decisionInputBeanCode?uncap_first};
	}
</#macro>