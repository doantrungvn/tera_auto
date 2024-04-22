<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<#if moduleCode!=''>
<mapper namespace="${projectCode?lower_case}.batch.repository.${moduleCode?lower_case}.${moduleCode?cap_first}Repository">
<#else>
<mapper namespace="${projectCode?lower_case}.batch.repository.common.CommonRepository">
</#if>
<#list sqlDesignCompounds as sqlDesignCompound>
	<#if (sqlDesignCompound.sqlDesign.sqlPattern)??>
		<#if sqlDesignCompound.sqlDesign.sqlPattern == 0>
		<#if sqlDesignCompound.sqlDesignOutputs?has_content>
			<#if sqlDesignCompound.sqlDesign.designType == 2 || sqlDesignCompound.sqlDesign.designType == 3>
	<resultMap id="${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first + 'ResultMap'}" type="org.terasoluna.qp.batch.model.Autocomplete">
			<#else>
				<#if moduleCode?has_content>
	<resultMap id="${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first + 'ResultMap'}" type="${projectCode?lower_case}.batch.model.${moduleCode?lower_case}.${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first + "OutputBean"}">
				<#else>
	<resultMap id="${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first + 'ResultMap'}" type="${projectCode?lower_case}.batch.model.common.${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first + "OutputBean"}">
				</#if>
			</#if>
				<#list sqlDesignCompound.sqlDesignOutputs as  sqlDesignOutput>
					<#if sqlDesignOutput.mappingColumn?has_content>
		<result property="${sqlDesignOutput.propertyPath?uncap_first}" column="${sqlDesignOutput.mappingColumn}" />
					</#if>
				</#list>
	</resultMap>
			<#if sqlDesignCompound.sqlDesign.designType == 2 || sqlDesignCompound.sqlDesign.designType == 3>
	<!-- <#if sqlDesignCompound.sqlDesign.sqlDesignName?has_content> SQL Design Name: ${sqlDesignCompound.sqlDesign.sqlDesignName} </#if> 
		<#if sqlDesignCompound.sqlDesign.remark?has_content>Remark: ${sqlDesignCompound.sqlDesign.remark} </#if> 
	-->
	<select id="QP_ATC_${sqlDesignCompound.sqlDesign.autoCompleteId}" resultMap="${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first + 'ResultMap'}">
			<#else>
	<!-- <#if sqlDesignCompound.sqlDesign.sqlDesignName?has_content> SQL Design Name: ${sqlDesignCompound.sqlDesign.sqlDesignName} </#if> 
		<#if sqlDesignCompound.sqlDesign.remark?has_content>Remark: ${sqlDesignCompound.sqlDesign.remark} </#if> 
	-->
	<select id="${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}" resultMap="${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first + 'ResultMap'}">
			</#if>
		<#else>
	<!-- <#if sqlDesignCompound.sqlDesign.sqlDesignName?has_content> SQL Design Name: ${sqlDesignCompound.sqlDesign.sqlDesignName} </#if> 
		<#if sqlDesignCompound.sqlDesign.remark?has_content>Remark: ${sqlDesignCompound.sqlDesign.remark} </#if> 
	-->
	<select id="${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}" resultType="Integer">
		</#if>
		<#if (sqlDesignCompound.sqlDesign.sqlText)??>
			<#if (sqlDesignCompound.sqlDesign.designType)?? && sqlDesignCompound.sqlDesign.designType == 5 && (sqlDesignCompound.dialect)?? && (sqlDesignCompound.dialect)== '2' && (sqlDesignCompound.sqlDesign.pageable)?? && sqlDesignCompound.sqlDesign.pageable == 1>
	<!-- <#if sqlDesignCompound.sqlDesign.sqlDesignName?has_content> SQL Design Name: ${sqlDesignCompound.sqlDesign.sqlDesignName} </#if> 
		<#if sqlDesignCompound.sqlDesign.remark?has_content>Remark: ${sqlDesignCompound.sqlDesign.remark} </#if> 
	-->
SELECT * FROM (					
${sqlDesignCompound.sqlDesign.sqlText}
)
WHERE
<![CDATA[
rn BETWEEN  (${'#'}{pageable.offset} +1) AND (${'#'}{pageable.offset} + ${'#'}{pageable.pageSize})
]]>
			<#else>
${sqlDesignCompound.sqlDesign.sqlText}						
			</#if>		
		</#if>
		<#if (sqlDesignCompound.dialect)?? && (sqlDesignCompound.dialect)== '1' && (sqlDesignCompound.sqlDesign.pageable)?? && sqlDesignCompound.sqlDesign.pageable == 1>
		${'<if'} test="pageable.sort != null">
			ORDER BY
			<foreach collection="pageable.sort" item="order" separator=",">
				${'$'}{order.property} ${'$'}{order.direction}
			</foreach>
		${'</if>'}
		<#if (sqlDesignCompound.dialect)?? && (sqlDesignCompound.dialect)== '1'>
		<![CDATA[
			 LIMIT
				 ${'#'}{pageable.pageSize}
			 OFFSET
				 ${'#'}{pageable.offset}
		]]>		
		</#if>
		</#if>
	</select>
		<#elseif sqlDesignCompound.sqlDesign.sqlPattern == 1>
	<!-- <#if sqlDesignCompound.sqlDesign.sqlDesignName?has_content> SQL Design Name: ${sqlDesignCompound.sqlDesign.sqlDesignName} </#if> 
		<#if sqlDesignCompound.sqlDesign.remark?has_content>Remark: ${sqlDesignCompound.sqlDesign.remark} </#if> 
	-->
	<insert id="${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}" >
			<#if (sqlDesignCompound.sqlDesign.sqlText)??>
${sqlDesignCompound.sqlDesign.sqlText}
			</#if>
	</insert>
		<#elseif sqlDesignCompound.sqlDesign.sqlPattern == 2>
	<!-- <#if sqlDesignCompound.sqlDesign.sqlDesignName?has_content> SQL Design Name: ${sqlDesignCompound.sqlDesign.sqlDesignName} </#if> 
		<#if sqlDesignCompound.sqlDesign.remark?has_content>Remark: ${sqlDesignCompound.sqlDesign.remark} </#if> 
	-->
	<update id="${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}">
			<#if (sqlDesignCompound.sqlDesign.sqlText)??>
${sqlDesignCompound.sqlDesign.sqlText}
			</#if>
	</update>
		<#else>
	<!-- <#if sqlDesignCompound.sqlDesign.sqlDesignName?has_content> SQL Design Name: ${sqlDesignCompound.sqlDesign.sqlDesignName} </#if> 
		<#if sqlDesignCompound.sqlDesign.remark?has_content>Remark: ${sqlDesignCompound.sqlDesign.remark} </#if>
	-->
	<delete id="${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}">
			<#if (sqlDesignCompound.sqlDesign.sqlText)??>
${sqlDesignCompound.sqlDesign.sqlText}
			</#if>
	</delete>
		</#if>
	</#if>
</#list>
</mapper>