/*
 * @(#)${table.tableCode?cap_first }.java
 * 
 * Copyright (c) 2016 NTTDATA Corporation.
 */
package ${pakage}.${place}.model;

import java.io.Serializable;

/**
 * ${table.tableName!""}
 *
 * @author ${table.author!""}
 * @version $Revision$
 */
public class ${table.tableCode?cap_first } implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	<#list table.listTableDesignDetails as item>
	
	/**
     * ${item.name!""}
     *
     */
	private ${item.dataTypeName} ${item.code?uncap_first};
	
	/**
     * Get ${item.name!""}
     *
     * @return ${item.dataTypeName}
     */
	public ${item.dataTypeName} get${item.name?cap_first}() {
		return ${item.code?uncap_first};
	}
		
	/**
     * Set ${item.name!""}
     *
     */	
	public void set${item.name?cap_first}(${item.dataTypeName} ${item.code?uncap_first}) {
		this.${item.code?uncap_first} = ${item.code?uncap_first};
	}
		
	</#list>
}