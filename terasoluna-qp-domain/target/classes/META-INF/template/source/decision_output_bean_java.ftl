/*
 * @(#)${decision.decisionTbName?cap_first }OutputBean.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
package ${package}.${place}${moduleCode?lower_case };

import java.io.Serializable;
import java.util.List;
<#if singleOutputList?size gt 0>
	<#list singleOutputList as item>
		<#if item.dataType == 0>
import ${package}.${place}${moduleCode?lower_case }.${decision.decisionTbCode?lower_case }outputbean.${item.decisionOutputBeanCode?cap_first};
		</#if>
	</#list>
</#if>

/**
 * ${decision.remark!""}
 *
 * @author 
 * @version $Revision$
 */
public class ${decision.decisionTbName?cap_first }OutputBean implements Serializable {

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
	<#assign dataTypeName = "">
	<#assign newObj = "">
		<#switch item.dataType>
			<#case 0 >
				<#assign dataTypeName = "${item.decisionOutputBeanCode?cap_first}">
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
     * ${item.decisionOutputBeanName!""}
     *
     */
	private ${dataTypeName} ${item.decisionOutputBeanCode?uncap_first} ${newObj};
	
	/**
     * Get ${item.decisionOutputBeanName!""}
     *
     * @return ${dataTypeName}
     */
	public ${dataTypeName} get${item.decisionOutputBeanCode?cap_first}() {
		return ${item.decisionOutputBeanCode?uncap_first};
	}

	/**
     * Set ${item.decisionOutputBeanName!""}
     *
     */	
	public void set${item.decisionOutputBeanCode?cap_first}(${dataTypeName} ${item.decisionOutputBeanCode?uncap_first}) {
		this.${item.decisionOutputBeanCode?uncap_first} = ${item.decisionOutputBeanCode?uncap_first};
	}
</#macro>