/*
 * @(#)${methodName?cap_first }${blogic.businessLogicCode?cap_first }output.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
package ${package?lower_case};

import java.io.Serializable;
import java.util.List;

/**
 * ${remark!""}
 *
 * @author ${blogic.author!""}
 * @version $Revision$
 */
public class ${methodName?cap_first }${blogic.businessLogicCode?cap_first }Output implements Serializable {

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
	<#assign itemCode = "${item.outputBeanCode?uncap_first}">
	<#assign dataTypeName = "">
		<#switch item.dataType>
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

	<#if item.arrayFlg == true>
		<#if item.dataType == 1 >
			<#assign dataTypeName = "${dataTypeName}[]">
		<#else>
			<#assign dataTypeName = "List<${dataTypeName}>">
		</#if>
	</#if>

	/**
	 * ${item.outputBeanName!""}
	 */
	<#if item.arrayFlg == true && item.dataType == 1>
	private byte[] ${itemCode?uncap_first} = new byte[0];
	<#else>
	private ${dataTypeName} ${itemCode?uncap_first};
	</#if>
	
	/**
     * Get ${item.outputBeanName!""}
     *
     * @return ${item.outputBeanName!""}
     */
	public ${dataTypeName} get${itemCode?cap_first}() {
		return ${itemCode?uncap_first};
	}
		
	/**
     * Set ${item.outputBeanName!""}
     *
     * @param ${item.outputBeanName!""}
     */
	public void set${itemCode?cap_first}(${dataTypeName} ${itemCode?uncap_first}) {
		this.${itemCode?uncap_first} = ${itemCode?uncap_first};
	}
</#macro>