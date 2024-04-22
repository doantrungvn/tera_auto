<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:util="http://www.springframework.org/schema/util"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
 
 	<!-- Codelist System -->
<#list systemCodelist as item>
	<!-- ${item.codeListName} -->
	<bean id="${item.codeListCode}" class="org.terasoluna.gfw.common.codelist.SimpleMapCodeList">
		<property name="map">
			<util:map>
		<#list item.codelistDetails as a>
			<#assign detail = (a.value)!"-1">
			<#if detail?string != "-1">
				<entry key="${a.value}" value="${a.name}" />
			</#if>
		</#list>
			</util:map>
		</property>
	</bean>
</#list>
 
	<!-- Codelist Screen -->
<#list prepareScreenDefineCodelist as item>
	<bean id="${item.codelistName}" class="org.terasoluna.gfw.common.codelist.SimpleMapCodeList">
		<property name="map">
			<util:map>
		<#list item.userDefineCodelistDetails as a>
			<#assign detail = (a.codelistValue)!"-1">
			<#if detail?string != "-1">
				<entry key="${a.codelistValue}" value="${a.codelistName}" />
			</#if>
		</#list>
			</util:map>
		</property>
	</bean>
</#list>

<#list prepareScreenItemCodelistByProject as item>
	<bean id="${item.codelistName}" class="org.terasoluna.gfw.common.codelist.SimpleMapCodeList">
		<property name="map">
			<util:map>
		<#list item.screenItemCodelistDetails as a>
			<#assign detail = (a.codelistVal)!"-1">
			<#if detail?string != "-1">
				<entry key="${a.codelistVal}" value="${a.codelistName}" />
			</#if>
		</#list>
			</util:map>
		</property>
	</bean>
</#list>
</beans>