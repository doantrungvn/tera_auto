/*
 * @(#)${businessLogic.businessLogicCode?cap_first }OutputForm.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
package ${package}.app.webservice.${module.moduleCode?lower_case };

import java.io.Serializable;
import java.util.List;
import org.terasoluna.gfw.common.codelist.ExistInCodeList;


<#if singleOutputList?size gt 0>
	<#list singleOutputList as item>
		<#switch item.dataType>
			<#case 0 >
import ${package}.app.webservice.${module.moduleCode?lower_case }.${businessLogic.businessLogicCode?lower_case }outputform.${item.outputBeanCode?cap_first};
				<#break>
			<#case 14>
				<#if item.tblDesignCode?has_content>
import ${package}.domain.model.${item.tblDesignCode?cap_first};
				</#if>
				<#break>
			<#case 16>
				<#if item.commonObjDefiCode?has_content>
                    <#if item.moduleCode?has_content>
import ${package}.domain.commonobject.${item.moduleCode?lower_case}.${item.commonObjDefiCode?cap_first };
                    <#else>
import ${package}.domain.commonobject.${item.commonObjDefiCode?cap_first };
                    </#if>
				</#if>
				<#break>
			<#case 17>
				<#if item.packageNameObjExt?has_content && item.externalObjDefiCode?has_content>
import ${item.packageNameObjExt?lower_case}.${item.externalObjDefiCode?cap_first };
				</#if>
				<#break>
		</#switch>
	</#list>
</#if>
<#if businessLogic.returnType == 5 >
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
</#if>

/**
 * ${businessLogic.remark!""}
 *
 * @author ${businessLogic.author!""}
 * @version $Revision$
 */
<#if businessLogic.returnType == 5 >
@XmlRootElement(name = "${businessLogic.businessLogicCode?cap_first }OutputForm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="${businessLogic.businessLogicCode?cap_first }OutputForm", namespace="${businessLogic.businessLogicCode?cap_first }OutputForm")
</#if>
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
		<#switch item.dataType>
			<#case 0 >
				<#assign dataTypeName = "${item.outputBeanCode?cap_first}">
				<#break>
			<#case 14>
				<#if item.tblDesignCode?has_content>
					<#assign dataTypeName = "${item.tblDesignCode?cap_first}">
				</#if>
				<#break>
			<#case 16>
				<#if item.commonObjDefiCode?has_content>
					<#assign dataTypeName = "${item.commonObjDefiCode?cap_first}">
				</#if>
				<#break>
			<#case 17>
				<#if item.externalObjDefiCode?has_content>
					<#assign dataTypeName = "${item.externalObjDefiCode?cap_first}">
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
				<#assign dataTypeName = "java.math.BigDecimal">
				<#break>
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
	</#if>
	
	/**
     * ${item.outputBeanName!""}
     *
     */
	<#if businessLogic.returnType == 5 >
	    <#if item.arrayFlg && item.dataType != 1>    
	@XmlElementWrapper(name="${itemCode?uncap_first}s")
	@XmlElement(name="${itemCode?uncap_first}")
		<#else> 
	@XmlElement(name="${itemCode?uncap_first}")
		</#if>	   
	</#if>
	private ${dataTypeName} ${itemCode?uncap_first};
	
	/**
     * Get ${item.outputBeanName!""}
     *
     * @return ${dataTypeName}
     */
	public ${dataTypeName} get${itemCode?cap_first}() {
		return ${itemCode?uncap_first};
	}

	/**
     * Set ${item.outputBeanName!""}
     *
     */	
	public void set${itemCode?cap_first}(${dataTypeName} ${itemCode}) {
		this.${itemCode?uncap_first} = ${itemCode?uncap_first};
	}
</#macro>