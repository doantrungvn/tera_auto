/*
 * @(#)${commonObjectDefinition.commonObjectDefinitionCode?cap_first}.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
<#if commonObjectDefinition.module?has_content>
    <#if commonObjectDefinition.module.moduleCode?has_content>
package ${package?lower_case}.${moduleType?lower_case}.commonobject.${commonObjectDefinition.module.moduleCode?lower_case};
    <#else>
package ${package?lower_case}.${moduleType?lower_case}.commonobject;
    </#if>
<#else>
package ${package?lower_case}.${moduleType?lower_case}.commonobject;
</#if>

import java.io.Serializable;
import java.util.List;
<#assign isFileInput = false>
<#if singleList?size gt 0>
<#list singleList as item>
	<#if item.dataType == 0>
        <#if commonObjectDefinition.module?has_content>
            <#if commonObjectDefinition.module.moduleCode?has_content>
import ${package?lower_case}.${moduleType?lower_case}.commonobject.${commonObjectDefinition.module.moduleCode?lower_case}.${commonObjectDefinition.commonObjectDefinitionCode?lower_case}.${item.objectDefinitionCode?cap_first};
    <#else>
import ${package?lower_case}.${moduleType?lower_case}.commonobject.${commonObjectDefinition.commonObjectDefinitionCode + "object"?lower_case}.${item.objectDefinitionCode?cap_first};
    </#if>
<#else>
import ${package?lower_case}.${moduleType?lower_case}.commonobject.${commonObjectDefinition.commonObjectDefinitionCode + "object"?lower_case}.${item.objectDefinitionCode?cap_first};
</#if>
	</#if>
	<#if item.dataType == 16>
	
<#if item.objectDefinitionId?has_content>
    <#if item.moduleCode?has_content>
import ${package?lower_case}.${moduleType?lower_case}.commonobject.${item.moduleCode?lower_case}.${item.objectDefinitionCode?cap_first};
    <#else>
import ${package?lower_case}.${moduleType?lower_case}.commonobject.${item.objectDefinitionCode?cap_first};
    </#if>
<#else>
import ${package?lower_case}.${moduleType?lower_case}.commonobject.${item.objectDefinitionCode?cap_first};
</#if>
	</#if>
	<#if item.dataType == 17>
import ${item.externalPackageName?lower_case}.${item.objectDefinitionCode?cap_first};
	</#if>
	<#if item.inputColumnNo??>
	<#assign isFileInput = true>
	</#if>
</#list>
</#if>
<#if isFileInput>
import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
</#if>

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
<#if isFileInput>
@FileFormat(lineFeedChar = "\r\n", fileEncoding = "UTF-8", overWriteFlg = true)
</#if>
public class ${commonObjectDefinition.commonObjectDefinitionCode?cap_first} implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	<#if singleList?size gt 0>
		<@printSingleList singleList/>
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
			<#case 16>
				<#assign dataTypeName = "${item.objectDefinitionCode?cap_first}">
				<#break>
			<#case 17>
				<#assign dataTypeName = "${item.objectDefinitionCode?cap_first}">
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
	<#if item.inputColumnNo??>
	@InputFileColumn(columnIndex = ${item.inputColumnNo})
	</#if>
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