/*
<#if moduleCode!=''>
 * @(#)${moduleCode?cap_first}Repository.java
 <#else>
 * @(#)CommonRepository.java
 </#if>
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
<#assign dataType_h = {"1":"byte[]","2":"Short","3":"Integer","4":"Long","5":"Float","6":"Double","7":"Character","8":"Boolean","9":"String","10":"java.math.BigDecimal","11":"java.sql.Timestamp","12":"java.sql.Timestamp","13":"java.sql.Time","15":"java.sql.Date"}>
<#if moduleCode?has_content>
package ${projectCode?lower_case}.batch.repository.${moduleCode?lower_case};

import ${projectCode?lower_case}.batch.model.${moduleCode?lower_case}.*;
<#else>
package ${projectCode?lower_case}.batch.repository.common;

import ${projectCode?lower_case}.batch.model.common.*;
</#if>
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 
 *
 * @author 
 * @version $Revision$
 */
<#if moduleCode!=''>
public interface ${moduleCode?cap_first}Repository {
<#else>
public interface CommonRepository {
</#if>
<#list sqlDesignCompounds as sqlDesignCompound>
	<#if (sqlDesignCompound.sqlDesign.sqlPattern)??>
		<#if sqlDesignCompound.sqlDesign.sqlPattern == 0>
			<#if sqlDesignCompound.sqlDesign.designType == 2 || sqlDesignCompound.sqlDesign.designType == 3>
	
	/** <#if sqlDesignCompound.sqlDesign.sqlDesignName?has_content> SQL Design Name: ${sqlDesignCompound.sqlDesign.sqlDesignName} </#if> 
	*	<#if sqlDesignCompound.sqlDesign.sqlPatternString?has_content>SQL Pattern: ${sqlDesignCompound.sqlDesign.sqlPatternString} </#if> 
	*	<#if sqlDesignCompound.sqlDesign.remark?has_content>Remark: ${sqlDesignCompound.sqlDesign.remark} </#if>
	*/
	public List<org.terasoluna.qp.batch.model.Autocomplete> QP_ATC_${sqlDesignCompound.sqlDesign.autoCompleteId}(${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first}InputBean input);		
			<#else>
				<#if sqlDesignCompound.sqlDesignOutputs?has_content>
					<#if sqlDesignCompound.sqlDesign.returnType == 0>
	
	/** <#if sqlDesignCompound.sqlDesign.sqlDesignName?has_content> SQL Design Name: ${sqlDesignCompound.sqlDesign.sqlDesignName} </#if> 
	*	<#if sqlDesignCompound.sqlDesign.sqlPatternString?has_content>SQL Pattern: ${sqlDesignCompound.sqlDesign.sqlPatternString} </#if> 
	*	<#if sqlDesignCompound.sqlDesign.remark?has_content>Remark: ${sqlDesignCompound.sqlDesign.remark} </#if>
	*/						
	public ${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first}OutputBean ${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}(${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first}InputBean input);						
					<#else>
						<#if (sqlDesignCompound.sqlDesign.pageable)?? && sqlDesignCompound.sqlDesign.pageable == 1>
	
	/** <#if sqlDesignCompound.sqlDesign.sqlDesignName?has_content> SQL Design Name: ${sqlDesignCompound.sqlDesign.sqlDesignName} </#if> 
	*	<#if sqlDesignCompound.sqlDesign.sqlPatternString?has_content>SQL Pattern: ${sqlDesignCompound.sqlDesign.sqlPatternString} </#if> 
	*	<#if sqlDesignCompound.sqlDesign.remark?has_content>Remark: ${sqlDesignCompound.sqlDesign.remark} </#if>
	*/
	public List<${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first}OutputBean> ${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}(@Param("input") ${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first}InputBean input, @Param("pageable") Pageable pageable);
						<#else>
	
	/** <#if sqlDesignCompound.sqlDesign.sqlDesignName?has_content> SQL Design Name: ${sqlDesignCompound.sqlDesign.sqlDesignName} </#if> 
	*	<#if sqlDesignCompound.sqlDesign.sqlPatternString?has_content>SQL Pattern: ${sqlDesignCompound.sqlDesign.sqlPatternString} </#if> 
	*	<#if sqlDesignCompound.sqlDesign.remark?has_content>Remark: ${sqlDesignCompound.sqlDesign.remark} </#if>
	*/
	public List<${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first}OutputBean> ${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}(${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first}InputBean input);										
						</#if>
						
					</#if>
				<#else>
	
	/** <#if sqlDesignCompound.sqlDesign.sqlDesignName?has_content> SQL Design Name: ${sqlDesignCompound.sqlDesign.sqlDesignName} </#if> 
	*	<#if sqlDesignCompound.sqlDesign.sqlPatternString?has_content>SQL Pattern: ${sqlDesignCompound.sqlDesign.sqlPatternString} </#if> 
	*	<#if sqlDesignCompound.sqlDesign.remark?has_content>Remark: ${sqlDesignCompound.sqlDesign.remark} </#if>
	*/
	public Integer ${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}(${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first}InputBean input);			
				</#if>
			</#if>
		<#else>
			<#if sqlDesignCompound.sqlDesignOutputs?has_content>
				<#list sqlDesignCompound.sqlDesignOutputs as sqlDesignOutput>
	
	/** <#if sqlDesignCompound.sqlDesign.sqlDesignName?has_content> SQL Design Name: ${sqlDesignCompound.sqlDesign.sqlDesignName} </#if> 
	*	<#if sqlDesignCompound.sqlDesign.sqlPatternString?has_content>SQL Pattern: ${sqlDesignCompound.sqlDesign.sqlPatternString} </#if> 
	*	<#if sqlDesignCompound.sqlDesign.remark?has_content>Remark: ${sqlDesignCompound.sqlDesign.remark} </#if>
	*/
	public ${dataType_h[sqlDesignOutput.dataType?string]} ${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}(${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first}InputBean input);
				</#list>
			<#else>
	
	/** <#if sqlDesignCompound.sqlDesign.sqlDesignName?has_content> SQL Design Name: ${sqlDesignCompound.sqlDesign.sqlDesignName} </#if> 
	*	<#if sqlDesignCompound.sqlDesign.sqlPatternString?has_content>SQL Pattern: ${sqlDesignCompound.sqlDesign.sqlPatternString} </#if> 
	*	<#if sqlDesignCompound.sqlDesign.remark?has_content>Remark: ${sqlDesignCompound.sqlDesign.remark} </#if>
	*/
	public Integer ${sqlDesignCompound.sqlDesign.sqlDesignCode?uncap_first}(${sqlDesignCompound.sqlDesign.sqlDesignCode?cap_first}InputBean input);		
			</#if>	
		</#if>
	</#if>
</#list>
}
