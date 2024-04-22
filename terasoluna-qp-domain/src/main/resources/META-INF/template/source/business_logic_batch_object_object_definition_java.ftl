/*
 * @(#)${objectdefinition.objectDefinitionCode?cap_first }.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
package ${package}.batch.${module.moduleCode?lower_case}.${businessLogic.businessLogicCode?lower_case}objectdefinition;

<#list objectdefinition.singleList as item>
	<#switch item.dataType>
		<#case 14>
			<#if item.tblDesignCode?has_content>
import ${package}.batch.model.${item.tblDesignCode?cap_first};
			</#if>
			<#break>
		<#case 17>
			<#if item.packageNameObjExt?has_content && item.externalObjDefiCode?has_content>
import ${item.packageNameObjExt?lower_case}.${item.externalObjDefiCode?cap_first };
			</#if>
			<#break>
	</#switch>
</#list>
<#if objectdefinition.fileFormat??>
import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;
import jp.terasoluna.fw.file.annotation.PaddingType;
import jp.terasoluna.fw.file.annotation.StringConverterToUpperCase;
import jp.terasoluna.fw.file.annotation.StringConverterToLowerCase;
import jp.terasoluna.fw.file.annotation.TrimType;
</#if>
import java.io.Serializable;
import java.util.List;

/**
 * ${businessLogic.remark!""}
 *
 * @author ${businessLogic.author!""}
 * @version $Revision$
 */
<#if objectdefinition.fileFormat??>
@FileFormat(
	<#if objectdefinition.fileFormat.lineFeedCharType != 0>
	lineFeedChar = "${objectdefinition.fileFormat.lineFeedChar}", 
	</#if>
	<#if objectdefinition.fileFormat.fileEncoding == 0> 
	fileEncoding = "UTF-8",
	</#if>
	<#if objectdefinition.fileFormat.fileEncoding == 1> 
	fileEncoding = "UTF-16",
	</#if>
	<#if objectdefinition.fileFormat.fileEncoding == 2> 
	fileEncoding = "MS932",
	</#if>
	overWriteFlg = ${objectdefinition.fileFormat.overwriteFlg?c},
	<#if objectdefinition.fileFormat.encloseCharType != 0> 
	encloseChar = '${objectdefinition.fileFormat.encloseChar}',
	</#if>
	headerLineCount = ${objectdefinition.fileFormat.headLineCount},
	trailerLineCount = ${objectdefinition.fileFormat.trailerLineCount}
)
</#if>
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
					<#assign dataTypeName = "${package}.batch.model.${item.tblDesignCode?cap_first}">
				</#if>
				<#break>
			<#case 16>
				<#if item.commonObjDefiCode?has_content>
					<#if item.moduleCode?has_content>
                        <#assign dataTypeName = "${package}.batch.commonobject.${item.moduleCode?lower_case}.${item.commonObjDefiCode?cap_first}">
                    <#else>
                        <#assign dataTypeName = "${package}.batch.commonobject.${item.commonObjDefiCode?cap_first}">
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
	 */
	<#if item.arrayFlg == true && item.dataType == 1>
	private byte[] ${itemCode} = new byte[0];
	<#else>
	<#if item.inputColumnNo??>
	@InputFileColumn(columnIndex = ${item.inputColumnNo})
	</#if>
	<#if item.outputColumnNo??>
	@OutputFileColumn(columnIndex = ${item.outputColumnNo},
		<#if item.columnFileFormat.paddingType == 1>
		paddingType = PaddingType.LEFT,
		</#if>
		<#if item.columnFileFormat.paddingType == 2>
		paddingType = PaddingType.RIGHT,
		</#if>
		<#if item.columnFileFormat.paddingCharType == 1>
		 paddingChar = '${item.columnFileFormat.paddingChar}', 
		</#if>
		<#if item.columnFileFormat.specifyByte != "">
	    bytes = ${item.columnFileFormat.specifyByte},
	    </#if>
	    <#if item.columnFileFormat.columnFormat == 0>
	    columnFormat = "###,###,###",
	    </#if>
	    <#if item.columnFileFormat.columnFormat == 1>
	    columnFormat = "yyyy/MM/dd",
	    </#if>
	    <#if item.columnFileFormat.trimType == 1>
	    trimType = TrimType.LEFT,
	    </#if>
	    <#if item.columnFileFormat.trimType == 2>
	    trimType = TrimType.RIGHT,
	    </#if>
	    <#if item.columnFileFormat.trimType == 3>
	    trimType = TrimType.BOTH,
	    </#if>
	    <#if item.columnFileFormat.trimChar != "">
	    trimChar = '${item.columnFileFormat.trimChar}',
	    </#if>
	    <#if item.columnFileFormat.converter == 1>
	    stringConverter = StringConverterToUpperCase.class,
	    </#if>
	    <#if item.columnFileFormat.converter == 2>
	    stringConverter = StringConverterToLowerCase.class,
	    </#if>
	    columnEncloseChar = Character.MIN_VALUE
	)
	</#if>
	private ${dataTypeName} ${itemCode?uncap_first} ${newListObj} ${newObj};
	</#if>

	/**
     * Get ${item.objectDefinitionName!""}
     *
     * @return ${item.objectDefinitionName!""}
     */
	public ${dataTypeName} get${itemCode?cap_first}() {
		return ${itemCode?uncap_first};
	}
		
	/**
     * Set ${item.objectDefinitionName!""}
     *
     * @param ${item.objectDefinitionName!""}
     */
	public void set${itemCode?cap_first}(${dataTypeName} ${itemCode?uncap_first}) {
		this.${itemCode?uncap_first} = ${itemCode?uncap_first};
	}
</#macro>